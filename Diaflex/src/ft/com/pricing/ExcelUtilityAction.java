package ft.com.pricing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.contact.MailForm;
import ft.com.dao.GenDAO;
import ft.com.dao.MNme;
import ft.com.marketing.SearchQuery;

import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ExcelUtilityAction extends DispatchAction {
    
    public ExcelUtilityAction() {
        super();
    }
    public ActionForward load(ActionMapping mapping,
                                 ActionForm af,
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
          util.updAccessLog(req,res,"ExcelUtility", "load");
        ExcelUtilityForm udf = (ExcelUtilityForm)af;
        udf.resetAll();
        ArrayList seqList = getSeq(req);
        udf.setSeqList(seqList);
          util.updAccessLog(req,res,"ExcelUtility", "load end");
     return mapping.findForward("load");
        }
    }
    
    public ActionForward view(ActionMapping mapping,
                                 ActionForm af,
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
          util.updAccessLog(req,res,"ExcelUtility", "view");
        ExcelUtilityForm udf = (ExcelUtilityForm)af;
        ArrayList params = new ArrayList();
        ArrayList priExlDtl=new ArrayList();
        HashMap exlDtl=null;
        String seq = util.nvl((String)udf.getValue("Sequence"));  
        params.add(seq);
        String loadSeq="select idn,col01,col02,col03,col04,col05,col06,col07,col08,col09,load_seq,flg from price_upd_ora where load_seq=?";
       
          ArrayList outLst = db.execSqlLst("Excel Utility", loadSeq, params);  
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while(rs.next()) {
                exlDtl=new HashMap();
                exlDtl.put("idn", util.nvl(rs.getString("idn")));
                exlDtl.put("seq", util.nvl(rs.getString("load_seq")));
                exlDtl.put("col01", util.nvl(rs.getString("col01")));
                exlDtl.put("col02", util.nvl(rs.getString("col02")));
                exlDtl.put("col03", util.nvl(rs.getString("col03")));
                exlDtl.put("col04", util.nvl(rs.getString("col04")));
                exlDtl.put("col05", util.nvl(rs.getString("col05")));
                exlDtl.put("col06", util.nvl(rs.getString("col06")));
                exlDtl.put("col07", util.nvl(rs.getString("col07")));
                exlDtl.put("col08", util.nvl(rs.getString("col08")));
                exlDtl.put("col09", util.nvl(rs.getString("col09")));
                exlDtl.put("flg", util.nvl(rs.getString("flg")));
                
                priExlDtl.add(exlDtl);
                udf.setValue("stt_"+util.nvl(rs.getString("idn")), util.nvl(rs.getString("flg")));
            }
                rs.close();
              pst.close();
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
        if(priExlDtl !=null && priExlDtl.size()>0){
            session.setAttribute("priExlDtlList", priExlDtl);
            req.setAttribute("view", "Y");
        }else{
            req.setAttribute("msg", "Sequence is Not Available");
        }
        udf.setValue("Sequence",seq);
          util.updAccessLog(req,res,"ExcelUtility", "view end");
     return mapping.findForward("excelutility");
        }
    }
    public ActionForward revert(ActionMapping mapping,
                                 ActionForm af,
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
          util.updAccessLog(req,res,"ExcelUtility", "revert");
        ExcelUtilityForm udf = (ExcelUtilityForm)af;
        ArrayList params = new ArrayList();
        String seq = util.nvl((String)req.getParameter("seqid"));
        if(!seq.equals("")){
        params.add(seq);
            String msg = "";
            int cnt = 0;
        ArrayList out = new ArrayList();
        out.add("I");
        out.add("V");
            String rvt = "Prc_data_pkg.del_file(PLoadSeq => ?, pCnt => ?, pMsg => ?)";
            CallableStatement ct = db.execCall("Excel Utility", rvt, params, out);
            cnt = ct.getInt(2);
            msg = ct.getString(3);
               req.setAttribute("msg", msg);
        }
        udf.setValue("Sequence",seq); 
          util.updAccessLog(req,res,"ExcelUtility", "revert end");
     return view(mapping, udf, req, res);
        }
    }
    
    public ActionForward save(ActionMapping mapping,
                                 ActionForm af,
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
          util.updAccessLog(req,res,"ExcelUtility", "save");
        ExcelUtilityForm udf = (ExcelUtilityForm)af;
        ArrayList params = new ArrayList();
        String seq =util.nvl((String)udf.getValue("Sequence"));
        String selectAll  = util.nvl(req.getParameter("sttth"));
        if(!selectAll.equals("") && !selectAll.equals("NA")){
            params = new ArrayList();
            params.add(selectAll);
            params.add(seq);
            db.execUpd("update","update price_upd_ora set flg = ? where load_seq = ?", params);
        }else{
        ArrayList priExlDtlList = (ArrayList)session.getAttribute("priExlDtlList");
        for(int i=0;i<priExlDtlList.size();i++){
            HashMap exlDtl = (HashMap)priExlDtlList.get(i);
            String idn=(String)exlDtl.get("idn");
            String flg = util.nvl((String)udf.getValue("stt_"+idn));
            String upexcelUtility = "update price_upd_ora set flg = ? where load_seq = ? and idn=? ";
            params= new ArrayList();
            params.add(flg);
            params.add(seq);
            params.add(idn);
            int ct=db.execUpd("update price_upd_ora", upexcelUtility,params);
            
        }}
        
        
         params = new ArrayList();
        params.add(seq);
        String msg = "";
        if(!seq.equals("")){
        
           
            int cnt = 0;
        ArrayList out = new ArrayList();
        out.add("I");
        out.add("V");
            String rvt = "Prc_data_pkg.apply_file(PLoadSeq => ?, pCnt => ?, pMsg => ?)";
            CallableStatement ct = db.execCall("Excel Utility", rvt, params, out);
            cnt = ct.getInt(2);
            msg = ct.getString(3);
               
        }
       
        req.setAttribute("msg", msg);
        udf.setValue("Sequence",seq); 
          util.updAccessLog(req,res,"ExcelUtility", "save end");
     return view(mapping, udf, req, res);
        }
    }
    
    public ActionForward loadpg(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
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
          return mapping.findForward(rtnPg);   
      }else{
          util.updAccessLog(request,response,"ExcelUtility", "load pg");
        ExcelUtilityForm udf = (ExcelUtilityForm)form;
        String upexcelUtility = "update price_upd_ora set flg = ? where load_seq = ? and idn=? ";
        ArrayList ary = new ArrayList();
        String flg = util.nvl(request.getParameter("flg"));
        String seq = util.nvl(request.getParameter("seq"));
        String idn = util.nvl(request.getParameter("idn"));
        ary = new ArrayList();
        ary.add(flg);
        ary.add(seq);
        ary.add(idn);
        int ct=db.execUpd("update price_upd_ora", upexcelUtility, ary);
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write("<msg>yes</msg>");
        return null;
        }
    }
    public ActionForward checkALL(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
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
          return mapping.findForward(rtnPg);   
      }else{
       
        String flg = util.nvl(request.getParameter("flg"));
        String seq = util.nvl(request.getParameter("seq"));
        ArrayList ary = new ArrayList();
        String conQ="";
        if(flg.equals("NA")){
            conQ=" and flg='NA'";
        }
        
        if(flg.equals("Y")){
            conQ=" and flg in('NA','Y')";
            
        }
        if(flg.equals("DEL")){
            conQ=" and flg='Y'";
        }
        
        String upexcelUtility = "update price_upd_ora set flg = ? where load_seq=? ";
        ary.add(flg);
        ary.add(seq);
        int ct=db.execUpd("update price_upd_ora", upexcelUtility+conQ, ary);
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write("<msg>yes</msg>");
        return null;
        }
    }
    
    public ArrayList getSeq(HttpServletRequest req){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
        ArrayList ary = new ArrayList();
        ArrayList seqList = new ArrayList();
        String seqSql = "select distinct load_seq from price_upd_ora where trunc(dte) >= trunc(sysdate) - 3 and flg = 'NA' order by 1 desc";
     //   String empSql = "select distinct load_seq from price_upd_ora order by 1 desc";
        
     ArrayList outLst = db.execSqlLst("empSql", seqSql, ary);
     PreparedStatement pst = (PreparedStatement)outLst.get(0);
     ResultSet rs = (ResultSet)outLst.get(1);
     try {
            while (rs.next()) {
                GenDAO gen=new GenDAO();
                gen.setIdn(rs.getString("load_seq"));
                gen.setNmeIdn(rs.getString("load_seq"));
                seqList.add(gen);
            }
            rs.close();
         pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return seqList;
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
          util.updAccessLog(req,res,"Assort Issue Action", "init");
          }
          }
          return rtnPg;
          }
}
