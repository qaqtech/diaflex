package ft.com.box;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.generic.GenericImpl;
import ft.com.marketing.SearchQuery;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;


public class BoxCriteriaAction extends DispatchAction {
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
            util.updAccessLog(req,res,"Box Criteria", "load start");
        BoxCriteriaForm boxCrtForm = (BoxCriteriaForm)form;
        boxCrtForm.reset();
            HashMap dbinfo = info.getDmbsInfoLst();
            String sz = (String)dbinfo.get("SIZE");
        ArrayList srchPrpList = new ArrayList();
        ArrayList prptable = new ArrayList();
        ArrayList typeList = new ArrayList();
        /*
* To load properties
**/
        String loadPrp =
            "select prp,flg from rep_prp where mdl = 'MEMO_CRT' and flg in('M','S') order by rnk";
        ResultSet rs = db.execSql(" loadPrpLst ", loadPrp, new ArrayList());
        while (rs.next()) {
            ArrayList prpList = new ArrayList();
            prpList.add(rs.getString("prp"));
            prpList.add(rs.getString("flg"));
            srchPrpList.add(prpList);
        }
        rs.close();
        // Properties and their flag in srchPrpList
        session.setAttribute("stkCrtprplist", srchPrpList);
        info.setStkCrtprplist(srchPrpList);
        // To load dropdown properties .only type C

        String dropProp =
            "select prp,dsc, srt, nvl(prt_nme, prp) prt, dta_typ from mprp where 1 = 1" +
            "and trunc(nvl(vld_till, sysdate)) <= trunc(sysdate) and dta_typ='C'" +
            "order by srt";
        rs = db.execSql(" dropDownPrpLst ", dropProp, new ArrayList());
        while (rs.next()) {
            ArrayList dropprp = new ArrayList();
            dropprp.add(rs.getString("dsc"));
            dropprp.add(rs.getString("prp"));
            prptable.add(dropprp);
        }
        session.setAttribute("prpTable", prptable);
        rs.close();
        /*
* type dropdown
*/
//        String typeqry =
//            "select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and\n" +
//            "b.mdl = 'JFLEX' and b.nme_rule = 'STK_CRT_TYP' order by a.srt_fr";
//        rs = db.execSql(" loadType ", typeqry, new ArrayList());
//        while (rs.next()) {
//            ArrayList type = new ArrayList();
//            type.add(rs.getString("chr_fr"));
//            type.add(rs.getString("dsc"));
//            typeList.add(type);
//        }
//        rs.close();
//        session.setAttribute("typeList", typeList);
        /*
* load Existing Criteria
*/
        ArrayList exsCrtra = new ArrayList();
        String exsCrt =
            "select crt_idn,typ||'-'||dsc exsDes from stk_crt where stt='A' and vld_dte is null";
        rs = db.execSql(" ExistingCriteria ", exsCrt, new ArrayList());
        while (rs.next()) {
            ArrayList crta = new ArrayList();
            crta.add(rs.getString("crt_idn"));
            crta.add(rs.getString("exsDes"));
            exsCrtra.add(crta);
        }
        rs.close();
        session.setAttribute("exsCrtra", exsCrtra);
            util.getcrtwtPrp(sz,req,res);
            util.updAccessLog(req,res,"Box Criteria", "load end");
        return am.findForward("load");
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
            util.updAccessLog(req,res,"Box Criteria", "removeCrt start");
        BoxCriteriaForm boxCrtForm = (BoxCriteriaForm)form;
        ArrayList ary = new ArrayList();

        String crtId = (String)boxCrtForm.getValue("exiCrt");
        ary.add(crtId);

        String removeCrt =
            " update stk_crt set stt='IA' ,vld_dte=sysdate where crt_idn=?";

        int ct = db.execUpd("RemoveCriteria", removeCrt, ary);
            util.updAccessLog(req,res,"Box Criteria", "removeCrt end");
        return am.findForward("load");
        }
    }

    public ActionForward loadCrt(ActionMapping am, ActionForm form,
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
            util.updAccessLog(req,res,"Box Criteria", "loadCrt start");
        BoxCriteriaForm boxCrtForm = (BoxCriteriaForm)form;

        HashMap prptb = info.getPrp();
        String crtId = (String)boxCrtForm.getValue("exiCrt");
        String type = (String)boxCrtForm.getValue("type");
        String desc = (String)boxCrtForm.getValue("dmdDsc");
        String mprpVal = (String)boxCrtForm.getValue("prp");
        String prp = (String)boxCrtForm.getValue("subPrp");
        String load = "Y";
        req.setAttribute("load", load);
        req.setAttribute("property", mprpVal);
        req.setAttribute("propValue", prp);
        int nmeID = boxCrtForm.getNmeID();
        boxCrtForm.reset();
        boxCrtForm.setValue("exiCrt", crtId);
        /* set crt id to hidden fld for Update criteria */
        boxCrtForm.setValue("oldCrtId", crtId);
        boxCrtForm.setValue("type", type);
        boxCrtForm.setValue("dmdDsc", desc);
        boxCrtForm.setValue("prp", mprpVal);


        boxCrtForm.setValue("subPrp", prp);
        boxCrtForm.setNmeID(nmeID);

        ArrayList ary = new ArrayList();


        String getDtls =
            " select a.mprp ,a.vfr , a.vto , a.sfr ,a.sto, b.dta_typ " +
            " from stk_crt_dtl a, mprp b where a.mprp = b.prp and a.crt_idn = ? ";
        ary = new ArrayList();
        ary.add(String.valueOf(crtId));

        ResultSet rs = db.execSql(" Criteria Id :" + crtId, getDtls, ary);
        while (rs.next()) {
            String mprp = util.nvl(rs.getString("mprp"));
            String sfr = util.nvl(rs.getString("sfr"));
            String sto = util.nvl(rs.getString("sto"));
            String vfr = util.nvl(rs.getString("vfr"));
            String vto = util.nvl(rs.getString("vto"));


            boxCrtForm.setValue(mprp + "_1", sfr);
            boxCrtForm.setValue(mprp + "_2", sto);

        }
        rs.close();
        String getSubDtls =
            " select a1.mprp, a1.sfr, a1.sto, a1.vfr, b1.srt psrt, b1.val pval " +
            " from (select a.mprp, a.sfr ,a.vfr, a.sto, decode(mprp, 'CRTWT', get_sz(sfr), a.vfr) sz, " +
            " decode(a.mprp, 'CRTWT', 'SIZE', a.mprp) prp from stk_crt_dtl_sub a where a.crt_idn = ?) a1, prp b1 " +
            " where a1.prp = b1.mprp and a1.sz = b1.val order by a1.mprp ";

        ary = new ArrayList();
        ary.add(String.valueOf(crtId));

        String dspTxt = "", lPrp = "";
        rs = db.execSql(" SubSrch :" + crtId, getSubDtls, ary);
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
                boxCrtForm.setValue("MTXT_" + lPrp,
                                      dspTxt.substring(3).replaceAll("NA",
                                                                     ""));
                if (mprp.equals("CRTWT"))
                    dspTxt = " , " + pval;
                else
                    dspTxt = " , " + vfr;
                lPrp = mprp;
            }

            if (mprp.equals("CRTWT")) {
                boxCrtForm.setValue(mprp + "_" + psrt, pval);
                boxCrtForm.setValue(mprp + "1" + psrt, sfr);
                boxCrtForm.setValue(mprp + "2" + psrt, sto);
            } else
                boxCrtForm.setValue(mprp + "_" + vfr, vfr);
//            System.out.println("mprp" + mprp + "_" + vfr);
        }
        rs.close();
        if (dspTxt.length() > 3)
            boxCrtForm.setValue("MTXT_" + lPrp,
                                  dspTxt.substring(3).replaceAll("NA", ""));

        // boxCrtForm.setValue("fav_id",srchIDN);
        // boxCrtForm.setValue("oldsrchId", srchIDN);
        req.setAttribute("modify", "y");
            util.updAccessLog(req,res,"Box Criteria", "loadCrt end");
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
            util.updAccessLog(req,res,"Box Criteria", "save start");
        BoxCriteriaForm boxCrtForm = (BoxCriteriaForm)form;
        SearchQuery query = new SearchQuery();
        HashMap prp;
        HashMap mprp;
        ArrayList params = null;
        int cnt = 0;
        int lSrchId = 0;
        ArrayList srchPrpLst = info.getStkCrtprplist();

        String buttonPressed = "";
        /* Button for add criteria */
        if (boxCrtForm.getValue("add") != null)
            buttonPressed = "add";
        /* Button for Save */
        if (boxCrtForm.getValue("pb_dmd") != null)
            buttonPressed = "dmd";
        /* Button for Update */
        if (boxCrtForm.getValue("loadFav") != null)
            buttonPressed = "loadFav";
        /* save/update button */
        if (boxCrtForm.getValue("pb_updateSrch") != null)
            buttonPressed = "updsrch";
        if (boxCrtForm.getValue("removeCrt") != null)
            buttonPressed = "remove";
        if (boxCrtForm.getValue("updateCrt") != null)
            buttonPressed = "updateCrt";
        if (buttonPressed.equalsIgnoreCase("loadFav")) {
            buttonPressed = "loadFav";

        }
        if (buttonPressed.equalsIgnoreCase("loadFav")) {
            return loadCrt(am, form, req, res);

        }
        if (buttonPressed.equalsIgnoreCase("remove")) {
            return removeCrt(am, form, req, res);

        }

        /* on clicking save/update button */
        if (buttonPressed.equalsIgnoreCase("updsrch")) {
            String oldSrchId = (String)boxCrtForm.getValue("oldsrchId");
            query.delSearch(req,oldSrchId);
        }
        if (buttonPressed.equals("add") || buttonPressed.equals("updateCrt")) {
          String prop = util.nvl((String)boxCrtForm.getValue("prp"));
          String prpVal = util.nvl((String)boxCrtForm.getValue("subPrp"));
            if (buttonPressed.equals("updateCrt")) {
                ArrayList ary = new ArrayList();
                ArrayList updary = new ArrayList();
                String type = util.nvl((String)boxCrtForm.getValue("type"));
                String dsc = util.nvl((String)boxCrtForm.getValue("dmdDsc"));
                String oldCrtid =
                    util.nvl((String)boxCrtForm.getValue("oldCrtId"));
                updary.add(type);
                updary.add(dsc);
                updary.add(prop);
                updary.add(prpVal);
                updary.add(oldCrtid);
                // update stk_crt table (Header)
                String updOldCrt =
                    " update stk_crt set typ=? ,dsc=? , mprp =? , val = ? where crt_idn=?";
                ary.add(oldCrtid);
                int ct = db.execUpd("UpdateCriteria", updOldCrt, updary);

                /// delete oldcrtid from stk_crt_dtl and stk_crt_dtl_sub
                String delOldCrtid = "delete stk_crt_dtl where crt_idn=?";
                int ctl = db.execUpd("deleteCriteria", delOldCrtid, ary);
                String delOldCrtidsub =
                    "delete stk_crt_dtl_sub where crt_idn=?";
                int ctv = db.execUpd("deleteCriteriadtl", delOldCrtidsub, ary);
                lSrchId = Integer.parseInt(oldCrtid);
            }
            if (!buttonPressed.equals("updateCrt")) {
               

                String getSrchId =
                    "STK_CRT_PKG.GET_ID(pTyp => ?,pNmeIdn =>? , pDsc => ?, pPrp => ?, pVal => ?, pMdl=> ?,pId => ?)";

                params = new ArrayList();
                params.add(util.nvl((String)boxCrtForm.getValue("type")));
                params.add(String.valueOf((boxCrtForm.getNmeID())));
                params.add(util.nvl((String)boxCrtForm.getValue("dmdDsc")));
                params.add(prop);
                params.add(prpVal);
                params.add("JFLEX");

                ArrayList outParams = new ArrayList();
                outParams.add("I");

                CallableStatement cst =
                    db.execCall(" Search Id ", getSrchId, params, outParams);
                try {
                    lSrchId = cst.getInt(params.size() + 1);
                } catch (SQLException e) {
                }
              cst.close();
              cst=null;
            }
            if (lSrchId > 0) {

                prp = info.getPrp();
                mprp = info.getMprp();
                String addDtl =
                    "STK_CRT_PKG.ADD_DTL(pId =>?, pPrp =>?, pSrtFr =>?, pSrtTo =>?)";
                String addDtlSub =
                    "STK_CRT_PKG.ADD_DTL_SUB(pId =>?, pPrp =>?, pSrtFr =>?, pSrtTo =>?)";
                String addDtlVal =
                    "STK_CRT_PKG.ADD_DTL_VAL(pId =>?, pPrp =>?, pValFr =>?, pValTo =>?)";
                String addDtlDte =
                    "STK_CRT_PKG.ADD_DTL_DTE(pId =>?, pPrp =>?, pValFr =>?, pValTo =>?)";
                String addDtlSubVal =
                    "STK_CRT_PKG.ADD_DTL_SUB_VAL(pId =>?, pPrp =>?, pValFr =>?, pValTo =>?) ";
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
                            String wtFr =
                                util.nvl((String)boxCrtForm.getValue("wt_fr_c"));
                            String wtTo =
                                util.nvl((String)boxCrtForm.getValue("wt_to_c"));
                            if ((wtFr.length() > 0) && (wtTo.length() > 0)) {
                                params = new ArrayList();
                                params.add(String.valueOf(lSrchId));
                                params.add(lprp);
                                params.add(wtFr);
                                params.add(wtTo);

                                cnt = db.execCall(" SrchDtl ", addDtl, params);
                            } else { // to select wt from chkbx textbox
                                for (int j = 0; j < lprpS.size(); j++) {
                                    String lSrt = (String)lprpS.get(j);
                                    String reqVal1 =
                                        util.nvl((String)boxCrtForm.getValue(lprp +
                                                                               "1" +
                                                                               lSrt),
                                                 "");
                                    String reqVal2 =
                                        util.nvl((String)boxCrtForm.getValue(lprp +
                                                                               "2" +
                                                                               lSrt),
                                                 "");
                                    if ((reqVal1.length() == 0) ||
                                        (reqVal2.length() == 0)) {
                                        //ignore no value selected;
                                    } else {
                                        if (!dtlAddedOnce) {
                                            params = new ArrayList();
                                            params.add(String.valueOf(lSrchId));
                                            params.add(lprp);
                                            params.add(reqVal1);
                                            params.add(reqVal2);
                                            cnt =
db.execCall(" SrchDtl ", addDtl,
            params); // first valuue added to stk_crt_dtl table
                                            dtlAddedOnce = true;
                                        }
                                        params = new ArrayList();
                                        params.add(String.valueOf(lSrchId));
                                        params.add(lprp);
                                        params.add(reqVal1);
                                        params.add(reqVal2);
                                        cnt =
db.execCall(" SrchDtl ", addDtlSub,
            params); // other crtwt values are added to stk_crt_dtl_sub table
                                    }
                                }
                            }
                        } else {
                            //All Other Multiple Properties

                            for (int j = 0; j < lprpS.size(); j++) {
                                String lSrt = (String)lprpS.get(j);
                                String lVal = (String)lprpV.get(j);
                                String lFld = lprp + "_" + lVal;
                                String reqVal =
                                    util.nvl((String)boxCrtForm.getValue(lFld));
                                //util.SOP(lprp + " : " + lFld + " : " + reqVal);
                                if (reqVal.length() >
                                    0) { // in shape the only first selected shape will go to this tble stk_crt_dtl
                                    if (!dtlAddedOnce) {
                                        params = new ArrayList();
                                        params.add(String.valueOf(lSrchId));
                                        params.add(lprp);
                                        params.add(reqVal);
                                        params.add(reqVal);
                                        cnt =
db.execCall(" SrchDtl ", addDtlVal, params);
                                        dtlAddedOnce = true;
                                    }
                                    params =
                                            new ArrayList(); // firstshape, second shape... will go to this table stk_crt_dtl_sub
                                    params.add(String.valueOf(lSrchId));
                                    params.add(lprp);
                                    params.add(reqVal);
                                    params.add(reqVal);
                                    cnt =
db.execCall(" SrchDtl ", addDtlSubVal, params);
                                }
                            }
                        } // othrt M properties
                    }

                    else { // S single properties


                        String reqVal1 =
                            util.nvl((String)boxCrtForm.getValue(lprp +
                                                                   "_1"), "");
                        String reqVal2 =
                            util.nvl((String)boxCrtForm.getValue(lprp +
                                                                   "_2"), "");
                        if (lprpTyp.equals("T"))
                            reqVal2 = reqVal1;
                        //util.SOP(lprp + " : "+ reqVal1 + " : " + reqVal2);

                        if (((reqVal1.length() == 0) ||
                             (reqVal2.length() == 0)) ||
                            ((reqVal1.equals("0")) && (reqVal2.equals("0")))) {
                            //ignore no selection;
                        } else {

                            params = new ArrayList();
                            params.add(String.valueOf(lSrchId));
                            params.add(lprp);
                            params.add(reqVal1);
                            params.add(reqVal2);
                            if (lprpTyp.equals("T"))
                                cnt =
db.execCall(" SrchDtl ", addDtlVal, params);
                            else if (lprpTyp.equals("D"))
                                cnt =
db.execCall(" SrchDtl ", addDtlDte, params);
                            else
                                cnt = db.execCall(" SrchDtl ", addDtl, params);

                        }
                    }

                }
            } // if srchid not null

            return load(am, form, req, res);
        }
        if ((buttonPressed.equalsIgnoreCase("dmd")) ||
            (buttonPressed.equalsIgnoreCase("upddmd"))) {
            /* String oldDmdId = util.nvl((String)boxCrtForm.getValue("oldDmdId"));
if( buttonPressed.equalsIgnoreCase("upddmd"))
{
if(!oldDmdId.equals("")) {
ArrayList rmAry = new ArrayList();
rmAry.addElement(oldDmdId);
int updCt = db.execCall(" Rmv Dmd ", " dmd_pkg.rmv(?) ", rmAry);
}
}
String dsc = (String)boxCrtForm.getValue("dmdDsc");
int dmdID = query.addDmd(req,lSrchId, dsc);*/
           return load(am, form, req, res);
            // return DmdList(mapping, form, req, response);
        }
            util.updAccessLog(req,res,"Box Criteria", "save end");
        return load(am, form, req, res);
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
        util.updAccessLog(req,res,"Box Criteria", "Unauthorized Access");
        else
        util.updAccessLog(req,res,"Box Criteria", "init");
    }
    }
    return rtnPg;
    }

    public BoxCriteriaAction() {
        super();
    }
}
