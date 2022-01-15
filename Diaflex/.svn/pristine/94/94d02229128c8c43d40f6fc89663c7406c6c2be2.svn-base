package ft.com.mpur;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.GenMail;
import ft.com.InfoMgr;
import ft.com.JspUtil;
import ft.com.MailAction;
//import ft.com.SyncOnRap;
import ft.com.dao.ByrDao;

import ft.com.dao.MemoType;

import ft.com.dao.PktDtl;

import ft.com.dao.TrmsDao;
import ft.com.generic.GenericImpl;

import ft.com.generic.GenericInterface;
import ft.com.marketing.SearchForm;
import ft.com.marketing.SearchQuery;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.OutputStream;

import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.ArrayList;

import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

public class PurchaseDmdAction extends DispatchAction {
    public ActionForward load(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          util.updAccessLog(req,res,"Purchase Dms", "load start");
          HashMap dbinfo = info.getDmbsInfoLst();
          String sz = (String)dbinfo.get("SIZE");
          if(info.getVwPrpLst()==null)
          
          util.initSrch();  
//          if(info.getSrchPrp()==null)
//              util.initPrpSrch();
        PurchaseDmdForm srchForm = (PurchaseDmdForm)form;
        GenericInterface genericInt = new GenericImpl();
        SearchQuery query = new SearchQuery();
        srchForm.reset(); 
        ArrayList byrLst= query.getByrList(req,res);
        srchForm.setempList(byrLst);
        srchForm.setEmpId("0");
          ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_purDmdSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_purDmdSrch");
        util.getcrtwtPrp(sz,req,res);
          util.updAccessLog(req,res,"Purchase Dms", "load end");
     return am.findForward("load");
      }
  }
    
    public ActionForward loadDmd(ActionMapping mapping,
    ActionForm form,
    HttpServletRequest req,
    HttpServletResponse response) throws Exception {
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
        rtnPg=init(req,response,session,util);
        }else{
        rtnPg="connExists";   
        }
        }else
        rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return mapping.findForward(rtnPg);  
        }else{
        util.updAccessLog(req,response,"Purchase Dmd", "loadDmd start");
    PurchaseDmdForm srchForm = (PurchaseDmdForm)form;
    SearchQuery query = new SearchQuery();
    ArrayList ary = null;
    String dmdIDN = util.nvl((String)srchForm.getDmdId(),"0");
    String empidn = util.nvl((String)srchForm.getEmpId(),"0");
    String buttonPressed = "" ;     
    if(srchForm.getValue("pb_dmd")!=null)
    buttonPressed = "dmd";
    if(srchForm.getValue("upd_dmd")!=null)
    buttonPressed = "upddmd";
    if(srchForm.getValue("sv_dmd")!=null)
    buttonPressed = "svdmd";
    if(srchForm.getValue("load")!=null)
    buttonPressed = "load";
    if(srchForm.getValue("remove")!=null)
    buttonPressed = "remove";
    ArrayList dmdLst=new ArrayList();
    HashMap mailDtl=new HashMap();
    HashMap dtl=new HashMap();
    if(buttonPressed.equals("load") || buttonPressed.equals("remove")){
    ArrayList byrLst= query.getByrList(req,response);
    srchForm.setempList(byrLst);
    String dmdDsc="";
    ary = new ArrayList();
    ary.add(dmdIDN);
            //      ResultSet rs = db.execSql("dmdDsc", "select a.dsc , a.name_id , a.rln_id,get_xrt(b.cur) xrt,byr.get_nm(a.name_id) nme from mdmd a,nmerel b where a.dmd_id=? and a.rln_id=nmerel_idn", ary); ///Mayur
    String getdtlQ="select a.dsc , a.name_id\n" + 
    "     from mdmd a\n" + 
    "     where a.dmd_id=?";
    ArrayList rsList = db.execSqlLst("dmdDsc", getdtlQ, ary);
    PreparedStatement pst = (PreparedStatement)rsList.get(0);
    ResultSet rs = (ResultSet)rsList.get(1);
    if(rs.next()){
    dmdDsc=util.nvl(rs.getString("dsc"));
    srchForm.setEmpId(empidn);
    }

    rs.close();
    pst.close();
    
    if(buttonPressed.equals("load") && !dmdIDN.equals("0")){
    int srchId = query.loadDmdPur(req,response, Integer.valueOf(dmdIDN));
    if(srchId!=0){
    TrmsDao trms = new TrmsDao();
    trms.setRelId(Integer.parseInt(dmdIDN));
    trms.setTrmDtl(dmdDsc);
    dmdLst.add(trms);
    srchForm.setDmdList(dmdLst);
    srchForm.setDmdId(dmdIDN);
    req.setAttribute("DmdDsc",dmdDsc);
    req.setAttribute("srchIdn",String.valueOf(srchId));
    req.setAttribute("dmdIdn",String.valueOf(dmdIDN));
    return loadSrchPrp(mapping, form, req, response);
    }
    }else if(buttonPressed.equals("remove") && !dmdIDN.equals("0")){
        ary = new ArrayList();
        ary.add(dmdIDN);
        int updCt = db.execCall(" Rmv Dmd ", " dmd_pkg.rmv(?) ", ary);
        mailDtl = util.getMailFMT("PUR_RMV");
        dtl=new HashMap();
        dtl.put("DMDDSC",dmdDsc);
        dtl.put("EMPIDN",empidn);
        dtl.put("TYP","PUR_RMV");
        dtl.put("IDN",String.valueOf(dmdIDN));
        sendmail(req,response,mailDtl,dtl);
        req.setAttribute("dmdMsg", "Demand Deleted Sucessfully");
    }
        }else if(!empidn.equals("0")){

        int lSrchId=0;
        if(!buttonPressed.equals("svdmd") ){
        lSrchId = query.saveSearchpur(req, srchForm, buttonPressed);
        }
        if(( buttonPressed.equalsIgnoreCase("svdmd"))||(buttonPressed.equalsIgnoreCase("dmd"))|| (buttonPressed.equalsIgnoreCase("upddmd"))) {
        if((buttonPressed.equalsIgnoreCase("dmd"))||( buttonPressed.equalsIgnoreCase("upddmd")) ||( buttonPressed.equalsIgnoreCase("svdmd")) ){
        ary = new ArrayList();
        String dsc = util.nvl((String)srchForm.getValue("dmdDsc")).trim();
        String oldDmdId = util.nvl((String)srchForm.getValue("oldDmdId"));
        ary.add(String.valueOf(info.getByrId()));
        String checksdsc = "select * from mdmd where upper(dsc) = upper('"+dsc+"') and name_id = ? and todte is null  and mdl='PUR'  ";
        if(buttonPressed.equalsIgnoreCase("upddmd")) {
             checksdsc = "select * from mdmd where upper(dsc) = upper('"+dsc+"') and name_id = ? and todte is null  and mdl='PUR' and dmd_id != ? ";
             ary.add(oldDmdId);
         }
          ArrayList rsList = db.execSqlLst("mdmd", checksdsc, ary);
          PreparedStatement pst = (PreparedStatement)rsList.get(0);
          ResultSet rs = (ResultSet)rsList.get(1);
        if(rs.next()){
            req.setAttribute("dmdMsg", "Demand Name already exist Please changes and try again.");
        }
            rs.close();
            pst.close();
        if( buttonPressed.equalsIgnoreCase("upddmd"))
        {
        if(!oldDmdId.equals("")) {
        ArrayList rmAry = new ArrayList();
        rmAry.add(oldDmdId);
        int updCt = db.execCall(" Rmv Dmd ", " dmd_pkg.rmv(?) ", rmAry);
        }
        }
            if(buttonPressed.equalsIgnoreCase("svdmd")){
                lSrchId=Integer.parseInt(req.getParameter("svsrchId"));
            }
        int dmdID = query.addDmdPur(req,response,empidn, lSrchId, dsc);
        if(buttonPressed.equalsIgnoreCase("dmd")){
        String dtls=util.nvl((String)req.getAttribute("dtls"));
        mailDtl = util.getMailFMT("PUR_ADD");
        dtl=new HashMap();
        dtl.put("DMDDSC",dsc);
        dtl.put("DTLS",dtls);
        dtl.put("EMPIDN",empidn);
        dtl.put("TYP","PUR_ADD");
        dtl.put("IDN",String.valueOf(dmdID));
        sendmail(req,response,mailDtl,dtl);
        req.setAttribute("dmdMsg", "Demand Save Sucessfully");
        }
        if(buttonPressed.equalsIgnoreCase("upddmd"))
        {
            String dtls=util.nvl((String)req.getAttribute("dtls"));
            mailDtl = util.getMailFMT("PUR_UPD");
            dtl=new HashMap();
            dtl.put("DMDDSC",dsc);
            dtl.put("DTLS",dtls);
            dtl.put("EMPIDN",empidn);
            dtl.put("TYP","PUR_UPD");
            dtl.put("IDN",String.valueOf(dmdID));
            sendmail(req,response,mailDtl,dtl);
            req.setAttribute("dmdMsg", "Demand Updated Sucessfully");
        }
        if((buttonPressed.equalsIgnoreCase("svdmd"))){
            srchForm.setEmpId(empidn);
        }        
        }
        }
            
    }    
        util.updAccessLog(req,response,"Purchase Dmd", "loadDmd end");
        return load(mapping, form, req, response);
    }
    }
    
    public ActionForward loadSrchPrp(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest req,
                                     HttpServletResponse response) throws Exception {
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
        rtnPg=init(req,response,session,util);
        }else{
        rtnPg="connExists";   
        }
        }else
        rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return mapping.findForward(rtnPg); 
        }else{
            util.updAccessLog(req,response,"Purchase Dmd", "loadSrchPrp start");
        PurchaseDmdForm srchForm = (PurchaseDmdForm)form;
        String sz = util.nvl((String)info.getDmbsInfoLst().get("SIZE"));
        if(sz.equals(""))
            sz="SIZE";
            int srchIDN =Integer.parseInt((String)req.getAttribute("srchIdn"));
            int dmdIDN= Integer.parseInt((String)req.getAttribute("dmdIdn"));
            String dmdDsc = (String)req.getAttribute("DmdDsc");
            srchForm.setValue("oldDmdId",dmdIDN);
            srchForm.setValue("dmdDsc", dmdDsc);
        HashMap vals = new HashMap();
        ArrayList srchList = new ArrayList();
        HashMap prp = info.getPrp();
        ArrayList ary = new ArrayList();
        String getDtls =
                   " select a.mprp ,a.vfr , a.vto , decode(b.dta_typ, 'C', a.sfr , 'D', a.sfr , decode(a.mprp, 'CRTWT', to_char(trunc(nfr,2),'90.00'), to_char(nfr))) sfr  " + 
                   "   , decode(b.dta_typ, 'C', a.sto, 'D', a.sto , decode(a.mprp, 'CRTWT', to_char(trunc(nto,2),'90.00'),  to_char(nto))) sto , b.dta_typ " + " from srch_dtl a, mprp b where a.mprp = b.prp and a.srch_id = ? ";
                ary = new ArrayList();
        ary.add(String.valueOf(srchIDN));

          ArrayList rsList = db.execSqlLst(" Dmd Id :" + srchIDN, getDtls, ary);
          PreparedStatement pst = (PreparedStatement)rsList.get(0);
          ResultSet rs = (ResultSet)rsList.get(1);
        while (rs.next()) {
            String mprp = util.nvl(rs.getString("mprp"));
            String sfr = util.nvl(rs.getString("sfr"));
            String sto = util.nvl(rs.getString("sto"));
            String vfr = util.nvl(rs.getString("vfr"));
            String vto = util.nvl(rs.getString("vto"));
            if (rs.getString("dta_typ").equals("C")) {
                String dspTxt ="";
                ArrayList prpSrt = (ArrayList)prp.get(mprp + "S");
                ArrayList prpVal = (ArrayList)prp.get(mprp + "V");
                int startAt = prpVal.indexOf(vfr);
                if(startAt==-1)
                    startAt = prpSrt.indexOf(sfr);
                int endAt = prpVal.indexOf(vto);
                if(endAt==-1)
                    endAt = prpSrt.indexOf(sto);
                List rngVals = prpSrt.subList(startAt, endAt+1);

                for (int i = 0; i < rngVals.size(); i++) {
                    String lSrt = (String)rngVals.get(i);
                    String lVal = (String)prpVal.get(prpSrt.indexOf(lSrt));
                    srchForm.setValue(mprp + "_" + lVal, lVal);
                    dspTxt += " , " + lVal;
                }
                if(dspTxt.length() > 3)
                  srchForm.setValue("MTXT_" + mprp, dspTxt.substring(3).replaceAll("NA", ""));

            }
            if (rs.getString("dta_typ").equals("D")) {
                sfr = sfr.substring(6, 8)+"-"+sfr.substring(4, 6)+"-"+sfr.substring(0, 4);
                sto = sto.substring(6, 8)+"-"+sto.substring(4, 6)+"-"+sto.substring(0, 4);
            }
            if(mprp.equals("CRTWT")){
                srchForm.setValue("wt_fr_c", sfr);
                srchForm.setValue("wt_to_c", sto);
                srchForm.setValue("MTXT_CRTWT", sfr+"-"+sto);
            }else{
            srchForm.setValue(mprp + "_1", sfr);
            srchForm.setValue(mprp + "_2", sto);
            }

        }
        rs.close();
        pst.close();  
        String getSubDtls =
       " select a1.mprp, a1.sfr, a1.sto, a1.vfr, b1.srt psrt, b1.val pval "+
       " from (select a.mprp, a.sfr ,a.vfr, a.sto, decode(mprp, 'CRTWT', get_sz(sfr), a.vfr) sz, "+
       " decode(a.mprp, 'CRTWT', '"+sz+"', a.mprp) prp from srch_dtl_sub a where a.srch_id = ?) a1, prp b1 "+
       " where a1.prp = b1.mprp and a1.sz = b1.val order by a1.mprp ";

        ary = new ArrayList();
        ary.add(String.valueOf(srchIDN));

        String dspTxt = "", lPrp = "";
          rsList = db.execSqlLst(" SubSrch :" + srchIDN, getSubDtls, ary);
         pst = (PreparedStatement)rsList.get(0);
        rs = (ResultSet)rsList.get(1);
        while (rs.next()) {
            String mprp = util.nvl(rs.getString("mprp"));
            String sfr = util.nvl(rs.getString("sfr"));
            String sto = util.nvl(rs.getString("sto"));
            String vfr = util.nvl(rs.getString("vfr"));
            String psrt = util.nvl(rs.getString("psrt"));
            String pval = util.nvl(rs.getString("pval"));


            if (lPrp.equals(""))
                lPrp = mprp;

            if (lPrp.equals(mprp)) {
           if (mprp.equals("CRTWT")) 
                 dspTxt += " , " + pval;
             else    
                 dspTxt += " , " + vfr;
             }else {
             srchForm.setValue("MTXT_" + lPrp, dspTxt.substring(3).replaceAll("NA", ""));
                 if (mprp.equals("CRTWT")) 
                       dspTxt = " , " + pval;
                  else
                      dspTxt = " , " + vfr;
              lPrp = mprp;
             } 
            if (mprp.equals("CRTWT")) {
                srchForm.setValue(mprp + "_" + psrt, pval);
                srchForm.setValue(mprp + "_1_" + psrt, sfr);
                srchForm.setValue(mprp + "_2_" + psrt, sto);
                srchForm.setValue("wt_fr_c","");
                srchForm.setValue("wt_to_c", "");
                srchForm.setValue("MTXT_" + lPrp,"");
            } else
                srchForm.setValue(mprp + "_" + vfr, vfr);
        }
        rs.close();
         pst.close();
        if(dspTxt.length() > 3)
          srchForm.setValue("MTXT_" + lPrp, dspTxt.substring(3).replaceAll("NA", ""));

        srchForm.setValue("oldsrchId", srchIDN);
            if(util.nvl(info.getIsEx()).equals("is2Ex")){
              srchForm.setValue("is2Ex", info.getIsEx());
            }
            if(util.nvl(info.getIsEx()).equals("is3Ex")){
              srchForm.setValue("is3Ex", info.getIsEx());
            }
            if(util.nvl(info.getIsEx()).equals("is3VG")){
              srchForm.setValue("is3VG", info.getIsEx());
            }
            util.updAccessLog(req,response,"Purchase Dmd", "loadSrchPrp start");
        return mapping.findForward("load");
        }
    }
    
    public ActionForward loaddmdIdns(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
      int fav = 0;
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
      StringBuffer sb = new StringBuffer();
      String nmeId = util.nvl(req.getParameter("nmeIdn"));
      ArrayList ary=new ArrayList();
        ary.add(nmeId);
        String favSrch = "select dmd_id  , dsc from mdmd where name_id=? and mdl='PUR' and todte  is null";
          ArrayList rsList = db.execSqlLst("favSrch",favSrch,ary);
          PreparedStatement pst = (PreparedStatement)rsList.get(0);
          ResultSet rs = (ResultSet)rsList.get(1);
        while(rs.next()){
            fav=1;
            sb.append("<dmdLst>");
            sb.append("<nme>" + util.nvl(rs.getString("dsc")) +"</nme>");
            sb.append("<nmeid>" + util.nvl(rs.getString("dmd_id")) +"</nmeid>");
            sb.append("</dmdLst>");
        }
        rs.close();
         pst.close();
       if(fav==1)
        res.getWriter().write("<dmdLsts>" +sb.toString()+ "</dmdLsts>");
        return null;
        }
    }
    public void sendmail(HttpServletRequest req , HttpServletResponse res,HashMap mailDtl,HashMap dtl)throws Exception{
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
            info.setSmtpHost((String)dbmsInfo.get("SMTPHost"));    
            info.setSmtpPort(Integer.parseInt((String)dbmsInfo.get("SMTPPort")));    
            info.setSmtpUsr((String)dbmsInfo.get("SMTPUsr"));    
            info.setSmtpPwd((String)dbmsInfo.get("SMTPPwd"));  
            mail.setInfo(info);
            mail.init();
            String dmddsc = util.nvl((String)dtl.get("DMDDSC"));
            String dmddtls = util.nvl((String)dtl.get("DTLS"));
            String empIdn = util.nvl((String)dtl.get("EMPIDN"));
            String mailtyp = util.nvl((String)dtl.get("TYP"));
            String dmdIdn = util.nvl((String)dtl.get("IDN"));

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
            if(bodymsg.indexOf("~DMD~") > -1)
            bodymsg = bodymsg.replaceAll("~DMD~", dmddsc);
            if(bodymsg.indexOf("~DTLS~") > -1)
            bodymsg = bodymsg.replaceAll("~DTLS~", dmddtls);
            msg.append(bodymsg);
            msg.append("</body>");
            msg.append("</html>");
            String mailSub=util.nvl((String)mailDtl.get("SUBJECT"));
            if(mailSub.indexOf("~DMD~") > -1)
            mailSub = mailSub.replaceFirst("~DMD~", dmddsc); 
            mailSub = mailSub+" "+util.getToDteMarker();
            mail.setSubject(mailSub);
            String senderID =(String)dbmsInfo.get("SENDERID");
            String senderNm =(String)dbmsInfo.get("SENDERNM");
            mail.setSender(senderID, senderNm); 
            String toEml = util.nvl((String)mailDtl.get("TOEML"));
           
            if(toEml.indexOf("SALEX")!=-1){
                String byrmailid = "select b.txt emailid from nme_dtl b where b.mprp like 'EMAIL%' and nme_idn=? and vld_dte is null";
                ArrayList ary=new ArrayList();
                ary.add(empIdn);
             ArrayList rsList = db.execSqlLst("Buyer Mail Ids", byrmailid ,ary);
             PreparedStatement pst = (PreparedStatement)rsList.get(0);
             ResultSet rs = (ResultSet)rsList.get(1);
                while(rs.next()){
                    String email=util.nvl(rs.getString("emailid"));
                    if(!email.equals("NA") && !email.equals("")){
                        mail.setTO(email);
                    }
                }
                rs.close();  
                pst.close();
           }
           String[] emlToLst = toEml.split(","); 
           if(emlToLst!=null){
           for(int i=0 ; i <emlToLst.length; i++)
           {
           String to=util.nvl(emlToLst[i]);
           if(!to.equals("SALEX")){   
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
        logDetails.put("BYRID",empIdn);
        logDetails.put("RELID","");
        logDetails.put("TYP",mailtyp);
        logDetails.put("IDN",dmdIdn);
        String mailLogIdn=util.mailLogDetails(req,logDetails,"I");  
        logDetails.put("MSGLOGIDN",mailLogIdn);
        logDetails.put("MAILDTL",mail.send(""));
        util.mailLogDetails(req,logDetails,"U");
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
                util.updAccessLog(req,res,"Purchase Dmd", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Purchase Dmd", "init");
            }
            }
            return rtnPg;
            }
    public PurchaseDmdAction() {
        super();
    }
}
