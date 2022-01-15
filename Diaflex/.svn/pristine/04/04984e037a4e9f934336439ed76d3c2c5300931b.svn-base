package ft.com.assort;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.generic.GenericImpl;

import java.sql.CallableStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;

import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class VerificationAction extends DispatchAction {
    HttpSession session ;
    DBMgr db ;
    InfoMgr info ;
    DBUtil util ;
    
    public ActionForward load(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
        String rtnPg=init(req,res);
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
        
        getFailRemark(req);
        //HashMap stkSummary = new HashMap();
        
        /*String query = "select MSTK_IDN, MPRP, VAL, NUM, TXT from stk_dtl where mstk_idn in " +
            " (select idn from mstk where stt = 'MF_IN') \n" + 
        " and (MPRP = 'MFG_TYP' OR MPRP = 'DEPT' OR  MPRP = 'CRTWT' OR MPRP = 'MEMO') ORDER BY MSTK_IDN, MPRP ";*/
        
        String query = "SELECT MFG_TYP, DEPT, SUM(QTY) PCS, SUM(CTS) CTS FROM MFG_SMRY_V GROUP BY MFG_TYP, DEPT";
        
        //ArrayList vec = new ArrayList();
        
        ResultSet rs = db.execSql("Stock type Summary", query, new ArrayList());
        
        HashMap newtab = new HashMap(), repair = new HashMap(), review = new HashMap();
        
        HashMap vals = new HashMap();
        while(rs.next()) {
            String mfgtyp = rs.getString("MFG_TYP");
            String dept = rs.getString("DEPT");
            
            vals = new HashMap();
            vals.put("PCS", rs.getString("PCS"));
            vals.put("CTS", rs.getString("CTS"));
                        
            if(mfgtyp.equalsIgnoreCase("New")) 
            {
                
              if(dept.equalsIgnoreCase("18-DN")) 
              {
                newtab.put("18-DN", vals);                    
              }
              else if(dept.equalsIgnoreCase("18-96-MIX"))
              {
                newtab.put("18-96-MIX", vals);
              }
              else if(dept.equalsIgnoreCase("18-96-GIA"))
              {
                newtab.put("18-96-GIA", vals);
              }
              else if(dept.equalsIgnoreCase("96-UP-GIA"))
              {
                newtab.put("96-UP-GIA", vals);
              }
              else if(dept.equalsIgnoreCase("96-UP-MIX"))
              {
                newtab.put("96-UP-MIX", vals);
              }                
            }
            else if(mfgtyp.equalsIgnoreCase("Repairing")) 
            {
              
              if(dept.equalsIgnoreCase("18-DN")) 
              {
                repair.put("18-DN", vals);
              }
              else if(dept.equalsIgnoreCase("18-96-MIX"))
              {
                repair.put("18-96-MIX", vals);
              }
              else if(dept.equalsIgnoreCase("18-96GIA"))
              {
                repair.put("18-96-GIA", vals);
              }
              else if(dept.equalsIgnoreCase("96-UP-GIA"))
              {
                repair.put("96-UP-GIA", vals);
              }
              else if(dept.equalsIgnoreCase("96-UP-MIX"))
              {
                repair.put("96-UP-MIX", vals);
              }
            }
            else if(mfgtyp.equalsIgnoreCase("Review")) 
            {
                
              if(dept.equalsIgnoreCase("18-DN"))
              {
                review.put("18-DN", vals);
              }
              else if(dept.equalsIgnoreCase("18-96-MIX"))
              {
                review.put("18-96-MIX", vals);
              }
              else if(dept.equalsIgnoreCase("18-96-GIA"))
              {
                review.put("18-96-GIA", vals);
              }
              else if(dept.equalsIgnoreCase("96-UP-GIA"))
              {
                review.put("96-UP-GIA", vals);
              }
              else if(dept.equalsIgnoreCase("96-UP-MIX"))
              {
                review.put("96-UP-MIX", vals);
              }
            }
        }
        
        rs.close();
        req.setAttribute("newtab", newtab);
        req.setAttribute("repair", repair);
        req.setAttribute("review", review);
        
        //select for memo wise details
        ArrayList newmemo = new ArrayList(), repairmemo = new ArrayList(), reviewmemo = new ArrayList();
        HashMap htmemo = new HashMap();
        HashMap intvals = new HashMap();
        
        String tempmemo = "";
        
        String query2 = "select MFG_TYP, MEMO, DEPT, QTY,CTS from MFG_SMRY_V order BY MFG_TYP, MEMO, DEPT";
      
        rs = db.execSql("Memo Wise Details", query2, new ArrayList());
        String memo = "";
        
        while(rs.next()) 
        {
            String mfgtyp = rs.getString("MFG_TYP");
            memo = rs.getString("MEMO");
            
            if(!tempmemo.equalsIgnoreCase(memo)) 
            {
                if(!tempmemo.equalsIgnoreCase(""))
                {
                    System.out.println("*****************htmemo: "+htmemo);
                    htmemo.put(tempmemo, vals);
                }
                vals = new HashMap();
                tempmemo = memo;
            }           
            
            String dept = rs.getString("DEPT");
            
            intvals = new HashMap();
            intvals.put("PCS", rs.getString("QTY"));
            intvals.put("CTS", rs.getString("CTS"));
            
            vals.put(dept, intvals);
            
            if(mfgtyp.equalsIgnoreCase("New")) 
            {
              newmemo.add(memo);                  
            }
            else if(mfgtyp.equalsIgnoreCase("Repairing")) 
            {
              repairmemo.add(memo);
            }
            else if(mfgtyp.equalsIgnoreCase("Review")) 
            {
              reviewmemo.add(memo);
            }
        }
        rs.close();
        if(!tempmemo.equalsIgnoreCase(""))
        {
          htmemo.put(tempmemo, vals);
        }
        
        //remove duplicates
        newmemo = new ArrayList(new HashSet(newmemo));
        repairmemo = new ArrayList(new HashSet(repairmemo));
        reviewmemo= new ArrayList(new HashSet(reviewmemo));
        
        //sort memos in order
        Collections.sort(newmemo);
        Collections.sort(repairmemo);
        Collections.sort(reviewmemo);
        
        req.setAttribute("newmemo", newmemo);
        req.setAttribute("repairmemo", repairmemo);
        req.setAttribute("reviewmemo", reviewmemo);
        req.setAttribute("htmemo", htmemo);
        
        
        
        /*String tempStkIdn = "";
        ArrayList stkIdnList = new ArrayList(); 
        HashMap temp = new HashMap();
        //boolean start = true;
        while(rs.next()) 
        {            
            String stkidn = String.valueOf(rs.getInt("MSTK_IDN"));
            
            if(!tempStkIdn.equals(stkidn)) 
            {
              if(!tempStkIdn.equals("")) {
                System.out.println("***********stkidn: "+tempStkIdn +"  temp:"+ temp);
                stkSummary.put(tempStkIdn, temp);                
              }
              
              tempStkIdn = stkidn;
              temp = new HashMap();
              stkIdnList.add(stkidn);
              //start = false;
            }
            
            temp.put("MSTK_IDN", rs.getString("MSTK_IDN"));
            String key = rs.getString("MPRP");
            String val;
            
            if(key.equalsIgnoreCase("CRTWT")) 
            {
              val = rs.getString("NUM");
            }
            else if(key.equalsIgnoreCase("MEMO")) 
            {
              val = rs.getString("TXT");
            }
            else 
            {
              val = rs.getString("VAL");
            }
            temp.put(key, val);
                        
        }
        System.out.println("***********stkidn: "+tempStkIdn +"  temp:"+ temp);
        stkSummary.put(tempStkIdn, temp);
        stkIdnList.add(tempStkIdn);
        
        //HashMap newtab = new HashMap(), repair = new HashMap(), review = new HashMap();
        ArrayList newList = new ArrayList(), reparingList = new ArrayList(), reviewList = new ArrayList();
        
        //process the stkSummary. group by MFG_TYP then DEPT
        ArrayList mfgtyparr = new ArrayList(), deptarr = new ArrayList();
        
        for(int i=0; i<stkIdnList.size(); i++) 
        {
            String stkidn = stkIdnList.get(i).toString();            
            temp = (HashMap) stkSummary.get(stkidn);
            System.out.println("**************stkidn"+stkidn+" temp: "+temp);
            String mfgtyp = temp.get("MFG_TYP").toString();
            String dept = temp.get("DEPT").toString();
            //float crtwt = (Float) temp.get("CRTWT");
            
            mfgtyparr.add(mfgtyp);
            deptarr.add(dept);
            
            if(mfgtyp.equalsIgnoreCase("New")) {
                newList.add(stkidn);
                
                if(dept.equalsIgnoreCase("18-DN")) 
                {
                  if(newtab.get("18-DN") != null) {
                    vec = (ArrayList) newtab.get("18-DN");
                  }
                  vec.add(stkidn);
                  newtab.put("18-DN", vec);                    
                }
                else if(dept.equalsIgnoreCase("18-96-MIX"))
                {
                  if(newtab.get("18-96-MIX") != null) {
                    vec = (ArrayList) newtab.get("18-96-MIX");
                  }
                  vec.add(stkidn);
                  newtab.put("18-96-MIX", vec);
                }
                else if(dept.equalsIgnoreCase("18-96-GIA"))
                {
                  if(newtab.get("18-96-GIA") != null) {
                    vec = (ArrayList) newtab.get("18-96-GIA");
                  }
                  vec.add(stkidn);
                  newtab.put("18-96-GIA", vec);
                }
                else if(dept.equalsIgnoreCase("96-UP-GIA"))
                {
                  if(newtab.get("96-UP-GIA") != null) {
                    vec = (ArrayList) newtab.get("96-UP-GIA");
                  }
                  vec.add(stkidn);
                  newtab.put("96-UP-GIA", vec);
                }
                else if(dept.equalsIgnoreCase("96-UP-MIX"))
                {
                  if(newtab.get("96-UP-MIX") != null) {
                    vec = (ArrayList) newtab.get("96-UP-MIX");
                  }
                  vec.add(stkidn);
                  newtab.put("96-UP-MIX", vec);
                }
                
            }
            else if(mfgtyp.equalsIgnoreCase("Repairing")) {
                reparingList.add(stkidn);
                
              if(dept.equalsIgnoreCase("18-DN")) 
              {
                if(repair.get("18-DN") != null) {
                  vec = (ArrayList) repair.get("18-DN");
                }
                vec.add(stkidn);
                repair.put("18-DN", vec);
              }
              else if(dept.equalsIgnoreCase("18-96-MIX"))
              {
                if(repair.get("18-96-MIX") != null) {
                  vec = (ArrayList) repair.get("18-96-MIX");
                }
                vec.add(stkidn);
                repair.put("18-96-MIX", vec);
              }
              else if(dept.equalsIgnoreCase("18-96GIA"))
              {
                if(repair.get("18-96-GIA") != null) {
                  vec = (ArrayList) repair.get("18-96GIA");
                }
                vec.add(stkidn);
                repair.put("18-96-GIA", vec);
              }
              else if(dept.equalsIgnoreCase("96-UP-GIA"))
              {
                if(repair.get("96-UP-GIA") != null) {
                  vec = (ArrayList) repair.get("96-UP-GIA");
                }
                vec.add(stkidn);
                repair.put("96-UP-GIA", vec);
              }
              else if(dept.equalsIgnoreCase("96-UP-MIX"))
              {
                if(repair.get("96-UP-MIX") != null) {
                  vec = (ArrayList) repair.get("96-UP-MIX");
                }
                vec.add(stkidn);
                repair.put("96-UP-MIX", vec);
              }
            }
            else if(mfgtyp.equalsIgnoreCase("Review")) {
                reviewList.add(stkidn);
                
              if(dept.equalsIgnoreCase("18-DN"))
              {
                if(review.get("18-DN") != null) {
                  vec = (ArrayList) review.get("18-DN");
                }
                vec.add(stkidn);
                review.put("18-DN", vec);
              }
              else if(dept.equalsIgnoreCase("18-96-MIX"))
              {
                if(review.get("18-96-MIX") != null) {
                  vec = (ArrayList) review.get("18-96-MIX");
                }
                vec.add(stkidn);
                review.put("18-96-MIX", vec);
              }
              else if(dept.equalsIgnoreCase("18-96GIA"))
              {
                if(review.get("18-96-GIA") != null) {
                  vec = (ArrayList) review.get("18-96-GIA");
                }
                vec.add(stkidn);
                review.put("18-96-GIA", vec);
              }
              else if(dept.equalsIgnoreCase("96-UP-GIA"))
              {
                if(review.get("96-UP-GIA") != null) {
                  vec = (ArrayList) review.get("96-UP-GIA");
                }
                vec.add(stkidn);
                review.put("96-UP-GIA", vec);
              }
              else if(dept.equalsIgnoreCase("96-UP-MIX"))
              {
                if(review.get("96-UP-MIX") != null) {
                  vec = (ArrayList) review.get("96-UP-MIX");
                }
                vec.add(stkidn);
                review.put("96-UP-MIX", vec);
              }
            }
        }
        */
        /*req.setAttribute("stkIdnList", stkIdnList);
        req.setAttribute("stkSummary", stkSummary);
        
        //group by memo
        
        //type = New
        for(int i=0; i<newList.size(); i++) {
            temp = (HashMap) stkSummary.get(newList.get(i));
                      
        }
        */
        return am.findForward("load");
        }
    }
    
    public void getFailRemark(HttpServletRequest req) throws Exception {

      
      ArrayList arrFailRem = new ArrayList();
      HashMap htFailRem = new HashMap();
      
      String query = "select val, dsc from prp where mprp = 'Ver_Fail'order by srt";
      
      ResultSet rs = db.execSql("Fail Remark list", query, new ArrayList());
      
      while(rs.next()) 
      {
          String val = rs.getString("val");
          String dsc = rs.getString("dsc");
          
          arrFailRem.add(val);
          
          HashMap temp = new HashMap();
          temp.put("val", val);
          temp.put("dsc", dsc);   
          htFailRem.put(val, temp);
      }
        rs.close();
      info.setArrFailRem(arrFailRem);
      info.setHtFailRem(htFailRem);
      
      System.out.println("****************htFailRem: "+htFailRem);
      
    }
    
    public ActionForward selectFailRemark(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
        String rtnPg=init(req,res);
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
      
      ArrayList arrFailRem = info.getArrFailRem();
      HashMap htFailRem = info.getHtFailRem();
      
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
      
      StringBuffer sb = new StringBuffer();
      sb.append("<failrem>");
      
      for(int i=0; i<arrFailRem.size(); i++) 
      {
          HashMap temp = new HashMap();
          temp = (HashMap)htFailRem.get(arrFailRem.get(i));
          sb.append("<row>");
          sb.append("<val>" + temp.get("val") + "</val>");
          sb.append("<dsc>" + temp.get("dsc") + "</dsc>");
          sb.append("</row>");
      }
      
      sb.append("</failrem>");
      System.out.println("failrem: " + sb);
      res.getWriter().write(sb.toString());
      return null;
        }
    }
    
    public ActionForward processForm(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
        String rtnPg=init(req,res);
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
      
      String query, query1;
      
      //**************************Process the pkt wise table***************************
      String[] pktwise = req.getParameterValues("pktwisechk");
      StringBuffer arrPkt = new StringBuffer();
      
      if(pktwise!=null) 
      {
          for(int i=0; i<pktwise.length; i++)
          {
              String pktno= pktwise[i];
              //add the pkt no to the list so that the memo associated 
              //with this packet will have no effects at the higher level 
              //i.e. "sizewise"
              arrPkt.append(pktno);
              
              String pktwisefail = req.getParameter(pktwise[i]+"_pktwisefail");
              
              if(pktwisefail!=null) 
              {
                  //insert  the reason for failure  
                  String failrem = req.getParameter(pktwise[i]+"_selectpktwise");
                  System.out.println("pktno:"+ pktno +" failrem:"+failrem);
                  
                  query = "insert into stk_dtl(mstk_idn, grp, mprp, val, lab) " +
                      "values("+ pktno +", 1, 'Ver_Fail', '"+failrem+"', 'NONE')";
                  System.out.println("if QUERY: "+query);
                  
                  int ct = db.execUpd("Insert verification fail remark", query, new ArrayList());
                
              }
              else 
              {
                  //else make them pass
                  query = "update mstk set stt='MF_FL' where idn = " + pktno;
                  System.out.println("else QUERY: "+query);
                  
                  int ct = db.execUpd("update status Pkt wise", query, new ArrayList());
              }
              
          }
      }
      
      //**************************Process the size wise table*************************
      
      StringBuffer sizewisePkt = new StringBuffer();
      String type;
      String memo;
      String dept;
      String subdept;
      boolean start = true;
      
      //first get the memo no for all the packets that were select in pktwise
      /*query = "select distinct b.txt memo from stk_dtl a, stk_dtl b\n" + 
      "where a.mstk_idn = b.mstk_idn and \n" + 
      "b.mprp = 'MEMO' and a.mstk_idn in ("+ arrPkt.toString() + ")";
      */
      
      ArrayList arrPktwiseSubdept = new ArrayList();
      ResultSet rs;
      
      if(arrPkt.length()>0){
        query = "select distinct val sub_dept from stk_dtl where mstk_idn in ("+ arrPkt.toString() +") and mprp = 'SUB_DEPT'";
        rs = db.execSql("Memo list", query, new ArrayList());
        
        while(rs.next())
        {
          arrPktwiseSubdept.add(rs.getString("sub_dept"));
        }
          rs.close();
      }     
      
      //start processing the size wise table
      String[] sizewise = req.getParameterValues("sizewisechk");
      ArrayList arrSizeWiseMemo = new ArrayList();
      
      if(sizewise!=null) 
      {
          for(int i=0; i<sizewise.length; i++)
          {
              String[] valSizewise = sizewise[i].split("_");
              
              type = valSizewise[0];
              memo = valSizewise[1];
              dept = valSizewise[2];
              subdept = valSizewise[3];
              
              if(!arrPktwiseSubdept.contains(subdept))
              {
                query = "select A.IDN PKTNO \n" + 
                  "from mstk a, stk_dtl b, stk_dtl c, stk_dtl d,stk_dtl e \n" + 
                  "where a.idn = b.mstk_idn AND a.idn = c.mstk_idn AND \n" + 
                  "a.idn = d.mstk_idn and a.idn = e.mstk_idn AND \n" + 
                  "(b.mprp = 'DEPT' AND B.VAL = '"+ dept +"' ) AND \n" + 
                  "(c.mprp = 'MFG_TYP' and c.val='"+ type.toUpperCase() +"') AND \n" + 
                  "(d.mprp = 'MEMO' and d.txt = '"+ memo +"') and \n" + 
                  "(e.mprp = 'SUB_DEPT' AND E.VAL = '"+ subdept +"') AND \n" + 
                  "b.grp = 1 AND c.grp = 1 AND d.grp = 1 and e.grp = 1 \n" + 
                  "AND a.stt = 'MF_IN'";
                
                rs = db.execSql("SizeWise Fail", query, new ArrayList());
                
                String sizewisefail = req.getParameter(sizewise[i]+"_sizewisefail");
                
                if(sizewisefail!=null) 
                {
                  //update the status of all the stones to fail and 
                  //insert the verification fail remark
                  String failrem = req.getParameter(sizewise[i]+"_selectsizewise");
                  System.out.println("sizewisePkt:"+ sizewisePkt +" failrem:"+failrem);
                  
                  while(rs.next()) 
                  {
                      query = "insert into stk_dtl(mstk_idn, grp, mprp, val, lab) " +
                          "values("+ rs.getString("pktno") +", 1, 'Ver_Fail', '"+failrem+"', 'NONE')";
                      System.out.println("if QUERY: "+query);
                      
                      int ct = db.execUpd("Insert verification fail remark", query, new ArrayList());
                      
                  }
                    rs.close();
                  CallableStatement cst = null;
                  cst = db.execCall("Call GEN_XLS ", "GEN_XLS", new ArrayList(), new ArrayList());
                  System.out.println("gen_xls executed.");
                  cst.close();
                  cst=null;
                }
                else {
                  //else make them pass
                    
                    while(rs.next()) 
                    {
                        query = "update mstk set stt='MF_FL' where idn = " + rs.getString("pktno");
                      System.out.println("else QUERY: "+query);
                        int ct = db.execUpd("Update status size wise", query, new ArrayList());
                    }
                    rs.close();
                }
                
                //add the size wise memo for upper level i.e. "memo wise"
                arrSizeWiseMemo.add(memo);
              }
              
              
          }
      }
      
      //merge the 2 memo lists 
      //arrPktwiseSubdept.addAll(arrSizeWiseMemo); merging not required 
      
      //***************************Process the memo wise table*********************
      String[] memowise = req.getParameterValues("memowise");
      
      if(memowise!=null) 
      {
          for(int i=0; i<memowise.length; i++)
          {
              String memono = memowise[i];
              
              if(!arrSizeWiseMemo.contains(memono))
              {
                  //update all the pkt status to "MF_FL"
                query = "update mstk set stt='MF_FL' \n" + 
                "where idn in (select distinct mstk_idn from mstk, stk_dtl \n" + 
                "where idn = mstk_idn and  stt = 'MF_IN' and \n" + 
                "mprp = 'MEMO' and txt in ('"+memono+"'))";
                
                int ct = db.execUpd("Update status memo wise", query, new ArrayList());
              }
              //do something for this memo no. maybe update the status of 
              //all the stones with MFG_TYP = 'MF_IN' to something
              
          }
      }
      
      
      
      
      return am.findForward("process");
        }
    }
    
    public ActionForward pktWise(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
        String rtnPg=init(req,res);
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
      
      String memo = req.getParameter("memono").toString();
      String type = req.getParameter("type").toString();
      String dept = req.getParameter("dept").toString();
      String subdept = req.getParameter("subdept").toString();
      
      String query = "select d.txt memo, A.IDN PKTNO, a.cts cts, b.val dept, e.val sub_dept \n" + 
      "from mstk a, stk_dtl b, stk_dtl c, stk_dtl d,stk_dtl e \n" + 
      "where a.idn = b.mstk_idn AND a.idn = c.mstk_idn AND \n" + 
      "a.idn = d.mstk_idn and a.idn = e.mstk_idn AND \n" + 
      "(b.mprp = 'DEPT' AND B.VAL = '"+ dept +"' ) AND \n" + 
      "(c.mprp = 'MFG_TYP' and c.val='"+ type +"') AND \n" + 
      "(d.mprp = 'MEMO' and d.txt = '"+ memo +"') and \n" + 
      "(e.mprp = 'SUB_DEPT' AND E.VAL = '"+ subdept +"') AND \n" + 
      "b.grp = 1 AND c.grp = 1 AND d.grp = 1 and e.grp = 1 \n" + 
      "AND a.stt = 'MF_IN'";
      
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
      
      StringBuffer sb = new StringBuffer();
      sb.append("<pktdata>");
      
      ResultSet rs = db.execSql("Pkt Wise Details", query, new ArrayList());
      
      while(rs.next()) 
      {
          sb.append("<row>");
          
          sb.append("<memo>" + rs.getString("memo") + "</memo>");
          sb.append("<pktno>" + rs.getString("pktno") + "</pktno>");
          sb.append("<cts>" + rs.getString("cts") + "</cts>");
          sb.append("<dept>" + rs.getString("dept") + "</dept>");
          sb.append("<subdept>" + rs.getString("sub_dept") + "</subdept>");
          
          sb.append("</row>");        
      }
      
      sb.append("</pktdata>");
      rs.close();
      System.out.println("packet: " + sb);
      res.getWriter().write(sb.toString());
      return null;
        }
    }
      
    public ActionForward verifySizeWise(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception 
    {
        String rtnPg=init(req,res);
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
      
      String memo = req.getParameter("memono").toString();
      String type = req.getParameter("type").toString();
      String dept = req.getParameter("dept").toString();
      String subdept = req.getParameter("subdept").toString();
      
      ArrayList arrSubdept = info.getArrSubdept();
      
      HashMap htSubdept = info.getHtSubdept();
      HashMap httemp = new HashMap();
      
      arrSubdept.add(subdept);
      
      httemp.put("memono", memo);
      httemp.put("type", type);
      httemp.put("dept", dept);
      
      htSubdept.put(subdept, httemp);
      
      info.setArrSubdept(arrSubdept);
      info.setHtSubdept(htSubdept);
      
      System.out.println("arrSubdept: "+arrSubdept);
      System.out.println("htSubdept: "+htSubdept);
      
      //will have to process this data for updating the database
      
      return null;
        }
    }
    
  public ActionForward verifyPktWise(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception 
  {
      String rtnPg=init(req,res);
      if(!rtnPg.equals("sucess")){
          return am.findForward(rtnPg);   
      }else{
    
    String pktno = req.getParameter("pktno").toString();
    
    ArrayList arrPktNo = info.getArrPktNo();
    
    arrPktNo.add(pktno);
    
    
    info.setArrPktNo(arrPktNo);
    
    System.out.println("arrPktNo: "+arrPktNo);
    //will have to process this data for updating the database
    
    return null;
      }
  }
    
    public ActionForward sizeWise(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
        String rtnPg=init(req,res);
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
      
      String memo = req.getParameter("memono").toString();
      String type = req.getParameter("type").toString();
      
      //Size Wise Detail of Memo No
      String query = "SELECT c.val mfg_typ, d.txt memo,b.val dept, e.val sub_dept, SUM (a.qty) qty, SUM (a.cts) cts \n" + 
      "FROM mstk a, stk_dtl b, stk_dtl c, stk_dtl d,stk_dtl e \n" + 
      "WHERE a.idn = b.mstk_idn AND a.idn = c.mstk_idn AND a.idn = d.mstk_idn and \n" + 
      "a.idn = e.mstk_idn AND b.mprp = 'DEPT' AND (c.mprp = 'MFG_TYP' and c.val='"+ type +"') " + 
      "AND (d.mprp = 'MEMO' and d.txt = '"+ memo +"') and e.mprp = 'SUB_DEPT' AND b.grp = 1 \n" + 
      "AND c.grp = 1 AND d.grp = 1 and e.grp = 1 AND a.stt = 'MF_IN' \n" + 
      "GROUP BY c.val, d.txt, b.val, e.val\n" + 
      "ORDER BY mfg_typ,MEMO";
      
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
      
      StringBuffer sb = new StringBuffer();
      sb.append("<memo>");
      
      StringBuffer sb18down = new StringBuffer();
      StringBuffer sb96mix = new StringBuffer();
      StringBuffer sb96gia = new StringBuffer();
      StringBuffer sb96upgia = new StringBuffer();
      StringBuffer sb96upmix = new StringBuffer();
      
      ResultSet rs = db.execSql("Size Wise Details", query, new ArrayList());
      
      while(rs.next()) 
      {
        String dept = rs.getString("DEPT");
        String subdept = rs.getString("SUB_DEPT");
        String qty = rs.getString("QTY");
        String cts = rs.getString("CTS");
          
        if(dept.equalsIgnoreCase("18-DN"))
        {
          sb18down.append("<row>");
          sb18down.append("<subdept>"+subdept+"</subdept>");
          sb18down.append("<qty>"+qty+"</qty>");
          sb18down.append("<cts>"+cts+"</cts>");
          sb18down.append("<dept>"+dept+"</dept>");
          sb18down.append("</row>");
        }
        else if(dept.equalsIgnoreCase("18-96-MIX"))
        {
          sb96mix.append("<row>");
          sb96mix.append("<subdept>"+subdept+"</subdept>");
          sb96mix.append("<qty>"+qty+"</qty>");
          sb96mix.append("<cts>"+cts+"</cts>");
          sb96mix.append("<dept>"+dept+"</dept>");
          sb96mix.append("</row>");
        }
        else if(dept.equalsIgnoreCase("18-96-GIA"))
        {
          sb96gia.append("<row>");
          sb96gia.append("<subdept>"+subdept+"</subdept>");
          sb96gia.append("<qty>"+qty+"</qty>");
          sb96gia.append("<cts>"+cts+"</cts>");
          sb96gia.append("<dept>"+dept+"</dept>");
          sb96gia.append("</row>");
        }
        else if(dept.equalsIgnoreCase("96-UP-GIA"))
        {
          sb96upgia.append("<row>");
          sb96upgia.append("<subdept>"+subdept+"</subdept>");
          sb96upgia.append("<qty>"+qty+"</qty>");
          sb96upgia.append("<cts>"+cts+"</cts>");
          sb96upgia.append("<dept>"+dept+"</dept>");
          sb96upgia.append("</row>");
        }
        else if(dept.equalsIgnoreCase("96-UP-MIX"))
        {
          sb96upmix.append("<row>");
          sb96upmix.append("<subdept>"+subdept+"</subdept>");
          sb96upmix.append("<qty>"+qty+"</qty>");
          sb96upmix.append("<cts>"+cts+"</cts>");
          sb96upmix.append("<dept>"+dept+"</dept>");
          sb96upmix.append("</row>");
        }
      }
        
      if(sb18down.length()!= 0)
      {
          sb.append("<n18Down>"+sb18down+"</n18Down>");
          System.out.println("sb18down");
      }
      if(sb96mix.length()!= 0)
      {
          sb.append("<n1896MIX>"+sb96mix+"</n1896MIX>");
          System.out.println("sb96mix");
      }
      if(sb96gia.length()!= 0)
      {
          sb.append("<n1896GIA>"+sb96gia+"</n1896GIA>");
          System.out.println("sb96gia");
      }
      if(sb96upgia.length()!= 0)
      {
          sb.append("<n96UPGIA>"+sb96upgia+"</n96UPGIA>");
          System.out.println("sb96upgia");
      }
      if(sb96upmix.length()!= 0)
      {
          sb.append("<n96UPMIX>"+sb96upmix+"</n96UPMIX>");
          System.out.println("sb96upmix");
      }
      sb.append("</memo>");
      
      res.getWriter().write(sb.toString());
      
      System.out.println(sb);
      rs.close();
      return null;
        }
    }
    
    public String init(HttpServletRequest req , HttpServletResponse res) {
         
        String rtnPg="sucess";
        String invalide="";
        session = req.getSession(false);
        db = (DBMgr)session.getAttribute("db");
        info = (InfoMgr)session.getAttribute("info");
        util = ((DBUtil)session.getAttribute("util") == null)?new DBUtil():(DBUtil)session.getAttribute("util");
            String connExists=util.nvl(util.getConnExists());  
            if(!connExists.equals("N"))
            invalide=util.nvl(util.chkTimeOut(),"N");
            if(session.isNew() || db==null)
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
                }
            }
        return rtnPg;
        }
    public VerificationAction() {
        super();
    }
}
