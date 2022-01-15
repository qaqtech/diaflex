package ft.com.report;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.dao.DFMenu;

import ft.com.dao.GtPktDtl;
import ft.com.dao.JsonDao;
import ft.com.dao.TransReportList;
import ft.com.dao.TransactionRDAO;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import org.json.JSONObject;

public class TransactionReportAction extends DispatchAction {
    
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
        util.updAccessLog(req,res,"TransactionReport Action", "load start");
            TransactionReportForm udf = (TransactionReportForm)af;
            udf.resetALL();
            String dtefr = util.getBackDate("dd-MM-yyyy", 7);
           String dteto = util.getBackDate("dd-MM-yyyy", 0);
           udf.setValue("dtefr", dtefr);
          udf.setValue("dteto", dteto);
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("TRNASACTION_RPT");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("TRNASACTION_RPT");
            allPageDtl.put("TRNASACTION_RPT",pageDtl);
            }
            util.updAccessLog(req,res,"TransactionReport Action", "load end");
        return am.findForward("load");
        }
    }
    
    public ActionForward fetch(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            boolean isSuccess=true;    
            HashMap dbmsInfo = info.getDmbsInfoLst();
            String serviceUrl = (String)dbmsInfo.get("DIAFLEXSERVICEURL");
            util.updAccessLog(req,res,"TransactionReport Action", "load start");
            TransactionReportForm udf = (TransactionReportForm)af;
            int partyId = Integer.parseInt( util.nvl(req.getParameter("nmeID"),"0"));
            String transRType = util.nvl((String)udf.getValue("transRType"));
            String dtefr = util.nvl((String)udf.getValue("dtefr"));
            String dteto = util.nvl((String)udf.getValue("dteto"));
            String transOrder = util.nvl((String)udf.getValue("transOrder"));
                String cnt = util.nvl((String)dbmsInfo.get("CNT")).toUpperCase();
            JSONObject jObj = new JSONObject();
            jObj.put("transType", transRType);
            jObj.put("partyId", partyId);
            jObj.put("transDateFrom", dtefr);
            jObj.put("transDateTo", dteto);
            jObj.put("orderBy", transOrder); 
            jObj.put("dbName", cnt);
                
            System.out.print(jObj.toString());
            serviceUrl=serviceUrl+"/TransRPT/TransReprot/transationAction";
            TransReportList transactionRJson=null;
                try {
                JsonDao jsonDao = new JsonDao();
                jsonDao.setServiceUrl(serviceUrl);
                jsonDao.setJsonObject(jObj);
                String jsonInString = util.getJsonString(jsonDao);
                System.out.println(jsonInString);
                if (!jsonInString.equals("FAIL")) {

                    ObjectMapper mapper = new ObjectMapper();
                    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

     transactionRJson = mapper.readValue(jsonInString, TransReportList.class);

                } else
                    isSuccess = false;
            } catch (JsonParseException jpe) {
                // TODO: Add catch code
                jpe.printStackTrace();
                isSuccess = false;
            } catch (JsonMappingException jme) {
                // TODO: Add catch code
                jme.printStackTrace();
                isSuccess = false;
            } catch (IOException ioe) {
                // TODO: Add catch code
                ioe.printStackTrace();
                isSuccess = false;
            }
                String status = transactionRJson.getSTATUS();
                if(status.equals("FAIL")){
                    isSuccess=false;
                    }
                if(isSuccess){
                    udf.setValue("dtefr", dtefr);
                    udf.setValue("dteto", dteto);
                    ArrayList transDtlList = transactionRJson.getRESULT();
                    if(transOrder.equals("BYR")){
                    Collections.sort(transDtlList, new Comparator<TransactionRDAO>() {
                         public int compare(TransactionRDAO o1, TransactionRDAO o2) {
                               int byrCmp =o1.getBuyer().compareTo(o2.getBuyer());
                               if(byrCmp!=0)
                                   return byrCmp;
                               
                               return (o1.getIDN() < o2.getIDN() ? -1 :
                                              (o1.getIDN() == o2.getIDN() ? 0 : 1));
                               
                           }
                       });
                    }else if(transOrder.equals("EMP")){
                        Collections.sort(transDtlList, new Comparator<TransactionRDAO>() {
                             public int compare(TransactionRDAO o1, TransactionRDAO o2) {
                                  int empCmp = o1.getEmp().compareTo(o2.getEmp());
                                   if(empCmp!=0)
                                       return empCmp;
                                   
                                   return (o1.getIDN() < o2.getIDN() ? -1 :
                                                  (o1.getIDN() == o2.getIDN() ? 0 : 1));
                               }
                           });
                    }else if(transOrder.equals("IDN")){
                        Collections.sort(transDtlList, new Comparator<TransactionRDAO>() {
                             public int compare(TransactionRDAO o1, TransactionRDAO o2) {
                                   Long idn1 = new Long(o1.getIDN());
                                    Long idn2 = new Long(o2.getIDN());
                                  return idn2.compareTo(idn1);
                                  
                                  
                               }
                           });
                        
                    }else{
                        Collections.sort(transDtlList, new Comparator<TransactionRDAO>() {
                             public int compare(TransactionRDAO o1, TransactionRDAO o2) {
                                   Long tm1 = new Long(o1.getIss_dtetm());
                                    Long tm2 = new Long(o2.getIss_dtetm());
                                   int tmCmp = tm2.compareTo(tm1);
                                   
                                   if(tmCmp!=0)
                                       return tmCmp;
                                   
                                   return (o1.getIDN() < o2.getIDN() ? -1 :
                                                  (o1.getIDN() == o2.getIDN() ? 0 : 1));
                               }
                           });
                    }
                    
                    Iterator<TransactionRDAO> it = transDtlList.iterator();
                    int sumQty = 0;
                    double sumCts = 0.0;
                    while (it.hasNext()) {
                            TransactionRDAO obj = it.next();
                            String qty = util.nvl(obj.getAc_qty());
                            String cts = util.nvl(obj.getAc_cts());
                            if (!qty.equals("")) {
                                    int iQty = Integer.parseInt(qty);
                                    sumQty += iQty;
                            }
                            if (!cts.equals("")) {
                                    double iCts = Double.parseDouble(cts);
                                    sumCts += iCts;
                            }
                        
                    }
                    
                    req.setAttribute("sumQty", String.valueOf(sumQty));
                    req.setAttribute("sumCts", String.valueOf(util.roundToDecimals(sumCts,2)));
                    
                    udf.setTransDtlList(transDtlList);
                    
                }else{
                    udf.setTransDtlList(new ArrayList<TransactionRDAO>());
                    req.setAttribute("MSG", "Sorry No Result found");
                }
                req.setAttribute("view", "Y");
            util.updAccessLog(req,res,"TransactionReport Action", "load end");
            return am.findForward("load");
            }
            
        }
    
    public ActionForward pktDetails(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        util.updAccessLog(req,res,"TransactionReport Action", "load start");
        String memoId =  util.nvl(req.getParameter("memoId")); 
        String typ =  util.nvl(req.getParameter("TYP")); 
        String transTyp =  util.nvl(req.getParameter("transTyp"));
            
        GenericInterface genericInt = new GenericImpl();   
        ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, "TRANS_VW", "TRANS_VW"); 
        ArrayList itemList = new ArrayList();
        int ct = db.execUpd("delete gt", "delete from gt_srch_rslt", new ArrayList());    
        String tableName = " from mjan a , jandtl b , mstk c ";
        if(transTyp.equals("SL")){
            tableName = " from msal a , jansal b , mstk c ";
        }else if(transTyp.equals("DLV")){
            tableName = " from mdlv a , dlv_Dtl b , mstk c ";
                }
        
        String srchRefQ = "Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts, cert_no ,quot,rap_rte, rap_dis, sl_rte, sl_rap_dis )\n" + 
        "select   c.pkt_ty, c.idn, c.vnm, c.dte, c.stt , 'Z' , c.qty , c.cts, c.cert_no , nvl(upr, cmp) , c.rap_rte , \n" + 
        "trunc(decode(c.rap_rte ,'1',null, trunc((nvl(upr,cmp)/c.rap_rte*100)-100, 2)),2) rap_dis ,\n" + 
        "nvl(b.FNL_SAL,b.QUOT) tranRte, decode(c.rap_rte ,'1',null, trunc((nvl(b.FNL_SAL,b.QUOT)/rap_rte*100)-100, 2)) tran_dis\n" + 
        tableName + 
        "where a.idn=b.idn and b.mstk_idn=c.idn\n" + 
        "and a.idn= ?   ";
            if(typ.equals("ORG")){
                if(transTyp.equals("I") || transTyp.equals("E") || transTyp.equals("WH") ){
                        srchRefQ = srchRefQ + "and b.stt in ('IS','AP','RT')";
                }else if(transTyp.equals("IAP") || transTyp.equals("WAP") || transTyp.equals("EAP")){
                        srchRefQ = srchRefQ + "and b.stt in ('AP','SL','RT')";
                }else if(transTyp.equals("CS")){
                        srchRefQ = srchRefQ + "and b.stt in ('IS','CS','RT')";
                }else if(transTyp.equals("SL")){
                    srchRefQ = srchRefQ + "and b.stt in ('SL','DLV','RT')";
                }else if(transTyp.equals("DLV")){
                        srchRefQ = srchRefQ + "and b.stt in ('DLV','RT')";
                    }
            }else if(typ.equals("RT")){
                srchRefQ = srchRefQ + "and b.stt in ('RT')";
            }else if(typ.equals("COM")){
                
                    if(transTyp.equals("I") || transTyp.equals("E") || transTyp.equals("WH") ){
                            srchRefQ = srchRefQ + "and b.stt in ('AP')";
                    }else if(transTyp.equals("IAP") || transTyp.equals("WAP") || transTyp.equals("EAP")){
                            srchRefQ = srchRefQ + "and b.stt in ('SL')";
                    }else if(transTyp.equals("CS")){
                            srchRefQ = srchRefQ + "and b.stt in ('CS')";
                    }else if(transTyp.equals("SL")){
                        srchRefQ = srchRefQ + "and b.stt in ('DLV')";
                    }else if(transTyp.equals("DLV")){
                            srchRefQ = srchRefQ + "and b.stt in ('DLV')";
                        }
            }else{
                if(transTyp.equals("I") || transTyp.equals("E") || transTyp.equals("WH") ){
                        srchRefQ = srchRefQ + "and b.stt in ('AP','IS')";
                }else if(transTyp.equals("IAP") || transTyp.equals("WAP") || transTyp.equals("EAP")){
                        srchRefQ = srchRefQ + "and b.stt in ('SL','AP')";
                }else if(transTyp.equals("CS")){
                        srchRefQ = srchRefQ + "and b.stt in ('CS','SL')";
                }else if(transTyp.equals("SL")){
                    srchRefQ = srchRefQ + "and b.stt in ('DLV','SL')";
                }else if(transTyp.equals("DLV")){
                        srchRefQ = srchRefQ + "and b.stt in ('DLV')";
                    }
                
            }
        
            ArrayList ary = new ArrayList();
            ary.add(memoId);
            ct = db.execUpd(" Srch Prp ", srchRefQ, ary);

            String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
            ary = new ArrayList();
            ary.add("TRANS_VW");
            ct = db.execCall(" Srch Prp ", pktPrp, ary);
            itemList.add("SRNO");
            itemList.add("PKTCODE");
            itemList.add("STT");
            String sql = " select stk_idn , pkt_ty, vnm, pkt_dte, stt , qty , cts,rap_rte, cert_no , quot, rap_dis, sl_rte, sl_rap_dis  ";

            for (int i = 0; i < vwPrpLst.size(); i++) {
                String lprp = (String) vwPrpLst.get(i);
            String fld = "prp_";
            int j = i + 1;
            if (j < 10)
            fld += "00" + j;
            else if (j < 100)
            fld += "0" + j;
            else if (j > 100)
            fld += j;

            sql += ", " + fld;
                itemList.add(lprp);
                if(lprp.equals("RTE")){
                    itemList.add("TRNS RTE");
                    itemList.add("TRNS DIS");
                }
                
            }

            sql += " from gt_srch_rslt where flg ='Z' order by sk1";
            
            ArrayList outLst = db.execSqlLst("calcPrp", sql, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            ArrayList pktDtlList = new ArrayList();
            try {
                int cnt=0;
                while (rs.next()) {
                    cnt++;
                    HashMap pktDtl = new HashMap();
                    pktDtl.put("PKTCODE", util.nvl(rs.getString("vnm")));
                    pktDtl.put("STT", util.nvl(rs.getString("stt")));
                    pktDtl.put("qty", util.nvl(rs.getString("qty")));
                    pktDtl.put("cts", util.nvl(rs.getString("cts")));
                    pktDtl.put("TRNS RTE", util.nvl(rs.getString("sl_rte")));
                    pktDtl.put("TRNS DIS", util.nvl(rs.getString("sl_rap_dis")));
                    pktDtl.put("SRNO", String.valueOf(cnt));
                    for (int i = 0; i < vwPrpLst.size(); i++) {
                        String lprp = (String) vwPrpLst.get(i);
                        String fld = "prp_";
                        int j = i + 1;
                        if (j < 10)
                            fld += "00" + j;
                        else if (j < 100)
                            fld += "0" + j;
                        else if (j > 100)
                            fld += j;
                        if (lprp.equals("RTE"))
                            fld = "quot";
                        if (lprp.equals("RAP_DIS"))
                            fld = "rap_dis";
                        if (lprp.equals("RAP_RTE"))
                            fld = "rap_rte";
                        pktDtl.put(lprp, util.nvl(rs.getString(fld)));

                    }

                    pktDtlList.add(pktDtl);
                }
                rs.close();
                pst.close();
                req.setAttribute("EXCL", "Y");
                session.setAttribute("itemHdr", itemList);
                session.setAttribute("pktList", pktDtlList);
                System.out.println("result set in request ");
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
        util.updAccessLog(req,res,"TransactionReport Action", "load end");
        return am.findForward("loadResult");
        }
    }
   
    public TransactionReportAction() {
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
                util.updAccessLog(req,res,"TransactionReport Action", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"TransactionReport Action", "init");
            }
          }
            return rtnPg;
            }
}
