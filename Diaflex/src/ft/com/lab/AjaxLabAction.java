package ft.com.lab;



import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import ft.com.dao.ByrDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.ArrayList;

import java.util.LinkedHashMap;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class AjaxLabAction extends DispatchAction {
    
    HttpSession session ;
    DBMgr db ;
    InfoMgr info ;
    DBUtil util ;
    public ActionForward setRCOB(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      String stkIdn = req.getParameter("stkIdn");
      String lprp = req.getParameter("lprp");
      String stt = req.getParameter("stt");
      String check = req.getParameter("chk");
      HashMap srchRcObList = (HashMap)session.getAttribute("srchReckObsMap");
        String str = (String)srchRcObList.get(stkIdn);
      if(check.equals("true")){
      if(srchRcObList.size()>0){
         
          if(stt.equals("LB_RS"))
             str = str+",Recheck "+lprp ;
          else
             str = str+",observer "+lprp ;
         }}else{
              if(stt.equals("LB_RS"))
                 str = str.replaceAll(",Recheck "+lprp,"");
              else
                str = str.replaceAll(",Observer "+lprp,"");
             
              
          }
       System.out.println("string"+str);
        srchRcObList.put(stkIdn, str);
        session.setAttribute("srchReckObsMap", srchRcObList);
      return null;
    }
    
    public ActionForward setRI(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");

        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      String stkIdn = req.getParameter("stkIdn");
      String lprp = req.getParameter("lprp");
      String lstr = req.getParameter("str");
      String certno = util.nvl(req.getParameter("certno"));

      HashMap srchRcObList = (HashMap)gtMgr.getValue("srchRIMap");
        String pstr ="";
        if(srchRcObList!=null && srchRcObList.size()>0){
        pstr = util.nvl((String)srchRcObList.get(stkIdn));
        }else{
            srchRcObList = new HashMap();
        }
       if(lstr.equals("")){
           String rechkstr="Recheck "+lprp; 
           int indexStr=pstr.indexOf(rechkstr);  
           if(indexStr>-1){
           rechkstr=pstr.substring(indexStr, pstr.length());
           if(rechkstr.indexOf(",")>-1)
           rechkstr=rechkstr.substring(0, rechkstr.indexOf(","));
           else
           rechkstr=rechkstr.substring(0, rechkstr.length());
           pstr=pstr.replaceAll(","+rechkstr,"");   
           }
           pstr = pstr.replaceAll(",Recheck "+lprp,"");
           pstr = pstr.replaceAll(",Observer "+lprp,"");
           pstr = pstr.replaceAll(",DIS "+lprp,"");
           lstr=pstr;
       }else{
           if((!certno.equals("")&& !certno.equals("0")) || pstr.indexOf("Recheck")>-1){
           String rechkstr="Recheck "+lprp; 
           int indexStr=pstr.indexOf(rechkstr);
               if(indexStr>-1){
               rechkstr=pstr.substring(indexStr, pstr.length());
               if(rechkstr.indexOf(",")>-1)
               rechkstr=rechkstr.substring(0, rechkstr.indexOf(","));
               else
               rechkstr=rechkstr.substring(0, rechkstr.length());
               pstr=pstr.replaceAll(","+rechkstr,"");   
               if(!certno.equals("")&& !certno.equals("0"))
               lstr = pstr+","+lstr+" Refer to Privious Report "+certno;
               else
               lstr = pstr+","+lstr;        
               }else{
               lstr = pstr+","+lstr;    
               }
           }else
           lstr = pstr+","+lstr;
       } 
  
        srchRcObList.put(stkIdn, lstr);
        gtMgr.setValue("srchRIMap", srchRcObList);
      return null;
    }
    public ActionForward selectRecheck(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        res.setContentType("text/xml"); 
        res.setHeader("Cache-Control", "no-cache"); 
        HashMap srchRcObList = (HashMap)gtMgr.getValue("srchRIMap");
        ArrayList stockIdnLst =new ArrayList();
        if(srchRcObList!=null && srchRcObList.size()>0){
        }else{
            srchRcObList = new HashMap();
        }
        StringBuffer sb = new StringBuffer();
        LinkedHashMap stockList = new LinkedHashMap(srchRcObList); 
        Set<String> keys = stockList.keySet();
        for(String key: keys){
            stockIdnLst.add(key);
        }
        if(stockIdnLst.size()>0){
        for(int i=0;i<stockIdnLst.size();i++){
            sb.append("<detail>");
            sb.append("<stkidn>"+stockIdnLst.get(i) +"</stkidn>");
            sb.append("</detail>");
        }
        }else{
            sb.append("<detail>");
            sb.append("<stkidn>1</stkidn>");
            sb.append("</detail>"); 
        }
        res.getWriter().write("<details>" +sb.toString()+ "</details>");
        return null;
    }
    public ActionForward setRechkRI(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      String stkIdn = req.getParameter("stkIdn");
      String lprp = req.getParameter("val");
     
        HashMap srchRcObList = (HashMap)session.getAttribute("srchReckObsMap");
        String str = util.nvl((String)srchRcObList.get(stkIdn));
        if(lprp.indexOf("NONE")!=-1){
          lprp = lprp.replaceAll("NONE ", "");
            str = str.replaceAll(",Recheck "+lprp,"");
            str = str.replaceAll(",Observer "+lprp,"");
        }else{
            str = str+","+lprp ;
         }
        System.out.println("string"+str);
        srchRcObList.put(stkIdn, str);
        session.setAttribute("srchReckObsMap",srchRcObList);
      return null;
    }
    
    public ActionForward setcertRI(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
      int fav = 1;
      ArrayList ary=new ArrayList();
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
      StringBuffer sb = new StringBuffer();
      String stkIdn = req.getParameter("stkIdn");
      String lprp = req.getParameter("lprp");
      String lab = util.nvl(req.getParameter("lab"),"GIA");
      ary.add(lprp);
      ary.add(stkIdn);
      ary.add(lab);
      String sqlQ="select distinct a.grp, a.mstk_idn, a.mprp, a.val \n" + 
      " , b.mprp CERT_NO, b.txt CERT_NO_VAL\n" + 
      " from stk_dtl a, stk_dtl b\n" + 
      " where a.mstk_idn = b.mstk_idn\n" + 
      " and a.mprp = ? and b.mprp = 'CERT_NO'\n" + 
      " and a.mstk_idn = ?\n" + 
      " and exists (select 1 from stk_dtl c\n" + 
      " where a.mstk_idn=c.mstk_idn\n" + 
      " and a.grp = c.grp and b.grp = c.grp\n" + 
      " and c.mprp = 'LAB' and c.val = ?)\n" + 
      " And B.Txt Is Not Null\n" + 
      " order by a.grp";

            ArrayList outLst = db.execSqlLst("load certno", sqlQ, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            fav=1;
            sb.append("<certno>");
            sb.append("<certval>"+util.nvl(rs.getString("val"))+"-"+ util.nvl(rs.getString("CERT_NO_VAL")) +"</certval>");
            sb.append("<certkey>"+util.nvl(rs.getString("CERT_NO_VAL"))+"</certkey>");
            sb.append("</certno>");
        }
        rs.close(); pst.close();
            //        if(fav==1)
//                    sb.append("<certno>");
//                    sb.append("<certval>A-123</certval>");
//                    sb.append("<certkey>123</certkey>");
//                    sb.append("</certno>");
         res.getWriter().write("<certsnos>" +sb.toString()+ "</certsnos>");

            return null;
        }
    public ActionForward AddList(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      String stkIdn = req.getParameter("stkIdn");
      String listNme = req.getParameter("listNme");
      ArrayList ary = new ArrayList();
      ArrayList prpUpdList = info.getPrpUpdTempList();
        ArrayList stockList = (ArrayList)session.getAttribute(listNme);
       String check = req.getParameter("chk");
      
       if(stkIdn.equals("ALL")){
        for(int i=0;i<stockList.size();i++){
         HashMap stockPkt = (HashMap)stockList.get(i);
          stkIdn = (String)stockPkt.get("stk_idn");
          if(check.equals("true")){
              prpUpdList.add(stkIdn);
           }else{
             prpUpdList.remove(stkIdn); 
          }}
        
        }else{
        if(check.equals("true")){
            prpUpdList.add(stkIdn);
         
        }else{
            prpUpdList.remove(stkIdn);
          
        }
       }

        info.setPrpUpdTempList(prpUpdList);
        return null;
    }
    
    public ActionForward  excel(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");

        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      String stkIdn = util.nvl(req.getParameter("stkIdn"));
      String stt = util.nvl(req.getParameter("stt"));
      String lstNme = (String)gtMgr.getValue("lstNmeRLT");
      HashMap stockList = (HashMap)gtMgr.getValue(lstNme);
      ArrayList selectstkIdnLst = (ArrayList)gtMgr.getValue(lstNme+"_SEL");
      if(selectstkIdnLst==null)
          selectstkIdnLst = new ArrayList();
      
     
      if(stt.equals("true")){
          selectstkIdnLst.add(stkIdn);
      }else{
          selectstkIdnLst.remove(stkIdn);
      }
      if(stkIdn.equals("ALL")){
          selectstkIdnLst =new ArrayList();
          if(stt.equals("true")){
         Set<String> keys = stockList.keySet();
          for(String key: keys){
                 selectstkIdnLst.add(key);
          }}
      }
            
        gtMgr.setValue(lstNme+"_SEL", selectstkIdnLst);
      
       return null;
    }
    
  public ActionForward GtList(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
    HttpSession session = req.getSession(false);
    InfoMgr info = (InfoMgr)session.getAttribute("info");
    GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");

    DBUtil util = new DBUtil();
    DBMgr db = new DBMgr();
    if(info!=null){
    db.setCon(info.getCon());
    util.setDb(db);
    util.setInfo(info);
    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
    util.setLogApplNm(info.getLoApplNm());
      String stkIdn = req.getParameter("stkIdn");
      String isChecked =  req.getParameter("stt");
      HashMap dtlMap = new HashMap();
      String updSql = "";
      ArrayList ary = new ArrayList();
      String lstNme = req.getParameter("lstNme");
      ArrayList selectstkIdnLst = (ArrayList)gtMgr.getValue(lstNme+"_SEL");
      if(selectstkIdnLst==null)
        selectstkIdnLst = new ArrayList();
      HashMap stockList = (HashMap)gtMgr.getValue(lstNme);
        ArrayList stockIdnLst =new ArrayList();
         Set<String> keys = stockList.keySet();
                for(String key: keys){
               stockIdnLst.add(key);
               }
      
      if(stkIdn.equals("ALL")){
          if(isChecked.equals("true")){
         selectstkIdnLst = stockIdnLst;
        
       }else{
           selectstkIdnLst = new ArrayList();
          }
       }else{
           if(isChecked.equals("true")){
           selectstkIdnLst.add(stkIdn);
           }else{
           selectstkIdnLst.remove(stkIdn); 
           }
       }
      dtlMap.put("selIdnLst",selectstkIdnLst);
      dtlMap.put("pktDtl", stockList);
      dtlMap.put("flg", "M");
      HashMap ttlMap = util.getTTL(dtlMap);
     
      gtMgr.setValue(lstNme+"_SEL",selectstkIdnLst);
      
      
      StringBuffer sb = new StringBuffer();
      sb.append("<value>");
      sb.append("<qty>"+util.nvl((String)ttlMap.get("MQ"),"0") +"</qty>");
      sb.append("<cts>"+util.nvl((String)ttlMap.get("MW"),"0") +"</cts>");
      sb.append("<vlu>"+util.nvl((String)ttlMap.get("MV"),"0") +"</vlu>");
      sb.append("<avg>"+util.nvl((String)ttlMap.get("MA"),"0") +"</avg>");
      sb.append("<base>"+util.nvl((String)ttlMap.get("MB"),"0") +"</base>");
      sb.append("<rapvlu>"+util.nvl((String)ttlMap.get("MR"),"0") +"</rapvlu>");
      sb.append("</value>");
      
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
      res.getWriter().write("<values>"+sb.toString()+"</values>");
      
    }
      return null;
  } 
  
    
    public ActionForward  remove(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        info.setPrpUpdTempList(new ArrayList());
      return null;
    }
    public AjaxLabAction() {
        super();
    }  
}
