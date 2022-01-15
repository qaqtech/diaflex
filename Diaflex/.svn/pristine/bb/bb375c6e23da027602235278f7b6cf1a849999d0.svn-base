package ft.com.marketing;

//~--- non-JDK imports --------------------------------------------------------

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.ExcelUtilObj;
import ft.com.GtMgr;
import ft.com.InfoMgr;
import ft.com.MailAction;
import ft.com.dao.ByrDao;
import ft.com.dao.GtPktDtl;
import ft.com.dao.MAddr;
import ft.com.dao.MNme;
import ft.com.dao.PktDtl;
import ft.com.dao.TrmsDao;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.report.DailySalesReportForm;

import java.io.IOException;

import java.io.OutputStream;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

//~--- JDK imports ------------------------------------------------------------

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class MemoSaleAction extends DispatchAction {
    public MemoSaleAction() {
        super();
    }

    public ActionForward loadSale(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        MemoSaleFrm udf = (MemoSaleFrm) af;
          util.updAccessLog(req,res,"Memo sale", "Memo sale load");
        ArrayList byrList = new ArrayList();
        String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
        String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
        String usrFlg=util.nvl((String)info.getUsrFlg());
        ArrayList rolenmLst=(ArrayList)info.getRoleLst();
        String dfNmeIdn=util.nvl((String)info.getDfNmeIdn());
        String conQ="";
//            ArrayList rfiddevice = ((ArrayList)info.getRfiddevice() == null)?new ArrayList():(ArrayList)info.getRfiddevice();
//            if(rfiddevice.size() == 0) {
//            util.rfidDevice();    
//            }   
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
            "With JAN_PNDG_V as (\n" + 
            "select distinct nme_idn \n" + 
            "from mjan j, jandtl jd \n" + 
            "where j.idn = jd.idn  and j.stt <> 'RT' and\n" + 
            "jd.stt='AP' and j.typ like '%AP' and j.stt='IS'\n" + 
            "and j.typ not in ('Z', 'ZD') and jd.qty > 0\n" + 
            ")\n" + 
            "select n.nme_idn,n.nme byr\n" + 
            "from nme_v n, jan_pndg_v j\n" + 
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
        byrList= (byrList == null)?new ArrayList():(ArrayList)byrList;
//        udf.setValue("byrList", byrList);
        udf.setByrLstFetch(byrList);
        udf.setByrLst(byrList);
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("MEMO_SALE");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("MEMO_SALE");
        allPageDtl.put("MEMO_SALE",pageDtl);
        }
        info.setPageDetails(allPageDtl);
          util.updAccessLog(req,res,"Memo sale", "Memo sale load done");
            finalizeObject(db, util);
        return am.findForward("load");
        }
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
        MemoSaleFrm udf = (MemoSaleFrm) af;
          util.updAccessLog(req,res,"Memo sale", "Memo sale load pkts ");
        SearchQuery srchQuery = new SearchQuery();
        String    view        = "NORMAL";
        ArrayList trmList     = new ArrayList();
        String    memoIds     = "";
        ArrayList params = null;
        String    pand        = req.getParameter("pnd");
        ArrayList pkts        = new ArrayList();
        String    memoSrchTyp = util.nvl((String) udf.getValue("memoSrch"));
        String    vnmLst      = "";
        String    dlvyn      = "Yes";
        String saleExYN="N";
        HashMap dbinfo = info.getDmbsInfoLst();
        String mgmt_Dflt = util.nvl((String)dbinfo.get("MGMT_DFLT"));
        int ct = db.execUpd(" del gt", "delete from gt_pkt", new ArrayList());
            util.noteperson();
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            ArrayList vwprpLst = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");
            HashMap pageDtl=(HashMap)allPageDtl.get("MEMO_SALE");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("MEMO_SALE");
            allPageDtl.put("MEMO_SALE",pageDtl);
            }
        ArrayList pageList=new ArrayList();
        HashMap pageDtlMap=new HashMap();
        String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
                
        if (memoSrchTyp.equals("ByrSrch")) {
            Enumeration reqNme = req.getParameterNames();

            while (reqNme.hasMoreElements()) {
                String paramNm = (String) reqNme.nextElement();

                if (paramNm.indexOf("cb_memo") > -1) {
                    String val = req.getParameter(paramNm);

                    if (memoIds.equals("")) {
                        memoIds = val;
                    } else {
                        memoIds = memoIds + "," + val;
                    }
                }
            }
        } else {
            memoIds =util.nvl(String.valueOf((udf.getMemoIdn())));
            vnmLst=util.nvl((String)udf.getValue("vnmLst"));
        }

        if (pand != null) {
            memoIds = util.nvl(req.getParameter("memoId"));
            String nmeId = util.nvl(req.getParameter("nmeId"));
            String trmId = util.nvl(req.getParameter("trmId"));
            if(!nmeId.equals("") && !trmId.equals("")){
            String getmemoIds="select idn from mjan where nme_idn=? and trms_idn=? and stt='IS' and typ like '%AP' order by idn";    
            params = new ArrayList();
            params.add(nmeId);
            params.add(trmId);
              ArrayList  outLst = db.execSqlLst("get memo Ids", getmemoIds,  params);
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet  rs1 = (ResultSet)outLst.get(1);
            while(rs1.next()){
            if (memoIds.equals(""))
            memoIds = util.nvl(rs1.getString("idn"));
            else
            memoIds = memoIds + "," + util.nvl(rs1.getString("idn"));
            }
                rs1.close();
                pst.close();
            }
        }
        String app = (String)req.getAttribute("APP");
        if(app!=null)
            memoIds = util.nvl((String)req.getAttribute("memoId"));
        if(!vnmLst.equals("")){
            boolean isRtn = true;
            vnmLst=util.getVnm(vnmLst);
            int cnt=0;
            String checkSql ="select distinct c.nmerel_idn  from jandtl a, mstk b , mjan c " + 
            "where a.mstk_idn = b.idn and c.idn = a.idn and a.stt='AP' and c.typ like '%AP' and c.stt='IS' and nvl(b.prt2,'NA')=decode(nvl(b.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(b.prt2,'NA'))" + 
            " and  ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) and b.stt in ('MKAP','MKWA','LB_PRI_AP','MKSA')";
          ArrayList  outLst = db.execSqlLst("check rel", checkSql, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs1 = (ResultSet)outLst.get(1);
            while(rs1.next()){
                cnt++;
            }
            rs1.close();
            pst.close();
            if(cnt==1){  
                isRtn = false;
                String saleIdSql = "select distinct c.idn  from jandtl a, mstk b , mjan c " + 
                "  where a.mstk_idn = b.idn and c.idn = a.idn and a.stt='AP' and c.typ like '%AP' and c.stt='IS' and nvl(b.prt2,'NA')=decode(nvl(b.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(b.prt2,'NA'))" + 
                "  and  ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) and b.stt in ('MKAP','MKWA','LB_PRI_AP','MKSA') ";
             outLst = db.execSqlLst("saleID",saleIdSql, new ArrayList());
               pst = (PreparedStatement)outLst.get(0);
                rs1 = (ResultSet)outLst.get(1);
                while(rs1.next()){
                    String appIdnval = util.nvl(rs1.getString("idn"));
                    if (memoIds.equals("")) {
                        memoIds = appIdnval;
                    } else {
                       memoIds = memoIds + "," + appIdnval;
                    }
                }
                rs1.close();
                pst.close();
            }
            if(isRtn){
                req.setAttribute("RTMSG", "Please verify packets");
               return loadSale(am, af, req, res);
            }
            
        }
 
        if(!memoIds.equals("")) {
            if(memoIds.length()< 80){
              util.updAccessLog(req,res,"Memo sale", "Memo Idn "+memoIds);
            }
            memoIds=memoIds.replaceAll("NULL", "");
            memoIds = util.getVnm(memoIds);
        int cout = 0;

        ArrayList  outLst = db.execSqlLst("check", "select distinct nme_idn from mjan where idn in (" + memoIds + ")", new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
       ResultSet  rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            cout++;
        }
            rs.close();
            pst.close();
        if (cout < 2) {
            String cur="";
            double exh_rte = 0;
             outLst = db.execSqlLst("check", "select max(exh_rte) exhRte , a.nmerel_idn , nvl(b.cur,'USD') cur " + 
            " from mjan a , nmerel b where idn in ("+memoIds+") " + 
            "  and a.typ like '%AP' and a.stt='IS' and a.nmerel_idn = b.nmerel_idn  group by a.nmerel_idn, b.cur ", new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
         
            while(rs.next()){
               exh_rte = Double.parseDouble(util.nvl(rs.getString("exhRte"),"1"));
               udf.setValue("exhRte", exh_rte); 
               cur=util.nvl(rs.getString("cur").toUpperCase());
            }
            rs.close();
            pst.close();
            
            udf.setValue("cur",cur);
            pageList=(ArrayList)pageDtl.get("FNLEXHRTE");
            if(pageList!=null && pageList.size() >0){
                for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                dflt_val=(String)pageDtlMap.get("dflt_val");
                if(dflt_val.equals("Y")){
                udf.setValue("fnlexhRteDIS", "Y"); 
                udf.setValue("fnlexhRte", exh_rte); 
                }
                }
            }
            
            pageList=(ArrayList)pageDtl.get("SALEX");
            if(pageList!=null && pageList.size() >0){
                for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                dflt_val=(String)pageDtlMap.get("dflt_val");
                saleExYN="Y"; 
                }
            }
            
            String dlvpopup_yn="N";
            pageList=(ArrayList)pageDtl.get("DLV_POPUP");
            if(pageList!=null && pageList.size() >0){
                for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                dflt_val=(String)pageDtlMap.get("dflt_val");
                if(dflt_val.equals(cur)){
                dlvpopup_yn="Y"; 
                }
                }
            }
            udf.setValue("DLV_POPUP", dlvpopup_yn);
            String summaryStr="";
            if(!vnmLst.equals(""))
            summaryStr = summaryStr+" and  ( m.vnm in ("+vnmLst+") or m.tfl3 in ("+vnmLst+")) ";
            
            String getAvgDis="with jSmry as (\n" + 
            "select 1 eg\n" + 
            " , sum(decode(m.pkt_ty, 'MIX', jd.qty, 1)) qty \n" + 
            " , sum(decode(m.pkt_ty, 'NR', trunc(jd.cts, 2), jd.cts)) cts\n" + 
            " , sum(decode(m.pkt_ty, 'NR', 0, jd.cts_rtn)) cts_rtn\n" + 
            " , sum(decode(m.pkt_ty, 'NR', 0, jd.cts_sal)) cts_sal\n" + 
            " , sum(decode(m.pkt_ty, 'NR', trunc(jd.cts, 2), jd.cts)*quot/nvl(j.exh_rte, 1)) quotVlu\n" + 
            " , sum(decode(m.pkt_ty, 'NR', trunc(jd.cts, 2), jd.cts)*nvl(fnl_sal, quot)/nvl(j.exh_rte, 1)) fnlVlu\n" + 
            " , sum(decode(m.pkt_ty, 'NR', trunc(jd.cts, 2), jd.cts)*decode(m.rap_rte, 1, 0, m.rap_rte)) rapVlu\n" + 
            "from mstk m, mjan j, jandtl jd\n" + 
            "where 1 = 1\n" + 
            " and j.idn = jd.idn\n" + 
            " and m.idn = jd.mstk_idn and jd.idn in ("+memoIds+") \n" + 
            " and m.stt in ('MKAP','MKWA','LB_PRI_AP','MKSA') and jd.stt = 'AP' and nvl(m.prt2,'NA')=decode(nvl(m.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(m.prt2,'NA'))\n" + summaryStr+
            ")\n" + 
            "select qty, cts, cts_rtn, cts_sal,trunc(quotVlu,2) quotVlu,trunc(fnlVlu,2) fnlVlu, rapVlu\n" + 
            " , trunc(fnlVlu/cts, 2) avgRte, decode(rapVlu, 0, null, trunc((fnlVlu*100/rapVlu) - 100, 2)) avgRapDis\n" + 
            "from jSmry";
           outLst = db.execSqlLst(" Memo Info", getAvgDis , new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
            if(rs.next()){
                udf.setAvgDis(rs.getString("avgRapDis"));
                udf.setAvgPrc(rs.getString("avgRte"));
                udf.setQty(rs.getString("qty"));
                udf.setCts(rs.getString("cts"));
                udf.setVlu(rs.getString("fnlVlu"));
            }
            rs.close();
            pst.close();
                  String getMemoInfo =
                " select a.nme_idn, a.nmerel_idn,byr.get_trms(a.nmerel_idn) trms,GET_TRM_DSC(a.nmerel_idn) shortTrms,  byr.get_nm(a.nme_idn) byr, byr.get_nm(c.emp_idn) SalEmp,a.typ, a.qty, a.cts, a.vlu, to_char(a.dte, 'dd-Mon-rrrr HH24:mi:ss') dte,a.rmk,a.note_person "
                + " from mjan a ,mnme c where a.idn in (" + memoIds + ") and a.stt='IS' and a.nme_idn=c.nme_idn and a.typ like '%AP' ";

            params = new ArrayList();
          outLst = db.execSqlLst(" Memo Info", getMemoInfo, params);
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            if (rs.next()) {
                udf.setNmeIdn(rs.getInt("nme_idn"));
                udf.setRelIdn(rs.getInt("nmerel_idn"));
                udf.setByr(rs.getString("byr"));
                udf.setTyp(rs.getString("typ"));
                udf.setDte(rs.getString("dte"));
                udf.setValue("trmsLb", rs.getString("trms"));
                udf.setValue("noteperson", util.nvl(rs.getString("note_person")));
                udf.setValue("rmk", util.nvl(rs.getString("rmk")));
                HashMap buyerDtlMap=util.getBuyerDtl(String.valueOf(rs.getInt("nme_idn")));
                udf.setValue("email",util.nvl((String)buyerDtlMap.get("EML")));
                udf.setValue("mobile",util.nvl((String)buyerDtlMap.get("MBL")));
                udf.setValue("office",util.nvl((String)buyerDtlMap.get("OFC")));
                udf.setValue("SALEMP", util.nvl(rs.getString("SalEmp")));
                udf.setValue("shortTrms", util.nvl(rs.getString("shortTrms")));
                udf.setValue("LOY_CTG", util.nvl((String)buyerDtlMap.get("LOY_CTG")));

                if (view.equalsIgnoreCase("Normal")) {
                    String mprpStr = "";
                    String mdlPrpsQ = "Select Decode ( Rank() Over ( Order By Srt),1,' ',',')||''''||Prp||''''||' '||\n" + 
                    "Upper(replace(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1'),'CERT NO.','CERT')) \n" + 
                    "str From Rep_Prp Rp Where rp.MDL = ? order by srt " ;
                    ArrayList ary = new ArrayList();
                    ary.add("MEMO_RTRN");
                    ArrayList outLst1 = db.execSqlLst("mprp ", mdlPrpsQ , ary);
                    PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
                    ResultSet rs1 = (ResultSet)outLst1.get(1);
                    while(rs1.next()) {
                    String val = util.nvl((String)rs1.getString("str"));
                    mprpStr = mprpStr +" "+val;
                    }
                    rs1.close();
                    pst1.close();
                    String getPktData = " with STKDTL as " +
                    " ( Select b.sk1,a.idn ,b.cert_no certno, a.qty ,a.mstk_idn,nvl(nvl(nvl(st.txt,st.dte),st.num),st.prt1) atr , st.mprp, nvl(fnl_sal, quot) memoQuot,trunc(trunc(a.cts,2)*nvl(a.fnl_sal, a.quot), 2) vlu, quot, nvl(fnl_sal, quot) fnl_sal , DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), b.vnm) vnm "
                        + ", b.cts, nvl(b.upr, b.cmp) rate, b.rap_rte raprte, a.stt,to_char(trunc(a.cts,2) * b.rap_rte, '99999999990.00') rapVlu,trunc((((nvl(a.fnl_sal,a.quot)/"+exh_rte+")-nvl(a.rte,0))/greatest(a.rte,1))*100, 2) plper,b.stt pktstt "
                        + " ,  to_char(decode(rap_rte, 1, '', trunc(((nvl(b.upr, b.cmp)/greatest(b.rap_rte,1))*100)-100,2)),9990.99) dis,  to_char(decode(rap_rte, 1, '', trunc(((nvl(fnl_sal, quot)/"+exh_rte+"/greatest(b.rap_rte,1))*100)-100,2)),9990.99) mDis,to_char(a.dte, 'dd-Mon-rrrr') dte "
                        + " from stk_dtl st,jandtl a, mstk b where st.mstk_idn=b.idn and a.mstk_idn = b.idn " + " and a.idn in (" + memoIds+ ") and b.stt in ('MKAP','MKWA','LB_PRI_AP','MKSA') and nvl(b.prt2,'NA')=decode(nvl(b.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(b.prt2,'NA'))" ;
                        
                    if(!vnmLst.equals("")){
                    getPktData = getPktData + " and ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) ";
                    }
                    // " and decode(typ, 'I','IS', 'E', 'IS', 'WH', 'IS', 'AP') = stt ";
                    getPktData = getPktData + " and a.stt = 'AP'" + " \n"+
                    "and exists (select 1 from rep_prp rp where rp.MDL = 'MEMO_RTRN' and st.mprp = rp.prp)  And st.Grp = 1) " +
                    " Select * from stkDtl PIVOT " +
                    " ( max(atr) " +
                    " for mprp in ( "+mprpStr+" ) ) order by 1 " ;
                    
                    params = new ArrayList();
                   outLst1 = db.execSqlLst(" memo pkts", getPktData, params);
                   pst1 = (PreparedStatement)outLst1.get(0);
                    rs1 = (ResultSet)outLst1.get(1);
                    while (rs1.next()) {
                        PktDtl pkt    = new PktDtl();
                        long   pktIdn = rs1.getLong("mstk_idn");

                        pkt.setPktIdn(pktIdn);
                        pkt.setMemoId(util.nvl(rs1.getString("idn")));
                        pkt.setRapRte(util.nvl(rs1.getString("raprte")));
                        pkt.setQty(util.nvl(rs1.getString("qty")));
                        pkt.setCts(util.nvl(rs1.getString("cts")));
                        pkt.setRte(util.nvl(rs1.getString("rate")));
                        pkt.setMemoQuot(util.nvl(rs1.getString("memoQuot")));
                        pkt.setByrRte(util.nvl(rs1.getString("quot")));
                        pkt.setFnlRte(util.nvl(rs1.getString("fnl_sal")));
                        pkt.setDis(rs1.getString("dis"));
                        pkt.setByrDis(rs1.getString("mDis"));
                        pkt.setVnm(rs1.getString("vnm"));
                        pkt.setValue("SalAmt", util.nvl(rs1.getString("vlu")));
                        pkt.setValue("appdte", util.nvl(rs1.getString("dte")));
                        pkt.setValue("pktstt", util.nvl(rs1.getString("pktstt")));
                        pkt.setValue("rapVlu", util.nvl(rs1.getString("rapVlu")));
                        pkt.setValue("plper", util.nvl(rs1.getString("plper")));
                        String lStt = rs1.getString("stt");

                        pkt.setStt(lStt);
                        udf.setValue("stt_" + pktIdn, lStt);

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
                        String fldVal = util.nvl((String)rs1.getString(fldName));
                        
                        pkt.setValue(vwPrp, fldVal);
                        }
                        if(vwprpLst.indexOf("CP_DIS") > -1) 
                        pkt.setValue("CP_DIS",util.calculateDisVlu(util.nvl((String)pkt.getRapRte()).trim(),util.nvl((String)pkt.getCts()).trim(),util.nvl((String)pkt.getValue("CP")),"DIS"));
                        pkts.add(pkt);
                    }
                    rs1.close();
                   pst1.close();
                }

                req.setAttribute("view", "Y");
            }
            rs.close();
            pst.close();
            udf.setValue("noofmemoid",memoIds);
            String insertQ =
                " insert into gt_pkt(mstk_idn,flg)" + 
                " select mstk_idn,a.stt from jandtl a, mstk b where a.mstk_idn = b.idn " + " and a.idn in (" + memoIds+ ") and b.stt in ('MKAP','MKWA','LB_PRI_AP','MKSA') and nvl(b.prt2,'NA')=decode(nvl(b.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(b.prt2,'NA'))";
                
            if(!vnmLst.equals("")){
            insertQ = insertQ + " and ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) ";
            }
            // " and decode(typ, 'I','IS', 'E', 'IS', 'WH', 'IS', 'AP') = stt ";
            insertQ = insertQ + " and a.stt = 'AP'";
            ct = db.execUpd("Insert", insertQ, new ArrayList());
        } else {
            req.setAttribute("error", "please select memoIds of one buyer");
            req.setAttribute("view", "N");
            return  loadSale(am, af, req, res);
        }}else {
            req.setAttribute("error", "please select memoIds of one buyer");
            req.setAttribute("view", "N");
            return  loadSale(am, af, req, res);
        }

        loadSale(am, af, req, res);
        ArrayList ary = new ArrayList();
        trmList = srchQuery.getTerm(req,res, udf.getNmeIdn());

//        String getTrm = " select a.idn, a.term from mtrms a, nmerel b "
//                        + " where a.idn = b.trms_idn and b.nme_idn = ? " + " order by a.pct, a.dys  ";
//        ArrayList ary = new ArrayList();
//
//        ary.add(String.valueOf(udf.getNmeIdn()));
//        rs = db.execSql("favSrch", getTrm, ary);
//
//        while (rs.next()) {
//            TrmsDao trms = new TrmsDao();
//
//            trms.setRelId(rs.getInt(1));
//            trms.setTrmDtl(rs.getString(2));
//            trmList.add(trms);
//        }

        ArrayList byrList = new ArrayList();
        String    getByr  =
            "select nme_idn, a.nme byr from nme_v a " + " where nme_idn = ? "
            + " or exists (select 1 from nme_grp_dtl c, nme_grp b where a.nme_idn = c.nme_idn and b.nme_grp_idn = c.nme_grp_idn "
            + " and b.nme_idn = ? and b.typ = 'BUYER' and c.vld_dte is null) " + " order by 2 ";

        ary = new ArrayList();
        ary.add(String.valueOf(udf.getNmeIdn()));
        ary.add(String.valueOf(udf.getNmeIdn()));
         ArrayList outLst = db.execSqlLst("byr", getByr, ary);
         PreparedStatement pst = (PreparedStatement)outLst.get(0);
       ResultSet   rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            ByrDao byr = new ByrDao();

            byr.setByrIdn(rs.getInt("nme_idn"));

            String nme = util.nvl(rs.getString("byr"));

            if (nme.indexOf("&") > -1) {
                nme = nme.replaceAll("&", "&amp;");
            }

            byr.setByrNme(nme);
            byrList.add(byr);
        }
        rs.close();
        pst.close();
        String brokerSql =
            "select   byr.get_nm(mbrk1_idn) brk1  , mbrk1_comm , mbrk1_paid , byr.get_nm(mbrk2_idn) brk2  ,mbrk2_comm , mbrk2_paid, byr.get_nm(mbrk3_idn) brk3  ,"
            + "mbrk3_comm , mbrk3_paid, byr.get_nm(mbrk4_idn) brk4  ,mbrk4_comm , mbrk4_paid , byr.get_nm(aadat_idn) aaDat  , aadat_paid , aadat_comm  from nmerel where nmerel_idn = ?";

        ary = new ArrayList();
        ary.add(String.valueOf(udf.getRelIdn()));
         outLst = db.execSqlLst("", brokerSql, ary);
          pst = (PreparedStatement)outLst.get(0);
         rs = (ResultSet)outLst.get(1);
        if (rs.next()) {
            udf.setValue("brk1", rs.getString("brk1"));
            udf.setValue("brk2", rs.getString("brk2"));
            udf.setValue("brk3", rs.getString("brk3"));
            udf.setValue("brk4", rs.getString("brk4"));
            udf.setValue("brk1comm", rs.getString("mbrk1_comm"));
            udf.setValue("brk2comm", rs.getString("mbrk2_comm"));
            udf.setValue("brk3comm", rs.getString("mbrk3_comm"));
            udf.setValue("brk4comm", rs.getString("mbrk4_comm"));
            udf.setValue("brk1paid", rs.getString("mbrk1_paid"));
            udf.setValue("brk2paid", rs.getString("mbrk2_paid"));
            udf.setValue("brk3paid", rs.getString("mbrk3_paid"));
            udf.setValue("brk4paid", rs.getString("mbrk4_paid"));
            udf.setValue("aaDat", rs.getString("aaDat"));
            udf.setValue("aadatpaid", rs.getString("aadat_paid"));
            udf.setValue("aadatcomm", rs.getString("aadat_comm"));
        }
        rs.close();
        pst.close();
       ary = new ArrayList();
        String sql =  "select a.addr_idn , unt_num, bldg ||''|| street ||''|| landmark ||''|| area ||''|| b.city_nm||''|| c.country_nm addr "+
                      " from maddr a, mcity b, mcountry c where a.city_idn = b.city_idn and b.country_idn = c.country_idn and a.nme_idn = ?  and a.vld_dte is null  order by a.dflt_yn desc ";
        
        ary.add(String.valueOf(udf.getNmeIdn()));
        ArrayList maddrList = new ArrayList();
          outLst = db.execSqlLst("memo pkt", sql, ary);
           pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            MAddr addr = new MAddr();
            addr.setIdn(rs.getString("addr_idn"));
            addr.setAddr(rs.getString("addr"));
            maddrList.add(addr);
        }
        rs.close();
            pst.close();
//        String bankSql = "select nme_idn, fnme  from mnme  where 1 = 1 and vld_dte is null  and typ in('GROUP','BANK')";
//        rs = db.execSql("bank Sql", bankSql, new ArrayList());
//        while(rs.next()){
//            MAddr addr = new MAddr();
//            addr.setIdn(rs.getString("nme_idn"));
//            addr.setAddr(rs.getString("fnme"));
//            bankList.add(addr);
//        }
//        rs.close();
//        ArrayList groupList = new ArrayList();
//        String companyQ="select a.nme_idn, a.fnme  from mnme A , nme_dtl b where 1 = 1  " + 
//        "and a.nme_idn=b.nme_idn and  b.mprp='PERFORMA' and b.txt='YES' " + 
//        "and a.vld_dte is null  and typ = 'GROUP'";
//        rs = db.execSql("Group Sql", companyQ, new ArrayList());
//        while(rs.next()){
//            MAddr addr = new MAddr();
//            addr.setIdn(rs.getString("nme_idn"));
//            addr.setAddr(rs.getString("fnme"));
//            groupList.add(addr);
//        }
//        rs.close();
        ArrayList bnkAddrList = new ArrayList();
        boolean dfltbankgrp=true;
        String banknmeIdn=  "" ;
        ary = new ArrayList();
        ary.add(String.valueOf(udf.getNmeIdn()));
        String setbnkCouQ="select bnk_idn,courier from  mdlv where idn in(select max(idn) from mdlv where nme_idn=?)";
          outLst = db.execSqlLst("setbnkCouQ", setbnkCouQ, ary);
           pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        if(rs.next()){
            banknmeIdn=util.nvl(rs.getString("bnk_idn"));
            udf.setValue("courier", util.nvl(rs.getString("courier"),"NA"));
        }
        rs.close();
        pst.close();
            
        if(!banknmeIdn.equals("")){
            String defltBnkQ="select b.nme_idn bnkidn,a.addr_idn addr_idn,  a.bldg ||''|| a.street ||''|| a.landmark ||''|| a.area addr \n" + 
            "from maddr a, mnme b\n" + 
            "where 1 = 1 \n" + 
            "and a.nme_idn = b.nme_idn  and b.typ in('GROUP','BANK')\n" + 
            "and b.nme_idn = ? order by a.dflt_yn desc";
            ary = new ArrayList();
            ary.add(banknmeIdn);
          outLst = db.execSqlLst("defltBnkQ", defltBnkQ, ary);
           pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                udf.setValue("bankIdn", util.nvl(rs.getString("bnkidn"),"NA"));
                MAddr addr = new MAddr();
                addr.setIdn(rs.getString("addr_idn"));
                addr.setAddr(rs.getString("addr"));
                bnkAddrList.add(addr);
            }
            rs.close();
            pst.close();
            dfltbankgrp=false;
        }
        
        if(dfltbankgrp){
        String defltBnkQ="select c.nme_idn bnkidn,b.mprp,b.txt,a.addr_idn addr_idn,  a.bldg ||''|| a.street ||''|| a.landmark ||''|| a.area addr  \n" + 
        "        from maddr a,nme_dtl b , mnme c\n" + 
        "        where 1 = 1 and a.nme_idn=b.nme_idn\n" + 
        "        and a.nme_idn = c.nme_idn  and c.typ in('GROUP','BANK')\n" + 
        "        and b.mprp='PERFORMABANK' and b.txt='Y'\n" + 
        "        and b.vld_dte is null and not exists (select 1 from mnme n where n.grp_nme_idn <> c.grp_nme_idn and n.nme_idn = ?)";
            ary = new ArrayList();
            ary.add(String.valueOf(udf.getNmeIdn()));
          outLst = db.execSqlLst("defltBnkQ", defltBnkQ, ary);
           pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            udf.setValue("bankIdn", util.nvl(rs.getString("bnkidn"),"NA"));
            MAddr addr = new MAddr();
            addr.setIdn(rs.getString("addr_idn"));
            addr.setAddr(rs.getString("addr"));
            bnkAddrList.add(addr);
        }
        rs.close();
        pst.close();
        }
            pageList=(ArrayList)pageDtl.get("SET_CO_IDN");
            if(pageList!=null && pageList.size() >0){
                for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                dflt_val=(String)pageDtlMap.get("dflt_val");
                if(dflt_val.equals("Y")){
                    String defltcoQ="select grp_nme_idn from mnme n where n.nme_idn = ?";
                    ary = new ArrayList();
                    ary.add(String.valueOf(udf.getNmeIdn()));
                  outLst = db.execSqlLst("defltcoQ", defltcoQ,ary);
                   pst = (PreparedStatement)outLst.get(0);
                  rs = (ResultSet)outLst.get(1);
                    while(rs.next()){
                        udf.setValue("grpIdn", util.nvl(rs.getString("grp_nme_idn"),"NA"));
                    }
                    rs.close();
                    pst.close();
                }
                }
            }
        ArrayList chargesLst=new ArrayList();
        String chargesQ="Select ct.idn,ct.typ,ct.dsc,ct.stg optional,ct.flg,ct.fctr,ct.db_call,ct.rmk\n" + 
        "    From Df_Pg A,Df_Pg_Itm B,Charges_Typ ct\n" + 
        "    Where A.Idn = B.Pg_Idn\n" + 
        "    and b.fld_nme=ct.typ\n" + 
        "    And A.Mdl = 'MEMO_SALE'\n" + 
        "    and b.itm_typ=?\n" + 
        "    And A.Stt='A'\n" + 
        "    And B.Stt='A'\n" + 
        "    and ct.stt='A'\n" + 
        "    And A.Vld_Dte Is Null \n" + 
        "    And Not Exists (Select 1 From Df_Pg_Itm_Usr C Where B.Idn=C.Itm_Idn And C.Stt='IA' And C.Usr_Idn=?)\n" + 
        "    order by b.itm_typ,b.srt";
        ary = new ArrayList();
        ary.add("SALE_CHARGES");
        ary.add(String.valueOf(info.getUsrId()));
          outLst = db.execSqlLst("chargesQ", chargesQ,ary);
           pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            HashMap dtl=new HashMap();
            dtl.put("idn",util.nvl(rs.getString("idn")));
            dtl.put("dsc",util.nvl(rs.getString("dsc")));
            dtl.put("autoopt",util.nvl(rs.getString("optional")));
            dtl.put("flg",util.nvl(rs.getString("flg")));
            dtl.put("typ",util.nvl(rs.getString("typ")));
            dtl.put("fctr",util.nvl(rs.getString("fctr")));
            dtl.put("fun",util.nvl(rs.getString("db_call")));
            dtl.put("rmk",util.nvl(rs.getString("rmk")));
            chargesLst.add(dtl);
        }
        rs.close();
        pst.close();
        if(!mgmt_Dflt.equals("")){
            udf.setValue("MGMT",mgmt_Dflt);
            udf.setValue("MGMT_save",mgmt_Dflt);    
        }
        ArrayList thruBankList = new ArrayList();
        String thruBankSql = "select b.nme_dtl_idn , b.txt from mnme a , nme_dtl b " + 
                             "where a.nme_idn = b.nme_idn and b.mprp like 'THRU_BANK%' and a.nme_idn=? and b.vld_dte is null";
        ary = new ArrayList();
        ary.add(String.valueOf(udf.getNmeIdn()));
          outLst = db.execSqlLst("thruBank", thruBankSql, ary );
           pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            MNme dtl = new MNme();
            String txt=util.nvl(rs.getString("txt"));
            dtl.setIdn(util.nvl(rs.getString("nme_dtl_idn")));
            txt.replaceAll("\\#"," ");
            dtl.setFnme(txt);
            thruBankList.add(dtl);
        }
            rs.close();
            pst.close();
            String days="365";
            pageList=(ArrayList)pageDtl.get("BYR_VLU");
            if(pageList!=null && pageList.size() >0){
                for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                dflt_val=(String)pageDtlMap.get("dflt_val");
                lov_qry=(String)pageDtlMap.get("lov_qry");
                days=dflt_val;
                udf.setValue("saleVlu", "Y");
                ary = new ArrayList();
                ary.add(String.valueOf(udf.getNmeIdn()));
                String salevluQ="select \n" + 
                "sum(trunc(c.cts,2)*(nvl(b.fnl_usd, b.quot))) vlu\n" + 
                "from msal a, sal_pkt_dtl_v b, mstk c\n" + 
                "where a.idn=b.idn and b.mstk_idn=c.idn and c.stt in('MKSD','MKSL','BRC_MKSD')\n" + 
                "and a.typ in ('LS','SL') and b.stt in('SL','DLV') and a.nme_idn=? and trunc(a.dte)>=trunc(sysdate)-"+days;
                  outLst = db.execSqlLst("setbnkCouQ", salevluQ, ary);
                   pst = (PreparedStatement)outLst.get(0);
                  rs = (ResultSet)outLst.get(1);
                if(rs.next()){
                udf.setValue("saleVlubyr", lov_qry+util.nvl(rs.getString("vlu"),"NA"));
                }
                rs.close();
                pst.close();
                }
            }
        fisalcharges(req);
            
        ArrayList empList= srchQuery.getByrList(req,res);
        udf.setEmpLst(empList); 
        udf.setValue("saleExYN", saleExYN);
        ArrayList daytrmList = srchQuery.getdayTerm(req,res);
        ArrayList groupList = srchQuery.getgroupList(req, res);
        ArrayList bankList = srchQuery.getBankList(req, res);
        ArrayList courierList = srchQuery.getcourierList(req, res);
        session.setAttribute("chargesLst", chargesLst);
        udf.setThruBankList(thruBankList);
        udf.setCourierList(courierList);
        udf.setBnkAddrList(bnkAddrList);
        udf.setGroupList(groupList);
        udf.setBankList(bankList);
        udf.setAddrList(maddrList);
//        udf.setValue("byrList", byrList);
        udf.setByrLstFetch(byrList);
        udf.setByrLst(byrList);
        udf.setValue("byrIdn", udf.getNmeIdn());
        udf.setValue("byrTrm", udf.getRelIdn());
        udf.setValue("memoIdn", memoIds);
        req.setAttribute("memoIdn", String.valueOf(memoIds));
        udf.setByrIdn(String.valueOf(udf.getNmeIdn()));
        udf.setTrmsLst(trmList);
        udf.setDaytrmsLst(daytrmList);
        udf.setPkts(pkts);
        info.setValue("PKTS", pkts);
        pageList=(ArrayList)pageDtl.get("DLVPOPYN");
        if(pageList!=null && pageList.size() >0){
           for(int j=0;j<pageList.size();j++){
           pageDtlMap=(HashMap)pageList.get(j);
           dlvyn=(String)pageDtlMap.get("dflt_val");
           }
        }
        udf.setValue("isDLV", dlvyn);
        util.updAccessLog(req,res,"Memo sale", "Memo sale load pkts size "+pkts.size());
        req.setAttribute("NMEIDN",String.valueOf(udf.getNmeIdn()));
            finalizeObject(db, util);
        return am.findForward("view");
        }
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
        synchronized(this){
        ArrayList params = null;
          util.updAccessLog(req,res,"Memo sale", "Memo sale save");
        MemoSaleFrm udf = (MemoSaleFrm) af;

        ArrayList pkts     = new ArrayList();
        String    pktNmsSl = "";
        String    pktNmsRT = "";
        String buttonPressed = "";
        int       appSlIdn = 0;
        
        String sl_typ="";
        if (udf.getValue("consignment")!=null)
        buttonPressed = "consignment";
        
        String typ="SL";
        String isDlv = util.nvl((String)udf.getValue("isDLV"));
        String noteperson = util.nvl((String)udf.getValue("noteperson"));
        String globalrmk = util.nvl((String)udf.getValue("rmk"));
        String mnlsale_id = util.nvl((String)udf.getValue("mnlsale_id"));
        String empId = util.nvl((String)udf.getValue("empId"));
        String empNme="";
        if(isDlv.equals("No"))
            typ="LS";
        if(buttonPressed.equalsIgnoreCase("consignment"))
            typ="CS";
        String dlvDte = util.nvl((String)udf.getValue("dlvDte"));
        
        pkts = (ArrayList) info.getValue("PKTS");    // udf.getPkts();
//        for (int i = 0; i < pkts.size(); i++) {
//            PktDtl pkt     = (PktDtl) pkts.get(i);
//            long   lPktIdn = pkt.getPktIdn();
//            String memoIdn = pkt.getMemoId();
//            String lStt    = util.nvl((String) udf.getValue("stt_" + lPktIdn));
//            String insertGt = "insert into gt_pkt_jan select ? , ? , sysdate , ? from dual";
//            ArrayList ary = new ArrayList();
//            ary.add(Long.toString(lPktIdn));
//            ary.add(memoIdn);
//            ary.add(lStt);
//            int ct = db.execUpd("insertGt", insertGt, ary);
//        }
        if(!empId.equals("")){
            String byrNmeQry = "select GET_ANA_BYR_NM(?) from dual";
            ArrayList ary = new ArrayList();
            ary.add(empId);
            ResultSet rs = db.execSql(" Srch Prp ", byrNmeQry, ary);
            while (rs.next()) {
                   empNme = rs.getString(1);  
            }
            rs.close();   
        }
            
        for (int i = 0; i < pkts.size(); i++) {
            PktDtl pkt     = (PktDtl) pkts.get(i);
            long   lPktIdn = pkt.getPktIdn();
            String memoIdn = pkt.getMemoId();
            String lStt    = util.nvl((String) udf.getValue("stt_" + lPktIdn));
            String vnm = pkt.getVnm();
          
            String updPkt = "";

            if (lStt.equals("SL")) {
                if (appSlIdn == 0) {
                    sl_typ = util.nvl((String)udf.getValue("sale_typ"));
                    ArrayList ary = new ArrayList();

                    ary.add(Integer.toString(udf.getNmeIdn()));
                    ary.add(Integer.toString(udf.getRelIdn()));
                    ary.add(udf.getValue("byrTrm"));
                    ary.add(udf.getByrIdn());
                    ary.add(udf.getValue("addr"));
                    ary.add(typ);
                    ary.add("IS");
                    ary.add(memoIdn);
                    ary.add("NN");
                    ary.add(nvl((String) udf.getValue("aadatpaid"), "Y"));
                    ary.add(nvl((String) udf.getValue("brk1paid"), "Y"));
                    ary.add(nvl((String) udf.getValue("brk2paid"), "Y"));
                    ary.add(nvl((String) udf.getValue("brk3paid"), "Y"));
                    ary.add(nvl((String) udf.getValue("brk4paid"), "Y"));
                    ary.add(util.nvl((String)udf.getValue("exhRte")));
                    ary.add(util.nvl((String) udf.getValue("bankIdn")));
                    ary.add(util.nvl((String) udf.getValue("courier")));
                    ary.add(util.nvl((String)udf.getValue("throubnk")));
                    ary.add(util.nvl((String)udf.getValue("grpIdn")));
                    ArrayList out = new ArrayList();

                    out.add("I");

                    CallableStatement cst = null;

                    cst = db.execCall(
                        "MKE_HDR ",
                        "sl_pkg.gen_hdr(pByrId => ?, pRlnIdn => ?, pTrmsIdn => ?, pInvNmeIdn => ?, pInvAddrIdn => ?, pTyp => ?, pStt => ?, pFrmId => ? , pFlg => ? , paadat_paid => ?, pMBRK1_PAID => ? , pMBRK2_PAID => ?, pMBRK3_PAID => ?, pMBRK4_PAID => ?, pExhRte => ?, p_bank_id => ? ,p_courier => ? ,pThruBnk => ?,co_idn => ?,  pIdn => ? )", ary, out);
                    appSlIdn = cst.getInt(ary.size()+1);
                  cst.close();
                  cst=null;                  
                }
                if (appSlIdn != 0) {
                params = new ArrayList();
                if(buttonPressed.equalsIgnoreCase("consignment"))
                params.add("CS");
                else
                params.add("SL");
                params.add(Long.toString(lPktIdn));
                params.add(memoIdn);

                int upJan = db.execUpd("updateJAN", "update jandtl set stt=? , dte_sal=sysdate,aud_modified_by = pack_var.get_usr where mstk_idn=? and idn= ? ", params);

                // params = new ArrayList();
                // params.add(String.valueOf(appSlIdn));
                // params.add(memoIdn);
                // int ct = db.execCall("sale from memo", "sl_pkg.Sal_Frm_Memo(pIdn => ?, pMemoIdn =>?)", params);
                params = new ArrayList();
                params.add(String.valueOf(appSlIdn));
                params.add(memoIdn);
                params.add(Long.toString(lPktIdn));
                params.add(pkt.getQty());
                params.add(pkt.getCts());
                params.add(pkt.getMemoQuot());
                if(buttonPressed.equalsIgnoreCase("consignment"))
                params.add("CS");
                else
                params.add("SL");

                int SalPkt = db.execCall("sale from memo",
                                         "sl_pkg.Sal_Pkt(" + "pIdn => ?" + ", pMemoIdn =>?" + ", pStkIdn => ?"
                                         + ", pQty => ?" + ", pCts => ?" + ", pFnlSal=> ?" + ", pStt => ?)", params);
                if(!sl_typ.equals("")){
                  String mstkLab = "select cert_lab from mstk where idn=?";
                    params = new ArrayList();
                    params.add(Long.toString(lPktIdn));
                  ArrayList  outLst = db.execSqlLst("mstkLab", mstkLab, params);
                  PreparedStatement pst = (PreparedStatement)outLst.get(0);
                  ResultSet  rs = (ResultSet)outLst.get(1);
                    if(rs.next()){
                      String cert_lab = rs.getString("cert_lab");
                      ArrayList ary = new ArrayList();
                      ary.add(Long.toString(lPktIdn));
                      ary.add("SL_TYP");
                      ary.add(sl_typ);
                      ary.add(cert_lab);
                      ary.add("C");
                      ary.add("1");
                      String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? , pLab=> ?, pPrpTyp => ?, pgrp => ? )";
                      int ct = db.execCall("stockUpd",stockUpd, ary);
                    }
                    rs.close();
                    pst.close();
                }
                    String mnlsale_idPkt=util.nvl((String) udf.getValue("mnlsale_id_" + lPktIdn));;
                    if(!mnlsale_id.equals("")){
                          ArrayList ary = new ArrayList();
                          ary.add(Long.toString(lPktIdn));
                          ary.add("SAL_ID");
                          ary.add(mnlsale_idPkt);
                          ary.add("T");
                          ary.add("1");
                          String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? ,pPrpTyp => ?, pgrp => ? )";
                          int ct = db.execCall("stockUpd",stockUpd, ary);
                    }
                    if(!empId.equals("")){
                        ArrayList ary = new ArrayList();
                        ary.add(Long.toString(lPktIdn));
                        ary.add("SAL_EMP");
                        ary.add(empNme);
                        ary.add("T");
                        ary.add("1");
                        String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? ,pPrpTyp => ?, pgrp => ? )";
                        int ct = db.execCall("stockUpd",stockUpd, ary);
                    }
              
                pktNmsSl = pktNmsSl +"," +vnm;
                }
            } else if (lStt.equals("RT")) {
                    String rmk = util.nvl((String)udf.getValue("rmk_"+Long.toString(lPktIdn)));
                   
                    params = new ArrayList();
                    params.add(memoIdn);
                    params.add(Long.toString(lPktIdn));
                    params.add(rmk);
                    int ct = db.execCall(" App Pkts", "memo_pkg.rtn_pkt( pMemoId =>?, pStkIdn=> ? , pRem => ?)", params);

                    pktNmsRT = pktNmsRT + "," +vnm;
                    req.setAttribute("RTMSG", "Packets get return ");
            }else if (lStt.equals("CAN")){
                
                
            }else if (lStt.equals("AP")) {
                if(!noteperson.equals("")){
                params = new ArrayList();
                params=new ArrayList();
                params.add(noteperson);
                params.add(globalrmk);
                params.add(memoIdn);
                String updateFlg = "update mjan set NOTE_PERSON=?,rmk=? where idn=?";
                int cntmj = db.execUpd("update msal", updateFlg, params);
                }else if(!globalrmk.equals("")){
                    params = new ArrayList();
                    params.add(globalrmk);
                    params.add(memoIdn);
                    String updateFlg = "update mjan set rmk=? where idn=?";
                    int cntmj = db.execUpd("update msal", updateFlg, params);
                }
            }else{
              
            }
            
            
            String updJanQty = " jan_qty(?) ";

            params = new ArrayList();
            params.add(memoIdn);
            int ct1 = db.execCall("JanQty", updJanQty, params);

        }
        info.setValue("PKTS",new ArrayList());

        if (appSlIdn != 0) {
            
            if(!noteperson.equals("")){
            params = new ArrayList();
            params=new ArrayList();
            params.add(noteperson);
            params.add(globalrmk);
            params.add(String.valueOf(appSlIdn));
            String updateFlg = "update msal set NOTE_PERSON=?,rmk=? where idn=?";
            int cntmj = db.execUpd("update msal", updateFlg, params);
            }else if(!globalrmk.equals("")){
                params = new ArrayList();
                params.add(globalrmk);
                params.add(String.valueOf(appSlIdn));
                String updateFlg = "update msal set rmk=? where idn=?";
                int cntmj = db.execUpd("update msal", updateFlg, params);
            }
            String fnlexhRte=util.nvl((String)udf.getValue("fnlexhRte"));
            if(!fnlexhRte.equals("")){
                params = new ArrayList();
                params.add(fnlexhRte);
                params.add(String.valueOf(appSlIdn));
                int upFnlExh = db.execUpd("upFnlExhRTE", "update msal set fnl_exh_rte=? where idn= ? ", params);
            }
            if(!sl_typ.equals("") && !sl_typ.equals("0")){
                params = new ArrayList();
                params.add(sl_typ);
                params.add(String.valueOf(appSlIdn));
                int sl_typcnt = db.execUpd("sl_typ", "update msal set inv_typ=? where idn= ? ", params);
            }
            if(!mnlsale_id.equals("") && !mnlsale_id.equals("0")){
                params = new ArrayList();
                params.add(mnlsale_id);
                params.add(String.valueOf(appSlIdn));
                int sl_typcnt = db.execUpd("sl_typ", "update msal set sl_slip=? where idn= ? ", params);
            }
            String broker_slab=util.nvl((String)udf.getValue("broker_slab"));
            if(!broker_slab.equals("")){
                params = new ArrayList();
                params.add(broker_slab);
                params.add(String.valueOf(appSlIdn));
                int upFnlExh = db.execUpd("upFnlExhRTE", "update msal set rmk=nvl(rmk,'')||' : '||"+broker_slab+" , brk4_comm = ? where idn= ? ", params);
            }
            String byrDayTrmIdn=util.nvl((String)udf.getValue("byrDayTrm"));
            if(!byrDayTrmIdn.equals("") && !byrDayTrmIdn.equals("0")){
                params = new ArrayList();
                params.add(byrDayTrmIdn);
                params.add(String.valueOf(appSlIdn));
                int upFnlExh = db.execUpd("upFnlExhRTE", "update msal set trms_idn=? where idn= ? ", params);
            }
            if(!empId.equals("")){
                params = new ArrayList();
                params.add(empId);
                params.add(String.valueOf(appSlIdn));
                int nt = db.execUpd("emp_idn", "update msal set emp_idn=? where idn= ? ", params); 
            }
            
            ArrayList chargesLst=(ArrayList)session.getAttribute("chargesLst");
            if(chargesLst!=null && chargesLst.size()>0){
            for(int i=0;i<chargesLst.size();i++){
            HashMap dtl=new HashMap();
            dtl=(HashMap)chargesLst.get(i);
            String idn=(String)dtl.get("idn");
            String dsc=(String)dtl.get("dsc");
            String flg=(String)dtl.get("flg");
            String typcharge=(String)dtl.get("typ");
            String fctr=(String)dtl.get("fctr");
            String fun=(String)dtl.get("fun");
            String autoopt=util.nvl((String)dtl.get("autoopt"));
            String calcdis= util.nvl((String)udf.getValue(typcharge+"_save"),"0");  
            String autooptional= util.nvl((String)udf.getValue(typcharge+"_AUTOOPT"));  
            String vlu= util.nvl((String)udf.getValue("vluamt"));
            String exhRte=util.nvl((String)udf.getValue("exhRte"),"1");
            String extrarmk= util.nvl((String)udf.getValue(typcharge+"_rmksave")); 
            if(!vlu.equals("") && !vlu.equals("0")  && !calcdis.equals("NaN")  && !vlu.equals("NaN")){
            if((!calcdis.equals("") && !calcdis.equals("0")) || (autoopt.equals("OPT"))){
            String insertQ="Insert Into Trns_Charges (trns_idn, ref_typ, ref_idn,charges_idn,amt,amt_usd,charges,CHARGES_PCT,net_usd,rmk,stt,flg)\n" + 
            "VALUES (TRNS_CHARGES_SEQ.nextval, 'SAL', ?,?,?,?,?,?,?,?,'A',?)";  
            ArrayList ary=new ArrayList();
            ary.add(String.valueOf(appSlIdn));
            ary.add(idn);
            ary.add(vlu);
            float amt_usd=Float.parseFloat(vlu)/Float.parseFloat(exhRte);
            ary.add(String.valueOf(amt_usd));
            ary.add(calcdis);
            ary.add(calcdis);
            float net_usd=amt_usd+Float.parseFloat(calcdis);
            ary.add(String.valueOf(net_usd));
            ary.add(extrarmk);
            ary.add(autooptional);
            int ct = db.execUpd("Insert", insertQ, ary);
            }
            }
            }
            }
            
            params = new ArrayList();
            params.add(String.valueOf(appSlIdn));

            int calCt = db.execCall("calQuot", "SL_PKG.Cal_Fnl_Quot(pIdn => ?)", params);

            params = new ArrayList();
            params.add(String.valueOf(appSlIdn));

            int upQuery = db.execCall("calQuot", "SL_PKG.UPD_QTY(pIdn => ? )", params);
            
            if(typ.equals("LS")){
            params = new ArrayList();
            params.add(String.valueOf(appSlIdn));
            int upbrcDlv = db.execCall("DP_GEN_BRC_LS", "DP_GEN_BRC_LS(pSalIdn => ?)", params);
            }
//            if(!dlvDte.equals("")){
//            ArrayList ary=new ArrayList();
//            ary.add(String.valueOf(appSlIdn));
//            int upmsal = db.execUpd("updatemsal", "update msal set rmk='"+dlvDte+"' where idn= ? ", ary);
//            }
            
            req.setAttribute("saleId", String.valueOf(appSlIdn));
            pktNmsSl=pktNmsSl.replaceFirst("\\,", "");
            req.setAttribute("SLMSG", "Packets get Sale : " + pktNmsSl + " with sale Id : " + appSlIdn);
           
            req.setAttribute("performaLink","Y");
//           pktNmsSl=pktNmsSl.replaceFirst("\\,", "");
//           req.setAttribute("performaLink", info.getReqUrl()+"/marketing/performa.do?method=perInv&idn="+appSlIdn+"&byrIdn="+udf.getByrIdn()+"&byrAddIdn="+util.nvl((String)udf.getValue("addr"))+"&bankIdn="+util.nvl((String)udf.getValue("bankIdn"))+
//                                            "&bankAddIdn="+util.nvl((String)udf.getValue("bankAddr"))+"&perInvIdn="+util.nvl(pktNmsSl)
//                                            +"&relIdn="+util.nvl((String)udf.getValue("byrTrm"))+"&echarge="+util.nvl(req.getParameter("echarge"))+"&grpIdn="+util.nvl((String)udf.getValue("grpIdn"))
//                                            +"&courier="+util.nvl((String)udf.getValue("courier")));
       int accessidn=util.updAccessLog(req,res,"Memo sale", "Memo sale idn "+appSlIdn); 
       req.setAttribute("accessidn", String.valueOf(accessidn));
             
            if (udf.getValue("autodlv")!=null){
                int       appDlvIdn = 0;
                if (appDlvIdn == 0) {
                    ArrayList ary = new ArrayList();
                    ary.add(Integer.toString(udf.getNmeIdn()));
                    ary.add(Integer.toString(udf.getRelIdn()));
                    ary.add(udf.getValue("byrTrm"));
                    ary.add(udf.getByrIdn());
                    ary.add(udf.getValue("addr"));
                    ary.add("DLV");
                    ary.add("IS");
                    ary.add(String.valueOf(appSlIdn));
                    ary.add("NN");
                    ary.add(nvl((String) udf.getValue("aadatpaid"), "Y"));
                    ary.add(nvl((String) udf.getValue("brk1paid"), "Y"));
                    ary.add(nvl((String) udf.getValue("brk2paid"), "Y"));
                    ary.add(nvl((String) udf.getValue("brk3paid"), "Y"));
                    ary.add(nvl((String) udf.getValue("brk4paid"), "Y"));
                    ary.add(util.nvl((String) udf.getValue("bankIdn")));
                    ary.add(util.nvl((String) udf.getValue("grpIdn")));
                    ary.add(util.nvl((String) udf.getValue("courier")));
                    ary.add(util.nvl((String)udf.getValue("throubnk")));
                    ArrayList out = new ArrayList();
                    out.add("I");
                    CallableStatement cst = db.execCall(
                        "Gen_HDR ",
                        "dlv_pkg.Gen_Hdr(pByrId => ? , pRlnIdn => ? , pTrmsIdn => ? , pInvNmeIdn => ? , pInvAddrIdn => ? , "
                        + "pTyp => ? , pStt => ?,pFrmId => ?, pFlg => ? ,paadat_paid => ?, pMBRK1_PAID => ?, pMBRK2_PAID => ?, pMBRK3_PAID => ? , "
                        + " pMBRK4_PAID => ?, bnk_idn =>? , co_idn => ? ,p_courier => ? ,pThruBnk => ? , pIdn => ?) ", ary, out);
                    appDlvIdn = cst.getInt(ary.size()+1);
                  cst.close();
                  cst=null;
                }
                if(appDlvIdn!=0){
                    if(!noteperson.equals("")){
                    params = new ArrayList();
                    params=new ArrayList();
                    params.add(noteperson);
                    params.add(globalrmk);
                    params.add(String.valueOf(appDlvIdn));
                    String updateFlg = "update mdlv set NOTE_PERSON=?,rmk=? where idn=?";
                    int cntmj = db.execUpd("update mdlv", updateFlg, params);
                    }else if(!globalrmk.equals("")){
                        params = new ArrayList();
                        params.add(globalrmk);
                        params.add(String.valueOf(appDlvIdn));
                        String updateFlg = "update mdlv set rmk=? where idn=?";
                        int cntmj = db.execUpd("update mdlv", updateFlg, params);
                    }
                 params = new ArrayList();
                 params.add(String.valueOf(appDlvIdn));
                 params.add(String.valueOf(appSlIdn));
                 int SalPkt =db.execCall("sale from dlv"," dlv_pkg.Dlv_Frm_Sale(pIdn => ?,pSalIdn => ?)",params);
                 if(SalPkt >0){
                 req.setAttribute("dlvId", String.valueOf(appDlvIdn));
                 pktNmsSl=pktNmsSl.replaceFirst("\\,", "");
                 req.setAttribute("SLMSG", "Packets get Deliver : " + pktNmsSl + " with delivery Id" + appDlvIdn);
                 req.setAttribute("performaLink","Y");
                 return am.findForward("delivery");
                 }
                }
                
               
                if(chargesLst!=null && chargesLst.size()>0){
                for(int i=0;i<chargesLst.size();i++){
                HashMap dtl=new HashMap();
                dtl=(HashMap)chargesLst.get(i);
                String idn=(String)dtl.get("idn");
                String dsc=(String)dtl.get("dsc");
                String flg=(String)dtl.get("flg");
                String typcharge=(String)dtl.get("typ");
                String fctr=(String)dtl.get("fctr");
                String fun=(String)dtl.get("fun");
                String autoopt=util.nvl((String)dtl.get("autoopt"));
                String autooptional= util.nvl((String)udf.getValue(typcharge+"_AUTOOPT"));  
                String calcdis= util.nvl((String)udf.getValue(typcharge+"_save"),"0").trim();  
                String vlu= util.nvl((String)udf.getValue("vluamt"));
                String exhRte=util.nvl((String)udf.getValue("exhRte"),"1");
                String extrarmk= util.nvl((String)udf.getValue(typcharge+"_rmksave")); 
                if(!vlu.equals("") && !vlu.equals("0")){
                if((!calcdis.equals("") && !calcdis.equals("0")) || (autoopt.equals("OPT"))){
                String insertQ="Insert Into Trns_Charges (trns_idn, ref_typ, ref_idn,charges_idn,amt,amt_usd,charges,CHARGES_PCT,net_usd,rmk,stt,flg)\n" + 
                "VALUES (TRNS_CHARGES_SEQ.nextval, 'DLV', ?,?,?,?,?,?,?,?,'A',?)";   
                ArrayList ary=new ArrayList();
                ary.add(String.valueOf(appDlvIdn));
                ary.add(idn);
                ary.add(vlu);
                float amt_usd=Float.parseFloat(vlu)/Float.parseFloat(exhRte);
                ary.add(String.valueOf(amt_usd));
                ary.add(calcdis);
                ary.add(calcdis);
                float net_usd=amt_usd+Float.parseFloat(calcdis);
                ary.add(String.valueOf(net_usd));
                ary.add(extrarmk);
                ary.add(autooptional);
                int ct = db.execUpd("Insert", insertQ, ary);
                }
                }
                }
                }
            }
        }
            finalizeObject(db, util);
        return loadSale(am, af, req, res);
        }
            }
    }

  
    public ActionForward createXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          util.updAccessLog(req,res,"Memo sale", "Create Excel");
          String mail=util.nvl(req.getParameter("mail"));
          String nmeIdn=util.nvl(req.getParameter("nmeIdn"));
          String byrDtl=util.nvl(req.getParameter("byrDtl"));
          String mdl=util.nvl(req.getParameter("mdl"));
          ExcelUtil xlUtil = new ExcelUtil();
          xlUtil.init(db, util, info);
          OutputStream out = res.getOutputStream();
          String CONTENT_TYPE = "getServletContext()/vnd-excel";
          String fileNm = "MemoSale"+util.getToDteTime()+".xls";
          ArrayList pktList = (ArrayList)session.getAttribute("pktList");
          ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
          HashMap dbinfo = info.getDmbsInfoLst();
          String cnt = (String)dbinfo.get("CNT");
          if(!byrDtl.equals("")){
              ArrayList params=new ArrayList();
              if(!cnt.equals("hk")){
              itemHdr=new ArrayList();
              itemHdr.add("Memo Id");itemHdr.add("Packet Code");
                  if(!mdl.equals("")){
                  ArrayList prpDspBlocked = info.getPageblockList();
                  ArrayList prps = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");
                  for(int j=0; j < prps.size(); j++) {
                  String lprp=(String)prps.get(j);
                  if(prpDspBlocked.contains(lprp)){
                  }else{
                  itemHdr.add(lprp);
                  }
                      }}else{
                          itemHdr.add("SH");itemHdr.add("CRTWT");itemHdr.add("CO");itemHdr.add("PU");
                      }
              itemHdr.add("Quot");itemHdr.add("SalAmt");itemHdr.add("ByrDis");itemHdr.add("RapRte");
                  itemHdr.add("RapVlu");
                  if(!cnt.equals("kj")){
                      itemHdr.add("Dis");itemHdr.add("Prc / Crt");
                  }
              itemHdr.add("BYR");
              itemHdr.add("Date");itemHdr.add("CERT NO.");
              itemHdr.add("AADATCOMM");
              itemHdr.add("TERM");
              itemHdr.add("BROKER");
              itemHdr.add("EMP");itemHdr.add("LAB");itemHdr.add("LOC");
              }else{
              itemHdr=new ArrayList();    
              itemHdr.add("EMP");
              itemHdr.add("BYR");
              itemHdr.add("Date");
              itemHdr.add("DAYS");
              itemHdr.add("appdte");
              itemHdr.add("Packet Code");    
              if(!mdl.equals("")){
              ArrayList prpDspBlocked = info.getPageblockList();    
              ArrayList prps = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");    
              for(int j=0; j < prps.size(); j++) {
              String lprp=(String)prps.get(j);
              if(prpDspBlocked.contains(lprp)){
              }else{
              itemHdr.add(lprp);
              }                  
              }}
              itemHdr.add("RapRte");itemHdr.add("RapVlu");


                  itemHdr.add("ByrDis");itemHdr.add("Quot");        
              itemHdr.add("Memo Id");
              itemHdr.add("REMARK");    
              itemHdr.add("pktstt");    
              itemHdr.add("MEMOTYP");
              itemHdr.add("TERM");    
              itemHdr.add("QTY");
              }
              
              String prvMemo="";
              for(int j=0; j < pktList.size(); j++) {
              HashMap pktPrpMap=(HashMap)pktList.get(j);
              String memo_id=util.nvl((String)pktPrpMap.get("Memo Id"));
              boolean execute=false;
              if(prvMemo.equals(""))
              prvMemo=memo_id;
              if(!prvMemo.equals(memo_id)|| j==0){
                     prvMemo=memo_id;
                     execute=true;
              }
              if(execute){
              String memoQ="select \n" + 
              "a.rmk,a.typ memotyp,byr.get_nm(Nvl(Nvl(a.Mbrk2_Idn,a.Mbrk3_Idn),a.Mbrk4_Idn)) broker,byr.get_trms(a.nmerel_idn) trms\n" + 
              ",byr.get_nm(a.nme_idn) byr, a.aadat_comm comm,byr.get_nm(b.emp_idn) emp,to_char(a.dte, 'dd-Mon-rrrr') dte,trunc(sysdate)-trunc(a.dte) days\n" + 
              "from mjan a,nme_v b\n" + 
              "where \n" + 
              "a.nme_idn=b.nme_idn and\n" + 
              "a.idn=?";
              params = new ArrayList();
              params.add(memo_id);
                    ArrayList  outLst = db.execSqlLst("memo by Shape", memoQ, params);
                    PreparedStatement pst = (PreparedStatement)outLst.get(0);
                    ResultSet  rs = (ResultSet)outLst.get(1);
              while(rs.next()){
                  pktPrpMap.put("BYR", util.nvl(rs.getString("byr")));
                  pktPrpMap.put("Date", util.nvl(rs.getString("dte")));
                  pktPrpMap.put("AADATCOMM", util.nvl(rs.getString("comm")));
                  pktPrpMap.put("TERM", util.nvl(rs.getString("trms")));
                  pktPrpMap.put("BROKER", util.nvl(rs.getString("broker")));
                  pktPrpMap.put("EMP", util.nvl(rs.getString("emp")));
                  pktPrpMap.put("MEMOTYP", util.nvl(rs.getString("memotyp")));
                  pktPrpMap.put("REMARK", util.nvl(rs.getString("rmk")));
                  pktPrpMap.put("DAYS", util.nvl(rs.getString("days")));
              }
                      rs.close();
                      pst.close();
                  }
             }      
          }
          HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
          if(mail.equals("")){
          res.setContentType(CONTENT_TYPE);
          res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
          hwb.write(out);
          out.flush();
          out.close();
              finalizeObject(db, util);
          return null;
          }else{
              SearchQuery query=new SearchQuery();
              query.MailExcelmass(hwb, fileNm, req,res);  
              ArrayList emailids=util.byrAllEmail(nmeIdn);
              req.setAttribute("ByrEmailIds",emailids);
              finalizeObject(db, util);
              return am.findForward("mail");
          }
          }
      }
    
    
    public ActionForward createRuchiXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            return am.findForward(rtnPg);   
        }else{
        util.updAccessLog(req,res,"Memo sale", "Create Excel");
            GenericInterface generic = new GenericImpl();
            HashMap PriMap = (HashMap)session.getAttribute("PriMap");
            String fromDaily = util.nvl((String)req.getAttribute("fromDaily"));
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "Ruchit_"+util.getToDteTime()+".xls";
        ArrayList pktList = (ArrayList)session.getAttribute("pktList");
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("RUCHIT_FMT");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("RUCHIT_FMT");
            allPageDtl.put("RUCHIT_FMT",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            ArrayList keyList = new ArrayList();
            HashMap  hdrMap = new HashMap();
          ArrayList pageList= ((ArrayList)pageDtl.get("RUCHIT_FMT") == null)?new ArrayList():(ArrayList)pageDtl.get("RUCHIT_FMT");
          if(pageList!=null && pageList.size() >0){
              for(int j=0;j<pageList.size();j++){
              HashMap pageDtlMap=(HashMap)pageList.get(j);
              String fld_nme=(String)pageDtlMap.get("fld_nme");
               String fld_ttl=(String)pageDtlMap.get("fld_ttl");
                  keyList.add(fld_nme);
                  hdrMap.put(fld_nme, fld_ttl);
              }
          }
            ArrayList vnmLst = new ArrayList();
            if(fromDaily.equals("")){
            for(int i=0;i<pktList.size();i++){  
                 HashMap pkt = (HashMap)pktList.get(i);
                 String vnm = (String)pkt.get("Packet Code");  
                 vnmLst.add(vnm);
            }
            }else{
                vnmLst =(ArrayList)req.getAttribute("pktList");
            }
            HashMap dataDtl = new HashMap();
            dataDtl.put("mdl", "RUCHIT_FMT");
            dataDtl.put("vnm",vnmLst);
            ArrayList pktDtlLst =  generic.DataColloction(req, res, dataDtl);
            ArrayList newPktLst = new ArrayList();
            for(int i=0;i<pktDtlLst.size();i++){  
                GtPktDtl pkt = (GtPktDtl)pktDtlLst.get(i);
                String stkIdn =String.valueOf(pkt.getPktIdn());
                String rte = (String)PriMap.get(stkIdn);
                pkt.setValue("RTE", rte);
             
                String certNo = util.nvl(pkt.getValue("CERT_NO"));
                if(!certNo.equals("")){
                    pkt.setValue("CERT", "1");
                }
                newPktLst.add(pkt);
            }
            HashMap excelDtl = new HashMap();
            excelDtl.put("PKTLIST", newPktLst);
            excelDtl.put("HDRLIST", hdrMap);
            excelDtl.put("KEYLIST", keyList);
            pktDtlLst = null;
            ExcelUtilObj excelUtil = new ExcelUtilObj();
            excelUtil.init(db, util, info,gtMgr);
            HSSFWorkbook hwb = excelUtil.GenExcelFormat(excelDtl, req);
            res.setContentType(CONTENT_TYPE);
            res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
            hwb.write(out);
            out.flush();
            out.close();
            finalizeObject(db, util);
            return null;
           
        }
    }
    
    public ActionForward createQtradeXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            return am.findForward(rtnPg);   
        }else{
        util.updAccessLog(req,res,"Memo sale", "Create Excel");
            GenericInterface generic = new GenericImpl();
            HashMap PriMap = (HashMap)session.getAttribute("PriMap");
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "Qtrade_"+util.getToDteTime()+".xls";
        ArrayList pktList = (ArrayList)session.getAttribute("pktList");
        String fromDaily = util.nvl((String)req.getAttribute("fromDaily"));
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("QTRADE_FMT");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("QTRADE_FMT");
            allPageDtl.put("QTRADE_FMT",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            ArrayList keyList = new ArrayList();
            HashMap  hdrMap = new HashMap();
          ArrayList pageList= ((ArrayList)pageDtl.get("QTRADE_FMT") == null)?new ArrayList():(ArrayList)pageDtl.get("QTRADE_FMT");
          if(pageList!=null && pageList.size() >0){
              for(int j=0;j<pageList.size();j++){
              HashMap pageDtlMap=(HashMap)pageList.get(j);
              String fld_nme=(String)pageDtlMap.get("fld_nme");
               String fld_ttl=(String)pageDtlMap.get("fld_ttl");
                  String dflt_val=(String)pageDtlMap.get("dflt_val");
                  keyList.add(fld_nme);
                  hdrMap.put(fld_nme, fld_ttl);
                  hdrMap.put(fld_nme+"_DFL", dflt_val);
              }
          }
        ArrayList vnmLst = new ArrayList();
        if(fromDaily.equals("")){
        for(int i=0;i<pktList.size();i++){  
             HashMap pkt = (HashMap)pktList.get(i);
             String vnm = (String)pkt.get("Packet Code");  
             vnmLst.add(vnm);
        }
        }else{
            vnmLst =(ArrayList)req.getAttribute("pktList");
        }
            HashMap dataDtl = new HashMap();
            dataDtl.put("mdl", "QTRADE_FMT");
            dataDtl.put("vnm",vnmLst);
            ArrayList pktDtlLst =  generic.DataColloction(req, res, dataDtl);
           
            HashMap excelDtl = new HashMap();
            excelDtl.put("PKTLIST", pktDtlLst);
            excelDtl.put("HDRLIST", hdrMap);
            excelDtl.put("KEYLIST", keyList);
            pktDtlLst = null;
            ExcelUtilObj excelUtil = new ExcelUtilObj();
            excelUtil.init(db, util, info,gtMgr);
            HSSFWorkbook hwb = excelUtil.GenExcelFormat(excelDtl, req);
            res.setContentType(CONTENT_TYPE);
            res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
            hwb.write(out);
            out.flush();
            out.close();
            finalizeObject(db, util);
            return null;
           
        }
    }
    
    public String nvl(String pVal, String rVal) {
        String val = pVal;

        if (pVal == null) {
            val = rVal;
        }

        return val;
    }
    public void fisalcharges(HttpServletRequest req){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        Connection conn=info.getCon();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    HashMap fisalcharges = (session.getAttribute("fisalcharges") == null)?new HashMap():(HashMap)session.getAttribute("fisalcharges");

    try {
    if(fisalcharges.size() == 0) {
    String ptyp="";
    ArrayList dataLst=new ArrayList();
    ArrayList data=new ArrayList();
    String gtView = "Select  c.typ,a.chr_fr,a.chr_to  From Rule_Dtl A, Mrule B , Charges_Typ C \n" + 
    "Where A.Rule_Idn = B.Rule_Idn And  \n" + 
    "c.typ = a.dsc and b.mdl = 'JFLEX' \n" + 
    "and b.nme_rule = 'FIX_SALE_CHARGES' and c.stt='A' \n" + 
    "and a.til_dte is null order by a.srt_fr , a.dsc , a.chr_fr";
      ArrayList  outLst = db.execSqlLst("gtView", gtView, new ArrayList());
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet  rs = (ResultSet)outLst.get(1);
    while (rs.next()) {
    data=new ArrayList();
    String typ=util.nvl(rs.getString("typ"));
        if(ptyp.equals(""))
        ptyp=typ;
        if(!ptyp.equals(typ)){
            fisalcharges.put(ptyp,dataLst);
            dataLst = new ArrayList();
            ptyp=typ;
        }
        data.add(util.nvl(rs.getString("chr_fr")));
        data.add(util.nvl(rs.getString("chr_to")));
        dataLst.add(data);
    }
        rs.close();
        pst.close();
        if(!ptyp.equals("")){
        fisalcharges.put(ptyp,dataLst);
        }
    session.setAttribute("fisalcharges", fisalcharges);
    }
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }
        finalizeObject(db, util);
    }
    
    
    
    public ActionForward loadauthenticate(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        MemoSaleFrm udf = (MemoSaleFrm) form;
        udf.reset();
        util.updAccessLog(req,res,"Memo sale", "loadauthenticate start");
        ArrayList ary=new ArrayList();
        ArrayList parms = new ArrayList();
        ArrayList pktList = new ArrayList();
        String sqlQ="          Select A.Idn Saleid,a.aud_created_by UserName,Sum(B.Qty) Qty,To_Char(Sum(Trunc(B.Cts,2)),'999990.99') Cts, \n" + 
        "          trunc(sum(Nvl(C.Upr,C.Cmp)*trunc(b.cts,2))/sum(trunc(b.cts, 2)),0) Rte, \n" + 
        "          byr.get_nm(nvl(d.nme_idn,0)) Byr, \n" + 
        "          Trunc(Sum((NVL (b.fnl_sal, b.quot)  / NVL (a.exh_rte, 1))*Trunc(b.Cts,2)),0) fnl_sal, \n" + 
        "          To_Char(Sum(Trunc(B.Cts,2)*(C.Rap_Rte)),'9999999999990.00') Rapvlu, \n" + 
        "          To_Char(Sum(Trunc(B.Cts,2)*((NVL (b.fnl_sal, b.quot)  / NVL (a.exh_rte, 1)))),'999,9999999990.00') Amt, \n" + 
        "          To_Char((Sum(((NVL (b.fnl_sal, b.quot)  / NVL (a.exh_rte, 1)))*Trunc(B.Cts,2))/Sum(Trunc(B.Cts, 2))),'999,9999999990.00') avg_amt, \n" + 
        "          To_Char(Trunc(((Sum(((NVL (b.fnl_sal, b.quot)  / NVL (a.exh_rte, 1)))*Trunc(B.Cts,2))/Sum(Trunc(B.Cts, 2)))/(Sum(C.Rap_Rte*Trunc(B.Cts,2))/Sum(Trunc(B.Cts, 2)))*100) - 100, 2) ,'999999990.00') R_Dis, \n" + 
        "          Byr.Get_Nm(A.Mbrk1_Idn) Mb, \n" + 
        "          nvl(a.brk1_comm,0) mbcomm, \n" + 
        "          Byr.Get_Nm(A.Mbrk2_Idn) Sub, Nvl(A.Brk2_Comm,0) Subcomm ,To_Char(A.Dte,'dd-mm-yyyy') Saldte , To_Char(F.Dte,'dd-mm-yyyy') Appdte , \n" + 
        "          F.Idn Memoid , F.Typ , B.Stt Salstt,a.typ saletyp,mt.term Trms,a.exh_rte,a.fnl_exh_rte,a.rmk \n" + 
        "          from msal a,jansal b, mstk c,mnme d,nmerel e, mjan f,mtrms mt  \n" + 
        "          where a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn and c.idn=b.mstk_idn\n" + 
        "          and a.stt='IS'  and b.stt in('SL','DLV')  and a.typ in('SL','LS')  and a.flg1='PND' and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA'))\n" + 
        "          And A.Nmerel_Idn=E.Nmerel_Idn And A.Memo_Id=F.Idn and e.trms_idn=mt.idn \n" + 
        "          Group By A.Idn,A.Aud_Created_By, byr.get_nm(nvl(d.nme_idn,0)), Byr.Get_Nm(A.Mbrk1_Idn), Nvl(A.Brk1_Comm,0), Byr.Get_Nm(A.Mbrk2_Idn), Nvl(A.Brk2_Comm,0), To_Char(A.Dte,'dd-mm-yyyy'), To_Char(F.Dte,'dd-mm-yyyy'), F.Idn, F.Typ, B.Stt,a.typ, mt.term,a.exh_rte, a.fnl_exh_rte,a.rmk \n" + 
        "          order by a.idn";
          ArrayList  outLst = db.execSqlLst("sqlQ", sqlQ, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
            int i = 0;
            while (rs.next()) {
                HashMap pktPrpMap = new HashMap();
                String saleidn = rs.getString("Saleid");
                pktPrpMap.put("SR", String.valueOf(++i));
                udf.setValue("stt_"+saleidn, "PND");
                pktPrpMap.put("SALEIDN",saleidn);
                pktPrpMap.put("CREATEDBY", util.nvl(rs.getString("UserName")));
                pktPrpMap.put("QTY", util.nvl(rs.getString("Qty")));
                pktPrpMap.put("CTS", util.nvl(rs.getString("Cts")));
                pktPrpMap.put("RTE", util.nvl(rs.getString("Rte")));
                pktPrpMap.put("BYR", util.nvl(rs.getString("Byr")));
                pktPrpMap.put("FNL_SAL", util.nvl(rs.getString("fnl_sal")));
                pktPrpMap.put("RAPVLU", util.nvl(rs.getString("Rapvlu")));
                pktPrpMap.put("VLU", util.nvl(rs.getString("Amt")));
                pktPrpMap.put("AVGAMT", util.nvl(rs.getString("avg_amt")));
                pktPrpMap.put("DIS", util.nvl(rs.getString("R_Dis")));
                pktPrpMap.put("MB", util.nvl(rs.getString("Mb")));
                pktPrpMap.put("MBCOMM", util.nvl(rs.getString("mbcomm")));
                pktPrpMap.put("SUB", util.nvl(rs.getString("Sub")));
                pktPrpMap.put("SUBCOMM", util.nvl(rs.getString("Subcomm")));
                pktPrpMap.put("SALEDTE", util.nvl(rs.getString("Saldte")));
                pktPrpMap.put("APPROVEDTE", util.nvl(rs.getString("Appdte")));
                pktPrpMap.put("MEMOIDN", util.nvl(rs.getString("Memoid")));
                pktPrpMap.put("TYP", util.nvl(rs.getString("Typ")));
                pktPrpMap.put("SALETYP", util.nvl(rs.getString("saletyp")));
                pktPrpMap.put("SALESTT", util.nvl(rs.getString("Salstt")));
                pktPrpMap.put("TRMS", util.nvl(rs.getString("Trms")));
                pktPrpMap.put("EXH_RTE", util.nvl(rs.getString("exh_rte")));
                pktPrpMap.put("FNL_XRT", util.nvl(rs.getString("fnl_exh_rte")));
                pktPrpMap.put("RMK", util.nvl(rs.getString("rmk")));
                udf.setValue("stt_" + saleidn,"PND");
                
                String getPktcharges = "                Select b.ref_idn,\n" + 
                "                Max (Decode (A.Typ, 'ADDL_DIS', B.Charges, Null)) ADDL_DIS\n" + 
                "                From Charges_Typ A,Trns_Charges B\n" + 
                "                Where A.Idn=B.Charges_Idn And A.Stt='A' And Nvl(B.Flg,'NA') Not In('Y') And Ref_Idn In (?) And App_Typ='PP' and ref_typ='SAL' \n" + 
                "                Group By B.Ref_Idn";
                parms = new ArrayList();
                parms.add(saleidn);
              ArrayList  outLst1 = db.execSqlLst("getPktcharges", getPktcharges, parms);
              PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
              ResultSet  rs1 = (ResultSet)outLst1.get(1);
                while (rs1.next()) {
                    pktPrpMap.put("ADDL_DIS", util.nvl(rs1.getString("ADDL_DIS")));
                }
                rs1.close();
                pst1.close();
                pktList.add(pktPrpMap);
            }
            rs.close();
            pst.close();
            session.setAttribute("pndsalepktList", pktList);
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("SALE_AUTHENTICATION");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("SALE_AUTHENTICATION");
            allPageDtl.put("SALE_AUTHENTICATION",pageDtl);
            }
            info.setPageDetails(allPageDtl);
        util.updAccessLog(req,res,"Memo sale", "loadauthenticate end");
            finalizeObject(db, util);
        return am.findForward("authenticate");
        }
    }
    
    public ActionForward saveauthenticate(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        MemoSaleFrm udf = (MemoSaleFrm) form;
        util.updAccessLog(req,res,"Memo sale", "saveauthenticate start");
        ArrayList pndsalepktList= (session.getAttribute("pndsalepktList") == null)?new ArrayList():(ArrayList)session.getAttribute("pndsalepktList");
            ArrayList params = null;
            ArrayList ary = null;
            ArrayList pkts      = new ArrayList();
            String    pktNmsSl  = "";
            String    pktNmsRT  = "";
            String    pktNmsCAN = "";
            boolean isRtn=false;
        int pndsalepktListsz=pndsalepktList.size();
            String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? , pLab=> ?, pPrpTyp => ?, pgrp => ? )";
        if(pndsalepktListsz>0){
            for(int i=0;i<pndsalepktListsz;i++){
                isRtn=false;
                HashMap pktPrpMap = new HashMap();
                pktPrpMap=(HashMap)pndsalepktList.get(i);
                String saleIdn = util.nvl((String)pktPrpMap.get("SALEIDN"));
                String lStt    = util.nvl((String) udf.getValue("stt_" + saleIdn));
                String rmk    = util.nvl((String) udf.getValue("rmk_" + saleIdn));
                if (lStt.equals("AP")) {
                    ary=new ArrayList();
                    ary.add(saleIdn);
                    int upMsal = db.execUpd("updateJAN", "update msal set flg1='CNF',mail_stt='P' where idn= ? ", ary);
                    
                    String sqlQ="     	Select A.Byr,A.Emp,A.Fnl_Usd,a.fnl_bse,to_char(A.Dte,'DD-MON-YYYY') dte,C.Cert_Lab,c.idn,b.typ  From Sal_Pkt_Dtl_V A, Msal B,Mstk C \n" + 
                    "    Where A.Idn = B.Idn And A.Mstk_Idn=C.Idn And A.Stt=Decode(B.Typ,'SL','SL','LS','SL','DLV') And B.Typ In ('SL','LS') And B.Stt='IS' And C.Stt=Decode(B.Typ,'SL','MKSL','MKSD') And A.Idn=?";
                    ary=new ArrayList();
                    ary.add(saleIdn);
                    ResultSet rs = db.execSql("sqlQ", sqlQ,ary);

                    while (rs.next()) {
                        params=new ArrayList();
                        params.add(util.nvl(rs.getString("idn")));
                        params.add("SAL_BYR");
                        params.add(util.nvl(rs.getString("Byr")));
                        params.add(util.nvl(rs.getString("Cert_Lab")));
                        params.add("T");
                        params.add("1");
                        int ct = db.execCall("stockUpd",stockUpd, params);
                        
                        params=new ArrayList();
                        params.add(util.nvl(rs.getString("idn")));
                        params.add("SAL_EMP");
                        params.add(util.nvl(rs.getString("Emp")));
                        params.add(util.nvl(rs.getString("Cert_Lab")));
                        params.add("T");
                        params.add("1");
                        ct = db.execCall("stockUpd",stockUpd, params);
                        
                        params=new ArrayList();
                        params.add(util.nvl(rs.getString("idn")));
                        params.add("SAL_RTE");
                        params.add(util.nvl(rs.getString("Fnl_Usd")));
                        params.add(util.nvl(rs.getString("Cert_Lab")));
                        params.add("N");
                        params.add("1");
                        ct = db.execCall("stockUpd",stockUpd, params);
                        
                        params=new ArrayList();
                        params.add(util.nvl(rs.getString("idn")));
                        params.add("SAL_FNLBSE");
                        params.add(util.nvl(rs.getString("fnl_bse")));
                        params.add(util.nvl(rs.getString("Cert_Lab")));
                        params.add("N");
                        params.add("1");
                        ct = db.execCall("stockUpd",stockUpd, params);
                        
                        params=new ArrayList();
                        params.add(util.nvl(rs.getString("idn")));
                        params.add("SAL_DTE");
                        params.add(util.nvl(rs.getString("Dte")));
                        params.add(util.nvl(rs.getString("Cert_Lab")));
                        params.add("D");
                        params.add("1");
                        ct = db.execCall("stockUpd",stockUpd, params);
                        
//                        params=new ArrayList();
//                        params.add(util.nvl(rs.getString("idn")));
//                        params.add("LOC");
//                        params.add("NA");
//                        params.add(util.nvl(rs.getString("Cert_Lab")));
//                        params.add("C");
//                        params.add("1");
//                        ct = db.execCall("stockUpd",stockUpd, params);
                        
                        params=new ArrayList();
                        params.add(util.nvl(rs.getString("idn")));
                        params.add("RFIDCD");
                        params.add("");
                        params.add(util.nvl(rs.getString("Cert_Lab")));
                        params.add("T");
                        params.add("1");
                        ct = db.execCall("stockUpd",stockUpd, params);
                        
                        params=new ArrayList();
                        params.add(util.nvl(rs.getString("idn")));
                        params.add("SL_PKT_TYP");
                        params.add(util.nvl(rs.getString("typ")));
                        params.add(util.nvl(rs.getString("Cert_Lab")));
                        params.add("C");
                        params.add("1");
                        ct = db.execCall("stockUpd",stockUpd, params);
                    }
                    rs.close();
                    pktNmsSl = pktNmsSl + "," +saleIdn;
                }   else if (lStt.equals("RT")) {
                    ary=new ArrayList();
                    ary.add(saleIdn);
                    String sqlQ="select c.vnm,c.idn\n" + 
                    "from\n" + 
                    "msal a,jansal b,mstk c \n" + 
                    "where \n" + 
                    "a.idn=b.idn and a.idn=? and \n" + 
                    "b.mstk_idn=c.idn and\n" + 
                    "a.stt='IS'  and b.stt = 'SL' and a.typ in('SL','LS')  and a.flg1='PND' and\n" + 
                    "c.stt in('MKSL','MKSD','BRC_MKSD')";
                  ArrayList  outLst = db.execSqlLst("packets", sqlQ, ary);
                  PreparedStatement pst = (PreparedStatement)outLst.get(0);
                  ResultSet  rs = (ResultSet)outLst.get(1);
                  while (rs.next()) {
                        String stkIdn=util.nvl(rs.getString("idn"));
                        String vnm=util.nvl(rs.getString("vnm"));
                        params = new ArrayList();
                        params.add(saleIdn);
                        params.add(stkIdn);
                        params.add(rmk);
                        int ct = db.execCall(" App Pkts", " DLV_PKG.rtn_pkt(pIdn => ? , pStkIdn => ?,pRem => ?)", params);
                        pktNmsRT = pktNmsRT + "," +vnm;
                   }
                   rs.close();
                   pst.close();
                   isRtn = true;
                } else if (lStt.equals("CAN")) {
                    ary=new ArrayList();
                    ary.add(saleIdn);
                    String sqlQ="select c.vnm,c.idn\n" + 
                    "from\n" + 
                    "msal a,jansal b,mstk c \n" + 
                    "where \n" + 
                    "a.idn=b.idn and a.idn=? and \n" + 
                    "b.mstk_idn=c.idn and\n" + 
                    "a.stt='IS'  and b.stt = 'SL'  and a.typ in('SL','LS')  and a.flg1='PND' and\n" + 
                    "c.stt in('MKSL','MKSD','BRC_MKSD')";
                  ArrayList  outLst = db.execSqlLst("packets", sqlQ, ary);
                  PreparedStatement pst = (PreparedStatement)outLst.get(0);
                  ResultSet  rs = (ResultSet)outLst.get(1);
                    while (rs.next()) {
                    String stkIdn=util.nvl(rs.getString("idn"));
                    String vnm=util.nvl(rs.getString("vnm"));
                    params = new ArrayList();
                    params.add(saleIdn);
                    params.add(stkIdn);
                    int ct = db.execCall(" Cancel Pkts ", "sl_pkg.Cancel_Pkt( pIdn =>?, pStkIdn=> ?)", params);
                    pktNmsCAN = pktNmsCAN + "," +vnm;
                    }
                    rs.close();
                    pst.close();
                    isRtn = true;
                }
                if(isRtn){
                params = new ArrayList();
                params.add(saleIdn);
                int upQuery = db.execCall("calQuot", "SL_PKG.UPD_QTY(pIdn => ? , pTyp => 'DLV')", params);
                }
            }
                
        }
        if(!pktNmsRT.equals("")){
        pktNmsRT=pktNmsRT.replaceFirst("\\,", "");
        req.setAttribute("RTMSG", "Packets get return: " + pktNmsRT);
        }
        if(!pktNmsCAN.equals("")){
        pktNmsCAN=pktNmsCAN.replaceFirst("\\,", "");
        req.setAttribute("CANMSG", "Packets cancelled " + pktNmsCAN);
        }
            
        if(!pktNmsSl.equals("")){
        pktNmsSl=pktNmsSl.replaceFirst("\\,", "");
        req.setAttribute("CONFIRMMSG", "Sale Confirmed " + pktNmsSl);
        }
        util.updAccessLog(req,res,"Memo sale", "saveauthenticate end");
            finalizeObject(db, util);
        return loadauthenticate(am, form, req, res);
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
            util.updAccessLog(req,res,"Daily Sale Report", "pktDtl start");
        MemoSaleFrm udf = (MemoSaleFrm) form;
        String salId = util.nvl(req.getParameter("saleId"));
        ArrayList pktDtlList = new ArrayList();
        ArrayList itemHdr = new ArrayList();
        itemHdr.add("SRNO");
        itemHdr.add("SALEID");
        itemHdr.add("VNM");
        itemHdr.add("SALEPERSON");
        itemHdr.add("BYR");
        itemHdr.add("COUNTRY");
        ArrayList ary = new ArrayList();
        ArrayList prpDspBlocked = info.getPageblockList();
        int sr=1;
        if(!salId.equals("")){
            String pktDtlSql =
                          " select a.idn saleid,c.idn,to_char(b.cts,'99999999990.99') cts,DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), c.vnm) vnm,nvl(c.upr,c.cmp) rte,c.rap_rte,byr.get_nm(nvl(d.emp_idn,0)) sale_name,byr.get_nm(nvl(d.nme_idn,0)) byr,nvl(b.fnl_sal, b.quot) memoQuot,to_char(trunc(b.cts,2) * c.rap_rte, '99999999990.00') rapvlu, \n" + 
                          "                           to_char(trunc(b.cts,2) * TRUNC (NVL (b.fnl_sal, b.quot) / NVL (a.exh_rte, 1), 2), '9999999990.00') amt,byr.get_country(nvl(a.nme_idn,0)) country,to_char((((TRUNC (NVL (b.fnl_sal, b.quot) / NVL (a.exh_rte, 1), 2))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') s_dis,decode(c.rap_rte,1,null,to_char((((nvl(c.upr,c.cmp))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00')) r_dis  \n" + 
                          "                           from msal a,jansal b, mstk c,mnme d \n" + 
                          "                           where a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn and c.idn=b.mstk_idn  \n" + 
                          "                           And B.Stt Not In ('RT','CS') And B.Typ In ('SL','DLV') And C.Stt In('MKSL', 'MKSD','BRC_MKSD') And a.idn= ? \n" + 
                          "                           order by c.sk1 ";
        ary.add(salId);
          ArrayList  outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            HashMap pktDtl = new HashMap();
            pktDtl.put("SRNO", String.valueOf(sr++));
            pktDtl.put("SALEID", util.nvl(rs.getString("saleid")));
            pktDtl.put("VNM", util.nvl(rs.getString("vnm")));
            pktDtl.put("SALEPERSON", util.nvl(rs.getString("sale_name")));
            pktDtl.put("BYR", util.nvl(rs.getString("byr")));
            pktDtl.put("COUNTRY", util.nvl(rs.getString("country")));
            pktDtl.put("RTE", util.nvl(rs.getString("rte")));
            pktDtl.put("SALE RTE", util.nvl(rs.getString("memoQuot")));
            pktDtl.put("SALE DIS", util.nvl(rs.getString("s_dis")));
            pktDtl.put("AMOUNT", util.nvl(rs.getString("amt")));
            pktDtl.put("RAP_RTE", util.nvl(rs.getString("rap_rte")));
            String pktIdn = rs.getString("idn");
            String getPktPrp =
                " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
                + " from stk_dtl a, mprp b, rep_prp c "
                + " where a.mprp = b.prp and b.prp = c.prp and c.mdl = 'DAILY_VW' and a.mstk_idn = ? and a.grp=1  and c.flg <> 'N' "
                + " order by c.rnk, c.srt ";

            ary = new ArrayList();
            ary.add(pktIdn);
          ArrayList  outLst1 = db.execSqlLst(" Pkt Prp", getPktPrp, ary);
          PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
          ResultSet  rs1 = (ResultSet)outLst1.get(1);
             while (rs1.next()) {
                String lPrp = util.nvl(rs1.getString("mprp"));
                String lVal = util.nvl(rs1.getString("val"));
                pktDtl.put(lPrp, lVal);
                if(prpDspBlocked.contains(lPrp)){
                }else if(!itemHdr.contains(lPrp)){
                itemHdr.add(lPrp);
                    if(lPrp.equals("RTE")){
                      itemHdr.add("SALE RTE");
                        itemHdr.add("SALE DIS");
                      itemHdr.add("AMOUNT");
                    }
                    if(lPrp.equals("RAP_RTE")){
                           itemHdr.add("RAPVLU");
                    }
                }
            }
            rs1.close();
            pst1.close();
            pktDtl.put("RTE",util.nvl(rs.getString("rte")));
            pktDtl.put("RAP_DIS",util.nvl(rs.getString("r_dis")));
            pktDtl.put("CRTWT",util.nvl(rs.getString("cts")));
            pktDtl.put("RAP_RTE",util.nvl(rs.getString("rap_rte")));
            pktDtl.put("RAPVLU",util.nvl(rs.getString("rapvlu")));
            pktDtlList.add(pktDtl);
        }
            rs.close();
            pst.close();
            
        }
       session.setAttribute("pktList", pktDtlList);
       session.setAttribute("itemHdr",itemHdr);
            util.updAccessLog(req,res,"Daily Sale Report", "pktDtl end");
            finalizeObject(db, util);
        return am.findForward("pktDtl");
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
                util.updAccessLog(req,res,"Memo sale", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Memo sale", "init");
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


//~ Formatted by Jindent --- http://www.jindent.com
