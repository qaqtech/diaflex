package ft.com.dashboard;
import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.FormsUtil;
import ft.com.InfoMgr;
import ft.com.dao.GenDAO;
import ft.com.dao.UIForms;
import ft.com.dao.UIFormsFields;
import java.sql.Connection;
import ft.com.inward.AssortLabReportForm;
import ft.com.marketing.MemoReportForm;

import ft.com.marketing.SearchQuery;

import ft.com.role.UserRoleForm;

import java.io.IOException;

import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class UserRightsAction extends DispatchAction {
  
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
          util.updAccessLog(req,res,"User Rights", "load start");
    UserRightsForm udf = (UserRightsForm)af;
    ResultSet rs           = null;
    ArrayList    ary          = null;
    ArrayList usridnLst=new ArrayList();
    HashMap userDtl=new HashMap();
    ArrayList boarditemLst=new ArrayList();
    String usrQ=" select upper(usr) usr,usr_id from df_users where stt='A' order by upper(usr)";
      rs = db.execSql("User Details", usrQ, new ArrayList());
      while (rs.next()) {
          String usrid=util.nvl(rs.getString("usr_id"));
          usridnLst.add(usrid); 
          userDtl.put(usrid,util.nvl(rs.getString("usr")));
      }
          rs.close();
      String boardQ = "select b.idn pgidn,a.idn pgitmidn,fld_nme from df_pg_itm a, df_pg b where a.pg_idn = b.idn \n" + 
      "       and b.mdl = ? and a.stt=b.stt and a.stt='A' and b.stt='A' and a.vld_dte is null order by a.itm_typ,a.srt ";
      ary=new ArrayList();
      ary.add("DASH_BOARD");
      rs = db.execSql("Board Items", boardQ, ary);
      while (rs.next()) {
          HashMap data=new HashMap();
          data.put("PGIDN",util.nvl(rs.getString("pgidn")));
          data.put("PGITMIDN",util.nvl(rs.getString("pgitmidn")));
          data.put("ITM",util.nvl(rs.getString("fld_nme")));
          boarditemLst.add(data); 
      }
          rs.close();
      String usrsttQ="select pg_idn,itm_idn,usr_idn from df_pg_itm_usr where stt='A'";
      rs = db.execSql("User Set", usrsttQ, new ArrayList());
      while (rs.next()) {
          String fldname=util.nvl(rs.getString("usr_idn"))+"_"+util.nvl(rs.getString("itm_idn"))+"_"+util.nvl(rs.getString("pg_idn"));
          udf.setValue(fldname, fldname);  
      }
          rs.close();
      req.setAttribute("usridnLst", usridnLst);
      req.setAttribute("userDtl", userDtl);
      req.setAttribute("boarditemLst", boarditemLst);
          util.updAccessLog(req,res,"User Rights", "load end");
      return am.findForward("load");
      }
  } 
  
    public ActionForward loadright(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      util.updAccessLog(req,res,"User Rights", "loadright start");
      UserRightsForm udf = (UserRightsForm)af;
      ResultSet rs           = null;
      ArrayList    ary          = null;
      ArrayList usridnLst=new ArrayList();
      HashMap userDtl=new HashMap();
      ArrayList boarditemLst=new ArrayList();
        String usrIdn=util.nvl(req.getParameter("usrIdn"));
        String boardQ = "select b.idn pgidn,a.idn pgitmidn,fld_nme from df_pg_itm a, df_pg b where a.pg_idn = b.idn \n" + 
        "       and b.mdl = ? and a.stt=b.stt and a.stt='A' and b.stt='A' and a.vld_dte is null order by a.itm_typ,a.srt ";
        ary=new ArrayList();
        ary.add("DASH_BOARD");
        rs = db.execSql("Board Items", boardQ, ary);
        while (rs.next()) {
            HashMap data=new HashMap();
            data.put("PGIDN",util.nvl(rs.getString("pgidn")));
            data.put("PGITMIDN",util.nvl(rs.getString("pgitmidn")));
            data.put("ITM",util.nvl(rs.getString("fld_nme")));
            boarditemLst.add(data); 
        }
            rs.close();
        String usrsttQ="select pg_idn,itm_idn,usr_idn from df_pg_itm_usr where stt='A'";
        rs = db.execSql("User Set", usrsttQ, new ArrayList());
        while (rs.next()) {
            String fldname=util.nvl(rs.getString("usr_idn"))+"_"+util.nvl(rs.getString("itm_idn"))+"_"+util.nvl(rs.getString("pg_idn"));
            udf.setValue(fldname, fldname);  
        }
            rs.close();
        req.setAttribute("usrIdn", usrIdn);
        req.setAttribute("boarditemLst", boarditemLst);
            util.updAccessLog(req,res,"User Rights", "loadright end");
        return am.findForward("loadright");
        }
    } 
    
    public ActionForward loadDf(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"User Rights", "loadDf start");
        UserRightsForm udf = (UserRightsForm) af;
        ResultSet rs =null;
        udf.reset();
        ArrayList dfpgdtl=new ArrayList();
        String sql = "  Select distinct A.Dsc dsc,A.Idn pgidn From Df_Pg A,Df_Pg_Itm B\n" + 
        "    where A.Idn = B.Pg_Idn and a.typ='DF' and b.flg2='DF' order by A.Dsc";
        rs = db.execSql("usrpageDf", sql, new ArrayList());
        while(rs.next()){
            HashMap dtl=new HashMap();
            dtl.put("DSC",util.nvl(rs.getString("dsc")));
            dtl.put("PGIDN",util.nvl(rs.getString("pgidn")));
            dfpgdtl.add(dtl);
        }
        rs.close();
        session.setAttribute("DFPG", dfpgdtl);
            util.updAccessLog(req,res,"User Rights", "loadDf end");
        return am.findForward("loadeusrdf");
        }
    }
    
    public ActionForward fetchDf(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        util.updAccessLog(req,res,"User Rights", "fetchDf start");
        UserRightsForm udf = (UserRightsForm) af;
        ResultSet rs =null;
        ArrayList usrdtl = (session.getAttribute("DF_USRS") == null)?new ArrayList():(ArrayList)session.getAttribute("DF_USRS");
        String pgidn=util.nvl((String)udf.getValue("pg"));
        ArrayList ary=new ArrayList();
        if(usrdtl.size()==0){
        String sql = "Select usr,usr_id from df_users where stt='A' and nvl(flg,'NA') not in('SYS')  order by usr";
        rs = db.execSql("usrRole", sql, new ArrayList());
        while(rs.next()){
            HashMap dtl=new HashMap();
            dtl.put("USR",util.nvl(rs.getString("usr")));
            dtl.put("USR_ID",util.nvl(rs.getString("usr_id")));
            usrdtl.add(dtl);
        }
        rs.close();
        session.setAttribute("DFUSRS", usrdtl);
        }
        ArrayList dfpgdtl=new ArrayList();
        ary.add(pgidn);
        String sql = "Select b.idn pgitmidn,nvl(Fld_Ttl,Fld_Nme) Fld_Ttl" + 
        "     From Df_Pg A,Df_Pg_Itm B\n" + 
        "     where A.Idn = B.Pg_Idn and a.idn=? and a.typ='DF' and b.flg2='DF' and b.stt='A' and b.flg='A' order by b.itm_typ,b.srt";
        rs = db.execSql("usrpageDf", sql, ary);
        while(rs.next()){
            HashMap dtl=new HashMap();
            dtl.put("PGITMIDN",util.nvl(rs.getString("pgitmidn")));
            dtl.put("FLDTTL",util.nvl(rs.getString("Fld_Ttl")));
            dfpgdtl.add(dtl);
        }
        rs.close(); 
        sql = "select usr_idn,pg_idn,itm_idn,stt from df_pg_itm_usr where stt='IA' and pg_idn=?";
                rs = db.execSql("rights", sql, ary);
                while(rs.next()){
                    String fldname=util.nvl(rs.getString("usr_idn"))+"_"+util.nvl(rs.getString("itm_idn"));
                    udf.setValue(fldname, fldname);  
                }
        rs.close(); 
        session.setAttribute("DFPGITM", dfpgdtl);
        req.setAttribute("view", "Y");
        udf.setValue("pg", pgidn);
            util.updAccessLog(req,res,"User Rights", "fetchDf end");
        return am.findForward("loadeusrdf");
        }
    }
    
    public ActionForward savepg(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        String stt = req.getParameter("stt");
        String pgidn = req.getParameter("pgidn");
        String pgitmidn = req.getParameter("pgitmidn");
        String usrid = req.getParameter("usrid");    
        ArrayList params  = new ArrayList();
        String sql="";
            if(stt.equals("true"))
            stt="IA";
            else
            stt="A";
      sql="update df_pg_itm_usr set stt=? where pg_idn=? and itm_idn=? and usr_idn=?";
      params  = new ArrayList();
      params.add(stt);
      params.add(pgidn);
      params.add(pgitmidn);
      params.add(usrid);
      int ct = db.execDirUpd(" update df_pg_itm_usr ", sql, params);
      if(ct<1){
        params.clear();
        params.add(pgitmidn);
        params.add(pgidn);
        params.add(usrid);
        params.add(stt);
        sql="insert into df_pg_itm_usr(idn,itm_idn,pg_idn,usr_idn,stt) values(df_pg_itm_usr_seq.nextval,?,?,?,?)"; 
        ct = db.execDirUpd(" insert df_pg_itm_usr ", sql, params);
      }
      return null;
    }
    
    public ActionForward savepgall(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        String stt = req.getParameter("stt");
        String pgidn = req.getParameter("pgidn");
        String pgitmidn = req.getParameter("pgitmidn");  
        ArrayList dfusersdtl = (session.getAttribute("DFUSRS") == null)?new ArrayList():(ArrayList)session.getAttribute("DFUSRS");
        ArrayList params  = new ArrayList();
        HashMap dtl=new HashMap();
        String sql="";
        int dfusersdtlsz=dfusersdtl.size();
        if(stt.equals("true"))
        stt="IA";
        else
        stt="A";
        for(int i=0;i<dfusersdtlsz;i++){
        dtl=new HashMap();
        dtl=(HashMap)dfusersdtl.get(i);
        String usrid=(String)dtl.get("USR_ID");
        sql="update df_pg_itm_usr set stt=? where pg_idn=? and itm_idn=? and usr_idn=?";
        params  = new ArrayList();
        params.add(stt);
        params.add(pgidn);
        params.add(pgitmidn);
        params.add(usrid);
        int ct = db.execDirUpd(" update df_pg_itm_usr ", sql, params);
        if(ct<1){
        params.clear();
        params.add(pgitmidn);
        params.add(pgidn);
        params.add(usrid);
        params.add(stt);
        sql="insert into df_pg_itm_usr(idn,itm_idn,pg_idn,usr_idn,stt) values(df_pg_itm_usr_seq.nextval,?,?,?,?)"; 
        ct = db.execDirUpd(" insert df_pg_itm_usr ", sql, params);
      }
      }
      return null;
    }
    public UserRightsAction() {
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
            util.updAccessLog(req,res,"User Rights", "init");
            }
            }
            return rtnPg;
            }
}
