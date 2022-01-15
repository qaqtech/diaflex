package ft.com;

//~--- non-JDK imports --------------------------------------------------------


import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import java.net.InetAddress;
import javax.servlet.http.Cookie;
import java.net.NetworkInterface;
import java.sql.Connection;
import java.net.SocketException;
import java.net.UnknownHostException;

import java.sql.ResultSet;

import java.util.ArrayList;

import java.util.Enumeration;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

//~--- JDK imports ------------------------------------------------------------

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sun.misc.BASE64Decoder;

public class HomeAction extends DispatchAction {
    public HomeAction() {
        super();
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
    
    public ActionForward loadhome(ActionMapping am, ActionForm af, HttpServletRequest request,
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
             util.updAccessLog(request,response,"Home Action", "loadhome");
             HashMap dbinfo = info.getDmbsInfoLst();
             String dashboardon =util.nvl((String)dbinfo.get("DASHBOARDON"));
             if(dashboardon.equals("Y")){
             HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
             HashMap pageDtl=(HashMap)allPageDtl.get("DASH_BOARD");
                 pageDtl=new HashMap();
                 pageDtl=util.pagedefdashboard("DASH_BOARD");
                 allPageDtl.put("DASH_BOARD",pageDtl);
             info.setPageDetails(allPageDtl);
             
             ArrayList pageLst=util.dashboardvisibility();
             info.setDashboardvisibility(pageLst);
             }
            
             util.updAccessLog(request,response,"Home Action", "loadhomeEnd");
            return am.findForward("home");
         }
        }  
    
    public ActionForward logout(ActionMapping am, ActionForm af, HttpServletRequest request,
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
             session.setAttribute("info",null);
             util.updAccessLog(request,response,"Log Out", "Log OutStart");
             String updQ = " update df_login_log set stt='DF',flg='S',log_out_dte=sysdate where log_idn = ? ";
             ArrayList ary  = new ArrayList();
             ary.add(String.valueOf(info.getLogId()));
             int ct = db.execUpd(" upd df_login_log", updQ, ary);
             request.setAttribute("logoutlogIdn",String.valueOf(info.getLogId()));
             request.setAttribute("requrl",info.getReqUrl());
             request.setAttribute("jsversion",info.getJsVersion());
             util.updAccessLog(request,response,"Log Out", "Log OutEnd");
             db.close();
             session.invalidate();
            return am.findForward("logout");
         }
        }
    
    public ActionForward LoadIdeaBank(ActionMapping am, ActionForm af, HttpServletRequest request,
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
            String method=util.nvl(request.getParameter("md"));
            String action=util.nvl(request.getParameter("action"));
            String IDEABANK = util.nvl((String)info.getDmbsInfoLst().get("IDEABANK"));
            String cnt =  util.nvl((String)info.getDmbsInfoLst().get("CNT"));
            String url=IDEABANK+"/ideaBank/"+action+".do?method="+method+"&dfaccount="+info.getDfAcct()+"&logIdn="+info.getLogId();
            request.setAttribute("URL", url);
            return am.findForward("load");  
        }
            }
    public ActionForward loginInvalid(ActionMapping am, ActionForm af, HttpServletRequest request,
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
            rtnPg=inittimeOut(request,response,session,util);
            }else{
            rtnPg="connExists";   
            }
            }else
            rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
             session.setAttribute("info",null);
             util.updAccessLog(request,response,"loginInvalid", "InvalidateStart");
             String updQ = " update df_login_log set flg='S' where log_idn = ? ";
             ArrayList ary  = new ArrayList();
             ary.add(String.valueOf(info.getLogId()));
             int ct = db.execUpd(" upd df_login_log", updQ, ary);
             util.updAccessLog(request,response,"Invalidate", "InvalidateEnd");
             request.setAttribute("requrl",info.getReqUrl());
             request.setAttribute("jsversion",info.getJsVersion());
             request.setAttribute("usr",info.getUsr());
             request.setAttribute("logoutlogIdn",String.valueOf(info.getLogId()));
             db.close();
             session.invalidate();
            return am.findForward("chktimeoutpage");
         }
        }
    public ActionForward connExists(ActionMapping am, ActionForm af, HttpServletRequest request,
                               HttpServletResponse response)
            throws Exception {
            HttpSession session = request.getSession(false);
            request.setAttribute("connExists", "Y");
            session.invalidate();
            return am.findForward("connExistspage");
    }
    
    public ActionForward sessionTO(ActionMapping am, ActionForm af, HttpServletRequest request,
                               HttpServletResponse response)
            throws Exception {
            HttpSession session = request.getSession(false);
            session.invalidate();
            return am.findForward("connExistspage");
    }
    
    public ActionForward unauthorized(ActionMapping am, ActionForm af, HttpServletRequest request,
                               HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        if(info!=null){
        Connection conn=info.getCon();
        if(conn!=null){
        db.setCon(info.getCon());
        util.setDb(db);
        request.setAttribute("requrl",info.getReqUrl());
        request.setAttribute("jsversion",info.getJsVersion());
        request.setAttribute("usr",info.getUsr());
        request.setAttribute("logoutlogIdn",String.valueOf(info.getLogId()));
        db.close();
        session.setAttribute("info",null);
        session.invalidate();
        }
        }
        return am.findForward("unauthorizedpage");
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
            util.updAccessLog(req,res,"Home Action", "init");
            }
            }
            return rtnPg;
            }
    
    public ActionForward headermsg(ActionMapping mapping, ActionForm form,
                              HttpServletRequest req,
                              HttpServletResponse response) throws Exception {
        
          HttpSession session = req.getSession(false);
          InfoMgr info = (InfoMgr)session.getAttribute("info");
          DBUtil util = new DBUtil();
          DBMgr db = new DBMgr(); 
          db.setCon(info.getCon());
          util.setDb(db);
          util.setInfo(info);
          db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
          util.setLogApplNm(info.getLoApplNm());
          HashMap dbinfo = info.getDmbsInfoLst();
          String headermsg = util.nvl((String)dbinfo.get("HEADERMSGON"),"N");
          String msgheader="NULL";
          if(headermsg.equals("Y")){
          util.getheadermsg();
          msgheader = util.nvl((String)info.getMsgheader(),"NULL");
          }
          response.setContentType("text/xml"); 
          response.setHeader("Cache-Control", "no-cache"); 
          response.getWriter().write("<message>"+msgheader+"</message>");
      return null;
      }
    
    public ActionForward prtScn(ActionMapping am, ActionForm af, HttpServletRequest request,
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
             String img_val=util.nvl(request.getParameter("img_val"));
            System.out.println(img_val);
             try {
                 String base64Image = img_val.split(",")[1];
                 BufferedImage image = null;
                 byte[] imageByte;

                 BASE64Decoder decoder = new BASE64Decoder();
                 imageByte = decoder.decodeBuffer(base64Image);
                 ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
                 image = ImageIO.read(bis);
                 bis.close();

                 // write the image to a file
                 File outputfile = new File("C:\\image.png");
                 ImageIO.write(image, "png", outputfile);
             } catch (IOException ex) {
                 System.err.println(ex);
             }
            return am.findForward("prtScn");
         }
        }  
    public String inittimeOut(HttpServletRequest req , HttpServletResponse res,HttpSession session,DBUtil util) {
            String rtnPg="sucess";
            String invalide="";
            String connExists=util.nvl(util.getConnExists());  
            if(session.isNew())
            rtnPg="sessionTO";    
            if(connExists.equals("N"))
            rtnPg="connExists";     
//            if(rtnPg.equals("sucess")){
//            boolean sout=util.getLoginsession(req,res,session.getId());
//            if(!sout){
//            rtnPg="sessionTO";
//            System.out.print("New Session Id :="+session.getId());
//            }else{
//            util.updAccessLog(req,res,"Home Action", "init");
//            }
//            }
            return rtnPg;
            }
}


//~ Formatted by Jindent --- http://www.jindent.com

