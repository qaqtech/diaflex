    package ft.com.mix;

    import ft.com.DBMgr;
    import ft.com.DBUtil;
    import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.SearchQuery;
import ft.com.pri.CostMgmtForm;

import ft.com.pri.PriceRtnForm;

import java.io.IOException;

import java.net.InetSocketAddress;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
    import java.sql.SQLException;

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.ArrayList;

import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import javax.servlet.http.HttpSession;

import net.spy.memcached.MemcachedClient;

import org.apache.struts.action.ActionForm;
    import org.apache.struts.action.ActionForward;
    import org.apache.struts.action.ActionMapping;
    import org.apache.struts.actions.DispatchAction;

    public class SingleToMixTrfAction  extends DispatchAction {
      
        public SingleToMixTrfAction() {
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
              util.updAccessLog(req,res,"SingleToMixTrf", "load");
            SingleToMixTrfForm udf = (SingleToMixTrfForm)af;
              GenericInterface genericInt = new GenericImpl();
            udf.resetAll();
            
                ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_singleTomixGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_singleTomixGNCSrch");
            info.setGncPrpLst(assortSrchList);
//            ArrayList singletomixList = (session.getAttribute("singletomixsttList") == null)?new ArrayList():(ArrayList)session.getAttribute("singletomixsttList");
//            if(singletomixList.size() == 0) {
//            singlesttList(session,db,info);
//            }
              util.updAccessLog(req,res,"SingleToMixTrf", "load end");
            return am.findForward("load");
            }
        }
        public ActionForward fetch(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
                throws Exception {
          HttpSession session = req.getSession(false);
          InfoMgr info = (InfoMgr)session.getAttribute("info");
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
              util.updAccessLog(req,res,"SingleToMixTrf", "fetch");
            SingleToMixTrfForm udf = (SingleToMixTrfForm)af;
              SearchQuery srchQuery = new SearchQuery();
              
            packetData(db, info , udf,session,util);
            ArrayList vwPrpLst = SINGLETOMIXPrprViw(session,db);
            HashMap pktList =SearchResult(db,"'Z'",session,vwPrpLst);
            req.setAttribute("pktList", pktList);
            req.setAttribute("view","Y");
            HashMap totals = srchQuery.GetTotal(req,res,"Z");
            req.setAttribute("totalMap", totals);
              util.updAccessLog(req,res,"SingleToMixTrf", "fetch end");
        return am.findForward("load");
            }
        }
        public ActionForward transfer(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
          HttpSession session = req.getSession(false);
          InfoMgr info = (InfoMgr)session.getAttribute("info");
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
              util.updAccessLog(req,res,"SingleToMixTrf", "transfer");
            SingleToMixTrfForm udf = (SingleToMixTrfForm)form;
            ArrayList pktStkIdnList = (ArrayList)session.getAttribute("pktStkIdnList");
            ArrayList ary = new ArrayList();
            String msg = "";
            for(int m=0;m< pktStkIdnList.size();m++){ 
            String stkIdn = (String)pktStkIdnList.get(m);
                String chk=util.nvl((String)udf.getValue("check_"+stkIdn));
                if(chk.equals("Y")){
                    ary = new ArrayList();
                    ary.add(stkIdn);
                    String mix ="MERGE_PKG.AV_SNGL_TO_MIX(pIdn =>?)";
                    int ct = db.execCall("AV_SNGL_TO_MIX", mix, ary);
                    if(ct>0){
                        msg = msg+","+stkIdn ;
                    }
                }
            }
            if(msg.length()>1)
            req.setAttribute("msg", "Pkts Transfer :-"+msg);
            else
            req.setAttribute("msg", "Error In Process");
              util.updAccessLog(req,res,"SingleToMixTrf", "transfer end");
                 return am.findForward("load"); 
            }
        }
        public HashMap SearchResult(DBMgr db, String flg ,HttpSession session, ArrayList vwPrpLst) {
                JspUtil jspUtil = new JspUtil();
                HashMap pktListMap = new HashMap();
                ArrayList pktStkIdnList = new ArrayList();    
                String srchQ = "";
                HashMap pktPrpMap = new HashMap();
                String srchQ1 = " select sk1, 0 dsp , a.stk_idn , cert_lab , dsp_stt , decode(stt " + 
                ", 'MKPP','color:Green'" + 
                " , 'MKLB','color:Maroon'" + 
                " , 'MKAV', decode(instr(upper(dsp_stt), 'NEW'), 0, ' ', 'color:Blue')" + 
                " , 'MKIS', 'color:Red'" + 
                " , 'SOLD') class ,  ";
                String srchQ2 = " select sk1, 1 dsp , a.stk_idn , cert_lab , '' dsp_stt , '' class , ";
                String dspStk = "stkDspFF";
                String getQuot = "quot rte";
               srchQ += "  cts crtwt, " +
                        //decode(quot, 0, 0, decode(nvl(rap_dis,0), 0, 101, trunc(((quot*100)/greatest(rap_rte,100)) - 100,2))) rr_dis, "+
                        " decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis " +
                        " , stk_idn  , vnm, kts_vw kts, cert_lab cert, cert_no, rmk, img, cmp , trunc(((cmp*100)/greatest(rap_rte,1)) - 100,2) cmp_dis ,  to_char(trunc(cts,2),'9990.99') cts, qty , rap_rte , " +
                        getQuot +
                        ", cmnt,stt stt1,  nvl(fquot,0) fquot , flg , to_char(trunc(cts,2) * quot, '99999990.00') amt , " +
                        " decode(nvl(fquot,0),0, 'NA', decode(fquot, quot, 'NA', decode(greatest(fquot, quot),quot, 'UP','DN'))) cmp_flg,srch_id ";

             

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

             
                String rsltQ =
                   srchQ1+" "+srchQ + " from gt_srch_rslt a where flg in ("+flg+") union " +srchQ2+ " "+srchQ +"   from gt_srch_multi a " ;
                    rsltQ = rsltQ+  " order by sk1";
                
                ArrayList ary = new ArrayList();
                
              ArrayList  rsLst = db.execSqlLst("search Result", rsltQ, ary);
              PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
              ResultSet  rs =(ResultSet)rsLst.get(1);
                pktListMap = new HashMap();
                try {
                    while (rs.next()) {
                        String vnm =jspUtil.nvl(rs.getString("vnm"));
                        String cert_No = jspUtil.nvl(rs.getString("cert_no"));
                        pktPrpMap = new HashMap();
                        String stkIdn = jspUtil.nvl(rs.getString("stk_idn"));
                        pktPrpMap.put("flg", jspUtil.nvl(rs.getString("flg")));
                        pktPrpMap.put("stt", jspUtil.nvl(rs.getString("dsp_stt")));
                        pktPrpMap.put("iss_idn",jspUtil.nvl(rs.getString("srch_id")));
                        pktPrpMap.put("stk_idn",stkIdn);
                        pktPrpMap.put("vnm",vnm);
                        pktPrpMap.put("qty",jspUtil.nvl(rs.getString("qty")));
                        pktPrpMap.put("class",jspUtil.nvl(rs.getString("class")));
                        pktPrpMap.put("cts", jspUtil.nvl(rs.getString("cts")));
                        pktPrpMap.put("cert_no",cert_No);
                        pktPrpMap.put("stt1",jspUtil.nvl(rs.getString("stt1")));
                        pktPrpMap.put("quot",jspUtil.nvl(rs.getString("rte")));
                        pktPrpMap.put("fquot",jspUtil.nvl(rs.getString("fquot")));
                        pktPrpMap.put("cmp",jspUtil.nvl(rs.getString("cmp")));
                        pktPrpMap.put("cmp_dis",jspUtil.nvl(rs.getString("cmp_dis")));
                        pktPrpMap.put("rap_rte",jspUtil.nvl(rs.getString("rap_rte")));
                        pktPrpMap.put("r_dis",jspUtil.nvl(rs.getString("r_dis")));
                        pktPrpMap.put("cert_lab", jspUtil.nvl(rs.getString("cert_lab")));
                        pktPrpMap.put("amt", jspUtil.nvl(rs.getString("amt")));
                        for (int j = 0; j < vwPrpLst.size(); j++) {
                            String prp = (String)vwPrpLst.get(j);

                            String fld = "prp_";
                            if (j < 9)
                                fld = "prp_00" + (j + 1);
                            else
                                fld = "prp_0" + (j + 1);

                            String val = jspUtil.nvl(rs.getString(fld));
                            if (prp.toUpperCase().equals("RAP_DIS"))
                                val = jspUtil.nvl(rs.getString("r_dis"));
                            if (prp.toUpperCase().equals("RAP_RTE"))
                                val = jspUtil.nvl(rs.getString("rap_rte"));
                            if(prp.equals("RTE"))
                                val = jspUtil.nvl(rs.getString("rte"));
                            if(prp.equals("KTSVIEW"))
                                val = jspUtil.nvl(rs.getString("kts"));
                            if(prp.equals("COMMENT"))
                                val = jspUtil.nvl(rs.getString("cmnt"));
                               

                                pktPrpMap.put(prp, val);
                        }

                        pktListMap.put(stkIdn , pktPrpMap);
                        pktStkIdnList.add(stkIdn);
                    }
                    rs.close();
                    stmt.close();
                } catch (SQLException sqle) {
                    // TODO: Add catch code
                    sqle.printStackTrace();
                }           
               session.setAttribute("pktStkIdnList", pktStkIdnList);
                return pktListMap;
            }
        public void packetData(DBMgr db,InfoMgr info, SingleToMixTrfForm form,HttpSession session,DBUtil util) {
            int ct=0;
            JspUtil jspUtil = new JspUtil();
            HashMap dbinfo = info.getDmbsInfoLst();
            int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
            String delQ = " Delete from gt_srch_rslt ";
            ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
            ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
            String vnm = jspUtil.nvl((String)form.getValue("vnm"));
            String stt = jspUtil.nvl((String)form.getValue("stt"));
            String srchTyp =jspUtil.nvl((String)form.getValue("srchRef"));
            ArrayList ary = new ArrayList();
          if(!vnm.equals(""))
          vnm = jspUtil.getVnm(vnm);
         
             if(!vnm.equals("")){
              if(srchTyp.equals("vnm") || srchTyp.equals("cert_no")){
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
                    String vnmSub =vnm.substring(fromLoc, toLoc);
              
              String srchRefQ =
              "    Insert into gt_srch_rslt ( srch_id, pkt_ty, stk_idn, vnm, qty, cts, pkt_dte, stt,  rap_rte,rap_dis , cert_lab, cert_no, flg, sk1, fquot, quot , cmp  ) " +
              "     select  1 srchId , pkt_ty, b.idn, b.vnm, b.qty qty, b.cts cts, b.dte, stt,  rap_rte ,  decode(rap_rte ,'1',null, trunc((nvl(upr,cmp)/rap_rte*100)-100, 2)) " +
              "     , cert_lab, cert_no, 'Z' flg, sk1, upr, cmp , cmp " +
              "    from mstk b " +
              "     where  pkt_ty = 'NR' " ;
                  String srchStr = "";  
                  if(srchTyp.equals("vnm"))
                      srchStr = " and ( b.vnm in ("+vnmSub+") or b.tfl3 in ("+vnmSub+") ) ";
                  else if(srchTyp.equals("cert_no"))
                      srchStr = " and b.cert_no in ("+vnmSub+")";  
                  srchRefQ=srchRefQ+srchStr+" and b.stt=?";
                  ary = new ArrayList();
                  ary.add(stt);
                  ct = db.execUpd(" Srch Ref ", srchRefQ,ary);
              }}
          ary = new ArrayList();
          String pktPrp = "pkgmkt.pktPrp(0,?)";
          ary = new ArrayList();
          ary.add("SINGLETOMIX_VW");
          ct = db.execCall(" Srch Prp ", pktPrp, ary);
          }else{
              HashMap mprp = info.getMprp();
              HashMap prp = info.getPrp();
              ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_singleTomixGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_singleTomixGNCSrch");
              info.setGncPrpLst(genricSrchLst);
              HashMap paramsMap = new HashMap();
                  for(int i=0;i<genricSrchLst.size();i++){
                      ArrayList prplist =(ArrayList)genricSrchLst.get(i);
                      String lprp = (String)prplist.get(0);
                      String flg= (String)prplist.get(1);
                      String typ = jspUtil.nvl((String)mprp.get(lprp+"T"));
                      String prpSrt = lprp ;  
                      if(flg.equals("M")) {
                      
                          ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
                          ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
                          for(int j=0; j < lprpS.size(); j++) {
                          String lSrt = (String)lprpS.get(j);
                          String lVal = (String)lprpV.get(j);    
                          String reqVal1 = jspUtil.nvl((String)form.getValue(lprp + "_" + lVal),"");
                          if(!reqVal1.equals("")){
                          paramsMap.put(lprp + "_" + lVal, reqVal1);
                          }
                             
                          }
                      }else{
                      String fldVal1 = jspUtil.nvl((String)form.getValue(lprp+"_1"));
                      String fldVal2 = jspUtil.nvl((String)form.getValue(lprp+"_2"));
                      if(typ.equals("T")){
                          fldVal1 = jspUtil.nvl((String)form.getValue(lprp+"_1"));
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
              paramsMap.put("stt", stt);
              paramsMap.put("mdl", "SINGLETOMIX_VW");
              util.genericSrch(paramsMap);
          }
        }
        
        public ArrayList SINGLETOMIXPrprViw(HttpSession session ,DBMgr db){
            ArrayList asViewPrp = (ArrayList)session.getAttribute("singletomixViewLst");
            try {
                if (asViewPrp == null) {

                    asViewPrp = new ArrayList();
            
                  ArrayList  rsLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'SINGLETOMIX_VW' and flg='Y' order by rnk ",
                                   new ArrayList());
                  PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
                  ResultSet  rs1 =(ResultSet)rsLst.get(1);
                    while (rs1.next()) {

                        asViewPrp.add(rs1.getString("prp"));
                    }
                    rs1.close();
                    stmt.close();
                    session.setAttribute("singletomixViewLst", asViewPrp);
                }

            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
            
            return asViewPrp;
        }
//        public void singlesttList(HttpSession session , DBMgr db,InfoMgr info){
//            JspUtil jspUtil = new JspUtil();
//            ArrayList singletomixsttList = (session.getAttribute("singletomixsttList") == null)?new ArrayList():(ArrayList)session.getAttribute("singletomixsttList");
//            if(singletomixsttList.size()>0){
//                try {
//                HashMap dbinfo = info.getDmbsInfoLst();
//                String cnt=jspUtil.nvl((String)dbinfo.get("CNT"));
//                String mem_ip=jspUtil.nvl((String)dbinfo.get("MEM_IP"),"13.229.255.212");
//                int mem_port=Integer.parseInt(jspUtil.nvl((String)dbinfo.get("MEM_PORT"),"11211"));
//                MemcachedClient mcc = new MemcachedClient(new InetSocketAddress(mem_ip, mem_port));
//                singletomixsttList=(ArrayList)mcc.get(cnt+"_singletomixsttList");
//                if(singletomixsttList==null){
//                singletomixsttList=new ArrayList();
//            String memoPrntOptn = "select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and "
//            + "b.mdl = 'JFLEX' and b.nme_rule = 'SINGLETOMIX' and a.til_dte is null order by a.srt_fr ";
//            ResultSet rs = db.execSql("MIXTOSINGLE", memoPrntOptn, new ArrayList());
//            while (rs.next()) {
//                ArrayList data = new ArrayList();
//                data.add(jspUtil.nvl(rs.getString("chr_fr")));
//                data.add(jspUtil.nvl(rs.getString("dsc")));
//                singletomixsttList.add(data);
//            }
//                rs.close();
//                Future fo = mcc.delete(cnt+"_singletomixsttList");
//                System.out.println("add status:_singletomixsttList" + fo.get());
//                fo = mcc.set(cnt+"_singletomixsttList", 24*60*60, singletomixsttList);
//                System.out.println("add status:_singletomixsttList" + fo.get());
//                }
//                mcc.shutdown();
//            } catch (SQLException sqle) {
//            // TODO: Add catch code
//            sqle.printStackTrace();
//            }catch(Exception ex){
//                 System.out.println( ex.getMessage() );
//            }
//            session.setAttribute("singletomixsttList", singletomixsttList);   
//            }
//        }    
        
//        public ArrayList ASGenricSrch(HttpServletRequest req , HttpServletResponse res){
//        init(req,res);
//        ArrayList asViewPrp = (ArrayList)session.getAttribute("singleTomixGNCSrch");
//        try {
//            if (asViewPrp == null) {
//
//                asViewPrp = new ArrayList();
//                ResultSet rs1 =
//                    db.execSql(" Vw Lst ", "Select prp, flg  from rep_prp where mdl = 'SINGLETOMIX_SRCH' and flg <> 'N' order by rnk ",
//                               new ArrayList());
//                while (rs1.next()) {
//                    ArrayList asViewdtl=new ArrayList();
//                    asViewdtl.add(rs1.getString("prp"));
//                    asViewdtl.add(rs1.getString("flg"));
//                    asViewPrp.add(asViewdtl);
//                }
//                rs1.close();
//                session.setAttribute("singleTomixGNCSrch", asViewPrp);
//            }
//
//        } catch (SQLException sqle) {
//            // TODO: Add catch code
//            sqle.printStackTrace();
//        }
//        
//        return asViewPrp;
//    }
//    
        
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
              util.updAccessLog(req,res,"Single TO MIX Action", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"Single TO MIX Action", "init");
          }
          }
          return rtnPg;
          }
    }
