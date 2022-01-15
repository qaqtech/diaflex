package ft.com.jasperReport;

import com.google.gson.Gson;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import java.io.File;
import java.io.FileWriter;

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

public class SaleDeliveryReport  extends DispatchAction {
    public SaleDeliveryReport() {
        super();
    }
    
    
    public ActionForward load(ActionMapping am, ActionForm form, HttpServletRequest req, HttpServletResponse res)
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
            JasperReportForm udf = (JasperReportForm)form;
            udf.reset();
            
            return am.findForward("load"); 
        }
     }
    
    public ActionForward Fetch(ActionMapping am, ActionForm form, HttpServletRequest req, HttpServletResponse res)
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
            JasperReportForm udf = (JasperReportForm)form;
            String dtefrom = " trunc(sysdate) ";
            String dteto = " trunc(sysdate) ";
            String dfr = util.nvl((String)udf.getValue("dtefr"));
            String dto = util.nvl((String)udf.getValue("dteto"));
            
            if(!dfr.equals(""))
              dtefrom = "to_date('"+dfr+"' , 'dd-mm-yyyy')";
            
            if(!dto.equals(""))
              dteto = "to_date('"+dto+"' , 'dd-mm-yyyy')";
           
            HashMap viewMap = ViewPrpLst(req);
            String pvPrpStr = (String)viewMap.get("STR");
            ArrayList pvPrpLst = (ArrayList)viewMap.get("PVLIST");
            ArrayList lprpLst = (ArrayList)viewMap.get("LPRPLIST");
            ArrayList pktDtlList = new ArrayList();
            HashMap empMap = new HashMap();
            empMap.put("DLVIDN", "");empMap.put("DLVNME", "");empMap.put("SALIDN", "");
            empMap.put("DLVDTE", ""); empMap.put("SALNME", "");empMap.put("VNM", "");
            for(int i=0;i<lprpLst.size();i++){
                String lprpKey = (String)lprpLst.get(i);
                empMap.put(lprpKey,"");
            }
            pktDtlList.add(empMap);
            String dataSql ="with STKDTL as  (\n" + 
            "select a.idn dlvId ,trunc(ms.cts,2) cts,  byr.get_nm(a.nme_idn) dlvNmeIdn, d.idn salIdn , byr.get_nm(d.nme_idn) salNmeIdn, to_char(b.dte,'dd-mm-yyyy') dlvdte,\n" + 
            "ms.vnm, nvl(nvl(txt,num),val)   atr ,  st.mprp  \n" + 
            "from mdlv a , dlv_dtl b , msal d , jansal e ,stk_dtl st,mstk ms\n" + 
            "where a.idn=b.idn and b.sal_idn=d.idn and d.idn=e.idn and b.mstk_idn=e.mstk_idn\n" + 
            "and e.mstk_idn=st.mstk_idn and st.mstk_idn=ms.idn and b.stt='DLV' and e.stt='SL' \n" + 
            "and trunc(a.dte) between "+dtefrom+" and "+dteto+" "+
            "and exists (select 1 from rep_prp rp where rp.MDL = 'REPORT_VW' and rp.flg <> 'N' and st.mprp = rp.prp)  And st.Grp = 1) "+
            " Select * from stkDtl PIVOT ( max(atr)  for mprp  in ( "+pvPrpStr+"  ) )  order by 1 desc ";
            ArrayList rsLst = db.execSqlLst("data sql", dataSql, new ArrayList());
            PreparedStatement psmt = (PreparedStatement)rsLst.get(0);
            ResultSet rs = (ResultSet)rsLst.get(1);
            while(rs.next()){
                HashMap dtlMap = new HashMap();
                dtlMap.put("DLVIDN", rs.getString("dlvId"));
                dtlMap.put("DLVNME", rs.getString("dlvNmeIdn"));
                dtlMap.put("SALIDN", rs.getString("salIdn"));
                dtlMap.put("DLVDTE", rs.getString("dlvdte"));
                dtlMap.put("SALNME", rs.getString("salNmeIdn"));
                dtlMap.put("VNM", rs.getString("VNM"));
                for(int i=0;i<pvPrpLst.size();i++){
                    String lprp = (String)pvPrpLst.get(i);
                    String lprpKey = (String)lprpLst.get(i);
                    dtlMap.put(lprpKey, util.nvl(rs.getString(lprp)));
                }
                dtlMap.put("CRTWT", rs.getString("cts"));
                pktDtlList.add(dtlMap);
            }
            rs.close();
            if(pktDtlList.size()>1){
            String jsonPath = (String)info.getDmbsInfoLst().get("JSJSONPATH");
            String REP_URL = (String)info.getDmbsInfoLst().get("REP_URL");
            Gson gson = new Gson();
            String jsonStr= gson.toJson(pktDtlList);
            System.out.print(jsonStr);
            File jsonFile = new File(jsonPath+"SalDelivery.json");
            jsonFile.createNewFile();
            FileWriter writer = new FileWriter(jsonFile);
            writer.write(jsonStr); 
            writer.flush();
            writer.close(); 
            req.setAttribute("rptUrl", REP_URL+"/jasper/reportAction.do?method=viewRPT&DS="+info.getDbTyp()+"&KEY=SALDLV");
            }else{
                req.setAttribute("msg", "Sorry no result found.");
            }
                  
            rs.close();
            psmt.close();
                  
            return am.findForward("jasperRT"); 
        }
     }
    
    public   HashMap ViewPrpLst(HttpServletRequest req) {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr();
                HashMap viewMap = new HashMap();
            if(info!=null){
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            
           
            ArrayList pvlprpLst = new ArrayList();
            ArrayList lprpLst = new ArrayList();
            String mprpStr = "";
              String mdlPrpsQ = " Select Decode ( Rank() Over ( Order By Srt),1,' ',',')||''''||Prp||''''||' '||\n" + 
                      "Upper(replace(replace(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1'),'CERT NO.','CERT'),' ','_')) \n" + 
                      "str , Upper(replace(replace(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1'),'CERT NO.','CERT'),' ','_')) prp , rp.prp lprp From Rep_Prp Rp Where rp.MDL = ? and flg <> 'N' order by srt " ;
                ArrayList  ary = new ArrayList();
                ary.add("REPORT_VW");
                  try {
                ResultSet rs = db.execSql("mprp ", mdlPrpsQ, ary);
                while (rs.next()) {
                    String val = util.nvl((String)rs.getString("str"));
                    mprpStr = mprpStr + " " + val;
                    String prp = util.nvl((String)rs.getString("prp"));
                    String lprp = util.nvl((String)rs.getString("lprp"));
                    pvlprpLst.add(prp);
                    lprpLst.add(lprp);
                }
                rs.close();
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
                      viewMap.put("STR", mprpStr);
                      viewMap.put("PVLIST", pvlprpLst);
                      viewMap.put("LPRPLIST",lprpLst);
          

        }
                return viewMap;
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
                util.updAccessLog(req,res,"Stock Tacking", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Stock Tacking", "init");
            }
            }
            return rtnPg;
            }
}
