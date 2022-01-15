package ft.com.mixre;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;
import ft.com.JspUtil;
import ft.com.mixakt.MixTransactionForm;
import ft.com.mixakt.MixTransactionReport;

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

public class MixTransaction  extends DispatchAction {
        public MixTransaction() {
            super();
        }
        
        public ActionForward load(ActionMapping am, ActionForm form, HttpServletRequest req,
                                  HttpServletResponse res) throws Exception {
        
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg = "sucess";
        if (info != null) {
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm());
            db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            rtnPg = init(req, res, session, util);
        } else
            rtnPg = "sessionTO";
        if (!rtnPg.equals("sucess")) {
            return am.findForward(rtnPg);
        } else {
            MixTransactionForm mixAsrt = (MixTransactionForm)form;
            mixAsrt.resetAll();
            
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("MIX_TRANS");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("MIX_TRANS");
            allPageDtl.put("MIX_TRANS",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            
           
            ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_RGHGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_RGHGNCSrch");
          if(assortSrchList.size()==0)
              assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIXAST_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIXAST_SRCH");
           info.setGncPrpLst(assortSrchList);

            return am.findForward("load");
        }
        }
        
        public ActionForward loadBOXTYP(ActionMapping am, ActionForm af, HttpServletRequest req,
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
              util.updAccessLog(req,res,"Mix Transaction", "load ");
              MixTransactionForm mixForm = (MixTransactionForm)af;
              ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_RGHGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_RGHGNCSrch");
              if(genricSrchLst.size()==0)
                  genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIXAST_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIXAST_SRCH");
               info.setGncPrpLst(genricSrchLst);

              HashMap mprp = info.getMprp();
              HashMap prp = info.getPrp();
              String delQ = " Delete from gt_srch_rslt ";
              int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
              
              String vnm = util.nvl((String)mixForm.getValue("vnmLst"));
              HashMap paramsMap = new HashMap();
              for (int i = 0; i < genricSrchLst.size(); i++) {
                  ArrayList prplist = (ArrayList)genricSrchLst.get(i);
                  String lprp = (String)prplist.get(0);
                  String flg = (String)prplist.get(1);
                  String typ = util.nvl((String)mprp.get(lprp + "T"));
                  String prpSrt = lprp;
                  if (flg.equals("M")) {

                      ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
                      ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
                      for (int j = 0; j < lprpS.size(); j++) {
                          String lSrt = (String)lprpS.get(j);
                          String lVal = (String)lprpV.get(j);
                          String reqVal1 = util.nvl((String)mixForm.getValue(lprp + "_" + lVal), "");
                          if (!reqVal1.equals("")) {
                              paramsMap.put(lprp + "_" + lVal, reqVal1);
                          }

                      }
                  } else {
                      String fldVal1 = util.nvl((String)mixForm.getValue(lprp + "_1"));
                      String fldVal2 = util.nvl((String)mixForm.getValue(lprp + "_2"));
                      if (typ.equals("T")) {
                          fldVal1 = util.nvl((String)mixForm.getValue(lprp + "_1"));
                          fldVal2 = fldVal1;
                      }
                      if (fldVal2.equals(""))
                          fldVal2 = fldVal1;
                      if (!fldVal1.equals("") && !fldVal2.equals("")) {
                          paramsMap.put(lprp + "_1", fldVal1);
                          paramsMap.put(lprp + "_2", fldVal2);
                      }
                  }
              }
              if (paramsMap.size() > 0) {
                
                  paramsMap.put("mdl", "MIX_VIEW");
                  paramsMap.put("MIX", "Y");
                  util.genericSrch(paramsMap);
              } else {
                 if(!vnm.equals("")){
                    vnm = util.getVnm(vnm);
                  String srchRefQ = 
                  " Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1  ,  cert_lab , rmk,rap_dis ) " + 
                  " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'Z' , qty , cts , sk1 ,  cert_lab , tfl3,decode(rap_rte ,'1',null,'0',null, trunc((nvl(upr,cmp)/greatest(rap_rte,1)*100)-100, 2)) "+
                  " from mstk b  where 1=1 ";
                 srchRefQ = srchRefQ+" and ( b.vnm in ("+vnm+") or b.tfl3 in ("+vnm+"))" ;
                  ct = db.execUpd(" Srch Prp ", srchRefQ, new ArrayList());
                 }
              }
              
              
              String TRANSTYP =  util.nvl((String)mixForm.getValue("TRANSTYP"));
              String dtefr = util.nvl((String)mixForm.getValue("dtefr"));
              String dteto = util.nvl((String)mixForm.getValue("dteto"));
              String dteFtr="";
              if(!dtefr.equals("") && !dteto.equals("")){
                  dteFtr = " and trunc(log_dte) between to_date('"+dtefr+"' , 'dd-mm-yyyy') and to_date('"+dteto+"' , 'dd-mm-yyyy') ";
              }
              if(!TRANSTYP.equals("")){
                  dteFtr = dteFtr +" and typ ='"+TRANSTYP+"' ";
              }
              ArrayList pktDtlList = new ArrayList();
                 
                  String logDtl="with pkt_Idn as (select m.stk_idn idn, sd.txt stk_ctg, m.cts from gt_srch_rslt m, stk_dtl sd   where  \n" + 
                  "      m.stk_idn = SD.mstk_idn and SD.grp=1 and SD.mprp = 'STK_CTG' )\n" + 
                  "  select l.log_dte, to_char(l.log_dte, 'dd-Mon-rrrr HH24:mi') dt_tm \n" + 
                  "  , decode(typ, 'Stock Tally', '*', decode(l.frm_idn, p.idn, '-', '+')) trns_typ\n" + 
                  "  , l.typ, l.rmk, l.qty, l.cts, l.upr\n" + 
                  "  , decode(l.frm_idn, p.idn, p.stk_ctg, '-') frmBox\n" + 
                  "  , decode(l.to_idn, p.idn, p.stk_ctg, '-') toBox\n" + 
                  "   from mix_trf_log l, pkt_idn p\n" + 
                  "   where (nvl(l.frm_idn, 0) = 0 or nvl(l.to_idn, 0) = 0)\n" + 
                  "   and (l.frm_idn = p.idn or l.to_idn = p.idn) " +dteFtr+
                  "     UNION\n" + 
                  "   select l.log_dte, to_char(l.log_dte, 'dd-Mon-rrrr HH24:mi') dt_tm \n" + 
                  "   , decode(typ, 'Stock Tally', '*', decode(l.frm_idn, p.idn, '-', '+')) trns_typ\n" + 
                  "   , l.typ, l.rmk, l.qty, l.cts, l.upr\n" + 
                  "   , decode(l.frm_idn, p.idn, p.stk_ctg, sd.txt) frmBox\n" + 
                  "   , decode(l.to_idn, p.idn, p.stk_ctg, sd.txt) toBox\n" + 
                  "   from mix_trf_log l, pkt_idn p, stk_dtl sd\n" + 
                  "    where nvl(l.frm_idn, 0) <> 0 and nvl(l.to_idn, 0) <> 0\n" + 
                  "   and l.frm_idn = p.idn\n" + 
                  "   and nvl(l.to_idn, 0) = sd.mstk_idn and sd.grp = 1 and sd.mprp = 'STK_CTG'  \n"+dteFtr+
                  "   UNION\n" + 
                  "  select l.log_dte, to_char(l.log_dte, 'dd-Mon-rrrr HH24:mi') dt_tm \n" + 
                  "  , decode(typ, 'Stock Tally', '*', decode(l.frm_idn, p.idn, '-', '+')) trns_typ\n" + 
                  "  , l.typ, l.rmk, l.qty, l.cts, l.upr\n" + 
                  "  , decode(l.frm_idn, p.idn, p.stk_ctg, sd.txt) frmBox\n" + 
                  "  , decode(l.to_idn, p.idn, p.stk_ctg, sd.txt) toBox\n" + 
                  "  from mix_trf_log l, pkt_idn p, stk_dtl sd\n" + 
                  "  where nvl(l.frm_idn, 0) <> 0 and nvl(l.to_idn, 0) <> 0\n" + 
                  "   and l.to_idn = p.idn\n" + 
                  "  and nvl(l.frm_idn, 0) = sd.mstk_idn and sd.grp = 1 and sd.mprp = 'STK_CTG'  \n"+dteFtr+ 
                  "  order by 1 desc";
                 ArrayList params = new ArrayList();
                 
                  ArrayList outLst = db.execSqlLst("prp List", logDtl, params);
                  PreparedStatement pst = (PreparedStatement)outLst.get(0);
                  ResultSet rs = (ResultSet)outLst.get(1);
                  while(rs.next()){
                      HashMap pktDtl = new HashMap();
                      String ctsVal = util.nvl(rs.getString("cts"));
                      String typ = util.nvl(rs.getString("Typ")).toUpperCase();
                      String trns_typ = util.nvl(rs.getString("trns_typ"));
                      if(typ.indexOf("RETURN")!=-1 || typ.indexOf("NEW")!=-1 || typ.indexOf("PURCHASED")!=-1 ){
                          ctsVal="+"+ctsVal;
                      }else{
                          ctsVal=trns_typ+""+ctsVal;
                      }
                    
                      
                        pktDtl.put("TYP", util.nvl(rs.getString("typ")));
                        pktDtl.put("RMK", util.nvl(rs.getString("rmk")));
                        pktDtl.put("QTY", util.nvl(rs.getString("qty")));
                        pktDtl.put("UPR", util.nvl(rs.getString("upr")));
                        pktDtl.put("CTS", ctsVal);
                        pktDtl.put("DTE", util.nvl(rs.getString("dt_tm")));
                        pktDtl.put("FRMBOX", util.nvl(rs.getString("frmBox")));
                        pktDtl.put("TOBOX", util.nvl(rs.getString("toBox")));

                        pktDtlList.add(pktDtl);
                    }
                  rs.close();
                  pst.close();
              
             req.setAttribute("PKTDTL", pktDtlList);
              req.setAttribute("view", "yes");
              return am.findForward("load"); 
          }
          }
        
        public ArrayList DataCollection (HttpServletRequest req , HttpServletResponse res,HashMap params){
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr();
            ArrayList pktList = new ArrayList();
            if(info!=null){
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
             init(req,res,session,util);
            String dtefrm = util.nvl((String)params.get("DTEFRM"));
            String dteTo = util.nvl((String)params.get("DTETO"));
            String idn = util.nvl((String)params.get("IDN"));
            String dteQ=null;
            if(!dtefrm.equals("") && !dteTo.equals("")){
                dteQ=" and trunc(a.log_dte)  between "+dtefrm+" and "+dteTo;  
            }
            if(!idn.equals("")){
                
            }
            
            
            }
            return pktList;
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
                    util.updAccessLog(req,res,"Mix Assort Rtn", "Unauthorized Access");
                    else
                util.updAccessLog(req,res,"Mix Transaction", "init");
                }
                }
                return rtnPg;
                }
    }


