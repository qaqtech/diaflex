package ft.com.masters;
import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.FormsUtil;
import ft.com.InfoMgr;
import java.util.HashMap;
import ft.com.dao.UIForms;

import ft.com.generic.GenericImpl;
import ft.com.marketing.SearchQuery;

import java.io.IOException;
import java.io.OutputStream;

import java.sql.Connection;
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

public class AnalysisGridFmtAction extends DispatchAction {

    public AnalysisGridFmtAction() {
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
            util.updAccessLog(req,res,"Analysis grid Format", "load start");
        AnalysisGridFmtForm udf = (AnalysisGridFmtForm)af;
        ResultSet rs           = null;
        ArrayList vwPrpLst= new ArrayList();
        ArrayList gridfmt= new ArrayList();
        rs = db.execSql(" Vw Memo Lst ", "Select prp  from rep_prp where mdl = 'ANAL_GRID' and flg='Y' order by rnk ", new ArrayList());
        while(rs.next()) {
        vwPrpLst.add(rs.getString("prp"));
        }
        rs.close();
        String sqlQ="select distinct a.nme,a.idn from  ANLS_GRP a,ANLS_GRP_PRP b\n" + 
        "where a.nme=b.nme and a.stt='A' and b.stt='A' and a.vld_dte is null and b.vld_dte is null order by a.idn";
        rs = db.execSql(" Vw Memo Lst ",sqlQ, new ArrayList());
        while(rs.next()) {
        gridfmt.add(rs.getString("nme"));
        }
            rs.close();
        req.setAttribute("gridfmt", gridfmt);
        session.setAttribute("gridfmtPrp", vwPrpLst);
        udf.reset();
            util.updAccessLog(req,res,"Analysis grid Format", "load end");
        return am.findForward("load");
        }
    }
    public ActionForward delete(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
         util.updAccessLog(req,res,"Analysis grid Format", "delete start");
         AnalysisGridFmtForm udf = (AnalysisGridFmtForm)af;
         String gridfmt = util.nvl((String) udf.getValue("gridfmt"));
         ArrayList ary=new ArrayList();
         ary.add(gridfmt);
         int ct =db.execUpd(" Del Old Pkts ", "Delete from ANLS_GRP_PRP where nme=?", ary);
         ct =db.execUpd(" Del Old Pkts ", " Delete from ANLS_GRP where nme=?",ary);
         req.setAttribute("MSG", gridfmt+" Format Deleted Sucessfully");
         util.updAccessLog(req,res,"Analysis grid Format", "delete end");
         return load(am, af, req, res);
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
            util.updAccessLog(req,res,"Analysis grid Format", "save start");
         AnalysisGridFmtForm udf = (AnalysisGridFmtForm)af;
            String key="";
            ResultSet rs =null;
            ArrayList gridfmttyp=new ArrayList();
            ArrayList commLst=new ArrayList();
            ArrayList rowLst=new ArrayList();
            ArrayList colLst=new ArrayList();
            ArrayList params=new ArrayList();
            String formatNme=util.nvl((String)udf.getValue("formatNme")).trim();
            gridfmttyp.add("COMM");
            gridfmttyp.add("ROW");
            gridfmttyp.add("COL");
                int ct=0;
            ArrayList gridfmtPrp = (ArrayList)session.getAttribute("gridfmtPrp");
            HashMap dbinfo = info.getDmbsInfoLst();
            for(int i=0;i<gridfmttyp.size();i++){
                String gridtyp=(String)gridfmttyp.get(i);
                for(int j=0;j<gridfmtPrp.size();j++){
                    String lprp=(String)gridfmtPrp.get(j);
                    String val=util.nvl((String)udf.getValue(gridtyp+"_"+lprp));
                if(!val.equals("")){
                if(gridtyp.equals("COMM"))
                    commLst.add(lprp);   
                if(gridtyp.equals("ROW"))
                    rowLst.add(lprp);   
                if(gridtyp.equals("COL"))
                    colLst.add(lprp);   
                }
            }  
            }
            if(commLst.size()==0){
                commLst.add((String)dbinfo.get("SHAPE")); 
                commLst.add((String)dbinfo.get("SIZE")); 
            }
            if(rowLst.size()==0)
                rowLst.add((String)dbinfo.get("COL")); 
            if(colLst.size()==0)
                colLst.add((String)dbinfo.get("CLR")); 
                
            String comm=commLst.toString();
            String row=rowLst.toString();
            String col=colLst.toString();
            comm=comm.replaceAll("\\[","");
            comm=comm.replaceAll("\\]","");
            comm=comm.replaceAll(",","-").trim();
            comm=comm.replaceAll(" ","");
            row=row.replaceAll("\\[","");
            row=row.replaceAll("\\]","");
            row=row.replaceAll(",","_").trim();
            col=col.replaceAll("\\[","");
            col=col.replaceAll("\\]","");
            col=col.replaceAll(",","_").trim();
            key=comm+"_"+row+"_"+col;
            if(!formatNme.equals(""))
                key= formatNme;  
            int lSeqVal=0;
            String sql = " select ANLS_GRP_SEQ.nextval seqVal from dual ";
            rs = db.execSql(" get seq val ", sql, new ArrayList());
            if(rs.next()) 
            lSeqVal = rs.getInt(1);
            rs.close();
            String insertmainQ="Insert into ANLS_GRP (idn, nme,srt, cmn_cnt, row_cnt , col_cnt , stt , frm_dte) \n" + 
            " select ? ,?,?,?,?,?,?,sysdate from dual";
            params.add(String.valueOf(lSeqVal));
            params.add(key);
            params.add(String.valueOf(lSeqVal));
            params.add(String.valueOf(commLst.size()));
            params.add(String.valueOf(rowLst.size()));
            params.add(String.valueOf(colLst.size()));
            params.add("A");
            ct = db.execDirUpd("insert gt",insertmainQ, params);
            if(ct>0){
            String insertsubQ=" Insert into ANLS_GRP_PRP (idn, nme, mprp,srt, typ , stt , frm_dte) \n" + 
            " select BULK_TRANS_IDN_SEQ.nextval ,?,?,?,?,?,sysdate from dual";
                for(int i=0;i<gridfmttyp.size();i++){
                    String gridtyp=(String)gridfmttyp.get(i);
                    for(int j=0;j<gridfmtPrp.size();j++){
                        String lprp=(String)gridfmtPrp.get(j);
                    String val=util.nvl((String)udf.getValue(gridtyp+"_"+lprp));
                    if(!val.equals("")){
                    params=new ArrayList();
                    params.add(key);
                    params.add(lprp);  
                    params.add(String.valueOf(j));
                    params.add(gridtyp);  
                    params.add("A"); 
                    ct = db.execDirUpd("insertsubQ",insertsubQ, params);  
                    }
                }  
                }
                req.setAttribute("MSG",key +" Format Created Sucessfully");
            }else{
                params=new ArrayList();
                params.add(key);
                rs = db.execSql("ANLS_GRP", "Select nme  from ANLS_GRP where nme = ?",params);
                if(rs.next()) {
                req.setAttribute("MSG", key+" Format Already Exists");
                }
                rs.close();
            }
            util.updAccessLog(req,res,"Analysis grid Format", "save end");
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
            util.updAccessLog(req,res,"Analysis grid Format", "loadFmt start");
        AnalysisGridFmtForm udf = (AnalysisGridFmtForm) af;
        String mdlTyp = req.getParameter("mdl");
        String rep_prpMdl = "select prp from rep_prp where mdl=?";
        ArrayList ary = new ArrayList();
        ary.add(mdlTyp);
        ResultSet rs = db.execSql("mdl", rep_prpMdl, ary);
        while(rs.next()){
            String prp = util.nvl(rs.getString("prp"));
            udf.setValue(prp , prp);
        }
        rs.close();
        udf.setValue("formatNme", mdlTyp);
        udf.setValue("isUpdate", "YES");
        req.setAttribute("mdlUp", "Y");
            util.updAccessLog(req,res,"Analysis grid Format", "loadFmt end");
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
            util.updAccessLog(req,res,"Analysis grid Format", "Unauthorized Access");
            else
            util.updAccessLog(req,res,"Analysis grid Format", "init");
            }
            }
            return rtnPg;
         }
}