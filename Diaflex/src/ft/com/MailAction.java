package ft.com;

import ft.com.dao.UIForms;
import ft.com.marketing.SearchQuery;

import java.io.IOException;

import java.net.InetSocketAddress;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.Hashtable;
import java.util.Vector;

import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.spy.memcached.MemcachedClient;

public class MailAction {
    HttpSession session ;
    InfoMgr info ;
    DBUtil util ;
    HtmlMailUtil html;
    DBMgr db=null;
    public MailAction() {
        super();
    }
    
    public StringBuffer mailToHED(HttpServletRequest req ,HttpServletResponse res, String name){
        init(req,res);
        StringBuffer msg = new StringBuffer();
        msg.append(html.head(""));
        msg.append(html.body());
        msg.append("<p>TO,<br></br>");
        msg.append(""+name+"</p>");
        
        return msg;
    }
    public StringBuffer mailHeader(HttpServletRequest req , HttpServletResponse res){
        init(req,res);
        StringBuffer msg = new StringBuffer();
       
        msg.append(html.body());
        msg.append("<p><b>TO,</b><br></br>");
        msg.append("<b>J B & Brothers</b></p><br></br> ");
        msg.append(html.table("inv", 1));
        msg.append(html.tr());
        msg.append(html.td("STOCK LOC"));
        msg.append(html.td(""));
        msg.append("</tr>");
        msg.append(html.tr());
        msg.append(html.td("DATE"));
        msg.append(html.td(util.getToDte()));
        msg.append("</tr>");
//        msg.append(html.tr());
//        msg.append(html.td("Contact Person"));
//        msg.append(html.td(info.getCont_prsn()));
//        msg.append("</tr>");
        msg.append(html.tr());
        msg.append(html.td("BUYER"));
        msg.append(html.td(info.getByrNm()));
        msg.append("</tr>");
        
        msg.append(html.tr());
        msg.append(html.td("TERMS"));
        msg.append(html.td(info.getTrms()));
        msg.append("</tr>");
//        msg.append(html.tr());
//        msg.append(html.td("Broker"));
//        msg.append(html.td(info.getBrkNme()));
//        msg.append("</tr>");
        msg.append(html.tr());
        msg.append(html.td("Sales Executive"));
        msg.append(html.td(info.getSaleExNme()));
        msg.append("</tr>");
        msg.append("</table>");
        
        return msg;
    }
    
    
    
    public StringBuffer mailTR(HttpServletRequest req ,HttpServletResponse res,  HashMap pktData , int count){
        init(req,res);
        HashMap mprp = info.getMprp();
        StringBuffer msg = new StringBuffer();
        String stt = util.nvl((String)pktData.get("stt"));
        String avb = "YES";
        if(stt.equals("MKIS"))
            avb = "NO";
        ArrayList invPrpList = info.getInvPrpLst();
        if(count==1){
            msg.append("<p><b>we are Interested to buy following Stone Please do the needful.</b></p>");
            msg.append(html.table("inv", 1));
            msg.append(html.tr());
            msg.append(html.th("Sr No."));
            msg.append(html.th("Code"));
            for(int j=0;j<invPrpList.size();j++){
             String prp = (String)invPrpList.get(j);
             String prpDsc = (String)mprp.get(prp);
             msg.append(html.th(prpDsc));
            }
            msg.append(html.th("Avaliable"));
            msg.append("</tr>");
        }
        msg.append(html.tr());
        msg.append(html.td(String.valueOf(count)));
        msg.append(html.td(util.nvl((String)pktData.get("vnm"))));
        for(int j=0;j<invPrpList.size();j++){
         String prp = (String)invPrpList.get(j);
         msg.append(html.td(util.nvl((String)pktData.get(prp))));
        }
        msg.append(html.td(avb));
        msg.append("</tr>");
        return msg;
    }
    
    public StringBuffer mailPepTR(HttpServletRequest req ,HttpServletResponse res,  HashMap pepParams){
        init(req,res);
        HashMap mprp = info.getMprp();
        StringBuffer msg = new StringBuffer();
        ArrayList seachList = new ArrayList();
        String stkIdn = (String)pepParams.get("stkIdn");
        int count = Integer.parseInt((String)pepParams.get("count"));
        HashMap pktData = null;
          for(int i=0;i<seachList.size();i++){
           HashMap stoneDtl = (HashMap)seachList.get(i);
            String stkID = (String)stoneDtl.get("stk_idn");
          
            if(stkID.equals(stkIdn)){
              pktData  = stoneDtl ;
                break;
               }
          }
        String stt = util.nvl((String)pktData.get("stt"));
        String avb = "YES";
        if(stt.equals("MKIS"))
            avb = "NO";
        ArrayList invPrpList = info.getInvPrpLst();
        if(count==1){
            msg.append("<p><b>Following stones we are put expected price</b></p>");
            msg.append(html.table("inv", 1));
            msg.append(html.tr());
            msg.append(html.th("Sr No."));
            msg.append(html.th("Code"));
            for(int j=0;j<invPrpList.size();j++){
             String prp = (String)invPrpList.get(j);
             String prpDsc = (String)mprp.get(prp);
            msg.append(html.th(prpDsc));
            if(prp.equals("RTE")){
                msg.append(html.th("Exp.Price"));
                msg.append(html.th("Exp.Off"));
                if(!(stt.equals("MKPP")|| stt.equals("MKLB")))
                    msg.append(html.th("ValidDate"));
            }
            }
            msg.append(html.th("Avaliable"));
            msg.append("</tr>");
        }
        msg.append(html.tr());
        msg.append(html.td(String.valueOf(count)));
        msg.append(html.td(util.nvl((String)pktData.get("vnm"))));
        for(int j=0;j<invPrpList.size();j++){
         String prp = (String)invPrpList.get(j);
         msg.append(html.td(util.nvl((String)pktData.get(prp))));
            if(prp.equals("RTE")){
                msg.append(html.th((String)pepParams.get("pepRte")));
                msg.append(html.th((String)pepParams.get("pepDis")));
                if(!(stt.equals("MKPP")|| stt.equals("MKLB")))
                    msg.append(html.th((String)pepParams.get("pepDte")));
            }
        }
        msg.append(html.td(avb));
        msg.append("</tr>");
        return msg;
    }
    
    public StringBuffer mailSLTR(HttpServletRequest req ,HttpServletResponse res , HashMap pepParams){
        init(req,res);
        HashMap mprp = info.getMprp();
        StringBuffer msg = new StringBuffer();
        ArrayList seachList = new ArrayList();
        String stkIdn = (String)pepParams.get("stkIdn");
        int count = Integer.parseInt((String)pepParams.get("count"));
        HashMap pktData = null;
          for(int i=0;i<seachList.size();i++){
           HashMap stoneDtl = (HashMap)seachList.get(i);
            String stkID = (String)stoneDtl.get("stk_idn");
          
            if(stkID.equals(stkIdn)){
              pktData  = stoneDtl ;
                break;
               }
          }
        String stt = util.nvl((String)pktData.get("stt"));
        String avb = "YES";
        if(stt.equals("MKIS"))
            avb = "NO";
        ArrayList invPrpList = info.getInvPrpLst();
        if(count==1){
            msg.append("<p><b>Following stones we are put In Short List</b></p>");
            msg.append(html.table("inv", 1));
            msg.append(html.tr());
            msg.append(html.th("Sr No."));
            msg.append(html.th("Code"));
            for(int j=0;j<invPrpList.size();j++){
             String prp = (String)invPrpList.get(j);
             String prpDsc = (String)mprp.get(prp);
            msg.append(html.th(prpDsc));
            }
            msg.append(html.th("Avaliable"));
            msg.append("</tr>");
        }
        msg.append(html.tr());
        msg.append(html.td(String.valueOf(count)));
        msg.append(html.td(util.nvl((String)pktData.get("vnm"))));
        for(int j=0;j<invPrpList.size();j++){
         String prp = (String)invPrpList.get(j);
         msg.append(html.td(util.nvl((String)pktData.get(prp))));
            
        }
        msg.append(html.td(avb));
        msg.append("</tr>");
        return msg;
    }
    public void TRF_TO_MKTG_MAIL(HashMap params , HttpServletRequest req , HttpServletResponse res) {
       init(req, res);
       boolean isSent = false;
        HashMap mailDtl = getAppMailMAP("TRF_MAIL");
        int issueId = (Integer)params.get("issueId");
        if(mailDtl!=null && mailDtl.size()>0){
        String isSND = util.nvl((String)mailDtl.get("ISSEND"));
        if(isSND.equals("Y")){
        try {
                int ct = db.execUpd("delete gtSrch", "DELETE FROM gt_srch_rslt ", new ArrayList());
                
                ArrayList invPrpLst = new ArrayList();
                String getInvPrp =
                        "Select prp from rep_prp where mdl = 'TRF_TO_MKTG_MAIL' and flg = 'Y' order by rnk ";
                ResultSet rs =db.execSql(" Inv Prp ", getInvPrp, new ArrayList());
                while (rs.next()) {
                 invPrpLst.add(rs.getString("prp"));
                }
                rs.close();
            String iss_pkt = "ISS_RTN_PKG.TRF_TO_MKTG_MAIL(pIssId => ?, pMdl => ?)";
            ArrayList ary = new ArrayList();
            ary.add(String.valueOf(issueId));
            ary.add("TRF_TO_MKTG_MAIL");
             ct = db.execCall("iss_pkt", iss_pkt, ary);
            HashMap mprp = info.getMprp();
            StringBuffer msg=new StringBuffer();
            msg.append(html.body());
            msg.append(html.table("inv", 1));
                msg.append("<tr>");
                msg.append(html.th("Sr"));
                msg.append(html.th("Packet Code"));
                for(int i=0; i < invPrpLst.size(); i++) {
                    String prp = (String)invPrpLst.get(i);
                    String prpDsc = (String)mprp.get(prp);
                    msg.append(html.th(prpDsc));
                 }
                msg.append("</tr>");
            
                String getInvPkts = " select  stk_idn, a.qty, trunc(a.cts,2) cts , a.rap_rte , a.quot , a.rap_dis , vnm   "
                       + util.getGTPrpLst(invPrpLst.size())
                      + " from gt_srch_rslt a order by a.sk1 ";
                rs = db.execSql(" Inv View ", getInvPkts, new ArrayList());
                int n = 1;
                   int ttl_qty = 0;
                   double ttl_cts = 0;
                while (rs.next()) {
                   int qty = rs.getInt("qty");
                   double  cts = rs.getDouble("cts");
                   ttl_qty = ttl_qty+1;
                    ttl_cts = ttl_cts+cts;
                  
                    msg.append(html.tr());
                    msg.append(html.td(Integer.toString(n++)));
                    msg.append(html.td(util.nvl(rs.getString("vnm"))));

                   
                    for (int i = 0; i < invPrpLst.size(); i++) {
                        String lprp = (String)invPrpLst.get(i);
                        String fld = "prp_";
                       
                        int j = i + 1;
                        if (j < 10)
                            fld += "00" + j;
                        else if (j < 100)
                            fld += "0" + j;
                        else if (j > 100)
                            fld += j;
                        boolean dsp = true;
                        if(lprp.equals("RTE"))
                            fld = "quot";
                         if(lprp.equals("RAP_RTE"))
                             fld = "rap_rte";
                         if(lprp.equals("RAP_DIS"))
                             fld = "rap_dis";
                        String fldVal = rs.getString(fld);
                      
                        if ((fldVal == null) || (fldVal.equals("NA")))
                            fldVal = "&nbsp;";
                        
                        msg.append(html.td(fldVal));
                         }
                  msg.append("</tr>");
                    isSent = true;
                   }
                rs.close();
                  
                  msg.append(" </table>");
                   msg.append("</body>");
                   msg.append("</html>"); 
                GenMail mail = new GenMail();
                HashMap dbmsInfo = info.getDmbsInfoLst();
                info.setSmtpHost((String)dbmsInfo.get("SMTPHost"));
                info.setSmtpPort(Integer.parseInt((String)dbmsInfo.get("SMTPPort")));
                info.setSmtpUsr((String)dbmsInfo.get("SMTPUsr"));
                info.setSmtpPwd((String)dbmsInfo.get("SMTPPwd"));
                mail.setInfo(info);    
                mail.init();
                String senderID =(String)dbmsInfo.get("SENDERID");
                if(senderID.equals("NA"))
                    senderID = info.getSaleExeEml();
                String senderNm =(String)dbmsInfo.get("SENDERNM");
                mail.setSender(senderID, senderNm);
                String mailsub = util.nvl((String)mailDtl.get("MAILSUB"));
                mail.setSubject(mailsub);
                String eml = util.nvl((String)mailDtl.get("MLIST"));
                String[] emlLst = eml.split(",");
                 if(emlLst!=null){
                   for(int i=0 ; i <emlLst.length; i++)
                    {
                     mail.setTO(emlLst[i]);
                    }
                   }
               
                if(!senderID.equals("NA"))
                mail.setReplyTo(info.getSaleExeEml());
                //mail.setBCC("purav@faunatechnologies.com");
                String mailMag = msg.toString();
                mail.setMsgText(mailMag);
            if(isSent){
                mail.send("");
            }
            
            } catch (SQLException sqle) {
                    // TODO: Add catch code
                    sqle.printStackTrace();
           }
                
    }}}
    public void sendRtnMemoMail(HashMap params , HttpServletRequest req , HttpServletResponse res) {
       init(req, res);
        HashMap mailDtl = getAppMailMAP("MAIL_RTN");
        if(mailDtl!=null && mailDtl.size()>0){
            String isSND = util.nvl((String)mailDtl.get("ISSEND"));
            if(isSND.equals("Y")){
        ArrayList ary=null;
        ResultSet rs=null;
        HashMap logDetails=new HashMap();
        String byrNm="";
        int byrId=0;
        int memoId = (Integer)params.get("memoId");
        ArrayList pktList = (ArrayList)params.get("pktList");
        String rtnPcs = pktList.toString();
        rtnPcs = rtnPcs.replace('[','(');
        rtnPcs = rtnPcs.replace(']',')');
        String byrEml="" , trms="";
        double exh_rte = 1;
        ary = new ArrayList();
        int relId=0;
        ary.add(String.valueOf(memoId));
        rs = db.execSql("get rel ID","select nmerel_idn ,  byr.get_trms(nmerel_idn) trms , initcap(byr.get_nm(nme_idn)) bym ,  nme_idn , byr.get_eml(nme_idn) eml , exh_rte from mjan where idn = ?", ary);
        try {
            if (rs.next()) {
                relId = rs.getInt("nmerel_idn");
                byrNm = util.nvl(rs.getString("bym"));
                byrEml = util.nvl(rs.getString("eml"));
                trms = util.nvl(rs.getString("trms"));
                byrId = rs.getInt("nme_idn");
                exh_rte = rs.getDouble("exh_rte");
                ary = new ArrayList();
                ary.add(String.valueOf(byrId));
                rs = db.execSql("get rel ID","select  initcap(byr.get_nm(emp_idn)) saleNme ,   byr.get_eml(emp_idn) saleEml from mnme where nme_idn = ?", ary);
                if (rs.next()) {
                    info.setSaleExNme(rs.getString("saleNme"));
                    info.setSaleExeEml(rs.getString("saleEml"));
                }
            
            }
            rs.close();
        
       int ct = db.execUpd("delete gtSrch", "DELETE FROM gt_srch_rslt ", new ArrayList());
       
            ArrayList invPrpLst = new ArrayList();
               
            String getInvPrp = "Select prp from rep_prp where mdl = 'MEMO_RTRN' and flg = 'Y' order by rnk "; 
            rs = db.execSql(" Inv Prp ", getInvPrp, new ArrayList());
            while(rs.next()) {
                invPrpLst.add(rs.getString("prp"))  ;  
             }
            rs.close();
           String gtInsert ="insert into gt_srch_rslt (stk_idn , vnm , fquot , quot , cts , sk1 , rap_rte , flg ,  cmp , mrte ) " + 
           " select mstk_idn, b.vnm ,  nvl(fnl_sal, a.quot) , a.quot,  b.cts,   b.sk1, b.rap_rte ,'M' ,  cmp , upr " + 
           " from jandtl a, mstk b where a.mstk_idn = b.idn  and a.idn = ?  and a.mstk_idn in "+rtnPcs ;
            ary = new ArrayList();
           ary.add(String.valueOf(memoId));
           ct =  db.execUpd("gtInsert", gtInsert, ary);
            
            String pktPrp = "pkgmkt.pktPrp(0,?)";
            ary = new ArrayList();
            ary.add("MEMO_RTRN");
            ct = db.execCall(" Srch Prp ", pktPrp, ary);
            
            
            String getInvPkts = " select  stk_idn, a.qty, trunc(a.cts,2) cts , a.fquot , a.quot , a.vnm , nvl(a.mrte, a.cmp) rte , nvl(fquot, quot) memoQuot , a.rap_rte "
                + ",  trunc((((nvl(fquot, quot)/"+exh_rte+")/greatest(rap_rte,1))*100)-100,2) byrDis , trunc(((nvl(a.mrte, a.cmp)/greatest(a.rap_rte,1))*100)-100,2) dis  "
                + util.getGTPrpLst(invPrpLst.size())
                + " from gt_srch_rslt a where a.flg = 'M' order by a.sk1 ";
            
           StringBuffer msg=new StringBuffer();
            msg.append(html.body());
           
            msg.append(html.table("inv", 1));
           
            msg.append(html.tr());
            msg.append(html.td("DATE"));
            msg.append(html.td(util.getToDte()));
            msg.append("</tr>");
         
            msg.append(html.tr());
            msg.append(html.td("BUYER"));
            msg.append(html.td(byrNm));
            msg.append("</tr>");
            
            msg.append(html.tr());
            msg.append(html.td("TERMS"));
            msg.append(html.td(trms));
            msg.append("</tr>");
         
            msg.append(html.tr());
            msg.append(html.td("Sales Executive"));
            msg.append(html.td(info.getSaleExNme()));
            msg.append("</tr>");
            
            msg.append(html.tr());
            msg.append(html.td("Memo ID"));
            msg.append(html.td(String.valueOf(memoId)));
            msg.append("</tr>");
            msg.append("</table>");
            msg.append("<br></br>");
        
         msg.append(html.table("inv", 1));
        
        
         HashMap mprp = info.getMprp();
         
        
         msg.append("<tr>");
         msg.append(html.th("Sr"));
         msg.append(html.th("Packet Code"));
        
         for(int i=0; i < invPrpLst.size(); i++) {
             String prp = (String)invPrpLst.get(i);
             String prpDsc = (String)mprp.get(prp);
             msg.append(html.th(prpDsc));
            
         }
         msg.append(html.th("Rap Rte"));
        msg.append(html.th("Byr Dis"));
        msg.append(html.th("Quot"));
        msg.append("</tr>");
         
         rs = db.execSql(" Inv View ", getInvPkts, new ArrayList());
         int n = 1;
            int ttl_qty = 0;
            double ttl_cts = 0;
         while (rs.next()) {
            int qty = rs.getInt("qty");
            double  cts = rs.getDouble("cts");
            ttl_qty = ttl_qty+1;
             ttl_cts = ttl_cts+cts;
           
             msg.append(html.tr());
             msg.append(html.td(Integer.toString(n++)));
             msg.append(html.td(util.nvl(rs.getString("vnm"))));

            
             for (int i = 0; i < invPrpLst.size(); i++) {
                 String fld = "prp_";
                
                 int j = i + 1;
                 if (j < 10)
                     fld += "00" + j;
                 else if (j < 100)
                     fld += "0" + j;
                 else if (j > 100)
                     fld += j;
                 boolean dsp = true;
                 String fldVal = rs.getString(fld);
               
                 if ((fldVal == null) || (fldVal.equals("NA")))
                     fldVal = "&nbsp;";
                 
                 msg.append(html.td(fldVal));
                  
                
              }
           
             msg.append(html.td(rs.getString("rap_rte")));
           
             msg.append(html.td(rs.getString("byrDis")));
             msg.append(html.td(rs.getString("memoQuot")));
             msg.append("</tr>");
            
         }
           
            rs.close();
           
          
            int lstSz = invPrpLst.size()+5;
            ttl_cts = util.Round(ttl_cts,2);
              msg.append(html.tr());
              msg.append(html.td("Total Qty :"+ttl_qty+"  Cts:"+ttl_cts+"", "left", lstSz));
              
            msg.append("</tr>");
              
          msg.append(" </table>");
            msg.append("</body>");
            msg.append("</html>"); 
         GenMail mail = new GenMail();
         HashMap dbmsInfo = info.getDmbsInfoLst();
         info.setSmtpHost((String)dbmsInfo.get("SMTPHost"));
         info.setSmtpPort(Integer.parseInt((String)dbmsInfo.get("SMTPPort")));
         info.setSmtpUsr((String)dbmsInfo.get("SMTPUsr"));
         info.setSmtpPwd((String)dbmsInfo.get("SMTPPwd"));
         mail.setInfo(info);    
         mail.init();
         String senderID =(String)dbmsInfo.get("SENDERID");
         if(senderID.equals("NA"))
             senderID = info.getSaleExeEml();
         String senderNm =(String)dbmsInfo.get("SENDERNM");
         mail.setSender(senderID, senderNm);
         mail.setSubject("Web Confirmation rejected | Memo Id "+String.valueOf(memoId)+" from "+byrNm);
        String isByr = util.nvl((String)mailDtl.get("ISBYR"));
        if(isByr.equals("Y")){
              mail.setTO(byrEml);
         }
         String eml = util.nvl((String)mailDtl.get("MLIST"));
         String[] emlLst = eml.split(",");
          if(emlLst!=null){
            for(int i=0 ; i <emlLst.length; i++)
             {
              mail.setTO(emlLst[i]);
             }
            }
         mail.setBCC(info.getSaleExeEml());
         if(!senderID.equals("NA"))
         mail.setReplyTo(info.getSaleExeEml());
         String mailMag = msg.toString();
         mail.setMsgText(mailMag);
            logDetails.put("BYRID",byrId);
            logDetails.put("RELID",relId);
            logDetails.put("TYP","MEMO_RTN");
            logDetails.put("IDN",String.valueOf(memoId));
            String mailLogIdn=util.mailLogDetails(req,logDetails,"I");  
            logDetails.put("MSGLOGIDN",mailLogIdn);
            logDetails.put("MAILDTL",mail.send(""));
            util.mailLogDetails(req,logDetails,"U");
         
        } catch (SQLException sqle) {
         // TODO: Add catch code
         sqle.printStackTrace();
        }
         }}
    }
  
    
    public boolean sendAppMemoMail(int memoId, HttpServletRequest req  , HttpServletResponse res) {
        init(req, res);
        HashMap mailDtl = util.getMailFMT("DIA_APP");
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = util.nvl((String)dbinfo.get("CNT"));
        boolean mailSent = false;
        HashMap logDetails=new HashMap();
        if(mailDtl!=null && mailDtl.size()>0){
        HashMap inv_grp_nme = new HashMap();
        HashMap inv_grp = new HashMap();
        ArrayList prpDspBlocked = info.getPageblockList();
        ArrayList ary=null;
        ResultSet rs=null;
        String byrNm="",grpCompanynme="",grpemlpkt="";
        String createdby ="";
        int byrId=0;
        String grp_nme = "";
        String grp_eml = "";
        String byrTerms="";
        String byrEml="",remark="",memotyp="",loyallow="NO",memallow="NO";
        HashMap mprp = info.getMprp();
        HashMap prpmprp = info.getPrp();
        double exh_rte = 1;
        String country = "";
        ary = new ArrayList();
        int relId=0;
        ary.add(String.valueOf(memoId));
        rs = db.execSql("get rel ID","select nmerel_idn ,typ,byr.get_trms(nmerel_idn) terms, initcap(byr.get_nm(nme_idn)) bym ,  nme_idn , byr.get_eml(nme_idn) eml,byr.get_country(nme_idn) country , exh_rte,rmk,aud_created_by from mjan where idn = ?", ary);
        try {
            if (rs.next()) {
                relId = rs.getInt("nmerel_idn");
                byrNm = util.nvl(rs.getString("bym"));
                byrEml = util.nvl(rs.getString("eml"));
                byrId = rs.getInt("nme_idn");
                exh_rte = rs.getDouble("exh_rte");
                remark= util.nvl(rs.getString("rmk"));
                memotyp= util.nvl(rs.getString("typ"));
                createdby=util.nvl(rs.getString("aud_created_by")); 
                country =util.nvl(rs.getString("country"));
                byrTerms= util.nvl(rs.getString("terms"));
                ary = new ArrayList();
                ary.add(String.valueOf(byrId));
                rs = db.execSql("get rel ID","select  initcap(byr.get_nm(emp_idn)) saleNme ,   byr.get_eml(emp_idn) saleEml,grp_co_nme \n" + 
                "from nme_v where nme_idn=?", ary);
                if (rs.next()) {
                    info.setSaleExNme(rs.getString("saleNme"));
                    info.setSaleExeEml(rs.getString("saleEml"));
                    grpCompanynme=(util.nvl(rs.getString("grp_co_nme")));
                }
            
            }
            rs.close();
            if(cnt.equals("kj")){
            ary = new ArrayList();
            ary.add(String.valueOf(memoId));
            rs = db.execSql("get rel ID","select loyalty_pkg.byr_allowed(nme_idn) loyallow,GET_MEMBER_DISCOUNT_ALLOWED(nme_idn) memallow from mjan where idn = ?", ary);
            if (rs.next()) {
                loyallow=(util.nvl(rs.getString("loyallow")));
                memallow=(util.nvl(rs.getString("memallow")));
            }
            rs.close();
            }
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
     
        if(memotyp.equals("LAP"))
        mailDtl = util.getMailFMT("DIA_APPLAP");
        
        String bodymsg=util.nvl((String)mailDtl.get("MAILBODY"));
        int ct=0;
     
         ct = db.execUpd("delete gtSrch", "DELETE FROM gt_srch_rslt ", new ArrayList());
        ary = new ArrayList();
        ary.add(String.valueOf(memoId));
        ary.add(String.valueOf(relId));
        ary.add("IS");
        ary.add("DIA_APP_MAIL");
         ct = db.execCall("Mail Memo : " + memoId, " pkgmkt.popMemo(?,?,?,?)",ary);
         if(ct>0){
         ArrayList invPrpLst = new ArrayList();
           
           try{
              
                
                String getInvPrp = "Select prp from rep_prp where mdl = 'DIA_APP_MAIL' and flg = 'Y' order by rnk "; 
               rs = db.execSql(" Inv Prp ", getInvPrp, new ArrayList());
               while(rs.next()) {
                   String prp=rs.getString("prp");
                    if(!prpDspBlocked.contains(prp))
                    invPrpLst.add(prp)  ;  
                }
                rs.close();
              int invPrpLstsz=invPrpLst.size();
              int tolCol=invPrpLstsz+3;
              String conQ="";
               
              if(cnt.equals("kj")){
              conQ=",GET_PKT_DIS ('MEMO',"+String.valueOf(memoId)+",a.stk_idn,'LOY') loy_dsc , GET_PKT_DIS ('MEMO',"+String.valueOf(memoId)+",a.stk_idn,'MEM_DIS') member_dsc";
                  if(loyallow.equals("YES"))
                  tolCol=tolCol+1;
                  if(memallow.equals("YES"))
                  tolCol=tolCol+1;
              }
               
              String getQuot = "quot,nvl(fnl_usd,quot) rte, to_char(trunc(a.cts,2)*quot,'99999999999999990.99') vlu ";
                    
               String getInvPkts = " select a1.fcpr cst, a1.vnm ref,  a1.rap_rte rap_rte, a1.tfl4 owner, stk_idn, a.qty, to_char(a.cts,'99999990.99') cts,"+ getQuot+conQ 
                   + ",  trunc(((a1.fcpr*100)/a1.rap_rte) - 100,2) c_dis "
                   + util.getGTPrpLst(invPrpLstsz)
                   + ", to_char(sum(trunc(a.cts,2)) over(partition by a.stt),'9999990.99') ttl_cts, decode(a.rap_rte, 1, '', decode(least(a.rap_dis, 0),0, '+'||a.rap_dis, a.rap_dis)) q_dis "
                                  
                   + ", to_char(sum(trunc(a.cts,2)*quot) over(partition by a.stt),'9999999999999990.99') ttl_vlu "
                   + ", sum(a.qty) over(partition by a.stt) ttl_qty "
                   + " from gt_srch_rslt a, mstk a1 where a.stk_idn = a1.idn and a.flg = 'M' order by a.sk1 ";
               
                StringBuffer msg=new StringBuffer();
                String hdr = "<html><head><title>Memo by Mail : "+ memoId + "</title>\n"+
                "<style type=\"text/css\">\n"+
                "body{\n" +
                " margin-left: 10px;\n" +
                " margin-top: 10px;\n" +
                " margin-right: 0px;\n" +
                " margin-bottom: 0px;\n" +
                " font-family: Arial, Helvetica, sans-serif;\n" +
                " font-size: 12px;\n" +
                " color: #333333;\n" +
                "}\n" +
                " table tr th {\n" +
                " font-family: Arial, Helvetica, Sans-Serif;\n" +
                " font-size: 9pt; \n" +
                " font-weight:bold;\n" +
                " color: #000000; \n" +
                " }\n" +
                " table tr td {\n" +
                " white-space: nowrap;font-family: Arial, Helvetica, Sans-Serif;\n" +
                " font-size: 9pt;\n" +
                " }\n"+
                "</style>\n"+         
                "</head>";
                msg.append(hdr);
//                msg.append("<body>");
//                msg.append("Order Buyer : "+byrNm+"<br/><br/>");
//                msg.append("Reference Memo Id : "+memoId+"<br/><br/>");
//                msg.append("Sale Coordinator: "+info.getSaleExNme()+"<br/><br/>");
//                msg.append("Date : "+util.getToDteMarker()+"<br/><br/>");
                if(bodymsg.indexOf("~SALENME~") > -1)
                          bodymsg = bodymsg.replaceFirst("~SALENME~", util.nvl(info.getSaleExNme())); 
                if(bodymsg.indexOf("~BYRNME~") > -1)
                          bodymsg = bodymsg.replaceFirst("~BYRNME~", byrNm); 
                if(bodymsg.indexOf("~BYREML~") > -1)
                          bodymsg = bodymsg.replaceFirst("~BYREML~", byrEml); 
                if(bodymsg.indexOf("~MEMOID~") > -1)
                            bodymsg = bodymsg.replaceFirst("~MEMOID~", String.valueOf(memoId)); 
                if(bodymsg.indexOf("~COMPANY~") > -1)
                            bodymsg = bodymsg.replaceFirst("~COMPANY~", byrNm); 
                if(bodymsg.indexOf("~GRPCOMPANY~") > -1)
                            bodymsg = bodymsg.replaceFirst("~GRPCOMPANY~", grpCompanynme); 
                if(bodymsg.indexOf("~DTE~") > -1)
                            bodymsg = bodymsg.replaceFirst("~DTE~", util.getToDteMarker());
                if(bodymsg.indexOf("~DATE~") > -1)
                            bodymsg = bodymsg.replaceFirst("~DATE~", util.getToDteMarker());
                if(bodymsg.indexOf("~CREATEDBY~") > -1)
                      bodymsg = bodymsg.replaceFirst("~CREATEDBY~", createdby);
                if(bodymsg.indexOf("~RMK~") > -1)
                            bodymsg = bodymsg.replaceFirst("~RMK~", remark.replaceAll("[^\\w\\s-]", "")); 
                if(bodymsg.indexOf("~ByrTrm~") > -1)
                      bodymsg = bodymsg.replaceFirst("~ByrTrm~", byrTerms);
                if(bodymsg.indexOf("~SlExeNme~") > -1)
                      bodymsg = bodymsg.replaceFirst("~SlExeNme~", util.nvl(info.getSaleExNme()));
               
                if(bodymsg.indexOf("~SlExeEml~") > -1)
                      bodymsg = bodymsg.replaceFirst("~SlExeEml~", util.nvl(info.getSaleExeEml()));
               
                StringBuffer packetDTL=new StringBuffer();
                String mailBody = "<table border=\"1\" id=\"inv\" cellspacing=\"0\" cellpadding=\"3\" >";
               if(!cnt.equalsIgnoreCase("KU")){
                packetDTL.append(mailBody);

                 packetDTL.append("<tr style=\"background-color:#83b1e2;font-weight:bold;\">");
                 packetDTL.append(html.td("","center",7));
                 if(cnt.equals("kj") && loyallow.equals("YES"))
                 packetDTL.append(html.td("Purchase Type","center",6));
               
                 packetDTL.append("</tr>");                    
                 packetDTL.append("<tr style=\"background-color:#83b1e2;font-weight:bold;\">");
                 packetDTL.append(html.td("Date","center",0));
                 packetDTL.append(html.td("Time","center",0));
                 packetDTL.append(html.td("Client Name","center",0));
                 packetDTL.append(html.td("Country","center",0));
                 packetDTL.append(html.td("Email","center",0));
                 packetDTL.append(html.td("Sale Ex","center",0));
                 packetDTL.append(html.td("Ref. Memo Id","center",0));
                
                 if(cnt.equals("kj") && loyallow.equals("YES")){
                     packetDTL.append("<td>Web</td>");
                     packetDTL.append("<td>Propose</td>");
                     packetDTL.append("<td>Office</td>"); 
                     packetDTL.append("<td>Mail</td>"); 
                     packetDTL.append("<td>Show</td>");
                     packetDTL.append("<td>Trip</td>");
                     
                 }
                 packetDTL.append("</tr>");
                
                 packetDTL.append(html.tr());
                 packetDTL.append(html.td(util.getToDte()));
                 packetDTL.append(html.td(util.getTime()));
                 packetDTL.append(html.td(byrNm));
                 packetDTL.append(html.td(country));
                 packetDTL.append(html.td(byrEml));
                 packetDTL.append(html.td(info.getSaleExNme()));
                 packetDTL.append(html.td(String.valueOf(memoId)));              
                
                if(cnt.equals("kj") && loyallow.equals("YES")){ 
                    HashMap purchDtl = new HashMap();
                    ary = new ArrayList();
                    ary.add(String.valueOf(memoId));
                    String purchQ ="select count(*) cnt , val from stk_dtl where mprp='SL_TYP' and grp=1 and mstk_idn in(select mstk_idn from jandtl where idn= ?) group by val " ;
                    rs = db.execSql("Purchase",purchQ, ary);
                    while(rs.next()){
                    purchDtl.put(util.nvl(rs.getString("val")),rs.getString("cnt"));                       
                    }
                    rs.close();
                        packetDTL.append(html.td(util.nvl((String)purchDtl.get("WEB"),"0")));
                        packetDTL.append(html.td(util.nvl((String)purchDtl.get("OTHER"),"0")));
                        packetDTL.append(html.td(util.nvl((String)purchDtl.get("OFFICE"),"0")));
                        packetDTL.append(html.td(util.nvl((String)purchDtl.get("MAIL"),"0")));
                        packetDTL.append(html.td(util.nvl((String)purchDtl.get("SHOW"),"0")));
                        packetDTL.append(html.td(util.nvl((String)purchDtl.get("TRIP"),"0")));
                }
                packetDTL.append("</tr>");
                packetDTL.append("</table><br>");
               }
               
                packetDTL.append(mailBody);
                packetDTL.append("<tr style=\"background-color:#83b1e2;\">");
                packetDTL.append(html.th("Description of Goods", "center",tolCol-1));
                packetDTL.append(html.th("","center",1));
                packetDTL.append(html.th("","center",1));
                packetDTL.append(html.th("Rate","center",1));
                packetDTL.append(html.th("","center",1));
                packetDTL.append("</tr>");
               
                packetDTL.append("<tr>");
                packetDTL.append(html.th("Sr"));
                packetDTL.append(html.th("Stone Id"));
           
                for(int i=0; i < invPrpLstsz; i++) {
                    String prp = (String)invPrpLst.get(i);
                    String prpDsc = (String)mprp.get(prp);
                    packetDTL.append(html.th(prpDsc));
                   
                }
                if(cnt.equals("kj")){
                if(loyallow.equals("YES"))
                packetDTL.append(html.th("Loy Dis"));
                if(memallow.equals("YES"))
                packetDTL.append(html.th("Member Dis"));
                }
                packetDTL.append(html.th("Final Dis"));
                packetDTL.append(html.th("Pr/Ct"));
                packetDTL.append(html.th("Final Pr/Ct"));
                packetDTL.append(html.th("Final Amount"));
                packetDTL.append("</tr>");
                
                rs = db.execSql(" Inv View ", getInvPkts, new ArrayList());

        
                int ttlQty=0;
                String ttlCts=null;
                String ttlVlu=null;
                int n = 1;
                while (rs.next()) {
                   ttlQty = rs.getInt("ttl_qty");
                   ttlCts = rs.getString("ttl_cts");
                   ttlVlu = rs.getString("ttl_vlu");

                  
                    packetDTL.append(html.tr());
                    packetDTL.append(html.td(Integer.toString(n++)));
                    packetDTL.append(html.td(util.nvl(rs.getString("ref"))));

                   
                    for (int i = 0; i < invPrpLst.size(); i++) {
                        String fld = "prp_";
                        String prp = (String)invPrpLst.get(i);
                        int j = i + 1;
                        if (j < 10)
                            fld += "00" + j;
                        else if (j < 100)
                            fld += "0" + j;
                        else if (j > 100)
                            fld += j;
                        boolean dsp = true;
                        String fldVal = rs.getString(fld);
                      
                       
                        if ((fldVal == null) || (fldVal.equals("NA")))
                            fldVal = "&nbsp;";
                         if(prp.equals("CRTWT"))
                             fldVal = util.nvl(rs.getString("cts"));                         
                         
                         if(prp.equals("RAP_RTE"))
                             fldVal = util.nvl(rs.getString("rap_rte"));
                        packetDTL.append(html.td(fldVal));
                         
                       
                     }
                    if(cnt.equals("kj")){
                    if(loyallow.equals("YES"))
                    packetDTL.append(html.td(rs.getString("loy_dsc")));
                    if(memallow.equals("YES"))
                    packetDTL.append(html.td(rs.getString("member_dsc")));
                    }
                    packetDTL.append(html.td(rs.getString("q_dis")));
                    packetDTL.append(html.td(rs.getString("rte")));
                    packetDTL.append(html.td(rs.getString("quot")));
                    packetDTL.append(html.td(rs.getString("vlu")));
                    packetDTL.append("</tr>");
                    mailSent = true;
                }
                rs.close();
               
                if(!ttlVlu.equals("") && cnt.equals("hk")){
                packetDTL.append(html.tr());
                packetDTL.append(html.td("", "left", tolCol-1));
                packetDTL.append(html.td("Shipping Charges", "center",2));
                Double shpCharge = 0.00;    
                String shippingChargeQuery = "select GET_SHIPPING_CHG( pVlu => ? , pNmeIdn => ? ) shpChrg from dual";
                ArrayList shpVector = new ArrayList();
                shpVector.add(ttlVlu);
                shpVector.add(Integer.toString(byrId));
                ResultSet rsShipping = db.execSql(" Shipping Charge  ", shippingChargeQuery, shpVector);            
                while (rsShipping.next()) {
                    shpCharge = rsShipping.getDouble("shpChrg");
                }    
                rsShipping.close();
                ttlVlu=String.valueOf(Double.parseDouble(ttlVlu)+shpCharge);
                    packetDTL.append(html.td(String.valueOf(shpCharge), "right"));
                    packetDTL.append("</tr>");
                }
                int ctsIndx = invPrpLst.indexOf("CRTWT");
                int ctsCol = ctsIndx+2;
                int addextracol=1;
               if(cnt.equals("kj")){
                   if(loyallow.equals("YES"))
                   addextracol=addextracol+1;
                   if(memallow.equals("YES"))
                   addextracol=addextracol+1;
               }
                     packetDTL.append("<tr style=\"background-color:#83b1e2;font-weight:bold;\">");
                     packetDTL.append(html.td("", "left", ctsCol-1));
                     packetDTL.append(html.td("Total Carat", "right"));
                     packetDTL.append(html.td(ttlCts, "right"));
                     packetDTL.append(html.td("", "left", (invPrpLstsz+addextracol)-ctsIndx));
                     packetDTL.append(html.td(" Total", "right"));
                     packetDTL.append(html.td(ttlVlu, "right"));
                     
                     packetDTL.append("</tr>");
                   packetDTL.append(" </table>");
//                System.out.println(packetDTL);
                if(bodymsg.indexOf("~PKTDTL~") > -1)
                bodymsg = bodymsg.replaceFirst("~PKTDTL~", String.valueOf(packetDTL)); //end packetDTL   
                msg.append(bodymsg);
                msg.append("</body>");
                msg.append("</html>"); 
                GenMail mail = new GenMail();
                HashMap dbmsInfo = info.getDmbsInfoLst();
                info.setSmtpHost((String)dbmsInfo.get("SMTPHost"));
                info.setSmtpPort(Integer.parseInt((String)dbmsInfo.get("SMTPPort")));
                info.setSmtpUsr((String)dbmsInfo.get("SMTPUsr"));
                info.setSmtpPwd((String)dbmsInfo.get("SMTPPwd"));
                mail.setInfo(info);    
                mail.init();
                String senderID =(String)dbmsInfo.get("SENDERID");
                if(senderID.equals("NA"))
                    senderID = info.getSaleExeEml();
                String senderNm =(String)dbmsInfo.get("SENDERNM");
                mail.setSender(senderID, senderNm);
                String mailSub=util.nvl((String)mailDtl.get("SUBJECT"));
                  if(mailSub.indexOf("~BYRNME~") > -1)
                mailSub = mailSub.replaceFirst("~BYRNME~", byrNm); 
                  if(mailSub.indexOf("~MEMOID~") > -1)
                  mailSub = mailSub.replaceFirst("~MEMOID~", String.valueOf(memoId));
                if(mailSub.indexOf("~GRPCONME~") > -1)
                mailSub = mailSub.replaceFirst("~GRPCONME~", grpCompanynme);
                mailSub = mailSub+" "+util.getToDte();
                mail.setSubject(mailSub);
                String toEml = util.nvl((String)mailDtl.get("TOEML"));
                if(toEml.indexOf("BYR")!=-1){
                mail.setTO(byrEml);  
                 }
               if(toEml.indexOf("SALEX")!=-1){
                String saleEx = util.nvl(info.getSaleExeEml());
                mail.setTO(saleEx); 
                ArrayList saleexemailIdLst=saleExIds(String.valueOf(memoId));
                for(int i=0 ; i <saleexemailIdLst.size(); i++){
                   String slexid=util.nvl((String)saleexemailIdLst.get(i)); 
                   if(!slexid.equals("") && !slexid.equals(saleEx))
                   mail.setTO(util.nvl(slexid));
                   }
               }
                String[] emlToLst = toEml.split(","); 
                if(emlToLst!=null){
                for(int i=0 ; i <emlToLst.length; i++)
                {
                 String to=util.nvl(emlToLst[i]);
                 if(!to.equals("BYR") && !to.equals("SALEX") && !to.equals("GRPCO")){   
                 mail.setTO(to);
                 }
                 }
                }
                String eml = util.nvl((String)mailDtl.get("CCEML"));
                String[] emlLst = eml.split(",");
                if(emlLst!=null){
                for(int i=0 ; i <emlLst.length; i++)
                {
                mail.setCC(emlLst[i]);
                }
                }

                 String bcceml = util.nvl((String)mailDtl.get("BCCEML"));
                 String[] bccemlLst = bcceml.split(",");
                 if(bccemlLst!=null){
                  for(int i=0 ; i <bccemlLst.length; i++)
                  {
                  mail.setBCC(bccemlLst[i]);
                  }
                }
                   if(toEml.indexOf("GRPCO")!=-1){
                   ary = new ArrayList();
                   ary.add(String.valueOf(memoId));
                   String grpNme="";
                   rs = db.execSql("get rel ID","select upper(byr.get_nm(nvl(b.grp_nme_idn,0))) grpNm from mjan a,nme_v b where a.nme_idn=b.nme_idn and a.idn=?", ary);
                   if (rs.next()) {
                       grpNme=util.nvl(rs.getString("grpNm"));                   
                   }
                   rs.close();
                   inv_grp_nme = rptListViw();
                   inv_grp = invMlLst();
                   if(!grpNme.equals("") && !grpNme.equals("NONE")){
                   ArrayList emlTyp = (ArrayList)inv_grp_nme.get(grpNme);

                   if(emlTyp != null && emlTyp.size() > 0){

                   String grpeml = util.nvl((String)emlTyp.get(0));
                   grpemlpkt = util.nvl((String)emlTyp.get(1));


                   if(!grpeml.equals("")){
                   String[] grpemlLst = grpeml.split(",");
                   if (grpemlLst.length > 0) {
                   for(int i=0;i<grpemlLst.length;i++){
                   grp_nme = grpemlLst[i];
                   eml = (String)inv_grp.get(grp_nme);
                   emlLst = eml.split(",");
                   if (emlLst.length > 0) {
                   for(int j=0;j<emlLst.length;j++){
                   grp_eml = emlLst[j];
                   mail.setCC(grp_eml);
                   }
                   }
                   }
                   }
                   }
                   }
                   }
               }
                if(!senderID.equals("NA"))
                mail.setReplyTo(info.getSaleExeEml());
                String mailMag = msg.toString();
                mail.setMsgText(mailMag);
                if(mailSent){
                logDetails.put("BYRID",byrId);
                logDetails.put("RELID",relId);
                logDetails.put("TYP","MEMO_APP");
                logDetails.put("IDN",String.valueOf(memoId));
                String mailLogIdn=util.mailLogDetails(req,logDetails,"I");  
                logDetails.put("MSGLOGIDN",mailLogIdn);
                logDetails.put("MAILDTL",mail.send(""));
                util.mailLogDetails(req,logDetails,"U");
                String updmemoId = "update mjan set mail_stt='SND' where idn=?";
                ary=new ArrayList();
                ary.add(String.valueOf(memoId));
                HashMap maillog=(HashMap)logDetails.get("MAILDTL");
                if(maillog.get("STT").equals("Y"))
                ct = db.execUpd(" Update Memo Id ", updmemoId, ary); 
                }
               if(cnt.equals("hk")){
                   mail = new GenMail();
                   mail.setInfo(info);
                   mail.init();
                   mail.setSender(senderID, senderNm);
                   mail.setSubject(mailSub);
                   if(!grpemlpkt.equals("")){
                   String[] grppktemlLst = grpemlpkt.split(",");
                   if (grppktemlLst.length > 0) {
                   for(int i=0;i<grppktemlLst.length;i++){
                   grp_nme = grppktemlLst[i];
                   eml = (String)inv_grp.get(grp_nme);
                   String[] pktemlLst = eml.split(",");
                   if (pktemlLst.length > 0) {
                   for(int j=0;j<pktemlLst.length;j++){
                   grp_eml = pktemlLst[j];
                   mail.setTO(grp_eml);
                   }
                   }
                   }
                   }
                       mail.setMsgText(packetDTL.toString());
                       logDetails=new HashMap();
                       logDetails.put("BYRID",byrId);
                       logDetails.put("RELID",relId);
                       logDetails.put("TYP","MEMO_APP");
                       logDetails.put("IDN",String.valueOf(memoId));
                       String mailLogIdn=util.mailLogDetails(req,logDetails,"I");  
                       logDetails.put("MSGLOGIDN",mailLogIdn);
                       logDetails.put("MAILDTL",mail.send(""));
                       util.mailLogDetails(req,logDetails,"U");
                   }   
               }
                
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }}   
        }
          
            return mailSent;
    }
    
        public boolean sendSalMail(int saleId,int dlvId , HttpServletRequest req  , HttpServletResponse res) {
            init(req, res);
            HashMap mailDtl = getSalMailMAP("MAIL_SALE");
            boolean mailSent = false;
            HashMap logDetails=new HashMap();
            int ctg =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_rslt", new ArrayList());
            int ctm =db.execUpd(" Del Old Pkts ", " Delete from gt_srch_multi", new ArrayList());
        try {
            if (mailDtl != null && mailDtl.size() > 0) {
                ArrayList ary = new ArrayList();
                ary.add(String.valueOf(saleId));
                ResultSet rs =
                    db.execSql("get rel ID", "select nmerel_idn , initcap(byr.get_nm(nme_idn)) bym , nme_idn , byr.get_eml(nme_idn) eml , exh_rte from msal where idn = ?",
                               ary);
                if (rs.next()) {
                   int  relId = rs.getInt("nmerel_idn");
                   String byrNm = util.nvl(rs.getString("bym"));
                   String byrEml = util.nvl(rs.getString("eml"));
                   int byrId = rs.getInt("nme_idn");
                   String setSaleExNme="";
                   String setSaleExeEml="";
                   String salExIdn="";
                    ary = new ArrayList();
                    ary.add(String.valueOf(byrId));                     
                    rs = db.execSql("get rel ID","select  initcap(byr.get_nm(emp_idn)) saleNme ,   byr.get_eml(emp_idn) saleEml,emp_idn from mnme where nme_idn = ?", ary);
                    if (rs.next()) {
                        setSaleExNme=util.nvl(rs.getString("saleNme"));
                        setSaleExeEml=util.nvl(rs.getString("saleEml"));
                        salExIdn=util.nvl(rs.getString("emp_idn"));
                    }     
                   ArrayList vwPrpLst =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_memoPrpListcon") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_memoPrpListcon"); 
                   String gtInsert = "	Insert into gt_srch_rslt (stk_idn, vnm, qty, cts, pkt_dte, stt, cmp, rap_rte, cert_lab, cert_no, flg, sk1, quot, exh_rte, rap_dis)\n" + 
                   "	  		select a.mstk_idn, b.vnm\n" + 
                   "        , decode(b.pkt_ty, 'NR', b.qty, (b.qty - nvl(b.qty_rm, 0)))qty\n" + 
                   "        , decode(b.pkt_ty, 'NR', b.cts, (b.cts - nvl(b.cts_rm, 0))) cts\n" + 
                   "        , b.dte, b.stt, upr, rap_rte, cert_lab, cert_no, 'M', sk1\n" + 
                   "       , nvl(a.fnl_sal, a.quot), nvl(a1.exh_rte, 1),  decode(b.rap_rte, 1, null ,trunc((nvl(a.fnl_sal, a.quot)/nvl(a1.exh_rte, 1)/b.rap_rte*100)  - 100,2))\n" + 
                   "	  		from msal a1, jansal a, mstk b\n" + 
                   "	  		where a1.idn = a.idn and a.mstk_idn = b.idn\n" + 
                   "	  		and a.idn = ?\n" + 
                   "	  		and a.stt <> 'RT'";
                    ary = new ArrayList();
                    ary.add(String.valueOf(saleId));
                    int ct = db.execUpd("gtInsert", gtInsert, ary);
                    String pktPrp = "pkgmkt.pktPrp(0,?)";
                    ary = new ArrayList();
                    ary.add("MEMO_RTRN_CON");
                    ct = db.execCall(" Srch Prp ", pktPrp, ary);
                    String getInvPkts = " select a1.fcpr cst, a1.vnm ref,  a1.rap_rte rap_rte, a1.tfl4 owner, stk_idn, a.qty, a.cts,"+ 
                                        " trunc(100 - ((a.quot/greatest(a.rap_rte,1))*100),2)*-1 quotper,quot, trunc(trunc(a.cts,2)*quot, 2) vlu "+
                         ",  trunc(((a1.fcpr*100)/a1.rap_rte) - 100,2) c_dis "
                        + util.getGTPrpLst(vwPrpLst.size())
                        + ", to_char(sum(trunc(a.cts,2)) over(partition by a.stt),'990.00') ttl_cts, decode(a.rap_rte, 1, '', decode(least(a.rap_dis, 0),0, '+'||a.rap_dis, a.rap_dis)) q_dis "
                                       
                        + ", to_char(sum(trunc(a.cts,2)*quot) over(partition by a.stt),'999999990.00') ttl_vlu "
                        + ", sum(a.qty) over(partition by a.stt) ttl_qty "
                        + " from gt_srch_rslt a, mstk a1 where a.stk_idn = a1.idn and a.flg = 'M' order by a.sk1 ";
                    
                     
                    StringBuffer msg=new StringBuffer();
                     String hdr = "<html><head><title> Mail : "+saleId + "</title>\n"+
                     "<style type=\"text/css\">\n"+
                     "body{\n" + 
                     "   margin-left: 10px;\n" + 
                     "   margin-top: 10px;\n" + 
                     "   margin-right: 0px;\n" + 
                     "   margin-bottom: 0px;\n" + 
                     "   font-family: Arial, Helvetica, sans-serif;\n" + 
                     "   font-size: 12px;\n" + 
                     "   color: #333333;\n" + 
                     "}\n" +
                     "</style>\n"+         
                     "</head>";
                     msg.append(hdr);
                     msg.append("<body>");
                     String bodymsg=util.nvl((String)mailDtl.get("MAILBODY"));
                     String sign =
                         util.nvl((String)mailDtl.get("SIGN"));
                     if(bodymsg.indexOf("~BYRNME~") > -1)
                           bodymsg = bodymsg.replaceAll("~BYRNME~", byrNm);
                    
                     StringBuffer userDTL=new StringBuffer();
                     userDTL.append(html.table("inv", 1));//Mayur userDTL
                     userDTL.append(html.tr());
                     userDTL.append(html.td("STOCK LOC"));
                     userDTL.append(html.td(""));
                     userDTL.append("</tr>");
                     userDTL.append(html.tr());
                     userDTL.append(html.td("DATE"));
                     userDTL.append(html.td(util.getToDte()));
                     userDTL.append("</tr>");
                     userDTL.append(html.tr());
                     userDTL.append(html.td("BUYER"));
                     userDTL.append(html.td(byrNm));
                     userDTL.append(html.tr());
                     userDTL.append(html.td("BUYER Email"));
                     userDTL.append(html.td(byrEml));
                     userDTL.append("</tr>");
                     userDTL.append("</tr>");
                     userDTL.append(html.tr());
                     userDTL.append(html.td("Memo ID"));
                     userDTL.append(html.td(String.valueOf(saleId)));
                     userDTL.append("</tr>");
                     userDTL.append(html.tr());
                     userDTL.append(html.td("Sales Executive"));
                     userDTL.append(html.td(setSaleExNme));
                     userDTL.append("</tr>");
                     userDTL.append(html.tr());
                     userDTL.append(html.td("Sales Executive Email"));
                     userDTL.append(html.td(setSaleExeEml));
                     userDTL.append("</tr>");
                     userDTL.append("</table>");//Mayur
                    
                     if(bodymsg.indexOf("~USRDTL~") > -1)
                           bodymsg = bodymsg.replaceFirst("~USRDTL~", String.valueOf(userDTL));//end userDTL
                    
                     StringBuffer packetDTL=new StringBuffer();//end packetDTL
                     String mailBody = "<table  border=\"1\" id=\"inv\" cellspacing=\"0\" cellpadding=\"5\" >";
                     packetDTL.append(mailBody);
                     HashMap mprp = info.getMprp();
                     packetDTL.append("<tr bgcolor=\"#add1ff\">");
                     packetDTL.append(html.th("Sr"));
                     packetDTL.append(html.th("StoneID/ReportNo"));
                    
                     for(int i=0; i < vwPrpLst.size(); i++) {
                         String prp = (String)vwPrpLst.get(i);
                         String prpDsc = (String)mprp.get(prp);
                         packetDTL.append(html.th(prpDsc));
                        
                     }
                     
                     packetDTL.append(html.th("Dis"));
                     packetDTL.append(html.th("Rte"));
                     packetDTL.append(html.th("Amount"));
                     packetDTL.append("</tr>");
                     
                     rs = db.execSql(" Inv View ", getInvPkts, new ArrayList());

                    
                     int ttlQty=0;
                     String ttlCts=null;
                     String ttlVlu=null;
                     int n = 1;
                    String color="";
                     while (rs.next()) {
                        ttlQty = rs.getInt("ttl_qty");
                        ttlCts = rs.getString("ttl_cts");
                        ttlVlu = rs.getString("ttl_vlu");                     
                         if(color.equals("") || color.equals("#e6e6e6"))
                         color="#cccccc";
                         else
                         color="#e6e6e6";
                         packetDTL.append(html.nwLn() + "<tr bgcolor=\""+color+"\">");
                         packetDTL.append(html.td(Integer.toString(n++)));
                         packetDTL.append(html.td(util.nvl(rs.getString("ref"))));
                        
                         for (int i = 0; i < vwPrpLst.size(); i++) {
                             String fld = "prp_";
                             String prp = (String)vwPrpLst.get(i);
                             int j = i + 1;
                             if (j < 10)
                                 fld += "00" + j;
                             else if (j < 100)
                                 fld += "0" + j;
                             else if (j > 100)
                                 fld += j;
                             boolean dsp = true;
                             String fldVal = rs.getString(fld);                         
                            
                             if ((fldVal == null) || (fldVal.equals("NA")))
                                 fldVal = "&nbsp;";
                             
                             packetDTL.append(html.td(fldVal));                            
                            
                          }
                       
                         packetDTL.append(html.td(rs.getString("q_dis")));
                       
                         packetDTL.append(html.td(rs.getString("quot")));
                         
                       
                         packetDTL.append(html.td(rs.getString("vlu")));
                         packetDTL.append("</tr>");
                         mailSent = true;
                     }
                    rs.close();
                    int ctsIndx = vwPrpLst.indexOf("CRTWT");
                    int ctsCol = ctsIndx+2;
                    int lstSz = vwPrpLst.size();
                   
                     packetDTL.append(html.tr());
                     packetDTL.append(html.tdbold("Total", "center", ctsCol-1));
                     packetDTL.append(html.tdbold("", "right"));
                     packetDTL.append(html.tdbold(ttlCts, "center"));
                     packetDTL.append(html.tdbold("", "left", lstSz-ctsIndx));
                     packetDTL.append(html.tdbold("Sub Total ", "right"));
                     packetDTL.append(html.tdbold(ttlVlu, "center"));
                     packetDTL.append("</tr>");
                     packetDTL.append(" </table>");
                    
                    if(bodymsg.indexOf("~PKTDTL~") > -1)
                           bodymsg = bodymsg.replaceFirst("~PKTDTL~", String.valueOf(packetDTL)); //end packetDTL
                    
                    if(bodymsg.indexOf("~SALEXC~") > -1)
                             bodymsg = bodymsg.replaceFirst("~SALEXC~", setSaleExeEml); 
                     if(bodymsg.indexOf("~DLVID~") > -1)
                               bodymsg = bodymsg.replaceFirst("~DLVID~", String.valueOf(dlvId)); 
                    
                   
                   
                    msg.append(bodymsg);
                    msg.append("</body>");
                    msg.append("</html>");
               
                     GenMail mail = new GenMail();
                     HashMap dbmsInfo = info.getDmbsInfoLst();
                     info.setSmtpHost((String)dbmsInfo.get("SMTPHost"));
                     info.setSmtpPort(Integer.parseInt((String)dbmsInfo.get("SMTPPort")));
                     info.setSmtpUsr((String)dbmsInfo.get("SMTPUsr"));
                     info.setSmtpPwd((String)dbmsInfo.get("SMTPPwd"));
                     mail.setInfo(info);    
                     mail.init();
                     String senderID =util.nvl((String)dbmsInfo.get("SENDERID"));
                     if(senderID.equals("NA"))
                         senderID = setSaleExeEml;
                     String senderNm =(String)dbmsInfo.get("SENDERNM");
                     mail.setSender(senderID, senderNm);          
                    String mailSub=util.nvl((String)mailDtl.get("SUBJECT"));
                     if(mailSub.indexOf("~BYRNME~") > -1)
                    mailSub = mailSub.replaceFirst("~BYRNME~", byrNm);
                     if(mailSub.indexOf("~DLVID~") > -1)
                     mailSub = mailSub.replaceFirst("~DLVID~", String.valueOf(dlvId));
                    mailSub = mailSub+" "+util.getToDte();
                    mail.setSubject(mailSub);
                    String toEml = util.nvl((String)mailDtl.get("TOEML"));
                    if(toEml.indexOf("BYR")!=-1 ){
                   mail.setTO(byrEml);
                    }
                    if(toEml.indexOf("SALEX")!=-1 ){
                    mail.setTO(setSaleExeEml);
                    }
                    String[] emlToLst = toEml.split(","); 
                    if(emlToLst!=null){
                    for(int i=0 ; i <emlToLst.length; i++)
                    {
                     String to=util.nvl(emlToLst[i]);
                     if(!to.equals("BYR") && !to.equals("SALEX") && !to.equals("")){   
                     mail.setTO(to);
                     }
                     }
                    }
                    String eml = util.nvl((String)mailDtl.get("CCEML"));
                    String[] emlLst = eml.split(",");
                    if(emlLst!=null ){
                    for(int i=0 ; i <emlLst.length; i++)
                    {
                   mail.setCC(emlLst[i]);
                    }
                    }

                    String bcceml = util.nvl((String)mailDtl.get("BCCEML"));
                    String[] bccemlLst = bcceml.split(",");
                    if(bccemlLst!=null ){
                     for(int i=0 ; i <bccemlLst.length; i++)
                     {
                    mail.setBCC(bccemlLst[i]);
                     }
                    }
                    if(!senderID.equals("NA"))
                    mail.setReplyTo(info.getSaleExeEml());
                   // mail.setBCC("surekha.kandhare@faunatechnologies.com");
                     
                    String mailMag = msg.toString();
                    mail.setMsgText(mailMag);
                    
                    if(mailSent){
                        logDetails.put("BYRID",byrId);
                        logDetails.put("RELID",relId);
                        logDetails.put("TYP","MAIL_SALE");
                        logDetails.put("IDN",String.valueOf(saleId));
                        String mailLogIdn=util.mailLogDetails(req,logDetails,"I");  
                        logDetails.put("MSGLOGIDN",mailLogIdn);
                        logDetails.put("MAILDTL",mail.send(""));
                        util.mailLogDetails(req,logDetails,"U");
                    }
                }
                rs.close();

            }
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
              
                return mailSent;
        }
        
        public HashMap getSalMailMAP(String typ){
          HashMap appMailMap = new HashMap();
         String memoPrntOptn="select typ,subj,to_eml,cc_eml,stt,mail_body,mail_body2,bcc_eml from mail_fmt where typ=? and stt='A' ";
          
          ArrayList ary = new ArrayList();
          ary.add(typ);
          ResultSet rs = db.execSql("memoPrint", memoPrntOptn, ary);

          try {
          while (rs.next()) {
          appMailMap.put("CCEML", util.nvl(rs.getString("cc_eml")));
          appMailMap.put("SUBJECT", util.nvl(rs.getString("subj")));
          appMailMap.put("TOEML", util.nvl(rs.getString("to_eml")));
          appMailMap.put("MAILBODY", util.nvl(rs.getString("mail_body")));
          appMailMap.put("SIGN", util.nvl(rs.getString("mail_body2")));
          appMailMap.put("BCCEML", util.nvl(rs.getString("bcc_eml")));
          }
              rs.close();
          } catch (SQLException sqle) {
          // TODO: Add catch code
          sqle.printStackTrace();
          }
    
          return appMailMap;
          }
    
//        public ArrayList getMemoRtn(){
//            ArrayList viewPrp = (ArrayList)session.getAttribute("memoPrpListcon");
//            try {
//                if (viewPrp == null) {
//                    HashMap dbinfo = info.getDmbsInfoLst();
//                    String cnt=util.nvl((String)dbinfo.get("CNT"));
//                    String mem_ip=util.nvl((String)dbinfo.get("MEM_IP"),"13.229.255.212");
//                    int mem_port=Integer.parseInt(util.nvl((String)dbinfo.get("MEM_PORT"),"11211"));
//                    MemcachedClient mcc = new MemcachedClient(new InetSocketAddress(mem_ip, mem_port));
//                    viewPrp=(ArrayList)mcc.get(cnt+"_memoPrpListcon");
//                    if(viewPrp==null){
//                    viewPrp=new ArrayList();
//                    ResultSet rs1 =
//                        db.execSql(" Vw Lst ", "Select prp  from rep_prp where mdl = 'MEMO_RTRN_CON' and flg='Y' order by rnk ",
//                                   new ArrayList());
//                    while (rs1.next()) {
//
//                        viewPrp.add(rs1.getString("prp"));
//                    }
//                    rs1.close();
//                    }
//                        session.setAttribute("memoPrpListcon", viewPrp);
//                        Future fo = mcc.delete(cnt+"_memoPrpListcon");
//                        System.out.println("add status:_memoPrpListcon" + fo.get());
//                        fo = mcc.set(cnt+"_memoPrpListcon", 24*60*60, viewPrp);
//                        System.out.println("add status:_memoPrpListcon" + fo.get());
//                        mcc.shutdown();
//                    }
//
//            } catch (SQLException sqle) {
//                // TODO: Add catch code
//                sqle.printStackTrace();
//                }catch(Exception ex){
//                 System.out.println( ex.getMessage() );
//                }
//            
//            return viewPrp;
//        }
    public HashMap getAppMailMAP(String typ){
        HashMap appMailMap = new HashMap();
        String    memoPrntOptn = "select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and "
                                 + "b.mdl = 'JFLEX' and b.nme_rule = ? and a.til_dte is null order by a.srt_fr ";
        ArrayList ary = new ArrayList();
        ary.add(typ);
        ResultSet rs = db.execSql("memoPrint", memoPrntOptn, ary);

        try {
            while (rs.next()) {
                appMailMap.put(rs.getString("dsc"), rs.getString("chr_fr"));
            }
            rs.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
       return appMailMap;
    }
    public void GrpVerifyPriceMail(HashMap matDtlMap , ArrayList idnList, HttpServletRequest req , HttpServletResponse res){
        init(req, res);
        String grpNme="";
        StringBuffer msg = new StringBuffer();
        String hdr = "<html><head><title></title>\n"+
        "<style type=\"text/css\">\n"+
        "body{\n" + 
        "   margin-left: 0px;\n" + 
        "   margin-top: 0px;\n" + 
        "   margin-right: 0px;\n" + 
        "   margin-bottom: 0px;\n" + 
        "   font-family: Arial, Helvetica, sans-serif;\n" + 
        "   font-size: 12px;\n" + 
        "   color: #333333;\n" + 
        "}\n" +
        "</style>\n"+         
        "</head>";
        msg.append(hdr);
        msg.append(html.body());
        msg.append("<p><table><tr><td><div style=\"width:15px;height:15px;background-color:red;\">&nbsp;&nbsp;</div></td><td>Before Verify</td> " +
                   "<td><div style=\"width:15px;height:15px;background-color:Blue;\">&nbsp;&nbsp;</div></td><td>After Verify</td>" +
                   "<td><div style=\"width:15px;height:15px;background-color:Green;\">&nbsp;&nbsp;</div></td><td>Same</td>" +
                   "</tr></table></p>");
        msg.append("<p><b>TO,</b><br></br>");
        msg.append("<b>J B & Brothers</b></p>");
       
        
        for(int i=0;i<idnList.size();i++){
            String liveIdn = (String)idnList.get(i);
            HashMap params = new HashMap();
            params.put("NME", matDtlMap.get("NME_"+liveIdn));
            params.put("GRP", matDtlMap.get("GRP_"+liveIdn));
            params.put("BFMATDTL", matDtlMap.get("BF_"+liveIdn));
            params.put("AFMATDTL", matDtlMap.get("AF_"+liveIdn));
            StringBuffer sb = VerifyPriceMailContent(params);
            msg.append(sb);
            grpNme = (String)matDtlMap.get("GRP_"+liveIdn);
        }
        GenMail mail = new GenMail();
        HashMap dbmsInfo = info.getDmbsInfoLst();
        info.setSmtpHost((String)dbmsInfo.get("SMTPHost"));
        info.setSmtpPort(Integer.parseInt((String)dbmsInfo.get("SMTPPort")));
        info.setSmtpUsr((String)dbmsInfo.get("SMTPUsr"));
        info.setSmtpPwd((String)dbmsInfo.get("SMTPPwd"));
        mail.setInfo(info);    
        mail.init();
        String senderID =(String)dbmsInfo.get("SENDERID");
        String senderNm =(String)dbmsInfo.get("SENDERNM");
        mail.setSender(senderID, senderNm);
        mail.setTO("sanjay.shah@jbbrothers.com");
        mail.setTO("vijay.shah@jbbrothers.com");
        //mail.setBCC("ceo@faunatechnologies.com");
        mail.setCC("gopal.soni@jbbrothers.com");
        mail.setSubject("Verify Sheet:- Group Name:"+grpNme+"");
        String mailMag = msg.toString();
        mail.setMsgText(mailMag);
        mail.send("");
    }
    public void VerifyPriceMail(HashMap params , HttpServletRequest req , HttpServletResponse res){
        init(req, res);
        String grpNme = (String)params.get("GRP");
        String nme = (String)params.get("NME");
        StringBuffer msg = new StringBuffer();
        String hdr = "<html><head><title></title>\n"+
        "<style type=\"text/css\">\n"+
        "body{\n" + 
        "   margin-left: 0px;\n" + 
        "   margin-top: 0px;\n" + 
        "   margin-right: 0px;\n" + 
        "   margin-bottom: 0px;\n" + 
        "   font-family: Arial, Helvetica, sans-serif;\n" + 
        "   font-size: 12px;\n" + 
        "   color: #333333;\n" + 
        "}\n" +
        "</style>\n"+         
        "</head>";
        msg.append(hdr);
        msg.append(html.body());
        msg.append("<p><b>TO,</b><br></br>");
        msg.append("<b>J B & Brothers</b></p><br></br>");
        msg.append("<p><table><tr><td><div style=\"width:15px;height:15px;background-color:red;\">&nbsp;&nbsp;</div></td><td>Before Verify</td> " +
                   "<td><div style=\"width:15px;height:15px;background-color:Blue;\">&nbsp;&nbsp;</div></td><td>After Verify</td>" +
                   "<td><div style=\"width:15px;height:15px;background-color:Green;\">&nbsp;&nbsp;</div></td><td>Same</td>" +
                   "</tr></table></p>");
        StringBuffer sb = VerifyPriceMailContent(params);
        msg.append(sb);
        GenMail mail = new GenMail();
        HashMap dbmsInfo = info.getDmbsInfoLst();
        info.setSmtpHost((String)dbmsInfo.get("SMTPHost"));
        info.setSmtpPort(Integer.parseInt((String)dbmsInfo.get("SMTPPort")));
        info.setSmtpUsr((String)dbmsInfo.get("SMTPUsr"));
        info.setSmtpPwd((String)dbmsInfo.get("SMTPPwd"));
        mail.setInfo(info);    
        mail.init();
        String senderID =(String)dbmsInfo.get("SENDERID");
        String senderNm =(String)dbmsInfo.get("SENDERNM");
        mail.setSender(senderID, senderNm);
        mail.setTO("sanjay.shah@jbbrothers.com");
        mail.setTO("vijay.shah@jbbrothers.com");
        //mail.setBCC("ceo@faunatechnologies.com");
        mail.setCC("gopal.soni@jbbrothers.com");
        mail.setSubject("Verify Sheet:- Group Name:"+grpNme+" , Sheet Name:"+nme);
        String mailMag = msg.toString();
        mail.setMsgText(mailMag);
        mail.send("");
        
    }
    public void DeleteMatMail(HashMap params , HttpServletRequest req , HttpServletResponse res){
        init(req, res);
        String grpNme = (String)params.get("GRP");
        String nme = (String)params.get("NME");
        StringBuffer msg = new StringBuffer();
        String hdr = "<html><head><title></title>\n"+
        "<style type=\"text/css\">\n"+
        "body{\n" + 
        "   margin-left: 0px;\n" + 
        "   margin-top: 0px;\n" + 
        "   margin-right: 0px;\n" + 
        "   margin-bottom: 0px;\n" + 
        "   font-family: Arial, Helvetica, sans-serif;\n" + 
        "   font-size: 12px;\n" + 
        "   color: #333333;\n" + 
        "}\n" +
        "</style>\n"+         
        "</head>";
        msg.append(hdr);
        msg.append(html.body());
        msg.append("<p><b>TO,</b><br></br>");
        msg.append("<b>J B & Brothers</b></p><br></br>");
      
        String sub = util.nvl((String)params.get("sub"));
        StringBuffer sb = MatDetail(params);
        msg.append(sb);
        GenMail mail = new GenMail();
        HashMap dbmsInfo = info.getDmbsInfoLst();
        info.setSmtpHost((String)dbmsInfo.get("SMTPHost"));
        info.setSmtpPort(Integer.parseInt((String)dbmsInfo.get("SMTPPort")));
        info.setSmtpUsr((String)dbmsInfo.get("SMTPUsr"));
        info.setSmtpPwd((String)dbmsInfo.get("SMTPPwd"));
        mail.setInfo(info);    
        mail.init();
        String senderID =(String)dbmsInfo.get("SENDERID");
        String senderNm =(String)dbmsInfo.get("SENDERNM");
        mail.setSender(senderID, senderNm);
        mail.setTO("sanjay.shah@jbbrothers.com");
        mail.setTO("vijay.shah@jbbrothers.com");
       //mail.setBCC("ceo@faunatechnologies.com");
        mail.setCC("gopal.soni@jbbrothers.com");
        mail.setSubject(sub+" :- Group Name:"+grpNme+" , Sheet Name:"+nme);
        String mailMag = sb.toString();
        mail.setMsgText(mailMag);
        mail.send("");
        
    }
    public StringBuffer MatDetail(HashMap params){
      
        HashMap MatDtl = (HashMap)params.get("DELMATDTL");
        String grpNme = (String)params.get("GRP");
        String nme = (String)params.get("NME");
        ArrayList refPrp = info.getPrcRefPrp();
        ArrayList grpPrp = util.getGrpPrp(grpNme);
        HashMap mprp = info.getSrchMprp();
           if(mprp==null){
//              util.initPrpSrch();
               mprp = info.getSrchMprp();
              }
        HashMap prp = info.getSrchPrp();
        StringBuffer msg = new StringBuffer();
        msg.append("<B>Group Name: "+grpNme+"</B>&nbsp;&nbsp;<B>Sheet Name: "+nme+"</B>");
         msg.append("<div><table cellspacing=\"3\" cellpadding=\"3\">\n" + 
        "  <tr><td colspan=\"3\" align=\"left\"><B>Basic Propeties:</b></td> </tr>\n" + 
        "  <tr><td></td><td>From</td><td>To</td></tr>");
      for(int j=0;j< grpPrp.size();j++){
            String lprp = (String)grpPrp.get(j);
            String prpDsc = (String)mprp.get(lprp+"D");
            String valFr = util.nvl((String)MatDtl.get(lprp+"_FR"));
            String valTo = util.nvl((String)MatDtl.get(lprp+"_TO"));
            
             msg.append("<tr><td>"+prpDsc+"</td><td> "+valFr+"&nbsp; </td><td>&nbsp; "+valTo+" </td></tr>");
         }
         msg.append("</table></div>");
         
        msg.append("<table><tr><td valign=\"top\">");
        msg.append(html.table("inv", 1));
         msg.append("<tr><td></td>");
        ArrayList bseCO = (ArrayList)prp.get("COLB");
        ArrayList bsePU = (ArrayList)prp.get("CLRB");
          for(int x=0 ; x < bsePU.size(); x++){ 
          msg.append("<th>"+bsePU.get(x)+"</th>");
          }
        msg.append("</tr>");
        
        for(int x=0 ; x < bseCO.size(); x++){
          String coVal = (String)bseCO.get(x);
          
          msg.append("<tr> <th>"+coVal+"</th>");
          
          for(int y=0 ; y < bsePU.size() ; y++){
          String puVal = (String)bsePU.get(y);
          String matVal = util.nvl((String)MatDtl.get(coVal+"_"+puVal));
          
           msg.append("<td>"+matVal+"</td>");
          
          }
             msg.append("</tr>");
          }
         
        msg.append("</table> </td><td valign=\"top\">");
        
         msg.append("<table>");
         msg.append("<tr><td  colspan=\"3\" align=\"left\"><B>Reference Propeties:</b></td> </tr>");
         msg.append("<tr><td valign=\"top\">");
            
           for(int j=0;j< refPrp.size();j++){
                String lprp = (String)refPrp.get(j);
                String prpDsc = (String)mprp.get(lprp+"D");
                String prpTyp = (String)mprp.get(lprp+"T");
                ArrayList prpPrt = (ArrayList)prp.get(lprp+"P");
                ArrayList prpVal = (ArrayList)prp.get(lprp+"V");
                  String tblId = "ref_"+lprp ;
                  
            msg.append(html.table("inv", 1));
            msg.append("<tr><th colspan=\"2\" align=\"center\">"+prpDsc+"</th></tr>");
                if(prpVal!=null && prpVal.size()>0){
                 
                  for(int k = 0 ; k < prpVal.size() ; k++){
                   String lVal = (String)prpPrt.get(k);
                   String lSrt = (String)prpVal.get(k);
                   String refMat = util.nvl((String)MatDtl.get(tblId + "_" + lSrt));
                  
                 msg.append("<tr> <td>"+lVal+" : &nbsp;</td><td>");
                 msg.append(refMat);
                 msg.append("</td></tr>");
                 }
                msg.append("</table></td><td valign=\"top\">");
              }}
            msg.append("</tr></table></tr></table>");
        
        return msg;
    }
    public StringBuffer VerifyPriceMailContent(HashMap params){
      
        HashMap bfVrfyMatDtl = (HashMap)params.get("BFMATDTL");
        HashMap afVrfyMatDtl = (HashMap)params.get("AFMATDTL");
        String grpNme = (String)params.get("GRP");
        String nme = (String)params.get("NME");
        ArrayList refPrp = info.getPrcRefPrp();
        ArrayList grpPrp = util.getGrpPrp(grpNme);
        HashMap mprp = info.getSrchMprp();
           if(mprp==null){
//              util.initPrpSrch();
               mprp = info.getSrchMprp();
              }
        HashMap prp = info.getSrchPrp();
        StringBuffer msg = new StringBuffer();
        msg.append("<B>Group Name: "+grpNme+"</B>&nbsp;&nbsp;<B>Sheet Name: "+nme+"</B>");
    
      
        msg.append("<div><table cellspacing=\"3\" cellpadding=\"3\">\n" + 
        "  <tr><td colspan=\"3\" align=\"left\"><B>Basic Propeties:</b></td> </tr>\n" + 
        "  <tr><td></td><td>Before Verify</td><td>After Verify</td></tr>");
      for(int j=0;j< grpPrp.size();j++){
            String lprp = (String)grpPrp.get(j);
            String prpDsc = (String)mprp.get(lprp+"D");
            String valBFVYFr = util.nvl((String)bfVrfyMatDtl.get(lprp+"_FR"));
            String valBFVYTo = util.nvl((String)bfVrfyMatDtl.get(lprp+"_TO"));
            String valAFVYFr = util.nvl((String)afVrfyMatDtl.get(lprp+"_FR"));
            String valAFVYTo = util.nvl((String)afVrfyMatDtl.get(lprp+"_TO"));
             msg.append("<tr><td>"+prpDsc+"</td><td><span style=\"color:red\"> "+valBFVYFr+"&nbsp; to&nbsp; "+valBFVYTo+"</span> </td><td>\n" + 
             "<span style=\"color:Blue\"> <b>"+valAFVYFr+"<b>&nbsp; to&nbsp; <b>"+valAFVYTo+"</b> </span> </td> </tr>");
         }
         msg.append("</table></div>");
         
        msg.append("<table><tr><td valign=\"top\">");
        msg.append(html.table("inv", 1));
         msg.append("<tr><td></td>");
        ArrayList bseCO = (ArrayList)prp.get("COLB");
        ArrayList bsePU = (ArrayList)prp.get("CLRB");
          for(int x=0 ; x < bsePU.size(); x++){ 
          msg.append("<th>"+bsePU.get(x)+"</th>");
          }
        msg.append("</tr>");
        
        for(int x=0 ; x < bseCO.size(); x++){
          String coVal = (String)bseCO.get(x);
          
          msg.append("<tr> <th>"+coVal+"</th>");
          
          for(int y=0 ; y < bsePU.size() ; y++){
          String puVal = (String)bsePU.get(y);
          String proPosVal = util.nvl((String)bfVrfyMatDtl.get(coVal+"_"+puVal));
          float proValInt = 0;
          if(!proPosVal.equals(""))
          proValInt = Float.parseFloat(proPosVal);
          
          String liveVal = util.nvl((String)afVrfyMatDtl.get(coVal+"_"+puVal));
           float liveValInt = 0;
          if(!liveVal.equals(""))
          liveValInt = Float.parseFloat(liveVal);
          
           if(liveValInt==0 && proValInt==0){
          
           msg.append("<td> &nbsp;&nbsp;</td>");
          }else if(liveValInt==proValInt){
      msg.append("<td><span style=\"color:Green\">"+util.nvl((String)bfVrfyMatDtl.get(coVal+"_"+puVal))+"</span></td>");
        }else{
    
          msg.append("<td><span style=\"color:Red\">"+util.nvl((String)bfVrfyMatDtl.get(coVal+"_"+puVal))+"</span><br> "+
          "<span style=\"color:Blue\"><b>"+util.nvl((String)afVrfyMatDtl.get(coVal+"_"+puVal))+"</b></span><br>");
         
        if(liveValInt!=0 && grpNme.equals("JB BASE PRICE")){
         float diffResult=(((float)liveValInt-(float)proValInt)/(float)proValInt)*100;
         
          msg.append(Math.round(diffResult*100.0)/100.0);
         }
           msg.append("</td>");
        }
          }
             msg.append("</tr>");
          }
         
        msg.append("</table> </td><td valign=\"top\">");
        
         msg.append("<table>");
         msg.append("<tr><td  colspan=\"3\" align=\"left\"><B>Reference Propeties:</b></td> </tr>");
         msg.append("<tr><td valign=\"top\">");
            
           for(int j=0;j< refPrp.size();j++){
                String lprp = (String)refPrp.get(j);
                String prpDsc = (String)mprp.get(lprp+"D");
                String prpTyp = (String)mprp.get(lprp+"T");
                ArrayList prpPrt = (ArrayList)prp.get(lprp+"P");
                ArrayList prpVal = (ArrayList)prp.get(lprp+"V");
                  String tblId = "ref_"+lprp ;
                  
            msg.append(html.table("inv", 1));
            msg.append("<tr><th colspan=\"2\" align=\"center\">"+prpDsc+"</th></tr>");
                if(prpVal!=null && prpVal.size()>0){
                 
                  for(int k = 0 ; k < prpVal.size() ; k++){
                   String lVal = (String)prpPrt.get(k);
                   String lSrt = (String)prpVal.get(k);
                   String proPct = util.nvl((String)bfVrfyMatDtl.get(tblId + "_" + lSrt));
                   String livePct = util.nvl((String)afVrfyMatDtl.get(tblId + "_" + lSrt));
                   int proValInt = 0;
                   if(!proPct.equals(""))
                    proValInt = Integer.parseInt(proPct);
                   int liveValInt = 0;
                   if(!livePct.equals(""))
                   liveValInt = Integer.parseInt(livePct);
                   
          
          msg.append("<tr> <td>"+lVal+" : &nbsp;</td><td>");
         if(liveValInt==0 && proValInt==0){
        }else if(liveValInt==proValInt){
          msg.append("<span style=\"color:Green\">"+proPct+"</span>");
        }else{
        msg.append("<span style=\"color:Blue\">"+proPct+"</span>/ &nbsp;<span style=\"color:red\">"+livePct+"</span>");
         }
         msg.append("</td></tr>");
        }
        msg.append("</table></td><td valign=\"top\">");
        }}
       msg.append("</tr></table></tr></table>");
        
        return msg;
    }
    public void init(HttpServletRequest req , HttpServletResponse res) {
        session = req.getSession(false);
        info = (InfoMgr)session.getAttribute("info");
        util = new DBUtil();
        db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        html = new HtmlMailUtil();
    }
   
    public boolean sendVerifyMail(ArrayList memoIDN,HashMap memoDtl, HttpServletRequest req , HttpServletResponse res) {
    init(req, res);
    HashMap mailDtl = getAppMailMAP("MAIL_VFY");
    boolean mailSent = false;
    HashMap logDetails=new HashMap();
    HashMap prp = info.getPrp();
    ArrayList PrpDtl = (ArrayList)prp.get("DEPT"+"V");
    if(mailDtl!=null && mailDtl.size()>0){
    String isSND = util.nvl((String)mailDtl.get("ISSEND"));
    if(isSND.equals("Y")){
    StringBuffer msg=new StringBuffer();
    msg.append(html.head("Verification Details"));
    msg.append(html.body());
    msg.append("<div>Verification Date :"+ util.getToDteTime()+" By -"+info.getUsr()+"</div><br>");
    msg.append(html.table("inv", 1));

    String body="<tr><td>MEMO</td>";
    for(int i=0;i<PrpDtl.size();i++){
    body=body+"<th colspan=\"2\">"+PrpDtl.get(i)+"</th>";
    }
    body=body+"<th colspan=\"2\">TOTAL</th></tr><tr><td></td>";

    for(int i=0;i<PrpDtl.size();i++){
    body=body+"<th>Pcs</th><th>Cts</th>";
    }
    body=body+"<th>Pcs</th><th>Cts</th></tr>";
    String data="";
    String PCS="";
    String CTS="";
    for(int i=0;i<memoIDN.size();i++){
    String memoId=(String)memoIDN.get(i);
    data=data+"<tr><td>"+memoId+"</td>";
    for(int j=0;j<PrpDtl.size();j++){
    String dept=(String)PrpDtl.get(j);
    String key=memoId+"_"+dept;
    HashMap memoDtlQC=(HashMap)memoDtl.get(key);
    PCS="0";CTS="0.0";
    if(memoDtlQC!=null){
    PCS=util.nvl2((String)memoDtlQC.get("PCS"),"0");
    CTS=util.nvl2((String)memoDtlQC.get("CTS"),"0");}
    data=data+"<td>"+PCS+"</td><td>"+CTS+"</td>";
    }
    HashMap memoDtlQC=(HashMap)memoDtl.get(memoId);
    PCS="0";CTS="0.0";
    if(memoDtlQC!=null){
    PCS=util.nvl2((String)memoDtlQC.get("PCS"),"0");
    CTS=util.nvl2((String)memoDtlQC.get("CTS"),"0");}
    data=data+"<td>"+PCS+"</td><td>"+CTS+"</td></tr>";
    }
    data=data+"<tr><td>Total</td>";
    for(int j=0;j<PrpDtl.size();j++){
    String dept=(String)PrpDtl.get(j);
    HashMap memoDtlQC=(HashMap)memoDtl.get(dept);
    PCS="0";CTS="0.0";
    if(memoDtlQC!=null){
    PCS=util.nvl2((String)memoDtlQC.get("PCS"),"0");
    CTS=util.nvl2((String)memoDtlQC.get("CTS"),"0");}
    data=data+"<td>"+PCS+"</td><td>"+CTS+"</td>";
    }
    HashMap memoDtlQC=(HashMap)memoDtl.get("GRAND");
    PCS="0";CTS="0.0";
    if(memoDtlQC!=null){
    PCS=util.nvl2((String)memoDtlQC.get("PCS"),"0");
    CTS=util.nvl2((String)memoDtlQC.get("CTS"),"0");}
    data=data+"<td>"+PCS+"</td><td>"+CTS+"</td></tr></table></body></html>";
    body=body+data;
    msg.append(body);
    GenMail mail = new GenMail();
    HashMap dbmsInfo = info.getDmbsInfoLst();
    info.setSmtpHost((String)dbmsInfo.get("SMTPHost"));
    info.setSmtpPort(Integer.parseInt((String)dbmsInfo.get("SMTPPort")));
    info.setSmtpUsr((String)dbmsInfo.get("SMTPUsr"));
    info.setSmtpPwd((String)dbmsInfo.get("SMTPPwd"));
    mail.setInfo(info);
    mail.init();
    String senderID =(String)dbmsInfo.get("SENDERID");
    String senderNm =(String)dbmsInfo.get("SENDERNM");
    mail.setSender(senderID, senderNm);
    //mail.setBCC("mayur.boob@faunatechnologies.com");

    String eml = util.nvl((String)mailDtl.get("MLIST"));
    String[] emlLst = eml.split(",");
    if(emlLst!=null){
    for(int i=0 ; i <emlLst.length; i++)
    {
    mail.setBCC(emlLst[i]);
    }
    }
    // System.out.println(msg);
    mail.setSubject("Verification Details "+util.getToDteTime());
    String mailMag = msg.toString();
    mail.setMsgText(mailMag);
        logDetails.put("BYRID","");
        logDetails.put("RELID","");
        logDetails.put("TYP","MAIL_VFY");
        logDetails.put("IDN","");
        String mailLogIdn=util.mailLogDetails(req,logDetails,"I");  
        logDetails.put("MSGLOGIDN",mailLogIdn);
        logDetails.put("MAILDTL",mail.send(""));
        util.mailLogDetails(req,logDetails,"U");
        
        
        for(int i=0;i<PrpDtl.size();i++){
        boolean sendmail=false;
        String dept=(String)PrpDtl.get(i);
        msg=new StringBuffer();
        msg.append(html.head("Verification Details"));
        msg.append(html.body());
        msg.append("<div>Verification Date :"+ util.getToDteTime()+" By -"+info.getUsr()+"</div><br>");
        msg.append(html.table("inv", 1));

        body="<tr><td>MEMO</td>";

        body=body+"<th colspan=\"2\">"+dept+"</th><tr>";
            
        body=body+"<tr><td></td>";
        body=body+"<th>Pcs</th><th>Cts</th></tr>";
        data="";
        PCS="";
        CTS="";
        for(int j=0;j<memoIDN.size();j++){
        String memoId=(String)memoIDN.get(j);
        data=data+"<tr><td>"+memoId+"</td>";
            
        String key=memoId+"_"+dept;
        memoDtlQC=new HashMap();
        memoDtlQC=(HashMap)memoDtl.get(key);
        PCS="0";CTS="0.0";
        if(memoDtlQC!=null){
        PCS=util.nvl2((String)memoDtlQC.get("PCS"),"0");
        CTS=util.nvl2((String)memoDtlQC.get("CTS"),"0");
        }
        data=data+"<td>"+PCS+"</td><td>"+CTS+"</td><tr>";

        }
        data=data+"<tr><td>Total</td>";
        memoDtlQC=new HashMap();
        memoDtlQC=(HashMap)memoDtl.get(dept);
        PCS="0";CTS="0.0";
        if(memoDtlQC!=null){
        sendmail=true;
        PCS=util.nvl2((String)memoDtlQC.get("PCS"),"0");
        CTS=util.nvl2((String)memoDtlQC.get("CTS"),"0");
        }
        data=data+"<td>"+PCS+"</td><td>"+CTS+"</td>";

        data=data+"</tr></table></body></html>";
        body=body+data;
        msg.append(body);
        mail = new GenMail();
        mail.setInfo(info);
        mail.init();
        mail.setSender(senderID, senderNm);
            if(sendmail){
            String email="NA";
            String sqlQ="select lower(byr.get_eml(a.nme_idn,'N')) eml from mnme a , nme_dtl b where a.nme_idn = b.nme_idn and mprp='HOD' and txt ='"+dept+"' and a.typ='EMPLOYEE' and a.vld_dte is null and b.vld_dte is null ";
            ResultSet rs = db.execSql("Employee", sqlQ, new ArrayList());
            try {
            if(rs.next()){
                email= util.nvl(rs.getString("eml") );            
            }    
                rs.close();
            } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
            }
        if(!email.equals("NA") && !email.equals("")){
        mail.setTO(email);
         if(emlLst!=null){
            for(int j=0 ; j <emlLst.length; j++)
            {
            mail.setBCC(emlLst[j]);
            }
            }
        //mail.setBCC("mayur.boob@faunatechnologies.com");
        mail.setSubject("Verification Details "+util.getToDteTime());
        mailMag = msg.toString();
        mail.setMsgText(mailMag);
            logDetails.put("BYRID","");
            logDetails.put("RELID","");
            logDetails.put("TYP","MAIL_VFY");
            logDetails.put("IDN","");
            mailLogIdn=util.mailLogDetails(req,logDetails,"I");  
            logDetails.put("MSGLOGIDN",mailLogIdn);
            logDetails.put("MAILDTL",mail.send(""));
            util.mailLogDetails(req,logDetails,"U");
        }
            }
        }
        

    }
    }
    return mailSent;
    }
        public HashMap rptListViw(){
            HashMap rptList= ((HashMap)session.getAttribute("INV_GRP_EML") == null)?new HashMap():(HashMap)session.getAttribute("INV_GRP_EML");
            if(rptList==null || rptList.size()<=0){
            String reportSql = "select upper(chr_fr) chr_fr,upper(chr_to) chr_to, upper(dsc) dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and "
            + "b.mdl = 'JFLEX' and b.nme_rule = 'INV_GRP_EML' and a.til_dte is null order by a.srt_fr ";
            try { 
            rptList = new HashMap();
            ResultSet rs = db.execSql("reportSql", reportSql , new ArrayList());
            while(rs.next()){
            ArrayList mlList = new ArrayList();    
            mlList.add(util.nvl(rs.getString("chr_fr")));
            mlList.add(util.nvl(rs.getString("chr_to")));
            rptList.put(util.nvl(rs.getString("dsc")),mlList);
            }
                rs.close();
            } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
            }
            }
            session.setAttribute("INV_GRP_EML",rptList);
            return rptList;
            }
        public HashMap invMlLst(){
            HashMap invMlLst= ((HashMap)session.getAttribute("invMlLst") == null)?new HashMap():(HashMap)session.getAttribute("invMlLst");
            if(invMlLst==null || invMlLst.size()<=0){
            String reportSql = "select upper(co_nme) co_nme, lower(byr.get_eml(nme_idn)) email from nme_v where typ = 'GROUP'  ";
            try { 
            invMlLst = new HashMap();
            ResultSet rs = db.execSql("reportSql", reportSql , new ArrayList());
            while(rs.next()){
            String co_nme = util.nvl(rs.getString("co_nme")); 
            String co_eml = util.nvl(rs.getString("email"));
            invMlLst.put(co_nme,co_eml);     
            }
            rs.close();
            } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
            }
            }
            session.setAttribute("invMlLst",invMlLst);
            return invMlLst;
            }
        public ArrayList saleExIds(String idn){
          ArrayList emailids = new ArrayList();
         String memoPrntOptn="select c.txt emailid \n" + 
         "from mjan a , mnme b,nme_dtl c\n" + 
         "where a.nme_idn=b.nme_idn\n" + 
         "and nvl(b.emp_idn,1)=c.nme_idn\n" + 
         "and a.idn=? \n" + 
         "and c.mprp like 'EMAIL%' and c.vld_dte is null";
          
          ArrayList ary = new ArrayList();
          ary.add(idn.trim());
          ResultSet rs = db.execSql("memoPrint", memoPrntOptn, ary);

          try {
          while (rs.next()) {
              String email=util.nvl(rs.getString("emailid"));
              if(!email.equals("NA") && !email.equals("")){
              emailids.add(email);
              }
          }
              rs.close();
          } catch (SQLException sqle) {
          // TODO: Add catch code
          sqle.printStackTrace();
          }
          return emailids;
          }
    }

