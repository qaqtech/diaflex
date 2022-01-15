package ft.com.boxsje;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.dao.boxDet;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import ft.com.lab.FinalLabSelectionForm;
import ft.com.lab.LabIssueForm;
import ft.com.lab.LabIssueImpl;
import ft.com.lab.LabIssueInterface;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class BoxToBox extends DispatchAction {
    public ActionForward load(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        util.updAccessLog(req,res,"Box to Box", "box to box load");
          BoxToBoxForm boxForm = (BoxToBoxForm)form;
          GenericInterface genericInt = new GenericImpl();
          ArrayList boxSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_boxSrchLst") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_boxSrchLst"); 
          session.setAttribute("boxSrchLst", boxSrchList);
          info.setGncPrpLst(boxSrchList);
        
          return am.findForward("load");
        }
        
    }
    
    
  public ActionForward fetch(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          util.updAccessLog(req,res,"Box To Box", "fetch start");
          BoxToBoxForm boxForm = (BoxToBoxForm)form;
       
 

          ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_boxSrchLst") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_boxSrchLst");
  info.setGncPrpLst(genricSrchLst);
       ArrayList stockList = new ArrayList();
  String vnm = util.nvl((String)boxForm.getValue("vnmLst"));       
        HashMap mprp = info.getMprp();
        HashMap prp = info.getPrp();
      if(!vnm.equals("")){
           vnm = util.getVnm(vnm);
          FetchResult(req ,res, vnm );
         }else{
        HashMap paramsMap = new HashMap();
       
      for(int i=0;i<genricSrchLst.size();i++){
          ArrayList prplist =(ArrayList)genricSrchLst.get(i);
          String lprp = (String)prplist.get(0);
          String flg= (String)prplist.get(1);
          String typ = util.nvl((String)mprp.get(lprp+"T"));
          String prpSrt = lprp ;  
          if(flg.equals("M")) {
          
              ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
              ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
              for(int j=0; j < lprpS.size(); j++) {
              String lSrt = (String)lprpS.get(j);
              String lVal = (String)lprpV.get(j);    
              String reqVal1 = util.nvl((String)boxForm.getValue(lprp + "_" + lVal),"");
              if(!reqVal1.equals("")){
              paramsMap.put(lprp + "_" + lVal, reqVal1);
              }
                 
              }
          }else{
          String fldVal1 = util.nvl((String)boxForm.getValue(lprp+"_1"));
          String fldVal2 = util.nvl((String)boxForm.getValue(lprp+"_2"));
          if(typ.equals("T")){
              fldVal1 = util.nvl((String)boxForm.getValue(lprp+"_1"));
              fldVal2 = fldVal1;
          }
          if(fldVal2.equals(""))
          fldVal2=fldVal1;
          if(!fldVal1.equals("") && !fldVal2.equals("")){
              paramsMap.put(lprp+"_1", fldVal1);
              paramsMap.put(lprp+"_2", fldVal2);
          }
          }
      }
      paramsMap.put("stt", "MKAV");
      paramsMap.put("mdl", "BOX_VIEW");
      util.genericSrch(paramsMap);
          
      }
     stockList = SearchResult(req,res,boxForm);
 
    if(stockList.size()>0){
    HashMap totals = GetTotal(req,res);
    req.setAttribute("totalMap", totals);
        ArrayList boxTypLst = new ArrayList();
        ArrayList boxLst = (ArrayList)prp.get("BOX_TYPV");   
        for(int i = 0 ;i< boxLst.size();i++) {
              String val = (String)boxLst.get(i);
            boxDet box = new boxDet();
            box.setBoxVal(val);
            boxTypLst.add(box);
       }
          boxForm.setValue("boxTypLst",boxTypLst);
      session.setAttribute("totalMap", totals);
    }
   session.setAttribute("StockList", stockList);
  req.setAttribute("view", "yes");
  return am.findForward("load");
      }
  }
  public void FetchResult(HttpServletRequest req ,HttpServletResponse res ,String vnm){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
    String delQ = " Delete from gt_srch_rslt ";
    int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
    String srchRefQ = "  Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1  ,  cert_lab , rmk ) " + 
    " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'Z' , qty , cts , sk1 ,  cert_lab , tfl3 "+
    "  from mstk b where stt='MKAV'  and   ( vnm in ("+vnm+") or tfl3 in ("+vnm+"))";
    
   ct = db.execUpd(" Srch Prp ", srchRefQ, new ArrayList());
    
    String pktPrp = "pkgmkt.pktPrp(0,?)";
    ArrayList ary = new ArrayList();
    ary.add("BOX_VIEW");
    ct = db.execCall(" Srch Prp ", pktPrp, ary);
  
  }
  public ActionForward save(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          util.updAccessLog(req,res,"Box To Box", "fetch start");
        BoxToBoxForm boxForm = (BoxToBoxForm)form;
          int ct=0;
          int count =0;
          int sr = 0;
        ArrayList stockList = (ArrayList)session.getAttribute("StockList");
        if(stockList!=null && stockList.size()>0){
            for(int i=0;i<stockList.size();i++){
                HashMap stockPkt = (HashMap)stockList.get(i);
                String stkIdn = (String)stockPkt.get("stk_idn");
                String lab = (String)stockPkt.get("lab");
                sr = i+1;
                String ischeck = util.nvl((String)boxForm.getValue("CHK_"+sr));
              if(ischeck.equals("yes")){
              String boxTypVal = util.nvl((String)boxForm.getValue("box_"+stkIdn));
                ArrayList ary = new ArrayList();
                ary.add(stkIdn);
                ary.add("BOX_TYP");
                ary.add(boxTypVal);
                ary.add(lab);
                ary.add("C");
                
                String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? , pLab=> ?, pPrpTyp => ? )";
                 ct = db.execCall("stockUpd",stockUpd, ary);
                if(ct>0)
                    count++;
              }
                
            }}
          if(count>0){
            req.setAttribute("msg", "Total packets transfer : "+count);
          }
        return am.findForward("load");
      }
  }
  public HashMap GetTotal(HttpServletRequest req , HttpServletResponse res){
    HttpSession session = req.getSession(false);
    InfoMgr info = (InfoMgr)session.getAttribute("info");
    DBUtil util = new DBUtil();
    DBMgr db = new DBMgr(); 
    db.setCon(info.getCon());
    util.setDb(db);
    util.setInfo(info);
    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
    util.setLogApplNm(info.getLoApplNm());
  HashMap gtTotalMap = new HashMap();
  String gtTotal ="Select count(*) qty, sum(cts) cts from gt_srch_rslt where flg = ?";
  ArrayList ary = new ArrayList();
  ary.add("Z");
  ResultSet rs = db.execSql("getTotal", gtTotal, ary);
  try {
  if (rs.next()) {
  gtTotalMap.put("qty",util.nvl(rs.getString("qty")));
  gtTotalMap.put("cts", util.nvl(rs.getString("cts")));
  }
      rs.close();
  } catch (SQLException sqle) {
  // TODO: Add catch code
  sqle.printStackTrace();
  }

  return gtTotalMap ;
  }
  public ArrayList SearchResult(HttpServletRequest req ,HttpServletResponse res,BoxToBoxForm boxForm  ){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
  
      ArrayList pktList = new ArrayList();
     ArrayList vwPrpLst = viewPrpLst(req,res);
 
  String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt , qty , cts , cert_lab , rmk ";

  

  for (int i = 0; i < vwPrpLst.size(); i++) {
      String fld = "prp_";
      int j = i + 1;
      if (j < 10)
          fld += "00" + j;
      else if (j < 100)
          fld += "0" + j;
      else if (j > 100)
          fld += j;

     

      srchQ += ", " + fld;
     
   }

  
  String rsltQ = srchQ + " from gt_srch_rslt where flg =? order by sk1 , cts ";
  
  ArrayList ary = new ArrayList();
  ary.add("Z");
  ResultSet rs = db.execSql("search Result", rsltQ, ary);
  try {
      while(rs.next()) {
        
          String stkIdn = util.nvl(rs.getString("stk_idn"));
          HashMap pktPrpMap = new HashMap();
          pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
          String vnm = util.nvl(rs.getString("vnm"));
          pktPrpMap.put("vnm",vnm);
             String tfl3 = util.nvl(rs.getString("rmk"));
//             if(vnmLst.indexOf(tfl3)!=-1 && !tfl3.equals("")){
//                 if(vnmLst.indexOf("'")==-1)
//                     vnmLst =  vnmLst.replaceAll(tfl3,"");
//                 else
//                     vnmLst =  vnmLst.replaceAll("'"+tfl3+"'", "");
//             } else if(vnmLst.indexOf(vnm)!=-1 && !vnm.equals("")){
//                 if(vnmLst.indexOf("'")==-1)
//                     vnmLst =  vnmLst.replaceAll(vnm,"");
//                 else
//                     vnmLst =  vnmLst.replaceAll("'"+vnm+"'", "");
//             }      
          pktPrpMap.put("stk_idn",stkIdn);
          pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
          pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
            pktPrpMap.put("lab",util.nvl(rs.getString("cert_lab")));
          for(int j=0; j < vwPrpLst.size(); j++){
               String prp = (String)vwPrpLst.get(j);
                
                String fld="prp_";
                if(j < 9)
                        fld="prp_00"+(j+1);
                else    
                        fld="prp_0"+(j+1);
                
                String val = util.nvl(rs.getString(fld)) ;
                if(prp.equals("BOX_TYP"))
                    boxForm.setValue("box_"+stkIdn, val);
                  
                  pktPrpMap.put(prp, val);
                   }
                        
              pktList.add(pktPrpMap);
          }
      rs.close();
  } catch (SQLException sqle) {

      // TODO: Add catch code
      sqle.printStackTrace();
  }
//  if(!vnmLst.equals("")){
//        vnmLst = util.pktNotFound(vnmLst);
//        req.setAttribute("vnmNotFnd", vnmLst);
//    }
  return pktList;
  }
  
  public ArrayList viewPrpLst(HttpServletRequest req ,HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      ArrayList asViewPrp = (ArrayList)session.getAttribute("boxViewLst");
      try {
          if (asViewPrp == null) {

              asViewPrp = new ArrayList();
              ResultSet rs1 =
                  db.execSql(" Vw Lst ", "Select prp  from rep_prp where mdl = 'BOX_VIEW' and flg='Y' order by rnk ",
                             new ArrayList());
              while (rs1.next()) {

                  asViewPrp.add(rs1.getString("prp"));
              }
              rs1.close();
              session.setAttribute("boxViewLst", asViewPrp);
          }

      } catch (SQLException sqle) {
          // TODO: Add catch code
          sqle.printStackTrace();
      }
      
      return asViewPrp;
  }
    public BoxToBox() {
        super();
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
              util.updAccessLog(req,res,"Box to Box", "Unauthorized Access");
              else
              util.updAccessLog(req,res,"Box to Box", "init");
          }
          }
          return rtnPg;
          }
}
