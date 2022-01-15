package ft.com.website;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.LogMgr;

import java.io.OutputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;


public class WebLoginAndSrchDtl extends DispatchAction{
  
    public WebLoginAndSrchDtl() {
        super();
    }
    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest request, HttpServletResponse response) throws Exception  {
      HttpSession session = request.getSession(false);
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
      rtnPg=init(request,response,session,util);
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          return am.findForward(rtnPg);   
      }else{
          util.updAccessLog(request,response,"WebLoginAndSrchDtl", "load");
         WebLoginAndSrchDtlForm udf = (WebLoginAndSrchDtlForm)af;
          String tm = util.nvl((String)info.getDmbsInfoLst().get("WEBSRCHTM"));
          String group=util.nvl((String)request.getParameter("group"));
          String saleEmp=util.nvl((String)request.getParameter("saleEmp"));
            String dtefr=util.nvl((String)request.getParameter("dtefr"));
            String dteto=util.nvl((String)request.getParameter("dteto"));
            String buyerId=util.nvl((String)request.getParameter("buyerId"));
          String typ=util.nvl((String)request.getParameter("typ"));
          HashMap dbinfo = info.getDmbsInfoLst();
          String cnt = (String)dbinfo.get("CNT");
          ArrayList ary=new ArrayList();
          ArrayList rolenmLst=(ArrayList)info.getRoleLst();
          String usrFlg=util.nvl((String)info.getUsrFlg());
          String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
          String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
          String dfNmeIdn=util.nvl((String)info.getDfNmeIdn());
          String conQ="";
          if(tm.equals(""))
              tm="3";
          if(!group.equals("")){
          conQ =conQ+ "and e.grp_nme_idn= ? ";
          ary.add(group);
          }else{
          if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
          }else if(!dfgrpnmeidn.equals("") && !dfgrpnmeidn.equals("0")){
          conQ =conQ+" and e.grp_nme_idn=? "; 
          ary.add(dfgrpnmeidn);
          }  
          }
          if(dfUsrtyp.equals("EMPLOYEE")){
              conQ += " and (e.emp_idn= ? or e.emp_idn in(select emp_idn from nme_v where typ='EMPLOYEE' and mgr_idn= ?)) ";  
              ary.add(dfNmeIdn);
              ary.add(dfNmeIdn);
          }else if(!saleEmp.equals("")){
              conQ =conQ+ " and e.emp_idn= ? ";  
              ary.add(saleEmp);
          }
          
            if(!buyerId.equals("")){
              conQ =conQ+  "and e.nme_idn= ? ";
              ary.add(buyerId);
            }
            
            String conQdte = " dt_tm > sysdate - ("+tm+"/24) " ;
            if(!dtefr.equals("") && !dteto.equals(""))
            conQdte = " trunc(dt_tm) between to_date('"+dtefr+"' , 'dd-mm-yyyy') and to_date('"+dteto+"' , 'dd-mm-yyyy') ";
            
            
            if(typ.equals("MOB")){
                if(cnt.equals("hk"))    
                conQ =conQ+ " and a.flg='MOBAPP' ";
                if(cnt.equals("kj"))
                conQ =conQ+ " and a.flg='MOB' ";
            }
            
           
          
          ArrayList dtlList = new ArrayList();
          String loginDtlSql = "select  e.nme , d.usr , a.cl_ip , to_char(dt_tm, 'dd-Mon HH24:mi:ss') dsp_dt_Tm " + 
          " ,  byr.get_nm(e.emp_idn) salEx , a.CL_COUNTRY cnt from web_login_log a " + 
          ", (select max(log_id) log_id, usr_id from web_login_log b where "+conQdte+" group by usr_id) b " + 
          ", nmerel c , web_usrs d , nme_v e " + 
          "where a.log_id = b.log_id and c.nmerel_idn = d.rel_idn and d.usr_id = a.usr_id and c.nme_idn=e.nme_idn " +conQ+ 
          "order by dt_tm desc";
         
         ArrayList rsLst = db.execSqlLst("loginDtl", loginDtlSql, ary);
        PreparedStatement pst = (PreparedStatement)rsLst.get(0);
        ResultSet rs = (ResultSet)rsLst.get(1);
        int srno=0;
        while(rs.next()){
            srno = srno+1;
            HashMap dtls = new HashMap();
            dtls.put("SR No", String.valueOf(srno));
            dtls.put("Byr Name", util.nvl(rs.getString("nme")));
            dtls.put("User", util.nvl(rs.getString("usr")));
            dtls.put("Date", util.nvl(rs.getString("dsp_dt_Tm")));
            dtls.put("Client IP",  util.nvl(rs.getString("cl_ip")));
            dtls.put("Country",  util.nvl(rs.getString("cnt")));
            dtls.put("Sale EX",  util.nvl(rs.getString("salEx")));
            dtlList.add(dtls);
        }
        rs.close();
        pst.close();
        ArrayList itemHdr = new ArrayList();
        itemHdr.add("SR No");
        itemHdr.add("Byr Name");
        itemHdr.add("User");
        itemHdr.add("Date");
        itemHdr.add("Client IP");
        itemHdr.add("Country");
        itemHdr.add("Sale EX");
        request.setAttribute("itemHdrload",itemHdr);
         request.setAttribute("dtlListload", dtlList);
         udf.setPktList(dtlList);
          util.updAccessLog(request,response,"WebLoginAndSrchDtl", "load end");
       return am.findForward("load");
        }
    }
    
    public ActionForward loadSrchDtl(ActionMapping am, ActionForm af, HttpServletRequest request, HttpServletResponse response) throws Exception  {
      HttpSession session = request.getSession(false);
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
      rtnPg=init(request,response,session,util);
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          return am.findForward(rtnPg);   
      }else{
          util.updAccessLog(request,response,"WebLoginAndSrchDtl", "srchdtl");
          WebLoginAndSrchDtlForm udf = (WebLoginAndSrchDtlForm)af;
          ArrayList dtlList = new ArrayList();
        String tm = util.nvl((String)info.getDmbsInfoLst().get("WEBSRCHTM"));
        String group=util.nvl((String)request.getParameter("group"));
        String saleEmp=util.nvl((String)request.getParameter("saleEmp"));
            String dtefr=util.nvl((String)request.getParameter("dtefr"));
            String dteto=util.nvl((String)request.getParameter("dteto"));
            String buyerId=util.nvl((String)request.getParameter("buyerId"));
        String typ=util.nvl((String)request.getParameter("typ"));
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        ArrayList ary=new ArrayList();
        ArrayList rolenmLst=(ArrayList)info.getRoleLst();
        String usrFlg=util.nvl((String)info.getUsrFlg());
        String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
        String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
        String dfNmeIdn=util.nvl((String)info.getDfNmeIdn());
        String conQ="";
        
        if(tm.equals(""))
            tm="3";
        if(!group.equals("")){
        conQ =conQ+ "and e.grp_nme_idn= ? ";
        ary.add(group);
        }else{
        if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
        }else if(!dfgrpnmeidn.equals("") && !dfgrpnmeidn.equals("0")){
        conQ =conQ+" and e.grp_nme_idn=? "; 
        ary.add(dfgrpnmeidn);
        }  
        }
        if(dfUsrtyp.equals("EMPLOYEE")){
            conQ += " and (e.emp_idn= ? or e.emp_idn in(select emp_idn from nme_v where typ='EMPLOYEE' and mgr_idn= ?)) ";  
            ary.add(dfNmeIdn);
            ary.add(dfNmeIdn);
        }else if(!saleEmp.equals("")){
            conQ =conQ+ " and e.emp_idn= ? ";  
            ary.add(saleEmp);
        }
          
            if(!buyerId.equals("")){
              conQ =conQ+  "and e.nme_idn= ? ";
              ary.add(buyerId);
            }
            
            String conQdte = " f.dte > sysdate - ("+tm+"/24) " ;
            if(!dtefr.equals("") && !dteto.equals(""))
            conQdte = " trunc(f.dte) between to_date('"+dtefr+"' , 'dd-mm-yyyy') and to_date('"+dteto+"' , 'dd-mm-yyyy') ";
            
            
            if(typ.equals("MOB"))
                conQ =conQ+ " and f.typ='MOB' ";  
            else 
                conQ =conQ+ " and f.mdl='WWW' ";
          
          String loginSrchDtlSql = "select e.nme nme ,web_pkg.get_srch_dscr(f.srch_id) scrhDsc ,to_char(f.dte, 'dd-Mon HH24:mi:ss') dsp_dt_Tm , f.srch_id srch_id , byr.get_nm(e.emp_idn) salEx from " + 
          " nmerel c , nme_v e , msrch f " + 
          " where c.nmerel_idn = f.rln_id and e.nme_idn = c.nme_idn and f.rln_id >0 " + conQ+
          " and "+conQdte+"  order by f.srch_id desc ";
         
         ArrayList rsLst = db.execSqlLst("loginSrchDtlSql", loginSrchDtlSql, ary);
        PreparedStatement pst = (PreparedStatement)rsLst.get(0);
        ResultSet rs = (ResultSet)rsLst.get(1);
        int srno = 0;
        while(rs.next()){
            srno = srno+1;
            String dsc= util.nvl(rs.getString("scrhDsc"));
            if(!dsc.equals("")){
            HashMap dtls = new HashMap();
            dtls.put("SR No", String.valueOf(srno));
            dtls.put("Byr Name", util.nvl(rs.getString("nme")));
            dtls.put("Search Dsc", util.nvl(rs.getString("scrhDsc")));
            dtls.put("Search Id", util.nvl(rs.getString("srch_id")));
            dtls.put("Date", util.nvl(rs.getString("dsp_dt_Tm")));
            dtls.put("Sale EX",  util.nvl(rs.getString("salEx")));
            dtlList.add(dtls);
            }
        }
        rs.close();
        pst.close();
        ArrayList itemHdr = new ArrayList();
        itemHdr.add("SR No");
        itemHdr.add("Byr Name");
        itemHdr.add("Date");
        itemHdr.add("Search Dsc");
        itemHdr.add("Search Id");
        itemHdr.add("Sale EX");
        request.setAttribute("itemHdrSrch",itemHdr);
        request.setAttribute("dtlListSrch", dtlList);
        udf.setSrchPktList(dtlList);
          util.updAccessLog(request,response,"WebLoginAndSrchDtl", "srchDtl end");
       return am.findForward("loadSrchDtl");
        }
    }
    
    public ActionForward loadweblogindtl(ActionMapping am, ActionForm af, HttpServletRequest request, HttpServletResponse response) throws Exception  {
      HttpSession session = request.getSession(false);
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
      rtnPg=init(request,response,session,util);
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          return am.findForward(rtnPg);   
      }else{
          util.updAccessLog(request,response,"WebLoginAndSrchDtl", "logindtl");
        WebLoginAndSrchDtlForm  udf = (WebLoginAndSrchDtlForm)af;
         String usr = util.nvl((String)udf.getValue("usr")).trim();
        String byridn = util.nvl(request.getParameter("nmeID"));
        String frmdte = util.nvl((String)udf.getValue("dtefr"));
        String todte = util.nvl((String)udf.getValue("dteto"));
        udf.reset();
          ArrayList dtlList = new ArrayList();
          ArrayList ary = new ArrayList();
          String conQ="";
          if(!usr.equals(""))
              conQ=" and upper(d.usr) = upper('"+usr+"') ";
          if(!byridn.equals("")){
            conQ=conQ+" and upper(c.nme_idn) = ? ";
              ary.add(byridn);
          }
         
         String conQdte=" trunc(b.dt_tm)=trunc(sysdate)";
         if(!frmdte.equals("") && !todte.equals(""))
        conQdte = " trunc(b.dt_tm) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
         
        
          
          String loginDtlSql = "select  e.nme , d.usr , a.cl_ip , to_char(dt_tm, 'dd-Mon HH24:mi:ss') dsp_dt_Tm  \n" + 
          "           ,  lower(byr.get_eml(e.nme_idn,'N')) eml,a.CL_COUNTRY cnt From Web_Login_Log A\n" + 
          "          , (select max(log_id) log_id, usr_id from web_login_log b where "+conQdte+" group by usr_id) b\n" + 
          "          , nmerel c , web_usrs d , nme_v e \n" + 
          "          where a.log_id = b.log_id and c.nmerel_idn = d.rel_idn and d.usr_id = a.usr_id and c.nme_idn=e.nme_idn  \n" + conQ+
          "          order by dt_tm desc";
         
         ArrayList rsLst = db.execSqlLst("loginDtl", loginDtlSql, ary);
        PreparedStatement pst = (PreparedStatement)rsLst.get(0);
        ResultSet rs = (ResultSet)rsLst.get(1);
        int srno=0;
        while(rs.next()){
            srno = srno+1;
            HashMap dtls = new HashMap();
            dtls.put("SR No", String.valueOf(srno));
            dtls.put("Byr Name", util.nvl(rs.getString("nme")));
            dtls.put("User", util.nvl(rs.getString("usr")));
            dtls.put("Date", util.nvl(rs.getString("dsp_dt_Tm")));
            dtls.put("Client IP",  util.nvl(rs.getString("cl_ip")));
            dtls.put("Country",  util.nvl(rs.getString("cnt")));
            dtls.put("Email Id",  util.nvl(rs.getString("eml")));
            dtlList.add(dtls);
        }
        rs.close();
        pst.close();
        ArrayList itemHdr = new ArrayList();
        itemHdr.add("SR No");
        itemHdr.add("User");
        itemHdr.add("Byr Name");
        itemHdr.add("Email Id");
        itemHdr.add("Date");
        itemHdr.add("Client IP");
        itemHdr.add("Country");
        request.setAttribute("itemHdr",itemHdr);
         request.setAttribute("dtlList", dtlList);
         udf.setPktList(dtlList);
         request.setAttribute("view","Y");
          util.updAccessLog(request,response,"WebLoginAndSrchDtl", "logindtl end");
       return am.findForward("loadDtl");
        }
    }
    public ActionForward loadwebdeactivatedtl(ActionMapping am, ActionForm af, HttpServletRequest request, HttpServletResponse response) throws Exception  {
      HttpSession session = request.getSession(false);
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
      rtnPg=init(request,response,session,util);
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          return am.findForward(rtnPg);   
      }else{
          util.updAccessLog(request,response,"WebLoginAndSrchDtl", "deactivatedtl");
        WebLoginAndSrchDtlForm  udf = (WebLoginAndSrchDtlForm)af;
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
            String usr = util.nvl((String)udf.getValue("usr")).trim();
            String byridn = util.nvl(request.getParameter("nmeID"));
        String frmdte = util.nvl((String)udf.getValue("dtefr"));
        String todte = util.nvl((String)udf.getValue("dteto"));
        udf.reset();
          ArrayList dtlList = new ArrayList();
          ArrayList ary = new ArrayList();
        
            String conQ="";
            if(!usr.equals(""))
                conQ=" and upper(a.usr) = upper('"+usr+"') ";
            if(!byridn.equals("")){
              conQ=conQ+" and upper(c.nme_idn) = ? ";
                ary.add(byridn);
            }
          
        String conStr = ""; 
        
        ArrayList itemHdr = new ArrayList();
        itemHdr.add("Sr No");
        itemHdr.add("Byr Name");
        itemHdr.add("Created On");
        itemHdr.add("Sales Executive");
        if(cnt.equals("hk")){
        conStr = " , D.Grp_Co_Nme GrpComp , decode(Byr.Get_Attrdata('BSDN',B.Nme_Idn),'B','Yes','E','Enquiry','No') Bsdn, Byr.Get_Attrdata('BSDN_DTE',B.Nme_Idn) Bsdn_Dte";
        itemHdr.add("Group Company");
        itemHdr.add("Business Done");
        itemHdr.add("Business Done Date");
        }
        itemHdr.add("Tel No");
        itemHdr.add("Mobile");
        itemHdr.add("Byr Email");
        itemHdr.add("Username");
        itemHdr.add("Country");
        itemHdr.add("Deactive Date");
        
        String conQdte=" and trunc(a.to_dt)=trunc(sysdate)";
        if(!frmdte.equals("") && !todte.equals(""))
        conQdte = " and trunc(a.to_dt) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
        
//      String loginDtlSql = "Select A.usr, byr.get_nm(b.nme_idn) byrNm, lower(byr.get_eml(c.nme_idn,'N')) byreml , to_char(to_dt, 'dd-Mon HH24:mi:ss') dsp_dt_Tm  From Web_Usrs A , Nmerel B  , Mnme C  \n" + 
//            " Where A.Rel_Idn = B.Nmerel_Idn And B.Nme_Idn = C.Nme_Idn "+conQdte+" "+conQ+" ";
        
        String loginDtlSql = " Select Byr.Get_Nm(B.Nme_Idn) Byrnm, to_char(D.Frm_Dte, 'DD-MON-YYYY')  CreatedDte,  Byr.Get_Nm(C.Emp_Idn) SalNm, Byr.Get_Attrdata('TEL_NO',B.Nme_Idn) Tel, "+
         " Byr.Get_Attrdata('MOBILE',B.Nme_Idn) Mob, Lower(Byr.Get_Eml(C.Nme_Idn,'N')) ByrEml , A.Usr Usr, Byr.Get_Country(B.Nme_Idn) ByrCountry, "+
         " To_Char(To_Dt, 'DD-MON-YYYY HH24:mi:ss') Dsp_Dt_Tm "+conStr+" From Web_Usrs A , Nmerel B  , Mnme C , Nme_V D  "+
         " Where A.Rel_Idn = B.Nmerel_Idn And B.Nme_Idn = C.Nme_Idn And C.Nme_Idn = D.Nme_Idn "+conQdte+" "+conQ+" ";
         
         ArrayList rsLst = db.execSqlLst("loginDtl", loginDtlSql, ary);
        PreparedStatement pst = (PreparedStatement)rsLst.get(0);
        ResultSet rs = (ResultSet)rsLst.get(1);
        int srno=0;
        while(rs.next()){
            srno = srno+1;
            HashMap dtls = new HashMap();
            dtls.put("Sr No", String.valueOf(srno));
                        dtls.put("Byr Name", util.nvl(rs.getString("ByrNm")));
                        dtls.put("Created On", util.nvl(rs.getString("CreatedDte")));
                        dtls.put("Sales Executive", util.nvl(rs.getString("SalNm")));
                        if(cnt.equals("hk")){
                        dtls.put("Group Company", util.nvl(rs.getString("GrpComp")));
                        dtls.put("Business Done", util.nvl(rs.getString("Bsdn")));
                        dtls.put("Business Done Date", util.nvl(rs.getString("Bsdn_Dte")));
                        }
                        dtls.put("Tel No", util.nvl(rs.getString("Tel")));
                        dtls.put("Mobile", util.nvl(rs.getString("Mob")));
                        dtls.put("Byr Email", util.nvl(rs.getString("ByrEml")));
                        dtls.put("Username", util.nvl(rs.getString("Usr")));
                        dtls.put("Country",  util.nvl(rs.getString("ByrCountry")));
                        dtls.put("Deactive Date", util.nvl(rs.getString("Dsp_Dt_Tm")));
                        dtlList.add(dtls);
        }
        rs.close();
        pst.close();
        
         session.setAttribute("itemHdr",itemHdr);
         session.setAttribute("dtlList", dtlList);
         udf.setPktList(dtlList);
         request.setAttribute("view","Y");
          util.updAccessLog(request,response,"WebLoginAndSrchDtl", "deactivatedtl end");
       return am.findForward("loadDeactivateDtl");
        }
    }
    public ActionForward createXLDeactivatedtl(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        util.updAccessLog(req,res,"WebLoginAndSrchDtl", "createXLDeactivatedtl start");
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "createXLDeactivatedtl"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
        ArrayList pktList = (ArrayList)session.getAttribute("dtlList");
        HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
        hwb.write(out);
        out.flush();
        out.close();
        util.updAccessLog(req,res,"WebLoginAndSrchDtl", "createXLDeactivatedtl end");
        return null;
        }
    }
    public ActionForward createXLSearchHistory(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        util.updAccessLog(req,res,"WebLoginAndSrchDtl", "createXLSearchHistory start");
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "createXLSearchHistory"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        ArrayList srchIds = (ArrayList)session.getAttribute("srchIdist");
        HashMap srchList = (HashMap)session.getAttribute("srchList");
        HashMap srchVal = (HashMap)session.getAttribute("srchVal");
        ArrayList srchPrp = (ArrayList)session.getAttribute("asHistorySrch");
        HSSFWorkbook hwb = xlUtil.getGenXlcreateXLSearchHistory(srchIds, srchList,srchVal,srchPrp);
        hwb.write(out);
        out.flush();
        out.close();
        util.updAccessLog(req,res,"WebLoginAndSrchDtl", "createXLSearchHistory end");
        return null;
        }
    }
    public ActionForward loadHistory(ActionMapping mapping,
     ActionForm form,
     HttpServletRequest req,
     HttpServletResponse res) throws Exception {
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
           return mapping.findForward(rtnPg);   
       }else{
           util.updAccessLog(req,res,"WebLoginAndSrchDtl", "loadHis");
       WebLoginAndSrchDtlForm udf = (WebLoginAndSrchDtlForm)form;
         udf.reset();
         ASGenricSrch(req,res);
         return mapping.findForward("loadHistory");
         }
     }
    public ActionForward loadActivity(ActionMapping mapping,
     ActionForm form,
     HttpServletRequest req,
     HttpServletResponse res) throws Exception {
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
           return mapping.findForward(rtnPg);   
       }else{
         util.updAccessLog(req,res,"WebLoginAndSrchDtl", "loadAct");
       WebLoginAndSrchDtlForm udf = (WebLoginAndSrchDtlForm)form;
        udf.reset();
        
         return mapping.findForward("loadActivity");
       }
     }
    public ActionForward loadSrch(ActionMapping mapping,
     ActionForm form,
     HttpServletRequest req,
     HttpServletResponse res) throws Exception {
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
           return mapping.findForward(rtnPg);   
       }else{
         util.updAccessLog(req,res,"WebLoginAndSrchDtl", "loadSrch Start");
         WebLoginAndSrchDtlForm udf = (WebLoginAndSrchDtlForm)form;
         udf.reset();
         util.updAccessLog(req,res,"WebLoginAndSrchDtl", "loadSrch end");
         return mapping.findForward("loadSrch");
         }
     }
    public ActionForward fetch(ActionMapping mapping,
     ActionForm form,
     HttpServletRequest req,
     HttpServletResponse res) throws Exception {
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
           return mapping.findForward(rtnPg);   
       }else{
           util.updAccessLog(req,res,"WebLoginAndSrchDtl", "fetch");
        WebLoginAndSrchDtlForm udf = (WebLoginAndSrchDtlForm)form;
         String group=util.nvl((String)udf.getValue("group"));
         String saleEmp=util.nvl((String)udf.getValue("saleEmp"));
             String dtefr = util.nvl((String)udf.getValue("dtefr"));
             String dteto = util.nvl((String)udf.getValue("dteto"));
             String buyerId = util.nvl(req.getParameter("nmeID"));
           
             req.setAttribute("dtefr", dtefr);
             req.setAttribute("dteto", dteto);
             req.setAttribute("buyerId", buyerId);
           
         req.setAttribute("group", group);
         req.setAttribute("saleEmp", saleEmp);
         udf.reset();
         return mapping.findForward("loadSrch");
         }
     }
    public ActionForward loadsrchHistory(ActionMapping mapping,
     ActionForm form,
     HttpServletRequest req,
     HttpServletResponse response) throws Exception {
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
      rtnPg=init(req,response,session,util);
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          return mapping.findForward(rtnPg);   
      }else{
          util.updAccessLog(req,response,"WebLoginAndSrchDtl", "srch His");
      WebLoginAndSrchDtlForm  udf = (WebLoginAndSrchDtlForm)form;
        String usr = util.nvl((String)udf.getValue("usr")).trim();
        String byridn = util.nvl(req.getParameter("nmeID"));
        String frmdte = util.nvl((String)udf.getValue("dtefr"));
        String todte = util.nvl((String)udf.getValue("dteto"));
        udf.reset();
         HashMap mprp = info.getMprp();
         HashMap prp = info.getPrp();
         ArrayList srchPrp = info.getSrchPrpLst();
         ResultSet rs=null;
      int pSrchId = 0 ;
      String dte="";
         String pPrp="";
      String valfr = "";
      HashMap srchVal = new HashMap();
      HashMap srchList = new HashMap();
      String conQ="";
        ArrayList ary = new ArrayList();
        ary.add("WEB_VW");
        if(!usr.equals("")){
            ary.add(usr);
            conQ=conQ+" and g.usr = ?";
        }
        if(!byridn.equals("")){
            ary.add(byridn);
            conQ=conQ+" and h.nme_idn = ?";
        }
        if(!frmdte.equals("") && !todte.equals(""))
        conQ =conQ+ " and trunc(f.dte) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
        else
        conQ =conQ+" and trunc(f.dte) = trunc(sysdate) ";
         
      String getDscr =" select d.srch_id srchId,g.usr,byr.get_nm(h.nme_idn) byrNm, a.prp prp,to_char(f.dte, 'dd-Mon-rr HH24:mi') dte, \n" + 
      "       decode(lag(a.dsc) over (order by e.srt, d.sfr), a.dsc, ',',''||a.dsc) lag_prp,\n" + 
      "      Decode(A.Dta_Typ, 'C', B.Prt1, To_Char(Trunc(D.Sfr,2), '90.00')||'-'||To_Char(Trunc(D.Sto,2), '90.00')) Valfr, Decode(A.Dta_Typ, 'C', C.Prt1, D.Sto) Valto, E.Srt \n" + 
      "      From Mprp A, Prp B, Prp C, Srch_Dtl_Sub D, Rep_Prp E, Msrch F ,Web_Usrs G,Nmerel h\n" + 
      "      Where A.Prp = D.Mprp And C.Mprp = D.Mprp And B.Mprp = D.Mprp And B.Val = D.Vfr And C.Val = D.Vto \n" + 
      "      And D.Mprp = E.Prp And E.Mdl = ? And F.Srch_Id = D.Srch_Id And F.Mdl = 'WWW' \n" + 
      "       and f.rln_id=g.rel_idn and h.nmerel_idn=g.rel_idn and f.rln_id=h.nmerel_idn " + conQ+
      "       order by f.srch_id, e.srt, d.sfr ";

      ArrayList srchIds = new ArrayList();         
            ArrayList outLst = db.execSqlLst("Get DSC",getDscr, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
      HashMap srchHistMap = new HashMap();
      int cnt=0;
      while(rs.next()){
             int lSrchId = rs.getInt("srchId");
             if(pSrchId == 0)
             {
                 pSrchId = lSrchId;
                 dte=util.nvl(rs.getString("dte"));
             }
          
        if(pSrchId != lSrchId) {
            cnt++;
                  srchHistMap.put(pPrp, valfr);
                  srchIds.add(Integer.toString(pSrchId));
                  srchVal.put(pSrchId + "_DTE",util.nvl(rs.getString("dte"))); 
                  srchVal.put(pSrchId + "_USR",util.nvl(rs.getString("usr"))); 
                  srchVal.put(pSrchId + "_BYR",util.nvl(rs.getString("byrNm"))); 
                  srchList.put(Integer.toString(pSrchId), srchHistMap);
                  srchHistMap = new HashMap();
                  pSrchId = lSrchId;
                  valfr = "";
                  pPrp = "";
              }
                  
              String lrpm =util.nvl(rs.getString("prp"));
              String prp1 = util.nvl(rs.getString("lag_prp"));
              String val = util.nvl(rs.getString("valfr"));
              
              
              if(pPrp=="")
                pPrp = lrpm;
              if(!pPrp.equals(lrpm)){
              if(pPrp.equals("CRTWT"))
                  
              srchHistMap.put(pPrp, valfr);
               valfr = val;
               pPrp = lrpm;
              }else{
                  if(valfr.length()>0)
                      valfr = valfr+","+val;
                  else
                      valfr = val;
              }
            
      }
            rs.close();
            pst.close();
     if(cnt==0) {
       srchIds.add(Integer.toString(pSrchId));
       srchVal.put(pSrchId + "_DTE",dte);
     }
      if(pSrchId != 0){
          srchHistMap.put(pPrp, valfr);
          srchList.put(Integer.toString(pSrchId), srchHistMap);
         }
        int size = srchList.size();
        //srchList.put(srchIdnList.get(j),srchHistMap);
      //}
      conQ="";
              ary = new ArrayList();
              if(!usr.equals("")){
                  ary.add(usr);
                  conQ=conQ+" and g.usr = ?";
              }
              if(!byridn.equals("")){
                  ary.add(byridn);
                  conQ=conQ+" and h.nme_idn = ?";
              }
              if(!frmdte.equals("") && !todte.equals(""))
              conQ =conQ+ " and trunc(d.dte) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
              else
              conQ =conQ+" and trunc(d.dte) = trunc(sysdate) ";
      String getSrchDtls = 
             " select d.srch_id srch_id,g.usr,byr.get_nm(h.nme_idn) byrNm, to_char(d.dte, 'dd-Mon-rr HH24:mi') dte, b.mprp prp "+
             ", decode(vfr, 'NA', to_char(nfr), vfr) vfr, decode(vto, 'NA', to_char(nto), vto) vto "+
             " from srch_dtl b, msrch d,Web_Usrs G,Nmerel h "+
             " where d.mdl = 'WWW' "+
             " and b.srch_id = d.srch_id and d.rln_id=g.rel_idn and h.nmerel_idn=g.rel_idn and d.rln_id=h.nmerel_idn"+conQ+
             " and not exists (select 1 from srch_dtl_sub a1 where a1.srch_id = b.srch_id and a1.mprp = b.mprp)"+ 
             " order by d.srch_id ASC ";
        
         int p_rnk = 0, p_srchId = 0 ;    
            outLst = db.execSqlLst(" Srch History ", getSrchDtls, ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
         while(rs.next()) { 
             String l_srchId = rs.getString("srch_id");
             if(!(srchIds.contains(l_srchId)))
                 srchIds.add(l_srchId);
             
             String lprp =util.nvl( rs.getString("prp"));
             String vfr =util.nvl( rs.getString("vfr"));
             String vto =util.nvl( rs.getString("vto"));
             srchVal.put(l_srchId + "_DTE", rs.getString("dte"));   
             srchVal.put(l_srchId + "_USR",util.nvl(rs.getString("usr"))); 
             srchVal.put(l_srchId + "_BYR",util.nvl(rs.getString("byrNm"))); 
             srchVal.put(l_srchId + "_" + lprp + "_fr", vfr);          
             srchVal.put(l_srchId + "_" + lprp + "_to", vto);          
         }
            rs.close();
            pst.close();
      info.setIsMix("");   
      session.setAttribute("srchIdist", srchIds);  
      session.setAttribute("srchVal", srchVal);
      session.setAttribute("srchList", srchList);
        req.setAttribute("view","Y");
        return mapping.findForward("loadHistory");
        }
    }
    
    public ArrayList ASGenricSrch(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
        ArrayList asViewPrp = (ArrayList)session.getAttribute("asHistorySrch");
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();
                ArrayList outLst = db.execSqlLst("Vw Lst ", "Select prp, flg  from rep_prp where mdl = 'HISTORY_SRCH' and flg <> 'N' order by rnk ",
                               new ArrayList());
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs1 = (ResultSet)outLst.get(1);
                while (rs1.next()) {
                    ArrayList asViewdtl=new ArrayList();
                    asViewdtl.add(rs1.getString("prp"));
                    asViewdtl.add(rs1.getString("flg"));
                    asViewPrp.add(asViewdtl);
                }
                rs1.close();
                pst.close();
                session.setAttribute("asHistorySrch", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return asViewPrp;
    }
    public ActionForward webactivitydtl(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception  {
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
        util.updAccessLog(req,res,"WebLoginAndSrchDtl", "web Active dtl");
       WebLoginAndSrchDtlForm   udf = (WebLoginAndSrchDtlForm)af;
             String type = util.nvl((String)udf.getValue("typ"));
             String flg = util.nvl((String)udf.getValue("flg"));
             String frmdte = util.nvl((String)udf.getValue("frmDte"));
             String todte = util.nvl((String)udf.getValue("toDte"));
             ArrayList param=new ArrayList();
             ArrayList actList = new ArrayList();
             String conQ=" and trunc(a.dte)=trunc(sysdate)";
            if(!frmdte.equals("") && !todte.equals(""))
            conQ =" and trunc(a.dte) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
            
            if(!type.equals("")){
                conQ=conQ+" and upper(a.typ)=upper(?) ";
                param.add(type);
                if(type.equals("APPOINTMENT") && !flg.equals("")){
                    conQ=conQ+" and upper(a.flg)=upper(?) ";
                    param.add(flg); 
                }
            }

         
          String actDtlSql = "select a.typ,a.name,a.com_name,a.mobile,a.email,a.commt,a.tel,to_char(a.dte, 'dd-Mon-rr HH24:mi') dte,a.gend,get_citycntry(a.country_Id,'CN') countryNme,get_citycntry(a.city_Id,'CT') cityNme,a.address,a.qual,a.exp_yrs,a.salary,a.time,a.sub,a.filename,a.biz_typ,a.flg,a.dept,a.saleex\n" + 
          "from web_Activity a \n" + 
          " where 1=1 "+conQ+" order by typ,dte desc";
          ArrayList outLst = db.execSqlLst(" Activity Dtl ", actDtlSql, param);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
         while(rs.next()){
            HashMap act = new HashMap();
            act.put("Type", util.nvl(rs.getString("typ")));
            act.put("Name", util.nvl(rs.getString("name")));
            act.put("Compny", util.nvl(rs.getString("com_name")));
            act.put("Mobile",  util.nvl(rs.getString("mobile")));
            act.put("Email",  util.nvl(rs.getString("email")));
            act.put("Commnt",  util.nvl(rs.getString("commt")));
            act.put("PhoneNo",  util.nvl(rs.getString("tel")));
            act.put("Date",  util.nvl(rs.getString("dte")));
            act.put("Gend",  util.nvl(rs.getString("gend")));
            act.put("Current Country",  util.nvl(rs.getString("countryNme")));
            act.put("Current City",  util.nvl(rs.getString("cityNme")));
            act.put("Preferred Location",  util.nvl(rs.getString("address")));
            act.put("Area of Interest",  util.nvl(rs.getString("sub")));
            act.put("Educational Qualification",  util.nvl(rs.getString("qual")));
            act.put("Work Experience (In Yrs.)",  util.nvl(rs.getString("exp_yrs")));
            act.put("Current Salary (In Rs.)",  util.nvl(rs.getString("salary")));
            act.put("Time",  util.nvl(rs.getString("time")));
            act.put("Subject",  util.nvl(rs.getString("sub")));
            act.put("Attachment",  util.nvl(rs.getString("filename"))); 
            act.put("BusinessType",  util.nvl(rs.getString("biz_typ")));
            act.put("Division Name",  util.nvl(rs.getString("dept")));
            act.put("Sales Person",  util.nvl(rs.getString("saleex")));
            actList.add(act);
        }
          rs.close();
          pst.close();
            ArrayList itemHdr = new ArrayList();
            itemHdr.add("Type");
            if(!type.equals("SUGGESTION")){
            itemHdr.add("Name");
            itemHdr.add("Mobile");
            itemHdr.add("Email");
            itemHdr.add("Date");
            if(!type.equals("CAREERZONE"))
                itemHdr.add("Subject");
            }
            if(type.equals("APPOINTMENT")){
            itemHdr.add("Time");
            itemHdr.add("Compny");
            itemHdr.add("Division Name");
            itemHdr.add("Sales Person");
            }
            if(type.equals("CAREERZONE")){
            itemHdr.add("Gend");
            itemHdr.add("Current Country");
            itemHdr.add("Current City");
            itemHdr.add("Preferred Location");
            itemHdr.add("Area of Interest");
            itemHdr.add("Educational Qualification");
            itemHdr.add("Work Experience (In Yrs.)");
            itemHdr.add("Current Salary (In Rs.)");
            }
            if(type.equals("FEEDBACK") || type.equals("ENQUIRY") || type.equals("")){
            itemHdr.add("Gend");
            itemHdr.add("Compny");
            itemHdr.add("BusinessType");
            itemHdr.add("PhoneNo");
            }
            if(!type.equals("CAREERZONE")){
            itemHdr.add("Commnt");
            }
            if(type.equals("CAREERZONE")|| type.equals("SUGGESTION")|| type.equals("")) 
            itemHdr.add("Attachment");
            req.setAttribute("itemHdr",itemHdr);
            req.setAttribute("actList", actList);
            udf.reset();
            req.setAttribute("view", "Y");
        return am.findForward("loadActivity");
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
              rtnPg=util.checkUserPageRights("website/webLoginSrchDtl.jsp","");
              if(rtnPg.equals("unauthorized"))
              util.updAccessLog(req,res,"WebLoginAndSrchDtl", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"WebLoginAndSrchDtl", "init");
          }
          }
          return rtnPg;
          }
}
