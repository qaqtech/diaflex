package ft.com.website;

import ft.com.DBMgr;

import ft.com.DBUtil;
import ft.com.GenMail;
import ft.com.InfoMgr;

import ft.com.LogMgr;

import ft.com.MailAction;

import ft.com.contact.AdvContactForm;

import java.io.IOException;

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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;


public class PendingRegistrationAction extends DispatchAction {

    

    public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      HttpSession session = request.getSession(false);
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
      rtnPg=init(request,response,session,util);
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          return mapping.findForward(rtnPg);   
      }else{
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = (String)dbinfo.get("CNT");
          util.updAccessLog(request,response,"PendingRegistration", "load");
        PendingRegistrationForm pendingRegistrationForm = (PendingRegistrationForm)form;
        ArrayList<UserRegistrationInfoImpl> regnUsrList = new ArrayList<UserRegistrationInfoImpl>();
            HashMap  cDtl = new HashMap();
            ArrayList cList = new ArrayList();
            ArrayList ary=new ArrayList();
            String  country=util.nvl((String)pendingRegistrationForm.getValue("country"));
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("PENDING_REG");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("PENDING_REG");
            allPageDtl.put("PENDING_REG",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            ArrayList pageList=new ArrayList();
            HashMap pageDtlMap=new HashMap();
            String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
            String kaAddColumn="";
            pageList=(ArrayList)pageDtl.get("KAADDCOLUMN");
            if(pageList!=null && pageList.size() >0){
                for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                dflt_val=(String)pageDtlMap.get("dflt_val");
                kaAddColumn="Y"; 
                }
            }
            
        String sql =
            "select reg_id,usr,fnm, lnm, co_nme,dsgn, bldg,street,city,country,\n" + 
            " tel , tel1,fax, mbl ,eml,know_us,biz,mship, to_char(dt_tm, 'DD-MON-YYYY') dt_tm from  web_reg_pndg_v";
          if(kaAddColumn.equals("Y")){
              sql =
                          "select reg_id,usr,fnm, lnm, co_nme,dsgn, bldg,street,city,country,\n" + 
                          " tel , tel1,fax, mbl ,eml,know_us,biz,mship, to_char(dt_tm, 'DD-MON-YYYY') dt_tm,zip,to_char(birthdate,'YYYY-MM_DD') birthdate,state,council_mem,verify from  web_reg_pndg_v";
          }
            if(!country.equals("")){
                sql=sql+" Where country= ?";
                ary.add(country);
             }
            ArrayList outLst = db.execSqlLst("pending registration list", sql, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            UserRegistrationInfoImpl donationInfo = new UserRegistrationInfoImpl();

            donationInfo.setRegId(rs.getInt("reg_id"));

            donationInfo.getUserRegistrationInfo().setUserId(util.nvl(rs.getString("usr")));

            donationInfo.getUserRegistrationInfo().setName(util.nvl(rs.getString("fnm")) + " " + util.nvl(rs.getString("lnm")));

            donationInfo.getUserRegistrationInfo().setFirstName(util.nvl(rs.getString("fnm")));
            donationInfo.getUserRegistrationInfo().setLastName(util.nvl(rs.getString("lnm")));
            donationInfo.getUserRegistrationInfo().setCompanyName(util.nvl(rs.getString("co_nme")));
            donationInfo.getUserRegistrationInfo().setDesignation(util.nvl(rs.getString("dsgn")));

            donationInfo.getUserRegistrationInfo().setAddress(util.nvl(rs.getString("bldg")) + " " + util.nvl(rs.getString("street")));

            donationInfo.getUserRegistrationInfo().setBuilding(util.nvl(rs.getString("bldg")));
            donationInfo.getUserRegistrationInfo().setStreet(util.nvl(rs.getString("street")));
            donationInfo.getUserRegistrationInfo().setCity(util.nvl(rs.getString("city")));
            donationInfo.getUserRegistrationInfo().setCountry(util.nvl(rs.getString("country")));
            donationInfo.getUserRegistrationInfo().setTelephoneNo1(util.nvl(rs.getString("tel")));
            donationInfo.getUserRegistrationInfo().setTelephoneNo2(util.nvl(rs.getString("tel1")));
            donationInfo.getUserRegistrationInfo().setFax(util.nvl(rs.getString("fax")));
            donationInfo.getUserRegistrationInfo().setMobile(util.nvl(rs.getString("mbl")));
            donationInfo.getUserRegistrationInfo().setEmail(util.nvl(rs.getString("eml")));
            donationInfo.getUserRegistrationInfo().setKnowus(util.nvl(rs.getString("know_us")));
            donationInfo.getUserRegistrationInfo().setBiz(util.nvl(rs.getString("biz")));
            donationInfo.getUserRegistrationInfo().setMembership(util.nvl(rs.getString("mship")));
            donationInfo.getUserRegistrationInfo().setReg_dte(util.nvl(rs.getString("dt_tm")));
            if(kaAddColumn.equals("Y")){
            donationInfo.getUserRegistrationInfo().setZip(util.nvl(rs.getString("zip")));
            donationInfo.getUserRegistrationInfo().setBirthdate(util.nvl(rs.getString("birthdate")));
            donationInfo.getUserRegistrationInfo().setState(util.nvl(rs.getString("state")));
            donationInfo.getUserRegistrationInfo().setCouncil_mem(util.nvl(rs.getString("council_mem")));
                donationInfo.getUserRegistrationInfo().setVerify(util.nvl(rs.getString("verify")));
            }

            regnUsrList.add(donationInfo);
        }
        rs.close(); pst.close();
            
            String mconty="select country_idn,country_nm from mcountry order by country_nm";

            outLst = db.execSqlLst("pending registration list", mconty, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            ResultSet rs1 = (ResultSet)outLst.get(1);
            while (rs1.next()) {
                cDtl = new HashMap();               
                cDtl.put("cidn" ,(String)util.nvl(rs1.getString("country_idn")));
                cDtl.put("cnme" ,(String)util.nvl(rs1.getString("country_nm")));
                cList.add(cDtl);
            }
            rs1.close();
            
                request.setAttribute("cList",cList);
               
        pendingRegistrationForm.setUserRegnInfo(regnUsrList);
          util.updAccessLog(request,response,"PendingRegistration", "load end");
        return mapping.findForward("load");
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
              rtnPg=util.checkUserPageRights("",util.getFullURL(req));
              if(rtnPg.equals("unauthorized"))
              util.updAccessLog(req,res,"PendingRegistration", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"PendingRegistration", "init");
          }
          }
          return rtnPg;
          }
    public ActionForward   addUsrEmp(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
                      HttpSession session = request.getSession(false);
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
                      rtnPg=init(request,response,session,util);
                      }else
                      rtnPg="sessionTO";
                      if(!rtnPg.equals("sucess")){
                          return mapping.findForward(rtnPg);   
                      }else{           
                       PendingRegistrationForm pendingRegistrationForm = (PendingRegistrationForm)form;
                       pendingRegistrationForm.reset();
                       String regnIdn = util.nvl((String)request.getParameter("regnIdn"));
                       request.setAttribute("regnIdn", regnIdn);
                       return mapping.findForward("addUsrEmp");   
                      }
                    }

    public ActionForward saveRegn(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
       PendingRegistrationForm pendingRegistrationForm = (PendingRegistrationForm)form;
      HttpSession session = request.getSession(false);
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
      rtnPg=init(request,response,session,util);
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          return mapping.findForward(rtnPg);   
      }else{
          util.updAccessLog(request,response,"PendingRegistration", "save reg");
            HashMap pageDtlMap=new HashMap();
            String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("PENDING_REG");
            ArrayList pageList=new ArrayList();
        HashMap logDetails=new HashMap();
        HashMap mailDtl = util.getMailFMT("ACCEPT_REG");
        String regnIdn =util.nvl((String) request.getParameter("regnIdn"));
        String grpnmeId ="";
        String empIdn ="";    
            if(regnIdn.equals("")){
                regnIdn =util.nvl((String)pendingRegistrationForm.getValue("regnIdn"));
             
            }
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = util.nvl((String)dbinfo.get("CNT"));
                                                                 
        ArrayList ary = new ArrayList();
        ary.add(regnIdn);
        HashMap dbmsInfo = info.getDmbsInfoLst();
        
        if(mailDtl!=null && mailDtl.size()>0){
        String regSql ="select wr.reg_id,wr.usr,wr.pwd, wr.fnm, wr.lnm, wr.co_nme, wr.eml," +
                       " wr.biz,wr.mship,country from web_reg wr where wr.reg_id= ? and wr.stt='P' ";

            ArrayList outLst = db.execSqlLst("regSql", regSql, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        if(rs.next()){
        int ct = db.execCall("reg info", "web_pkg.do_reg(?)", ary);
        if(ct>0){
                   grpnmeId =util.nvl((String)pendingRegistrationForm.getValue("groupIdn"));
                   empIdn =util.nvl((String)pendingRegistrationForm.getValue("empIdn"));
               if(grpnmeId.equals("0"))
               grpnmeId="";
               if(empIdn.equals("0"))
               empIdn="";
               if(!grpnmeId.equals("") || !empIdn.equals("")){
               ResultSet res = db.execSql("regSql", "select nme_idn from web_reg where reg_id= ?", ary);
            if(res.next()){
             String  nme_idn=util.nvl(String.valueOf(res.getInt("nme_idn")));
             ArrayList params=new ArrayList();
             params.add(grpnmeId);
             params.add(empIdn);
             params.add(nme_idn);
             String updateReg="update mnme set grp_nme_idn=? , emp_idn=? where nme_idn=?" ;
             int  ctt = db.execUpd("update webReg", updateReg, params);
             if(!grpnmeId.equals("") && !grpnmeId.equals("0")){
                 int nme_grp_idn=util.getSeqVal("nme_grp_seq");
                 String inserQ="insert into nme_grp (nme_grp_idn, nme_idn , grp_nme, ctg, typ) \n" + 
                 "values(?, ?, 'HK', 'OWN','BUYER')";
                 params=new ArrayList();
                 params.add(String.valueOf(nme_grp_idn));
                 params.add(nme_idn);
                 int cnt1 = db.execUpd("inserQ", inserQ, params);
                 
                 inserQ="insert into nme_grp_dtl (nme_grp_dtl_idn, nme_grp_idn , nme_idn, cur)\n" + 
                 "values(nme_grp_dtl_seq.nextval, ?, ?, 'USD')";
                 params=new ArrayList();
                 params.add(String.valueOf(nme_grp_idn));
                 params.add(grpnmeId);
                 cnt1 = db.execUpd("inserQ", inserQ, params);
             }
             
             if(grpnmeId.equals("185")){
             params=new ArrayList();
             params.add(nme_idn);
             int cnt1 = db.execUpd("inserQ", "update nmerel set flg='NEW' where nme_idn=?", params);
             }
                params=new ArrayList();
                params.add(nme_idn);
                ct = db.execCall("DP_WEB_REG_ADDON", "DP_WEB_REG_ADDON(pNmeIdn => ?)", params);
                request.setAttribute("nme_idn", nme_idn);
            }
            res.close();
            request.setAttribute("msg", "Update sale Person and Group Company ");
            }else{
                 request.setAttribute("msg", "Not Update Sale Person and group Company");
             }                
        String usr = util.nvl(rs.getString("usr"));
        String pwd = util.nvl(rs.getString("pwd"));
        String coNme = util.nvl(rs.getString("co_nme"));
        String eml = util.nvl(rs.getString("eml"));
        String contryIdn = util.nvl(rs.getString("country"));
        String sal ="";
        String salEml = "";
        String byrNm = "";
        System.out.println("@--------ct---------@" + ct);
        GenMail mail = new GenMail();
        MailAction mailFt = new MailAction();
        info.setSmtpHost((String)dbmsInfo.get("SMTPHost"));    
        info.setSmtpPort(Integer.parseInt((String)dbmsInfo.get("SMTPPort")));    
        info.setSmtpUsr((String)dbmsInfo.get("SMTPUsr"));    
        info.setSmtpPwd((String)dbmsInfo.get("SMTPPwd"));  
        
        mail.setInfo(info);
        mail.init();
//        if(info.getDmbsInfoLst().get("CNT").equals("kj")){
//            sb.append("<table  border=\"1\" id=\"inv\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\" ><tr><td>");
//            sb.append("<table border=\"1\" cellspacing=\"2\" cellpadding=\"5\">");
//            String message = "  <tr> "+
//                        "<td colspan=\"2\"><p><img src=\"http://www.kapugems.com/kgstock/images/kapulogo.jpg\" alt=\"kapu Gems\" /> </p></td>"+
//                        "</tr>"+
//                        "<tr>"+
//                        "<td width=\"81%\"><p><strong>Registration Confirmation</strong> </p>"+
//                            "<p>Now you can Login on kapuGems.com With</p><p>UserName:"+ rs.getString("usr")+"</P><p>Password:"+ rs.getString("pwd")+"</p>";
//            sb.append(message);
//            sb.append("</table>");
//            sb.append("<p>Thank you.</p>");
//            
//        }else{
//        sb.append("<table  border=\"1\" id=\"inv\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\" ><tr><td>");
//        sb.append("<p>Welcome to the J.B. And Brothers  online Business facility.</p>");
//        sb.append("<p>Your registration has been approved and you are granted temporary access to our business website</P>");
//        sb.append("<p>J.B And Brothers Group is committed towards transparency in the business conducts & also promotes ethical business practices.</P>");
//        sb.append("<P>In this endeavor & per our business standards  it is required to have the attached documents executed from your end:<br></br> ");
//        sb.append("<ul><li>Document 1: <b>KYC Form</b> dully filled from your end</li>");
//        sb.append("<li>Document 2: <b>Business Partner�s Undertaking</b>  please copy this on your letter head & duly execute the document bearing the Authorised Signature & Company Seal (Stamp).</li>");
//        sb.append("</ul></p>");
//        sb.append("<p>Please follow the steps mentioned below:");
//        sb.append("<ul><li>Please login using your <B>Username :"+usr+" & Password: "+pwd+"</B></li>");
//        sb.append("<li>Please click on <b>Utility</b> button (OR under <b>Utility</b> menu) � please click on <b>Edit Your Registration Details</b></li>");
//        sb.append("<li>Please complete your profile under <b>EDIT PROFILE</b> page</li>");
//        sb.append("</ul>");
//        sb.append("<p>Note: Fields marked with <font color=\"red\">*</font> are mandatory fields </p>");
//        sb.append("<p><ul>" +
//                  "<li>Please download <b>Terms and Conditions</b> document (in .pdf format) & please duly execute the document bearing the Authorised Signature & Company Seal (Stamp) in the space provided.</li>" +
//                  "<li>Please scan all 3 documents (viz.: KYC Form; Business Partner�s Undertaking & Terms and Conditions) & upload them in the relevant space provided on EDIT PROFILE page.</li>" +
//                  "<li>Additionally please <b><i>send the Original Executed Documents to the following address:</i></b></li></ul></p>" +
//                 
//                  "<P><b>J.B. And Brothers Pvt Ltd.</b><br></br>" +
//                  "<b>Tower FC-3011, 3Rd Floor,</b><br></br>" +
//                  "<b>Bharat Diamond Bourse,</b><br></br>" +
//                  "<b>Bandra Kurla Complex</b><br></br>"+
//                  "<b>Bandra (East),</b><br></br>"+
//                  "<b>Mumbai : 400 051, India</b><br></br>");
//        sb.append("<p>Thank you.</p>");
//        }
            StringBuffer msg=new StringBuffer();
             String hdr = "<html><head><title>Registration Approved</title>\n"+
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
        String salEx = "select byr.get_nm(c.emp_idn) sal , byr.get_eml(c.emp_idn) salEml,byr.get_nm(c.nme_idn) byr from web_usrs a , nmerel b, mnme c " + 
        "where a.rel_idn = b.nmerel_idn and c.nme_idn = b.nme_idn " + 
        "and upper(a.usr) = upper('"+usr+"') and upper(a.pwd) = upper('"+pwd+"') ";
            outLst = db.execSqlLst("salEx", salEx, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            ResultSet rs1 = (ResultSet)outLst.get(1);
        if(rs1.next()){
        sal = util.nvl(rs1.getString("sal"));
        salEml = util.nvl(rs1.getString("salEml"));
        byrNm = util.nvl(rs1.getString("byr"));
        }
            rs1.close();
            pst.close();
        if(bodymsg.indexOf("~BYRNME~") > -1)
        bodymsg = bodymsg.replaceAll("~BYRNME~", byrNm);
        if(bodymsg.indexOf("~BYRNME~") > -1)
        bodymsg = bodymsg.replaceAll("~BYRNME~", byrNm);
        if(bodymsg.indexOf("~SALEXC~") > -1 && !sal.equals(""))
        bodymsg = bodymsg.replaceAll("~SALEXC~", "For any further assistance; please feel free to contact your Sales Executive:"+sal);
            if(bodymsg.indexOf("~USR~") > -1)
            bodymsg = bodymsg.replaceAll("~USR~", usr);
            if(bodymsg.indexOf("~PASS~") > -1)
            bodymsg = bodymsg.replaceAll("~PASS~", pwd);
        msg.append(bodymsg);
        msg.append("</body>");
        msg.append("</html>");
            String mailSub=util.nvl((String)mailDtl.get("SUBJECT"));
            if(mailSub.indexOf("~BYRNME~") > -1)
            mailSub = mailSub.replaceFirst("~BYRNME~", byrNm); 
            if(mailSub.indexOf("~CO~") > -1)
            mailSub = mailSub.replaceFirst("~CO~", coNme);
            if(mailSub.indexOf("~DTE~") > -1)
            mailSub = mailSub.replaceFirst("~DTE~", util.getToDte());
            if(mailSub.indexOf("~WHO~") > -1)
            mailSub = mailSub.replaceFirst("~WHO~", info.getUsr());
            mail.setSubject(mailSub);
//          mail.setSubject("Registration Approved for "+coNme);
//        mail.setSubject("Registration Approved for "+coNme);
       String senderID =(String)dbmsInfo.get("SENDERID");
            String senderNm =(String)dbmsInfo.get("SENDERNM");
            if(senderID.equals("NA"))
            senderID = salEml;
            if(cnt.equals("hk")){
            ArrayList country=fourtenCountry(request);
            if(country.contains(contryIdn)){
              senderID="hk@hk.co";
              senderNm= "H.K. IMPEX";
              mail.setCC("hk@hk.co");
              mail.setCC("brijesh@hk.co");
            }
            }
            mail.setSender(senderID, senderNm); 
            String toEml = util.nvl((String)mailDtl.get("TOEML"));
            if(toEml.indexOf("BYR")!=-1 && !eml.equals("")){
            mail.setTO(eml);
            }
            if(toEml.indexOf("SALEXC")!=-1 && !salEml.equals("")){
            mail.setTO(salEml);
            mail.setReplyTo(salEml);
            }
            String[] emlToLst = toEml.split(",");
            if(emlToLst!=null){
            for(int i=0 ; i <emlToLst.length; i++)
            {
            String to=util.nvl(emlToLst[i]);
            if(!to.equals("BYR") && !to.equals("SALEXC")){
            mail.setTO(to);
            }
            }
            }
            
            
            String cceml = util.nvl((String)mailDtl.get("CCEML"));
            String[] emlLst = cceml.split(",");
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
        String mailMag = msg.toString();
        mail.setMsgText(mailMag);
            logDetails.put("BYRID","");
            logDetails.put("RELID","");
            logDetails.put("TYP","ACCEPT_REG");
            logDetails.put("IDN",regnIdn);
            String mailLogIdn=util.mailLogDetails(request,logDetails,"I");  
            logDetails.put("MSGLOGIDN",mailLogIdn);
            logDetails.put("MAILDTL",mail.send(""));
            util.mailLogDetails(request,logDetails,"U");
        response.getWriter().write("<script>alert(\\\"Thank you for Registering...\\\");</script>");
            String updateReg = "update web_reg set stt='A' where reg_id= ?";
            ary = new ArrayList();
            ary.add(regnIdn);
            ct = db.execUpd("update webReg", updateReg, ary);
        }
        }
            rs.close(); pst.close();
        }     
          
        pageList= ((ArrayList)pageDtl.get("ACCEPT_WITH_DTL") == null)?new ArrayList():(ArrayList)pageDtl.get("ACCEPT_WITH_DTL");
        if(pageList!=null && pageList.size() >0){  
        request.setAttribute("edit_contact", "Y");
        return addUsrEmp(mapping, form, request, response);
        }
          util.updAccessLog(request,response,"PendingRegistration", "reg end");
        return load(mapping, form, request, response);

        }
    }
 
    public ActionForward rejectRegn(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        //PendingRegistrationForm pendingRegistrationForm = (PendingRegistrationForm)form;
      HttpSession session = request.getSession(false);
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
      rtnPg=init(request,response,session,util);
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          return mapping.findForward(rtnPg);   
      }else{
          util.updAccessLog(request,response,"PendingRegistration", "reject reg");
        String regnIdn = request.getParameter("regnIdn");
     
        ArrayList ary = new ArrayList();
        ary.add(regnIdn);
       String updateReg = "update web_reg set stt='R' where reg_id= ? and stt='P'";
       int ct = db.execUpd("update webReg", updateReg, ary);
        if (ct > 0) {
        HashMap mailDtl = util.getMailFMT("REJECT_REG");
        ary = new ArrayList();
        ary.add(regnIdn);
        String sql = "select fnm,lnm,eml,co_nme,country FROM web_reg WHERE reg_id=? and stt='R'";

            ArrayList outLst = db.execSqlLst("fmly memb", sql, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        if(rs.next()){
        String email = util.nvl(rs.getString("eml"));
        String fnm = util.nvl(rs.getString("fnm"));
        String lnm = util.nvl(rs.getString("lnm"));
        String co_nme = util.nvl(rs.getString("co_nme"));
         String contry = util.nvl(rs.getString("country"));
        if(mailDtl!=null && mailDtl.size()>0){
            HashMap usrdtl=new HashMap();
            usrdtl.put("FNM", fnm);
            usrdtl.put("EMAIL", email);
            usrdtl.put("LNM", lnm);
            usrdtl.put("CO", co_nme);
            usrdtl.put("REGID", regnIdn);
            usrdtl.put("COUNTRY",contry);
            sendmail(request,response,mailDtl,usrdtl);
            response.getWriter().write("<script>alert(\\\"Your Registraion will be Cancel shortly.\\\");</script>");
        }
        }
        rs.close(); pst.close();
        }else{
        response.getWriter().write("<script>alert(\\\"Denying Registration Failed.\\\");</script>");
        }
        load(mapping, form, request, response);
          util.updAccessLog(request,response,"PendingRegistration", "reject end");
        return mapping.findForward("load");
        }
    }
    public ActionForward rejectALL(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        //PendingRegistrationForm pendingRegistrationForm = (PendingRegistrationForm)form;
        HttpSession session = request.getSession(false);
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
        rtnPg=init(request,response,session,util);
        }else
        rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return mapping.findForward(rtnPg);   
        }else{
          util.updAccessLog(request,response,"PendingRegistration", "reject all");
       ArrayList ary = new ArrayList();
       ArrayList reIdnLst=new ArrayList();
       HashMap usrdtl=new HashMap();
       HashMap mailDtl = util.getMailFMT("REJECT_REG");
       String sql = "select fnm,lnm,eml,co_nme,reg_id,country FROM web_reg WHERE stt='P'";
            ArrayList outLst = db.execSqlLst("fmly memb", sql, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
       while(rs.next()){
           HashMap usr=new HashMap();
           String reg_id = util.nvl(rs.getString("reg_id"));
           usr.put("FNM", util.nvl(rs.getString("fnm")));
           usr.put("EMAIL", util.nvl(rs.getString("eml")));
           usr.put("LNM", util.nvl(rs.getString("lnm")));
           usr.put("CO", util.nvl(rs.getString("co_nme")));
           usr.put("COUNTRY",util.nvl(rs.getString("country")));
           usr.put("REGID", reg_id);
           reIdnLst.add(reg_id);
           usrdtl.put(reg_id,usr);
       }
            rs.close(); pst.close();
       String updateReg = "update web_reg set stt='R' where stt='P'";
       int ct = db.execUpd("update webReg", updateReg, new ArrayList());
       if(ct>0 && reIdnLst!=null && reIdnLst.size()>0){
           for(int i=0;i<reIdnLst.size();i++){
           String reg_id = util.nvl((String)reIdnLst.get(i));
           HashMap usr=new HashMap();
           usr=(HashMap)usrdtl.get(reg_id);
           if(usr!=null && usr.size()>0) {
           sendmail(request,response,mailDtl,usr);        
           }
           } 
           response.getWriter().write("<script>alert(\\\"All Registraion will be Cancel shortly.\\\");</script>"); 
       }
        load(mapping, form, request, response);
          util.updAccessLog(request,response,"PendingRegistration", "rejectall end");
        return mapping.findForward("load");
        }
    }
    public ActionForward linkUserNavigate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
      HttpSession session = request.getSession(false);
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
      rtnPg=init(request,response,session,util);
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          return mapping.findForward(rtnPg);   
      }else{
        return mapping.findForward("linkExistingUser");
        }
    }

    public ActionForward linkExistingUserRegn(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                              HttpServletResponse response) throws Exception {
      HttpSession session = request.getSession(false);
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
      rtnPg=init(request,response,session,util);
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          return mapping.findForward(rtnPg);   
      }else{
          util.updAccessLog(request,response,"PendingRegistration", "linkExistingUser");
        PendingRegistrationForm regForm = (PendingRegistrationForm)form;

        String regnIdn = util.nvl(request.getParameter("regnIdn"));
      
        String rlnId = regForm.getByrRln();
    
        ArrayList ary = new ArrayList();
        ary.add(regnIdn);
        ary.add(rlnId);

       int ct = db.execCall("link existing user registration", "web_pkg.do_reg_link(?,?)", ary);
       
          return load(mapping, form, request, response);
        }
    }
    
  public ActionForward populateCountryList(ActionMapping am, ActionForm a, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
      StringBuffer sb = new StringBuffer();
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
    String sql="select country_idn, country_nm from mcountry" ;
  

          ArrayList outLst = db.execSqlLst("country search", sql, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
    while(rs.next()){
        
        sb.append("<country>");
        sb.append("<id>"+ rs.getInt("country_idn")+"</id>");
        sb.append("<name>"+rs.getString("country_nm")+"</name>");
        sb.append("</country>");
        }
      rs.close(); pst.close();
        res.getWriter().write("<values>" +sb.toString()+ "</values>");
        
    System.out.println("@-------populateCountryList ends----------@");
   return null;
      }
  }
    public void sendmail(HttpServletRequest req , HttpServletResponse res,HashMap mailDtl,HashMap usrdtl ){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
            HashMap dbmsInfo = info.getDmbsInfoLst();
            GenMail mail = new GenMail();
            HashMap logDetails=new HashMap();
            HashMap dbinfo = info.getDmbsInfoLst();        
            String cnt = util.nvl((String)dbinfo.get("CNT"));
            info.setSmtpHost((String)dbmsInfo.get("SMTPHost"));    
            info.setSmtpPort(Integer.parseInt((String)dbmsInfo.get("SMTPPort")));    
            info.setSmtpUsr((String)dbmsInfo.get("SMTPUsr"));    
            info.setSmtpPwd((String)dbmsInfo.get("SMTPPwd"));  
            mail.setInfo(info);
            mail.init();
            String regnIdn = util.nvl((String)usrdtl.get("REGID"));
            String email = util.nvl((String)usrdtl.get("EMAIL"));
            String fnm = util.nvl((String)usrdtl.get("FNM"));
            String lnm = util.nvl((String)usrdtl.get("LNM"));
            String co_nme = util.nvl((String)usrdtl.get("CO"));
            String countryIdn = util.nvl((String)usrdtl.get("COUNTRY"));
                        StringBuffer msg=new StringBuffer();
                 String hdr = "<html><head><title>Registration</title>\n"+
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
            if(bodymsg.indexOf("~FNM~") > -1)
            bodymsg = bodymsg.replaceAll("~FNM~", fnm);
            if(bodymsg.indexOf("~LNM~") > -1)
            bodymsg = bodymsg.replaceAll("~LNM~", lnm);
            msg.append(bodymsg);
            msg.append("</body>");
            msg.append("</html>");
            String mailSub=util.nvl((String)mailDtl.get("SUBJECT"));
            if(mailSub.indexOf("~FNM~") > -1)
            mailSub = mailSub.replaceFirst("~FNM~", fnm); 
            if(mailSub.indexOf("~CO~") > -1)
            mailSub = mailSub.replaceFirst("~CO~", co_nme);
            if(mailSub.indexOf("~DTE~") > -1)
            mailSub = mailSub.replaceFirst("~DTE~", util.getToDteMarker());           
            if(mailSub.indexOf("~WHO~") > -1)
            mailSub = mailSub.replaceFirst("~WHO~", info.getUsr());
            mail.setSubject(mailSub);
            String senderID =(String)dbmsInfo.get("SENDERID");
            String senderNm =(String)dbmsInfo.get("SENDERNM");
        if( cnt.equals("hk")){
        ArrayList country=fourtenCountry(req);
            if(country.contains(countryIdn)){
            senderID="hk@diamondbyhk.com";
            senderNm= "H.K. IMPEX";
            mail.setCC("hk@diamondbyhk.com");
            mail.setCC("brijesh@diamondbyhk.com");
        }
        }
            mail.setSender(senderID, senderNm); 
            String toEml = util.nvl((String)mailDtl.get("TOEML"));
           if(toEml.indexOf("FNM")!=-1){
           mail.setTO(email);
           }
           String[] emlToLst = toEml.split(","); 
           if(emlToLst!=null){
           for(int i=0 ; i <emlToLst.length; i++)
           {
           String to=util.nvl(emlToLst[i]);
           if(!to.equals("FNM")){   
           mail.setTO(to);
           }
           }
           }
            String cceml = util.nvl((String)mailDtl.get("CCEML"));
            String[] emlLst = cceml.split(",");
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
                        String mailMag = msg.toString();
                        mail.setMsgText(mailMag);
        logDetails.put("BYRID","");
        logDetails.put("RELID",regnIdn);
        logDetails.put("TYP","REJECT_REG");
        logDetails.put("IDN",regnIdn);
        String mailLogIdn=util.mailLogDetails(req,logDetails,"I");  
        logDetails.put("MSGLOGIDN",mailLogIdn);
        logDetails.put("MAILDTL",mail.send(""));
        util.mailLogDetails(req,logDetails,"U");
    }
    public ArrayList fourtenCountry(HttpServletRequest req){
              HttpSession session = req.getSession(false);
              InfoMgr info = (InfoMgr)session.getAttribute("info");
              DBUtil util = new DBUtil();
              DBMgr db = new DBMgr();
              db.setCon(info.getCon());
              util.setDb(db);
              util.setInfo(info);
              db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
              util.setLogApplNm(info.getLoApplNm());
          ArrayList fourtyConty =new ArrayList();  
                ArrayList ary =new ArrayList();  
                    try{
               String  contyQry= "select chr_fr countryIdn , upper(dsc) country from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and\n" + 
                " b.mdl = 'JFLEX' and b.nme_rule = 'REG_COUNTRIES'  and a.til_dte is null order by a.srt_fr";

                    ArrayList outLst = db.execSqlLst("contyQry", contyQry, new ArrayList());
                    PreparedStatement pst = (PreparedStatement)outLst.get(0);
                    ResultSet rs = (ResultSet)outLst.get(1);
                
                while(rs.next()){
                   fourtyConty.add((String)rs.getString("countryIdn"));
                }
                rs.close(); pst.close();
                }catch(Exception e) {
                    
                    e.printStackTrace();
                }
                
            return fourtyConty;
            }       
    
    public ActionForward loadreject(ActionMapping am, ActionForm af, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Adv Contact", "loadpa start");
            util.updAccessLog(req,res,"PendingRegistration", "loadreject");
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = (String)dbinfo.get("CNT");
            PendingRegistrationForm udf = (PendingRegistrationForm) af;
            udf.reset();
        ArrayList paLst=new ArrayList();
        HashMap paDtl=new HashMap();
        String sqlQ="select reg_id,usr,fnm, lnm, co_nme,dsgn, bldg,street, \n" + 
        "tel , tel1,fax, mbl ,eml,know_us,biz,mship, to_char(dt_tm, 'DD-MON-YYYY') dt_tm from  web_reg where stt='R' order by usr";
            ArrayList outLst = db.execSqlLst("Repeat Customer", sqlQ, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                paDtl = new HashMap();
                paDtl.put("reg_id", util.nvl((String)rs.getString("reg_id")));
                paDtl.put("usr", util.nvl((String)rs.getString("usr")));
                paDtl.put("name", util.nvl(rs.getString("fnm")) + " " + util.nvl(rs.getString("lnm")));
                paDtl.put("co_nme", util.nvl((String)rs.getString("co_nme")));
                paDtl.put("dsgn", util.nvl((String)rs.getString("dsgn")));
                paDtl.put("address", util.nvl(rs.getString("bldg")) + " " + util.nvl(rs.getString("street")));
                paDtl.put("tel", util.nvl((String)rs.getString("tel")));
                paDtl.put("tel1", util.nvl((String)rs.getString("tel1")));
                paDtl.put("fax", util.nvl((String)rs.getString("fax")));
                paDtl.put("mbl", util.nvl((String)rs.getString("mbl")));
                paDtl.put("eml", util.nvl((String)rs.getString("eml")));
                paDtl.put("know_us", util.nvl((String)rs.getString("know_us")));
                paDtl.put("biz", util.nvl((String)rs.getString("biz")));
                paDtl.put("mship", util.nvl((String)rs.getString("mship")));
                paDtl.put("dt_tm", util.nvl((String)rs.getString("dt_tm")));
                paLst.add(paDtl);
            }
            rs.close(); pst.close();  
        session.setAttribute("rejLst", paLst);
            util.updAccessLog(req,res,"PendingRegistration", "loadreject end");
        return am.findForward("loadreject");
        }
    }
     
    public ActionForward savereject(ActionMapping am, ActionForm af, HttpServletRequest req,
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
            util.updAccessLog(req,res,"PendingRegistration", "savereject start");
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = (String)dbinfo.get("CNT");
            PendingRegistrationForm udf = (PendingRegistrationForm) af;
            String delete=util.nvl((String)udf.getValue("delete"));
            ArrayList  paLst= (session.getAttribute("rejLst") == null)?new ArrayList():(ArrayList)session.getAttribute("rejLst");
            int paLstsz=paLst.size();
            ArrayList ary=new ArrayList();
            int updcnt=0;
            String stt="P";
            if(!delete.equals(""))
                stt="D";
        for(int i=0;i<paLstsz;i++){
            HashMap paDtl=(HashMap)paLst.get(i);
            String reg_id=util.nvl((String)paDtl.get("reg_id"));
            String chk=util.nvl((String)udf.getValue("stt_"+reg_id));
            if(chk.equals("Y")){
                ary=new ArrayList();
                ary.add(stt);
                ary.add(reg_id);
                int upd = db.execUpd("update typ", "update web_reg set stt=? where reg_id=? and stt='R' ", ary);
                if(upd>0){
                    updcnt++;
                }
            }
        }
        if(updcnt>0){
            req.setAttribute("rtnmsg","Move To Pending Sucessfully");
        }
        util.updAccessLog(req,res,"PendingRegistration", "savereject end");
        return loadreject(am,af,req,res);
        }
    }
}
