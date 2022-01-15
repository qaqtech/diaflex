package ft.com.marketing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
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


public class StockCriteriaAction extends DispatchAction {
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
        StockCriteriaForm stockCrtForm = (StockCriteriaForm)form;
        stockCrtForm.reset();
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
          ArrayList outLst = db.execSqlLst(" loadPrpLst ", loadPrp, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            ArrayList prpList = new ArrayList();
            prpList.add(rs.getString("prp"));
            prpList.add(rs.getString("flg"));
            srchPrpList.add(prpList);
        }
        rs.close();
            pst.close();
        // Properties and their flag in srchPrpList
        session.setAttribute("stkCrtprplist", srchPrpList);
        info.setStkCrtprplist(srchPrpList);
        // To load dropdown properties .only type C

        String dropProp =
            "select prp,dsc, srt, nvl(prt_nme, prp) prt, dta_typ from mprp where 1 = 1" +
            "and trunc(nvl(vld_till, sysdate)) <= trunc(sysdate) and dta_typ in('C','N')" +
            "order by dsc";
         outLst = db.execSqlLst(" dropDownPrpLst ", dropProp, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            ArrayList dropprp = new ArrayList();
            dropprp.add(rs.getString("dsc"));
            dropprp.add(rs.getString("prp"));
            prptable.add(dropprp);
        }
        rs.close();
        pst.close();
        session.setAttribute("prpTable", prptable);
    

        String typeqry =
            "select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and\n" +
            "b.mdl = 'JFLEX' and b.nme_rule = 'STK_CRT_TYP' order by a.srt_fr";
          outLst = db.execSqlLst(" loadType ", typeqry, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            ArrayList type = new ArrayList();
            type.add(rs.getString("chr_fr"));
            type.add(rs.getString("dsc"));
            typeList.add(type);
        }
       rs.close();
        pst.close();
       session.setAttribute("typeList", typeList);
        /*
* load Existing Criteria
*/
        ArrayList exsCrtra = new ArrayList();
        String exsCrt =
            "select crt_idn,typ||'-'||dsc exsDes from stk_crt where stt='A' and vld_dte is null order by dsc";
          outLst = db.execSqlLst(" ExistingCriteria ", exsCrt, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            ArrayList crta = new ArrayList();
            crta.add(rs.getString("crt_idn"));
            crta.add(rs.getString("exsDes"));
            exsCrtra.add(crta);
        }
        rs.close();
            pst.close();
        session.setAttribute("exsCrtra", exsCrtra);
        
        util.getcrtwtPrp(sz,req,res);
            util.updAccessLog(req,res,"Stock Criteria", "end");
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
            util.updAccessLog(req,res,"Stock Criteria", "remove crt");
        StockCriteriaForm stockCrtForm = (StockCriteriaForm)form;
        ArrayList ary = new ArrayList();

        String crtId = (String)stockCrtForm.getValue("exiCrt");
        ary.add(crtId);

        String removeCrt =
            " update stk_crt set stt='IA' ,vld_dte=sysdate where crt_idn=?";

        int ct = db.execUpd("RemoveCriteria", removeCrt, ary);
            util.updAccessLog(req,res,"Stock Criteria", "end");
        return am.findForward("load");
        }
    }
    
    public ActionForward filterCrt(ActionMapping am, ActionForm form,
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
            util.updAccessLog(req,res,"Stock Criteria", "filtercrt");
        StockCriteriaForm stockCrtForm = (StockCriteriaForm)form;
        HashMap mprp = info.getMprp();
        String type = util.nvl((String)stockCrtForm.getValue("type"));
        String mprpVal = util.nvl((String)stockCrtForm.getValue("prp"));
        String prp =util.nvl((String)stockCrtForm.getValue("subPrpTN"));
        String prpTyp = util.nvl((String)mprp.get(prp+"T"));
            
        if(prp.equals("") && prpTyp.equals("C"))
        prp = util.nvl((String)stockCrtForm.getValue("subPrp"),"NA");
            
        int nmeID = stockCrtForm.getNmeID();
        ArrayList ary = new ArrayList();
        String crtSql = " select  a.crt_idn , a.typ||'-'||a.dsc exsDes   from stk_crt a,rule_dtl b " + 
        " where  a.stt='A' and trim(a.typ)=trim(b.chr_fr) and a.vld_dte is null " ;
        if(nmeID!=0){
         crtSql = crtSql+" and a.nme_idn = ? ";
        ary.add(String.valueOf(nmeID));
        }
        if(!type.equals("")){
        crtSql = crtSql+" and b.chr_fr = ? ";
        ary.add(type);
        }
        if(!mprpVal.equals("") && !mprpVal.equals("0")){
        crtSql = crtSql+" and a.mprp = ?  ";
        ary.add(mprpVal);
        }
        if(!prp.equals("") && prpTyp.equals("C")){
        crtSql = crtSql+" and a.val = ? ";
        ary.add(prp);
        }
        if(!prp.equals("") && !prpTyp.equals("C")){
        crtSql = crtSql+" and a.num = ? ";
        ary.add(prp);
        }
          ArrayList outLst = db.execSqlLst("crtSql", crtSql, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        ArrayList exsCrtra = new ArrayList();
        while (rs.next()) {
            ArrayList crta = new ArrayList();
            crta.add(rs.getString("crt_idn"));
            crta.add(rs.getString("exsDes"));
            exsCrtra.add(crta);
        }
        rs.close();
            pst.close();
        session.setAttribute("exsCrtra", exsCrtra);
            util.updAccessLog(req,res,"Stock Criteria", "ecd");
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
            util.updAccessLog(req,res,"Stock Criteria", "loadcrt");
        StockCriteriaForm stockCrtForm = (StockCriteriaForm)form;
        ArrayList ary = new ArrayList();
        HashMap prptb = info.getPrp();
        HashMap mprpLst = info.getMprp();
        String crtId = (String)stockCrtForm.getValue("exiCrt");
        String type = (String)stockCrtForm.getValue("type");
        String desc = (String)stockCrtForm.getValue("dmdDsc");
        String mprpVal = (String)stockCrtForm.getValue("prp");
        String prp =util.nvl((String)stockCrtForm.getValue("subPrpTN"));
        String prpTyp = util.nvl((String)mprpLst.get(prp+"T"));
        if(prp.equals("") && prpTyp.equals("C"))
        prp = util.nvl((String)stockCrtForm.getValue("subPrp"),"NA");
        String load = "Y";
        req.setAttribute("load", load);
        req.setAttribute("property", mprpVal);
        req.setAttribute("propValue", prp);
        int nmeID = stockCrtForm.getNmeID();
        stockCrtForm.reset();
        stockCrtForm.setValue("exiCrt", crtId);
        /* set crt id to hidden fld for Update criteria */
        stockCrtForm.setValue("oldCrtId", crtId);
        stockCrtForm.setValue("type", type);
        stockCrtForm.setValue("dmdDsc", desc);
        stockCrtForm.setValue("prp", mprpVal);

        if(prpTyp.equals("C"))
        stockCrtForm.setValue("subPrp", prp);
        else
        stockCrtForm.setValue("subPrpTN", prp);
            
        stockCrtForm.setNmeID(nmeID);
        
         String gethdr = " select b.chr_fr typkey, b.dsc typdsc,a.dsc descr,a.mprp,a.val,a.num,a.nme_idn , byr.get_nm(a.nme_idn) nme from stk_crt a,rule_dtl b "+
                         " where a.crt_idn=? and a.stt='A' and trim(a.typ)=trim(b.chr_fr) and a.vld_dte is null ";
        ary = new ArrayList();
        ary.add(String.valueOf(crtId));
          ArrayList outLst = db.execSqlLst(" Criteria Id :" + crtId, gethdr, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
         while(rs.next()){
             stockCrtForm.setValue("type", util.nvl(rs.getString("typkey")));
             stockCrtForm.setValue("dmdDsc", util.nvl(rs.getString("descr")));
             stockCrtForm.setValue("prp", util.nvl(rs.getString("mprp")));
             stockCrtForm.setValue("subPrp", util.nvl(rs.getString("val")));
             stockCrtForm.setValue("subPrpTN", util.nvl(rs.getString("num")));
             stockCrtForm.setNmeID(rs.getInt("nme_idn"));
             req.setAttribute("nme", util.nvl(rs.getString("nme")));
         }
    
        rs.close();
          pst.close();

        String getDtls =
            " select a.mprp ,a.vfr , a.vto , a.sfr ,a.sto, b.dta_typ " +
            " from stk_crt_dtl a, mprp b where a.mprp = b.prp and a.crt_idn = ? ";
        ary = new ArrayList();
        ary.add(String.valueOf(crtId));

           outLst = db.execSqlLst(" Criteria Id :" + crtId, getDtls, ary);
          pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            String mprp = util.nvl(rs.getString("mprp"));
            String sfr = util.nvl(rs.getString("sfr"));
            String sto = util.nvl(rs.getString("sto"));
            String vfr = util.nvl(rs.getString("vfr"));
            String vto = util.nvl(rs.getString("vto"));


            stockCrtForm.setValue(mprp + "_1", sfr);
            stockCrtForm.setValue(mprp + "_2", sto);

        }
        rs.close();
        pst.close();
        String getSubDtls =
            " select a1.mprp, a1.sfr, a1.sto, a1.vfr, b1.srt psrt, b1.val pval " +
            " from (select a.mprp, a.sfr ,a.vfr, a.sto, decode(mprp, 'CRTWT', get_sz(sfr), a.vfr) sz, " +
            " decode(a.mprp, 'CRTWT', 'SIZE', a.mprp) prp from stk_crt_dtl_sub a where a.crt_idn = ?) a1, prp b1 " +
            " where a1.prp = b1.mprp and a1.sz = b1.val order by a1.mprp ";

        ary = new ArrayList();
        ary.add(String.valueOf(crtId));

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
                stockCrtForm.setValue("MTXT_" + lPrp,
                                      dspTxt.substring(3).replaceAll("NA",
                                                                     ""));
                if (mprp.equals("CRTWT"))
                    dspTxt = " , " + pval;
                else
                    dspTxt = " , " + vfr;
                lPrp = mprp;
            }

            if (mprp.equals("CRTWT")) {
                stockCrtForm.setValue(mprp + "_" + psrt, pval);
                stockCrtForm.setValue(mprp + "1" + psrt, sfr);
                stockCrtForm.setValue(mprp + "2" + psrt, sto);
            } else
                stockCrtForm.setValue(mprp + "_" + vfr, vfr);
            System.out.println("mprp" + mprp + "_" + vfr);
        }
        rs.close();
            pst.close();
        if (dspTxt.length() > 3)
            stockCrtForm.setValue("MTXT_" + lPrp,
                                  dspTxt.substring(3).replaceAll("NA", ""));

        // stockCrtForm.setValue("fav_id",srchIDN);
        // stockCrtForm.setValue("oldsrchId", srchIDN);
        req.setAttribute("modify", "y");
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
        util.updAccessLog(req,res,"Stock Criteria", "save");
        StockCriteriaForm stockCrtForm = (StockCriteriaForm)form;
        SearchQuery query = new SearchQuery();
        HashMap prp= info.getPrp();
        HashMap mprp = info.getMprp();
        ArrayList params = null;
        int cnt = 0;
        int lSrchId = 0;
        ArrayList srchPrpLst = info.getStkCrtprplist();

        String buttonPressed = "";
        /* Button for add criteria */
        if (stockCrtForm.getValue("add") != null)
            buttonPressed = "add";
        /* Button for Save */
        if (stockCrtForm.getValue("pb_dmd") != null)
            buttonPressed = "dmd";
        /* Button for Update */
        if (stockCrtForm.getValue("loadFav") != null)
            buttonPressed = "loadFav";
        /* save/update button */
        if (stockCrtForm.getValue("pb_updateSrch") != null)
            buttonPressed = "updsrch";
        if (stockCrtForm.getValue("removeCrt") != null)
            buttonPressed = "remove";
        if (stockCrtForm.getValue("updateCrt") != null)
            buttonPressed = "updateCrt";
        if (stockCrtForm.getValue("filterCrt") != null)
            buttonPressed = "filterCrt";
        if(stockCrtForm.getValue("reset")!=null)
            buttonPressed = "reset";
        if (buttonPressed.equalsIgnoreCase("loadFav")) {
            buttonPressed = "loadFav";

        }
        if(buttonPressed.equals("filterCrt")){
            return filterCrt(am, form, req, res);
        }
        if (buttonPressed.equalsIgnoreCase("loadFav")) {
            return loadCrt(am, form, req, res);

        }
        if(buttonPressed.equalsIgnoreCase("reset")){
            load(am, form, req, res);
        }
        if (buttonPressed.equalsIgnoreCase("remove")) {
            return removeCrt(am, form, req, res);

        }

        /* on clicking save/update button */
        if (buttonPressed.equalsIgnoreCase("updsrch")) {
            String oldSrchId = (String)stockCrtForm.getValue("oldsrchId");
            query.delSearch(req,oldSrchId);
        }
        if (buttonPressed.equals("add") || buttonPressed.equals("updateCrt")) {
          String prop = util.nvl((String)stockCrtForm.getValue("prp"));
          String prpVal =util.nvl((String)stockCrtForm.getValue("subPrpTN"));
            String prpTyp = util.nvl((String)mprp.get(prop+"T"));
            if(prpVal.equals("") && prpTyp.equals("C"))
            prpVal = util.nvl((String)stockCrtForm.getValue("subPrp"),"NA");
          
            if (buttonPressed.equals("updateCrt")) {
                ArrayList ary = new ArrayList();
                ArrayList updary = new ArrayList();
                String type = util.nvl((String)stockCrtForm.getValue("type"));
                String dsc = util.nvl((String)stockCrtForm.getValue("dmdDsc"));
                String updOldCrt =
                    " update stk_crt set typ=? ,dsc=?, mprp =? , val = ? where crt_idn=?";
                if(!prpTyp.equals("C"))
                    updOldCrt =" update stk_crt set typ=? ,dsc=?, mprp =? ,num=? where crt_idn=?";
                String oldCrtid =
                util.nvl((String)stockCrtForm.getValue("oldCrtId"));
                updary.add(type);
                updary.add(dsc);
                updary.add(prop);
                updary.add(prpVal);
                updary.add(oldCrtid);
                int ct = db.execUpd("UpdateCriteria", updOldCrt, updary);
                ary.add(oldCrtid);
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
                params.add(util.nvl((String)stockCrtForm.getValue("type")));
                params.add(String.valueOf((stockCrtForm.getNmeID())));
                params.add(util.nvl((String)stockCrtForm.getValue("dmdDsc")));
                params.add(prop);
                params.add(prpVal);
                params.add("JFLEX");

                ArrayList outParams = new ArrayList();
                outParams.add("I");

                CallableStatement cst =
                    db.execCall(" Search Id ", getSrchId, params, outParams);
                try {
                    lSrchId = cst.getInt(params.size() + 1);
                  cst.close();
                  cst=null;
                } catch (SQLException e) {
                }
            }
            if (lSrchId > 0) {
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
                                util.nvl((String)stockCrtForm.getValue("wt_fr_c"));
                            String wtTo =
                                util.nvl((String)stockCrtForm.getValue("wt_to_c"));
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
                                        util.nvl((String)stockCrtForm.getValue(lprp +
                                                                               "1" +
                                                                               lSrt),
                                                 "");
                                    String reqVal2 =
                                        util.nvl((String)stockCrtForm.getValue(lprp +
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
                                    util.nvl((String)stockCrtForm.getValue(lFld));
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
                            util.nvl((String)stockCrtForm.getValue(lprp +
                                                                   "_1"), "");
                        String reqVal2 =
                            util.nvl((String)stockCrtForm.getValue(lprp +
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
            /* String oldDmdId = util.nvl((String)stockCrtForm.getValue("oldDmdId"));
if( buttonPressed.equalsIgnoreCase("upddmd"))
{
if(!oldDmdId.equals("")) {
ArrayList rmAry = new ArrayList();
rmAry.addElement(oldDmdId);
int updCt = db.execCall(" Rmv Dmd ", " dmd_pkg.rmv(?) ", rmAry);
}
}
String dsc = (String)stockCrtForm.getValue("dmdDsc");
int dmdID = query.addDmd(req,lSrchId, dsc);*/
            util.updAccessLog(req,res,"Stock Criteria", "end");
           return load(am, form, req, res);
            // return DmdList(mapping, form, req, response);
        }
        util.updAccessLog(req,res,"Stock Criteria", "end");
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
                util.updAccessLog(req,res,"Stock Criteria", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Stock Criteria", "init");
            }
            }
            return rtnPg;
            }

    public StockCriteriaAction() {
        super();
    }
}
