package ft.com.contact;

//~--- non-JDK imports --------------------------------------------------------

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.FormsUtil;
import ft.com.InfoMgr;
import ft.com.dao.GenDAO;
import ft.com.dao.UIForms;
import ft.com.dao.UIFormsFields;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.io.OutputStream;

import java.sql.PreparedStatement;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

//~--- JDK imports ------------------------------------------------------------

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.sql.Connection;
public class SearchContactAction extends DispatchAction {
    private final String formName = "searchcontact";

    public SearchContactAction() {
        super();
    }

    public ActionForward search(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg="sucess";
        if(info!=null){  
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        rtnPg=init(req,res,session,util);
        }else
        rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
            util.updAccessLog(req,res,"Contact", "search start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        // udf = (SearchContactFrm)af;
        DynaActionForm udf = (DynaActionForm) af;

        int    cnt    = 0;
        ArrayList params = null;
        String delQ   = " delete from gt_nme_srch ";

        cnt = db.execUpd(" del GT", delQ, new ArrayList());

        String nmeSrch = " insert into gt_nme_srch(srch_id, nme_idn, flg) " + " select 1, nme_idn, 'N' from mnme "
                         + " where 1 = 1 " + " and nvl(emp_idn, 0) = ? " + " and typ = ? "
                         + " and (upper(fnme) like ? or upper(mnme) like ? or upper(lnme) like ?) "
                         + " and vld_dte is null order by fnme, mnme, lnme";

        params = new ArrayList();

        String nmeParam = "";

        // nmeParam = util.nvl((udf.getNme()).toUpperCase();
        nmeParam = util.nvl((String) udf.get("nme")).toUpperCase();

        String  nmeId     = util.nvl((String) udf.get("nmeID"));
        boolean srchByNme = true;
        
        nmeSrch = " insert into gt_nme_srch(srch_id, nme_idn, flg) select 1, nme_idn, 'N' " + " from mnme "
        + " where 1 = 1 and vld_dte is null ";

        String emp = util.nvl((String) udf.get("emp"));

        if (emp.length() > 0) {
        nmeSrch += " and nvl(emp_idn, 0) = ? " ;
        params.add(emp);
        }

        String typ = util.nvl((String) udf.get("typ"));
        if(typ.length() > 0) {
        nmeSrch += " and typ = ? ";
        params.add(typ);
        }


        

        if (nmeId.length() > 0) {
            nmeSrch = " insert into gt_nme_srch(srch_id, nme_idn, flg) " + " select 1, nme_idn, 'N' from mnme "
                      + " where 1 = 1 and nme_idn = ? ";
            params = new ArrayList();
            params.add(nmeId);
        } else {
            if (nmeParam.length() > 0) {
                nmeSrch = " insert into gt_nme_srch(srch_id, nme_idn, flg) " + " select 1, nme_idn, 'N' from mnme "
                          + " where 1 = 1 " + " and nvl(emp_idn, 0) = ? " + " and typ = ? "
                          + " and (upper(fnme) like ? or upper(mnme) like ? or upper(lnme) like ?) "
                          + " and vld_dte is null order by fnme, mnme, lnme";

                String nmeFltr = "";

                // nmeFltr = util.nvl(udf.getNmeFltr(), "C");
                nmeFltr = util.nvl((String) udf.get("nmeFltr"), "C");

                if (nmeFltr.equals("C")) {
                    nmeParam = "%" + nmeParam + "%";
                }

                if (nmeFltr.equals("S")) {
                    nmeParam += "%";
                }

                if (nmeFltr.equals("E")) {
                    nmeParam = "%" + nmeParam;
                }

                // nmeParam = nmeParam.toUpperCase();
                params.add(nmeParam);
                params.add(nmeParam);
                params.add(nmeParam);
            }
        }
        String usr = util.nvl((String)udf.get("usr"));
        if(!usr.equals("")){
            nmeSrch = " insert into gt_nme_srch(srch_id, nme_idn, flg) " + 
            " select 1, nme_idn, 'N' from web_usrs a , nmerel b where " + 
            " a.rel_idn = b.nmerel_idn and a.to_dt is null and b.vld_dte is null and  upper(a.usr) = upper(?)";
            params = new ArrayList();
            params.add(usr);
        }
        
       
        cnt = db.execUpd("Nme Srch", nmeSrch, params);
      
        
        boolean srchByAddr = true;
        String  addrQ      =
            " delete from gt_nme_srch a where not exists (select 1 from maddr b, mcity c, mcountry d where a.nme_idn = b.nme_idn and b.city_idn = c.city_idn and c.country_idn = d.country_idn "
            + " and (upper(b.unt_num) like ? or upper(b.bldg) like ? or upper(b.street) like ? or upper(b.landmark) like ? or upper(b.area) like ? "
            + " or upper(c.city_nm) like ? or upper(d.country_nm) like ?) and b.vld_dte is null)";

        if (!srchByNme) {
            addrQ =
                " insert into gt_nme_srch(srch_id, nme_idn, flg) " + " select 1, a.nme_idn, 'N' "
                + " from mnme a, maddr b, mcity c, mcountry d "
                + " where a.nme_idn = b.nme_idn and b.city_idn = c.city_idn and c.country_idn = d.country_idn "
                + " and (upper(b.unt_num) like ? or upper(b.bldg) like ? or upper(b.street) like ? or upper(b.landmark) like ? or upper(b.area) like ? "
                + " or upper(c.city_nm) like ? or upper(d.country_nm) like ?) and b.vld_dte is null and a.vld_dte is null";
        }

        String addrCrt = "";

        // addrCrt = util.nvl(udf.getAddr()).toUpperCase();
        addrCrt = util.nvl((String) udf.get("addr")).toUpperCase();

        if (addrCrt.length() > 0) {
            addrCrt = "%" + addrCrt + "%";
            params  = new ArrayList();
            params.add(addrCrt);
            params.add(addrCrt);
            params.add(addrCrt);
            params.add(addrCrt);
            params.add(addrCrt);
            params.add(addrCrt);
            params.add(addrCrt);
           
            cnt = db.execUpd("Addr Srch", addrQ, params);
        } else {
            srchByAddr = false;
        }

        String nmeDtlQ =
            " delete from gt_nme_srch a where not exists (select 1 from nme_dtl b where a.nme_idn = b.nme_idn "
            + " and (upper(b.vfr) like ? or to_char(b.nfr) like ? or upper(b.txt) like ?) and b.vld_dte is null) ";

        if ((!srchByNme) && (!srchByAddr)) {
            nmeDtlQ =
                " insert into gt_nme_srch(srch_id, nme_idn, flg) "
                + " select 1, a.nme_idn, 'N' from mnme a, nme_dtl b " + " where a.nme_idn = b.nme_idn "
                + " and (upper(b.vfr) like ? or to_char(b.nfr) like ? or upper(b.txt) like ?) and b.vld_dte is null and a.vld_dte is null";
        }

        String nmeDtlCrt = "";

        // nmeDtlCrt = util.nvl(udf.getAttrDtl()).toUpperCase();
        nmeDtlCrt = util.nvl((String) udf.get("attrDtl")).toUpperCase();

        if (nmeDtlCrt.length() > 0) {
            nmeDtlCrt = "%" + nmeDtlCrt + "%";
            params    = new ArrayList();
            params.add(nmeDtlCrt);
            params.add(nmeDtlCrt);
            params.add(nmeDtlCrt);
           
            cnt = db.execUpd("nmeDtlQ", nmeDtlQ, params);
        }
        String updateFlg = " update gt_nme_srch a set flg='U' where exists (select 1 from web_usrs b , nmerel c " + 
        " where b.rel_idn = c.nmerel_idn and b.to_dt is null and c.vld_dte is null and c.nme_idn = a.nme_idn ) ";
        cnt = db.execUpd("update flg", updateFlg, new ArrayList());
        
        String    rtrn    = "result";
        String    srchCnt = " select count(*) from gt_nme_srch ";

            ArrayList outLst = db.execSqlLst(" Srch Cnt ", srchCnt, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        if (rs.next()) {
            int fnlCnt = rs.getInt(1);

            if (fnlCnt > 0) {
                rtrn = "result";
            } else {
                rtrn = "nodata";
            }
        }
        rs.close(); pst.close();
            util.updAccessLog(req,res,"Contact", "search end");
        return am.findForward(rtrn);
        }
    }
    public ActionForward Excel(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Contact", "Excel start");
        HashMap dbinfo = info.getDmbsInfoLst();
        String zipallowyn = util.nvl((String)dbinfo.get("ZIP_DWD_YN"),"N"); 
            int zipdwnsz = Integer.parseInt(util.nvl((String)dbinfo.get("ZIP_DWD_SZ"),"100")); 
        ArrayList atrList = new ArrayList();
        String contDtl = "select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " + 
        " b.mdl = 'JFLEX' and b.nme_rule = 'CONT_XL' and a.til_dte is null order by a.srt_fr ";
        ArrayList outLst = db.execSqlLst("contAtr",contDtl, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            atrList.add(util.nvl(rs.getString("chr_fr")));
        }
        rs.close(); pst.close();

      
        if(atrList!=null && atrList.size()>0){
        ArrayList itemHdr = new ArrayList();
        itemHdr.add("SR No");
        itemHdr.add("Customer Type");
        itemHdr.add("Customer Name");
        itemHdr.add("Country");
        itemHdr.add("City");
       
      
        String contactSql =  "select a.fnme , a.typ , c.country_nm , c.city_nm  , f.term , e.usr " ;
        for(int i=0;i < atrList.size() ; i++){
            String atr = (String)atrList.get(i);
            contactSql+=" , MAX (DECODE (d.mprp, '"+atr+"', d.txt, NULL)) "+atr+" ";
            itemHdr.add(atr);
        }
            itemHdr.add("Terms");
            itemHdr.add("Web Usr");
        contactSql+= " from mnme a , maddr b , country_city_v c , nme_dtl d , web_usrs e, nmerel_trm_v f " + 
        "where a.nme_idn = b.nme_idn(+) and a.nme_idn = d.nme_idn(+) " + 
        "and b.city_idn = c.city_idn(+) " + 
        "and f.nmerel_idn=e.rel_idn(+) " + 
        "and f.nme_idn=a.nme_idn " + 
        "and  exists (select 1 from gt_nme_srch g where  g.nme_idn = a.nme_idn) " + 
        "and a.vld_dte is null and d.txt IS NOT NULL and b.vld_dte is null  " + 
        " and b.dflt_yn = 'Y' and f.dflt_yn='Y' "+
        "group by a.fnme, a.typ, c.country_nm, c.city_nm, e.usr, f.term order by a.fnme";
        ArrayList contDtlList = new ArrayList();
        outLst = db.execSqlLst("contactSql",contactSql, new ArrayList());
        pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
        int cnt = 0;
        while(rs.next()){
            HashMap contDtlMap = new HashMap();
            cnt++;
            contDtlMap.put("SR No", String.valueOf(cnt));
            contDtlMap.put("Customer Type", util.nvl(rs.getString("typ")));
            contDtlMap.put("Customer Name", util.nvl(rs.getString("fnme")));
            contDtlMap.put("Country", util.nvl(rs.getString("country_nm")));
            contDtlMap.put("City", util.nvl(rs.getString("city_nm")));
            contDtlMap.put("Terms", util.nvl(rs.getString("term")));
            contDtlMap.put("Web Usr", util.nvl(rs.getString("usr")));
            for(int i=0;i < atrList.size() ; i++){
                String atr = (String)atrList.get(i);
                contDtlMap.put(atr, util.nvl(rs.getString(atr)));
            }
            contDtlList.add(contDtlMap);
        }
            rs.close(); pst.close();

            ExcelUtil xlUtil = new ExcelUtil();
            xlUtil.init(db, util, info);
            session.setAttribute("itemHdr", itemHdr);
            session.setAttribute("pktDtlList", contDtlList);
         
            HSSFWorkbook hwb = new HSSFWorkbook();
            hwb = xlUtil.getGenXl(itemHdr, contDtlList);
            int pktListsz=contDtlList.size();
            if(zipallowyn.equals("Y") && pktListsz>zipdwnsz){
            String contentTypezip = "application/zip";
            String fileNmzip = "ContactExcel"+util.getToDteTime();
            OutputStream outstm = res.getOutputStream();
            ZipOutputStream zipOut = new ZipOutputStream(outstm);
            ZipEntry entry = new ZipEntry(fileNmzip+".xls");
            zipOut.putNextEntry(entry);
            res.setHeader("Content-Disposition","attachment; filename="+fileNmzip+".zip");
            res.setContentType(contentTypezip);
              ByteArrayOutputStream bos = new ByteArrayOutputStream();
               hwb.write(bos);
               bos.writeTo(zipOut);      
              zipOut.closeEntry();
               zipOut.flush();
               zipOut.close();
               outstm.flush();
               outstm.close(); 
            }else{
            String fileNm = "ContactExcel"+util.getToDteTime()+".xls";
            String CONTENT_TYPE = "getServletContext()/vnd-excel";
            res.setContentType(CONTENT_TYPE);
            res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
            OutputStream out = res.getOutputStream();
            hwb.write(out);
            out.flush();
            out.close();
            }
        
        }
            util.updAccessLog(req,res,"Contact", "Excel end");
        return null;
        }
    }
    public ActionForward CNTExcel(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
             util.updAccessLog(req,res,"Contact", "CNTExcel start");
         FormsUtil form = (FormsUtil)session.getAttribute("form");
        ArrayList  repForms = (ArrayList)session.getAttribute("CONTXLFLT");
        HashMap headerMap = new HashMap();
         HashMap pktDtlMap = new HashMap();
         ArrayList nmeIdnList = new ArrayList();
         ArrayList  selectRepForms = new ArrayList();
        if(repForms!=null && repForms.size()>0){
            for(int i=0 ; i< repForms.size();i++){
                String formNme = (String)repForms.get(i);
                String isDtlRq = util.nvl(req.getParameter(formNme));
                if(isDtlRq.equals("yes"))
                    selectRepForms.add(formNme);
                ArrayList formFLdDsc = new ArrayList();
                ArrayList formFLdVal = new ArrayList();
                String formTTL ="";
              
                 UIForms uiForms = form.getUIForms(formNme);
                 ArrayList daos = form.getDAOS(uiForms);
                 formTTL = uiForms.getFORM_TTL();
                String tblNme = util.nvl(uiForms.getFRM_TBL());
                if(tblNme.length() > 0) {
                if(tblNme.indexOf(",") > -1) 
                    tblNme = tblNme.substring(0, tblNme.indexOf(","));
                     tblNme = tblNme+".";
                 }  
                
              if(!formNme.equals("nmedtl") && !formNme.equals("mrole")){
                for(int j=0 ; j < daos.size();j++){
                    UIFormsFields dao     = (UIFormsFields) daos.get(j);
                    String    lTblFld = dao.getTBL_FLD().toLowerCase();
                    String    dspTtl = dao.getDSP_TTL();
                    String        fldAlias = util.nvl(dao.getALIAS());
                    String isChecked = util.nvl(req.getParameter(formNme+"_"+lTblFld));
                        
                   if (fldAlias.length() > 0) 
                            lTblFld = fldAlias.substring(fldAlias.lastIndexOf(" ")+1);
                        
                     if(isChecked.equals("yes")){
                        formFLdDsc.add(dspTtl);
                        formFLdVal.add(lTblFld);
                    }
                    }
              }else if(formNme.equals("mrole")){
                  ArrayList webroleList =  (ArrayList)session.getAttribute("WEBROLEDTL");
                  if(webroleList!=null && webroleList.size()>0){
                      for(int j=0 ; j < webroleList.size();j++){
                           ArrayList contDtl = (ArrayList)webroleList.get(j);
                           String    dspTtl = (String)contDtl.get(0);
                           String   lTblFld = (String)contDtl.get(1);
                           String isChecked = util.nvl(req.getParameter(formNme+"_"+lTblFld));
                          if(isChecked.equals("yes")){
                             formFLdDsc.add(dspTtl);
                             formFLdVal.add(lTblFld);
                          }
                           formTTL="Web Role Details";
                      }
                  }
              }else{
                    ArrayList addtionDtl =  (ArrayList)session.getAttribute("ADDCONDTL");
                    if(addtionDtl!=null && addtionDtl.size()>0){
                        for(int j=0 ; j < addtionDtl.size();j++){
                             ArrayList contDtl = (ArrayList)addtionDtl.get(j);
                             String    dspTtl = (String)contDtl.get(0);
                             String   lTblFld = (String)contDtl.get(1);
                            String isChecked = util.nvl(req.getParameter(formNme+"_"+lTblFld));
                            if(isChecked.equals("yes")){
                               formFLdDsc.add(dspTtl);
                               formFLdVal.add(lTblFld);
                            }
                             formTTL="Addtional Detail";
                        }
                    }
                }
                headerMap.put(formNme+"_DSC", formFLdDsc);
                headerMap.put(formNme+"_VAL", formFLdVal);
                headerMap.put(formNme+"_TTL", formTTL);
                ArrayList list=new ArrayList();
                if(formNme.equals("countryfrm")){
                  String webroleQ="select byr.get_country(nme_idn) country , nme_idn  from gt_nme_srch where flg='M'";
                    ArrayList outLst = db.execSqlLst("webroleQ", webroleQ, new ArrayList());
                    PreparedStatement pst = (PreparedStatement)outLst.get(0);
                    ResultSet rs = (ResultSet)outLst.get(1);
                                    while(rs.next()){
                                    String nmeIdn = util.nvl(rs.getString("nme_idn"));
                                    String country = util.nvl(rs.getString("country"));
                                    ArrayList pktDtlList=new ArrayList();
                                    pktDtlList.add(country);
                                    pktDtlMap.put(formNme+"_"+nmeIdn, pktDtlList); 
                             }
                    rs.close(); pst.close();
                }else if(!formNme.equals("nmedtl") && !formNme.equals("mrole")){   
                 String bseFlds = tblNme+"nme_idn idn, "+ tblNme + "nme_idn "; 
               
                 String srchFields = "";
                 if(formNme.equals("webaccess")){
                 bseFlds = "b.nme_idn idn,b.nme_idn "; 
                 srchFields=form.getSrchFields(daos, bseFlds);
                 list = form.getSrchList(uiForms, tblNme, " distinct "+srchFields, "", new ArrayList(), daos, "WBGT");
                 }else{
                 srchFields=form.getSrchFields(daos, bseFlds);
                 if(formNme.equals("contact") || formNme.equals("address"))
                 list = form.getSrchList(uiForms, tblNme, " distinct "+srchFields, " and "+tblNme+"vld_dte is null", new ArrayList(), daos, "GT");
                 else if(formNme.equals("customerterms")) 
                 list = form.getSrchList(uiForms, tblNme, " distinct nmerel.vld_dte, "+srchFields, " and "+tblNme+"vld_dte is null", new ArrayList(), daos, "GT");
                 else
                 list = form.getSrchList(uiForms, tblNme, srchFields, " and "+tblNme+"vld_dte is null", new ArrayList(), daos, "GT");
                 }
                String pnmeIdn="0";
                ArrayList pktDtlList = new ArrayList();
            
                for(int m=0; m < list.size(); m++) {
                    GenDAO lDao = (GenDAO)list.get(m);
                    String lIdn = lDao.getIdn();
                    String nmeIdn = lDao.getNmeIdn();
                    if(pnmeIdn.equals("0"))
                        pnmeIdn = nmeIdn;
                    if(!pnmeIdn.equals(nmeIdn)){
                       pktDtlMap.put(formNme+"_"+pnmeIdn, pktDtlList);
                       pktDtlList = new ArrayList();
                       pnmeIdn = nmeIdn;
                       
                    }
                    pktDtlList.add(lDao);
                    if(!nmeIdnList.contains(nmeIdn))
                      nmeIdnList.add(nmeIdn);
                }
                if(!pnmeIdn.equals("0"))
                    pktDtlMap.put(formNme+"_"+pnmeIdn, pktDtlList);
              }else if(formNme.equals("mrole")){
                  String webroleQ="select nme_idn from gt_nme_srch where flg='M'";
                  ArrayList outLst = db.execSqlLst("webroleQ", webroleQ, new ArrayList());
                  PreparedStatement pst = (PreparedStatement)outLst.get(0);
                  ResultSet rs = (ResultSet)outLst.get(1);
                  while(rs.next()){
                  String nmeIdn = util.nvl(rs.getString("nme_idn"));
                  ArrayList pktDtlList=(ArrayList)getWebRoleDtl(req,nmeIdn);
                  pktDtlMap.put(formNme+"_"+nmeIdn, pktDtlList); 
                  }
                  rs.close(); pst.close();
              }else if(formNme.equals("nmedtl") && formFLdVal!=null && formFLdVal.size()>0){
                      String whereClou = "";
                  String addtionlInfo = "select a.nme_idn , a.mprp   ,a.txt " ;
               
                  for(int a=0;a < formFLdVal.size() ; a++){
                      String atr = (String)formFLdVal.get(a);
                      whereClou+= "'"+atr+"',";
                     
                  }
                 whereClou = whereClou.substring(0, whereClou.length()-1);
                     addtionlInfo+=" from nme_dtl a where a.txt IS NOT NULL " +
                                   " and  exists (select 1 from gt_nme_srch b where  b.nme_idn = a.nme_idn and b.flg='M') " +
                                   " and a.mprp in ("+whereClou+") order by a.nme_idn ";
                                 
                   

                  ArrayList outLst = db.execSqlLst("addtionDtl", addtionlInfo, new ArrayList());
                  PreparedStatement pst = (PreparedStatement)outLst.get(0);
                  ResultSet rs = (ResultSet)outLst.get(1);
                     GenDAO dao = new GenDAO();
                     String pnmeIdn = "";
                      ArrayList pktDtlList = new ArrayList();
                      while(rs.next()){
                          String nmeIdn = util.nvl(rs.getString("nme_idn"));
                          if(pnmeIdn.equals(""))
                              pnmeIdn = nmeIdn;
                          if(!pnmeIdn.equals(nmeIdn)){
                              pktDtlList.add(dao);
                              pktDtlMap.put(formNme+"_"+pnmeIdn, pktDtlList); 
                              pktDtlList = new ArrayList();
                              dao = new GenDAO();
                              pnmeIdn = nmeIdn;
                          }
                          String mprp = util.nvl(rs.getString("mprp"));
                          String txt = util.nvl(rs.getString("txt"));
                          dao.setValue(mprp, txt);
                          
                      }
                  rs.close(); pst.close();
                      if(!pnmeIdn.equals("")){
                          pktDtlList.add(dao);
                          pktDtlMap.put(formNme+"_"+pnmeIdn, pktDtlList); 
                      }
                        
              }
          }
        }
        req.setAttribute("selectRepForms", selectRepForms);
        req.setAttribute("pktDtlMap", pktDtlMap);
        req.setAttribute("headerMap", headerMap);
         req.setAttribute("nmeIdnList", nmeIdnList);
         ExcelUtil xlUtil = new ExcelUtil();
         xlUtil.init(db, util, info);
         String CONTENT_TYPE = "getServletContext()/vnd-excel";
         String fileNm = "ContactExcel"+util.getToDteTime()+".xls";
         res.setContentType(CONTENT_TYPE);
         res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
         HSSFWorkbook hwb = xlUtil.ConatctExcel(req);
         OutputStream out = res.getOutputStream();
         hwb.write(out);
         out.flush();
         out.close();
             util.updAccessLog(req,res,"Contact", "CNTExcel end");
         return null;
         }
     }
    
        public ArrayList getWebRoleDtl(HttpServletRequest req,String nmeIdn){
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
            ArrayList ary= new ArrayList();
            ArrayList pktDtlList = new ArrayList(); 
            String webroleQ="select d.nme_idn,d.nmerel_idn,b.role_nm ,a.stt,c.usr_id \n" + 
            "from usr_role a,mrole b,web_usrs c,nmerel_all_trm_v d\n" + 
            "where \n" + 
            "a.role_idn=b.role_idn\n" + 
            "and a.usr_idn=c.usr_id\n" + 
            "and c.rel_idn=d.nmerel_idn\n" + 
            "and d.nme_idn=?\n" + 
            "order by d.nme_idn,d.nmerel_idn,c.usr";
            ary.add(nmeIdn);

            ArrayList outLst = db.execSqlLst("webroleQ", webroleQ,ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
             GenDAO dao = new GenDAO();
             String pusrIdn = "";
            try {
              while(rs.next()){
                  String usrIdn = util.nvl(rs.getString("usr_id"));
                  if(pusrIdn.equals(""))
                      pusrIdn = usrIdn;
                  if(!pusrIdn.equals(usrIdn)){
                      pktDtlList.add(dao);
                      dao = new GenDAO();
                      pusrIdn = usrIdn;
                  }
                  String role_nm = util.nvl(rs.getString("role_nm"));
                  String stt = util.nvl(rs.getString("stt"));
                  dao.setValue(role_nm, stt);
                  
              }
            rs.close(); pst.close();
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
              if(!pusrIdn.equals("")){
                  pktDtlList.add(dao); 
              }  
            return pktDtlList;
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
                rtnPg=util.checkUserPageRights("contact/advcontact.do?","");
                if(rtnPg.equals("unauthorized"))
                util.updAccessLog(req,res,"Contact", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Contact", "init");
            }
            }
            return rtnPg;
         }
}


//~ Formatted by Jindent --- http://www.jindent.com
