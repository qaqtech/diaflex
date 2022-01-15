package ft.com;

//~--- non-JDK imports --------------------------------------------------------


import java.net.InetAddress;
import javax.servlet.http.Cookie;
import java.net.NetworkInterface;
import java.sql.Connection;
import java.net.SocketException;
import java.net.UnknownHostException;

import java.sql.CallableStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.Enumeration;
import java.util.HashMap;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

//~--- JDK imports ------------------------------------------------------------

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

public class LoginAction extends DispatchAction {
    public LoginAction() {
        super();
    }
    public ActionForward login(ActionMapping am, ActionForm af, HttpServletRequest request,
                               HttpServletResponse response)
            throws Exception {
         synchronized(this){
         HttpSession session = request.getSession(true);
         DBMgr db= new DBMgr();
         InfoMgr info=null;
         DBUtil util=null;
         GtMgr gtMgr = new GtMgr();
        LoginForm   udf     = (LoginForm) af;
        info = new InfoMgr();
        util = new DBUtil();
//        String      dbTyp   = request.getParameter("dbTyp"); MAYUR 04-NOV-2014
        String      dfaccount     = util.nvl(request.getParameter("dfaccount").toUpperCase());
        info.setDfAcct(dfaccount);
        String      dbTyp   = db.validateAccountNm(dfaccount);
        if(!dbTyp.equals("FAIL")){
        String      usr     = util.nvl(request.getParameter("dfusr"));
        String      pwd     = util.nvl(request.getParameter("dfpwd"));
        String      connectBy     = util.nvl(request.getParameter("connectBy"));
        String sessionid=session.getId();
        request.setCharacterEncoding("UTF-8");    // Do this so we can capture non-Latin chars
        String manualconnectBy= util.nvl((String)db.validateAccountNm(dfaccount+"_CONN_TYPE"));
        if(!manualconnectBy.equals("FAIL"))
        connectBy=manualconnectBy;
//        if(dfaccount.equalsIgnoreCase("kg"))
//            connectBy="CP";
        info.setConnectBy(connectBy);
        info.setDbTyp(dbTyp);
        String usrip="";
        usrip=util.nvl(request.getParameter("ip_val"));
        if(usrip.equals(""))
        usrip=request.getRemoteAddr();
        System.out.println("Who am I :"+dbTyp+":"+usr+":"+pwd);
        boolean valid = db.init(dbTyp,connectBy);
        if(!valid)
            return am.findForward("dbnotavl");   
             
        info.setCon(db.getCon());
        util.setDb(db);
        util.setInfo(info);
        Boolean isValid = false;
        isValid = util.authenticate(usr, pwd);
            if (isValid.booleanValue()) {
                if(util.nvl(info.getUsrFlg()).equals("SYS"))
                dfaccount="";
                Cookie accnm = new Cookie ("ACCOUNTNM",dfaccount);
                accnm.setMaxAge(365 * 24 * 60 * 60);
                response.addCookie(accnm);
                info.setSessionId(sessionid);
                String schema = db.getSchema(dbTyp);
                /*
                if(isProposal){
                    db.close();
                    db  = new DBMgr();
                    dbTyp = "OracleProposal";
                    db.init("OracleProposal",usr);
                    
                    log.log(db.getStatus());
                    util.setDb(db);
                    util.setInfo(info);
                    util.setLog(log);
                }
                */
//                util.initPrp();
                info.setSchema(schema.toUpperCase());
                HashMap pricePrpDtl=new HashMap();
                pricePrpDtl.put("MFG_PRI", "MFG_DIS");
                pricePrpDtl.put("MFG_DIS", "MFG_PRI");
                pricePrpDtl.put("RTE", "UPR_DIS");
                pricePrpDtl.put("UPR_DIS", "RTE");
                pricePrpDtl.put("CP", "CP_DIS");
                pricePrpDtl.put("CP_DIS", "CP");
                pricePrpDtl.put("FA_PRI", "FA_DIS");
                pricePrpDtl.put("FA_DIS", "FA_PRI");
                session.setAttribute("pricePrpDtl", pricePrpDtl);
                String applNm = (String)info.getDmbsInfoLst().get("COMPANY")+" DF";
                info.setLoApplNm(applNm);
                db.setLogApplNm(applNm);
                util.setLogApplNm(applNm);
                db.setDsName(dbTyp);
                util.setDbUsr(request,usrip);
                HashMap dbinfo = info.getDmbsInfoLst();
//                String errLvl = util.nvl((String)dbinfo.get("ERRLVL"));
//                db.setErrLvl(Integer.parseInt(errLvl));
                String diaflexMode = util.nvl((String)dbinfo.get("DIAFLEX_MODE"));
                if(diaflexMode.equalsIgnoreCase("C")&& info.getUsrFlg().equals("SU")){
                    String Diaflexonpath = util.nvl((String)dbinfo.get("DIAFLEXON_PATH"));
                    String errorpage = util.nvl((String)dbinfo.get("ERROR_PAGE"));
                    util.updateDiaflexMode();
                    db.close();
                    db=null;
                    session.invalidate();
                    response.sendRedirect(errorpage+"?MSGON=Y");
                    return null;
                } else if(diaflexMode.equalsIgnoreCase("C") && !info.getUsrFlg().equals("SYS")){
                    db.close();
                    db=null;
                    session.invalidate();
                    return am.findForward("dbnotavl"); 
                }
                
                String prpblock =util.nvl((String)dbinfo.get("PRPBLOCKON"));
                if(prpblock.equals("Y")){
                    ArrayList pageblockList=util.pagedefblockPrp("BLOCKPRP","BLOCKPRP");
                    info.setPageblockList(pageblockList);
                }
                util.pagedefblockPrpLs("BLOCK","LS");
                String dashboardon =util.nvl((String)dbinfo.get("DASHBOARDON"));
                if(dashboardon.equals("Y")){
                HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
                HashMap pageDtl=(HashMap)allPageDtl.get("DASH_BOARD");
                if(pageDtl==null || pageDtl.size()==0){
                    pageDtl=new HashMap();
                    pageDtl=util.pagedefdashboard("DASH_BOARD");
                    allPageDtl.put("DASH_BOARD",pageDtl);
                }
                info.setPageDetails(allPageDtl);
                
                
                
                    ArrayList pageLst=util.dashboardvisibility();
                    info.setDashboardvisibility(pageLst);
                }
                util.alterSession(schema);
                session.setAttribute("info",info);
                session.setAttribute("logUSR",usr);
                session.setAttribute("gtMgr", gtMgr);
                InetAddress ip=null;
                String ipAddr =null;
                String macStr =null;
                try {
                    
                    String getLogId = " select df_login_log_seq.nextval logid from dual " ;
                    ResultSet rs = db.execSql(" Log id ", getLogId, new ArrayList());
                    if(rs.next()) {
                        int logId = rs.getInt(1);
                        info.setLogId(logId);
                        db.setLogid(String.valueOf(logId));
                        
                        ArrayList ary = new ArrayList();
                        ary.add(String.valueOf(logId));
                        int ct = db.execCall("Set_log_id", " PACK_VAR.Set_LogIdn(?)", ary);
                        
                        Cookie cookie = new Cookie("DFLOGIDN", String.valueOf(logId));
                        cookie.setMaxAge(24*60*60);
                        response.addCookie(cookie);
                        
                        String sessionInfo ="select sid,serial# , username from v$session where sid in ( select sid from v$mystat)";
                        ResultSet rs1= db.execSql("sessionInfo", sessionInfo, new ArrayList());
                        String sid ="";
                        String serial = "";
                        if(rs1.next()){
                            sid = rs1.getString(1);
                            serial = rs1.getString(2);
                        }
                        rs1.close();
                        info.setSid(sid);
                        String countryNme="",status="";
//                        JSONParser parser = new JSONParser();
//                        Object obj1 = parser.getJSONFromUrl("http://ip-api.com/json/"+usrip);
//                        JSONObject jsonObject = (JSONObject) obj1;
//                        if(jsonObject!=null){
//                        status=util.nvl((String) jsonObject.get("status"));
//                        if(status.equals("success"))
//                        countryNme = util.nvl((String) jsonObject.get("country"));
//                        }
                        
                        String insLogQ = " insert into df_login_log(log_idn , log_dte , df_usr , df_ip, df_mch,cl_browser, df_country ,DB_SID,DB_SERIAL,JAVA_SID) "+
                            " select ?, sysdate, ?, ?, ?, ? ,? , ? , ?, ? from dual ";
                        ary = new ArrayList();
                        ary.add(Integer.toString(logId));
                        ary.add(usr);
                        ary.add(usrip);
                        ary.add(request.getRemoteUser());
                        ary.add(request.getHeader("User-Agent"));
                        ary.add(countryNme);
                        ary.add(sid);
                        ary.add(serial);
                        ary.add(info.getSessionId());
                        ct = db.execUpd(" insert log ", insLogQ, ary);
                        
                        ary = new ArrayList();
                        ct = db.execCall("Set_log_id", "PACK_VAR.Set_Special(GET_USR_SPECIAL)", ary);
                    }
                    rs.close();
                    ip = InetAddress.getLocalHost();
                    ipAddr = ip.getHostAddress();
//                    System.out.println("Current IP address : " + ip.getHostAddress());
                    NetworkInterface network = NetworkInterface.getByInetAddress(ip);

//                byte[] mac = network.getHardwareAddress();
//
//                System.out.print("Current MAC address : ");
//
//                StringBuilder sb = new StringBuilder();
//                for (int i = 0; i < mac.length; i++) {
//                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
//                }
//                macStr = sb.toString();

                } catch (UnknownHostException e) {

                e.printStackTrace();

                } catch (SocketException e){

                e.printStackTrace();

                }
                String rtnPg="loginsuccess";
                String diaflexsmsMode = util.nvl((String)dbinfo.get("DIAFLEX_SMS_MODE"));
                if(diaflexsmsMode.equalsIgnoreCase("ON") && !info.getAuth_mode().equalsIgnoreCase("NONE") && !info.getUsrFlg().equals("SYS")){
                   sendOtp(request);
                   rtnPg="loadOTP";
                }
//                util.getheadermsg();
                util.updAccessLog(request,response,"Login", "Login");
                
//                String adv_pri = util.nvl((String)dbinfo.get("ADV_PRI"),"N");
//                if(adv_pri.equals("Y"))
//                new ResetArrayThread(request).start();
                
                String insertdbms_mac_address ="select count(*) from dbms_mac_address where mac_id= ? and lcl_ip=?";
                ArrayList ary = new ArrayList();
                ary.add(macStr);
                ary.add(ipAddr);
                ResultSet rs = db.execSql("insert dbms_mac_address", insertdbms_mac_address, ary);
                if(rs.next()){
                int cnt = rs.getInt(1);
                if(cnt>0){
                    return am.findForward(rtnPg);
                }else{
                    info.setMacId(macStr);
                    info.setLclIp(ipAddr);
                    return am.findForward(rtnPg);
                }
                }else{
                info.setMacId(macStr);
                info.setLclIp(ipAddr);    
                    return am.findForward(rtnPg);
                }
                
               
            } else {
                Cookie accnm = new Cookie ("ACCOUNTNM",dfaccount);
                accnm.setMaxAge(365 * 24 * 60 * 60);
                response.addCookie(accnm);
                db.close();
                db=null;
                session.invalidate();
                response.sendRedirect(info.getErrorPath());
                return am.findForward("error");
            }
        }else{
            Cookie accnm = new Cookie ("ACCOUNTNM","");
            accnm.setMaxAge(365 * 24 * 60 * 60);
            response.addCookie(accnm);
            request.setAttribute("MSG", "Please Verify Account Name");
            return am.findForward("accountfail");
        }
        }
    }
    
    public void sendOtp(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList ary = new ArrayList();
        ArrayList typLst = new ArrayList();
        HashMap typDtl = new HashMap();
         String authQ ="select typ , val otp ,eml emlsendto,mbl mblsendto \n" + 
         "from df_login_auth where to_char(vld_upto, 'DD-MON HH24:mi')>=to_char(sysdate, 'DD-MON HH24:mi') \n" + 
         "and log_idn=? and stt='N'";
         ary.add(String.valueOf(info.getLogId()));
         ResultSet rs = db.execSql("GET OTP VALUE", authQ, ary);
         while(rs.next()){
         String typ=util.nvl((String)rs.getString("typ"));
         typDtl = new HashMap();
         typDtl.put("OTP",util.nvl((String)rs.getString("otp")));
         typDtl.put("TYP",typ);
         if(typ.equals("SMS"))
         typDtl.put("SENDTO",util.nvl((String)rs.getString("mblsendto")));
         if(typ.equals("EMAIL"))
         typDtl.put("SENDTO",util.nvl((String)rs.getString("emlsendto")));
         typLst.add(typDtl);
         }
         rs.close();
         
         for(int i=0;i<typLst.size();i++){
         typDtl = new HashMap();
         typDtl=(HashMap)typLst.get(i);  
         String TYP=util.nvl((String)typDtl.get("TYP"));
         String OTP=util.nvl((String)typDtl.get("OTP"));
         String SENDTO=util.nvl((String)typDtl.get("SENDTO"));
         if(TYP.equals("SMS")){
           generateSmsUrl(request,OTP,SENDTO);       
         }else{
           generateMail(request,OTP,SENDTO); 
         }
         }
          
    }
    
    public ActionForward verifyOtp (ActionMapping am, ActionForm af, HttpServletRequest request,
                               HttpServletResponse response)
            throws Exception {
            HttpSession session = request.getSession(false);
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
            rtnPg=init(request,response,session,util);
            }else{
            rtnPg="connExists";   
            }
            }else
            rtnPg="sessionTO";
            if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
            }else{
             LoginForm   udf     = (LoginForm) af;
             String dfOtp=util.nvl((String)request.getParameter("dfOtp")).trim();
             String verify=util.nvl((String)udf.getValue("verify"));
             String regenerate=util.nvl((String)udf.getValue("regenerate"));
             ArrayList ary =  new ArrayList();
             ary.add(String.valueOf(info.getLogId())); 
             String msg="";
             rtnPg="loginsuccess";
             int smsModeCounter=info.getSmsModeCounter();
             if(!verify.equals("")){
             util.updAccessLog(request,response,"Login", "verifyOtp verify start");
             boolean validate=false;
             ary =  new ArrayList();
             ary.add(String.valueOf(info.getLogId()));
             String getOtpId = "select  val  from df_login_auth where log_idn=? and stt='N' and val='"+dfOtp+"' and to_char(VLD_UPTO, 'DD-MON HH24:mi')>=to_char(sysdate, 'DD-MON HH24:mi')" ; 
             ResultSet rs = db.execSql(" Log id ", getOtpId,ary);
             if(rs.next()) {
                  validate=true;
             }
             rs.close();
             if(!validate){
                 rtnPg="loadOTP";
                 info.setSmsModeCounter(++smsModeCounter);
                 msg="Please Enter Correct OTP/Expired After Generation of 15 Mins/ReGenerate";
             }else{
                 String upGt = "update df_login_auth set stt = 'Y' where log_idn=? and stt='N' and val='"+dfOtp+"' ";
                 ary = new ArrayList();
                 ary.add(String.valueOf(info.getLogId()));
                 int ct = db.execUpd("updatGt", upGt, ary);
                 info.setSmsModeCounter(420);
             }
            }else{
                HashMap dbinfo = info.getDmbsInfoLst();
                String cnt = (String)dbinfo.get("CNT");
                util.updAccessLog(request,response,"Login", "verifyOtp regenerate start");
                if(smsModeCounter<=3){
                    String upGt = "update df_login_auth set val=DP_GET_AUTH_VAL(typ),VLD_UPTO=(sysdate + (15/1440)),stt='N' where log_idn=?";
                    if(cnt.equals("kj")){
                        upGt = "update df_login_auth set val=DF_GET_AUTH_VAL(typ),VLD_UPTO=(sysdate + (15/1440)),stt='N' where log_idn=?";
                    }
                    ary = new ArrayList();
                    ary.add(String.valueOf(info.getLogId()));
                    int ct = db.execUpd("updatGt", upGt, ary);
                    sendOtp(request);
                    rtnPg="loadOTP";
                    info.setSmsModeCounter(++smsModeCounter);
                    msg="OTP message send Sucessfully";  
                }else{
                    msg="Sorry, you have failed in Authentication process Your Account get Locked. Please Contact to your team !";  
                    String upGt = "update df_users set stt='LK' where usr_id=?";
                    ary = new ArrayList();
                    ary.add(String.valueOf(info.getUsrId()));
                    int ct = db.execUpd("updatGt", upGt, ary);
                    request.setAttribute("MSG",msg);
                    request.setAttribute("requrl",info.getReqUrl());
                    request.setAttribute("jsversion",info.getJsVersion());
                    request.setAttribute("usr",info.getUsr());
                    request.setAttribute("logoutlogIdn",String.valueOf(info.getLogId()));
                    db.close();
                    session.invalidate();
                    return am.findForward("chktimeoutpage");
                }
            }
            request.setAttribute("MSG",msg);
            util.updAccessLog(request,response,"OTP Verification", " OTP Verification"); 
            return am.findForward(rtnPg);
         }
        } 
    public void generateMail(HttpServletRequest request,String OTP,String SENDTO) throws Exception {
        HttpSession session = request.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HashMap dbinfo = info.getDmbsInfoLst();
        HashMap mailDtl = util.getMailFMT("OTP_MAIL");
        GenMail mail = new GenMail();
        HashMap logDetails=new HashMap();
        info.setSmtpHost((String)dbinfo.get("SMTPHost"));    
        info.setSmtpPort(Integer.parseInt((String)dbinfo.get("SMTPPort")));    
        info.setSmtpUsr((String)dbinfo.get("SMTPUsr"));    
        info.setSmtpPwd((String)dbinfo.get("SMTPPwd"));  
        mail.setInfo(info);
        mail.init();
        try{
            StringBuffer msg=new StringBuffer();
            String hdr = "<html><head><title>ONE TIME PASSWORD</title>\n"+
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
            String bodymsg= util.nvl((String)mailDtl.get("MAILBODY"));
            bodymsg = bodymsg.replaceAll("~DFUSR~", info.getUsr());
            bodymsg = bodymsg.replaceAll("~LOGID~", String.valueOf(info.getLogId()));
            bodymsg =bodymsg.replaceAll("~OTP~", OTP);
            bodymsg =bodymsg.replaceAll("~NME~", info.getDfNme());
            bodymsg =bodymsg.replaceAll("~USRTYP~", info.getDfUsrTyp());
            msg.append(bodymsg);
            msg.append("</body>");
            msg.append("</html>");
            String mailSub= util.nvl((String)mailDtl.get("SUBJECT"));
            mailSub = mailSub.replaceFirst("~DTE~", util.getToDteMarker());
            mailSub = mailSub.replaceAll("~DFUSR~", info.getUsr());
            mailSub = mailSub.replaceAll("~LOGID~", String.valueOf(info.getLogId()));
            mailSub =mailSub.replaceAll("~OTP~", OTP);
            mailSub =mailSub.replaceAll("~NME~", info.getDfNme());
            mailSub =mailSub.replaceAll("~USRTYP~", info.getDfUsrTyp());
            if(mailSub.indexOf("~DTE~") > -1)
            mailSub = mailSub.replaceFirst("~DTE~", util.getToDteMarker());
            
            mail.setSubject(mailSub);
        String senderID =(String)dbinfo.get("SENDERID");
        String senderNm =(String)dbinfo.get("SENDERNM");
        if(senderID.equals("NA"))
        senderID=util.nvl((String)dbinfo.get("SENDERIDIFNA"));  
        mail.setSender(senderID, senderNm); 
        String toEml = SENDTO;
        String[] emlToLst = toEml.split(",");
        if(emlToLst!=null){
        for(int i=0 ; i <emlToLst.length; i++){
        String to=util.nvl(emlToLst[i]);
        mail.setTO(to);
        }
        }
        String cceml = util.nvl((String)mailDtl.get("CCEML"));
        String[] emlLst = cceml.split(",");
        if(emlLst!=null){
        for(int i=0 ; i <emlLst.length; i++){
        mail.setCC(emlLst[i]);
        }
        }
        
        String bcceml = util.nvl((String)mailDtl.get("BCCEML"));
        String[] bccemlLst = bcceml.split(",");
        if(bccemlLst!=null){
        for(int i=0 ; i <bccemlLst.length; i++){
        mail.setBCC(bccemlLst[i]);
        }
        }
            mail.setMsgText(msg.toString());
            logDetails.put("BYRID","");
            logDetails.put("RELID","");
            logDetails.put("TYP","OTP_MAIL");
            logDetails.put("IDN",String.valueOf(info.getUsrId()));
            String mailLogIdn=util.mailLogDetails(request,logDetails,"I");  
            logDetails.put("MSGLOGIDN",mailLogIdn);
            logDetails.put("MAILDTL",mail.send(""));
            util.mailLogDetails(request,logDetails,"U");
        } catch (Exception e) {
         e.printStackTrace();
        }
    }
    public void generateSmsUrl(HttpServletRequest request,String OTP,String SENDTO) throws Exception {
        HttpSession session = request.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HashMap dbinfo = info.getDmbsInfoLst();
        String message = (String)dbinfo.get("SMS_MODE_MSG");
        String smsurl = (String)dbinfo.get("SMSURL");
        String senderID = (String)dbinfo.get("SMSSENDERID");
        String smspass = (String)dbinfo.get("SMSPASS");
        String smsusr = (String)dbinfo.get("SMSUSR");
        smsurl = smsurl.replaceAll("~USR~", smsusr);
        smsurl = smsurl.replaceAll("~PASS~", smspass);
        smsurl = smsurl.replaceAll("~SENSER~", senderID);
        
        message = message.replaceAll("~DFUSR~", info.getUsr());
        message = message.replaceAll("~LOGID~", String.valueOf(info.getLogId()));
        message =message.replaceAll("~OTP~", OTP);
        message =message.replaceAll("~NME~", info.getDfNme());
        message =message.replaceAll("~USRTYP~", info.getDfUsrTyp());
        
        smsurl = smsurl.replaceAll("~MSG~", message);
        smsurl = smsurl.replaceAll("~NOS~", SENDTO);
        request.setAttribute("sendsmsurl", smsurl);
    }
    public ActionForward loginhome(ActionMapping am, ActionForm af, HttpServletRequest request,
                               HttpServletResponse response)
            throws Exception {
            HttpSession session = request.getSession(false);
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
            rtnPg=init(request,response,session,util);
            }else{
            rtnPg="connExists";   
            }
            }else
            rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
             util.updAccessLog(request,response,"Home Action", "loginhome");
             String servPath = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
             info.setReqUrl(servPath); 
             HashMap dbinfo = info.getDmbsInfoLst();
             String url=util.nvl((String)dbinfo.get("DIAFLEX_URL"));
             Cookie cookie = new Cookie ("APPURL",url);
             cookie.setMaxAge(365 * 24 * 60 * 60);
             response.addCookie(cookie);
             String jsVersion=util.nvl((String)dbinfo.get("JSCSSVERSION"));
             info.setJsVersion(jsVersion);
             util.updAccessLog(request,response,"Login url", info.getReqUrl());
             
             
             String dashboardon =util.nvl((String)dbinfo.get("DASHBOARDON"));
             if(dashboardon.equals("Y")){
             HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
             HashMap pageDtl=(HashMap)allPageDtl.get("DASH_BOARD");
             if(pageDtl==null || pageDtl.size()==0){
                 pageDtl=new HashMap();
                 pageDtl=util.pagedefdashboard("DASH_BOARD");
                 allPageDtl.put("DASH_BOARD",pageDtl);
             }
             info.setPageDetails(allPageDtl);
            
            
             
                 ArrayList pageLst=util.dashboardvisibility();
                 info.setDashboardvisibility(pageLst);
             }
             util.updAccessLog(request,response,"Home Action", "loginhomeEnd");
            return am.findForward("home");
         }
        }
    public ActionForward security(ActionMapping am, ActionForm af, HttpServletRequest request,
                               HttpServletResponse response)
            throws Exception {
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
                return am.findForward(rtnPg);   
            }else{
          String securityCode = util.nvl(request.getParameter("security"));
         String dbSecureCode = util.nvl((String)info.getDmbsInfoLst().get("MAC_PASSWORD"));
         if(securityCode.equals(dbSecureCode)){
             String insertdbms_mac_address ="insert into dbms_mac_address(mac_id,lcl_ip,unm, dte,stt) values(? ,? , ? ,sysdate, 'A')";
             ArrayList ary = new ArrayList();
             ary.add(info.getMacId());
             ary.add(info.getLclIp());
             ary.add(info.getUsr());
             int ct = db.execUpd("insert dbms_mac_address", insertdbms_mac_address, ary);
            return am.findForward("loginsuccess");
         }else{
             request.setAttribute("msg", "Invaild Security Code");
             return am.findForward("securityPg");  
         }
         }
        }
    
    public ActionForward directLoad(ActionMapping am, ActionForm af, HttpServletRequest request,
                               HttpServletResponse response)
            throws Exception {
            HttpSession session = request.getSession(false);
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
            rtnPg=init(request,response,session,util);
            }else{
            rtnPg="connExists";   
            }
            }else
            rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
             util.updAccessLog(request,response,"Login Redirect", "Login Redirect");
             rtnPg="loginsuccess";
             HashMap dbinfo = info.getDmbsInfoLst();
             String diaflexsmsMode = util.nvl((String)dbinfo.get("DIAFLEX_SMS_MODE"));
             if(diaflexsmsMode.equalsIgnoreCase("ON") && !info.getAuth_mode().equalsIgnoreCase("NONE") && !info.getUsrFlg().equals("SYS")){
                int smsModeCounter=info.getSmsModeCounter();
                if(smsModeCounter!=420){
                rtnPg="loadOTP";
                }
             }
            return am.findForward(rtnPg);
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
            util.updAccessLog(req,res,"Login Action", "init");
            }
            }
            return rtnPg;
            }
}


//~ Formatted by Jindent --- http://www.jindent.com
