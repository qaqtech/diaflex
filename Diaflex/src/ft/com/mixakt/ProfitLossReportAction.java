package ft.com.mixakt;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.mix.MixFinalIssueForm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ProfitLossReportAction extends DispatchAction {
    public ProfitLossReportAction() {
        super();
    }
   public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req,
                                  HttpServletResponse res) throws Exception {
          HttpSession session = req.getSession(false);
          InfoMgr info = (InfoMgr)session.getAttribute("info");
          GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
          DBUtil util = new DBUtil();
          DBMgr db = new DBMgr();
          String rtnPg="sucess";
          if(info!=null){  
          db.setCon(info.getCon());
          util.setDb(db);
          util.setInfo(info);
          db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
          util.setLogApplNm(info.getLoApplNm());
          rtnPg=init(req,res,session,util);
          }else
          rtnPg="sessionTO";
          if(!rtnPg.equals("sucess")){
              return am.findForward(rtnPg);   
          }else{
              ProfitLossReportForm udf = (ProfitLossReportForm)af;
             udf.resetAll();
              GenericInterface genericInt = new GenericImpl();
              ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_PFTLOSS_RPT") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_PFTLOSS_RPT");
              info.setGncPrpLst(assortSrchList);
              return am.findForward("load");    
          }
          }
   
    public ActionForward View(ActionMapping am, ActionForm af, HttpServletRequest req,
                                   HttpServletResponse res) throws Exception {
           HttpSession session = req.getSession(false);
           InfoMgr info = (InfoMgr)session.getAttribute("info");
           GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
           DBUtil util = new DBUtil();
           DBMgr db = new DBMgr();
           String rtnPg="sucess";
           if(info!=null){  
           db.setCon(info.getCon());
           util.setDb(db);
           util.setInfo(info);
           db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
           util.setLogApplNm(info.getLoApplNm());
           rtnPg=init(req,res,session,util);
           }else
           rtnPg="sessionTO";
           if(!rtnPg.equals("sucess")){
               return am.findForward(rtnPg);   
           }else{
               ProfitLossReportForm udf = (ProfitLossReportForm)af;
               ArrayList params = new ArrayList();
               String dteFr = util.nvl((String)udf.getValue("dtefr"));
               String dteTo = util.nvl((String)udf.getValue("dteto"));
               String boxId = util.nvl((String)udf.getValue("MTXT_BOX_ID"));
               HashMap dbinfo = info.getDmbsInfoLst();
               String cnt = (String)dbinfo.get("CNT");
               if(boxId.equals(""))
               boxId = util.nvl((String)udf.getValue("BOX_ID_1"));
               
               String sqlStr ="";
               if(!dteFr.equals("") && !dteTo.equals("")){
                  String dtefrom = "to_date('"+dteFr+"' , 'dd-mm-yyyy')";
                  String dteto = "to_date('"+dteTo+"' , 'dd-mm-yyyy')";
                   sqlStr=" and trunc(c.dte)  between "+dtefrom+" and "+dteto; 
               }
                   
                String nmeIdn = util.nvl((String)req.getParameter("nmeID"));
               if(!nmeIdn.equals("")){
                   sqlStr=sqlStr+" and c.nme_idn = ? ";
                   params.add(nmeIdn);
               }
               if(!boxId.equals("") && !boxId.equals("ALL")){
                   boxId = boxId.replaceAll(",", "','");
                   boxId= "'"+boxId+"'";
                   sqlStr=sqlStr+" and  bi.val in ("+boxId+")";
               }
               ArrayList pktDtlList = new ArrayList();
               String sqlQury= "With SAL_V as (\n" + 
               "select c.nme_idn, sum(a.qty) salQty, trunc(sum(a.cts),3) salCts,    \n" + 
               "trunc((sum(trunc(a.cts,3) * nvl(a.fnl_sal, a.quot))/sum(trunc(a.cts,3))),2) salAvg,\n" + 
               "trunc(sum(trunc(a.cts,3) * nvl(a.fnl_sal, a.quot)),2) salAmt,\n" + 
               "trunc((sum(trunc(a.cts,3) * a.rte)/sum(trunc(a.cts,3))),2) bseAvg,\n" + 
               "trunc(sum(trunc(a.cts,3) * a.rte),2) bseAmt,bx.val boxTyp ,bi.val boxId\n" + 
               "from jansal a , mstk b , msal c, stk_dtl bx , stk_dtl bi \n" + 
               "where a.mstk_idn=b.idn and b.pkt_ty in ('MIX','SMX') and a.idn=c.idn\n" + 
               "and a.stt in ('SL','DLV')  " +sqlStr+ 
               "and bx.mstk_idn= b.idn and bx.mprp='BOX_TYP' and bx.grp=1  \n" + 
               "and bi.mstk_idn=b.idn and bi.mprp='BOX_ID' and bi.grp=1\n" + 
               "and a.cts > 0\n" + 
               "group by bx.val,bi.val,c.nme_idn)\n" + 
               "select n.nme,j.boxTyp,j.boxId,j.salQty,to_char(j.salCts,'9999990.000') salCts,j.salAvg,j.salAmt,j.bseAvg,j.bseAmt , to_char((j.salAmt-j.bseAmt),'999999990.00') diff\n" + 
               "from nme_v n, SAL_V j\n" + 
               "where n.nme_idn = j.nme_idn \n" + 
               "order by  1";
               if(cnt.equals("kj")){
                   sqlQury= "With SAL_V as (\n" + 
                                  "select c.nme_idn, sum(a.qty) salQty, trunc(sum(a.cts),3) salCts,    \n" + 
                                  "trunc((sum(trunc(a.cts,3)*nvl(a.fnl_sal,a.quot)/nvl(c.exh_rte,1))/sum(trunc(a.cts,3))),2) salAvg,\n" + 
                                  "trunc(sum(trunc(a.cts,3)*nvl(a.fnl_sal,a.quot)/nvl(c.exh_rte,1)),2) salAmt,\n" + 
                                  "trunc((sum(decode(b.pkt_ty,'SMX',trunc(a.cts,3)*a.rte/nvl(c.exh_rte,1),trunc(a.cts,3) * a.rte))/sum(trunc(a.cts,3))),2) bseAvg,\n" + 
                                  "trunc(sum(decode(b.pkt_ty,'SMX',trunc(a.cts,3)*a.rte/nvl(c.exh_rte,1),trunc(a.cts,3) * a.rte)),2) bseAmt,bx.val boxTyp ,bi.val boxId\n" + 
                                  "from jansal a , mstk b , msal c, stk_dtl bx , stk_dtl bi \n" + 
                                  "where a.mstk_idn=b.idn and b.pkt_ty in ('MIX','SMX') and a.idn=c.idn\n" + 
                                  "and a.stt in ('SL','DLV')  " +sqlStr+ 
                                  "and bx.mstk_idn= b.idn and bx.mprp='BOX_TYP' and bx.grp=1  \n" + 
                                  "and bi.mstk_idn=b.idn and bi.mprp='BOX_ID' and bi.grp=1\n" + 
                                  "and a.cts > 0\n" + 
                                  "group by bx.val,bi.val,c.nme_idn)\n" + 
                                  "select n.nme,j.boxTyp,j.boxId,j.salQty,to_char(j.salCts,'9999990.000') salCts,j.salAvg,j.salAmt,j.bseAvg,j.bseAmt , to_char((j.salAmt-j.bseAmt),'999999990.00') diff\n" + 
                                  "from nme_v n, SAL_V j\n" + 
                                  "where n.nme_idn = j.nme_idn \n" + 
                                  "order by  1";
               }
               ArrayList outLst = db.execSqlLst("prp List", sqlQury, params);
               PreparedStatement pst = (PreparedStatement)outLst.get(0);
               ResultSet rs = (ResultSet)outLst.get(1);
               String pnme="";
               while(rs.next()){
                   HashMap dtlMap = new HashMap();
                  String lnme = util.nvl(rs.getString("nme"));
                   String dNme=lnme;
                   if(!pnme.equals("") && pnme.equals(lnme))
                       dNme="";
                   dtlMap.put("NME", dNme);
                   dtlMap.put("BOXTYP", util.nvl(rs.getString("boxTyp")));
                   dtlMap.put("BOXID", util.nvl(rs.getString("boxId")));
                   dtlMap.put("SALQTY", util.nvl(rs.getString("salQty")));
                   dtlMap.put("SALCTS", util.nvl(rs.getString("salCts")));
                   dtlMap.put("SALAVG", util.nvl(rs.getString("salAvg")));
                   dtlMap.put("SALAMT", util.nvl(rs.getString("salAmt")));
                   dtlMap.put("BSEAVG", util.nvl(rs.getString("bseAvg")));
                   dtlMap.put("BSEAMT", util.nvl(rs.getString("bseAmt")));
                   double diff = rs.getDouble("diff");
                   if(diff < 0)
                       dtlMap.put("STT", "L");
                   else
                       dtlMap.put("STT", "P");
                   dtlMap.put("DIFF", util.nvl(rs.getString("diff")));
                   pktDtlList.add(dtlMap);
                   pnme=lnme;
               }
               rs.close();
               pst.close();
               req.setAttribute("PKTDTLLIST", pktDtlList);
               req.setAttribute("view", "Y");
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
            util.updAccessLog(req,res,"Price Calc", "init");
            }
            }
            return rtnPg;
            }
}
