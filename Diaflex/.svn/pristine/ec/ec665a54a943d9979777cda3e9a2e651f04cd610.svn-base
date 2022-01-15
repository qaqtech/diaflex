
package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.OutputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;


public class DailyDlvReportAction extends DispatchAction {
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
            util.updAccessLog(req,res,"Daily Delivery Report", "load start");
        DailyDlvForm dailyDlvForm = (DailyDlvForm)form;
            
      ResultSet rs = null;
      ArrayList ary = new ArrayList();
      String dtefrom = " trunc(sysdate) ";
      String dteto = " trunc(sysdate) ";
      String flg=util.nvl(req.getParameter("flg"));
      if(flg.equals("New")){
            dailyDlvForm.reset();
      }
            String pktTy = util.nvl(req.getParameter("PKT_TY"));
            if(pktTy.equals(""))
               pktTy = util.nvl((String)dailyDlvForm.getValue("PKT_TY"));
      String isHk = util.nvl(req.getParameter("ISHK"));
      if(isHk.equals(""))
          isHk = util.nvl((String)dailyDlvForm.getValue("ISHK"));
      String isLS = util.nvl(req.getParameter("ISLS"));
          if(isLS.equals(""))
        isLS = util.nvl((String)dailyDlvForm.getValue("ISLS"));
      String dfr = util.nvl((String)dailyDlvForm.getValue("dtefr"));
      String dto = util.nvl((String)dailyDlvForm.getValue("dteto"));
      String spersonId = util.nvl((String)dailyDlvForm.getValue("styp"));
      String buyerId = util.nvl(req.getParameter("nmeID"));
      String group = util.nvl((String)dailyDlvForm.getValue("group"));
     String usrLoc = util.nvl((String)dailyDlvForm.getValue("usrLoc"));
     String dfNmeIdn=util.nvl((String)info.getDfNmeIdn());
     String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
     ArrayList nmeidnDteDlvLst=new ArrayList();
      String sql="";
      String bql="";
      String localQ="";
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
      ArrayList rolenmLst=(ArrayList)info.getRoleLst();
      String usrFlg=util.nvl((String)info.getUsrFlg());
      String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
      if(!dfr.equals(""))
        dtefrom = "to_date('"+dfr+"' , 'dd-mm-yyyy')";
      
      if(!dto.equals(""))
        dteto = "to_date('"+dto+"' , 'dd-mm-yyyy')";
      
            if(info.getBlockLs().equals("Y")){
          localQ=" and a.typ not in ('LS')";
                isLS="YES";
            }
     if(isLS.equals("YES"))
         localQ=" and a.typ not in ('LS')";
      if(!spersonId.equals("") && spersonId.length()>1){
        sql = "and d.emp_idn='"+spersonId+"' ";
       
      }
      
      if(!buyerId.equals("")){
        bql = "and d.nme_idn= '"+buyerId+"' ";
      
      }
     if(!pktTy.equals("")){
                      sql+= " and c.pkt_ty in ('"+pktTy+"') "; 
                  dailyDlvForm.setValue("PKT_TY", pktTy);
     }
        if(!group.equals("")){
          bql =bql+ "and d.grp_nme_idn= ? ";
          ary.add(group);
          ary.add(group);
        }else{
        if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
        }else if(!dfgrpnmeidn.equals("") && !dfgrpnmeidn.equals("0")){
        bql =bql+" and d.grp_nme_idn=?"; 
        ary.add(dfgrpnmeidn);
        ary.add(dfgrpnmeidn);
        }  
        }          
        
            if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
                    if(util.nvl((String)info.getDmbsInfoLst().get("EMP_LOCK")).equals("Y")){
                        bql += " and (d.emp_idn= ? or d.emp_idn in(select emp_idn from nme_v where typ='EMPLOYEE' and mgr_idn= ?)) ";  
                        ary.add(dfNmeIdn);
                        ary.add(dfNmeIdn);
                        ary.add(dfNmeIdn);
                        ary.add(dfNmeIdn);
                    }
            }
        if(isHk.equals("HK"))
            bql = bql+" and b.stt in ('DP') ";
        else
            bql = bql+" and b.stt not in ('RT','CS','DP','PS','CL') ";
        
        HashMap delivery = new HashMap();
        String loadqry =" select delivery_id, delivery_name, byr, byrid , typ, dte,srt_dte "+
       "  , sum(qty) qty, to_char(sum(trunc(cts,2)),'999,990.99') cts "+
       " , to_char(sum(trunc(vlu,2)),'999,9999,999,990.00') vlu,to_char(sum(trunc(fnlexhvlu,2)),'9999999999990.00') fnlexhvlu  "+
       " from ( select nvl(d.emp_idn,0) delivery_id , byr.get_nm(nvl(d.emp_idn,0)) delivery_name "+
       " ,byr.get_nm(nvl(d.nme_idn,0)) byr , d.nme_idn byrid , b.typ typ "+
       " , to_char(b.dte,'dd-mm-yyyy') dte,to_number(to_char(b.dte, 'rrrrmmdd')) srt_dte "+
       " , sum(c.qty) qty , sum(trunc(b.cts,2)) cts "+
       " , sum(trunc(b.cts,2)*(nvl(b.fnl_usd, b.quot))) vlu, sum(trunc(b.cts,2)*(nvl(b.fnl_usd, b.quot))*nvl(decode(a.fnl_exh_rte,1,null,null,null,a.fnl_exh_rte),0)) fnlexhvlu "+
       " from mdlv a,Dlv_pkt_dtl_v b, mstk c,mnme d "+
       " where a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn and c.stt in('MKSD','BRC_MKSD') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) "+
       "  and b.typ in ('DLV') "+sql+""+bql+
       " and trunc(b.dte) between "+dtefrom+" and "+dteto+""+
       " group by nvl(d.emp_idn,0), d.nme_idn,byr.get_nm(nvl(d.nme_idn,0)), to_char(b.dte,'dd-mm-yyyy'),to_number(to_char(b.dte, 'rrrrmmdd')), b.typ "+
       "  UNION "+
       " select nvl(d.emp_idn,0) delivery_id "+
       " , byr.get_nm(nvl(d.emp_idn,0)) delivery_name  , 'MIX' byr , d.nme_idn byrid,b.typ typ "+
       " , to_char(b.dte,'dd-mm-yyyy') dte ,to_number(to_char(b.dte, 'rrrrmmdd')) srt_dte, sum(c.qty) qty , sum(trunc(b.cts,2)) cts "+
       " , sum(trunc(b.cts,2)*(nvl(b.fnl_usd, b.quot))) vlu, sum(trunc(b.cts,2)*(nvl(b.fnl_usd, b.quot))*nvl(decode(a.fnl_exh_rte,1,null,null,null,a.fnl_exh_rte),0)) fnlexhvlu "+
       " from msal a, Sal_pkt_dtl_v b, mstk c,mnme d "+
       " where a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn and c.stt in('MKSD','BRC_MKSD') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) "+
       " and a.typ in ('LS') "+sql+""+bql+localQ+
       " and trunc(b.dte) between "+dtefrom+" and "+dteto+""+
       " group by nvl(d.emp_idn,0), d.nme_idn,byr.get_nm(nvl(d.nme_idn,0)), to_char(b.dte,'dd-mm-yyyy'),to_number(to_char(b.dte, 'rrrrmmdd')), b.typ ) "+
       " group by delivery_id, delivery_name, byr, byrid, typ, dte,srt_dte   order by 2,3,7 ";
          if(!usrLoc.equals("")){
            loadqry =" select delivery_id, delivery_name, byr, byrid , typ, dte,srt_dte "+
                   "  , sum(qty) qty, to_char(sum(trunc(cts,2)),'999,990.99') cts "+
                   " , to_char(sum(trunc(vlu,2)),'999,9999,999,990.00') vlu,to_char(sum(trunc(fnlexhvlu,2)),'9999999999990.00') fnlexhvlu  "+
                   " from ( select nvl(d.emp_idn,0) delivery_id , byr.get_nm(nvl(d.emp_idn,0)) delivery_name "+
                   " ,byr.get_nm(nvl(d.nme_idn,0)) byr , d.nme_idn byrid , b.typ typ "+
                   " , to_char(b.dte,'dd-mm-yyyy') dte,to_number(to_char(b.dte, 'rrrrmmdd')) srt_dte "+
                   " , sum(c.qty) qty , sum(trunc(b.cts,2)) cts "+
                   " , sum(trunc(b.cts,2)*(nvl(b.fnl_usd, b.quot))) vlu, sum(trunc(b.cts,2)*(nvl(b.fnl_usd, b.quot))*nvl(decode(a.fnl_exh_rte,1,null,null,null,a.fnl_exh_rte),0)) fnlexhvlu "+
                   " from mdlv a,Dlv_pkt_dtl_v b, mstk c,mnme d ,df_users e , nme_dtl f   "+
                   " where a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn and c.stt in('MKSD','BRC_MKSD') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) "+
                   "  and b.typ in ('DLV') "+sql+""+bql+
                   " and trunc(b.dte) between "+dtefrom+" and "+dteto+""+
                   " and e.usr = a.aud_created_by and e.nme_idn =f.nme_idn and f.mprp='OFFLOC'  and f.txt ='"+usrLoc+"'"+
                   " group by nvl(d.emp_idn,0), d.nme_idn,byr.get_nm(nvl(d.nme_idn,0)), to_char(b.dte,'dd-mm-yyyy'),to_number(to_char(b.dte, 'rrrrmmdd')), b.typ "+
                   "  UNION "+
                   " select nvl(d.emp_idn,0) delivery_id "+
                   " , byr.get_nm(nvl(d.emp_idn,0)) delivery_name  , 'MIX' byr , d.nme_idn byrid,b.typ typ "+
                   " , to_char(b.dte,'dd-mm-yyyy') dte ,to_number(to_char(b.dte, 'rrrrmmdd')) srt_dte, sum(c.qty) qty , sum(trunc(b.cts,2)) cts "+
                   " , sum(trunc(b.cts,2)*(nvl(b.fnl_usd, b.quot))) vlu, sum(trunc(b.cts,2)*(nvl(b.fnl_usd, b.quot))*nvl(decode(a.fnl_exh_rte,1,null,null,null,a.fnl_exh_rte),0)) fnlexhvlu "+
                   " from msal a, Sal_pkt_dtl_v b, mstk c,mnme d ,df_users e , nme_dtl f "+
                   " where a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn and c.stt in('MKSD','BRC_MKSD') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) "+
                   " and a.typ in ('LS') "+sql+""+bql+localQ+
                   " and trunc(b.dte) between "+dtefrom+" and "+dteto+""+
                   " and e.usr = a.aud_created_by and e.nme_idn =f.nme_idn and f.mprp='OFFLOC'   and f.txt ='"+usrLoc+"'"+
                   " group by nvl(d.emp_idn,0), d.nme_idn,byr.get_nm(nvl(d.nme_idn,0)), to_char(b.dte,'dd-mm-yyyy'),to_number(to_char(b.dte, 'rrrrmmdd')), b.typ ) "+
                   " group by delivery_id, delivery_name, byr, byrid, typ, dte,srt_dte   order by 2,3,7 ";
                   
              
              
          }


            ArrayList outLst = db.execSqlLst("loadqry", loadqry, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
        int i = 0;
        while (rs.next()) {
            HashMap deliverydtl = new HashMap();
            i++;
            deliverydtl.put("delivery_id", util.nvl(rs.getString("delivery_id")));
            deliverydtl.put("delivery_name", util.nvl(rs.getString("delivery_name")));
            deliverydtl.put("byr", util.nvl(rs.getString("byr")));
            deliverydtl.put("dte", util.nvl(rs.getString("dte")));
            deliverydtl.put("qty", util.nvl(rs.getString("qty")));
            deliverydtl.put("cts", util.nvl(rs.getString("cts")));
            deliverydtl.put("typ", util.nvl(rs.getString("typ")));
            deliverydtl.put("byrid", util.nvl(rs.getString("byrid")));
            deliverydtl.put("vlu", util.nvl(rs.getString("vlu")));
            String fnlexhvlu=util.nvl(rs.getString("fnlexhvlu"),"0").trim();
            if(Double.parseDouble(fnlexhvlu) <= 0)
            fnlexhvlu="-";
            deliverydtl.put("fnlexhvlu", fnlexhvlu);
            delivery.put(i, deliverydtl);
            nmeidnDteDlvLst.add(util.nvl(rs.getString("byrid"))+"_"+util.nvl(rs.getString("dte")));
        }
        rs.close(); pst.close();
        req.setAttribute("deliveryTbl", delivery);
//  Added by Reshmi on 15-11-2011
        HashMap totaldelivery = new HashMap();
        String totalQry = " select delivery_id, delivery_name , sum(qty) qty, to_char(sum(trunc(cts,2)),'999,990.99') cts "+
       " , to_char(sum(trunc(vlu,2)),'999,9999,999,990.00') vlu,to_char(sum(trunc(fnlexhvlu,2)),'9999999999990.00') fnlexhvlu from ( "+
       " select nvl(d.emp_idn,0) delivery_id  , byr.get_nm(nvl(d.emp_idn,0)) delivery_name "+
      "  , sum(c.qty) qty , sum(trunc(b.cts,2)) cts "+
       " , sum(trunc(b.cts,2)*(nvl(b.fnl_usd, b.quot))) vlu, sum(trunc(b.cts,2)*(nvl(b.fnl_usd, b.quot))*nvl(decode(a.fnl_exh_rte,1,null,null,null,a.fnl_exh_rte),0)) fnlexhvlu "+
       " from mdlv a,Dlv_pkt_dtl_v b, mstk c,mnme d "+
       " where a.idn=b.idn and b.mstk_idn=c.idn "+
       "  and b.typ in ('DLV')  "+sql+""+bql+
      "  and d.nme_idn = a.nme_idn and c.stt in('MKSD','BRC_MKSD') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) "+
        " and trunc(b.dte)  between "+dtefrom+" and "+dteto+
       " group by nvl(d.emp_idn,0) "+
       " UNION "+
       " select nvl(d.emp_idn,0) delivery_id , byr.get_nm(nvl(d.emp_idn,0)) delivery_name "+
       " , sum(c.qty) qty , sum(trunc(b.cts,2)) cts "+
       " , sum(trunc(b.cts,2)*(nvl(b.fnl_usd, b.quot))) vlu, sum(trunc(b.cts,2)*(nvl(b.fnl_usd, b.quot))*nvl(decode(a.fnl_exh_rte,1,null,null,null,a.fnl_exh_rte),0)) fnlexhvlu "+
      "  from msal a, Sal_pkt_dtl_v b, mstk c,mnme d "+
      "  where a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn and c.stt in('MKSD','BRC_MKSD') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) "+
       "  and a.typ in ('LS') "+sql+""+bql+localQ+
        " and trunc(b.dte)  between "+dtefrom+" and "+dteto+
       " group by nvl(d.emp_idn,0) ) group by delivery_id, delivery_name "+
        " order by 2 ";
          if(!usrLoc.equals("")){
              
            totalQry = " select delivery_id, delivery_name , sum(qty) qty, to_char(sum(trunc(cts,2)),'999,990.99') cts "+
                   " , to_char(sum(trunc(vlu,2)),'999,9999,999,990.00') vlu,to_char(sum(trunc(fnlexhvlu,2)),'9999999999990.00') fnlexhvlu from ( "+
                   " select nvl(d.emp_idn,0) delivery_id  , byr.get_nm(nvl(d.emp_idn,0)) delivery_name "+
                  "  , sum(c.qty) qty , sum(trunc(b.cts,2)) cts "+
                   " , sum(trunc(b.cts,2)*(nvl(b.fnl_usd, b.quot))) vlu, sum(trunc(b.cts,2)*(nvl(b.fnl_usd, b.quot))*nvl(decode(a.fnl_exh_rte,1,null,null,null,a.fnl_exh_rte),0)) fnlexhvlu "+
                   " from mdlv a,Dlv_pkt_dtl_v b, mstk c,mnme d ,df_users e , nme_dtl f "+
                   " where a.idn=b.idn and b.mstk_idn=c.idn "+
                   "  and b.typ in ('DLV')  "+sql+""+bql+
                  "  and d.nme_idn = a.nme_idn and c.stt in('MKSD','BRC_MKSD') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) "+
                    " and trunc(b.dte)  between "+dtefrom+" and "+dteto+
                   " and e.usr = a.aud_created_by and e.nme_idn =f.nme_idn and f.mprp='OFFLOC'  and f.txt ='"+usrLoc+"'"+
                   " group by nvl(d.emp_idn,0) "+
                   " UNION "+
                   " select nvl(d.emp_idn,0) delivery_id , byr.get_nm(nvl(d.emp_idn,0)) delivery_name "+
                   " , sum(c.qty) qty , sum(trunc(b.cts,2)) cts "+
                   " , sum(trunc(b.cts,2)*(nvl(b.fnl_usd, b.quot))) vlu, sum(trunc(b.cts,2)*(nvl(b.fnl_usd, b.quot))*nvl(decode(a.fnl_exh_rte,1,null,null,null,a.fnl_exh_rte),0)) fnlexhvlu "+
                  "  from msal a, Sal_pkt_dtl_v b, mstk c,mnme d ,df_users e , nme_dtl f "+
                  "  where a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn and c.stt in('MKSD','BRC_MKSD') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) "+
                   "  and a.typ in ('LS') "+sql+""+bql+localQ+
                    " and trunc(b.dte)  between "+dtefrom+" and "+dteto+
                  " and e.usr = a.aud_created_by and e.nme_idn =f.nme_idn and f.mprp='OFFLOC'  and f.txt ='"+usrLoc+"'"+
                   " group by nvl(d.emp_idn,0) ) group by delivery_id, delivery_name "+
                    " order by 2 ";
          }

            outLst = db.execSqlLst("totalqry", totalQry, ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            HashMap totalDtl = new HashMap();
            totalDtl.put("delivery_id", util.nvl(rs.getString("delivery_id")));
            totalDtl.put("qty", util.nvl(rs.getString("qty")));
            totalDtl.put("cts", util.nvl(rs.getString("cts")));
            totalDtl.put("vlu", util.nvl(rs.getString("vlu")));
            String fnlexhvlu=util.nvl(rs.getString("fnlexhvlu"),"0").trim();
            if(Double.parseDouble(fnlexhvlu) <= 0)
            fnlexhvlu="-";
            totalDtl.put("fnlexhvlu", fnlexhvlu);
            totaldelivery.put(util.nvl(rs.getString("delivery_id")), totalDtl);
        }
        rs.close(); pst.close();
        req.setAttribute("totaldelivery", totaldelivery);
        
      HashMap grandDtl = new HashMap();
      String grandQry =  "select sum(qty) qty "+
        " , to_char(sum(trunc(cts,2)),'999,990.00') cts "+
        " , to_char(sum(vlu),'999,9999,999,990.00') vlu,to_char(sum(trunc(fnlexhvlu,2)),'9999999999990.00') fnlexhvlu "+
        " from ( select sum(c.qty) qty  , sum(trunc(b.cts,2)) cts "+
       " , sum(trunc(b.cts,2)*nvl(b.fnl_usd, b.quot)) vlu, sum(trunc(b.cts,2)*(nvl(b.fnl_usd, b.quot))*nvl(decode(a.fnl_exh_rte,1,null,null,null,a.fnl_exh_rte),0)) fnlexhvlu "+
       " from mdlv a,Dlv_pkt_dtl_v b, mstk c,mnme d "+
       " where a.idn=b.idn and b.mstk_idn=c.idn and c.stt in('MKSD','BRC_MKSD') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) "+
       " and b.typ in ('DLV') "+sql+""+bql+
       " and d.nme_idn=a.nme_idn and trunc(b.dte) between "+dtefrom+" and "+dteto+ 
                         
       " UNION "+
       " select sum(c.qty) qty "+
       " , sum(trunc(b.cts,2)) cts , sum(trunc(b.cts,2)*nvl(b.fnl_usd, b.quot)) vlu, sum(trunc(b.cts,2)*(nvl(b.fnl_usd, b.quot))*nvl(decode(a.fnl_exh_rte,1,null,null,null,a.fnl_exh_rte),0)) fnlexhvlu "+
        " from msal a,sal_pkt_dtl_v b, mstk c,mnme d "+
       " where a.idn=b.idn and b.mstk_idn=c.idn and c.stt in('MKSD','BRC_MKSD') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) "+
       "  and a.typ = 'LS' "+sql+""+bql+localQ+" and d.nme_idn=a.nme_idn "+
        " and trunc(b.dte) between "+dtefrom+" and "+dteto+" ) ";
          if(!usrLoc.equals("")){
            grandQry =  "select sum(qty) qty "+
                    " , to_char(sum(trunc(cts,2)),'999,990.00') cts "+
                    " , to_char(sum(vlu),'999,9999,999,990.00') vlu,to_char(sum(trunc(fnlexhvlu,2)),'9999999999990.00') fnlexhvlu "+
                    " from ( select sum(c.qty) qty  , sum(trunc(b.cts,2)) cts "+
                   " , sum(trunc(b.cts,2)*nvl(b.fnl_usd, b.quot)) vlu, sum(trunc(b.cts,2)*(nvl(b.fnl_usd, b.quot))*nvl(decode(a.fnl_exh_rte,1,null,null,null,a.fnl_exh_rte),0)) fnlexhvlu "+
                   " from mdlv a,Dlv_pkt_dtl_v b, mstk c,mnme d ,df_users e , nme_dtl f "+
                   " where a.idn=b.idn and b.mstk_idn=c.idn and c.stt in('MKSD','BRC_MKSD') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) "+
                   " and b.typ in ('DLV') "+sql+""+bql+
                   " and d.nme_idn=a.nme_idn and trunc(b.dte) between "+dtefrom+" and "+dteto+ 
                   " and e.usr = a.aud_created_by and e.nme_idn =f.nme_idn and f.mprp='OFFLOC'  and f.txt ='"+usrLoc+"'"+
                   " UNION "+
                   " select sum(c.qty) qty "+
                   " , sum(trunc(b.cts,2)) cts , sum(trunc(b.cts,2)*nvl(b.fnl_usd, b.quot)) vlu, sum(trunc(b.cts,2)*(nvl(b.fnl_usd, b.quot))*nvl(decode(a.fnl_exh_rte,1,null,null,null,a.fnl_exh_rte),0)) fnlexhvlu "+
                    " from msal a,sal_pkt_dtl_v b, mstk c,mnme d ,df_users e , nme_dtl f "+
                   " where a.idn=b.idn and b.mstk_idn=c.idn and c.stt in('MKSD','BRC_MKSD') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) "+
                   "  and a.typ = 'LS' "+sql+""+bql+localQ+" and d.nme_idn=a.nme_idn "+
                    " and trunc(b.dte) between "+dtefrom+" and "+dteto+"  "+
                   " and e.usr = a.aud_created_by and e.nme_idn =f.nme_idn and f.mprp='OFFLOC'  and f.txt ='"+usrLoc+"' )";
              
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
        session.setAttribute("nmeidnDteDlvLstAll", nmeidnDteDlvLst);
        dailyDlvForm.setValue("ISHK", isHk);
            dailyDlvForm.setValue("ISLS", isLS);
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("DAILY_DLV_RPT");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("DAILY_DLV_RPT");
        allPageDtl.put("DAILY_DLV_RPT",pageDtl);
        }
        info.setPageDetails(allPageDtl);
        util.saleperson();
        if(cnt.equals("hk"))
        util.groupcompany();
        int accessidn=util.updAccessLog(req,res,"Daily Delivery Report", "load end");
        req.setAttribute("accessidn", String.valueOf(accessidn));;
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
            util.updAccessLog(req,res,"Daily Delivery Report", "pktDtlExcel start");
        GenericInterface genericInt = new GenericImpl();
        ArrayList nmeidnDteDlvLst = (session.getAttribute("nmeidnDteDlvLst") == null)?new ArrayList():(ArrayList)session.getAttribute("nmeidnDteDlvLst");
        int nmeidnDteDlvLstsz=nmeidnDteDlvLst.size();
        String nmeidn="",dte="";
        DailyDlvForm dailyDlvForm = (DailyDlvForm)form;
        ArrayList pktDtlList = new ArrayList();
        ArrayList itemHdr = new ArrayList();
        ArrayList ary = new ArrayList();
        ArrayList prpDspBlocked = info.getPageblockList();
        String pktTy = util.nvl(req.getParameter("pktTy"));
        String mdl="DAILY_VW";
        if(pktTy.equals("MIX"))
        mdl="DAILY_MIX_VW";
        ArrayList vwprpLst = genericInt.genericPrprVw(req,res,mdl,mdl);
        itemHdr.add("DLVID");
        itemHdr.add("VNM");
        itemHdr.add("SALEPERSON");
        itemHdr.add("BYR");
        itemHdr.add("COUNTRY");
            itemHdr.add("DTE_DLV");
            itemHdr.add("AADAT");
            itemHdr.add("AADAT_COMM");
            itemHdr.add("AADAT1");
            itemHdr.add("AADAT1_COMM");
            itemHdr.add("BRK1");
            itemHdr.add("BRK1COMM");
            itemHdr.add("BRK2");
            itemHdr.add("BRK2COMM");
            itemHdr.add("BRK3");
            itemHdr.add("BRK3COMM");
            itemHdr.add("TERM");
            itemHdr.add("RMK");
            itemHdr.add("NOTE_PERSON");
            for(int i=0;i<vwprpLst.size();i++){
                String prp=util.nvl((String)vwprpLst.get(i));
                if(prpDspBlocked.contains(prp)){
                }else if(!itemHdr.contains(prp)){
                itemHdr.add(prp);
                if(prp.equals("RTE")){
                     itemHdr.add("FNLEXH_RTE");
                    itemHdr.add("SALE RTE");
                    itemHdr.add("SALE_DIS");
                    itemHdr.add("AMOUNT");
                     itemHdr.add("NET RTE");
                     itemHdr.add("NET DIS");
                     itemHdr.add("NET AMOUNT");
                     itemHdr.add("INR AMOUNT");
                 }
                    if(prp.equals("RAP_RTE")){
                           itemHdr.add("RAPVLU");
                    }
                }  
            } 
            
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
            String salByr ="";
            if(mprpStr.indexOf("SAL_BYR")!=-1)
                salByr=" SAL_BYR , ";
        for(int i=0;i<nmeidnDteDlvLstsz;i++){
        String nmeidnDteVal=util.nvl((String)nmeidnDteDlvLst.get(i));
        String[] nmeidnDteValSplit=nmeidnDteVal.split("_");
        nmeidn=nmeidnDteValSplit[0];  
        dte=nmeidnDteValSplit[1];  
        String dtefrom = " trunc(sysdate) ";
        String dteto = " trunc(sysdate) ";
        String pktTyQ="";
        if(!pktTy.equals(""))
        pktTyQ=" and c.pkt_ty='"+pktTy+"'";
        if(!nmeidn.equals("") && !dte.equals("")){
        boolean dlvtype=true;
              dtefrom = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
              dteto = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
         String pktDtlSql = " with " +
        " STKDTL as " +
        " ( Select a.idn dlvid,c.vnm pktcode, c.sk1,nvl(nvl(nvl(st.txt,st.dte),st.num),st.prt1) atr , mprp,C.Idn,to_char(b.cts,'99999999990.99') cts,DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), c.vnm) vnm,Nvl(C.Upr,C.Cmp) rate,C.Rap_Rte raprte,Byr.Get_Nm(Nvl(d.Emp_Idn,0)) Sale_Name,byr.get_nm(nvl(d.nme_idn,0)) Byr,nvl(b.fnl_sal, b.quot) memoQuot,decode(c.pkt_ty,'NR',to_char(((nvl(b.fnl_sal, b.quot)/c.rap_rte*100)-100),'999999990.00'),'') slback,to_char(trunc(b.cts,2) * c.rap_rte, '99999999990.00') rapvlu, \n" + 
              " byr.get_nm(nvl(a.inv_nme_idn,a.nme_idn)) billByr,to_char(b.dte, 'DD-MON-YYYY') dlvdte,a.fnl_exh_rte,byr.get_nm(a.aadat_idn) aadat, DECODE (a.aadat_paid, 'Y', NVL (a.aadat_comm, 0), 0) aadatcomm,byr.get_nm(a.mbrk1_idn) aadat1, DECODE (a.mbrk1_paid, 'Y', NVL (a.brk1_comm, 0), 0) aadat1comm,byr.get_nm(a.mbrk2_idn) brk1, DECODE (a.mbrk2_paid, 'Y', NVL (a.brk2_comm, 0), 0) brk1comm,byr.get_nm(a.mbrk3_idn) brk2,DECODE (a.mbrk3_paid, 'Y', NVL (a.brk3_comm, 0), 0) brk2comm,byr.get_nm(a.mbrk4_idn) brk3, DECODE (a.mbrk4_paid, 'Y', NVL (a.brk4_comm, 0), 0) brk3comm,b.trm trms,a.rmk dlvrmk,a.note_person,to_char(trunc(b.cts,2) * Nvl(b.fnl_usd, b.quot), '9999999990.00') amt,byr.get_country(nvl(a.nme_idn,0)) country,to_char((((nvl(b.fnl_usd, b.quot))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') r_dis , "+
            " b.qty qt,to_char(trunc(b.cts,2) * Nvl(b.fnl_bse, b.quot), '9999999990.00') netVlu , b.fnl_bse netRte,\n" + 
            " to_char((((nvl(b.fnl_bse, b.quot))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') netDis,decode(a.fnl_exh_rte,1,'',to_char((trunc(b.cts,2) * Nvl(b.fnl_bse, b.quot)*a.fnl_exh_rte), '9999999990.00')) inrVlu"+
             " From stk_dtl st,Mdlv A , Dlv_Pkt_Dtl_V B,Mstk C,Mnme d\n" + 
              "Where st.mstk_idn=c.idn and A.Idn = B.Idn And B.Mstk_Idn=C.Idn and d.nme_idn=a.nme_idn  and c.stt in('MKSD','BRC_MKSD') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA'))\n" + 
              "And Trunc(B.Dte) Between "+dtefrom+" And "+dteto+"\n" + 
              "And B.Nme_Idn=? And B.Stt Not In ('RT','CS','DP','PS','CL') And B.Typ In ('DLV')\n" + pktTyQ+
              " and exists (select 1 from rep_prp rp where rp.MDL = '"+mdl+"' and st.mprp = rp.prp)  And st.Grp = 1) " +
        " Select * from stkDtl PIVOT " +
        " ( max(atr) " +
        " for mprp in ( "+mprpStr+" ) )  order by 1 ,"+salByr+" 2 " ;
        ary = new ArrayList();
        ary.add(nmeidn);
        System.out.println("DLV"+nmeidn);

            outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            dlvtype=false;
            HashMap pktDtl = new HashMap();
            pktDtl.put("DLVID", util.nvl(rs.getString("dlvid")));
            pktDtl.put("VNM", util.nvl(rs.getString("vnm")));
            pktDtl.put("SALEPERSON", util.nvl(rs.getString("sale_name")));
            pktDtl.put("BYR", util.nvl(rs.getString("byr")));
            pktDtl.put("COUNTRY", util.nvl(rs.getString("country")));
            pktDtl.put("DTE_DLV", util.nvl(rs.getString("dlvdte")));
            pktDtl.put("FNLEXH_RTE", util.nvl(rs.getString("fnl_exh_rte")));
            pktDtl.put("RTE", util.nvl(rs.getString("rate")));
            pktDtl.put("RAP_RTE", util.nvl(rs.getString("raprte")));
            pktDtl.put("SALE RTE", util.nvl(rs.getString("memoQuot")));
            pktDtl.put("SALE_DIS", util.nvl(rs.getString("slback")));
            pktDtl.put("AMOUNT", util.nvl(rs.getString("amt")));
            pktDtl.put("AADAT", util.nvl(rs.getString("aadat")));
            pktDtl.put("AADAT_COMM", util.nvl(rs.getString("aadatcomm")));
            pktDtl.put("AADAT1", util.nvl(rs.getString("aadat1")));
            pktDtl.put("AADAT1_COMM", util.nvl(rs.getString("aadat1comm")));
            pktDtl.put("BRK1", util.nvl(rs.getString("brk1")));
            pktDtl.put("BRK1COMM", util.nvl(rs.getString("brk1comm")));
            pktDtl.put("BRK2", util.nvl(rs.getString("brk2")));
            pktDtl.put("BRK2COMM", util.nvl(rs.getString("brk3comm")));
            pktDtl.put("BRK3", util.nvl(rs.getString("brk3")));
            pktDtl.put("BRK3COMM", util.nvl(rs.getString("brk3comm")));
            pktDtl.put("TERM", util.nvl(rs.getString("trms")));
            pktDtl.put("RMK", util.nvl(rs.getString("dlvrmk")));
            pktDtl.put("NOTE_PERSON", util.nvl(rs.getString("note_person")));
            pktDtl.put("NET RTE", util.nvl(rs.getString("netRte")));
            pktDtl.put("NET DIS", util.nvl(rs.getString("netDis")));
            pktDtl.put("NET AMOUNT", util.nvl(rs.getString("netVlu")));
            pktDtl.put("INR AMOUNT", util.nvl(rs.getString("inrVlu")));
            String pktIdn = util.nvl(rs.getString("idn"));
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
                if (vwPrp.toUpperCase().equals("QTY"))
                fldName = "qt";
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
        if(dlvtype && info.getBlockLs().equals("")){
            pktDtlSql = " with " +
                    " STKDTL as " +
                    " ( Select a.idn dlvid,c.vnm pktcode, c.sk1,nvl(nvl(nvl(st.txt,st.dte),st.num),st.prt1) atr , mprp,C.Idn,to_char(b.cts,'99999999990.99') cts,DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), c.vnm) vnm,Nvl(C.Upr,C.Cmp) rate,C.Rap_Rte raprte,Byr.Get_Nm(Nvl(d.Emp_Idn,0)) Sale_Name,byr.get_nm(nvl(d.nme_idn,0)) Byr,nvl(b.fnl_sal, b.quot) memoQuot,decode(c.pkt_ty,'NR',to_char(((nvl(b.fnl_sal, b.quot)/c.rap_rte*100)-100),'999999990.00'),'') slback,to_char(trunc(b.cts,2) * c.rap_rte, '99999999990.00') rapvlu, \n" + 
                          " byr.get_nm(nvl(a.inv_nme_idn,a.nme_idn)) billByr,to_char(b.dte, 'DD-MON-YYYY') dlvdte,a.fnl_exh_rte,byr.get_nm(a.aadat_idn) aadat, DECODE (a.aadat_paid, 'Y', NVL (a.aadat_comm, 0), 0) aadatcomm,byr.get_nm(a.mbrk1_idn) aadat1, DECODE (a.mbrk1_paid, 'Y', NVL (a.brk1_comm, 0), 0) aadat1comm,byr.get_nm(a.mbrk2_idn) brk1, DECODE (a.mbrk2_paid, 'Y', NVL (a.brk2_comm, 0), 0) brk1comm,byr.get_nm(a.mbrk3_idn) brk2,DECODE (a.mbrk3_paid, 'Y', NVL (a.brk3_comm, 0), 0) brk2comm,byr.get_nm(a.mbrk4_idn) brk3, DECODE (a.mbrk4_paid, 'Y', NVL (a.brk4_comm, 0), 0) brk3comm,b.trm trms,a.rmk dlvrmk,a.note_person,to_char(trunc(b.cts,2) * Nvl(b.fnl_usd, b.quot), '9999999990.00') amt,byr.get_country(nvl(a.nme_idn,0)) country,to_char((((nvl(b.fnl_usd, b.quot))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') r_dis , "+
                        " to_char(trunc(b.cts,2) * Nvl(b.fnl_bse, b.quot), '9999999990.00') netVlu , b.fnl_bse netRte,\n" + 
                        " to_char((((nvl(b.fnl_bse, b.quot))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') netDis"+
                        "  From stk_dtl st,msal A , Sal_pkt_dtl_v B,Mstk C,Mnme d\n" + 
                          "Where st.mstk_idn=c.idn and A.Idn = B.Idn And B.Mstk_Idn=C.Idn and d.nme_idn=a.nme_idn  and c.stt in('MKSD','BRC_MKSD') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) \n" + 
                          "And Trunc(B.Dte) Between "+dtefrom+" And "+dteto+"\n" + 
                          "And B.Nme_Idn=? And B.Stt Not In ('RT','CS','DP','PS','CL') And a.Typ In ('LS')\n" +pktTyQ+ 
                          " and exists (select 1 from rep_prp rp where rp.MDL = '"+mdl+"' and st.mprp = rp.prp)  And st.Grp = 1) " +
                    " Select * from stkDtl PIVOT " +
                    " ( max(atr) " +
                    " for mprp in ( "+mprpStr+" ) ) order by 1  , "+salByr+" 2 " ;
                    ary = new ArrayList();
                    ary.add(nmeidn);
                    System.out.println("LS"+nmeidn);
                    
             outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
                    while(rs.next()){
                        HashMap pktDtl = new HashMap();
                        pktDtl.put("DLVID", util.nvl(rs.getString("dlvid")));
                        pktDtl.put("VNM", util.nvl(rs.getString("vnm")));
                        pktDtl.put("SALEPERSON", util.nvl(rs.getString("sale_name")));
                        pktDtl.put("BYR", util.nvl(rs.getString("byr")));
                        pktDtl.put("COUNTRY", util.nvl(rs.getString("country")));
                        pktDtl.put("DTE_DLV", util.nvl(rs.getString("dlvdte")));
                        pktDtl.put("FNLEXH_RTE", util.nvl(rs.getString("fnl_exh_rte")));
                        pktDtl.put("RTE", util.nvl(rs.getString("rate")));
                        pktDtl.put("RAP_RTE", util.nvl(rs.getString("raprte")));
                        pktDtl.put("SALE RTE", util.nvl(rs.getString("memoQuot")));
                        pktDtl.put("SALE_DIS", util.nvl(rs.getString("slback")));
                        pktDtl.put("AMOUNT", util.nvl(rs.getString("amt")));
                        pktDtl.put("AADAT", util.nvl(rs.getString("aadat")));
                        pktDtl.put("AADAT_COMM", util.nvl(rs.getString("aadatcomm")));
                        pktDtl.put("AADAT1", util.nvl(rs.getString("aadat1")));
                        pktDtl.put("AADAT1_COMM", util.nvl(rs.getString("aadat1comm")));
                        pktDtl.put("BRK1", util.nvl(rs.getString("brk1")));
                        pktDtl.put("BRK1COMM", util.nvl(rs.getString("brk1comm")));
                        pktDtl.put("BRK2", util.nvl(rs.getString("brk2")));
                        pktDtl.put("BRK2COMM", util.nvl(rs.getString("brk3comm")));
                        pktDtl.put("BRK3", util.nvl(rs.getString("brk3")));
                        pktDtl.put("BRK3COMM", util.nvl(rs.getString("brk3comm")));
                        pktDtl.put("TERM", util.nvl(rs.getString("trms")));
                        pktDtl.put("RMK", util.nvl(rs.getString("dlvrmk")));
                        pktDtl.put("NOTE_PERSON", util.nvl(rs.getString("note_person")));
                        pktDtl.put("NET RTE", util.nvl(rs.getString("netRte")));
                        pktDtl.put("NET DIS", util.nvl(rs.getString("netDis")));
                        pktDtl.put("NET AMOUNT", util.nvl(rs.getString("netVlu")));
                        String pktIdn = util.nvl(rs.getString("idn"));
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
        }
        session.setAttribute("pktList", pktDtlList);
        session.setAttribute("itemHdr",itemHdr);
            util.updAccessLog(req,res,"Daily Delivery Report", "pktDtlExcel end");
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
            util.updAccessLog(req,res,"Daily Delivery Report", "pktDtlExcel start");
        GenericInterface genericInt = new GenericImpl();
        ArrayList vwprpLst = genericInt.genericPrprVw(req,res,"DAILY_VW_CD","DAILY_VW_CD");
        ArrayList nmeidnDteDlvLst = (session.getAttribute("nmeidnDteDlvLst") == null)?new ArrayList():(ArrayList)session.getAttribute("nmeidnDteDlvLst");
        int nmeidnDteDlvLstsz=nmeidnDteDlvLst.size();
        String nmeidn="",dte="";
        DailyDlvForm dailyDlvForm = (DailyDlvForm)form;
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
            String salByr ="";
            if(mprpStr.indexOf("SAL_BYR")!=-1)
                salByr=" SAL_BYR , ";
        for(int i=0;i<nmeidnDteDlvLstsz;i++){
        String nmeidnDteVal=util.nvl((String)nmeidnDteDlvLst.get(i));
        String[] nmeidnDteValSplit=nmeidnDteVal.split("_");
        nmeidn=nmeidnDteValSplit[0];  
        dte=nmeidnDteValSplit[1];  
        String dtefrom = " trunc(sysdate) ";
        String dteto = " trunc(sysdate) ";
        if(!nmeidn.equals("") && !dte.equals("")){
        boolean dlvtype=true;
              dtefrom = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
              dteto = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
            String pktDtlSql = " with " +
            " STKDTL as " +
            " ( Select c.sk1,a.idn saleid,c.idn,nvl(nvl(nvl(st.txt,st.dte),st.num),st.prt1) atr , mprp,to_char(b.cts,'99999999990.99') cts,DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), c.vnm) vnm,nvl(c.upr,c.cmp) rate,c.rap_rte raprte,byr.get_nm(nvl(d.emp_idn,0)) sale_name,byr.get_nm(nvl(d.nme_idn,0)) byr,nvl(b.fnl_sal, b.quot) memoQuot,to_char(trunc(b.cts,2) * c.rap_rte, '99999999990.00') rapvlu,\n" +
                 " to_char(trunc(b.cts,2) * Nvl(b.fnl_usd, b.quot), '9999999990.00') amt,byr.get_country(nvl(a.nme_idn,0)) country,to_char((((nvl(b.fnl_usd, b.quot))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') r_dis,decode(a.cur,'USD','',b.quot) quot,TRUNC (b.quot/NVL (a.exh_rte, 1), 2) quot_usd,DF_GET_ADDLSLDIS(a.idn) mgmt_dis,a.fnl_exh_rte,SUBSTR (INITCAP (byr.get_nm (a.mbrk2_idn, 'NM')), 0, 200) sb\n" + 
                 "                , SUBSTR (INITCAP (byr.get_nm (a.mbrk3_idn, 'NM')), 0, 200) sb2\n" + 
                 "                , SUBSTR (INITCAP (byr.get_nm (a.mbrk4_idn, 'NM')), 0, 200) sb3,a.rmk,Byr.Get_Trms(a.Nmerel_Idn) days "+
                 " from stk_dtl st,Mdlv a,Dlv_Pkt_Dtl_V b, mstk c,mnme d\n" + 
                 " where st.mstk_idn=c.idn and a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn and c.idn=b.mstk_idn and a.typ='DLV' and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA'))\n" + 
                 " And B.Stt Not In ('RT','CS','DP','PS','CL') And B.Typ In ('DLV') And C.Stt In( 'MKSD','BRC_MKSD') And D.Nme_Idn= ?\n" + 
                 " and trunc(b.dte)  between "+dtefrom+" and "+dteto +"\n "+
               "and exists (select 1 from rep_prp rp where rp.MDL = 'DAILY_VW_CD' and st.mprp = rp.prp)  And st.Grp = 1) " +
               " Select * from stkDtl PIVOT " +
               " ( max(atr) " +
               " for mprp in ( "+mprpStr+" ) ) order by 1 " ;
        ary = new ArrayList();
        ary.add(nmeidn);
        System.out.println("DLV"+nmeidn);

            outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            dlvtype=false;
            HashMap pktDtl = new HashMap();
            pktDtl.put("VNM", util.nvl(rs.getString("vnm")));
            pktDtl.put("PARTY NAME", util.nvl(rs.getString("byr")));
            pktDtl.put("RATE(RS)", util.nvl(rs.getString("quot")));
            pktDtl.put("RATE($)", util.nvl(rs.getString("quot_usd")));
            pktDtl.put("MGMT_DIS", util.nvl(rs.getString("mgmt_dis")));
            pktDtl.put("FNL_EXH_RTE", util.nvl(rs.getString("fnl_exh_rte")));           
            pktDtl.put("DAYS", util.nvl(rs.getString("days")));
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
        if(dlvtype && info.getBlockLs().equals("")){
            pktDtlSql = " with " +
                    " STKDTL as " +
                    " ( Select c.sk1,a.idn saleid,c.idn,nvl(nvl(nvl(st.txt,st.dte),st.num),st.prt1) atr , mprp,to_char(b.cts,'99999999990.99') cts,DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), c.vnm) vnm,nvl(c.upr,c.cmp) rate,c.rap_rte raprte,byr.get_nm(nvl(d.emp_idn,0)) sale_name,byr.get_nm(nvl(d.nme_idn,0)) byr,nvl(b.fnl_sal, b.quot) memoQuot,to_char(trunc(b.cts,2) * c.rap_rte, '99999999990.00') rapvlu,\n" + 
                          " to_char(trunc(b.cts,2) * Nvl(b.fnl_usd, b.quot), '9999999990.00') amt,byr.get_country(nvl(a.nme_idn,0)) country,to_char((((nvl(b.fnl_usd, b.quot))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') r_dis,decode(a.cur,'USD','',b.quot) quot,TRUNC (b.quot/NVL (a.exh_rte, 1), 2) quot_usd,DF_GET_ADDLSLDIS(a.idn) mgmt_dis,a.fnl_exh_rte,SUBSTR (INITCAP (byr.get_nm (a.mbrk2_idn, 'NM')), 0, 200) sb\n" + 
                          "                , SUBSTR (INITCAP (byr.get_nm (a.mbrk3_idn, 'NM')), 0, 200) sb2\n" + 
                          "                , SUBSTR (INITCAP (byr.get_nm (a.mbrk4_idn, 'NM')), 0, 200) sb3,a.rmk,Byr.Get_Trms(a.Nmerel_Idn) days "+
                          " from stk_dtl st,msal a,Sal_Pkt_Dtl_V b, mstk c,mnme d\n" + 
                          " where st.mstk_idn=c.idn and a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn and c.idn=b.mstk_idn and a.typ='LS'\n" + 
                          " And B.Stt Not In ('RT','CS','DP','PS','CL') And B.Typ In ('SL','DLV') And C.Stt In('MKSD','BRC_MKSD') And D.Nme_Idn= ? and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA'))\n" + 
                          " and trunc(b.dte)  between "+dtefrom+" and "+dteto +"\n "+
                        "and exists (select 1 from rep_prp rp where rp.MDL = 'DAILY_VW_CD' and st.mprp = rp.prp)  And st.Grp = 1) " +
                        " Select * from stkDtl PIVOT " +
                        " ( max(atr) " +
                        " for mprp in ( "+mprpStr+" ) ) order by 1 " ;
                    ary = new ArrayList();
                    ary.add(nmeidn);
                    System.out.println("LS"+nmeidn);
                    rs = db.execSql("pktDtl", pktDtlSql,ary);
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
        }
        session.setAttribute("pktList", pktDtlList);
        session.setAttribute("itemHdr",itemHdr);
            util.updAccessLog(req,res,"Daily Delivery Report", "pktDtlExcel end");
        return am.findForward("pktDtl");  
        }
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
            util.updAccessLog(req,res,"Daily Delivery Report", "pktDtlExcel start");
        GenericInterface genericInt = new GenericImpl();
        ArrayList nmeidnDteDlvLst = (session.getAttribute("nmeidnDteDlvLst") == null)?new ArrayList():(ArrayList)session.getAttribute("nmeidnDteDlvLst");
        int nmeidnDteDlvLstsz=nmeidnDteDlvLst.size();
        String nmeidn="",dte="";
        DailyDlvForm dailyDlvForm = (DailyDlvForm)form;
        ArrayList ary = new ArrayList();
        ArrayList vnmLst = new ArrayList();
        ResultSet rs=null;
        for(int i=0;i<nmeidnDteDlvLstsz;i++){
        String nmeidnDteVal=util.nvl((String)nmeidnDteDlvLst.get(i));
        String[] nmeidnDteValSplit=nmeidnDteVal.split("_");
        nmeidn=nmeidnDteValSplit[0];  
        dte=nmeidnDteValSplit[1];  
        String dtefrom = " trunc(sysdate) ";
        String dteto = " trunc(sysdate) ";
        if(!nmeidn.equals("") && !dte.equals("")){
        boolean dlvtype=true;
              dtefrom = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
              dteto = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
         String pktDtlSql = "select c.vnm\n" + 
         "From Mdlv A , Dlv_Pkt_Dtl_V B,Mstk C\n" + 
         "Where A.Idn = B.Idn And B.Mstk_Idn=C.Idn and c.stt in('MKSD','BRC_MKSD') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA'))\n" + 
         "And Trunc(B.Dte) Between "+dtefrom+" And "+dteto+"\n" + 
         "And B.Nme_Idn=? And B.Stt Not In ('RT','CS','DP','PS','CL') And B.Typ In ('DLV')" ;
        ary = new ArrayList();
        ary.add(nmeidn);
        System.out.println("DLV"+nmeidn);
  
            ArrayList outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            dlvtype=false;
            vnmLst.add(util.nvl(rs.getString("vnm")));
        }
            rs.close(); pst.close();
        if(dlvtype && info.getBlockLs().equals("")){
            pktDtlSql = "select c.vnm\n" + 
            "From msal A , Sal_pkt_dtl_v B,Mstk C\n" + 
            "Where A.Idn = B.Idn And B.Mstk_Idn=C.Idn  and c.stt in('MKSD','BRC_MKSD') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA'))\n" + 
            "And Trunc(B.Dte) Between "+dtefrom+" And "+dteto+"\n" +  
            "And B.Nme_Idn=? And B.Stt Not In ('RT','CS','DP','PS','CL') And a.Typ In ('LS')" ;
                    ary = new ArrayList();
                    ary.add(nmeidn);
                    System.out.println("LS"+nmeidn);

             outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
                    while(rs.next()){
                        vnmLst.add(util.nvl(rs.getString("vnm")));
                    }
            rs.close(); pst.close();
        }
            
        }
        }
        req.setAttribute("pktList",vnmLst);
        req.setAttribute("fromDaily","Y");
        util.updAccessLog(req,res,"Daily Delivery Report", "pktDtlExcel end");
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
            util.updAccessLog(req,res,"Daily Delivery Report", "pktDtlExcel start");
        GenericInterface genericInt = new GenericImpl();
        ArrayList nmeidnDteDlvLst = (session.getAttribute("nmeidnDteDlvLst") == null)?new ArrayList():(ArrayList)session.getAttribute("nmeidnDteDlvLst");
        int nmeidnDteDlvLstsz=nmeidnDteDlvLst.size();
        String nmeidn="",dte="";
        DailyDlvForm dailyDlvForm = (DailyDlvForm)form;
        ArrayList ary = new ArrayList();
        ArrayList vnmLst = new ArrayList();
        HashMap priMap = new HashMap();
        ResultSet rs=null;
        for(int i=0;i<nmeidnDteDlvLstsz;i++){
        String nmeidnDteVal=util.nvl((String)nmeidnDteDlvLst.get(i));
        String[] nmeidnDteValSplit=nmeidnDteVal.split("_");
        nmeidn=nmeidnDteValSplit[0];  
        dte=nmeidnDteValSplit[1];  
        String dtefrom = " trunc(sysdate) ";
        String dteto = " trunc(sysdate) ";
        if(!nmeidn.equals("") && !dte.equals("")){
        boolean dlvtype=true;
              dtefrom = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
              dteto = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
         String pktDtlSql = "select c.vnm,c.idn,b.fnl_usd\n" + 
         "From Mdlv A , Dlv_Pkt_Dtl_V B,Mstk C\n" + 
         "Where A.Idn = B.Idn And B.Mstk_Idn=C.Idn and c.stt in('MKSD','BRC_MKSD') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA'))\n" + 
         "And Trunc(B.Dte) Between "+dtefrom+" And "+dteto+"\n" + 
         "And B.Nme_Idn=? And B.Stt Not In ('RT','CS','DP','PS','CL') And B.Typ In ('DLV')" ;
        ary = new ArrayList();
        ary.add(nmeidn);
        System.out.println("DLV"+nmeidn);

            ArrayList outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            dlvtype=false;
            vnmLst.add(util.nvl(rs.getString("vnm")));
            priMap.put(util.nvl(rs.getString("idn")), util.nvl(rs.getString("fnl_usd")));
        }
            rs.close(); pst.close();
        if(dlvtype && info.getBlockLs().equals("")){
            pktDtlSql = "select c.vnm,c.idn,b.fnl_usd\n" + 
            "From msal A , Sal_pkt_dtl_v B,Mstk C\n" + 
            "Where A.Idn = B.Idn And B.Mstk_Idn=C.Idn  and c.stt in('MKSD','BRC_MKSD') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA'))\n" + 
            "And Trunc(B.Dte) Between "+dtefrom+" And "+dteto+"\n" +  
            "And B.Nme_Idn=? And B.Stt Not In ('RT','CS','DP','PS','CL') And a.Typ In ('LS')" ;
                    ary = new ArrayList();
                    ary.add(nmeidn);
                    System.out.println("LS"+nmeidn);

             outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
                    while(rs.next()){
                        vnmLst.add(util.nvl(rs.getString("vnm")));
                        priMap.put(util.nvl(rs.getString("idn")), util.nvl(rs.getString("fnl_usd")));
                    }
            rs.close(); pst.close();
        }
            
        }
        }
        req.setAttribute("pktList",vnmLst);
        session.setAttribute("PriMap", priMap);
        req.setAttribute("fromDaily","Y");
        util.updAccessLog(req,res,"Daily Delivery Report", "pktDtlExcel end");
        return am.findForward("createRuchiXL");  
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
        util.updAccessLog(req,res,"Daily Delivery Report", "pktDtlExcel start");
        GenericInterface genericInt = new GenericImpl();
        DailyDlvForm dailyDlvForm = (DailyDlvForm)form;
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "SAPPHIRE.xls";
        res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        res.setContentType(CONTENT_TYPE);
        String nmeId="142812";
        ArrayList vwPrpLst =new ArrayList();
            ArrayList nmeidnDteDlvLst = (session.getAttribute("nmeidnDteDlvLst") == null)?new ArrayList():(ArrayList)session.getAttribute("nmeidnDteDlvLst");
            int nmeidnDteDlvLstsz=nmeidnDteDlvLst.size();
            String nmeidn="",dte="";
        ArrayList ary = new ArrayList();
        db.execUpd("delete from gt", "delete from gt_srch_rslt", new ArrayList());
            for(int i=0;i<nmeidnDteDlvLstsz;i++){
            String nmeidnDteVal=util.nvl((String)nmeidnDteDlvLst.get(i));
            String[] nmeidnDteValSplit=nmeidnDteVal.split("_");
            nmeidn=nmeidnDteValSplit[0];  
            dte=nmeidnDteValSplit[1];  
        String dtefrom = " trunc(sysdate) ";
        String dteto = " trunc(sysdate) ";
        if(!nmeidn.equals("") && !dte.equals("")){
              dtefrom = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
              dteto = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
         String pktDtlSql = "Insert into gt_srch_rslt (rln_idn, srch_id, pkt_ty, stk_idn, vnm, qty, cts, pkt_dte, stt, cmp, rap_rte, cert_lab, cert_no, flg, sk1, quot, rap_dis, CERTIMG, DIAMONDIMG, JEWIMG, SRAYIMG, AGSIMG, MRAYIMG, RINGIMG, LIGHTIMG, REFIMG, VIDEOS, VIDEO_3D)\n" + 
         "select a.nmerel_idn,a.idn,c.pkt_ty,c.idn,c.vnm,c.qty,b.cts,c.dte,c.stt,b.fnl_usd,c.rap_rte,c.cert_lab,c.cert_no,'Z' flg,c.sk1,b.fnl_usd,decode(c.rap_rte, 1, null, trunc(100 - (b.fnl_usd/c.rap_rte*100), 2)),CERTIMG, DIAMONDIMG, JEWIMG, SRAYIMG, AGSIMG, MRAYIMG, RINGIMG, LIGHTIMG, REFIMG, VIDEOS, VIDEO_3D\n" + 
         "From Mdlv A , Dlv_Pkt_Dtl_V B,Mstk C,Mnme d\n" + 
         "Where A.Idn = B.Idn And B.Mstk_Idn=C.Idn and d.nme_idn=a.nme_idn  and c.stt in('MKSD','BRC_MKSD') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA'))\n" + 
         "And Trunc(B.Dte) Between "+dtefrom+" And "+dteto+"\n" +
         "And B.Nme_Idn=? And B.Stt Not In ('RT','CS','DP','PS','CL') And B.Typ In ('DLV')";
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

            ArrayList outLst1 = db.execSqlLst(" Vw Lst ",prpqry , ary);
            PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
            ResultSet rs1 = (ResultSet)outLst1.get(1);
        while (rs1.next()) {
        prpDsc.put(util.nvl(rs1.getString("mprp")), util.nvl(rs1.getString("dsc")));
        }
        rs1.close(); pst1.close();
    
        ary=new ArrayList();
        ary.add(nmeId);
        prpqry="select idx prp, rank() over (order by srt) rnk from prp_map_web  where name_id = ? and mprp = 'DFCOL'";

            outLst1 = db.execSqlLst(" Vw Lst ",prpqry , ary);
            pst1 = (PreparedStatement)outLst1.get(0);
            rs1 = (ResultSet)outLst1.get(1);
        while (rs1.next()) {
        vwPrpLst.add(rs1.getString("prp"));
        }
        rs1.close();
            
        ArrayList pktList=stockList(req,vwPrpLst,nmeId);
        HSSFWorkbook hwb = xlUtil.getSapphireXl(vwPrpLst, pktList,prpDsc);
            hwb.write(out);
            out.flush();
            out.close();
            util.updAccessLog(req,res,"Daily Delivery Report", "pktDtlExcel end");
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
            util.updAccessLog(req,res,"Daily Delivery Report", "pktDtl start");
        String salId = util.nvl(req.getParameter("dlvId"));
        String typ = util.nvl(req.getParameter("typ"));
        ArrayList pktDtlList = new ArrayList();
        ArrayList itemHdr = new ArrayList();
        itemHdr.add("DLVID");
                itemHdr.add("VNM");
                itemHdr.add("SALEPERSON");
                itemHdr.add("BYR");
                itemHdr.add("COUNTRY");
        ArrayList ary = new ArrayList();
        ArrayList prpDspBlocked = info.getPageblockList();
        if(!salId.equals("")){
            boolean dlvtype=true;
            String pktDtlSql ="";
            ResultSet rs=null;
            if(typ.equals("DLV")){
        pktDtlSql=" Select a.idn dlvid,C.Idn,to_char(b.cts,'99999999990.99') cts,DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), c.vnm) vnm,Nvl(C.Upr,C.Cmp) rte,C.Rap_Rte,Byr.Get_Nm(Nvl(d.Emp_Idn,0)) Sale_Name,byr.get_nm(nvl(d.nme_idn,0)) Byr,nvl(b.fnl_sal, b.quot) memoQuot,to_char(trunc(b.cts,2) * c.rap_rte, '99999999990.00') rapvlu, \n" + 
                          " to_char(trunc(b.cts,2) * Nvl(b.fnl_usd, b.quot), '9999999990.00') amt,byr.get_country(nvl(a.nme_idn,0)) country,to_char((((nvl(b.fnl_usd, b.quot))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') r_dis , "+
                          " to_char(trunc(b.cts,2) * Nvl(b.fnl_bse, b.quot), '9999999990.00') netVlu , b.fnl_bse netRte,\n" + 
                          " to_char((((nvl(b.fnl_bse, b.quot))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') netDis,c.pkt_ty"+
                          " From Mdlv A , Dlv_Pkt_Dtl_V B,Mstk C,Mnme d\n" + 
                          "Where A.Idn = B.Idn And B.Mstk_Idn=C.Idn and d.nme_idn=a.nme_idn and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) \n" + 
                          " and c.stt in('MKSD','BRC_MKSD') And B.Idn=? And B.Stt Not In ('RT','CS','DP','PS','CL') And B.Typ In ('DLV')\n" + 
                          "order by a.idn ,Byr.Get_Nm(Nvl(d.Emp_Idn,0)),c.vnm ";
        ary = new ArrayList();
        ary.add(salId);

             ArrayList outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
             PreparedStatement pst = (PreparedStatement)outLst.get(0);
              rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            dlvtype=false;
            HashMap pktDtl = new HashMap();
            pktDtl.put("DLVID", util.nvl(rs.getString("dlvid")));
            pktDtl.put("VNM", util.nvl(rs.getString("vnm")));
            pktDtl.put("SALEPERSON", util.nvl(rs.getString("sale_name")));
            pktDtl.put("BYR", util.nvl(rs.getString("byr")));
            pktDtl.put("COUNTRY", util.nvl(rs.getString("country")));
            pktDtl.put("RTE", util.nvl(rs.getString("rte")));
            pktDtl.put("RAP_RTE", util.nvl(rs.getString("rap_rte")));
            pktDtl.put("SALE RTE", util.nvl(rs.getString("memoQuot")));
            pktDtl.put("AMOUNT", util.nvl(rs.getString("amt")));
            pktDtl.put("NET RTE", util.nvl(rs.getString("netRte")));
            pktDtl.put("NET DIS", util.nvl(rs.getString("netDis")));
            pktDtl.put("NET AMOUNT", util.nvl(rs.getString("netVlu")));
            String pktIdn = util.nvl(rs.getString("idn"));
            String pkt_ty=util.nvl(rs.getString("pkt_ty"));
            String mdl="DAILY_VW";
            if(pkt_ty.equals("MIX"))
            mdl="DAILY_MIX_VW";
            String getPktPrp =
                " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
                + " from stk_dtl a, mprp b, rep_prp c "
                + " where a.mprp = b.prp and b.prp = c.prp and c.mdl = ? and a.mstk_idn = ? and a.grp=1 and  c.flg <> 'N' "
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
                    itemHdr.add("NET RTE");
                    itemHdr.add("NET DIS");
                    itemHdr.add("NET AMOUNT");
                }
                    if(lPrp.equals("RAP_RTE")){
                           itemHdr.add("RAPVLU");
                    }
                }
            }
            rs1.close();pst1.close();
            pktDtl.put("RTE",util.nvl(rs.getString("rte")));
            pktDtl.put("RAP_DIS",util.nvl(rs.getString("r_dis")));
            pktDtl.put("CRTWT",util.nvl(rs.getString("cts")));
            pktDtl.put("RAP_RTE",util.nvl(rs.getString("rap_rte")));
            pktDtl.put("RAPVLU",util.nvl(rs.getString("rapvlu")));
            pktDtlList.add(pktDtl);
        } 
            rs.close(); pst.close();
         }
        if(dlvtype && info.getBlockLs().equals("")){
            pktDtlSql =
                                      " Select a.idn dlvid,C.Idn,to_char(b.cts,'99999999990.99') cts,DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), c.vnm) vnm,Nvl(C.Upr,C.Cmp) rte,C.Rap_Rte,Byr.Get_Nm(Nvl(d.Emp_Idn,0)) Sale_Name,byr.get_nm(nvl(d.nme_idn,0)) Byr,nvl(b.fnl_sal, b.quot) memoQuot,to_char(trunc(b.cts,2) * c.rap_rte, '99999999990.00') rapvlu, \n" + 
                                      " to_char(trunc(b.cts,2) * Nvl(b.fnl_usd, b.quot), '9999999990.00') amt,byr.get_country(nvl(a.nme_idn,0)) country,to_char((((nvl(b.fnl_usd, b.quot))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') r_dis "+
                                      "From msal A , Sal_pkt_dtl_v B,Mstk C,Mnme d\n" + 
                                      "Where A.Idn = B.Idn And B.Mstk_Idn=C.Idn and d.nme_idn=a.nme_idn and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA'))\n" + 
                                      " and c.stt in('MKSD','BRC_MKSD') And B.Idn=? And B.Stt Not In ('RT','CS','DP','PS','CL') And a.Typ In ('LS')\n" + 
                                      "order by c.sk1 ";
                    ary = new ArrayList();
                    ary.add(salId);

            ArrayList outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
                    while(rs.next()){
                        dlvtype=false;
                        HashMap pktDtl = new HashMap();
                        pktDtl.put("DLVID", util.nvl(rs.getString("dlvid")));
                        pktDtl.put("VNM", util.nvl(rs.getString("vnm")));
                        pktDtl.put("SALEPERSON", util.nvl(rs.getString("sale_name")));
                        pktDtl.put("BYR", util.nvl(rs.getString("byr")));
                        pktDtl.put("COUNTRY", util.nvl(rs.getString("country")));
                        pktDtl.put("RTE", util.nvl(rs.getString("rte")));
                        pktDtl.put("RAP_RTE", util.nvl(rs.getString("rap_rte")));
                        pktDtl.put("SALE RTE", util.nvl(rs.getString("memoQuot")));
                        pktDtl.put("AMOUNT", util.nvl(rs.getString("amt")));
                        String pktIdn = util.nvl(rs.getString("idn"));
                        String getPktPrp =
                            " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
                            + " from stk_dtl a, mprp b, rep_prp c "
                            + " where a.mprp = b.prp and b.prp = c.prp and c.mdl = 'DAILY_VW' and a.mstk_idn = ? and a.grp=1 and  c.flg <> 'N' "
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
                        rs1.close();pst1.close();
                        pktDtl.put("RTE",util.nvl(rs.getString("rte")));
                        pktDtl.put("RAP_DIS",util.nvl(rs.getString("r_dis")));
                        pktDtl.put("CRTWT",util.nvl(rs.getString("cts")));
                        pktDtl.put("RAP_RTE",util.nvl(rs.getString("rap_rte")));
                        pktDtl.put("RAPVLU",util.nvl(rs.getString("rapvlu")));
                        pktDtlList.add(pktDtl);
                    }
            rs.close(); pst.close();
        }
        }
       session.setAttribute("pktList", pktDtlList);
       session.setAttribute("itemHdr",itemHdr);
            util.updAccessLog(req,res,"Daily Delivery Report", "pktDtl end");
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
            util.updAccessLog(req,res,"Daily Delivery Report", "pktDtlExcelXLFNL start");
            GenericInterface genericInt = new GenericImpl();
        DailyDlvForm dailyDlvForm = (DailyDlvForm)form;
        ArrayList vwprpLst = genericInt.genericPrprVw(req,res,"DAILY_VW_XLHK","DAILY_VW_XLHK");
            ArrayList nmeidnDteDlvLst = (session.getAttribute("nmeidnDteDlvLst") == null)?new ArrayList():(ArrayList)session.getAttribute("nmeidnDteDlvLst");
            int nmeidnDteDlvLstsz=nmeidnDteDlvLst.size();
            String nmeidn="",dte="";
        ArrayList pktDtlList = new ArrayList();
        ArrayList itemHdr = new ArrayList();
        ArrayList ary = new ArrayList();
        ArrayList prpDspBlocked = info.getPageblockList();
         itemHdr.add("BYR");
        
            for(int i=0;i<vwprpLst.size();i++){
                String prp=util.nvl((String)vwprpLst.get(i));
                if(prpDspBlocked.contains(prp)){
                }else if(!itemHdr.contains(prp)){
                itemHdr.add(prp);
                if(prp.equals("BD_CL")){
                  itemHdr.add("SALEID");
                }
               
                }  
            }
        itemHdr.add("VALUE"); 
       
          itemHdr.add("NET_RAP_BACK");
          itemHdr.add("NET_STONE_AMTPER_CTS");
          itemHdr.add("NET_STONE_AMT");
          itemHdr.add("DATE");
            for(int i=0;i<nmeidnDteDlvLstsz;i++){
            String nmeidnDteVal=util.nvl((String)nmeidnDteDlvLst.get(i));
            String[] nmeidnDteValSplit=nmeidnDteVal.split("_");
            nmeidn=nmeidnDteValSplit[0];  
            dte=nmeidnDteValSplit[1];  
        String dtefrom = " trunc(sysdate) ";
        String dteto = " trunc(sysdate) ";
        if(!nmeidn.equals("") && !dte.equals("")){
              dtefrom = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
              dteto = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
         String pktDtlSql =
              " Select a.idn dlvid,c.tfl3,c.cert_no,C.Idn,1 qty ,to_char(b.cts,'99999999990.99') cts,DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), c.vnm) vnm,Nvl(C.Upr,C.Cmp) rte,C.Rap_Rte,Byr.Get_Nm(Nvl(d.Emp_Idn,0)) Sale_Name,byr.get_nm(nvl(d.nme_idn,0)) Byr,nvl(b.fnl_sal, b.quot) memoQuot,to_char(trunc(b.cts,2) * c.rap_rte, '99999999990.00') rapvlu, \n" + 
              " to_char(trunc(b.cts,2) * Nvl(b.fnl_usd, b.quot), '9999999990.00') amt,to_char((((nvl(b.fnl_usd, b.quot))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') r_dis , "+
              " to_char(a.dte,'dd-mm-yyyy') dlvDte , to_char(f.dte,'dd-mm-yyyy') salDte ,f.idn salId , f.typ ,f.memo_id , b.stt  dlvStt,b.trm trms,f.fnl_xrt,b.STONEWISE_DIS,b.NET_DISC_AMT ,b.NET_STONE_AMT ,b.NET_STONE_AMTPER_CTS,b.NET_RAP_BACK "+
              "From Mdlv A , Dlv_Pkt_Dtl_V B,Mstk C,Mnme d,nmerel e, msal f\n" + 
              "Where A.Idn = B.Idn And B.Mstk_Idn=C.Idn and d.nme_idn=a.nme_idn and c.stt in('MKSD','BRC_MKSD') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA'))\n" + 
              "And Trunc(B.Dte) Between "+dtefrom+" And "+dteto+"\n" + 
              "And B.Nme_Idn=? And B.Stt Not In ('RT','CS','DP','PS','CL') And B.Typ In ('DLV') and a.nmerel_idn=e.nmerel_idn and a.sal_idn=f.idn\n" + 
              "order by c.sk1 ";
        ary = new ArrayList();
        ary.add(nmeidn);

            ArrayList outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            HashMap pktDtl = new HashMap();
            pktDtl.put("DLVID", util.nvl(rs.getString("dlvid")));
          pktDtl.put("DATE", util.nvl(rs.getString("dlvDte")));
          pktDtl.put("DLVSTT", util.nvl(rs.getString("dlvStt")));
          pktDtl.put("SALEID", util.nvl(rs.getString("salId")));
          pktDtl.put("SALE DATE", util.nvl(rs.getString("salDte")));
          pktDtl.put("MEMOID", util.nvl(rs.getString("memo_id")));
          pktDtl.put("TYP", util.nvl(rs.getString("typ")));
          pktDtl.put("BYR", util.nvl(rs.getString("byr")));
          pktDtl.put("TERM", util.nvl(rs.getString("trms")));
          pktDtl.put("TFL3", util.nvl(rs.getString("tfl3")));
          pktDtl.put("RTE", util.nvl(rs.getString("rte")));
          pktDtl.put("SALE RTE", util.nvl(rs.getString("memoQuot")));
          pktDtl.put("AMOUNT", util.nvl(rs.getString("amt")));
          pktDtl.put("RAP_RTE", util.nvl(rs.getString("rap_rte")));
         
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
            rs1.close();
        }
            rs.close(); pst.close();
            
        }
        }
        session.setAttribute("pktList", pktDtlList);
        session.setAttribute("itemHdr",itemHdr);
            util.updAccessLog(req,res,"Daily Delivery Report", "pktDtlExcelXLFNL end");
        return am.findForward("pktDtl");  
        }
    }

  public ActionForward pktDtlExcelSummery(ActionMapping am, ActionForm form,
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
        util.updAccessLog(req,res,"Daily Delivery Report", "pktDtlExcelSummery start");
      DailyDlvForm dailyDlvForm = (DailyDlvForm)form;
     
        ArrayList nmeidnDteDlvLst = (session.getAttribute("nmeidnDteDlvLst") == null)?new ArrayList():(ArrayList)session.getAttribute("nmeidnDteDlvLst");
        int nmeidnDteDlvLstsz=nmeidnDteDlvLst.size();
        String nmeidn="",dte="";
      ArrayList pktDtlList = new ArrayList();
      ArrayList itemHdr = new ArrayList();
      itemHdr.add("APPROVEDTE");
      itemHdr.add("DATE"); 
      itemHdr.add("LOCATION"); 
      itemHdr.add("USERNAME");
      itemHdr.add("IDN");
      itemHdr.add("MEMO ID");
      itemHdr.add("BYR");
      itemHdr.add("TERM");
      itemHdr.add("QTY");
      itemHdr.add("CTS");
      itemHdr.add("FNL_SAL");
      itemHdr.add("AVG_RAP_DIS");
      itemHdr.add("AVG_AMT");
      itemHdr.add("EXH_RTE");
      itemHdr.add("MB");
      itemHdr.add("MB COMM");
      itemHdr.add("SB");
      itemHdr.add("SB COMM");
      itemHdr.add("REMARK");
      itemHdr.add("NET DIS");
      itemHdr.add("NET AMT");
      itemHdr.add("FNL GRAND TOTAL");
        for(int i=0;i<nmeidnDteDlvLstsz;i++){
        String nmeidnDteVal=util.nvl((String)nmeidnDteDlvLst.get(i));
        String[] nmeidnDteValSplit=nmeidnDteVal.split("_");
        nmeidn=nmeidnDteValSplit[0];  
        dte=nmeidnDteValSplit[1];  
      String dtefrom = " trunc(sysdate) ";
      String dteto = " trunc(sysdate) ";
      if(!nmeidn.equals("") && !dte.equals("")){
            dtefrom = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
            dteto = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
        String pktDtlSql ="select  To_Char(c.Dte,'dd-mm-yyyy') saleDte ,  To_Char(m.Dte,'dd-mm-yyyy') memoDte , m.idn memoIdn ,nt.txt Location,a.aud_created_by UserName, c.idn saleId ,b.byr,b.trm,\n" + 
        "Sum(B.Qty) Qty,To_Char(Sum(Trunc(B.Cts,2)),'999990.99') Cts,Trunc(Sum(Nvl(B.Fnl_Sal, B.Quot)*Trunc(b.Cts,2)),0) fnl_sal,\n" + 
        "To_Char((Sum((Nvl(B.Fnl_Sal, B.Quot))*Trunc(B.Cts,2))/Sum(Trunc(B.Cts, 2))),'999,9999999990.00') avg_amt,a.exh_rte,Byr.Get_Nm(A.Mbrk1_Idn) Mb,nvl(a.brk1_comm,0) mbcomm,\n" + 
        "Byr.Get_Nm(A.Mbrk2_Idn) Sub, Nvl(A.Brk2_Comm,0) Subcomm ,\n" + 
        "To_Char(Trunc(((Sum((Nvl(B.Fnl_Sal, B.Quot))*Trunc(B.Cts,2))/Sum(Trunc(B.Cts, 2)))/(Sum(f.Rap_Rte*Trunc(B.Cts,2))/Sum(Trunc(B.Cts, 2)))*100) - 100, 2) ,'999999990.00') R_Dis,\n" + 
        "Sum(B.Net_Stone_Amt) Net_Amt,\n" + 
        "Trunc(100-((Sum(B.Net_Stone_Amt)/To_Char(Sum(Trunc(B.Cts,2)*(Nvl(B.Fnl_Usd, B.Quot))),'9999999999990.00'))*100),2) Net_Dis,\n" + 
        "Trunc(Sum(Nvl(B.Fnl_Sal, B.Quot)*Trunc(B.Cts,2))/Sum(Trunc(B.Cts, 2)),0)-Sum(B.Net_Stone_Amt) Fnlgrand , c.rmk\n" + 
        "from mdlv a , dlv_Pkt_Dtl_V b , msal c , mnme e , df_users df , nme_dtl nt , mstk f , mjan m\n" + 
        "where a.idn = b.idn and b.sal_idn = c.idn and a.nme_idn = e.nme_idn and df.usr = a.aud_created_by and  df.nme_idn = nt.nme_idn and nt.mprp='OFFLOC' and b.mstk_idn=f.idn\n" + 
        "And Trunc(a.Dte) Between "+dtefrom+" And "+dteto+"\n" + 
        " and a.nme_idn=? and c.memo_id = m.idn and c.stt in('MKSD','BRC_MKSD') \n" + 
        "and  B.Stt Not In ('RT','CS','DP','PS','CL') And B.Typ In ('DLV') group by To_Char(c.Dte,'dd-mm-yyyy'), nt.txt, a.aud_created_by, c.idn, b.byr, b.trm, a.exh_rte, Byr.Get_Nm(A.Mbrk1_Idn), nvl(a.brk1_comm,0), Byr.Get_Nm(A.Mbrk2_Idn), Nvl(A.Brk2_Comm,0), c.rmk , m.idn,m.dte  order by b.byr \n";
        ArrayList ary = new ArrayList();
        ary.add(nmeidn);

          ArrayList outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
           HashMap pktDtl = new HashMap();
          pktDtl.put("APPROVEDTE",util.nvl(rs.getString("memoDte")));
          pktDtl.put("DATE",util.nvl(rs.getString("saleDte")));
          pktDtl.put("LOCATION",util.nvl(rs.getString("Location")));
          pktDtl.put("USERNAME",util.nvl(rs.getString("UserName")));
          pktDtl.put("IDN",util.nvl(rs.getString("saleId")));
          pktDtl.put("MEMO ID",util.nvl(rs.getString("memoIdn")));
          pktDtl.put("BYR",util.nvl(rs.getString("byr")));
          pktDtl.put("TERM",util.nvl(rs.getString("trm")));
          pktDtl.put("QTY",util.nvl(rs.getString("qty")));
          pktDtl.put("CTS",util.nvl(rs.getString("cts")));
          pktDtl.put("FNL_SAL",util.nvl(rs.getString("fnl_sal")));
          pktDtl.put("AVG_RAP_DIS",util.nvl(rs.getString("R_Dis")));
          pktDtl.put("AVG_AMT",util.nvl(rs.getString("avg_amt")));
          pktDtl.put("EXH_RTE",util.nvl(rs.getString("exh_rte")));
          pktDtl.put("MB",util.nvl(rs.getString("Mb")));
          pktDtl.put("MB COMM",util.nvl(rs.getString("mbcomm")));
          pktDtl.put("SB",util.nvl(rs.getString("Sub")));
          pktDtl.put("SB COMM",util.nvl(rs.getString("Subcomm")));
          pktDtl.put("REMARK",util.nvl(rs.getString("rmk")));
          pktDtl.put("NET DIS",util.nvl(rs.getString("Net_Dis")));
          pktDtl.put("NET AMT",util.nvl(rs.getString("Net_Amt")));
          pktDtl.put("FNL GRAND TOTAL",util.nvl(rs.getString("Fnlgrand")));
          
          pktDtlList.add(pktDtl);  
        }
          rs.close(); pst.close();
            
      }
      }
      session.setAttribute("pktList", pktDtlList);
      session.setAttribute("itemHdr",itemHdr);
    }
          util.updAccessLog(req,res,"Daily Delivery Report", "pktDtlExcelSummery end");
        
        return am.findForward("pktDtl");
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
          util.updAccessLog(req,res,"Daily Delivery Report", "pktDtlExcelXLHK start");
          GenericInterface genericInt = new GenericImpl();
      ArrayList vwprpLst = genericInt.genericPrprVw(req,res,"DAILY_VW_XLHK","DAILY_VW_XLHK");
          ArrayList nmeidnDteDlvLst = (session.getAttribute("nmeidnDteDlvLst") == null)?new ArrayList():(ArrayList)session.getAttribute("nmeidnDteDlvLst");
          int nmeidnDteDlvLstsz=nmeidnDteDlvLst.size();
          String nmeidn="",dte="";
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
             
              }
                  if(prp.equals("RAP_RTE")){
                         itemHdr.add("RAPVLU");
                  }
              }  
          }
        itemHdr.add("SALE RTE");
        itemHdr.add("AMOUNT");
         itemHdr.add("SALEID");
       itemHdr.add("MEMOID");
        itemHdr.add("BYR");
         itemHdr.add("MB");
       itemHdr.add("MB COMM");
        itemHdr.add("SB");
        itemHdr.add("SB COMM");
        
      itemHdr.add("CERT_NO");
          
          for(int i=0;i<nmeidnDteDlvLstsz;i++){
          String nmeidnDteVal=util.nvl((String)nmeidnDteDlvLst.get(i));
          String[] nmeidnDteValSplit=nmeidnDteVal.split("_");
          nmeidn=nmeidnDteValSplit[0];  
          dte=nmeidnDteValSplit[1];  
      String dtefrom = " trunc(sysdate) ";
      String dteto = " trunc(sysdate) ";
      if(!nmeidn.equals("") && !dte.equals("")){
            dtefrom = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
            dteto = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
       String pktDtlSql ="  Select f.idn saleId,c.tfl3,c.cert_no,C.Idn,1 qty ,to_char(b.cts,'99999999990.99') cts,DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), c.vnm) vnm,Nvl(C.Upr,C.Cmp) rte,C.Rap_Rte,Byr.Get_Nm(Nvl(d.Emp_Idn,0)) Sale_Name,byr.get_nm(nvl(d.nme_idn,0)) Byr,nvl(b.fnl_sal, b.quot) memoQuot,to_char(trunc(b.cts,2) * c.rap_rte, '99999999990.00') rapvlu, \n" + 
       " to_char(trunc(b.cts,2) * Nvl(b.fnl_usd, b.quot), '9999999990.00') amt,to_char((((nvl(b.fnl_usd, b.quot))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') r_dis ,  \n" + 
       " Byr.Get_Nm(A.Mbrk1_Idn) Mb, Nvl(A.Brk1_Comm,0) Mbcomm, Byr.Get_Nm(A.Mbrk2_Idn) Sub, Nvl(A.Brk2_Comm,0) Subcomm ,To_Char(f.Dte,'dd-mm-yyyy') saldte , To_Char(g.Dte,'dd-mm-yyyy') appdte ,g.Idn memoid , g.Typ ,\n" + 
       " to_char(a.dte,'dd-mm-yyyy') dlvDte , to_char(f.dte,'dd-mm-yyyy') salDte ,f.idn salId , f.typ ,f.memo_id ,f.stt slStt , b.stt  dlvStt,b.trm trms,f.fnl_xrt,b.STONEWISE_DIS,b.NET_DISC_AMT ,b.NET_STONE_AMT ,b.NET_STONE_AMTPER_CTS,b.NET_RAP_BACK\n" + 
       " From Mdlv A , Dlv_Pkt_Dtl_V B,Mstk C,Mnme d,nmerel e, msal f , mjan g "+
        " where A.Idn = B.Idn And B.Mstk_Idn=C.Idn and d.nme_idn=a.nme_idn and B.Nme_Idn=?  and c.stt in('MKSD','BRC_MKSD') And B.Stt Not In ('RT','CS','DP','PS','CL') And B.Typ In ('DLV') and a.nmerel_idn=e.nmerel_idn and a.sal_idn=f.idn and f.memo_id=g.idn "+
        "         And Trunc(a.Dte) Between "+dtefrom+" And "+dteto+"  order by c.sk1 ";
      ary = new ArrayList();
      ary.add(nmeidn);

          ArrayList outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
      while(rs.next()){
          HashMap pktDtl = new HashMap();
          pktDtl.put("SALEID", util.nvl(rs.getString("saleId")));
        pktDtl.put("DATE", util.nvl(rs.getString("saldte")));
        pktDtl.put("SALSTT", util.nvl(rs.getString("slStt")));
        pktDtl.put("MEMOID", util.nvl(rs.getString("memoid")));
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

          ArrayList outLst1= db.execSqlLst(" Pkt Prp", getPktPrp, ary);
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
          rs1.close();
      }
          rs.close(); pst.close();
          
      }
      }
      session.setAttribute("pktList", pktDtlList);
      session.setAttribute("itemHdr",itemHdr);
          util.updAccessLog(req,res,"Daily Delivery Report", "pktDtlExcelXLHK end");
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
          util.updAccessLog(req,res,"Daily Delivery Report", "pktDtlExcelXL start");
          GenericInterface genericInt = new GenericImpl();
      ArrayList vwprpLst = genericInt.genericPrprVw(req,res,"DAILY_VW_XL","DAILY_VW_XL");
          ArrayList nmeidnDteDlvLst = (session.getAttribute("nmeidnDteDlvLst") == null)?new ArrayList():(ArrayList)session.getAttribute("nmeidnDteDlvLst");
          int nmeidnDteDlvLstsz=nmeidnDteDlvLst.size();
          String nmeidn="",dte="";
      ArrayList pktDtlList = new ArrayList();
      ArrayList itemHdr = new ArrayList();
      ArrayList ary = new ArrayList();
      ArrayList prpDspBlocked = info.getPageblockList();
 
        itemHdr.add("APPROVE DATE");
        itemHdr.add("IDN");
        itemHdr.add("VNM");
        itemHdr.add("QTY");
        for(int i=0;i<vwprpLst.size();i++){
              String prp=util.nvl((String)vwprpLst.get(i));
               itemHdr.add(prp);
        }
        itemHdr.add("VALUE");
        itemHdr.add("STONEWISE_DIS");
        itemHdr.add("NET_DISC_AMT");
        itemHdr.add("NET_STONE_AMTPER_CTS");
        itemHdr.add("NET_STONE_AMT");
        itemHdr.add("NET_RAP_BACK");
        itemHdr.add("BYR");
        itemHdr.add("TYP");
        itemHdr.add("LOC");
        itemHdr.add("LATESTDT");
        itemHdr.add("MEMOID");
        itemHdr.add("MB");
        itemHdr.add("MB COMM");
        itemHdr.add("SB");
        itemHdr.add("SB COMM");
        itemHdr.add("RMK");
        itemHdr.add("EXHRTE");
        itemHdr.add("TRM");
        itemHdr.add("TYP");
          
          for(int i=0;i<nmeidnDteDlvLstsz;i++){
          String nmeidnDteVal=util.nvl((String)nmeidnDteDlvLst.get(i));
          String[] nmeidnDteValSplit=nmeidnDteVal.split("_");
          nmeidn=nmeidnDteValSplit[0];  
          dte=nmeidnDteValSplit[1];  
      String dtefrom = " trunc(sysdate) ";
      String dteto = " trunc(sysdate) ";
      if(!nmeidn.equals("") && !dte.equals("")){
            dtefrom = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
            dteto = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
       String pktDtlSql ="Select To_Char(g.Dte,'dd-mm-yyyy') appdte , b.cts ,c.idn, c.rap_rte, f.idn saleId, DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), c.vnm) vnm,b.qty,\n" + 
       "nvl(b.fnl_sal, b.quot) memoQuot,to_char(trunc(b.cts,2) * c.rap_rte, '99999999990.00') rapvlu, \n" + 
       " to_char(trunc(b.cts,2) * Nvl(b.fnl_usd, b.quot), '9999999990.00') amt,to_char((((nvl(b.fnl_usd, b.quot))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') r_dis , \n" + 
       "t.txt loc , b.STONEWISE_DIS,b.NET_DISC_AMT ,b.NET_STONE_AMT ,b.NET_STONE_AMTPER_CTS,b.NET_RAP_BACK ,b.byr,g.typ, To_Char(a.Dte,'dd-mm-yyyy') lstDte ,g.idn memoId , \n" + 
       "Byr.Get_Nm(A.Mbrk1_Idn) Mb, Nvl(A.Brk1_Comm,0) Mbcomm, Byr.Get_Nm(A.Mbrk2_Idn) Sub, Nvl(A.Brk2_Comm,0) Subcomm ,b.trm trms,nvl(f.fnl_xrt,f.exh_rte) fnl_xrt,b.stt salTyp,f.rmk\n" + 
       "From Mdlv A , Dlv_Pkt_Dtl_V B,Mstk C,Mnme d,nmerel e, msal f , mjan g  , df_users u , nme_dtl t\n" + 
       " where A.Idn = B.Idn And B.Mstk_Idn=C.Idn and d.nme_idn=a.nme_idn  and c.stt in('MKSD','BRC_MKSD') and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA'))\n" + 
       " and u.usr = a.aud_created_by  and u.nme_idn =t.nme_idn and t.mprp='OFFLOC' \n" + 
       " And B.Stt Not In ('RT','CS','DP','PS','CL') And B.Typ In ('DLV') and a.nmerel_idn=e.nmerel_idn and a.sal_idn=f.idn and f.memo_id=g.idn       \n" + 
       " And Trunc(a.Dte) Between "+dtefrom+" And "+dteto+"and B.Nme_Idn=? order by c.sk1 ";
      ary = new ArrayList();
      ary.add(nmeidn);

          ArrayList outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
      while(rs.next()){
          HashMap pktDtl = new HashMap();
          pktDtl.put("IDN", util.nvl(rs.getString("saleId")));
        pktDtl.put("LATESTDT", util.nvl(rs.getString("lstDte")));
      
        pktDtl.put("MEMOID", util.nvl(rs.getString("memoid")));
        pktDtl.put("APPROVE DATE", util.nvl(rs.getString("appdte")));
        pktDtl.put("TYP", util.nvl(rs.getString("typ")));
        pktDtl.put("LOC", util.nvl(rs.getString("loc")));
        pktDtl.put("BYR", util.nvl(rs.getString("byr")));
        pktDtl.put("TRM", util.nvl(rs.getString("trms")));
      
      
        pktDtl.put("SALE RTE", util.nvl(rs.getString("memoQuot")));
        pktDtl.put("VALUE", util.nvl(rs.getString("amt")));
        pktDtl.put("RAP_RTE", util.nvl(rs.getString("rap_rte")));
        pktDtl.put("MB", util.nvl(rs.getString("mb")));
        pktDtl.put("MB COMM", util.nvl(rs.getString("mbcomm")));
        pktDtl.put("SB", util.nvl(rs.getString("sub")));
        pktDtl.put("SB COMM", util.nvl(rs.getString("subcomm")));
        pktDtl.put("VNM", util.nvl(rs.getString("vnm")));
        pktDtl.put("QTY", util.nvl(rs.getString("qty")));
        pktDtl.put("CTS", util.nvl(rs.getString("cts")));
        
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
              + " where a.mprp = b.prp and b.prp = c.prp and c.mdl = 'DAILY_VW_XL' and a.mstk_idn = ? and a.grp=1 and c.flg <> 'N' "
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
          pktDtl.put("RTE",util.nvl(rs.getString("memoQuot")));
          pktDtl.put("RAP_DIS",util.nvl(rs.getString("r_dis")));
          pktDtl.put("CRTWT",util.nvl(rs.getString("cts")));
     
          pktDtlList.add(pktDtl);
          rs1.close();pst1.close();
      }
          rs.close(); pst.close();
          
      }
      }
      session.setAttribute("pktList", pktDtlList);
      session.setAttribute("itemHdr",itemHdr);
          util.updAccessLog(req,res,"Daily Delivery Report", "pktDtlExcelXL end");
      return am.findForward("pktDtl");  
      }
  }
 
    public ActionForward setDailyDlvParam(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        String nmeidnDte = util.nvl(req.getParameter("nmeidnDte"));
        String chk = util.nvl(req.getParameter("chk"));
        ArrayList nmeidnDteDlvLst = (session.getAttribute("nmeidnDteDlvLst") == null)?new ArrayList():(ArrayList)session.getAttribute("nmeidnDteDlvLst");
        if(nmeidnDte.equals("ALL")){
        ArrayList nmeidnDteDlvLstAll = (session.getAttribute("nmeidnDteDlvLstAll") == null)?new ArrayList():(ArrayList)session.getAttribute("nmeidnDteDlvLstAll");
        nmeidnDteDlvLst=new ArrayList(); 
            if(chk.equals("true"))
            nmeidnDteDlvLst=util.useDifferentArrayList(nmeidnDteDlvLstAll);               
        }else{
            if(chk.equals("true")){
            if(!nmeidnDteDlvLst.contains(nmeidnDte))
            nmeidnDteDlvLst.add(nmeidnDte);
            }else
                nmeidnDteDlvLst.remove(nmeidnDte);
        }
        session.setAttribute("nmeidnDteDlvLst", nmeidnDteDlvLst);
        return null;
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
                rtnPg=util.checkUserPageRights("report/dailyDlvReport.do?","");
                if(rtnPg.equals("unauthorized"))
                util.updAccessLog(req,res,"Daily Delivery Report", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Daily Delivery Report", "init");
            }
            }
            return rtnPg;
            }

    public DailyDlvReportAction() {
        super();
    }
}
