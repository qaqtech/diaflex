package ft.com;

//~--- non-JDK imports --------------------------------------------------------

import java.sql.PreparedStatement;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

//~--- JDK imports ------------------------------------------------------------

import java.sql.ResultSet;

import java.util.ArrayList;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AjaxAction extends Action {

    public AjaxAction() {
        super();
    }

    public ActionForward execute(ActionMapping am, ActionForm a, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb      = new StringBuffer();
        String       typ     = util.nvl(req.getParameter("typ"));
        String      usrTyp = util.nvl(req.getParameter("UsrTyp"));
        String      role = util.nvl(req.getParameter("role"));
        String       initial = req.getParameter("param");
        String       match   = "" + initial + "%";
        String       empidn = util.nvl(req.getParameter("emp")).trim();
        String isSrch = util.nvl(req.getParameter("isSrch"));
        String frm = util.nvl(req.getParameter("frm"));
        String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
        match = match.toLowerCase();
        
        ArrayList ary = new ArrayList();
        int    ctr = 0;
        String sql = "WITH NME_LIST as\n" + 
        "(select nme_idn , form_url_encode(nme) nme, dense_rank() over (order by nme) rnk \n" + 
        "from nme_v a where nme is not null and lower(trim(nme)) like lower(trim(?)) " ;
        if(!empidn.equals("") && !empidn.equals("0")){
            sql=sql+" and emp_idn="+empidn+"";
            }
        if(!usrTyp.equals("")){
            usrTyp=util.getVnm(usrTyp);
            sql = sql+" and typ in("+usrTyp+")" ;
        }
        ArrayList pageList=new ArrayList();
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("CONTACT_SRCH");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("CONTACT_SRCH");
        if(pageDtl==null)
            pageDtl=new HashMap();
        allPageDtl.put("CONTACT_SRCH",pageDtl);
        }
        info.setPageDetails(allPageDtl); 
        if(frm.equals("ADV") && usrTyp.equals("'EMPLOYEE'")){
            String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
            String dfNmeIdn=util.nvl((String)info.getDfNmeIdn()); 
            
            pageList= ((ArrayList)pageDtl.get("EMPLOYEE") == null)?new ArrayList():(ArrayList)pageDtl.get("EMPLOYEE");
            if(pageList!=null && pageList.size() >0){
            for(int i=0;i<pageList.size();i++){
                if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
                sql=sql+" and (emp_idn= "+util.nvl((String)info.getDfNmeIdn()).trim()+" or emp_idn in(select emp_idn from nme_v where typ='EMPLOYEE' and mgr_idn= "+util.nvl((String)info.getDfNmeIdn()).trim()+")) ";
                }
            }
            }
         }
          sql = sql+" order by nme)\n" + 
          "select * from nme_list where rnk < 50";
          
        if(!role.equals(""))
        sql="select nmerel_idn, form_url_encode(nme) nme from nmerel_trm_v b where lower(trim(nme)) like lower(trim(?))  and dflt_yn='Y' order by 2";
        
        if(isSrch.equals("Y")){
                    pageDtl=(HashMap)allPageDtl.get("SEARCH_RESULT");
                    if(pageDtl==null || pageDtl.size()==0){
                    pageDtl=new HashMap();
                    pageDtl=util.pagedef("SEARCH_RESULT");
                    if(pageDtl==null)
                        pageDtl=new HashMap();
                    allPageDtl.put("SEARCH_RESULT",pageDtl);
                    }
                    info.setPageDetails(allPageDtl); 
            pageList= ((ArrayList)pageDtl.get("SEARCHSTARTFROM") == null)?new ArrayList():(ArrayList)pageDtl.get("SEARCHSTARTFROM");
            if(pageList!=null && pageList.size() >0){
            match="%"+match;
            }
           sql = "with nmeLst as (select distinct nme_idn, form_url_encode(initcap(trim(nme))) nme from NME_EMP_V a " +
                 " where  exists (select 1 from nmerel b where a.nme_idn = b.nme_idn and " +
                 " b.vld_dte is null and flg = 'CNF') and ss_flg='Y' ";
            pageList= ((ArrayList)pageDtl.get("TYP") == null)?new ArrayList():(ArrayList)pageDtl.get("TYP");
         if(pageList!=null && pageList.size()>0)
             sql=sql+" and typ <> 'EXPORTPARTY' ";
             
          if(!empidn.equals("") && !empidn.equals("0")){
              sql=sql+" and emp_idn= ? ";
              ary.add(empidn);
              }else  if(info.getIsSalesExec()){
               sql=sql+" and emp_idn= ? ";
               ary.add(info.getDfNmeIdn());
             }
            if(!dfgrpnmeidn.equals("") && !dfgrpnmeidn.equals("0")){
            sql +=" and nvl(grp_nme_idn,0) =? "; 
            ary.add(dfgrpnmeidn);
            } 
                
            sql = sql +" and trim(lower(nme)) like lower(?) order by initcap(trim(nme))) select * from nmeLst where rownum < 50 ";
        }
        String lFld = util.nvl(req.getParameter("lFld"));
        if(lFld.length() > 0) {
            String lovQ = util.nvl((String)session.getAttribute(lFld));
            if(lovQ.length() > 0)
                sql = lovQ;
        }
       
        if (typ.equalsIgnoreCase("city")) {
            match = "%" + match;

            /*
             * if(match.indexOf(" ") == -1)
             * sql = "select distinct country_idn, country_nm from country_city_v where lower(country_nm) like lower(?) and rownum < 21 order by 2";
             * else
             */
            sql = "select city_idn, country_city from country_city_v " + " where lower(country_city) like lower(?) "
                  + " order by 2";
        }
        if (usrTyp.indexOf("COUNTRY") > -1) {
            match = "%" + match;

            /*
             * if(match.indexOf(" ") == -1)
             * sql = "select distinct country_idn, country_nm from country_city_v where lower(country_nm) like lower(?) and rownum < 21 order by 2";
             * else
             */
            sql = "select distinct COUNTRY_NM,COUNTRY_NM from mcountry where lower(COUNTRY_NM) like lower(?) order by 1";
        }
        ary.add(match);

        ArrayList outLst = db.execSqlLst("column nme", sql, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            String nme = util.nvl(rs.getString(2), "0");

            ++ctr;
            sb.append("<nmes>");
            sb.append("<nmeid>" + util.nvl(rs.getString(1)) + "</nmeid>");
            sb.append("<nme>" + nme + "</nme>");
            sb.append("</nmes>");

            /*
             * if(ctr == 20)
             *   break;
             */
        }

        rs.close();
        pst.close();
        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        res.getWriter().write("<mnme>" + sb.toString() + "</mnme>");

        return null;
    }

    public StringBuffer getCityCountry(HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb      = new StringBuffer();
        String       initial = req.getParameter("param");
        String       match   = "%"+initial + "%";

        match = match.toLowerCase();

        ArrayList ary = new ArrayList();

        ary.add(match);

        int    ctr = 0;
        String sql =
            "select idn, form_url_encode(nme) nme from country_city_v where lower(nme) like lower(?) and nme is not null order by nme";

        ArrayList outLst = db.execSqlLst("column nme", sql, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            ++ctr;
            sb.append("<nmes>");
            sb.append("<nmeid>" + util.nvl(rs.getString(1), "0") + "</nmeid>");
            sb.append("<nme>" + util.nvl(rs.getString("nme"), "0") + "</nme>");
            sb.append("</nmes>");

            if (ctr == 20) {
                break;
            }
        }

        rs.close();
        pst.close();

        return sb;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
