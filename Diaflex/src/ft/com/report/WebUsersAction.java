package ft.com.report;

//~--- non-JDK imports --------------------------------------------------------

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.generic.GenericImpl;
import ft.com.marketing.MemoReturnFrm;

import java.io.IOException;

import java.sql.Connection;

import java.sql.PreparedStatement;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

//~--- JDK imports ------------------------------------------------------------

import java.sql.ResultSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class WebUsersAction extends DispatchAction {


    public WebUsersAction() {
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
                util.updAccessLog(req,res,"Web Users List", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Web Users List", "init");
            }
            }
            return rtnPg;
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
            util.updAccessLog(req,res,"Web Users List", "load start");

        WebUsersForm udf      = (WebUsersForm) af;

        ArrayList ppLst    = new ArrayList();
        HashMap map = new HashMap();
        ArrayList    params   = null;
            
        ArrayList mroleLst = new ArrayList();
         
        ArrayList mroleMapLst = new ArrayList();
        String mroleSql="select role_nm,role_dsc,role_idn from mrole where stt='A' and to_dte is null order by srt";
          ArrayList outLst = db.execSqlLst("sql", mroleSql, new ArrayList());
          PreparedStatement  pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            HashMap roleDtl = new HashMap();
            roleDtl.put("ROLEDSC", util.nvl(rs.getString("role_dsc")));
            roleDtl.put("ROLENME",  util.nvl(rs.getString("role_nm")));
            roleDtl.put("ROLEIDN",  util.nvl(rs.getString("role_idn")));
            mroleMapLst.add(roleDtl);
            mroleLst.add(rs.getString("role_nm"));
        }
        rs.close();
            pst.close();

        String getDataQ = " select b.usr_id, a.nme_id, a.byr, b.usr, b.usr_eml eml, b.pwd , to_char(b.fr_dt, 'MON-DD-YYYY') fr_dt, byr.get_nm(a.emp_idn) sl_prsn ,\n" + 
        "d.term, c.cur, byr.get_nm(c.AADAT_IDN) aadat1,c.aadat_comm, byr.get_nm(c.mbrk2_idn) broker1,c.mbrk2_comm broker1comm,c.ttl_trm_pct,ext_pct,rap_pct\n" + 
        "from nme_cntct_v a, web_usrs b, nmerel c, mtrms d\n" + 
        "where a.nme_id = c.nme_idn and b.rel_idn = c.nmerel_idn\n" + 
        "and c.trms_idn = d.idn and b.to_dt is null order by a.byr ";
        
           outLst = db.execSqlLst("webUsrData", getDataQ, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        int count = 0;

        while (rs.next()) {
            count++;
            HashMap ppLstMap = new HashMap();
            String nme_idn=util.nvl(rs.getString("nme_id"));
            ppLstMap.put("Count", String.valueOf(count));
            ppLstMap.put("byr", util.nvl(rs.getString("byr")));
            ppLstMap.put("NME_IDN", util.nvl(rs.getString("nme_id")));
            ppLstMap.put("USR_IDN", util.nvl(rs.getString("usr_id")));
            ppLstMap.put("byrEX", util.nvl(rs.getString("byr")));
            ppLstMap.put("usr", util.nvl(rs.getString("usr")));
            ppLstMap.put("pwd", util.nvl(rs.getString("pwd")));
            ppLstMap.put("eml", util.nvl(rs.getString("eml")));
            ppLstMap.put("dte", util.nvl(rs.getString("fr_dt")));
            ppLstMap.put("sl_prsn", util.nvl(rs.getString("sl_prsn")));
            ppLstMap.put("term", util.nvl(rs.getString("term")));
            ppLstMap.put("cur", util.nvl(rs.getString("cur")));
            ppLstMap.put("aadat1", util.nvl(rs.getString("aadat1")));
            ppLstMap.put("aadat_comm", util.nvl(rs.getString("aadat_comm")));
            ppLstMap.put("broker1", util.nvl(rs.getString("broker1")));
            ppLstMap.put("broker1comm", util.nvl(rs.getString("broker1comm")));
            ppLstMap.put("ext_pct", util.nvl(rs.getString("ext_pct")));
            ppLstMap.put("rap_pct", util.nvl(rs.getString("rap_pct")));
            ppLstMap.put("ttl_trm_pct", util.nvl(rs.getString("ttl_trm_pct")));
            
            String usrId = rs.getString("usr_id");
            ArrayList usrRoleLst = new ArrayList();
            String roleLst = "select a.role_nm from mrole a , usr_role b \n" + 
            "where a.role_idn=b.role_idn and b.stt = 'A' and b.usr_idn=?\n";
            params = new ArrayList();
            params.add(usrId);
            ResultSet rs1 = db.execSql("roleLst", roleLst, params);
            while(rs1.next()){
                usrRoleLst.add(util.nvl(rs1.getString("role_nm")));
            }
            ppLstMap.put("USRLST", usrRoleLst);
            rs1.close();
            
            map.put(nme_idn,ppLstMap);

            ppLst.add(ppLstMap);
        }
        rs.close();
        pst.close();
           
        getDataQ = " select f.nme_idn, b.bldg address,c.city_nm,b.zip,d.country_nm,b.flg2 state,e.lnme name,to_char(e.dob,'rrrr-mm-dd') dob from web_usrs a, nmerel f, maddr b,mcity c,mcountry d,mnme e\n" + 
        "where  a.rel_idn = f.nmerel_idn and b.nme_idn=f.nme_idn and b.nme_idn=e.nme_idn and c.city_idn=b.city_idn and d.country_idn=b.flg1 \n" + 
        "and b.vld_dte is null and a.to_dt is null";
        
          outLst = db.execSqlLst("webUsrData", getDataQ, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            String nme_idn=util.nvl(rs.getString("nme_idn"));
            HashMap userMap = (HashMap)map.get(nme_idn);
            userMap.put("Address",util.nvl(rs.getString("address")));
            userMap.put("City",util.nvl(rs.getString("city_nm")));
            userMap.put("ZipCode",util.nvl(rs.getString("zip")));
            userMap.put("Country",util.nvl(rs.getString("country_nm")));
            userMap.put("State",util.nvl(rs.getString("state")));
            userMap.put("Name",util.nvl(rs.getString("name")));
            userMap.put("BirthDate",util.nvl(rs.getString("dob")));
            map.put(nme_idn,userMap);
        }
        rs.close(); 
            pst.close();
            
        getDataQ = "select a.nme_idn,a.txt,a.mprp from nme_dtl a, web_usrs b, nmerel c\n" + 
        "where b.rel_idn = c.nmerel_idn and c.nme_idn = a.nme_idn and a.mprp in('MOBILE','OFFICE','Business_Type','Council_Member','FAX','Member_Id') \n" + 
        "and a.vld_dte is null and a.txt is not null and c.dflt_yn='Y' and b.to_dt is null order by nme_idn";
        
          outLst = db.execSqlLst("webUsrData", getDataQ, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        String pNme_idn="";
        HashMap userMap = new HashMap();
        while(rs.next()){
            String nme_idn=util.nvl(rs.getString("nme_idn"));
            String txt=util.nvl(rs.getString("txt"));
            String mprp=util.nvl(rs.getString("mprp"));
            if(pNme_idn.equals(""))
                pNme_idn=nme_idn;
            if(!pNme_idn.equals(nme_idn)){
                HashMap prvuserMap = (HashMap)map.get(pNme_idn);     
                prvuserMap.putAll(userMap);
                map.put(pNme_idn,prvuserMap);
                pNme_idn=nme_idn;
                userMap= new HashMap();
            }
            userMap.put(mprp,txt);
         
        }
            rs.close();
            pst.close();
        if(!pNme_idn.equals("")){
            HashMap prvuserMap = (HashMap)map.get(pNme_idn);     
            prvuserMap.putAll(userMap);
            map.put(pNme_idn,prvuserMap);            
        }
            
            
 
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("WEB_USER_REPORT");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("WEB_USER_REPORT");
        allPageDtl.put("WEB_USER_REPORT",pageDtl);
        }
        info.setPageDetails(allPageDtl);    
            
            
        udf.setVwListMap(ppLst);
       req.setAttribute("MROLEMapLST",mroleMapLst);
       req.setAttribute("MROLELST",mroleLst);
        req.setAttribute("USRDTLLST", ppLst);
        //session.setAttribute("page", "PREMIUM PLUS");
        util.updAccessLog(req,res,"Web Users List", "load end");
        return am.findForward("load");
        }
    }
    
    public ActionForward saveUSRROLE(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
          util.updAccessLog(req,res,"WebMenuRoleAction", "save");
        String stt = req.getParameter("stt");
        String usrIdn = req.getParameter("usrIdn");
        String roleIdn = req.getParameter("roleIdn");
        String sql="";
        ArrayList    params  = new ArrayList();
        if(stt.equals("true"))
        sql="update usr_role set stt= 'A' , to_dte = null where role_idn= ? and usr_idn=? "; 
        else
        sql="update usr_role set stt= 'IA' , to_dte = sysdate where role_idn= ? and usr_idn=? "; 
   
      params.add(roleIdn);
      params.add(usrIdn);
      
   
      int ct = db.execUpd(" update menu_role ", sql, params);
      if(ct<1){
          String insertRole = "insert into usr_role(ur_idn,role_idn,usr_idn,stt) "+
              "select SEQ_USR_ROLE_IDN.nextval , ? , ?, ? from dual ";
          params = new ArrayList();
          params.add(roleIdn);
          params.add(usrIdn);
          params.add("A");
         ct = db.execUpd("insertRole",insertRole,params);    
         }
          String msg ="";
          if(ct>0){
             msg="Update SuccessFully.."; 
          }else{
              msg="Update Failed..";
          }
            
            res.setContentType("text/xml");
            res.setHeader("Cache-Control", "no-cache");
            res.getWriter().write("<msg>" + msg + "</msg>");

          util.updAccessLog(req,res,"WebMenuRoleAction", "save end");
        return null;
        }
    }

}


//~ Formatted by Jindent --- http://www.jindent.com
