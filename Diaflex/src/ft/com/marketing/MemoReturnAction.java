package ft.com.marketing;

//~--- non-JDK imports --------------------------------------------------------

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;
import ft.com.MailAction;
//import ft.com.SyncOnRap;
import ft.com.box.BoxReturnForm;
import ft.com.contact.NmeFrm;
import ft.com.dao.ByrDao;
import ft.com.dao.MAddr;
import ft.com.dao.MNme;
import ft.com.dao.PktDtl;
import ft.com.dao.TrmsDao;
import ft.com.dao.UIForms;

import java.io.IOException;

import java.io.OutputStream;

import java.sql.SQLException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

//~--- JDK imports ------------------------------------------------------------

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.ArrayList;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class MemoReturnAction extends DispatchAction {

    public MemoReturnAction() {
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
                util.updAccessLog(req,res,"Memo Return", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Memo Return", "init");
            }
            }
            return rtnPg;
            }
    public ActionForward loadRtn(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            MemoReturnFrm udf = (MemoReturnFrm) af;
            util.updAccessLog(req,res,"Memo Return", "loadRtn");
            ArrayList byrList = new ArrayList();
            SearchQuery query = new SearchQuery();
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
            String strTyp="('NR','SMX')";
            String typ = util.nvl(req.getParameter("TYP"));
                if(!typ.equals(""))
                    strTyp="('"+typ+"')" ;
               
                    
            String    getByr  =
                "With JAN_IS_PNDG_V as (\n" + 
                "select distinct c.nme_idn\n" + 
                "from mstk a, jandtl b , mjan c \n" + 
                "Where a.idn = b.mstk_idn and a.pkt_ty in "+strTyp+" and c.typ in ('I','N','O','E','S','H', 'WH','WA','WM','ZP','LB','BID','K','R') and b.stt='IS' And b.idn=c.idn and nvl(a.prt2,'NA')=decode(nvl(a.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(a.prt2,'NA')) \n" + 
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
            udf.setByrLstFetch(byrList);
            udf.setByrLst(byrList);
            ArrayList byrCbList = query.getBuyerCabin(req, res);
            if(byrCbList !=null && byrCbList.size()>0){
                udf.setValue("ByrCbList", byrCbList);
            }
            udf.setValue("PKTTYP", typ);
            util.updAccessLog(req,res,"Memo Return", "End");
                req.setAttribute("TYP",typ);
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
        util.updAccessLog(req,res,"Memo Return ", "Memo Return load pkts");
        info.setSlPktList(new ArrayList());
        MemoReturnFrm udf = (MemoReturnFrm) af;
        
        SearchQuery srchQuery = new SearchQuery();
        int       memoIdn = 0;
        String    view    = "NORMAL";
        ArrayList pkts    = new ArrayList();
        String    pnd     = req.getParameter("pnd");
        ArrayList ary = new ArrayList();
        String pktLst="";
        String blockpopup_yn="N";
        util.noteperson();
        String    memoSrchTyp = util.nvl((String) udf.getValue("memoSrch"));
            ArrayList pageList=new ArrayList();
            HashMap pageDtlMap=new HashMap();
           
            ArrayList vwprpLst = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");
            String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
            if (memoSrchTyp.equals("ByrSrch")) {
                Enumeration reqNme = req.getParameterNames();

                while (reqNme.hasMoreElements()) {
                    String paramNm = (String) reqNme.nextElement();

                    if (paramNm.indexOf("cb_memo") > -1) {
                        String val = req.getParameter(paramNm);

                        if (memoIdn==0) {
                            memoIdn = Integer.parseInt(val);
                        }
                    }
                }
            } else {
                memoIdn =(udf.getMemoIdn());
                pktLst=util.nvl((String)udf.getValue("vnmLst"));
            }
        if (pnd != null) {
            memoIdn = Integer.parseInt(req.getParameter("memoId"));
            udf.setMemoIdn(memoIdn);
        } 

        String app = (String)req.getAttribute("APP");
        if(app!=null){
        memoIdn = Integer.parseInt((String)req.getAttribute("memoId"));
        udf.setMemoIdn(memoIdn);
        } 
        ArrayList params = null;
        String memoStr = "";
           
        
        //+
        if (pktLst.length() > 3) {
           
            pktLst = util.getVnm(pktLst);
            String vnmLst = pktLst.replaceAll("'", "");
            String[] str = null;
            if(vnmLst.indexOf(",") > 0)
                str = vnmLst.split(",");
            else{
                str = new String[1];
                str[0] = vnmLst ;
            }
            
            ArrayList pktsRecd = new ArrayList();
            for(int p=0 ; p < str.length; p++) {
                pktsRecd.add(str[p]);    
            }
            if(memoIdn==0){
                params = new ArrayList();
                //params.add(pktLst);
                params.add(pktsRecd.get(0));
                params.add("IS");
                ArrayList out = new ArrayList();
                out.add("I");
                CallableStatement cst = db.execCall("findMemo","memo_pkg.find_ref_idn(pVnm => ?, pTyp =>? , pIdn => ?)", params ,out);
                memoIdn = cst.getInt(3);
                udf.setMemoIdn(memoIdn);
              cst.close();
              cst=null;
            }
            if((util.nvl(udf.getSubmit()).equalsIgnoreCase("View All")) && (pktLst.indexOf(",") == -1)){
                
                req.setAttribute("lMemoIdn", String.valueOf(memoIdn));
                req.setAttribute("viewAll", "yes");
              
            } else if(pktLst.indexOf(",") > -1) {
           
            memoStr = " and  ( b.vnm in ("+pktLst+") or b.tfl3 in ("+pktLst+") )  ";
            req.setAttribute("lMemoIdn", String.valueOf(memoIdn));
            req.setAttribute("viewAll", "no");
            }else{
               
                memoStr = " and ( b.vnm in ("+pktLst+") or b.tfl3 in ("+pktLst+"))  ";
               
            }
               
        }
            String summaryStr="";
          if(!pktLst.equals(""))
            summaryStr = summaryStr+" and  ( m.vnm in ("+pktLst+") or m.tfl3 in ("+pktLst+")) ";
            
        String getAvgDis="WITH JPndg as (\n" + 
        "select jd.idn, jd.stt , m.pkt_ty \n" + 
        " , sum(decode(m.pkt_ty, 'MIX', jd.qty, 1)) qty \n" + 
        " , sum(decode(m.pkt_ty, 'NR', trunc(jd.cts, 2), jd.cts)) cts\n" + 
        " , sum(decode(m.pkt_ty, 'NR', 0, jd.cts_rtn)) cts_rtn\n" + 
        " , sum(decode(m.pkt_ty, 'NR', 0, jd.cts_sal)) cts_sal\n" + 
        " , sum(decode(m.pkt_ty, 'NR', trunc(jd.cts, 2), jd.cts)*quot) quotVlu\n" + 
        " , sum(decode(m.pkt_ty, 'NR', trunc(jd.cts, 2), jd.cts)*nvl(fnl_sal, quot)) fnlVlu\n" + 
        " , sum(decode(m.pkt_ty, 'NR', trunc(jd.cts, 2), jd.cts)*decode(m.rap_rte, 1, 0, m.rap_rte)) rapVlu\n" + 
        "from mstk m, jandtl jd\n" + 
        "where m.idn = jd.mstk_idn and jd.idn = ? and jd.stt = 'IS' and nvl(m.prt2,'NA')=decode(nvl(m.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(m.prt2,'NA')) " +summaryStr+
        "group by jd.idn, jd.stt ,m.pkt_ty \n" + 
        ") \n" + 
        "select j.exh_rte, p.stt, p.qty, p.cts, p.cts_rtn, p.cts_sal , p.pkt_ty \n" + 
        ", p.quotVlu\n" + 
        ", trunc(decode(rapVlu, 0, p.fnlVlu/p.cts, p.fnlVlu/trunc(p.cts, 2)),2) avgRte, trunc(decode(rapVlu, 0, p.quotVlu/p.cts, p.quotVlu/trunc(p.cts, 2)),2) avgRtequot\n" + 
        ", trunc(decode(rapVlu, 0, p.fnlVlu/p.cts*trunc(p.cts, 2), p.fnlVlu),2) fnlVlu\n" + 
        ", trunc(decode(rapVlu, 0, p.fnlVlu/p.cts*trunc(p.cts, 2)/nvl(j.exh_rte, 1), p.fnlVlu/nvl(j.exh_rte, 1)), 2) usdVlu\n" + 
        ", rapVlu\n" + 
        ", decode(rapVlu, 0, null, trunc((((fnlVlu/nvl(j.exh_rte, 1))*100)/rapVlu) - 100, 2)) avgRapDis,decode(rapVlu, 0, null, trunc((((quotVlu)*100)/rapVlu) - 100, 2)) avgRapDisquot\n" + 
        "from jPndg p, mjan j\n" + 
        "where j.idn = p.idn\n";
        
      
        ary = new ArrayList();
        ary.add(String.valueOf(memoIdn));
          ArrayList  outLst = db.execSqlLst(" Memo Info", getAvgDis , ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
        if(rs.next()){
            udf.setAvgDis(rs.getString("avgRapDis"));
            udf.setAvgPrc(rs.getString("avgRte"));
            udf.setAvgDisQuot(rs.getString("avgRapDisquot"));
            udf.setAvgPrcQuot(rs.getString("avgRtequot"));
            udf.setQty(rs.getString("qty"));
            udf.setCts(rs.getString("cts"));
            udf.setVlu(rs.getString("fnlVlu"));
            udf.setVluQuot(rs.getString("quotVlu"));
            req.setAttribute("PKTTY", rs.getString("pkt_ty"));
        }
        rs.close();
          pst.close();
            String cnt = util.nvl((String)info.getDmbsInfoLst().get("CNT"));
        String getMemoInfo =
            " select a.nme_idn, a.nmerel_idn,'N' memo_lmt, byr.get_nm(a.nme_idn) byr,byr.get_nm(c.emp_idn) SalEmp,GET_TRM_DSC(a.nmerel_idn) shortTrms, a.typ, a.qty, a.cts, a.vlu, a.exh_rte , to_char(a.dte, 'dd-Mon-rrrr HH24:mi:ss') dte,b.dtls,a.rmk,a.note_person,a.byr_cabin "
            + " from mjan a,nme_rel_v b,mnme c where a.nme_idn=b.nme_idn and a.nme_idn=c.nme_idn and a.nmerel_idn=b.nmerel_idn and a.idn = ?  and a.typ in ('I','N','O','E','H','S','K', 'WH','WA','WM','ZP','LB','RGH','BID','R') ";
            if(cnt.equalsIgnoreCase("KJ")){
                getMemoInfo =
                    " select a.nme_idn, a.nmerel_idn,GET_CREDIT_LMT('Z',a.nme_idn,a.nmerel_idn) memo_lmt, byr.get_nm(a.nme_idn) byr,byr.get_nm(c.emp_idn) SalEmp,GET_TRM_DSC(a.nmerel_idn) shortTrms, a.typ, a.qty, a.cts, a.vlu, a.exh_rte , to_char(a.dte, 'dd-Mon-rrrr HH24:mi:ss') dte,b.dtls,a.rmk,a.note_person,a.byr_cabin "
                    + " from mjan a,nme_rel_v b,mnme c where a.nme_idn=b.nme_idn and a.nme_idn=c.nme_idn and a.nmerel_idn=b.nmerel_idn and a.idn = ?  and a.typ in ('I','N','O','K', 'E','S', 'WH','WA','WM','ZP','LB','RGH','BID','R','H') ";
                    
            }
        ary = new ArrayList();
        ary.add(String.valueOf(memoIdn));

            
         outLst = db.execSqlLst(" Memo Info", getMemoInfo, ary);
         pst = (PreparedStatement)outLst.get(0);
         rs = (ResultSet)outLst.get(1);

        if (rs.next()) {
            udf.setNmeIdn(rs.getInt("nme_idn"));
            udf.setRelIdn(rs.getInt("nmerel_idn"));
            udf.setByr(rs.getString("byr"));
            HashMap buyerDtlMap=util.getBuyerDtl(String.valueOf(rs.getInt("nme_idn")));
            udf.setValue("email",util.nvl((String)buyerDtlMap.get("EML")));
            udf.setValue("mobile",util.nvl((String)buyerDtlMap.get("MBL")));
            udf.setValue("office",util.nvl((String)buyerDtlMap.get("OFC")));
            String limit = util.nvl(rs.getString("memo_lmt"),"N");
            if(limit.equals("-1"))
                limit="N";
            udf.setValue("crtLimit",limit);
            udf.setValue("trmsdtls",rs.getString("dtls"));
            udf.setValue("shortTrms",rs.getString("shortTrms"));
            udf.setTyp(rs.getString("typ"));
            udf.setDte(rs.getString("dte"));
            udf.setValue("exh_rte", rs.getString("exh_rte"));
            String exhRte = util.nvl(rs.getString("exh_rte"),"1");
            double exh_rte = Double.parseDouble(exhRte);
            udf.setOldMemoIdn(memoIdn);
            udf.setValue("memoIdn", String.valueOf(memoIdn));
            udf.setValue("noteperson", util.nvl(rs.getString("note_person")));
            udf.setValue("cabin", util.nvl(rs.getString("byr_cabin")));
            udf.setValue("rmk", util.nvl(rs.getString("rmk")));
            udf.setValue("SALEMP", util.nvl(rs.getString("SalEmp")));
            
            String ttlMemoVal = srchQuery.TotalMemoVal(req,udf.getNmeIdn(),udf.getRelIdn(),cnt,"AP");
            req.setAttribute("ttlMemoVal", ttlMemoVal);
         
            if (view.equalsIgnoreCase("Normal")) {
                String mprpStr = "";
                String mdlPrpsQ = " Select Decode ( Rank() Over ( Order By Srt),1,' ',',')||''''||Prp||''''||' '||\n" + 
                "Upper(replace(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1'),'CERT NO.','CERT')) \n" + 
                "str From Rep_Prp Rp Where rp.MDL = ? order by srt " ;
                ary = new ArrayList();
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
                    " ( Select b.sk1,b.cert_no certno, to_char(a.dte, 'dd-Mon-rrrr') dte,a.mstk_idn,nvl(nvl(nvl(st.txt,st.dte),st.num),st.prt1) atr , st.mprp, DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), b.vnm) vnm , b.tfl3 , nvl(fnl_sal, quot) memoQuot, quot, fnl_sal"
                    + ", b.cts, nvl(b.upr, b.cmp) rate, b.rap_rte raprte,to_char(trunc(a.cts,2) * b.rap_rte, '99999999990.00') rapVlu,to_char(trunc(a.cts,2) * nvl(fnl_sal, quot), '99999999990.00') amt ,trunc((((nvl(a.fnl_sal,a.quot)/"+exh_rte+")-nvl(a.rte,0))/greatest(a.rte,1))*100, 2) plper, a.stt "
                    + " , to_char(decode(b.rap_rte, 1, '', trunc(((nvl(b.upr, b.cmp)/greatest(b.rap_rte,1))*100)-100,2)),9990.99) dis,  to_char(decode(rap_rte, 1, '', trunc((((nvl(fnl_sal, quot)/"+exh_rte+")/greatest(b.rap_rte,1))*100)-100,2)),9990.99) mDis "
                    + " from stk_dtl st,jandtl a, mstk b where st.mstk_idn=b.idn and a.mstk_idn = b.idn and a.stt = 'IS'  and b.pkt_ty in ('NR','SMX','RGH')  ";
               
              
              
                 getPktData = getPktData+" "+memoStr+" and a.idn = ? and nvl(b.prt2,'NA')=decode(nvl(b.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(b.prt2,'NA'))\n"+
                    "and exists (select 1 from rep_prp rp where rp.MDL = 'MEMO_RTRN' and st.mprp = rp.prp)  And st.Grp = 1) " +
                    " Select * from stkDtl PIVOT " +
                    " ( max(atr) " +
                    " for mprp in ( "+mprpStr+" ) ) order by 1 " ;
                  params = new ArrayList();
                  params.add(String.valueOf(memoIdn));
                
                

                // " and decode(typ, 'I','IS', 'E', 'IS', 'WH', 'IS', 'AP') = stt ";
              

                 
               outLst1 = db.execSqlLst(" memo pkts", getPktData, params);
               pst1 = (PreparedStatement)outLst1.get(0);
                rs1 = (ResultSet)outLst1.get(1);

                while (rs1.next()) {
                    PktDtl pkt    = new PktDtl();
                    long   pktIdn = rs1.getLong("mstk_idn");

                    pkt.setPktIdn(pktIdn);
                    pkt.setRapRte(util.nvl(rs1.getString("raprte")));
                    pkt.setCts(util.nvl(rs1.getString("cts")));
                    pkt.setRte(util.nvl(rs1.getString("rate")));
                    pkt.setMemoQuot(util.nvl(rs1.getString("memoQuot")));
                    pkt.setByrRte(util.nvl(rs1.getString("quot")));
                    pkt.setFnlRte(util.nvl(rs1.getString("fnl_sal")));
                    pkt.setDis(rs1.getString("dis"));
                    pkt.setByrDis(rs1.getString("mDis"));
                    pkt.setVnm(util.nvl(rs1.getString("vnm")));
                    pkt.setValue("dte",util.nvl(rs1.getString("dte")));
                    pkt.setValue("memoIdn", String.valueOf(memoIdn));
                    pkt.setValue("rapVlu", util.nvl(rs1.getString("rapVlu")));
                    pkt.setValue("plper", util.nvl(rs1.getString("plper")));
                    pkt.setValue("AMT", util.nvl(rs1.getString("amt")));
                    String lStt = rs1.getString("stt");
                   
                    pkt.setStt(lStt);
                    udf.setValue("stt_" + pktIdn, lStt);
                    String vnm = util.nvl(rs1.getString("vnm"));
                    String tfl3 = util.nvl(rs1.getString("tfl3"));
                    if(!pktLst.equals("")){
                        if(pktLst.indexOf(tfl3)!=-1 && !tfl3.equals("")){
                            if(pktLst.indexOf("'")==-1)
                                pktLst =  pktLst.replaceAll(tfl3,"");
                            else
                                pktLst =  pktLst.replaceAll("'"+tfl3+"'", "");
                        } else if(pktLst.indexOf(vnm)!=-1 && !vnm.equals("")){
                            if(pktLst.indexOf("'")==-1)
                                pktLst =  pktLst.replaceAll(vnm,"");
                            else
                                pktLst =  pktLst.replaceAll("'"+vnm+"'", "");
                        }  
                    }
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
                    
                    if(vwPrp.toUpperCase().equals("MEM_COMMENT"))
                    fldName = "MEM_COM1";
                    
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
                    
                    String fldVal = util.nvl((String)rs1.getString(fldName));
                    
                    pkt.setValue(vwPrp, fldVal);
                    }
                    pkt.setValue("CP_DIS",util.calculateDisVlu(util.nvl((String)pkt.getRapRte()).trim(),util.nvl((String)pkt.getCts()).trim(),util.nvl((String)pkt.getValue("CP")),"DIS"));
                    pkts.add(pkt);
                }
                rs1.close();
                pst1.close();
            }
            
            if(!pktLst.equals("")){
                pktLst = util.pktNotFound(pktLst);
                req.setAttribute("vnmNotFnd", pktLst);
            }
        }
        rs.close();  
        pst.close();
        udf.setPkts(pkts);
        info.setValue(memoIdn + "_PKTS", pkts);
      
            ArrayList byrList = new ArrayList();
            String    getByr  =
                "select nme_idn, nme  byr from nme_v a " + " where nme_idn = ? "
                + " or exists (select 1 from nme_grp_dtl c, nme_grp b where a.nme_idn = c.nme_idn and b.nme_grp_idn = c.nme_grp_idn "
                + " and b.nme_idn = ? and b.typ = 'BUYER') " + " order by 2 ";

            ary  = new ArrayList();
            ary.add(String.valueOf(udf.getNmeIdn()));
            ary.add(String.valueOf(udf.getNmeIdn()));
            
           outLst = db.execSqlLst("byr", getByr, ary);
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
          while (rs.next()) {
                ByrDao byr = new ByrDao();

                byr.setByrIdn(rs.getInt("nme_idn"));

                String nme = util.nvl(rs.getString("byr"));

               

                byr.setByrNme(nme);
                byrList.add(byr);
            }
            rs.close();
            pst.close();
           
            String    symbol  =
                "select txt from nme_dtl where mprp=? and nme_idn = ? ";
            ary  = new ArrayList();
            ary.add("SYMBOL");
            ary.add(String.valueOf(udf.getNmeIdn()));
          outLst = db.execSqlLst("byr", symbol, ary);
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            if(rs.next()) {
                String symboltxt = rs.getString("txt");
                udf.setValue("SYMBOL", symboltxt);
            }
            rs.close();
            pst.close();
        req.setAttribute("view", "Y");
        req.setAttribute("memoIdn", String.valueOf(memoIdn));
        info.setValue("RTRN_PKTS", pkts);
        udf.setByrLstFetch(byrList);
        udf.setByrLst(byrList);
        ArrayList  trmList = srchQuery.getTerm(req,res, udf.getNmeIdn());
        udf.setTrmsLst(trmList);
            
            ArrayList byrCbList = srchQuery.getBuyerCabin(req, res);
            if(byrCbList !=null && byrCbList.size()>0){
                udf.setValue("ByrCbList", byrCbList);
            }
          util.updAccessLog(req,res,"Memo Return ", "Memo Return load pkts done pkts size "+pkts.size());
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("MEMO_RETURN");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("MEMO_RETURN");
            allPageDtl.put("MEMO_RETURN",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            pageList=(ArrayList)pageDtl.get("BLOCK_POPUP");
            if(pageList!=null && pageList.size() >0){
                for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                dflt_val=(String)pageDtlMap.get("dflt_val");
                lov_qry=(String)pageDtlMap.get("lov_qry");
                blockpopup_yn=dflt_val; 
                udf.setValue("isBLOCK", lov_qry);
                }
            }
            udf.setValue("BLOCK_POPUP", blockpopup_yn);
            req.setAttribute("NMEIDN",String.valueOf(udf.getNmeIdn()));
            util.updAccessLog(req,res,"Memo Return", "end");
            finalizeObject(db, util);
        return am.findForward(view);
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
          util.updAccessLog(req,res,"Memo Return ", "Memo Return save");
        ArrayList params = null;
        boolean isRtn = false;
        boolean isCl = false;
        MemoReturnFrm udf = (MemoReturnFrm) af;
        HashMap dbinfo = info.getDmbsInfoLst();
        String externalLnk = util.nvl((String)dbinfo.get("EXTERNALLNK"));
        String rapsync = util.nvl((String)dbinfo.get("RAPSYNC"));
        String cnt = util.nvl((String)dbinfo.get("CNT"));
       String rtnPktIdn = "";
          String clPktIdn = "";
        ArrayList pkts       = new ArrayList();
        int       memoIdn    = udf.getOldMemoIdn();
        int       appMemoIdn = 0;
        String    memoTyp    = "",
                  lFlg       = "IH";
        String buttonPressed = "";
        ArrayList rtnPktLst = new ArrayList();
        ArrayList clPktLst = new ArrayList();
        ArrayList appPktLst = new ArrayList();
        if(udf.getValue("wtChange")!=null){
            return WTChange(am, af, req, res);   
         }else{
        if (udf.getValue("consignment")!=null)
        buttonPressed = "consignment";
       String exh_rte = util.nvl((String)udf.getValue("exh_rte"));
            ArrayList ary = new ArrayList();
            String noteperson = util.nvl((String)udf.getValue("noteperson"));
            String cabin = util.nvl((String)udf.getValue("cabin"));
            String globalrmk = util.nvl((String)udf.getValue("rmk"));
            String symbol = util.nvl((String)udf.getValue("SYMBOL"));
            String loc_of_business = util.nvl((String)udf.getValue("LOC_OF_BUSINESS"));
            String byr_contacted = util.nvl((String)udf.getValue("BYR_CONTACTED"));
            String byr_status = util.nvl((String)udf.getValue("BYR_STATUS"));
            String byr_mod_of_sale = util.nvl((String)udf.getValue("BYR_MOD_OF_SALE"));
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("MEMO_RETURN");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("MEMO_RETURN");
            allPageDtl.put("MEMO_RETURN",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            String blockpopup_yn="N";
            String redirect="";
            ArrayList pageList=new ArrayList();
            HashMap pageDtlMap=new HashMap();
            ArrayList mailMemoTypList=new ArrayList();
            mailMemoTypList.add("WA");
            String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
            pageList=(ArrayList)pageDtl.get("BLOCK_POPUP");
            if(pageList!=null && pageList.size() >0){
                for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                dflt_val=(String)pageDtlMap.get("dflt_val");
                blockpopup_yn=dflt_val; 
                }
            }
            
            pageList=(ArrayList)pageDtl.get("MAIL_MEMO");
            if(pageList!=null && pageList.size() >0){
                for(int j=0;j<pageList.size();j++){
                    pageDtlMap=(HashMap)pageList.get(j);
                    mailMemoTypList.add((String)pageDtlMap.get("dflt_val"));
             }}
            pageList=(ArrayList)pageDtl.get("REDIRECT");
            if(pageList!=null && pageList.size() >0){
                for(int j=0;j<pageList.size();j++){
                    pageDtlMap=(HashMap)pageList.get(j);
                    redirect=util.nvl(((String)pageDtlMap.get("dflt_val")));
             }}
        pkts = (ArrayList)info.getValue("RTRN_PKTS");
            //(ArrayList) info.getValue(memoIdn + "_PKTS");    // udf.getPkts();

        for (int i = 0; i < pkts.size(); i++) {
            PktDtl pkt     = (PktDtl) pkts.get(i);
            long   lPktIdn = pkt.getPktIdn();
            String vnm = pkt.getVnm();
            String lStt    = util.nvl((String) udf.getValue("stt_" + lPktIdn));

        

            String updPkt = "";

            params = new ArrayList();
            params.add(String.valueOf(memoIdn));
            params.add(Long.toString(lPktIdn));

            if (lStt.equals("AP")) {
                if (appMemoIdn == 0) {
                    memoTyp = udf.getTyp().substring(0, 1)+"AP";

                    if (memoTyp.equals("WAP")) {
                        lFlg = "WEB";
                    }
                    if(buttonPressed.equalsIgnoreCase("consignment")){
                        memoTyp="CS";
                        lFlg = "CS";
                    }
                    ary = new ArrayList();

                    ary.add(Integer.toString(udf.getNmeIdn()));
                    ary.add(Integer.toString(udf.getRelIdn()));
                    ary.add("IS");
                    ary.add(memoTyp);
                    ary.add("NN");
                    ary.add(String.valueOf(memoIdn));
                    ary.add(exh_rte);
                    ary.add(globalrmk);
                    ArrayList out = new ArrayList();

                    out.add("I");

                    CallableStatement cst = null;

                    cst = db.execCall(
                        "MKE_HDR ",
                        "memo_pkg.gen_hdr(pByrId => ?, pRlnIdn => ?, pStt => ? , pTyp => ? , pflg => ? , pFrmId => ?,  pXrt => ? ,pRem => ?, pMemoIdn => ?)",
                        ary, out);
                    appMemoIdn = cst.getInt(ary.size()+1);
                  cst.close();
                  cst=null;
                    if(!noteperson.equals("")){
                    ary=new ArrayList();
                    ary.add(noteperson);
                    ary.add(String.valueOf(appMemoIdn));
                    String updateFlg = "update mjan set NOTE_PERSON=? where idn=?";
                    int cntmj = db.execUpd("update mjan", updateFlg, ary);
                    }
                    if(!cabin.equals("")){
                    ary=new ArrayList();
                    ary.add(cabin);
                    ary.add(String.valueOf(appMemoIdn));
                    String updateFlg = "update mjan set byr_cabin=? where idn=?";
                    int cntmj = db.execUpd("update mjan", updateFlg, ary);
                    }
                    
                
                }

                updPkt = " memo_pkg.app_pkt(?, ?)";
                appPktLst.add(vnm);
            }

            if (lStt.equals("RT")) {
                String rmk = util.nvl((String)udf.getValue("rmk_"+Long.toString(lPktIdn)));
                if(rmk.equals(""))
                rmk=globalrmk;
                params.add(rmk);
                updPkt = " memo_pkg.rtn_pkt( pMemoId =>?, pStkIdn=> ? , pRem => ?)";
            }
            
            if (lStt.equals("IS")) {
                if(!noteperson.equals("")){
                ary = new ArrayList();
                ary=new ArrayList();
                ary.add(noteperson);
                ary.add(globalrmk);
                ary.add(String.valueOf(memoIdn));
                String updateFlg = "update mjan set NOTE_PERSON=?,rmk=? where idn=?";
                int cntmj = db.execUpd("update mjan", updateFlg, ary);
                }
                if(!globalrmk.equals("")){
                    ary = new ArrayList();
                    ary.add(globalrmk);
                    ary.add(String.valueOf(memoIdn));
                    String updateFlg = "update mjan set rmk=? where idn=?";
                    int cntmj = db.execUpd("update mjan", updateFlg, ary);
                }
                if(!cabin.equals("")){
                    ary=new ArrayList();
                    ary.add(cabin);
                    ary.add(String.valueOf(memoIdn));
                    String updateFlg = "update mjan set byr_cabin=? where idn=?";
                    int cntmj = db.execUpd("update mjan", updateFlg, ary);
                }
            }

            int ct = 0;

            if (updPkt.length() > 0) {
                ct = db.execCall(" App Pkts", updPkt, params);
            }

             if (lStt.equals("CL")) {
                  String clPkt = "memo_pkg.Cancel_ISPkt(pIdn => ?, pStkIdn => ?)";
                  ary  = new ArrayList();
                   ary.add(String.valueOf(memoIdn));
                  ary.add(Long.toString(lPktIdn));
                  ct = db.execCall("clPkt", clPkt, ary);
                  isCl = true;
                  clPktIdn = clPktIdn+" , "+vnm;
                  clPktLst.add(Long.toString(lPktIdn));
            }
            if (lStt.equals("RT") || lStt.equals("CL")) {
                String pndgAlc = "memo_pkg.pndg_alc(?, ?)";
                ary     = new ArrayList();

                ary.add(String.valueOf(memoIdn));
                ary.add(Long.toString(lPktIdn));
                ct = db.execCall("pngd", pndgAlc, ary);
                isRtn = true;
                rtnPktIdn = rtnPktIdn+" , "+vnm;
                rtnPktLst.add(Long.toString(lPktIdn));
            }

            if ((lStt.equals("AP")) && (appMemoIdn > 0)) {
                String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? , pPrpTyp => ?, pgrp => ? )";
                String app_memoPkt = "memo_pkg.App_Memo_Pkt(pFrmMemoId => ? , pAppMemoId => ?, pStkIdn => ?, pAppFlg => ?)";
                params = new ArrayList();
                params.add(String.valueOf(memoIdn));
                params.add(String.valueOf(appMemoIdn));
                params.add(Long.toString(lPktIdn));
                params.add(lFlg);
                ct = db.execCall("app_memo_pkt",app_memoPkt , params);
                if(blockpopup_yn.equals("Y") && buttonPressed.equalsIgnoreCase("consignment")){
                        ary = new ArrayList();
                        ary.add(Long.toString(lPktIdn));
                        ary.add("WEB_BLOCK");
                        ary.add(util.nvl((String)udf.getValue("isBLOCK")));
                        ary.add("C");
                        ary.add("1");
                        ct = db.execCall("stockUpd",stockUpd, ary);                            
                }
                
                params = new ArrayList();
                params.add(Long.toString(lPktIdn));
                params.add("BLIND_YN");
                params.add("N");
                params.add("C");
                params.add("1");
                ct = db.execCall("stockUpd",stockUpd, params);
                
                if(!loc_of_business.equals("")){
                    params = new ArrayList();
                    params.add(Long.toString(lPktIdn));
                    params.add("LOC_OF_BUSINESS");
                    params.add(loc_of_business);
                    params.add("C");
                    params.add("1");
                    ct = db.execCall("stockUpd",stockUpd, params);
                    
                    params = new ArrayList();
                    params.add(loc_of_business);
                    params.add(String.valueOf(appMemoIdn));
                    ct = db.execCall("updateQ","update mjan set LOC_OF_BUSINESS=? where idn=?", params);
                }
                
                if(!byr_contacted.equals("")){
                    params = new ArrayList();
                    params.add(Long.toString(lPktIdn));
                    params.add("BYR_CONTACTED");
                    params.add(byr_contacted);
                    params.add("C");
                    params.add("1");
                    ct = db.execCall("stockUpd",stockUpd, params);
                    
                    params = new ArrayList();
                    params.add(byr_contacted);
                    params.add(String.valueOf(appMemoIdn));
                    ct = db.execCall("updateQ","update mjan set BYR_CONTACTED=? where idn=?", params);
                }
                
                if(!byr_status.equals("")){
                    params = new ArrayList();
                    params.add(Long.toString(lPktIdn));
                    params.add("BYR_STATUS");
                    params.add(byr_status);
                    params.add("C");
                    params.add("1");
                    ct = db.execCall("stockUpd",stockUpd, params);
                    
                    params = new ArrayList();
                    params.add(byr_status);
                    params.add(String.valueOf(appMemoIdn));
                    ct = db.execCall("updateQ","update mjan set BYR_STATUS=? where idn=?", params);
                }
                
                if(!byr_mod_of_sale.equals("")){
                    params = new ArrayList();
                    params.add(Long.toString(lPktIdn));
                    params.add("BYR_MOD_OF_SALE");
                    params.add(byr_mod_of_sale);
                    params.add("C");
                    params.add("1");
                    ct = db.execCall("stockUpd",stockUpd, params);
                    
                    params = new ArrayList();
                    params.add(byr_mod_of_sale);
                    params.add(String.valueOf(appMemoIdn));
                    ct = db.execCall("updateQ","update mjan set BYR_MOD_OF_SALE=? where idn=?", params);
                }
                if(!symbol.equals("")){
                 
                    params.add(Long.toString(lPktIdn));
                    params.add("SYMBOL");
                    params.add(symbol);
                  
                  db.execCall("stockUpd","stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? )", params);
                }
//                String appMemoPkt =
//                    " insert into jandtl(idn, mstk_idn, qty, cts, rte, quot, dte, stt, rap_dis)\n"
//                    + " select ?, a.idn, a.qty, a.cts, nvl(upr, cmp), b.quot, sysdate, 'AP', decode(a.rap_rte, 1, null, trunc(100 - (quot*100/a.rap_rte),2))\n"
//                    + " from mstk a, jandtl b where a.idn = b.mstk_idn and b.idn = ? and b.mstk_idn = ? ";
//                ArrayList ary = new ArrayList();
//
//                ary.add(String.valueOf(appMemoIdn));
//                ary.add(String.valueOf(memoIdn));
//                ary.add(Long.toString(lPktIdn));
//                ct = db.execUpd(" app pkt ", appMemoPkt, ary);
//
//                if (ct > 0) {
//                    String updMStk = " update mstk set odt = sysdate, stt = 'MKAP', flg = ? where idn = ? ";
//
//                    ary = new ArrayList();
//                    ary.add(lFlg);
//                    ary.add(Long.toString(lPktIdn));
//                    ct = db.execUpd(" app pkt ", updMStk, ary);
//                }
            }
        }
//        if(rapsync.equals("Y"))
//        new SyncOnRap(cnt).start();
        info.setValue("RTRN_PKTS",null);
        String updJanQty = "jan_qty(?) ";

        params = new ArrayList();
        params.add(String.valueOf(memoIdn));

        int ct = 0;

        ct = db.execCall("JanQty", updJanQty, params);

        if (appMemoIdn > 0) {
            params = new ArrayList();
            params.add(String.valueOf(appMemoIdn));

            ct = db.execCall("calQuot", "MEMO_PKG.Cal_Fnl_Quot(pIdn => ?)", params);

            
            updJanQty = " jan_qty(?) ";
            params    = new ArrayList();
            params.add(Integer.toString(appMemoIdn));
            ct = 0;
            ct = db.execCall("JanQty", updJanQty, params);
            
           
        }
        if (appMemoIdn > 0) {
            
           session.setAttribute("memoId", String.valueOf(appMemoIdn));
            if(buttonPressed.equals("")){
            MailAction mailObj = new MailAction();
            if(symbol.equals("INV")){
            params = new ArrayList();
            params.add(String.valueOf(appMemoIdn));
            ct = db.execCall("updateQ","update mjan set mail_stt='REJ' where idn=?", params);
            }else
            mailObj.sendAppMemoMail(appMemoIdn, req, res);
            }
           if(!externalLnk.equals("")){
           String usr=util.nvl((String)dbinfo.get("DFLTUSR"));
           String vnm = appPktLst.toString();
           vnm = vnm.replaceAll("\\[", "");
           vnm = vnm.replaceAll("\\]", "").replaceAll(" ", "");
           ary = new ArrayList();
           ary.add(Integer.toString(udf.getRelIdn()));
           String usrQ="select usr from web_usrs where rel_idn=?";
           ResultSet rs = db.execSql("usrQ", usrQ, ary);
           while (rs.next()) {
           usr=util.nvl(rs.getString("usr"));    
           }
           rs.close();
           externalLnk=externalLnk.replaceAll("USR", usr);
           externalLnk=externalLnk.replaceAll("VNM", vnm);
           req.setAttribute("externalLnk",externalLnk);
           }
            req.setAttribute("memoId",String.valueOf(appMemoIdn));
          util.updAccessLog(req,res,"Memo Return ", "Memo approve done with id "+appMemoIdn);
          if(!redirect.equals("")){
              if(redirect.equals("MEMOFORM"))
              return am.findForward("memo");
          }else if (udf.getValue("saveRedirectToSale")!=null){
           req.setAttribute("APP","Y");
           return am.findForward("sale");
          }else if (udf.getValue("saveRedirectToDlv")!=null){
           return loadsaveRedirectToDlv(am, af, req, res,String.valueOf(appMemoIdn));
          }else
           return loadRtn(am, af, req, res);
        }else if(isRtn){
            req.setAttribute("rtnPtk", "Packets get return"); 
              memoTyp = util.nvl(udf.getTyp());
              if(mailMemoTypList.contains(memoTyp)){
                  HashMap paramsLst = new HashMap();
                  paramsLst.put("memoId", memoIdn);
                  paramsLst.put("pktList", rtnPktLst);
                  MailAction mailObj = new MailAction();
                  mailObj.sendRtnMemoMail(paramsLst , req , res);
              }
          util.updAccessLog(req,res,"Memo Return ", "Memo Return done");
            finalizeObject(db, util);
            return loadRtn(am, af, req, res);
        }else if(isCl){
           req.setAttribute("rtnPtk", "Packets get Cancel :-"+clPktIdn); 
            finalizeObject(db, util);
          return loadRtn(am, af, req, res);
        }else
            return loadRtn(am, af, req, res);
        }}}
        return loadRtn(am, af, req, res);
    }

    public ActionForward WTChange(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
          util.updAccessLog(req,res,"Memo Return ", "Memo Return save");
         MemoReturnFrm udf = (MemoReturnFrm) af;
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("MEMO_SALE");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("MEMO_SALE");
            allPageDtl.put("MEMO_SALE",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            String exh_rte = util.nvl((String)udf.getValue("exh_rte"));
            String globalrmk = util.nvl((String)udf.getValue("rmk"));
            String memoTyp = udf.getTyp().substring(0, 1)+"AP";
            ArrayList appStkInLst = new ArrayList();
            ArrayList rtStkIdnLst = new ArrayList();
            String stkIdnStr="";
            HashMap map = (HashMap)udf.getValues();
            Set<String> keys = map.keySet();
            for(String key: keys){
                if(key.indexOf("stt_")!=-1){
                    String val = (String)udf.getValue(key);
                    if(val.equals("AP")){
                        String[] keyLst = key.split("_");
                        String stkIdn=keyLst[1];
                            appStkInLst.add(stkIdn);
                     }
                        if(val.equals("RT")){
                            String[] keyLst = key.split("_");
                            String stkIdn=keyLst[1];
                                rtStkIdnLst.add(stkIdn);
                         }
                    }
             }
         if(appStkInLst!=null && appStkInLst.size()>0){
                stkIdnStr = appStkInLst.toString();
                stkIdnStr =stkIdnStr.replace("[", "(");
                stkIdnStr =stkIdnStr.replace("]", ")");
            ArrayList boxList= new ArrayList();
            int memoIdn = udf.getOldMemoIdn();
            String summryStr="select sum(jd.qty) qty, sum(trunc(jd.cts,3)) cts, trunc(((sum(jd.cts*nvl(jd.fnl_sal, jd.quot)) / sum(jd.cts)))) avgRte , prp1.val BoxTYP,prp2.val BoxVal,prp1.srt,prp2.srt from jandtl jd , stk_dtl prp1, stk_dtl prp2\n" + 
            "where idn=? and jd.mstk_idn  in "+stkIdnStr+
            "and jd.mstk_idn = prp1.mstk_idn and prp1.grp = 1 and prp1.mprp = 'BOX_TYP'\n" + 
            "and jd.mstk_idn = prp2.mstk_idn and prp2.grp = 1 and prp2.mprp = 'BOX_ID'\n" + 
            "group by prp1.val,prp2.val ,prp1.srt,prp2.srt order by prp1.srt,prp2.srt ";
            ArrayList params = new ArrayList();
            params.add(String.valueOf(memoIdn));
            ArrayList rsLst = db.execSqlLst("summryStr", summryStr, params);
            PreparedStatement pst = (PreparedStatement)rsLst.get(0);
            ResultSet rs = (ResultSet)rsLst.get(1);
            while(rs.next()){
                HashMap boxMap = new HashMap();
                String boxTyp = rs.getString("BoxTYP");
                String boxId=rs.getString("BoxVal");
                String cts = rs.getString("cts");/*  */
                String qty = rs.getString("qty");
                String avgRte = rs.getString("avgRte");
                boxMap.put("QTY", qty);
                boxMap.put("CTS", cts);
                boxMap.put("BOXTYP", boxTyp);
                boxMap.put("BOXID", boxId);
                boxMap.put("AVGRTE", avgRte);
                boxList.add(boxMap);
            }
                rs.close();
                pst.close();
           req.setAttribute("BOXDTLList", boxList);
           udf.setValue("IDNSTR", stkIdnStr);
           udf.setValue("MEMOID", memoIdn);
            udf.setValue("exh_rte", exh_rte);
            udf.setValue("globalrmk", globalrmk);
            udf.setValue("MEMOTYP", memoTyp);
            udf.setRelIdn(udf.getRelIdn());
            udf.setNmeIdn(udf.getNmeIdn());
            
                String getMemoInfo =
                " select a.nme_idn, a.nmerel_idn,byr.get_trms(a.nmerel_idn) trms,  byr.get_nm(a.nme_idn) byr, byr.get_nm(c.emp_idn) SalEmp,a.typ, a.qty, a.cts, a.vlu, to_char(a.dte, 'dd-Mon-rrrr HH24:mi:ss') dte,a.rmk,a.note_person,a.byr_cabin "
                + " from mjan a ,mnme c where a.idn = ? and a.stt='IS' and a.nme_idn=c.nme_idn  ";

               params = new ArrayList();
               params.add(String.valueOf(memoIdn));
              rsLst = db.execSqlLst(" Memo Info", getMemoInfo, params);
              pst = (PreparedStatement)rsLst.get(0);
              ResultSet mrs = (ResultSet)rsLst.get(1);
                if (mrs.next()) {
                udf.setNmeIdn(mrs.getInt("nme_idn"));
                udf.setRelIdn(mrs.getInt("nmerel_idn"));
                udf.setByr(mrs.getString("byr"));
                udf.setTyp(mrs.getString("typ"));
                udf.setDte(mrs.getString("dte"));
                udf.setValue("trmsLb", mrs.getString("trms"));
                udf.setValue("noteperson", util.nvl(mrs.getString("note_person")));
                udf.setValue("cabin", util.nvl(mrs.getString("byr_cabin")));
                udf.setValue("rmk", util.nvl(mrs.getString("rmk")));
                HashMap buyerDtlMap=util.getBuyerDtl(String.valueOf(mrs.getInt("nme_idn")));
                udf.setValue("email",util.nvl((String)buyerDtlMap.get("EML")));
                udf.setValue("mobile",util.nvl((String)buyerDtlMap.get("MBL")));
                udf.setValue("office",util.nvl((String)buyerDtlMap.get("OFC")));
                udf.setValue("SALEMP", util.nvl(mrs.getString("SalEmp")));
                }
                mrs.close();
                pst.close();
                ArrayList byrList = new ArrayList();
                String    getByr  =
                    "select nme_idn, a.nme byr from nme_v a " + " where nme_idn = ? "
                    + " or exists (select 1 from nme_grp_dtl c, nme_grp b where a.nme_idn = c.nme_idn and b.nme_grp_idn = c.nme_grp_idn "
                    + " and b.nme_idn = ? and b.typ = 'BUYER' and c.vld_dte is null) " + " order by 2 ";

               ArrayList ary = new ArrayList();
                ary.add(String.valueOf(udf.getNmeIdn()));
                ary.add(String.valueOf(udf.getNmeIdn()));
              rsLst = db.execSqlLst("byr", getByr, ary);
              pst = (PreparedStatement)rsLst.get(0);
              rs = (ResultSet)rsLst.get(1);
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
              rsLst = db.execSqlLst("", brokerSql, ary);
              pst = (PreparedStatement)rsLst.get(0);
              rs = (ResultSet)rsLst.get(1);
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
              rsLst = db.execSqlLst("memo pkt", sql, ary);
              pst = (PreparedStatement)rsLst.get(0);
              rs = (ResultSet)rsLst.get(1);
                while(rs.next()){
                    MAddr addr = new MAddr();
                    addr.setIdn(rs.getString("addr_idn"));
                    addr.setAddr(rs.getString("addr"));
                    maddrList.add(addr);
                }
                rs.close();
                pst.close();
              
                ArrayList thruBankList = new ArrayList();
                String thruBankSql = "select b.nme_dtl_idn , b.txt from mnme a , nme_dtl b " + 
                                     "where a.nme_idn = b.nme_idn and b.mprp like 'THRU_BANK%' and a.nme_idn=? and b.vld_dte is null";
                ary = new ArrayList();
                ary.add(String.valueOf(udf.getNmeIdn()));
              rsLst = db.execSqlLst("thruBank", thruBankSql, ary );
              pst = (PreparedStatement)rsLst.get(0);
              rs = (ResultSet)rsLst.get(1);
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
                ArrayList bnkAddrList = new ArrayList();
                boolean dfltbankgrp=true;
                String banknmeIdn=  "" ;
                ary = new ArrayList();
                ary.add(String.valueOf(udf.getNmeIdn()));
                String setbnkCouQ="select bnk_idn,courier from  mdlv where idn in(select max(idn) from mdlv where nme_idn=?)";
              rsLst = db.execSqlLst("setbnkCouQ", setbnkCouQ, ary);
              pst = (PreparedStatement)rsLst.get(0);
              rs = (ResultSet)rsLst.get(1);
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
                  rsLst = db.execSqlLst("defltBnkQ", defltBnkQ, ary);
                  pst = (PreparedStatement)rsLst.get(0);
                  rs = (ResultSet)rsLst.get(1);
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
                "        and b.vld_dte is null";
                  rsLst = db.execSqlLst("defltBnkQ", defltBnkQ, new ArrayList());
                  pst = (PreparedStatement)rsLst.get(0);
                  rs = (ResultSet)rsLst.get(1);
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
              rsLst = db.execSqlLst("chargesQ", chargesQ,ary);
              pst = (PreparedStatement)rsLst.get(0);
              rs = (ResultSet)rsLst.get(1);
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
                session.setAttribute("chargesLst", chargesLst);
                SearchQuery srchQuery = new SearchQuery();
               ArrayList  trmList = srchQuery.getTerm(req,res, udf.getNmeIdn());
                ArrayList daytrmList = srchQuery.getdayTerm(req,res);
                ArrayList groupList = srchQuery.getgroupList(req, res);
                ArrayList bankList = srchQuery.getBankList(req, res);
                ArrayList courierList = srchQuery.getcourierList(req, res);
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
                udf.setValue("memoIdn", memoIdn);
                req.setAttribute("memoIdn", String.valueOf(memoIdn));
                udf.setByrIdn(String.valueOf(udf.getNmeIdn()));
                udf.setTrmsLst(trmList);
                udf.setDaytrmsLst(daytrmList);
            req.setAttribute("view", "Y");
             return am.findForward("loadWT");   
            }
            String rtnPkt=null;
            if(rtStkIdnLst!=null && rtStkIdnLst.size()>0){
                int memoIdn = udf.getOldMemoIdn();
                ArrayList params =null;
                for(int i=0;i<rtStkIdnLst.size();i++){
                    params = new ArrayList();
                String stkIdn  = (String)rtStkIdnLst.get(i);
                String rmk = util.nvl((String)udf.getValue("rmk_"+stkIdn));
                if(rmk.equals(""))
                rmk=globalrmk;
                 params.add(String.valueOf(memoIdn));
                 params.add(stkIdn);
                 params.add(rmk);
                int ct = db.execCall(" App Pkts", "memo_pkg.rtn_pkt( pMemoId =>?, pStkIdn=> ? , pRem => ?)", params);
               if(ct > 0)
                   rtnPkt =rtnPkt+","+stkIdn;
                }
                rtnPkt = rtnPkt.replaceFirst(",", "");
                req.setAttribute("rtnPtk", "Return Packets :-"+rtnPkt);
                return am.findForward("load");   
            }
            return am.findForward("load");   
        }
    }
    public ActionForward WTCHNSAVE(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
          util.updAccessLog(req,res,"Memo Return ", "Memo Return save");
               MemoReturnFrm udf = (MemoReturnFrm) af;
            String outMsg="";
            try {
             
                db.setAutoCommit(false);
                
                String memoIdn = (String)udf.getValue("MEMOID");
                String memoTyp = (String)udf.getValue("MEMOTYP");
                String exh_rte = (String)udf.getValue("exh_rte");
                String globalrmk = (String)udf.getValue("globalrmk");
                String IDNSTR = (String)udf.getValue("IDNSTR");
                ArrayList ary = new ArrayList();
                ary.add(Integer.toString(udf.getNmeIdn()));
                ary.add(Integer.toString(udf.getRelIdn()));
                ary.add("IS");
                ary.add(memoTyp);
                ary.add("NN");
                ary.add(String.valueOf(memoIdn));
                ary.add(exh_rte);
                ary.add(globalrmk);
                ArrayList out = new ArrayList();
                out.add("I");
                CallableStatement cst = null;
                cst =db.execCall("MKE_HDR ", "memo_pkg.gen_hdr(pByrId => ?, pRlnIdn => ?, pStt => ? , pTyp => ? , pflg => ? , pFrmId => ?,  pXrt => ? ,pRem => ?, pMemoIdn => ?)",
                 ary, out);
                int appMemoIdn = cst.getInt(ary.size() + 1);
              cst.close();
              cst=null;
                 boolean isCommit=true;
                if(appMemoIdn>0){
                Enumeration reqNme = req.getParameterNames();
                while (reqNme.hasMoreElements()) {
                    String paramNm = util.nvl((String)reqNme.nextElement());
                    if (paramNm.indexOf("CHK_") > -1) {
                        String val = req.getParameter(paramNm);
                        String[] boxDtl = val.split("@");
                        String boxTyp = boxDtl[0];
                        String boxId = boxDtl[1];
                        String qty = util.nvl(req.getParameter("QTY_" + boxTyp + "_" + boxId));
                        String cts = util.nvl(req.getParameter("CTS_" + boxTyp + "_" + boxId));
                        String RTE = util.nvl(req.getParameter("RTE_" + boxTyp + "_" + boxId));
                        ary = new ArrayList();
                        ary.add(Integer.toString(appMemoIdn));
                        ary.add(qty);
                        ary.add(cts);
                        ary.add(RTE);
                        ary.add(boxTyp);
                        ary.add(boxId);
                        out = new ArrayList();
                        out.add("I");
                        out.add("V");
                        CallableStatement ct =
                            db.execCall("WtChange", "memo_pkg.WTCHGAPP_PKT(pAppMemoIdn => ? ,pQty => ? , pCts => ? , pRte => ? ,pBoxTyp => ? ,pBoxId => ?, pStkIdn => ? , pMsg => ?)",
                                        ary, out);
                        long stkIdn = ct.getLong(ary.size() + 1);
                        String msg = ct.getString(ary.size() + 2);
                        if (msg.equals("Success")) {
                            String updmstk =
                                "update mstk a set a.stt='APSMX_MRG' , pkt_ty='MRG' , pkt_rt=? where a.idn in " + IDNSTR + " " +
                                " and exists ( select 1 from stk_dtl bx where bx.mstk_idn=a.idn and bx.grp=1 and bx.mprp='BOX_TYP' and bx.val=?) " +
                                " and exists ( select 1 from stk_dtl bi where bi.mstk_idn=a.idn and bi.grp=1 and bi.mprp='BOX_ID' and bi.val=?)";
                            ary = new ArrayList();
                            ary.add(String.valueOf(stkIdn));
                            ary.add(boxTyp);
                            ary.add(boxId);
                          int upd=db.execUpd("updMskt", updmstk, ary);
                          if(upd>0){
                          String updJanDtl="update jandtl a set a.stt='MRG' where a.mstk_idn in "+ IDNSTR +" and a.idn=? " + 
                          "and exists ( select 1 from stk_dtl bx where bx.mstk_idn=a.mstk_idn and bx.grp=1 and bx.mprp='BOX_TYP' and bx.val=?)\n" + 
                          "and exists ( select 1 from stk_dtl bi where bi.mstk_idn=a.mstk_idn and bi.grp=1 and bi.mprp='BOX_ID' and bi.val=?)";
                              ary = new ArrayList();
                              ary.add(memoIdn);
                              ary.add(boxTyp);
                              ary.add(boxId);
                              upd=db.execUpd("updJanDtl", updJanDtl, ary);
                              if(upd < 0){
                                  isCommit=false;
                                  outMsg="Error in Process";
                                     break;
                                 }
                              
                          }else{
                              isCommit=false;
                              outMsg="Error in Process";
                            break;
                          }
                        
                        }else{
                            outMsg="Error in Process";
                            isCommit=false;
                            break;
                            
                        }
                    }
                }
                if(isCommit){
                if (appMemoIdn > 0) {
                    ArrayList params = new ArrayList();
                    params.add(String.valueOf(appMemoIdn));
                    int ct = db.execCall("calQuot", "MEMO_PKG.Cal_Fnl_Quot(pIdn => ?)", params);

                    String updJanQtynew = " jan_qty(?) ";
                    params = new ArrayList();
                    params.add(Integer.toString(appMemoIdn));
                    ct = db.execCall("JanQty", updJanQtynew, params);
                    
                    String updJanQtyold = " jan_qty(?) ";
                    params = new ArrayList();
                    params.add(memoIdn);
                    ct = db.execCall("JanQty", updJanQtyold, params);
                    String Typ ="SL";
                    String sale = util.nvl((String)udf.getValue("sale"));
                    String LocalSale = util.nvl((String)udf.getValue("LocalSale"));
                    if(!LocalSale.equals(""))
                        Typ="LS";
                    
                            ary = new ArrayList();
                            ary.add(Integer.toString(udf.getNmeIdn()));
                            ary.add(Integer.toString(udf.getRelIdn()));
                            ary.add(udf.getValue("byrTrm"));
                            ary.add(udf.getByrIdn());
                            ary.add(udf.getValue("addr"));
                            ary.add(Typ);
                            ary.add("IS");
                            ary.add(String.valueOf(appMemoIdn));
                            ary.add("NN");
                            ary.add(util.nvl((String) udf.getValue("aadatpaid"), "Y"));
                            ary.add(util.nvl((String) udf.getValue("brk1paid"), "Y"));
                            ary.add(util.nvl((String) udf.getValue("brk2paid"), "Y"));
                            ary.add(util.nvl((String) udf.getValue("brk3paid"), "Y"));
                            ary.add(util.nvl((String) udf.getValue("brk4paid"), "Y"));
                            ary.add(util.nvl((String)udf.getValue("exh_rte")));
                            ary.add(util.nvl((String) udf.getValue("bankIdn")));
                            ary.add(util.nvl((String) udf.getValue("courier")));
                            ary.add(util.nvl((String)udf.getValue("throubnk")));
                            ary.add(util.nvl((String)udf.getValue("grpIdn")));
                            out = new ArrayList();
                            out.add("I");

                           cst = db.execCall(
                                "MKE_HDR ",
                                "sl_pkg.gen_hdr(pByrId => ?, pRlnIdn => ?, pTrmsIdn => ?, pInvNmeIdn => ?, pInvAddrIdn => ?, pTyp => ?, pStt => ?, pFrmId => ? , pFlg => ? , paadat_paid => ?, pMBRK1_PAID => ? , pMBRK2_PAID => ?, pMBRK3_PAID => ?, pMBRK4_PAID => ?, pExhRte => ?, p_bank_id => ? ,p_courier => ? ,pThruBnk => ?,co_idn => ?,  pIdn => ? )", ary, out);
                           int appSlIdn = cst.getInt(ary.size()+1);
                            cst.close();                    
                            if(appSlIdn>0){
                                ary = new ArrayList();
                               
                                ary.add(String.valueOf(appMemoIdn));
                              ct = db.execUpd("update jandtl", "    Update jandtl set stt = 'SL', dte_sal=sysdate,aud_modified_by = pack_var.get_usr where idn = ?", ary);
                                ary = new ArrayList();
                                ary.add(String.valueOf(appSlIdn));
                                ary.add(String.valueOf(appMemoIdn));
                                ary.add("SL");
                               ct = db.execCall("slPkg", "sl_pkg.Sal_Frm_Memo(pIdn => ? , pMemoIdn => ? , pStt => ? )", ary);
                               if(ct > 0){
                               
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
                                   if((!calcdis.equals("") && !calcdis.equals("0")) || (autoopt.equals("OPT"))){
                                   String insertQ="Insert Into Trns_Charges (trns_idn, ref_typ, ref_idn,charges_idn,amt,amt_usd,charges,CHARGES_PCT,net_usd,rmk,stt,flg)\n" + 
                                   "VALUES (TRNS_CHARGES_SEQ.nextval, 'SAL', ?,?,?,?,?,?,?,?,'A',?)";  
                                    ary=new ArrayList();
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
                                    ct = db.execUpd("Insert", insertQ, ary);
                                   }
                                   
                                   }
                                   }
                               
                                   params = new ArrayList();
                                   params.add(String.valueOf(appSlIdn));

                                   int calCt = db.execCall("calQuot", "SL_PKG.Cal_Fnl_Quot(pIdn => ?)", params);
                                   req.setAttribute("saleId", String.valueOf(appSlIdn));
                                   outMsg="Stones Sale Successfully with sale ID :-"+appSlIdn;
                                   req.setAttribute("saleId", String.valueOf(appSlIdn));
                                   isCommit=true;
                               }else{
                                   isCommit=false;
                                   outMsg="Error in Process"; 
                               }
                            }else{
                                isCommit=false;
                                outMsg="Error in Process";
                            }
                   
                        
                    }
                   
                    
                    
                }
                if(isCommit) {
                db.doCommit();
                }else{
                    outMsg="Error in Process";
                    db.doRollBack();
                }
                }else{
                    outMsg="Error in Process";
                    db.doRollBack();
                }
               
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
                outMsg="Error in Process";
                db.doRollBack();
            
            } finally {
                db.setAutoCommit(true);
            }
            req.setAttribute("msg", outMsg);     
            udf.reset();
            

            return am.findForward("loadWT");   
        }
    }
    public ActionForward loadsaveRedirectToDlv(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res,String appMemoIdn)
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
        MemoReturnFrm udf = (MemoReturnFrm) af;
        util.updAccessLog(req,res,"Memo Return ", "Memo Return loadsaveRedirectToDlv");
        ArrayList ary = new ArrayList();
        int appDlvIdn=0;
        int appSlIdn=0;
        ary = new ArrayList();
        ary.add(appMemoIdn);
        ary.add("SL");
        ArrayList out = new ArrayList();
        out.add("I");
        CallableStatement cst  = db.execCall("sale Confirm", "SL_PKG.Gen_Hdr_Frm_Memo(pMemoIdn =>?, pTyp => ?, pIdn => ? )", ary , out);
        appSlIdn = cst.getInt(ary.size()+1);
      cst.close();
      cst=null;
            
        if (appSlIdn != 0) {
            ary = new ArrayList();
            ary.add(String.valueOf(appSlIdn));
            ary.add("DLV");
            out = new ArrayList();
            out.add("I");
            cst  = db.execCall("delivery Confirm", "DLV_PKG.Gen_Hdr_Frm_Sale(pSaleIdn =>?, pTyp => ?, pIdn => ? )", ary , out);
            appDlvIdn = cst.getInt(ary.size()+1);
            cst.close();
        }
            
        if (appDlvIdn != 0) {
            req.setAttribute("dlvId", String.valueOf(appDlvIdn));
            req.setAttribute("SLMSG", "Packets get Deliver with delivery Id" + appDlvIdn);
            req.setAttribute("performaLink","Y");       
        }
        util.updAccessLog(req,res,"Memo Return ", "Memo Return loadsaveRedirectToDlv done");
        return am.findForward("saleDelivery");
    }
    }
    public ActionForward merge(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        MemoReturnFrm udf = (MemoReturnFrm) af;
          util.updAccessLog(req,res,"Memo Return ", "Memo Return merge load");
        String    sqlMerge = "";
        ArrayList    ary      = null;
        String    nmeIdn   = req.getParameter("nmeIdn");
        String typ = util.nvl(req.getParameter("typ"));
        String  memoTyp = util.nvl(req.getParameter("MemoTyp"));
        
        ArrayList trmList  = new ArrayList();
        sqlMerge = "select distinct a.nmerel_idn, b.trm||' '||b.rln terms from "
                  + "mjan a, cus_rel_v b ,  jan_pkt_dtl_v c where 1 = 1 " + "and a.nmerel_idn = b.rel_idn "
                   + "and a.nme_idn = ? and a.typ = ? and a.stt='IS' and a.idn = c.idn and c.PKT_TY in ('NR','SMX')";
        ary = new ArrayList();
        ary.add(nmeIdn);
        ary.add(typ);
          ArrayList  outLst = db.execSqlLst("trms", sqlMerge, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            TrmsDao trms = new TrmsDao();
            trms.setRelId(rs.getInt("nmerel_idn"));
            trms.setTrmDtl(rs.getString("terms"));
            trmList.add(trms);
        }
        rs.close();
        pst.close();
        udf.setTrmsLst(trmList);
        udf.setValue("nmeIdn", nmeIdn);
        udf.setValue("typ", typ);
        udf.setValue("MemoTyp", memoTyp);
          util.updAccessLog(req,res,"Memo Return ", "Memo Return merge load done");
            finalizeObject(db, util);
            util.noteperson();
        return am.findForward("merge");
        }
    }

    public ActionForward mergeMemo(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        MemoReturnFrm udf = (MemoReturnFrm) af;
          util.updAccessLog(req,res,"Memo Return ", "Memo Return memo merge load");
        ArrayList      ary     = new ArrayList();
        String      memoIds = "";
        try {
                String nmeIdn = (String)udf.getValue("nmeIdn");

                Enumeration reqNme = req.getParameterNames();

                while (reqNme.hasMoreElements()) {
                    String paramNm = (String)reqNme.nextElement();

                    if (paramNm.indexOf("cb_memo") > -1) {
                        String val = req.getParameter(paramNm);

                        if (memoIds.equals("")) {
                            memoIds = val;
                        } else {
                            memoIds = memoIds + "," + val;
                        }
                    }
                }

                ary.add(nmeIdn);
                ary.add(String.valueOf(memoIds));

                ArrayList out = new ArrayList();

                out.add("I");

                CallableStatement cst = null;

                cst = db.execCall("MKE_HDR ", "memo_pkg.Merge_Memo(pNmeIdn => ?, pIds => ?,  pMemoIdn => ?)", ary, out);

                int MemoIdn = cst.getInt(3);
              cst.close();
              cst=null;
            if(MemoIdn > 0){
                    String noteperson = util.nvl((String)udf.getValue("noteperson"));
                    String rmk = util.nvl((String)udf.getValue("rmk"));
                    if(!noteperson.equals("")){
                                       ary=new ArrayList();
                                       ary.add(noteperson);
                                       ary.add(String.valueOf(String.valueOf(MemoIdn)));
                                       String updateFlg = "update mjan set NOTE_PERSON=? where idn=?";
                                       int cntmj = db.execUpd("update mjan", updateFlg, ary);
                                       }
                    
                    if(!rmk.equals("")){
                                       ary=new ArrayList();
                                       ary.add(rmk);
                                       ary.add(String.valueOf(String.valueOf(MemoIdn)));
                                       String updateFlg = "update mjan set rmk=? where idn=?";
                                       int cntmj = db.execUpd("update mjan", updateFlg, ary);
                                       }
                    
                    
                    
                }
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            } finally {

            }
            
        session.setAttribute("typ",udf.getValue("MemoTyp"));
       
            finalizeObject(db, util);
        return am.findForward("pndPage");
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
        util.updAccessLog(req,res,"Memo Return ", "Create Excel");
        String mail=util.nvl(req.getParameter("mail"));
        String nmeIdn=util.nvl(req.getParameter("nmeIdn"));
        String lemon=util.nvl(req.getParameter("lemon"));
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "PacketsDtl"+util.getToDteTime()+".xls";
        ArrayList pktList = (ArrayList)session.getAttribute("pktList");
        ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
        if(!lemon.equals("")){
            itemHdr=new ArrayList();
            itemHdr.add("Description");
            itemHdr.add("SHAPE");
            itemHdr.add("COL");
            itemHdr.add("CLR");
            itemHdr.add("Type");
            itemHdr.add("Pcs");
            itemHdr.add("Carats");
            itemHdr.add("Rate");
            itemHdr.add("Est.Amount");
            itemHdr.add("MEASUREMENT");
            itemHdr.add("CERT_NO");
            itemHdr.add("ID No");
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

    public void finalizeObject(DBMgr db, DBUtil util){
        try {
            db=null;
            util=null;
        } catch (Throwable t) {
            // TODO: Add catch code
            t.printStackTrace();
        }
       
    }
    /**
     *    @param val
     *    @return
     */
}


//~ Formatted by Jindent --- http://www.jindent.com
