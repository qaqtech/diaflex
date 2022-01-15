package ft.com.marketing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;
import ft.com.assort.AssortReturnForm;
import ft.com.assort.AssortReturnImpl;
import ft.com.assort.AssortReturnInterface;
import ft.com.dao.GtPktDtl;
import ft.com.dao.MNme;
import ft.com.dao.Mprc;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.sql.CallableStatement;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class CertReturnAction extends DispatchAction {

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
          
          CertReturnForm udf = (CertReturnForm)af;
          udf.resetAll();
          GenericInterface genericInt = new GenericImpl();
          ArrayList mprcList = getPrc(req, res,db);
          udf.setProcessLst(mprcList);
          
          ArrayList empList = getEmp(req,res,db);
          udf.setEmpList(empList);
          
//          ArrayList assortSrchList = genericInt.genricSrch(req,res,"mktGNCSrch","MKT_SRCH");
//          info.setGncPrpLst(assortSrchList);
          
          return am.findForward("load");
      }
      
      }
  
    public ActionForward fecth(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");

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
        CertReturnForm udf = (CertReturnForm)form;

    
        String mprcIdn = util.nvl((String)udf.getValue("mprcIdn"),"0");
        String stoneId = util.nvl((String)udf.getValue("vnmLst"));
        String empId = util.nvl((String)udf.getValue("empIdn"),"0");
        String issueId = util.nvl((String)udf.getValue("issueId"),"0");
        String TYP = util.nvl((String)udf.getValue("TYP"));
        String idn = util.nvl((String)udf.getValue("idn"),"0");
            
        HashMap params = new HashMap();
        params.put("vnm",stoneId);
        params.put("empIdn", empId);
        params.put("issueId", issueId);
        params.put("mprcIdn", mprcIdn);
        params.put("typ", TYP);
        params.put("Idn", idn);
        HashMap stockList = FecthResultGt(req,res, params);
            String lstNme = "CRTRT_"+info.getLogId()+"_"+util.getToDteGiveFrmt("yyyyMMdd");
            gtMgr.setValue(lstNme+"_SEL",new ArrayList());
            gtMgr.setValue(lstNme, stockList);
            gtMgr.setValue("lstNmeCRTRT", lstNme);
            if(stockList.size()>0){
            HashMap dtlMap = new HashMap();
            ArrayList selectstkIdnLst = new ArrayList();
            Set<String> keys = stockList.keySet();
                   for(String key: keys){
                  selectstkIdnLst.add(key);
                   }
            dtlMap.put("selIdnLst",selectstkIdnLst);
            dtlMap.put("pktDtl", stockList);
            dtlMap.put("flg", "M");
            HashMap ttlMap = util.getTTL(dtlMap);
            gtMgr.setValue(lstNme+"_TTL", ttlMap);
            }
        
            req.setAttribute("view", "Y");
            udf.setValue("prcId", mprcIdn);
            udf.setValue("empId", empId);
               udf.setValue("lstNme", lstNme);
       
          gtMgr.setValue(lstNme+"_SEL", new ArrayList());

        return am.findForward("load");
        }
    }
  
    public ActionForward Return(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");

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
            CertReturnForm udf = (CertReturnForm)form;

       String lstNme = (String)udf.getValue("lstNme");
        ArrayList RtnMsgList = new ArrayList();
        String msg = "";
        int cnt = 0;
     
        String rtnPktStr = "";
        ArrayList selectList = (ArrayList)gtMgr.getValue(lstNme+"_SEL");
        HashMap stockList = (HashMap)gtMgr.getValue(lstNme);

        
          if(selectList!=null && selectList.size()>0) {
              for(int i=0;i<selectList.size();i++){
            String stkIdn = (String)selectList.get(i);
            GtPktDtl pktDtl = (GtPktDtl)stockList.get(stkIdn);
            String issId = pktDtl.getValue("issIdn");
           String vnm = pktDtl.getValue("vnm");
           String updateIssRtnDtl = "update iss_rtn_dtl set stt='RT' where iss_id =? and iss_stk_idn = ?";
            ArrayList ary = new ArrayList();
            ary.add(issId);
            ary.add(stkIdn);
             cnt = db.execUpd("updateIssrtnDtl", updateIssRtnDtl, ary);     
          if(cnt>0)
              rtnPktStr = rtnPktStr+" "+vnm+",";
       }}
    if(rtnPktStr.length() >0)
        req.setAttribute("msg","packets returns :-"+rtnPktStr);
            udf.reset();
          GtMgrReset(req);
            return am.findForward("load");
        }
    }
  
    public HashMap FecthResultGt(HttpServletRequest req,HttpServletResponse res, HashMap params){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      HashMap stockList = new HashMap();
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
      
        String vnm = (String)params.get("vnm");
        String mprcId = (String)params.get("mprcIdn");
        String issId = (String)params.get("issueId");
        String empIdn = (String)params.get("empIdn");
        String typ = (String)params.get("typ");
        String idn = (String)params.get("Idn");
          
        ArrayList ary = null;
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        
        String srchRefQ = 
        "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , srch_id , rmk , rln_idn , sk1 ) " + 
        " select  a.pkt_ty, a.idn, a.vnm,  a.dte, a.stt , 'Z' , a.qty , a.cts , b.iss_id , a.tfl3 , b.iss_emp_id , a.sk1 "+
        "   from mstk a , iss_rtn_dtl b , iss_rtn c where a.idn = b.iss_stk_idn and b.stt='IS' and b.iss_id = c.iss_id  "+
        " and a.stt =a.stt and a.cts > 0   ";
        ary = new ArrayList();
        if(vnm.length()>2){
           vnm = util.getVnm(vnm);
            srchRefQ = srchRefQ+" and ( a.vnm in ("+vnm+") or a.tfl3 in ("+vnm+") or a.cert_no in ("+vnm+") ) " ;
        }
        if(!mprcId.equals("0")){
            srchRefQ = srchRefQ+" and c.prc_id = ? " ;
            ary.add(mprcId);
        }
          if(!issId.equals("0")){
              srchRefQ = srchRefQ+" and b.iss_id = ? " ;
              ary.add(issId);
          }
        if(!empIdn.equals("0") && !empIdn.equals("")){
            
            srchRefQ = srchRefQ+" and b.iss_emp_id = ? " ;
            ary.add(empIdn);
        }
          if(!idn.equals("0") && !idn.equals("")){
              String str = " and exists (select 1 from jandtl d where d.mstk_idn = a.idn and d.stt in('IS','AP') and d.idn = ?)";
              if(typ.equals("SALE"))
                 str = " and exists (select 1 from jansal d where d.mstk_idn = a.idn and d.stt in('SL','DLV','ADJ') and d.idn = ?)";
              else if(typ.equals("DLV"))
                 str = " and exists (select 1 from dlvdtl d where d.mstk_idn = a.idn and d.stt='DLV' and d.idn = ?)";
              else if(typ.equals("AP"))
                str = " and exists (select 1 from jandtl d where d.mstk_idn = a.idn and d.stt in('AP','SL') and d.idn = ?)";

              srchRefQ = srchRefQ+str ;
              ary.add(idn);
          }
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        
        String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
        ary = new ArrayList();
        ary.add("MKT_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        
         
        stockList = SearchResultGt(req ,res, vnm);
      }
        return stockList;
    }
    
    public HashMap SearchResultGt(HttpServletRequest req ,HttpServletResponse res, String vnmLst ){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      HashMap pktList = new HashMap();
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
       
        ArrayList vwPrpLst = mktPrpViw(req,res);
        String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt , qty , cts , srch_id ,rmk , byr.get_nm(rln_idn) emp ";

        

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
        ArrayList  outLst = db.execSqlLst("search Result", rsltQ, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while(rs.next()) {
               
                  String stk_idn = util.nvl(rs.getString("stk_idn"));
                  GtPktDtl pktPrpMap = new GtPktDtl();
                  pktPrpMap.setStt(util.nvl(rs.getString("stt")));
                  String vnm = util.nvl(rs.getString("vnm"));
                  pktPrpMap.setVnm(vnm);
                  pktPrpMap.setValue("emp", util.nvl(rs.getString("emp")));
                  pktPrpMap.setValue("vnm", vnm);

                    String tfl3 = util.nvl(rs.getString("rmk"));
                    if(vnmLst.indexOf(tfl3)!=-1 && !tfl3.equals("")){
                        if(vnmLst.indexOf("'")==-1 && !tfl3.equals(""))
                            vnmLst =  vnmLst.replaceAll(tfl3,"");
                        else
                            vnmLst =  vnmLst.replaceAll("'"+tfl3+"'", "");
                    } else if(vnmLst.indexOf(vnm)!=-1 && !vnm.equals("")){
                        if(vnmLst.indexOf("'")==-1)
                            vnmLst =  vnmLst.replaceAll(vnm,"");
                        else
                            vnmLst =  vnmLst.replaceAll("'"+vnm+"'", "");
                    }      
                  pktPrpMap.setPktIdn(rs.getLong("stk_idn"));
                    pktPrpMap.setValue("qty",util.nvl(rs.getString("qty")));
                    pktPrpMap.setValue("cts",util.nvl(rs.getString("cts")));
                       pktPrpMap.setValue("issIdn",util.nvl(rs.getString("srch_id")));
                for(int j=0; j < vwPrpLst.size(); j++){
                     String prp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                      
                           String val = util.nvl(rs.getString(fld)) ;
                           pktPrpMap.setValue(prp, val);
                    }
                              
                  pktList.put(stk_idn,pktPrpMap);
                }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
        ArrayList list = (ArrayList)req.getAttribute("msgList");
        if(list!=null && list.size()>0){
        }else{
        if(!vnmLst.equals("")){
            vnmLst = util.pktNotFound(vnmLst);
            req.setAttribute("vnmNotFnd", vnmLst);
        }
        }
      }
        return pktList;
    }
    public ArrayList mktPrpViw(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList asViewPrp = (ArrayList)session.getAttribute("mktViewLst");
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
      
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();
          
                
              ArrayList  outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'MKT_VIEW' and flg='Y' order by rnk ",
                               new ArrayList());
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs1 = (ResultSet)outLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                pst.close();
                session.setAttribute("mktViewLst", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return asViewPrp;
    }
    public ArrayList getEmp(HttpServletRequest req , HttpServletResponse res,DBMgr db){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
    
      ArrayList empList = new ArrayList();
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
        ArrayList ary = new ArrayList();
      
        String empSql = "select nme_idn, nme from nme_v where typ = 'EMPLOYEE' order by nme";
        ArrayList  outLst = db.execSqlLst("empSql", empSql, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                MNme emp = new MNme();
                emp.setEmp_idn(rs.getString("nme_idn"));
                emp.setEmp_nme(rs.getString("nme"));
                empList.add(emp);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return empList;
    }
    
    public ArrayList getPrc(HttpServletRequest req ,HttpServletResponse res,DBMgr db){
      
          DBUtil util = new DBUtil();
         
        ArrayList ary = new ArrayList();
        ArrayList prcList = new ArrayList();
        
        
          String grp = util.nvl(req.getParameter("grp"));
          
          String prcSql = "select idn, prc , in_stt from mprc where  stt = ? " ;
              if(!grp.equals("")){
              prcSql+= " and grp in ("+grp+") ";
              }
              prcSql+=" order by srt";
          ary = new ArrayList();
          ary.add("A");
      ArrayList  outLst = db.execSqlLst("prcSql", prcSql, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
          try {
              while (rs.next()) {
                  Mprc mprc = new Mprc();
                  String mprcId = rs.getString("idn");
                  mprc.setMprcId(rs.getString("idn"));
                  mprc.setPrc(rs.getString("prc"));
                  mprc.setIn_stt(rs.getString("in_stt"));
                  prcList.add(mprc);
                 
              }
              rs.close();
              pst.close();
          } catch (SQLException sqle) {
              // TODO: Add catch code
              sqle.printStackTrace();
          }
          
        return prcList;
    }
    
    public void GtMgrReset(HttpServletRequest req){
          HttpSession session = req.getSession(false);
          GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
          String lstNme = (String)gtMgr.getValue("lstNmeCRTRT");
          HashMap gtMgrMap = (HashMap)gtMgr.getValues();
          gtMgrMap.remove(lstNme);
           gtMgrMap.remove(lstNme+"_SEL");
           gtMgrMap.remove(lstNme+"_TTL");
           gtMgrMap.remove("lstNmeCRTRT");
         }
    public CertReturnAction() {
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
                util.updAccessLog(req,res,"Cert Return", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Cert Return", "init");
            }
            }
            return rtnPg;
            }
}
