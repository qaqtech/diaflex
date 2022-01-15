package ft.com.masters;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.SearchQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class EmpDepAction extends DispatchAction {

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
        util.updAccessLog(req,res,"Sale Ex Allocation", "load start");
        EmpDepForm udf = (EmpDepForm)af;
        udf.resetALL();
        SearchQuery srchQuery = new SearchQuery();
        ArrayList empLst = srchQuery.getEmpList(req,res);
        udf.setEmpList(empLst);
        GenericInterface genericInt = new GenericImpl();
        ArrayList empdepprpLst = (ArrayList)session.getAttribute("empdepprpLst");     
        if (empdepprpLst == null) {     
        empdepprpLst = genericInt.genericPrprVw(req,res,"EMP_DEP_MDL","EMP_DEP_MDL");     
        session.setAttribute("empdepprpLst",empdepprpLst);     
        }
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("EMP_DEP_FORM");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("EMP_DEP_FORM");
        allPageDtl.put("EMP_DEP_FORM",pageDtl);
        }
        info.setPageDetails(allPageDtl);     
        util.updAccessLog(req,res,"Sale Ex Allocation", "load end");
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
         util.updAccessLog(req,res,"Sale Ex Allocation", "save start");
         ArrayList empdepprpLst = (ArrayList)session.getAttribute("empdepprpLst");
         EmpDepForm udf = (EmpDepForm)af;
         ArrayList ary = new ArrayList();
         String row = util.nvl((String)req.getParameter("ROWCNT"));    
         int rowCnt = Integer.parseInt(row);
         int ct = 0;    
         for(int p=1;p<=rowCnt;p++){    
         ary = new ArrayList();
         String buyerId = util.nvl(req.getParameter("nmeID_"+p));
         if(!buyerId.equals("")){    
         String empIdn = util.nvl((String)udf.getValue("empIdn_"+p)).trim();
         if(!empIdn.equals("0")){
         String dfr = util.nvl((String)udf.getValue("frmdte_"+p));
         String dto = util.nvl((String)udf.getValue("todte_"+p));
         String dfrQ = " , to_date(sysdate, 'dd-mm-rrrr') " ;
         if(!dfr.equals(""))
         dfrQ = " , to_date('"+dfr+"', 'dd-mm-rrrr') " ;
         String dtoQ = " , to_date(sysdate, 'dd-mm-rrrr') " ;
         if(!dto.equals(""))
         dtoQ = " , to_date('"+dto+"', 'dd-mm-rrrr') " ;   
         String insIntoQ = " insert into Nme_Emp_Rel ( idn , nme_idn , emp_idn , vld_frm , vld_to  ";
         String insValQ  = " select Nme_Emp_Rel_Seq.nextval, ? , ? "+dfrQ+dtoQ;
         for(int k=0;k<empdepprpLst.size();k++){
         int q = k+1;    
         String lTblFld = util.nvl((String)empdepprpLst.get(k));    
         String lTblFldVal = util.nvl((String)udf.getValue(lTblFld+"_"+p));
         insIntoQ += " , prp"+q+",prp"+q+"_val";    
         insValQ += " , '"+lTblFld+"','"+lTblFldVal+"'";  
         }    
         ary.add(buyerId);
         ary.add(empIdn);
         String sql = insIntoQ + ") " + insValQ + " from dual ";
         ct = db.execUpd("Insert Emp Dep", sql, ary); 
         System.out.println("Insert Emp Dep "+p);
         }
         }
         }    
         if(ct>0)
         req.setAttribute("msg", "Records added Successfully");
         util.updAccessLog(req,res,"Sale Ex Allocation", "save end");
         return load(am,af,req,res);
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
                util.updAccessLog(req,res,"Sale Ex Allocation", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Sale Ex Allocation", "init");
            }
            }
            return rtnPg;
            }
    
    
    public EmpDepAction() {
        super();
    }
}
