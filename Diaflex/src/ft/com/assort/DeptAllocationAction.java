package ft.com.assort;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.dao.MNme;

import ft.com.generic.GenericImpl;

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

public class DeptAllocationAction extends DispatchAction {
    public ActionForward load(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception 
    {
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
       DeptAllocationForm deptAllocationForm = (DeptAllocationForm) form;
        
        //set the form element for Through 
        String query = "select val, dsc from prp where mprp = 'Through' order by srt";
        ArrayList arr = new ArrayList();
        
          ArrayList  outLst = db.execSqlLst("Get Dept-Emp list", query, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()) {
          MNme emp = new MNme();
          emp.setEmp_idn(rs.getString("val"));
          emp.setEmp_nme(rs.getString("dsc"));
          arr.add(emp);
        }
        rs.close();
        pst.close();
        deptAllocationForm.setThruList(arr);
        
        query = "select C.DEPT dept, a.fnme name, a.nme_idn nmeidn from mnme a, dept_emp_valid b, mdept c \n" + 
        "where a.nme_idn = b.emp_idn and b.dept_idn = c.idn \n" + 
        "group by c.dept, a.fnme, a.nme_idn \n" + 
        "order by dept";
        
        HashMap htdeptname = new HashMap();
        String tempdept = "";
        ArrayList vec = new ArrayList();
        arr = new ArrayList();
        
           outLst = db.execSqlLst("Get Dept-Emp list", query, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);   
        while(rs.next()) 
        {
            String dept = rs.getString("dept");
            String name = rs.getString("name");
            String nmeidn = rs.getString("nmeidn");
            
            MNme emp = new MNme();
            
            if(!dept.equals(tempdept)) 
            {
              //then add this record to the hashtable
              //create a new vector to store names
                if(!tempdept.equals("")) {
                  htdeptname.put(tempdept, arr);
                }
              
              arr = new ArrayList();
              tempdept = dept;
            }
            //else 
            //{
            emp.setEmp_idn(nmeidn);
            emp.setEmp_nme(name);
            arr.add(emp);
            //}
        }
        rs.close();
          pst.close();
        htdeptname.put(tempdept, vec);
        //deptAllocationForm.setEmpList(htdeptname);
        info.setHtdeptEmpRel(htdeptname);
        req.setAttribute("deptEmpList", htdeptname);
        
        System.out.println("****************************htdeptname: "+ htdeptname);
        
        ArrayList arrAlocThru = new ArrayList();
        HashMap htAlocThru = new HashMap();
        
        query = "select val, dsc from prp where mprp = 'Through'order by srt";
        
        rs = db.execSql("Fail Remark list", query, new ArrayList());
        
        while(rs.next()) 
        {
            String val = rs.getString("val");
            String dsc = rs.getString("dsc");
            
            arrAlocThru.add(val);
            
            HashMap temp = new HashMap();
            temp.put("val", val);
            temp.put("dsc", dsc);   
            htAlocThru.put(val, temp);
        }
        rs.close();
        info.setArrAlocThru(arrAlocThru);
        info.setHtAlocThru(htAlocThru);
        
        System.out.println("****************htAlocThru: "+htAlocThru);
        
        /*query = "select dept, sum(cts) cts, sum(pkts) pcs from ( \n" + 
        "select count(a.idn) pkts, sum(A.CTS) cts, b.txt memo, c.val dept, d.val subdept from mstk a, stk_dtl b, stk_dtl c, stk_dtl d\n" + 
        "where A.STT = 'MF_FL' and a.idn = B.MSTK_IDN \n" + 
        "and A.IDN = C.MSTK_IDN  and a.idn = D.MSTK_IDN\n" + 
        "and b.grp = 1 AND c.grp = 1\n" + 
        "and c.mprp = 'DEPT' and b.mprp = 'MEMO' and d.mprp = 'SUB_DEPT'\n" + 
        "group by d.val, c.val, b.txt)\n" + 
        "group by dept";
        */
        query = "select c.val dept, b.txt memo, d.val subdept, count(a.idn) pcs, sum(A.CTS) cts from mstk a, stk_dtl b, stk_dtl c, stk_dtl d\n" + 
        "where A.STT = 'MF_FL' and a.idn = B.MSTK_IDN \n" + 
        "and A.IDN = C.MSTK_IDN  and a.idn = D.MSTK_IDN\n" + 
        "and b.grp = 1 AND c.grp = 1\n" + 
        "and c.mprp = 'DEPT' and b.mprp = 'MEMO' and d.mprp = 'SUB_DEPT'\n" + 
        "group by d.val, c.val, b.txt\n" + 
        "order by dept, subdept, memo";
        
        ArrayList arrdept = new ArrayList();
        HashMap deptAlocList = new HashMap();
        tempdept = "";
        
        rs = db.execSql("Get Dept Allocation", query, new ArrayList());
        while(rs.next()) 
        {
            String dept = rs.getString("dept");
            if(!dept.equals(tempdept)) 
            {
                if(!tempdept.equals("")) 
                {
                  deptAlocList.put(tempdept, vec);
                  arrdept.add(tempdept);
                }
                vec = new ArrayList();
                tempdept = dept;
            }
            HashMap temp = new HashMap();
            
            temp.put("cts", rs.getString("cts"));
            temp.put("pcs", rs.getString("pcs"));
            temp.put("subdept", rs.getString("subdept"));
            
            vec.add(temp);
            
        }
        rs.close();
        if(!tempdept.equals("")) 
        {
          deptAlocList.put(tempdept, vec);
          arrdept.add(tempdept);
        }
        
        req.setAttribute("deptAloc", deptAlocList);
        req.setAttribute("arrdept", arrdept);
        
        System.out.println("****************************deptAlocList: "+ deptAlocList);
        System.out.println("****************************arrdept: "+ arrdept);
        
        query = "select C.DEPT dept, a.fnme name from mnme a, dept_emp_valid b, mdept c \n" + 
        "where a.nme_idn = b.emp_idn and b.dept_idn = c.idn and \n" + 
        "C.DEPT = '18-96-GIA'\n" + 
        "order by dept";
        
        req.setAttribute("form", deptAllocationForm);
        return am.findForward("load");
        }
    }
    
    public ActionForward processForm(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
       DeptAllocationForm deptAllocationForm = (DeptAllocationForm) form;
         ArrayList arrdept = new ArrayList();
      Enumeration reqNme = req.getParameterNames();
      
      while(reqNme.hasMoreElements()) 
      {
          String paramNm = (String)reqNme.nextElement();
        
          if(paramNm.indexOf("dept_") > -1) 
          {
              String dept = req.getParameter(paramNm);
              arrdept.add(dept);
          }
      }
      
      String person = "",query = "", dept = "";
      HashMap htdeptCnt = new HashMap();
      ArrayList ary = new ArrayList();
      
      ArrayList out = new ArrayList();
      out.add("I");
      
      String processid = "";
      query = "select iss_rtn_pkg.get_prc_id(?) prcid from dual";
      //get the issue id
      ary.add("MF_FL");
      ResultSet rs1  = db.execSql("GET PROCESSID ", query, ary);
      if(rs1.next()) {
        processid = rs1.getString("prcid");
      }
        rs1.close();
      System.out.println("*******************procsid: "+processid);
      
      for(int i=0; i<arrdept.size(); i++)
      {
          dept = arrdept.get(i).toString();
          
          //get the person for dept
          person = (String)deptAllocationForm.getValue(dept);
          //get the issue id
          ary = new ArrayList();
          ary.add(processid);
          ary.add(person);
          out = new ArrayList();
          out.add("I");
          
          CallableStatement cst = null;
          cst = db.execCall("GET ISSUEID ", "ISS_RTN_PKG.GEN_HDR(pPRCID => ?, pEMPID => ?,  pIssId => ?)", ary , out);
          
          int issueid = 0;
          issueid = cst.getInt(3);
        cst.close();
        cst=null;
          query = "select A.IDN IDN, A.CTS CTS from mstk a, stk_dtl b\n" + 
          "where A.IDN = B.MSTK_IDN\n" + 
          "AND A.STT = 'MF_FL'\n" + 
          "AND B.MPRP = 'DEPT' AND B.VAL = '"+dept+"'";
          
          int count = 0;
          ResultSet rs = db.execSql("Get IDN", query, new ArrayList());
          while(rs.next()) 
          {
              ary = new ArrayList();
              ary.add(String.valueOf(issueid));
              ary.add(rs.getString("idn"));
              ary.add(rs.getString("cts"));
              int ct = db.execCall("Issue Pkt", "ISS_RTN_PKG.ISS_PKT(pIssId => ?, pStkIdn => ? , pCts => ?)", ary );
              count++;
          }
          rs.close();
          htdeptCnt.put(dept, count);
      }
      
      System.out.println("************successfully issued");
      req.setAttribute("alocationDone", "true");
      req.setAttribute("submitDeptAloc", "true");
      return am.findForward("process");
        }
    }
  
    public ActionForward printJan(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
      String dept = req.getParameter("dept");
      
      String query = "select distinct b.txt memo \n" + 
      "from stk_dtl a, stk_dtl b, mstk m \n" + 
      "where a.mstk_idn = b.mstk_idn \n" + 
      "and m.idn = a.mstk_idn \n" + 
      "and M.STT = 'MF_FL' \n" + 
      "and a.mprp = 'DEPT' AND a.VAL = '"+dept+"' \n" + 
      "and b.mprp = 'MEMO' " +
      "order by memo desc";
      
      StringBuffer sbmemo = new StringBuffer();
      
      sbmemo.append("<memo>");
      
      ResultSet rs = db.execSql("Get SubDept Allocation", query, new ArrayList());
      if(rs.next()) {
        sbmemo.append("<memono>"+rs.getString("memo")+"</memono>");
      }
      sbmemo.append("</memo>");
      rs.close();
      System.out.println("memo");
      
      return null;
        }
    }
    
    public String memono(String dept, HttpServletRequest req, DBMgr db) throws Exception{   
        
      String query = "select distinct b.txt memo \n" + 
      "from stk_dtl a, stk_dtl b, mstk m \n" + 
      "where a.mstk_idn = b.mstk_idn \n" + 
      "and m.idn = a.mstk_idn \n" + 
      "and M.STT = 'MF_FL' \n" + 
      "and a.mprp = 'DEPT' AND a.VAL = '"+dept+"' \n" + 
      "and b.mprp = 'MEMO' " +
      "order by memo desc";
      
      String memono = "";
      
      ResultSet rs = db.execSql("Get SubDept Allocation", query, new ArrayList());
      if(rs.next()) {
        memono = rs.getString("memo");
      }
        rs.close();
      return memono;
    }
    
    public ActionForward dispSubdept(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
       DeptAllocationForm deptAllocationForm = (DeptAllocationForm) form;
        
      String dept = req.getParameter("dept").toString();
      
      String query = "select b.txt memo, d.val subdept, count(a.idn) pcs, sum(A.CTS) cts from mstk a, stk_dtl b, stk_dtl c, stk_dtl d\n" + 
      "where A.STT = 'MF_FL' and a.idn = B.MSTK_IDN \n" + 
      "and A.IDN = C.MSTK_IDN  and a.idn = D.MSTK_IDN\n" + 
      "and b.grp = 1 AND c.grp = 1 AND d.grp = 1\n" + 
      "and c.mprp = 'DEPT' and C.VAL = '"+dept+"' and b.mprp = 'MEMO' and d.mprp = 'SUB_DEPT'\n" + 
      "group by d.val, c.val, b.txt\n" + 
      "order by memo, subdept";
      
      ArrayList arrdept = new ArrayList();
      HashMap deptAlocList = new HashMap();
      String tempSubdept = "";
      HashMap temp = new HashMap(); 
      
      ResultSet rs = db.execSql("Get SubDept Allocation", query, new ArrayList());
      while(rs.next()) 
      {
          String subdept = rs.getString("subdept");
          if(!subdept.equals(tempSubdept)) 
          {
            if(!tempSubdept.equals("")) {
              deptAlocList.put(tempSubdept, temp);
              arrdept.add(tempSubdept);
            }
            temp = new HashMap();
            tempSubdept = subdept;
          }
          
          temp.put("memo", rs.getString("memo"));
          temp.put("cts", rs.getString("cts"));
          temp.put("pcs", rs.getString("pcs"));
          temp.put("subdept", rs.getString("subdept"));
          
      }
        rs.close();
      deptAlocList.put(tempSubdept, temp);
      arrdept.add(tempSubdept);
      
      req.setAttribute("deptAloc", deptAlocList);
      req.setAttribute("arrdept", arrdept);
      
      req.setAttribute("form", deptAllocationForm);
      req.setAttribute("dept", dept);
      
      return am.findForward("subdept");
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
              util.updAccessLog(req,res,"Purchase Form", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"Assort Issue Action", "init");
          }
          }
          return rtnPg;
          }
    
    public DeptAllocationAction() {
        super();
    }
}
