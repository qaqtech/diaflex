package ft.com.rough;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.dao.MNme;
import ft.com.dao.Mprc;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import ft.com.mix.MixFinalIssueForm;
import ft.com.mix.MixFinalIssueImpl;
import ft.com.mix.MixFinalIssueInterface;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class RoughGroupAction  extends DispatchAction {
    public RoughGroupAction() {
        super();
    }
    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
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
           RoughGroupFrom udf =(RoughGroupFrom)af;
           
           
           util.updAccessLog(req,res,"Assort Issue", "load");
           
           udf.resetAll();
           ArrayList mprcList = getPrc(req,res);
           udf.setMprcList(mprcList);
           
           ArrayList empList = getEmp(req,res);
           udf.setEmpList(empList);
           
           ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_RGHGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_RGHGNCSrch");
           info.setGncPrpLst(assortSrchList);
         
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
          util.updAccessLog(req,res,"Mix Final issue", "fetch");
            RoughGroupFrom udf =(RoughGroupFrom)form;
     
  
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
       
        String stoneId = util.nvl((String)udf.getValue("vnmLst"));
        String empId = util.nvl((String)udf.getValue("empIdn"));
        String mprcIdn = util.nvl((String)udf.getValue("mprcIdn"));
            
            ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_RGHGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_RGHGNCSrch");
            info.setGncPrpLst(genricSrchLst);
          
        HashMap mprp = info.getMprp();
        String inStt="";
        String otStt ="";
        String isStt="";
        ArrayList ary = new ArrayList();
        ary.add(mprcIdn);
          ArrayList outLst = db.execSqlLst("mprcId", "select idn , in_stt , ot_stt , is_stt from mprc where idn=? and stt='A'", ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        if(rs.next()) {
         mprcIdn = util.nvl(rs.getString("idn"));
         inStt = util.nvl(rs.getString("in_stt"));
         otStt = util.nvl(rs.getString("ot_stt"));
         isStt = util.nvl(rs.getString("is_stt"));
        }
        rs.close();
          pst.close();
        
        HashMap params = new HashMap();
        params.put("stt", inStt);
        params.put("vnm",stoneId);
        params.put("empIdn", empId);
        params.put("mprcIdn", mprcIdn);
        HashMap paramsMap = new HashMap();
        for(int i=0;i<genricSrchLst.size();i++){
            ArrayList prplist =(ArrayList)genricSrchLst.get(i);
            String lprp = (String)prplist.get(0);
            String typ = util.nvl((String)mprp.get(lprp+"T"));
            String fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
            String fldVal2 = util.nvl((String)udf.getValue(lprp+"_2"));
            if(typ.equals("T")){
                fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
                fldVal2 = fldVal1;
            }
            if(fldVal2.equals(""))
            fldVal2=fldVal1;
            if(!fldVal1.equals("") && !fldVal2.equals("")){
                paramsMap.put(lprp+"_1", fldVal1);
                paramsMap.put(lprp+"_2", fldVal2);
            }
        
        }
        ArrayList stockList = null;
        if(paramsMap.size()>0){
        paramsMap.put("stt", inStt);
        paramsMap.put("mdl", "RGH_VIEW");
        paramsMap.put("MIX","Y");
        util.genericSrch(paramsMap);
    
        stockList = SearchResult(req,res, "");
        }else{
        stockList = FecthResult(req,res, params);
        }
        if(stockList.size()>0){
       HashMap totals = GetTotal(req,res);
        req.setAttribute("totalMap", totals);
        }
        req.setAttribute("view", "Y");
        udf.setValue("prcId", mprcIdn);
        udf.setValue("empId", empId);
        udf.setValue("in_stt", inStt);
        session.setAttribute("StockList", stockList);
          util.updAccessLog(req,res,"Mix Final issue", "fetch end");
        
        
    return am.findForward("load");
        }
    }
    
    public ArrayList SearchResult(HttpServletRequest req ,HttpServletResponse res, String vnmLst ){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList pktList = new ArrayList();
      if(info!=null){  
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       
        ArrayList vwPrpLst = ASPrprViw(req,res);
        String  srchQ =  " select sum(qty) qty , sum(cts) cts ,  ";

        int shInx = vwPrpLst.indexOf("SH")+1;
        String fldSh = "prp_00"+shInx;
        if(shInx > 9)
         fldSh = "prp_0"+shInx;
      
        int sizeInx = vwPrpLst.indexOf("MIX_SIZE")+1;
        String fldSize ="prp_00"+sizeInx;
        if(sizeInx > 9)
            fldSize ="prp_0"+sizeInx;
        
       
        
        String rsltQ = srchQ +fldSh +" sh ,"+fldSize+" sz , trunc(((sum(trunc(cts,2)* QUOT) / sum(trunc(cts,2))))) rteAvg  from gt_srch_rslt where flg =? group by "+fldSh+","+fldSize;
        
        ArrayList ary = new ArrayList();
        ary.add("Z");
        ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while(rs.next()) {

                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("qty", util.nvl(rs.getString("qty")));
                pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
                pktPrpMap.put("sh",util.nvl(rs.getString("sh"),"NA"));
                pktPrpMap.put("rte",util.nvl(rs.getString("rteAvg"),"NA"));
                pktPrpMap.put("size",util.nvl(rs.getString("sz"),"NA"));
                pktList.add(pktPrpMap);
                }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return pktList;
    }
    
    public ArrayList ASPrprViw(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList asViewPrp = (ArrayList)session.getAttribute("MixViewLst");
      if(info!=null){  
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();
               
              ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'RGH_VIEW' and flg='Y' order by rnk ",
                               new ArrayList());
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs1 = (ResultSet)outLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                 rs1.close();
                 pst.close();
                session.setAttribute("MixViewLst", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return asViewPrp;
    }
    
    public HashMap GetTotal(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      HashMap gtTotalMap = new HashMap();
      if(info!=null){  
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
    
    String gtTotal ="Select count(*) qty, sum(cts) cts from gt_srch_rslt where flg = ?";
    ArrayList ary = new ArrayList();
    ary.add("Z");
        ArrayList outLst = db.execSqlLst("getTotal", gtTotal, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
    try {
    if (rs.next()) {
    gtTotalMap.put("qty",util.nvl(rs.getString("qty")));
    gtTotalMap.put("cts", util.nvl(rs.getString("cts")));
    }
        rs.close();
        pst.close();
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }
      }
    return gtTotalMap ;
    }
    public ArrayList FecthResult(HttpServletRequest req,HttpServletResponse res, HashMap params){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList stockList = new ArrayList();
      if(info!=null){  
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       
        String stt = (String)params.get("stt");
        String vnm = (String)params.get("vnm");
        String mprcIdn = (String)params.get("mprcIdn");
        String empIdn = (String)params.get("empIdn");
        ArrayList ary = null;
        String flg = "ADD";
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        ary = new ArrayList();
        ary.add(stt);
        String srchRefQ = 
        "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 ,QUOT, rmk ) " + 
        " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'Z' , qty , cts , sk1 ,nvl(upr,cmp), tfl3  "+
        "     from mstk b "+
        " where stt =? and cts > 0  ";

         if(vnm.length()> 2){
         vnm = util.getVnm(vnm);
         srchRefQ = srchRefQ+" and ( b.vnm in ("+vnm+") or b.tfl3 in ("+vnm+") ) " ;
         }
          
          ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
          System.out.println("query: "+srchRefQ+" @ary: "+ary);
          flg = "VNM";
        
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        ary = new ArrayList();
        ary.add("RGH_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        
        stockList = SearchResult(req ,res, vnm);
      }
        return stockList;
    }
    
    
    public ActionForward Issue(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          util.updAccessLog(req,res,"Mix Final issue", "fetch");
            RoughGroupFrom udf =(RoughGroupFrom)form;
          String empId = (String)udf.getValue("empId");
          String prcId = (String)udf.getValue("prcId");
          String in_stt = (String)udf.getValue("in_stt");
          HashMap mprp = info.getMprp();
          HashMap prp = info.getPrp();
          Enumeration reqNme = req.getParameterNames(); 
          int   issueIdn=0;
          ArrayList vwPrpLst = (ArrayList)session.getAttribute("MixViewLst");
          int shInx = vwPrpLst.indexOf("SH")+1;
          String fldSh = "prp_00"+shInx;
          String srtSh = "srt_00"+shInx;
          if(shInx > 9){
           fldSh = "prp_0"+shInx;
           srtSh= "srt_0"+shInx;
          }
          
          int sizeInx = vwPrpLst.indexOf("MIX_SIZE")+1;
          String fldSize ="prp_00"+sizeInx;
          String fldSzSrt="srt_00"+sizeInx;
          if(sizeInx > 9){
              fldSize ="prp_0"+sizeInx;
              fldSzSrt="srt_0"+sizeInx;
          }
          db.setAutoCommit(false);
          boolean isCommit=true;

            try {
                while (reqNme.hasMoreElements()) {
                    String paramNm = (String)reqNme.nextElement();
                    if (paramNm.indexOf("cb@") > -1) {
                        String val = req.getParameter(paramNm);
                        String[] valLst = val.split("@");
                        String sh = valLst[1];
                        String sz = valLst[2];

                        if (issueIdn == 0) {
                            ArrayList params = new ArrayList();
                            params.add(prcId);
                            params.add(empId);
                            ArrayList out = new ArrayList();
                            out.add("I");
                            CallableStatement cst =
                                db.execCall("findIssueId", "ISS_RTN_PKG.GEN_HDR(pPRCID => ?, pEMPID => ?,  pIssId => ?)",
                                            params, out);
                            issueIdn = cst.getInt(3);
                          cst.close();
                          cst=null;
                        }
                        if(issueIdn>0){
                        int ttlQty = 0;
                        double ttlCts = 0;
                        double ttlVal = 0;
                        double avgRte = 0;
                        String idnlst = "";
                        int shSrt=0;
                        int szSrt=0;
              
                      ArrayList outLst = db.execSqlLst("gt fetch", "select stk_idn , qty , cts , (cts*QUOT) val , "+srtSh+" shsrt, "+fldSzSrt+" szsrt from gt_srch_rslt where " +
                                       fldSh + "='" + sh + "' and " + fldSize +
                                       "='" + sz + "'", new ArrayList());
                      PreparedStatement pst = (PreparedStatement)outLst.get(0);
                      ResultSet rs = (ResultSet)outLst.get(1);
                        while (rs.next()) {
                            ttlQty = ttlQty + rs.getInt("qty");
                            ttlCts = ttlCts + rs.getDouble("cts");
                            ttlVal = ttlVal + rs.getDouble("val");
                            idnlst = idnlst + " , " + rs.getString("stk_idn");
                            shSrt= rs.getInt("shsrt");
                            szSrt= rs.getInt("szsrt");
                        }
                        rs.close();
                        pst.close();
                        if(ttlCts>0){
                        idnlst = idnlst.replaceFirst(",", "");
                        ttlCts = util.roundToDecimals(ttlCts, 2);
                        avgRte = ttlVal / ttlCts;
                        avgRte = util.roundToDecimals(avgRte, 2);
                        ArrayList ary = null;
                        int newIdn =0;
                        int ct=0;
                        try {
                            String insMst =
                                "MIX_PKG.GEN_PKT(pStt => ?,pQty => ? ,pCts=> ? , pPktTyp => ?,pIdn =>?,pVnm =>?)";
                            ary = new ArrayList();
                            ary.add(in_stt);
                            ary.add(String.valueOf(ttlQty));
                            ary.add(String.valueOf(ttlCts));
                            ary.add("MIX");

                            ArrayList out = new ArrayList();
                            out.add("I");
                            out.add("V");
                            CallableStatement cst =
                                db.execCall("findMstkId", insMst, ary, out);
                             newIdn = cst.getInt(ary.size() + 1);
                            String vnm = cst.getString(ary.size() + 2);
                          cst.close();
                          cst=null;
                            if(newIdn>0){
                            ArrayList prpLst = (ArrayList)prp.get("SHV");
                            ArrayList srtLst = (ArrayList)prp.get("SHS");
                            ary = new ArrayList();
                            ary.add(String.valueOf(newIdn));
                            ary.add("SH");
                            ary.add(prpLst.get(srtLst.indexOf(String.valueOf(shSrt))));
                            String stockUpd =
                                "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?)";
                            ct = db.execCallDir("stockUpd", stockUpd, ary);

                            ary = new ArrayList();
                            ary.add(String.valueOf(newIdn));
                            ary.add("MIX_SIZE");
                            ary.add(sz);
                            stockUpd =
                                    "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?)";
                            ct = db.execCallDir("stockUpd", stockUpd, ary);

                            ary = new ArrayList();
                            ary.add(String.valueOf(newIdn));
                            ary.add("RTE");
                            ary.add(String.valueOf(avgRte));
                            stockUpd =
                                    "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?)";
                            ct = db.execCallDir("stockUpd", stockUpd, ary);

                            ary = new ArrayList();
                            ary.add(String.valueOf(issueIdn));
                            ary.add(String.valueOf(newIdn));

                            String issuePkt =
                                "MIX_IR_PKG.ISS_PKT(pIssId => ?, pStkIdn => ?)";
                            ct = db.execCall("issuePkt", issuePkt, ary);
                            }else{
                                isCommit=false;
                            }
                        } catch (SQLException sqle) {
                            // TODO: Add catch code
                            sqle.printStackTrace();
                            isCommit=false;
                        }
                        if(ct>0){
                        String updMstk =
                            "update mstk set pkt_rt=? , stt=? where idn in (" +
                            idnlst + ")";
                        ary = new ArrayList();
                        ary.add(String.valueOf(newIdn));
                        ary.add("ASRT_MRG");
                        ct = db.execUpd("update mstk", updMstk, ary);
                        }else{
                            isCommit=false;
                        }
                       if(ct<0)
                           isCommit=false;
                        }else{
                            isCommit=false;
                        }

                    }
                }}

            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
                isCommit=false;
            } finally {
             db.setAutoCommit(true);
            }
          if(isCommit){
              req.setAttribute( "msg","Requested packets get Issue with Issue Id "+issueIdn);
              db.doCommit();
          }else{
              req.setAttribute("msg", "Error in process..");
              db.doRollBack();
          }
     
          return am.findForward("load");   
      }
    }
    
    public ArrayList getPrc(HttpServletRequest req ,HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList prcList = new ArrayList();
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
       
        ArrayList ary = new ArrayList();
        HashMap prcSttMap = new HashMap();
        
        
          String grp = util.nvl(req.getParameter("grp"));
          
          String prcSql = "select idn, prc , in_stt from mprc where  stt = ?  " ;
              if(!grp.equals("")){
              prcSql+= " and grp in ("+grp+") ";
              }
              prcSql+=" order by srt";
          ary = new ArrayList();
          ary.add("A");
        ArrayList outLst = db.execSqlLst("prcSql", prcSql, ary);
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
                  prcSttMap.put(mprcId, mprc);
              }
              rs.close();
              pst.close();
          } catch (SQLException sqle) {
              // TODO: Add catch code
              sqle.printStackTrace();
          }
          
        
        
      
        
      }
        return prcList;
    }
    
    public ArrayList getEmp(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
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
        ArrayList outLst = db.execSqlLst("empSql", empSql, ary);
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
                util.updAccessLog(req,res,"Split Return Action", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Split Return Action", "init");
            }
            }
            return rtnPg;
            }
    
}
