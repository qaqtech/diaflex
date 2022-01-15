package ft.com.marketing;

import ft.com.DBMgr;
import ft.com.DBUtil;

import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.net.InetSocketAddress;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.spy.memcached.MemcachedClient;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class StockCriteriaActionNew extends DispatchAction {
    public StockCriteriaActionNew() {
        super();
    }
    
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
        util.updAccessLog(req,res,"Stock Criteria", "load");
        StockCriteriaForm udf = (StockCriteriaForm)form;
        GenericInterface genericInt = new GenericImpl();
        HashMap dbinfo = info.getDmbsInfoLst();
        String sz = (String)dbinfo.get("SIZE");
        String type=util.nvl((String)req.getParameter("typ"));
        ArrayList srchPrpList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_stkCrtprplist") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_stkCrtprplist"); 
        info.setStkCrtprplist(srchPrpList);
        
        if(type.equals(""))
        type = util.nvl((String)udf.getValue("typ"));
        if(type.equals(""))
        type = util.nvl((String)session.getAttribute("criteriatyp"));
//        if(type.equals(""))
//        gettypeList(req,res);   
        
        udf.reset(); 
        ArrayList crtLst=getExistingCriteria(udf,req,res,type,"");    
        req.setAttribute("crtLst", crtLst);
        session.setAttribute("criteriatyp", type);
        util.getcrtwtPrp(sz,req,res); 
        udf.setValue("add","Y");
        udf.setValue("edit","N");
        udf.setValue("typ",type);
        util.updAccessLog(req,res,"Stock Criteria", "end");
        return am.findForward("load");
        }
    }
    
    public ActionForward edit(ActionMapping am, ActionForm form,
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
        util.updAccessLog(req,res,"Stock Criteria", "load");
        StockCriteriaForm udf = (StockCriteriaForm)form;
        String crtId = util.nvl((String)req.getParameter("crt_idn"));
        String type = util.nvl((String)req.getParameter("typ"));
        if(crtId.equals(""))
        crtId = util.nvl((String)udf.getValue("crt_idn"));
        if(type.equals(""))
        type = util.nvl((String)udf.getValue("typ"));
        udf.reset();
        ArrayList ary=new ArrayList();
        ArrayList crtLst=getExistingCriteria(udf,req,res,type,crtId); 
        
        for(int i=0;i<crtLst.size();i++){
            HashMap crtDtl=new HashMap();
            crtDtl=(HashMap)crtLst.get(i);
            udf.setValue("crt_idn", util.nvl((String)crtDtl.get("CRT_IDN")));
            udf.setValue("typ", util.nvl((String)crtDtl.get("TYP")));
            udf.setNmeID(Integer.parseInt(util.nvl((String)crtDtl.get("NME_IDN"))));
            udf.setValue("byr", util.nvl((String)crtDtl.get("BYR")));
            udf.setValue("dsc", util.nvl((String)crtDtl.get("DSC")));
            udf.setValue("mprp", util.nvl((String)crtDtl.get("MPRP")));
            udf.setValue("subPrp", util.nvl((String)crtDtl.get("VAL")));
            udf.setValue("subPrpTN", util.nvl((String)crtDtl.get("NUM")));
        }    
        
            String getDtls =" select a.mprp ,a.vfr , a.vto , a.sfr ,a.sto, b.dta_typ " +
                " from stk_crt_dtl a, mprp b where a.mprp = b.prp and a.crt_idn = ? ";
            ary = new ArrayList();
            ary.add(crtId);
          ArrayList outLst = db.execSqlLst(" Criteria Id :" + crtId, getDtls, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                String mprp = util.nvl(rs.getString("mprp"));
                String sfr = util.nvl(rs.getString("sfr"));
                String sto = util.nvl(rs.getString("sto"));
                String vfr = util.nvl(rs.getString("vfr"));
                String vto = util.nvl(rs.getString("vto"));
                udf.setValue(mprp + "_1", sfr);
                udf.setValue(mprp + "_2", sto);
            }
            rs.close();
            pst.close();
            String getSubDtls =" select a1.mprp, a1.sfr, a1.sto, a1.vfr, b1.srt psrt, b1.val pval " +
                " from (select a.mprp, a.sfr ,a.vfr, a.sto, decode(mprp, 'CRTWT', get_sz(sfr), a.vfr) sz, " +
                " decode(a.mprp, 'CRTWT', 'SIZE', a.mprp) prp from stk_crt_dtl_sub a where a.crt_idn = ?) a1, prp b1 " +
                " where a1.prp = b1.mprp and a1.sz = b1.val order by a1.mprp ";
            ary = new ArrayList();
            ary.add(crtId);
            String dspTxt = "", lPrp = "";
           outLst = db.execSqlLst(" SubSrch :" + crtId, getSubDtls, ary);
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                String mprp = util.nvl(rs.getString("mprp"));
                String sfr = util.nvl(rs.getString("sfr"));
                String sto = util.nvl(rs.getString("sto"));
                String vfr = util.nvl(rs.getString("vfr"));
                String psrt = util.nvl(rs.getString("psrt"));
                String pval = util.nvl(rs.getString("pval"));
                if (lPrp.equals(""))
                    lPrp = mprp;
                if (lPrp.equals(mprp)) {
                    if (mprp.equals("CRTWT"))
                        dspTxt += " , " + pval;
                    else
                        dspTxt += " , " + vfr;
                } else {
                    udf.setValue("MTXT_" + lPrp,dspTxt.substring(3).replaceAll("NA",""));
                    if (mprp.equals("CRTWT"))
                        dspTxt = " , " + pval;
                    else
                        dspTxt = " , " + vfr;
                    lPrp = mprp;
                }
                if (mprp.equals("CRTWT")) {
                    udf.setValue(mprp + "_" + psrt, pval);
                    udf.setValue(mprp + "1" + psrt, sfr);
                    udf.setValue(mprp + "2" + psrt, sto);
                } else
                    udf.setValue(mprp + "_" + vfr, vfr);
                System.out.println("mprp" + mprp + "_" + vfr);
            }
            rs.close();
            pst.close();
            if (dspTxt.length() > 3)
            udf.setValue("MTXT_" + lPrp,dspTxt.substring(3).replaceAll("NA", ""));
        
        req.setAttribute("crtLst", crtLst);
        udf.setValue("add","N");
        udf.setValue("edit","Y");
        req.setAttribute("modify", "Y");
        util.updAccessLog(req,res,"Stock Criteria", "end");
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
        util.updAccessLog(req,res,"Stock Criteria", "load");
        StockCriteriaForm udf = (StockCriteriaForm)form;
        String rtnmsg="";
        HashMap mprp = info.getMprp();
        HashMap prp= info.getPrp();
        ArrayList ary=new ArrayList();
        String addnew=util.nvl((String)udf.getValue("addnew"));
        String update=util.nvl((String)udf.getValue("update"));
        int lSrchId=0;
        int cnt = 0;
        ArrayList srchPrpLst = info.getStkCrtprplist();
        String type=util.nvl((String)udf.getValue("typ"));
        String nme_idn=util.nvl((String)String.valueOf(udf.getNmeID()),"0");
        String dsc=util.nvl((String)udf.getValue("dsc"));
        String prop=util.nvl((String)udf.getValue("mprp"));
        String prpVal =util.nvl((String)udf.getValue("subPrpTN"));
        String dta_typ = util.nvl((String)mprp.get(prop+"T"));
        if(prpVal.equals("") && dta_typ.equals("C"))
        prpVal = util.nvl((String)udf.getValue("subPrp"),"NA");
        if(!addnew.equals("")){
            String getSrchId ="STK_CRT_PKG.GET_ID(pTyp => ?,pNmeIdn =>? , pDsc => ?, pPrp => ?, pVal => ?, pMdl=> ?,pId => ?)";
            ary = new ArrayList();
            ary.add(type);
            ary.add(nme_idn);
            ary.add(dsc);
            ary.add(prop);
            ary.add(prpVal);
            ary.add("JFLEX");
            ArrayList outParams = new ArrayList();
            outParams.add("I");
            CallableStatement cst =
                db.execCall(" Search Id ", getSrchId, ary, outParams);
            try {
                lSrchId = cst.getInt(ary.size() + 1);
              cst.close();
              cst=null;
                if (lSrchId > 0)
                rtnmsg="Criteria Created Sucessfully and Criteria Idn is :-"+lSrchId;
            } catch (SQLException e) {
            }   
        }else{
            String crt_idn=util.nvl((String)udf.getValue("crt_idn"));
            if(!crt_idn.equals("")){
                String updCrt =" update stk_crt set typ=? ,dsc=?,nme_idn=?, mprp =? ,val=? where crt_idn=?";
                if(!dta_typ.equals("C"))
                updCrt =" update stk_crt set typ=? ,dsc=?,nme_idn=?, mprp =? ,num=? where crt_idn=?";
                ary = new ArrayList();
                ary.add(type);
                ary.add(dsc);
                ary.add(nme_idn);
                ary.add(prop);
                ary.add(prpVal);
                ary.add(crt_idn);
                int ct = db.execUpd("UpdateCriteria", updCrt, ary);
                
                lSrchId = Integer.parseInt(crt_idn);
                if(ct>0)
                rtnmsg="Criteria Updated Sucessfully and Criteria Idn is :-"+lSrchId;
                
                ary = new ArrayList();
                ary.add(crt_idn);
                
                String delOldCrtidsub ="delete stk_crt_dtl_sub where crt_idn=?";
                int ctv = db.execUpd("deleteCriteriadtl", delOldCrtidsub, ary);
                
                ary = new ArrayList();
                ary.add(crt_idn);
                String delOldCrtid = "delete stk_crt_dtl where crt_idn=?";
                int ctl = db.execUpd("deleteCriteria", delOldCrtid, ary);
                
            }else{
                rtnmsg="Please Provide Criteria Idn To delete";
            }   
        }
            if (lSrchId > 0) {
                String addDtl ="STK_CRT_PKG.ADD_DTL(pId =>?, pPrp =>?, pSrtFr =>?, pSrtTo =>?)";
                String addDtlSub ="STK_CRT_PKG.ADD_DTL_SUB(pId =>?, pPrp =>?, pSrtFr =>?, pSrtTo =>?)";
                String addDtlVal ="STK_CRT_PKG.ADD_DTL_VAL(pId =>?, pPrp =>?, pValFr =>?, pValTo =>?)";
                String addDtlDte ="STK_CRT_PKG.ADD_DTL_DTE(pId =>?, pPrp =>?, pValFr =>?, pValTo =>?)";
                String addDtlSubVal ="STK_CRT_PKG.ADD_DTL_SUB_VAL(pId =>?, pPrp =>?, pValFr =>?, pValTo =>?) ";
                String delCrt = "STK_CRT_PKG.DEL_CRT(pId =>?)";
                for (int i = 0; i < srchPrpLst.size(); i++) {
                    boolean dtlAddedOnce = false;
                    ArrayList srchPrp = (ArrayList)srchPrpLst.get(i);
                    String lprp = (String)srchPrp.get(0);
                    String flg = (String)srchPrp.get(1);
                    String prpSrt = lprp;
                    String lprpTyp = util.nvl((String)mprp.get(lprp + "T"));

                    if (lprp.equals("CRTWT"))
                        prpSrt = "SIZE";
                    ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
                    ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");

                    // if((buttonPressed.equalsIgnoreCase("refine")) && (lprpS!=null))
                    // flg = "M";

                    if (flg.equals("M")) { // Multiple
                        if (lprp.equalsIgnoreCase("CRTWT")) {
                            String wtFr =util.nvl((String)udf.getValue("wt_fr_c"));
                            String wtTo =util.nvl((String)udf.getValue("wt_to_c"));
                            if ((wtFr.length() > 0) && (wtTo.length() > 0)) {
                                ary = new ArrayList();
                                ary.add(String.valueOf(lSrchId));
                                ary.add(lprp);
                                ary.add(wtFr);
                                ary.add(wtTo);
                                cnt = db.execCall(" SrchDtl ", addDtl, ary);
                            } else { // to select wt from chkbx textbox
                                for (int j = 0; j < lprpS.size(); j++) {
                                    String lSrt = (String)lprpS.get(j);
                                    String reqVal1 =util.nvl((String)udf.getValue(lprp +"1" +lSrt),"");
                                    String reqVal2 =
                                        util.nvl((String)udf.getValue(lprp +"2" +lSrt),"");
                                    if ((reqVal1.length() == 0) || (reqVal2.length() == 0)) {
                                        //ignore no value selected;
                                    } else {
                                        if (!dtlAddedOnce) {
                                            ary = new ArrayList();
                                            ary.add(String.valueOf(lSrchId));
                                            ary.add(lprp);
                                            ary.add(reqVal1);
                                            ary.add(reqVal2);
                                            cnt =db.execCall(" SrchDtl ", addDtl,ary); // first valuue added to stk_crt_dtl table
                                            dtlAddedOnce = true;
                                        }
                                        ary = new ArrayList();
                                        ary.add(String.valueOf(lSrchId));
                                        ary.add(lprp);
                                        ary.add(reqVal1);
                                        ary.add(reqVal2);
                                        cnt =db.execCall(" SrchDtl ", addDtlSub,ary); // other crtwt values are added to stk_crt_dtl_sub table
                                    }
                                }
                            }
                        } else {
                            //All Other Multiple Properties

                            for (int j = 0; j < lprpS.size(); j++) {
                                String lSrt = (String)lprpS.get(j);
                                String lVal = (String)lprpV.get(j);
                                String lFld = lprp + "_" + lVal;
                                String reqVal =util.nvl((String)udf.getValue(lFld));
                                //util.SOP(lprp + " : " + lFld + " : " + reqVal);
                                if (reqVal.length() > 0) { // in shape the only first selected shape will go to this tble stk_crt_dtl
                                    if (!dtlAddedOnce) {
                                        ary = new ArrayList();
                                        ary.add(String.valueOf(lSrchId));
                                        ary.add(lprp);
                                        ary.add(reqVal);
                                        ary.add(reqVal);
                                        cnt =db.execCall(" SrchDtl ", addDtlVal, ary);
                                        dtlAddedOnce = true;
                                    }
                                    ary =
                                            new ArrayList(); // firstshape, second shape... will go to this table stk_crt_dtl_sub
                                    ary.add(String.valueOf(lSrchId));
                                    ary.add(lprp);
                                    ary.add(reqVal);
                                    ary.add(reqVal);
                                    cnt =db.execCall(" SrchDtl ", addDtlSubVal, ary);
                                }
                            }
                        } // othrt M properties
                    }

                    else { // S single properties


                        String reqVal1 =util.nvl((String)udf.getValue(lprp +"_1"), "");
                        String reqVal2 =util.nvl((String)udf.getValue(lprp + "_2"), "");
                        if (lprpTyp.equals("T"))
                            reqVal2 = reqVal1;
                        //util.SOP(lprp + " : "+ reqVal1 + " : " + reqVal2);

                        if (((reqVal1.length() == 0) ||
                             (reqVal2.length() == 0)) ||
                            ((reqVal1.equals("0")) && (reqVal2.equals("0")))) {
                            //ignore no selection;
                        } else {
                            ary = new ArrayList();
                            ary.add(String.valueOf(lSrchId));
                            ary.add(lprp);
                            ary.add(reqVal1);
                            ary.add(reqVal2);
                            if (lprpTyp.equals("T"))
                                cnt =db.execCall(" SrchDtl ", addDtlVal, ary);
                            else if (lprpTyp.equals("D"))
                                cnt =db.execCall(" SrchDtl ", addDtlDte, ary);
                            else
                                cnt = db.execCall(" SrchDtl ", addDtl, ary);
                        }
                    }

                }
                
                ary = new ArrayList();
                ary.add(String.valueOf(lSrchId));
                String updateCrt ="update stk_crt set dtl_dscr=STK_CRT_PKG.GET_CRT_DSCR(crt_idn) where crt_idn=?";
                int cti = db.execUpd("UpdateCriteria", updateCrt, ary);
                
                udf.reset();
                udf.setValue("crt_idn", String.valueOf(lSrchId));
                udf.setValue("typ", type);
            }else{
                udf.setValue("add","Y");
                udf.setValue("edit","N");
                udf.setValue("typ", type);
                rtnmsg="Error in Process";
            }
        req.setAttribute("rtnmsg", rtnmsg);
        util.updAccessLog(req,res,"Stock Criteria", "end");
        return edit(am,form,req,res);
        }
        }
    public ActionForward removeCrt(ActionMapping am, ActionForm form,
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
        util.updAccessLog(req,res,"Stock Criteria", "remove crt");
        StockCriteriaForm udf = (StockCriteriaForm)form;
        ArrayList ary = new ArrayList();
        String crtId = util.nvl((String)req.getParameter("crt_idn"));
        String type = util.nvl((String)req.getParameter("typ"));
        ary.add(crtId);
        String removeCrt =" update stk_crt set stt='IA' ,vld_dte=sysdate where crt_idn=?";
        int ct = db.execUpd("RemoveCriteria", removeCrt, ary);
        if(ct>0)
        req.setAttribute("rtnmsg", "Criteria Idn :-"+crtId+" Deleted Sucessfully");
        udf.setValue("typ", type);
        util.updAccessLog(req,res,"Stock Criteria", "end");
        return load(am,form,req,res);
        }
    }
    
    public ActionForward getPackets(ActionMapping am, ActionForm form,
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
        util.updAccessLog(req,res,"Stock Criteria", "getPackets Start");
        StockCriteriaForm udf = (StockCriteriaForm)form;
        ArrayList ary = new ArrayList();
            String crtId = util.nvl((String)req.getParameter("crt_idn"));
            GenericInterface genericInt = new GenericImpl();
            ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "STK_CTG_VW", "STK_CTG_VW");
            ArrayList itemHdr = new ArrayList();
            ArrayList prpDspBlocked = info.getPageblockList();
            ArrayList pktList = new ArrayList();
            HashMap dbinfo = info.getDmbsInfoLst();
            String delQ = " Delete from gt_srch_rslt ";
            int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
            
            if(!crtId.equals("")){
                ary = new ArrayList();
                ary.add(crtId);
                ary.add(util.nvl((String)dbinfo.get("DFLT_REL_IDN"),"0"));
                ary.add("STK_CTG_VW");
                int cnt = db.execCall(" Srch : " + crtId, "DP_STK_CRT_SRCH(pCrtIdn =>?, pRelIdn  =>?, pMdl =>?)", ary);
            }
                
            itemHdr.add("Sr No");
            itemHdr.add("vnm");
            itemHdr.add("stt");
            int counter=1;
            for(int m=0;m<vwPrpLst.size();m++){
            String lprp = (String)vwPrpLst.get(m);
            if(prpDspBlocked.contains(lprp)){
            }else{
            itemHdr.add(lprp);
            }}
            String  srchQ =  " select stk_idn , pkt_ty,  vnm, to_char(pkt_dte, 'dd-Mon-rrrr') issdte, stt , qty , cts ,img , rmk , srch_id , quot , to_char(trunc(cts,2) * quot, '99999990.00') amt  ";

            for (int i = 0; i < vwPrpLst.size(); i++) {
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

            
            String rsltQ = srchQ + " from gt_srch_rslt where flg =? order by sk1";
            
            ary = new ArrayList();
            ary.add("Z");
          ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            try {
                while(rs.next()) {

                    HashMap pktPrpMap = new HashMap();
                    pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
                    String vnm = util.nvl(rs.getString("vnm"));
                    pktPrpMap.put("vnm",vnm);
                    pktPrpMap.put("Sr No",String.valueOf(counter++));
                    pktPrpMap.put("stk_idn",util.nvl(rs.getString("stk_idn")));
                    pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
                    pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
                    pktPrpMap.put("rte", util.nvl(rs.getString("quot")));
                    pktPrpMap.put("amt", util.nvl(rs.getString("amt")));
                    for(int j=0; j < vwPrpLst.size(); j++){
                         String prp = (String)vwPrpLst.get(j);
                          
                          String fld="prp_";
                          if(j < 9)
                                  fld="prp_00"+(j+1);
                          else    
                                  fld="prp_0"+(j+1);
                          
                          String val = util.nvl(rs.getString(fld)) ;
                         
                            
                            pktPrpMap.put(prp, val);
                             }
                                  
                        pktList.add(pktPrpMap);
                    }
                rs.close();
                pst.close();
            } catch (SQLException sqle) {

                // TODO: Add catch code
                sqle.printStackTrace();
            }
            
            session.setAttribute("pktList", pktList);
            session.setAttribute("itemHdr",itemHdr);
        util.updAccessLog(req,res,"Stock Criteria", "getPackets End");
        return am.findForward("pktDtl");
        }
    }
//    public void gettypeList(HttpServletRequest req , HttpServletResponse res){
//        HttpSession session = req.getSession(false);
//        InfoMgr info = (InfoMgr)session.getAttribute("info");
//        DBUtil util = new DBUtil();
//        DBMgr db = new DBMgr();
//        db.setCon(info.getCon());
//        util.setDb(db);
//        util.setInfo(info);
//        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
//        util.setLogApplNm(info.getLoApplNm());
//        ArrayList typeList = (session.getAttribute("typeList") == null)?new ArrayList():(ArrayList)session.getAttribute("typeList");
//        try {
//        if(typeList.size() == 0) {
//            HashMap dbinfo = info.getDmbsInfoLst();
//            String cnt=util.nvl((String)dbinfo.get("CNT"));
//            String mem_ip=util.nvl((String)dbinfo.get("MEM_IP"),"13.229.255.212");
//            int mem_port=Integer.parseInt(util.nvl((String)dbinfo.get("MEM_PORT"),"11211"));
//            MemcachedClient mcc = new MemcachedClient(new InetSocketAddress(mem_ip, mem_port));
//            typeList=(ArrayList)mcc.get(cnt+"_typeList");
//            if(typeList==null){
//            typeList=new ArrayList();    
//        String sqlQ="select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and b.mdl = 'JFLEX' and b.nme_rule = 'STK_CRT_TYP' order by a.srt_fr\n";
//        ResultSet rs = db.execSql("stk_crt Result", sqlQ, new ArrayList());
//            while (rs.next()) {
//                ArrayList type = new ArrayList();
//                type.add(rs.getString("chr_fr"));
//                type.add(rs.getString("dsc"));
//                typeList.add(type);
//            }
//            rs.close();
//            Future fo = mcc.delete(cnt+"_typeList");
//            System.out.println("add status:_typeList" + fo.get());
//            fo = mcc.set(cnt+"_typeList", 24*60*60, typeList);
//            System.out.println("add status:_typeList" + fo.get());
//            }
//            mcc.shutdown();
//        session.setAttribute("typeList", typeList);
//        }
//        } catch (SQLException sqle) {
//        sqle.printStackTrace();
//            }catch(Exception ex){
//             System.out.println( ex.getMessage() );
//       }
//    }
    public ArrayList getExistingCriteria(StockCriteriaForm udf,HttpServletRequest req , HttpServletResponse res,String type,String crt_idn){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList ary=null;
        ArrayList crtLst=new ArrayList();
        HashMap crtDtl=new HashMap();
        String conQ="";
        ary=new ArrayList();
        if(!type.equals("")){
            conQ+=" and typ=?";
            ary.add(type);
        }
        
        if(!crt_idn.equals("")){
            conQ+=" and crt_idn=?";
            ary.add(crt_idn);
        }
        String sqlQ="select crt_idn,typ,nme_idn,byr.get_nm(nme_idn) byr,dsc,mprp,val,num,dtl_dscr,stt from stk_crt where vld_dte is null "+conQ+" order by dsc\n";
      ArrayList outLst = db.execSqlLst("stk_crt Result", sqlQ, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        try {
        while(rs.next()) {
            crtDtl=new HashMap();
            String crt_idnfld=util.nvl((String)rs.getString("crt_idn"));
            crtDtl.put("CRT_IDN",crt_idnfld);
            crtDtl.put("TYP",util.nvl((String)rs.getString("typ")));
            crtDtl.put("NME_IDN",util.nvl((String)rs.getString("nme_idn")));
            crtDtl.put("BYR",util.nvl((String)rs.getString("byr")));
            crtDtl.put("DSC",util.nvl((String)rs.getString("dsc")));
            crtDtl.put("MPRP",util.nvl((String)rs.getString("mprp")));
            crtDtl.put("VAL",util.nvl((String)rs.getString("val")));
            crtDtl.put("NUM",util.nvl((String)rs.getString("num")));
            crtDtl.put("DTL_DSC",util.nvl((String)rs.getString("dtl_dscr")));
            udf.setValue("STT_"+crt_idnfld,util.nvl((String)rs.getString("stt")));
            crtLst.add(crtDtl);
        }
        rs.close();
            pst.close();
        } catch (SQLException sqle) {
        sqle.printStackTrace();
        }
        return crtLst;
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
                util.updAccessLog(req,res,"Stock Criteria", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Stock Criteria", "init");
            }
            }
            return rtnPg;
            }
}
