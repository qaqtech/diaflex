package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.lab.LabIssueForm;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class LabStockReport  extends DispatchAction{
    public LabStockReport() {
        super();
    }
    public ActionForward load(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
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
            util.updAccessLog(req,res,"Lab Issue", "load start");
            GtMgrReset(req, "", "LABSTK");
            GenericInterface genericInt = new GenericImpl();
            ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "LAB_VIEW","LAB_VIEW");
           int delct = db.execCall("delete gt", "delete from gt_srch_rslt", new ArrayList());
            String lstNme = "LABSTK_"+info.getLogId()+"_"+util.getToDteGiveFrmt("ddMMyyyyhhmmss");
            String msg="";
            LabStockReportForm labForm = (LabStockReportForm)form;
            ArrayList ary = new ArrayList();
            String insertGt = "insert into gt_srch_rslt (srch_id, stk_idn, vnm, qty, cts, cmp, quot, stt, sk1,rap_rte,cert_lab,rmk,flg, CERTIMG, DIAMONDIMG, JEWIMG, SRAYIMG, AGSIMG, MRAYIMG, RINGIMG, LIGHTIMG, REFIMG, VIDEOS, VIDEO_3D)\n" + 
            "        select 1, a.idn, a.vnm, a.qty, a.cts, a.cmp, nvl(a.upr, a.cmp), a.stt, a.sk1 , a.rap_rte,b.val,c.val,'Z'\n" + 
            "          , CERTIMG, DIAMONDIMG, JEWIMG, SRAYIMG, AGSIMG, MRAYIMG, RINGIMG, LIGHTIMG, REFIMG, VIDEOS, VIDEO_3D\n" + 
            "        from mstk a , stk_dtl b ,stk_dtl c\n" + 
            "        where a.idn=b.mstk_idn and b.mstk_idn=c.mstk_idn\n" + 
            "        and b.mprp='LAB_PRC' and b.grp=1 \n" + 
            "        and c.mprp='LAB_LOC' and c.grp=1 \n" + 
            "          and a.pkt_ty = 'NR' and a.stt in ('LB_IS','LB_AU_IS','LB_RS','LB_RI','LB_CF','LB_CFRS')\n" ;
            int ct = db.execUpd("insertGt", insertGt, new ArrayList());
                if(ct>0){
                ArrayList giaSttList = new ArrayList();
                ArrayList NoGiaSttList = new ArrayList();
                ArrayList LocList = new ArrayList();
                ArrayList LabList = new ArrayList();
                HashMap labSummary = new HashMap();
                String giaSummary="select sum(qty) qty,to_char(trunc(sum(trunc(cts, 2)),2),'99999990.00') cts, stt , rmk \n" + 
                "     from gt_srch_rslt where cert_lab=?\n" + 
                "     group by stt,rmk";
                ary = new ArrayList();
                ary.add("GIA");
                ArrayList rsLst = db.execSqlLst("GIASummary", giaSummary, ary);
                PreparedStatement pst = (PreparedStatement)rsLst.get(0);
                ResultSet rs = (ResultSet)rsLst.get(1);
                while(rs.next()){
                   String qty = rs.getString("qty");
                    String cts = rs.getString("cts");
                    String stt = rs.getString("stt");
                    String loc = rs.getString("rmk");
                    labSummary.put(loc+"_"+stt+"_QTYGIA", qty);
                    labSummary.put(loc+"_"+stt+"_CTSGIA", cts);
                    if(!LocList.contains(loc))
                        LocList.add(loc);
                    if(!giaSttList.contains(stt))
                        giaSttList.add(stt);
                }   
                rs.close();
                pst.close();
                
                    String noSummary="select sum(qty) qty,to_char(trunc(sum(trunc(cts, 2)),2),'99999990.00') cts, stt , cert_lab \n" + 
                    "     from gt_srch_rslt where cert_lab <> ?\n" + 
                    "     group by stt,cert_lab";
                    ary = new ArrayList();
                    ary.add("GIA");
                    rsLst = db.execSqlLst("noSummary", noSummary, ary);
                    pst = (PreparedStatement)rsLst.get(0);
                    rs = (ResultSet)rsLst.get(1);
                    while(rs.next()){
                       String qty = rs.getString("qty");
                        String cts = rs.getString("cts");
                        String stt = rs.getString("stt");
                        String lab = rs.getString("cert_lab");
                        labSummary.put(lab+"_"+stt+"_QTY", qty);
                        labSummary.put(lab+"_"+stt+"_CTS", cts);
                        if(!LabList.contains(lab))
                            LabList.add(lab);
                        if(!NoGiaSttList.contains(stt))
                            NoGiaSttList.add(stt);
                    }   
                    rs.close();
                    pst.close();
                    req.setAttribute("lstNme", lstNme);
                gtMgr.setValue(lstNme+"_GIASTT", giaSttList);
                gtMgr.setValue(lstNme+"_NOGIASTT", NoGiaSttList);
                gtMgr.setValue(lstNme+"_LABLIST", LabList);/*  */
                gtMgr.setValue(lstNme+"_LABLOC", LocList);
                gtMgr.setValue(lstNme+"_DTLMAP", labSummary);
                 PktCollection(db,util,gtMgr,lstNme,vwPrpLst);
               
                }else{
                    msg="Sorry there are no packets in Lab Stock....";
                }
            return am.findForward("load");   
           
        }
    }
   
    public void GtMgrReset(HttpServletRequest req,String lstNme,String initial){
          HttpSession session = req.getSession(false);
          GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
         HashMap gtMgrMap = (HashMap)gtMgr.getValues();
         if(lstNme!=null && !lstNme.equals("")){
             gtMgrMap.remove(lstNme+"_FRMSRCH");
             gtMgrMap.remove(lstNme);
             gtMgrMap.remove(lstNme+"_TOSRCH");
             gtMgrMap.remove(lstNme+"_FRMSRCH");
             gtMgrMap.remove(lstNme+"_TOSRCH");
             gtMgrMap.remove(lstNme+"_FMLST");
             gtMgrMap.remove(lstNme+"_TOLST");
         }else{
             ArrayList KeyList = new ArrayList();
         Set<String> keys = gtMgrMap.keySet();
             for(String key: keys){
                 if(key.indexOf(initial)!=-1)
                     KeyList.add(key);
             }
             for(int i=0;i<KeyList.size();i++){
                 String ky =(String)KeyList.get(i); 
                 gtMgrMap.remove(ky);
             }
         }
          
        }
    
    public void PktCollection(DBMgr db,DBUtil util,GtMgr gtMgr,String lstNme,ArrayList vwPrpLst){
 
           String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
           ArrayList  ary = new ArrayList();
            ary.add("LAB_VIEW");
           int ct = db.execCall(" Srch Prp ", pktPrp, ary);
        String  srchQ =  " select sk1,stk_idn ,cert_lab, pkt_ty, vnm,rap_rte, pkt_dte, stt , qty ,to_char(cts,'9999990.99') cts , rmk,quot rte,to_char(cts * quot, '99999990.00') amt,to_char(cts * rap_rte, '99999990.00') rapvlu  ";

        

        for (int i = 0; i < vwPrpLst.size(); i++) {
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

        
        String rsltQ = srchQ + " from gt_srch_rslt where flg =? " ;
        
         rsltQ  = rsltQ+ "order by sk1 , cts ";
        
        ary = new ArrayList();
        ary.add("Z");
        ArrayList pktList = new ArrayList();
        ArrayList rsLst = db.execSqlLst("gt rsltQ", rsltQ, ary);
        PreparedStatement pst = (PreparedStatement)rsLst.get(0);
        ResultSet rs = (ResultSet)rsLst.get(1);
        try {
             while (rs.next()) {
               HashMap pktDtl = new HashMap();
                 pktDtl.put("PACKETCODE", util.nvl(rs.getString("vnm")));
                 pktDtl.put("STT", util.nvl(rs.getString("stt")));
                    pktDtl.put("QTY", util.nvl(rs.getString("qty")));
                    pktDtl.put("CTS", util.nvl(rs.getString("cts")));
                    pktDtl.put("LAB_LOC", util.nvl(rs.getString("rmk")));
                    pktDtl.put("LAB_PRC", util.nvl(rs.getString("cert_lab")));
                    for(int j=0; j < vwPrpLst.size(); j++){
                         String lprp = util.nvl((String)vwPrpLst.get(j));
                          
                          String fld="prp_";
                          if(j < 9)
                                  fld="prp_00"+(j+1);
                          else    
                                  fld="prp_0"+(j+1);
                          if(lprp.equals("RAP_RTE"))
                              fld="rap_rte";
                          String val = util.nvl(rs.getString(fld)) ;
                        pktDtl.put(lprp,val);
                    }
                 pktList.add(pktDtl);
                }
                rs.close();
                pst.close();
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
        gtMgr.setValue(lstNme+"_PKTDTL", pktList);
       }
    
    
    public ActionForward PktDtl(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
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
            String lstNme = req.getParameter("lstNme");
            ArrayList pktList = (ArrayList)gtMgr.getValue(lstNme+"_PKTDTL"); 
            ArrayList pktDtlList = new ArrayList();
            String lstt = util.nvl(req.getParameter("STT"));
            String lab_loc = util.nvl(req.getParameter("LAB_LOC"));
            String lab = util.nvl(req.getParameter("LAB"));
            HashMap valMap = new HashMap();
            valMap.put("STT", lstt); valMap.put("LAB_LOC", lab_loc); valMap.put("LAB_PRC", lab);
            ArrayList keyList =new ArrayList();
            keyList.add("STT");
            keyList.add("LAB_LOC");
            keyList.add("LAB_PRC");
            ArrayList fltPktList =null;
            if(pktList!=null && pktList.size()>0){
                 fltPktList =pktList;
                for(int j=0;j<keyList.size();j++){
                    ArrayList keyFilterList = new ArrayList();
                    String key = (String)keyList.get(j);
                    String keyVal = util.nvl((String)valMap.get(key));
                       if(!keyVal.equals("")){
                      for(int i=0;i<fltPktList.size();i++){
                       HashMap pktDtl = (HashMap)fltPktList.get(i);
                      String pktVal = util.nvl((String)pktDtl.get(key));
                        if(keyVal.equals("NOGIA")){
                            if(!pktVal.equals("GIA"))
                                keyFilterList.add(pktDtl);
                        } else if(pktVal.equals(keyVal))
                           keyFilterList.add(pktDtl);
                       }
                          fltPktList=keyFilterList;
                      }
                    }
            }
           gtMgr.setValue(lstNme+"_FILTPKTDTL" , fltPktList);
            return am.findForward("loadPkt");   
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
                util.updAccessLog(req,res,"Mix Sale Report", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Mix Sale Report", "init");
            }
            }
            return rtnPg;
            }
}

