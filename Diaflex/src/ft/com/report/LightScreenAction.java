package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.JspUtil;
import ft.com.dao.ByrDao;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.SearchQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

public class LightScreenAction  extends DispatchAction {
    public LightScreenAction() {
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
        util.updAccessLog(req,res,"LightScreenAction", "load start");
        LightScreenForm udf = (LightScreenForm)af;
        GenericInterface genericInt = new GenericImpl();
        udf.reset();
            ArrayList disSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LIGHT_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LIGHT_SRCH");
            info.setGncPrpLst(disSrchList);
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("LIGHT_SCREEN");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("LIGHT_SCREEN");
            allPageDtl.put("LIGHT_SCREEN",pageDtl);
            }
            info.setPageDetails(allPageDtl); 
            udf.setValue("srchRef", "NONE");
            udf.setValue("typ", "ALL");
        util.updAccessLog(req,res,"LightScreenAction", "load end");
        return am.findForward("load");
        }
    }
    
    public ActionForward fetch(ActionMapping am, ActionForm af, HttpServletRequest req,
                                     HttpServletResponse res) throws Exception {
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
        util.updAccessLog(req,res,"LightScreenAction", "fetch start");
        LightScreenForm udf = (LightScreenForm)af;
        ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LIGHT_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LIGHT_SRCH");
        info.setGncPrpLst(genricSrchLst);
        HashMap dbinfo = info.getDmbsInfoLst();
            String sttstr = util.nvl((String)udf.getValue("stt"));
            String srchTyp = util.nvl((String)udf.getValue("srchRef"));
            String vnm = util.nvl((String)udf.getValue("idns"));
            String typstt = util.nvl((String)udf.getValue("typ"));
            String[] disStt=sttstr.split(",");
            ArrayList lightPktLst=new ArrayList();
            
            int ctg =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_rslt", new ArrayList());
            int ctp =db.execUpd(" Del Old Pkts ", " Delete from gt_pkt_scan", new ArrayList());
            if(srchTyp.equals("NONE")){
            String mdl = "DIS_VIEW";
            ArrayList params = null;
            ArrayList ary = new ArrayList();
            if(disStt.length>0){
            String stt=disStt[0].trim();
            stt=disStt[0];
            info.setGncPrpLst(genricSrchLst);
            HashMap prp = info.getPrp();
            HashMap mprp = info.getMprp();
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
            String reqVal1 = util.nvl((String)udf.getValue(lprp + "_" + lVal),"");
            if(!reqVal1.equals("")){
            paramsMap.put(lprp + "_" + lVal, reqVal1);
            }
            }
            }else{
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
            }

            if(paramsMap.size()>0){
            paramsMap.put("stt", stt);
            paramsMap.put("mdl", mdl);
            int lSrchId=util.genericSrchEntries(paramsMap);
              String insrtAddon = " insert into srch_addon( srch_id , cprp , cval) "+
                         "select ? , ? , ?  from dual ";
                for(int st=0;st<disStt.length;st++){
                    stt=disStt[st].trim();
                    if(!stt.equals("")){
                    params = new ArrayList();
                    params.add(String.valueOf(lSrchId));
                    params.add("STT");
                    params.add(stt);
                    int ct = db.execUpd("", insrtAddon, params);  
                    }}
                    String srch_pkg = "DP_DISCO_SRCH(?)";
                    ary = new ArrayList();
                    ary.add(String.valueOf(lSrchId));
                    int ct = db.execCall("stk_srch", srch_pkg, ary); 
                    ct = db.execCall("stk_srch", "Insert into gt_pkt_scan (vnm) select rmk from gt_srch_rslt where rmk is not null", new ArrayList()); 
            }
            }
            }else{
            if(!vnm.equals("")){
            if(srchTyp.equals("PKT") || srchTyp.equals("CERT")){
                int ct=0;
                int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
                vnm = util.getVnm(vnm);
                String[] vnmLst = vnm.split(",");
                int loopCnt = 1 ;
                System.out.println(vnmLst.length);
                float loops = ((float)vnmLst.length)/stepCnt;
                loopCnt = Math.round(loops);
                   if(vnmLst.length <= stepCnt || new Float(loopCnt)>=loops) {
                   
                } else
                   loopCnt += 1 ;
                if(loopCnt==0)
                   loopCnt += 1 ;
                int fromLoc = 0 ;
                int toLoc = 0 ;
                for(int i=1; i <= loopCnt; i++) {
                   
                    int aryLoc = Math.min(i*stepCnt, vnmLst.length) ;
                    
                    String lookupVnm = vnmLst[aryLoc-1];
                      if(i == 1)
                          fromLoc = 0 ;
                      else
                          fromLoc = toLoc+1;
                      
                      toLoc = Math.min(vnm.lastIndexOf(lookupVnm) + lookupVnm.length(), vnm.length());
                      String vnmSub = vnm.substring(fromLoc, toLoc);
                
                 String srchStr = "";
                   
                 if(srchTyp.equals("PKT"))
                     srchStr = " ( b.vnm in ("+vnmSub+") or b.tfl3 in ("+vnmSub+") ) ";
                 else if(srchTyp.equals("CERT"))
                     srchStr = " b.cert_no in ("+vnmSub+")";
                
                
                String srchRefQ =
                "    Insert into gt_pkt_scan (vnm) " +
                " select  b.tfl3  from mstk b where  b.tfl3 is not null and "+srchStr;
                ct = db.execUpd(" Srch Prp ", srchRefQ, new ArrayList());
                }
                
            }else{
                vnm=util.getVnm(vnm);
                vnm = vnm.replaceAll("'", "");
                String srchRef="";
                String conQ=" and b.idn in("+vnm+") ";
                if(srchTyp.equals("IS")){  
                    if(!typstt.equals("ALL")){
                        conQ+="and b.stt in('IS')";
                    }
                    srchRef=" Insert into gt_pkt_scan (vnm) \n" + 
                    "select c.tfl3\n" + 
                    "from mjan a , jandtl b  , mstk c\n" + 
                    "where a.idn = b.idn  and b.mstk_idn = c.idn\n" + 
                    "and a.typ in ('I', 'E', 'WH', 'Z','K','H','BID','O') and c.tfl3 is not null "+conQ;
                }else if(srchTyp.equals("AP")){
                    if(!typstt.equals("ALL")){
                        conQ+="and b.stt in('AP')";
                    }
                    srchRef = " Insert into gt_pkt_scan (vnm) \n" + 
                    "select c.tfl3\n" + 
                    "from mjan a , jandtl b  , mstk c\n" + 
                    "where a.idn = b.idn  and b.mstk_idn = c.idn\n" + 
                    "and b.typ in ('IAP', 'EAP','WAP','LAP','BCAP','MAP','SAP','HAP','BAP','KAP','BIDAP','OAP') and c.tfl3 is not null  "+conQ;     
                }else if(srchTyp.equals("SL")){
                    if(!typstt.equals("ALL")){
                        conQ+="and b.stt in('SL')";
                    }
                srchRef = " Insert into gt_pkt_scan (vnm) \n" + 
                "select c.tfl3\n" + 
                "from msal a , jansal b  , mstk c\n" + 
                "where a.idn = b.idn  and b.mstk_idn = c.idn  and c.tfl3 is not null "+conQ;
                }else if(srchTyp.equals("DLV")){
                    if(!typstt.equals("ALL")){
                        conQ+="and b.stt in('DLV')";
                    }
                    srchRef = "Insert into gt_pkt_scan (vnm) \n" + 
                    "select c.tfl3\n" + 
                    "from mdlv a , dlv_dtl b  , mstk c\n" + 
                    "where a.idn = b.idn  and b.mstk_idn = c.idn and c.tfl3 is not null \n"+conQ ;
                }
                int ct = db.execUpd("insert gt",srchRef, new ArrayList());
            }
            }
            }
            try {
                String sqlVal = "select vnm from gt_pkt_scan";

                ArrayList outLst = db.execSqlLst("sqlVlu", sqlVal, new ArrayList());
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
                while(rs.next()) {
                    lightPktLst.add(util.nvl(rs.getString("vnm")));
                }
                rs.close(); pst.close();
            } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
            }
            if(!vnm.equals("")){
            vnm = vnm.replaceAll("\\'","");
            vnm = vnm.replaceAll(" ","");
            }
            req.setAttribute("lightPktLst", lightPktLst);
            udf.setValue("srchRef", srchTyp);
            udf.setValue("idns", vnm);
            udf.setValue("typ", typstt);
            
        util.updAccessLog(req,res,"LightScreenAction", "fetch end");
        return am.findForward("load");
        }
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
                util.updAccessLog(req,res,"LightScreenAction", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"LightScreenAction", "init");
            }
            }
            return rtnPg;
            }
}
