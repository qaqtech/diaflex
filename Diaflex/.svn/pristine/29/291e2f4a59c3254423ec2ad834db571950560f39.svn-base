package ft.com.jasperReport;

import com.google.gson.Gson;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

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


import java.io.File;

import java.io.FileNotFoundException;

import java.io.FileWriter;

import javax.servlet.ServletOutputStream;



public class StockTackingReport extends DispatchAction {
    public StockTackingReport() {
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
            ArrayList list = new ArrayList();
            list.add("SAL");
            list.add("MKT");
            list.add("DLV");
            String vnm = (String)udf.getValue("vnm");
            String dataSql ="with pkt_Idn as (select idn from mstk where vnm ='"+vnm+"'),\n" + 
            " mx_grp as (select max(grp) grp from stk_dtl sd , pkt_Idn p where sd.mstk_idn = p.idn),\n" + 
            "trf_dte as (select dte , num from stk_dtl sd , pkt_Idn p where sd.grp = 1 and sd.mprp = 'TRF_DTE' and sd.mstk_idn = p.idn)\n" + 
            "select 0 seq,a.pur_dte,'NA' usr, to_number(to_char(a.dte, 'rrrrmmddhh24mmss')) dte , to_char(a.dte, 'dd/mm/yy') issDte,to_char(b.trf_dte, 'dd/mm/yy') tranDte ,'Purchase TRF' prc,\n" + 
            "  n.nme emp, p.mprp ,decode(m.dta_typ, 'C', p.val, 'N', decode(p.mprp,'CRTWT',  to_char(p.num,'9990.90'),to_char(p.num)) , 'D', p.dte, p.txt) val ,'PUR' flg\n" + 
            "  from mpur a ,pur_dtl b, pur_prp p , nme_v n ,rep_prp rp ,mprp m, pkt_Idn pn where\n" + 
            "a.pur_idn=b.pur_idn and a.vndr_idn=n.nme_idn and b.pur_dtl_idn = p.pur_dtl_idn and nvl(b.flg2, 'NA') <> 'DEL' and nvl(a.flg2, 'NA') <> 'DEL'\n" + 
            "and p.mprp=rp.prp and rp.flg='Y' and rp.mdl='STKTRK_VW' and m.prp=p.mprp\n" + 
            "and b.mstk_idn=pn.idn \n" + 
            "union\n" + 
            "select 1 seq, sd1.dte,'NA' usr,to_number(to_char(sd1.dte, 'rrrrmmddhh24mmss')) dte,'-' issDte,to_char(sd1.dte, 'dd/mm/yy') tranDte, 'MFG' prc, 'Inward' emp, p.prp mprp \n" + 
            ", decode(p.dta_typ, 'C', sd.prt1, 'N', to_char(sd.num), 'D', sd.dte, sd.txt) val , 'MFG' flg \n" + 
            "from STK_DTL sd, mx_grp g, mprp p ,rep_prp rp ,stk_dtl sd1 ,pkt_Idn pn\n" + 
            "where 1 = 1 \n" + 
            "and sd.mprp = p.prp\n" + 
            "and sd.grp = g.grp and sd.mstk_idn =  pn.idn \n" + 
            "and sd1.grp=g.grp and sd1.mprp='INWARD_DTE' and sd1.mstk_idn = pn.idn \n" + 
            "and sd.mprp =rp.prp and rp.mdl='STKTRK_VW' and rp.flg='Y'\n" + 
            "union\n" + 
            "select 2 seq,t.dte,'NA' usr,to_number(to_char(t.dte, 'rrrrmmddhh24mmss')) dte,'-' issDte,to_char(t.dte, 'dd/mm/yy') tranDte, 'Trf to Mktg' prc, 'InStock' emp\n" + 
            ", p.prp mprp, decode(p.dta_typ, 'C', sd.prt1, 'N', decode(p.prp,'CRTWT',  to_char(sd.num,'9990.90'),to_char(sd.num)), 'D', sd.dte, sd.txt) val ,'TRFMKT' flg\n" + 
            "from STK_DTL sd, trf_dte t, mprp p ,rep_prp rp ,pkt_Idn pn\n" + 
            "where 1 = 1 \n" + 
            "and sd.mprp = p.prp\n" + 
            "and sd.grp = 1 and sd.mstk_idn = pn.idn \n" + 
            "and sd.mprp =rp.prp and rp.mdl='STKTRK_VW' and rp.flg='Y'\n" + 
            "union\n" + 
            "select 2 seq, ird.iss_dt,ird.aud_modified_by usr,  to_number(to_char(ird.iss_dt, 'rrrrmmddhh24mmss')) dte,to_char(ird.iss_dt, 'dd/mm/yy') issDte,to_char(ird.rtn_dt, 'dd/mm/yy') tranDte, i.prc, i.emp, irp.mprp\n" + 
            ", decode(p.dta_typ, 'C', nvl(irp.rtn_val, irp.iss_val), 'N', decode(irp.mprp,'CRTWT',  to_char(nvl(irp.rtn_num, irp.iss_num),'99990.90'),to_char(nvl(irp.rtn_num, irp.iss_num))),  txt) val,'ISS' flg\n" + 
            "from iss_rtn_v i, iss_rtn_dtl ird, iss_rtn_prp irp, mprp p  ,rep_prp rp ,pkt_Idn pn\n" + 
            "where i.iss_id = ird.iss_id and ird.iss_id = irp.iss_id and ird.iss_stk_idn = irp.iss_stk_idn\n" + 
            "and irp.mprp = p.prp and ird.iss_stk_idn = pn.idn and ird.stt <> 'CL' \n" + 
            "and irp.mprp =rp.prp and rp.mdl='STKTRK_VW' and rp.flg='Y'\n" + 
            "union\n" + 
            "select 2 seq,p.dte,p.unm usr, to_number(to_char(p.dte, 'rrrrmmddhh24mmss')) dte, '-' issDte, to_char(p.dte, 'dd/mm/yy') tranDte,'Price Change' prc, unm emp, flg||' : Old = '||old_upr mprp, 'New = '||upr val ,'PRC' flg\n" + 
            "from stk_pri_log p , pkt_Idn pn where mstk_idn = pn.idn  and old_upr <> upr\n" + 
            "union\n" + 
            "select 2 seq,jd.dte, c.AUD_CREATED_BY usr,to_number(to_char(j.dte, 'rrrrmmddhh24mmss')) dte ,to_char(jd.dte, 'dd/mm/yy') issDte,to_char(nvl(jd.dte,jd.dte_rtn), 'dd/mm/yy') tranDte\n" + 
            ", decode(j.typ,'BID','Bid Issue','O','PBB','K','KSelect', 'I', 'Internal','H','Happy Hours', 'E', 'External', 'WH', 'Web Hold'\n" + 
            "  , 'WAP', 'Web Approved', 'IAP', 'Internall Approved', 'EAP', 'External Approved'\n" + 
            "  , 'Z', 'List', 'WR', 'Web Request', j.typ) prc\n" + 
            "  , j.byr||' : '||j.trm emp\n" + 
            "  , 'Memo' mprp\n" + 
            "  , to_char(jd.quot) val,'MKT' flg\n" + 
            "from jan_v j, jandtl jd , pkt_Idn pn ,  mjan c \n" + 
            "where j.idn = jd.idn and jd.mstk_idn = pn.idn and jd.idn = c.idn and c.typ <> 'Z' \n" + 
            " union  \n" + 
            "  select 2 seq,sl.dte,sl.AUD_CREATED_BY usr, to_number(to_char(nvl(sl.dte,sl.dte_rtn), 'rrrrmmddhh24mmss')) dte ,to_char(sl.dte, 'dd/mm/yy') issDte,to_char(nvl(sl.dte,sl.dte_rtn), 'dd/mm/yy') tranDte\n" + 
            " , 'Sale' prc\n" + 
            "  , j.byr||' : '||j.trm emp \n" + 
            "    , 'Memo' mprp \n" + 
            "  , to_char(sl.fnl_sal) val,'SAL' flg\n" + 
            "  from sal_v j, jansal sl,pkt_Idn pn\n" + 
            "  where j.idn = sl.idn and sl.mstk_idn =  pn.idn \n" + 
            "  union\n" + 
            "    select 2 seq,dl.dte,dl.AUD_CREATED_BY usr, to_number(to_char(nvl(dl.dte,dl.dte_rtn), 'rrrrmmddhh24mmss')) dte ,to_char(dl.dte, 'dd/mm/yy') issDte,to_char(nvl(dl.dte,dl.dte_rtn), 'dd/mm/yy') tranDte\n" + 
            " , 'Delivery' prc\n" + 
            "  , j.byr||' : '||j.trm emp \n" + 
            "    , 'Memo' mprp \n" + 
            "  , to_char(dl.fnl_sal) val,'DLV' flg\n" + 
            "  from dlv_v j, dlv_dtl dl,pkt_Idn pn\n" + 
            "  where j.idn = dl.idn and dl.mstk_idn = pn.idn \n" + 
            " order by 1, 2"; 
            
            ArrayList stockTrackingData = new ArrayList();
            HashMap mprpMap = new HashMap();
            HashMap mprpCollMap = new HashMap();
            HashMap stkTrkMap = new HashMap();
            ArrayList ary = new ArrayList();
            viewPrpLst(req);
            ArrayList stockTrkList = (ArrayList)session.getAttribute("STKTRK_VW");
            
            ArrayList rsLst = db.execSqlLst("data sql", dataSql, ary);
            PreparedStatement psmt = (PreparedStatement)rsLst.get(0);
            ResultSet rs = (ResultSet)rsLst.get(1);
            String PFlg="";
            HashMap emptyRow = new HashMap();
            emptyRow.put("Flg", "");
            emptyRow.put("prc", "");
            emptyRow.put("Name", "");
            emptyRow.put("Date","");
            emptyRow.put("ISSDate","");
            emptyRow.put("VNM", "");
            emptyRow.put("USR", "");
            while (rs.next()) {
                String lFlg =util.nvl(rs.getString("flg"));
                if(PFlg.equals(""))
                    PFlg=lFlg;
                if(!PFlg.equals(lFlg)){
                if(stockTrkList!=null){
                    for(int i=0;i< stockTrkList.size();i++){
                    String mprp = (String)stockTrkList.get(i);
                    String mVal = util.nvl((String)mprpMap.get(mprp)).trim();
                            if(mVal.equals(""))
                                 mVal="NA";
                    stkTrkMap.put(mprp,mVal);
                    emptyRow.put(mprp,"");
                     }
                  }
                    mprpCollMap.put(PFlg, mprpMap);
                    stockTrackingData.add(stkTrkMap);
                    stkTrkMap = new HashMap();
                    mprpMap = new HashMap();
                    PFlg=lFlg;
                }else if(list.contains(PFlg)){
                    if(stockTrkList!=null){
                        for(int i=0;i< stockTrkList.size();i++){
                        String mprp = (String)stockTrkList.get(i);
                        String mVal = util.nvl((String)mprpMap.get(mprp)).trim();
                                if(mVal.equals(""))
                                     mVal="NA";
                        stkTrkMap.put(mprp,mVal);
                        emptyRow.put(mprp,"");
                         }
                      }
                    mprpCollMap.put(PFlg, mprpMap);
                    stockTrackingData.add(stkTrkMap);
                    stkTrkMap = new HashMap();
                    mprpMap = new HashMap();
                }
                String lprc = util.nvl(rs.getString("prc"),"NA");
                String tranDte = util.nvl(rs.getString("tranDte"));
                String usr = util.nvl(rs.getString("usr"));
                String issDte = util.nvl(rs.getString("issDte"),"-");
                String emp = util.nvl(rs.getString("emp"),"NA");
                String lprp = util.nvl(rs.getString("mprp"),"NA");
                String lval = util.nvl(rs.getString("val"),"NA");
                stkTrkMap.put("Flg", lFlg);
                stkTrkMap.put("prc", lprc);
                stkTrkMap.put("Name", emp);
                stkTrkMap.put("Date",tranDte);
                stkTrkMap.put("ISSDate",issDte);
                stkTrkMap.put("VNM", vnm);
                stkTrkMap.put("USR", usr);
                if(lprp.equals("CRTWT"))
                 lval = lval.replaceAll(" ", "");
                mprpMap.put(lprp, lval);
            }
            rs.close();
            if(!PFlg.equals("")){
                if(stockTrkList!=null){
                    for(int i=0;i< stockTrkList.size();i++){
                    String mprp = util.nvl((String)stockTrkList.get(i));
                    String mVal = util.nvl((String)mprpMap.get(mprp)).trim();
                     if(mVal.equals(""))
                            mVal="NA";
                    stkTrkMap.put(mprp,mVal);
                    emptyRow.put(mprp,"");
                     }
                  }
                mprpCollMap.put(PFlg, mprpMap);
                stockTrackingData.add(stkTrkMap);
            }
            rs.close();
            psmt.close();
            
           
            
            
            ArrayList finalStockTrackList = new ArrayList();
            finalStockTrackList.add(emptyRow);
            if(stockTrackingData!=null && stockTrackingData.size()>0){
                 for(int i=0;i<stockTrackingData.size();i++){
                 HashMap stockTrackMap = (HashMap)stockTrackingData.get(i);
                 String flg = (String)stockTrackMap.get("Flg");
                 if(flg.equals("PRC")|| flg.equals("MKT") || flg.equals("SAL")|| flg.equals("DLV")){
                 HashMap mprpList = (HashMap)mprpCollMap.get("TRFMKT");
                 if(mprpList==null && mprpList.size()==0)
                     mprpList = (HashMap)mprpCollMap.get("ISS");
                     if(mprpList==null && mprpList.size()==0)
                         mprpList = (HashMap)mprpCollMap.get("PUR");
                 for(int j=0;j< stockTrkList.size();j++){
                      String mprp = (String)stockTrkList.get(j);
                      String mVal = util.nvl((String)mprpList.get(mprp));
                      stockTrackMap.put(mprp, mVal);
                     
                    }
                 }
               
                 finalStockTrackList.add(stockTrackMap);
                }
            }
            if(finalStockTrackList.size()>1){
            
            req.setAttribute("finalStockTrackList",finalStockTrackList);
//           String jsonPath = "E:/JasperReport/";
//               //(String)info.getDmbsInfoLst().get("JSJSONPATH");
//           String REP_URL = (String)info.getDmbsInfoLst().get("JSP_URL");
//             System.out.println("jsonPath"+jsonPath); 
//            Gson gson = new Gson();
//            String jsonStr= gson.toJson(finalStockTrackList);
//            File jsonFile = new File(jsonPath+"StockTracking.json");
//            jsonFile.createNewFile();
//            FileWriter writer = new FileWriter(jsonFile);
//            writer.write(jsonStr); 
//            writer.flush();
//            writer.close(); 
//            req.setAttribute("rptUrl", REP_URL+"/jasper/reportAction.do?method=viewRPT&DS="+info.getDbTyp()+"&KEY=STKTRCK");
            }else{
                req.setAttribute("msg", "Sorry no result found.");
            }
                  
            return am.findForward("jasperRT"); 
        }
     }
    
    public void viewPrpLst(HttpServletRequest req){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        Connection conn=info.getCon();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());

        try {
            
            ArrayList viewPrp = (ArrayList)session.getAttribute("STKTRK_VW");
            if (viewPrp == null) {
                viewPrp = new ArrayList();
                ResultSet rs1 =
                    db.execSql(" Vw Lst ", "Select prp  from rep_prp where mdl = 'STKTRK_VW' and flg='Y' order by rnk ",
                               new ArrayList());
                while (rs1.next()) {

                    viewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                session.setAttribute("STKTRK_VW", viewPrp);
            }
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
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
                util.updAccessLog(req,res,"Stock Tacking", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Stock Tacking", "init");
            }
            }
            return rtnPg;
            }
}
