package ft.com.mixakt;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.box.BoxReturnAction;
import ft.com.mixakt.MixReturnForm;
import ft.com.dao.ByrDao;
import ft.com.dao.MAddr;
import ft.com.dao.MNme;
import ft.com.dao.PktDtl;
import ft.com.dao.TrmsDao;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.SearchQuery;

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

public class MixReturnAction extends DispatchAction {
        public MixReturnAction() {
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
            util.updAccessLog(req,res,"Box Return", "Unauthorized Access");
            else
            util.updAccessLog(req,res,"Box Return", "init");
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
                    util.updAccessLog(req,res,"Box Return", "loadRtn start");
                MixReturnForm udf = (MixReturnForm) af;
                    udf.resetALL();
                SearchQuery query = new SearchQuery();
                ArrayList byrList = new ArrayList();
                String    getByr  =
                    "select distinct byr.get_nm(c.nme_idn) byr , c.nme_idn nme_idn" + 
                    " from mstk a, jandtl b , mjan c Where a.idn = b.mstk_idn and a.pkt_ty in ('MX','MIX','RGH') and b.stt in ('IS','AP') And b.idn=c.idn";

                 ArrayList rsLst = db.execSqlLst("byr", getByr, new ArrayList());
                  PreparedStatement pst = (PreparedStatement)rsLst.get(0);
                  ResultSet rs = (ResultSet)rsLst.get(1);
                while (rs.next()) {
                    ByrDao byr = new ByrDao();

                    byr.setByrIdn(rs.getInt("nme_idn"));
                    byr.setByrNme(rs.getString("byr"));
                    byrList.add(byr);
                }
                rs.close();
                    pst.close();
//                udf.setValue("byrList", byrList);
                udf.setByrLstFetch(byrList);
                udf.setByrLst(byrList);
                ArrayList memoList = query.getMemoType(req,res);
                udf.setMemoList(memoList);
//                udf.setValue("memoList", memoList);
                    
                                    ArrayList chargesLst=new ArrayList();

                    String chargesQ="Select ct.idn,ct.typ,ct.dsc,ct.stg optional,ct.flg,ct.fctr,ct.db_call,ct.rmk\n" + 
                    "    From Df_Pg A,Df_Pg_Itm B,Charges_Typ ct\n" + 
                    "    Where A.Idn = B.Pg_Idn\n" + 
                    "    and b.fld_nme=ct.typ\n" + 
                    "    And A.Mdl = 'BOX_MEMO_RTN'\n" + 
                    "    and b.itm_typ=?\n" + 
                    "    And A.Stt='A'\n" + 
                    "    And B.Stt='A'\n" + 
                    "    and ct.stt='A'\n" + 
                    "    And A.Vld_Dte Is Null \n" + 
                    "    And Not Exists (Select 1 From Df_Pg_Itm_Usr C Where B.Idn=C.Itm_Idn And C.Stt='IA' And C.Usr_Idn=?)\n" + 
                    "    order by b.itm_typ,b.srt";
                   ArrayList ary = new ArrayList();
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
            
                HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
                HashMap pageDtl=(HashMap)allPageDtl.get("BOX_MEMO_RTN");
                if(pageDtl==null || pageDtl.size()==0){
                pageDtl=new HashMap();
                pageDtl=util.pagedef("BOX_MEMO_RTN");
                allPageDtl.put("BOX_MEMO_RTN",pageDtl);
                }
                info.setPageDetails(allPageDtl);
                    util.updAccessLog(req,res,"Box Return", "loadRtn end");
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
                    util.updAccessLog(req,res,"Box Return", "load start");
                info.setSlPktList(new ArrayList());
                SearchQuery query = new SearchQuery();
                MixReturnForm udf = (MixReturnForm) af;
                GenericInterface genericInt=new GenericImpl(); 
                ResultSet rs = null;
                int       memoIdn = 0;
                String    view    = "NORMAL";
                String    memoIds     = "";
                String    pand        = req.getParameter("pnd");
            ArrayList pkts        = new ArrayList();
            String    memoSrchTyp = util.nvl((String) udf.getValue("memoSrch"));
            String    vnmLst      = "";
                    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
                    HashMap pageDtl=(HashMap)allPageDtl.get("BOX_MEMO_RTN");
                    if(pageDtl==null || pageDtl.size()==0){
                    pageDtl=new HashMap();
                    pageDtl=util.pagedef("BOX_MEMO_RTN");
                    allPageDtl.put("BOX_MEMO_RTN",pageDtl);
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
                udf.reset();
            }
            String app = (String)req.getAttribute("APP");
            if(app!=null)
            memoIds = util.nvl((String)req.getAttribute("memoId"));
            ArrayList params = null;
            String memoStr = "";
//                if (!vnmLst.equals("") && !vnmLst.equals("0")) {
//                   
//                    if(memoIdn==0){
//                        params = new ArrayList();
//                        params.add(vnmLst);
//                        params.add("IS");
//                        ArrayList out = new ArrayList();
//                        out.add("I");
//                        CallableStatement cst = db.execCall("findMemo","memo_pkg.find_ref_idn(pVnm => ?, pTyp =>? , pIdn => ?)", params ,out);
//                        memoIdn = cst.getInt(3);
//                        memoIds=String.valueOf(memoIdn);
//                        udf.setMemoIdn(memoIdn);
//                    }
//                    vnmLst = util.getVnm(vnmLst);
//                    if((util.nvl(udf.getSubmit()).equalsIgnoreCase("View All")) && (vnmLst.indexOf(",") == -1)){
//                        
//                        req.setAttribute("lMemoIdn", memoIds);
//                        req.setAttribute("viewAll", "yes");
//                    } else if(vnmLst.indexOf(",") > -1) {
//                   
//                    memoStr = " and  ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+") )  ";
//                    req.setAttribute("lMemoIdn", memoIds);
//                    req.setAttribute("viewAll", "no");
//                    }else{
//                       
//                        memoStr = " and ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+"))  ";
//                       
//                    }
//                       
//                }
                    
                String cur="";
                double exh_rte = 0;
                String exhRte = "select max(exh_rte) exhRte , a.nmerel_idn , b.cur " + 
                " from mjan a , nmerel b where idn in ("+memoIds+") " + 
                " and a.nmerel_idn = b.nmerel_idn  group by a.nmerel_idn, b.cur ";
                  ArrayList rsLst = db.execSqlLst(" Memo Info", exhRte , new ArrayList());
                  PreparedStatement pst = (PreparedStatement)rsLst.get(0);
                  ResultSet mrs1 = (ResultSet)rsLst.get(1);
                if(mrs1.next()){
                    exh_rte = Double.parseDouble(util.nvl(mrs1.getString("exhRte"),"1"));
                    udf.setValue("exhRte", mrs1.getString("exhRte")); 
                    cur=util.nvl(mrs1.getString("cur")).toUpperCase();
                }
                    udf.setValue("cur",cur);
                mrs1.close();
                    pst.close();
                String getAvgDis = "select sum(a.qty-nvl(a.qty_rtn,'0')) qty,sum(a.cts-nvl(a.cts_rtn,'0')) cts,trunc(sum(trunc((a.cts-nvl(a.cts_rtn,'0')),2)*nvl(a.fnl_sal, a.quot)), 2) vlu,trunc(((sum(trunc(a.cts-nvl(a.cts_rtn,'0'),2)*(nvl(a.fnl_sal, a.quot)/"+exh_rte+")) / sum(trunc(a.cts-nvl(a.cts_rtn,'0'),2)*b.rap_rte))*100) - 100, 2) avg_dis ," +
                                  " trunc(((sum(trunc(a.cts-nvl(a.cts_rtn,'0'),2)*nvl(a.fnl_sal, a.quot)) / sum(trunc(a.cts-nvl(a.cts_rtn,'0'),2))))) avg_Rte from jandtl a, mstk b " + 
                                   " where a.mstk_idn = b.idn and a.stt in ('IS','AP') " + 
                                    " and a.idn in (" + memoIds + ") ";
                if(!vnmLst.equals(""))
                getAvgDis = getAvgDis+" and  ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) ";
                params = new ArrayList();

                   rsLst = db.execSqlLst(" Memo Info", getAvgDis , new ArrayList());
                  pst = (PreparedStatement)rsLst.get(0);
                  ResultSet mrs = (ResultSet)rsLst.get(1);
                if(mrs.next()){
                    udf.setAvgDis(mrs.getString("avg_dis"));
                    udf.setAvgPrc(mrs.getString("avg_Rte"));
                    udf.setQty(mrs.getString("qty"));
                    udf.setCts(mrs.getString("cts"));
                    udf.setVlu(mrs.getString("vlu"));
                }
                mrs.close();
                    pst.close();
                String getMemoInfo =
                    " select a.nme_idn,a.rmk, a.nmerel_idn,byr.get_trms(a.nmerel_idn) trms, byr.get_nm(a.nme_idn) byr,b.trms_idn, a.typ, a.qty, a.cts, a.vlu, to_char(a.dte, 'dd-Mon-rrrr HH24:mi:ss') dte "
                    + " from mjan a , nmerel b where a.idn in (" + memoIds + ")  and a.stt <> 'RT' and a.nmerel_idn=b.nmerel_idn  ";
                  rsLst = db.execSqlLst(" Memo Info", getMemoInfo, new ArrayList());
                  pst = (PreparedStatement)rsLst.get(0);
                   mrs = (ResultSet)rsLst.get(1);

                if (mrs.next()) {
                    udf.setNmeIdn(mrs.getInt("nme_idn"));
                    udf.setRelIdn(mrs.getInt("nmerel_idn"));
                    udf.setByr(mrs.getString("byr"));
                    udf.setTyp(mrs.getString("typ"));
                    udf.setValue("trms_idn",mrs.getString("trms_idn"));
                    udf.setDte(mrs.getString("dte"));
                    udf.setValue("trmsLb", mrs.getString("trms"));
                    HashMap buyerDtlMap=util.getBuyerDtl(String.valueOf(mrs.getInt("nme_idn")));
                    udf.setValue("email",util.nvl((String)buyerDtlMap.get("EML")));
                    udf.setValue("mobile",util.nvl((String)buyerDtlMap.get("MBL")));
                    udf.setValue("office",util.nvl((String)buyerDtlMap.get("OFC")));
                    udf.setOldMemoIdn(memoIdn);
                    String rmk = util.nvl(mrs.getString("rmk"));
                    if(rmk.indexOf("@")!=-1){
                        String[] rmkLst = rmk.split("@");
                        udf.setValue("rmk1",rmkLst[0]);
                        udf.setValue("rmk2",rmkLst[1]);
                    }else
                        udf.setValue("rmk1",rmk); 
                    if (view.equalsIgnoreCase("Normal")) {
                        String getPktData =
                            " select mstk_idn, to_char(a.dte, 'dd-Mon-rrrr') dte,  (a.qty-nvl(a.qty_rtn,'0')) qty, a.qty_sal , a.qty_rtn , a.cts-nvl(a.cts_rtn,'0') cts , a.cts_sal , a.cts_rtn , b.vnm , nvl(fnl_sal, quot) memoQuot, quot, fnl_sal"
                            + ", nvl(b.upr, b.cmp) rte, nvl(b.sk1,1) sk1, b.rap_rte, a.stt,a.rmk "
                            + " , trunc(100 - ((nvl(b.upr, b.cmp)/greatest(b.rap_rte,1))*100),2) dis, trunc(100 - ((nvl(fnl_sal, quot)/greatest(b.rap_rte,1))*100),2) mDis "
                            + " from jandtl a, mstk b where a.mstk_idn = b.idn and a.stt in ('IS','AP')  ";
                       
                      
                      
                         getPktData = getPktData+" "+memoStr+" and a.idn  in (" + memoIds+ ") order by a.srl, b.sk1 ";                    
                     ArrayList rsLst1 = db.execSqlLst(" memo pkts", getPktData, new ArrayList());
                    PreparedStatement  pst1 = (PreparedStatement)rsLst1.get(0);
                    ResultSet   rs1 = (ResultSet)rsLst1.get(1);
                        while (rs1.next()) {
                            PktDtl pkt    = new PktDtl();
                            long   pktIdn = rs1.getLong("mstk_idn");
                            String mstkIdn = String.valueOf(pktIdn);
                            pkt.setPktIdn(pktIdn);
                            pkt.setRapRte(util.nvl(rs1.getString("rap_rte")));
                            pkt.setCts(util.nvl(rs1.getString("cts")));
                            pkt.setRte(util.nvl(rs1.getString("rte")));
                            pkt.setMemoQuot(util.nvl(rs1.getString("memoQuot")));
                            pkt.setByrRte(util.nvl(rs1.getString("quot")));
                            pkt.setFnlRte(util.nvl(rs1.getString("fnl_sal")));
                            pkt.setDis(rs1.getString("dis"));
                            pkt.setByrDis(rs1.getString("mDis"));
                            pkt.setVnm(util.nvl(rs1.getString("vnm")));
                            pkt.setValue("dte",util.nvl(rs1.getString("dte")));
                            pkt.setValue("cts",util.nvl(rs1.getString("cts")));
                            pkt.setValue("qty",util.nvl(rs1.getString("qty")));
                            udf.setValue("RMK_"+mstkIdn,util.nvl(rs1.getString("rmk")));
                            String lStt = rs1.getString("stt");

                            pkt.setStt(lStt);
                          

                            String getPktPrp =
                                " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
                                + " from stk_dtl a, mprp b, rep_prp c "
                                + " where a.mprp = b.prp and b.prp = c.prp and c.mdl = 'BOX_SAL_RTN' and a.grp=1 and a.mstk_idn = ? "
                                + " order by c.rnk, c.srt ";

                            params = new ArrayList();
                            params.add(Long.toString(pktIdn));

                          ArrayList rsLst2 = db.execSqlLst(" Pkt Prp", getPktPrp, params);
                          PreparedStatement  pst2 = (PreparedStatement)rsLst2.get(0);
                          ResultSet   rs2 = (ResultSet)rsLst2.get(1);
                            while (rs2.next()) {
                                String lPrp = rs2.getString("mprp");
                                String lVal = rs2.getString("val");

                                pkt.setValue(lPrp, lVal);
                            }
                            rs2.close();
                            pst2.close();
                            pkts.add(pkt);
                        }
                        rs1.close();
                        pst1.close();
                    }
                }
                mrs.close();
                    pst.close();
               
                ArrayList byrList = new ArrayList();
                String    getByr  =
                    "select nme_idn, nme  byr from nme_v a " + " where nme_idn = ? "
                    + " or exists (select 1 from nme_grp_dtl c, nme_grp b where a.nme_idn = c.nme_idn and b.nme_grp_idn = c.nme_grp_idn "
                    + " and b.nme_idn = ? and b.typ = 'BUYER' and c.vld_dte is null) " + " order by 2 ";

                ArrayList ary  = new ArrayList();
                ary.add(String.valueOf(udf.getNmeIdn()));
                ary.add(String.valueOf(udf.getNmeIdn()));
                   rsLst = db.execSqlLst("byr", getByr, ary);
                   pst = (PreparedStatement)rsLst.get(0);
                   rs = (ResultSet)rsLst.get(1);
                while (rs.next()) {
                    ByrDao byr = new ByrDao();

                    byr.setByrIdn(rs.getInt("nme_idn"));

                    String nme = util.nvl(rs.getString("byr"));

                   

                    byr.setByrNme(nme);
                    byrList.add(byr);
                }
                rs.close(); 
                    pst.close();
                
  
                    
                String brokerSql =
                    "select   aadat_idn,mbrk1_idn,mbrk2_idn,mbrk3_idn,mbrk4_idn,byr.get_nm(mbrk1_idn) brk1  , mbrk1_comm , mbrk1_paid , byr.get_nm(mbrk2_idn) brk2  ,mbrk2_comm , mbrk2_paid, byr.get_nm(mbrk3_idn) brk3  ,"
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
                    
                    udf.setValue("aadat_idn", rs.getString("aadat_idn"));
                    udf.setValue("mbrk1_idn", rs.getString("mbrk1_idn"));
                    udf.setValue("mbrk2_idn", rs.getString("mbrk2_idn"));
                    udf.setValue("mbrk3_idn", rs.getString("mbrk3_idn"));
                    udf.setValue("mbrk4_idn", rs.getString("mbrk4_idn"));
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
                String bankSql = "select nme_idn, fnme  from mnme  where 1 = 1 and vld_dte is null  and typ in('GROUP','BANK')";
                ArrayList bankList = new ArrayList();
                  rsLst = db.execSqlLst("bank Sql", bankSql, new ArrayList());
                  pst = (PreparedStatement)rsLst.get(0);
                  rs = (ResultSet)rsLst.get(1);
                while(rs.next()){
                    MAddr addr = new MAddr();
                    addr.setIdn(rs.getString("nme_idn"));
                    addr.setAddr(rs.getString("fnme"));
                    bankList.add(addr);
                }
                rs.close();
                    pst.close();
//                ArrayList groupList = new ArrayList();
//                String companyQ="select a.nme_idn, a.fnme  from mnme A , nme_dtl b where 1 = 1  " + 
//                "and a.nme_idn=b.nme_idn and  b.mprp='PERFORMA' and b.txt='YES' " + 
//                "and a.vld_dte is null  and typ = 'GROUP'";
//                rs = db.execSql("Group Sql", companyQ, new ArrayList());
//                while(rs.next()){
//                    MAddr addr = new MAddr();
//                    addr.setIdn(rs.getString("nme_idn"));
//                    addr.setAddr(rs.getString("fnme"));
//                    groupList.add(addr);
//                }
//                rs.close();
                ArrayList bnkAddrList = new ArrayList();
                    boolean dfltbankgrp=true;
                    String banknmeIdn=  "" ;
                    ary = new ArrayList();
                    ary.add(String.valueOf(udf.getNmeIdn()));
                    String setbnkCouQ="select bank_id,courier from  msal where idn in(select max(idn) from msal where nme_idn=?)";
                  rsLst = db.execSqlLst("setbnkCouQ", setbnkCouQ, ary);
                  pst = (PreparedStatement)rsLst.get(0);
                  rs = (ResultSet)rsLst.get(1);
                    if(rs.next()){
                        banknmeIdn=util.nvl(rs.getString("bank_id"));
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
//                String chargesQ="Select  c.idn,c.typ,c.dsc,c.stg optional,c.flg,c.fctr,c.db_call,c.rmk  From Rule_Dtl A, Mrule B , Charges_Typ C Where A.Rule_Idn = B.Rule_Idn And \n" + 
//                "       c.typ = a.Chr_Fr and b.mdl = 'JFLEX' and b.nme_rule = 'BOX_CHARGES' and c.stt='A' and a.til_dte is null order by a.srt_fr , a.dsc , a.chr_fr";
//                rs = db.execSql("chargesQ", chargesQ, new ArrayList());
//                while(rs.next()){
//                    HashMap dtl=new HashMap();
//                    dtl.put("idn",util.nvl(rs.getString("idn")));
//                    dtl.put("dsc",util.nvl(rs.getString("dsc")));
//                    dtl.put("autoopt",util.nvl(rs.getString("optional")));
//                    dtl.put("flg",util.nvl(rs.getString("flg")));
//                    dtl.put("typ",util.nvl(rs.getString("typ")));
//                    dtl.put("fctr",util.nvl(rs.getString("fctr")));
//                    dtl.put("fun",util.nvl(rs.getString("db_call")));
//                    dtl.put("rmk",util.nvl(rs.getString("rmk")));
//                    chargesLst.add(dtl);
//                }
//                rs.close();
                    String chargesQ="Select ct.idn,ct.typ,ct.dsc,ct.stg optional,ct.flg,ct.fctr,ct.db_call,ct.rmk\n" + 
                    "    From Df_Pg A,Df_Pg_Itm B,Charges_Typ ct\n" + 
                    "    Where A.Idn = B.Pg_Idn\n" + 
                    "    and b.fld_nme=ct.typ\n" + 
                    "    And A.Mdl = 'BOX_MEMO_RTN'\n" + 
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
//                ArrayList courierList = new ArrayList();
//                String courierQ="select chr_fr,dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and\n" + 
//                "             b.mdl = 'JFLEX' and b.nme_rule = 'COURIER' and a.til_dte is null order by a.srt_fr";
//                rs = db.execSql("courierQ", courierQ, new ArrayList());
//                while(rs.next()){
//                    MAddr addr = new MAddr();
//                    addr.setIdn(util.nvl(rs.getString("chr_fr")));
//                    addr.setAddr(util.nvl(rs.getString("dsc")));
//                    courierList.add(addr);
//                }
//                rs.close();
                ArrayList chargeLst=new ArrayList();
                String mixjan="select chr_fr, chr_to , dsc , dta_typ from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " + 
                " b.mdl = 'MIX_JAN' and b.nme_rule = 'Mix Price Jangad1' and a.til_dte is null order by a.srt_fr ";
                 rsLst = db.execSqlLst("mixjan", mixjan, new ArrayList());
                  pst = (PreparedStatement)rsLst.get(0);
                  rs = (ResultSet)rsLst.get(1);
                while(rs.next()){
                    ArrayList Lst=new ArrayList();
                    udf.setValue(util.nvl(rs.getString("chr_fr")),util.nvl(rs.getString("dsc"))); 
                    Lst.add(util.nvl(rs.getString("chr_fr")));
                    Lst.add(util.nvl(rs.getString("dsc")));
                    chargeLst.add(Lst);
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
                ArrayList courierList = query.getcourierList(req, res);
                ArrayList  trmList = query.getTerm(req,res, udf.getNmeIdn());
                ArrayList memoList = query.getMemoType(req,res);
                ArrayList groupList = query.getgroupList(req, res);
                ArrayList mtrmsList = query.getdayTerm(req, res);
                udf.setDayTermsList(mtrmsList);
                udf.setThruBankList(thruBankList);
                udf.setCourierList(courierList);
                udf.setBnkAddrList(bnkAddrList);
                udf.setGroupList(groupList);
//                udf.setValue("memoList", memoList);
                udf.setMemoList(memoList);
                udf.setBankList(bankList);
                udf.setAddrList(maddrList);
                udf.setByrLstFetch(byrList);
//                udf.setValue("byrList", byrList);
                udf.setByrLst(byrList);
                udf.setValue("byrIdn", udf.getNmeIdn());
                udf.setValue("byrTrm", udf.getRelIdn());
                udf.setValue("memoIdn", memoIds);
                req.setAttribute("memoIdn", String.valueOf(memoIds));
                udf.setByrIdn(String.valueOf(udf.getNmeIdn()));
                udf.setTrmsLst(trmList);
                udf.setPkts(pkts);
                info.setValue("PKTS", pkts);
                udf.setValue("isDLV", "Yes");
              
                udf.setPkts(pkts);
                udf.setBoxIdn(Integer.parseInt(memoIds));
                info.setValue("PKTS", pkts);
                session.setAttribute("chargeLst", chargeLst);
                info.setValue("RTRN_PKTS", pkts);
                if(pkts!=null && pkts.size()>0)
                req.setAttribute("view", "Y");
                genericInt.genericPrprVw(req, res, "BOX_SAL_RTN", "BOX_SAL_RTN");
                    
                    HashMap summaryMap =  SummeryDetail(req,memoIds);
                    req.setAttribute("SUMMRYDLT", summaryMap);
                    util.updAccessLog(req,res,"Box Return", "load end");
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
                util.updAccessLog(req,res,"Box Return", "save start");
                MixReturnForm udf = (MixReturnForm) af;
            ArrayList ary = new ArrayList();
            ArrayList pktList = (ArrayList)info.getValue("PKTS");
            String typ="SL";
            String    pktNmsSl = "";
            String isDlv = util.nvl((String)udf.getValue("isDLV"));
            String sl_typ = util.nvl((String)udf.getValue("sale_typ"));
            if(isDlv.equals("No"))
                typ="LS";
            int boxIdn = udf.getBoxIdn();
            for(int i=0;i<pktList.size();i++){
                PktDtl pktDtl = (PktDtl)pktList.get(i);
                long pktIdn = pktDtl.getPktIdn();
                String mstkIdn = String.valueOf(pktIdn);
                String isChecked = util.nvl((String)udf.getValue(mstkIdn));
                if(isChecked.equals("yes")){
                    ary = new ArrayList();
                    ary.add(String.valueOf(boxIdn));
                    ary.add(mstkIdn);
                    ary.add(util.nvl((String)udf.getValue("QR_"+mstkIdn)));
                    ary.add(util.nvl((String)udf.getValue("CR_"+mstkIdn)));
                    ary.add(util.nvl((String)udf.getValue("QS_"+mstkIdn)));
                    ary.add(util.nvl((String)udf.getValue("CS_"+mstkIdn)));
                    ary.add(util.nvl((String)udf.getValue("PRCS_"+mstkIdn)));
                    ary.add(util.nvl((String)udf.getValue("PRCR_"+mstkIdn)));
                    ary.add("RT");
                    String mixRtn ="memo_pkg.Mix_Rtn_Pkt(pMemoId =>?, pStkIdn =>?, pRtnQty => ? , pRtnCts => ? "+
                                  " , pSalQty => ?, pSalCts => ? , pSalRte => ? , pRtnRte =>  ? , pStt => ?) ";
                     int ct = db.execCall("mixRtn", mixRtn, ary);
               
                }
            }
            int appSlIdn = 0;
            for(int i=0;i<pktList.size();i++){
                PktDtl pktDtl = (PktDtl)pktList.get(i);
                long pktIdn = pktDtl.getPktIdn();
                String mstkIdn = String.valueOf(pktIdn);
                String isChecked = util.nvl((String)udf.getValue(mstkIdn));
                String salCts = util.nvl((String)udf.getValue("CS_"+mstkIdn));
                if(salCts.equals(""))
                    salCts = "0";
                double salCtsDb = Double.parseDouble(salCts);
                if(isChecked.equals("yes") && salCtsDb >0){
            if (appSlIdn == 0) {
                ary = new ArrayList();
                ary.add(Integer.toString(udf.getNmeIdn()));
                ary.add(Integer.toString(udf.getRelIdn()));
                ary.add(util.nvl((String)udf.getValue("byrTrm"),"0"));
                ary.add(util.nvl(udf.getByrIdn(),"0"));
                ary.add(util.nvl((String)udf.getValue("addr"),""));
                ary.add(typ);
                ary.add("IS");
                ary.add(Integer.toString(boxIdn));
                ary.add("NN");
                ary.add(util.nvl((String)udf.getValue("aadatpaid"), "Y"));
                ary.add(util.nvl((String)udf.getValue("brk1paid"), "Y"));
                ary.add(util.nvl((String)udf.getValue("brk2paid"), "Y"));
                ary.add(util.nvl((String)udf.getValue("brk3paid"), "Y"));
                ary.add(util.nvl((String)udf.getValue("brk4paid"), "Y"));
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
                    "sl_pkg.gen_hdr(pByrId => ?, pRlnIdn => ?, pTrmsIdn => ?, pInvNmeIdn => ?, pInvAddrIdn => ?, pTyp => ?, pStt => ?, pFrmId => ? , pFlg => ? , paadat_paid => ?, pMBRK1_PAID => ? , pMBRK2_PAID => ?, pMBRK3_PAID => ?, pMBRK4_PAID => ?, pExhRte => ?, p_bank_id => ? ,p_courier => ? ,pThruBnk => ?,co_idn => ?,pIdn => ? )", ary, out);
                appSlIdn = cst.getInt(ary.size()+1);
              cst.close();
              cst=null;
            }
            if (appSlIdn != 0) {
            ary = new ArrayList();
            ary.add("SL");
            ary.add(mstkIdn);
            ary.add(String.valueOf(boxIdn));

            int upJan = db.execUpd("updateJAN", "update jandtl set stt=? where mstk_idn=? and idn= ? ", ary);

            ary = new ArrayList();
            ary.add(String.valueOf(appSlIdn));
            ary.add(String.valueOf(boxIdn));
            ary.add(mstkIdn);
            if(typ.equals("LS"))
            ary.add("DLV");
            else
             ary.add("SL");
             ary.add(util.nvl((String)udf.getValue("RMK_"+mstkIdn)));
            int SalPkt = db.execCall("sale from memo",
                                     "sl_pkg.Mix_Sal_Pkt(pIdn => ?, pMemoIdn => ? , pStkIdn => ? , pStt => ? , pRmk => ?)",ary);

            pktNmsSl = pktNmsSl + "," + mstkIdn;
            
                String mixLog="insert into mix_trans_log(idn,dte,mstk_idn,trans_typ,qty,cts,rte,trans_qty,trans_cts,trans_rte,fnl_qty,fnl_cts,fnl_rte)" +
                    " select seq_mix_trans_log.nextval,sysdate, ? , ?, ? , ? , ? , ? , ? ,? ,? , ? ,? ,? from dual";
                ary = new ArrayList();
                ary.add(mstkIdn);
                if(typ.equals("LS"))
                ary.add("DLV");
                else
                 ary.add("SL");
                ary.add(util.nvl(req.getParameter("qty_"+mstkIdn)));
                ary.add(util.nvl(req.getParameter("cts_"+mstkIdn)));
                ary.add(util.nvl(req.getParameter("prc_"+mstkIdn)));
                ary.add(util.nvl((String)udf.getValue("QS_"+mstkIdn)));
                ary.add(util.nvl((String)udf.getValue("CS_"+mstkIdn)));
                ary.add(util.nvl((String)udf.getValue("PRCS_"+mstkIdn)));
                ary.add(util.nvl((String)udf.getValue("QR_"+mstkIdn)));
                ary.add(util.nvl((String)udf.getValue("CR_"+mstkIdn)));
                ary.add(util.nvl((String)udf.getValue("PRCR_"+mstkIdn)));
                
                db.execUpd("mix_log", mixLog, ary);
                
            }
            }
            }
            String updJanQty = " jan_qty(?) ";

            ary = new ArrayList();
            ary.add(String.valueOf(boxIdn));
            int ct1 = db.execCall("JanQty", updJanQty, ary);
            req.setAttribute("salId", String.valueOf(appSlIdn));
                   if(appSlIdn!=0){
            ary = new ArrayList();
            ary.add(String.valueOf(appSlIdn));
            int ctq = db.execCall("sl_pkg.upd_qty", "sl_pkg.UPD_QTY(pIdn => ?)", ary);
         
                String fnlexhRte=util.nvl((String)udf.getValue("fnlexhRte"));
                if(!fnlexhRte.equals("")){
                    ary = new ArrayList();
                    ary.add(fnlexhRte);
                    ary.add(String.valueOf(appSlIdn));
                    int upFnlExh = db.execUpd("upFnlExhRTE", "update msal set fnl_exh_rte=? where idn= ? ", ary);
                }
                
                if(!sl_typ.equals("") && !sl_typ.equals("0")){
                    ary = new ArrayList();
                    ary.add(sl_typ);
                    ary.add(String.valueOf(appSlIdn));
                    int sl_typcnt = db.execUpd("sl_typ", "update msal set inv_typ=? where idn= ? ", ary);
                }
                req.setAttribute("msg", "Sale Idn : "+appSlIdn);
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
                String autooptional= util.nvl((String)udf.getValue(typcharge+"_AUTOOPT"));  
                String calcdis= util.nvl((String)udf.getValue(typcharge+"_save"),"0"); 
                String extrarmk= util.nvl((String)udf.getValue(typcharge+"_rmksave"));    
                String vlu= util.nvl((String)udf.getValue("vluamt"));
                String exhRte=util.nvl((String)udf.getValue("exhRte"));
                    if(!vlu.equals("") && !vlu.equals("0")  && !calcdis.equals("NaN")  && !vlu.equals("NaN")){
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
                int ct = db.execUpd("Insert", insertQ, ary);
                }
                }
                }
                }
                pktNmsSl=pktNmsSl.replaceFirst("\\,", "");
                req.setAttribute("saleId", String.valueOf(appSlIdn));
                req.setAttribute("performaLink","Y");
//                req.setAttribute("performaLink", info.getReqUrl()+"/marketing/performa.do?method=perInv&idn="+appSlIdn+"&byrIdn="+util.nvl((String)udf.getByrIdn())
//                                                 +"&byrAddIdn="+util.nvl((String)udf.getValue("addr"))+"&bankIdn="+util.nvl((String)udf.getValue("bankIdn"))+
//                                                 "&bankAddIdn="+util.nvl((String)udf.getValue("bankAddr"))+"&perInvIdn="+util.nvl(pktNmsSl)
//                                                 +"&relIdn="+util.nvl((String)udf.getValue("byrTrm"))+"&typ=BOX&pktTyp=MIX"+"&echarge="+util.nvl((String)req.getParameter("echarge"))+"&grpIdn="+util.nvl((String)udf.getValue("grpIdn"))
//                                                 +"&courier="+util.nvl((String)udf.getValue("courier")));
             
                ary=new ArrayList();
                ary.add(String.valueOf(appSlIdn));
              int calCt = db.execCall("calQuot", "SL_PKG.Cal_Fnl_Quot(pIdn => ?)", ary);
          
               }else
            req.setAttribute("msg", "Packets take return.");
            int accessidn=util.updAccessLog(req,res,"Box Return", "save end");
            req.setAttribute("accessidn", String.valueOf(accessidn));;
            return loadRtn(am,af,req,res);
            }
        }


        public ActionForward saveNW(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
                util.updAccessLog(req,res,"Box Return", "save start");
                MixReturnForm udf = (MixReturnForm) af;
            ArrayList ary = new ArrayList();
           ArrayList mstkIdnLst = new ArrayList();
            String typ="SL";
            String    pktNmsSl = "";
            String localSale = util.nvl((String)udf.getValue("localSale"));
            String isDlv = util.nvl((String)udf.getValue("dlv"));
                
                HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
                HashMap pageDtl=(HashMap)allPageDtl.get("BOX_MEMO_RTN");
                if(pageDtl==null || pageDtl.size()==0){
                pageDtl=new HashMap();
                pageDtl=util.pagedef("BOX_MEMO_RTN");
                allPageDtl.put("BOX_MEMO_RTN",pageDtl);
                }
                ArrayList pageList=new ArrayList();
                HashMap pageDtlMap=new HashMap();
                String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
            if(!localSale.equals(""))
                typ="LS";
            int boxIdn = udf.getBoxIdn();
             Enumeration reqNme  = req.getParameterNames();
               while (reqNme.hasMoreElements()) {
                    String paramNm = (String) reqNme.nextElement();

                    if (paramNm.indexOf("CHK_") > -1) {
                        String val = req.getParameter(paramNm);
                     mstkIdnLst.add(val);
                    }
                }
              String savermk = util.nvl((String)udf.getValue("savermk"));
                String cancel = util.nvl((String)udf.getValue("cancel"));
              String rmk1 = util.nvl((String)udf.getValue("rmk1"));
              String rmk2 = util.nvl((String)udf.getValue("rmk2"));
              String fnlexhRte=util.nvl((String)udf.getValue("fnlexhRte"));
              if(!rmk2.equals(""))
              rmk1=rmk1+"@"+rmk2;
                
                String mnl_fields="N";
                pageList=(ArrayList)pageDtl.get("MNL_FIELDS");
                if(pageList!=null && pageList.size() >0){
                for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                dflt_val=(String)pageDtlMap.get("dflt_val");
                if(dflt_val.equals("Y")){
                mnl_fields="Y";
                }
                }
                }  
            try {
                String rtnmsg="";
                if(savermk.equals("")){
             db.setAutoCommit(false);
                int appSlIdn=0;
                double ttlRtnCts=0;
                double ttlCnlCts=0;
                double ttlSalCts=0;
                
                for (int i = 0; i < mstkIdnLst.size(); i++) {
                String mstkIdn = (String)mstkIdnLst.get(i);
               
             
                   
                 
                    for(int j=1;j <=5;j++){
                    String slQty = util.nvl((String)udf.getValue("QTY_"+mstkIdn+"_"+j));
                    String slCts = util.nvl((String)udf.getValue("CTS_"+mstkIdn+"_"+j));
                    String slPrc = util.nvl((String)udf.getValue("PRC_"+mstkIdn+"_"+j));
                    String slrmk = util.nvl((String)udf.getValue("RMK_"+mstkIdn+"_"+j));
                   if(!slCts.equals("") && !slPrc.equals("")){
                        if (appSlIdn == 0) {
                        ary = new ArrayList();
                        ary.add(Integer.toString(udf.getNmeIdn()));
                        ary.add(Integer.toString(udf.getRelIdn()));
                        ary.add(util.nvl((String)udf.getValue("byrTrm"),"0"));
                        ary.add(util.nvl(udf.getByrIdn(),"0"));
                        ary.add(util.nvl((String)udf.getValue("addr"),""));
                        ary.add(typ);
                        ary.add("IS");
                        ary.add(Integer.toString(boxIdn));
                        ary.add("NN");
                        ary.add(util.nvl((String)udf.getValue("aadatpaid"), "Y"));
                        ary.add(util.nvl((String)udf.getValue("brk1paid"), "Y"));
                        ary.add(util.nvl((String)udf.getValue("brk2paid"), "Y"));
                        ary.add(util.nvl((String)udf.getValue("brk3paid"), "Y"));
                        ary.add(util.nvl((String)udf.getValue("brk4paid"), "Y"));
                            ary.add(util.nvl((String)udf.getValue("aadatcomm")));
                            ary.add(util.nvl((String)udf.getValue("brk1comm")));
                            ary.add(util.nvl((String)udf.getValue("brk2comm")));
                            ary.add(util.nvl((String)udf.getValue("brk3comm")));
                            ary.add(util.nvl((String)udf.getValue("brk4comm")));
                        ary.add(util.nvl((String)udf.getValue("exhRte")));
                        ary.add(util.nvl((String) udf.getValue("bankIdn")));
                        ary.add(util.nvl((String) udf.getValue("courier")));
                        ary.add(util.nvl((String)udf.getValue("throubnk")));
                        ary.add(util.nvl((String)udf.getValue("grpIdn")));
                        ary.add(util.nvl((String)udf.getValue("trms_idn")));
                        ArrayList out = new ArrayList();

                        out.add("I");

                        CallableStatement cst = null;

                        cst = db.execCall(
                            "MKE_HDR ",
                            "sl_pkg.gen_hdr(pByrId => ?, pRlnIdn => ?, pTrmsIdn => ?, pInvNmeIdn => ?, pInvAddrIdn => ?, pTyp => ?, pStt => ?, pFrmId => ? , pFlg => ? , paadat_paid => ?, pMBRK1_PAID => ? , pMBRK2_PAID => ?, pMBRK3_PAID => ?, pMBRK4_PAID => ?," +
                            " paadat_comm => ?, pMBRK1_comm => ?, pMBRK2_comm => ?, pMBRK3_comm => ? , pMBRK4_comm => ? , "+
                            " pExhRte => ?, p_bank_id => ? ,p_courier => ? ,pThruBnk => ?,co_idn => ?,daytrms_idn => ? ,pIdn => ? )", ary, out);
                        appSlIdn = cst.getInt(ary.size()+1);
                          cst.close();
                          cst=null;
                        if(mnl_fields.equals("Y")){
                            String updslQ="update msal set AADAT_IDN=?,MBRK1_IDN=?,MBRK2_IDN=?,MBRK3_IDN=?,MBRK4_IDN=?,AADAT_COMM=?,BRK1_COMM=?,BRK2_COMM=?,BRK3_COMM=?,BRK4_COMM=?,AADAT_PAID=?,MBRK1_PAID=?,MBRK2_PAID=?,MBRK3_PAID=?,MBRK4_PAID=? where idn=?";
                            ary = new ArrayList();
                            ary.add(util.nvl((String)udf.getValue("aadat_idn")));
                            ary.add(util.nvl((String)udf.getValue("mbrk1_idn")));
                            ary.add(util.nvl((String)udf.getValue("mbrk2_idn")));
                            ary.add(util.nvl((String)udf.getValue("mbrk3_idn")));
                            ary.add(util.nvl((String)udf.getValue("mbrk4_idn")));
                            
                            ary.add(util.nvl((String)udf.getValue("aadatcomm")));
                            ary.add(util.nvl((String)udf.getValue("brk1comm")));
                            ary.add(util.nvl((String)udf.getValue("brk2comm")));
                            ary.add(util.nvl((String)udf.getValue("brk3comm")));
                            ary.add(util.nvl((String)udf.getValue("brk4comm")));
                            
                            ary.add(util.nvl((String)udf.getValue("aadatpaid")));
                            ary.add(util.nvl((String)udf.getValue("brk1paid")));
                            ary.add(util.nvl((String)udf.getValue("brk2paid")));
                            ary.add(util.nvl((String)udf.getValue("brk3paid")));
                            ary.add(util.nvl((String)udf.getValue("brk4paid")));
                            ary.add(String.valueOf(appSlIdn));
                            int cntmj = db.execUpd("update msal", updslQ, ary);
                        }
                            if(!fnlexhRte.equals("")){
                                ary = new ArrayList();
                                ary.add(fnlexhRte);
                                ary.add(String.valueOf(appSlIdn));
                                int upFnlExh = db.execUpd("upFnlExhRTE", "update msal set fnl_exh_rte=? where idn= ? ", ary);
                            }
                        }
                      
                          
                        String mixRtn ="mix_memo_pkg.part_app_pkt(pMemoIdn => ?, pSalIdn => ?, pRtPkt => ? , pQty => ? "+
                                      " , pCts => ?, pRte => ? ,pRmk => ? , pJanStt => ?, pStkIdn =>  ? ,pMsg => ? ) ";
                         ary = new ArrayList();
                        ary.add(String.valueOf(boxIdn));
                        ary.add(String.valueOf(appSlIdn));
                        ary.add(mstkIdn);
                        ary.add(slQty);
                        ary.add(slCts);
                        ary.add(slPrc);
                        ary.add(slrmk);
                        ary.add("SL");
                       ArrayList out = new ArrayList();
                        out.add("I");
                        out.add("V");
                        CallableStatement ct = db.execCall("mixRtn", mixRtn, ary,out);
                         ttlSalCts=ttlSalCts+Double.parseDouble(slCts);
                        }
                    }
                
                        String rtnCts = util.nvl((String)udf.getValue("CR_"+mstkIdn));

                    if(!rtnCts.equals("") && !rtnCts.equals("0")){
                                   ary = new ArrayList();
                                   ary.add(String.valueOf(boxIdn));
                                   ary.add(mstkIdn);
                                   ary.add(util.nvl((String)udf.getValue("QR_"+mstkIdn)));
                                   ary.add(util.nvl((String)udf.getValue("CR_"+mstkIdn)));
                                   ary.add("0");
                                   ary.add("0");
                                   ary.add("0");
                                   ary.add(util.nvl((String)udf.getValue("PRCR_"+mstkIdn)));
                                   String mixRtn ="memo_pkg.Mix_Rtn_Pkt(pMemoId =>?, pStkIdn =>?, pRtnQty => ? , pRtnCts => ? "+
                                                 " , pSalQty => ?, pSalCts => ? , pSalRte => ? , pRtnRte =>  ? ) ";
                                    int ct = db.execCall("mixRtn", mixRtn, ary);
                                ttlRtnCts=ttlRtnCts+Double.parseDouble(rtnCts);
                     }
                        
                    if(!cancel.equals("")){
                        ary = new ArrayList();
                        ary.add(String.valueOf(boxIdn));
                        ary.add(mstkIdn);
                       String mixRtn ="memo_pkg.Cancel_ISPkt(pIdn =>?, pStkIdn =>? ) ";
                         int ct = db.execCall("mixRtn", mixRtn, ary);
                        ttlCnlCts=ttlCnlCts+1;
                        
                    }
                        ary = new ArrayList();
                        ary.add(String.valueOf(boxIdn));
                        ary.add(String.valueOf(mstkIdn));
                        int ctq = db.execCall("mix_memo_pkg.jan_stt", "mix_memo_pkg.jan_stt(pMemoIdn => ?, pStkIdn => ?)", ary);
                        
                    
                }  
                
                
                int appDlvIdn=0;
                if(appSlIdn!=0){
                    String calQuot = "mix_memo_pkg.Cal_Fnl_Quot(pIdn => ?) ";
                    ary = new ArrayList();
                    ary.add(String.valueOf(appSlIdn));
                   int ct1 = db.execCall("calQuot", calQuot, ary);
                   
                    ary = new ArrayList();
                    ary.add(String.valueOf(appSlIdn));

                    int upQuery = db.execCall("calQuot", "SL_PKG.UPD_QTY(pIdn => ? )", ary);
                  if(!rmk1.equals("")){
                    ary = new ArrayList();
                    ary.add(rmk1);
                    ary.add(String.valueOf(appSlIdn));
                    String updateFlg = "update msal set rmk=? where idn=?";
                    int cntmj = db.execUpd("update msal", updateFlg, ary);
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
                    String autooptional= util.nvl((String)udf.getValue(typcharge+"_AUTOOPT"));  
                    String calcdis= util.nvl((String)udf.getValue(typcharge+"_save"),"0"); 
                    String extrarmk= util.nvl((String)udf.getValue(typcharge+"_rmksave"));    
                    String vlu= util.nvl((String)udf.getValue("vluamt"));
                    String exhRte=util.nvl((String)udf.getValue("exhRte"));
                        if(vlu.equalsIgnoreCase("NaN"))
                            vlu="0";
                    if(!vlu.equals("")){
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
                    int ct = db.execUpd("Insert", insertQ, ary);
                    }
                    }
                    }
                    }
                    if(!isDlv.equals("")){
                        if (appSlIdn != 0) {
                            ary = new ArrayList();
                            ary.add(String.valueOf(appSlIdn));
                            ary.add("DLV");
                            ary.add("MIX");
                           ArrayList out = new ArrayList();
                            out.add("I");
                           CallableStatement cst  = db.execCall("delivery Confirm", "DLV_PKG.Gen_Hdr_Frm_Sale(pSaleIdn =>?, pTyp => ?,pkt_ty => ? , pIdn => ? )", ary , out);
                            appDlvIdn = cst.getInt(ary.size()+1);
                          cst.close();
                          cst=null;
                            
                            if(mnl_fields.equals("Y")){
                                String updslQ="update mdlv set AADAT_IDN=?,MBRK1_IDN=?,MBRK2_IDN=?,MBRK3_IDN=?,MBRK4_IDN=?,AADAT_COMM=?,BRK1_COMM=?,BRK2_COMM=?,BRK3_COMM=?,BRK4_COMM=?,AADAT_PAID=?,MBRK1_PAID=?,MBRK2_PAID=?,MBRK3_PAID=?,MBRK4_PAID=? where idn=?";
                                ary = new ArrayList();
                                ary.add(util.nvl((String)udf.getValue("aadat_idn")));
                                ary.add(util.nvl((String)udf.getValue("mbrk1_idn")));
                                ary.add(util.nvl((String)udf.getValue("mbrk2_idn")));
                                ary.add(util.nvl((String)udf.getValue("mbrk3_idn")));
                                ary.add(util.nvl((String)udf.getValue("mbrk4_idn")));
                                
                                ary.add(util.nvl((String)udf.getValue("aadatcomm")));
                                ary.add(util.nvl((String)udf.getValue("brk1comm")));
                                ary.add(util.nvl((String)udf.getValue("brk2comm")));
                                ary.add(util.nvl((String)udf.getValue("brk3comm")));
                                ary.add(util.nvl((String)udf.getValue("brk4comm")));
                                
                                ary.add(util.nvl((String)udf.getValue("aadatpaid"), "Y"));
                                ary.add(util.nvl((String)udf.getValue("brk1paid"), "Y"));
                                ary.add(util.nvl((String)udf.getValue("brk2paid"), "Y"));
                                ary.add(util.nvl((String)udf.getValue("brk3paid"), "Y"));
                                ary.add(util.nvl((String)udf.getValue("brk4paid"), "Y"));
                                ary.add(String.valueOf(appDlvIdn));
                                int cntmj = db.execUpd("update msal", updslQ, ary);
                            }
                            if(!fnlexhRte.equals("")){
                                ary = new ArrayList();
                                ary.add(fnlexhRte);
                                ary.add(String.valueOf(appDlvIdn));
                                int upFnlExh = db.execUpd("upFnlExhRTE", "update mdlv set fnl_exh_rte=? where idn= ? ", ary);
                            }
                            rtnmsg= rtnmsg+"&nbsp;&nbsp;  Delivery Id : "+appDlvIdn;
                        }
                        
                        
                        
                    }
                    
                }
                    if(ttlRtnCts!=0) 
                        rtnmsg= rtnmsg+"&nbsp;&nbsp; Total Carat return successfully :-"+ttlRtnCts;
                    if(ttlCnlCts > 0)
                        rtnmsg= rtnmsg+"&nbsp;&nbsp; Total Packets Cancel :-"+ttlCnlCts;
                    if(ttlSalCts!=0) 
                        rtnmsg= rtnmsg+"&nbsp;&nbsp;  Total Carat Sale successfully :-"+ttlSalCts+ ";&nbsp;&nbsp;  SALE ID"+appSlIdn;
                    
                    if(appDlvIdn > 0)
                        rtnmsg= rtnmsg+"&nbsp;&nbsp;  Delivery ID "+appDlvIdn;
                }

                if(!rmk1.equals("")){
                ary = new ArrayList();
                ary.add(rmk1);
                ary.add(Integer.toString(boxIdn));
                String updateFlg = "update mjan set rmk=? where idn=?";
                int cntmj = db.execUpd("update mjan", updateFlg, ary);
                if(cntmj>0)
                    rtnmsg= rtnmsg+"&nbsp;&nbsp; ,  Remark Save successfully";
                else
                    rtnmsg=rtnmsg+"&nbsp;&nbsp;  ,Error in Remark Save";
                }
              
               
                req.setAttribute("rtnMsg", rtnmsg);
            
            } catch (Exception e) {
                // TODO: Add catch code
                e.printStackTrace();
                req.setAttribute("rtnMsg", "Some Error in process..");
                db.doRollBack();
            } finally {
                db.setAutoCommit(true);
            }
            udf.resetALL();
            return loadRtn(am,af,req,res);
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
                util.updAccessLog(req,res,"Box Return", "merge start");
                MixReturnForm udf = (MixReturnForm) af;

            ArrayList    ary      = null;
            String    nmeIdn   = req.getParameter("nmeIdn");
            String typ = util.nvl(req.getParameter("typ"));
            String str = " a.typ like '%AP'";
            if(typ.equals("IS")){
                str = " a.typ in ('I', 'E', 'WH','K','H','BID','O')";
            }else if(typ.equals("WEB")){
                str = " a.typ in ('WA') ";
            }else{
                
            }
            ArrayList trmList  = new ArrayList();
            String    sqlMerge = "select distinct a.nmerel_idn, b.trm||' '||b.rln terms from "
                                 + "mjan a, cus_rel_v b where 1 = 1 " + "and a.nmerel_idn = b.rel_idn "
                                 + "and a.nme_idn = ? and a.stt = 'IS' and "+str;

            ary = new ArrayList();
            ary.add(nmeIdn);

              ArrayList rsLst = db.execSqlLst("trms", sqlMerge, ary);
              PreparedStatement pst = (PreparedStatement)rsLst.get(0);
              ResultSet rs = (ResultSet)rsLst.get(1);
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
                util.updAccessLog(req,res,"Box Return", "merge end");
            return am.findForward("merge");
            }
        }
        public HashMap SummeryDetail(HttpServletRequest req,String memoId){
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            HashMap summaryMap = new HashMap();
        try {
           
            ArrayList summryList = new ArrayList();
            String dataWiseSummry = "select box.val box, id.val boxId\n" +
                " , jd.dte, jd.qty_sal, jd.cts_sal, nvl(jd.fnl_sal, jd.quot) rte, trunc(jd.cts_sal*nvl(jd.fnl_sal, jd.quot), 2) vlu\n" +
                "from stk_dtl box, stk_dtl id, jandtl jd\n" +
                "where 1 = 1 \n" +
                "and jd.idn = ? and nvl(jd.cts_sal, 0) > 0\n" +
                "and jd.mstk_idn = box.mstk_idn and box.grp = 1 and box.mprp = 'BOX_TYP'\n" +
                "and jd.mstk_idn = id.mstk_idn and id.grp = 1 and id.mprp = 'BOX_ID'\n" +
                "order by  id.srt ";
            ArrayList ary = new ArrayList();
            ary.add(memoId);
            ArrayList rsLst = db.execSqlLst("dataWiseSummry", dataWiseSummry, ary);
            PreparedStatement ps = (PreparedStatement)rsLst.get(0);
            ResultSet rs = (ResultSet)rsLst.get(1);
            while (rs.next()) {
             HashMap pktMap = new HashMap();
             pktMap.put("BOX", rs.getString("box"));
             pktMap.put("BOXID", rs.getString("boxId"));
             pktMap.put("DTE", rs.getString("dte"));
             pktMap.put("QTYSAL", rs.getString("qty_sal"));
             pktMap.put("CTSSAL", rs.getString("cts_sal"));
             pktMap.put("RTE", rs.getString("rte"));
             pktMap.put("VLU", rs.getString("vlu"));
            summryList.add(pktMap);
            }
            rs.close();
            ps.close();
            summaryMap.put("DTESMMY", summryList);
            
            ArrayList ttlSummryList = new ArrayList();
            String ttlSummry = "select box.val box, id.val boxId ,id.srt" + 
            " , sum(nvl(qty, 0)) qty, sum(nvl(qty-nvl(qty_rtn, 0)-nvl(qty_sal, 0), 0)) curqty,sum(nvl(qty_rtn, 0)) rtnQty, sum(nvl(qty_sal, 0)) salQty\n" + 
            " , trunc(sum(nvl(cts, 0)),2) cts,  trunc(sum(nvl(cts-nvl(cts_rtn, 0)-nvl(cts_sal, 0), 0)),2) curcts ,\n" + 
            " trunc(sum(nvl(cts_rtn, 0)),2) rtnCts, trunc(sum(nvl(cts_sal, 0)),2) salCts\n" + 
            "from stk_dtl box, stk_dtl id, jandtl jd\n" + 
            "where 1 = 1 \n" + 
            "and jd.idn = ? \n" + 
            "and jd.mstk_idn = box.mstk_idn and box.grp = 1 and box.mprp = 'BOX_TYP'\n" + 
            "and jd.mstk_idn = id.mstk_idn and id.grp = 1 and id.mprp = 'BOX_ID'\n" + 
            "group by box.val, id.val,id.srt order by  id.srt ";
             ary = new ArrayList();
            ary.add(memoId);
            rsLst = db.execSqlLst("dataWiseSummry", ttlSummry,ary);
            ps = (PreparedStatement)rsLst.get(0);
            rs = (ResultSet)rsLst.get(1);
            while (rs.next()) {
             HashMap pktMap = new HashMap();
             pktMap.put("BOX", rs.getString("box"));
             pktMap.put("BOXID", rs.getString("boxId"));
             pktMap.put("QTY", rs.getString("qty"));
             pktMap.put("CURQTY", rs.getString("curqty"));
             pktMap.put("SALQTY", rs.getString("salQty"));
             pktMap.put("RTNQTY", rs.getString("rtnQty"));
             pktMap.put("CTS", rs.getString("cts"));
             pktMap.put("SALCTS", rs.getString("salCts"));
             pktMap.put("CURCTS", rs.getString("curcts"));
             pktMap.put("RTNCTS", rs.getString("rtnCts"));
            ttlSummryList.add(pktMap);
            }
            rs.close();
            ps.close();
            summaryMap.put("TTLSMMY", ttlSummryList);
            
            

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
           
           return  summaryMap;
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
                util.updAccessLog(req,res,"Box Return", "mergeMemo start");
                MixReturnForm udf = (MixReturnForm) af;

            ArrayList      ary     = new ArrayList();
            String      memoIds = "";
            String      nmeIdn  = (String) udf.getValue("nmeIdn");
            Enumeration reqNme  = req.getParameterNames();

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

            ary.add(nmeIdn);
            ary.add(String.valueOf(memoIds));

            ArrayList out = new ArrayList();

            out.add("I");

            CallableStatement cst = null;

            cst = db.execCall("MKE_HDR ", "memo_pkg.Merge_Memo(pNmeIdn => ?, pIds => ?,  pMemoIdn => ?)", ary, out);

            int MemoIdn = cst.getInt(3);
              cst.close();
              cst=null;
                util.updAccessLog(req,res,"Box Return", "mergeMemo end");
            return am.findForward("pndPage");
            }
        }
    }


    //~ Formatted by Jindent --- http://www.jindent.com

  

