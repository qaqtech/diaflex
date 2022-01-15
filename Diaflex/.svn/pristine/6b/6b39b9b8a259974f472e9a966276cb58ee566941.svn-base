package ft.com.box;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.lab.LabSelectionForm;

import java.sql.Connection;
import java.sql.ResultSet;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.HashMap;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.swing.Box;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class BoxSplitAction extends DispatchAction{
public ActionForward loadsp(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        util.updAccessLog(req,res,"Box Split", "loadsp start");
BoxSplitForm boxSplitForm = (BoxSplitForm)form;
GenericInterface genericInt=new GenericImpl();
boxSplitForm.resetALL();
ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LBGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LBGNCSrch"); 
info.setGncPrpLst(assortSrchList);

req.setAttribute("PageVW", "MIX");

HashMap boxTyp = new HashMap();
String getBoxtyp = "select VAL, DSC from prp where mprp = 'BOX_TYP' and dte is NULL";
ArrayList ary1 = new ArrayList();
ResultSet rs = null;
rs = db.execSql(" Fav Lst ", getBoxtyp, ary1);
while(rs.next())
{
boxTyp.put(util.nvl(rs.getString(1)),util.nvl(rs.getString(2)));
}
rs.close();
session.setAttribute("boxTyp", boxTyp);
HashMap boxnme = new HashMap();
String getBoxname = "select idn, vnm from mstk where pkt_ty in ('MIX','MX') order by vnm";
//String getBoxname = "select idn, vnm from mstk where pkt_ty in ('MIX','MX') and flg='M' and vnm is not null order by vnm";
ArrayList ary2 = new ArrayList();

ArrayList idns = new ArrayList();
ResultSet rs1 = null;
rs1 = db.execSql(" Fav Lst ", getBoxname, ary2);
while(rs1.next())
{
boxnme.put(util.nvl(rs1.getString(1)),util.nvl(rs1.getString(2)));
idns.add(rs1.getString(1));
}
rs1.close();
session.setAttribute("boxnme", boxnme);

//get all values
// HashMap htidn = new HashMap();
// ArrayList vnmlist = new ArrayList();
//
// String qry = "select vnm,qty,cts,upr from mstk where pkt_rt=";
// for(int i=0; i<idns.size(); i++)
// {
// String idn = idns.get(i).toString();
// vnmlist = new ArrayList();
// rs1 = null;
// rs1 = db.execSql(" Fav Lst ", qry+idn, new ArrayList());
// while(rs1.next()) {
// HashMap htvnm = new HashMap();
// htvnm.put("vnm", util.nvl(rs1.getString(1)));
// htvnm.put("qty", util.nvl(rs1.getString(2)));
// htvnm.put("cts", util.nvl(rs1.getString(3)));
// htvnm.put("upr", util.nvl(rs1.getString(4)));
// vnmlist.add(htvnm);
// }
// htidn.put(idn, vnmlist);
// rs1.close();
// }


session.setAttribute("idns", idns);
//session.setAttribute("htidn", htidn);

//return viewsp(am, form, req, res);
                util.updAccessLog(req,res,"Box Split", "loadsp end");
return viewsp(am, form, req, res);
    }
}

public ActionForward boxdtl(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
StringBuffer sb = new StringBuffer();
String idn = req.getParameter("idn");
ArrayList ary = new ArrayList();
String sql = " select vnm,qty,cts,upr from mstk where pkt_rt=?" ;
ary.add(idn);
ResultSet rs = db.execSql("Box pkt", sql, ary);
while(rs.next()){
sb.append("<box>");
sb.append("<vnm>"+util.nvl(rs.getString("vnm"),"0") +"</vnm>");
sb.append("<qty>"+util.nvl(rs.getString("qty"),"0") +"</qty>");
sb.append("<cts>"+util.nvl(rs.getString("cts"),"0") +"</cts>");
sb.append("<upr>"+util.nvl(rs.getString("upr"),"0") +"</upr>");
sb.append("</box>");
}
    rs.close();
res.setContentType("text/xml");
res.setHeader("Cache-Control", "no-cache");
res.getWriter().write("<boxs>"+sb.toString()+"</boxs>");
return null;
    }
}

public ActionForward viewsp(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        util.updAccessLog(req,res,"Box Split", "viewsp start");
BoxSplitForm boxSplitForm = (BoxSplitForm)form;


req.setAttribute("page","MIX");

boolean isGencSrch = false;
ArrayList stockList = new ArrayList();

// String view =util.nvl(boxSplitForm.getView());
// String vnm = util.nvl((String)boxSplitForm.getValue("vnmLst"));

// stockList = boxSplitInt.FetchResult(req,res , boxSplitForm );
req.setAttribute("view", "Y");
// session.setAttribute("StockList", stockList);

// boxSplitForm.setView("");
// boxSplitForm.setViewAll("");

        util.updAccessLog(req,res,"Box Split", "viewsp end");
return am.findForward("loadsplit");
    }
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
        util.updAccessLog(req,res,"Box Split", "save start");
BoxSplitForm boxSplitForm = (BoxSplitForm)form;
String submit=util.nvl(req.getParameter("fetch"));
if(!submit.equals("")){
    ArrayList ary = new ArrayList();
    ResultSet rs=null;
    HashMap FromBoxDtl=new HashMap();
    HashMap ToBoxDtl=new HashMap();
    String fromidn=util.nvl(req.getParameter("bnme"));
    String toidn=util.nvl(req.getParameter("bnmeto"));
    String vnmLst=util.nvl(req.getParameter("vnmLst"));
    String selidn = util.nvl(req.getParameter("selidn"));
    //From Box fetch data
    if(!fromidn.equals("")){
    String FromBox = "select QTY, to_char(trunc(CTS,2),'9990.99') CTS, UPR from mstk where idn= "+fromidn;
    rs = db.execSql("FromBox",FromBox, new ArrayList());
    while(rs.next()){
        double rte=Math.round(Double.parseDouble(util.nvl(rs.getString("upr"),"0")));
        double value=Math.round((Double.parseDouble(util.nvl(rs.getString("cts"),"0")) * rte));
        double size=Math.round(Double.parseDouble(util.nvl(rs.getString("cts"),"0")) / Double.parseDouble(util.nvl(rs.getString("qty"),"0"))*100)/100.0;
        FromBoxDtl.put("qty",util.nvl(rs.getString("QTY"),"0")) ;  
        FromBoxDtl.put("cts",util.nvl(rs.getString("CTS"),"0")) ;  
        FromBoxDtl.put("rte",util.nvl(String.valueOf(rte),"0")) ; 
        FromBoxDtl.put("value",util.nvl(String.valueOf(value),"0")) ; 
        FromBoxDtl.put("size",util.nvl(String.valueOf(size),"0")) ; 
    }
        rs.close();
    }

    //To Box fetch data
    if(!toidn.equals("")){
    String ToBox = "select QTY, to_char(trunc(CTS,2),'9990.99') CTS, UPR from mstk where idn= "+toidn;
    rs = db.execSql("ToBox",ToBox, new ArrayList());
    while(rs.next()){
        double rte=Math.round(Double.parseDouble(util.nvl(rs.getString("upr"),"0")));
        double value=Math.round((Double.parseDouble(util.nvl(rs.getString("cts"),"0")) * rte));
        double size=Math.round(Double.parseDouble(util.nvl(rs.getString("cts"),"0")) / Double.parseDouble(util.nvl(rs.getString("qty"),"0"))*100)/100.0;
        ToBoxDtl.put("qty",util.nvl(rs.getString("QTY"),"0")) ;  
        ToBoxDtl.put("cts",util.nvl(rs.getString("CTS"),"0")) ;  
        ToBoxDtl.put("rte",util.nvl(String.valueOf(rte),"0")) ; 
        ToBoxDtl.put("value",util.nvl(String.valueOf(value),"0")) ; 
        ToBoxDtl.put("size",util.nvl(String.valueOf(size),"0")) ; 
    }
    rs.close();
    }
    //From Box Pkt Dtl fetch
    HashMap pktFrom=new HashMap();
    HashMap pktDtlFrom=new HashMap();
    ary = new ArrayList();
    if(!vnmLst.equals("")){
        vnmLst=vnmLst.toUpperCase();
        vnmLst=util.getVnm(vnmLst);
    }
    String Boxpkt = " select idn, vnm,qty,to_char(trunc(cts,2),'9990.99') cts,upr from mstk where pkt_rt=?" ;
    ary.add(fromidn);
    if(vnmLst.length() > 1){
        Boxpkt = " select idn,vnm,qty,cts,upr from mstk where pkt_rt=? and vnm in ("+vnmLst+")" ;   
    }
    rs = db.execSql("Box pkt", Boxpkt, ary);
    int loop=0;
    while(rs.next()){
        loop++;
        pktFrom=new HashMap();
        double rte=Math.round(Double.parseDouble(util.nvl(rs.getString("upr"),"0")));
        double value=Math.round((Double.parseDouble(util.nvl(rs.getString("cts"),"0")) * rte));
        double size=Math.round(Double.parseDouble(util.nvl(rs.getString("cts"),"0")) / Double.parseDouble(util.nvl(rs.getString("qty"),"0"))*100)/100.0;
        pktFrom.put("vnm",util.nvl(rs.getString("vnm"),"0")); 
        pktFrom.put("idn",util.nvl(rs.getString("idn"),"0")); 
        pktFrom.put("qty",util.nvl(rs.getString("qty"),"0"));  
        pktFrom.put("cts",util.nvl(rs.getString("cts"),"0"));   
        pktFrom.put("rte",util.nvl(String.valueOf(rte),"0")) ; 
        pktFrom.put("value",util.nvl(String.valueOf(value),"0")) ; 
        pktDtlFrom.put(loop, pktFrom);
        
        
    }
    rs.close();
    if(vnmLst.length() > 1){
        vnmLst=vnmLst.replaceAll("'", ""); 
    }
    //Calculation Logic Box
//    double fromQTY=Double.parseDouble((String)FromBoxDtl.get("qty"));
//    double fromCTS=Double.parseDouble((String)FromBoxDtl.get("cts"));
//    double fromRTE=Double.parseDouble((String)FromBoxDtl.get("rte"));
//    double fromVALUE=Double.parseDouble((String)FromBoxDtl.get("value"));
//    double fromSIZE=Double.parseDouble((String)FromBoxDtl.get("size"));
//    
//    double toQTY=Double.parseDouble((String)ToBoxDtl.get("qty"));
//    double toCTS=Double.parseDouble((String)ToBoxDtl.get("cts"));
//    double toRTE=Double.parseDouble((String)ToBoxDtl.get("rte"));
//    double toVALUE=Double.parseDouble((String)ToBoxDtl.get("value"));
//    double toSIZE=Double.parseDouble((String)ToBoxDtl.get("size"));
//    
//    double newfromQTY,newfromCTS,newfromRTE,newfromVALUE,newfromSIZE;
//    double newtoQTY,newtoCTS,newtoRTE,newtoVALUE,newtoSIZE;
//    double newQTYf,newCTSf,newRTEf;
//    double newQTYt,newCTSt,newRTEt;
//    for(int k=0;k<pktDtlFrom.size();k++){
//        HashMap DtlFrom=(HashMap) pktDtlFrom.get(k);
//        double pktfromQTY=Double.parseDouble((String)DtlFrom.get("qty"));
//        double pktfromCTS=Double.parseDouble((String)DtlFrom.get("cts"));
//        double pktfromRTE=Double.parseDouble((String)DtlFrom.get("rte"));
//        double pktfromVALUE=Double.parseDouble((String)DtlFrom.get("value"));
//        double pktfromSIZE=Double.parseDouble((String)DtlFrom.get("size"));
//        if(newfromQTY<=0){
//            newfromCTS = fromCTS - pktfromCTS;
//            newfromQTY = fromQTY - pktfromQTY;
//
//           double a = fromRTE * fromCTS;
//           double b = pktfromRTE * pktfromCTS;
//            double rate = a - b;
//            newfromRTE = rate/newfromCTS;
//
//            newtoCTS = toCTS + pktfromCTS;
//            newtoQTY = toQTY + pktfromQTY;
//
//            double a1 = toRTE * toCTS;
//            double b1 = pktfromVALUE * pktfromCTS;
//            double rate1 = a1 + b1;
//            newtoRTE = rate1/newtoCTS;
//            newfromVALUE=Math.round(newfromCTS * newfromRTE);
//            newfromSIZE=newfromCTS / newfromQTY;
//            newtoVALUE=Math.round(newtoCTS * newtoRTE);
//            newtoSIZE=newtoCTS / newtoQTY;
//        }else{
//            newCTSf = newfromCTS - pktfromCTS;
//            newQTYf = newfromQTY - pktfromQTY;
//
//            double a4 = newfromRTE * newfromCTS;
//            double b4 = pktfromRTE * pktfromCTS;
//
//            double rate4 = a4 - b4;
//            newRTEf = rate4/newCTSf;
//
//
//            newCTSt = newtoCTS + pktfromCTS;
//            newQTYt = newtoQTY + pktfromQTY;
//
//            double a8 = newtoRTE * newtoCTS;
//            double b8 = pktfromRTE * pktfromCTS;
//            double rate8 = a8 + b8;
//            newRTEt = rate8/newCTSt;
//            
//            newfromQTY=newQTYf;
//            newfromCTS=newCTSf;
//            newfromRTE=newRTEf;
//            newfromVALUE=Math.round(newCTSf * newRTEf);
//            newfromSIZE=newCTSf / newQTYf;
//            
//            newtoQTY=newQTYt;
//            newtoCTS=newCTSt;
//            newtoRTE=newRTEt;
//            newtoVALUE=Math.round(newCTSt * newRTEt);
//            newtoSIZE=newCTSt / newQTYt;
//        }
//        
//    }
//    
//    
//    
//    
    
    
    
    
    
    req.setAttribute("FromBoxDtl", FromBoxDtl);
    req.setAttribute("ToBoxDtl", ToBoxDtl);
    req.setAttribute("pktDtlFrom", pktDtlFrom);
    req.setAttribute("fromidn", fromidn);
    req.setAttribute("toidn", toidn);
    req.setAttribute("selidn", selidn);
    req.setAttribute("vnmLst", vnmLst);
    req.setAttribute("view", "Y");
    req.setAttribute("page","MIX");
    return am.findForward("loadsplit");
}

//ArrayList stockList = (ArrayList)session.getAttribute("StockList");
String boxto = req.getParameter("bnmeto");
String boxfrom = req.getParameter("bnme");

String selidn = util.nvl(req.getParameter("selidn"));
String pkt = "0";
//if(req.getParameter("h_"+selidn)!=null) {
//pkt = req.getParameter("h_"+selidn).toString();
//}
if(req.getParameter("count")!=null) {
pkt = req.getParameter("count").toString();
}

Integer pktcnt = Integer.valueOf(pkt);

int ct=0;
ArrayList ary = new ArrayList();
ArrayList ary1 = new ArrayList();
ArrayList ary2 = new ArrayList();
for(int i=0; i< pktcnt;i++){

// HashMap stockPkt = (HashMap)stockList.get(i);
// String stkIdn = (String)stockPkt.get("stk_idn");
String lStt = "";
// String box = util.nvl((String)boxSplitForm.getValue("BOX_"+(i+1)));
// String single = util.nvl((String)boxSplitForm.getValue("SINGLE_"+(i+1)));
// String qtyf = util.nvl((String)boxSplitForm.getValue("qty_"+(i+1)));
// String ctsf = util.nvl((String)boxSplitForm.getValue("cts_"+(i+1)));
// String rtef = util.nvl((String)boxSplitForm.getValue("rte_"+(i+1)));
// String vnmf = util.nvl((String)boxSplitForm.getValue("vnm_"+(i+1)));

String box = util.nvl(req.getParameter("BOX_"+(i+1)));
String single = util.nvl(req.getParameter("SINGLE_"+(i+1)));
String qtyf = util.nvl(req.getParameter("qty_"+(i+1)));
String ctsf = util.nvl(req.getParameter("cts_"+(i+1)));
String rtef = util.nvl(req.getParameter("rte_"+(i+1)));
String idnf = util.nvl(req.getParameter("idn_"+(i+1)));

//stkIdn="605800004";
// String reqbox = util.nvl((String)req.getParameter("BOX_"+(i+1)));
// String reqsin = util.nvl((String)req.getParameter("SINGLE_"+(i+1)));
// String reqvnm = util.nvl((String)req.getParameter("vnm_"+(i+1)));
if(box.equals("") && single.equals("")) {
lStt="";
}else if(box.equals("") && !single.equals("")){
lStt="SINGLE";
}else{
lStt="BOX";
}

if(lStt.equals("SINGLE")){
ary = new ArrayList();
ary.add(idnf);
ct = db.execCall("box_sel","MIX_PKG.SPLIT_PKT(pIdn => ?)", ary);

    String cts = (String)req.getParameter("newfromcts");
    String rte = (String)req.getParameter("newfromrte");
    String qty = (String)req.getParameter("newfromqty");
    String ufqty = (String)req.getParameter("userfromqty");
    String ufcts = (String)req.getParameter("userfromcts");
    String ufrte = (String)req.getParameter("userfromrte");

    ary1 = new ArrayList();
    ary1.add(boxfrom);

    if(!ufcts.equals("")){
    ary1.add(ufcts);
    ary1.add(ufrte);
    ary1.add(ufqty);
    }else {
    ary1.add(cts);
    ary1.add(rte);
    ary1.add(qty);
    }


    ct = db.execCall("box_sel","MIX_PKG.MOD_RT_PKT(pIdn => ?, pCts => ?, pRte => ?, pQty => ?)", ary1);

}
if(lStt.equals("BOX")) {
ary = new ArrayList();
ary.add(idnf);
ary.add(boxto);
ct = db.execCall("box_sel","MIX_PKG.SPLIT_PKT(pIdn => ?, pPktRt => ?, pTyp => 'B')", ary);

String cts = (String)req.getParameter("newfromcts");
String rte = (String)req.getParameter("newfromrte");
String qty = (String)req.getParameter("newfromqty");
String ufqty = (String)req.getParameter("userfromqty");
String ufcts = (String)req.getParameter("userfromcts");
String ufrte = (String)req.getParameter("userfromrte");

ary1 = new ArrayList();
ary1.add(boxfrom);

if(!ufcts.equals("")){
ary1.add(ufcts);
ary1.add(ufrte);
ary1.add(ufqty);
}else {
ary1.add(cts);
ary1.add(rte);
ary1.add(qty);
}


ct = db.execCall("box_sel","MIX_PKG.MOD_RT_PKT(pIdn => ?, pCts => ?, pRte => ?, pQty => ?)", ary1);

String cts1 = (String)req.getParameter("newtocts");
String rte1 = (String)req.getParameter("newtorte");
String qty1 = (String)req.getParameter("newtoqty");
String utqty = (String)req.getParameter("usertoqty");
String utcts = (String)req.getParameter("usertocts");
String utrte = (String)req.getParameter("usertorte");

ary2 = new ArrayList();
ary2.add(boxto);


if(!utcts.equals("")){
ary1.add(utcts);
ary1.add(utrte);
ary1.add(utqty);
}else {
ary2.add(cts1);
ary2.add(rte1);
ary2.add(qty1);
}

ct = db.execCall("box_sel","MIX_PKG.MOD_RT_PKT(pIdn => ?, pCts => ?, pRte => ?, pQty => ?)", ary2);
    
ary2 = new ArrayList();
ary2.add(boxfrom);
ct = db.execCall("box_sel","MIX_PKG.SET_RT_PKT_WT(pPktRt => ?)", ary2);

   
ary2 = new ArrayList();
ary2.add(boxto);
ct = db.execCall("box_sel","MIX_PKG.SET_RT_PKT_WT(pPktRt => ?)", ary2);

}
}


boxSplitForm.setView("");
boxSplitForm.setViewAll("");
req.setAttribute("msg", "Box selection done successfully..");
//session.setAttribute("StockList", stockList);
util.updAccessLog(req,res,"Box Split", "save end");
return loadsp(am, form, req, res);
    }

}


public ActionForward view1(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        util.updAccessLog(req,res,"Box Split", "view1 start");
BoxSplitForm boxSplitForm = (BoxSplitForm)form;

String vnmlst = (String) boxSplitForm.getValue("vnmLst");


String getpktlist = "";

if(vnmlst.equals("")){
getpktlist = "select vnm,sh,co,cts,qty,upr from vstk_a where stt='MIX_AV'";

} else
getpktlist = "select vnm,sh,co,cts,qty,upr from vstk_a where vnm in ('"+vnmlst+"') and stt='MIX_AV'";

ResultSet rs = db.execSql(" Get Inv Id ", getpktlist, new ArrayList());
ArrayList listpkt=new ArrayList();
try {
while(rs.next()){
ArrayList listpt=new ArrayList();
String vnm =util.nvl(rs.getString("vnm")) ;
String sh =util.nvl(rs.getString("sh")) ;
String co =util.nvl(rs.getString("co")) ;
String cts =util.nvl(rs.getString("cts")) ;
String qty =util.nvl(rs.getString("qty")) ;
String upr =util.nvl(rs.getString("upr")) ;

listpt.add(vnm);
listpt.add(sh);
listpt.add(co);
listpt.add(cts);
listpt.add(qty);
listpt.add(upr);
listpkt.add(listpt);
}
    rs.close();
} catch (Exception e) {
e.printStackTrace();
}
req.setAttribute("listpkt",listpkt);



HashMap boxnme = new HashMap();
String getBoxname = "select idn, vnm from mstk where pkt_ty='MIX' order by vnm";
ArrayList ary1 = new ArrayList();
ResultSet rs1 = null;
try{
rs1 = db.execSql(" Fav Lst ", getBoxname, ary1);
while(rs1.next())
{
boxnme.put(rs1.getString(1),rs1.getString(2));
}
    rs1.close();
session.setAttribute("boxnme", boxnme);
}
catch (Exception e) {
e.printStackTrace();
}



boxSplitForm.setView("");
boxSplitForm.setViewAll("");

        util.updAccessLog(req,res,"Box Split", "view1 end");
return am.findForward("load");
    }
}







public ActionForward saveOLD(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        util.updAccessLog(req,res,"Box Split", "saveOLD start");
BoxSplitForm boxSplitForm = (BoxSplitForm)form;
ArrayList stockList = (ArrayList)session.getAttribute("StockList");
int ct=0;
ArrayList ary = new ArrayList();
for(int i=0;i< stockList.size();i++){
HashMap stockPkt = (HashMap)stockList.get(i);
String stkIdn = (String)stockPkt.get("stk_idn");
String lStt = util.nvl((String) boxSplitForm.getValue("stt_" + stkIdn));
if(!lStt.equals("")){
String newStt = lStt+"_AV";
if(lStt.equals("LB")){
String lab= util.nvl((String) boxSplitForm.getValue("lab_" + stkIdn));
if(lab.equals("NA") || lab.equals("NONE") || lab.equals("NC"))
newStt = "LB_CF";
ary = new ArrayList();
ary.add(stkIdn);
ary.add(lab);
ct = db.execCall("lab_sel","ISS_RTN_PKG.LAB_SEL(pStkIdn => ?, pLab => ?)", ary);

ary = new ArrayList();
ary.add(newStt);
ary.add(lab);
ary.add(stkIdn);
String updGt ="update mstk set stt=? , cert_lab=? where idn=? ";
ct = db.execUpd("update Gt", updGt, ary);
}else if(lStt.equals("REP") || lStt.equals("MIX")){
ary = new ArrayList();
ary.add(newStt);
ary.add(stkIdn);
String updGt ="update mstk set stt=? where idn=? ";
ct = db.execUpd("update Gt", updGt, ary);
}else{

}

}

}
boxSplitForm.setView("");
boxSplitForm.setViewAll("");
req.setAttribute("msg", "Box selection done successfully..");
req.setAttribute("view", "Y");
session.setAttribute("StockList", stockList);
        util.updAccessLog(req,res,"Box Split", "saveOLD end");
return am.findForward("load");
    }
}
public ActionForward loadsplit(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        util.updAccessLog(req,res,"Box Split", "loadsplit start");
BoxSplitForm boxSplitForm = (BoxSplitForm)form;
GenericInterface genericInt=new GenericImpl();
boxSplitForm.resetALL();
ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LBGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LBGNCSrch"); 
info.setGncPrpLst(assortSrchList);

HashMap boxTyp = new HashMap();
String getBoxtyp = "select VAL, DSC from prp where mprp = 'BOX_TYP' and dte is NULL";
ArrayList ary1 = new ArrayList();
ResultSet rs = null;
try{
rs = db.execSql(" Fav Lst ", getBoxtyp, ary1);
while(rs.next())
{
boxTyp.put(rs.getString(1),rs.getString(2));
}
    rs.close();
session.setAttribute("boxTyp", boxTyp);
}
catch (Exception e) {
e.printStackTrace();
}

HashMap boxnme = new HashMap();
String getBoxname = "select idn, vnm from mstk where pkt_ty='MIX' order by vnm";
ArrayList ary2 = new ArrayList();
ResultSet rs1 = null;
try{
rs1 = db.execSql(" Fav Lst ", getBoxname, ary2);
while(rs1.next())
{
boxnme.put(rs1.getString(1),rs1.getString(2));
}
    rs1.close();
session.setAttribute("boxnme", boxnme);
}
catch (Exception e) {
e.printStackTrace();
}

        util.updAccessLog(req,res,"Box Split", "loadsplit end");
return am.findForward("splitload");
    }
}
public ActionForward splitview(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        util.updAccessLog(req,res,"Box Split", "splitview start");
BoxSplitForm boxSplitForm = (BoxSplitForm)form;
String bxnme = (String) boxSplitForm.getBnme();

String getpktlist = "";

if(bxnme.equals("")){
getpktlist = "select idn,vnm,cts,qty,upr from vstk_a where pkt_ty= 'MX'";

} else
getpktlist = "select idn,vnm,cts,qty,upr from vstk_a where pkt_ty= 'MX' and pkt_rt="+bxnme;

ResultSet rs = db.execSql(" Get Inv Id ", getpktlist, new ArrayList());
ArrayList listpkt=new ArrayList();
try {
while(rs.next()){
ArrayList listpt=new ArrayList();
String idn =util.nvl(rs.getString("idn")) ;
String vnm =util.nvl(rs.getString("vnm")) ;
String cts =util.nvl(rs.getString("cts")) ;
String qty =util.nvl(rs.getString("qty")) ;
String upr =util.nvl(rs.getString("upr")) ;

listpt.add(idn);
listpt.add(vnm);
listpt.add(cts);
listpt.add(qty);
listpt.add(upr);
listpkt .add(listpt);
}
    rs.close();
} catch (Exception e) {
e.printStackTrace();
}
req.setAttribute("listpkt",listpkt);

String getsumpkt = "";

if(bxnme.equals("")){
getsumpkt = "select sum(cts) ttlcts,sum(qty) ttlqty,sum(upr) ttlupr from vstk_a where pkt_ty= 'MX'";
} else
getsumpkt = "select sum(cts) ttlcts,sum(qty) ttlqty,sum(upr) ttlupr from vstk_a where pkt_ty= 'MX' and pkt_rt="+bxnme;

ResultSet rs1 = db.execSql(" Get Inv Id ", getsumpkt, new ArrayList());
ArrayList ttlpkt=new ArrayList();
try {
while(rs1.next()){

String cts =util.nvl(rs1.getString("ttlcts")) ;
String qty =util.nvl(rs1.getString("ttlqty")) ;
String upr =util.nvl(rs1.getString("ttlupr")) ;
ttlpkt.add(cts);
ttlpkt.add(qty);
ttlpkt.add(upr);
}
    rs1.close();
} catch (Exception e) {
e.printStackTrace();
}
req.setAttribute("ttlpkt",ttlpkt);


boxSplitForm.setView("");
boxSplitForm.setViewAll("");

        util.updAccessLog(req,res,"Box Split", "splitview end");
return am.findForward("splitload");
    }
}

public BoxSplitAction() {
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
        util.updAccessLog(req,res,"Login Action", "Unauthorized Access");
        else
    util.updAccessLog(req,res,"Login Action", "init");
    }
    }
    return rtnPg;
    }
}