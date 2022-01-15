package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import java.io.IOException;

import java.net.URLEncoder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;



public class AjaxRptAction extends DispatchAction {
    
    HttpSession session ;
    DBMgr db ;
    InfoMgr info ;
    DBUtil util ;
    public ActionForward srchTyp(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      StringBuffer sb = new StringBuffer();
      String srchTyp = req.getParameter("srchTyp");
      String dtaTyp="";
      String srchTypSql = " select chr_fr, dsc , a.dta_typ from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " + 
      " b.mdl = 'JFLEX' and b.nme_rule = 'GENRCFLT' and a.chr_fr=? ";
      ArrayList ary = new ArrayList();
      ary.add(srchTyp);

        ArrayList outLst = db.execSqlLst("srchTyp", srchTypSql, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
      if(rs.next()){
          dtaTyp = rs.getString("dta_typ");
      }
        rs.close(); pst.close();
      if(dtaTyp.equals("L")){
          String sql = "select nme_idn , form_url_encode(nme) nme from nme_v where typ = ? "
                       + " and nme is not null order by nme ";
          ary = new ArrayList();
          ary.add(srchTyp);

          outLst = db.execSqlLst("nme", sql, ary);
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
          while(rs.next()){
              String nme = util.nvl(rs.getString("nme"));//util.nvl(rs.getString("nme")).replaceAll("&", "&amp;");
              sb.append("<nmes>");
              sb.append("<nmeid>" + util.nvl(rs.getString("nme_idn")) + "</nmeid>");
              sb.append("<nme>" + nme + "</nme>");
              sb.append("</nmes>");
          }
      }
        rs.close(); pst.close();
        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        res.getWriter().write("<mnme>" + sb.toString() + "</mnme>");
      return null;
    }
    public ActionForward updGT(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        int ct = 0;
        String nwFlg = "Z";
        String status = util.nvl(req.getParameter("CHKVAL"));
        String stkid = util.nvl(req.getParameter("stkIdn"));
        String bidId = util.nvl(req.getParameter("bidIdn"));
        String updGT="Update gt_srch_rslt set flg = ? where stk_idn = ? " ;
        nwFlg = "Z";
        ArrayList params = new ArrayList();
        if(status.equals("ALLOW")){
            nwFlg ="B";
            updGT="Update gt_srch_rslt set flg = ? , srch_id = ? where stk_idn = ?  ";
            params.add(nwFlg);
            params.add(bidId);
            params.add(stkid);
        }else{
            params.add(nwFlg);
            params.add(stkid);
        }
         ct = db.execUpd("update gt", updGT, params) ;
        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        res.getWriter().write("<mnme>Yes</mnme>");
        return null;
    }
    
  public ActionForward memo(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      StringBuffer sb = new StringBuffer();
      String nmeId = req.getParameter("byridn");
      String typ=req.getParameter("typ");
      String dfr = util.nvl(req.getParameter("dtefr"));
      String dto = util.nvl(req.getParameter("dteto"));
      String memotbl = util.nvl(req.getParameter("memotbl"));
      HashMap dbinfo = info.getDmbsInfoLst();
      String cnt =(String)dbinfo.get("CNT");
    String dtefrom = " trunc(sysdate) ";
    String dteto = " trunc(sysdate) ";
    String sttQ=" and b.stt not in ('APRT','RT','CS','CL','ALC','MRG') ";
    if(!dfr.equals(""))
      dtefrom = "to_date('"+dfr+"' , 'dd-mm-yyyy')";
    
    if(!dto.equals(""))
      dteto = "to_date('"+dto+"' , 'dd-mm-yyyy')";
    
      ArrayList ary = new ArrayList();
      ArrayList params = new ArrayList();
      if(typ.indexOf("AP")>0){
         sttQ =" and b.stt not in ('APRT','RT','CS','CL','ALC','MRG') and b.typ in ('IAP', 'EAP','WAP','LAP','MAP','SAP','HAP','BAP','KAP','OAP','BIDAP') ";    
      }else{
          sttQ+=" and a.typ in (?)";
          ary.add(typ);
      }
      String dteQ=" and trunc(b.dte) between "+dtefrom+" and "+dteto;
          if(!memotbl.equals("")){
          String[] memotblList = memotbl.split("-");
              sttQ=sttQ+" and b.idn between ? and ? ";
              ary.add(memotblList[0].trim());
              ary.add(memotblList[1].trim());
              dteQ="";
          }

      String sql =   " select a.idn ,  decode(a.typ, 'WR', 'NA', 'Z', 'NA', decode(b.stt, 'IS', 'Issued', 'AP', 'Approved', 'SL', 'Sold', 'RT', 'Returned', b.stt)) dsp_stt , b.stt "+
                       "  , a.typ typ, to_char(a.dte, 'dd-Mon-rrrr HH24:mi:ss') dte , a.byr byr,a.trm,a.rmk,a.note_person "+
                        " , sum(b.qty) sum, to_char(sum(b.cts),'9999999999990.99') cts "+
                      " , to_char(sum(b.cts*(nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte,1))),'9999999999990.00') vlu "+
                     " from jan_v a, jandtl b  where a.idn = b.idn "+sttQ+
                       " and a.nme_idn =?  "+dteQ+
                        " group by b.stt, a.idn, a.typ, a.dte, a.byr, a.trm,a.rmk,a.note_person  order by idn, 2 "; 
            ary.add(nmeId);
//            ary.add(typ);

      ArrayList outLst = db.execSqlLst("Daily memo pkt", sql, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                sb.append("<memo>");
                String memoidn=util.nvl(rs.getString("idn"),"0");
                sb.append("<id>"+memoidn +"</id>");
                sb.append("<trm>"+util.nvl(rs.getString("trm"),"0") +"</trm>");
                sb.append("<qty>"+util.nvl(rs.getString("sum"),"0") +"</qty>");
                sb.append("<cts>"+util.nvl(rs.getString("cts"),"0") +"</cts>");
                sb.append("<vlu>"+util.nvl(rs.getString("vlu"),"0") +"</vlu>");
                sb.append("<dte>"+util.nvl(rs.getString("dte"),"0") +"</dte>");
                sb.append("<typ>"+util.nvl(rs.getString("typ"),"0") +"</typ>");
                sb.append("<stt>"+util.nvl(rs.getString("stt"),"0") +"</stt>");
                sb.append("<sttDs>"+util.nvl(rs.getString("dsp_stt"),"0") +"</sttDs>");
                sb.append("<memormk>"+util.nvl(rs.getString("rmk"),"NA") +"</memormk>");
                sb.append("<noteperson>"+util.nvl(rs.getString("note_person"),"NA") +"</noteperson>");
                sb.append("</memo>");
            }
      rs.close(); pst.close();
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
      res.getWriter().write("<memos>"+sb.toString()+"</memos>");
      return null;
  }
  
    public ActionForward memoWR(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb = new StringBuffer();
        String nmeId = req.getParameter("byridn");
        String typ=req.getParameter("typ");
        String dfr = util.nvl(req.getParameter("dtefr"));
        String dto = util.nvl(req.getParameter("dteto"));
        String memotbl = util.nvl(req.getParameter("memotbl"));
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt =(String)dbinfo.get("CNT");
      String dtefrom = " trunc(sysdate) ";
      String dteto = " trunc(sysdate) ";
      String sttQ=" and b.stt in ('RT') ";
      if(!dfr.equals(""))
        dtefrom = "to_date('"+dfr+"' , 'dd-mm-yyyy')";
      
      if(!dto.equals(""))
        dteto = "to_date('"+dto+"' , 'dd-mm-yyyy')";
      
        ArrayList ary = new ArrayList();
        ArrayList params = new ArrayList();
        if(typ.indexOf("AP")>0){
           sttQ =" and b.stt in ('APRT','RT','CS','CL','ALC','MRG') and b.typ in ('IAP', 'EAP','WAP','LAP','MAP','SAP','HAP','BAP','OAP','KAP','BIDAP') ";    
        }else{
            sttQ+=" and a.typ in (?)";
            ary.add(typ);
        }
        String dteQ=" and trunc(a.dte) between "+dtefrom+" and "+dteto;
            if(!memotbl.equals("")){
            String[] memotblList = memotbl.split("-");
                sttQ=sttQ+" and b.idn between ? and ? ";
                ary.add(memotblList[0].trim());
                ary.add(memotblList[1].trim());
                dteQ="";
            }

        String sql =   " select a.idn ,  decode(a.typ, 'WR', 'NA', 'Z', 'NA', decode(b.stt, 'IS', 'Issued', 'AP', 'Approved', 'SL', 'Sold', 'RT', 'Returned', b.stt)) dsp_stt , b.stt "+
                         "  , a.typ typ, to_char(a.dte, 'dd-Mon-rrrr HH24:mi:ss') dte , a.byr byr,a.trm,a.rmk,a.note_person "+
                          " , sum(b.qty) sum, to_char(sum(b.cts),'9999999999990.99') cts "+
                        " , to_char(sum(b.cts*(nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte,1))),'9999999999990.00') vlu "+
                       " from jan_v a, jandtl b  where a.idn = b.idn "+sttQ+
                         " and a.nme_idn =?  "+dteQ+
                          " group by b.stt, a.idn, a.typ, a.dte, a.byr, a.trm,a.rmk,a.note_person  order by idn, 2 "; 
              ary.add(nmeId);

        ArrayList outLst = db.execSqlLst("Daily memo pkt", sql, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
              while(rs.next()){
                  sb.append("<memo>");
                  String memoidn=util.nvl(rs.getString("idn"),"0");
                  sb.append("<id>"+memoidn +"</id>");
                  sb.append("<trm>"+util.nvl(rs.getString("trm"),"0") +"</trm>");
                  sb.append("<qty>"+util.nvl(rs.getString("sum"),"0") +"</qty>");
                  sb.append("<cts>"+util.nvl(rs.getString("cts"),"0") +"</cts>");
                  sb.append("<vlu>"+util.nvl(rs.getString("vlu"),"0") +"</vlu>");
                  sb.append("<dte>"+util.nvl(rs.getString("dte"),"0") +"</dte>");
                  sb.append("<typ>"+util.nvl(rs.getString("typ"),"0") +"</typ>");
                  sb.append("<stt>"+util.nvl(rs.getString("stt"),"0") +"</stt>");
                  sb.append("<sttDs>"+util.nvl(rs.getString("dsp_stt"),"0") +"</sttDs>");
                  sb.append("<memormk>"+util.nvl(rs.getString("rmk"),"NA") +"</memormk>");
                  sb.append("<noteperson>"+util.nvl(rs.getString("note_person"),"NA") +"</noteperson>");
                  sb.append("</memo>");
              }
        rs.close(); pst.close();
        res.setContentType("text/xml"); 
        res.setHeader("Cache-Control", "no-cache"); 
        res.getWriter().write("<memos>"+sb.toString()+"</memos>");
        return null;
    }
    
  
    public ActionForward memoCS(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb = new StringBuffer();
        String nmeId = req.getParameter("byridn");
        String typ=req.getParameter("typ");
        String dfr = util.nvl(req.getParameter("dtefr"));
        String dto = util.nvl(req.getParameter("dteto"));
        String memotbl = util.nvl(req.getParameter("memotbl"));
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt =(String)dbinfo.get("CNT");
      String dtefrom = " trunc(sysdate) ";
      String dteto = " trunc(sysdate) ";
      String sttQ=" and b.stt not in ('APRT','RT','CL','ALC','MRG') ";
      if(!dfr.equals(""))
        dtefrom = "to_date('"+dfr+"' , 'dd-mm-yyyy')";
      
      if(!dto.equals(""))
        dteto = "to_date('"+dto+"' , 'dd-mm-yyyy')";
      
        ArrayList ary = new ArrayList();
        ArrayList params = new ArrayList();
        sttQ+=" and a.typ in (?)";
        ary.add("CS");
        String dteQ=" and trunc(a.dte) between "+dtefrom+" and "+dteto;
            if(!memotbl.equals("")){
            String[] memotblList = memotbl.split("-");
                sttQ=sttQ+" and b.idn between ? and ? ";
                ary.add(memotblList[0].trim());
                ary.add(memotblList[1].trim());
                dteQ="";
            }

        String sql =   " select a.idn ,  decode(a.typ, 'WR', 'NA', 'Z', 'NA', decode(b.stt, 'IS', 'Issued', 'AP', 'Approved', 'SL', 'Sold', 'RT', 'Returned', b.stt)) dsp_stt , b.stt "+
                         "  , a.typ typ, to_char(a.dte, 'dd-Mon-rrrr HH24:mi:ss') dte , a.byr byr,a.trm,a.rmk,a.note_person "+
                          " , sum(b.qty) sum, to_char(sum(b.cts),'9999999999990.99') cts "+
                        " , to_char(sum(b.cts*(nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte,1))),'9999999999990.00') vlu "+
                       " from jan_v a, jandtl b  where a.idn = b.idn "+sttQ+
                         " and a.nme_idn =?  "+dteQ+
                          " group by b.stt, a.idn, a.typ, a.dte, a.byr, a.trm,a.rmk,a.note_person  order by idn, 2 "; 
              ary.add(nmeId);

        ArrayList outLst = db.execSqlLst("Daily memo pkt", sql, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
              while(rs.next()){
                  sb.append("<memo>");
                  String memoidn=util.nvl(rs.getString("idn"),"0");
                  sb.append("<id>"+memoidn +"</id>");
                  sb.append("<trm>"+util.nvl(rs.getString("trm"),"0") +"</trm>");
                  sb.append("<qty>"+util.nvl(rs.getString("sum"),"0") +"</qty>");
                  sb.append("<cts>"+util.nvl(rs.getString("cts"),"0") +"</cts>");
                  sb.append("<vlu>"+util.nvl(rs.getString("vlu"),"0") +"</vlu>");
                  sb.append("<dte>"+util.nvl(rs.getString("dte"),"0") +"</dte>");
                  sb.append("<typ>"+util.nvl(rs.getString("typ"),"0") +"</typ>");
                  sb.append("<stt>"+util.nvl(rs.getString("stt"),"0") +"</stt>");
                  sb.append("<sttDs>"+util.nvl(rs.getString("dsp_stt"),"0") +"</sttDs>");
                  sb.append("<memormk>"+util.nvl(rs.getString("rmk"),"NA") +"</memormk>");
                  sb.append("<noteperson>"+util.nvl(rs.getString("note_person"),"NA") +"</noteperson>");
                  sb.append("</memo>");
              }
        rs.close(); pst.close();
        res.setContentType("text/xml"); 
        res.setHeader("Cache-Control", "no-cache"); 
        res.getWriter().write("<memos>"+sb.toString()+"</memos>");
        return null;
    }
  
    public ActionForward callDailySale(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb = new StringBuffer();
        String nmeId = req.getParameter("byridn");
        String typ=req.getParameter("typ");
        String dfr = util.nvl(req.getParameter("dtefr"));
        String dto = util.nvl(req.getParameter("dteto"));
        String PKTTY=util.nvl(req.getParameter("PKTTY"));
        String isLs = util.nvl(req.getParameter("isLs"));
        String sl_slip = util.nvl(req.getParameter("sl_slip"));
        String pktStr ="";
        if(!PKTTY.equals("")){
            pktStr=" and c.pkt_ty in ('"+PKTTY+"')";
        }
        String typStr=" and a.typ in ('SL')";
        if(!isLs.equals("")){
            typStr=" and a.typ in ('SL','LS')";
        }
      String dtefrom = " trunc(sysdate) ";
      String dteto = " trunc(sysdate) ";
      if(!dfr.equals(""))
        dtefrom = "to_date('"+dfr+"' , 'dd-mm-yyyy')";
      
      if(!dto.equals(""))
        dteto = "to_date('"+dto+"' , 'dd-mm-yyyy')";
      
      String conQ="";
      if(sl_slip.equals("Y")){
          conQ=",a.sl_slip";
      }
      
      ArrayList ary = new ArrayList();
        String sql = "select a.idn,a.typ, b.stt"+conQ+", a.nmerel_idn,form_url_encode(a.rmk) rmk,a.note_person, Byr.Get_Trms(a.Nmerel_Idn) trm\n" + 
        ", sum(nvl(b.qty, 1)) qty\n" + 
        ", trunc(sum(nvl(b.cts, 0)), 3) cts\n" + 
        ", trunc(sum(nvl(b.cts,0)*nvl(fnl_sal, quot)),2) vlu,Trunc(sum(trunc(b.cts,2)*(nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte,1))*nvl(a.fnl_exh_rte,1)),2) fnlexhvlu\n" + 
        ", to_char(a.dte, 'dd-Mon-rrrr HH24:mi:ss') dte\n" + 
        ", decode(a.aadat_paid,'Y',decode(nvl(a.aadat_comm, 0),0,'No','Yes'),'N','No','NN','Net',a.aadat_paid) aadat_paid"+
        ", decode(a.mbrk1_paid,'Y',decode(nvl(a.brk1_comm, 0),0,'No','Yes'),'N','No','NN','Net',a.mbrk1_paid) mbrk1_paid"+
        ", decode(a.mbrk2_paid,'Y',decode(nvl(a.brk2_comm, 0),0,'No','Yes'),'N','No','NN','Net',a.mbrk2_paid) mbrk2_paid"+
        ", decode(a.mbrk3_paid,'Y',decode(nvl(a.brk3_comm, 0),0,'No','Yes'),'N','No','NN','Net',a.mbrk3_paid) mbrk3_paid"+
        ", decode(a.mbrk4_paid,'Y',decode(nvl(a.brk4_comm, 0),0,'No','Yes'),'N','No','NN','Net',a.mbrk4_paid) mbrk4_paid \n"+
        "from msal a, jansal b , mstk c \n" + 
        "where a.idn = b.idn and c.idn=b.mstk_idn "+pktStr+typStr+
        "and a.nme_idn = ?\n" + 
        "and trunc(b.dte) between "+dtefrom+" and "+dteto+
        "  and B.Stt Not In ('RT','CS','CL') and a.flg1='CNF' \n" + 
        "Group By A.Idn,a.typ, B.Stt"+conQ+", A.Nmerel_Idn,form_url_encode(a.rmk),a.note_person, A.Dte \n"+
        ", decode(a.aadat_paid,'Y',decode(nvl(a.aadat_comm, 0),0,'No','Yes'),'N','No','NN','Net',a.aadat_paid)"+
        ", decode(a.mbrk1_paid,'Y',decode(nvl(a.brk1_comm, 0),0,'No','Yes'),'N','No','NN','Net',a.mbrk1_paid)"+
        ", decode(a.mbrk2_paid,'Y',decode(nvl(a.brk2_comm, 0),0,'No','Yes'),'N','No','NN','Net',a.mbrk2_paid)"+
        ", decode(a.mbrk3_paid,'Y',decode(nvl(a.brk3_comm, 0),0,'No','Yes'),'N','No','NN','Net',a.mbrk3_paid)"+
        ", decode(mbrk4_paid,'Y',decode(nvl(a.brk4_comm, 0),0,'No','Yes'),'N','No','NN','Net',mbrk4_paid)";
    //     String sql = "select idn,nme_idn, nmerel_idn,byr.get_trms(nmerel_idn) trm ,stt," +
    //     "typ, qty, trunc(cts,2) cts, vlu, to_char(dte, 'dd-Mon-rrrr HH24:mi:ss') dte  " +
    //     "from msal where typ in (?)  and stt <> 'CS' " +
    //     "and trunc(dte) between "+dtefrom+" and "+dteto+" and nme_idn= ? order by idn";
        ary.add(nmeId);
       
        

        ArrayList outLst = db.execSqlLst("Daily memo pkt", sql, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            sb.append("<memo>");
            sb.append("<id>"+util.nvl(rs.getString("idn"),"0") +"</id>");
            sb.append("<trm>"+util.nvl(rs.getString("trm"),"0") +"</trm>");
            sb.append("<qty>"+util.nvl(rs.getString("qty"),"0") +"</qty>");
            sb.append("<cts>"+util.nvl(rs.getString("cts"),"0") +"</cts>");
            sb.append("<vlu>"+util.nvl(rs.getString("vlu"),"0") +"</vlu>");
            sb.append("<fnlexhvlu>"+util.nvl(rs.getString("fnlexhvlu"),"0") +"</fnlexhvlu>");
            sb.append("<dte>"+util.nvl(rs.getString("dte"),"0") +"</dte>");
            sb.append("<typ>"+util.nvl(rs.getString("typ"),"0") +"</typ>");
            sb.append("<stt>"+util.nvl(rs.getString("stt"),"0") +"</stt>");
            sb.append("<memormk>"+util.nvl(rs.getString("rmk"),"NA") +"</memormk>");
            sb.append("<noteperson>"+util.nvl(rs.getString("note_person"),"NA") +"</noteperson>");
            sb.append("<aadatpaid>"+util.nvl(rs.getString("aadat_paid"),"0") +"</aadatpaid>");
            sb.append("<aadatpaid2>"+util.nvl(rs.getString("mbrk1_paid"),"0") +"</aadatpaid2>");
            sb.append("<mbrk2paid>"+util.nvl(rs.getString("mbrk2_paid"),"0") +"</mbrk2paid>");
            sb.append("<mbrk3paid>"+util.nvl(rs.getString("mbrk3_paid"),"0") +"</mbrk3paid>");
            sb.append("<mbrk4paid>"+util.nvl(rs.getString("mbrk4_paid"),"0") +"</mbrk4paid>");
            if(sl_slip.equals("Y")){
                sb.append("<sl_slip>"+util.nvl(rs.getString("sl_slip"),"0") +"</sl_slip>");
            }
            sb.append("</memo>");
        }
        rs.close(); pst.close();
        res.setContentType("text/xml"); 
        res.setHeader("Cache-Control", "no-cache"); 
        res.getWriter().write("<memos>"+sb.toString()+"</memos>");
        return null;
    }
    
  public ActionForward callDailyDlv(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      StringBuffer sb = new StringBuffer();
      String nmeId = req.getParameter("byridn");
      String typ=req.getParameter("typ");
      String dfr = util.nvl(req.getParameter("dtefr"));
      String dto = util.nvl(req.getParameter("dteto"));
      String ISHK = util.nvl(req.getParameter("ISHK"));
      String ISLS = util.nvl(req.getParameter("ISLS"));
      String pkt_ty = util.nvl(req.getParameter("pkt_ty"));
      String dtefrom = " trunc(sysdate) ";
      String dteto = " trunc(sysdate) ";
      String localQ="";
      if(!dfr.equals(""))
          dtefrom = "to_date('"+dfr+"' , 'dd-mm-yyyy')";
     if(!dto.equals(""))
         dteto = "to_date('"+dto+"' , 'dd-mm-yyyy')";
     String sqlStr = " and b.stt not in ('RT','CS','DP','PS','CL') ";
     if(ISHK.equals("HK"))
         sqlStr=" and b.stt in ('DP') ";
      if(ISLS.equals("YES"))
          localQ=" and a.typ not in ('LS')";
      if(!pkt_ty.equals(""))
          sqlStr=" and c.pkt_ty in ('"+pkt_ty+"') ";
    
    ArrayList ary = new ArrayList();
    String sql="select a.idn,nvl(a.fnl_exh_rte,0) fnl_exh_rte,a.typ, a.stt, a.nmerel_idn,a.rmk,a.note_person, Byr.Get_Trms(a.Nmerel_Idn) trm \n" + 
    "      , sum(nvl(b.qty, 1)) qty \n" + 
    "      , to_char(sum(trunc(b.cts,2)),'999,990.99') cts \n" + 
    "      , trunc(sum(nvl(b.cts,0)*nvl(fnl_sal, quot)),2) vlu,Trunc(sum(trunc(b.cts,2)*(nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte,1))*nvl(a.fnl_exh_rte,1)),2) fnlexhvlu \n" + 
    "      , to_char(a.dte, 'dd-Mon-rrrr HH24:mi:ss') dte \n" + 
      ", decode(a.aadat_paid,'Y',decode(nvl(a.aadat_comm, 0),0,'No','Yes'),'N','No','NN','Net',a.aadat_paid) aadat_paid"+
      ", decode(a.mbrk1_paid,'Y',decode(nvl(a.brk1_comm, 0),0,'No','Yes'),'N','No','NN','Net',a.mbrk1_paid) mbrk1_paid"+
      ", decode(a.mbrk2_paid,'Y',decode(nvl(a.brk2_comm, 0),0,'No','Yes'),'N','No','NN','Net',a.mbrk2_paid) mbrk2_paid"+
      ", decode(a.mbrk3_paid,'Y',decode(nvl(a.brk3_comm, 0),0,'No','Yes'),'N','No','NN','Net',a.mbrk3_paid) mbrk3_paid"+
      ", decode(a.mbrk4_paid,'Y',decode(nvl(a.brk4_comm, 0),0,'No','Yes'),'N','No','NN','Net',a.mbrk4_paid) mbrk4_paid \n"+
    " from mdlv a,dlv_dtl b,mstk c \n" + 
    " where a.idn=b.idn and b.mstk_idn=c.idn and c.stt in('MKSD','BRC_MKSD') \n" + 
    " and a.typ in (?) and a.stt <> 'CS' \n" + 
    " and trunc(a.dte) between "+dtefrom+" and "+dteto+"  and a.nme_idn= ? \n" +sqlStr+
    "      Group By A.Idn,nvl(a.fnl_exh_rte,0),a.typ, a.stt, A.Nmerel_Idn,a.rmk,a.note_person, A.Dte \n" + 
      ", decode(a.aadat_paid,'Y',decode(nvl(a.aadat_comm, 0),0,'No','Yes'),'N','No','NN','Net',a.aadat_paid)"+
      ", decode(a.mbrk1_paid,'Y',decode(nvl(a.brk1_comm, 0),0,'No','Yes'),'N','No','NN','Net',a.mbrk1_paid)"+
      ", decode(a.mbrk2_paid,'Y',decode(nvl(a.brk2_comm, 0),0,'No','Yes'),'N','No','NN','Net',a.mbrk2_paid)"+
      ", decode(a.mbrk3_paid,'Y',decode(nvl(a.brk3_comm, 0),0,'No','Yes'),'N','No','NN','Net',a.mbrk3_paid)"+
      ", decode(mbrk4_paid,'Y',decode(nvl(a.brk4_comm, 0),0,'No','Yes'),'N','No','NN','Net',mbrk4_paid) \n"+
    "      union\n" + 
    "      select a.idn,nvl(a.fnl_exh_rte,0),a.typ, a.stt, a.nmerel_idn,a.rmk,a.note_person, Byr.Get_Trms(a.Nmerel_Idn) trm \n" + 
    "      , sum(nvl(b.qty, 1)) qty \n" + 
    "      , to_char(sum(trunc(b.cts,2)),'999,990.99') cts \n" + 
    "      , trunc(sum(nvl(b.cts,0)*nvl(fnl_sal, quot)),2) vlu,Trunc(sum(trunc(b.cts,2)*(nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte,1))*nvl(a.fnl_exh_rte,1)),2) fnlexhvlu \n" + 
    "      , to_char(a.dte, 'dd-Mon-rrrr HH24:mi:ss') dte \n" + 
      ", decode(a.aadat_paid,'Y',decode(nvl(a.aadat_comm, 0),0,'No','Yes'),'N','No','NN','Net',a.aadat_paid) aadat_paid"+
      ", decode(a.mbrk1_paid,'Y',decode(nvl(a.brk1_comm, 0),0,'No','Yes'),'N','No','NN','Net',a.mbrk1_paid) mbrk1_paid"+
      ", decode(a.mbrk2_paid,'Y',decode(nvl(a.brk2_comm, 0),0,'No','Yes'),'N','No','NN','Net',a.mbrk2_paid) mbrk2_paid"+
      ", decode(a.mbrk3_paid,'Y',decode(nvl(a.brk3_comm, 0),0,'No','Yes'),'N','No','NN','Net',a.mbrk3_paid) mbrk3_paid"+
      ", decode(a.mbrk4_paid,'Y',decode(nvl(a.brk4_comm, 0),0,'No','Yes'),'N','No','NN','Net',a.mbrk4_paid) mbrk4_paid \n"+
    "      from msal a,jansal b,mstk c  \n" + 
    "      where a.idn=b.idn and b.mstk_idn=c.idn and c.stt in('MKSD','BRC_MKSD') and a.typ = 'LS' "+localQ+" and a.stt <> 'CS' and a.nme_idn= ? \n" + 
    "       and trunc(a.dte) between "+dtefrom+" and "+dteto+"  \n" + 
    "      Group By A.Idn,nvl(a.fnl_exh_rte,0),a.typ, a.stt, A.Nmerel_Idn,a.rmk,a.note_person, A.Dte "+
      ", decode(a.aadat_paid,'Y',decode(nvl(a.aadat_comm, 0),0,'No','Yes'),'N','No','NN','Net',a.aadat_paid)"+
      ", decode(a.mbrk1_paid,'Y',decode(nvl(a.brk1_comm, 0),0,'No','Yes'),'N','No','NN','Net',a.mbrk1_paid)"+
      ", decode(a.mbrk2_paid,'Y',decode(nvl(a.brk2_comm, 0),0,'No','Yes'),'N','No','NN','Net',a.mbrk2_paid)"+
      ", decode(a.mbrk3_paid,'Y',decode(nvl(a.brk3_comm, 0),0,'No','Yes'),'N','No','NN','Net',a.mbrk3_paid)"+
      ", decode(mbrk4_paid,'Y',decode(nvl(a.brk4_comm, 0),0,'No','Yes'),'N','No','NN','Net',mbrk4_paid) \n"+
    "      order by 1";
    
//     String sql = "select a.idn,a.nme_idn, a.nmerel_idn,byr.get_trms(a.nmerel_idn) trm ,a.stt," + 
//     "a.typ, a.qty, trunc(a.cts,2) cts, a.vlu,Trunc(sum(trunc(b.cts,2)*(nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte,1))*nvl(a.fnl_exh_rte,1)),2) fnlexhvlu, to_char(a.dte, 'dd-Mon-rrrr HH24:mi:ss') dte " + 
//     "from mdlv a,dlv_dtl b,mstk c where a.idn=b.idn and b.mstk_idn=c.idn and c.stt='MKSD' and a.typ in (?) and a.stt <> 'CS' "+sqlStr+
//     "and trunc(a.dte) between "+dtefrom+" and "+dteto+" and a.nme_idn= ? " +
//     "UNION "+
//      " select a.idn,a.nme_idn, a.nmerel_idn,byr.get_trms(a.nmerel_idn) trm ,a.stt," + 
//      " a.typ, a.qty, trunc(a.cts,2) cts, a.vlu,Trunc(sum(trunc(b.cts,2)*(nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte,1))*nvl(a.fnl_exh_rte,1)),2) fnlexhvlu, to_char(a.dte, 'dd-Mon-rrrr HH24:mi:ss') dte " + 
//      " from msal a,jansal b,mstk c  where a.idn=b.idn and b.mstk_idn=c.idn and c.stt='MKSD' and a.typ = 'LS' and a.stt <> 'CS' " + 
//      " and trunc(a.dte) between "+dtefrom+" and "+dteto+" and a.nme_idn= ? " +                  
//      " order by 1";
      ary.add(typ);
      ary.add(nmeId);
      ary.add(nmeId);

      ArrayList outLst = db.execSqlLst("Daily memo pkt", sql, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
      while(rs.next()){
          sb.append("<memo>");
          sb.append("<id>"+util.nvl(rs.getString("idn"),"0") +"</id>");
          sb.append("<trm>"+util.nvl(rs.getString("trm"),"0") +"</trm>");
          sb.append("<qty>"+util.nvl(rs.getString("qty"),"0") +"</qty>");
          sb.append("<cts>"+util.nvl(rs.getString("cts"),"0") +"</cts>");
          sb.append("<vlu>"+util.nvl(rs.getString("vlu"),"0") +"</vlu>");
          sb.append("<fnlexhrte>"+util.nvl(rs.getString("fnl_exh_rte"),"0") +"</fnlexhrte>");
          sb.append("<fnlexhvlu>"+util.nvl(rs.getString("fnlexhvlu"),"0") +"</fnlexhvlu>");
          sb.append("<dte>"+util.nvl(rs.getString("dte"),"0") +"</dte>");
          sb.append("<typ>"+util.nvl(rs.getString("typ"),"0") +"</typ>");
          sb.append("<stt>"+util.nvl(rs.getString("stt"),"0") +"</stt>");
          sb.append("<aadatpaid>"+util.nvl(rs.getString("aadat_paid"),"0") +"</aadatpaid>");
          sb.append("<aadatpaid2>"+util.nvl(rs.getString("mbrk1_paid"),"0") +"</aadatpaid2>");
          sb.append("<mbrk2paid>"+util.nvl(rs.getString("mbrk2_paid"),"0") +"</mbrk2paid>");
          sb.append("<mbrk3paid>"+util.nvl(rs.getString("mbrk3_paid"),"0") +"</mbrk3paid>");
          sb.append("<mbrk4paid>"+util.nvl(rs.getString("mbrk4_paid"),"0") +"</mbrk4paid>");
          sb.append("<memormk>"+util.nvl(rs.getString("rmk"),"NA") +"</memormk>");
          sb.append("<noteperson>"+util.nvl(rs.getString("note_person"),"NA") +"</noteperson>");
          sb.append("</memo>");
      }
      rs.close(); pst.close();
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
      res.getWriter().write("<memos>"+sb.toString()+"</memos>");
      return null;
  }
  
  public ActionForward callBranchDailyDlv(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      StringBuffer sb = new StringBuffer();
      String nmeId = req.getParameter("byridn");
      String typ=req.getParameter("typ");
      String dfr = util.nvl(req.getParameter("dtefr"));
      String dto = util.nvl(req.getParameter("dteto"));
      String ISHK = util.nvl(req.getParameter("ISHK"));
      String dtefrom = " trunc(sysdate) ";
      String dteto = " trunc(sysdate) ";
      if(!dfr.equals(""))
          dtefrom = "to_date('"+dfr+"' , 'dd-mm-yyyy')";
     if(!dto.equals(""))
         dteto = "to_date('"+dto+"' , 'dd-mm-yyyy')";
    
    
    ArrayList ary = new ArrayList();
     String sql = " select a.dlv_idn ,c.idn salidn, a.nme_idn , a.nmerel_idn ,byr.get_trms(a.nmerel_idn) trm ,b.stt,\n" + 
     "     a.typ, sum(b.qty) qty ,to_char(sum(trunc(b.cts,2)),'999,990.99') cts ,to_char(sum(trunc(nvl(b.fnl_sal,b.quot)*b.cts,2)),'999,9999,999,990.00') vlu, to_char(b.dte_dlv, 'dd-Mon-rrrr') dte  from  brc_mdlv a , brc_dlv_dtl b,msal c\n" + 
     "       where a.idn=b.idn and b.sal_idn=c.idn and b.stt='DLV' and trunc(b.dte_dlv) between "+dtefrom+" and "+dteto+"  and c.nme_idn = ?  " +
         "  group by a.dlv_idn,c.idn, a.nme_idn, a.nmerel_idn, byr.get_trms(a.nmerel_idn), b.stt, a.typ, to_char(b.dte_dlv, 'dd-Mon-rrrr')  order by 1 ";
     
      ary.add(nmeId);

      ArrayList outLst = db.execSqlLst("Daily memo pkt", sql, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
      while(rs.next()){
          sb.append("<memo>");
          sb.append("<id>"+util.nvl(rs.getString("dlv_idn"),"0") +"</id>");
          sb.append("<salidn>"+util.nvl(rs.getString("salidn"),"0") +"</salidn>");
          sb.append("<trm>"+util.nvl(rs.getString("trm"),"0") +"</trm>");
          sb.append("<qty>"+util.nvl(rs.getString("qty"),"0") +"</qty>");
          sb.append("<cts>"+util.nvl(rs.getString("cts"),"0") +"</cts>");
          sb.append("<vlu>"+util.nvl(rs.getString("vlu"),"0") +"</vlu>");
          sb.append("<dte>"+util.nvl(rs.getString("dte"),"0") +"</dte>");
          sb.append("<typ>"+util.nvl(rs.getString("typ"),"0") +"</typ>");
          sb.append("<stt>"+util.nvl(rs.getString("stt"),"0") +"</stt>");
          sb.append("</memo>");
      }
      rs.close(); pst.close();
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
      res.getWriter().write("<memos>"+sb.toString()+"</memos>");
      return null;
  }
  
  public ActionForward buyerSugg(ActionMapping am, ActionForm a, HttpServletRequest req, HttpServletResponse res)
          throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());

      StringBuffer sb      = new StringBuffer();
      String       typ     = util.nvl(req.getParameter("typ"));
      String      usrTyp = util.nvl(req.getParameter("UsrTyp"));
      String       initial = req.getParameter("param");
      String       match   = "" + initial + "%";
     
      match = match.toLowerCase();
      
      ArrayList ary = new ArrayList();
      int    ctr = 0;
      String sql = "select nme_idn , form_url_encode(nme) nme from nme_v where lower(nme) like lower(?) "
                   + " and nme is not null and typ in ('BUYER','AADAT','MFG','GROUP')" ;
      if(!usrTyp.equals("")){
          sql = sql+" and typ="+usrTyp ;
      }
        sql = sql+" and rownum <50 order by nme ";
      
      String lFld = util.nvl(req.getParameter("lFld"));
      if(lFld.length() > 0) {
          String lovQ = util.nvl((String)session.getAttribute(lFld));
          if(lovQ.length() > 0)
              sql = lovQ;
      }
      util.SOP(lFld + " : "+ sql);
      if (typ.equalsIgnoreCase("city")) {
          match = "%" + match;

          /*
           * if(match.indexOf(" ") == -1)
           * sql = "select distinct country_idn, country_nm from country_city_v where lower(country_nm) like lower(?) and rownum < 21 order by 2";
           * else
           */
          sql = "select city_idn, country_city from country_city_v " + " where lower(country_city) like lower(?) "
                + " order by 2";
      }

      ary.add(match);


      ArrayList outLst = db.execSqlLst("column nme", sql, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);

      while (rs.next()) {
          String nme = util.nvl(rs.getString(2), "0");

          ++ctr;
          sb.append("<nmes>");
          sb.append("<nmeid>" + util.nvl(rs.getString(1)) + "</nmeid>");
          sb.append("<nme>" + nme + "</nme>");
          sb.append("</nmes>");

          /*
           * if(ctr == 20)
           *   break;
           */
      }

      rs.close(); pst.close();
      res.setContentType("text/xml");
      res.setHeader("Cache-Control", "no-cache");
      res.getWriter().write("<mnme>" + sb.toString() + "</mnme>");

      return null;
  }
    public ActionForward UpdateInvNme(ActionMapping am, ActionForm a, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList ary = new ArrayList();
        ArrayList param = new ArrayList();
        StringBuffer sb      = new StringBuffer();
        String       nme_idn     = util.nvl(req.getParameter("nme_idn"));
        String      addr_idn = util.nvl(req.getParameter("addr_idn"));
        String      sal_idn = util.nvl(req.getParameter("sal_idn"));
        String      sal_dte = util.nvl(req.getParameter("sal_dte"));
        String      mnlsale_id = util.nvl(req.getParameter("mnlsale_id"));

        ary.add(nme_idn);
        ary.add(addr_idn);
        ary.add(sal_idn);
         String sqlUpd ="update msal set inv_nme_idn =? , inv_addr_idn = ? where idn= ?";
        int cnt =db.execUpd("Update Nme", sqlUpd ,ary);
            if (cnt==1) {
            res.setContentType("text/xml"); 
            res.setHeader("Cache-Control", "no-cache"); 
            res.getWriter().write("<message>Yes</message>");
            
            if(!sal_dte.equals("")){
                param = new ArrayList();
                param.add(sal_dte);
                param.add(sal_idn);
                param.add(sal_dte);
                sqlUpd ="update jansal set dte =to_date(?,'dd-mm-yyyy') where idn= ? and to_char(dte,'dd-mm-yyyy')<> ?";
                cnt =db.execUpd("Update jansal", sqlUpd ,param);
                
                param = new ArrayList();
                param.add(sal_dte);
                param.add(sal_idn);
              
                sqlUpd ="update msal set dte =to_date(?,'dd-mm-yyyy') where idn= ?";
                cnt =db.execUpd("Update msal", sqlUpd ,param);
                
                if(cnt>0){
                String salmstkQ="select mstk_idn from jansal where stt in('SL','DLV','ADJ') and idn=?";
                ary = new ArrayList();
                ary.add(sal_idn);

                    ArrayList outLst = db.execSqlLst("salmstkQ ", salmstkQ, ary);
                    PreparedStatement pst = (PreparedStatement)outLst.get(0);
                    ResultSet rs = (ResultSet)outLst.get(1);
                while(rs.next()){
                    param = new ArrayList();
                    param.add(util.nvl((String)rs.getString("mstk_idn")));
                    param.add(sal_dte);
                    int ct = db.execCall("stockUpd","stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => 'SAL_DTE', pVal => to_char(to_date(?,'dd-mm-yyyy'), 'dd-MON-rrrr'), pGrp => 1, pPrpTyp => 'D')", param);
                    if(!mnlsale_id.equals("")){
                        param = new ArrayList();
                        param.add(util.nvl((String)rs.getString("mstk_idn")));
                        param.add(mnlsale_id);
                        ct = db.execCall("stockUpd","stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => 'SAL_ID', pVal => ?, pGrp => 1, pPrpTyp => 'T')", param);
                    }
                }
                rs.close(); pst.close();
                    if(!mnlsale_id.equals("")){
                        param = new ArrayList();
                        param.add(mnlsale_id);
                        param.add(sal_idn);
                        sqlUpd ="update msal set sl_slip =? where idn= ?";
                        cnt =db.execUpd("Update msal", sqlUpd ,param);
                    }
                }
            }
            } else{
            res.setContentType("text/xml"); 
            res.setHeader("Cache-Control", "no-cache"); 
            res.getWriter().write("<message>No</message>");
            }
        return null;
    }
    public ActionForward getAddrs(ActionMapping am, ActionForm a, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList ary = new ArrayList();
        StringBuffer sb      = new StringBuffer();
        String       nme_idn     = util.nvl(req.getParameter("nme_idn"));
        String addr ="";
        String addr_idn ="";

        String SqlAdds ="select a.addr_idn , unt_num, bldg ||''|| street ||''|| landmark ||''|| area ||' '|| b.city_nm||' '|| c.country_nm addr \n" + 
                "  from maddr a, mcity b, mcountry c where a.city_idn = b.city_idn and b.country_idn = c.country_idn and a.nme_idn =? and a.vld_dte is null  order by a.dflt_yn desc";   
        ary.add(nme_idn);

        ArrayList outLst = db.execSqlLst("SqlAdds", SqlAdds, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
           
                    sb.append("<addr>");
                    sb.append("<idn>"+util.nvl(rs.getString("addr_idn"),"0") +"</idn>");
                    sb.append("<addrss>"+util.nvl(rs.getString("unt_num"))+" "+util.nvl(rs.getString("addr"))+"</addrss>");                 
                    sb.append("</addr>");
                }
                rs.close(); pst.close();
                res.setContentType("text/xml");
                res.setHeader("Cache-Control", "no-cache");
                res.getWriter().write("<addrs>"+sb.toString()+"</addrs>");
                
        return null;
    }
    public ActionForward CalAvgTTl(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb = new StringBuffer();
        String prp = util.nvl(req.getParameter("prp"));
        String lprp = util.nvl(req.getParameter("lprp"));
        if(lprp.equals(""))
            lprp=prp; 
        String typ = util.nvl(req.getParameter("typ"));
        String rqStt = util.nvl(req.getParameter("stt"));
        rqStt=rqStt.replaceAll("_", "");
        ArrayList grpOrderList = (ArrayList)session.getAttribute("grpOrderList");
        HashMap dataDtl = (HashMap)session.getAttribute("pktdataDtl");
        String msg = "Total : ";
        int cnt = 0;
        double ttlCts=0;
        double total = 0,totalVlu=0, totalAvgVlu=0,totalRapVlu = 0,ttlAmt=0;
        for(int z=0;z< grpOrderList.size();z++){
        String grpby =(String)grpOrderList.get(z);
        ArrayList pkt=(ArrayList)dataDtl.get(grpby);
        for (int m = 0; m< pkt.size(); m++) {
        HashMap pktData = (HashMap)pkt.get(m);
        String val = util.nvl((String)pktData.get(lprp)).trim();
        String stt = util.nvl((String)pktData.get("stt")).trim();
            if((stt.equals(rqStt) || rqStt.equals("")) && !val.equals("")){
                cnt = cnt+1;
                val  = val.trim();
                float fval = Float.parseFloat(val);
                float cts = Float.parseFloat((String)pktData.get("cts"));
                total = total + Double.parseDouble(val);
                totalVlu = totalVlu + fval;
                totalAvgVlu = totalAvgVlu + (fval*cts);
                ttlCts = ttlCts+ cts;
                ttlAmt =ttlAmt+Float.parseFloat((String)pktData.get("amt"));
                float rapRte = Float.parseFloat((String)pktData.get("rap_rte"));
                totalRapVlu = totalRapVlu + (rapRte*cts);
                
            }
        }
    }
        if(cnt==0)
            cnt=1;
        if(typ.equals("AVG")){
            prp = prp.toUpperCase();
            if(prp.indexOf("DIS")!=-1 || prp.indexOf("BACK")!=-1)
              total = (((totalAvgVlu/ttlCts)/(totalRapVlu/ttlCts))*100)-100 ;
            else
                total = totalAvgVlu/ttlCts;
            msg= "Average : ";
        }else{
           total= totalVlu;
        }
        total=util.roundToDecimals(total,2);
        sb.append(msg+""+total);
        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        res.getWriter().write("<total>" + sb.toString() + "</total>");
        return null;
    }
    public ActionForward commonpieChart(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
          res.setContentType("text/xml"); 
          res.setHeader("Cache-Control", "no-cache"); 
          String  key = (String)req.getParameter(("key"));
          if(key.indexOf("~") > -1)
          key=key.replaceAll("\\~","+"); 
          String row = util.nvl((String)req.getParameter("row"));
          String col = util.nvl((String)req.getParameter("col"));
          String stt = util.nvl((String)req.getParameter("stt"));
          HashMap dataDtl = (HashMap)session.getAttribute("dataDtl");
          HashMap gridstructure = (HashMap)session.getAttribute("gridstructure");
          HashMap prp = info.getSrchPrp();
//          if(prp==null){
//          util.initPrpSrch();
//          prp = info.getSrchPrp();
//          }
            HashMap mprp = info.getSrchMprp();
          ArrayList gridcommLst=new ArrayList();
          ArrayList gridrowLst=new ArrayList();
          ArrayList gridcolLst=new ArrayList();
          gridcommLst=(ArrayList)gridstructure.get("COMM");
          gridrowLst=(ArrayList)gridstructure.get("ROW");
          gridcolLst=(ArrayList)gridstructure.get("COL");
            ArrayList rowList=new ArrayList();
            ArrayList colList=new ArrayList();
            String gridByGrid = util.nvl((String)session.getAttribute("gridByGrid"),"PRP");
            if(gridByGrid.equals("PRP")){
            rowList = (ArrayList)prp.get(gridrowLst.get(0)+"V");
            colList = (ArrayList)prp.get(gridcolLst.get(0)+"V");
            }else{
              rowList = util.useDifferentArrayListUnique((ArrayList)prp.get(gridrowLst.get(0)+"G"));
              colList = util.useDifferentArrayListUnique((ArrayList)prp.get(gridcolLst.get(0)+"G"));
            }
            ArrayList rowListGt = (ArrayList)session.getAttribute("rowList");
            ArrayList colListGt  = (ArrayList)session.getAttribute("colList");
            String rowlprpTyp = util.nvl((String)mprp.get(gridrowLst.get(0)+"T"));
            String collprpTyp = util.nvl((String)mprp.get(gridcolLst.get(0)+"T"));
            if(rowlprpTyp.equals("T") || rowlprpTyp.equals("N")){
            rowList=new ArrayList();
            rowList=rowListGt;
            }
            if(collprpTyp.equals("T") || collprpTyp.equals("N")){
            colList=new ArrayList();
            colList=colListGt;
            }
          StringBuffer sb = new StringBuffer();
          HashMap finalDtl=new HashMap();
          ArrayList finalList=new ArrayList();
            if(!row.equals("")) { 
                String rowV=row ;  
            for(int n=0;n< colList.size();n++){
              String colV = (String)colList.get(n); 
              String keyQty = key+"@"+rowV+"@"+colV+"@"+stt+"@QTY";
              String valQty = util.nvl((String)dataDtl.get(keyQty));
              if(!valQty.equals("")) { 
              finalDtl.put(colV,valQty);
              finalList.add(colV);
              } 
            }
            }else{
                for(int m=0;m< rowList.size();m++){
                  String rowV = (String)rowList.get(m);
                  String colV = col;  
                  String keyQty = key+"@"+rowV+"@"+colV+"@"+stt+"@QTY";
                  String valQty = util.nvl((String)dataDtl.get(keyQty));
                 
                  if(!valQty.equals("")) { 
                  finalDtl.put(rowV,valQty);
                  finalList.add(rowV);
                  }
                } 
            }
            
            for(int z=0;z<finalList.size();z++){
            String finalkey=util.nvl((String)finalList.get(z));
            String vlu=util.nvl((String)finalDtl.get(finalkey));
            if(!vlu.equals("")){
                sb.append("<subTag>");
                sb.append("<attrNme>"+finalkey+"</attrNme>");
                sb.append("<attrVal>"+vlu+"</attrVal>");
                sb.append("</subTag>");
            }
            }
            res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");

            return null;
        }
    
    public ActionForward commonpieselection(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
          res.setContentType("text/xml"); 
          res.setHeader("Cache-Control", "no-cache"); 
          String  key = (String)req.getParameter(("key"));
          if(key.indexOf("~") > -1)
          key=key.replaceAll("\\~","+"); 
          String row = util.nvl((String)req.getParameter("row"));
          String col = util.nvl((String)req.getParameter("col"));
          String stt = util.nvl((String)req.getParameter("stt"));
            String  typ = util.nvl((String)req.getParameter(("typ")));
            HashMap gridstructure = (HashMap)session.getAttribute("gridstructure");
          ArrayList rowList = (ArrayList)session.getAttribute("rowList");
          ArrayList colList  = (ArrayList)session.getAttribute("colList");
          HashMap dataDtl = (HashMap)session.getAttribute("dataDtl");
          String[] rowparam=null;
          String[] colparam=null;
          ArrayList rowListSelected = new ArrayList();
          ArrayList colListSelected  = new ArrayList();
          HashMap prp = info.getSrchPrp();
          HashMap finalDtl=new HashMap();
//          if(prp==null){
//          util.initPrpSrch();
//          prp = info.getSrchPrp();
//          }
          ArrayList gridcommLst=new ArrayList();
          ArrayList gridrowLst=new ArrayList();
          ArrayList gridcolLst=new ArrayList();
            HashMap mprp = info.getSrchMprp();
          gridcommLst=(ArrayList)gridstructure.get("COMM");
          gridrowLst=(ArrayList)gridstructure.get("ROW");
          gridcolLst=(ArrayList)gridstructure.get("COL");
            ArrayList rowListPrp=new ArrayList();
            ArrayList colListPrp=new ArrayList();
            String gridByGrid = util.nvl((String)session.getAttribute("gridByGrid"),"PRP");
            if(gridByGrid.equals("PRP")){
            rowListPrp = (ArrayList)prp.get(gridrowLst.get(0)+"V");
            colListPrp = (ArrayList)prp.get(gridcolLst.get(0)+"V");
            }else{
              rowListPrp = util.useDifferentArrayListUnique((ArrayList)prp.get(gridrowLst.get(0)+"G"));
              colListPrp = util.useDifferentArrayListUnique((ArrayList)prp.get(gridcolLst.get(0)+"G"));
            }
            String rowlprpTyp = util.nvl((String)mprp.get(gridrowLst.get(0)+"T"));
            String collprpTyp = util.nvl((String)mprp.get(gridcolLst.get(0)+"T"));
            if(rowlprpTyp.equals("T") || rowlprpTyp.equals("N")){
            rowListPrp=new ArrayList();
            rowListPrp=rowList;
            }
            if(collprpTyp.equals("T") || collprpTyp.equals("N")){
            colListPrp=new ArrayList();
            colListPrp=colList;
            }
          StringBuffer sb = new StringBuffer();
            if(row.equals("ALL")){
            rowListSelected=rowList;
            rowListSelected.remove("ALL");
            rowListSelected.remove("ALL");
            }
            if(col.equals("ALL")){
            colListSelected=colList;
            colListSelected.remove("ALL");
            colListSelected.remove("ALL");
            }
            if(!row.equals("") && !row.equals("ALL")){
            rowparam = row.split(",");
            for(int k=0;k< rowparam.length;k++){
            String rowp=rowparam[k];
            rowListSelected.add(rowp);
            }
            }
            if(!col.equals("") && !col.equals("ALL")){
            colparam = col.split(",");
            for(int k=0;k< colparam.length;k++){
            String colp=colparam[k];
            colListSelected.add(colp);
            }}
            if(typ.equals("COL")) { 
            for(int m=0;m< rowListSelected.size();m++){
            String rowV = (String)rowListSelected.get(m);
            for(int n=0;n< colListSelected.size();n++){
              String colV = (String)colListSelected.get(n);  
              String keyQty = key+"@"+rowV+"@"+colV+"@"+stt+"@QTY";
              String valQty = util.nvl((String)dataDtl.get(keyQty));
                  if(!valQty.equals("")) { 
                  finalDtl.put(rowV+"@"+colV,valQty);
                  } 
            }}
            }else{
                for(int m=0;m< rowListSelected.size();m++){
                  String rowV = (String)rowListSelected.get(m);                
                  for(int n=0;n< colListSelected.size();n++){
                  String colV = (String)colListSelected.get(n);   
                  String keyQty = key+"@"+rowV+"@"+colV+"@"+stt+"@QTY";
                  String valQty = util.nvl((String)dataDtl.get(keyQty));
                 
                  if(!valQty.equals("")) { 
                  finalDtl.put(rowV+"@"+colV,valQty);
                  }}
                } 
            }
                if(colListSelected.size()>0 && colListSelected!=null){
                if(typ.equals("CLR")){
                for(int i=0;i<rowListPrp.size();i++){
                String rowkey=util.nvl((String)rowListPrp.get(i));
                if(rowListSelected.contains(rowkey)){
                String Valuettl="0";
                for(int j=0;j<colListSelected.size();j++){    
                String colkey=util.nvl((String)colListSelected.get(j));
                String Value=util.nvl((String)finalDtl.get(rowkey+"@"+colkey));
                if(!Value.equals("")){
                    Valuettl=String.valueOf(Integer.parseInt(Valuettl)+Integer.parseInt(Value));
                }
                }
                    if(!Valuettl.equals("0") && !Valuettl.equals("")){
                    sb.append("<subTag>");
                    sb.append("<attrNme>"+rowkey+"</attrNme>");
                    sb.append("<attrVal>"+Valuettl+"</attrVal>");
                    sb.append("</subTag>");
                    }
                }
                }
                }else{
                 for(int i=0;i<colListPrp.size();i++){
                 String colkey=util.nvl((String)colListPrp.get(i));
                 if(colListSelected.contains(colkey)){
                 String Valuettl="0";
                 for(int j=0;j<rowListSelected.size();j++){    
                 String rowkey=util.nvl((String)rowListSelected.get(j));
                 String Value=util.nvl((String)finalDtl.get(rowkey+"@"+colkey));
                 if(!Value.equals("")){
                     Valuettl=String.valueOf(Integer.parseInt(Valuettl)+Integer.parseInt(Value));
                 }
                 }
                 if(!Valuettl.equals("0") && !Valuettl.equals("")){
                 sb.append("<subTag>");
                 sb.append("<attrNme>"+colkey+"</attrNme>");
                 sb.append("<attrVal>"+Valuettl+"</attrVal>");
                 sb.append("</subTag>");
                 }   
                 }
                }
                }
                }
            res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");

            return null;
        }
    
    public ActionForward createbuyerwisePieChart(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
          res.setContentType("text/xml"); 
          res.setHeader("Cache-Control", "no-cache"); 
            String itm = util.nvl((String)req.getParameter("itm"));
            String defaultdisplay = util.nvl((String)req.getParameter("defaultdisplay"));
            String basedon = util.nvl((String)req.getParameter("basedon"));
          ArrayList summaryList = (ArrayList)session.getAttribute("summaryList");
          StringBuffer sb = new StringBuffer();
            ArrayList list=new ArrayList();
            ArrayList listDtl=new ArrayList();
            String ttl="0";
            String ttlother="0";
            for(int i=0;i<summaryList.size();i++){
            HashMap summDtl = (HashMap)summaryList.get(i);
                String vlu =util.nvl((String)summDtl.get(itm));
                if(!vlu.equals("") && !vlu.equals("0")){
                if(i<Integer.parseInt(defaultdisplay)){
                listDtl=new ArrayList();
                listDtl.add(vlu);   
                listDtl.add(util.nvl((String)summDtl.get(basedon)));
                list.add(listDtl);
                }else{
                ttlother=String.valueOf(Double.parseDouble(ttlother)+Double.parseDouble(vlu));
                }
                ttl=String.valueOf(Double.parseDouble(ttl)+Double.parseDouble(vlu));
                }
            }
            if(!ttlother.equals("0")){
                listDtl=new ArrayList();
                listDtl.add(ttlother);   
                listDtl.add("Others");
                list.add(listDtl);    
            }
            for(int k=0;k<list.size();k++){
            listDtl=new ArrayList();
            listDtl=(ArrayList)list.get(k);
            String vlu = util.nvl((String)listDtl.get(0));
            String on = util.nvl((String)listDtl.get(1));
            if (on.indexOf("&") > -1)
            on = on.replaceAll("&", "%26");
            sb.append("<subTag>");
            sb.append("<attrNme>"+on+"</attrNme>");
            sb.append("<attrVal>"+vlu+"</attrVal>");
            sb.append("</subTag>");
            }

            res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");

            return null;
        }
    
    public ActionForward createempwisePieChart(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
          res.setContentType("text/xml"); 
          res.setHeader("Cache-Control", "no-cache"); 
          
            StringBuffer sb = new StringBuffer();
            String defaultdisplay = util.nvl((String)req.getParameter("defaultdisplay"));
            String basedon = util.nvl((String)req.getParameter("basedon"));
            ArrayList list=new ArrayList();
            ArrayList listDtl=new ArrayList();
            String ttl="0";
            String ttlother="0";
            int i = 0;
            String rsltQ = " select emp, trunc(sum(vlu_usd)/1000,2) vlu  from gt_srch_rslt group by emp order by emp " ;

            ArrayList outLst = db.execSqlLst("search Result", rsltQ, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()) {
            i++;    
            String emp = util.nvl(rs.getString("emp"));
            String vlu = util.nvl(rs.getString("vlu"));    
            if(!vlu.equals("") && !vlu.equals("0")){
            if(i<Integer.parseInt(defaultdisplay)){
            listDtl=new ArrayList();
                listDtl.add(vlu);   
                listDtl.add(emp);
                list.add(listDtl);
                }else{
                ttlother=String.valueOf(util.roundToDecimals(Double.parseDouble(ttlother)+Double.parseDouble(vlu), 2));
                }
                ttl=String.valueOf(util.roundToDecimals(Double.parseDouble(ttl)+Double.parseDouble(vlu),2));
                }    
            }
            rs.close(); pst.close();            
            if(!ttlother.equals("0")){
                listDtl=new ArrayList();
                listDtl.add(ttlother);   
                listDtl.add("Others");
                list.add(listDtl);    
            }
            for(int k=0;k<list.size();k++){
            listDtl=new ArrayList();
            listDtl=(ArrayList)list.get(k);
            String vlu = util.nvl((String)listDtl.get(0));
            String on = util.nvl((String)listDtl.get(1));
            if (on.indexOf("&") > -1)
            on = on.replaceAll("&", "%26");
            sb.append("<subTag>");
            sb.append("<attrNme>"+on+"</attrNme>");
            sb.append("<attrVal>"+vlu+"</attrVal>");
            sb.append("</subTag>");
            }
            
            res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");

            return null;
        }
    
    public ActionForward createempwiseSaleDisPieChart(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
          res.setContentType("text/xml"); 
          res.setHeader("Cache-Control", "no-cache"); 
          
            StringBuffer sb = new StringBuffer();
            String defaultdisplay = util.nvl((String)req.getParameter("defaultdisplay"));
            String basedon = util.nvl((String)req.getParameter("basedon"));
            String itm = util.nvl((String)req.getParameter("itm"));
            ArrayList list=new ArrayList();
            ArrayList listDtl=new ArrayList();
            String ttl="0";
            String ttlother="0";
            int i = 0;
            String rsltQ = " select emp, trunc(sum(cmp)/1000,2) vlu, sum(nvl(srt_001, 0)) loyalty, sum(quot*nvl(srt_002, 0)/100) mgmtDis  from gt_srch_rslt group by emp order by emp " ;

            ArrayList outLst = db.execSqlLst("search Result", rsltQ, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()) {
            i++;    
            String emp = util.nvl(rs.getString("emp"));
            String vlu = util.nvl(rs.getString(itm));    
            if(!vlu.equals("") && !vlu.equals("0")){
            if(i<Integer.parseInt(defaultdisplay)){
            listDtl=new ArrayList();
                listDtl.add(vlu);   
                listDtl.add(emp);
                list.add(listDtl);
                }else{
                ttlother=String.valueOf(util.roundToDecimals(Double.parseDouble(ttlother)+Double.parseDouble(vlu), 2));
                }
                ttl=String.valueOf(util.roundToDecimals(Double.parseDouble(ttl)+Double.parseDouble(vlu),2));
                }    
            }
            rs.close(); pst.close();
            if(!ttlother.equals("0")){
                listDtl=new ArrayList();
                listDtl.add(ttlother);   
                listDtl.add("Others");
                list.add(listDtl);    
            }
            for(int k=0;k<list.size();k++){
            listDtl=new ArrayList();
            listDtl=(ArrayList)list.get(k);
            String vlu = util.nvl((String)listDtl.get(0));
            String on = util.nvl((String)listDtl.get(1));
            if (on.indexOf("&") > -1)
            on = on.replaceAll("&", "%26");
            sb.append("<subTag>");
            sb.append("<attrNme>"+on+"</attrNme>");
            sb.append("<attrVal>"+vlu+"</attrVal>");
            sb.append("</subTag>");
            }
            
            res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");

            return null;
        }
    
    public ActionForward creatememoPieChart(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
          res.setContentType("text/xml"); 
          res.setHeader("Cache-Control", "no-cache"); 
          String prp = util.nvl((String)req.getParameter("prp"));
          String dept = util.nvl((String)req.getParameter("dept"));
          HashMap dataDtl = (HashMap)session.getAttribute("PIEdataDtl");
          StringBuffer sb = new StringBuffer();
          ArrayList grpLst=new ArrayList();
          grpLst=(ArrayList)dataDtl.get(prp+"_GRP");
          for(int k=0;k<grpLst.size();k++){
          String grp=(String)grpLst.get(k);
          String vlu = util.nvl((String)dataDtl.get(prp+"_"+grp+"_"+dept));
          if(!vlu.equals("") && !vlu.equals("0")){
          sb.append("<subTag>");
          sb.append("<attrNme>"+grp+"</attrNme>");
          sb.append("<attrVal>"+vlu+"</attrVal>");
          sb.append("</subTag>");
          }
          }           

          res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");
         return null;
        }
    
    public ActionForward rptshapeclarityBar(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
          res.setContentType("text/xml"); 
          res.setHeader("Cache-Control", "no-cache"); 
          String key = util.nvl((String)req.getParameter("key"));
          HashMap dataDtl= (HashMap)session.getAttribute("dataDtl");
          ArrayList shapeLst= (ArrayList)dataDtl.get("SHAPE");
          String valavg="",valavgdis="",valcum_dis="";
          StringBuffer sb = new StringBuffer();
            for(int i=0;i<shapeLst.size();i++){
              String shVal=util.nvl((String)shapeLst.get(i));
              String keyString=key+"_"+shVal;
              valavg=util.nvl((String)dataDtl.get(keyString+"_AVG"));
              valavgdis=util.nvl((String)dataDtl.get(keyString+"_DIS"));
              valcum_dis=util.nvl((String)dataDtl.get(keyString+"_CUMDIS"));
                if(!valavg.equals("") && !valavg.equals("0")){
                sb.append("<subTag>");
                sb.append("<attrNme>"+shVal+"</attrNme>");
                sb.append("<attrVal>"+valavg+"</attrVal>");
                sb.append("</subTag>");
                }
            }
         res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");
         return null;
        }
    public ActionForward shapeclarityBar(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
          res.setContentType("text/xml"); 
          res.setHeader("Cache-Control", "no-cache"); 
          String key = util.nvl((String)req.getParameter("key"));
            HashMap dataDtl= (HashMap)session.getAttribute("dataDtl");
            ArrayList shapeLst= (ArrayList)dataDtl.get("SHAPE");
            String valavg="",valavgdis="";
          StringBuffer sb = new StringBuffer();
                for(int i=0;i<shapeLst.size();i++){
                  String shVal=util.nvl((String)shapeLst.get(i));
                  String keyString=key+"_"+shVal;
                  valavg=util.nvl((String)dataDtl.get(keyString+"_AVG"));
                  valavgdis=util.nvl((String)dataDtl.get(keyString+"_DIS"));
                    if(!valavg.equals("") && !valavg.equals("0")){
                    sb.append("<subTag>");
                    sb.append("<attrNme>"+shVal+"</attrNme>");
                    sb.append("<attrVal>"+valavg+"</attrVal>");
                    sb.append("</subTag>");
                    }
                }
         res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");
         return null;
        }
    
    public ActionForward createChartSizepurity(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
          res.setContentType("text/xml"); 
          res.setHeader("Cache-Control", "no-cache"); 
            String ttl="AVG";
            String szVal = util.nvl((String)req.getParameter("szVal"));
            String mfgrte = util.nvl((String)req.getParameter("mfgrte"));            HashMap dataDtl= (HashMap)session.getAttribute("dataDtl");
            ArrayList monthLst= (ArrayList)session.getAttribute("monthLst");
            ArrayList grpLst=(ArrayList)dataDtl.get("GRP");
            int monthLstsz=monthLst.size();
            String currentkey="",currentqty="",currentavg="",firstmonth="",firstkey="",firstavg="";
            double favg=0;
            double cavg=0;
          StringBuffer sb = new StringBuffer();
                for(int m=0;m<monthLstsz;m++){
                firstmonth=util.nvl((String)monthLst.get(0));
                String currentmonth=util.nvl((String)monthLst.get(m));
                String appendVal="";
                sb.append("<subTag>");
                sb.append("<attrNme>"+currentmonth+"</attrNme>");
                for(int l=0;l<grpLst.size();l++){
                String grp=util.nvl((String)grpLst.get(l));
                firstkey=szVal+"_"+firstmonth+"_"+grp;
                currentkey=szVal+"_"+currentmonth+"_"+grp;
                currentavg=util.nvl((String)dataDtl.get(currentkey+"_AVG"));
                firstavg=util.nvl((String)dataDtl.get(firstkey+"_AVG"));
                String percent="0";
                if(!currentavg.equals("") && !firstavg.equals("")){
                favg=Double.parseDouble(firstavg);
                cavg=Double.parseDouble(currentavg);
                percent=String.valueOf(util.roundToDecimals((100-((favg/cavg)*100)),2));
                }
                if(mfgrte.equals("")){
                    appendVal=appendVal+"A"+percent;
                }else if(mfgrte.equals("Y"))
                    appendVal=appendVal+"XML"+currentavg;
                }
                appendVal=appendVal.replaceFirst("XML", "");   
                sb.append("<attrVal>"+appendVal+"</attrVal>");
                sb.append("</subTag>");
                }
         res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");
         return null;
        }
    public ActionForward pricelinegraph(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
          res.setContentType("text/xml"); 
          res.setHeader("Cache-Control", "no-cache"); 
          StringBuffer sb = new StringBuffer();
          String key = util.nvl((String)req.getParameter("key"));
          HashMap dataDtl= (HashMap)session.getAttribute("dataDtl");
          String valavg="";
          ArrayList grpLst= (ArrayList)dataDtl.get("GRP");
          ArrayList monthLst= (ArrayList)dataDtl.get("MONTH");
          for(int j=0;j<monthLst.size();j++){
          String month=util.nvl((String)monthLst.get(j));
          String appendVal="";
          sb.append("<subTag>");
          sb.append("<attrNme>"+month+"</attrNme>");
          for(int i=0;i<grpLst.size();i++){
          String grp=util.nvl((String)grpLst.get(i));
          String keyString=key+"_"+grp+"_"+month;
          valavg=util.nvl((String)dataDtl.get(keyString+"_AVG"));
          appendVal=appendVal+"XML"+valavg;       
          }
          appendVal=appendVal.replaceFirst("XML", "");   
          sb.append("<attrVal>"+appendVal+"</attrVal>");
          sb.append("</subTag>");    
          }
         res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");
         return null;
        }
    public ActionForward monthwiselinegraph(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
          res.setContentType("text/xml"); 
          res.setHeader("Cache-Control", "no-cache"); 
          StringBuffer sb = new StringBuffer();
            String month="";
            String qty="",avg="";
          ArrayList summaryList = (ArrayList)session.getAttribute("summaryList");
            if(summaryList !=null && summaryList.size()>0){
              for(int i=0;i<summaryList.size();i++){
                HashMap summDtl = (HashMap)summaryList.get(i);
                month=util.nvl((String)summDtl.get("dspMM"));
                qty=util.nvl((String)summDtl.get("qty"));  
                avg=util.nvl((String)summDtl.get("avg"));
                    sb.append("<subTag>");
                    sb.append("<attrNme>"+month+"</attrNme>");
                    sb.append("<attrVal>"+qty+"XML"+avg+"</attrVal>");
                    sb.append("</subTag>");  
                }
            }
         res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");
         return null;
        }
    public ActionForward monthlyCmpBarGraph(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
                HttpSession session = req.getSession(false);
                InfoMgr info = (InfoMgr)session.getAttribute("info");
                DBUtil util = new DBUtil();
                DBMgr db = new DBMgr(); 
                db.setCon(info.getCon());
                util.setDb(db);
                util.setInfo(info);
                db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
                util.setLogApplNm(info.getLoApplNm());
              res.setContentType("text/xml"); 
              res.setHeader("Cache-Control", "no-cache"); 
              StringBuffer sb = new StringBuffer(); 
              ArrayList rowlst= new ArrayList(); 
              String key = util.nvl((String)req.getParameter("key"));
              String grid = util.nvl((String)req.getParameter("grid"));
              HashMap dataDtl= new HashMap();
              ResultSet rs=null;
              ArrayList gridLst= (ArrayList)session.getAttribute("gridLst");
              ArrayList vWPrpList = (ArrayList)session.getAttribute("memocomp_vw");
                int grLn = vWPrpList.indexOf(grid)+1;
                String gridSrt = "srt_00"+grLn;            
                String srchQ="";
                ArrayList dscLst=new ArrayList();
                dscLst.add("+");dscLst.add("=");dscLst.add("-"); 
                HashMap dscLstDtl=new HashMap();
                dscLstDtl.put("+","P");
                dscLstDtl.put("=","E"); 
                dscLstDtl.put("-","M");
                if(key.equals("EMP")){
                srchQ=" select n.nme ,  n.nme_idn" +
                             ", count(*) cnt , '+' Dsc\n" + 
                             " from gt_srch_rslt a, gt_srch_multi b, nme_v n\n" + 
                             " where a.stk_idn = b.stk_idn and a."+gridSrt+" < b."+gridSrt+"\n" + 
                             " and b.rln_idn = n.nme_idn\n" + 
                             " group by n.nme ,  n.nme_idn" + 
                             " UNION\n" + 
                             " select n.nme ,  n.nme_idn" +
                             ", count(*) cnt, '=' Dsc\n" + 
                             " from gt_srch_rslt a, gt_srch_multi b, nme_v n\n" + 
                             " where a.stk_idn = b.stk_idn and a."+gridSrt+" = b."+gridSrt+"\n" + 
                             " and b.rln_idn = n.nme_idn\n" + 
                             " group by n.nme ,  n.nme_idn" +
                             " UNION\n" + 
                             " select n.nme ,  n.nme_idn" + 
                             ", count(*) cnt, '-' Dsc\n" + 
                             " from gt_srch_rslt a, gt_srch_multi b, nme_v n\n" + 
                             " where a.stk_idn = b.stk_idn and a."+gridSrt+" > b."+gridSrt+"\n" + 
                             " and b.rln_idn = n.nme_idn\n" + 
                             " group by n.nme ,  n.nme_idn" +
                             " UNION\n" + 
                             " select n.nme ,  n.nme_idn" +
                             ", count(*) cnt,  'Total' Dsc\n" + 
                             " from gt_srch_rslt a, gt_srch_multi b, nme_v n\n" + 
                             " where a.stk_idn = b.stk_idn\n" + 
                             " and b.rln_idn = n.nme_idn\n" + 
                             " group by n.nme ,  n.nme_idn";
                }else{
                    srchQ=" select  to_number(to_char(b.pkt_dte, 'rrrrmm')) srt_mon, to_char(b.pkt_dte, 'MONrrrr') MON_YR\n" +
                                 ", count(*) cnt , '+' Dsc\n" + 
                                 " from gt_srch_rslt a, gt_srch_multi b, nme_v n\n" + 
                                 " where a.stk_idn = b.stk_idn and a."+gridSrt+" < b."+gridSrt+"\n" + 
                                 " and b.rln_idn = n.nme_idn\n" + 
                                 " group by to_number(to_char(b.pkt_dte, 'rrrrmm')), to_char(b.pkt_dte, 'MONrrrr')\n" + 
                                 " UNION\n" + 
                                 " select to_number(to_char(b.pkt_dte, 'rrrrmm')) srt_mon, to_char(b.pkt_dte, 'MONrrrr') MON_YR\n" +
                                 ", count(*) cnt, '=' Dsc\n" + 
                                 " from gt_srch_rslt a, gt_srch_multi b, nme_v n\n" + 
                                 " where a.stk_idn = b.stk_idn and a."+gridSrt+" = b."+gridSrt+"\n" + 
                                 " and b.rln_idn = n.nme_idn\n" + 
                                 " group by to_number(to_char(b.pkt_dte, 'rrrrmm')), to_char(b.pkt_dte, 'MONrrrr')\n" +
                                 " UNION\n" + 
                                 " select to_number(to_char(b.pkt_dte, 'rrrrmm')) srt_mon, to_char(b.pkt_dte, 'MONrrrr') MON_YR\n" +
                                 ", count(*) cnt, '-' Dsc\n" + 
                                 " from gt_srch_rslt a, gt_srch_multi b, nme_v n\n" + 
                                 " where a.stk_idn = b.stk_idn and a."+gridSrt+" > b."+gridSrt+"\n" + 
                                 " and b.rln_idn = n.nme_idn\n" + 
                                 " group by to_number(to_char(b.pkt_dte, 'rrrrmm')), to_char(b.pkt_dte, 'MONrrrr')\n" +
                                 " UNION\n" + 
                                 " select to_number(to_char(b.pkt_dte, 'rrrrmm')) srt_mon, to_char(b.pkt_dte, 'MONrrrr') MON_YR\n" +
                                 ", count(*) cnt,  'Total' Dsc\n" + 
                                 " from gt_srch_rslt a, gt_srch_multi b, nme_v n\n" + 
                                 " where a.stk_idn = b.stk_idn\n" + 
                                 " and b.rln_idn = n.nme_idn\n" + 
                                 " group by to_number(to_char(b.pkt_dte, 'rrrrmm')), to_char(b.pkt_dte, 'MONrrrr')\n";
                }

                ArrayList outLst = db.execSqlLst("srchQ", srchQ, new ArrayList());
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                 rs = (ResultSet)outLst.get(1);
                         while (rs.next()) {
                          String grp="";
                          if(key.equals("EMP")){
                          grp=util.nvl(rs.getString("nme_idn")); 
                          dataDtl.put(grp, util.nvl(rs.getString("nme")));
                          }else
                          grp=util.nvl(rs.getString("MON_YR"));  
                          if(!rowlst.contains(grp))
                          rowlst.add(grp);
                          String dsc=util.nvl(rs.getString("Dsc")); 
                          dataDtl.put(grp+"_"+dsc+"_"+grid,util.nvl(rs.getString("cnt")));   
                         }
                         rs.close(); pst.close();
                for(int i=0;i<rowlst.size();i++){
                String row = util.nvl((String)rowlst.get(i));
                String nme = util.nvl((String)dataDtl.get(row),row);
                sb.append("<subTag>");
                sb.append("<attrNme>"+nme+"</attrNme>");
                for(int j=0;j<dscLst.size();j++){
                String dsc=(String)dscLst.get(j);
                String dscval=util.nvl((String)dscLstDtl.get(dsc));
                String CNT=util.nvl((String)dataDtl.get(row+"_"+dsc+"_"+grid),"0");
                String GRANDCNT=util.nvl((String)String.valueOf(dataDtl.get(row+"_Total_"+grid)));
                double per=0.0;
                per=(Double.parseDouble(CNT)/Double.parseDouble(GRANDCNT))*100;
                sb.append("<"+dscval+">"+Math.round(per)+"</"+dscval+">");
                }
                sb.append("</subTag>"); 
                }
             res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");
             return null;
            }
    
    public ActionForward stockTally(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
                HttpSession session = req.getSession(false);
                InfoMgr info = (InfoMgr)session.getAttribute("info");
                DBUtil util = new DBUtil();
                DBMgr db = new DBMgr(); 
                db.setCon(info.getCon());
                util.setDb(db);
                util.setInfo(info);
                db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
                util.setLogApplNm(info.getLoApplNm());
              res.setContentType("text/xml"); 
              res.setHeader("Cache-Control", "no-cache"); 
              StringBuffer sb = new StringBuffer();
                HashMap pktDtl = (HashMap)session.getAttribute("pktDtl");
                ArrayList grpList = (ArrayList)session.getAttribute("grpList");
                HashMap grp3list = (HashMap)session.getAttribute("grp3List");
                ArrayList seqList = (ArrayList)session.getAttribute("seqLst");
                
                for(int i=0;i<grpList.size();i++){
                String mstkStt = (String)grpList.get(i);
                String issQ = util.nvl((String)pktDtl.get(mstkStt+"_IS_Q"));
                String issC = util.nvl((String)pktDtl.get(mstkStt+"_IS_C"));
                String rtQ = util.nvl((String)pktDtl.get(mstkStt+"_RT_Q"));
                String rtC = util.nvl((String)pktDtl.get(mstkStt+"_RT_C"));
                
                String  ttlQ ="";
                if(issQ.length()>0 && rtQ.length()>0){
                ttlQ = String.valueOf((Integer.parseInt(issQ) +Integer.parseInt(rtQ)));
                }else if(issQ.length()>0){
                 ttlQ = String.valueOf(Integer.parseInt(issQ));
                }else if(rtQ.length()>0){
                 ttlQ = String.valueOf(Integer.parseInt(rtQ));
                }
//                String  ttlC ="";
//                if(issC.length()>0 && rtC.length()>0){
//                ttlC = String.valueOf(Float.valueOf(issC) + Float.valueOf(rtC));
//                }else if(issC.length()>0){
//                 ttlC = String.valueOf(Float.valueOf(issC));
//                }else  if(rtC.length()>0){
//                  ttlC = String.valueOf(Float.valueOf(rtC));
//                }Total_Issue_Return
                    sb.append("<subTag>");
                    sb.append("<attrNme>"+util.nvl((String)grp3list.get(mstkStt),mstkStt)+"</attrNme>");
                    sb.append("<Total>"+util.nvl(ttlQ,"0")+"</Total>");
                    sb.append("<Issue>"+util.nvl((String)pktDtl.get(mstkStt+"_IS_Q"),"0")+"</Issue>");
                    sb.append("<Return>"+util.nvl((String)pktDtl.get(mstkStt+"_RT_Q"),"0")+"</Return>");
                    sb.append("</subTag>");     
                }

             res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");
             return null;
            }
    public ActionForward ioWeekChart(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
          res.setContentType("text/xml"); 
          res.setHeader("Cache-Control", "no-cache"); 
          StringBuffer sb = new StringBuffer();
            Integer OPENSTR = (Integer)session.getAttribute("OPENSTRWEEK");
              HashMap iosummWeekDtl = (HashMap)session.getAttribute("iosummWeekDtl");
              ArrayList colWeekList = (ArrayList)session.getAttribute("colWeekList");
            for(int i=0;i<colWeekList.size();i++){
            String fldVal = (String)colWeekList.get(i); 
            String newVal = util.nvl((String)iosummWeekDtl.get("NEW_"+fldVal));
            String soldVal = util.nvl((String)iosummWeekDtl.get("SOLD_"+fldVal));
            String newsoldVal = util.nvl((String)iosummWeekDtl.get("NEW_SOLD_"+fldVal));
            int newValInt =0;
            int soldValInt =0;
            int openingVal = 0;
            int closeVal = 0;
            int newSold = 0;
            if(!newVal.equals(""))
              newValInt = Integer.parseInt(newVal);
            if(!soldVal.equals(""))
              soldValInt = Integer.parseInt(soldVal);
            if(!newsoldVal.equals(""))
              newSold = Integer.parseInt(newsoldVal);
               openingVal = OPENSTR;
               closeVal = (openingVal + newValInt)-soldValInt-newSold;
               OPENSTR = closeVal;
                fldVal=fldVal.substring(0,2); 
                sb.append("<subTag>");
                sb.append("<attrNme>"+fldVal+"</attrNme>");   
                sb.append("<attrVal>"+newValInt+"XML"+openingVal+"XML"+newSold+"XML"+soldValInt+"XML</attrVal>");
                sb.append("</subTag>");    
                }
                res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");
         return null;
        }
    public ActionForward commonBarChart(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
          res.setContentType("text/xml"); 
          res.setHeader("Cache-Control", "no-cache"); 
          StringBuffer sb = new StringBuffer();
            HashMap mprp = info.getSrchMprp();
            String key = util.nvl((String)req.getParameter("key"));
            String row = util.nvl((String)req.getParameter("row"));
            String col = util.nvl((String)req.getParameter("col"));
            HashMap dataDtl = (HashMap)session.getAttribute("dataDtl");
            ArrayList statusLst = (ArrayList)session.getAttribute("statusLst");
            HashMap gridstructure = (HashMap)session.getAttribute("gridstructure");
            ArrayList gridcommLst=new ArrayList();
            ArrayList gridrowLst=new ArrayList();
            ArrayList gridcolLst=new ArrayList();
            gridcommLst=(ArrayList)gridstructure.get("COMM");
            gridrowLst=(ArrayList)gridstructure.get("ROW");
            gridcolLst=(ArrayList)gridstructure.get("COL");
            HashMap prp = info.getSrchPrp();
            ArrayList rowList=new ArrayList();
            ArrayList colList=new ArrayList();
            String gridByGrid = util.nvl((String)session.getAttribute("gridByGrid"),"PRP");
            if(gridByGrid.equals("PRP")){
            rowList = (ArrayList)prp.get(gridrowLst.get(0)+"V");
            colList = (ArrayList)prp.get(gridcolLst.get(0)+"V");
            }else{
              rowList = util.useDifferentArrayListUnique((ArrayList)prp.get(gridrowLst.get(0)+"G"));
              colList = util.useDifferentArrayListUnique((ArrayList)prp.get(gridcolLst.get(0)+"G"));
            }
            ArrayList rowListGt = (ArrayList)session.getAttribute("rowList");
            ArrayList colListGt  = (ArrayList)session.getAttribute("colList");
            String rowlprpTyp = util.nvl((String)mprp.get(gridrowLst.get(0)+"T"));
            String collprpTyp = util.nvl((String)mprp.get(gridcolLst.get(0)+"T"));
            if(rowlprpTyp.equals("T") || rowlprpTyp.equals("N")){
            rowList=new ArrayList();
            rowList=rowListGt;
            }
            if(collprpTyp.equals("T") || collprpTyp.equals("N")){
            colList=new ArrayList();
            colList=colListGt;
            }
            if(key.indexOf("~") > -1)
            key=key.replaceAll("\\~","+");
            if(!row.equals("")) { 
                String rowV=row ;  
            for(int n=0;n< colList.size();n++){
              String colV = (String)colList.get(n);
              if(colListGt.contains(colV)){
              sb.append("<subTag>");
              sb.append("<attrNme>"+colV+"</attrNme>");
              for(int st=0;st<statusLst.size();st++){
              String stt=(String)statusLst.get(st);    
              String keyQty = key+"@"+rowV+"@"+colV+"@"+stt+"@QTY";
              String keyAVG = key+"@"+rowV+"@"+colV+"@"+stt+"@AVG";
              String valQty = util.nvl((String)dataDtl.get(keyQty),"0");
              String valAVG = util.nvl((String)dataDtl.get(keyAVG),"0");  
              sb.append("<"+stt+"Qty>"+valQty+"</"+stt+"Qty>");
              sb.append("<"+stt+"Avg>"+valAVG+"</"+stt+"Avg>");   
            }
            sb.append("</subTag>");
            }
            }
            }else{
                for(int m=0;m< rowList.size();m++){
                  String rowV = (String)rowList.get(m);
                  if(rowListGt.contains(rowV)){
                  sb.append("<subTag>");
                  sb.append("<attrNme>"+rowV+"</attrNme>");
                  String colV = col; 
                  for(int st=0;st<statusLst.size();st++){
                  String stt=(String)statusLst.get(st);    
                  String keyQty = key+"@"+rowV+"@"+colV+"@"+stt+"@QTY";
                  String valQty = util.nvl((String)dataDtl.get(keyQty),"0");
                  String keyAVG = key+"@"+rowV+"@"+colV+"@"+stt+"@AVG";
                  String valAVG = util.nvl((String)dataDtl.get(keyAVG),"0");    
                  sb.append("<"+stt+"Qty>"+valQty+"</"+stt+"Qty>");
                  sb.append("<"+stt+"Avg>"+valAVG+"</"+stt+"Avg>");   
                  }
                  sb.append("</subTag>");
                }
                }
                } 
            res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");
         return null;
        }
    public ActionForward commonBarChartSelection(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
          res.setContentType("text/xml"); 
          res.setHeader("Cache-Control", "no-cache"); 
          StringBuffer sb = new StringBuffer();
            String key = util.nvl((String)req.getParameter("key"));
            String row = util.nvl((String)req.getParameter("row"));
            String col = util.nvl((String)req.getParameter("col"));
            String typ = util.nvl((String)req.getParameter("typ"));
            HashMap dataDtl = (HashMap)session.getAttribute("dataDtl");
            HashMap prp = info.getSrchPrp();
            ArrayList statusLst = (ArrayList)session.getAttribute("statusLst");
            HashMap gridstructure = (HashMap)session.getAttribute("gridstructure");
            ArrayList vwPrpLst = (ArrayList)session.getAttribute("ANLS_VW");
            ArrayList gridcommLst=new ArrayList();
            ArrayList gridrowLst=new ArrayList();
            ArrayList gridcolLst=new ArrayList();
            HashMap mprp = info.getSrchMprp();
            gridcommLst=(ArrayList)gridstructure.get("COMM");
            gridrowLst=(ArrayList)gridstructure.get("ROW");
            gridcolLst=(ArrayList)gridstructure.get("COL");
            ArrayList rowList=new ArrayList();
            ArrayList colList=new ArrayList();
            String gridByGrid = util.nvl((String)session.getAttribute("gridByGrid"),"PRP");
            if(gridByGrid.equals("PRP")){
            rowList = (ArrayList)prp.get(gridrowLst.get(0)+"V");
            colList = (ArrayList)prp.get(gridcolLst.get(0)+"V");
            }else{
              rowList = util.useDifferentArrayListUnique((ArrayList)prp.get(gridrowLst.get(0)+"G"));
              colList = util.useDifferentArrayListUnique((ArrayList)prp.get(gridcolLst.get(0)+"G"));
            }
            ArrayList rowListGt = (ArrayList)session.getAttribute("rowList");
            ArrayList colListGt  = (ArrayList)session.getAttribute("colList");
            String colPrp = util.prpsrtcolumn("prp",vwPrpLst.indexOf(gridcolLst.get(0))+1);
            String rowPrp = util.prpsrtcolumn("prp",vwPrpLst.indexOf(gridrowLst.get(0))+1);
            String rowlprpTyp = util.nvl((String)mprp.get(gridrowLst.get(0)+"T"));
            String collprpTyp = util.nvl((String)mprp.get(gridcolLst.get(0)+"T"));
            if(rowlprpTyp.equals("T") || rowlprpTyp.equals("N")){
            rowList=new ArrayList();
            rowList=rowListGt;
            }
            if(collprpTyp.equals("T") || collprpTyp.equals("N")){
            colList=new ArrayList();
            colList=colListGt;
            }
            int gridcommLstsz=gridcommLst.size();
            String condQ="";
            if(key.indexOf("~") > -1)
            key=key.replaceAll("\\~","+");
            String[] param = key.split("@");
            for (int i = 0; i < gridcommLstsz; i++) {
            int index = vwPrpLst.indexOf(gridcommLst.get(i))+1;    
            String fldprp=util.prpsrtcolumn("prp",index);
            condQ += " and ( "+fldprp+"= '+"+param[i].trim()+"' or "+fldprp+"= '"+param[i]+"') ";
            }
    //            if(!row.equals("ALL")){
    //            int index = vwPrpLst.indexOf(gridrowLst.get(0))+1;
    //            String fldprp=util.prpsrtcolumn("prp",index);
    //            condQ +=" and "+fldprp+" in ('"+row+"') ";
    //            }
    //            if(!col.equals("ALL")){
    //            int index = vwPrpLst.indexOf(gridcolLst.get(0))+1;
    //            String fldprp=util.prpsrtcolumn("prp",index);
    //            condQ +=" and "+fldprp+" in ('"+col+"') ";
    //            }
            
            String commprpQ=getconQ("prp",gridcommLst,vwPrpLst);
            String[] rowparam=null;
               String[] colparam=null;
               ArrayList rowListSelected = new ArrayList();
               ArrayList colListSelected  = new ArrayList();
               if(row.equals("ALL")){
               rowListSelected.addAll(rowListGt);
               rowListSelected.remove("ALL");
               rowListSelected.remove("ALL");
               }
               if(col.equals("ALL")){
               colListSelected.addAll(colListGt);
               colListSelected.remove("ALL");
               colListSelected.remove("ALL");
               }
               if(!row.equals("") && !row.equals("ALL")){
              rowparam = row.split(",");
              for(int k=0;k< rowparam.length;k++){
              String rowp=rowparam[k];
              rowListSelected.add(rowp);
              }
              }
               if(!col.equals("") && !col.equals("ALL")){
              colparam = col.split(",");
              for(int k=0;k< colparam.length;k++){
              String colp=colparam[k];
              colListSelected.add(colp);
              }}
              if(typ.equals("COL")) { 
              String rowListItm = rowListSelected.toString();
              rowListItm = rowListItm.replaceAll("\\[","");
              rowListItm = rowListItm.replaceAll("\\]","");
              rowListItm=util.getVnm(rowListItm);
            for(int n=0;n< colList.size();n++){
                String colV = (String)colList.get(n);
                if(colListSelected.contains(colV)){
                int index = vwPrpLst.indexOf(gridcolLst.get(0))+1;    
                String fldprp=util.prpsrtcolumn("prp",index);
                String subcondQ =" and "+fldprp+" in ('"+colV+"') ";
                int indexcol = vwPrpLst.indexOf(gridrowLst.get(0))+1;    
                String fldprpcol=util.prpsrtcolumn("prp",indexcol);
                subcondQ +=" and "+fldprpcol+" in ("+rowListItm+") ";
                sb.append("<subTag>");
                sb.append("<attrNme>"+colV+"</attrNme>");
                for(int st=0;st<statusLst.size();st++){
                String stt=(String)statusLst.get(st);
                String getColTotal="select sum(qty) qty ,trunc(sum(quot*trunc(cts,2))/sum(trunc(cts, 2)),0) avg,"+
                "stt,"+commprpQ+
                " from gt_srch_rslt where 1 = 1 " +condQ+ " and stt = '"+stt+"'"+subcondQ+" group by "+commprpQ+",stt" ;

                  ArrayList outLst = db.execSqlLst("getColTotal", getColTotal, new ArrayList());
                  PreparedStatement pst = (PreparedStatement)outLst.get(0);
                  ResultSet rs = (ResultSet)outLst.get(1);
                  if(rs.next()){
                  sb.append("<"+stt+"Qty>"+rs.getString("qty")+"</"+stt+"Qty>");
                  sb.append("<"+stt+"Avg>"+rs.getString("avg")+"</"+stt+"Avg>");  
                  }else{
                  sb.append("<"+stt+"Qty>0.0</"+stt+"Qty>");
                  sb.append("<"+stt+"Avg>0.0</"+stt+"Avg>");    
                  }
                  rs.close(); pst.close();
              }
                  sb.append("</subTag>");
              }
              }
              }else{
                  String colListItm = colListSelected.toString();
                  colListItm = colListItm.replaceAll("\\[","");
                  colListItm = colListItm.replaceAll("\\]","");
                  colListItm=util.getVnm(colListItm);
                  for(int n=0;n< rowList.size();n++){
                  String rowV = (String)rowList.get(n);
                  if(rowListSelected.contains(rowV)){
                  int index = vwPrpLst.indexOf(gridcolLst.get(0))+1;    
                  String fldprp=util.prpsrtcolumn("prp",index);
                  String subcondQ =" and "+fldprp+" in ("+colListItm+") ";
                  int indexcol = vwPrpLst.indexOf(gridrowLst.get(0))+1;    
                  String fldprpcol=util.prpsrtcolumn("prp",indexcol);
                  subcondQ +=" and "+fldprpcol+" in ('"+rowV+"') ";
                  sb.append("<subTag>");
                  sb.append("<attrNme>"+rowV+"</attrNme>");
                  for(int st=0;st<statusLst.size();st++){
                  String stt=(String)statusLst.get(st);     
                 String getrowTotal="select sum(qty) qty ,trunc(sum(quot*trunc(cts,2))/sum(trunc(cts, 2)),0) avg,"+
                 "stt,"+commprpQ+
                 " from gt_srch_rslt where 1 = 1 " +condQ+ " and stt = '"+stt+"'"+subcondQ+" group by "+commprpQ+",stt" ;

                          ArrayList outLst = db.execSqlLst("getrowTotal", getrowTotal, new ArrayList());
                          PreparedStatement pst = (PreparedStatement)outLst.get(0);
                          ResultSet rs = (ResultSet)outLst.get(1);
                      if(rs.next()){
                      sb.append("<"+stt+"Qty>"+rs.getString("qty")+"</"+stt+"Qty>");
                      sb.append("<"+stt+"Avg>"+rs.getString("avg")+"</"+stt+"Avg>");  
                      }else{
                      sb.append("<"+stt+"Qty>0.0</"+stt+"Qty>");
                      sb.append("<"+stt+"Avg>0.0</"+stt+"Avg>");    
                      }
                          rs.close(); pst.close();
                      }
                      sb.append("</subTag>"); 
                }
               } 
              }
            res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");
         return null;
        }
    
    public String getconQ(String column,ArrayList Lst,ArrayList vwList){
        String rtnQ="";
        for (int i = 0; i < Lst.size(); i++) {
        int index = vwList.indexOf(Lst.get(i))+1;    
        String fld = column+"_";
        if (index < 10)
        fld += "00" + index;
        else if (index < 100)
        fld += "0" + index;
        rtnQ += ", " + fld;
        }
        rtnQ=rtnQ.replaceFirst("\\,", "");
        return rtnQ;
    }
    public ActionForward Weeksummary(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
          res.setContentType("text/xml"); 
          res.setHeader("Cache-Control", "no-cache"); 
            HashMap summaryList = (HashMap)session.getAttribute("summaryList");
            HashMap summListdate = (HashMap)session.getAttribute("summListdate");
            ArrayList weekList = (ArrayList)session.getAttribute("weekList");
            ArrayList statusLst = (ArrayList)session.getAttribute("statusLst");
            String qtyVal="";
            String priceVal="";
            StringBuffer sb = new StringBuffer();
            for(int j=0;j<weekList.size();j++){
            String week=util.nvl((String)weekList.get(j));
            sb.append("<subTag>");
            sb.append("<attrNme>"+week+"</attrNme>");    
            for(int i=0;i<statusLst.size();i++){
                String stt=util.nvl((String)statusLst.get(i));
                HashMap summDtl = (HashMap)summaryList.get(stt+"_"+week);
                if(summDtl!=null && summDtl.size()>0){     
                qtyVal=util.nvl((String)summDtl.get("qty"),"0");
                priceVal=util.nvl((String)summDtl.get("avg"),"0");
                sb.append("<"+stt+"Qty>"+qtyVal+"</"+stt+"Qty>");
                sb.append("<"+stt+"Avg>"+priceVal+"</"+stt+"Avg>"); 
                }else{
                    sb.append("<"+stt+"Qty>0</"+stt+"Qty>");
                    sb.append("<"+stt+"Avg>0</"+stt+"Avg>");   
                }
            }
            sb.append("</subTag>"); 
            }         

          res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");
         return null;
        }
    public ActionForward callageGraph(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
          res.setContentType("text/xml"); 
          res.setHeader("Cache-Control", "no-cache"); 
            StringBuffer sb = new StringBuffer();
            ArrayList AgeDtl = (ArrayList)session.getAttribute("AgeDtl");
            HashMap ageSubDtl=new HashMap();
            for(int i=0;i<AgeDtl.size();i++){
            ageSubDtl=new HashMap(); 
            ageSubDtl=(HashMap)AgeDtl.get(i);
            String vlu=util.nvl((String)ageSubDtl.get("vlu"),"0");
            String qty=util.nvl((String)ageSubDtl.get("qty"),"0"); 
            String cts=util.nvl((String)ageSubDtl.get("cts"),"0");
            String DSP_MON2=util.nvl((String)ageSubDtl.get("DSP_MON2"),"A");  
            sb.append("<subTag>");
            sb.append("<Month>"+DSP_MON2+"</Month>");
            sb.append("<Qty>"+qty+"</Qty>"); 
            sb.append("<Cts>"+cts+"</Cts>");
            sb.append("<Vlu>"+vlu+"</Vlu>"); 
            sb.append("</subTag>"); 
            }        

          res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");
         return null;
        }
    public ActionForward agepie(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
          res.setContentType("text/xml"); 
          res.setHeader("Cache-Control", "no-cache"); 
            StringBuffer sb = new StringBuffer();
            ArrayList AgeDtl = (ArrayList)session.getAttribute("AgeDtl");
            HashMap ageSubDtl=new HashMap();
            for(int i=0;i<AgeDtl.size();i++){
            ageSubDtl=new HashMap(); 
            ageSubDtl=(HashMap)AgeDtl.get(i);
            String vlu=util.nvl((String)ageSubDtl.get("vlu"),"0");
            String DSP_MON2=util.nvl((String)ageSubDtl.get("DSP_MON2"),"A");  
            DSP_MON2=DSP_MON2.replaceAll("\\/","");
            sb.append("<subTag>");
            sb.append("<attrNme>"+DSP_MON2+"</attrNme>");
            sb.append("<attrVal>"+vlu+"</attrVal>"); 
            sb.append("</subTag>"); 
            }        

          res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");
         return null;
        }
    public float Round(float Rval, int Rpl) {
      float p = (float)Math.pow(10,Rpl);
      Rval = Rval * p;
      float tmp = Math.round(Rval);
      return (float)tmp/p;
     }
    public AjaxRptAction() {
        super();
    }
    
}
