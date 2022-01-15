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

import java.sql.Connection;
import java.sql.ResultSet;

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

public class XrtRteAction extends DispatchAction {
    private final String formName   = "xrtrteform";


    public XrtRteAction() {
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
            util.updAccessLog(req,res,"Xrt Rate", "load start");
        XrtRteForm udf = (XrtRteForm)af;
        
      
        ResultSet rs  = null;
      
       ArrayList  curList = new ArrayList();
        String currency = "select cur_nme from  mcur where flg=? order by cur_nme";
        ArrayList ary = new ArrayList();
        ary.add("A");
         rs = db.execSql("currency", currency , ary );
        
        while(rs.next()){
        curList.add(rs.getString("cur_nme"));
        }
        rs.close();
        String lclXrt = "select cur , xrt,to_char(fr_dte, 'dd-Mon HH24:mi:ss') fr_dte from lcl_xrt where to_dte is null order by cur";
        rs = db.execSql("xrt", lclXrt, new ArrayList());
        while(rs.next()){
            String cur=util.nvl((String)rs.getString("cur"));
            udf.setValue(cur, rs.getString("xrt"));
            udf.setValue(cur+"_FRDTE", rs.getString("fr_dte"));
        }
        rs.close();
        xrtHistory(req,res,udf);
        session.setAttribute("curList", curList);
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("XRTRTE_FORM");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("XRTRTE_FORM");
            allPageDtl.put("XRTRTE_FORM",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            util.updAccessLog(req,res,"Xrt Rate", "load end");
        return am.findForward("view");
        }
    }
    public void xrtHistory(HttpServletRequest req, HttpServletResponse res,XrtRteForm udf)throws Exception{
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList ary=new ArrayList();
        ArrayList dteLst=new ArrayList();
        HashMap dbinfo = info.getDmbsInfoLst();
        String dfltdays =util.nvl((String)dbinfo.get("DFLT_XRT_DAYS"),"7");
        String sqlQ="  Select cur , xrt,To_Char(Fr_Dte, 'dd-Mon HH24:mi:ss') fr_dte,To_Char(to_dte, 'dd-Mon HH24:mi:ss')\n" + 
        " from lcl_xrt where trunc(fr_dte)>=trunc(sysdate)-? order by Fr_Dte desc" ;
        ary.add(dfltdays);
        ResultSet rs = db.execSql("location", sqlQ, ary);
        while(rs.next()){
            String frdt=util.nvl((String)rs.getString("fr_dte"));
            String cur=util.nvl((String)rs.getString("cur"));
            if(!dteLst.contains(frdt))
                dteLst.add(frdt);
            udf.setValue(cur+"_"+frdt, rs.getString("xrt"));
        }
        rs.close();
        req.setAttribute("dteLst", dteLst);
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
            util.updAccessLog(req,res,"Xrt Rate", "save start");
        XrtRteForm udf = (XrtRteForm)af;
        ArrayList curList = (ArrayList)session.getAttribute("curList");
        if(curList!=null && curList.size()>0){
            for(int i=0;i<curList.size();i++){
                String cur = (String)curList.get(i);
                String xrt = util.nvl((String)udf.getValue(cur));
                if(!xrt.equals("")){
                String insertXrt = "insert into lcl_xrt(cur,xrt) values(?,?)";
                ArrayList ary = new ArrayList();
                ary.add(cur);
                ary.add(xrt);
                int ct = db.execUpd("insertXrt", insertXrt, ary);
                }
         }
        }
       
            util.updAccessLog(req,res,"Xrt Rate", "save end");
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
                util.updAccessLog(req,res,"Xrt Rate", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Xrt Rate", "init");
            }
            }
            return rtnPg;
         }
}

  

