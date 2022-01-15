package ft.com.marketing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.JspUtil;
import ft.com.dao.ByrDao;
import ft.com.dao.UIForms;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.lab.FinalLabSelectionForm;
import ft.com.lab.LabIssueImpl;
import ft.com.lab.LabIssueInterface;
import ft.com.pricing.TransferToMkt;
import ft.com.pricing.TransferToMktForm;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.ws.rs.core.MediaType;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PacketPrintAction  extends DispatchAction {
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
                util.updAccessLog(req,res,"Packet Print", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Packet Print", "init");
            }
            }
            return rtnPg;
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
          util.updAccessLog(req,res,"Packet Print", "Packet print Load");
        PacketPrintForm form = (PacketPrintForm)af;
        GenericInterface genericInt = new GenericImpl();
        form.resetAll();
        ArrayList srchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_PKTPRTSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_PKTPRTSrch"); 
        info.setGncPrpLst(srchList);
        String    memoPrntOptn = "select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and "
                                 + "b.mdl = 'MKTG' and b.nme_rule = 'STATUS' and a.til_dte is null order by a.dsc ";
        ArrayList sttList = new ArrayList();
          ArrayList  outLst = db.execSqlLst("memoPrint", memoPrntOptn, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                UIForms memoOpn = new UIForms();
                memoOpn.setFORM_NME(rs.getString("chr_fr"));
                memoOpn.setFORM_TTL(rs.getString("dsc"));
                sttList.add(memoOpn);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        form.setSttList(sttList);
          util.updAccessLog(req,res,"Packet Print", "Packet print Load done");
         return am.findForward("load");
        }
    }
    public ActionForward view(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
         PacketPrintForm form = (PacketPrintForm)af;
         SearchQuery srchQuery = new SearchQuery();
         GenericInterface genericInt = new GenericImpl();
         util.updAccessLog(req,res,"Packet Print", "Packet print Load view"); 
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
        boolean isGencSrch = false;
        HashMap stockList = new HashMap();
        ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_PKTPRTSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_PKTPRTSrch"); 
        info.setGncPrpLst(genricSrchLst);
        //      ArrayList genricSrchLst = info.getGncPrpLst();
        ArrayList prpList = genericInt.genericPrprVw(req, res, "memoPrpList", "MEMO_RTRN");
        String vnm = util.nvl((String)form.getValue("vnmLst"));
        String stt = util.nvl((String)form.getValue("stt"));
        HashMap mprp = info.getMprp();
        HashMap prp = info.getPrp();
         if(vnm.equals("")){
               
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
                            String reqVal1 = util.nvl((String)form.getValue(lprp + "_" + lVal),"");
                            if(!reqVal1.equals("")){
                            paramsMap.put(lprp + "_" + lVal, reqVal1);
                            }
                               
                            }
                        }else{
                        String fldVal1 = util.nvl((String)form.getValue(lprp+"_1"));
                        String fldVal2 = util.nvl((String)form.getValue(lprp+"_2"));
                        if(typ.equals("T")){
                            fldVal1 = util.nvl((String)form.getValue(lprp+"_1"));
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
                isGencSrch = true; 
             paramsMap.put("stt", stt);
             paramsMap.put("mdl", "MEMO_RTRN");
                util.genericSrch(paramsMap);
                form.reset();
              
         }else{
         
             ArrayList params = null;
             String srchRefQ = 
             "    Insert into gt_srch_rslt ( srch_id, pkt_ty, stk_idn, vnm, qty, cts, pkt_dte, stt, cmp, rap_rte, cert_lab, cert_no, flg, sk1, quot, rap_dis ,  rmk ) " + 
             "     select  1 srchId , pkt_ty, b.idn, b.vnm, b.qty qty, b.cts cts, b.dte, stt, cmp , rap_rte " +
             "     , cert_lab, cert_no, 'Z' flg, sk1, nvl(upr, cmp) " + 
             ", decode(greatest(nvl(rap_rte,1), 1), 1, null, trunc((nvl(upr,cmp)/rap_rte*100) - 100, 2)) dis , tfl3 " + 
             "    from mstk b " +
             " where pkt_ty in('NR','SMX') ";
             if(!vnm.equals("")){
                 vnm = util.getVnm(vnm);
                 srchRefQ = srchRefQ +" and ( b.vnm in ("+vnm+") or b.tfl3 in ("+vnm+"))";
             }
                
             if(!stt.equals("0")){
                 srchRefQ = srchRefQ +" and b.stt ='"+stt+"' " ;
             }
                 ct = db.execUpd(" Srch Ref ", srchRefQ, new ArrayList());
                 String pktPrp = "pkgmkt.pktPrp(0,?)";
                 params = new ArrayList();
                 params.add("MEMO_RTRN");
                 ct = db.execCall(" Srch Prp ", pktPrp, params);
                 form.reset();
             
             }
        req.setAttribute("vnmLst", vnm);
        stockList = srchQuery.SearchResult(req,res, "'Z'", prpList);
        if(stockList!=null && stockList.size() >0){
            HashMap totals = srchQuery.GetTotal(req,res,"Z");
            req.setAttribute("totalMap", totals);
        }
            
        req.setAttribute("view", "Y");
        req.setAttribute("pktList", stockList);
        req.setAttribute("stt", stt);
        
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("PACKET_PRINT");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("PACKET_PRINT");
        allPageDtl.put("PACKET_PRINT",pageDtl);
        }
        info.setPageDetails(allPageDtl);
        int accessidn=util.updAccessLog(req,res,"Packet Print", "Packet print pkt List "+stockList.size());
        req.setAttribute("accessidn", String.valueOf(accessidn));;
        return am.findForward("load");
        }
    }
    public PacketPrintAction() {
        super();
    }
    public ActionForward pktPrint(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
             PacketPrintForm form = (PacketPrintForm)af;
            util.updAccessLog(req,res,"Packet Print", "pktPrint");
              String delQ = " Delete from mkt_prc ";
              int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
              String repNme = util.nvl((String)form.getValue("repNme"));
              String accessidn = util.nvl((String)form.getValue("accessidn"));
//              String stt = util.nvl((String)form.getValue("assortSave"));
//              String labIss = util.nvl((String)form.getValue("labSave"));
//              String labDnPktPrint = util.nvl((String)form.getValue("labDnPktPrint"));
//              String labUpPktPrint = util.nvl((String)form.getValue("labUpPktPrint"));
//              String barPktPrint = util.nvl((String)form.getValue("barPktPrint"));
//              String pktPrint = util.nvl((String)form.getValue("pktPrinttest"));
//             
//             String lotPrint = util.nvl((String)form.getValue("lotPktPrint"));
              ArrayList ary = new ArrayList();
              HashMap dbinfo = info.getDmbsInfoLst();
              String cnt = (String)dbinfo.get("CNT");
              String webUrl = (String)dbinfo.get("REP_URL");
              String reqUrl = (String)dbinfo.get("HOME_DIR");
               String repPath = (String)dbinfo.get("REP_PATH");
               if(repNme.equals(""))
               repNme = "pktprint_prc.rdf&p_stt=NA";
               repNme = repNme.replace("accessID", accessidn);
               
//              if(!stt.equals(""))
//                  repNme = "pktprint_prc.rdf&p_stt=ASRT";
//              if(!labIss.equals(""))
//                 repNme="pktprint_lbiss.rdf";
//              if(!labDnPktPrint.equals(""))
//                    repNme = "pktlbl_gage_dn.jsp";
//             if(!labUpPktPrint.equals(""))
//              repNme = "pktlbl_gage_up.jsp";
//              if(!barPktPrint.equals(""))
//                    repNme = "lbl_bar.jsp";
//            if(!lotPrint.equals(""))
//               repNme = "lbl_lot.jsp";
//              if(!pktPrint.equals(""))
//              repNme = "pktprint_prctest.rdf&p_stt=NA"; 
                  String url = "";
                  if(cnt.equals("ad")){
                      ArrayList pktIdnList = new ArrayList(); 
                      ArrayList  outLst = db.execSqlLst("gt_Srch_reslt", "select stk_idn from gt_srch_rslt where flg = 'S' ", new ArrayList());
                      PreparedStatement pst = (PreparedStatement)outLst.get(0);
                      ResultSet  rs = (ResultSet)outLst.get(1);
                      while(rs.next()){
                        String idn = rs.getString("stk_idn");
                        pktIdnList.add(idn);
                      }
                      
                        rs.close();
                        pst.close();
                      
                      System.out.println("IN API"+pktIdnList);
                      DefaultHttpClient httpClient = new DefaultHttpClient();
                      HttpPost postRequest = new HttpPost("http://dev.qaqtech.com/preMarketing/oraPacketPrint");
                      postRequest.setHeader("Accept", "application/json");
                      postRequest.setHeader("Content-type", "application/json");
                      postRequest.setHeader("method", "getPacketPrintOraPdf");
                      postRequest.setHeader("ds", "ADORCL");
                      JSONObject jObj = new JSONObject();
                      
                      try{
                               jObj.put("pktIdnList", pktIdnList);
                                                
                      } catch (JSONException jsone) {
                        jsone.printStackTrace();
                      }
                                   
                      StringEntity insetValue = new StringEntity(jObj.toString());
                      insetValue.setContentType(MediaType.APPLICATION_JSON);
                      postRequest.setEntity(insetValue);
                                                          
                      HttpResponse responsejson = httpClient.execute(postRequest);
                                                          
                      if (responsejson.getStatusLine().getStatusCode() !=200) {
                      throw new RuntimeException("Failed : HTTP error code : "
                                             + responsejson.getStatusLine().getStatusCode());
                      }
                                                          
                                                                 
                      BufferedReader br = new BufferedReader(new InputStreamReader((responsejson.getEntity().getContent())));
                      String outsetValue="";
                      String jsonStr = br.readLine();
                      System.out.println("OutsetValue from Server .... \n"+br.readLine());
                      while ((outsetValue = br.readLine()) != null) {
                            System.out.println("outsetValue"+outsetValue);
                            jsonStr =jsonStr+outsetValue ;
                      }
                      System.out.println("jsonStr"+jsonStr);
                      httpClient.getConnectionManager().shutdown();
          
                      if(!jsonStr.equals("")){
                          JSONObject  jObject = new JSONObject(jsonStr);
                          url = (String)jObject.get("result");
                      } 
                      System.out.println("url"+url);
                  }
               
            if(url == ""){
              int mkt_prc = 0;
              ArrayList  outLst = db.execSqlLst("mkt_prc", "select  seq_mkt_prc.nextval from dual ", new ArrayList());
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet  rs = (ResultSet)outLst.get(1);
              if(rs.next())
                  mkt_prc = rs.getInt(1);
                rs.close();
                pst.close();
              String insertPrc = " insert into mkt_prc(prc_idn ,mstk_idn ,rep_dte) select ?, stk_idn, sysdate from" +
                  " gt_srch_rslt where flg = ? ";
              ary.add(String.valueOf(mkt_prc));
              ary.add("S");
               ct = db.execUpd("insert mkt_prc", insertPrc, ary);
               form.setValue("save", "");
              form.setValue("assortSave", "");
              form.setValue("labSave", "");
              form.setValue("labPktPrint", "");
              form.setValue("barPktPrint", "");
              form.setValue("pktPrinttest", "");
              if(ct>0){
                 url = repPath+"/reports/rwservlet?"+cnt+"&report="+reqUrl+"\\reports\\"+repNme ;
                 res.sendRedirect(url);    
              }
            } else {
                res.sendRedirect(url);  
            }
            util.updAccessLog(req,res,"Packet Print", "pktPrint done");
            return null;
        }
    }
}
