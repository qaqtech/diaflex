package ft.com.marketing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.dao.UIForms;

import java.io.IOException;
import java.io.OutputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class MemoXLAction extends DispatchAction {

    public MemoXLAction() {
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
        MemoXLForm udf = (MemoXLForm)af;
        SearchQuery query=new SearchQuery();
        ArrayList    vwPrpLst          = null;
        vwPrpLst = new ArrayList();
        util.updAccessLog(req,res,"Memo Excel Format", "Memo Excel Format load");
        ArrayList  outLst = db.execSqlLst(" Vw Memo Lst ", "Select prp  from rep_prp where mdl = 'MEMO_VW_XL' and flg='Y' order by rnk ", new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet  rs = (ResultSet)outLst.get(1);
        while(rs.next()) {
              vwPrpLst.add(rs.getString("prp"));
        }
        rs.close();
        pst.close();
            req.setAttribute("vwPrpLst", vwPrpLst);
        ArrayList repPrpList = query.getMemoXlGrpFrm(req,res);
        String mdlTyp = util.nvl(req.getParameter("mdl"));
        udf.setValue("memoXlList",repPrpList);
        if(!mdlTyp.equals(""))
        udf.setValue("mdl",mdlTyp);
        else
        udf.setValue("mdl","MEMO_VW_XL");
            if(info.getVwPrpLst()==null)
            util.initSrch();  
        util.updAccessLog(req,res,"Memo Excel Format", "Memo Excel Format load end");
        return am.findForward("load");
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
             util.updAccessLog(req,res,"Memo Excel Format", "Memo Excel Format save");
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
         Boolean saveBtn = null;

         saveBtn = Boolean.valueOf(true);
         if (req.getParameter("save") != null) 
             saveBtn = Boolean.valueOf(true);
         
        MemoXLForm udf = (MemoXLForm) af;
        ArrayList ary = null;
         String xlMdl= "";
          
           
            xlMdl= util.nvl((String)udf.getValue("formatNme"));
            xlMdl = xlMdl.replaceAll("_MEMOXL","");
            xlMdl = xlMdl.trim();
            xlMdl = xlMdl.replaceAll(" ", "");
            if(!xlMdl.equals("")){
            xlMdl = xlMdl+"_MEMOXL";
            xlMdl = xlMdl.toUpperCase();
            ary = new ArrayList();
            ary.add(xlMdl);
            int ct1 = db.execUpd("delete","delete from rep_prp where mdl=?", ary);
            ArrayList vwPrpList = new ArrayList();
              ArrayList  outLst = db.execSqlLst(" Vw Memo Lst ", "Select prp  from rep_prp where mdl = 'MEMO_VW_XL' and flg='Y' order by rnk ", new ArrayList());
  
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet  rs = (ResultSet)outLst.get(1);
               
                while(rs.next()) {
                    
                    vwPrpList.add(rs.getString("prp"));
                }
                rs.close();
                pst.close();
            int count = 0;
            for(int i=0;i < vwPrpList.size();i++){
                
                String prp = (String)vwPrpList.get(i);
                String fldPrp =util.nvl((String)udf.getValue(prp));
                if(!fldPrp.equals("")){
                    count++;
                    int srt = count * 10;
                    ary = new ArrayList();
                    ary.add(xlMdl);
                    ary.add(prp);
                    ary.add(Integer.toString(srt));
                    ary.add(Integer.toString(count));
                    ary.add("Y");
                    String insertGrpDtl =
                        "insert into rep_prp (mdl , prp, srt, rnk, flg) select ?,?,?,?,? from dual";
                   int ct = db.execUpd("insertGrpDtl", insertGrpDtl, ary);
                }
                
            }
            udf.setValue("formatNme",xlMdl);
            }
            
               
            
        
             util.updAccessLog(req,res,"Memo Excel Format", "Memo Excel Format save end");       
         return load(am, af, req, res);
         }
     }
    public ActionForward loadFmt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        MemoXLForm udf = (MemoXLForm) af;
        String mdlTyp = req.getParameter("mdl");
        util.updAccessLog(req,res,"Memo Excel Format", "Memo Excel Format loadFmt"); 
        String rep_prpMdl = "select prp from rep_prp where mdl=?";
        ArrayList ary = new ArrayList();
        ary.add(mdlTyp);
          ArrayList  outLst = db.execSqlLst("mdl", rep_prpMdl, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            String prp = util.nvl(rs.getString("prp"));
            udf.setValue(prp , prp);
        }
        rs.close();
        pst.close();
        udf.setValue("formatNme", mdlTyp);
        udf.setValue("isUpdate", "YES");
        req.setAttribute("mdlUp", "Y");
            util.updAccessLog(req,res,"Memo Excel Format", "Memo Excel Format loadFmt end"); 
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
                rtnPg=util.checkUserPageRights("",util.getFullURL(req));
                if(rtnPg.equals("unauthorized"))
                util.updAccessLog(req,res,"Memo Excel Format", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Memo Excel Format", "init"); 
            }
            }
            return rtnPg;
            }
}