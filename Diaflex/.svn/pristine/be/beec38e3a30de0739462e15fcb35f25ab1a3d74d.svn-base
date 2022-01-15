package ft.com.fileupload;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;
import ft.com.assort.AssortIssueForm;
import ft.com.dao.Mprc;

import ft.com.generic.GenericImpl;

import ft.com.generic.GenericInterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import java.io.OutputStream;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

public class MfgReciveAction extends DispatchAction {

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
        util.updAccessLog(req,res,"Mfg Recive", "Mfg Recive load start");
        HashMap dbinfo = info.getDmbsInfoLst();
        String mfgdb = (String)dbinfo.get("MFGDB");
        ArrayList sheetList = new ArrayList();
        String mfgQ = "Select Count(Distinct pckt_id) Cnt, to_char(trunc(send_dt),'dd-mm-yyyy') dte, To_Char(Sht_Nmbr) Sht From "+mfgdb+".Mfg_Dspsht_V where pckt_stts='IS' \n" + 
        "group by to_char(SHT_NMBR), to_char(trunc(send_dt),'dd-mm-yyyy') order by to_char(trunc(send_dt),'dd-mm-yyyy'), to_char(sht_nmbr)" ;

            ArrayList outLst = db.execSqlLst("mfgQ", mfgQ, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("QTY", util.nvl(rs.getString("Cnt")));
                pktPrpMap.put("SDTE", util.nvl(rs.getString("dte")));
                pktPrpMap.put("SHEET", util.nvl(rs.getString("Sht")));
                sheetList.add(pktPrpMap);
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        session.setAttribute("StockList", sheetList);
            util.updAccessLog(req,res,"Mfg Recive", "Mfg Recive load end");
        return am.findForward("load");
        }
    }
    public ActionForward loadPKT(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Mfg Recive", "Mfg Recive loadPKT start");
        FileUploadForm uploadForm = (FileUploadForm)form;
        GenericInterface genericInt = new GenericImpl();
        String sheet=util.nvl((String)req.getParameter("sheet"));
        if(sheet.equals(""))
        sheet=util.nvl((String)uploadForm.getValue("sheet"));
        HashMap dbinfo = info.getDmbsInfoLst();
        String mfgdb = (String)dbinfo.get("MFGDB");
        ArrayList pktList = new ArrayList();
        ArrayList ary =new ArrayList();
        ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "mfgrecViewLst", "MFGREC_PKT");
        int vwPrpLstsz=vwPrpLst.size();
        String conQ="";
        for(int i=0;i<vwPrpLstsz;i++){
            String prp=(String)vwPrpLst.get(i);
            conQ=conQ+", max(decode(a.prc_mprp, '"+prp+"', b.attr_vlu, null)) "+prp;
        }

        String mfgQ = "select fctr_id, pckt_id, sht_nmbr\n" + conQ+
                      
//        " , max(decode(a.prc_mprp, 'SHAPE', b.attr_vlu, null)) shape\n" + 
//        " , max(decode(a.prc_mprp, 'CRTWT', b.attr_vlu, null)) crtwt\n" + 
//        " , max(decode(a.prc_mprp, 'COL', b.attr_vlu, null)) col\n" + 
//        " , max(decode(a.prc_mprp, 'CLR', b.attr_vlu, null)) clr\n" + 
//        " , max(decode(a.prc_mprp, 'CUT', b.attr_vlu, null)) cut\n" + 
        " from "+mfgdb+".MFG_ATTRBT_M a, "+mfgdb+".mfg_dspsht_v b\n" + 
        " where a.attr_typ = b.attr_cd  and pckt_stts='IS' \n" + 
        " and b.sht_nmbr = ?\n" + 
        " group by fctr_id, pckt_id, sht_nmbr\n" + 
        " Order By Sht_Nmbr, pckt_id" ;
        ary.add(sheet);

            ArrayList outLst = db.execSqlLst("mfgQ", mfgQ, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("fctr_id", util.nvl(rs.getString("fctr_id")));
                pktPrpMap.put("pckt_id", util.nvl(rs.getString("pckt_id")));
                pktPrpMap.put("sht_nmbr", util.nvl(rs.getString("sht_nmbr")));
                for(int j=0; j < vwPrpLstsz; j++){
                    String prp=(String)vwPrpLst.get(j);  
                    pktPrpMap.put(prp, util.nvl(rs.getString(prp)));
                }
//                pktPrpMap.put("shape", util.nvl(rs.getString("shape")));
//                pktPrpMap.put("crtwt", util.nvl(rs.getString("crtwt")));
//                pktPrpMap.put("col", util.nvl(rs.getString("col")));
//                pktPrpMap.put("clr", util.nvl(rs.getString("clr")));
//                pktPrpMap.put("cut", util.nvl(rs.getString("cut")));
                pktList.add(pktPrpMap);
            }
            rs.close(); pst.close();
        session.setAttribute("pktList", pktList);
        req.setAttribute("sheetno", sheet);
        uploadForm.setValue("sheet", sheet);
            util.updAccessLog(req,res,"Mfg Recive", "Mfg Recive loadPKT end");
      return am.findForward("loadPKT");
        }
    }
    public ActionForward saveSheetpkt(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Mfg Recive", "Mfg Recive saveSheetpkt start");
        FileUploadForm uploadForm = (FileUploadForm)form;
        ArrayList pktList = (ArrayList)session.getAttribute("pktList");
        String sheet=util.nvl((String)uploadForm.getValue("sheet"));
        ArrayList params = null;
        ArrayList RtnMsgList = new ArrayList();
        String msg = "";
        int cnt = 0;
        ArrayList recivePkt = new ArrayList();
        for(int i=0;i< pktList.size();i++){
            HashMap pktPrpMap = (HashMap)pktList.get(i);
             String sheetno = (String)pktPrpMap.get("sht_nmbr");
             String pckt_id = (String)pktPrpMap.get("pckt_id");
             String isChecked = util.nvl((String)uploadForm.getValue(pckt_id));
            if(isChecked.equals("yes")){
                params = new ArrayList();
                params.add(sheetno);
                params.add(pckt_id);
                recivePkt.add(pckt_id);
                ArrayList RtnMsg = new ArrayList();
                ArrayList out = new ArrayList();
                out.add("I");
                out.add("V");
                CallableStatement cst = null;
                cst = db.execCall(
                    "MFG_PKG ",
                    "MFG_PKG.TRF_SHT_PKT(pSht => ?,pPktId => ?, pCnt => ?, pMsg => ?)", params, out);
                cnt = cst.getInt(3);
                msg = cst.getString(4);
                RtnMsg.add(cnt);
                RtnMsg.add(msg);
                RtnMsgList.add(RtnMsg);
              cst.close();
              cst=null;
                
            }
       }
        uploadForm.reset();
        uploadForm.setValue("sheet", sheet);
        if(recivePkt.size()>0)
            req.setAttribute("msgList",RtnMsgList);
            util.updAccessLog(req,res,"Mfg Recive", "Mfg Recive saveSheetpkt end");
     return loadPKT(am,form,req,res);
        }
    }
    public ActionForward saveSheet(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Mfg Recive", "Mfg Recive saveSheet start");
        FileUploadForm uploadForm = (FileUploadForm)form;
        ArrayList stockList = (ArrayList)session.getAttribute("StockList");
        ArrayList params = null;
        ArrayList RtnMsgList = new ArrayList();
        String msg = "";
        int cnt = 0;
        ArrayList reciveSheet = new ArrayList();
        for(int i=0;i< stockList.size();i++){
            HashMap stockPkt = (HashMap)stockList.get(i);
             String sheetno = (String)stockPkt.get("SHEET");
             String isChecked = util.nvl((String)uploadForm.getValue(sheetno));
            if(isChecked.equals("yes")){
                params = new ArrayList();
                params.add(sheetno);
                ArrayList RtnMsg = new ArrayList();
                ArrayList out = new ArrayList();
                out.add("I");
                out.add("V");
                CallableStatement cst = null;
                cst = db.execCall(
                    "MFG_PKG ",
                    "MFG_PKG.TRF_SHT(pSht => ?, pCnt => ?, pMsg => ?)", params, out);
                cnt = cst.getInt(2);
                msg = cst.getString(3);
                RtnMsg.add(cnt);
                RtnMsg.add(msg);
                RtnMsgList.add(RtnMsg);
              cst.close();
              cst=null;          
            }
       }
        uploadForm.reset();
            if(reciveSheet.size()>0)
                req.setAttribute("msgList",RtnMsgList);
            util.updAccessLog(req,res,"Mfg Recive", "Mfg Recive saveSheet end");
     return load(am,form,req,res);
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
                util.updAccessLog(req,res,"Mfg Recive", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Mfg Recive", "init");
            }
            }
            return rtnPg;
            }
    public MfgReciveAction() {
        super();
//        this.fileUploadInt  = fileUploadInt;
}


   
   
}

