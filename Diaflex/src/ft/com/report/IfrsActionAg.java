package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

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

public class IfrsActionAg extends DispatchAction {
    public IfrsActionAg() {
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
        util.updAccessLog(req,res,"Ifrs Action", "load start");
        ReportForm udf = (ReportForm)af;
        udf.resetALL();
        GenericInterface genericInt = new GenericImpl();
        ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, "IFRS_OPEN_CLOSE","IFRS_OPEN_CLOSE");
        util.updAccessLog(req,res,"Ifrs Action", "load end");
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("IFRS");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("IFRS");
            allPageDtl.put("IFRS",pageDtl);
            }
            info.setPageDetails(allPageDtl); 
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
            util.updAccessLog(req,res,"Ifrs Action", "load start");
            ReportForm udf = (ReportForm)af;
            ArrayList ary = new ArrayList();
            String frmdte = util.nvl((String)udf.getValue("frmdte"));
            String todte = util.nvl((String)udf.getValue("todte"));
            String typ = util.nvl((String)udf.getValue("srch"));
                HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
                HashMap pageDtl=(HashMap)allPageDtl.get("IFRS");
                if(pageDtl==null || pageDtl.size()==0){
                pageDtl=new HashMap();
                pageDtl=util.pagedef("IFRS");
                allPageDtl.put("IFRS",pageDtl);
                }
            if(typ.equals("FetchBucketWise"))
                    return fetchBucket(am,af,req,res);
            if(typ.equals("SummaryBucketWise"))
                    return summaryBucket(am,af,req,res);
                ArrayList addtabList = new ArrayList();
              ArrayList  pageList=(ArrayList)pageDtl.get("ADDTAB");
                if(pageList!=null && pageList.size() >0){
                    for(int j=0;j<pageList.size();j++){
                        HashMap pgDtl= (HashMap)pageList.get(j);
                        String addTab = (String)pgDtl.get("fld_nme");
                        addtabList.add(addTab);
                    }}
                     
            HashMap dataDtl = new HashMap();
            ArrayList asOnDateList = new ArrayList();
            ArrayList ifrsLst = new ArrayList();
            String prvAsOnDate="";
                if(!frmdte.equals("") && !todte.equals("")){
                String slctPart = "";
                String summyPart = "";
                for(int i=0;i<addtabList.size();i++){
                    String tab = (String)addtabList.get(i);
                   slctPart= slctPart+" to_char(trunc("+tab+"_cts,3),'99999999999990.000') "+tab+"_cts,to_char(trunc("+tab+"_rte,2),'99999999999990.00') "+tab+"_rte ,to_char(trunc("+tab+"_vlu,2),'9999999999999999990.00') "+tab+"_vlu, ";
                    summyPart = summyPart+" to_char(trunc(sum(trunc("+tab+"_cts, 2)),2),'99999999999990.00') "+tab+"_cts,to_char(trunc(sum(trunc("+tab+"_rte, 2)),2),'99999999999990.00') "+tab+"_rte ,to_char(trunc(sum(trunc("+tab+"_vlu, 2)),2),'9999999999999999990.00') "+tab+"_vlu, ";
                }
                    
                String conQ = " and trunc(as_on_date) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
                String dataQ = "select ifrs,to_char(as_on_date, 'rrrrmmdd') as_on_dateSrt,to_char(as_on_date, 'dd-mm-rrrr') as_on_date,\n" + 
                "to_char(trunc(open_cts,3),'99999999999990.000') open_cts,to_char(trunc(open_rte,2),'99999999999990.00') open_rte ,to_char(trunc(open_vlu,2),'9999999999999999990.00') open_vlu, \n" + 
                "to_char(trunc(mfgnew_cts,3),'99999999999990.000') mfgnew_cts,to_char(trunc(mfgnew_rte,2),'99999999999990.00') mfgnew_rte ,to_char(trunc(mfgnew_vlu,2),'9999999999999999990.00') mfgnew_vlu, \n" + 
                "to_char(trunc(purnew_cts,3),'99999999999990.000') purnew_cts,to_char(trunc(purnew_rte,2),'99999999999990.00') purnew_rte ,to_char(trunc(purnew_vlu,2),'9999999999999999990.00') purnew_vlu, \n" + 
                    " to_char(trunc(sold_act_vlu,2),'9999999999999999990.00') sold_act_vlu,\n" + 
                    "to_char(trunc(decode(sold_act_vlu,'0','0',  (100-(sold_vlu*100/sold_act_vlu)) ),2),'9999999999999999990.00') diff,"+slctPart+
                  "to_char(trunc(sold_cts,3),'99999999999990.000') sold_cts,to_char(trunc(sold_rte,2),'99999999999990.00') sold_rte ,to_char(trunc(sold_vlu,2),'9999999999999999990.00') sold_vlu, \n" + 
                "to_char(trunc(cts,3),'99999999999990.000') close_cts,to_char(trunc(rte,2),'99999999999990.00') close_rte ,to_char(trunc(vlu,2),'9999999999999999990.00') close_vlu , to_char(trunc(NRV,2),'9999999999999999990.00') NRV_vlu \n" + 
                "from IFRS_STK_VLU \n" + 
                "where stt='A' "+conQ+
                "order by 2 desc,1";
                 ary = new ArrayList();  

                    ArrayList outLst = db.execSqlLst("Collection", dataQ, ary);
                    PreparedStatement pst = (PreparedStatement)outLst.get(0);
                    ResultSet rs = (ResultSet)outLst.get(1);
                 while(rs.next()) {
                     String as_on_date=util.nvl(rs.getString("as_on_date"));
                     String ifrs=util.nvl(rs.getString("ifrs"));
                     if(prvAsOnDate.equals(""))
                     prvAsOnDate=as_on_date;
                     
                         if(!as_on_date.equals(prvAsOnDate)){
                             dataDtl.put(prvAsOnDate,ifrsLst);
                             ifrsLst = new ArrayList();
                             prvAsOnDate=as_on_date;
                         }    
                     
                     ifrsLst.add(ifrs);   
                     dataDtl.put(as_on_date+"_"+ifrs+"_OPEN_CTS",util.nvl(rs.getString("open_cts")));
                     dataDtl.put(as_on_date+"_"+ifrs+"_OPEN_RTE",util.nvl(rs.getString("open_rte")));
                     dataDtl.put(as_on_date+"_"+ifrs+"_OPEN_VLU",util.nvl(rs.getString("open_vlu")));
                     dataDtl.put(as_on_date+"_"+ifrs+"_MFGNEW_CTS",util.nvl(rs.getString("mfgnew_cts")));
                     dataDtl.put(as_on_date+"_"+ifrs+"_MFGNEW_RTE",util.nvl(rs.getString("mfgnew_rte")));
                     dataDtl.put(as_on_date+"_"+ifrs+"_MFGNEW_VLU",util.nvl(rs.getString("mfgnew_vlu")));
                     dataDtl.put(as_on_date+"_"+ifrs+"_PURNEW_CTS",util.nvl(rs.getString("purnew_cts")));
                     dataDtl.put(as_on_date+"_"+ifrs+"_PURNEW_RTE",util.nvl(rs.getString("purnew_rte")));
                     dataDtl.put(as_on_date+"_"+ifrs+"_PURNEW_VLU",util.nvl(rs.getString("purnew_vlu")));
                     dataDtl.put(as_on_date+"_"+ifrs+"_SOLD_ACTVLU",util.nvl(rs.getString("sold_act_vlu")));
                     dataDtl.put(as_on_date+"_"+ifrs+"_SOLD_DIFF",util.nvl(rs.getString("diff")));
                     
                     dataDtl.put(as_on_date+"_"+ifrs+"_SOLD_CTS",util.nvl(rs.getString("sold_cts")));
                     dataDtl.put(as_on_date+"_"+ifrs+"_SOLD_RTE",util.nvl(rs.getString("sold_rte")));
                     dataDtl.put(as_on_date+"_"+ifrs+"_SOLD_VLU",util.nvl(rs.getString("sold_vlu")));
                     dataDtl.put(as_on_date+"_"+ifrs+"_CLOSE_CTS",util.nvl(rs.getString("close_cts")));
                     dataDtl.put(as_on_date+"_"+ifrs+"_CLOSE_RTE",util.nvl(rs.getString("close_rte")));
                     dataDtl.put(as_on_date+"_"+ifrs+"_CLOSE_VLU",util.nvl(rs.getString("close_vlu")));
                     dataDtl.put(as_on_date+"_"+ifrs+"_CLOSE_NRVVLU",util.nvl(rs.getString("NRV_vlu")));
                     for(int i=0;i<addtabList.size();i++){
                         String tab = (String)addtabList.get(i);
                         
                         dataDtl.put(as_on_date+"_"+ifrs+"_"+tab+"_CTS",util.nvl(rs.getString(tab+"_cts")));
                         dataDtl.put(as_on_date+"_"+ifrs+"_"+tab+"_RTE",util.nvl(rs.getString(tab+"_rte")));
                         dataDtl.put(as_on_date+"_"+ifrs+"_"+tab+"_VLU",util.nvl(rs.getString(tab+"_vlu")));
                     }
                     
                 }
                 rs.close(); pst.close();
                 
                    dataQ = "select to_char(as_on_date, 'rrrrmmdd') as_on_dateSrt,to_char(as_on_date, 'dd-mm-rrrr') as_on_date,\n" + 
                    "to_char(trunc(sum(trunc(open_cts, 3)),3),'99999999999990.000') open_cts,to_char(trunc(sum(trunc(open_rte, 2)),2),'99999999999990.00') open_rte ,to_char(trunc(sum(trunc(open_vlu, 2)),2),'9999999999999999990.00') open_vlu, \n" + 
                    "to_char(trunc(sum(trunc(mfgnew_cts, 3)),3),'99999999999990.000') mfgnew_cts,to_char(trunc(sum(trunc(mfgnew_rte, 2)),2),'99999999999990.00') mfgnew_rte ,to_char(trunc(sum(trunc(mfgnew_vlu, 2)),2),'9999999999999999990.00') mfgnew_vlu, \n" + 
                    "to_char(trunc(sum(trunc(purnew_cts, 3)),3),'99999999999990.000') purnew_cts,to_char(trunc(sum(trunc(purnew_rte, 2)),2),'99999999999990.00') purnew_rte ,to_char(trunc(sum(trunc(purnew_vlu, 2)),2),'9999999999999999990.00') purnew_vlu, \n" + 
                    "to_char(trunc(sum(trunc(sold_cts, 3)),3),'99999999999990.000') sold_cts,to_char(trunc(sum(trunc(sold_rte, 2)),2),'99999999999990.00') sold_rte ,to_char(trunc(sum(trunc(sold_vlu, 2)),2),'9999999999999999990.00') sold_vlu, \n" + 
                    "to_char(trunc(sum(trunc(cts, 3)),3),'99999999999990.000') close_cts,to_char(trunc(sum(trunc(rte, 2)),2),'99999999999990.00') close_rte ,to_char(trunc(sum(trunc(vlu, 2)),2),'9999999999999999990.00') close_vlu\n" + 
                    " ,to_char(trunc(sum(trunc(NRV, 2)),2),'9999999999999999990.00') nrv_vlu, to_char(trunc(sum(trunc(sold_act_vlu, 2)),2),'9999999999999999990.00') sold_act_vlu, \n" +summyPart+
                    "to_char(trunc(decode(sum(sold_act_vlu),'0','0',  (100-(sum(sold_vlu)*100/sum(sold_act_vlu))) ),2),'9999999999999999990.00') diff from IFRS_STK_VLU  a  \n" + 
                    
                  
                    "where stt='A' "+conQ+
                    "group by to_char(as_on_date, 'rrrrmmdd'),to_char(as_on_date, 'dd-mm-rrrr')\n" + 
                    "order by 1 desc";
                     ary = new ArrayList();  

                    outLst = db.execSqlLst("Collection", dataQ, ary);
                    pst = (PreparedStatement)outLst.get(0);
                    rs = (ResultSet)outLst.get(1);
                     while(rs.next()) {
                         String as_on_date=util.nvl(rs.getString("as_on_date"));
                         dataDtl.put(as_on_date+"_OPEN_CTS",util.nvl(rs.getString("open_cts")));
                         dataDtl.put(as_on_date+"_OPEN_RTE",util.nvl(rs.getString("open_rte")));
                         dataDtl.put(as_on_date+"_OPEN_VLU",util.nvl(rs.getString("open_vlu")));
                         dataDtl.put(as_on_date+"_MFGNEW_CTS",util.nvl(rs.getString("mfgnew_cts")));
                         dataDtl.put(as_on_date+"_MFGNEW_RTE",util.nvl(rs.getString("mfgnew_rte")));
                         dataDtl.put(as_on_date+"_MFGNEW_VLU",util.nvl(rs.getString("mfgnew_vlu")));
                         dataDtl.put(as_on_date+"_PURNEW_CTS",util.nvl(rs.getString("purnew_cts")));
                         dataDtl.put(as_on_date+"_PURNEW_RTE",util.nvl(rs.getString("purnew_rte")));
                         dataDtl.put(as_on_date+"_PURNEW_VLU",util.nvl(rs.getString("purnew_vlu")));
                         dataDtl.put(as_on_date+"_SOLD_CTS",util.nvl(rs.getString("sold_cts")));
                         dataDtl.put(as_on_date+"_SOLD_RTE",util.nvl(rs.getString("sold_rte")));
                         dataDtl.put(as_on_date+"_SOLD_VLU",util.nvl(rs.getString("sold_vlu")));
                         dataDtl.put(as_on_date+"_CLOSE_CTS",util.nvl(rs.getString("close_cts")));
                         dataDtl.put(as_on_date+"_CLOSE_RTE",util.nvl(rs.getString("close_rte")));
                         dataDtl.put(as_on_date+"_CLOSE_VLU",util.nvl(rs.getString("close_vlu")));
                         dataDtl.put(as_on_date+"_CLOSE_NRVVLU",util.nvl(rs.getString("nrv_vlu")));
                         dataDtl.put(as_on_date+"_SOLD_ACT",util.nvl(rs.getString("sold_act_vlu")));
                         dataDtl.put(as_on_date+"_SOLD_DIFF",util.nvl(rs.getString("diff")));
                         for(int i=0;i<addtabList.size();i++){
                             String tab = (String)addtabList.get(i);
                             
                             dataDtl.put(as_on_date+"_"+tab+"_CTS",util.nvl(rs.getString(tab+"_cts")));
                             dataDtl.put(as_on_date+"_"+tab+"_RTE",util.nvl(rs.getString(tab+"_rte")));
                             dataDtl.put(as_on_date+"_"+tab+"_VLU",util.nvl(rs.getString(tab+"_vlu")));
                         }
                         
                         asOnDateList.add(as_on_date);
                     }
                     rs.close(); pst.close();
                 if(!prvAsOnDate.equals(""))
                 dataDtl.put(prvAsOnDate,ifrsLst);
                }
                
                req.setAttribute("dataDtl", dataDtl);
                req.setAttribute("asOnDateList", asOnDateList);
                req.setAttribute("view", "Y");
                udf.reset();
            util.updAccessLog(req,res,"Ifrs Action", "load end");
            return am.findForward("load");
            }
    }
    
    public ActionForward fetchBucket(ActionMapping am, ActionForm af, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Ifrs Action", "load start");
            ReportForm udf = (ReportForm)af;
            ArrayList ary = new ArrayList();
                HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
                HashMap pageDtl=(HashMap)allPageDtl.get("IFRS");
                if(pageDtl==null || pageDtl.size()==0){
                pageDtl=new HashMap();
                pageDtl=util.pagedef("IFRS");
                allPageDtl.put("IFRS",pageDtl);
                }
            String conQP = "";    
                    String frmdte = util.nvl((String)udf.getValue("frmdte"));    
                    String todte = util.nvl((String)udf.getValue("todte"));
                    String bucket = util.nvl((String)udf.getValue("bucket"));
                    if(!bucket.equals(""))
                    conQP = " and ifrs = '"+bucket+"' ";
                ArrayList addtabList = new ArrayList();
                ArrayList  pageList=(ArrayList)pageDtl.get("ADDTAB");
                  if(pageList!=null && pageList.size() >0){
                      for(int j=0;j<pageList.size();j++){
                          HashMap pgDtl= (HashMap)pageList.get(j);
                          String addTab = (String)pgDtl.get("fld_nme");
                          addtabList.add(addTab);
                      }}
            HashMap dataDtl = new HashMap();
            ArrayList asOnDateList = new ArrayList();
            ArrayList ifrsLst = new ArrayList();
            String prvIfrs="";
                if(!frmdte.equals("") && !todte.equals("")){
                    String slctPart = "";
                    String summyPart = "";
                    for(int i=0;i<addtabList.size();i++){
                        String tab = (String)addtabList.get(i);
                       slctPart= slctPart+" to_char("+tab+"_cts ,'99999999999990.000') "+tab+"_cts,to_char(trunc("+tab+"_rte,2),'99999999999990.00') "+tab+"_rte ,to_char(trunc("+tab+"_vlu,2),'9999999999999999990.00') "+tab+"_vlu , ";
                        summyPart = summyPart+" to_char(trunc(sum(trunc("+tab+"_cts, 3)),3),'99999999999990.000') "+tab+"_cts,to_char(trunc(sum(trunc("+tab+"_rte, 2)),2),'99999999999990.00') "+tab+"_rte ,to_char(trunc(sum(trunc("+tab+"_vlu, 2)),2),'9999999999999999990.00') "+tab+"_vlu, ";
                    }
                String conQ = " and trunc(as_on_date) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
                String dataQ = "select ifrs,to_char(as_on_date, 'rrrrmmdd') as_on_dateSrt,to_char(as_on_date, 'dd-mm-rrrr') as_on_date,\n" + 
                "to_char(trunc(open_cts,3),'99999999999990.000') open_cts,to_char(trunc(open_rte,2),'99999999999990.00') open_rte ,to_char(trunc(open_vlu,2),'9999999999999999990.00') open_vlu, \n" + 
                "to_char(trunc(mfgnew_cts,3),'99999999999990.000') mfgnew_cts,to_char(trunc(mfgnew_rte,2),'99999999999990.00') mfgnew_rte ,to_char(trunc(mfgnew_vlu,2),'9999999999999999990.00') mfgnew_vlu, \n" + 
                "to_char(trunc(purnew_cts,3),'99999999999990.000') purnew_cts,to_char(trunc(purnew_rte,2),'99999999999990.00') purnew_rte ,to_char(trunc(purnew_vlu,2),'9999999999999999990.00') purnew_vlu, \n" + 
                "to_char(trunc(sold_cts,3),'99999999999990.000') sold_cts,to_char(trunc(sold_rte,2),'99999999999990.00') sold_rte ,to_char(trunc(sold_vlu,2),'9999999999999999990.00') sold_vlu, \n" + 
                  " to_char(trunc(sold_act_vlu,2),'9999999999999999990.00') sold_act_vlu,\n" +  slctPart+
                  "to_char(trunc(decode(sold_act_vlu,'0','0',  (100-(sold_vlu*100/sold_act_vlu)) ),2),'9999999999999999990.00') diff,"+          
                "to_char(trunc(cts,3),'99999999999990.000') close_cts,to_char(trunc(rte,2),'99999999999990.00') close_rte ,to_char(trunc(vlu,2),'9999999999999999990.00') close_vlu , to_char(trunc(NRV,2),'9999999999999999990.00') NRV_vlu \n" + 
                
               " from IFRS_STK_VLU \n" + 
                "where stt='A' "+conQ+" "+conQP+
                "order by 1,2 desc";
                 ary = new ArrayList();  

                    ArrayList outLst = db.execSqlLst("Collection", dataQ, ary);
                    PreparedStatement pst = (PreparedStatement)outLst.get(0);
                    ResultSet rs = (ResultSet)outLst.get(1);
                 while(rs.next()) {
                     String as_on_date=util.nvl(rs.getString("as_on_date"));
                     String ifrs=util.nvl(rs.getString("ifrs"));
                     if(prvIfrs.equals(""))
                     prvIfrs=ifrs;
                     
                         if(!ifrs.equals(prvIfrs)){
                             dataDtl.put(prvIfrs,ifrsLst);
                             ifrsLst = new ArrayList();
                             prvIfrs=ifrs;
                         }    
                     
                     ifrsLst.add(as_on_date);   
                     dataDtl.put(ifrs+"_"+as_on_date+"_OPEN_CTS",util.nvl(rs.getString("open_cts")));
                     dataDtl.put(ifrs+"_"+as_on_date+"_OPEN_RTE",util.nvl(rs.getString("open_rte")));
                     dataDtl.put(ifrs+"_"+as_on_date+"_OPEN_VLU",util.nvl(rs.getString("open_vlu")));
                     dataDtl.put(ifrs+"_"+as_on_date+"_MFGNEW_CTS",util.nvl(rs.getString("mfgnew_cts")));
                     dataDtl.put(ifrs+"_"+as_on_date+"_MFGNEW_RTE",util.nvl(rs.getString("mfgnew_rte")));
                     dataDtl.put(ifrs+"_"+as_on_date+"_MFGNEW_VLU",util.nvl(rs.getString("mfgnew_vlu")));
                     dataDtl.put(ifrs+"_"+as_on_date+"_PURNEW_CTS",util.nvl(rs.getString("purnew_cts")));
                     dataDtl.put(ifrs+"_"+as_on_date+"_PURNEW_RTE",util.nvl(rs.getString("purnew_rte")));
                     dataDtl.put(ifrs+"_"+as_on_date+"_PURNEW_VLU",util.nvl(rs.getString("purnew_vlu")));
                     dataDtl.put(ifrs+"_"+as_on_date+"_SOLD_CTS",util.nvl(rs.getString("sold_cts")));
                     dataDtl.put(ifrs+"_"+as_on_date+"_SOLD_RTE",util.nvl(rs.getString("sold_rte")));
                     dataDtl.put(ifrs+"_"+as_on_date+"_SOLD_VLU",util.nvl(rs.getString("sold_vlu")));
                     dataDtl.put(ifrs+"_"+as_on_date+"_SOLD_ACTVLU",util.nvl(rs.getString("sold_act_vlu")));
                     dataDtl.put(ifrs+"_"+as_on_date+"_SOLD_DIFF",util.nvl(rs.getString("diff")));
                     dataDtl.put(ifrs+"_"+as_on_date+"_CLOSE_CTS",util.nvl(rs.getString("close_cts")));
                     dataDtl.put(ifrs+"_"+as_on_date+"_CLOSE_RTE",util.nvl(rs.getString("close_rte")));
                     dataDtl.put(ifrs+"_"+as_on_date+"_CLOSE_VLU",util.nvl(rs.getString("close_vlu")));
                     dataDtl.put(ifrs+"_"+as_on_date+"_CLOSE_NRVVLU",util.nvl(rs.getString("NRV_vlu")));
                     for(int i=0;i<addtabList.size();i++){
                         String tab = (String)addtabList.get(i);
                         
                         dataDtl.put(ifrs+"_"+as_on_date+"_"+tab+"_CTS",util.nvl(rs.getString(tab+"_cts")));
                         dataDtl.put(ifrs+"_"+as_on_date+"_"+tab+"_RTE",util.nvl(rs.getString(tab+"_rte")));
                         dataDtl.put(ifrs+"_"+as_on_date+"_"+tab+"_VLU",util.nvl(rs.getString(tab+"_vlu")));
                     }
                 }
                 rs.close(); pst.close();
                 
                    dataQ = "select  b.srt,ifrs ,\n" + 
                    "to_char(trunc(sum(trunc(open_cts, 3)),3),'99999999999990.000') open_cts,to_char(trunc(sum(trunc(open_rte, 2)),2),'99999999999990.00') open_rte ,to_char(trunc(sum(trunc(open_vlu, 2)),2),'9999999999999999990.00') open_vlu, \n" + 
                    "to_char(trunc(sum(trunc(mfgnew_cts,3)),3),'99999999999990.000') mfgnew_cts,to_char(trunc(sum(trunc(mfgnew_rte, 2)),2),'99999999999990.00') mfgnew_rte ,to_char(trunc(sum(trunc(mfgnew_vlu, 2)),2),'9999999999999999990.00') mfgnew_vlu, \n" + 
                    "to_char(trunc(sum(trunc(purnew_cts, 3)),3),'99999999999990.000') purnew_cts,to_char(trunc(sum(trunc(purnew_rte, 2)),2),'99999999999990.00') purnew_rte ,to_char(trunc(sum(trunc(purnew_vlu, 2)),2),'9999999999999999990.00') purnew_vlu, \n" + 
                    "to_char(trunc(sum(trunc(sold_cts, 3)),3),'99999999999990.000') sold_cts,to_char(trunc(sum(trunc(sold_rte, 2)),2),'99999999999990.00') sold_rte ,to_char(trunc(sum(trunc(sold_vlu, 2)),2),'9999999999999999990.00') sold_vlu, \n" + 
                    "to_char(trunc(sum(trunc(cts, 3)),3),'99999999999990.000') close_cts,to_char(trunc(sum(trunc(rte, 2)),2),'99999999999990.00') close_rte ,to_char(trunc(sum(trunc(vlu, 2)),2),'9999999999999999990.00') close_vlu , to_char(trunc(sum(trunc(NRV, 2)),2),'9999999999999999990.00') NRV_vlu ,\n" + 
                    "  to_char(trunc(sum(trunc(sold_act_vlu, 2)),2),'9999999999999999990.00') sold_act_vlu, \n" +summyPart+ 
                    "to_char(trunc(decode(sum(sold_act_vlu),'0','0',  (100-(sum(sold_vlu)*100/sum(sold_act_vlu))) ),2),'9999999999999999990.00') diff from IFRS_STK_VLU  a , prp b \n" + 
                    "where stt='A' "+conQ+" "+conQP+ " and a.ifrs= b.val and b.mprp='IFRS'"+
                    "group by ifrs,b.srt \n" + 
                    "order by 1";
                     ary = new ArrayList();  

                    outLst = db.execSqlLst("Collection", dataQ, ary);
                    pst = (PreparedStatement)outLst.get(0);
                    rs = (ResultSet)outLst.get(1);
                     while(rs.next()) {
                         String ifrs=util.nvl(rs.getString("ifrs"));
                         dataDtl.put(ifrs+"_OPEN_CTS",util.nvl(rs.getString("open_cts")));
                         dataDtl.put(ifrs+"_OPEN_RTE",util.nvl(rs.getString("open_rte")));
                         dataDtl.put(ifrs+"_OPEN_VLU",util.nvl(rs.getString("open_vlu")));
                         dataDtl.put(ifrs+"_MFGNEW_CTS",util.nvl(rs.getString("mfgnew_cts")));
                         dataDtl.put(ifrs+"_MFGNEW_RTE",util.nvl(rs.getString("mfgnew_rte")));
                         dataDtl.put(ifrs+"_MFGNEW_VLU",util.nvl(rs.getString("mfgnew_vlu")));
                         dataDtl.put(ifrs+"_PURNEW_CTS",util.nvl(rs.getString("purnew_cts")));
                         dataDtl.put(ifrs+"_PURNEW_RTE",util.nvl(rs.getString("purnew_rte")));
                         dataDtl.put(ifrs+"_PURNEW_VLU",util.nvl(rs.getString("purnew_vlu")));
                         dataDtl.put(ifrs+"_SOLD_CTS",util.nvl(rs.getString("sold_cts")));
                         dataDtl.put(ifrs+"_SOLD_RTE",util.nvl(rs.getString("sold_rte")));
                         dataDtl.put(ifrs+"_SOLD_VLU",util.nvl(rs.getString("sold_vlu")));
                         dataDtl.put(ifrs+"_SOLD_ACT",util.nvl(rs.getString("sold_act_vlu")));
                         dataDtl.put(ifrs+"_SOLD_DIFF",util.nvl(rs.getString("diff")));
                         dataDtl.put(ifrs+"_CLOSE_CTS",util.nvl(rs.getString("close_cts")));
                         dataDtl.put(ifrs+"_CLOSE_RTE",util.nvl(rs.getString("close_rte")));
                         dataDtl.put(ifrs+"_CLOSE_VLU",util.nvl(rs.getString("close_vlu")));
                         dataDtl.put(ifrs+"_CLOSE_NRVVLU",util.nvl(rs.getString("NRV_vlu")));
                         for(int i=0;i<addtabList.size();i++){
                             String tab = (String)addtabList.get(i);
                             
                             dataDtl.put(ifrs+"_"+tab+"_CTS",util.nvl(rs.getString(tab+"_cts")));
                             dataDtl.put(ifrs+"_"+tab+"_RTE",util.nvl(rs.getString(tab+"_rte")));
                             dataDtl.put(ifrs+"_"+tab+"_VLU",util.nvl(rs.getString(tab+"_vlu")));
                         }
                         asOnDateList.add(ifrs);
                     }
                     rs.close(); pst.close();
                 if(!prvIfrs.equals(""))
                 dataDtl.put(prvIfrs,ifrsLst);
                }
                
                req.setAttribute("dataDtl", dataDtl);
                req.setAttribute("asOnDateList", asOnDateList);
                req.setAttribute("view", "Y");
                req.setAttribute("fetch", "BucketWise");
                udf.reset();
            util.updAccessLog(req,res,"Ifrs Action", "load end");
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
                util.updAccessLog(req,res,"Ifrs Action", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Ifrs Action", "init");
            }
            }
            return rtnPg;
            }
    
    public ActionForward getPktDtl(ActionMapping am, ActionForm af, HttpServletRequest req,
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
               util.updAccessLog(req,res,"Ifrs Action", "Packet Detail start");
               ReportForm udf = (ReportForm)af;
                String cnt =  util.nvl((String)info.getDmbsInfoLst().get("CNT"));
               String asondate = util.nvl((String)req.getParameter("asondate"));
               String asontodate = util.nvl((String)req.getParameter("asontodate"));
               String bkt = util.nvl((String)req.getParameter("bkt"));
               String sttName = util.nvl((String)req.getParameter("sttName"));  
               ArrayList pktList = new ArrayList();
               ArrayList itmHdr = new ArrayList();
                   itmHdr.add("VNM");  
                   itmHdr.add("STK_STT");  
                   itmHdr.add("CTS");  
                   itmHdr.add("RTE");  
                   itmHdr.add("VLU");  
                   itmHdr.add("SHAPE");
                   itmHdr.add("SIZE");  
                   itmHdr.add("COLOR");  
                   itmHdr.add("PU");
                   itmHdr.add("BOX_TYPE");  
                   itmHdr.add("BOX_ID");  
                   itmHdr.add("DTE");
                   itmHdr.add("TYPE");
                   itmHdr.add("IFRS");
                   itmHdr.add("IFRS_YN");
                   itmHdr.add("RMK 1");
                   itmHdr.add("RMK 2");
                   String descr=" , '1' des ";
                   if(cnt.equalsIgnoreCase("HK")){
                       itmHdr = new ArrayList();
                        itmHdr.add("VNM");
                       itmHdr.add("CTS");  
                       itmHdr.add("DTE");  
                       itmHdr.add("CP");  
                       itmHdr.add("CP VLU"); 
                       itmHdr.add("SALE RTE"); 
                       itmHdr.add("SALE VLU"); 
                       itmHdr.add("IFRS GRP");
                       itmHdr.add("DESC");
                       itmHdr.add("INVOICE NO");
                       descr=" , GET_IFRS_DSCR(nvl(in_ifrs, out_ifrs)) des  ";
                   }
                 
                   String getQ ="";
                   String bktQ = "";
               
                   ArrayList ary = new ArrayList();
                   if(!asondate.equals("") && !bkt.equals("") && !sttName.equals("")){ 
                       if(sttName.equals("MFG") || sttName.equals("PUR")){
                       String dteStr = " and trunc(in_dte)  between to_date(?,'dd-mm-yy') and to_date(?,'dd-mm-yy') ";
                                ary.add(asondate);
                               
                       if(cnt.equalsIgnoreCase("HK")){
                           //dteStr = "  last_day(in_dte) between  to_date(?,'dd-mm-yy') and to_date(?,'dd-mm-yy') ";
                               if(asontodate.equals(""))
                                   dteStr = " and trunc(in_dte) between to_date(to_char(TO_DATE(substr(?,4,9),'mm-yyyy'),'dd-mm-yyyy'),'dd-mm-yyyy') and last_day(to_date(to_char(TO_DATE(substr(?,4,9),'mm-yyyy'),'dd-mm-yyyy'),'dd-mm-yyyy') ) ";
                               else
                                    dteStr = " and trunc(in_dte)  between to_date(?,'dd-mm-yy') and to_date(?,'dd-mm-yy') ";
                       }
                         if(asontodate.equals("")){
                            ary.add(asondate);
                         }else{
                            ary.add(asontodate); 
                         }
                       if(!bkt.equals("ALL")){
                               bktQ =" and in_ifrs=? ";
                                ary.add(bkt);
                            }
                           getQ = "select vnm,stk_stt,to_char(trunc(in_cts,3),'99999.000') cts,nvl(in_cost,in_rte)  rte, out_rte ,to_char(in_cts*out_rte,'99999999990.90') outvlu, ifrs_yn,nvl(in_ifrs,out_ifrs) ifrs, to_char(in_cts*nvl(in_cost,in_rte),'99999999990.90') vlu,rem1,rem2, sh,sz,co,pu,box_typ,box_id,to_char(in_dte,'dd-mm-yyyy') dte,in_typ typ " +descr+
                               "from ifrs_stk where 1=1 " +dteStr+""+ bktQ +" and in_typ = ?  order by in_dte desc";
                           ary.add(sttName);
                       }else if(sttName.equals("CLOSE")){
                           String dteStr = " and trunc(in_dte)  <=  to_date(?,'dd-mm-yy') and (out_dte is null or trunc(out_dte)  > to_date(?,'dd-mm-yy')) and a.stk_idn not in (select stk_idn from ifrs_stk_closing_1718 i where i.stk_stt like '%MRG') ";
                          
                           if(asontodate.equals("")){
                              ary.add(asondate);
                               ary.add(asondate);
                           }else{
                              ary.add(asontodate); 
                               ary.add(asontodate); 
                           }
                           if(!bkt.equals("ALL")){
                                 bktQ =" and in_ifrs=? ";
                                  ary.add(bkt);
                              }
                             getQ = "select vnm,stk_stt,to_char(trunc(in_cts,3),'99999.000') cts,nvl(in_cost,in_rte)  rte, out_rte ,to_char(in_cts*out_rte,'99999999990.90') outvlu, ifrs_yn,nvl(in_ifrs,out_ifrs) ifrs, to_char(in_cts*nvl(in_cost,in_rte),'99999999990.90') vlu,rem1,rem2, sh,sz,co,pu,box_typ,box_id,to_char(in_dte,'dd-mm-yyyy') dte,in_typ typ " +descr+
                                 "from ifrs_stk where 1=1 " +dteStr+""+ bktQ +"   order by in_dte desc";
                           
                           
                       }else{
                           
                        String dteStr = " and trunc(out_dte)  between to_date(?,'dd-mm-yy') and to_date(?,'dd-mm-yy') ";
                                 ary.add(asondate);
                                
                        if(cnt.equalsIgnoreCase("HK")){
                            dteStr = " and last_day(out_dte) between  to_date(?,'dd-mm-yy') and to_date(?,'dd-mm-yy') ";
                        }
                        if(asontodate.equals("")){
                           ary.add(asondate);
                        }else{
                           ary.add(asontodate); 
                        }
                           
                           
                         if(!bkt.equals("ALL")){
                                   bktQ =" and out_ifrs=? ";
                                    ary.add(bkt);
                         }
                               getQ = "select vnm,stk_stt,to_char(trunc(out_cts,3),'999999.000') cts,out_cost rte,out_rte ,to_char(out_cts*out_rte,'99999999990.90') outvlu,ifrs_yn, nvl(in_ifrs,out_ifrs) ifrs, rem1,rem2, to_char(out_cts*out_cost,'99999999990.90') vlu, sh,sz,co,pu,box_typ,box_id,to_char(out_dte,'dd-mm-yyyy') dte,out_typ  typ " +descr+
                                   "from ifrs_stk where 1=1 "+dteStr+ bktQ +" and out_typ =?  \n" + 
                                   "order by out_dte desc";
                        ary.add(sttName);
                    }

                           
                          
                          
                           double ttlCts=0;
                           double ttlVlu = 0;

                       ArrayList outLst = db.execSqlLst("Collection", getQ, ary);
                       PreparedStatement pst = (PreparedStatement)outLst.get(0);
                       ResultSet rs = (ResultSet)outLst.get(1);
                           while(rs.next()) {
                               double cts =Double.parseDouble(util.nvl(rs.getString("cts"),"0"));
                               double vlu =Double.parseDouble(util.nvl(rs.getString("vlu"),"0"));
                               ttlCts=ttlCts+cts;
                               ttlVlu = ttlVlu+vlu;
                               HashMap map = new HashMap();
                               map.put("VNM",util.nvl(rs.getString("vnm")));
                               map.put("STK_STT",util.nvl(rs.getString("stk_stt")));
                               map.put("CTS",util.nvl(rs.getString("cts")));
                               map.put("RTE",util.nvl(rs.getString("rte")));
                               map.put("VLU",util.nvl(rs.getString("vlu")));
                               map.put("SHAPE",util.nvl(rs.getString("sh")));
                               map.put("SIZE",util.nvl(rs.getString("sz")));
                               map.put("COLOR",util.nvl(rs.getString("co")));
                               map.put("PU",util.nvl(rs.getString("pu")));
                               map.put("BOX_TYPE",util.nvl(rs.getString("box_typ")));
                               map.put("BOX_ID",util.nvl(rs.getString("box_id")));
                               map.put("DTE",util.nvl(rs.getString("dte")));
                               map.put("TYPE",util.nvl(rs.getString("typ")));
                               map.put("IFRS_YN",util.nvl(rs.getString("ifrs_yn")));
                               map.put("IFRS",util.nvl(rs.getString("ifrs")));
                               map.put("RMK 1",util.nvl(rs.getString("rem1")));
                               map.put("RMK 2",util.nvl(rs.getString("rem2")));
                               map.put("DESC",util.nvl(rs.getString("des")));
                               map.put("IFRS GRP",util.nvl(rs.getString("ifrs")));
                               map.put("INVOICE NO",util.nvl(rs.getString("rem1")));
                               map.put("CP",util.nvl(rs.getString("rte")));
                               map.put("CP VLU",util.nvl(rs.getString("vlu")));
                               map.put("SALE RTE",util.nvl(rs.getString("out_rte")));
                               map.put("SALE VLU",util.nvl(rs.getString("outvlu")));
                               pktList.add(map);
                           }                      
                       req.setAttribute("TTLCTS", String.valueOf(util.roundToDecimals(ttlCts,2)));
                      req.setAttribute("TTLVLU", String.valueOf(util.roundToDecimals(ttlVlu,2)));
                       session.setAttribute("pktList", pktList);
                       session.setAttribute("itemHdr",itmHdr);
                       req.setAttribute("EXCL", "YES");
                   } 
                   
               util.updAccessLog(req,res,"Ifrs Action", "Packet Detail end");
               return am.findForward("pktDtl");   
               }
           }
    
    public ActionForward summaryBucket(ActionMapping am, ActionForm af, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Ifrs Action", "load start");
            ReportForm udf = (ReportForm)af;
            ArrayList ary = new ArrayList();
            String conQP = "";   
                HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
                HashMap pageDtl=(HashMap)allPageDtl.get("IFRS");
                if(pageDtl==null || pageDtl.size()==0){
                pageDtl=new HashMap();
                pageDtl=util.pagedef("IFRS");
                allPageDtl.put("IFRS",pageDtl);
                }
                    String frmdte = util.nvl((String)udf.getValue("frmdte"));    
                    String todte = util.nvl((String)udf.getValue("todte"));
                    String bucket = util.nvl((String)udf.getValue("bucket"));
                
                ArrayList addtabList = new ArrayList();
                ArrayList  pageList=(ArrayList)pageDtl.get("ADDTAB");
                  if(pageList!=null && pageList.size() >0){
                      for(int j=0;j<pageList.size();j++){
                          HashMap pgDtl= (HashMap)pageList.get(j);
                          String addTab = (String)pgDtl.get("fld_nme");
                          addtabList.add(addTab);
                      }}

            HashMap dataDtl = new HashMap();
            ArrayList asOnDateList = new ArrayList();
            ArrayList ifrsLst = new ArrayList();
                String prvAsOnDate="";
                if(!frmdte.equals("") && !todte.equals("")){
                String as_on_date=frmdte+"To"+todte;
                String slctPart = "";
              
                String selQuPart ="";
                String summyPart="";
                String summyQuPart = "";
                for(int i=0;i<addtabList.size();i++){
                  String tab = (String)addtabList.get(i);
              slctPart= slctPart+" , sum("+tab+"_cts) "+tab+"_cts, sum("+tab+"_vlu) "+tab+"_vlu ";
              selQuPart = selQuPart+" , "+tab+"_cts, "+tab+"_vlu, trunc(decode("+tab+"_cts, 0, 0, "+tab+"_vlu/"+tab+"_cts), 2) "+tab+"_rte";
                     summyPart=summyPart+" , sum("+tab+"_cts) "+tab+"_cts, sum("+tab+"_vlu) "+tab+"_vlu ";
                     summyQuPart=summyQuPart+" , "+tab+"_cts, "+tab+"_vlu, trunc(decode("+tab+"_cts, 0, 0, "+tab+"_vlu/"+tab+"_cts), 2) "+tab+"_rte ";
                
                 }
                
                String conQ = " trunc(as_on_date) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
                String dataQ = "with smry as (\n" + 
                "  select ifrs\n" + 
                "    , sum(open_cts) open_cts, sum(open_vlu) open_vlu\n" + 
                "    , sum(mfgnew_cts) mfgnew_cts, sum(mfgnew_vlu) mfgnew_vlu\n" + 
                "    , sum(purnew_cts) purnew_cts, sum(purnew_vlu) purnew_vlu\n" + slctPart+
                "     , sum(sold_cts) sold_cts, sum(sold_vlu) sold_vlu,sum(sold_act_vlu) sold_act_vlu\n" + 
                "    , sum(cts) cts, sum(vlu) vlu,sum(NRV) NRV\n" + 
                "  from ifrs_stk_vlu \n" + 
                "  where " + conQ +"\n"+ 
                "  group by ifrs\n" + 
                ")\n" + 
                "select ifrs\n" + 
                "  , open_cts, open_vlu, trunc(decode(open_cts, 0, 0, open_vlu/open_cts), 2) open_rte\n" + 
                "  , mfgnew_cts, mfgnew_vlu, trunc(decode(mfgnew_cts, 0, 0, mfgnew_vlu/mfgnew_cts), 2) mfgnew_rte\n" + 
                "  , purnew_cts, purnew_vlu, trunc(decode(purnew_cts, 0, 0, purnew_vlu/purnew_cts), 2) purnew_rte\n" +summyQuPart+ 
                "   , sold_cts, sold_vlu,sold_act_vlu , trunc(decode(sold_act_vlu, 0, 0, (100-(sold_vlu*100/sold_act_vlu))), 2) sold_diff, trunc(decode(sold_cts, 0, 0, sold_vlu/sold_cts), 2) sold_rte\n" + 
                "  , cts close_cts, vlu close_vlu,NRV nrv_vlu, trunc(decode(cts, 0, 0, vlu/cts), 2) close_rte\n" + 
                "from smry";
                 ary = new ArrayList();  

                         ArrayList outLst = db.execSqlLst("Collection", dataQ, ary);
                         PreparedStatement pst = (PreparedStatement)outLst.get(0);
                         ResultSet rs = (ResultSet)outLst.get(1);
                 while(rs.next()) {
                     String ifrs=util.nvl(rs.getString("ifrs"));
                     if(prvAsOnDate.equals(""))
                     prvAsOnDate=as_on_date;
                     
                         if(!as_on_date.equals(prvAsOnDate)){
                             dataDtl.put(prvAsOnDate,ifrsLst);
                             ifrsLst = new ArrayList();
                             prvAsOnDate=as_on_date;
                         }    
                     
                     ifrsLst.add(ifrs);   
                      dataDtl.put(as_on_date+"_"+ifrs+"_MFGNEW_CTS",util.nvl(rs.getString("mfgnew_cts")));
                     dataDtl.put(as_on_date+"_"+ifrs+"_MFGNEW_RTE",util.nvl(rs.getString("mfgnew_rte")));
                     dataDtl.put(as_on_date+"_"+ifrs+"_MFGNEW_VLU",util.nvl(rs.getString("mfgnew_vlu")));
                     dataDtl.put(as_on_date+"_"+ifrs+"_PURNEW_CTS",util.nvl(rs.getString("purnew_cts")));
                     dataDtl.put(as_on_date+"_"+ifrs+"_PURNEW_RTE",util.nvl(rs.getString("purnew_rte")));
                     dataDtl.put(as_on_date+"_"+ifrs+"_PURNEW_VLU",util.nvl(rs.getString("purnew_vlu")));
                     dataDtl.put(as_on_date+"_"+ifrs+"_SOLD_CTS",util.nvl(rs.getString("sold_cts")));
                     dataDtl.put(as_on_date+"_"+ifrs+"_SOLD_RTE",util.nvl(rs.getString("sold_rte")));
                     dataDtl.put(as_on_date+"_"+ifrs+"_SOLD_VLU",util.nvl(rs.getString("sold_vlu")));
                     dataDtl.put(as_on_date+"_"+ifrs+"_SOLD_ACTVLU",util.nvl(rs.getString("sold_act_vlu")));
                     dataDtl.put(as_on_date+"_"+ifrs+"_SOLD_DIFF",util.nvl(rs.getString("sold_diff")));
                     
                        for(int i=0;i<addtabList.size();i++){
                            String tab = (String)addtabList.get(i);
                            
                            dataDtl.put(as_on_date+"_"+ifrs+"_"+tab+"_CTS",util.nvl(rs.getString(tab+"_cts")));
                            dataDtl.put(as_on_date+"_"+ifrs+"_"+tab+"_RTE",util.nvl(rs.getString(tab+"_rte")));
                            dataDtl.put(as_on_date+"_"+ifrs+"_"+tab+"_VLU",util.nvl(rs.getString(tab+"_vlu")));
                        }
                    }
                 rs.close(); pst.close();
                 
                    dataQ = "with smry as (\n" + 
                    "  select \n" + 
                    "     sum(open_cts) open_cts, sum(open_vlu) open_vlu\n" + 
                    "    , sum(mfgnew_cts) mfgnew_cts, sum(mfgnew_vlu) mfgnew_vlu\n" + 
                    "    , sum(purnew_cts) purnew_cts, sum(purnew_vlu) purnew_vlu\n" + 
                    "    , sum(sold_cts) sold_cts, sum(sold_vlu) sold_vlu,sum(sold_act_vlu) sold_act_vlu\n" + 
                    "    , sum(cts) cts, sum(vlu) vlu,sum(nrv) nrv\n" +summyPart+
                    "  from ifrs_stk_vlu \n" + 
                    "  where " + conQ +"\n"+ 
                    ")\n" + 
                    "select\n" + 
                    "   open_cts, open_vlu, trunc(decode(open_cts, 0, 0, open_vlu/open_cts), 2) open_rte\n" + 
                    "  , mfgnew_cts, mfgnew_vlu, trunc(decode(mfgnew_cts, 0, 0, mfgnew_vlu/mfgnew_cts), 2) mfgnew_rte\n" + 
                    "  , purnew_cts, purnew_vlu, trunc(decode(purnew_cts, 0, 0, purnew_vlu/purnew_cts), 2) purnew_rte\n" + 
                    "  , sold_cts, sold_vlu, trunc(decode(sold_cts, 0, 0, sold_vlu/sold_cts), 2) sold_rte\n" + 
                       ",  sold_act_vlu , trunc(decode(sold_act_vlu, 0, 0, (100-(sold_vlu*100/sold_act_vlu))), 2) sold_diff,"+
                    "   cts close_cts, vlu close_vlu,nrv nrv_vlu, trunc(decode(cts, 0, 0, vlu/cts), 2) close_rte\n" +summyQuPart+ 
                    "from smry";
                     ary = new ArrayList();  

                         outLst = db.execSqlLst("Collection", dataQ, ary);
                         pst = (PreparedStatement)outLst.get(0);
                         rs = (ResultSet)outLst.get(1);
                     while(rs.next()) {
                           dataDtl.put(as_on_date+"_MFGNEW_CTS",util.nvl(rs.getString("mfgnew_cts")));
                         dataDtl.put(as_on_date+"_MFGNEW_RTE",util.nvl(rs.getString("mfgnew_rte")));
                         dataDtl.put(as_on_date+"_MFGNEW_VLU",util.nvl(rs.getString("mfgnew_vlu")));
                         dataDtl.put(as_on_date+"_PURNEW_CTS",util.nvl(rs.getString("purnew_cts")));
                         dataDtl.put(as_on_date+"_PURNEW_RTE",util.nvl(rs.getString("purnew_rte")));
                         dataDtl.put(as_on_date+"_PURNEW_VLU",util.nvl(rs.getString("purnew_vlu")));
                         dataDtl.put(as_on_date+"_SOLD_CTS",util.nvl(rs.getString("sold_cts")));
                         dataDtl.put(as_on_date+"_SOLD_RTE",util.nvl(rs.getString("sold_rte")));
                         dataDtl.put(as_on_date+"_SOLD_VLU",util.nvl(rs.getString("sold_vlu")));
                         dataDtl.put(as_on_date+"_SOLD_ACT",util.nvl(rs.getString("sold_act_vlu")));
                         dataDtl.put(as_on_date+"_SOLD_DIFF",util.nvl(rs.getString("sold_diff")));
                         
                         for(int i=0;i<addtabList.size();i++){
                             String tab = (String)addtabList.get(i);
                             
                             dataDtl.put(as_on_date+"_"+tab+"_CTS",util.nvl(rs.getString(tab+"_cts")));
                             dataDtl.put(as_on_date+"_"+tab+"_RTE",util.nvl(rs.getString(tab+"_rte")));
                             dataDtl.put(as_on_date+"_"+tab+"_VLU",util.nvl(rs.getString(tab+"_vlu")));
                         }
                            asOnDateList.add(as_on_date);
                     }
                     rs.close(); pst.close();
                     
                     
                     
                         String conOpenQ = " trunc(as_on_date) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+frmdte+"' , 'dd-mm-yyyy') ";
                         String dataOpenQ = "with smry as (\n" + 
                         "  select ifrs\n" + 
                         "    , sum(open_cts) open_cts, sum(open_vlu) open_vlu\n" + 
                        "  from ifrs_stk_vlu \n" + 
                         "  where " + conOpenQ +"\n"+ 
                         "  group by ifrs\n" + 
                         ")\n" + 
                         "select ifrs\n" + 
                         "  , open_cts, open_vlu, trunc(decode(open_cts, 0, 0, open_vlu/open_cts), 2) open_rte\n" + 
                         " from smry";
                          ary = new ArrayList();  

                         outLst = db.execSqlLst("Collection", dataOpenQ, ary);
                         pst = (PreparedStatement)outLst.get(0);
                         rs = (ResultSet)outLst.get(1);
                          while(rs.next()) {
                              String ifrs=util.nvl(rs.getString("ifrs"));
                              dataDtl.put(as_on_date+"_"+ifrs+"_OPEN_CTS",util.nvl(rs.getString("open_cts")));
                              dataDtl.put(as_on_date+"_"+ifrs+"_OPEN_RTE",util.nvl(rs.getString("open_rte")));
                              dataDtl.put(as_on_date+"_"+ifrs+"_OPEN_VLU",util.nvl(rs.getString("open_vlu")));
                             }
                          rs.close(); pst.close();
                          
                         String conOpenSummQ = " trunc(as_on_date) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+frmdte+"' , 'dd-mm-yyyy') ";
                         
                          String   dataOpenSummQ = "with smry as (\n" + 
                             "  select \n" + 
                             "     sum(open_cts) open_cts, sum(open_vlu) open_vlu\n" + 
                               "  from ifrs_stk_vlu \n" + 
                             "  where " + conOpenSummQ +"\n"+ 
                             ")\n" + 
                             "select\n" + 
                             "   open_cts, open_vlu, trunc(decode(open_cts, 0, 0, open_vlu/open_cts), 2) open_rte\n" + 
                                  "from smry";
                              ary = new ArrayList();  

                         outLst = db.execSqlLst("Collection", dataOpenSummQ, ary);
                         pst = (PreparedStatement)outLst.get(0);
                         rs = (ResultSet)outLst.get(1);
                              while(rs.next()) {
                                 dataDtl.put(as_on_date+"_OPEN_CTS",util.nvl(rs.getString("open_cts")));
                                  dataDtl.put(as_on_date+"_OPEN_RTE",util.nvl(rs.getString("open_rte")));
                                  dataDtl.put(as_on_date+"_OPEN_VLU",util.nvl(rs.getString("open_vlu")));
                                
                              }
                              rs.close(); pst.close();
                              
                         String conCloseQ = " trunc(as_on_date) between to_date('"+todte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
                         String dataCloseQ = "with smry as (\n" + 
                         "  select ifrs\n" + 
                          "  , sum(cts) cts, sum(vlu) vlu, sum(NRV) nrv \n" + 
                         "  from ifrs_stk_vlu \n" + 
                         "  where " + conCloseQ +"\n"+ 
                         "  group by ifrs\n" + 
                         ")\n" + 
                         "select ifrs\n" + 
                          "  , cts close_cts, vlu close_vlu,nrv nrv_vlu, trunc(decode(cts, 0, 0, vlu/cts), 2) close_rte\n" + 
                         "from smry";
                          ary = new ArrayList();  

                         outLst = db.execSqlLst("Collection", dataCloseQ, ary);
                         pst = (PreparedStatement)outLst.get(0);
                         rs = (ResultSet)outLst.get(1);
                          while(rs.next()) {
                              String ifrs=util.nvl(rs.getString("ifrs"));
                              
                              
                              dataDtl.put(as_on_date+"_"+ifrs+"_CLOSE_CTS",util.nvl(rs.getString("close_cts")));
                              dataDtl.put(as_on_date+"_"+ifrs+"_CLOSE_RTE",util.nvl(rs.getString("close_rte")));
                              dataDtl.put(as_on_date+"_"+ifrs+"_CLOSE_VLU",util.nvl(rs.getString("close_vlu")));
                                 dataDtl.put(as_on_date+"_"+ifrs+"_CLOSE_NRVVLU",util.nvl(rs.getString("nrv_vlu")));

                             }
                          rs.close(); pst.close();
                          
                         String conCloseSummQ = " trunc(as_on_date) between to_date('"+todte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
                         
                          String   dataSummCloseQ = "with smry as (\n" + 
                             "  select \n" + 
                              "     sum(cts) cts, sum(vlu) vlu , sum(NRV) nrv \n" + 
                             "  from ifrs_stk_vlu \n" + 
                             "  where " + conCloseSummQ +"\n"+ 
                             ")\n" + 
                             "select\n" + 
                              "  cts close_cts, vlu close_vlu,nrv nrv_vlu, trunc(decode(cts, 0, 0, vlu/cts), 2) close_rte\n" + 
                             "from smry";
                              ary = new ArrayList();  
  
                         outLst = db.execSqlLst("Collection", dataSummCloseQ, ary);
                         pst = (PreparedStatement)outLst.get(0);
                         rs = (ResultSet)outLst.get(1);
                              while(rs.next()) {
                                 dataDtl.put(as_on_date+"_CLOSE_CTS",util.nvl(rs.getString("close_cts")));
                                  dataDtl.put(as_on_date+"_CLOSE_RTE",util.nvl(rs.getString("close_rte")));
                                  dataDtl.put(as_on_date+"_CLOSE_VLU",util.nvl(rs.getString("close_vlu")));
                                  dataDtl.put(as_on_date+"_CLOSE_NRVVLU",util.nvl(rs.getString("nrv_vlu")));
                                  
                              }
                              rs.close(); pst.close();
                              
                              
                              
                        
                     if(!prvAsOnDate.equals(""))
                     dataDtl.put(prvAsOnDate,ifrsLst);
                     }
                
                req.setAttribute("dataDtl", dataDtl);
                req.setAttribute("asOnDateList", asOnDateList);
                req.setAttribute("view", "Y");
                req.setAttribute("fetch", "Summary");
                udf.reset();
            util.updAccessLog(req,res,"Ifrs Action", "load end");
            return am.findForward("load");
            }
    }

}