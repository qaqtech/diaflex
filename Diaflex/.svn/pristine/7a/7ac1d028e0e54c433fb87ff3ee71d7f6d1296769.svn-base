package ft.com.mixLkhi;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;
import ft.com.JspUtil;
import ft.com.dao.GtPktDtl;
import ft.com.dao.MNme;
import ft.com.dao.Mprc;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.sql.CallableStatement;
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

public class MixAssortLKhiRtn extends DispatchAction {
    public MixAssortLKhiRtn() {
        super();
    }
    
    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
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
           MixAssortLKhiRtnFrom udf = (MixAssortLKhiRtnFrom)af;
       
          GenericInterface genericInt = new GenericImpl();
          
          udf.resetAll();
          ArrayList mprcList = getPrc(req,res);
          udf.setMprcList(mprcList);
          
          ArrayList empList = getEmp(req,res);
          udf.setEmpList(empList);
          
          ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIX_RTNSRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIX_RTNSRCH");
          info.setGncPrpLst(assortSrchList);
          return am.findForward("load"); 
         
      }
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
        ArrayList  rsLst = db.execSqlLst("empSql", empSql, ary);
        PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
        ResultSet  rs =(ResultSet)rsLst.get(1);
        try {
            while (rs.next()) {
                MNme emp = new MNme();
                emp.setEmp_idn(rs.getString("nme_idn"));
                emp.setEmp_nme(rs.getString("nme"));
                empList.add(emp);
            }
            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return empList;
    }
    public ActionForward fetch(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
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
           MixAssortLKhiRtnFrom udf = (MixAssortLKhiRtnFrom)af;
           String mprcIdn = util.nvl((String)udf.getValue("mprcIdn"));
           String empIdn = util.nvl((String)udf.getValue("empIdn"));
          HashMap mprp = info.getMprp();
          HashMap prp = info.getPrp();
          ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIX_RTNSRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIX_RTNSRCH");
          info.setGncPrpLst(genricSrchLst);
         String lprpLst = "";
          String lprpVal="";
          String lprpSrt="";
          HashMap paramsMap = new HashMap();
          for (int i = 0; i < genricSrchLst.size(); i++) {
          ArrayList srchPrp = (ArrayList)genricSrchLst.get(i);
          String lprp = (String)srchPrp.get(0);
          ArrayList prpLst = (ArrayList)prp.get(lprp+"V");
          ArrayList srtLst = (ArrayList)prp.get(lprp+"S");
            String typ = util.nvl((String)mprp.get(lprp+"T"));
              String fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
              paramsMap.put(lprp+"_1", fldVal1);
              if(!fldVal1.equals("0") &&!fldVal1.equals("")){
                  lprpSrt = lprpSrt+"@"+fldVal1;
              if(typ.equals("C"))
                  fldVal1 = (String)prpLst.get(srtLst.indexOf(fldVal1));
                
                lprpLst = lprpLst+"@"+lprp;
                lprpVal = lprpVal+"@"+fldVal1;
              }
          }
          lprpSrt =lprpSrt.replaceFirst("@", "");
          lprpVal =lprpVal.replaceFirst("@", "");
          lprpLst =lprpLst.replaceFirst("@", "");
          udf.reset();
        
         ArrayList  params = new ArrayList();
          params.add(mprcIdn);
          params.add(empIdn);
          ArrayList out = new ArrayList();
          out.add("I");
          CallableStatement cst = db.execCall("findIssueId","ISS_RTN_PKG.GEN_HDR(pPRCID => ?, pEMPID => ?,  pIssId => ?)", params ,out);
         int  issueIdn = cst.getInt(params.size()+1);
        cst.close();
        cst=null;
          
          params = new ArrayList();
          params.add(String.valueOf(issueIdn));
          params.add(lprpLst);
          params.add(lprpVal);
         out = new ArrayList();
         out.add("V");
         cst = db.execCall("ASSORT_TRF","MIX_IR_PKG.ASSORT_TRF(pTrfIssId => ? , pPrpLst => ?, pValLst => ?, pMsg => ?)", params ,out);
         String msg = cst.getString(params.size()+1);
        cst.close();
        cst=null;
          HashMap paramDtl = new HashMap();
          paramDtl.put("issueId", String.valueOf(issueIdn));
          paramDtl.put("prcIdn", mprcIdn);
           FecthResult(req, res, paramDtl,udf); 
          if(paramsMap!=null && paramsMap.size()>0){
          Set<String> keys = paramsMap.keySet();
                  for(String key: keys){
                 udf.setValue(key, paramsMap.get(key));
                  }
          }
          req.setAttribute("commPrpLst", lprpLst);
          udf.setValue("mprcIdn", mprcIdn);
          udf.setValue("empIdn", empIdn);
          udf.setValue("issueIdn", issueIdn);
          udf.setValue("commonLprp", lprpLst);
          udf.setValue("commonLprpSrt", lprpSrt);
          return am.findForward("load"); 
      }
     }
    
    public ActionForward Return(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
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
           MixAssortLKhiRtnFrom udf = (MixAssortLKhiRtnFrom)af;
          String delQ = " Delete from gt_srch_rslt ";
          int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
          
           String issIdn = util.nvl((String)udf.getValue("issueIdn"));
           ArrayList param = new ArrayList();
           param.add(issIdn);
          ArrayList out = new ArrayList();
          out.add("V");
          CallableStatement cts = db.execCall("mixRtn", "MIX_IR_PKG.MIX_RTN_PKT(pIssId => ?, pMsg => ?)", param, out);
          String msg = cts.getString(param.size()+1);
          if(msg.equals("SUCCESSFUL")){
            String lstNme = (String)gtMgr.getValue("lstNmeMIXRTN");
           HashMap gtPktMap =  SearchResult(req,res,issIdn);
           gtMgr.setValue(lstNme+"_FNLDTL", gtPktMap);
           req.setAttribute("viewPkt", "Y");
          }
          req.setAttribute("msg", msg);
          udf.reset();
          return am.findForward("load"); 
      }
    }
    public HashMap SearchResult(HttpServletRequest req ,HttpServletResponse res, String issueIdn ){
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

            String srchRefQ = 
                      "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 , rmk,rap_rte,rap_dis ) " + 
                      "  select  pkt_ty, b.idn, b.vnm,  b.dte, b.stt , 'Z' , b.qty , b.cts , b.sk1 , b.tfl3,b.rap_rte,decode(b.rap_rte ,'1',null,'0',null, trunc((nvl(b.upr,b.cmp)/greatest(b.rap_rte,1)*100)-100, 2))  "+
                       " from mstk b , iss_rtn_dtl a   , iss_rtn c , mprc d where b.idn = a.iss_stk_idn \n" + 
                       "   and a.iss_id = c.iss_id and c.prc_id=d.idn and a.flg1=d.is_stt "+
                       " and a.iss_id = ? and b.cts > 0  ";
           ArrayList ary = new ArrayList();
            ary.add(issueIdn);
             int ct = db.execUpd(" ins gt_srch_rslt", srchRefQ,ary); 


           String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
           ary = new ArrayList();
           ary.add("MIX_VIEW");
           ct = db.execCall(" Srch Prp ", pktPrp, ary);
         
           ArrayList vwPrpLst = Prpview(req,res);
           String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt , qty , cts , rmk,decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis ";

           

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
           
            ary = new ArrayList();
           ary.add("Z");
           ArrayList  rsLst = db.execSqlLst("search Result", rsltQ, ary);
           PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
           ResultSet  rs =(ResultSet)rsLst.get(1);
           try {
               while(rs.next()) {
                    String stk_idn = util.nvl(rs.getString("stk_idn"));
                   GtPktDtl pktPrpMap = new GtPktDtl();
                   pktPrpMap.setStt(util.nvl(rs.getString("stt")));
                   String vnm = util.nvl(rs.getString("vnm"));
                   pktPrpMap.setVnm(vnm);
                    pktPrpMap.setPktIdn(rs.getLong("stk_idn"));
                   pktPrpMap.setQty(util.nvl(rs.getString("qty")));
                   pktPrpMap.setCts(util.nvl(rs.getString("cts")));
                   for(int j=0; j < vwPrpLst.size(); j++){
                        String prp = (String)vwPrpLst.get(j);
                         
                         String fld="prp_";
                         if(j < 9)
                                 fld="prp_00"+(j+1);
                         else    
                                 fld="prp_0"+(j+1);
                         
                         String val = util.nvl(rs.getString(fld)) ;
                         if (prp.toUpperCase().equals("RAP_DIS"))
                         val = util.nvl(rs.getString("r_dis")) ;  
                           pktPrpMap.setValue(prp, val);
                            }
                                 
                       pktList.put(stk_idn,pktPrpMap);
                   }
               rs.close();
               stmt.close();
           } catch (SQLException sqle) {

               // TODO: Add catch code
               sqle.printStackTrace();
           }
          
         }
           return pktList;
       }
        public void FecthResult(HttpServletRequest req, HttpServletResponse res, HashMap params ,MixAssortLKhiRtnFrom udf){
          HttpSession session = req.getSession(false);
          InfoMgr info = (InfoMgr)session.getAttribute("info");
          GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
          DBUtil util = new DBUtil();
          DBMgr db = new DBMgr();
          HashMap stockList = new HashMap();
          if(info!=null){  
          db.setCon(info.getCon());
          util.setDb(db);
          util.setInfo(info);
          db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
          util.setLogApplNm(info.getLoApplNm());
           
            
            String issueId = util.nvl((String)params.get("issueId"));
            ArrayList ary = null;

            String delQ = " Delete from gt_srch_rslt ";
            int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
            ct =db.execUpd(" Del Old Pkts ", " Delete from gt_srch_asrt ", new ArrayList());
            ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
            String srchRefQ = 
            "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 , rmk , srch_id , cert_lab,quot ) " + 
            " select  b.pkt_ty, b.idn, b.vnm,  b.dte, a.flg1 , 'Z' , b.qty , b.cts , b.sk1 , b.tfl3  , a.iss_id , cert_lab , b.upr  "+
            "     from mstk b , "+
             " iss_rtn_dtl a where b.idn = a.iss_stk_idn and a.stt='IS'  "+
             "  and b.cts > 0  and a.iss_id = ? ";
    
          ary = new ArrayList();
          ary.add(issueId);
          ct = db.execUpd(" ins gt_srch_rslt", srchRefQ,ary); 

          String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
           ary = new ArrayList();
          ary.add("MIX_VIEW");
          ct = db.execCall(" Srch Prp ", pktPrp, ary);
          
        String prcIdn = util.nvl((String)params.get("prcIdn"));
        ArrayList grpLprpLst = new ArrayList();
         String lprpAlw = "select b.mprp, a.in_stt,a.is_stt from mprc a , prc_prp_alw b\n" + 
         "where a.idn=b.prc_idn and b.flg='FTCH' and b.prc_idn=? order by b.srt \n";
        ary = new ArrayList();
        ary.add(prcIdn);
        String is_stt="";
        String in_stt="";
            try {
                ArrayList outLst = db.execSqlLst("lprpAlw", lprpAlw, ary);
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
                while (rs.next()) {
                    grpLprpLst.add(rs.getString("mprp"));
                    in_stt = util.nvl(rs.getString("in_stt"));
                    is_stt = util.nvl(rs.getString("is_stt"));
                }
                rs.close();
                pst.close();
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
            ArrayList viewPrpLst =  Prpview(req,res);
        String colLprp = (String)grpLprpLst.get(0);
        String rowLprp = (String)grpLprpLst.get(1);
        int indCol = viewPrpLst.indexOf(colLprp);
        int indRow = viewPrpLst.indexOf(rowLprp);
        indCol =indCol+1;
        indRow =indRow +1;
        String colFld = "prp_00"+indCol;
        String rowFld = "prp_00"+indRow;
        String colFldSrt = "srt_00"+indCol;
        String rowFldSrt = "srt_00"+indRow;
        
      try {
          
          ArrayList param = new ArrayList();
            param.add(in_stt);
            String ttlIsscts = "";
             String ttlIssue =  "select to_char(sum(cts),'9999990.90') cts  from gt_srch_rslt where stt=?";
            
                ArrayList outLst = db.execSqlLst("ttlIssue", ttlIssue, param);
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
                while (rs.next()) {
                 
                    ttlIsscts = util.nvl2(rs.getString("cts"),"0");
                    
                }
                rs.close();
                pst.close();
            req.setAttribute("ttlIssCts", ttlIsscts);
        ary = new ArrayList();
        ary.add(in_stt);
        double ttlcts = 0;
         String ttlRtncts =  "select to_char(sum(cts),'9999990.90') cts ,trunc(((sum(cts*quot) / sum(cts)))) avgRte, "+colFld+" colPrp, "+rowFld+" rowPrp ,"+colFldSrt+" colSrt , "+rowFldSrt+" rowSrt from gt_srch_rslt where "+colFld+" is not null and "+rowFld+" is not null and stt=? group by "+colFld+" , "+rowFld+" , "+colFldSrt+"  , "+rowFldSrt+" ";
      
             outLst = db.execSqlLst("ttlRtncts", ttlRtncts, ary);
            pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                String rowSrt = rs.getString("rowSrt");
                String colSrt = rs.getString("colSrt");
                String avgRte = rs.getString("avgRte");
                String cts = util.nvl2(rs.getString("cts"),"0");
                udf.setValue("CTS_"+colSrt+"_"+rowSrt, cts);
                udf.setValue("RTE_"+colSrt+"_"+rowSrt, avgRte);
                ttlcts = ttlcts + Double.parseDouble(cts);
            }
            rs.close();
            pst.close();
            req.setAttribute("ttlRtnCts", String.valueOf(ttlcts));
         
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        String lstNme = "MIXRTN_"+info.getLogId()+"_"+util.getToDteGiveFrmt("yyyyMMdd");
      
        gtMgr.setValue(lstNme+"_GRPLST", grpLprpLst);
        gtMgr.setValue("lstNmeMIXRTN", lstNme);
       
        req.setAttribute("view", "yes");
        
    }}
        
    public ActionForward GenPkt(ActionMapping mapping, ActionForm form,
                               HttpServletRequest req,
                               HttpServletResponse response) throws Exception {
           HttpSession session = req.getSession(false);
           InfoMgr info = (InfoMgr)session.getAttribute("info");
           DBUtil util = new DBUtil();
           DBMgr db = new DBMgr(); 
           db.setCon(info.getCon());
           util.setDb(db);
           util.setInfo(info);
           db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
           util.setLogApplNm(info.getLoApplNm());
           StringBuffer sb = new StringBuffer();
           HashMap prp = info.getPrp();
           HashMap mprp = info.getMprp();
           String lprpLst = util.nvl(req.getParameter("lprpLst"));
           String lprpVal = util.nvl(req.getParameter("lprpVal"));
           String prpNme = util.nvl(req.getParameter("lprp"));
           String prpVlu = util.nvl(req.getParameter("lprpVlu"));
           if(prpVlu.equals(""))
               prpVlu="0";
           String issueIdn = util.nvl(req.getParameter("issueIdn"));
           String nwlprpVal = "";
           String nwlprp="";
           String[] lprpPLst = lprpLst.split("@");
           String[] lprpValLst = lprpVal.split("@");
           if(lprpPLst.length==lprpValLst.length ){
               for(int i=0;i<lprpValLst.length;i++){
               String lprp = lprpPLst[i];
                String lprpSrt = lprpValLst[i];
              String lprpTyp = (String)mprp.get(lprp+"T");
              if(lprpTyp.equals("C")){
               
              
             
               
               ArrayList lprpSLst = (ArrayList)prp.get(lprp+"S");
               ArrayList lprpVLst = (ArrayList)prp.get(lprp+"V");
              
               String val = (String)lprpVLst.get(lprpSLst.indexOf(lprpSrt));
               nwlprpVal=nwlprpVal+"@"+val;
              }else{
                  nwlprpVal=nwlprpVal+"@"+lprpSrt;
              }
                nwlprp = nwlprp+"@"+lprp;
               }
           }
           
         
           nwlprp = nwlprp+"@"+prpNme;
           nwlprpVal = nwlprpVal+"@"+prpVlu;
           nwlprp =nwlprp.replaceFirst("@", "");
           nwlprpVal =nwlprpVal.replaceFirst("@", "");
           
           ArrayList params = new ArrayList();
            params.add(String.valueOf(issueIdn));
           params.add(nwlprp);
           params.add(nwlprpVal);
           ArrayList out = new ArrayList();
           out.add("V");
          CallableStatement  cst = db.execCall("MIX_ISS_PKT","MIX_IR_PKG.MIX_ISS_PKT(pIssId => ? , pPrpLst => ? , pValLst => ?, pMsg => ?)", params ,out);
           String rte = cst.getString(params.size()+1);
           
          
     
           response.setContentType("text/xml"); 
           response.setHeader("Cache-Control", "no-cache"); 
           response.getWriter().write("<RTE>"+rte+"</RTE>");
       return null;
       }   
        
    public ActionForward PRCPRP(ActionMapping mapping, ActionForm form,
                               HttpServletRequest req,
                               HttpServletResponse response) throws Exception {
           HttpSession session = req.getSession(false);
           InfoMgr info = (InfoMgr)session.getAttribute("info");
           DBUtil util = new DBUtil();
           DBMgr db = new DBMgr(); 
           db.setCon(info.getCon());
           util.setDb(db);
           util.setInfo(info);
           db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
           util.setLogApplNm(info.getLoApplNm());
           StringBuffer sb = new StringBuffer();
           String prcIdn = util.nvl(req.getParameter("PRCID"));
           String prpalw = "select mprp from prc_prp_alw where flg=? and prc_idn=? order by srt";
           ArrayList ary = new ArrayList();
           ary.add("ISSEDIT");
           ary.add(prcIdn);
           ArrayList  rsLst = db.execSqlLst("prpalw", prpalw, ary);
           PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
             ResultSet  rs =(ResultSet)rsLst.get(1);
           while(rs.next()){
               sb.append("<value>");
               sb.append("<PRP>"+util.nvl(rs.getString("mprp")) +"</PRP>");
               sb.append("</value>");
           }
         rs.close();
        stmt.close();
          response.setContentType("text/xml"); 
          response.setHeader("Cache-Control", "no-cache"); 
          response.getWriter().write("<values>"+sb.toString()+"</values>");
          return null;
  }
    public ArrayList Prpview(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList asViewPrp = (ArrayList)session.getAttribute("MIX_VIEW");
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
          
              ArrayList  rsLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'MIX_VIEW' and flg='Y' order by rnk ",
                               new ArrayList());
              PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
                ResultSet  rs1 =(ResultSet)rsLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                stmt.close();
                session.setAttribute("MIX_VIEW", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return asViewPrp;
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
        ArrayList  rsLst = db.execSqlLst("prcSql", prcSql, ary);
        PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
        ResultSet  rs =(ResultSet)rsLst.get(1);
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
              stmt.close();
          } catch (SQLException sqle) {
              // TODO: Add catch code
              sqle.printStackTrace();
          }
          
        
        
      
        
      }
        return prcList;
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
                util.updAccessLog(req,res,"Mix Assort Lkhi Rtn", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Mix Assort Lkhi Rtn", "init");
            }
            }
            return rtnPg;
            }
}
