package ft.com.pricing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class RapnetAction extends DispatchAction {
    
    public RapnetAction() {
        super();
    }
    public ActionForward load(ActionMapping mapping, ActionForm af,
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
      util.updAccessLog(req,res,"Rapnet", "load");                       
     RapnetForm udf = (RapnetForm)af;
     udf.reset();
     ArrayList rapNetList = new ArrayList();
     String rapSql = "select idn , shape , purity , colour , wt_min , wt_max , price , old_price ,  diff_pct , chg_pct from rapnet_bse where stt in ('NA')  and diff_pct != 0 order by sk1";
     ArrayList outLst = db.execSqlLst("rapSql", rapSql, new ArrayList());
     PreparedStatement pst = (PreparedStatement)outLst.get(0);
     ResultSet rs = (ResultSet)outLst.get(1);
     while(rs.next()){
         HashMap  rapNetMap = new HashMap();
         String idn = util.nvl(rs.getString("idn"));
         rapNetMap.put("idn", util.nvl(rs.getString("idn")));
         rapNetMap.put("shape", util.nvl(rs.getString("shape")));
         rapNetMap.put("purity", util.nvl(rs.getString("purity")));
         rapNetMap.put("colour", util.nvl(rs.getString("colour")));
         rapNetMap.put("wt_min", util.nvl(rs.getString("wt_min")));
         rapNetMap.put("wt_max", util.nvl(rs.getString("wt_max")));
         rapNetMap.put("price", util.nvl(rs.getString("price")));
         rapNetMap.put("oldprice", util.nvl(rs.getString("old_price")));
         rapNetMap.put("diffPct", util.nvl(rs.getString("diff_pct")));
         rapNetList.add(rapNetMap);
         udf.setValue("NEWDIFF_"+idn, util.nvl(rs.getString("chg_pct")));
     }
        rs.close();
     req.setAttribute("rapMapList", rapNetList);
     rs.close();
     pst.close();
      util.updAccessLog(req,res,"Rapnet", "load end");                
      return mapping.findForward("load");
    }
   
  }
    public ActionForward save(ActionMapping mapping, ActionForm af,
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
        util.updAccessLog(req,res,"Rapnet", "save");    
     RapnetForm udf = (RapnetForm)af;
        Enumeration reqNme = req.getParameterNames();
        ArrayList idnLst = new ArrayList();
        while(reqNme.hasMoreElements()) {
            String paramNm = (String)reqNme.nextElement();
            if(paramNm.indexOf("cb_rap_") > -1) {
                String val = util.nvl(req.getParameter(paramNm));
                if(!val.equals("")){
                    val = val.trim();
                    idnLst.add(val);
                }
            }
        }
        for(int i=0;i<idnLst.size();i++){
            String idn = (String)idnLst.get(i);
            String chgVal = util.nvl((String)udf.getValue("NEWDIFF_"+idn));
            String updateRap = "update rapnet_bse set stt=? , chg_pct=? where idn = ? ";
            ArrayList ary = new ArrayList();
            ary.add("P");
            ary.add(chgVal);
            ary.add(idn);
           int ct = db.execUpd("update Rap", updateRap, ary);
           
        }
        if(idnLst.size()>0){
            ArrayList out = new ArrayList();
            out.add("I");
            out.add("V");
            CallableStatement stmt = db.execCall("apply_rap_chng", "prc_data_pkg.apply_rap_chng(pCnt => ? , pMsg => ?)", new ArrayList(), out);
            int cnt = stmt.getInt(1);
            String msg = stmt.getString(2);
            req.setAttribute("msg", msg);
            stmt.close();
        }
    
          util.updAccessLog(req,res,"Rapnet", "save end");    
          return load(mapping, af, req, res);
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
          util.updAccessLog(req,res,"Assort Issue Action", "init");
          }
          }
          return rtnPg;
          }
}
