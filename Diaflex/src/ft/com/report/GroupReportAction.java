package ft.com.report;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import ft.com.DBMgr;
import ft.com.DBUtil;

import ft.com.HomeAction;
import ft.com.InfoMgr;

import ft.com.JSONParser;
import ft.com.dao.JsonDao;

import ft.com.dao.JsonResultDao;

import java.io.IOException;

import java.math.BigDecimal;

import java.math.RoundingMode;

import java.sql.Connection;

import java.util.ArrayList;

import java.util.Collections;

import java.util.HashMap;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import org.json.JSONObject;

public class GroupReportAction extends DispatchAction {

    public GroupReportAction() {
        super();
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
             util.getOpenCursorConnection(db,util,info);
             util.updAccessLog(req,res,"Group Report", "load start");
             String serviceUrl = util.nvl((String)info.getDmbsInfoLst().get("DIAFLEXSERVICEURL"));
             String cnt = util.nvl((String)info.getDmbsInfoLst().get("CNT")).toUpperCase();
             String location = util.nvl(req.getParameter("LOC")); 
             String labstock = util.nvl(req.getParameter("labstock")); 
             String pkt_ty = util.nvl(req.getParameter("PKT_TY"));
             String mdl="GROUP_VIEW";
             String df="STOCK_SUMMARY";
             if(pkt_ty.equals("MIX")){
                 mdl="GROUP_MIX_VIEW";
                 df="MIX_STOCK_SUMMARY";
             }
             String[] paramstr={location,labstock,mdl,pkt_ty};
             JSONObject jObj = new JSONObject();
             jObj.put("dbName",cnt );
             jObj.put("inputMap", paramstr);
             boolean isSuccess = true;
             ArrayList pktDtlList = new ArrayList();
             System.out.print(jObj.toString());
             serviceUrl=serviceUrl+"/ReportREST/reportService/groupAction";
             JsonResultDao jsonResult =null;
             try {
              JSONParser jsonUtil = new JSONParser();
             JsonDao jsonDao = new JsonDao();
             jsonDao.setServiceUrl(serviceUrl);
             jsonDao.setJsonObject(jObj);
            String jsonInString = jsonUtil.getJsonString(jsonDao);
              	
            //    String  jsonInString="{\"status\":\"s\", \"result\": [{\"name\":\"mkyong\",\"age\":29},{ \"name\" : \"mkyong\",\"age\" : 29}]}";
             if(!jsonInString.equals("FAIL")){
                 
                  ObjectMapper mapper = new ObjectMapper();
                 jsonResult = mapper.readValue(jsonInString, JsonResultDao.class);
              }else
             isSuccess=false;
             } catch (JsonMappingException jme) {
             // TODO: Add catch code
             jme.printStackTrace();
             isSuccess=false;
             } catch (IOException ioe) {
             // TODO: Add catch code
             ioe.printStackTrace();
             isSuccess=false;
             }
            if(jsonResult!=null && isSuccess){                 
                String status = util.nvl(jsonResult.getStatus());
                if(status.equals("FAIL"))
                     isSuccess=false; 
                else
                   pktDtlList = jsonResult.getRESULT();
              }
                   
             if(pktDtlList!=null && pktDtlList.size()>0){
                 HashMap prp = info.getPrp();
                 HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
                 HashMap pageDtl=(HashMap)allPageDtl.get(df);
                 if(pageDtl==null || pageDtl.size()==0){
                 pageDtl=new HashMap();
                 pageDtl=util.pagedef(df);
                 allPageDtl.put(df,pageDtl);
                 }
                 String level1="";
                 String level2="";
                ArrayList level1List = new ArrayList();
                 ArrayList pageList=(ArrayList)pageDtl.get("LEVEL1");
                 if(pageList!=null && pageList.size() >0){
                 for(int j=0;j<pageList.size();j++){
                 HashMap pageDtlMap=(HashMap)pageList.get(j);
                 level1=(String)pageDtlMap.get("dflt_val");
                 level1List=(ArrayList)prp.get(level1+"V");
                 }}
                 
                 pageList=(ArrayList)pageDtl.get("LEVEL2");
                 if(pageList!=null && pageList.size() >0){
                 for(int j=0;j<pageList.size();j++){
                 HashMap pageDtlMap=(HashMap)pageList.get(j);
                 level2=(String)pageDtlMap.get("dflt_val");
                 }}
                 HashMap GroupDtl=new HashMap();
                 ArrayList level1dbList = new ArrayList();
                 ArrayList level2dbList = new ArrayList();

                 ArrayList grplist = jsonResult.getGrpList();
                 if(grplist!=null && grplist.size()>0){
                   
                     for(int i=0;i<grplist.size();i++){
                         String grp = (String)grplist.get(i);
                             double ttlCts = 0;
                             double ttlVlu = 0 ;
                             double avgRte = 0 ;

                           BigDecimal ttlBigCts = new BigDecimal(ttlCts);
                           BigDecimal avgBigRte = new BigDecimal(avgRte);
                           BigDecimal ttlBigVlu = new BigDecimal(ttlVlu);
                             
                       for(int j=0;j<level1List.size();j++){
                         String lvl1Prp = (String)level1List.get(j);
                         for(int k=0;k<pktDtlList.size();k++){
                          HashMap pktDtl = (HashMap)pktDtlList.get(k);
                          String lvl1Val = util.nvl((String)pktDtl.get(level1));
                          String sttVal = util.nvl((String)pktDtl.get(level2));
                             if(lvl1Prp.equals(lvl1Val) && sttVal.equals(grp)){
                                 if(!level1dbList.contains(lvl1Val))
                                 level1dbList.add(lvl1Val);
                                 if(!level2dbList.contains(grp))
                                 level2dbList.add(grp);
                                 String count = util.nvl((String)GroupDtl.get(grp+"_"+lvl1Val+"_CNT"),"0");
                                 count= String.valueOf(Integer.parseInt(count)+1);
                                 GroupDtl.put(grp+"_"+lvl1Val+"_CNT",count);
                                 String cts = util.nvl((String)pktDtl.get("cts"),"0");
                                 String rte = util.nvl((String)pktDtl.get("quot"),"0");
                                 String amt = util.nvl((String)pktDtl.get("amt"),"0");
                                 
                                 BigDecimal BigCts = new BigDecimal(cts);
                                 BigDecimal BigRte = new BigDecimal(avgRte);
                                 BigDecimal BigVlu = new BigDecimal(ttlVlu);
                                 ttlBigCts.add(BigCts);
                                 ttlBigVlu.add(BigVlu);
                             }}
                        }
                             avgBigRte = ttlBigVlu.divide(ttlBigCts,4,RoundingMode.HALF_EVEN);
                             GroupDtl.put("TTLCTS_"+grp,String.valueOf(ttlBigCts));
                             GroupDtl.put("TTLVLU_"+grp,String.valueOf(ttlBigCts));
                             GroupDtl.put("AVTRTE_"+grp,String.valueOf(avgBigRte));
                         }
                 }
                 req.setAttribute("lvl1List", level1dbList); 
                 req.setAttribute("lvl2List", level2dbList);
                 req.setAttribute("level1", level1); 
                 req.setAttribute("level2", level2);
                 req.setAttribute("GroupDtl", GroupDtl);
                 session.setAttribute("PKTDTLLIST", pktDtlList);
             }
            
             
             util.updAccessLog(req,res,"Group Report", "load end");
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
                util.updAccessLog(req,res,"Group Report", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Group Report", "init");
            }
            }
            return rtnPg;
            }
}
