package ft.com.contact;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.FormsUtil;
import ft.com.InfoMgr;
import ft.com.dao.GenDAO;
import ft.com.dao.UIForms;
import ft.com.dao.UIFormsFields;
import ft.com.fileupload.FileUploadForm;
import ft.com.fileupload.FileUploadInterface;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.SearchQuery;
import ft.com.masters.ActivityForm;
import ft.com.masters.EmpDepForm;
import ft.com.masters.MprpForm;

import ft.com.masters.PrpForm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.io.OutputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class BulkUpdateAction extends DispatchAction {
    private final String formName   = "contactbulkupdate";
 
    public BulkUpdateAction() {
        super();
    }
    
    public ActionForward bulkUpdate(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
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
            util.updAccessLog(req,res,"Contact Bulk Upd", "bulkUpdate start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        AdvContactForm udf = (AdvContactForm)af;
        ArrayList    params  = new ArrayList();
        ArrayList chkNmeIdnList = (info.getChkNmeIdnList() == null)?new ArrayList():(ArrayList)info.getChkNmeIdnList();
        if(chkNmeIdnList.size()>0){
        String nmeidnLst = chkNmeIdnList.toString();
        nmeidnLst = nmeidnLst.replace('[',' ');
        nmeidnLst = nmeidnLst.replace(']',' ');
        String msg="";
            nmeidnLst=nmeidnLst.trim();
            String[] nmeLst = nmeidnLst.split(",");
            int loopCnt = 1 ;
            System.out.println(nmeLst.length);
            float loops = ((float)nmeLst.length)/900;
            loopCnt = Math.round(loops);
                if(nmeLst.length <= 900 || new Float(loopCnt)>=loops) {
                
            } else
        loopCnt += 1 ;
        if(loopCnt==0)
        loopCnt += 1 ;
        int fromLoc = 0 ;
        int toLoc = 0 ;
        for(int j=0; j < daos.size(); j++) {
        UIFormsFields dao = (UIFormsFields)daos.get(j);
        String lFld = dao.getFORM_FLD();
        String htmlFldTbl = dao.getTBL_NME();
        String fldNm= htmlFldTbl+"_"+ lFld;//tablename_columnname
        String lVal  = util.nvl((String) udf.getValue(fldNm));
        String chklVal  = util.nvl((String) udf.getValue(fldNm+"_CHK"));
        String fldTtl = util.nvl(dao.getDSP_TTL(), lFld);
        int count=0;
        if(chklVal.equals("Y")){
        if(lVal.equals("0"))
            lVal="";
        params  = new ArrayList();
        params.add(lVal);
        String updQ="";
        if(htmlFldTbl.equalsIgnoreCase("mnme")){
        updQ="update mnme set "+lFld+" = ? where nme_idn ";        
        }
        if(htmlFldTbl.equalsIgnoreCase("maddr")){
        updQ="update maddr set "+lFld+" = ? where nme_idn ";             
        }  
        if(htmlFldTbl.equalsIgnoreCase("nmerel")){
        updQ="update nmerel set "+lFld+" = ? where nme_idn ";             
        }
        if(htmlFldTbl.equalsIgnoreCase("nme_dtl")){
        params.add(lFld);    
        updQ="update nme_dtl set txt = ? where  mprp=? and nme_idn ";                            
        }    
            for(int i=1; i <= loopCnt; i++) {
            int aryLoc = Math.min(i*900, nmeLst.length-1) ;
            String lookupVnm = nmeLst[aryLoc];
            if(i == 1)
            fromLoc = 0 ;
            else
            fromLoc = toLoc+1;
            toLoc = Math.min(nmeidnLst.lastIndexOf(lookupVnm) + lookupVnm.length(), nmeidnLst.length());
            String vnmSub = nmeidnLst.substring(fromLoc, toLoc);
                System.out.println(updQ+" in ("+vnmSub+")");
            int ct = db.execDirUpd(" upd  ", updQ+" in ("+vnmSub+") and vld_dte is null", params);
            count=count+ct;
            }
            msg=msg+","+fldTtl+"("+count+")";
        }
        }
            msg=msg.replaceFirst("\\,", "");
            req.setAttribute("RTMSG", "Contact Sucessfully Updated -:"+msg);
        }else{
            req.setAttribute("RTMSG", "Please Check Checkbox To Update");
        }
        udf.reset();
            util.updAccessLog(req,res,"Contact Bulk Upd", "bulkUpdate end");
        return am.findForward("load");
        }
    }
    
    public ActionForward viewbulkwebrole(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
              throws Exception {
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
              util.updAccessLog(req,res,"Contact Bulk Upd", "viewbulkwebrole start");
        AdvContactForm udf = (AdvContactForm)af;
        ResultSet rs           = null;
        ArrayList params   = new ArrayList();
        ArrayList partydtl=new ArrayList();
        ArrayList usrIdnLst=new ArrayList();
        ArrayList webroledscList = new ArrayList();
        HashMap webroleDtl=new HashMap();
          ArrayList chkNmeIdnList = (info.getChkNmeIdnList() == null)?new ArrayList():(ArrayList)info.getChkNmeIdnList();
          if(chkNmeIdnList.size()>0){        
                  String nmeidnLst = chkNmeIdnList.toString();
                  nmeidnLst = nmeidnLst.replace('[',' ');
                  nmeidnLst = nmeidnLst.replace(']',' ');
                      nmeidnLst=nmeidnLst.trim();
                      String[] nmeLst = nmeidnLst.split(",");
                      int loopCnt = 1 ;
                      System.out.println(nmeLst.length);
                      float loops = ((float)nmeLst.length)/900;
                      loopCnt = Math.round(loops);
                          if(nmeLst.length <= 900 || new Float(loopCnt)>=loops) {
                          
                      } else
                  loopCnt += 1 ;
                  if(loopCnt==0)
                  loopCnt += 1 ;
                  int fromLoc = 0 ;
                  int toLoc = 0 ;
        String sql= "select  role_idn , nvl(role_dsc,role_nm) role_dsc from mrole where stt='A' and to_dte is null order by role_idn";

              ArrayList outLst = db.execSqlLst("webroleDtl", sql,new ArrayList());
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              rs = (ResultSet)outLst.get(1);
        while(rs.next()){
          webroledscList.add(util.nvl(rs.getString("role_dsc")));
          webroleDtl.put(util.nvl(rs.getString("role_dsc")),rs.getInt("role_idn"));
          }
          rs.close(); pst.close();        
        
        sql = "select c.usr_idn,c.role_idn\n" + 
        "from web_usrs a, nmerel_all_trm_v b,usr_role c where 1 =1 \n" + 
        "and a.rel_idn = b.nmerel_idn \n" + 
        "and a.usr_id=c.usr_idn and c.stt='A'\n" + 
        "and b.nme_idn ";
          for(int i=1; i <= loopCnt; i++) {
                      int aryLoc = Math.min(i*900, nmeLst.length-1) ;
                      String lookupVnm = nmeLst[aryLoc];
                      if(i == 1)
                      fromLoc = 0 ;
                      else
                      fromLoc = toLoc+1;
                      toLoc = Math.min(nmeidnLst.lastIndexOf(lookupVnm) + lookupVnm.length(), nmeidnLst.length());
                      String vnmSub = nmeidnLst.substring(fromLoc, toLoc);
                          System.out.println(sql+" in ("+vnmSub+")");

           outLst = db.execSqlLst("WebMenuRole", sql+" in ("+vnmSub+") order by 1", new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            String usr_idn = rs.getString("usr_idn");
            String role_idn =  rs.getString("role_idn");
            String fldNme = usr_idn+"_NA_"+role_idn;
            udf.setValue(fldNme,fldNme);
        }
          rs.close(); pst.close();
       }
       sql = "select a.usr,a.usr_id usrid,b.nme,decode(a.to_dt,'','A','IA') stt\n" + 
       "from web_usrs a, nmerel_all_trm_v b where 1 =1 \n" + 
       "and a.rel_idn = b.nmerel_idn \n" + 
       "and b.nme_idn";
          for(int i=1; i <= loopCnt; i++) {
                      int aryLoc = Math.min(i*900, nmeLst.length-1) ;
                      String lookupVnm = nmeLst[aryLoc];
                      if(i == 1)
                      fromLoc = 0 ;
                      else
                      fromLoc = toLoc+1;
                      toLoc = Math.min(nmeidnLst.lastIndexOf(lookupVnm) + lookupVnm.length(), nmeidnLst.length());
                      String vnmSub = nmeidnLst.substring(fromLoc, toLoc);
                          System.out.println(sql+" in ("+vnmSub+")");

              outLst = db.execSqlLst("Sale Person", sql+" in ("+vnmSub+") order by b.nme , a.usr", params);
              pst = (PreparedStatement)outLst.get(0);
              rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
              HashMap prtyDtl = new HashMap();
              String nme=util.nvl(rs.getString("nme"));
              String usr=util.nvl(rs.getString("usr"));
              String usrid=util.nvl(rs.getString("usrid"));
              prtyDtl.put("nme", nme+"("+usr+")");
              prtyDtl.put("usrIdn",usrid);
              partydtl.add(prtyDtl);
              if(!usrIdnLst.contains(usrid))
              usrIdnLst.add(usrid);
              udf.setValue(usrid,util.nvl(rs.getString("stt")));
              }
            rs.close(); pst.close();
          }
          req.setAttribute("partydtl",partydtl);
          req.setAttribute("webroledscList",webroledscList);
          req.setAttribute("webroleDtl", webroleDtl);
          session.setAttribute("usrIdnLst", usrIdnLst);
          }
              util.updAccessLog(req,res,"Contact Bulk Upd", "viewbulkwebrole end");
          return am.findForward("view");
          }
      } 
    
    public ActionForward createtermsexcl(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Contact Terms Excel", "createtermsexcl start");
        HashMap dbinfo = info.getDmbsInfoLst();
        String zipallowyn = util.nvl((String)dbinfo.get("ZIP_DWD_YN"),"N"); 
        int zipdwnsz = Integer.parseInt(util.nvl((String)dbinfo.get("ZIP_DWD_SZ"),"100")); 
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
        ArrayList chkNmeIdnList = (info.getChkNmeIdnList() == null)?new ArrayList():(ArrayList)info.getChkNmeIdnList();
        ArrayList pktList = new ArrayList();
        ArrayList itemHdr = new ArrayList();
            
        int ct =db.execUpd(" Del Old Pkts ", "Delete from gt_pkt_scan", new ArrayList());
            
        if(chkNmeIdnList.size()>0){
           String vnm = chkNmeIdnList.toString();
           vnm= vnm.replace("["," ");
           vnm= vnm.replace("]"," ");
           vnm= vnm.replaceAll(" ","");
           System.out.println(vnm);  
            String[] vnmLst = vnm.split(",");
            int loopCnt = 1 ;
            float loops = ((float)vnmLst.length)/stepCnt;
            loopCnt = Math.round(loops);
               if(vnmLst.length <= stepCnt || new Float(loopCnt)>=loops) {
                
            } else
                loopCnt += 1 ;
            if(loopCnt==0)
                loopCnt += 1 ;
            int fromLoc = 0 ;
            int toLoc = 0 ;
            for(int i=1; i <= loopCnt; i++) {
                
              int aryLoc = Math.min(i*stepCnt, vnmLst.length) ;
              
              String lookupVnm = vnmLst[aryLoc-1];
                   if(i == 1)
                       fromLoc = 0 ;
                   else
                       fromLoc = toLoc+1;
                   
                   toLoc = Math.min(vnm.lastIndexOf(lookupVnm) + lookupVnm.length(), vnm.length());
                   String vnmSub = vnm.substring(fromLoc, toLoc);
                
            vnmSub = vnmSub.replaceAll("''", ",");
            vnmSub = vnmSub.replaceAll("'", "");
                if(!vnmSub.equals("")){
            vnmSub="'"+vnmSub+"'";
            ArrayList params = new ArrayList();
            //        params.add(vnmSub);
            String insScanPkt = " insert into gt_pkt_scan select * from TABLE(PARSE_TO_TBL("+vnmSub+"))";
            ct = db.execDirUpd(" ins scan", insScanPkt,params);
            System.out.println(insScanPkt);  
            }
            }
            String sqlQ="select a.nmerel_idn, a.nme_idn, byr.get_nm(nme_idn) byr, byr.get_nm(aadat_idn) aadat,byr.get_nm(mbrk1_idn) aadat2, byr.get_nm(mbrk2_idn) mb1,byr.get_nm(mbrk3_idn) mb2,byr.get_nm(mbrk4_idn) mb3  , \n" + 
            "aadat_comm,mbrk1_comm aadat_comm2, mbrk2_comm mb1_comm,mbrk3_comm mb2_comm, mbrk4_comm mb3_comm, cur, symbl, del_typ,rap_pct,\n" + 
            "byr.get_trms(a.nmerel_idn) trms, b.ins_grp_nme, c.brc_grp_nme  , a.brc_comm, a.ext_vlu,a.ext_pct, a.flg, a.dflt_yn,a.ttl_trm_pct\n" + 
            "from gt_pkt_scan gt,nmerel a, ins_grp b, brc_grp_aadat c  \n" + 
            "where gt.vnm=a.nme_idn and a.ins_grp_idn = b.ins_grp_idn and a.brc_grp_idn = c.brc_grp_idn  AND a.vld_dte IS NULL and a.dflt_yn='Y' order by 3";

            ArrayList outLst = db.execSqlLst("stockDtl", sqlQ, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()) {
            HashMap pktPrpMap = new HashMap();
            pktPrpMap.put("Buyer", util.nvl(rs.getString("byr")));
            pktPrpMap.put("Currency", util.nvl(rs.getString("cur")));
            pktPrpMap.put("Delivery Type", util.nvl(rs.getString("del_typ")));
            pktPrpMap.put("Day Terms", util.nvl(rs.getString("trms")));
            pktPrpMap.put("Extra $", util.nvl(rs.getString("ext_vlu")));
            pktPrpMap.put("Ext %", util.nvl(rs.getString("ext_pct")));
            pktPrpMap.put("Rap %", util.nvl(rs.getString("rap_pct")));
            pktPrpMap.put("Default Y/N", util.nvl(rs.getString("dflt_yn")));
            pktPrpMap.put("Aadat/Party", util.nvl(rs.getString("aadat")));
            pktPrpMap.put("Aadat/Party Comm", util.nvl(rs.getString("aadat_comm")));
            pktPrpMap.put("Aadat/Party 2", util.nvl(rs.getString("aadat2")));
            pktPrpMap.put("Aadat/Party 2 Comm", util.nvl(rs.getString("aadat_comm2")));
            pktPrpMap.put("Broker 1", util.nvl(rs.getString("mb1")));
            pktPrpMap.put("Broker 1 Comm", util.nvl(rs.getString("mb1_comm")));
            pktPrpMap.put("Broker 2", util.nvl(rs.getString("mb2")));
            pktPrpMap.put("Broker 2 Comm", util.nvl(rs.getString("mb2_comm")));
            pktPrpMap.put("Broker 3", util.nvl(rs.getString("mb3")));
            pktPrpMap.put("Broker 3 Comm", util.nvl(rs.getString("mb3_comm")));
            pktPrpMap.put("Total Trms Pct", util.nvl(rs.getString("ttl_trm_pct")));
            pktList.add(pktPrpMap);
            }
            rs.close();
            pst.close();
        }
            itemHdr.add("Buyer");
            itemHdr.add("Currency");
            itemHdr.add("Delivery Type");
            itemHdr.add("Day Terms");
            itemHdr.add("Extra $");
            itemHdr.add("Ext %");
            itemHdr.add("Rap %");
            itemHdr.add("Default Y/N");
            itemHdr.add("Aadat/Party");
            itemHdr.add("Aadat/Party Comm");
            itemHdr.add("Aadat/Party 2");
            itemHdr.add("Aadat/Party 2 Comm");
            itemHdr.add("Broker 1");
            itemHdr.add("Broker 1 Comm");
            itemHdr.add("Broker 2");
            itemHdr.add("Broker 2 Comm");
            itemHdr.add("Broker 3");
            itemHdr.add("Broker 3 Comm");
            itemHdr.add("Total Trms Pct");
            int pktListsz=pktList.size();
     
            HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
            if(zipallowyn.equals("Y") && pktListsz>zipdwnsz){
            String contentTypezip = "application/zip";
            String fileNmzip = "Consolidated_Terms_"+util.getToDteTime();
            OutputStream outstm = res.getOutputStream();
            ZipOutputStream zipOut = new ZipOutputStream(outstm);
            ZipEntry entry = new ZipEntry(fileNmzip+".xls");
            zipOut.putNextEntry(entry);
            res.setHeader("Content-Disposition","attachment; filename="+fileNmzip+".zip");
            res.setContentType(contentTypezip);
              ByteArrayOutputStream bos = new ByteArrayOutputStream();
               hwb.write(bos);
               bos.writeTo(zipOut);      
              zipOut.closeEntry();
               zipOut.flush();
               zipOut.close();
               outstm.flush();
               outstm.close(); 
            }else{
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "Consolidated_Terms_"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        hwb.write(out);
        out.flush();
        out.close();
        }
            util.updAccessLog(req,res,"Contact Terms Excel", "createtermsexcl end");
        return null;
        }
    }
    
    public ActionForward loadSaleExAlloc(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
              throws Exception {
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
          util.updAccessLog(req,res,"Sale Ex Bulk Upd", "loadSaleExAlloc start");
          AdvContactForm udf = (AdvContactForm)af;
          udf.reset();
          SearchQuery srchQuery = new SearchQuery();
          ArrayList empLst = srchQuery.getEmpList(req,res);
          udf.setEmpList(empLst);
          GenericInterface genericInt = new GenericImpl();
          ArrayList prpLst = (ArrayList)session.getAttribute("prpLst");     
          if (prpLst == null) {     
          genericInt.genericPrprVw(req,res,"EMP_MDL","EMP_MDL");       
          }
          util.updAccessLog(req,res,"Sale Ex Bulk Upd", "loadSaleExAlloc end");
          return am.findForward("loadSaleExAlloc");
          }
      }
    
    public ActionForward saveSaleExAlloc(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
              throws Exception {
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
          util.updAccessLog(req,res,"Sale Ex Bulk Upd", "saveSaleExAlloc start");
          AdvContactForm udf = (AdvContactForm)af;
          ArrayList ary = new ArrayList();
          GenericInterface genericInt = new GenericImpl();
          ArrayList prpLst = genericInt.genericPrprVw(req,res,"EMP_MDL","EMP_MDL");       
          ArrayList chkNmeIdnList = (info.getChkNmeIdnList() == null)?new ArrayList():(ArrayList)info.getChkNmeIdnList();
          int ct = 0;    
          if(chkNmeIdnList.size()>0){    
              String empIdn = util.nvl((String)udf.getValue("empIdn"),"0").trim(); 
              if(!empIdn.equals("0")){
              for(int i=0;i<chkNmeIdnList.size();i++){    
              String nmeidnLst = util.nvl((String)chkNmeIdnList.get(i));    
              String insIntoQ = " insert into Nme_Emp_Rel ( idn , nme_idn , emp_idn   ";
              String insValQ  = " select Nme_Emp_Rel_Seq.nextval, ? , ? ";
              for(int k=0;k<prpLst.size();k++){  
              String lTblFld = util.nvl((String)prpLst.get(k));    
              String lTblFldVal = util.nvl((String)udf.getValue(lTblFld));
              insIntoQ += " , prp"+(k+1)+",prp"+(k+1)+"_val";    
              insValQ += " , '"+lTblFld+"','"+lTblFldVal+"'";  
              }    
              ary = new ArrayList();
              ary.add(nmeidnLst);
              ary.add(empIdn);
              String sql = insIntoQ + ") " + insValQ + " from dual ";
              ct = db.execUpd("Insert Sale Ex Allocation", sql, ary); 
              }
              }
              }  
              
              if(ct>0)
              req.setAttribute("msg", "Data saved Successfully");
              util.updAccessLog(req,res,"Sale Ex Bulk Upd", "saveSaleExAlloc start");
              return loadSaleExAlloc(am,af,req,res);
          }
      }
    
    public ActionForward save(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
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
        AdvContactForm udf = (AdvContactForm)af;
        String stt = req.getParameter("stt");
        String usr_idn = req.getParameter("usrIdn");
        String roleIdn = req.getParameter("roleIdn");
        ArrayList    params  = new ArrayList();
        String sql="";
        sql = "update usr_role a set a.stt='IA' " + 
        "where a.role_idn in (select c.role_idn from mrole b , mrole c where b.typ = c.typ  and b.role_idn=?  and b.typ is not null ) " + 
        "and a.usr_idn = ? ";
      
        params.add(roleIdn);
        params.add(usr_idn);
        int up = db.execUpd("update", sql, params);
       
        if(stt.equals("true"))
        stt="A";
        else
          stt="IA";
      
      sql="update usr_role set usr_idn=?,role_idn=?,stt=? where usr_idn=? and role_idn=?"; 
       params  = new ArrayList();
      params.add(usr_idn);
      params.add(roleIdn);
      params.add(stt);
      params.add(usr_idn);
      params.add(roleIdn);
      int ct = db.execUpd(" update usr_role ", sql, params);
      if(ct<1){
        params.clear();
        params.add(usr_idn);
        params.add(roleIdn);
        params.add(stt);
        sql="insert into usr_role(ur_idn,usr_idn,role_idn,stt) values(seq_usr_role_idn.nextval,?,?,?)"; 
        ct = db.execUpd(" insert usr_role ", sql, params);
      }
      
        
        return null;
        }
    }
    
    public ActionForward checkAllBulk(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
              throws Exception {
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
          AdvContactForm udf = (AdvContactForm)af;
        ArrayList usrIdnLst = (ArrayList)session.getAttribute("usrIdnLst");
        String roleName=req.getParameter("roleName");
        String stt=req.getParameter("stt");

        ArrayList params   = new ArrayList();
        String sql="";
        String role_idn="";
        int usrIdnLstsz=usrIdnLst.size();
        
        sql="select  role_idn , nvl(role_dsc,role_nm) role_dsc from mrole where stt='A' and role_dsc=? and to_dte is null order by role_idn";
        params.add(roleName);

              ArrayList outLst = db.execSqlLst("Role Idn", sql, params);
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                role_idn=String.valueOf(rs.getInt("role_idn"));
              }
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }

        for(int i=0;i<usrIdnLstsz;i++){
        String usrid=(String)usrIdnLst.get(i);
        sql="update usr_role set usr_idn=?,role_idn=?,stt=? where usr_idn=? and role_idn=?"; 
        params   = new ArrayList();
        params.add(usrid);
        params.add(role_idn);
        params.add(stt);
        params.add(usrid);
        params.add(role_idn);
        int ct = db.execUpd(" update usr_role ", sql, params);
             
        if(ct<1){
          params   = new ArrayList();
          params.add(usrid);
          params.add(role_idn);
          params.add(stt);
          sql="insert into usr_role(ur_idn,usr_idn,role_idn,stt) values(seq_usr_role_idn.nextval,?,?,?)"; 
          ct = db.execUpd(" insert usr_role ", sql, params);
         }
         }
        
          return null;
          }
      } 
    
    public ActionForward checkWebusers(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
              throws Exception {
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
          AdvContactForm udf = (AdvContactForm)af;
        ArrayList usrIdnLst = (ArrayList)session.getAttribute("usrIdnLst");
        String stt=util.nvl(req.getParameter("stt"));
        String usrIdn=util.nvl(req.getParameter("usrIdn"));
        ArrayList params   = new ArrayList();
        ArrayList activityonusr=new ArrayList();
        String sql="update web_usrs ";
        if(stt.equals("true"))
        sql+=" set to_dt=null where usr_id=? ";
        else
        sql+=" set to_dt=sysdate where usr_id=? ";
        if(usrIdn.equals(""))
            activityonusr=usrIdnLst ;
        else
            activityonusr.add(usrIdn);
        int activityonusrsz=activityonusr.size();
        for(int i=0;i<activityonusrsz;i++){
        String usrid=(String)activityonusr.get(i);
        params   = new ArrayList();
        params.add(usrid);
        int ct = db.execUpd(" update usr_role ", sql, params);
        params   = new ArrayList();
        params.add(usrid);
        ct = db.execUpd("update", "delete usr_role where stt='IA' and usr_idn=? ",params);
        util.updAccessLog(req,res,"Bulk update Web users", usrid);
        }
        
          return null;
          }
      } 
    
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
        util.updAccessLog(req,res,"Sale Ex Allocation", "load start");
        AdvContactForm udf = (AdvContactForm)af;
        udf.reset();
        SearchQuery srchQuery = new SearchQuery();
        String buyerId = util.nvl(req.getParameter("nmeIdn"));     
        ArrayList empLst = srchQuery.getEmpList(req,res);
        udf.setEmpList(empLst);
        GenericInterface genericInt = new GenericImpl();
        if(buyerId.equals(""))
            buyerId = util.nvl((String)req.getAttribute("nmeIdn"));  
        ArrayList empdepprpLst = (ArrayList)session.getAttribute("empdepprpLst");     
        if (empdepprpLst == null) {     
        empdepprpLst = genericInt.genericPrprVw(req,res,"EMP_MDL","EMP_MDL");     
        session.setAttribute("empdepprpLst",empdepprpLst);     
        }
        udf.setValue("nmeIdn", buyerId);
        
        String sql="Select Decode(Vld_To,'','','red') Color, Ev.Nme,To_Char(Nr.Vld_Frm, 'DD-MON-YYYY') Vld_Frm,To_Char(Nr.Vld_To, 'DD-MON-YYYY') Vld_To,nr.idn,nr.nme_idn ";
        for (int p = 0; p < empdepprpLst.size(); p++) {
                 String fld = "prp";
                 String fldval = "prp";
                 int j = p + 1;
                 fld += ""+j;
                 fldval += ""+j+"_val";
                 sql += ", " + fld+", " + fldval;
         }
        String rsltQ = sql + " From Nme_Emp_Rel Nr, Emp_V Ev Where Nr.Emp_Idn=Ev.Nme_Idn and Nr.Nme_Idn = ? Order By 1 desc,Ev.Nme,3 desc ";
        ArrayList dataList = new ArrayList();
        ArrayList params = new ArrayList();
        params.add(buyerId);

             ArrayList outLst = db.execSqlLst("Sale Ex rsltQ", rsltQ, params);
             PreparedStatement pst = (PreparedStatement)outLst.get(0);
             ResultSet rs = (ResultSet)outLst.get(1);
        try {
        while (rs.next()) {
        HashMap saleExMap = new HashMap();
        saleExMap.put("EMP", util.nvl((String)rs.getString("Nme")));
        saleExMap.put("Vld_Frm", util.nvl((String)rs.getString("Vld_Frm")));
        saleExMap.put("Vld_To", util.nvl((String)rs.getString("Vld_To")));
        saleExMap.put("Color", util.nvl((String)rs.getString("Color")));
        saleExMap.put("IDN", util.nvl((String)rs.getString("idn")));
        saleExMap.put("NMEIDN", util.nvl((String)rs.getString("nme_idn")));
        for (int q = 0; q < empdepprpLst.size(); q++) {    
        String prp = (String)empdepprpLst.get(q);
        String fld = "prp";
        String fldval = "prp";
        fld = "prp" + (q + 1);    
        fldval = "prp" + (q + 1)+"_val";
        String prpdsc = util.nvl(rs.getString(fld));    
        String prpval = util.nvl(rs.getString(fldval));    
        saleExMap.put(prpdsc, prpval);    
        }    
        dataList.add(saleExMap);    
        }
        rs.close(); pst.close();
        } catch (SQLException sqle) {
        // TODO: Add catch code
        sqle.printStackTrace();
        }
             
             
        req.setAttribute("dataList",dataList);     
        util.updAccessLog(req,res,"Sale Ex Allocation", "load end");
        return am.findForward("loadSaleEx");
         }
     }
    
    public ActionForward saveSaleEx(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
         util.updAccessLog(req,res,"Sale Ex Allocation", "save start");
         ArrayList empdepprpLst = (ArrayList)session.getAttribute("empdepprpLst");
         AdvContactForm udf = (AdvContactForm)af;
         ArrayList ary = new ArrayList();
         String row = util.nvl((String)req.getParameter("ROWCNT"));    
         String buyerId = util.nvl((String)udf.getValue("nmeIdn"));
         int rowCnt = Integer.parseInt(row);
         int ct = 0;    
         if(!buyerId.equals("")){    
         for(int p=1;p<=rowCnt;p++){    
         String empIdn = util.nvl((String)udf.getValue("empIdn_"+p)).trim();
         if(!empIdn.equals("0")){
         String insIntoQ = " insert into Nme_Emp_Rel ( idn , nme_idn , emp_idn   ";
         String insValQ  = " select Nme_Emp_Rel_Seq.nextval, ? , ? ";
         for(int k=0;k<empdepprpLst.size();k++){
         int q = k+1;    
         String lTblFld = util.nvl((String)empdepprpLst.get(k));    
         String lTblFldVal = util.nvl((String)udf.getValue(lTblFld+"_"+p));
         insIntoQ += " , prp"+q+",prp"+q+"_val";    
         insValQ += " , '"+lTblFld+"','"+lTblFldVal+"'";  
         }    
         ary = new ArrayList();
         ary.add(buyerId);
         ary.add(empIdn);
         String sql = insIntoQ + ") " + insValQ + " from dual ";
         ct = db.execUpd("Insert Emp Dep", sql, ary); 
         System.out.println("Insert Emp Dep "+p);
         }
         }
         }    
         if(ct>0)
         req.setAttribute("msg", "Records added Successfully");
         req.setAttribute("nmeIdn", buyerId);    
         util.updAccessLog(req,res,"Sale Ex Allocation", "save end");
         return load(am,af,req,res);
         }
     }
    
    public ActionForward delete(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
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
            util.updAccessLog(req,res,"Sale Ex Allocation", "delete start");
            AdvContactForm udf = (AdvContactForm)af;

        String    idn  = util.nvl(req.getParameter("idn"));
        String    nme_idn  = util.nvl(req.getParameter("nme_idn"));
        String delQ = " update Nme_Emp_Rel set stt='IA',VLD_TO=sysdate where idn = ? ";
        ArrayList ary  = new ArrayList();

        ary.add(idn);

        int ct = db.execUpd(" del msg", delQ, ary);
            req.setAttribute("nmeIdn", nme_idn);  
            util.updAccessLog(req,res,"Sale Ex Allocation", "delete end");
        return load(am, af, req, res);
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
                rtnPg=util.checkUserPageRights("contact/advcontact.do?","");
                if(rtnPg.equals("unauthorized"))
                util.updAccessLog(req,res,"Contact Bulk Upd", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Contact Bulk Upd", "init");
            }
            }
            return rtnPg;
         }
}
