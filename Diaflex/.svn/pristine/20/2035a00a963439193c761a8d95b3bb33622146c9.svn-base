package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;

import java.io.OutputStream;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.ws.rs.core.MediaType;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class IfrsAction extends DispatchAction {
    public IfrsAction() {
        super();
    }

    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg = "sucess";
        if (info != null) {
            Connection conn = info.getCon();
            if (conn != null) {
                db.setCon(info.getCon());
                util.setDb(db);
                util.setInfo(info);
                db.setLogApplNm(info.getLoApplNm());
                db.setLogid(String.valueOf(info.getLogId()));
                util.setLogApplNm(info.getLoApplNm());
                rtnPg = init(req, res, session, util);
            } else {
                rtnPg = "connExists";
            }
        } else
            rtnPg = "sessionTO";
        if (!rtnPg.equals("sucess")) {
            return am.findForward(rtnPg);
        } else {
            util.updAccessLog(req, res, "Ifrs Action", "load start");
            ReportForm udf = (ReportForm)af;
            udf.resetALL();
            util.getmonthyr();
            ArrayList quarterList =
                (info.getQuarterList() == null) ? new ArrayList() : (ArrayList)info.getQuarterList();
            udf.setQuarterList(quarterList);
            ArrayList yrList = (info.getYrList() == null) ? new ArrayList() : (ArrayList)info.getYrList();
            udf.setYrList(yrList);
            HashMap allPageDtl = (info.getPageDetails() == null) ? new HashMap() : (HashMap)info.getPageDetails();
            HashMap pageDtl = (HashMap)allPageDtl.get("IFRS");
            if (pageDtl == null || pageDtl.size() == 0) {
                pageDtl = new HashMap();
                pageDtl = util.pagedef("IFRS");
                allPageDtl.put("IFRS", pageDtl);
            }
            info.setPageDetails(allPageDtl);
            util.updAccessLog(req, res, "Ifrs Action", "load end");
            return am.findForward("load");
        }
    }

    public ActionForward fetch(ActionMapping am, ActionForm af, HttpServletRequest req,
                               HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg = "sucess";
        if (info != null) {
            Connection conn = info.getCon();
            if (conn != null) {
                db.setCon(info.getCon());
                util.setDb(db);
                util.setInfo(info);
                db.setLogApplNm(info.getLoApplNm());
                db.setLogid(String.valueOf(info.getLogId()));
                util.setLogApplNm(info.getLoApplNm());
                rtnPg = init(req, res, session, util);
            } else {
                rtnPg = "connExists";
            }
        } else
            rtnPg = "sessionTO";
        if (!rtnPg.equals("sucess")) {
            return am.findForward(rtnPg);
        } else {
            util.updAccessLog(req, res, "Ifrs Action", "fetch start");
            ReportForm udf = (ReportForm)af;
            HashMap dbinfo = info.getDmbsInfoLst();
            GenericInterface genericInt = new GenericImpl();
            HashMap prp = info.getPrp();
            HashMap mprp = info.getMprp();
            String rep_path = (String)dbinfo.get("REP_PATH");
            String mongodb = (String)dbinfo.get("MONGODB");
            String cnt = (String)dbinfo.get("CNT");
            String recfrmdte = util.nvl((String)udf.getValue("recfrmdte"));
            String restodte = util.nvl((String)udf.getValue("restodte"));
            String laser = util.nvl((String)udf.getValue("laser"));
            String fldVal1recpt_dt = util.convertyyyymmddFmt(recfrmdte);
            String fldVal2recpt_dt = util.convertyyyymmddFmt(restodte);
            String type = util.nvl((String)udf.getValue("type"));

            HashMap allpktListMap = new HashMap();
            ArrayList pktStkIdnList = new ArrayList();
            HashMap allPageDtl = (info.getPageDetails() == null) ? new HashMap() : (HashMap)info.getPageDetails();
            HashMap pageDtl = (HashMap)allPageDtl.get("IFRS");
            ArrayList pageList = new ArrayList();
            HashMap pageDtlMap = new HashMap();
            String fld_nme = "", fld_ttl = "", val_cond = "", lov_qry = "", dflt_val = "", fld_typ = "", form_nme =
                "", flg1 = "", fixedprp = "CP", avgfixedprp = "RTE";
            ArrayList filterlprpLst = new ArrayList();
            HashMap dtls = new HashMap();
            ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "IFRS_OPEN_CLOSE", "IFRS_OPEN_CLOSE");
            pageList = (ArrayList)pageDtl.get("FIXED");
            if (pageList != null && pageList.size() > 0) {
                for (int j = 0; j < pageList.size(); j++) {
                    pageDtlMap = (HashMap)pageList.get(j);
                    fixedprp = (String)pageDtlMap.get("fld_nme");
                }
            }

            pageList = (ArrayList)pageDtl.get("AVGFIXED");
            if (pageList != null && pageList.size() > 0) {
                for (int j = 0; j < pageList.size(); j++) {
                    pageDtlMap = (HashMap)pageList.get(j);
                    avgfixedprp = (String)pageDtlMap.get("fld_nme");
                }
            }

            if (laser.equals("")) {
                if (type.equals("NR") || type.equals("BOTH")) {
                    dtls = new HashMap();
                    if (cnt.equals("hk")) {
                        dtls.put("prpTyp", "Y#Y#M");
                        dtls.put("prp", "RECPT_DT#STK-CTG#IFRS_YN");
                        dtls.put("prpVal", "20000101@" + fldVal2recpt_dt + "#5@10@15#10@20");
                        dtls.put("mdl", "IFRS_OPEN_CLOSE");
                        dtls.put("pkt_ty", "NR,MIX");
                        dtls.put("tblNme", "STK_ASRT,STK_MKTG");
                        dtls.put("clientName", mongodb);
                        dtls.put("fixedprp", fixedprp);
                        dtls.put("avgfixedprp", avgfixedprp);
                        dtls.put("fldVal1recpt_dt", fldVal1recpt_dt);
                        dtls.put("fldVal2recpt_dt", fldVal2recpt_dt);
                        dtls.put("condition", "N");

                        //            dtls.put("prpTyp", "Y#Y#M");
                        //            dtls.put("prp", "RECPT_DT#STK-CTG#IFRS_YN");
                        //            dtls.put("prpVal", fldVal2recpt_dt+"@"+fldVal2recpt_dt+"#10@15#10@20");
                        //            dtls.put("mdl", "IFRS_OPEN_CLOSE");
                        //            dtls.put("pkt_ty", "NR,MIX");
                        //            dtls.put("tblNme", "STK_ASRT,STK_MKTG");
                        //            dtls.put("clientName", mongodb);
                        //            dtls.put("fixedprp", fixedprp);
                        //            dtls.put("avgfixedprp", avgfixedprp);
                        //            dtls.put("fldVal1recpt_dt", fldVal1recpt_dt);
                        //            dtls.put("fldVal2recpt_dt", fldVal2recpt_dt);
                        //            dtls.put("condition", "N");
                    } else {
                        dtls.put("prpTyp", "Y#M");
                        dtls.put("prp", "RECPT_DT#IFRS_YN");
                        dtls.put("prpVal", "20000101@" + fldVal2recpt_dt + "#10@20");
                        dtls.put("mdl", "IFRS_OPEN_CLOSE");
                        dtls.put("pkt_ty", "NR,MIX");
                        dtls.put("tblNme", "STK_ASRT,STK_MKTG");
                        dtls.put("clientName", mongodb);
                        dtls.put("fixedprp", fixedprp);
                        dtls.put("avgfixedprp", avgfixedprp);
                        dtls.put("fldVal1recpt_dt", fldVal1recpt_dt);
                        dtls.put("fldVal2recpt_dt", fldVal2recpt_dt);
                        dtls.put("condition", "N");
                    }
                    HashMap pktListMap = getPacketsFromMongo(req, res, dtls);
                    //            HashMap pktListMap=new HashMap();
                    if (pktListMap.size() > 0)
                        allpktListMap.putAll(pktListMap);

                    String fldVal1recpt_dtSoldTbl = util.convertyyyymmddFmt(recfrmdte);
                    String fldVal2recpt_dtSoldTbl = util.convertyyyymmddFmt(restodte);
                    String dtePrpQ1 =
                        "select to_char(to_date('" + restodte + "','dd-mm-rrrr')+1, 'dd-mm-rrrr') frmD,to_char(to_date(sysdate,'dd-mm-rrrr'), 'dd-mm-rrrr') toD from dual";
                    ResultSet rs1 = db.execSql("Date calc", dtePrpQ1, new ArrayList());
                    while (rs1.next()) {
                        fldVal1recpt_dtSoldTbl = util.convertyyyymmddFmt(util.nvl(rs1.getString("frmD")));
                        fldVal2recpt_dtSoldTbl = util.convertyyyymmddFmt(util.nvl(rs1.getString("toD")));
                    }
                    rs1.close();
                    dtls = new HashMap();
                    if (cnt.equals("hk")) {
                        dtls.put("prpTyp", "Y#Y#Y#M#M");
                        dtls.put("prp", "RECPT_DT#ACC_INV_DTE#STK-CTG#SL_PKT_TYP#IFRS_YN");
                        dtls.put("prpVal",
                                 "20000101@" + fldVal2recpt_dt + "#" + fldVal1recpt_dtSoldTbl + "@" + fldVal2recpt_dtSoldTbl +
                                 "#5@10@15#10@30@40#10@20");
                        dtls.put("mdl", "IFRS_OPEN_CLOSE");
                        dtls.put("pkt_ty", "NR,MIX");
                        dtls.put("tblNme", "STK_SOLD");
                        dtls.put("clientName", mongodb);
                        dtls.put("fixedprp", fixedprp);
                        dtls.put("avgfixedprp", avgfixedprp);
                        dtls.put("fldVal1recpt_dt", fldVal1recpt_dt);
                        dtls.put("fldVal2recpt_dt", fldVal2recpt_dt);
                        dtls.put("condition", "N");
                    } else {
                        dtls.put("prpTyp", "Y#Y#M");
                        dtls.put("prp", "RECPT_DT#ACC_INV_DTE#IFRS_YN");
                        dtls.put("prpVal",
                                 "20000101@" + fldVal2recpt_dt + "#" + fldVal1recpt_dtSoldTbl + "@" + fldVal2recpt_dtSoldTbl +
                                 "#10@20");
                        dtls.put("mdl", "IFRS_OPEN_CLOSE");
                        dtls.put("pkt_ty", "NR,MIX");
                        dtls.put("tblNme", "STK_SOLD");
                        dtls.put("clientName", mongodb);
                        dtls.put("fixedprp", fixedprp);
                        dtls.put("avgfixedprp", avgfixedprp);
                        dtls.put("fldVal1recpt_dt", fldVal1recpt_dt);
                        dtls.put("fldVal2recpt_dt", fldVal2recpt_dt);
                        dtls.put("condition", "N");
                    }
                    pktListMap = new HashMap();
                    pktListMap = getPacketsFromMongo(req, res, dtls);
                    if (pktListMap.size() > 0)
                        allpktListMap.putAll(pktListMap);

                    dtls = new HashMap();
                    if (cnt.equals("hk")) {
                        dtls.put("prpTyp", "Y#Y#M#M");
                        dtls.put("prp", "ACC_INV_DTE#STK-CTG#SL_PKT_TYP#IFRS_YN");
                        dtls.put("prpVal", fldVal1recpt_dt + "@" + fldVal2recpt_dt + "#5@10@15#10@30@40#10@20");
                        dtls.put("mdl", "IFRS_OPEN_CLOSE");
                        dtls.put("pkt_ty", "NR,MIX");
                        dtls.put("tblNme", "STK_SOLD");
                        dtls.put("clientName", mongodb);
                        dtls.put("fixedprp", fixedprp);
                        dtls.put("avgfixedprp", avgfixedprp);
                        dtls.put("fldVal1recpt_dt", fldVal1recpt_dt);
                        dtls.put("fldVal2recpt_dt", fldVal2recpt_dt);
                        dtls.put("condition", "N");
                    } else {
                        dtls.put("prpTyp", "Y#M");
                        dtls.put("prp", "ACC_INV_DTE#IFRS_YN");
                        dtls.put("prpVal", fldVal1recpt_dt + "@" + fldVal2recpt_dt + "#10@20");
                        dtls.put("mdl", "IFRS_OPEN_CLOSE");
                        dtls.put("pkt_ty", "NR,MIX");
                        dtls.put("tblNme", "STK_SOLD");
                        dtls.put("clientName", mongodb);
                        dtls.put("fixedprp", fixedprp);
                        dtls.put("avgfixedprp", avgfixedprp);
                        dtls.put("fldVal1recpt_dt", fldVal1recpt_dt);
                        dtls.put("fldVal2recpt_dt", fldVal2recpt_dt);
                        dtls.put("condition", "N");
                    }
                    pktListMap = new HashMap();
                    pktListMap = getPacketsFromMongo(req, res, dtls);
                    if (pktListMap.size() > 0)
                        allpktListMap.putAll(pktListMap);

                    dtls = new HashMap();
                    if (cnt.equals("hk")) {
                        dtls.put("prpTyp", "Y#Y#M#M");
                        dtls.put("prp", "RECPT_DT#STK-CTG#SL_PKT_TYP#IFRS_YN");
                        dtls.put("prpVal", "20000101@" + fldVal2recpt_dt + "#5@10@15#10@30@40#10@20");
                        dtls.put("mdl", "IFRS_OPEN_CLOSE");
                        dtls.put("stt", "MKSL");
                        dtls.put("pkt_ty", "NR,MIX");
                        dtls.put("tblNme", "STK_SOLD");
                        dtls.put("clientName", mongodb);
                        dtls.put("fixedprp", fixedprp);
                        dtls.put("avgfixedprp", avgfixedprp);
                        dtls.put("fldVal1recpt_dt", fldVal1recpt_dt);
                        dtls.put("fldVal2recpt_dt", fldVal2recpt_dt);
                        dtls.put("condition", "N");
                    } else {
                        dtls.put("prpTyp", "Y#M");
                        dtls.put("prp", "RECPT_DT#IFRS_YN");
                        dtls.put("prpVal", "20000101@" + fldVal2recpt_dt + "#10@20");
                        dtls.put("mdl", "IFRS_OPEN_CLOSE");
                        dtls.put("stt", "MKSL");
                        dtls.put("pkt_ty", "NR,MIX");
                        dtls.put("tblNme", "STK_SOLD");
                        dtls.put("clientName", mongodb);
                        dtls.put("fixedprp", fixedprp);
                        dtls.put("avgfixedprp", avgfixedprp);
                        dtls.put("fldVal1recpt_dt", fldVal1recpt_dt);
                        dtls.put("fldVal2recpt_dt", fldVal2recpt_dt);
                        dtls.put("condition", "N");
                    }
                    pktListMap = new HashMap();
                    pktListMap = getPacketsFromMongo(req, res, dtls);
                    if (pktListMap.size() > 0)
                        allpktListMap.putAll(pktListMap);
                }
                //Mix Ifrs Start
                dtls = new HashMap();
                String dtePrpQ =
                    "select to_char(trunc(sysdate), 'dd-mm-rrrr') todte,to_char(trunc(sysdate), 'rrrrmmdd') rrtodte,to_char(to_date('" +
                    restodte + "','dd-mm-rrrr')+1, 'dd-mm-rrrr') frmdte,to_char(to_date('" + restodte +
                    "','dd-mm-rrrr')+1, 'rrrrmmdd') rrfrmdte from dual";

                ArrayList outLst = db.execSqlLst("Date calc", dtePrpQ, new ArrayList());
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
                while (rs.next()) {
                    dtls.put("p2recfrmdte", util.nvl(rs.getString("frmdte")));
                    dtls.put("p2restodte", util.nvl(rs.getString("todte")));
                    session.setAttribute("p2recfrmdte", util.nvl(rs.getString("rrfrmdte")));
                    session.setAttribute("p2restodte", util.nvl(rs.getString("rrtodte")));
                }
                rs.close(); pst.close();
                dtls.put("mdl", "IFRS_OPEN_CLOSE_MIX");
                dtls.put("recfrmdte", recfrmdte);
                dtls.put("restodte", restodte);
                if (type.equals("MIX") || type.equals("BOTH")) {
                    HashMap summaryMixMap = getDataNewSOld(req, res, dtls);
                    session.setAttribute("summaryMixMap", summaryMixMap);
                }
                //Mix Ifrs End

                pktStkIdnList = util.getKeyInArrayList(allpktListMap);
                session.setAttribute("pktStkIdnListopenclose", pktStkIdnList);
                session.setAttribute("filterStkIdnListopenclose", util.useDifferentArrayList(pktStkIdnList));
                session.setAttribute("pktListMapopenclose", (HashMap)sortByComparatorMap(allpktListMap, "sk1"));
                session.setAttribute("opencloseCriteria", "");
                session.setAttribute("fldVal1recpt_dt", fldVal1recpt_dt);
                session.setAttribute("fldVal2recpt_dt", fldVal2recpt_dt);


                pageList = (ArrayList)pageDtl.get("LEVEL1LPRP");
                if (pageList != null && pageList.size() > 0) {
                    for (int j = 0; j < pageList.size(); j++) {
                        pageDtlMap = (HashMap)pageList.get(j);
                        fld_ttl = (String)pageDtlMap.get("fld_ttl");
                        fld_nme = (String)pageDtlMap.get("fld_nme");
                        filterlprpLst.add(fld_nme);
                    }
                }

                session.setAttribute("openclosefilterlprpLst", filterlprpLst);
                info.setLevel1(util.nvl((String)udf.getValue("level1openclose")));
            } else {
                String memoString = util.nvl((String)udf.getValue("memoString"));
                if (!memoString.equals(""))
                    memoString = util.getVnm(memoString);
                String[] memoLst = memoString.split(",");
                dtls = new HashMap();
                String dtePrpQ =
                    "select to_char(trunc(sysdate), 'dd-mm-rrrr') todte,to_char(trunc(sysdate), 'rrrrmmdd') rrtodte,to_char(to_date('" +
                    restodte + "','dd-mm-rrrr')+1, 'dd-mm-rrrr') frmdte,to_char(to_date('" + restodte +
                    "','dd-mm-rrrr')+1, 'rrrrmmdd') rrfrmdte from dual";

                ArrayList outLst = db.execSqlLst("Date calc", dtePrpQ, new ArrayList());
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
                while (rs.next()) {
                    dtls.put("p2recfrmdte", util.nvl(rs.getString("rrfrmdte")));
                    dtls.put("p2restodte", util.nvl(rs.getString("rrtodte")));
                    session.setAttribute("p2recfrmdte", util.nvl(rs.getString("rrfrmdte")));
                    session.setAttribute("p2restodte", util.nvl(rs.getString("rrtodte")));
                }
                rs.close(); pst.close();
                dtls.put("recfrmdte", fldVal1recpt_dt);
                dtls.put("restodte", fldVal2recpt_dt);
                dtls.put("memoLst", memoLst);
                dtls.put("groupLprp", util.nvl((String)udf.getValue("level1openclose")));

                getLaser(req, res, dtls);
                req.setAttribute("laser", "Y");
                return am.findForward("load");
            }
            util.updAccessLog(req, res, "Ifrs Action", "fetch end");
            return loadstkopencloseData(am, af, req, res);
        }
    }

    public HashMap getLaser(HttpServletRequest req, HttpServletResponse res, HashMap dtls) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm());
        db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HashMap dbinfo = info.getDmbsInfoLst();
        String mongodb = util.nvl((String)dbinfo.get("MONGODB"));
        String rep_path = (String)dbinfo.get("REP_PATH");
        GenericInterface genericInt = new GenericImpl();
        String resfrmdte = util.nvl((String)dtls.get("recfrmdte"));
        String restodte = util.nvl((String)dtls.get("restodte"));
        String p2recfrmdte = util.nvl((String)dtls.get("p2recfrmdte"));
        String p2restodte = util.nvl((String)dtls.get("p2restodte"));
        String[] memoLst = ((String[])dtls.get("memoLst"));
        String groupLprp = util.nvl((String)dtls.get("groupLprp"));
        long startm = new Date().getTime();
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HashMap pktListMap = new HashMap();
        ArrayList monthLst = new ArrayList();
        HttpPost postRequest = new HttpPost("http://ifrs1.hk.co/ifrsservice7/REST/WebService/getLaser");
        postRequest.setHeader("Accept", "application/json");
        postRequest.setHeader("Content-type", "application/json");
        JSONObject jObj = new JSONObject();
        try {
            jObj.put("recfrmdte", resfrmdte);
            jObj.put("restodte", restodte);
            jObj.put("p2recfrmdte", p2recfrmdte);
            jObj.put("p2restodte", p2restodte);
            jObj.put("memoLst", "");
            jObj.put("groupLprp", groupLprp);
            jObj.put("clientName", mongodb);
        } catch (JSONException jsone) {
            jsone.printStackTrace();
        }
          System.out.print(jObj.toString());
        StringEntity insetValue = new StringEntity(jObj.toString());
        insetValue.setContentType(MediaType.APPLICATION_JSON);
        postRequest.setEntity(insetValue);

        HttpResponse responsejson = httpClient.execute(postRequest);

        if (responsejson.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + responsejson.getStatusLine().getStatusCode());
        }


        BufferedReader br = new BufferedReader(new InputStreamReader((responsejson.getEntity().getContent())));
        String outsetValue = "";
        String jsonStr = "";
        //System.out.println("OutsetValue from Server .... \n"+br.readLine());
        while ((outsetValue = br.readLine()) != null) {
            //    System.out.println(outsetValue);
            jsonStr = jsonStr + outsetValue;
        }
        //    System.out.println(jsonStr);
        httpClient.getConnectionManager().shutdown();
        if (!jsonStr.equals("")) {
            JSONObject jObject = new JSONObject(jsonStr);
            JSONObject jSupportObject = (JSONObject)jObject.get("RESULT");
            if (jSupportObject != null) {
                pktListMap = jsonToMap((JSONObject)jSupportObject.get("DATA"));
            }
            JSONArray dataObject = (JSONArray)jSupportObject.get("MONTHLST");
            if (dataObject != null) {
                for (int i = 0; i < dataObject.length(); i++) {
                    monthLst.add((String)dataObject.get(i));
                }
            }
            session.setAttribute("summaryMixMap", pktListMap);
            session.setAttribute("monthLst", monthLst);
        }
        long endm = new Date().getTime();
        System.out.println("@ Collection on Table Total Time = " + ((endm - startm) / 1000));
        return pktListMap;
    }

    public HashMap getDataNewSOld(HttpServletRequest req, HttpServletResponse res, HashMap dtls) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm());
        db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HashMap dbinfo = info.getDmbsInfoLst();
        String mongodb = util.nvl((String)dbinfo.get("MONGODB"));
        String rep_path = (String)dbinfo.get("REP_PATH");
        GenericInterface genericInt = new GenericImpl();
        String mdl = util.nvl((String)dtls.get("mdl"));
        String resfrmdte = util.nvl((String)dtls.get("recfrmdte"));
        String restodte = util.nvl((String)dtls.get("restodte"));
        String p2recfrmdte = util.nvl((String)dtls.get("p2recfrmdte"));
        String p2restodte = util.nvl((String)dtls.get("p2restodte"));
        long startm = new Date().getTime();
        genericInt.genericPrprVw(req, res, mdl, mdl);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HashMap pktListMap = new HashMap();
        HttpPost postRequest = new HttpPost("http://ifrs1.hk.co/ifrsservice7/REST/WebService/getDataNewSOld");
        postRequest.setHeader("Accept", "application/json");
        postRequest.setHeader("Content-type", "application/json");
        JSONObject jObj = new JSONObject();
        try {
            jObj.put("recfrmdte", resfrmdte);
            jObj.put("restodte", restodte);
            jObj.put("p2recfrmdte", p2recfrmdte);
            jObj.put("p2restodte", p2restodte);
            jObj.put("mdl", mdl);
            jObj.put("clientName", mongodb);
            //            jObj.put("recfrmdte", "01-10-2016");
            //            jObj.put("restodte", "31-12-2016");
            //            jObj.put("p2recfrmdte", "01-01-2017");
            //            jObj.put("p2restodte", "15-01-2017");
            //            jObj.put("mdl", mdl);
            //            jObj.put("clientName", mongodb);
        } catch (JSONException jsone) {
            jsone.printStackTrace();
        }
        System.out.println(jObj.toString());
        StringEntity insetValue = new StringEntity(jObj.toString());
        insetValue.setContentType(MediaType.APPLICATION_JSON);
        postRequest.setEntity(insetValue);

        HttpResponse responsejson = httpClient.execute(postRequest);

        if (responsejson.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + responsejson.getStatusLine().getStatusCode());
        }


        BufferedReader br = new BufferedReader(new InputStreamReader((responsejson.getEntity().getContent())));
        String outsetValue = "";
        String jsonStr = "";
        //System.out.println("OutsetValue from Server .... \n"+br.readLine());
        while ((outsetValue = br.readLine()) != null) {
            //    System.out.println(outsetValue);
            jsonStr = jsonStr + outsetValue;
        }
        //    System.out.println(jsonStr);
        httpClient.getConnectionManager().shutdown();
        if (!jsonStr.equals("")) {
            JSONObject jObject = new JSONObject(jsonStr);
            JSONObject jSupportObject = (JSONObject)jObject.get("RESULT");
            if (jSupportObject != null) {
                pktListMap = jsonToMap((JSONObject)jSupportObject.get("DATA"));
            }
        }
        long endm = new Date().getTime();
        System.out.println("@ Collection on Table Total Time = " + ((endm - startm) / 1000));
        return pktListMap;
    }

    public ActionForward loadstkopencloseData(ActionMapping am, ActionForm af, HttpServletRequest req,
                                              HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg = "sucess";
        if (info != null) {
            Connection conn = info.getCon();
            if (conn != null) {
                db.setCon(info.getCon());
                util.setDb(db);
                util.setInfo(info);
                db.setLogApplNm(info.getLoApplNm());
                db.setLogid(String.valueOf(info.getLogId()));
                util.setLogApplNm(info.getLoApplNm());
                rtnPg = init(req, res, session, util);
            } else {
                rtnPg = "connExists";
            }
        } else
            rtnPg = "sessionTO";
        if (!rtnPg.equals("sucess")) {
            return am.findForward(rtnPg);
        } else {
            util.updAccessLog(req, res, "Ifrs Action", "loadstkoencloseData start");
            GenericInterface genericInt = new GenericImpl();
            ReportForm udf = (ReportForm)af;
            HashMap dbinfo = info.getDmbsInfoLst();
            String gridbylprp = util.nvl((String)req.getParameter("gridbylprp"), info.getLevel1());
            String filterlprp = util.nvl((String)req.getParameter("filterlprp"), info.getLevel1());
            String filterlprpval = util.nvl((String)req.getParameter("filterlprpval"), "ALL");
            String opencloseCriteria = util.nvl((String)session.getAttribute("opencloseCriteria"), "");
            ArrayList dspgrpList = new ArrayList();
            HashMap pktListMapopenclose = (HashMap)session.getAttribute("pktListMapopenclose");
            ArrayList filterStkIdnListopenclose = (ArrayList)session.getAttribute("filterStkIdnListopenclose");
            ArrayList openclosefilterlprpLst = (ArrayList)session.getAttribute("openclosefilterlprpLst");
            int filterStkIdnListopenclosesz = filterStkIdnListopenclose.size();
            HashMap pktPrpMap = new HashMap();
            HashMap dataDtl = new HashMap();
            HashMap mprp = info.getSrchMprp();
            ArrayList gtList = new ArrayList();
            ArrayList lstfltquickpktStkIdnList = new ArrayList();
            double qty = 0;
            double vlu = 0;
            double cts = 0;

            opencloseCriteria += " " + util.nvl((String)mprp.get(filterlprp + "D")) + " : " + filterlprpval;
            openclosefilterlprpLst.remove((openclosefilterlprpLst.indexOf(gridbylprp)));

            for (int i = 0; i < filterStkIdnListopenclosesz; i++) {
                String stkIdn = (String)filterStkIdnListopenclose.get(i);
                pktPrpMap = new HashMap();
                pktPrpMap = (HashMap)pktListMapopenclose.get(stkIdn);
                String lprpval = util.nvl((String)pktPrpMap.get(filterlprp));
                if (filterlprpval.equals("ALL") || filterlprpval.equals(lprpval)) {
                    String gridbylprpval = util.nvl((String)pktPrpMap.get(gridbylprp));
                    lstfltquickpktStkIdnList.add(stkIdn);
                    String dsp_stt = util.nvl((String)pktPrpMap.get("dsp_stt"));
                    String stt = util.nvl((String)pktPrpMap.get("stt"));
                    String curlprpqty = util.nvl((String)pktPrpMap.get("qty"), "1").trim();
                    String curcts = util.nvl((String)pktPrpMap.get("cts"), "0").trim();
                    String curMfgcts = util.nvl((String)pktPrpMap.get("MFG_CTS"), "0").trim();
                    String currte = util.nvl((String)pktPrpMap.get("quot"), "0").trim();
                    String curcprte = util.nvl((String)pktPrpMap.get("cp"), "0").trim();
                    String currap_rte = util.nvl((String)pktPrpMap.get("rap_rte"), "0").trim();
                    if (dsp_stt.equals(stt)) {
                        System.out.println("IAMLOCHA" + stkIdn);
                    }
                    if (curMfgcts.equals("0"))
                        curMfgcts = curcts;
                    for (int l = 0; l < 2; l++) {
                        String sttgrp = stt;
                        if (l == 1)
                            sttgrp = dsp_stt;
                        if (!gtList.contains(gridbylprpval))
                            gtList.add(gridbylprpval);
                        BigDecimal currBigLprpQty = new BigDecimal(curlprpqty);
                        BigDecimal lprpBigqty = (BigDecimal)dataDtl.get(gridbylprpval + "_" + sttgrp + "_QTY");
                        if (lprpBigqty == null)
                            lprpBigqty = new BigDecimal(qty);
                        if (!curlprpqty.equals("0"))
                            lprpBigqty = lprpBigqty.add(currBigLprpQty);
                        dataDtl.put(gridbylprpval + "_" + sttgrp + "_QTY", lprpBigqty);

                        BigDecimal currBigLprpCts = new BigDecimal(curcts);
                        BigDecimal lprpBigcts = (BigDecimal)dataDtl.get(gridbylprpval + "_" + sttgrp + "_CTS");
                        if (lprpBigcts == null)
                            lprpBigcts = new BigDecimal(cts);
                        if (!curcts.equals("0"))
                            lprpBigcts = lprpBigcts.add(currBigLprpCts);
                        dataDtl.put(gridbylprpval + "_" + sttgrp + "_CTS", lprpBigcts);

                        BigDecimal currBigLprpMfgCts = new BigDecimal(curMfgcts);
                        BigDecimal lprpBigMfgcts = (BigDecimal)dataDtl.get(gridbylprpval + "_" + sttgrp + "_MFGCTS");
                        if (lprpBigMfgcts == null)
                            lprpBigMfgcts = new BigDecimal(cts);
                        if (!curMfgcts.equals("0"))
                            lprpBigMfgcts = lprpBigMfgcts.add(currBigLprpMfgCts);
                        dataDtl.put(gridbylprpval + "_" + sttgrp + "_MFGCTS", lprpBigMfgcts);

                        BigDecimal currBigCts = new BigDecimal(curcts);
                        BigDecimal currBigLprpVlu = new BigDecimal(currte);
                        BigDecimal lprpBigvlu = (BigDecimal)dataDtl.get(gridbylprpval + "_" + sttgrp + "_VLU");
                        if (lprpBigvlu == null)
                            lprpBigvlu = new BigDecimal(vlu);
                        if (!currte.equals("0")) {
                            currBigLprpVlu = currBigLprpVlu.multiply(currBigCts);
                            lprpBigvlu = lprpBigvlu.add(currBigLprpVlu);
                        }
                        lprpBigvlu = lprpBigvlu.setScale(0, RoundingMode.HALF_EVEN);
                        dataDtl.put(gridbylprpval + "_" + sttgrp + "_VLU", lprpBigvlu);

                        BigDecimal currBigLprpcpVlu = new BigDecimal(curcprte);
                        BigDecimal lprpBigcpvlu = (BigDecimal)dataDtl.get(gridbylprpval + "_" + sttgrp + "_CPVLU");
                        if (lprpBigcpvlu == null)
                            lprpBigcpvlu = new BigDecimal(vlu);
                        if (!curcprte.equals("0")) {
                            currBigLprpcpVlu = currBigLprpcpVlu.multiply(currBigCts);
                            lprpBigcpvlu = lprpBigcpvlu.add(currBigLprpcpVlu);
                        }
                        lprpBigcpvlu = lprpBigcpvlu.setScale(0, RoundingMode.HALF_EVEN);
                        dataDtl.put(gridbylprpval + "_" + sttgrp + "_CPVLU", lprpBigcpvlu);

                        BigDecimal currBigLprpRapVlu = new BigDecimal(currap_rte);
                        BigDecimal lprpBigrapvlu = (BigDecimal)dataDtl.get(gridbylprpval + "_" + sttgrp + "_RAPVLU");
                        if (lprpBigrapvlu == null)
                            lprpBigrapvlu = new BigDecimal(vlu);
                        if (!currte.equals("0")) {
                            currBigLprpRapVlu = currBigLprpRapVlu.multiply(currBigCts);
                            lprpBigrapvlu = lprpBigrapvlu.add(currBigLprpRapVlu);
                        }
                        lprpBigrapvlu = lprpBigrapvlu.setScale(0, RoundingMode.HALF_EVEN);
                        dataDtl.put(gridbylprpval + "_" + sttgrp + "_RAPVLU", lprpBigrapvlu);

                        BigDecimal grandlprpBigqty = (BigDecimal)dataDtl.get(sttgrp + "_QTY");
                        if (grandlprpBigqty == null)
                            grandlprpBigqty = new BigDecimal(qty);
                        if (!curlprpqty.equals("0"))
                            grandlprpBigqty = grandlprpBigqty.add(currBigLprpQty);
                        dataDtl.put(sttgrp + "_QTY", grandlprpBigqty);

                        BigDecimal grandlprpBigcts = (BigDecimal)dataDtl.get(sttgrp + "_CTS");
                        if (grandlprpBigcts == null)
                            grandlprpBigcts = new BigDecimal(cts);
                        if (!curcts.equals("0"))
                            grandlprpBigcts = grandlprpBigcts.add(currBigLprpCts);
                        dataDtl.put(sttgrp + "_CTS", grandlprpBigcts);

                        BigDecimal grandlprpBigMfgcts = (BigDecimal)dataDtl.get(sttgrp + "_MFGCTS");
                        if (grandlprpBigMfgcts == null)
                            grandlprpBigMfgcts = new BigDecimal(cts);
                        if (!curMfgcts.equals("0"))
                            grandlprpBigMfgcts = grandlprpBigMfgcts.add(currBigLprpMfgCts);
                        dataDtl.put(sttgrp + "_MFGCTS", grandlprpBigMfgcts);

                        BigDecimal grandlprpBigvlu = (BigDecimal)dataDtl.get(sttgrp + "_VLU");
                        if (grandlprpBigvlu == null)
                            grandlprpBigvlu = new BigDecimal(vlu);
                        if (!currte.equals("0"))
                            grandlprpBigvlu = grandlprpBigvlu.add(currBigLprpVlu);
                        grandlprpBigvlu = grandlprpBigvlu.setScale(0, RoundingMode.HALF_EVEN);
                        dataDtl.put(sttgrp + "_VLU", grandlprpBigvlu);

                        BigDecimal grandlprpBigcpvlu = (BigDecimal)dataDtl.get(sttgrp + "_CPVLU");
                        if (grandlprpBigcpvlu == null)
                            grandlprpBigcpvlu = new BigDecimal(vlu);
                        if (!curcprte.equals("0"))
                            grandlprpBigcpvlu = grandlprpBigcpvlu.add(currBigLprpcpVlu);
                        grandlprpBigcpvlu = grandlprpBigcpvlu.setScale(0, RoundingMode.HALF_EVEN);
                        dataDtl.put(sttgrp + "_CPVLU", grandlprpBigcpvlu);

                        BigDecimal grandlprpBigrapvlu = (BigDecimal)dataDtl.get(sttgrp + "_RAPVLU");
                        if (grandlprpBigrapvlu == null)
                            grandlprpBigrapvlu = new BigDecimal(vlu);
                        if (!currte.equals("0"))
                            grandlprpBigrapvlu = grandlprpBigrapvlu.add(currBigLprpRapVlu);
                        grandlprpBigrapvlu = grandlprpBigrapvlu.setScale(0, RoundingMode.HALF_EVEN);
                        dataDtl.put(sttgrp + "_RAPVLU", grandlprpBigrapvlu);
                    }
                }
            }

            dspgrpList.add("OPEN");
            dspgrpList.add("PURNEW");
            dspgrpList.add("MFGNEW");
            dspgrpList.add("MIXTOSNGL");
            dspgrpList.add("SOLD");
            dspgrpList.add("CLOSE");
            req.setAttribute("filterlprp", gridbylprp);
            udf.setValue("criteria", opencloseCriteria);
            req.setAttribute("gridbylprp", gridbylprp);
            session.setAttribute("openclosefilterlprpLst", openclosefilterlprpLst);
            session.setAttribute("opencloseCriteria", opencloseCriteria);
            session.setAttribute("filterStkIdnListopenclose", lstfltquickpktStkIdnList);
            session.setAttribute("stkoenclosedataDtl", dataDtl);
            session.setAttribute("stkoenclosegtList", gtList);
            session.setAttribute("dspgrpList", dspgrpList);
            ArrayList quarterList =
                (info.getQuarterList() == null) ? new ArrayList() : (ArrayList)info.getQuarterList();
            udf.setQuarterList(quarterList);
            ArrayList yrList = (info.getYrList() == null) ? new ArrayList() : (ArrayList)info.getYrList();
            udf.setYrList(yrList);
            util.updAccessLog(req, res, "Ifrs Action", "loadstkoencloseData end");

            return am.findForward("load");
        }
    }

    public ActionForward loadstkopenclosereset(ActionMapping am, ActionForm af, HttpServletRequest req,
                                               HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg = "sucess";
        if (info != null) {
            Connection conn = info.getCon();
            if (conn != null) {
                db.setCon(info.getCon());
                util.setDb(db);
                util.setInfo(info);
                db.setLogApplNm(info.getLoApplNm());
                db.setLogid(String.valueOf(info.getLogId()));
                util.setLogApplNm(info.getLoApplNm());
                rtnPg = init(req, res, session, util);
            } else {
                rtnPg = "connExists";
            }
        } else
            rtnPg = "sessionTO";
        if (!rtnPg.equals("sucess")) {
            return am.findForward(rtnPg);
        } else {
            util.updAccessLog(req, res, "Ifrs Action", "loadstkoenclose start");
            ReportForm udf = (ReportForm)af;
            HashMap allPageDtl = (info.getPageDetails() == null) ? new HashMap() : (HashMap)info.getPageDetails();
            HashMap pageDtl = (HashMap)allPageDtl.get("IFRS");
            ArrayList pageList = new ArrayList();
            HashMap pageDtlMap = new HashMap();
            String fld_nme = "", fld_ttl = "", val_cond = "", lov_qry = "", dflt_val = "", fld_typ = "", form_nme =
                "", flg1 = "";
            ArrayList filterlprpLst = new ArrayList();
            ArrayList pktStkIdnListopenclose = (ArrayList)session.getAttribute("pktStkIdnListopenclose");

            session.setAttribute("opencloseCriteria", "");
            session.setAttribute("filterStkIdnListopenclose", util.useDifferentArrayList(pktStkIdnListopenclose));
            session.setAttribute("opencloseCriteria", "");
            pageList = (ArrayList)pageDtl.get("LEVEL1LPRP");
            if (pageList != null && pageList.size() > 0) {
                for (int j = 0; j < pageList.size(); j++) {
                    pageDtlMap = (HashMap)pageList.get(j);
                    fld_ttl = (String)pageDtlMap.get("fld_ttl");
                    fld_nme = (String)pageDtlMap.get("fld_nme");
                    filterlprpLst.add(fld_nme);
                }
            }
            session.setAttribute("openclosefilterlprpLst", filterlprpLst);
            util.updAccessLog(req, res, "Ifrs Action", "loadstkoenclose end");
            return loadstkopencloseData(am, af, req, res);
        }
    }

    public ActionForward pktDtlStockOpenClose(ActionMapping am, ActionForm af, HttpServletRequest req,
                                              HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg = "sucess";
        if (info != null) {
            Connection conn = info.getCon();
            if (conn != null) {
                db.setCon(info.getCon());
                util.setDb(db);
                util.setInfo(info);
                db.setLogApplNm(info.getLoApplNm());
                db.setLogid(String.valueOf(info.getLogId()));
                util.setLogApplNm(info.getLoApplNm());
                rtnPg = init(req, res, session, util);
            } else {
                rtnPg = "connExists";
            }
        } else
            rtnPg = "sessionTO";
        if (!rtnPg.equals("sucess")) {
            return am.findForward(rtnPg);
        } else {
            util.updAccessLog(req, res, "Analysis Report", "loadDtl start");
            ReportForm udf = (ReportForm)af;
            GenericInterface genericInt = new GenericImpl();
            ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "IFRS_OPEN_CLOSE", "IFRS_OPEN_CLOSE");
            ArrayList pktList = new ArrayList();
            ArrayList itemHdr = new ArrayList();
            HashMap dbinfo = info.getDmbsInfoLst();

            String dsp_stt = util.nvl((String)req.getParameter("dsp_stt"));
            String filterlprpval = util.nvl((String)req.getParameter("filterlprpval"));
            String filterlprp = util.nvl((String)req.getParameter("filterlprp"));
            ArrayList filterStkIdnListopenclose = (ArrayList)session.getAttribute("filterStkIdnListopenclose");
            HashMap pktListMapopenclose = (HashMap)session.getAttribute("pktListMapopenclose");
            int filterStkIdnListopenclosesz = filterStkIdnListopenclose.size();
            HashMap pktPrpMap = new HashMap();
            itemHdr.add("Sr No");
            itemHdr.add("stk_idn");
            itemHdr.add("Status");
            itemHdr.add("Byr");
            itemHdr.add("Byr Country");
            itemHdr.add("Sale Ex");
            itemHdr.add("Packet Code");
            int sr = 1;
            for (int i = 0; i < vwPrpLst.size(); i++) {
                String prp = util.nvl((String)vwPrpLst.get(i));
                itemHdr.add(prp);
            }

            for (int i = 0; i < filterStkIdnListopenclosesz; i++) {
                String stkIdn = (String)filterStkIdnListopenclose.get(i);
                boolean vldpkt = false;
                pktPrpMap = new HashMap();
                pktPrpMap = (HashMap)pktListMapopenclose.get(stkIdn);
                String lprpval = util.nvl((String)pktPrpMap.get(filterlprp));
                String dsp_sttMap = util.nvl((String)pktPrpMap.get("dsp_stt"));
                String sttMap = util.nvl((String)pktPrpMap.get("stt"));
                if ((filterlprpval.equals("ALL") || filterlprpval.equals(lprpval)) &&
                    (dsp_sttMap.equals(dsp_stt) || sttMap.equals(dsp_stt))) {
                    vldpkt = true;
                }
                if (vldpkt) {
                    pktPrpMap.put("Sr No", String.valueOf(sr++));
                    pktPrpMap.put("Status", dsp_stt);
                    pktList.add(pktPrpMap);
                }
            }
            int pktListsz = pktList.size();
            String zipallowyn = util.nvl((String)dbinfo.get("ZIP_DWD_YN"), "N");
            int zipdwnsz = Integer.parseInt(util.nvl((String)dbinfo.get("ZIP_DWD_SZ"), "100"));
            ExcelUtil xlUtil = new ExcelUtil();
            xlUtil.init(db, util, info);
            HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
            if (zipallowyn.equals("Y") && pktListsz > zipdwnsz) {
                String contentTypezip = "application/zip";
                String fileNmzip = "ResultExcelNR" + util.getToDteTime();
                OutputStream outstm = res.getOutputStream();
                ZipOutputStream zipOut = new ZipOutputStream(outstm);
                ZipEntry entry = new ZipEntry(fileNmzip + ".xlsx");
                zipOut.putNextEntry(entry);
                res.setHeader("Content-Disposition", "attachment; filename=" + fileNmzip + ".zip");
                res.setContentType(contentTypezip);
              ByteArrayOutputStream bos = new ByteArrayOutputStream();
               hwb.write(bos);
               bos.writeTo(zipOut);      
              zipOut.closeEntry();
               zipOut.flush();
               zipOut.close();
               outstm.flush();
               outstm.close(); 
            } else {
                OutputStream out = res.getOutputStream();
                String CONTENT_TYPE = "getServletContext()/vnd-excel";
                String fileNm = "ResultExcel" + util.getToDteTime() + ".xlsx";
                res.setContentType(CONTENT_TYPE);
                res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
                hwb.write(out);
                out.flush();
                out.close();
            }
            return null;
            //           session.setAttribute("pktList", pktList);
            //           session.setAttribute("itemHdr",itemHdr);
            //       util.updAccessLog(req,res,"Analysis Report", "loadDtl end");
            //        return am.findForward("pktDtl");
        }
    }

    public ActionForward getDataMixIfrs2Level(ActionMapping am, ActionForm af, HttpServletRequest req,
                                              HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg = "sucess";
        if (info != null) {
            Connection conn = info.getCon();
            if (conn != null) {
                db.setCon(info.getCon());
                util.setDb(db);
                util.setInfo(info);
                db.setLogApplNm(info.getLoApplNm());
                db.setLogid(String.valueOf(info.getLogId()));
                util.setLogApplNm(info.getLoApplNm());
                rtnPg = init(req, res, session, util);
            } else {
                rtnPg = "connExists";
            }
        } else
            rtnPg = "sessionTO";
        if (!rtnPg.equals("sucess")) {
            return am.findForward(rtnPg);
        } else {
            util.updAccessLog(req, res, "Analysis Report", "getDataMixIfrs2Level start");
            ReportForm udf = (ReportForm)af;
            GenericInterface genericInt = new GenericImpl();
            ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "IFRS_OPEN_CLOSE_MIX", "IFRS_OPEN_CLOSE_MIX");
            ArrayList pktList = new ArrayList();
            ArrayList itemHdr = new ArrayList();
            HashMap dbinfo = info.getDmbsInfoLst();
            String mongodb = (String)dbinfo.get("MONGODB");
            String dsp_stt = util.nvl((String)req.getParameter("dsp_stt"));
            String p2recfrmdte = util.nvl((String)session.getAttribute("p2recfrmdte"));
            String p2restodte = util.nvl((String)session.getAttribute("p2restodte"));
            String fldVal1recpt_dt = util.nvl((String)session.getAttribute("fldVal1recpt_dt"));
            String fldVal2recpt_dt = util.nvl((String)session.getAttribute("fldVal2recpt_dt"));
            String dept = util.nvl((String)req.getParameter("dept"));
            HashMap dtls = new HashMap();
            HashMap prp = info.getPrp();
            ArrayList lprpS = (ArrayList)prp.get("DEPTS");
            ArrayList lprpV = (ArrayList)prp.get("DEPTV");
            String srtVal = "";
            if (!dept.equals("ALL"))
                srtVal = (String)lprpS.get(lprpV.indexOf(dept));
            int sr = 1;
            dtls = new HashMap();
            dtls.put("mdl", "IFRS_OPEN_CLOSE_MIX");
            dtls.put("recfrmdte", fldVal1recpt_dt);
            dtls.put("restodte", fldVal2recpt_dt);
            dtls.put("p2recfrmdte", p2recfrmdte);
            dtls.put("p2restodte", p2restodte);
            if (!dept.equals("ALL"))
                dtls.put("prpVal", srtVal);
            HashMap summaryMixMap = getDataNewSOld2level(req, res, dtls);
            session.setAttribute("summaryMix2LevelMap", summaryMixMap);
            req.setAttribute("dsp_stt", dsp_stt);
            util.updAccessLog(req, res, "Analysis Report", "getDataMixIfrs2Level end");
            return am.findForward("load2Level");
        }
    }

    public HashMap getDataNewSOld2level(HttpServletRequest req, HttpServletResponse res,
                                        HashMap dtls) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm());
        db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HashMap dbinfo = info.getDmbsInfoLst();
        String mongodb = util.nvl((String)dbinfo.get("MONGODB"));
        String rep_path = (String)dbinfo.get("REP_PATH");
        GenericInterface genericInt = new GenericImpl();
        String mdl = util.nvl((String)dtls.get("mdl"));
        String resfrmdte = util.nvl((String)dtls.get("recfrmdte"));
        String restodte = util.nvl((String)dtls.get("restodte"));
        String p2recfrmdte = util.nvl((String)dtls.get("p2recfrmdte"));
        String p2restodte = util.nvl((String)dtls.get("p2restodte"));
        String prpVal = util.nvl((String)dtls.get("prpVal"));
        long startm = new Date().getTime();
        genericInt.genericPrprVw(req, res, mdl, mdl);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HashMap pktListMap = new HashMap();
        HttpPost postRequest = new HttpPost("http://ifrs1.hk.co/ifrsservice7/REST/WebService/getDataMixIfrs2Level");
        postRequest.setHeader("Accept", "application/json");
        postRequest.setHeader("Content-type", "application/json");
        JSONObject jObj = new JSONObject();
        try {
            jObj.put("recfrmdte", resfrmdte);
            jObj.put("restodte", restodte);
            jObj.put("p2recfrmdte", p2recfrmdte);
            jObj.put("p2restodte", p2restodte);
            jObj.put("mdl", mdl);
            jObj.put("clientName", mongodb);
            if (!prpVal.equals(""))
                jObj.put("prpVal", prpVal);
            //            jObj.put("recfrmdte", "01-10-2016");
            //            jObj.put("restodte", "31-12-2016");
            //            jObj.put("p2recfrmdte", "01-01-2017");
            //            jObj.put("p2restodte", "15-01-2017");
            //            jObj.put("mdl", mdl);
            //            jObj.put("clientName", mongodb);
        } catch (JSONException jsone) {
            jsone.printStackTrace();
        }

        StringEntity insetValue = new StringEntity(jObj.toString());
        insetValue.setContentType(MediaType.APPLICATION_JSON);
        postRequest.setEntity(insetValue);

        HttpResponse responsejson = httpClient.execute(postRequest);

        if (responsejson.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + responsejson.getStatusLine().getStatusCode());
        }


        BufferedReader br = new BufferedReader(new InputStreamReader((responsejson.getEntity().getContent())));
        String outsetValue = "";
        String jsonStr = "";
        //System.out.println("OutsetValue from Server .... \n"+br.readLine());
        while ((outsetValue = br.readLine()) != null) {
            //    System.out.println(outsetValue);
            jsonStr = jsonStr + outsetValue;
        }
        //    System.out.println(jsonStr);
        httpClient.getConnectionManager().shutdown();
        if (!jsonStr.equals("")) {
            JSONObject jObject = new JSONObject(jsonStr);
            JSONObject jSupportObject = (JSONObject)jObject.get("RESULT");
            if (jSupportObject != null) {
                pktListMap = jsonToMap((JSONObject)jSupportObject.get("DATA"));
            }
        }
        long endm = new Date().getTime();
        System.out.println("@ Collection on Table Total Time = " + ((endm - startm) / 1000));
        return pktListMap;
    }

    public ActionForward pktDtlMix(ActionMapping am, ActionForm af, HttpServletRequest req,
                                   HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg = "sucess";
        if (info != null) {
            Connection conn = info.getCon();
            if (conn != null) {
                db.setCon(info.getCon());
                util.setDb(db);
                util.setInfo(info);
                db.setLogApplNm(info.getLoApplNm());
                db.setLogid(String.valueOf(info.getLogId()));
                util.setLogApplNm(info.getLoApplNm());
                rtnPg = init(req, res, session, util);
            } else {
                rtnPg = "connExists";
            }
        } else
            rtnPg = "sessionTO";
        if (!rtnPg.equals("sucess")) {
            return am.findForward(rtnPg);
        } else {
            util.updAccessLog(req, res, "Analysis Report", "loadDtl start");
            ReportForm udf = (ReportForm)af;
            GenericInterface genericInt = new GenericImpl();
            ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "IFRS_OPEN_CLOSE_MIX", "IFRS_OPEN_CLOSE_MIX");
            ArrayList pktList = new ArrayList();
            ArrayList itemHdr = new ArrayList();
            HashMap dbinfo = info.getDmbsInfoLst();
            String mongodb = (String)dbinfo.get("MONGODB");
            String dsp_stt = util.nvl((String)req.getParameter("dsp_stt"));
            String fldVal1recpt_dt = util.nvl((String)session.getAttribute("fldVal1recpt_dt"));
            String fldVal2recpt_dt = util.nvl((String)session.getAttribute("fldVal2recpt_dt"));
            String dept = util.nvl((String)req.getParameter("dept"));
            HashMap dtls = new HashMap();
            HashMap prp = info.getPrp();
            ArrayList lprpS = (ArrayList)prp.get("DEPTS");
            ArrayList lprpV = (ArrayList)prp.get("DEPTV");
            String srtVal = "";
            if (!dept.equals("ALL"))
                srtVal = (String)lprpS.get(lprpV.indexOf(dept));
            int sr = 1;
            if (dsp_stt.equals("NEW")) {
                if (dept.equals("ALL")) {
                    dtls.put("prpTyp", "Y#M#M#M");
                    dtls.put("prp", "RECPT_DT#PUR#IFRS_YN#DEPT");
                    dtls.put("prpVal",
                             fldVal1recpt_dt + "@" + fldVal2recpt_dt + "#" + "10@30" + "#" + "10@20#12@20@22@30@70");
                } else {
                    dtls.put("prpTyp", "Y#M#M#M");
                    dtls.put("prp", "RECPT_DT#DEPT#PUR#IFRS_YN");
                    dtls.put("prpVal",
                             fldVal1recpt_dt + "@" + fldVal2recpt_dt + "#" + srtVal + "#" + "10@30" + "#" + "10@20");
                }
                dtls.put("mdl", "IFRS_OPEN_CLOSE_MIX");
                dtls.put("pkt_ty", "SMX,MIX");
                dtls.put("tblNme", "AS_MRG_TBL");
                dtls.put("stt", "");
                dtls.put("clientName", mongodb);
                ArrayList genericPacketLst = new ArrayList();
                genericPacketLst = getPacketsFromMongoMix(req, res, dtls);
                for (int i = 0; i < genericPacketLst.size(); i++) {
                    HashMap pktPrpMap = new HashMap();
                    pktPrpMap = (HashMap)genericPacketLst.get(i);
                    pktPrpMap.put("Sr No", String.valueOf(sr++));
                    pktPrpMap.put("Status", dsp_stt);
                    pktList.add(pktPrpMap);
                }

                dtls.put("tblNme", "OTHER_TBL");
                dtls.put("stt", "AS_FN_MRG,AS_SMX,PU_MRG,DEPT_MRG,SNGL_MRG");
                genericPacketLst = new ArrayList();
                genericPacketLst = getPacketsFromMongoMix(req, res, dtls);
                for (int i = 0; i < genericPacketLst.size(); i++) {
                    HashMap pktPrpMap = new HashMap();
                    pktPrpMap = (HashMap)genericPacketLst.get(i);
                    pktPrpMap.put("Sr No", String.valueOf(sr++));
                    pktPrpMap.put("Status", dsp_stt);
                    pktList.add(pktPrpMap);
                }
            } else if (dsp_stt.equals("PURNEW")) {
                if (dept.equals("ALL")) {
                    dtls.put("prpTyp", "Y#M#M#M");
                    dtls.put("prp", "RECPT_DT#PUR#IFRS_YN#DEPT");
                    dtls.put("prpVal",
                             fldVal1recpt_dt + "@" + fldVal2recpt_dt + "#" + "20" + "#" + "10@20#12@20@22@30@70");
                } else {
                    dtls.put("prpTyp", "Y#M#M#M");
                    dtls.put("prp", "RECPT_DT#DEPT#PUR#IFRS_YN");
                    dtls.put("prpVal",
                             fldVal1recpt_dt + "@" + fldVal2recpt_dt + "#" + srtVal + "#" + "20" + "#" + "10@20");
                }
                dtls.put("mdl", "IFRS_OPEN_CLOSE_MIX");
                dtls.put("pkt_ty", "SMX,MIX");
                dtls.put("tblNme", "AS_MRG_TBL");
                dtls.put("stt", "AS_MRG");
                dtls.put("clientName", mongodb);
                ArrayList genericPacketLst = new ArrayList();
                genericPacketLst = getPacketsFromMongoMix(req, res, dtls);
                for (int i = 0; i < genericPacketLst.size(); i++) {
                    HashMap pktPrpMap = new HashMap();
                    pktPrpMap = (HashMap)genericPacketLst.get(i);
                    pktPrpMap.put("Sr No", String.valueOf(sr++));
                    pktPrpMap.put("Status", dsp_stt);
                    pktList.add(pktPrpMap);
                }

                dtls.put("tblNme", "OTHER_TBL");
                dtls.put("stt", "AS_FN_MRG,AS_SMX,PU_MRG,DEPT_MRG,SNGL_MRG");
                genericPacketLst = new ArrayList();
                genericPacketLst = getPacketsFromMongoMix(req, res, dtls);
                for (int i = 0; i < genericPacketLst.size(); i++) {
                    HashMap pktPrpMap = new HashMap();
                    pktPrpMap = (HashMap)genericPacketLst.get(i);
                    pktPrpMap.put("Sr No", String.valueOf(sr++));
                    pktPrpMap.put("Status", dsp_stt);
                    pktList.add(pktPrpMap);
                }
            } else if (dsp_stt.equals("DEPTIN")) {
                if (dept.equals("ALL")) {
                    dtls.put("prpTyp", "Y#M#M#M");
                    dtls.put("prp", "RECPT_DT#PUR#IFRS_YN#DEPT");
                    dtls.put("prpVal",
                             fldVal1recpt_dt + "@" + fldVal2recpt_dt + "#" + "40" + "#" + "10@20#12@20@22@30@70");
                } else {
                    dtls.put("prpTyp", "Y#M#M#M");
                    dtls.put("prp", "RECPT_DT#DEPT#PUR#IFRS_YN");
                    dtls.put("prpVal",
                             fldVal1recpt_dt + "@" + fldVal2recpt_dt + "#" + srtVal + "#" + "40" + "#" + "10@20");
                }
                dtls.put("mdl", "IFRS_OPEN_CLOSE_MIX");
                dtls.put("pkt_ty", "SMX,MIX,NR");
                dtls.put("clientName", mongodb);
                ArrayList genericPacketLst = new ArrayList();
                dtls.put("tblNme", "OTHER_TBL");
                dtls.put("stt", "AS_FN_MRG,AS_SMX,PU_MRG,DEPT_MRG,SNGL_MRG");
                genericPacketLst = new ArrayList();
                genericPacketLst = getPacketsFromMongoMix(req, res, dtls);
                for (int i = 0; i < genericPacketLst.size(); i++) {
                    HashMap pktPrpMap = new HashMap();
                    pktPrpMap = (HashMap)genericPacketLst.get(i);
                    pktPrpMap.put("Sr No", String.valueOf(sr++));
                    pktPrpMap.put("Status", dsp_stt);
                    pktList.add(pktPrpMap);
                }
            } else if (dsp_stt.equals("SOLD")) {
                if (dept.equals("ALL")) {
                    dtls.put("prpTyp", "Y#M#M");
                    dtls.put("prp", "ACC_INV_DTE#SL_PKT_TYP#DEPT");
                    dtls.put("prpVal", fldVal1recpt_dt + "@" + fldVal2recpt_dt + "#10@30@40#12@20@22@30@70");
                } else {
                    dtls.put("prpTyp", "Y#M#M");
                    dtls.put("prp", "ACC_INV_DTE#DEPT#SL_PKT_TYP");
                    dtls.put("prpVal", fldVal1recpt_dt + "@" + fldVal2recpt_dt + "#" + srtVal + "#10@30@40");
                }
                dtls.put("mdl", "IFRS_OPEN_CLOSE_MIX");
                dtls.put("pkt_ty", "SMX,MIX");
                dtls.put("tblNme", "MIX_SOLD");
                dtls.put("clientName", mongodb);
            }

            if (dsp_stt.equals("SOLD")) {
                ArrayList genericPacketLst = new ArrayList();
                genericPacketLst = getPacketsFromMongoMix(req, res, dtls);
                for (int i = 0; i < genericPacketLst.size(); i++) {
                    HashMap pktPrpMap = new HashMap();
                    pktPrpMap = (HashMap)genericPacketLst.get(i);
                    pktPrpMap.put("Sr No", String.valueOf(sr++));
                    pktPrpMap.put("Status", dsp_stt);
                    pktList.add(pktPrpMap);
                }
            }
            itemHdr.add("Sr No");
            itemHdr.add("stk_idn");
            itemHdr.add("Status");
            itemHdr.add("Byr");
            itemHdr.add("Byr Country");
            itemHdr.add("Sale Ex");
            itemHdr.add("Packet Code");
            itemHdr.add("Sale Idn");
            for (int i = 0; i < vwPrpLst.size(); i++) {
                String mprp = util.nvl((String)vwPrpLst.get(i));
                itemHdr.add(mprp);
            }

            int pktListsz = pktList.size();
            String zipallowyn = util.nvl((String)dbinfo.get("ZIP_DWD_YN"), "N");
            int zipdwnsz = Integer.parseInt(util.nvl((String)dbinfo.get("ZIP_DWD_SZ"), "100"));
            ExcelUtil xlUtil = new ExcelUtil();
            xlUtil.init(db, util, info);
            HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
            if (zipallowyn.equals("Y") && pktListsz > zipdwnsz) {
                String contentTypezip = "application/zip";
                String fileNmzip = "ResultExcelMIX" + util.getToDteTime();
                OutputStream outstm = res.getOutputStream();
                ZipOutputStream zipOut = new ZipOutputStream(outstm);
                ZipEntry entry = new ZipEntry(fileNmzip + ".xlsx");
                zipOut.putNextEntry(entry);
                res.setHeader("Content-Disposition", "attachment; filename=" + fileNmzip + ".zip");
                res.setContentType(contentTypezip);
              ByteArrayOutputStream bos = new ByteArrayOutputStream();
               hwb.write(bos);
               bos.writeTo(zipOut);      
              zipOut.closeEntry();
               zipOut.flush();
               zipOut.close();
               outstm.flush();
               outstm.close(); 
            } else {
                OutputStream out = res.getOutputStream();
                String CONTENT_TYPE = "getServletContext()/vnd-excel";
                String fileNm = "ResultExcel" + util.getToDteTime() + ".xlsx";
                res.setContentType(CONTENT_TYPE);
                res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
                hwb.write(out);
                out.flush();
                out.close();
            }
            return null;
            //           session.setAttribute("pktList", pktList);
            //           session.setAttribute("itemHdr",itemHdr);
            //       util.updAccessLog(req,res,"Analysis Report", "loadDtl end");
            //        return am.findForward("pktDtl");
        }
    }

    public HashMap getPacketsFromMongo(HttpServletRequest req, HttpServletResponse res,
                                       HashMap dtls) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm());
        db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HashMap dbinfo = info.getDmbsInfoLst();
        String mongodb = util.nvl((String)dbinfo.get("MONGODB"));
        String rep_path = (String)dbinfo.get("REP_PATH");
        GenericInterface genericInt = new GenericImpl();
        String lprpTyp = util.nvl((String)dtls.get("prpTyp"));
        String lprpStr = util.nvl((String)dtls.get("prp"));
        String lprpVal = util.nvl((String)dtls.get("prpVal"));
        String sttStr = util.nvl((String)dtls.get("stt"));
        String mdl = util.nvl((String)dtls.get("mdl"));
        String tblNme = util.nvl((String)dtls.get("tblNme"));
        String pkt_ty = util.nvl((String)dtls.get("pkt_ty"));
        String avgfixedprp = util.nvl((String)dtls.get("avgfixedprp"));
        String fixedprp = util.nvl((String)dtls.get("fixedprp"));
        String fldVal1recpt_dt = util.nvl((String)dtls.get("fldVal1recpt_dt"));
        String fldVal2recpt_dt = util.nvl((String)dtls.get("fldVal2recpt_dt"));
        String condition = util.nvl((String)dtls.get("condition"));
        long startm = new Date().getTime();
        System.out.println(lprpTyp);
        System.out.println(lprpStr);
        System.out.println(lprpVal);
        ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, mdl, mdl);
        HashMap mprp = info.getMprp();
        HashMap prp = info.getPrp();
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HashMap pktListMap = new HashMap();
        HttpPost postRequest = new HttpPost("http://ifrs1.hk.co/diaflexWebService/REST/WebService/searchValues");
        postRequest.setHeader("Accept", "application/json");
        postRequest.setHeader("Content-type", "application/json");
        JSONObject jObj = new JSONObject();
        try {
            jObj.put("prpTyp", lprpTyp);
            jObj.put("prp", lprpStr);
            jObj.put("prpVal", lprpVal);
            jObj.put("stt", sttStr);
            jObj.put("mdl", mdl);
            jObj.put("pkt_ty", pkt_ty);
            jObj.put("tblNme", tblNme);
            jObj.put("clientName", mongodb);
        } catch (JSONException jsone) {
            jsone.printStackTrace();
        }

        StringEntity insetValue = new StringEntity(jObj.toString());
        insetValue.setContentType(MediaType.APPLICATION_JSON);
        postRequest.setEntity(insetValue);

        HttpResponse responsejson = httpClient.execute(postRequest);

        if (responsejson.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + responsejson.getStatusLine().getStatusCode());
        }


        BufferedReader br = new BufferedReader(new InputStreamReader((responsejson.getEntity().getContent())));
        String outsetValue = "";
        String jsonStr = "";
        //System.out.println("OutsetValue from Server .... \n"+br.readLine());
        while ((outsetValue = br.readLine()) != null) {
            //    System.out.println(outsetValue);
            jsonStr = jsonStr + outsetValue;
        }
        //    System.out.println(jsonStr);
        httpClient.getConnectionManager().shutdown();
        ArrayList pktList = new ArrayList();
        ArrayList lprpPurS = (ArrayList)prp.get("PURS");
        ArrayList lprpPurV = (ArrayList)prp.get("PURV");
        ArrayList lprpMixToSingleS = (ArrayList)prp.get("MIX_SNGLS");
        ArrayList lprpMixToSingleV = (ArrayList)prp.get("MIX_SNGLV");
        int count = 0;
        if (!jsonStr.equals("")) {
            JSONObject jObject = new JSONObject(jsonStr);
            JSONArray dataObject = (JSONArray)jObject.get("STKDTL");
            if (dataObject != null) {
                for (int i = 0; i < dataObject.length(); i++) {
                    try {
                        count++;
                        JSONObject pktDtl = dataObject.getJSONObject(i);
                        String stkIdn = util.nvl((String)pktDtl.get("STK_IDN"));
                        String stt = util.nvl((String)pktDtl.get("STATUS"));
                        String dsp_stt = util.nvl((String)pktDtl.get("GRP1"));
                        String cts = util.nvl((String)pktDtl.get("CRTWT"), "0.01");
                        String pur = util.nvl((String)pktDtl.get("PUR"), "NA");
                        if (pur != "") {
                            Double obj1 = new Double(pur);
                            int prpSrt1 = obj1.intValue();
                            String prepSrtVal1 = String.valueOf(prpSrt1);
                            try {
                                pur = (String)lprpPurV.get(lprpPurS.indexOf(prepSrtVal1));
                            } catch (ArrayIndexOutOfBoundsException a) {
                                pur = "";
                            }
                        }
                        String mix_sngl = "N";
                        try {
                            String copymix_sngl = util.nvl((String)pktDtl.get("MIX_SNGL"));
                            if (copymix_sngl != "") {
                                Double obj1 = new Double(copymix_sngl);
                                int prpSrt1 = obj1.intValue();
                                String prepSrtVal1 = String.valueOf(prpSrt1);
                                try {
                                    copymix_sngl = (String)lprpMixToSingleV.get(lprpMixToSingleS.indexOf(prepSrtVal1));
                                } catch (ArrayIndexOutOfBoundsException a) {
                                    copymix_sngl = "N";
                                }
                                mix_sngl = copymix_sngl;
                            }
                        } catch (JSONException js) {
                        }
                        String rap_rte = util.nvl((String)pktDtl.get("RAP_RTE"));
                        String rte = util.nvl((String)pktDtl.get(avgfixedprp), "0");
                        String cp = util.nvl((String)pktDtl.get(fixedprp), "0");
                        if (dsp_stt.equals("SOLD") || dsp_stt.equals("DLV")) {
                            String sal_rte = util.nvl((String)pktDtl.get("ACC_PER_FE"), "0");
                            if (!sal_rte.equals("0"))
                                rte = sal_rte;
                        }

                        double amt = util.roundToDecimals(Double.parseDouble(cts) * Double.parseDouble(rte), 2);
                        double rapVal = util.roundToDecimals(Double.parseDouble(cts) * Double.parseDouble(rap_rte), 2);

                        double cpamt = util.roundToDecimals(Double.parseDouble(cts) * Double.parseDouble(cp), 2);

                        HashMap pktPrpMap = new HashMap();
                        pktPrpMap.put("stt", stt);
                        pktPrpMap.put("cts", cts);
                        pktPrpMap.put("sr", String.valueOf(count));
                        String vnm = util.nvl((String)pktDtl.get("PACKETCODE"));
                        pktPrpMap.put("Packet Code", vnm);
                        pktPrpMap.put("stk_idn", stkIdn);
                        pktPrpMap.put("dsp_stt", dsp_stt);
                        pktPrpMap.put("Byr", util.nvl((String)pktDtl.get("SAL_BYR")));
                        pktPrpMap.put("Sale Ex", util.nvl((String)pktDtl.get("SAL_EMP")));
                        pktPrpMap.put("qty", util.nvl((String)pktDtl.get("QTY")));
                        pktPrpMap.put("rap_rte", util.nvl((String)pktDtl.get("RAP_RTE")));
                        pktPrpMap.put("quot", rte);
                        pktPrpMap.put("amt", String.valueOf(amt));
                        pktPrpMap.put("rap_val", String.valueOf(rapVal));
                        pktPrpMap.put("sk1", new BigDecimal(util.nvl((String)pktDtl.get("SK1"), "0")));
                        pktPrpMap.put("condition", condition);
                        String dlv_dte = "";
                        try {
                            dlv_dte = util.nvl((String)pktDtl.get("ACC_INV_DTE"));
                        } catch (JSONException js) {
                        }
                        for (int j = 0; j < vwPrpLst.size(); j++) {
                            try {
                                String lprp = (String)vwPrpLst.get(j);
                                String typ = util.nvl((String)mprp.get(lprp + "T"));
                                String prpVal = "";
                                String fldVal = util.nvl((String)pktDtl.get(lprp));
                                if (typ.equals("C")) {
                                    ArrayList lprpS = (ArrayList)prp.get(lprp + "S");
                                    ArrayList lprpV = (ArrayList)prp.get(lprp + "V");
                                    if (fldVal != "") {
                                        Double obj = new Double(fldVal);
                                        int prpSrt = obj.intValue();
                                        String prepSrtVal = String.valueOf(prpSrt);
                                        try {
                                            prpVal = (String)lprpV.get(lprpS.indexOf(prepSrtVal));
                                        } catch (ArrayIndexOutOfBoundsException a) {
                                            prpVal = "";
                                        }
                                    }
                                } else if (typ.equals("D") && !fldVal.equals("") && lprp.equals("RECPT_DT") &&
                                           condition.equals("N")) {
                                    double pktdte = new Double(util.toconvertStringtoDate(fldVal));
                                    if ((dsp_stt.equals("SOLD") || dsp_stt.equals("DLV") && stt.equals("MKSD"))) {
                                        if (!dlv_dte.equals("") && !dlv_dte.equals("0")) {
                                            double saldte = new Double(util.toconvertStringtoDate(dlv_dte));
                                            if (saldte <= Double.parseDouble(fldVal2recpt_dt))
                                                pktPrpMap.put("stt", "SOLD");
                                            else
                                                pktPrpMap.put("stt", "CLOSE");
                                        } else
                                            pktPrpMap.put("stt", "CLOSE");
                                    } else
                                        pktPrpMap.put("stt", "CLOSE");
                                    if (pktdte < Double.parseDouble(fldVal1recpt_dt))
                                        pktPrpMap.put("dsp_stt", "OPEN");
                                    if (pktdte >= Double.parseDouble(fldVal1recpt_dt) &&
                                        pktdte <= Double.parseDouble(fldVal2recpt_dt)) {
                                        if (pur.equals("YES") && !mix_sngl.equals("Y"))
                                            pktPrpMap.put("dsp_stt", "PURNEW");
                                        else if (!mix_sngl.equals("Y") && !pur.equals("YES"))
                                            pktPrpMap.put("dsp_stt", "MFGNEW");
                                        else
                                            pktPrpMap.put("dsp_stt", "MIXTOSNGL");
                                    }
                                    prpVal = fldVal;
                                } else {
                                    if (lprp.equals("CP"))
                                        prpVal = util.roundToDecimals2(util.nvl(fldVal, "0"), 2);
                                    else
                                        prpVal = fldVal;
                                }
                                pktPrpMap.put(lprp, prpVal);
                            } catch (JSONException js) {

                            }
                        }
                        pktPrpMap.put("RTE", rte);
                        pktPrpMap.put("cp", cp);
                        pktPrpMap.put("cpamt", cpamt);
                        pktListMap.put(stkIdn, pktPrpMap);
                    } catch (NumberFormatException js) {

                    }
                }
            }
        }
        long endm = new Date().getTime();
        System.out.println("@ Collection on Table " + tblNme + " Total Time = " + ((endm - startm) / 1000));
        return pktListMap;
    }


    public ArrayList getPacketsFromMongoMix(HttpServletRequest req, HttpServletResponse res,
                                            HashMap dtls) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm());
        db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HashMap dbinfo = info.getDmbsInfoLst();
        String mongodb = util.nvl((String)dbinfo.get("MONGODB"));
        String rep_path = (String)dbinfo.get("REP_PATH");
        GenericInterface genericInt = new GenericImpl();
        String lprpTyp = util.nvl((String)dtls.get("prpTyp"));
        String lprpStr = util.nvl((String)dtls.get("prp"));
        String lprpVal = util.nvl((String)dtls.get("prpVal"));
        String sttStr = util.nvl((String)dtls.get("stt"));
        String mdl = util.nvl((String)dtls.get("mdl"));
        String tblNme = util.nvl((String)dtls.get("tblNme"));
        String pkt_ty = util.nvl((String)dtls.get("pkt_ty"));
        long startm = new Date().getTime();
        System.out.println(lprpTyp);
        System.out.println(lprpStr);
        System.out.println(lprpVal);
        ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, mdl, mdl);
        HashMap mprp = info.getMprp();
        HashMap prp = info.getPrp();
        DefaultHttpClient httpClient = new DefaultHttpClient();
        ArrayList pktList = new ArrayList();
        HttpPost postRequest = new HttpPost("http://ifrs1.hk.co/ifrsservice7/REST/WebService/searchValuesMix");
        postRequest.setHeader("Accept", "application/json");
        postRequest.setHeader("Content-type", "application/json");
        JSONObject jObj = new JSONObject();
        try {
            jObj.put("prpTyp", lprpTyp);
            jObj.put("prp", lprpStr);
            jObj.put("prpVal", lprpVal);
            jObj.put("stt", sttStr);
            jObj.put("mdl", mdl);
            jObj.put("pkt_ty", pkt_ty);
            jObj.put("tblNme", tblNme);
            jObj.put("clientName", mongodb);
        } catch (JSONException jsone) {
            jsone.printStackTrace();
        }
      System.out.print(jObj.toString());
        StringEntity insetValue = new StringEntity(jObj.toString());
        insetValue.setContentType(MediaType.APPLICATION_JSON);
        postRequest.setEntity(insetValue);

        HttpResponse responsejson = httpClient.execute(postRequest);

        if (responsejson.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + responsejson.getStatusLine().getStatusCode());
        }


        BufferedReader br = new BufferedReader(new InputStreamReader((responsejson.getEntity().getContent())));
        String outsetValue = "";
        String jsonStr = "";
        //System.out.println("OutsetValue from Server .... \n"+br.readLine());
        while ((outsetValue = br.readLine()) != null) {
            //    System.out.println(outsetValue);
            jsonStr = jsonStr + outsetValue;
        }
        //    System.out.println(jsonStr);
        httpClient.getConnectionManager().shutdown();

        int count = 0;
        if (!jsonStr.equals("")) {
            JSONObject jObject = new JSONObject(jsonStr);
            JSONArray dataObject = (JSONArray)jObject.get("STKDTL");
            if (dataObject != null) {
                for (int i = 0; i < dataObject.length(); i++) {
                    try {
                        count++;
                        JSONObject pktDtl = dataObject.getJSONObject(i);
                        String stkIdn = util.nvl((String)pktDtl.get("STK_IDN"));
                        String stt = util.nvl((String)pktDtl.get("STATUS"));
                        String dsp_stt = util.nvl((String)pktDtl.get("GRP1"));
                        String cts = util.nvl((String)pktDtl.get("CRTWT"), "0.01");
                        String rap_rte = util.nvl((String)pktDtl.get("RAP_RTE"));
                        String rte = util.nvl((String)pktDtl.get("RTE"), "0");
                        String cp = util.nvl((String)pktDtl.get("CP"), "0");
                        if (rte.indexOf("Undefined") > -1)
                            rte = cp;
                        if (cp.indexOf("Undefined") > -1)
                            cp = rte;
                        if (cp.indexOf("Undefined") > -1 || rte.indexOf("Undefined") > -1) {
                            rte = "1";
                            cp = "1";
                        }
                        if (dsp_stt.equals("SOLD") || dsp_stt.equals("DLV")) {
                            String sal_rte = util.nvl((String)pktDtl.get("ACC_PER_FE"), "0");
                            if (!sal_rte.equals("0"))
                                rte = sal_rte;
                        }


                        double amt = util.roundToDecimals(Double.parseDouble(cts) * Double.parseDouble(rte), 2);
                        double rapVal = util.roundToDecimals(Double.parseDouble(cts) * Double.parseDouble(rap_rte), 2);

                        double cpamt = util.roundToDecimals(Double.parseDouble(cts) * Double.parseDouble(cp), 2);

                        HashMap pktPrpMap = new HashMap();
                        pktPrpMap.put("stt", stt);
                        pktPrpMap.put("cts", cts);
                        pktPrpMap.put("sr", String.valueOf(count));
                        String vnm = util.nvl((String)pktDtl.get("PACKETCODE"));
                        pktPrpMap.put("Packet Code", vnm);
                        pktPrpMap.put("stk_idn", stkIdn);
                        pktPrpMap.put("dsp_stt", dsp_stt);
                        pktPrpMap.put("Sale Idn", util.nvl((String)pktDtl.get("SAL_IDN")));
                        pktPrpMap.put("Byr", util.nvl((String)pktDtl.get("SAL_BYR")));
                        pktPrpMap.put("Sale Ex", util.nvl((String)pktDtl.get("SAL_EMP")));
                        pktPrpMap.put("qty", util.nvl((String)pktDtl.get("QTY")));
                        pktPrpMap.put("rap_rte", util.nvl((String)pktDtl.get("RAP_RTE")));
                        pktPrpMap.put("quot", rte);
                        pktPrpMap.put("amt", String.valueOf(amt));
                        pktPrpMap.put("rap_val", String.valueOf(rapVal));
                        pktPrpMap.put("sk1", new BigDecimal(util.nvl((String)pktDtl.get("SK1"), "0")));
                        for (int j = 0; j < vwPrpLst.size(); j++) {
                            try {
                                String lprp = (String)vwPrpLst.get(j);
                                String typ = util.nvl((String)mprp.get(lprp + "T"));
                                String prpVal = "";
                                String fldVal = util.nvl((String)pktDtl.get(lprp));
                                if (typ.equals("C")) {
                                    ArrayList lprpS = (ArrayList)prp.get(lprp + "S");
                                    ArrayList lprpV = (ArrayList)prp.get(lprp + "V");
                                    if (fldVal != "") {
                                        Double obj = new Double(fldVal);
                                        int prpSrt = obj.intValue();
                                        String prepSrtVal = String.valueOf(prpSrt);
                                        try {
                                            prpVal = (String)lprpV.get(lprpS.indexOf(prepSrtVal));
                                        } catch (ArrayIndexOutOfBoundsException a) {
                                            prpVal = "";
                                        }
                                    }
                                } else {
                                    if (lprp.equals("CP"))
                                        prpVal = util.roundToDecimals2(util.nvl(fldVal, "0"), 2);
                                    else
                                        prpVal = fldVal;
                                }
                                pktPrpMap.put(lprp, prpVal);
                            } catch (JSONException js) {

                            }
                        }
                        pktPrpMap.put("RTE", rte);
                        pktPrpMap.put("cp", cp);
                        pktPrpMap.put("cpamt", cpamt);
                        pktList.add(pktPrpMap);
                    } catch (NumberFormatException js) {

                    }
                }
            }
        }
        long endm = new Date().getTime();
        System.out.println("@ Collection on Table " + tblNme + " Total Time = " + ((endm - startm) / 1000));
        return pktList;
    }

    public Map<String, HashMap> sortByComparatorMap(Map<String, HashMap> unsortMap, final String sortby) {

        // Convert Map to List
        List<Map.Entry<String, HashMap>> list = new LinkedList<Map.Entry<String, HashMap>>(unsortMap.entrySet());

        // Sort list with comparator, to compare the Map values
        Collections.sort(list, new Comparator<Map.Entry<String, HashMap>>() {
                public int compare(Map.Entry<String, HashMap> o1, Map.Entry<String, HashMap> o2) {
                    return ((Comparable)((Map.Entry<String, HashMap>)(o1)).getValue().get(sortby)).compareTo(((Map.Entry<String, HashMap>)(o2)).getValue().get(sortby));
                }
            });

        // Convert sorted map back to a Map
        Map<String, HashMap> sortedMap = new LinkedHashMap<String, HashMap>();
        for (Iterator<Map.Entry<String, HashMap>> it = list.iterator(); it.hasNext(); ) {
            Map.Entry<String, HashMap> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;

    }

    public HashMap jsonToMap(JSONObject jObject) throws JSONException {
        HashMap<String, Double> map = new HashMap<String, Double>();
        Iterator<?> keys = jObject.keys();
        while (keys.hasNext()) {
            String key = (String)keys.next();
            map.put(key, jObject.getDouble(key));
        }
        System.out.println("json : " + jObject);
        System.out.println("map : " + map);
        return map;
    }

    public String init(HttpServletRequest req, HttpServletResponse res, HttpSession session, DBUtil util) {
        String rtnPg = "sucess";
        String invalide = "";
        String connExists = util.nvl(util.getConnExists());
        if (!connExists.equals("N"))
            invalide = util.nvl(util.chkTimeOut(), "N");
        if (session.isNew())
            rtnPg = "sessionTO";
        if (connExists.equals("N"))
            rtnPg = "connExists";
        if (invalide.equals("Y"))
            rtnPg = "chktimeout";
        if (rtnPg.equals("sucess")) {
            boolean sout = util.getLoginsession(req, res, session.getId());
            if (!sout) {
                rtnPg = "sessionTO";
                System.out.print("New Session Id :=" + session.getId());
            } else {
                rtnPg = util.checkUserPageRights("", util.getFullURL(req));
                if (rtnPg.equals("unauthorized"))
                    util.updAccessLog(req, res, "Ifrs Action", "Unauthorized Access");
                else
                    util.updAccessLog(req, res, "Ifrs Action", "init");
            }
        }
        return rtnPg;
    }
}
