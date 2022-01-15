package ft.com;

//~--- non-JDK imports --------------------------------------------------------

import ft.com.dao.GenDAO;
import ft.com.dao.MAddr;
import ft.com.dao.UIForms;
import ft.com.dao.UIFormsFields;

import java.sql.PreparedStatement;

import org.apache.struts.action.ActionForm;

//~--- JDK imports ------------------------------------------------------------

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.logging.Level;

public class FormsUtil {
    DBMgr   db;
    InfoMgr info;
    DBUtil  util;

    public FormsUtil() {
        super();
    }

    public void init(DBMgr db, DBUtil util, InfoMgr info) {
        this.db   = db;
        this.util = util;
        this.info = info;
    }

    public UIForms getUIForms(String formName) {

        // HashMap allFields = (HashMap)info.getFormFields();
        HashMap formFields = info.getFormFields(formName);

        if ((formFields == null) || (formFields.size() == 0)) {
            formFields = util.getFormFields(formName);
        }

        UIForms uiForms = (UIForms) formFields.get("DAOS");

        return uiForms;
    }

    public ArrayList getDAOS(UIForms uiForms) {
        ArrayList daos = uiForms.getFields();

        return daos;
    }

    public String getSrchFields(ArrayList daos, String bseFlds) {
        return getSrchFields(daos, bseFlds, true);
    }

    public String getSrchFields(ArrayList daos, String bseFlds, boolean alias) {
        String srchFields;

        srchFields = "";

        ArrayList ukFld = new ArrayList();

        for (int j = 0; j < daos.size(); j++) {
            UIFormsFields dao     = (UIFormsFields) daos.get(j);
            String        lTblFld = dao.getTBL_FLD().toLowerCase();
            String        fldTyp  = dao.getFLD_TYP();
            String        tblNme  = dao.getTBL_NME();

            if (util.nvl(dao.getFLG()).equals("UK")) {
                ukFld.add(lTblFld);
            }

            String delim = ", ";

            /*
             * if (j==0) {
             *   delim = "";
             * }
             */
            if (j == 0) {
                if(bseFlds.length() > 0)
                  srchFields = bseFlds;
            }

            String lQryFld = tblNme + "." + lTblFld;

            if (fldTyp.equalsIgnoreCase("DT")) {
                lQryFld = "to_char(" + tblNme + "." + lTblFld + ", 'dd-mm-rrrr') " + lTblFld;
            }

            srchFields += delim + lQryFld;

            if (alias) {
                if (util.nvl(dao.getALIAS()).length() > 0) {
                    srchFields += delim + dao.getALIAS();
                }
            }
        }

        return srchFields;
    }

    public ArrayList getSrchList(UIForms uiForms, String frmTbl, String srchFields, String srchQ, ArrayList params,
                                 ArrayList daos, String typ) {
        ArrayList list = new ArrayList();

        try {

            // String frmTbl = "maddr";
            String bseTbl = "" ;
            if (util.nvl(uiForms.getFRM_TBL()).length() > 0) {
                frmTbl = uiForms.getFRM_TBL();
            }

            if(frmTbl.length() > 0) {
                if(frmTbl.indexOf(",") > -1) 
                    bseTbl = frmTbl.substring(0, frmTbl.indexOf(","));
                else 
                    bseTbl = frmTbl ;
            }
            
            String whereCl = "";

            if (util.nvl(uiForms.getWHERE_CL()).length() > 0) {
                whereCl = " and " + uiForms.getWHERE_CL();
            }

            if (typ.equals("GT")) {
               
                frmTbl += ", gt_nme_srch gt";                
                whereCl += " and "+ bseTbl + ".nme_idn = gt.nme_idn and gt.flg='M' ";
            }
            if (typ.equals("WBGT")) {
                frmTbl += ", gt_nme_srch gt";                
                whereCl += " and b.nme_idn = gt.nme_idn and gt.flg='M' ";
            }
            String orderby = "1";
            if (util.nvl(uiForms.getORDER_BY()).length() > 0) {
                orderby = uiForms.getORDER_BY();
            }
            if (typ.equals("WBGT")) {
                orderby="1,2,web_usrs.usr";
            }
            
            if(frmTbl.equals("nme_docs") && !srchFields.equals("")){
                srchFields+=",doc_lnk doclnk";
            }

            String getDataQ = " select " + srchFields + " from " + frmTbl + " where 1 =1 " + whereCl + srchQ
                              + " order by "+orderby;
            ArrayList outLst = db.execSqlLst(" get Search data", getDataQ, params);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                GenDAO srchDao = new GenDAO();
                String lIdn    = rs.getString(1);
                String nmeIdn  = rs.getString(2);

                srchDao.setIdn(lIdn);
                srchDao.setNmeIdn(nmeIdn);

                // nme.setValue("idn", nmeIdn);
                for (int i = 0; i < daos.size(); i++) {
                    UIFormsFields dao      = (UIFormsFields) daos.get(i);
                    String        tblFld   = dao.getTBL_FLD().toLowerCase();
                    String        lFld     = dao.getFORM_FLD();
                    String        lFldTyp  = dao.getFLD_TYP();
                    String        dbVal    = util.nvl(rs.getString(tblFld));
                    String        fldAlias = util.nvl(dao.getALIAS());
                    String        aliasFld = "",
                                  aliasVal = "";

                  

                    String dfltVal = dao.getDFLT_VAL();

                    try {
                        if ((dfltVal.length() > 0) && (dbVal.length() == 0)) {
                            dbVal = dfltVal;
                        }
                    } catch (Exception e) {}

                    try {
                        if (fldAlias.length() > 0) {
                            aliasFld = fldAlias.substring(fldAlias.lastIndexOf(" ") + 1);
                            aliasVal = util.nvl(rs.getString(aliasFld));
                        }
                    } catch (SQLException sqle) {

                        // TODO: Add catch code
                        // sqle.printStackTrace();
                       
                    }

                    srchDao.setValue(lFld, dbVal);

                    if (aliasFld.length() > 0) {
                        srchDao.setValue(aliasFld, aliasVal);
                    }
                }
                if(frmTbl.equals("nme_docs") && !srchFields.equals("")){
                    srchDao.setValue("doclnk", util.nvl(rs.getString("doclnk")));
                }
                list.add(srchDao);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }

        return list;
    }

    public ArrayList getForm(UIForms uiForms, String frmTbl, String srchFields, String srchQ, ArrayList params,
                             ArrayList daos, String typ) {
        ArrayList  list = new ArrayList();
        ActionForm af;

        try {

            // String frmTbl = "maddr";
            if (util.nvl(uiForms.getFRM_TBL()).length() > 0) {
                frmTbl = uiForms.getFRM_TBL();
            }

            String whereCl = "";

            if (util.nvl(uiForms.getWHERE_CL()).length() > 0) {
                whereCl = " and " + uiForms.getWHERE_CL();
            }

            String getDataQ = " select " + srchFields + " from " + frmTbl + " where 1 =1 " + whereCl + srchQ
                              + " order by 1 ";

            ArrayList outLst = db.execSqlLst(" get Search data", getDataQ, params);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                GenDAO srchDao = new GenDAO();
                String lIdn    = rs.getString(1);
                String nmeIdn  = rs.getString(2);

                srchDao.setIdn(lIdn);
                srchDao.setNmeIdn(nmeIdn);

                // nme.setValue("idn", nmeIdn);
                for (int i = 0; i < daos.size(); i++) {
                    UIFormsFields dao      = (UIFormsFields) daos.get(i);
                    String        tblFld   = dao.getTBL_FLD().toLowerCase();
                    String        lFld     = dao.getFORM_FLD();
                    String        dbVal    = util.nvl(rs.getString(tblFld));
                    String        fldAlias = util.nvl(dao.getALIAS());
                    String        aliasFld = "",
                                  aliasVal = "";

                    try {
                        if (fldAlias.length() > 0) {
                            aliasFld = fldAlias.substring(fldAlias.indexOf(" ") + 1);
                            aliasVal = util.nvl(rs.getString(aliasFld));
                        }
                    } catch (SQLException sqle) {

                        // TODO: Add catch code
                        // sqle.printStackTrace();
                      
                    }

                    srchDao.setValue(lFld, dbVal);

                    if (aliasFld.length() > 0) {
                        srchDao.setValue(aliasFld, aliasVal);
                    }
                }

                list.add(srchDao);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }

        return list;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
