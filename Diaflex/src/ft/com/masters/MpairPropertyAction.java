package ft.com.masters;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.FormsUtil;
import ft.com.InfoMgr;
import ft.com.dao.GenDAO;
import ft.com.dao.UIForms;
import ft.com.dao.UIFormsFields;

import ft.com.generic.GenericImpl;

import java.io.IOException;

import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.iterators.IteratorEnumeration;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class MpairPropertyAction extends DispatchAction {
   
    private final String formName   = "mpairprpertyform";



    public MpairPropertyAction() {
        super();
    }
  public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
          util.updAccessLog(req,res,"Matchpair Property", "load start");
          HashMap  formFields = info.getFormFields(formName);

          if ((formFields == null) || (formFields.size() == 0)) {
              formFields = util.getFormFields(formName);
          }

          UIForms  uiForms = (UIForms) formFields.get("DAOS");
          ArrayList  daos    = uiForms.getFields();
          FormsUtil helper  = new FormsUtil();
          ArrayList errors  = new ArrayList();
          helper.init(db, util, info);
      MpairPropertyForm udf = (MpairPropertyForm) af;

    ArrayList    params  = new ArrayList();
    ResultSet rs  = null;
    HashMap mprp = info.getMprp();
    HashMap prp = info.getPrp();
    String rule_idn = req.getParameter("rule_idn");
    
    if(rule_idn==null){
    rule_idn = (String)req.getAttribute("rule_idn");
    }
    
    HashMap  propertyList = new HashMap();
    String currency = "select prp ,prt_nme txt from mprp where prp in (select prp from rep_prp where mdl='Mdl_Match') order by dsc";
    ArrayList ary = new ArrayList();
     rs = db.execSql("currency", currency , ary );
    
    while(rs.next()){
      propertyList.put(rs.getString(1),rs.getString(2)); 
    }
      rs.close();
    String lclList = "SELECT dsc, decode(srt_fr,null,num_fr,srt_fr) srt_fr, decode(srt_to,null,num_to,srt_to) \n" + 
    "        srt_to FROM rule_dtl where rule_idn=? and dsc is not null";
    params.add(rule_idn);
    rs = db.execSql("List", lclList, params);
    ArrayList listtable=new ArrayList();
    
    while(rs.next()){
      ArrayList listprp=new ArrayList();
      String dsc =util.nvl(rs.getString("dsc"));
      String srt_fr =util.nvl(rs.getString("srt_fr"));
      String srt_to =util.nvl(rs.getString("srt_to"));
      listprp.add(dsc);
      listprp.add(srt_fr);
      listprp.add(srt_to);
      listtable .add(listprp);
    }
      rs.close();
      session.setAttribute("listtable", listtable);
      session.setAttribute("propertyList", propertyList);
      udf.setValue("rule_idn", rule_idn);
          util.updAccessLog(req,res,"Matchpair Property", "load end");         
      return am.findForward("view");
      }
  } 
  public ActionForward save(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
                    util.updAccessLog(req,res,"Matchpair Property", "save start");
                    HashMap  formFields = info.getFormFields(formName);

                    if ((formFields == null) || (formFields.size() == 0)) {
                        formFields = util.getFormFields(formName);
                    }

                    UIForms  uiForms = (UIForms) formFields.get("DAOS");
                    ArrayList  daos    = uiForms.getFields();
                    FormsUtil helper  = new FormsUtil();
                    ArrayList errors  = new ArrayList();
                    helper.init(db, util, info);
                MpairPropertyForm udf = (MpairPropertyForm) af;
        ArrayList    params  = new ArrayList();
        HashMap mprp = info.getMprp();
        HashMap prp = info.getPrp();
        String typ=null;
        String sql=null;
        String buttonPressed = "";
        HashMap propertyList = (HashMap)session.getAttribute("propertyList");   
        Enumeration e=new IteratorEnumeration(propertyList.keySet().iterator());
        if(propertyList!=null && propertyList.size()>0){
            for(int i=0;i<propertyList.size();i++){
                String prpid=(String)e.nextElement();
                typ = util.nvl((String)mprp.get(prpid+"T"));
                String srt_fr = util.nvl((String)req.getParameter("frm_"+(i+1)));
                String srt_to = util.nvl((String)req.getParameter("to_"+(i+1)));
                if(!srt_fr.equals("") && !srt_to.equals("") ){
                  
                  if(typ.equals("N"))
                  sql="update rule_dtl set num_fr=?,num_to=? where rule_idn=? and dsc=?"; 
                  else
                  sql="update rule_dtl set srt_fr=?,srt_to=? where rule_idn=? and dsc=?"; 
                
                  params.add(srt_fr);
                  params.add(srt_to);
                  params.add(udf.getValue("rule_idn"));
                  params.add(prpid);
                  int ct = db.execUpd(" update rul_dtl ", sql, params);
                  
                  if(ct<1){
                      
                  if(typ.equals("N"))
                   sql="insert into rule_dtl(rule_dtl_idn,rule_idn,dsc,num_fr,num_to) values(rule_dtl_seq.nextval,?,?,?,?)"; 
                  else
                    sql="insert into rule_dtl(rule_dtl_idn,rule_idn,dsc,srt_fr,srt_to) values(rule_dtl_seq.nextval,?,?,?,?)"; 
                  
                  params.clear();    
                  params.add(udf.getValue("rule_idn"));
                  params.add(prpid);
                  params.add(srt_fr);
                  params.add(srt_to);
                  ct = db.execUpd(" insert rul_dtl ", sql, params);
                  
                  }
                  params.clear();
                }
                }
         }
        req.setAttribute("rule_idn",udf.getValue("rule_idn"));
                    util.updAccessLog(req,res,"Matchpair Property", "save end");
        return load(am, af, req, res);
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
                rtnPg=util.checkUserPageRights("masters/mpair.do?","");
                if(rtnPg.equals("unauthorized"))
                util.updAccessLog(req,res,"Matchpair Property", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Matchpair Property", "init");
            }
            }
            return rtnPg;
         }
}
