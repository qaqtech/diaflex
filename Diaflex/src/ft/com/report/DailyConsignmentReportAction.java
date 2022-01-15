package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.generic.GenericImpl;

import ft.com.generic.GenericInterface;
import ft.com.marketing.SearchQuery;

import java.io.IOException;

import java.io.OutputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

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


public class DailyConsignmentReportAction extends DispatchAction {
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
            util.getOpenCursorConnection(db,util,info);
            util.updAccessLog(req,res,"Daily Approve Report", "load start");
        DailyConsignmentReportForm dailyConsignmentReportForm = (DailyConsignmentReportForm)form;
      ResultSet rs = null;
      ArrayList ary = new ArrayList();
      String dtefrom = " trunc(sysdate) ";
      String dteto = " trunc(sysdate) ";
        String flg=util.nvl(req.getParameter("flg"));
        if(flg.equals("New")){
            dailyConsignmentReportForm.reset();
        }
      String dfr = util.nvl((String)dailyConsignmentReportForm.getValue("dtefr"));
      String dto = util.nvl((String)dailyConsignmentReportForm.getValue("dteto"));
      String spersonId = util.nvl((String)dailyConsignmentReportForm.getValue("styp"));
      String typ = util.nvl((String)dailyConsignmentReportForm.getValue("typ"));
      String buyerId = util.nvl(req.getParameter("nmeID"));
      String group = util.nvl((String)dailyConsignmentReportForm.getValue("group"));
      String memofr = util.nvl((String)dailyConsignmentReportForm.getValue("memofr"));
      String memoto = util.nvl((String)dailyConsignmentReportForm.getValue("memoto"));
      String dfNmeIdn=util.nvl((String)info.getDfNmeIdn());
      String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
      String sql="";
      String bql="";String dteQ="";
        String memotbl="";
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        ArrayList rolenmLst=(ArrayList)info.getRoleLst();
        String usrFlg=util.nvl((String)info.getUsrFlg());
        String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
      if(!dfr.equals(""))
        dtefrom = "to_date('"+dfr+"' , 'dd-mm-yyyy')";
      
      if(!dto.equals(""))
        dteto = "to_date('"+dto+"' , 'dd-mm-yyyy')";
    
       dteQ=" and trunc(b.dte)  between "+dtefrom+" and "+dteto;  
            
      if(!spersonId.equals("") && spersonId.length()>1){
        sql =sql+ " and d.emp_idn= ? ";
        ary.add(spersonId);
      }
        if(!typ.equals("") && typ.length()>1){
          sql =sql+ " and b.typ= ? ";
          ary.add(typ);
        }else{
          sql =sql+ " and b.typ in ('CS') ";  
        }
        if(!memofr.equals("") && !memoto.equals("")){
            sql =sql+ " and b.idn between ? and ? ";
        }
        if(memofr.equals("") && !memoto.equals("")){
            sql =sql+ " and b.idn between ? and ? ";
            memofr=memoto;
        }
        if(!memofr.equals("") && memoto.equals("")){
            sql =sql+ " and b.idn between ? and ? ";
            memoto=memofr;
        }
        if(!memofr.equals("") && !memoto.equals("")){
            ary.add(memofr);
            ary.add(memoto);
            memotbl=memofr+"-"+memoto;
            dteQ="";
        }
      if(!buyerId.equals("")){
        bql =bql+ "and d.nme_idn= ? ";
        ary.add(buyerId);
      }
        if(!group.equals("")){
          bql =bql+  "and d.grp_nme_idn= ? ";
          ary.add(group);
        }else{
        if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
        }else if(!dfgrpnmeidn.equals("") && !dfgrpnmeidn.equals("0")){
        bql =bql+" and d.grp_nme_idn= ?"; 
        ary.add(dfgrpnmeidn);
        }  
        }          
        
            if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
                    if(util.nvl((String)info.getDmbsInfoLst().get("EMP_LOCK")).equals("Y")){
                        bql += " and (d.emp_idn= ? or d.emp_idn in(select emp_idn from nme_v where typ='EMPLOYEE' and mgr_idn= ?)) ";  
                        ary.add(dfNmeIdn);
                        ary.add(dfNmeIdn);
                    }
            }
      
        HashMap approve = new HashMap();
        String loadqry =
        " select d.emp_idn approve_id, byr.get_nm(nvl(d.emp_idn,0)) sale_name, byr.get_nm(nvl(d.nme_idn,0)) byr, d.nme_idn byrid "+
         " , to_char(b.dte,'dd-mm-yyyy') dte ,to_number(to_char(b.dte, 'rrrrmmdd')) srt_dte , sum(b.qty) qty , to_char(sum(trunc(b.cts,2)),'999,990.99') cts "+
         " , to_char(sum(trunc(b.cts,2)*(nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte,1))),'999,9999,999,990.00') vlu "+
        " from mjan a,jandtl b, mstk c,mnme d "+
        " where a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn  and c.pkt_ty in('NR','SMX') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) "+
       "  and b.stt not in ('APRT','RT','CL','ALC','MRG') "+sql+""+bql+dteQ+
       " group by d.emp_idn, d.nme_idn, d.fnme, to_char(b.dte,'dd-mm-yyyy') ,to_number(to_char(b.dte, 'rrrrmmdd')) "+
       " order by 2,3,7 ";

      


            ArrayList outLst = db.execSqlLst("loadqry", loadqry, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        int i = 0;
        while (rs.next()) {
            HashMap approvedtl = new HashMap();
            i++;
            approvedtl.put("approve_id", util.nvl(rs.getString("approve_id")));
            approvedtl.put("salename", util.nvl(rs.getString("sale_name")));
            approvedtl.put("byr", util.nvl(rs.getString("byr")));
            approvedtl.put("dte", util.nvl(rs.getString("dte")));
            approvedtl.put("qty", util.nvl(rs.getString("qty")));
            approvedtl.put("cts", util.nvl(rs.getString("cts")));
            approvedtl.put("typ", "");
            approvedtl.put("byrid", util.nvl(rs.getString("byrid")));
            approvedtl.put("vlu", util.nvl(rs.getString("vlu")));

            approve.put(i, approvedtl);
        }
        rs.close(); pst.close();
        req.setAttribute("approveTbl", approve);
//  Added by Reshmi on 15-11-2011
        HashMap totalapprove= new HashMap();
        String totalQry = " select d.emp_idn approve_id "+
        " , byr.get_nm(nvl(d.emp_idn,0)) sale_name "+
        " , sum(b.qty) qty  , to_char(sum(trunc(b.cts,2)),'999,990.99') cts "+
        " , to_char(sum(trunc(b.cts,2)*(nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte,1))),'999,999,999,990.00') vlu "+
       "  from mjan a,jandtl b, mstk c,mnme d "+
       " where a.idn=b.idn and b.mstk_idn=c.idn  and c.pkt_ty in('NR','SMX') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA'))  "+
        "  and b.stt not in ('APRT','RT','CL','ALC','MRG')  "+sql+""+bql+
        " and d.nme_idn = a.nme_idn "+dteQ+
       " group by d.emp_idn order by 2 ";

            outLst = db.execSqlLst("totalqry", totalQry, ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            HashMap totalDtl = new HashMap();
            totalDtl.put("approve_id", util.nvl(rs.getString("approve_id")));
            totalDtl.put("qty", util.nvl(rs.getString("qty")));
            totalDtl.put("cts", util.nvl(rs.getString("cts")));
            totalDtl.put("vlu", util.nvl(rs.getString("vlu")));
            totalapprove.put(util.nvl(rs.getString("approve_id")), totalDtl);
        }
        rs.close(); pst.close();
        req.setAttribute("totalapprove", totalapprove);
        
      HashMap grandDtl = new HashMap();
      String grandQry = " select sum(b.qty) qty "+
       " , to_char(sum(trunc(b.cts,2)),'999,990.00') cts "+
       " , to_char(sum(trunc(b.cts,2)*nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte,1)),'999,9999,999,990.00') vlu "+
       " from mjan a,jandtl b, mstk c,mnme d "+
       " where a.idn=b.idn and b.mstk_idn=c.idn  and c.pkt_ty in('NR','SMX') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) "+
      "  and b.stt not in ('APRT','RT','CL','ALC','MRG')  "+sql+""+bql+
      " and d.nme_idn=a.nme_idn "+dteQ;

            outLst = db.execSqlLst("grand total", grandQry, ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
      while (rs.next()) {
                
          grandDtl.put("qty", util.nvl(rs.getString("qty")));
          grandDtl.put("cts", util.nvl(rs.getString("cts")));
          grandDtl.put("vlu", util.nvl(rs.getString("vlu")));
       
      }
        rs.close(); pst.close();
        req.setAttribute("grandtotal", grandDtl);
        dailyConsignmentReportForm.setValue("memotbl", memotbl);
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("DAILY_CONSIGNMENT_RPT");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("DAILY_CONSIGNMENT_RPT");
        allPageDtl.put("DAILY_CONSIGNMENT_RPT",pageDtl);
        }
        info.setPageDetails(allPageDtl);
        util.saleperson();
        if(cnt.equals("hk"))
        util.groupcompany();
        util.updAccessLog(req,res,"Daily Approve Report", "load end");
        return am.findForward("load");
        }
    }
  
    public ActionForward fecth(ActionMapping am, ActionForm form,
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
        DailyConsignmentReportForm dailyConsignmentReportForm = (DailyConsignmentReportForm)form;
        util.updAccessLog(req,res,"Daily Approve Report", "fecth start");
        util.updAccessLog(req,res,"Daily Approve Report", "fecth end");
        return am.findForward("load");
        }
    }
    
    public ActionForward pktDtlExcel(ActionMapping am, ActionForm form,
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
            util.updAccessLog(req,res,"Daily Approve Report", "pktDtlExcel start");
            GenericInterface genericInt = new GenericImpl();
                  ArrayList vwprpLst = genericInt.genericPrprVw(req,res,"DAILY_CON_VW","DAILY_CON_VW");
                  HashMap dbinfo = info.getDmbsInfoLst();
                   String cnt = util.nvl((String)dbinfo.get("CNT"));
        DailyConsignmentReportForm dailyConsignmentReportForm = (DailyConsignmentReportForm)form;
        String nmeidn = util.nvl(req.getParameter("nmeidn"));
        String dte = util.nvl(req.getParameter("dte"));
        String memotbl = util.nvl(req.getParameter("memotbl"));
        ArrayList pktDtlList = new ArrayList();
        ArrayList itemHdr = new ArrayList();
        ArrayList ary = new ArrayList();
        ArrayList prpDspBlocked = info.getPageblockList();
        itemHdr.add("APPROVEID");
        itemHdr.add("FROM");
        itemHdr.add("VNM");
        itemHdr.add("SALEPERSON");
        itemHdr.add("BYR");
        itemHdr.add("COUNTRY");
            for(int i=0;i<vwprpLst.size();i++){
                String prp=util.nvl((String)vwprpLst.get(i));
                if(prpDspBlocked.contains(prp)){
                }else if(!itemHdr.contains(prp)){
                itemHdr.add(prp);
                if(prp.equals("RTE")){
                    itemHdr.add("SALE RTE");
                    itemHdr.add("AMOUNT");
                }
                }  
            }            
            itemHdr.add("ApproveDte");
            if( cnt.equals("hk")){
            itemHdr.add("WEBUSER");
            itemHdr.add("COMMENT");  
            }
        nmeidn=nmeidn.replaceFirst(",", "");
        dte=dte.replaceFirst(",", "");
        String[] nmeidnLst=nmeidn.split(",");
        String[] dteLst=dte.split(",");
        System.out.println(nmeidn);
            String mprpStr = "";
            String mdlPrpsQ = " Select Decode ( Rank() Over ( Order By Srt),1,' ',',')||''''||Prp||''''||' '||Upper(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1')) str From Rep_Prp Rp Where rp.MDL = ? order by srt " ;
            ary = new ArrayList();
            ary.add("DAILY_CON_VW");
            ArrayList outLst = db.execSqlLst("mprp ", mdlPrpsQ , ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()) {
            String val = util.nvl((String)rs.getString("str"));
            mprpStr = mprpStr +" "+val;
            }
            rs.close(); pst.close();
        for(int i=0;i<nmeidnLst.length;i++){
        nmeidn=nmeidnLst[i];  
        dte=dteLst[i];  
       String dtefrom = " trunc(sysdate) ";
       String dteto = " trunc(sysdate) ";
        String Qry= "";   
        if(!nmeidn.equals("") && !dte.equals("")){
              dtefrom = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
              dteto = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
            if( cnt.equals("hk")){
              Qry=",b.AUD_CREATED_BY usrname, a.rmk cmnt";
             }
            ary = new ArrayList();
         String pktDtlSql = " with " +
        " STKDTL as " +
        " ( Select c.sk1,a.idn approve_id,nvl(nvl(nvl(st.txt,st.dte),st.num),st.prt1) atr , mprp,decode(a.flg, 'NN','DF', 'CNF', 'WEB', 'DF') frm,c.idn,to_char(b.cts,'99999999990.99') cts,c.vnm,c.rap_rte raprte,nvl(c.upr,c.cmp) rate, byr.get_nm(nvl(d.emp_idn,0)) sale_name, DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', byr.get_nm(nvl(d.nme_idn,0)),d.fnme) byr,nvl(b.fnl_sal, b.quot) memoQuot\n" + 
            ", to_char(b.cts*(nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte,1)),'999,9999,999,990.00') amt,byr.get_country(nvl(a.nme_idn,0)) country,to_char((((nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte,1))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') r_dis,to_char(b.dte, 'dd-Mon-rrrr HH24:mi:ss') dte " +Qry+ " \n" + 
            " From stk_dtl st,mjan a,jandtl b, mstk c,mnme d where \n" + 
            "st.mstk_idn=c.idn and a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn  and c.pkt_ty in('NR','SMX') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) \n" + 
            "and b.stt not in ('APRT','RT','CL','ALC','MRG') and b.typ in ('CS') And d.Nme_Idn= ?\n";
            
            ary.add(nmeidn);
            if(!memotbl.equals("")){
            String[] memotblList = memotbl.split("-");
                pktDtlSql+=" and b.idn between ? and ? ";
                ary.add(memotblList[0].trim());
                ary.add(memotblList[1].trim());
            }
        pktDtlSql+=" and trunc(b.dte)  between "+dtefrom+" and "+dteto+"  \n"+
            "and exists (select 1 from rep_prp rp where rp.MDL = 'DAILY_CON_VW' and st.mprp = rp.prp)  And st.Grp = 1) " +
            " Select * from stkDtl PIVOT " +
            " ( max(atr) " +
            " for mprp in ( "+mprpStr+" ) ) order by 1 " ;

            outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            HashMap pktDtl = new HashMap();
            pktDtl.put("APPROVEID", util.nvl(rs.getString("approve_id")));
            pktDtl.put("FROM", util.nvl(rs.getString("frm")));
            pktDtl.put("VNM", util.nvl(rs.getString("vnm")));
            pktDtl.put("SALEPERSON", util.nvl(rs.getString("sale_name")));
            pktDtl.put("BYR", util.nvl(rs.getString("byr")));
            pktDtl.put("COUNTRY", util.nvl(rs.getString("country")));
            pktDtl.put("RTE", util.nvl(rs.getString("rate")));
            pktDtl.put("ApproveDte", util.nvl(rs.getString("dte")));
            pktDtl.put("SALE RTE", util.nvl(rs.getString("memoQuot")));
            pktDtl.put("AMOUNT", util.nvl(rs.getString("amt")));
            pktDtl.put("RAP_RTE", util.nvl(rs.getString("raprte")));
            if( cnt.equals("hk")){
            pktDtl.put("WEBUSER", util.nvl(rs.getString("usrname")));
            pktDtl.put("COMMENT", util.nvl(rs.getString("cmnt")));
            }
            for (int v = 0; v < vwprpLst.size(); v++) {
            String vwPrp = (String)vwprpLst.get(v);
            String fldName = vwPrp;
            if(vwPrp.toUpperCase().equals("H&A"))
            fldName = "H_A";
            
            if(vwPrp.toUpperCase().equals("COMMENT"))
            fldName = "COM1";
            
            if(vwPrp.toUpperCase().equals("REMARKS"))
            fldName = "REM1";
            
            if(vwPrp.toUpperCase().equals("COL-DESC"))
            fldName = "COL_DESC";
            
            if(vwPrp.toUpperCase().equals("COL-SHADE"))
            fldName = "COL_SHADE";
            
            if(vwPrp.toUpperCase().equals("FL-SHADE"))
            fldName = "FL_SHADE";
            
            if(vwPrp.toUpperCase().equals("STK-CTG"))
            fldName = "STK_CTG";
            
            if(vwPrp.toUpperCase().equals("STK-CODE"))
            fldName = "STK_CODE";
            
            if(vwPrp.toUpperCase().equals("SUBSIZE"))
            fldName = "SUBSIZE1";
            
            if(vwPrp.toUpperCase().equals("SIZE"))
            fldName = "SIZE1";
            
            if(vwPrp.toUpperCase().equals("MIX_SIZE"))
            fldName = "MIX_SIZE1";
            
            if(vwPrp.toUpperCase().equals("STK-CTG"))
            fldName = "STK_CTG";
            
            if(vwPrp.toUpperCase().equals("CRN-OP"))
            fldName = "CRN_OP";
            
            if(vwPrp.toUpperCase().equals("CRTWT"))
            fldName = "cts";
            
            if(vwPrp.toUpperCase().equals("RAP_DIS"))
            fldName = "r_dis";
            
            if(vwPrp.toUpperCase().equals("RTE"))
            fldName = "rate";
            
            if (vwPrp.toUpperCase().equals("RAP_RTE"))
            fldName = "raprte";
                if(vwPrp.toUpperCase().equals("MEM_COMMENT"))
                fldName = "MEM_COM1";
            String fldVal = util.nvl((String)rs.getString(fldName));
            
            pktDtl.put(vwPrp, fldVal);
            }
            pktDtl.put("RTE",util.nvl(rs.getString("rate")));
            pktDtl.put("RAP_DIS",util.nvl(rs.getString("r_dis")));
            pktDtl.put("CRTWT",util.nvl(rs.getString("cts")));
            pktDtl.put("RAP_RTE",util.nvl(rs.getString("raprte")));
            pktDtlList.add(pktDtl);
        }
            rs.close(); pst.close();
            
        }
        }
        session.setAttribute("pktList", pktDtlList);
        session.setAttribute("itemHdr",itemHdr);
        util.updAccessLog(req,res,"Daily Approve Report", "pktDtlExcel end");
        return am.findForward("pktDtl"); 
        }
    }
    
    public ActionForward pktDtlSapphire(ActionMapping am, ActionForm form,
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
        util.updAccessLog(req,res,"Daily Consignment Report", "pktDtlExcel start");
        GenericInterface genericInt = new GenericImpl();
            DailyConsignmentReportForm dailyConsignmentReportForm = (DailyConsignmentReportForm)form;
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "SAPPHIRE.xls";
        res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        res.setContentType(CONTENT_TYPE);
        String nmeId="142812";
        ArrayList vwPrpLst =new ArrayList();
            String nmeidn = util.nvl(req.getParameter("nmeidn"));
            String dte = util.nvl(req.getParameter("dte"));
            String memotbl = util.nvl(req.getParameter("memotbl"));
            nmeidn=nmeidn.replaceFirst(",", "");
            dte=dte.replaceFirst(",", "");
            String[] nmeidnLst=nmeidn.split(",");
            String[] dteLst=dte.split(",");
        ArrayList ary = new ArrayList();
        db.execUpd("delete from gt", "delete from gt_srch_rslt", new ArrayList());
            for(int i=0;i<nmeidnLst.length;i++){
            nmeidn=nmeidnLst[i];   
            dte=dteLst[i];  
        String dtefrom = " trunc(sysdate) ";
        String dteto = " trunc(sysdate) ";
        if(!nmeidn.equals("") && !dte.equals("")){
              dtefrom = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
              dteto = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
         String pktDtlSql = "Insert into gt_srch_rslt (rln_idn, srch_id, pkt_ty, stk_idn, vnm, qty, cts, pkt_dte, stt, cmp, rap_rte, cert_lab, cert_no, flg, sk1, quot, rap_dis, CERTIMG, DIAMONDIMG, JEWIMG, SRAYIMG, AGSIMG, MRAYIMG, RINGIMG, LIGHTIMG, REFIMG, VIDEOS, VIDEO_3D)\n" + 
         "select a.nmerel_idn,a.idn,c.pkt_ty,c.idn,c.vnm,c.qty,b.cts,c.dte,c.stt,TRUNC (NVL (b.fnl_sal, b.quot) / NVL (a.exh_rte, 1), 2) fnl_usd,c.rap_rte,c.cert_lab,c.cert_no,'Z' flg,c.sk1,TRUNC (NVL (b.fnl_sal, b.quot) / NVL (a.exh_rte, 1), 2) fnl_usd,decode(c.rap_rte, 1, null, trunc(100 - (TRUNC (NVL (b.fnl_sal, b.quot) / NVL (a.exh_rte, 1), 2)/c.rap_rte*100), 2)),CERTIMG, DIAMONDIMG, JEWIMG, SRAYIMG, AGSIMG, MRAYIMG, RINGIMG, LIGHTIMG, REFIMG, VIDEOS, VIDEO_3D\n" + 
         "From mjan A , jandtl B,Mstk C,Mnme d\n" + 
         "Where A.Idn = B.Idn And B.Mstk_Idn=C.Idn and d.nme_idn=a.nme_idn  and c.pkt_ty in('NR','SMX') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) \n" + 
         "And Trunc(B.Dte) Between "+dtefrom+" And "+dteto+"\n" +
         "And a.Nme_Idn=? And B.Stt Not In ('APRT','RT','CL','ALC','MRG') And B.Typ In ('CS')";
         ary = new ArrayList();
         ary.add(nmeidn);
         int ct = db.execUpd(" Srch Prp ", pktDtlSql, ary);
        }
        }
        
        ary = new ArrayList();
        ary.add(nmeId);
        int ct = db.execCall(" Srch Prp ", "dp_pop_gt_mapping(pIdn=>?)", ary);
            
        HashMap prpDsc=new HashMap();         
        ary=new ArrayList();
        ary.add(nmeId);
        String prpqry="select idx mprp,nvl(w3,idx) dsc, rank() over (order by srt) rnk from prp_map_web  where name_id = ? and mprp = 'DFCOL'";

            ArrayList outLst = db.execSqlLst(" Vw Lst ",prpqry , ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs1 = (ResultSet)outLst.get(1);
        while (rs1.next()) {
        prpDsc.put(util.nvl(rs1.getString("mprp")), util.nvl(rs1.getString("dsc")));
        }
        rs1.close(); pst.close(); pst.close();
    
        ary=new ArrayList();
        ary.add(nmeId);
        prpqry="select idx prp, rank() over (order by srt) rnk from prp_map_web  where name_id = ? and mprp = 'DFCOL'";

            outLst = db.execSqlLst(" Vw Lst ",prpqry , ary);
            pst = (PreparedStatement)outLst.get(0);
            rs1 = (ResultSet)outLst.get(1);
        while (rs1.next()) {
        vwPrpLst.add(rs1.getString("prp"));
        }
        rs1.close(); pst.close();
            
        ArrayList pktList=stockList(req,vwPrpLst,nmeId);
        HSSFWorkbook hwb = xlUtil.getSapphireXl(vwPrpLst, pktList,prpDsc);
            hwb.write(out);
            out.flush();
            out.close();
            util.updAccessLog(req,res,"Daily Consignment Report", "pktDtlExcel end");
            return null;
        }
    }
    
    public ArrayList stockList(HttpServletRequest req,ArrayList vwPrpLst,String  nmeIdn){
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
        String  srchQ = " select stk_idn , pkt_ty,''  rap_dis  , rap_rte,'' quot , cert_no,cert_lab , vnm, pkt_dte, stt ,dsp_stt, qty ,to_char(trunc(cts,2) * quot, '9999999990.00') amt, to_char(trunc(cts,2),'9990.99') cts ,cmnt, rmk ,CERTIMG, DIAMONDIMG ";
          for (int i = 0; i < vwPrpLst.size(); i++) {
          String lprp=(String)vwPrpLst.get(i);
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
              String rsltQ = srchQ + " from gt_srch_rslt where cts <> 0 order by sk1 , cts ";

        ArrayList outLst = db.execSqlLst("search Result", rsltQ, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
              try {
                  while(rs.next()) {
                      String stkIdn = util.nvl(rs.getString("stk_idn"));
                      HashMap pktPrpMap = new HashMap();
                      pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
                      String vnm = util.nvl(rs.getString("vnm"));
                      String cert_no = util.nvl(rs.getString("cert_no"));
                      String cert_lab = util.nvl(rs.getString("cert_lab"));
                      String cts = util.nvl(rs.getString("cts"));
                     
                      pktPrpMap.put("stk_idn",stkIdn);
                     
                      for(int j=0; j < vwPrpLst.size(); j++){
                           String prp = (String)vwPrpLst.get(j);
                           String fld="";
                           String val="";
                            int indexof=vwPrpLst.indexOf(prp);
                            fld="prp_";
                            if(indexof < 9)
                                    fld="prp_00"+(indexof+1);
                            else    
                                    fld="prp_0"+(indexof+1);
                                   if (prp.toUpperCase().equals("RAP_DIS"))
                                            fld = "rap_dis";
                                   if (prp.toUpperCase().equals("RAP_RTE"))
                                             fld = "rap_rte";
                                   if(prp.toUpperCase().equals("RTE"))
                                               fld = "quot";                   
                                   if (prp.toUpperCase().equals("VNM"))
                                   fld = "vnm";
                                   if (prp.toUpperCase().equals("CRTWT"))
                                   fld = "cts";
                                   if (prp.toUpperCase().equals("COMMENT"))
                                   fld = "cmnt";
                                   
                                   val = util.nvl(rs.getString(fld)) ;
                                   if(prp.equals("PKTCODE"))
                                   val=util.nvl(rs.getString("vnm"));
                                  if(prp.equals("PKT_ID"))
                                  val=util.nvl(rs.getString("vnm"));
                                
                                   if(prp.equals("CRTWT"))
                                   val = util.nvl(rs.getString("cts"));
                            pktPrpMap.put(prp, val);
                          }
                          pktPrpMap.put("STOCK",util.nvl(rs.getString("dsp_stt")));
                          pktPrpMap.put("USER_CODE","0");
                          pktPrpMap.put("SINGLE_GROUP_CODE","1");
                          pktPrpMap.put("FEATHER_DISC","0");pktPrpMap.put("PROPORTION_DISC","0");pktPrpMap.put("NATTS_DISC","0");
                          pktPrpMap.put("GIRDLE_DISC","0");pktPrpMap.put("POLISH_QUALITY_DISC","0");pktPrpMap.put("OPEN_INCLUSION_DISC","0");
                          pktPrpMap.put("TABLE_INCLUSION_DISC","0");pktPrpMap.put("SPREADED_CODE","0");pktPrpMap.put("LUSTER_DISC","0");
                          pktPrpMap.put("SYMMETRY_DISC","0");pktPrpMap.put("CUT_DISC","0");pktPrpMap.put("MILKY_DISC","0");
                          pktPrpMap.put("FLUO_DISC","0");pktPrpMap.put("SAFE_CODE","0");
                          pktList.add(pktPrpMap);
                      }
                  rs.close(); pst.close();
              } catch (SQLException sqle) {

                  // TODO: Add catch code
                  sqle.printStackTrace();
              }
              return pktList;
    }
    
    public ActionForward createQtradeXL(ActionMapping am, ActionForm form,
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
            util.updAccessLog(req,res,"Daily Consignment Report", "pktDtlExcel start");
        GenericInterface genericInt = new GenericImpl();
            String nmeidn = util.nvl(req.getParameter("nmeidn"));
            String dte = util.nvl(req.getParameter("dte"));
            String memotbl = util.nvl(req.getParameter("memotbl"));
            DailyConsignmentReportForm dailyConsignmentReportForm = (DailyConsignmentReportForm)form;
        ArrayList ary = new ArrayList();
        ArrayList vnmLst = new ArrayList();
        ResultSet rs=null;
            nmeidn=nmeidn.replaceFirst(",", "");
                    dte=dte.replaceFirst(",", "");
                    String[] nmeidnLst=nmeidn.split(",");
                    String[] dteLst=dte.split(",");
            for(int i=0;i<nmeidnLst.length;i++){
            nmeidn=nmeidnLst[i];  
            dte=dteLst[i];  
        String dtefrom = " trunc(sysdate) ";
        String dteto = " trunc(sysdate) ";
        if(!nmeidn.equals("") && !dte.equals("")){
              dtefrom = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
              dteto = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
         String pktDtlSql = "select c.vnm\n" + 
         "From mjan A , jandtl B,Mstk C\n" + 
         "Where A.Idn = B.Idn And B.Mstk_Idn=C.Idn  and c.pkt_ty in('NR','SMX') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA'))\n" + 
         "And Trunc(B.Dte) Between "+dtefrom+" And "+dteto+"\n" + 
         "And a.Nme_Idn=? And B.Stt Not In ('APRT','RT','CL','ALC','MRG') And B.Typ In ('CS')" ;
        ary = new ArrayList();
        ary.add(nmeidn);
        System.out.println("DLV"+nmeidn);

            ArrayList outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            vnmLst.add(util.nvl(rs.getString("vnm")));
        }
            rs.close(); pst.close();
            
        }
        }
        req.setAttribute("pktList",vnmLst);
        req.setAttribute("fromDaily","Y");
        util.updAccessLog(req,res,"Daily Consignment Report", "pktDtlExcel end");
        return am.findForward("createQtradeXL");  
        }
    }
    
    public ActionForward createRuchiXL(ActionMapping am, ActionForm form,
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
            util.updAccessLog(req,res,"Daily Consignment Report", "pktDtlExcel start");
        GenericInterface genericInt = new GenericImpl();
            String nmeidn = util.nvl(req.getParameter("nmeidn"));
            String dte = util.nvl(req.getParameter("dte"));
            String memotbl = util.nvl(req.getParameter("memotbl"));
        DailyConsignmentReportForm dailyConsignmentReportForm = (DailyConsignmentReportForm)form;
        ArrayList ary = new ArrayList();
        ArrayList vnmLst = new ArrayList();
        HashMap priMap = new HashMap();
        ResultSet rs=null;
            nmeidn=nmeidn.replaceFirst(",", "");
                    dte=dte.replaceFirst(",", "");
                    String[] nmeidnLst=nmeidn.split(",");
                    String[] dteLst=dte.split(",");
            for(int i=0;i<nmeidnLst.length;i++){
            nmeidn=nmeidnLst[i];  
            dte=dteLst[i];  
        String dtefrom = " trunc(sysdate) ";
        String dteto = " trunc(sysdate) ";
        if(!nmeidn.equals("") && !dte.equals("")){
              dtefrom = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
              dteto = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
         String pktDtlSql = "select c.vnm,c.idn,TRUNC (NVL (b.fnl_sal, b.quot) / NVL (a.exh_rte, 1), 2) fnl_usd\n" + 
         "From mjan A , jandtl B,Mstk C\n" + 
         "Where A.Idn = B.Idn And B.Mstk_Idn=C.Idn and c.pkt_ty in('NR','SMX') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA'))\n" + 
         "And Trunc(B.Dte) Between "+dtefrom+" And "+dteto+"\n" + 
         "And a.Nme_Idn=? And B.Stt Not In ('RT','CS','DP','PS','CL') And B.Typ In ('CS')" ;
        ary = new ArrayList();
        ary.add(nmeidn);
        System.out.println("DLV"+nmeidn);;
            ArrayList outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            vnmLst.add(util.nvl(rs.getString("vnm")));
            priMap.put(util.nvl(rs.getString("idn")), util.nvl(rs.getString("fnl_usd")));
        }
            rs.close(); pst.close();
            
        }
        }
        req.setAttribute("pktList",vnmLst);
        session.setAttribute("PriMap", priMap);
        req.setAttribute("fromDaily","Y");
        util.updAccessLog(req,res,"Daily Consignment Report", "pktDtlExcel end");
        return am.findForward("createRuchiXL");  
        }
    }
  public ActionForward pktDtlExcelXL (ActionMapping am, ActionForm form,
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
          util.updAccessLog(req,res,"Daily Approve Report", "pktDtlExcelXL start");
          GenericInterface genericInt = new GenericImpl();
                ArrayList vwprpLst = genericInt.genericPrprVw(req,res,"DAILY_CON_VW","DAILY_CON_VW");
                HashMap dbinfo = info.getDmbsInfoLst();
                 String cnt = util.nvl((String)dbinfo.get("CNT"));
      DailyConsignmentReportForm dailyConsignmentReportForm = (DailyConsignmentReportForm)form;
      String nmeidn = util.nvl(req.getParameter("nmeidn"));
      String dte = util.nvl(req.getParameter("dte"));
      String memotbl = util.nvl(req.getParameter("memotbl"));
      ArrayList pktDtlList = new ArrayList();
      ArrayList itemHdr = new ArrayList();
      ArrayList ary = new ArrayList();
    
     
        itemHdr.add("IDN");
        itemHdr.add("BYR");
        itemHdr.add("TYP");
        itemHdr.add("TRM");
        itemHdr.add("VNM");
        itemHdr.add("QTY");
        itemHdr.add("CTS");
        itemHdr.add("DATE");
        itemHdr.add("ODT");
          for(int i=0;i<vwprpLst.size();i++){
              String prp=util.nvl((String)vwprpLst.get(i));
              itemHdr.add(prp);
          }            
       
        itemHdr.add("QUOT");
        itemHdr.add("TOTALVALUE");
       
        itemHdr.add("STT");
        itemHdr.add("NET_DIS");
        itemHdr.add("NET_AMT");
        itemHdr.add("GRANDTOTAL");
         
      nmeidn=nmeidn.replaceFirst(",", "");
      dte=dte.replaceFirst(",", "");
      String[] nmeidnLst=nmeidn.split(",");
      String[] dteLst=dte.split(",");
      System.out.println(nmeidn);
      for(int i=0;i<nmeidnLst.length;i++){
      nmeidn=nmeidnLst[i];  
      dte=dteLst[i];  
     String dtefrom = " trunc(sysdate) ";
     String dteto = " trunc(sysdate) ";
      String Qry= "";   
      if(!nmeidn.equals("") && !dte.equals("")){
            dtefrom = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
            dteto = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
       
          ary = new ArrayList();
       String pktDtlSql =
        "select a.idn memoId,c.idn , to_char(b.dte, 'dd-Mon-rrrr HH24:mi:ss') dte ,c.odt , byr.get_nm(nvl(d.nme_idn,0)) byr ,a.typ ,e.term ,c.vnm,b.qty,to_char(b.cts,'99999999990.99') cts,"+
        "to_char((((nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte,1))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') r_dis,nvl(b.fnl_sal, b.quot) memoQuot,"+
        "to_char(b.cts*(nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte,1)),'999,9999,999,990.00') amt ,c.stt, c.rap_rte ,"+
        "get_net (b.idn, b.mstk_idn,'M') STONEWISE_DIS,ROUND ( (NVL (b.FNL_SAL, b.quot) * b.cts) * get_net (b.idn,b.mstk_idn,'M') / 100, 2 ) NET_DISC_AMT,to_char(trunc(c.cts*nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte,1), 2),'9999999999990.00')-abs(ROUND ( (NVL (b.FNL_SAL, b.quot) * b.cts) * get_net (b.idn, b.mstk_idn,'M') / 100, 2 )) grandttl "+
        "from mjan a,jandtl b, mstk c,mnme d , mtrms e where "+
        "a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn  and c.pkt_ty in('NR','SMX') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) "+
        " and b.stt not in ('APRT','RT','CL','ALC','MRG') and b.typ in ('CS') and a.trms_idn=e.idn And d.Nme_Idn= ? \n";
          
          ary.add(nmeidn);
          if(!memotbl.equals("")){
          String[] memotblList = memotbl.split("-");
              pktDtlSql+=" and b.idn between ? and ? ";
              ary.add(memotblList[0].trim());
              ary.add(memotblList[1].trim());
          }
      pktDtlSql+=" and trunc(b.dte)  between "+dtefrom+" and "+dteto+"  order by a.idn,c.sk1 ";

          ArrayList outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
      while(rs.next()){
          HashMap pktDtl = new HashMap();
          pktDtl.put("IDN", util.nvl(rs.getString("memoId")));
          pktDtl.put("VNM", util.nvl(rs.getString("vnm")));
          pktDtl.put("BYR", util.nvl(rs.getString("byr")));
          pktDtl.put("TYP", util.nvl(rs.getString("typ")));
          pktDtl.put("QTY", util.nvl(rs.getString("qty")));
          pktDtl.put("CTS", util.nvl(rs.getString("cts")));
          pktDtl.put("RAP_RTE", util.nvl(rs.getString("rap_rte")));
          pktDtl.put("STT", util.nvl(rs.getString("stt")));
          pktDtl.put("DATE", util.nvl(rs.getString("dte")));
          pktDtl.put("TRM", util.nvl(rs.getString("term")));
        
         String pktIdn = util.nvl(rs.getString("idn"));
          String getPktPrp =
              " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
              + " from stk_dtl a, mprp b, rep_prp c "
              + " where a.mprp = b.prp and b.prp = c.prp and c.mdl = 'DAILY_CON_VW' and a.mstk_idn = ? and a.grp=1 and  c.flg <> 'N' "
              + " order by c.rnk, c.srt ";

          ary = new ArrayList();
          ary.add(pktIdn);
          ArrayList outLst1 = db.execSqlLst(" Pkt Prp", getPktPrp, ary);
          PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
          ResultSet rs1 = (ResultSet)outLst1.get(1);
           while (rs1.next()) {
              String lPrp = util.nvl(rs1.getString("mprp"));
              String lVal = util.nvl(rs1.getString("val"));
              pktDtl.put(lPrp, lVal);         
          }
          rs1.close(); pst.close();
        pktDtl.put("ODT", util.nvl(rs.getString("odt")));
          pktDtl.put("QUOT",util.nvl(rs.getString("memoQuot")));
          pktDtl.put("TOTALVALUE",util.nvl(rs.getString("amt")));
          pktDtl.put("RAP_DIS",util.nvl(rs.getString("r_dis")));
          pktDtl.put("CRTWT",util.nvl(rs.getString("cts")));
          pktDtl.put("RAP_RTE",util.nvl(rs.getString("rap_rte")));
          pktDtl.put("NET_DIS",util.nvl(rs.getString("STONEWISE_DIS")));
          pktDtl.put("NET_AMT",util.nvl(rs.getString("NET_DISC_AMT")));
          pktDtl.put("GRANDTOTAL",util.nvl(rs.getString("grandttl")));
          pktDtlList.add(pktDtl);
          rs1.close(); pst.close();
      }
          rs.close(); pst.close();
          
      }
      }
      session.setAttribute("pktList", pktDtlList);
      session.setAttribute("itemHdr",itemHdr);
          util.updAccessLog(req,res,"Daily Approve Report", "pktDtlExcelXL end");
      return am.findForward("pktDtl"); 
      }
  }
    
  public ActionForward pktDtlExcelXLFNL(ActionMapping am, ActionForm form,
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
          util.updAccessLog(req,res,"Daily Approve Report", "pktDtlExcelXLFNL start");
          GenericInterface genericInt = new GenericImpl();
      ArrayList vwprpLst = genericInt.genericPrprVw(req,res,"DAILY_VW_XLHK","DAILY_VW_XLHK");
      String nmeidn = util.nvl(req.getParameter("nmeidn"));
      String dte = util.nvl(req.getParameter("dte"));
      ArrayList pktDtlList = new ArrayList();
      ArrayList itemHdr = new ArrayList();
      ArrayList ary = new ArrayList();
      ArrayList prpDspBlocked = info.getPageblockList();
       itemHdr.add("BYR");
       itemHdr.add("TFL3");
          for(int i=0;i<vwprpLst.size();i++){
              String prp=util.nvl((String)vwprpLst.get(i));
              if(prpDspBlocked.contains(prp)){
              }else if(!itemHdr.contains(prp)){
              itemHdr.add(prp);
              if(prp.equals("BD_CL")){
                itemHdr.add("MEMOID");
              }
              }  
          }
          itemHdr.add("RAPVLU"); 
          itemHdr.add("RAP_DIS"); 
          itemHdr.add("RTE");
        itemHdr.add("VALUE"); 
        itemHdr.add("NET_RAP_BACK");
        itemHdr.add("NET_STONE_AMTPER_CTS");
        itemHdr.add("NET_STONE_AMT");
        itemHdr.add("DATE");
        itemHdr.add("SK1");
      nmeidn=nmeidn.replaceFirst(",", "");
      dte=dte.replaceFirst(",", "");
      String[] nmeidnLst=nmeidn.split(",");
      String[] dteLst=dte.split(",");
      for(int i=0;i<nmeidnLst.length;i++){
      nmeidn=nmeidnLst[i];  
      dte=dteLst[i];  
      String dtefrom = " trunc(sysdate) ";
      String dteto = " trunc(sysdate) ";
      if(!nmeidn.equals("") && !dte.equals("")){
            dtefrom = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
            dteto = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
        ary = new ArrayList();
        String pktDtlSql =
        "select a.idn memoId,c.idn ,c.tfl3,c.sk1,c.rap_rte,nvl(c.upr,c.cmp) rte, to_char(b.dte, 'dd-Mon-rrrr HH24:mi:ss') dte ,c.odt , byr.get_nm(nvl(d.nme_idn,0)) byr ,a.typ ,e.term ,\n" + 
        "c.vnm,b.qty,to_char(b.cts,'99999999990.99') cts,to_char((((nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte,1))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') r_dis,\n" + 
        "nvl(b.fnl_sal, b.quot) memoQuot,to_char(b.cts*(nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte,1)),'999,9999,999,990.00') amt ,to_char(trunc(b.cts,2) * c.rap_rte, '99999999990.00') rapvlu,\n" + 
        "get_net (b.idn, b.mstk_idn,'M') STONEWISE_DIS,\n" + 
        "ROUND ( (NVL (b.fnl_sal, b.quot) * b.cts) * get_net (b.idn, b.mstk_idn,'M') / 100, 2 ) NET_DISC_AMT,\n" + 
        " ROUND ( (NVL (B.FNL_SAL, b.quot) * b.cts) - (NVL (B.FNL_SAL, b.quot) * b.cts) * get_net (b.idn, b.mstk_idn,'M') / 100, 2 ) NET_STONE_AMT,\n" + 
        "ROUND( (NVL (B.FNL_SAL, b.quot)  * b.cts) - (NVL (B.FNL_SAL, b.quot) * b.cts) * get_net (b.idn, b.mstk_idn,'M') / 100 / b.cts) NET_STONE_AMTPER_CTS,\n" + 
        "ROUND ( 100- (ROUND( (NVL (B.FNL_SAL, b.quot) * b.cts) - (NVL (B.FNL_SAL, b.quot) * b.cts) * get_net (b.idn, b.mstk_idn,'M') / 100 / b.cts)) * 100 / c.rap_rte, 2 ) NET_RAP_BACK"+
        " from mjan a,jandtl b, mstk c,mnme d , mtrms e where "+
        "a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn  and c.pkt_ty in('NR','SMX') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) "+
        " and b.stt not in ('APRT','RT','CL','ALC','MRG') and b.typ in ('CS') and a.trms_idn=e.idn And d.Nme_Idn= ? \n";
        pktDtlSql+=" and trunc(b.dte)  between "+dtefrom+" and "+dteto+"  order by a.idn,c.sk1 ";
      ary = new ArrayList();
      ary.add(nmeidn);
 
          ArrayList outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
      while(rs.next()){
        HashMap pktDtl = new HashMap();
        pktDtl.put("MEMOID", util.nvl(rs.getString("memoId")));
        pktDtl.put("BYR", util.nvl(rs.getString("byr")));
        pktDtl.put("TFL3", util.nvl(rs.getString("tfl3")));
        pktDtl.put("RTE", util.nvl(rs.getString("rte")));
        pktDtl.put("RAP_RTE", util.nvl(rs.getString("rap_rte")));
        pktDtl.put("VNM", util.nvl(rs.getString("vnm")));
        pktDtl.put("QTY", util.nvl(rs.getString("qty")));
        pktDtl.put("CTS", util.nvl(rs.getString("cts")));
        pktDtl.put("VALUE",util.nvl(rs.getString("amt")));
        pktDtl.put("STONEWISE_DIS", util.nvl(rs.getString("STONEWISE_DIS")));
        pktDtl.put("NET_DISC_AMT", util.nvl(rs.getString("NET_DISC_AMT")));
        pktDtl.put("NET_STONE_AMT", util.nvl(rs.getString("NET_STONE_AMT")));
        pktDtl.put("NET_STONE_AMTPER_CTS", util.nvl(rs.getString("NET_STONE_AMTPER_CTS")));
        pktDtl.put("NET_RAP_BACK",util.nvl(rs.getString("NET_RAP_BACK")));
        pktDtl.put("RAP_DIS",util.nvl(rs.getString("r_dis")));
          pktDtl.put("DATE", util.nvl(rs.getString("dte")));
        pktDtl.put("RAPVLU",util.nvl(rs.getString("rapvlu")));
          pktDtl.put("SK1",util.nvl(rs.getString("sk1")));
          String pktIdn = util.nvl(rs.getString("idn"));
          String getPktPrp =
              " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
              + " from stk_dtl a, mprp b, rep_prp c "
              + " where a.mprp = b.prp and b.prp = c.prp and c.mdl = 'DAILY_VW_XLHK' and a.mstk_idn = ? and a.grp=1 and c.flg <> 'N' "
              + " order by c.rnk, c.srt ";

          ary = new ArrayList();
          ary.add(pktIdn);

          ArrayList outLst1 = db.execSqlLst(" Pkt Prp", getPktPrp, ary);
          PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
          ResultSet rs1 = (ResultSet)outLst1.get(1);
           while (rs1.next()) {
              String lPrp = util.nvl(rs1.getString("mprp"));
              String lVal = util.nvl(rs1.getString("val"));
              pktDtl.put(lPrp, lVal);
          }
        
          pktDtlList.add(pktDtl);
          rs1.close(); pst1.close();
      }
          rs.close(); pst.close();
          
      }
      }
      session.setAttribute("pktList", pktDtlList);
      session.setAttribute("itemHdr",itemHdr);
          util.updAccessLog(req,res,"Daily Approve Report", "pktDtlExcelXLFNL end");
      return am.findForward("pktDtl");  
      }
  }


    public ActionForward pktDtlExcelXLPNDSUMMY(ActionMapping am, ActionForm form,
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
            util.updAccessLog(req,res,"Daily Approve Report", "pktDtlExcelXLPNDSUMMY start");
        String nmeidn = util.nvl(req.getParameter("nmeidn"));
        String dte = util.nvl(req.getParameter("dte"));
        ArrayList pktDtlList = new ArrayList();
        ArrayList itemHdr = new ArrayList();
        ArrayList ary = new ArrayList();
        ArrayList prpDspBlocked = info.getPageblockList();
         itemHdr.add("MEMOID");
         itemHdr.add("BYR");
         itemHdr.add("QTY"); 
         itemHdr.add("CTS"); 
         itemHdr.add("DTE");
          itemHdr.add("VLU"); 
          itemHdr.add("TOTAL_VLU");
          itemHdr.add("AVG_VLU");
          itemHdr.add("NET_DIS");
          itemHdr.add("NET_AMT");
          itemHdr.add("GRAND_TOTAL");
        nmeidn=nmeidn.replaceFirst(",", "");
        dte=dte.replaceFirst(",", "");
        String[] nmeidnLst=nmeidn.split(",");
        String[] dteLst=dte.split(",");
        for(int i=0;i<nmeidnLst.length;i++){
        nmeidn=nmeidnLst[i];  
        dte=dteLst[i];  
        String dtefrom = " trunc(sysdate) ";
        String dteto = " trunc(sysdate) ";
        if(!nmeidn.equals("") && !dte.equals("")){
              dtefrom = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
              dteto = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
          ary = new ArrayList();
          String pktDtlSql =
          "select a.idn memoId,byr.get_nm(nvl(d.nme_idn,0)) byr,to_char(b.dte, 'dd-Mon-rrrr') dte,Sum(B.Qty) Qty,To_Char(Sum(Trunc(B.Cts,2)),'999990.99') Cts,\n" + 
          "SUM(NVL (B.FNL_SAL, b.quot)) vlu,SUM(TRUNC(b.Cts*NVL (B.FNL_SAL, b.quot),2)) ttl_vlu,\n" + 
          "TRUNC(SUM(TRUNC(b.Cts)*NVL (B.FNL_SAL, b.quot))/SUM(TRUNC(b.Cts,2)),2) avg_vlu,\n" + 
          "Trunc(100-((get_netamt(a.idn)/To_Char(Sum(Trunc(B.Cts,2)*(Nvl(B.FNL_SAL, B.Quot))),'9999999999990.00'))*100),2) Net_Dis,\n" + 
          "get_netamt(a.idn) net_amt,(SUM(TRUNC(b.Cts*NVL (B.FNL_SAL, b.quot),2))-get_netamt(a.idn)) grandttl\n" + 
          " from mjan a,jandtl b, mstk c,mnme d \n" + 
          "where a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn  and b.stt not in ('APRT','RT','CL','ALC','MRG')  and c.pkt_ty in('NR','SMX') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) \n" + 
          "and b.typ in ('CS') And d.Nme_Idn= ? \n";
          pktDtlSql+=" and trunc(b.dte)  between "+dtefrom+" and "+dteto+" group by a.idn,d.fnme,to_char(b.dte, 'dd-Mon-rrrr') order by a.idn ";
        ary = new ArrayList();
        ary.add(nmeidn);

            ArrayList outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
          HashMap pktDtl = new HashMap();
          pktDtl.put("MEMOID", util.nvl(rs.getString("memoId")));
          pktDtl.put("BYR", util.nvl(rs.getString("byr")));
          pktDtl.put("QTY", util.nvl(rs.getString("Qty")));
          pktDtl.put("CTS", util.nvl(rs.getString("Cts")));
          pktDtl.put("DTE", util.nvl(rs.getString("dte")));
          pktDtl.put("VLU", util.nvl(rs.getString("vlu")));
          pktDtl.put("TOTAL_VLU", util.nvl(rs.getString("ttl_vlu")));
          pktDtl.put("AVG_VLU", util.nvl(rs.getString("avg_vlu")));
          pktDtl.put("NET_DIS",util.nvl(rs.getString("Net_Dis")));
          pktDtl.put("NET_AMT", util.nvl(rs.getString("net_amt")));
          pktDtl.put("GRAND_TOTAL", util.nvl(rs.getString("grandttl")));
            pktDtlList.add(pktDtl);
        }
            rs.close(); pst.close();
            
        }
        }
        session.setAttribute("pktList", pktDtlList);
        session.setAttribute("itemHdr",itemHdr);
            util.updAccessLog(req,res,"Daily Approve Report", "pktDtlExcelXLPNDSUMMY end");
        return am.findForward("pktDtl");  
        }
    }
  public ActionForward pktDtlExcelXLHK(ActionMapping am, ActionForm form,
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
          util.updAccessLog(req,res,"Daily Approve Report", "pktDtlExcelXLHK start");
          GenericInterface genericInt = new GenericImpl();
     
      ArrayList vwprpLst = genericInt.genericPrprVw(req,res,"DAILY_VW_XLHK","DAILY_VW_XLHK");
      String nmeidn = util.nvl(req.getParameter("nmeidn"));
      String dte = util.nvl(req.getParameter("dte"));
      ArrayList pktDtlList = new ArrayList();
      ArrayList itemHdr = new ArrayList();
      ArrayList ary = new ArrayList();
      ArrayList prpDspBlocked = info.getPageblockList();
  //          itemHdr.add("QTY");
      
          for(int i=0;i<vwprpLst.size();i++){
              String prp=util.nvl((String)vwprpLst.get(i));
              if(prpDspBlocked.contains(prp)){
              }else if(!itemHdr.contains(prp)){
              itemHdr.add(prp);
              if(prp.equals("PU")){
             
              }
                  if(prp.equals("RAP_RTE")){
                         itemHdr.add("RAPVLU");
                  }
              }  
          }
        itemHdr.add("RTE");
        itemHdr.add("AMOUNT");
       
       itemHdr.add("MEMOID");
        itemHdr.add("BYR");
         itemHdr.add("MB");
       itemHdr.add("MB COMM");
        itemHdr.add("SB");
        itemHdr.add("SB COMM");
        
      itemHdr.add("CERT_NO");
          
      nmeidn=nmeidn.replaceFirst(",", "");
      dte=dte.replaceFirst(",", "");
      String[] nmeidnLst=nmeidn.split(",");
      String[] dteLst=dte.split(",");
      for(int i=0;i<nmeidnLst.length;i++){
      nmeidn=nmeidnLst[i];  
      dte=dteLst[i];  
      String dtefrom = " trunc(sysdate) ";
      String dteto = " trunc(sysdate) ";
      if(!nmeidn.equals("") && !dte.equals("")){
            dtefrom = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
            dteto = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
       String pktDtlSql ="select a.idn memoId,c.idn ,c.cert_no, to_char(b.dte, 'dd-Mon-rrrr HH24:mi:ss') dte ,c.odt , byr.get_nm(nvl(d.nme_idn,0)) byr ,a.typ ,e.term ,c.vnm,b.qty,to_char(b.cts,'99999999990.99') cts,"+
        "to_char((((nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte,1))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') r_dis,nvl(b.fnl_sal, b.quot) memoQuot,"+
        "to_char(b.cts*(nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte,1)),'999,9999,999,990.00') amt ,c.stt, c.rap_rte , "+
        " Byr.Get_Nm(A.Mbrk1_Idn) Mb, Nvl(A.Brk1_Comm,0) Mbcomm, Byr.Get_Nm(A.Mbrk2_Idn) Sub, Nvl(A.Brk2_Comm,0) Subcomm "+         
        "from mjan a,jandtl b, mstk c,mnme d , mtrms e where "+
        "a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn  and c.pkt_ty in('NR','SMX') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) "+
        " and b.stt not in ('APRT','RT','CL','ALC','MRG') and b.typ in ('CS') and a.trms_idn=e.idn And d.Nme_Idn= ? \n"+
         "   And Trunc(a.Dte) Between "+dtefrom+" And "+dteto+"  order by c.sk1 ";
      ary = new ArrayList();
      ary.add(nmeidn);

          ArrayList outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
      while(rs.next()){
          HashMap pktDtl = new HashMap();
        pktDtl.put("MEMOID", util.nvl(rs.getString("memoid")));
        pktDtl.put("BYR", util.nvl(rs.getString("byr")));
       
        pktDtl.put("RTE", util.nvl(rs.getString("memoQuot")));
        pktDtl.put("AMOUNT", util.nvl(rs.getString("amt")));
        pktDtl.put("RAP_RTE", util.nvl(rs.getString("rap_rte")));
        pktDtl.put("MB", util.nvl(rs.getString("mb")));
        pktDtl.put("MB COMM", util.nvl(rs.getString("mbcomm")));
        pktDtl.put("SB", util.nvl(rs.getString("sub")));
        pktDtl.put("SB COMM", util.nvl(rs.getString("subcomm")));
        pktDtl.put("VNM", util.nvl(rs.getString("vnm")));
        pktDtl.put("QTY", util.nvl(rs.getString("qty")));
        pktDtl.put("CTS", util.nvl(rs.getString("cts")));
        pktDtl.put("CERT_NO", util.nvl(rs.getString("cert_no")));
        
       
          String pktIdn = util.nvl(rs.getString("idn"));
          String getPktPrp =
              " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
              + " from stk_dtl a, mprp b, rep_prp c "
              + " where a.mprp = b.prp and b.prp = c.prp and c.mdl = 'DAILY_VW_XLHK' and a.mstk_idn = ? and a.grp=1 and c.flg <> 'N' "
              + " order by c.rnk, c.srt ";

          ary = new ArrayList();
          ary.add(pktIdn);

          ArrayList outLst1 = db.execSqlLst(" Pkt Prp", getPktPrp, ary);
          PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
          ResultSet rs1 = (ResultSet)outLst1.get(1);
           while (rs1.next()) {
              String lPrp = util.nvl(rs1.getString("mprp"));
              String lVal = util.nvl(rs1.getString("val"));
              pktDtl.put(lPrp, lVal);
          }
          
          pktDtlList.add(pktDtl);
          rs1.close(); pst1.close();
      }
          rs.close(); pst.close();
          
      }
      }
      session.setAttribute("pktList", pktDtlList);
      session.setAttribute("itemHdr",itemHdr);
      util.updAccessLog(req,res,"Daily Approve Report", "pktDtlExcelXLHK end");
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
                rtnPg=util.checkUserPageRights("report/dailyapproveReport.do?","");
                if(rtnPg.equals("unauthorized"))
                util.updAccessLog(req,res,"Daily Approve Report", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Daily Approve Report", "init");
            }
            }
            return rtnPg;
            }

    public DailyConsignmentReportAction() {
        super();
    }
}
