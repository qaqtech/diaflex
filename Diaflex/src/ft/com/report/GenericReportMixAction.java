package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
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

public class GenericReportMixAction extends DispatchAction {
    public GenericReportMixAction() {
        super();
    }
    public ActionForward load(ActionMapping am, ActionForm form,
                                        HttpServletRequest req,
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
            GenericReportForm udf = (GenericReportForm)form;
            String  level1="";
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("GENERIC_REPORT_MIX");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("GENERIC_REPORT_MIX");
            allPageDtl.put("GENERIC_REPORT_MIX",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            ArrayList pageList=new ArrayList();
            HashMap pageDtlMap=new HashMap();
            String fld_nme="",fld_ttl="",val_cond="",lov_qry="",dflt_val="",fld_typ="",form_nme="",flg1="",lotlprp="";
            
            pageList=(ArrayList)pageDtl.get("LEVEL1");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            level1=(String)pageDtlMap.get("dflt_val");
            }}
            
            String days=util.nvl((String)req.getParameter("daysfilter"));
            if(days.equals(""))
            days=util.nvl((String)udf.getValue("daysfilter"));
            if(days.equals(""))
            days="0";
            dayscomp(req,res,udf,days,level1);
            
            if(days.equals("DATE")){
                    String frmdte = util.nvl((String)udf.getValue("frmdte"),"01-01-2016");
                    String todte =util.nvl((String)udf.getValue("todte"),"31-12-2016");
                    days=String.valueOf((util.daysbetweendate(frmdte,todte)));
            }
            udf.reset();
            udf.setValue("daysfilter", days);
            req.setAttribute("days", days);
            SearchQuery srchQuery = new SearchQuery();
            ArrayList empList= srchQuery.getByrList(req,res);
            udf.setByrList(empList);
        return am.findForward("load");
        }
    }
    
    public void dayscomp(HttpServletRequest req,HttpServletResponse res,GenericReportForm udf,String days,String level1)throws Exception{
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        GenericInterface genericInt = new GenericImpl();
        ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "ANLS_VW_MIX","ANLS_VW_MIX");
        int indexL1 = vWPrpList.indexOf(level1)+1;
        String l1Prp = util.prpsrtcolumn("prp",indexL1);
        HashMap dataTbl=new HashMap();
        ArrayList params=new ArrayList();
        ArrayList level1Lst=new ArrayList();
        String empId=util.nvl((String)udf.getValue("empId"));
        String byrId=util.nvl((String)udf.getValue("byrId"));
        String frmdte = util.nvl((String)udf.getValue("frmdte"));
        String todte =util.nvl((String)udf.getValue("todte"));
        String procQ="DP_MIX_ANALYSIS(pDteFrm => to_date(trunc(sysdate-?), 'dd-mm-rrrr') , pDteTo => to_date(trunc(sysdate), 'dd-mm-rrrr'),pMdl =>'ANLS_VW_MIX',pEmpIdn => ?,pByrIdn => ?)";
        if(byrId.equals("0"))
            byrId="";
        if(empId.equals("0"))
            empId="";
        
        if(!days.equals("DATE")){
        params.add(days);
        }else{
        procQ="DP_MIX_ANALYSIS(pDteFrm => to_date(?, 'dd-mm-rrrr') , pDteTo =>  to_date(?, 'dd-mm-rrrr'),pMdl =>'ANLS_VW_MIX',pEmpIdn => ?,pByrIdn => ?)";
            params.add(frmdte);
            params.add(todte); 
        }
        params.add(empId);
        params.add(byrId);
        int ct = db.execCall("DP_MIX_ANALYSIS", procQ, params);  
        
        String dataQ="select "+l1Prp+" level1 ,sum(qty) qty,  to_char(trunc(sum(trunc(cts, 3)),3),'99999990.00') cts,\n" + 
        "trunc(sum(quot*trunc(cts,3))/sum(trunc(cts, 3)),0) avg,trunc(sum(quot*trunc(cts, 3)),0) vlu,trunc(sum(nvl(nvl(cmp,fquot),quot)*trunc(cts, 3)),0) bsevlu,\n" + 
        "dsp_stt grp \n" + 
        "from gt_srch_multi  where cts > 0 \n" + 
        "group by "+l1Prp+", dsp_stt";

        ArrayList outLst = db.execSqlLst("Data", dataQ, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        while (rs.next()) { 
            HashMap data=new HashMap();
            String prp1=util.nvl(rs.getString("level1"),"NA");
            String grp=util.nvl(rs.getString("grp"));
            dataTbl.put(prp1+"_"+grp+"_QTY",util.nvl(rs.getString("qty")));
            dataTbl.put(prp1+"_"+grp+"_CTS",util.nvl(rs.getString("cts")));
            dataTbl.put(prp1+"_"+grp+"_AVG", util.nvl(rs.getString("avg")));
            dataTbl.put(prp1+"_"+grp+"_VLU", util.nvl(rs.getString("vlu")));
            dataTbl.put(prp1+"_"+grp+"_BSEVLU", util.nvl(rs.getString("bsevlu")));
            if(!level1Lst.contains(prp1))
            level1Lst.add(prp1);
        }
        rs.close(); pst.close();
        dataQ="select sum(qty) qty,  to_char(trunc(sum(trunc(cts, 3)),3),'99999990.00') cts,\n" + 
        "trunc(sum(quot*trunc(cts,3))/sum(trunc(cts, 3)),0) avg,trunc(sum(quot*trunc(cts, 3)),0) vlu,trunc(sum(nvl(nvl(cmp,fquot),quot)*trunc(cts, 3)),0) bsevlu,\n" + 
        "dsp_stt grp \n" + 
        "from gt_srch_multi  where cts > 0 \n" + 
        "group by dsp_stt";

        outLst = db.execSqlLst("Data", dataQ, new ArrayList());
        pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
        while (rs.next()) { 
                    HashMap data=new HashMap();
                    String grp=util.nvl(rs.getString("grp"));
                    dataTbl.put(grp+"_QTY_TTL",util.nvl(rs.getString("qty")));
                    dataTbl.put(grp+"_CTS_TTL",util.nvl(rs.getString("cts")));
                    dataTbl.put(grp+"_AVG_TTL", util.nvl(rs.getString("avg")));
                    dataTbl.put(grp+"_VLU_TTL", util.nvl(rs.getString("vlu")));    
                    dataTbl.put(grp+"_BSEVLU_TTL", util.nvl(rs.getString("bsevlu")));
        }
        rs.close(); pst.close();
        req.setAttribute("dataTblMix", dataTbl);
        req.setAttribute("level1LstMix", level1Lst);

    } 
    
    public ActionForward pktlist(ActionMapping am, ActionForm form,
                              HttpServletRequest req,
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
            ArrayList pktList = new ArrayList();
            GenericInterface genericInt = new GenericImpl();
            ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, "ANLS_VW_MIX", "ANLS_VW_MIX");
            ArrayList itemHdr = new ArrayList();
            ArrayList prpDspBlocked = info.getPageblockList();
            int s=1;
            String stt=util.nvl((String)req.getParameter("stt"));
            String level1=util.nvl((String)req.getParameter("level1"));
            String prpval=util.nvl((String)req.getParameter("prpval"));
            int indexL1 = vwPrpLst.indexOf(level1)+1;
            String l1Prp = util.prpsrtcolumn("prp",indexL1);
            ArrayList ary=new ArrayList();
            String conQ="";
            itemHdr.add("SR");
            itemHdr.add("VNM");
            itemHdr.add("QTY");
            itemHdr.add("CTS");
            itemHdr.add("BSERTE");
            itemHdr.add("MKTRTE");
            itemHdr.add("NETRTE");
            itemHdr.add("RTE");
            itemHdr.add("VLU");
            itemHdr.add("BYR");
            itemHdr.add("EMP");
            itemHdr.add("SALE DATE");
            if(!stt.equals("")){
                conQ+=" and dsp_stt=?";
                ary.add(stt);
            }
            if(!prpval.equals("")){
                conQ+=" and "+l1Prp+"=?";
                ary.add(prpval);
            }
            String  srchQ =  " select stk_idn ,qty,nvl(cmp,fquot) cmp,nvl(fquot,nvl(cmp,quot)) fquot,nvl(fnl_usd,quot) fnl_usd,quot,byr,emp,  vnm , stt,flg,rmk tfl3,to_char(cts,'99999999990.99') cts,to_char(trunc(cts,3) * quot, '9999999990.00') vlu,sk1,to_char(sl_dte,'dd-mm-yyyy') sl_dte  ";
            
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

            
            String rsltQ = srchQ + " from gt_srch_multi where 1=1 "+conQ+" order by sk1,stk_idn,cts ";

            ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            try {
                while(rs.next()) {
                  
                    String stkIdn = util.nvl(rs.getString("stk_idn"));
                  
                    HashMap pktPrpMap = new HashMap();
                    pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
                    String vnm = util.nvl(rs.getString("vnm"));
                    pktPrpMap.put("VNM",vnm);
                        pktPrpMap.put("SR",String.valueOf(s++));
                    pktPrpMap.put("QTY", util.nvl(rs.getString("qty")));
                        pktPrpMap.put("CTS", util.nvl(rs.getString("cts")));
                        pktPrpMap.put("BSERTE", util.nvl(rs.getString("cmp")));
                        pktPrpMap.put("MKTRTE", util.nvl(rs.getString("fquot")));
                        pktPrpMap.put("NETRTE", util.nvl(rs.getString("quot")));
                        pktPrpMap.put("RTE", util.nvl(rs.getString("fnl_usd")));
                        pktPrpMap.put("VLU", util.nvl(rs.getString("vlu")));
                        pktPrpMap.put("BYR", util.nvl(rs.getString("byr")));
                        pktPrpMap.put("EMP", util.nvl(rs.getString("emp")));
                        pktPrpMap.put("SALE DATE", util.nvl(rs.getString("sl_dte")));
                    for(int j=0; j < vwPrpLst.size(); j++){
                         String prp = (String)vwPrpLst.get(j);
                          
                          String fld="prp_";
                          if(j < 9)
                                  fld="prp_00"+(j+1);
                          else    
                                  fld="prp_0"+(j+1);
                          
                          String val = util.nvl(rs.getString(fld)) ;
                          if(prp.equals("CRTWT"))
                          val = util.nvl(rs.getString("cts"));
                          if (prp.toUpperCase().equals("RFIDCD"))
                          val = util.nvl(rs.getString("tfl3"));
                            pktPrpMap.put(prp, val);
                            if(prpDspBlocked.contains(prp)){
                            }else if(!itemHdr.contains(prp)){
                            itemHdr.add(prp);
                            }
                            }
                                  
                        pktList.add(pktPrpMap);
                    }
                rs.close(); pst.close();
            } catch (SQLException sqle) {

                // TODO: Add catch code
                sqle.printStackTrace();
            }

              session.setAttribute("pktList", pktList);
              session.setAttribute("itemHdr",itemHdr);
        return am.findForward("pktDtl");
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
                rtnPg=util.checkUserPageRights("report/genericReport.do?","");
                if(rtnPg.equals("unauthorized"))
                util.updAccessLog(req,res,"Analysis Report Mix", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Analysis Report Mix", "init");
            }
            }
            return rtnPg;
    }
}
