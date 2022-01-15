package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.assort.AssortIssueImpl;
import ft.com.assort.AssortIssueInterface;
import ft.com.dao.MNme;
import ft.com.dao.Mprc;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.PacketPrintForm;

import java.io.IOException;

import java.io.OutputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class IssueReturnRptAction extends DispatchAction {

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
         util.updAccessLog(req,res,"Issue Return Report", "load");
         IssueReturnRptForm udf = (IssueReturnRptForm)af;
         GenericInterface genericInt = new GenericImpl();
        udf.resetAll();
      
        AssortIssueInterface assortInt = new AssortIssueImpl();
        ArrayList mprcList = assortInt.getPrc(req,res);
        udf.setMprcList(mprcList);
        ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_ISSUE_RTN_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_ISSUE_RTN_SRCH");
        info.setGncPrpLst(assortSrchList);
        ArrayList empList = assortInt.getEmp(req,res);
        udf.setEmpList(empList);
         udf.setValue("issueId", "");
         udf.setValue("vnmLst", "");
         udf.setValue("prcIdn", "");
         udf.setValue("empIdn", "");
         udf.setValue("Typ", "");
        util.updAccessLog(req,res,"Issue Return Report", "ISSUE_RTN_SRCH :"+assortSrchList.size());
        util.updAccessLog(req,res,"Issue Return Report", "End");
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
             GenericInterface genericInt = new GenericImpl();
         util.updAccessLog(req,res,"Issue Return Report", "fetch");
         IssueReturnRptForm udf = (IssueReturnRptForm)af;
         String issueId = util.nvl((String)udf.getValue("issueId"));
         String vnmLst = util.nvl((String)udf.getValue("vnmLst"));
         String Typ = util.nvl((String)udf.getValue("Typ"));
         String empIdn = util.nvl((String)udf.getValue("empIdn"));
         String prcIdn = util.nvl((String)udf.getValue("prcIdn"));
         String rtnfrmdte = util.nvl((String)udf.getValue("rtnfrmdte"));
         String rtntodte = util.nvl((String)udf.getValue("rtntodte"));
         String issfrmdte = util.nvl((String)udf.getValue("issfrmdte"));
         String isstodte = util.nvl((String)udf.getValue("isstodte"));
         ArrayList prpDspBlocked = info.getPageblockList();
         HashMap params = new HashMap();
         params.put("issueId", issueId);
         params.put("vnm", vnmLst);
         params.put("Typ", Typ);
         params.put("empIdn", empIdn);
         params.put("prcIdn", prcIdn);
         params.put("rtnfrmdte", rtnfrmdte);
         params.put("rtntodte", rtntodte);
         params.put("issfrmdte", issfrmdte);
         params.put("isstodte", isstodte);
         ArrayList pktList =FecthResult(udf,req,res, params);
         ArrayList vwPrpLst= genericInt.genericPrprVw(req, res, "ISSUE_RTN_RPT","ISSUE_RTN_RPT");
             String cnt = (String)info.getDmbsInfoLst().get("CNT");
         ArrayList itemHdr = new ArrayList();
         itemHdr.add("Issue Id");
         itemHdr.add("Process");
         itemHdr.add("Employee");
         itemHdr.add("Issue Date");
         itemHdr.add("Return Date");
         itemHdr.add("Time Diff");
         itemHdr.add("Aud_Created_By");
         itemHdr.add("Aud_Modified_By");
         itemHdr.add("Packet Id");
         
         for(int m=0;m<vwPrpLst.size();m++){
             String prp=(String)vwPrpLst.get(m);
             if(prp.equals("CRTWT") && cnt.equalsIgnoreCase("ag")){
                      itemHdr.add("CRT ISS");    
             } 
             if(prp.equals("COL") && cnt.equalsIgnoreCase("ag")){
                      itemHdr.add("COL ISS");    
             } 
             if(prp.equals("CLR") && cnt.equalsIgnoreCase("ag")){
                      itemHdr.add("CLR ISS");    
             } 
             if(prpDspBlocked.contains(prp)){
             }else{
             itemHdr.add(prp);
             if(prp.equals("RTE")){
             itemHdr.add("Amount");    
             }
            
                 }
         }
         session.setAttribute("itemHdr", itemHdr); 
         session.setAttribute("pktList", pktList); 
         udf.setValue("issueId", issueId);
		 req.setAttribute("view", "y");
         udf.setValue("vnm", vnmLst);
         udf.setValue("Typ", Typ);
         udf.setValue("empIdn", empIdn);
         udf.setValue("prcIdn", prcIdn);
         udf.setValue("rtnfrmdte", rtnfrmdte);
         udf.setValue("rtntodte", rtntodte);
         util.updAccessLog(req,res,"Issue Return Report", "ItemHdr :"+itemHdr.size());
         util.updAccessLog(req,res,"Issue Return Report", "PktList :"+pktList.size());
         util.updAccessLog(req,res,"Issue Return Report", "End");
         return am.findForward("load");
         }
     }
    public ArrayList FecthResult(IssueReturnRptForm udf,HttpServletRequest req, HttpServletResponse res, HashMap params){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_ISSUE_RTN_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_ISSUE_RTN_SRCH");
        info.setGncPrpLst(genricSrchLst);
        HashMap prp = info.getPrp();
        HashMap mprp = info.getMprp();
        ArrayList pktList = new ArrayList();
        String vnm = util.nvl((String)params.get("vnm"));
        String issId = util.nvl((String)params.get("issueId"));
        String Typ = util.nvl((String)params.get("Typ"));
        String empIdn = util.nvl((String)params.get("empIdn"));
        String prcIdn = util.nvl((String)params.get("prcIdn"));
        String rtnfrmdte = util.nvl((String)params.get("rtnfrmdte"));
        String rtntodte = util.nvl((String)params.get("rtntodte"));
        String issfrmdte = util.nvl((String)params.get("issfrmdte"));
        String isstodte = util.nvl((String)params.get("isstodte"));
        ArrayList ary = null;
        String delQ = " Delete from gt_srch_multi ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        
        String srchRefQ = 
        "    Insert into gt_srch_multi (pkt_ty, stk_idn, vnm, stt , flg , qty , cts , srch_id , rmk, quot , rap_rte,rap_dis) " + 
        " select a.pkt_ty, a.idn, a.vnm, a.stt , 'Z' , a.qty , a.cts , b.iss_id,a.tfl3,decode(nvl(upr, 0),0, cmp, upr)"+
        " , a.rap_rte,decode(a.rap_rte ,'1',null, trunc((nvl(upr,cmp)/rap_rte*100)-100, 2))  from mstk a ,iss_rtn_dtl b,iss_rtn c where a.idn = b.iss_stk_idn and b.iss_id=c.iss_id "+
        " and  a.cts > 0  ";
        ary = new ArrayList();
       
        if(vnm.length()>2){
           vnm = util.getVnm(vnm);
            srchRefQ = srchRefQ+" and ( a.vnm in ("+vnm+") or a.tfl3 in ("+vnm+") ) " ;
        }
        
        if(!issId.equals("")){
            srchRefQ = srchRefQ+" and b.iss_id = ? " ;
            ary.add(issId);
        }
        
        if(!empIdn.equals("0")){
            srchRefQ = srchRefQ+" and b.iss_emp_id = ? " ;
            ary.add(empIdn);
        }
        if(!prcIdn.equals("0")){
            srchRefQ = srchRefQ+" and c.prc_id = ? " ;
            ary.add(prcIdn);
        }
        if(!Typ.equals("") && !Typ.equals("0")){
            srchRefQ = srchRefQ+" and b.stt =? " ;
            ary.add(Typ);
        }
        if(!rtnfrmdte.equals("") && !rtntodte.equals("")){
            srchRefQ = srchRefQ+" and trunc(b.rtn_dt) between to_date('"+rtnfrmdte+"' , 'dd-mm-yyyy') and to_date('"+rtntodte+"' , 'dd-mm-yyyy') ";
        }
        if(!issfrmdte.equals("") && !isstodte.equals("")){
            srchRefQ = srchRefQ+" and trunc(b.iss_dt) between to_date('"+issfrmdte+"' , 'dd-mm-yyyy') and to_date('"+isstodte+"' , 'dd-mm-yyyy') ";
        }
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        
//        String pktPrp = "pkgmkt.multiCertPrp(?)";
//        ary = new ArrayList();
//        ary.add("ISSUE_RTN_RPT");
//        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        HashMap paramsMap = new HashMap();
        for (int i = 0; i < genricSrchLst.size(); i++) {
                    ArrayList srchPrp = (ArrayList)genricSrchLst.get(i);
                    String lprp = (String)srchPrp.get(0);
                    String flg= (String)srchPrp.get(1);
                    String prpSrt = lprp ;  
                    String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
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
                           if(fldVal2.equals(""))
                               fldVal2=fldVal1;
                             if(!fldVal1.equals("") && !fldVal2.equals("")){
                                            paramsMap.put(lprp+"_1", fldVal1);
                                            paramsMap.put(lprp+"_2", fldVal2);
                            }   
                        }            
                }
        if(paramsMap.size()>0){
        paramsMap.put("stt", "ALL");
        paramsMap.put("mdl", "ISSUE_RTN_RPT");
        int lSrchId=util.genericSrchEntries(paramsMap);
     
            ary = new ArrayList();
            ary.add(String.valueOf(lSrchId));
            ary.add("NA");
            ct = db.execCall(" Gt Prp ", "SRCH_PKG.FLTR_SRCH(pSrchId => ?,pTyp => ?)", ary);
        }
        String pktPrp = "iss_rtn_pkg.POP_GT_RTN_PRP(pMdl => ?, pTyp => ?)";
        ary = new ArrayList();
        ary.add("ISSUE_RTN_RPT");
        ary.add("M");
        ct = db.execCall(" Gt Prp ", pktPrp, ary);
        
        pktList = SearchResult(req , res, vnm);
       
        return pktList;
    }
    
    public ArrayList SearchResult(HttpServletRequest req ,HttpServletResponse res, String vnmLst ){
        
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr();
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            ArrayList pktList = new ArrayList();
            String cnt = (String)info.getDmbsInfoLst().get("CNT");
            GenericInterface genericInt = new GenericImpl();
            ArrayList vwPrpLst= genericInt.genericPrprVw(req, res, "ISSUE_RTN_RPT","ISSUE_RTN_RPT");
            int inxRdis = vwPrpLst.indexOf("RTE");
            String rdisPrp = "";
                if(inxRdis==-1)
                    rdisPrp = "quot";
                else{
                    inxRdis = inxRdis+1;
            rdisPrp = "prp_00"+inxRdis;
            if(inxRdis > 9)
                rdisPrp = "prp_0"+inxRdis;
                }
           
            ArrayList itemHdr = new ArrayList();
            String  srchQ =  " select a.srch_id,c.prc, c.emp, a.stk_idn ,a.img3, a.pkt_ty,  a.vnm, a.pkt_dte, a.stt , a.qty ,a.quot, a.cts,to_char(trunc(a.cts,2) * a.quot, '99999990.00') amt,a.rap_rte ,decode(a.rap_rte ,'1',null, trunc((nvl(quot,cmp)/a.rap_rte*100)-100, 2))  rap_dis,decode(a.rap_rte ,'1',null, trunc((nvl("+rdisPrp+",quot)/a.rap_rte*100)-100, 2))  upr_dis " +
                ", a.srch_id , a.rmk ,floor(((nvl(b.iss_dt,sysdate)-nvl(b.rtn_dt,sysdate))*24*60*60)/3600)\n" + 
                "           || ':' ||\n" + 
                "           floor((((nvl(b.iss_dt,sysdate)-nvl(b.rtn_dt,sysdate))*24*60*60) -\n" + 
                "          floor(((nvl(b.iss_dt,sysdate)-nvl(b.rtn_dt,sysdate))*24*60*60)/3600)*3600)/60)\n" + 
                "           || ':' ||\n" + 
                "           round((((nvl(b.iss_dt,sysdate)-nvl(b.rtn_dt,sysdate))*24*60*60) -\n" + 
                "           floor(((nvl(b.iss_dt,sysdate)-nvl(b.rtn_dt,sysdate))*24*60*60)/3600)*3600 -\n" + 
                "           (floor((((nvl(b.iss_dt,sysdate)-nvl(b.rtn_dt,sysdate))*24*60*60) -\n" + 
                "           floor(((nvl(b.iss_dt,sysdate)-nvl(b.rtn_dt,sysdate))*24*60*60)/3600)*3600)/60)*60) ))\n" + 
                "           time_difference, b.iss_emp_id , b.iss_dt ,a.img,a.img2, b.rtn_dt,c.prc_id,b.aud_created_by,b.aud_modified_by   ";

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

            
            String rsltQ = srchQ + " from gt_srch_multi a,iss_rtn_dtl b,iss_rtn_v c " +
                                    "where flg =? and a.srch_id=b.iss_id and " +
                                    "c.iss_id=b.iss_id and " + 
                                    " a.stk_idn=b.iss_stk_idn order by a.sk1,a.srch_id desc ";
            
            ArrayList ary = new ArrayList();
            ary.add("Z");

            ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            try {
                while(rs.next()) {

                    HashMap pktPrpMap = new HashMap();
                    pktPrpMap.put("Process", util.nvl(rs.getString("prc")));
                    pktPrpMap.put("Employee", util.nvl(rs.getString("emp")));
                    pktPrpMap.put("Issue Date", util.nvl(rs.getString("iss_dt")));
                    pktPrpMap.put("Return Date", util.nvl(rs.getString("rtn_dt")));
                    pktPrpMap.put("COL ISS", util.nvl(rs.getString("img")));
                    pktPrpMap.put("CLR ISS", util.nvl(rs.getString("img2")));
                        pktPrpMap.put("CRT ISS", util.nvl(rs.getString("img3")));
                    pktPrpMap.put("Aud_Created_By", util.nvl(rs.getString("aud_created_by")));
                    pktPrpMap.put("Aud_Modified_By", util.nvl(rs.getString("aud_modified_by")));
                    pktPrpMap.put("Time Diff", util.nvl(rs.getString("time_difference")).replaceAll(".000000", ""));
                    String vnm = util.nvl(rs.getString("vnm"));
                    pktPrpMap.put("Packet Id",vnm);
                        String tfl3 = util.nvl(rs.getString("rmk"));
                        if(vnmLst.indexOf(tfl3)!=-1 && !tfl3.equals("")){
                            if(vnmLst.indexOf("'")==-1 && !tfl3.equals(""))
                                vnmLst =  vnmLst.replaceAll(tfl3,"");
                            else
                                vnmLst =  vnmLst.replaceAll("'"+tfl3+"'", "");
                        } else if(vnmLst.indexOf(vnm)!=-1 && !vnm.equals("")){
                            if(vnmLst.indexOf("'")==-1)
                                vnmLst =  vnmLst.replaceAll(vnm,"");
                            else
                                vnmLst =  vnmLst.replaceAll("'"+vnm+"'", "");
                        }      
    //                pktPrpMap.put("stk_idn",util.nvl(rs.getString("stk_idn")));
    //                pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
    //                pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
                        pktPrpMap.put("Issue Id",util.nvl(rs.getString("srch_id")));
                        pktPrpMap.put("Amount", util.nvl(rs.getString("amt")));
                    String cts = util.nvl(rs.getString("cts"));
                    for(int j=0; j < vwPrpLst.size(); j++){
                         String prp = (String)vwPrpLst.get(j);
                          
                          String fld="prp_";
                          if(j < 9)
                                  fld="prp_00"+(j+1);
                          else    
                                  fld="prp_0"+(j+1);
                        if(prp.equals("RAP_RTE"))
                            fld="rap_rte";
                        String val = util.nvl(rs.getString(fld)) ;
                        if(prp.equals("RTE")){
                            if(!val.equals("") && !cts.equals("")){
                            double amt = Double.parseDouble(val)*Double.parseDouble(cts);
                            amt = util.Round(amt, 2);
                            pktPrpMap.put("Amount", String.valueOf(amt));
                            }else{
                            if(val.equals(""))
                                val = util.nvl(rs.getString("quot")) ;
                            }
                          }
                      
                        if(prp.equals("RAP_DIS")){
                        val = util.nvl(rs.getString("rap_dis")) ;
                        }
                        if(prp.equals("UPR_DIS")){
                        val = util.nvl(rs.getString("upr_dis")) ;
                        }
                        pktPrpMap.put(prp, val);
                        }
                                  
                        pktList.add(pktPrpMap);
                    }
                rs.close(); pst.close();
            } catch (SQLException sqle) {

                // TODO: Add catch code
                sqle.printStackTrace();
            }
            if(!vnmLst.equals("")){
                vnmLst = util.pktNotFound(vnmLst);
                req.setAttribute("vnmNotFnd", vnmLst);
            }

            return pktList;
        }
    
    public ActionForward createXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        util.updAccessLog(req,res,"Issue Return Report", "createXL");
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "IssueReturnReport"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        ArrayList pktList = (ArrayList)session.getAttribute("pktList");
        ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
        HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
        hwb.write(out);
        out.flush();
        out.close();
        util.updAccessLog(req,res,"Issue Return Report", "End");
        return null;
        }
    }
    
    

//    public ArrayList ASGenricSrch(HttpServletRequest req , HttpServletResponse res){
//        init(req,res);
//        ArrayList asViewPrp = (session.getAttribute("ISSUE_RTN_SRCH") == null)?new ArrayList():(ArrayList)session.getAttribute("ISSUE_RTN_SRCH");
//        try {
//            if (asViewPrp.size()==0) {
//
//                asViewPrp = new ArrayList();
//                ResultSet rs1 =
//                    db.execSql(" Vw Lst ", "Select prp, flg  from rep_prp where mdl = 'ISSUE_RTN_SRCH' and flg <> 'N' order by rnk ",
//                               new ArrayList());
//                while (rs1.next()) {
//                    ArrayList asViewdtl=new ArrayList();
//                    asViewdtl.add(rs1.getString("prp"));
//                    asViewdtl.add(rs1.getString("flg"));
//                    asViewPrp.add(asViewdtl);
//                }
//                rs1.close();
//                session.setAttribute("ISSUE_RTN_SRCH", asViewPrp);
//            }
//
//        } catch (SQLException sqle) {
//            // TODO: Add catch code
//            sqle.printStackTrace();
//        }
//        
//        return asViewPrp;
//    }
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
                util.updAccessLog(req,res,"Issue Return Report", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Issue Return Report", "end");
            }
            }
            return rtnPg;
            }
    
    
    public IssueReturnRptAction() {
        super();
    }
}
