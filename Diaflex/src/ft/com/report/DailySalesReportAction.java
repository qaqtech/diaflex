package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.ExcelUtilObj;
import ft.com.InfoMgr;

import ft.com.generic.GenericImpl;

import ft.com.generic.GenericInterface;

import java.io.IOException;

import java.io.OutputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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


public class DailySalesReportAction extends DispatchAction {

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
            util.updAccessLog(req,res,"Daily Sale Report", "load start");
        DailySalesReportForm dailySalesReportForm = (DailySalesReportForm)form;
      ResultSet rs = null;
      ArrayList ary = new ArrayList();
      String dtefrom = " trunc(sysdate) ";
      String dteto = " trunc(sysdate) ";
        String flg=util.nvl(req.getParameter("flg"));
        if(flg.equals("New")){
            dailySalesReportForm.reset();
        }
      String pktTy = util.nvl(req.getParameter("PKT_TY"));
     if(pktTy.equals(""))
         pktTy = util.nvl((String)dailySalesReportForm.getValue("PKT_TY"));
      String dfr = util.nvl((String)dailySalesReportForm.getValue("dtefr"));
      String dto = util.nvl((String)dailySalesReportForm.getValue("dteto"));
      String spersonId = util.nvl((String)dailySalesReportForm.getValue("styp"));
      String buyerId = util.nvl(req.getParameter("nmeID"));
      String group = util.nvl((String)dailySalesReportForm.getValue("group"));
      String usrLoc = util.nvl((String)dailySalesReportForm.getValue("usrLoc"));
      String sttTyp = util.nvl((String)dailySalesReportForm.getValue("sttTyp"));
     String isLS = util.nvl(req.getParameter("isLS"));
      if(isLS.equals(""))
          isLS = util.nvl((String)dailySalesReportForm.getValue("isLS"));
            
    dailySalesReportForm.setValue("isLS", isLS);
      String sql="";
      String bql="";
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
      ArrayList rolenmLst=(ArrayList)info.getRoleLst();
      String usrFlg=util.nvl((String)info.getUsrFlg());
      String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
      String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
      String dfNmeIdn=util.nvl((String)info.getDfNmeIdn()); 
      if(!dfr.equals(""))
        dtefrom = "to_date('"+dfr+"' , 'dd-mm-yyyy')";
      
      if(!dto.equals(""))
        dteto = "to_date('"+dto+"' , 'dd-mm-yyyy')";
      
      if(!spersonId.equals("") && spersonId.length()>1){
        sql = "and d.emp_idn= ? ";
        ary.add(spersonId);
      }
            
//      if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
//      sql+= " and d.emp_idn= ? ";  
//      ary.add(dfNmeIdn);
//      }
           if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
                        sql += " and (d.emp_idn= ? or d.emp_idn in(select emp_idn from nme_v where typ='EMPLOYEE' and mgr_idn= ?)) ";  
                        ary.add(dfNmeIdn);
                        ary.add(dfNmeIdn);
            }
      
      if(!buyerId.equals("")){
        bql = "and d.nme_idn= ? ";
        ary.add(buyerId);
      }
        if(sttTyp.equals("")) {
            sql+= " and b.stt not in ('RT','CS','CL') ";    
        }else{
            if(sttTyp.equals("IS")){
                sql+= " and b.stt in ('IS') ";    
            }else if(sttTyp.equals("PR")){
                sql+= " and b.stt in ('PR','DLV') ";  
            }
        }
           
      if(!pktTy.equals("")){
                sql+= " and c.pkt_ty in ('"+pktTy+"') "; 
            dailySalesReportForm.setValue("PKT_TY", pktTy);
        }
        if(!group.equals("")){
          bql =bql+ "and d.grp_nme_idn= ? ";
          ary.add(group);
        }else{
        if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
        }else if(!dfgrpnmeidn.equals("") && !dfgrpnmeidn.equals("0")){
        bql =bql+" and d.grp_nme_idn=?"; 
        ary.add(dfgrpnmeidn);
        }  
        }
           
            if(!isLS.equals("")){
            sql=sql+" and a.typ in ('SL','LS') ";
            }else{
             sql=sql+" and a.typ in ('SL') ";
            }
      
        HashMap sale = new HashMap();
        String loadqry =
        " select d.emp_idn sale_id, byr.get_nm(nvl(d.emp_idn,0)) sale_name,byr.get_nm(nvl(d.nme_idn,0)) byr, d.nme_idn byrid,b.typ typ "+
         " , to_char(b.dte,'dd-mm-yyyy') dte,to_number(to_char(b.dte, 'rrrrmmdd')) srt_dte , " +
         " sum(nvl(b.qty,1)) qty , sum(decode(c.pkt_ty, 'NR', trunc(b.cts, 2), b.cts)) cts "+
         " , to_char(sum(decode(c.pkt_ty, 'NR', trunc(b.cts, 2), b.cts)*nvl(b.fnl_usd, b.quot))  ,'999,9999,999,990.00') vlu, to_char(sum(decode(c.pkt_ty, 'NR', trunc(b.cts, 2), b.cts)*(nvl(b.fnl_usd, b.quot)*nvl(decode(a.fnl_exh_rte,1,null,null,null,a.fnl_exh_rte),0))),'999999999990.00') fnlexhvlu  "+
        " from msal a,Sal_Pkt_Dtl_V b, mstk c,mnme d where "+
        " a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn and c.idn=b.mstk_idn and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA'))"+
       " and b.typ in ('SL','DLV') and c.stt in('MKSL','SRV_SL_IS', 'MKSD','BRC_MKSD') "+sql+""+bql+
        " and trunc(b.dte)  between "+dtefrom+" and "+dteto+ " group by d.emp_idn, d.nme_idn, d.fnme, to_char(b.dte,'dd-mm-yyyy'),to_number(to_char(b.dte, 'rrrrmmdd')),b.typ,c.pkt_ty "+
       " order by 2,3,7 ";
     if(!usrLoc.equals("")){
              loadqry="select d.emp_idn sale_id, byr.get_nm(nvl(d.emp_idn,0)) sale_name,byr.get_nm(nvl(d.nme_idn,0)) byr, d.nme_idn byrid,b.typ typ \n" + 
              " , to_char(b.dte,'dd-mm-yyyy') dte ,to_number(to_char(b.dte, 'rrrrmmdd')) srt_dte," +
                " sum(nvl(b.qty,1)) qty , sum(decode(c.pkt_ty, 'NR', trunc(b.cts, 2), b.cts)) cts "+
                " , to_char(sum(decode(c.pkt_ty, 'NR', trunc(b.cts, 2), b.cts)*nvl(b.fnl_usd, b.quot))  ,'999,9999,999,990.00') vlu, to_char(sum(decode(c.pkt_ty, 'NR', trunc(b.cts, 2), b.cts)*(nvl(b.fnl_usd, b.quot)*nvl(decode(a.fnl_exh_rte,1,null,null,null,a.fnl_exh_rte),0))),'999999999990.00') fnlexhvlu  "+
                 " from msal a,Sal_Pkt_Dtl_V b, mstk c,mnme d , df_users e , nme_dtl f where \n" + 
              " a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn and c.idn=b.mstk_idn and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA'))\n" + 
              "  and b.typ in ('SL','DLV') and c.stt in('MKSL','SRV_SL_IS', 'MKSD','BRC_MKSD')  "+sql+""+bql+
              " and trunc(b.dte)   between "+dtefrom+" and "+dteto+" \n" + 
              " and e.usr = a.aud_created_by and e.nme_idn =f.nme_idn and f.mprp='OFFLOC'  and f.txt = ?\n" + 
              " group by d.emp_idn, d.nme_idn, d.fnme, to_char(b.dte,'dd-mm-yyyy'),to_number(to_char(b.dte, 'rrrrmmdd')),b.typ ,c.pkt_ty\n" + 
              " order by 2,3,7 ";
              ary = new ArrayList();
              ary.add(usrLoc);
       }
      

            ArrayList outLst = db.execSqlLst("loadqry", loadqry, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        int i = 0;
        while (rs.next()) {
            HashMap saledtl = new HashMap();
            i++;
            saledtl.put("sale_id", util.nvl(rs.getString("sale_id")));
            saledtl.put("saleName", util.nvl(rs.getString("sale_name")));
            saledtl.put("byr", util.nvl(rs.getString("byr")));
            saledtl.put("dte", util.nvl(rs.getString("dte")));
            saledtl.put("qty", util.nvl(rs.getString("qty")));
            saledtl.put("cts", util.nvl(rs.getString("cts")));
            saledtl.put("typ", util.nvl(rs.getString("typ")));
            saledtl.put("byrid", util.nvl(rs.getString("byrid")));
            saledtl.put("vlu", util.nvl(rs.getString("vlu")));
            String fnlexhvlu=util.nvl(rs.getString("fnlexhvlu"),"0").trim();
            if(Double.parseDouble(fnlexhvlu) <= 0)
            fnlexhvlu="-";
            saledtl.put("fnlexhvlu", fnlexhvlu);
            sale.put(i, saledtl);
        }
        rs.close(); pst.close();
        req.setAttribute("saleTbl", sale);
//  Added by Reshmi on 15-11-2011
        HashMap totalSale = new HashMap();
        String totalQry = " select d.emp_idn sale_id "+
        " , byr.get_nm(nvl(d.emp_idn,0)) sale_name , "+
            " sum(nvl(b.qty,1)) qty , sum(decode(c.pkt_ty, 'NR', trunc(b.cts, 2), b.cts)) cts "+
            " , to_char(sum(decode(c.pkt_ty, 'NR', trunc(b.cts, 2), b.cts)*nvl(b.fnl_usd, b.quot))  ,'999,9999,999,990.00') vlu,to_char(sum(decode(c.pkt_ty, 'NR', trunc(b.cts, 2), b.cts)*(nvl(b.fnl_usd, b.quot)*nvl(decode(a.fnl_exh_rte,1,null,null,null,a.fnl_exh_rte),0))),'999999999990.00') fnlexhvlu "+
              "  from msal a,Sal_Pkt_Dtl_V b, mstk c,mnme d "+
       " where a.idn=b.idn and b.mstk_idn=c.idn "+
        "   and b.typ in ('SL','DLV') and c.stt in ('MKSL','SRV_SL_IS', 'MKSD','BRC_MKSD') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) "+sql+""+bql+
        " and d.nme_idn = a.nme_idn "+
      " and trunc(b.dte)  between "+dtefrom+" and "+dteto+
       " group by d.emp_idn order by 2 ";
            
          if(!usrLoc.equals("")){
            totalQry=" select d.emp_idn sale_id "+
        " , byr.get_nm(nvl(d.emp_idn,0)) sale_name , "+
         " sum(nvl(b.qty,1)) qty , sum(decode(c.pkt_ty, 'NR', trunc(b.cts, 2), b.cts)) cts "+
        " , to_char(sum(decode(c.pkt_ty, 'NR', trunc(b.cts, 2), b.cts)*nvl(b.fnl_usd, b.quot))  ,'999,9999,999,990.00') vlu, to_char(sum(decode(c.pkt_ty, 'NR', trunc(b.cts, 2), b.cts)*(nvl(b.fnl_usd, b.quot)*nvl(decode(a.fnl_exh_rte,1,null,null,null,a.fnl_exh_rte),0))),'999999999990.00') fnlexhvlu "+
        "  from msal a,Sal_Pkt_Dtl_V b, mstk c,mnme d , df_users e , nme_dtl f "+
       " where a.idn=b.idn and b.mstk_idn=c.idn  "+
        "  and b.typ in ('SL','DLV') and c.stt in ('MKSL','SRV_SL_IS', 'MKSD','BRC_MKSD') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) "+sql+""+bql+
        " and d.nme_idn = a.nme_idn "+
       " and e.usr = a.aud_created_by  and e.nme_idn =f.nme_idn and f.mprp='OFFLOC'  and f.txt = ?\n" + 
      " and trunc(b.dte)  between "+dtefrom+" and "+dteto+
       " group by d.emp_idn order by 2 ";
            ary = new ArrayList();
            ary.add(usrLoc);
          }
          


            outLst = db.execSqlLst("totalqry", totalQry, ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            HashMap totalDtl = new HashMap();
            totalDtl.put("sale_id", util.nvl(rs.getString("sale_id")));
            totalDtl.put("qty", util.nvl(rs.getString("qty")));
            totalDtl.put("cts", util.nvl(rs.getString("cts")));
            totalDtl.put("vlu", util.nvl(rs.getString("vlu")));
            String fnlexhvlu=util.nvl(rs.getString("fnlexhvlu"),"0").trim();
            if(Double.parseDouble(fnlexhvlu) <= 0)
            fnlexhvlu="-";
            totalDtl.put("fnlexhvlu", fnlexhvlu);
            totalSale.put(util.nvl(rs.getString("sale_id")), totalDtl);
        }
        rs.close(); pst.close();
        req.setAttribute("totalSaleTbl", totalSale);
        
      HashMap grandDtl = new HashMap();
      String grandQry = " select " +
            " sum(nvl(b.qty,1)) qty , sum(decode(c.pkt_ty, 'NR', trunc(b.cts, 2), b.cts)) cts "+
            " , to_char(sum(decode(c.pkt_ty, 'NR', trunc(b.cts, 2), b.cts)*nvl(b.fnl_usd, b.quot))  ,'999,9999,999,990.00') vlu,to_char(sum(decode(c.pkt_ty, 'NR', trunc(b.cts, 2), b.cts)*(nvl(b.fnl_usd, b.quot)*nvl(decode(a.fnl_exh_rte,1,null,null,null,a.fnl_exh_rte),0))),'999999999990.00') fnlexhvlu  "+
        " from msal a,Sal_Pkt_Dtl_V b, mstk c,mnme d "+
       " where a.idn=b.idn and b.mstk_idn=c.idn  "+
      "  and b.typ in ('SL','DLV') and c.stt in('MKSL','SRV_SL_IS', 'MKSD','BRC_MKSD') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) "+sql+""+bql+
      " and d.nme_idn=a.nme_idn and trunc(b.dte)  between "+dtefrom+" and "+dteto;
     if(!usrLoc.equals("")){
       grandQry = " select" +
           " sum(nvl(b.qty,1)) qty , sum(decode(c.pkt_ty, 'NR', trunc(b.cts, 2), b.cts)) cts "+
           " , to_char(sum(decode(c.pkt_ty, 'NR', trunc(b.cts, 2), b.cts)*nvl(b.fnl_usd, b.quot))  ,'999,9999,999,990.00') vlu, to_char(sum(decode(c.pkt_ty, 'NR', trunc(b.cts, 2), b.cts)*(nvl(b.fnl_usd, b.quot)*nvl(decode(a.fnl_exh_rte,1,null,null,null,a.fnl_exh_rte),0))),'999999999990.00') fnlexhvlu "+
                 " from msal a,Sal_Pkt_Dtl_V b, mstk c,mnme d , df_users e , nme_dtl f "+
               " where a.idn=b.idn and b.mstk_idn=c.idn and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) "+
             " and e.usr = a.aud_created_by  and e.nme_idn =f.nme_idn and f.mprp='OFFLOC'  and f.txt = ?\n" + 
              "  and b.typ in ('SL','DLV') and c.stt in('MKSL','SRV_SL_IS', 'MKSD','BRC_MKSD') "+sql+""+bql+
              " and d.nme_idn=a.nme_idn and trunc(b.dte)  between "+dtefrom+" and "+dteto;

       }

            outLst = db.execSqlLst("grand total", grandQry, ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
      while (rs.next()) {
                
          grandDtl.put("qty", util.nvl(rs.getString("qty")));
          grandDtl.put("cts", util.nvl(rs.getString("cts")));
          grandDtl.put("vlu", util.nvl(rs.getString("vlu")));
          String fnlexhvlu=util.nvl(rs.getString("fnlexhvlu"),"0").trim();
          if(Double.parseDouble(fnlexhvlu) <= 0)
          fnlexhvlu="-";
          grandDtl.put("fnlexhvlu", fnlexhvlu);
       
      }
        rs.close(); pst.close();
      req.setAttribute("grandtotal", grandDtl);
      req.setAttribute("pktTy", pktTy);
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("DAILY_SALE_RPT");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("DAILY_SALE_RPT");
        allPageDtl.put("DAILY_SALE_RPT",pageDtl);
        }
        info.setPageDetails(allPageDtl);
            util.saleperson();
            if(cnt.equals("hk"))
            util.groupcompany();
            int accessidn=util.updAccessLog(req,res,"Daily Sale Report", "load end");
            req.setAttribute("accessidn", String.valueOf(accessidn));;
        return am.findForward("load");
        }
    }
    public ActionForward chnageInvNme(ActionMapping am, ActionForm form,
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
            util.updAccessLog(req,res,"Daily Sale Report", "load start");
        DailySalesReportForm dailySalesReportForm = (DailySalesReportForm)form;

      ArrayList ary = new ArrayList();
            String salIdn =util.nvl((String)req.getParameter("p_sal_idn"));
            String nme_idn ="";
            String name ="";
            String addr ="";
            String addr_idn ="";
            String saledte="";
            String mnlsale_id="";
            ary.add(salIdn);
            String sqlGetNme="select a.nme_idn , a.nme nme,to_char(dte,'dd-mm-yyyy') dte from nme_v a ,msal b  where a.nme_idn =b.inv_nme_idn and b.idn= ? ";

            ArrayList outLst = db.execSqlLst("sqlGetNme ", sqlGetNme, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                 nme_idn =util.nvl((String)rs.getString("nme_idn"));
                 name =util.nvl((String)rs.getString("nme"));
                 saledte=util.nvl((String)rs.getString("dte"));
                }
            rs.close(); pst.close();
            ary = new ArrayList();
            String SqlAdds ="select a.addr_idn , unt_num, bldg ||''|| street ||''|| landmark ||''|| area ||' '|| b.city_nm||' '|| c.country_nm addr \n" + 
                    "  from maddr a, mcity b, mcountry c where a.city_idn = b.city_idn and b.country_idn = c.country_idn and a.nme_idn =? and a.vld_dte is null  order by a.dflt_yn desc";   
            ary.add(nme_idn);

            outLst = db.execSqlLst("SqlAdds", SqlAdds, ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                 addr_idn =util.nvl((String)rs.getString("addr_idn"));
                 addr =util.nvl((String)rs.getString("unt_num")+" "+(String)rs.getString("addr"));
                }
            rs.close(); pst.close();
            String mnlsaleidQ="select b.txt from jansal a ,stk_dtl b  where a.idn=? and a.mstk_idn=b.mstk_idn and b.mprp='SAL_ID' and b.grp=1";
            ary = new ArrayList();
            ary.add(salIdn);

            outLst = db.execSqlLst("mnlsaleidQ", mnlsaleidQ, ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
            if(rs.next()){
                 mnlsale_id=util.nvl((String)rs.getString("txt"));
            }
            rs.close(); pst.close();            
            
            req.setAttribute("ADDRIDN", addr_idn);
            req.setAttribute("ADDR", addr);
            req.setAttribute("NMEIDN", nme_idn);
            req.setAttribute("SAL_DTE", saledte);
            req.setAttribute("NAME", name);
            req.setAttribute("SALIDN", salIdn);
            req.setAttribute("MNLSALE_ID", mnlsale_id);
            util.updAccessLog(req,res,"Daily Sale Report", "load start");
        return am.findForward("chnageInvNme");
        }
    }
    
    public ActionForward pktDtl(ActionMapping am, ActionForm form,
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
            util.updAccessLog(req,res,"Daily Sale Report", "pktDtl start");
        DailySalesReportForm dailySalesReportForm = (DailySalesReportForm)form;
        String salId = util.nvl(req.getParameter("saleId"));
        ArrayList pktDtlList = new ArrayList();
        ArrayList itemHdr = new ArrayList();
        itemHdr.add("SALEID");
        itemHdr.add("VNM");
        itemHdr.add("SALEPERSON");
        itemHdr.add("BYR");
        itemHdr.add("COUNTRY");
        ArrayList ary = new ArrayList();
        ArrayList prpDspBlocked = info.getPageblockList();
        if(!salId.equals("")){
            String pktDtlSql =
                          " select a.idn saleid,c.idn,to_char(b.cts,'99999999990.99') cts,DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), c.vnm) vnm,nvl(c.upr,c.cmp) rte,c.rap_rte,byr.get_nm(nvl(d.emp_idn,0)) sale_name,byr.get_nm(nvl(d.nme_idn,0)) byr,nvl(b.fnl_sal, b.quot) memoQuot,to_char(trunc(b.cts,2) * c.rap_rte, '99999999990.00') rapvlu,\n" + 
                          " to_char(trunc(b.cts,2) * Nvl(b.fnl_usd, b.quot), '9999999990.00') amt,byr.get_country(nvl(a.nme_idn,0)) country,to_char((((nvl(b.fnl_usd, b.quot))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') r_dis,c.pkt_ty  "+
                          " from msal a,Sal_Pkt_Dtl_V b, mstk c,mnme d\n" + 
                          " where a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn and c.idn=b.mstk_idn and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) \n" + 
                          " And B.Stt Not In ('RT','CS','CL') And B.Typ In ('SL','DLV') And C.Stt In('MKSL','SRV_SL_IS', 'MKSD','BRC_MKSD') And a.idn= ?\n" + 
                          " order by c.sk1 ";
        ary.add(salId);

            ArrayList outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            HashMap pktDtl = new HashMap();
            pktDtl.put("SALEID", util.nvl(rs.getString("saleid")));
            pktDtl.put("VNM", util.nvl(rs.getString("vnm")));
            pktDtl.put("SALEPERSON", util.nvl(rs.getString("sale_name")));
            pktDtl.put("BYR", util.nvl(rs.getString("byr")));
            pktDtl.put("COUNTRY", util.nvl(rs.getString("country")));
            pktDtl.put("RTE", util.nvl(rs.getString("rte")));
            pktDtl.put("SALE RTE", util.nvl(rs.getString("memoQuot")));
            pktDtl.put("AMOUNT", util.nvl(rs.getString("amt")));
            pktDtl.put("RAP_RTE", util.nvl(rs.getString("rap_rte")));
            String pktIdn = rs.getString("idn");
            String pkt_ty=util.nvl(rs.getString("pkt_ty"));
            String mdl="DAILY_VW";
            if(pkt_ty.equals("MIX"))
            mdl="DAILY_MIX_VW";
            String getPktPrp =
                " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
                + " from stk_dtl a, mprp b, rep_prp c "
                + " where a.mprp = b.prp and b.prp = c.prp and c.mdl = ? and a.mstk_idn = ? and a.grp=1  and c.flg <> 'N' "
                + " order by c.rnk, c.srt ";

            ary = new ArrayList();
            ary.add(mdl);
            ary.add(pktIdn);

            ArrayList outLst1 = db.execSqlLst(" Pkt Prp", getPktPrp, ary);
            PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
            ResultSet rs1 = (ResultSet)outLst1.get(1);
             while (rs1.next()) {
                String lPrp = util.nvl(rs1.getString("mprp"));
                String lVal = util.nvl(rs1.getString("val"));
                pktDtl.put(lPrp, lVal);
                if(prpDspBlocked.contains(lPrp)){
                }else if(!itemHdr.contains(lPrp)){
                itemHdr.add(lPrp);
                    if(lPrp.equals("RTE")){
                      itemHdr.add("SALE RTE");
                      itemHdr.add("AMOUNT");
                    }
                    if(lPrp.equals("RAP_RTE")){
                           itemHdr.add("RAPVLU");
                    }
                }
            }
            pktDtl.put("RTE",util.nvl(rs.getString("rte")));
            pktDtl.put("RAP_DIS",util.nvl(rs.getString("r_dis")));
            pktDtl.put("CRTWT",util.nvl(rs.getString("cts")));
            pktDtl.put("RAP_RTE",util.nvl(rs.getString("rap_rte")));
            pktDtl.put("RAPVLU",util.nvl(rs.getString("rapvlu")));
            pktDtlList.add(pktDtl);
            rs1.close(); pst1.close();
        }
            rs.close(); pst.close();
            
        }
       session.setAttribute("pktList", pktDtlList);
       session.setAttribute("itemHdr",itemHdr);
            util.updAccessLog(req,res,"Daily Sale Report", "pktDtl end");
        return am.findForward("pktDtl");
        }
    }
    
  public ActionForward SaleSlipDtl(ActionMapping am, ActionForm form,
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
          util.updAccessLog(req,res,"Daily Sale Report", "pktDtl start");
      DailySalesReportForm dailySalesReportForm = (DailySalesReportForm)form;
        String nmeidn = util.nvl(req.getParameter("nmeidn"));
        String dte = util.nvl(req.getParameter("dte"));
      ArrayList pktDtlList = new ArrayList();
      ArrayList itemHdr = new ArrayList();
      itemHdr.add("SR");
      itemHdr.add("VNM");
      String mdl="SALE_SLIP_VW";
      ArrayList ary = new ArrayList();
      ArrayList prpDspBlocked = info.getPageblockList();
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
        String pktDtlSql =
                      "select c.idn,a.idn salId, c.vnm,c.cert_no,a.idn saleid,b.emp sale_name,b.byr byr,b.dlv_dte dlv_dte,to_char(a.dte,'dd-mm-yyyy') sal_dte,b.rmk \n" + 
                      " ,nvl(c.upr,c.cmp) rte, a.exh_rte,c.vnm,c.cert_no,to_char(b.cts,'99999999990.99') cts, to_char(trunc(b.cts,2) * Nvl(b.fnl_usd, b.quot), '9999999990.00') amt, \n" + 
                      "to_char((((nvl(b.fnl_usd, b.quot))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') r_dis,nvl(b.fnl_sal, b.quot) memoQuot\n" + 
                      "from msal a,Sal_Pkt_Dtl_V b, mstk c\n" + 
                      "where a.idn=b.idn and b.mstk_idn=c.idn and c.idn=b.mstk_idn  \n" + 
                      "And B.Stt Not In ('RT','CS','CL') And B.Typ In ('SL','DLV') And C.Stt In('MKSL','SRV_SL_IS', 'MKSD','BRC_MKSD') and b.nme_idn=? And Trunc(B.Dte) Between "+dtefrom+" And "+dteto+ 
                      " order by c.sk1,a.idn";
        ary = new ArrayList();
        ary.add(nmeidn);

          ArrayList outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
          HashMap pktDtl = new HashMap();
          pktDtl.put("SLIP ID", util.nvl(rs.getString("salId")));
          pktDtl.put("VNM", util.nvl(rs.getString("vnm")));
          pktDtl.put("SALE RTE", util.nvl(rs.getString("memoQuot")));
          pktDtl.put("AMOUNT", util.nvl(rs.getString("amt")));
          pktDtl.put("REMARK", util.nvl(rs.getString("rmk")));
          String pktIdn = rs.getString("idn");
           String getPktPrp =
              " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
              + " from stk_dtl a, mprp b, rep_prp c "
              + " where a.mprp = b.prp and b.prp = c.prp and c.mdl = ? and a.mstk_idn = ? and a.grp=1  and c.flg <> 'N' "
              + " order by c.rnk, c.srt ";

          ary = new ArrayList();
          ary.add(mdl);
          ary.add(pktIdn);

          ArrayList outLst1 = db.execSqlLst(" Pkt Prp", getPktPrp, ary);
          PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
          ResultSet rs1 = (ResultSet)outLst1.get(1);
           while (rs1.next()) {
              String lPrp = util.nvl(rs1.getString("mprp"));
              String lVal = util.nvl(rs1.getString("val"));
              pktDtl.put(lPrp, lVal);
              if(prpDspBlocked.contains(lPrp)){
              }else if(!itemHdr.contains(lPrp)){
                  itemHdr.add(lPrp);
                  if(lPrp.equals("RTE")){
                    itemHdr.add("SALE RTE");
                    itemHdr.add("SALE DIS");         
                    itemHdr.add("AMOUNT");
                  }
                  if(lPrp.equals("RAP_RTE")){
                         itemHdr.add("RAPVLU");
                  }
              }
          }
          pktDtl.put("RTE",util.nvl(rs.getString("rte")));
          pktDtl.put("SALE RTE",util.nvl(rs.getString("memoQuot")));
          pktDtl.put("SALE DIS",util.nvl(rs.getString("r_dis")));
          pktDtl.put("CRTWT",util.nvl(rs.getString("cts")));
          pktDtlList.add(pktDtl);
          rs1.close(); pst1.close();
      }
          rs.close(); pst.close();
          
      }
        }
        itemHdr.add("SLIP ID");
        itemHdr.add("REMARK");
     session.setAttribute("pktList", pktDtlList);
     session.setAttribute("itemHdr",itemHdr);
          util.updAccessLog(req,res,"Daily Sale Report", "pktDtl end");
      return am.findForward("pktDtl");
      }
  }
    public ActionForward pktDtlvigat(ActionMapping am, ActionForm form,
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
            util.updAccessLog(req,res,"Daily Sale Report", "pktDtlvigat start");
        DailySalesReportForm dailySalesReportForm = (DailySalesReportForm)form;
        String salId = util.nvl(req.getParameter("saleId"));
        ArrayList pktDtlList = new ArrayList();
        ArrayList itemHdr = new ArrayList();
        itemHdr.add("SR");
        itemHdr.add("PKTNO");
        ArrayList ary = new ArrayList();
        ArrayList prpDspBlocked = info.getPageblockList();
        int count=1;
        if(!salId.equals("")){
            String pktDtlSql =
                          " select B.Trm Trms,to_char(a.dte,'dd-mm-yyyy') salDte,a.idn saleid,c.idn,to_char(b.cts,'99999999990.99') cts,DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), c.vnm) vnm,nvl(c.upr,c.cmp) rte,c.rap_rte,byr.get_nm(nvl(d.emp_idn,0)) sale_name,byr.get_nm(nvl(d.nme_idn,0)) byr,nvl(b.fnl_sal, b.quot) memoQuot,to_char(trunc(b.cts,2) * c.rap_rte, '99999999990.00') rapvlu,\n" + 
                          " to_char(Nvl(b.fnl_usd, b.quot), '9999999990.00') est,to_char(trunc(b.cts,2) * Nvl(b.fnl_usd, b.quot), '9999999990.00') amt,byr.get_country(nvl(a.nme_idn,0)) country,to_char((((nvl(b.fnl_usd, b.quot))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') r_dis,c.pkt_ty,decode(c.pkt_ty,'NR','S','M') stone  "+
                          " from msal a,Sal_Pkt_Dtl_V b, mstk c,mnme d\n" + 
                          " where a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn and c.idn=b.mstk_idn and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA'))\n" + 
                          " And B.Stt Not In ('RT','CS','CL') And B.Typ In ('SL','DLV') And C.Stt In('MKSL','SRV_SL_IS', 'MKSD','BRC_MKSD') And a.idn= ?\n" + 
                          " order by c.sk1 ";
        ary.add(salId);

            ArrayList outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            HashMap pktDtl = new HashMap();
            pktDtl.put("SR", String.valueOf(count++));
            pktDtl.put("PKTNO", util.nvl(rs.getString("vnm")));
            pktDtl.put("STONE", util.nvl(rs.getString("stone")));
            pktDtl.put("PCS", "1");
            pktDtl.put("CTS", util.nvl(rs.getString("cts")));
            pktDtl.put("EST", util.nvl(rs.getString("est")));
            pktDtl.put("TRMS", util.nvl(rs.getString("Trms")));
            pktDtl.put("BYR", util.nvl(rs.getString("byr")));
            pktDtl.put("DTE", util.nvl(rs.getString("salDte")));
            pktDtl.put("EST", util.nvl(rs.getString("est")));
            pktDtl.put("TOTAL $", util.nvl(rs.getString("amt")));
            String pktIdn = rs.getString("idn");
            String pkt_ty=util.nvl(rs.getString("pkt_ty"));
            String mdl="VIGAT_VW";
            if(pkt_ty.equals("MIX"))
            mdl="VIGAT_VW";
            String getPktPrp =
                " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
                + " from stk_dtl a, mprp b, rep_prp c "
                + " where a.mprp = b.prp and b.prp = c.prp and c.mdl = ? and a.mstk_idn = ? and a.grp=1  and c.flg <> 'N' "
                + " order by c.rnk, c.srt ";

            ary = new ArrayList();
            ary.add(mdl);
            ary.add(pktIdn);

            ArrayList outLst1 = db.execSqlLst(" Pkt Prp", getPktPrp, ary);
            PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
            ResultSet rs1 = (ResultSet)outLst1.get(1);
             while (rs1.next()) {
                String lPrp = util.nvl(rs1.getString("mprp"));
                String lVal = util.nvl(rs1.getString("val"));
                pktDtl.put(lPrp, lVal);
                if(prpDspBlocked.contains(lPrp)){
                }else if(!itemHdr.contains(lPrp)){
                itemHdr.add(lPrp);
                    if(lPrp.equals("SHAPE_CLR_COL")){
                      itemHdr.add("STONE");
                      itemHdr.add("PCS");
                        itemHdr.add("CTS");
                        itemHdr.add("EST");
                        itemHdr.add("TOTAL $");
                    }
                }
            }
            pktDtlList.add(pktDtl);
            rs1.close(); pst1.close();
        }
            rs.close(); pst.close();
            
        }
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = (String)dbinfo.get("CNT");
            HashMap ExcelDtl = new HashMap();
            HSSFWorkbook hwb = null;
            ExcelUtil xlUtil = new ExcelUtil();
            xlUtil.init(db, util, info);
            hwb =  xlUtil.VIGAT(itemHdr,pktDtlList);
            OutputStream out = res.getOutputStream();
            String CONTENT_TYPE = "getServletContext()/vnd-excel";
            String fileNm = "VIGAT_"+salId+".xls";
            res.setContentType(CONTENT_TYPE);
            res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
            hwb.write(out);
            out.flush();
            out.close();
            util.updAccessLog(req,res,"Daily Sale Report", "pktDtlvigat end");
            return null;
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
            util.updAccessLog(req,res,"Daily Sale Report", "pktDtlExcel start");
            GenericInterface genericInt = new GenericImpl();
        DailySalesReportForm dailySalesReportForm = (DailySalesReportForm)form;
        String nmeidn = util.nvl(req.getParameter("nmeidn"));
        String dte = util.nvl(req.getParameter("dte"));
        String pktTy = util.nvl(req.getParameter("pktTy"));
        ArrayList pktDtlList = new ArrayList();
        ArrayList itemHdr = new ArrayList();
        ArrayList ary = new ArrayList();
        ArrayList prpDspBlocked = info.getPageblockList();
        String mdl="DAILY_VW";
        if(pktTy.equals("MIX"))
        mdl="DAILY_MIX_VW";
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = (String)dbinfo.get("CNT");
        ArrayList vwprpLst = genericInt.genericPrprVw(req,res,mdl,mdl);
        if(cnt.equals("ri"))
        itemHdr.add("Customer ID");
        itemHdr.add("SALEID");
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
                 if(prp.equals("RAP_RTE")){
                        itemHdr.add("RAPVLU");
                 }
                }              
            }
        nmeidn=nmeidn.replaceFirst(",", "");
        dte=dte.replaceFirst(",", "");
        String[] nmeidnLst=nmeidn.split(",");
        String[] dteLst=dte.split(",");
        System.out.println(nmeidn);
            String mprpStr = "";
            String mdlPrpsQ = " Select Decode ( Rank() Over ( Order By Srt),1,' ',',')||''''||Prp||''''||' '||Upper(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1')) str From Rep_Prp Rp Where rp.MDL = ? order by srt " ;
            ary = new ArrayList();
            ary.add(mdl);
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
       String pktTyQ="";
        if(!pktTy.equals(""))
            pktTyQ=" and c.pkt_ty='"+pktTy+"'";
        if(!nmeidn.equals("") && !dte.equals("")){
              dtefrom = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
              dteto = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
         String pktDtlSql = " with " +
        " STKDTL as " +
        " ( Select c.sk1,a.idn saleid,c.idn,nvl(nvl(nvl(st.txt,st.dte),st.num),st.prt1) atr , mprp,to_char(b.cts,'99999999990.99') cts,DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), c.vnm) vnm,nvl(c.upr,c.cmp) rate,c.rap_rte raprte,byr.get_nm(nvl(d.emp_idn,0)) sale_name,byr.get_nm(nvl(d.nme_idn,0)) byr,d.nme_idn,nvl(b.fnl_sal, b.quot) memoQuot,to_char(trunc(b.cts,2) * c.rap_rte, '99999999990.00') rapvlu,\n" + 
              " to_char(trunc(b.cts,2) * Nvl(b.fnl_usd, b.quot), '9999999990.00') amt,byr.get_country(nvl(a.nme_idn,0)) country,to_char((((nvl(b.fnl_usd, b.quot))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') r_dis "+
              " from stk_dtl st,msal a,Sal_Pkt_Dtl_V b, mstk c,mnme d\n" + 
              " where st.mstk_idn=c.idn and a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn and c.idn=b.mstk_idn and a.typ='SL'\n" + 
              " And B.Stt Not In ('RT','CS','CL') And B.Typ In ('SL','DLV') And C.Stt In('MKSL','SRV_SL_IS', 'MKSD','BRC_MKSD') And D.Nme_Idn= ? and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) \n" + 
              " and trunc(b.dte)  between "+dtefrom+" and "+dteto +"\n "+pktTyQ+
            "and exists (select 1 from rep_prp rp where rp.MDL = '"+mdl+"' and st.mprp = rp.prp)  And st.Grp = 1) " +
            " Select * from stkDtl PIVOT " +
            " ( max(atr) " +
            " for mprp in ( "+mprpStr+" ) ) order by 1 " ;
        ary = new ArrayList();
        ary.add(nmeidn);

            outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            HashMap pktDtl = new HashMap();
            pktDtl.put("Customer ID", util.nvl(rs.getString("nme_idn")));
            pktDtl.put("SALEID", util.nvl(rs.getString("saleid")));
            pktDtl.put("VNM", util.nvl(rs.getString("vnm")));
            pktDtl.put("SALEPERSON", util.nvl(rs.getString("sale_name")));
            pktDtl.put("BYR", util.nvl(rs.getString("byr")));
            pktDtl.put("COUNTRY", util.nvl(rs.getString("country")));
            pktDtl.put("RTE", util.nvl(rs.getString("rate")));
            pktDtl.put("SALE RTE", util.nvl(rs.getString("memoQuot")));
            pktDtl.put("AMOUNT", util.nvl(rs.getString("amt")));           
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
            pktDtl.put("RAPVLU",util.nvl(rs.getString("rapvlu")));
            pktDtlList.add(pktDtl);
        }
            rs.close(); pst.close();
            
        }
        }
        session.setAttribute("pktList", pktDtlList);
        session.setAttribute("itemHdr",itemHdr);
            util.updAccessLog(req,res,"Daily Sale Report", "pktDtlExcel end");
        return am.findForward("pktDtl");  
        }
    }
    
    public ActionForward pktDtlExcelCdinesh(ActionMapping am, ActionForm form,
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
            util.updAccessLog(req,res,"Daily Sale Report", "pktDtlExcel start");
            GenericInterface genericInt = new GenericImpl();
        ArrayList vwprpLst = genericInt.genericPrprVw(req,res,"DAILY_VW_CD","DAILY_VW_CD");
        DailySalesReportForm dailySalesReportForm = (DailySalesReportForm)form;
        String nmeidn = util.nvl(req.getParameter("nmeidn"));
        String dte = util.nvl(req.getParameter("dte"));
        ArrayList pktDtlList = new ArrayList();
        ArrayList itemHdr = new ArrayList();
        ArrayList ary = new ArrayList();
        ArrayList prpDspBlocked = info.getPageblockList();
        itemHdr.add("PARTY NAME");
        itemHdr.add("VNM");
            for(int i=0;i<vwprpLst.size();i++){
                String prp=util.nvl((String)vwprpLst.get(i));
                if(prpDspBlocked.contains(prp)){
                }else if(!itemHdr.contains(prp)){
                itemHdr.add(prp);
                if(prp.equals("CRTWT")){
                    itemHdr.add("BROKER");
                    itemHdr.add("DAYS");
                     itemHdr.add("SAL DAYS");
                    itemHdr.add("REMARKS");
                 }
                    if(prp.equals("LOTID")){
                        itemHdr.add("RATE(RS)");
                        itemHdr.add("RATE($)");
                         itemHdr.add("A/L");
                         itemHdr.add("MGMT_DIS");
                         itemHdr.add("FNL_EXH_RTE");
                         itemHdr.add("REJ");
                     }
                }              
            }
        nmeidn=nmeidn.replaceFirst(",", "");
        dte=dte.replaceFirst(",", "");
        String[] nmeidnLst=nmeidn.split(",");
        String[] dteLst=dte.split(",");
        System.out.println(nmeidn);
            String mprpStr = "";
            String mdlPrpsQ = " Select Decode ( Rank() Over ( Order By Srt),1,' ',',')||''''||Prp||''''||' '||Upper(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1')) str From Rep_Prp Rp Where rp.MDL = ? order by srt " ;
            ary = new ArrayList();
            ary.add("DAILY_VW_CD");

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
        if(!nmeidn.equals("") && !dte.equals("")){
              dtefrom = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
              dteto = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
         String pktDtlSql = " with " +
        " STKDTL as " +
        " ( Select c.sk1,a.idn saleid,c.idn,nvl(nvl(nvl(st.txt,st.dte),st.num),st.prt1) atr , mprp,to_char(b.cts,'99999999990.99') cts,DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), c.vnm) vnm,nvl(c.upr,c.cmp) rate,c.rap_rte raprte,byr.get_nm(nvl(d.emp_idn,0)) sale_name,byr.get_nm(nvl(d.nme_idn,0)) byr,nvl(b.fnl_sal, b.quot) memoQuot,to_char(trunc(b.cts,2) * c.rap_rte, '99999999990.00') rapvlu,\n" + 
              " to_char(trunc(b.cts,2) * Nvl(b.fnl_usd, b.quot), '9999999990.00') amt,byr.get_country(nvl(a.nme_idn,0)) country,to_char((((nvl(b.fnl_usd, b.quot))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') r_dis,decode(a.cur,'USD','',b.quot) quot,TRUNC (b.quot/NVL (a.exh_rte, 1), 2) quot_usd,DF_GET_ADDLSLDIS(a.idn) mgmt_dis,a.fnl_exh_rte,SUBSTR (INITCAP (byr.get_nm (a.mbrk2_idn, 'NM')), 0, 200) sb\n" + 
              "                , SUBSTR (INITCAP (byr.get_nm (a.mbrk3_idn, 'NM')), 0, 200) sb2\n" + 
              "                , SUBSTR (INITCAP (byr.get_nm (a.mbrk4_idn, 'NM')), 0, 200) sb3,a.rmk,Byr.Get_Trms(a.Nmerel_Idn) days,Byr.get_mtrms(a.trms_idn) saldays "+
              " from stk_dtl st,msal a,Sal_Pkt_Dtl_V b, mstk c,mnme d\n" + 
              " where st.mstk_idn=c.idn and a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn and c.idn=b.mstk_idn and a.typ in ('SL','LS') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA'))\n" + 
              " And B.Stt Not In ('RT','CS','CL') And B.Typ In ('SL','DLV') And C.Stt In('MKSL','SRV_SL_IS', 'MKSD','BRC_MKSD') And D.Nme_Idn= ?\n" + 
              " and trunc(b.dte)  between "+dtefrom+" and "+dteto +"\n "+
            "and exists (select 1 from rep_prp rp where rp.MDL = 'DAILY_VW_CD' and st.mprp = rp.prp)  And st.Grp = 1) " +
            " Select * from stkDtl PIVOT " +
            " ( max(atr) " +
            " for mprp in ( "+mprpStr+" ) ) order by 1 " ;
        ary = new ArrayList();
        ary.add(nmeidn);

            outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            HashMap pktDtl = new HashMap();
            pktDtl.put("VNM", util.nvl(rs.getString("vnm")));
            pktDtl.put("PARTY NAME", util.nvl(rs.getString("byr")));
            pktDtl.put("RATE(RS)", util.nvl(rs.getString("quot")));
            pktDtl.put("RATE($)", util.nvl(rs.getString("quot_usd")));
            pktDtl.put("MGMT_DIS", util.nvl(rs.getString("mgmt_dis")));
            pktDtl.put("FNL_EXH_RTE", util.nvl(rs.getString("fnl_exh_rte")));           
            pktDtl.put("DAYS", util.nvl(rs.getString("days")));
            pktDtl.put("SAL DAYS", util.nvl(rs.getString("saldays")));
            pktDtl.put("REMARKS", util.nvl(rs.getString("rmk")));  
            String sb = util.nvl(rs.getString("sb"));
            String sb2 = util.nvl(rs.getString("sb2"));
            String sb3 = util.nvl(rs.getString("sb3"));
            String brknm="";
            if (!sb.equals("None"))
                brknm = sb;
            if (!sb2.equals("None"))
                brknm = brknm + "-" + sb2;
            if (!sb3.equals("None"))
                brknm = brknm + "-" + sb3;
            pktDtl.put("BROKER",brknm);
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
            pktDtl.put("RAPVLU",util.nvl(rs.getString("rapvlu")));
            pktDtlList.add(pktDtl);
        }
            rs.close(); pst.close();
            
        }
        }
        session.setAttribute("pktList", pktDtlList);
        session.setAttribute("itemHdr",itemHdr);
            util.updAccessLog(req,res,"Daily Sale Report", "pktDtlExcel end");
        return am.findForward("pktDtl");  
        }
    }
  public ActionForward pktDtlExcelXL(ActionMapping am, ActionForm form,
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
          util.updAccessLog(req,res,"Daily Sale Report", "pktDtlExcelXL start");
          GenericInterface genericInt = new GenericImpl();
      ArrayList vwprpLst = genericInt.genericPrprVw(req,res,"DAILY_VW_XL","DAILY_VW_XL");
      DailySalesReportForm dailySalesReportForm = (DailySalesReportForm)form;
      String nmeidn = util.nvl(req.getParameter("nmeidn"));
      String dte = util.nvl(req.getParameter("dte"));
      ArrayList pktDtlList = new ArrayList();
      ArrayList itemHdr = new ArrayList();
      ArrayList ary = new ArrayList();
      ArrayList prpDspBlocked = info.getPageblockList();
    
     
      itemHdr.add("FLG");
      itemHdr.add("SALEID");
      itemHdr.add("APPROVE_DATE");
      itemHdr.add("DATE");
      itemHdr.add("MEMOID");
      itemHdr.add("SALEID");
      itemHdr.add("BYR");
      itemHdr.add("TERM");
      
      itemHdr.add("VNM");
      itemHdr.add("QTY");
    
     for(int i=0;i<vwprpLst.size();i++){
              String prp=util.nvl((String)vwprpLst.get(i));
              if(prpDspBlocked.contains(prp)){
              }else if(!itemHdr.contains(prp)){
              itemHdr.add(prp);
              if(prp.equals("RTE")){
              itemHdr.add("VALUE");
              }
            }  
          }
      
        itemHdr.add("STONEWISE_DIS");
        itemHdr.add("NET_DISC_AMT");
        itemHdr.add("NET_STONE_AMTPER_CTS");
        itemHdr.add("NET_STONE_AMT");
        itemHdr.add("NET_RAP_BACK");
        itemHdr.add("MB");
        itemHdr.add("MB COMM");
        itemHdr.add("SB");
        itemHdr.add("SB COMM");
        itemHdr.add("GRANDTOTAL");
        itemHdr.add("EXHRTE");
        itemHdr.add("RMK");
        itemHdr.add("TYP");
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
      if(!nmeidn.equals("") && !dte.equals("")){
            dtefrom = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
            dteto = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
       String pktDtlSql =
        "select a.idn saleid,c.idn,c.tfl3 ,b.qty,to_char(b.cts,'99999999990.99') cts,DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), c.vnm) vnm,nvl(c.upr,c.cmp) rte,c.rap_rte,byr.get_nm(nvl(d.nme_idn,0)) byr,nvl(b.fnl_sal, b.quot) memoQuot,to_char(trunc(b.cts,2) * c.rap_rte, '99999999990.00') rapvlu, "+
         "to_char(trunc(b.cts,2) * Nvl(b.fnl_usd, b.quot), '9999999990.00') amt, to_char(Trunc(Nvl(B.Fnl_Sal, B.Quot)*Trunc(B.Cts,2)/Trunc(B.Cts, 2),0)-(B.Net_Stone_Amt),'9999999990.00') Fnlgrand,to_char((((nvl(b.fnl_usd, b.quot))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') r_dis, "+ 
         " a.rmk ,byr.get_nm(a.mbrk1_idn) mb, nvl(a.brk1_comm,0) mbcomm, byr.get_nm(a.mbrk2_idn) sub, nvl(a.brk2_comm,0) subComm ,to_char(a.dte,'dd-mm-yyyy') salDte , to_char(f.dte,'dd-mm-yyyy') appDte ,f.idn memoId , f.typ , b.stt  salStt,b.trm trms,nvl(f.fnl_xrt,f.exh_rte) fnl_xrt,b.STONEWISE_DIS,b.NET_DISC_AMT ,b.NET_STONE_AMT ,b.NET_STONE_AMTPER_CTS,b.NET_RAP_BACK  "+
        " from msal a,Sal_Pkt_Dtl_V b, mstk c,mnme d,nmerel e, mjan f "+
         " where a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn and c.idn=b.mstk_idn and a.typ='SL' and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA'))\n" + 
         " And B.Stt Not In ('RT','CS','CL') And B.Typ In ('SL','DLV') And C.Stt In('MKSL','SRV_SL_IS', 'MKSD','BRC_MKSD') And D.Nme_Idn= ? and a.nmerel_idn=e.nmerel_idn and a.memo_id=f.idn \n" + 
        " and trunc(b.dte)  between "+dtefrom+" and "+dteto+"  order by d.fnme , c.sk1 ";
      ary = new ArrayList();
      ary.add(nmeidn);

          ArrayList outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
      while(rs.next()){
          HashMap pktDtl = new HashMap();
          pktDtl.put("SALEID", util.nvl(rs.getString("saleid")));
          pktDtl.put("DATE", util.nvl(rs.getString("salDte")));
          pktDtl.put("FLG", util.nvl(rs.getString("salStt")));
          pktDtl.put("MEMOID", util.nvl(rs.getString("memoId")));
          pktDtl.put("APPROVE_DATE", util.nvl(rs.getString("appDte")));
          pktDtl.put("TYP", util.nvl(rs.getString("typ")));
          pktDtl.put("BYR", util.nvl(rs.getString("byr")));
          pktDtl.put("TERM", util.nvl(rs.getString("trms")));
          pktDtl.put("VALUE", util.nvl(rs.getString("amt")));
          pktDtl.put("RAP_RTE", util.nvl(rs.getString("rap_rte")));
          pktDtl.put("REMARK", util.nvl(rs.getString("rmk")));
          pktDtl.put("MB", util.nvl(rs.getString("mb")));
          pktDtl.put("MB COMM", util.nvl(rs.getString("mbcomm")));
          pktDtl.put("SB", util.nvl(rs.getString("sub")));
          pktDtl.put("SB COMM", util.nvl(rs.getString("subcomm")));
          pktDtl.put("VNM", util.nvl(rs.getString("vnm")));
          pktDtl.put("QTY", util.nvl(rs.getString("qty")));
          pktDtl.put("CTS", util.nvl(rs.getString("cts")));
          pktDtl.put("GRANDTOTAL", util.nvl(rs.getString("Fnlgrand")));
          pktDtl.put("EXHRTE",util.nvl(rs.getString("fnl_xrt")));
          pktDtl.put("STONEWISE_DIS", util.nvl(rs.getString("STONEWISE_DIS")));
          pktDtl.put("NET_DISC_AMT", util.nvl(rs.getString("NET_DISC_AMT")));
          pktDtl.put("NET_STONE_AMT", util.nvl(rs.getString("NET_STONE_AMT")));
          pktDtl.put("NET_STONE_AMTPER_CTS", util.nvl(rs.getString("NET_STONE_AMTPER_CTS")));
          pktDtl.put("NET_RAP_BACK",util.nvl(rs.getString("NET_RAP_BACK")));
          String pktIdn = util.nvl(rs.getString("idn"));
          String getPktPrp =
              " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
              + " from stk_dtl a, mprp b, rep_prp c "
              + " where a.mprp = b.prp and b.prp = c.prp and c.mdl = 'DAILY_VW_XL' and a.mstk_idn = ? and a.grp=1 and  c.flg <> 'N' "
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
          pktDtl.put("RTE", util.nvl(rs.getString("memoQuot")));
          pktDtl.put("RAP_DIS",util.nvl(rs.getString("r_dis")));
          pktDtl.put("CRTWT",util.nvl(rs.getString("cts")));
          pktDtl.put("RAP_RTE",util.nvl(rs.getString("rap_rte")));
          pktDtl.put("RAPVLU",util.nvl(rs.getString("rapvlu")));
          pktDtlList.add(pktDtl);
          rs1.close(); pst1.close();
          
      }
          rs.close(); pst.close();
          
      }
      }
      session.setAttribute("pktList", pktDtlList);
      session.setAttribute("itemHdr",itemHdr);
          util.updAccessLog(req,res,"Daily Sale Report", "pktDtlExcelXL end");
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
            util.updAccessLog(req,res,"Daily Sale Report", "pktDtlExcelXLHK start");
            GenericInterface genericInt = new GenericImpl();
            DailySalesReportForm dailySalesReportForm = (DailySalesReportForm)form;
        ArrayList vwprpLst = genericInt.genericPrprVw(req,res,"DAILY_VW_XLHK","DAILY_VW_XLHK");
        String nmeidn = util.nvl(req.getParameter("nmeidn"));
        String dte = util.nvl(req.getParameter("dte"));
        ArrayList pktDtlList = new ArrayList();
        ArrayList itemHdr = new ArrayList();
        ArrayList ary = new ArrayList();
        ArrayList prpDspBlocked = info.getPageblockList();
    //          itemHdr.add("QTY");
          itemHdr.add("TFL3");
            for(int i=0;i<vwprpLst.size();i++){
                String prp=util.nvl((String)vwprpLst.get(i));
                if(prpDspBlocked.contains(prp)){
                }else if(!itemHdr.contains(prp)){
                itemHdr.add(prp);
                if(prp.equals("PU")){
                itemHdr.add("SALE RTE");
                itemHdr.add("AMOUNT");
//                itemHdr.add("STONEWISE_DIS");
//                itemHdr.add("NET_DISC_AMT");
//                itemHdr.add("NET_STONE_AMT");
//                itemHdr.add("NET_STONE_AMTPER_CTS");
//                itemHdr.add("NET_RAP_BACK");
                    itemHdr.add("SALEID");
//                      itemHdr.add("DATE");
//                      itemHdr.add("SALSTT");
                      itemHdr.add("MEMOID");
//                      itemHdr.add("APPROVE DATE");
                      itemHdr.add("BYR");
//                      itemHdr.add("TYP");
                        itemHdr.add("MB");
                        itemHdr.add("MB COMM");
                        itemHdr.add("SB");
                        itemHdr.add("SB COMM");
//                        itemHdr.add("FNL XRT");
//                        itemHdr.add("TERM");
//                      itemHdr.add("VNM");
                }
                    if(prp.equals("RAP_RTE")){
                           itemHdr.add("RAPVLU");
                    }
                }  
            }
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
         String pktDtlSql =
              " Select a.idn salid,c.tfl3,c.cert_no,C.Idn,1 qty ,to_char(b.cts,'99999999990.99') cts,DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), c.vnm) vnm,Nvl(C.Upr,C.Cmp) rte,C.Rap_Rte,Byr.Get_Nm(Nvl(d.Emp_Idn,0)) Sale_Name,byr.get_nm(nvl(d.nme_idn,0)) Byr,nvl(b.fnl_sal, b.quot) memoQuot,to_char(trunc(b.cts,2) * c.rap_rte, '99999999990.00') rapvlu,  \n" + 
              "               To_Char(Trunc(B.Cts,2) * Nvl(B.Fnl_Usd, B.Quot), '9999999990.00') Amt,Byr.Get_Country(Nvl(A.Nme_Idn,0)) Country,To_Char((((Nvl(B.Fnl_Usd, B.Quot))*B.Cts/B.Cts)/(C.Rap_Rte*B.Cts/B.Cts)*100) - 100,'999999990.00') R_Dis , \n" + 
              "              Byr.Get_Nm(A.Mbrk1_Idn) Mb, Nvl(A.Brk1_Comm,0) Mbcomm, Byr.Get_Nm(A.Mbrk2_Idn) Sub, Nvl(A.Brk2_Comm,0) Subcomm ,To_Char(A.Dte,'dd-mm-yyyy') saldte , To_Char(F.Dte,'dd-mm-yyyy') appdte ,F.Idn memoid , F.Typ ,F.idn Memo_Id , B.Stt  Slstt,B.Trm Trms,F.Fnl_Xrt,B.Stonewise_Dis,B.Net_Disc_Amt ,B.Net_Stone_Amt ,B.Net_Stone_Amtper_Cts,B.Net_Rap_Back\n" + 
              "         from msal a,Sal_Pkt_Dtl_V b, mstk c,mnme d,nmerel e, mjan f\n" + 
              "         where a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn and c.idn=b.mstk_idn and a.typ='SL' and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA'))\n" + 
              "         And B.Stt Not In ('RT','CS','CL') And B.Typ In ('SL','DLV') And C.Stt In('MKSL','SRV_SL_IS', 'MKSD','BRC_MKSD') And D.Nme_Idn= ? and a.nmerel_idn=e.nmerel_idn and a.memo_id=f.idn \n" + 
              "         And Trunc(B.Dte) Between "+dtefrom+" And "+dteto+"  order by c.sk1 ";
        ary = new ArrayList();
        ary.add(nmeidn);
            ArrayList outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            HashMap pktDtl = new HashMap();
            pktDtl.put("SALEID", util.nvl(rs.getString("salid")));
          pktDtl.put("DATE", util.nvl(rs.getString("saldte")));
          pktDtl.put("SALSTT", util.nvl(rs.getString("slStt")));
          pktDtl.put("MEMOID", util.nvl(rs.getString("memo_id")));
          pktDtl.put("APPROVE DATE", util.nvl(rs.getString("appdte")));
          pktDtl.put("TYP", util.nvl(rs.getString("typ")));
          pktDtl.put("BYR", util.nvl(rs.getString("byr")));
          pktDtl.put("TERM", util.nvl(rs.getString("trms")));
          pktDtl.put("TFL3", util.nvl(rs.getString("tfl3")));
          pktDtl.put("RTE", util.nvl(rs.getString("rte")));
          pktDtl.put("SALE RTE", util.nvl(rs.getString("memoQuot")));
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
            pktDtl.put("FNL XRT",util.nvl(rs.getString("fnl_xrt")));
            pktDtl.put("STONEWISE_DIS", util.nvl(rs.getString("STONEWISE_DIS")));
            pktDtl.put("NET_DISC_AMT", util.nvl(rs.getString("NET_DISC_AMT")));
            pktDtl.put("NET_STONE_AMT", util.nvl(rs.getString("NET_STONE_AMT")));
            pktDtl.put("NET_STONE_AMTPER_CTS", util.nvl(rs.getString("NET_STONE_AMTPER_CTS")));
              pktDtl.put("NET_RAP_BACK",util.nvl(rs.getString("NET_RAP_BACK")));
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
            pktDtl.put("RTE",util.nvl(rs.getString("rte")));
            pktDtl.put("RAP_DIS",util.nvl(rs.getString("r_dis")));
            pktDtl.put("CRTWT",util.nvl(rs.getString("cts")));
            pktDtl.put("RAP_RTE",util.nvl(rs.getString("rap_rte")));
            pktDtl.put("RAPVLU",util.nvl(rs.getString("rapvlu")));
            pktDtlList.add(pktDtl);
            rs1.close(); pst1.close();
        }
            rs.close(); pst.close();
            
        }
        }
        session.setAttribute("pktList", pktDtlList);
        session.setAttribute("itemHdr",itemHdr);
            util.updAccessLog(req,res,"Daily Sale Report", "pktDtlExcelXLHK end");
        return am.findForward("pktDtl");  
        }
    }
    public ActionForward memoDtlExcelXL(ActionMapping am, ActionForm form,
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
            util.updAccessLog(req,res,"Daily Sale Report", "memoDtlExcelXL start");
        DailySalesReportForm dailySalesReportForm = (DailySalesReportForm)form;
        String nmeidn = util.nvl(req.getParameter("nmeidn"));
        String dte = util.nvl(req.getParameter("dte"));
        ArrayList pktDtlList = new ArrayList();
        ArrayList itemHdr = new ArrayList();
        ArrayList ary = new ArrayList();
        itemHdr.add("APPROVE DATE");
        itemHdr.add("SALE DATE");
        itemHdr.add("LOCATION");
        itemHdr.add("User Name");
        itemHdr.add("SALEID");
        itemHdr.add("MEMOID");
        itemHdr.add("BYR");
        itemHdr.add("TERM");
        itemHdr.add("QTY");
        itemHdr.add("CTS");
        itemHdr.add("FNL_SAL");
        itemHdr.add("AVG_RAP_DIS");
        itemHdr.add("AVG_AMOUNT");
        itemHdr.add("EXH_RTE");
//        itemHdr.add("FNL XRT");
        itemHdr.add("MB");
        itemHdr.add("MB COMM");
        itemHdr.add("SB");
        itemHdr.add("SB COMM");
        itemHdr.add("REMARK");
        itemHdr.add("NET_DIS");
        itemHdr.add("NET_AMOUNT");
        itemHdr.add("FINAL_GRAND");
        

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
        if(!nmeidn.equals("") && !dte.equals("")){
              dtefrom = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
              dteto = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
         String pktDtlSql =
          "Select A.Idn Saleid,nt.txt Location,a.aud_created_by UserName,Sum(B.Qty) Qty,To_Char(Sum(Trunc(B.Cts,2)),'999990.99') Cts,\n" + 
          "trunc(sum(Nvl(C.Upr,C.Cmp)*trunc(b.cts,2))/sum(trunc(b.cts, 2)),0) Rte,\n" + 
          "D.Fnme Byr,\n" + 
          "Trunc(Sum(Nvl(B.Fnl_Sal, B.Quot)*Trunc(b.Cts,2)),0) fnl_sal,\n" + 
          "To_Char(Sum(Trunc(B.Cts,2)*(C.Rap_Rte)),'9999999999990.00') Rapvlu,\n" + 
          "To_Char(Sum(Trunc(B.Cts,2)*(Nvl(B.Fnl_Sal, B.Quot))),'999,9999999990.00') Amt,\n" + 
          "To_Char((Sum((Nvl(B.Fnl_Sal, B.Quot))*Trunc(B.Cts,2))/Sum(Trunc(B.Cts, 2))),'999,9999999990.00') avg_amt,\n" + 
          "To_Char(Trunc(((Sum((Nvl(B.Fnl_Sal, B.Quot))*Trunc(B.Cts,2))/Sum(Trunc(B.Cts, 2)))/(Sum(C.Rap_Rte*Trunc(B.Cts,2))/Sum(Trunc(B.Cts, 2)))*100) - 100, 2) ,'999999990.00') R_Dis,\n" + 
          "Byr.Get_Nm(A.Mbrk1_Idn) Mb,\n" + 
          "nvl(a.brk1_comm,0) mbcomm,\n" + 
          "Byr.Get_Nm(A.Mbrk2_Idn) Sub, Nvl(A.Brk2_Comm,0) Subcomm ,To_Char(A.Dte,'dd-mm-yyyy') Saldte , To_Char(F.Dte,'dd-mm-yyyy') Appdte ,\n" + 
          "F.Idn Memoid , F.Typ , B.Stt Salstt,B.Trm Trms,a.exh_rte,F.Fnl_Xrt,Sum(B.Net_Stone_Amt) Net_Amt,\n" + 
          "Trunc(100-((Sum(B.Net_Stone_Amt)/To_Char(Sum(Trunc(B.Cts,2)*(Nvl(B.Fnl_Usd, B.Quot))),'9999999999990.00'))*100),2) Net_Dis,\n" + 
          "Trunc(Sum(Nvl(B.Fnl_Sal, B.Quot)*Trunc(B.Cts,2))/Sum(Trunc(B.Cts, 2)),0)-Sum(B.Net_Stone_Amt) Fnlgrand,a.rmk\n" + 
          "from msal a,Sal_Pkt_Dtl_V b, mstk c,mnme d,nmerel e, mjan f,nme_dtl nt, df_users df \n" + 
          "where a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn and c.idn=b.mstk_idn and a.typ='SL'\n" + 
          "And B.Stt Not In ('RT','CS','CL') And B.Typ In ('SL','DLV') And C.Stt In('MKSL','SRV_SL_IS', 'MKSD','BRC_MKSD') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA'))\n" + 
          "And D.Nme_Idn= ? And A.Nmerel_Idn=E.Nmerel_Idn And A.Memo_Id=F.Idn and\n" + 
          " df.usr = a.aud_created_by and df.nme_idn = nt.nme_idn and nt.mprp='OFFLOC'\n" + 
          "and trunc(b.dte)  between "+dtefrom+" and "+dteto+" \n" + 
          "Group By A.Idn,Nt.Txt,A.Aud_Created_By, D.Fnme, Byr.Get_Nm(A.Mbrk1_Idn), Nvl(A.Brk1_Comm,0), Byr.Get_Nm(A.Mbrk2_Idn), Nvl(A.Brk2_Comm,0), To_Char(A.Dte,'dd-mm-yyyy'), To_Char(F.Dte,'dd-mm-yyyy'), F.Idn, F.Typ, B.Stt, B.Trm,a.exh_rte, F.Fnl_Xrt,a.rmk order by a.idn ";
        ary = new ArrayList();
        ary.add(nmeidn);

            ArrayList outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            HashMap pktDtl = new HashMap();
            pktDtl.put("APPROVE DATE", util.nvl(rs.getString("appDte")));
            pktDtl.put("SALE DATE", util.nvl(rs.getString("Saldte")));
            pktDtl.put("User Name", util.nvl(rs.getString("UserName")));
            pktDtl.put("LOCATION", util.nvl(rs.getString("Location")));            
            pktDtl.put("SALEID", util.nvl(rs.getString("saleid")));
            pktDtl.put("MEMOID", util.nvl(rs.getString("memoId")));
            pktDtl.put("BYR", util.nvl(rs.getString("byr")));
            pktDtl.put("TERM", util.nvl(rs.getString("trms")));
            pktDtl.put("QTY", util.nvl(rs.getString("qty")));
            pktDtl.put("CTS", util.nvl(rs.getString("cts")));
            pktDtl.put("FNL_SAL", util.nvl(rs.getString("fnl_sal")));
            pktDtl.put("AVG_RAP_DIS", util.nvl(rs.getString("R_Dis")));
            pktDtl.put("AVG_AMOUNT", util.nvl(rs.getString("avg_amt")));
            pktDtl.put("EXH_RTE", util.nvl(rs.getString("exh_rte")));
            pktDtl.put("FNL XRT",util.nvl(rs.getString("Fnl_Xrt")));
            pktDtl.put("MB", util.nvl(rs.getString("mb")));
            pktDtl.put("MB COMM", util.nvl(rs.getString("mbcomm")));
            pktDtl.put("SB", util.nvl(rs.getString("sub")));
            pktDtl.put("SB COMM", util.nvl(rs.getString("subcomm")));
            pktDtl.put("NET_DIS", util.nvl(rs.getString("Net_Dis")));
            pktDtl.put("NET_AMOUNT", util.nvl(rs.getString("Net_Amt")));
            pktDtl.put("FINAL_GRAND",util.nvl(rs.getString("Fnlgrand")));
            pktDtl.put("REMARK",util.nvl(rs.getString("rmk")));
            pktDtlList.add(pktDtl);           
        }
            rs.close(); pst.close();
            
        }
        }
        session.setAttribute("pktList", pktDtlList);
        session.setAttribute("itemHdr",itemHdr);
            util.updAccessLog(req,res,"Daily Sale Report", "memoDtlExcelXL end");
        return am.findForward("pktDtl");  
        }
    }
  public ActionForward pktDtlXL(ActionMapping am, ActionForm form,
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
          util.updAccessLog(req,res,"Daily Sale Report", "pktDtlXL start");
      DailySalesReportForm dailySalesReportForm = (DailySalesReportForm)form;
      String salId = util.nvl(req.getParameter("saleId"));
      ArrayList pktDtlList = new ArrayList();
      ArrayList itemHdr = new ArrayList();
        itemHdr.add("SALEID");
        itemHdr.add("DATE");
        itemHdr.add("SALSTT");
        itemHdr.add("MEMOID");
        itemHdr.add("APPROVE DATE");
        itemHdr.add("TYP");
        itemHdr.add("BYR");
        itemHdr.add("TERM");
        itemHdr.add("MB");
        itemHdr.add("MB COMM");
        itemHdr.add("SB");
        itemHdr.add("SB COMM");
        itemHdr.add("FNL XRT");
        itemHdr.add("VNM");
        itemHdr.add("QTY");
      ArrayList ary = new ArrayList();
      ArrayList prpDspBlocked = info.getPageblockList();
      if(!salId.equals("")){
          String pktDtlSql =
                        " select a.idn saleid,c.idn,to_char(b.cts,'99999999990.99') cts,DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), c.vnm) vnm,nvl(c.upr,c.cmp) rte,c.rap_rte,byr.get_nm(nvl(d.emp_idn,0)) sale_name,d.fnme byr,nvl(b.fnl_sal, b.quot) memoQuot,to_char(trunc(b.cts,2) * c.rap_rte, '99999999990.00') rapvlu,\n" + 
                        " to_char(trunc(c.cts,2) * Nvl(b.fnl_usd, b.quot), '9999999990.00') amt,to_char((((nvl(b.fnl_usd, b.quot))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') r_dis , f.fnl_xrt ,"+
                        " byr.get_nm(a.mbrk1_idn) mb, nvl(a.brk1_comm,0) mbcomm, byr.get_nm(a.mbrk2_idn) sub, nvl(a.brk2_comm,0) subComm ,to_char(a.dte,'dd-mm-yyyy') salDte , to_char(f.dte,'dd-mm-yyyy') appDte ,f.idn memoId , f.typ , b.stt  salStt,byr.get_trms(a.nme_idn) trms "+
                        " from msal a,Sal_Pkt_Dtl_V b, mstk c,mnme d,nmerel e, mjan f "+
                        " where a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn and c.idn=b.mstk_idn and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA'))\n" + 
                        " And B.Stt Not In ('RT','CS','CL') And B.Typ In ('SL','DLV') And C.Stt In('MKSL','SRV_SL_IS', 'MKSD','BRC_MKSD') And a.idn= ? and a.nmerel_idn=e.nmerel_idn and a.memo_id=f.idn\n" + 
                        " order by c.sk1 ";
      ary.add(salId);

          ArrayList outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
      while(rs.next()){
          HashMap pktDtl = new HashMap();
        pktDtl.put("SALEID", util.nvl(rs.getString("saleid")));
        pktDtl.put("DATE", util.nvl(rs.getString("salDte")));
        pktDtl.put("SALSTT", util.nvl(rs.getString("salStt")));
        pktDtl.put("MEMOID", util.nvl(rs.getString("memoId")));
        pktDtl.put("APPROVE DATE", util.nvl(rs.getString("appDte")));
        pktDtl.put("TYP", util.nvl(rs.getString("typ")));
        pktDtl.put("BYR", util.nvl(rs.getString("byr")));
        pktDtl.put("TERM", util.nvl(rs.getString("trms")));
        pktDtl.put("RTE", util.nvl(rs.getString("rte")));
        pktDtl.put("SALE RTE", util.nvl(rs.getString("memoQuot")));
        pktDtl.put("AMOUNT", util.nvl(rs.getString("amt")));
        pktDtl.put("RAP_RTE", util.nvl(rs.getString("rap_rte")));
        pktDtl.put("MB", util.nvl(rs.getString("mb")));
        pktDtl.put("MB COMM", util.nvl(rs.getString("mbcomm")));
        pktDtl.put("SB", util.nvl(rs.getString("sub")));
        pktDtl.put("SB COMM", util.nvl(rs.getString("subcomm")));
        pktDtl.put("FNL XRT",util.nvl(rs.getString("fnl_xrt")));
          String pktIdn = rs.getString("idn");
          String getPktPrp =
              " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
              + " from stk_dtl a, mprp b, rep_prp c "
              + " where a.mprp = b.prp and b.prp = c.prp and c.mdl = 'DAILY_VW' and a.mstk_idn = ? and a.grp=1  and c.flg <> 'N' "
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
              if(prpDspBlocked.contains(lPrp)){
              }else if(!itemHdr.contains(lPrp)){
              itemHdr.add(lPrp);
                  if(lPrp.equals("RTE")){
                    itemHdr.add("SALE RTE");
                    itemHdr.add("AMOUNT");
                  }
                  if(lPrp.equals("RAP_RTE")){
                         itemHdr.add("RAPVLU");
                  }
              }
          }
          pktDtl.put("RTE",util.nvl(rs.getString("rte")));
          pktDtl.put("RAP_DIS",util.nvl(rs.getString("r_dis")));
          pktDtl.put("CRTWT",util.nvl(rs.getString("cts")));
          pktDtl.put("RAP_RTE",util.nvl(rs.getString("rap_rte")));
          pktDtl.put("RAPVLU",util.nvl(rs.getString("rapvlu")));
          pktDtlList.add(pktDtl);
          rs1.close(); pst1.close();
      }
          rs.close(); pst.close();
          
      }
     session.setAttribute("pktList", pktDtlList);
     session.setAttribute("itemHdr",itemHdr);
     util.updAccessLog(req,res,"Daily Sale Report", "pktDtlXL end");
      return am.findForward("pktDtl");
      }
  }
  
  
    public ActionForward pktDtlSHMFG(ActionMapping am, ActionForm form,
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
            util.updAccessLog(req,res,"Daily Sale Report", "pktDtlSHMFG start");
        DailySalesReportForm dailySalesReportForm = (DailySalesReportForm)form;
        GenericInterface genericInt = new GenericImpl();
        ArrayList vwprpLst = genericInt.genericPrprVw(req,res,"SHMF_DAILY_SL_VW","SHMF_DAILY_SL_VW");
        String nmeidn = util.nvl(req.getParameter("nmeidn"));
        String dte = util.nvl(req.getParameter("dte"));
        ArrayList pktDtlList = new ArrayList();
        ArrayList itemHdr = new ArrayList();
        ArrayList prpDspBlocked = info.getPageblockList();
            itemHdr.add("SRNO");
            for(int i=0;i<vwprpLst.size();i++){
                String prp=util.nvl((String)vwprpLst.get(i));
                if(prpDspBlocked.contains(prp)){
                }else if(!itemHdr.contains(prp)){
                itemHdr.add(prp);
                    if(prp.equals("HK_ID"))
                    itemHdr.add("SALEEX");
                    if(prp.equals("LOC")){
                    itemHdr.add("DLV_DTE");
                    itemHdr.add("SAL_DTE");
                    itemHdr.add("CUSTOMER");
                    itemHdr.add("RMK");
                    itemHdr.add("MB");
                    itemHdr.add("MB COMM");
                    itemHdr.add("SB");
                    itemHdr.add("SB COMM");
                    }
                    if(prp.equals("SL_TYP")){
                    itemHdr.add("EXH_RTE");
                    }
                    if(prp.equals("RAP_PCS")){
                    itemHdr.add("RTE");
                    itemHdr.add("RAP_DIS");
                    }
                    if(prp.equals("DP")){
                    itemHdr.add("AADAT LESS-2");
                    itemHdr.add("BLIND / WEB");
                        itemHdr.add("VALUE -0.25");
                        itemHdr.add("VALUE -0.5");
                        itemHdr.add("VALUE -1");
                        itemHdr.add("VALUE -2");
                        itemHdr.add("FULL TRADE DISCOUNT");
                        itemHdr.add("LESS OTHER");
                        itemHdr.add("PACKING LIST");
                        itemHdr.add("INVOICE NO");
                    }
                }  
            }
        ArrayList ary = new ArrayList();
        int sr=1;
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
            String pktDtlSql =
                          "select c.idn,c.vnm,c.cert_no,a.idn saleid,b.emp sale_name,b.byr byr,b.dlv_dte dlv_dte,to_char(a.dte,'dd-mm-yyyy') sal_dte,b.rmk \n" + 
                          ",byr.get_nm(a.mbrk1_idn) mb, nvl(a.brk1_comm,0) mbcomm, byr.get_nm(a.mbrk2_idn) sub, nvl(a.brk2_comm,0) subComm,a.exh_rte,c.vnm,c.cert_no,to_char(b.cts,'99999999990.99') cts, \n" + 
                          "to_char((((nvl(b.fnl_usd, b.quot))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') r_dis,nvl(b.fnl_sal, b.quot) memoQuot\n" + 
                          "from msal a,Sal_Pkt_Dtl_V b, mstk c\n" + 
                          "where a.idn=b.idn and b.mstk_idn=c.idn and c.idn=b.mstk_idn  \n" + 
                          "And B.Stt Not In ('RT','CS','CL') And B.Typ In ('SL','DLV') And C.Stt In('MKSL','SRV_SL_IS', 'MKSD','BRC_MKSD') and b.nme_idn=? And Trunc(B.Dte) Between "+dtefrom+" And "+dteto+ 
                          " order by c.sk1,a.idn";
        ary = new ArrayList();
        ary.add(nmeidn);

            ArrayList outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs= (ResultSet)outLst.get(1);
        while(rs.next()){
          HashMap pktDtl = new HashMap();
          String pktIdn = rs.getString("idn");
          String saleid = rs.getString("saleid");
          pktDtl.put("SRNO", String.valueOf(sr++));
          pktDtl.put("INVOICE NO", saleid);
          pktDtl.put("SALEEX", util.nvl(rs.getString("sale_name")));
          pktDtl.put("DLV_DTE", util.nvl(rs.getString("dlv_dte")));
          pktDtl.put("SAL_DTE", util.nvl(rs.getString("sal_dte")));
          pktDtl.put("CUSTOMER", util.nvl(rs.getString("byr")));
          pktDtl.put("RMK", util.nvl(rs.getString("rmk")));
          pktDtl.put("MB", util.nvl(rs.getString("mb")));
          pktDtl.put("MB COMM", util.nvl(rs.getString("mbcomm")));
          pktDtl.put("SB", util.nvl(rs.getString("sub")));
          pktDtl.put("SB COMM", util.nvl(rs.getString("subcomm")));
          pktDtl.put("EXH_RTE",util.nvl(rs.getString("EXH_RTE")));
            pktDtl.put("RTE",util.nvl(rs.getString("memoQuot")));
            pktDtl.put("RAP_DIS",util.nvl(rs.getString("r_dis")));
            String getPktPrp =
                " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
                + " from stk_dtl a, mprp b, rep_prp c "
                + " where a.mprp = b.prp and b.prp = c.prp and c.mdl = 'SHMF_DAILY_SL_VW' and a.mstk_idn = ? and a.grp=1  and c.flg <> 'N' "
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
            
            String getcharges =
                "Select b.ref_idn,\n" + 
                "Max (Decode (A.Typ, 'AADAT_COMM', nvl(nvl(b.charges_pct, b.charges), 0), Null)) AADAT_COMM,\n" + 
                "Max (Decode (A.Typ, 'BLIND_DISC', nvl(nvl(b.charges_pct, b.charges), 0), Null)) BLIND_DISC,\n" + 
                "Max (Decode (A.Typ, 'TRD_DIS', nvl(nvl(b.charges_pct, b.charges), 0), Null)) TRD_DIS,\n" + 
                "Max (Decode (A.Typ, 'ADDL_DIS', nvl(nvl(b.charges_pct, b.charges), 0), Null)) ADDL_DIS\n" + 
                "From Charges_Typ A,Trns_Charges B \n" + 
                "Where A.Idn=B.Charges_Idn And A.Stt='A' And Nvl(B.Flg,'NA') Not In('Y') And Ref_Idn In (?) And App_Typ='PP' and ref_typ='SAL'\n" + 
                "Group By B.Ref_Idn";
            ary = new ArrayList();
            ary.add(saleid);

            outLst1 = db.execSqlLst(" Pkt getcharges", getcharges, ary);
            pst1 = (PreparedStatement)outLst1.get(0);
            rs1 = (ResultSet)outLst1.get(1);
             while (rs1.next()) {
                pktDtl.put("AADAT LESS-2", util.nvl(rs1.getString("AADAT_COMM")));
                pktDtl.put("BLIND / WEB", util.nvl(rs1.getString("BLIND_DISC")));
                pktDtl.put("VALUE -"+util.nvl(rs1.getString("TRD_DIS")), util.nvl(rs1.getString("TRD_DIS")));
                pktDtl.put("LESS OTHER", util.nvl(rs1.getString("ADDL_DIS")));
            }
            rs1.close(); pst1.close();
            pktDtl.put("VNM",util.nvl(rs.getString("vnm")));
            pktDtl.put("CERT_NO",util.nvl(rs.getString("cert_no")));
            pktDtl.put("CRTWT",util.nvl(rs.getString("cts")));
            pktDtlList.add(pktDtl);

        }
            rs.close(); pst.close();
            
        }
        }
       session.setAttribute("pktList", pktDtlList);
       session.setAttribute("itemHdr",itemHdr);
       util.updAccessLog(req,res,"Daily Sale Report", "pktDtlSHMFG end");
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
          util.updAccessLog(req,res,"Daily Sale Report", "pktDtlExcelXLFNL start");
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
       String pktDtlSql =
            " Select a.idn salId , c.tfl3,c.cert_no,C.Idn,1 qty ,to_char(b.cts,'99999999990.99') cts,DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), c.vnm) vnm,Nvl(C.Upr,C.Cmp) rte,C.Rap_Rte,d.Fnme Byr,nvl(b.fnl_sal, b.quot) memoQuot,to_char(trunc(b.cts,2) * c.rap_rte, '99999999990.00') rapvlu, \n" + 
            " to_char(trunc(b.cts,2) * Nvl(b.fnl_usd, b.quot), '9999999990.00') amt,to_char((((nvl(b.fnl_usd, b.quot))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') r_dis , "+
            " to_char(a.dte,'dd-mm-yyyy') salDte ,c.sk1 , a.memo_id , b.STONEWISE_DIS,b.NET_DISC_AMT ,b.NET_STONE_AMT ,b.NET_STONE_AMTPER_CTS,b.NET_RAP_BACK "+
            "From msal A , Sal_Pkt_Dtl_V b, mstk c,mnme d,nmerel e \n" + 
            "Where A.Idn = B.Idn And B.Mstk_Idn=C.Idn and d.nme_idn=a.nme_idn and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA'))\n" + 
            "And Trunc(B.Dte) Between "+dtefrom+" And "+dteto+"\n" + 
            "And B.Nme_Idn=? And B.Stt Not In ('RT','CS','CL') And B.Typ In ('SL','DLV') And C.Stt In('MKSL','SRV_SL_IS', 'MKSD','BRC_MKSD') and a.nmerel_idn=e.nmerel_idn \n" + 
            "order by d.Fnme , c.sk1 ";
      ary = new ArrayList();
      ary.add(nmeidn);

          ArrayList outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
      while(rs.next()){
          HashMap pktDtl = new HashMap();
       
        pktDtl.put("DATE", util.nvl(rs.getString("salDte")));
   
        pktDtl.put("SALEID", util.nvl(rs.getString("salId")));
        pktDtl.put("MEMOID", util.nvl(rs.getString("memo_id")));
     
        pktDtl.put("BYR", util.nvl(rs.getString("byr")));
        pktDtl.put("SK1", util.nvl(rs.getString("sk1")));
        pktDtl.put("TFL3", util.nvl(rs.getString("tfl3")));
        pktDtl.put("RTE", util.nvl(rs.getString("rte")));
        pktDtl.put("SALE RTE", util.nvl(rs.getString("memoQuot")));
        pktDtl.put("AMOUNT", util.nvl(rs.getString("amt")));
        pktDtl.put("RAP_RTE", util.nvl(rs.getString("rap_rte")));
       
        pktDtl.put("VNM", util.nvl(rs.getString("vnm")));
        pktDtl.put("QTY", util.nvl(rs.getString("qty")));
        pktDtl.put("CTS", util.nvl(rs.getString("cts")));
          pktDtl.put("CERT_NO", util.nvl(rs.getString("cert_no")));
      
          pktDtl.put("STONEWISE_DIS", util.nvl(rs.getString("STONEWISE_DIS")));
          pktDtl.put("NET_DISC_AMT", util.nvl(rs.getString("NET_DISC_AMT")));
          pktDtl.put("NET_STONE_AMT", util.nvl(rs.getString("NET_STONE_AMT")));
          pktDtl.put("NET_STONE_AMTPER_CTS", util.nvl(rs.getString("NET_STONE_AMTPER_CTS")));
            pktDtl.put("NET_RAP_BACK",util.nvl(rs.getString("NET_RAP_BACK")));
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
          pktDtl.put("RTE",util.nvl(rs.getString("rte")));
          pktDtl.put("RAP_DIS",util.nvl(rs.getString("r_dis")));
          pktDtl.put("CRTWT",util.nvl(rs.getString("cts")));
          pktDtl.put("RAP_RTE",util.nvl(rs.getString("rap_rte")));
          pktDtl.put("RAPVLU",util.nvl(rs.getString("rapvlu")));
          pktDtlList.add(pktDtl);
          rs1.close(); pst1.close();
      }
          rs.close(); pst.close();
          
      }
      }
      session.setAttribute("pktList", pktDtlList);
      session.setAttribute("itemHdr",itemHdr);
          util.updAccessLog(req,res,"Daily Sale Report", "pktDtlExcelXLFNL end");
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
                rtnPg=util.checkUserPageRights("report/dailySalesReport.do?","");
                if(rtnPg.equals("unauthorized"))
                util.updAccessLog(req,res,"Daily Sale Report", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Daily Sale Report", "init");
            }
            }
            return rtnPg;
            }
    public DailySalesReportAction() {
        super();
    }
}
