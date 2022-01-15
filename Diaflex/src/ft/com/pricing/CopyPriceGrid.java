package ft.com.pricing;

//~--- non-JDK imports --------------------------------------------------------

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;

public class CopyPriceGrid extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    DBMgr                       db           = null;
    InfoMgr                     info         = null;
    String                      matNme       = "";
    HashMap                   mprp         = null,
                                prp          = null;
    ArrayList                      bsePrp       = null,
                                disPrp       = null,
                                refPrp       = null,
                                params       = null;
    HttpSession                 session      = null;
    DBUtil                      util         = null;
    String                      grpNme;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void copyGrid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);

        PrintWriter out = response.getWriter();

       init(request, response);
        
        mprp    = info.getMprp();
        prp     = info.getPrp();
        bsePrp  = info.getPrcBsePrp();
        disPrp  = info.getPrcDisPrp();
        refPrp  = info.getPrcRefPrp();
        grpNme  = util.nvl(request.getParameter("grpNme"));
        String oldMatNme = "";
        String nmeIdn = util.nvl(request.getParameter("matIdn"));
        HashMap grpDtls    = info.getGrpDtls();
        String refNmeSql = " select nme from dyn_mst_t where idn=? ";
        params = new ArrayList();
        params.add(nmeIdn);
        ResultSet rsNme = db.execSql("refNme", refNmeSql, params);
        try {
            if (rsNme.next())
                oldMatNme = rsNme.getString(1);
            rsNme.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        String    defnGrpNme = "";
        String    getDefnGrp = " select nvl(defn_grp_nme, 'NA') defnGrp from prc_defn_grp where grp_nme = ? ";
        ArrayList    params     = new ArrayList();
        params = new ArrayList();
        params.add(grpNme);

        ResultSet rs = db.execSql(" Def nGrp ", getDefnGrp, params);

        try {
            if (rs.next()) {
                String lDefnNme = rs.getString(1);

                if (lDefnNme.equals("NA")) {}
                else {
                    defnGrpNme = lDefnNme;
                }
            }
            rs.close();
        } catch (SQLException e) {}

        int matIdn    = 0,
            matSrt    = 0,
            prmDisIdn = 0;

        prmDisIdn = util.getSeqVal("SEQ_PRM");

        double subPct = Double.parseDouble(request.getParameter("sub_pct"));
        String prmnme = "";

        try {
            ArrayList subVals   = new ArrayList();
            String    flg       = "SUB";
            String    getMatIdn = " select SQ_DYN_MST.NextVal matIdn, SQ_DYN_MST_SRT.NextVal matSrt from dual";

            rs = db.execSql(" Mat Idn", getMatIdn, new ArrayList());

            if (rs.next()) {
                matIdn = rs.getInt(1);
                matSrt = rs.getInt(2);

                // prmDisIdn = rs.getInt(3);
            }
            rs.close();
            String insDynMstQ = " insert into dyn_mst_t (idn, srt, nme, flg, prop, rem, prc_grp , ref_nme , un ) "
                                + "values(?, ?, ?, ?, ?, ?, ?, ? , ? )";

            params = new ArrayList();
            params.add(Integer.toString(matIdn));
            params.add(Integer.toString(matSrt));
            params.add(matIdn + "NEW");
            params.add(flg);
            params.add(flg);
            params.add(grpNme);
            params.add(defnGrpNme);
            params.add(oldMatNme);
            params.add(info.getUsr());
            int ct = db.execUpd(" Ins Mat ", insDynMstQ, params);

            for (int i = 0; i < bsePrp.size(); i++) {
                String lprp     = (String) bsePrp.get(i);
                String prpDsc   = (String) mprp.get(lprp + "D");
                ArrayList prpPrt   = (ArrayList) prp.get(lprp + "P");
                ArrayList prpSrt   = (ArrayList) prp.get(lprp + "V");
                String fld1     = lprp + "_1";
                String fld2     = lprp + "_2";
                String valFr    = util.nvl(request.getParameter("sub_" + fld1));
                String valTo    = util.nvl(request.getParameter("sub_" + fld2));
                String subValFr = util.nvl(request.getParameter(fld1));

                subVals.add(lprp + "," + subValFr + "," + "0");
                matNme += valFr + "_";

                String insCmnQ = " insert into dyn_cmn_t (idn, mprp, val_fr, val_to)" + " values (?, ?, ?, ?) ";

                params = new ArrayList();
                params.add(Integer.toString(matIdn));
                params.add(lprp);
                params.add(valFr);
                params.add(valTo);

                // db.execBatchUpd("DYN_CMN_T", " Dyn Cmn", insCmnQ, params, "DIR");
                db.execDirUpd("DYN_CMN_T", insCmnQ, params);
            }
            matNme = "SUB_"+oldMatNme;
            
            util.SOP("@Price " + matNme + " Bse added");

            for (int i = 0; i < disPrp.size(); i++) {
                String lprp     = (String) disPrp.get(i);
                String prpDsc   = (String) mprp.get(lprp + "D");
                String prpTyp   = (String) mprp.get(lprp + "T");
                ArrayList prpPrt   = (ArrayList) prp.get(lprp + "P");
                ArrayList prpSrt   = (ArrayList) prp.get(lprp + "V");
                String fld1     = lprp + "_1";
                String fld2     = lprp + "_2";
                String valFr    = util.nvl(request.getParameter("sub_" + fld1));
                String valTo    = util.nvl(request.getParameter("sub_" + fld2));
                String subValFr = util.nvl(request.getParameter(fld1));

                // subVals.add(lprp+","+subValFr+","+"");
                String numFr    = "",
                       numTo    = "",
                       subNumFr = "";

                if ((valFr.length() > 0) && (valTo.length() > 0)) {

                    // matNme += valFr + "_";
                    String insCmnQ = " insert into dyn_cmn_t (idn, mprp, val_fr, val_to)" + " values(?, ?, ?, ?)";

                    if (prpTyp.equals("N")) {
                        insCmnQ  = " insert into dyn_cmn_t (idn, mprp, num_fr, num_to)" + " values(?, ?, ?, ?) ";
                        numFr    = valFr;
                        numTo    = valTo;
                        subNumFr = subValFr;
                        subValFr = "NA";

                        // valFr = "NA";
                        // valTo = "NA";
                    }

                    params = new ArrayList();
                    params.add(Integer.toString(matIdn));
                    params.add(lprp);
                    params.add(valFr);
                    params.add(valTo);
                    util.SOP(params.toString());

                    /*
                     * ct = db.execDirUpd(" Dyn Cmn", insCmnQ, params);
                     * if(ct == -1)
                     *   break;
                     */

                    // db.execBatchUpd("DYN_CMN_T", " Dyn Cmn", insCmnQ, params, "DIR");
                    db.execDirUpd("DYN_CMN_T", insCmnQ, params);
                    subVals.add(lprp + "," + subValFr + "," + subNumFr);
                }
            }

            util.SOP("@Price " + matNme + " Dis Prp added");
            matNme += "_" + matIdn;
            //matNme = matNme.substring(0, matNme.length() - 1) + "_" + matIdn;

            String updNme = " Update dyn_mst_t set nme = ? where idn = ?";

            params = new ArrayList();
            params.add(matNme);
            params.add(Integer.toString(matIdn));
            ct        = db.execUpd(" Dyn Mst Nme", updNme, params);
            prmDisIdn = util.getSeqVal("SEQ_PRM");

            String mprmQ =
                " insert into mprm_dis(idn, rev_dte, pct, prmnme, prmtyp, flg, srt, rem, prc_grp, ref_cnt, ref_srt , ref_nme)"
                + " values(?, sysdate, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?)";
            ArrayList params1 = new ArrayList();

            params1.add(Integer.toString(prmDisIdn));
            params1.add(Double.toString(subPct));
            params1.add(matNme);
            params1.add(flg);
            params1.add(flg);
            params1.add(Integer.toString(matSrt));
            params1.add(grpNme);
            params1.add(defnGrpNme);
            params1.add("0");
            params1.add("0");
            params1.add("SUB_"+oldMatNme);

            // db.execBatchUpd("MPRM_DIS", " Mprm Hdr", mprmQ, params1);
            db.execUpd("Mprm Hdr", mprmQ, params1);

            String insCmnDtl =
                " insert into prm_dis_dtl(mprm_dis_idn, mprp, val_fr, val_to, num_fr, num_to, srt_fr, srt_to) "
                + " select ?, a.mprp, a.val_fr, a.val_to, a.num_fr, a.num_to, decode(b.dta_typ, 'C', c.srt, num_fr) srt_fr, decode(b.dta_typ, 'C', d.srt, num_to) srt_to "
                + " from dyn_cmn_t a, mprp b, prp c, prp d "
                + " where a.mprp = b.prp and b.prp = c.mprp and b.prp = d.mprp and a.val_fr = c.val and a.val_to = d.val and a.idn = ? ";

            params1 = new ArrayList();
            params1.add(Integer.toString(prmDisIdn));
            params1.add(Integer.toString(matIdn));
            ct = db.execUpd(" Mprm Cmn", insCmnDtl, params1);

            // db.execBatchUpd("PRM_DIS_DTL_CMN", " Mprm Cmn", insCmnDtl, params1);

            for (int i = 0; i < subVals.size(); i++) {
                String rowVals = (String) subVals.get(i);

                util.SOP("CopySub : " + i + " :: " + rowVals);

                String[] vals    = rowVals.split(",");
                String   insCnsQ = " insert into cns_prm_dis(cns_prm_idn, mprm_dis_idn, mprp, val, num_cn, srt_cn) "
                                   + "   values (SEQ_CNS_PRM_DIS.nextval, ?, ?, ?, ?, 1)";
                ArrayList cparams = new ArrayList();

                cparams.add(Integer.toString(prmDisIdn));
                cparams.add(vals[0]);
                cparams.add(vals[1]);

                if (vals.length == 3) {
                    cparams.add(vals[2]);
                } else {
                    cparams.add("");
                }

                db.execDirUpd("CNS_PRM_DIS", insCnsQ, cparams);

                // db.execBatchUpd("CNS_PRM_DIS", " InsCNs", insCnsQ, cparams);
            }
        } catch (SQLException e) {}

        int ct = db.execCall("Upd Srt", "PRC_DATA_PKG.SET_REM_SRT", new ArrayList());

        out.println(" Matrix Id : " + matIdn + " | Nme : " + matNme);
        out.println("<a href=\"" + info.getReqUrl() + "/pricing/loadPriceGrid.jsp?grpNme=" + grpNme
                    + "\">Group Home</a>");
        out.close();
    }
    public void init(HttpServletRequest req , HttpServletResponse res){
        session = req.getSession(false);
        info    = (InfoMgr) session.getAttribute("info");
        util = ((DBUtil)session.getAttribute("util") == null)?new DBUtil():(DBUtil)session.getAttribute("util");
        db      = (DBMgr) session.getAttribute("db");
        String connExists=util.nvl(util.getConnExists());   
        if(session.isNew() || db==null || connExists.equals("N")){
        try {
            res.sendRedirect("../error_page.jsp?connExists="+connExists);
            } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
            }
        }
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        copyGrid(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        copyGrid(request, response);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
