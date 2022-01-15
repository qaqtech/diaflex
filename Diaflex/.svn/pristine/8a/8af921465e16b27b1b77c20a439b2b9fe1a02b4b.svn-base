 package ft.com.mix;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import ft.com.dao.GtPktDtl;

import ft.com.dao.MNme;
import ft.com.dao.ObjBean;

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

public class MixReturnCdAction extends DispatchAction {
    
    public MixReturnCdAction() {
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
      MixReturnCdForm udf = (MixReturnCdForm)af;
      udf.reset();
          GtMgrReset(req);

          String prcId = "0";
          String mprcIdn ="select idn from mprc where is_stt='MIX_AS_IS'";
          ArrayList rsLst = db.execSqlLst("mprcInd", mprcIdn, new ArrayList());
          PreparedStatement pst = (PreparedStatement)rsLst.get(0);
          ResultSet rs = (ResultSet)rsLst.get(1);
          while(rs.next()){
              prcId = rs.getString("idn");
              udf.setValue("prcIdn", prcId);
          }
          rs.close();
          pst.close();
      HashMap mixIssueMap = new HashMap();
      ArrayList boxTypLst = new ArrayList();
  
      String issIdnSql ="select to_char(sum(c.cts),'999,990.00') cts , sum(c.qty) qty , trunc(((sum(trunc(c.cts,3)*nvl(c.upr, c.cmp)) / greatest(sum(trunc(c.cts,3)), 0.001)))) avg_Rte, \n" + 
      "     b.flg1 , d.val , d.srt , nm.nme , nm.nme_idn \n" + 
      "      from iss_rtn a , iss_rtn_dtl b , mstk c , stk_dtl d , mprc e ,nme_v nm\n" + 
      "      where a.iss_id = b.iss_id and b.iss_stk_idn=c.idn and a.emp_id=nm.nme_idn\n" + 
      "      and b.stt='IS' and a.prc_id = ? and c.pkt_ty <> 'NR'\n" + 
      "      and c.stt=? and c.idn = d.mstk_idn and d.mprp='BOX_TYP' and d.grp=1\n" + 
      "      and a.prc_id = e.idn and e.is_stt=? \n" + 
      "      group by b.flg1 , d.val , d.srt ,nm.nme,nm.nme_idn\n" + 
      "      order by d.val , d.srt , b.flg1";
      ArrayList ary = new ArrayList();
     ary.add(prcId);
     ary.add("MIX_AS_IS");
     ary.add("MIX_AS_IS");
       rsLst = db.execSqlLst("issIdnLst", issIdnSql, ary);
       pst = (PreparedStatement)rsLst.get(0);
      rs = (ResultSet)rsLst.get(1);
        String pboxTyp="";
    ArrayList boxDtlList = new ArrayList();
    HashMap mixAssortMap = new HashMap();
        while(rs.next()){
             String box_typ = util.nvl(rs.getString("val"));
            if(pboxTyp.equals(""))
                pboxTyp=box_typ;
            if(!pboxTyp.equals(box_typ)){
                mixAssortMap.put(pboxTyp, boxDtlList);
                
                boxTypLst.add(pboxTyp);
                pboxTyp=box_typ;
                boxDtlList= new ArrayList();
            }
            String cts = util.nvl(rs.getString("cts"),"0").trim();
            String qty = util.nvl(rs.getString("qty"),"0").trim();
             String avgRte = util.nvl(rs.getString("avg_Rte"),"0").trim();
           
            String emp = util.nvl(rs.getString("nme"));
            String nme_idn=util.nvl(rs.getString("nme_idn"));
            String stt = util.nvl(rs.getString("flg1"));
            HashMap boxDtlMap = new HashMap();
            boxDtlMap.put("CTS", cts);
            boxDtlMap.put("QTY", qty);
            boxDtlMap.put("AVG", avgRte);
            boxDtlMap.put("EMP", emp);
            boxDtlMap.put("EMP_IDN", nme_idn);
            boxDtlList.add(boxDtlMap);
         }
          if(!pboxTyp.equals("")){
              mixAssortMap.put(pboxTyp, boxDtlList);
              boxTypLst.add(pboxTyp);
          }
          rs.close();
          pst.close();
          
          HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
          HashMap pageDtl=(HashMap)allPageDtl.get("MIX_ASSORTRTN");
          if(pageDtl==null || pageDtl.size()==0){
          pageDtl=new HashMap();
          pageDtl=util.pagedef("MIX_ASSORTRTN");
          allPageDtl.put("MIX_ASSORTRTN",pageDtl);
          }
          info.setPageDetails(allPageDtl);
          
         
          String lstNme = "MIXRTN_"+info.getLogId()+"_"+util.getToDteGiveFrmt("yyyyMMdd");
          gtMgr.setValue("lstNmeMIXRTN", lstNme);
          gtMgr.setValue(lstNme, mixAssortMap);
          gtMgr.setValue(lstNme+"BOXLST", boxTypLst);
          return am.findForward("load");   
      }}
    
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
          String lstNme = (String)gtMgr.getValue("lstNmeMIXRTN");
      MixReturnCdForm udf = (MixReturnCdForm)af;
          ArrayList boxTypLst = new ArrayList();
        String empIdn = util.nvl(req.getParameter("emp_idn"));
        String box_typ = util.nvl(req.getParameter("box_typ"));
       String iss_id =  "";
       double ttlMktCts =0;
          int ttlMktQty=0;
          ArrayList ary = new ArrayList();
          boxTypLst.add(box_typ);
       String ttlCtsSql  = "select  to_char(sum(a.cts),'999,990.00') cts , sum(a.qty) qty,d.val boxid from mstk a , stk_dtl b , iss_rtn_dtl c  , stk_dtl d , iss_rtn b" + 
       " where a.idn=b.mstk_idn and b.mstk_idn=c.iss_stk_idn  " + 
       "and c.stt='IS' and a.stt='MIX_AS_IS' and b.mprp='BOX_TYP' and b.grp=1  and b.val = ? "+
        " and d.mstk_idn=c.iss_stk_idn and d.mprp='BOX_ID'  and d.grp=1  and c.iss_id=b.iss_id and b.emp_id=? group by d.val ";
          ary.add(box_typ);
          ary.add(empIdn);
        ArrayList  rsLst = db.execSqlLst("ttlCts", ttlCtsSql, ary);
        PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
        ResultSet  rs =(ResultSet)rsLst.get(1);
          while(rs.next()){
              String cts = util.nvl(rs.getString("cts"));
              String qty = util.nvl(rs.getString("qty"));
              String boxId = util.nvl(rs.getString("boxid"));
              String ctsTxt = "CTS_"+boxId;
              ctsTxt = ctsTxt.replace("-", "_");
              udf.setValue(ctsTxt, cts);
              String oldctsTxt = "OLD_"+boxId;
              oldctsTxt = oldctsTxt.replace("-", "_");
              udf.setValue(oldctsTxt, cts);
              String qtyTxt = "QTY_"+boxId;
              qtyTxt = qtyTxt.replace("-", "_");
              udf.setValue(qtyTxt, qty);
              ttlMktCts = ttlMktCts + Double.parseDouble(cts);
              ttlMktQty=ttlMktQty+ Integer.parseInt(qty);
          }
          rs.close();
          stmt.close();
          
          String prcId = "0";
          String mprcIdn ="select idn from mprc where is_stt='MIX_AS_IS'";
         rsLst = db.execSqlLst("mprcInd", mprcIdn, new ArrayList());
          stmt =(PreparedStatement)rsLst.get(0);
         rs =(ResultSet)rsLst.get(1);
          while(rs.next()){
              prcId = rs.getString("idn");
          }
          rs.close();
          stmt.close();
          ArrayList params = new ArrayList();
          params.add(prcId);
          params.add(empIdn);
          ArrayList out = new ArrayList();
          out.add("I");
          CallableStatement cst = db.execCall("findIssueId","ISS_RTN_PKG.GEN_HDR(pPRCID => ?, pEMPID => ?,  pIssId => ?)", params ,out);
          int issueIdn = cst.getInt(3);
        cst.close();
        cst=null;
          String msg=null;
             ttlCtsSql  = "select  to_char(sum(a.cts),'999,990.00') cts , sum(a.qty) qty,c.iss_id , b.val from mstk a , stk_dtl b , iss_rtn_dtl c , iss_rtn d  " + 
              " where a.idn=b.mstk_idn and b.mstk_idn=c.iss_stk_idn  and c.iss_id=d.iss_id and d.emp_id=?  "+
              "and c.stt='IS' and a.stt='MIX_AS_IS' and b.mprp='BOX_TYP' and b.grp=1  and b.val = ?  group by c.iss_id , b.val ";
              ary = new ArrayList();
              ary.add(empIdn);
              ary.add(box_typ);
                 HashMap boxIdBoxTypMap = new HashMap();
        rsLst = db.execSqlLst("ttlCts", ttlCtsSql, ary);
         stmt =(PreparedStatement)rsLst.get(0);
        rs =(ResultSet)rsLst.get(1);
              double ttlCts =0;
                 while(rs.next()){
                     String cts = util.nvl(rs.getString("cts"));
                     ttlCts = ttlCts + Double.parseDouble(cts);
                     iss_id =util.nvl(rs.getString("iss_id"));
                     String boxtyp =util.nvl(rs.getString("val"));
                     boxIdBoxTypMap.put(boxtyp+"_CTS", cts);
                     boxIdBoxTypMap.put(boxtyp+"_ISSID", iss_id);
                    params = new ArrayList();
                    params.add(String.valueOf(issueIdn));
                    params.add(iss_id);
                    params.add(boxtyp);
                    out = new ArrayList();
                    out.add("V");
                    cst = db.execCall("issuePkt", "MIX_PKG.ASSORT_TRF(pTrfIssId => ?, pIssId => ?, pBoxTyp => ?, pMsg => ?)", params,out);
                     msg = util.nvl(cst.getString(params.size()+1));
                }
                 rs.close();
          stmt.close();
          gtMgr.setValue("BOXTYPDTL", boxIdBoxTypMap);
          gtMgr.setValue("BOXTYPLST", boxTypLst);
          String ttlSql = 
          "select  to_char(sum(a.cts),'999,990.00') cts , sum(a.qty) qty , trunc(((sum(trunc(a.cts,2)*nvl(a.upr, a.cmp)) / greatest(sum(trunc(a.cts,3)), 0.001)))) avg_Rte from mstk a , stk_dtl b , iss_rtn_dtl c ,iss_rtn d \n" + 
          " where a.idn=b.mstk_idn and b.mstk_idn=c.iss_stk_idn   and c.iss_id=d.iss_id and d.emp_id=?"+
          " and c.stt='IS' and a.stt='MIX_AS_IS' and b.mprp='BOX_TYP' and b.grp=1  and b.val = ?  and c.iss_id = ? ";
          params = new ArrayList();
          params.add(empIdn);
          params.add(box_typ);
          params.add(String.valueOf(issueIdn));
        rsLst = db.execSqlLst("ttlSql", ttlSql, params);
         stmt =(PreparedStatement)rsLst.get(0);
        rs =(ResultSet)rsLst.get(1);
          while(rs.next()){
              req.setAttribute("TTLCTS",rs.getString("cts"));
              req.setAttribute("TTLQTY",rs.getString("qty"));
              req.setAttribute("AvgRte",rs.getString("avg_Rte"));
           
          }
          
          rs.close();
          stmt.close();
          req.setAttribute("TTLMKTCTS",String.valueOf(util.roundToDecimals(ttlMktCts, 2)));
          req.setAttribute("TTLMKTQTY",String.valueOf(ttlMktQty));
          req.setAttribute("view", "yes");
          req.setAttribute("TrfIssueId", String.valueOf(issueIdn));
          udf.setValue("fetch","");
          

          return am.findForward("load");   
      }
      
      }
    
    public ActionForward pktDtl(ActionMapping am, ActionForm af, HttpServletRequest req,
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
      MixReturnCdForm udf = (MixReturnCdForm)af;
          String delQ = " Delete from gt_srch_rslt ";
          int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
          String lstNme = (String)gtMgr.getValue("lstNmeMIXRTN");
      String stt = util.nvl(req.getParameter("stt")); 
          if(stt.equals(""))
              stt = util.nvl((String)udf.getValue("stt"));
      String box_typ = util.nvl(req.getParameter("box_typ"));
          if(box_typ.equals(""))
              box_typ = util.nvl((String)udf.getValue("box_typ"));
    String vnm = util.nvl((String)udf.getValue("vnm"));
          if(stt.equals("ALL")){
              ArrayList sttList = (ArrayList)gtMgr.getValue(lstNme+"STTLST");
          
                if(sttList!=null && sttList.size()>0){
                      String sttStr = sttList.toString();
                       sttStr =sttStr.replaceAll(",", "','");
                       sttStr =sttStr.replaceAll("\\[", "");
                       sttStr =sttStr.replaceAll("\\]", "").replaceAll(" ", "");
                       stt = sttStr;
                }
              
          }
             
          String gtInsert = "Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , quot , sk1,srch_id )\n" + 
                            "select a.PKT_TY,a.idn,a.vnm,a.DTE,c.flg1,'Z',a.qty,a.cts,nvl(upr,cmp),a.sk1 , c.iss_id from mstk a , stk_dtl b ,iss_rtn_dtl c \n" + 
                            " where a.idn=b.mstk_idn and c.flg1 in ('"+stt+"') and a.pkt_ty <> 'NR'\n" + 
                            " and b.mstk_idn=c.iss_stk_idn and c.stt='IS' and b.mprp='BOX_TYP' and b.val in (?)";
          if(!vnm.equals("")) {
              vnm = util.getVnm(vnm);
              gtInsert=gtInsert+" and a.vnm in ("+vnm+")";
          }
           ArrayList ary = new ArrayList();
          ary.add(box_typ);
           ct = db.execCall("gtInsert", gtInsert, ary);
          
          String pktPrp = "SRCH_PKG.POP_PKT_PRP_TEST(pMdl => ?)";
          ary = new ArrayList();
          ary.add("BOX_VIEW");
           ct = db.execCall(" Srch Prp ", pktPrp, ary);
          
          HashMap stockMap = SearchResultGt(req, res);
          gtMgr.setValue(lstNme+"_CNLST", stockMap);
          req.setAttribute("cancel", "Y");
          udf.setValue("stt", stt);
          udf.setValue("box_typ", box_typ);
          return am.findForward("load");
      }
    }
    
    public HashMap SearchResultGt(HttpServletRequest req ,HttpServletResponse res ){
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
       
        ArrayList vwPrpLst = PrpViewLst(req,res);
        String  srchQ =  " select stk_idn , pkt_ty,srch_id,  vnm, pkt_dte, decode(stt,'MF_IN','MF_IN','RE_ASRT','MKAV',stt) stt, qty , cts , srch_id , rmk , quot ";
        
       for(int i = 0; i < vwPrpLst.size(); i++) {
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

        
        String rsltQ = srchQ + " from gt_srch_rslt where flg =? order by stt ";
        
        ArrayList ary = new ArrayList();
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
                    pktPrpMap.setValue("vnm", vnm);

                  pktPrpMap.setPktIdn(rs.getLong("stk_idn"));
                  pktPrpMap.setQty(util.nvl(rs.getString("qty")));
                  pktPrpMap.setCts(util.nvl(rs.getString("cts")));
                  pktPrpMap.setValue("issue_id", util.nvl(rs.getString("srch_id")));
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
            stmt.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
       
        
      }
        return pktList;
    }
    
    public ArrayList PrpViewLst(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList asViewPrp = (ArrayList)session.getAttribute("boxViewLst");
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
                
              ArrayList  rsLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'BOX_VIEW' and flg='Y' order by rnk ",
                               new ArrayList());
              PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
              ResultSet  rs1 =(ResultSet)rsLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close(); 
                stmt.close();
                session.setAttribute("boxViewLst", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return asViewPrp;
    }
    
    public ActionForward Cancel(ActionMapping am, ActionForm af, HttpServletRequest req,
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
          ArrayList stkIdnLst = new ArrayList();
          Enumeration reqNme = req.getParameterNames();
          while(reqNme.hasMoreElements()) {
              String paramNm = (String) reqNme.nextElement();
             if (paramNm.indexOf("CHK_") > -1) {
                  String val = req.getParameter(paramNm);
                 stkIdnLst.add(val);
              }
          }
          String iss_id = "";
          String lstNme = (String)gtMgr.getValue("lstNmeMIXRTN");
          HashMap pktDtlLst = (HashMap)gtMgr.getValue(lstNme+"_CNLST");
          for(int i=0;i<stkIdnLst.size();i++){
             String stkIdn = (String)stkIdnLst.get(i);
             GtPktDtl pktDtl = (GtPktDtl)pktDtlLst.get(stkIdn);
             String stt = pktDtl.getStt();
             iss_id = pktDtl.getValue("issue_id");
             ArrayList  params = new ArrayList();
             params.add(iss_id);
             params.add(stkIdn);
             params.add(stt);
             ArrayList out = new ArrayList();
             out.add("V");
             CallableStatement cst = db.execCall("issuePkt", "MIX_PKG.CANCEL_ISSUE(pIssId => ? , pStk_Idn => ?,pStt => ?, pMsg => ?)", params,out);
            String  msg = cst.getString(params.size()+1);
            cst.close();
            cst=null;
             if(msg.contains("SUCCESS"))
                req.setAttribute("msg", "Issue cancel successfully..");
          }
          return load(am, af, req, res);   
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
        
          MixReturnCdForm udf = (MixReturnCdForm)af;
          String issIdn = req.getParameter("TrfIssueId");
         HashMap prp = info.getPrp();
          ArrayList boxIdValLst = (ArrayList)prp.get("BOX_IDV");
         ArrayList boxIdPrtLst = (ArrayList)prp.get("BOX_IDP");
          String lstNme = (String)gtMgr.getValue("lstNmeMIXRTN");
          ArrayList boxIdnLst = (ArrayList)gtMgr.getValue(lstNme+"BOXIDNLST");
          ArrayList boxTypLst = (ArrayList)gtMgr.getValue("BOXTYPLST");
          ArrayList transBoxIdLst = new ArrayList();
          String   msg = "";
          db.setAutoCommit(false);
          boolean isCommit=true;
            try {
                for (int j = 0; j < boxTypLst.size(); j++) {
                    String box_typ = (String)boxTypLst.get(j);
                    for (int i = 0; i < boxIdnLst.size(); i++) {
                        String boxId = (String)boxIdnLst.get(i);
                        String ctsId = "CTS_" + box_typ + "_" + boxId;
                        String qtyId = "QTY_" + box_typ + "_" + boxId;
                        String prcId = "RTE_" + box_typ + "_" + boxId;
                        String ctsVal = util.nvl((String)udf.getValue(ctsId), "0").trim();
                        String qtyVal = util.nvl((String)udf.getValue(qtyId), "0").trim();
                        String prcVal = util.nvl((String)udf.getValue(prcId), "0").trim();
                        if (!ctsVal.equals("0") && !ctsVal.equals("")) {
                            String boxVal = (String)boxIdValLst.get(boxIdPrtLst.indexOf(boxId));
                            transBoxIdLst.add(boxVal);
                            ArrayList params = new ArrayList();
                            params.add(issIdn);
                            params.add(box_typ);
                            params.add(boxVal);
                            params.add(qtyVal);
                            params.add(ctsVal);
                            params.add(prcVal);
                            ArrayList out = new ArrayList();
                            out.add("V");
                            CallableStatement cst =
                                db.execCall("issuePkt", "MIX_PKG.ASSORT_RETURN(pIssId => ? , pBoxTyp => ? , pBoxID => ?, pQty => ?, pCts => ?, pRte => ?, pMsg => ?)",
                                            params, out);
                            msg = util.nvl(cst.getString(params.size() + 1));
                          cst.close();
                          cst=null;
                           if(msg.indexOf("Err")!=-1)
                               isCommit=false;
                        }
                    }
                }
                if(isCommit){
                String Count = util.nvl(req.getParameter("COUNT"));
                if (!Count.equals("")) {
                    int cnt = Integer.parseInt(Count);
                    for (int i = 1; i <= cnt; i++) {
                        String qty = util.nvl(req.getParameter("QTY_" + i), "0").trim();
                        String cts = util.nvl(req.getParameter("CTS_" + i), "0").trim();
                        String rte = util.nvl(req.getParameter("RTE_" + i), "0").trim();
                        if (!cts.equals("0")) {
                            String boxId = util.nvl(req.getParameter("BOXTXT_ID1_" + i));
                            if(!boxId.equals("")){
                            transBoxIdLst.add(boxId);
                            String box_typ="";
                            if(boxId.indexOf("-")!=-1)
                            box_typ = boxId.substring(0, boxId.indexOf("-"));
                           if(boxId.indexOf("_")!=-1)
                            box_typ = boxId.substring(0, boxId.indexOf("_"));
                                
                            if (boxTypLst.indexOf(box_typ) == -1)
                                boxTypLst.add(box_typ);
                            ArrayList params = new ArrayList();
                            params.add(issIdn);
                            params.add(box_typ);
                            params.add(boxId);
                            params.add(qty);
                            params.add(cts);
                            params.add(rte);
                            ArrayList out = new ArrayList();
                            out.add("V");
                            CallableStatement cst =
                                db.execCall("issuePkt", "MIX_PKG.ASSORT_RETURN(pIssId => ? , pBoxTyp => ? , pBoxID => ?, pQty => ?, pCts => ?, pRte => ?, pMsg => ?)",
                                            params, out);
                            msg = cst.getString(params.size() + 1);
                              cst.close();
                              cst=null;
                            if(msg.indexOf("Err")!=-1)
                                isCommit=false;
                            }else{
                                isCommit=false;
                                break;
                            }
                        }
                    }
                }
                }
                if (isCommit) {
                    try {
                        ArrayList params = new ArrayList();
                        params.add(issIdn);

                        ArrayList out = new ArrayList();
                        out.add("V");
                        CallableStatement cst =
                            db.execCall("issuePkt", "MIX_PKG.ASSORT_BOX_RETURN(pIssId => ? ,  pMsg => ?)", params,
                                        out);
                        msg = util.nvl(cst.getString(params.size() + 1));
                      cst.close();
                      cst=null;
                        if (msg.indexOf("Err") != -1)
                            isCommit = false;

                    } catch (SQLException sqle) {
                        // TODO: Add catch code
                        sqle.printStackTrace();
                        isCommit = false;
                    }
                    String boxTypstr = boxTypLst.toString();
                    boxTypstr = boxTypstr.replaceAll(",", "','");
                    boxTypstr = boxTypstr.replaceAll("\\[", "");
                    boxTypstr = boxTypstr.replaceAll("\\]", "").replaceAll(" ", "");

                    String boxIDstr = transBoxIdLst.toString();
                    boxIDstr = boxIDstr.replaceAll(",", "','");
                    boxIDstr = boxIDstr.replaceAll("\\[", "");
                    boxIDstr = boxIDstr.replaceAll("\\]", "").replaceAll(" ", "");
                     if (msg.contains("SUCCESS")) {
                        String pktsSummry =
                            "select to_char(sum(a.cts),'999,990.00') cts , c.prt1 , b.val,b.srt , c.srt  from mstk a , stk_dtl b , stk_dtl c " +
                            "where a.idn = b.mstk_idn and b.mstk_idn = c.mstk_idn  " +
                            "and a.stt='MKAV' and b.mprp='BOX_TYP' and b.grp=1 and b.val in ('" + boxTypstr + "')" +
                            "and c.mprp='BOX_ID' and c.grp=1 and c.val in ('" + boxIDstr +
                            "') group by c.prt1 , b.val,b.srt , c.srt order by b.srt , c.srt ";
                        ArrayList params = new ArrayList();

                        ArrayList rsLst = db.execSqlLst("pktSummry", pktsSummry, params);
                        ArrayList summryLst = new ArrayList();
                        PreparedStatement pst = (PreparedStatement)rsLst.get(0);
                        ResultSet rs = (ResultSet)rsLst.get(1);
                        while (rs.next()) {
                            String cts = util.nvl(rs.getString("cts"));
                            String boxId = util.nvl(rs.getString("prt1"));
                            String boxTyp = util.nvl(rs.getString("val"));

                            HashMap summydtl = new HashMap();
                            summydtl.put("CTS", cts);
                            summydtl.put("BOXID", boxId);
                            summydtl.put("BOXTYP", boxTyp);
                            summryLst.add(summydtl);
                        }
                        rs.close();
                        pst.close();
                        req.setAttribute("summryDtl", summryLst);
                        req.setAttribute("msg", msg);
                    } else {
                        req.setAttribute("msg", "Error in process..");
                    }

                }
                
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            } catch (NumberFormatException nfe) {
                // TODO: Add catch code
                nfe.printStackTrace();
                isCommit=false;
            } finally {
             db.setAutoCommit(true);
            }
          if(isCommit)
              db.doCommit();
          else
              db.doRollBack();
        GtMgrReset(req);
       udf.reset();
          return load(am, af, req, res);
      }}
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
    
    public void GtMgrReset(HttpServletRequest req){
          HttpSession session = req.getSession(false);
          GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
          String lstNme = (String)gtMgr.getValue("lstNmeMIXRTN");
          HashMap gtMgrMap = (HashMap)gtMgr.getValues();
          gtMgrMap.remove(lstNme+"BOXLST");
            gtMgrMap.remove(lstNme+"BOXIDNLST");
            gtMgrMap.remove(lstNme+"_CNLST");
          gtMgrMap.remove("BOXTYPDTL");
          gtMgrMap.remove("BOXTYPLST");
            gtMgrMap.remove(lstNme);
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
                util.updAccessLog(req,res,"Mix Assort Rtn", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Mix Assort Rtn", "init");
            }
            }
            return rtnPg;
            }
}
