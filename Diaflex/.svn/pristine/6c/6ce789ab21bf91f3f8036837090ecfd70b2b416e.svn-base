 package ft.com.fileupload;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.GenMail;
import ft.com.HtmlMailUtil;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.assort.AssortFinalRtnForm;
import ft.com.assort.AssortIssueInterface;
import ft.com.dao.FileUploadDao;

import ft.com.dao.JsonDao;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.lab.LabResultForm;

import java.io.FileOutputStream;
import java.io.IOException;

import java.net.InetSocketAddress;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.spy.memcached.MemcachedClient;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.json.JSONException;
import org.json.JSONObject;

public class FileUploadImpl implements FileUploadInterface  {
    public FileUploadImpl() {
        super();
    }
    public ArrayList fileUploadTyp(HttpServletRequest req , HttpServletResponse res,FileUploadForm form){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        FileUploadForm udf = (FileUploadForm)form;
        String type=util.nvl(req.getParameter("typ"));
        if(type.equals(""))
            type=util.nvl((String)udf.getValue("filetyp"));  
        String conQ="";
        if(!type.equals("") && !type.equals("ALL"))
            conQ= " and file_typ='"+type+"' ";
        ArrayList fileUploadList = new ArrayList();

        ArrayList outLst = db.execSqlLst("fileuploadTyp", "select file_typ ,file_ext, dsc from file_load_typ where stt='A' "+conQ+" order by srt", new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                 FileUploadDao fileDao = new FileUploadDao();
                fileDao.setFileTyp(rs.getString("file_typ"));
                fileDao.setFileExt(rs.getString("file_ext"));
                fileDao.setFileDsc(rs.getString("dsc"));
                fileUploadList.add(fileDao);
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        udf.setValue("filetyp", type);
        req.setAttribute("fileUploadList", fileUploadList);
        return fileUploadList;
    }
    
    
    public ArrayList fileUploadTypList(HttpServletRequest req , HttpServletResponse res,FileUploadForm form){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        FileUploadForm udf = (FileUploadForm)form;
        String type=util.nvl(req.getParameter("typ"));
        if(type.equals(""))
            type=util.nvl((String)udf.getValue("filetyp"));  
        String conQ="";
        if(!type.equals("") && !type.equals("ALL"))
            conQ= " and file_typ='"+type+"' ";
        ArrayList fileUploadList = new ArrayList();

        ArrayList outLst = db.execSqlLst("fileuploadTyp", "select file_typ ,file_ext, dsc , date_fmt from file_load_typ where stt='A' "+conQ+" order by srt", new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                 FileUploadDao fileDao = new FileUploadDao();
                fileDao.setFileTyp(rs.getString("file_typ"));
                fileDao.setFileExt(rs.getString("file_ext"));
                fileDao.setFileDsc(rs.getString("dsc"));
                fileDao.setDate_fmt(rs.getString("date_fmt"));
                fileUploadList.add(fileDao);
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        udf.setValue("filetyp", type);
        session.setAttribute("fileUploadList", fileUploadList);
        return fileUploadList;
    }
    
    public int addFileData(HttpServletRequest req, HttpServletResponse res, HashMap paramsList) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      
        int ct =0;
      
       
           
            ArrayList params = new ArrayList();
            params.add(paramsList.get("fileIdn"));
            params.add(paramsList.get("typ"));
            params.add(paramsList.get("rctdte"));
            params.add(paramsList.get("disdte"));
            params.add(paramsList.get("rctLoc"));
        
            String mloadInsert =
                "insert into mload_file(load_file_idn , typ , dte , recpt_dte , dsp_dte , dsp_loc ) values( ? , ? , sysdate , to_date(? , 'dd-mm-rrrr') , to_date(? , 'dd-mm-rrrr') , ?)";
            ct = db.execUpd("mload", mloadInsert, params);
            if (ct > 0) {
                ArrayList lineData = (ArrayList)paramsList.get("dataList");
                if (lineData != null || lineData.size() > 0) {
                    for (int i = 0; i < lineData.size(); i++) {
                        String line = (String)lineData.get(i);
                        int lineNo = i+1;
                        String loadDtlInsert =
                            "insert into load_file_dtl(load_file_idn , ln_no , dtl , dte ) values( ? , ? , ?, sysdate)";
                        params = new ArrayList();
                        params.add(paramsList.get("fileIdn"));
                        params.add(String.valueOf(lineNo));
                        params.add(String.valueOf(line));
                        ct = db.execUpd("load dtl", loadDtlInsert, params);
                    }
                }
            }

        
        
        return ct;
    }
    
    public String fileTRFTOCSV(HttpServletRequest req ,HttpServletResponse res, HashMap params){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        String msg = "";
        int ct = 0;
        String fileIdn = (String)params.get("fileIdn");
        String fileTyp = (String)params.get("typ");
        String dte = util.nvl((String)params.get("dte"));
        try {
            ArrayList ary = new ArrayList();
            ary.add(String.valueOf(fileIdn));
            ArrayList out = new ArrayList();
            out.add("V");
            
            CallableStatement cst = null;
            String sql="FILE_LOAD_PKG.TRF_TO_CSV(pIdn => ?, pMsg => ?)";
            if(!dte.equals("")){
                 sql="FILE_LOAD_PKG.TRF_TO_CSV(pIdn => ?, pDte=>? , pMsg => ?)";
                 ary.add(dte);
            }
             cst = db.execCall("MKE_HDR ", sql, ary, out);
             msg = cst.getString(ary.size()+1);
             cst.close();
             cst=null;
           } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
         }
        
        return msg;
        
    }
    
    public String fileReportName(HttpServletRequest req ,HttpServletResponse res, String fileTyp){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        String reportNme = "";
       ArrayList ary = new ArrayList();
       ary.add(fileTyp);
           

        ArrayList outLst = db.execSqlLst("fileuploadTyp", "select err_rep from file_load_typ where file_typ=?", ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            if (rs.next()) {
                 reportNme = rs.getString(1);
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        return reportNme;
    }
    
    public ArrayList FileUploadStt(HttpServletRequest req , HttpServletResponse res, String seqNo ){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList fileDataList = new ArrayList();
        ArrayList ary = new ArrayList();
       
        String fileStt = " select stg, min(dte) minDte, max(dte) maxDte, to_char(trunc((max(dte) - min(dte))*24*60,2), '990.00') min "+
                        " from file_upload_log where file_seq = ? "+
                        " group by stg order by 2 ";
      
        ary.add(seqNo);

        ArrayList outLst = db.execSqlLst("fileStt", fileStt, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
            HashMap pktDtl = new HashMap();
            pktDtl.put("stg", util.nvl(rs.getString("stg")));
            pktDtl.put("minDte", util.nvl(rs.getString("minDte")));
            pktDtl.put("maxDte", util.nvl(rs.getString("maxDte")));
            pktDtl.put("min", util.nvl(rs.getString("min")));
            fileDataList.add(pktDtl);
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        return fileDataList;
    }
    
    public ArrayList FileUploadSttTm(HttpServletRequest req , HttpServletResponse res, HashMap params ){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList fileDataList = new ArrayList();
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = util.nvl((String)dbinfo.get("CNT"));
        ArrayList ary = new ArrayList();
        String seqNo = (String)params.get("seqNo");
        String typ = util.nvl((String)params.get("typ"));
        String fileStt = "select count(*) cnt, decode(flg,'P','Completed','N','Pending','E','Error','Invaild Stones') stt , flg from file_upload_ora where file_seq = ? group by flg ";
        if(cnt.equalsIgnoreCase("hk") && !typ.equals("MKT"))
           fileStt = " select count(*) cnt, decode(flg,'P','Completed','N','Pending','E','Error','Invaild Stones') stt , flg from mfg_upload_ora where file_seq =? group by flg ";
        if(typ.equals("LAB"))
            fileStt = "select count(*) cnt, decode(flg,'P','Completed','N','Pending','E','Error','Invaild Stones') stt ,  flg  from lab_inward_ora where load_seq = ? group by flg ";
        ary.add(seqNo);

        ArrayList outLst = db.execSqlLst("fileStt", fileStt, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
            ArrayList pktDtl = new ArrayList();
            pktDtl.add(util.nvl(rs.getString("stt")));
            pktDtl.add(util.nvl(rs.getString("cnt")));
            pktDtl.add(util.nvl(rs.getString("flg")));
            fileDataList.add(pktDtl);
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        return fileDataList;
    }
    public ArrayList FileUploadErrorDtl(HttpServletRequest req , HttpServletResponse res, HashMap params ){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList fileDataList = new ArrayList();
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = util.nvl((String)dbinfo.get("CNT"));
        ArrayList ary = new ArrayList();
        String seqNo = (String)params.get("seqNo");
        String typ = util.nvl((String)params.get("typ"));
        String flg = util.nvl((String)params.get("flg"));
        String fileStt = "select vnm , rem from file_upload_ora where file_seq = ? and flg=?  ";
        if(cnt.equalsIgnoreCase("hk") && !typ.equals("MKT"))
            fileStt = "select vnm , rmk rem from mfg_upload_ora where file_seq = ? and flg=?  ";
        if(typ.equals("LAB"))
            fileStt = "select vnm , rem  from lab_inward_ora where load_seq = ? and flg=?  ";
        ary.add(seqNo);
        ary.add(flg);

        ArrayList outLst = db.execSqlLst("fileStt", fileStt, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
            ArrayList pktDtl = new ArrayList();
            pktDtl.add(util.nvl(rs.getString("vnm")));
            pktDtl.add(util.nvl(rs.getString("rem")));
            
            fileDataList.add(pktDtl);
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        return fileDataList; 
    }

    public ArrayList FileUploadErr(HttpServletRequest req , HttpServletResponse res, String seqNo ){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList fileDataList = new ArrayList();
        ArrayList ary = new ArrayList();
       
        String fileStt = " select vnm, rem from lab_inward_ora where load_seq = ? and flg in ('E','I') "+
                        " UNION "+
                        " select vnm, rem from file_upload_ora where file_seq = ?  and flg in ('E','I') ";

      
        ary.add(seqNo);
        ary.add(seqNo);

        ArrayList outLst = db.execSqlLst("fileStt", fileStt, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
            HashMap pktDtl = new HashMap();
            pktDtl.put("vnm", util.nvl(rs.getString("vnm")));
            pktDtl.put("rem", util.nvl(rs.getString("rem")));
           
            fileDataList.add(pktDtl);
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        return fileDataList;
    }
    
    public ArrayList SearchResult(HttpServletRequest req , HttpServletResponse res, String mdl ){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        int count = 0;
        ArrayList pktList = new ArrayList();
        ArrayList vwPrpLst = LabPrprViw(req ,res , mdl);
        String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt , qty , cts , quot , to_char(trunc(cts,2)*prte ,'999990.99') val  ";
        HashMap srchReckObsMap = new HashMap();
        

        for (int i = 0; i < vwPrpLst.size(); i++) {
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

        
        String rsltQ = srchQ + "  from gt_srch_rslt where flg =? order by sk1";
        
        ArrayList ary = new ArrayList();
        ary.add("Z");

        ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while(rs.next()) {
                count++;
                String stkIdn = util.nvl(rs.getString("stk_idn"));
                String stt = util.nvl(rs.getString("stt"));
            
               
                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("stt", stt);
                pktPrpMap.put("sr", String.valueOf(count));
                String vnm = util.nvl(rs.getString("vnm"));
                pktPrpMap.put("vnm",vnm);
                pktPrpMap.put("stk_idn", stkIdn);
                pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
                pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
                pktPrpMap.put("quot",util.nvl(rs.getString("quot")));
                pktPrpMap.put("val",util.nvl(rs.getString("val")));
                srchReckObsMap.put(stkIdn, "");
                for(int j=0; j < vwPrpLst.size(); j++){
                     String prp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                      
                      String val = util.nvl(rs.getString(fld)) ;
                     
                        
                        pktPrpMap.put(prp, val);
                         }
                              
                    pktList.add(pktPrpMap);
                }
        
            rs.close(); pst.close();
        
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
      
        return pktList;
    }
//    public ArrayList MftGenricSrch(HttpServletRequest req , HttpServletResponse res){
//        init(req,res);
//        ArrayList asViewPrp = (ArrayList)session.getAttribute("MftGNCSrch");
//        try {
//            if (asViewPrp == null) {
//
//                asViewPrp = new ArrayList();
//                ResultSet rs1 =
//                    db.execSql(" Vw Lst ", "Select prp , flg  from rep_prp where mdl = 'MFT_SRCH' and flg in ('Y','S') order by rnk ",
//                               new ArrayList());
//                while (rs1.next()) {
//                    ArrayList asViewdtl=new ArrayList();
//                    asViewdtl.add(rs1.getString("prp"));
//                    asViewdtl.add(rs1.getString("flg"));
//                    asViewPrp.add(asViewdtl);
//                }
//                rs1.close();
//                session.setAttribute("MftGNCSrch", asViewPrp);
//            }
//
//        } catch (SQLException sqle) {
//            // TODO: Add catch code
//            sqle.printStackTrace();
//        }
//        
//        return asViewPrp;
//    }
    
    public ArrayList LabPrprViw(HttpServletRequest req , HttpServletResponse res , String mdl){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList viewPrp = null;
        ArrayList ary = new ArrayList();
        try {
            if (viewPrp == null) {
                ary = new ArrayList();
                ary.add(mdl);
                viewPrp = new ArrayList();
                ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = ? and flg='Y' order by rnk ", ary);
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs1 = (ResultSet)outLst.get(1);
                while (rs1.next()) {

                    viewPrp.add(rs1.getString("prp"));
                }
                rs1.close();pst.close();
                session.setAttribute("MftViewLst", viewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return viewPrp;
    }
    
    public HashMap GetTotal(HttpServletRequest req , HttpServletResponse res , String flg){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HashMap gtTotalMap = new HashMap();
        String gtTotal ="Select count(*) qty, sum(trunc(cts,2)) cts , to_char(sum((trunc(cts,2) * nvl(prte,1))),'9999999990.99') vlu  from gt_srch_rslt where flg = ?";
        ArrayList ary = new ArrayList();
        ary.add(flg);

        ArrayList outLst = db.execSqlLst("getTotal", gtTotal, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            if (rs.next()) {
             gtTotalMap.put("qty", util.nvl(rs.getString("qty")));
             gtTotalMap.put("cts", util.nvl(rs.getString("cts")));
             gtTotalMap.put("vlu", util.nvl(rs.getString("vlu")));
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return gtTotalMap ;
    }
    public ArrayList GetTotalByRCNo(HttpServletRequest req , HttpServletResponse res ){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        int rcptRnk = 0;
        String recptRnk = "select rnk from rep_prp where mdl='SUR_RT' and prp='RECPT_NO'";


        try {
            ArrayList outLst = db.execSqlLst("groupTotal",recptRnk, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs1 = (ResultSet)outLst.get(1);
            if (rs1.next()) {
              rcptRnk = rs1.getInt(1);
            }
            rs1.close();pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        ArrayList rcptDtlList = new ArrayList();
        String prpRcptRnk = "prp_00"+rcptRnk;
        HashMap gtTotalMap = new HashMap();
        String gtTotal ="Select count(*) qty, sum(trunc(cts,2)) cts  , "+prpRcptRnk+" rcptNo " + 
        " from gt_srch_rslt where flg='Z' and stt='MF_FL' group by "+prpRcptRnk+" ";
        ArrayList ary = new ArrayList();
       

        ArrayList outLst = db.execSqlLst("getTotal", gtTotal, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
              HashMap rcptList = new HashMap();
              rcptList.put("qty",util.nvl(rs.getString("qty")));
              rcptList.put("cts",util.nvl(rs.getString("cts")));
              rcptList.put("rcptNo",util.nvl(rs.getString("rcptNo")));
              rcptDtlList.add(rcptList);
            }
            rs.close(); pst.close();
            
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return rcptDtlList;
    }
    public void sendmail(HttpServletRequest req , HttpServletResponse res,String typ,String msg){
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
        GenericInterface genericInt = new GenericImpl();
        GenMail mail = new GenMail();
        String senderNm =(String)dbmsInfo.get("SENDERNM");
        String MAILTO = (String)dbmsInfo.get("MAILTO");
        String senderID =(String)dbmsInfo.get("SENDERID");
        info.setSmtpHost((String)dbmsInfo.get("SMTPHost"));
        info.setSmtpPort(Integer.parseInt((String)dbmsInfo.get("SMTPPort")));
        info.setSmtpUsr((String)dbmsInfo.get("SMTPUsr"));
        info.setSmtpPwd((String)dbmsInfo.get("SMTPPwd"));
        HashMap mailDtl = util.getMailFMT("FILEUPLOAD");
        String client = (String)dbmsInfo.get("CNT");
        HashMap logDetails=new HashMap();
        if(mailDtl!=null && mailDtl.size()>0){
        String bodymsg=util.nvl((String)mailDtl.get("MAILBODY"));
        String subj=util.nvl((String)mailDtl.get("SUBJECT"));
        mail.setInfo(info);
        mail.init();
        if(senderID.equals("NA"))
        senderID=util.nvl((String)dbmsInfo.get("SENDERIDIFNA"));  
        mail.setSender(senderID, senderNm);
        StringBuffer body=new StringBuffer();
        HtmlMailUtil html = new HtmlMailUtil();
        body.append(html.head("File Upload Details"));
        body.append(html.body());
        if(bodymsg.indexOf("~TYM~") > -1)
        bodymsg = bodymsg.replaceAll("~TYM~", util.getToDteTime());
        if(bodymsg.indexOf("~USR~") > -1)
        bodymsg = bodymsg.replaceAll("~USR~", info.getUsr());
        if(bodymsg.indexOf("~MSG~") > -1)
        bodymsg = bodymsg.replaceAll("~MSG~", util.nvl(msg));
        if(bodymsg.indexOf("~TYP~") > -1)
        bodymsg = bodymsg.replaceAll("~TYP~",typ);
        body.append(bodymsg);
        String mailMag = body.toString();
        if(subj.indexOf("~TYM~") > -1)
        subj = subj.replaceAll("~TYM~", util.getToDteTime());
        if(subj.indexOf("~TYP~") > -1)
        subj = subj.replaceAll("~TYP~",typ);
        mail.setSubject(subj);
        mail.setMsgText(mailMag);
        mail.setBCC(MAILTO);
        String eml = util.nvl((String)mailDtl.get("CCEML"));
        String[] emlLst = eml.split(",");
        for(int i=0 ; i <emlLst.length; i++)
        {
        mail.setCC(emlLst[i]);
        }

         String bcceml = util.nvl((String)mailDtl.get("BCCEML"));
         String[] bccemlLst = bcceml.split(",");
          for(int i=0 ; i <bccemlLst.length; i++)
          {
          mail.setBCC(bccemlLst[i]);
          }
            String toeml = util.nvl((String)mailDtl.get("TOEML"));
            String[] toemlLst = toeml.split(",");
             for(int i=0 ; i <toemlLst.length; i++)
             {
             mail.setTO(toemlLst[i]);
             }
//            mail.setBCC("mayur.boob@faunatechnologies.com");
            if(typ.equals("PRI") && client.equals("kj")){
                try {
                ExcelUtil xlUtil = new ExcelUtil();
                xlUtil.init(db, util, info);
                ArrayList attAttachFilNme = new ArrayList();
                ArrayList attAttachTyp = new ArrayList();
                ArrayList attAttachFile = new ArrayList();
                ArrayList pktList = new ArrayList();
                ArrayList itemHdr = new ArrayList();
                ArrayList ary = new ArrayList();
                String file_seq="";
                file_seq=msg;
                file_seq=file_seq.substring(file_seq.lastIndexOf(":")+1, file_seq.length());   
                String fileNm = "PRICEUPLOAD"+"_"+util.getToDteTime()+".xls"; 
                String filePath = session.getServletContext().getRealPath("/") + fileNm;
                String delQ = " Delete from gt_srch_rslt ";
                int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
                String srchRefQ ="Insert into gt_srch_rslt (stk_idn , vnm  ,cts, flg , stt,rap_dis ,ofr_dis)\n" + 
                "With MAX_STK_PRI_LOG as (select max(idn) idn, mstk_idn from stk_pri_log s group by mstk_idn)\n" + 
                "                select a.mstk_idn,b.vnm,decode(c.pkt_ty, 'NR', c.cts, c.cts - nvl(c.cts_iss, 0)),'Z', c.stt,\n" + 
                "                trunc(((decode(a.flg, 'MNL', a.old_upr, a.old_cmp)/greatest(a.old_rap_rte,1))*100)-100,2) old_dis ,\n" + 
                "                trunc(((decode(a.flg, 'MNL', a.upr, a.cmp)/greatest(a.rap_rte,1))*100)-100,2) new_dis\n" + 
                "                from stk_pri_log a,direct_price_upd_ora b,mstk c,max_stk_pri_log m\n" + 
                "                where b.file_seq =? \n" + 
                "                and a.mstk_idn=c.idn \n" + 
                "                and b.vnm=c.vnm and trunc(b.dte)=trunc(sysdate)\n" + 
                "                and a.idn = m.idn and a.mstk_idn = m.mstk_idn and m.mstk_idn=c.idn and c.stt in ('MKAV','MKPP','MKWH','MKEI','MKIS','SHIS','MKKS_IS','MKOS_IS') \n" + 
                "                order by a.idn desc"; 
                ary.add(file_seq.trim());
                ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
                if(ct>0){
                String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
                ary = new ArrayList();
                ary.add("PRICEUPLOAD_VW");
                ct = db.execCall(" Srch Prp ", pktPrp, ary);
                ArrayList vwPrpLst =genericInt.genericPrprVw(req,res,"PRICEUPLOADVW","PRICEUPLOAD_VW");
                ArrayList prpDspBlocked = info.getPageblockList();
                itemHdr.add("Sr");
                itemHdr.add("VNM");
                    String  srchQ =  " select stk_idn,  vnm,cts, stt,rap_dis old_dis,ofr_dis new_dis,nvl(ofr_dis,0)-nvl(rap_dis,0) diff";
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
                        if(prpDspBlocked.contains(lprp)){
                        }else{
                        itemHdr.add(lprp);
                    }}
                    itemHdr.add("OldRapDiS");
                    itemHdr.add("NewRapDiS");
                    itemHdr.add("DIFF");
                    itemHdr.add("STATUS");
                    
                    int sr=1;
                    String rsltQ = srchQ + " from gt_srch_rslt where 1=1 order by stk_idn";
                    

                    ArrayList outLst = db.execSqlLst("search Result", rsltQ, new ArrayList());
                    PreparedStatement pst = (PreparedStatement)outLst.get(0);
                    ResultSet rs = (ResultSet)outLst.get(1);
                    try {
                        while(rs.next()) {

                            HashMap pktPrpMap = new HashMap();
                            pktPrpMap.put("Sr", String.valueOf(sr++));
                            pktPrpMap.put("STATUS", util.nvl(rs.getString("stt")));
                            String vnm = util.nvl(rs.getString("vnm"));
                            pktPrpMap.put("VNM",vnm);
                            pktPrpMap.put("stk_idn",util.nvl(rs.getString("stk_idn")));
                            pktPrpMap.put("OldRapDiS",util.nvl(rs.getString("old_dis")));
                            pktPrpMap.put("NewRapDiS",util.nvl(rs.getString("new_dis")));
                            pktPrpMap.put("DIFF",util.nvl(rs.getString("diff")));
                            for(int j=0; j < vwPrpLst.size(); j++){
                                 String prp = (String)vwPrpLst.get(j);
                                  
                                  String fld="prp_";
                                  if(j < 9)
                                          fld="prp_00"+(j+1);
                                  else    
                                          fld="prp_0"+(j+1);
                                  
                                  String val = util.nvl(rs.getString(fld)) ;
                                  if (prp.toUpperCase().equals("CRTWT"))
                                  val = util.nvl(rs.getString("cts"));
                                    
                                    pktPrpMap.put(prp, val);
                                     }
                                          
                                pktList.add(pktPrpMap);
                            }
                        rs.close(); pst.close();
                    } catch (SQLException sqle) {

                        // TODO: Add catch code
                        sqle.printStackTrace();
                    }
                HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
                FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                hwb.write(fileOutputStream);
                attAttachFilNme.add(fileNm);
                attAttachTyp.add("application/vnd.ms-excel");
                attAttachFile.add(filePath);
                mail.setFileName(attAttachFilNme);
                mail.setAttachmentType(attAttachTyp);
                mail.setAttachments(attAttachFile);
                }
                } catch (Exception ioe) {
                // TODO: Add catch code
                ioe.printStackTrace();
                }
//            mail.setTO("kalpesh@kapugems.com");
//            mail.setTO("kumar@kapugems.com");
        }
            logDetails.put("BYRID","");
            logDetails.put("RELID","");
            logDetails.put("TYP","FILEUPLOAD");
            logDetails.put("IDN","");
            String mailLogIdn=util.mailLogDetails(req,logDetails,"I");  
            logDetails.put("MSGLOGIDN",mailLogIdn);
            logDetails.put("MAILDTL",mail.send(""));
            util.mailLogDetails(req,logDetails,"U");
        }
    }
    
    
    public HashMap VerifiedHeadData(HttpServletRequest req , HttpServletResponse res,ArrayList dataList,HashMap params){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        String msg="SUCCESS";
       
        String headerstr = (String)dataList.get(0);
        ArrayList headerList = new ArrayList();
        ArrayList mappingNFList = new ArrayList();
        if(!headerstr.equals("")){
            String[] headerLst = headerstr.split(",");
            int hedLnt = headerLst.length;
            if(hedLnt>0){
                for(int i=0;i<hedLnt;i++){
                    String hdr = headerLst[i];
                    headerList.add(hdr);
                }
                
            mappingNFList = VerifiedHeader(req, res, db, headerList) ;
            }else
             msg="Header is empty";  
            
        }else
            msg="Header is empty";
        HashMap returnData = new HashMap();
        returnData.put("MSG", msg);
        returnData.put("NFMAPPINGLST", mappingNFList);
        return returnData;
       }
    
    public ArrayList VerifiedHeader(HttpServletRequest req , HttpServletResponse res,DBMgr db,ArrayList headerList){
      JspUtil jspUtil = new JspUtil();
       HttpSession session = req.getSession(false);
       InfoMgr info = (InfoMgr)session.getAttribute("info");
       HashMap dbinfo = info.getDmbsInfoLst();
       HashMap mappingMap = jspUtil.MappingMap(dbinfo);
       ArrayList mappingNFList = new ArrayList();
       if(mappingMap!=null){
           int hedLn = headerList.size();
           for(int i=0;i<hedLn;i++){
               String hedVal = (String)headerList.get(i);
               String mappIdn = jspUtil.nvl((String)mappingMap.get(hedVal+"#ID"));
               if(mappIdn.equals("")){
                 mappingNFList.add(hedVal);
             }}
         }
        return mappingNFList;
       
   }
    
    public HashMap VerifiedHeaderDtl(ArrayList dataList,HashMap dbinfo){
      JspUtil jspUtil = new JspUtil();
       HashMap mappingMap = jspUtil.MappingMap(dbinfo);
       ArrayList headerList = new ArrayList();
       ArrayList headerIdnList = new ArrayList();
       ArrayList headerIdnTypList = new ArrayList();
       HashMap headerDtl = new HashMap();
        String headerstr = (String)dataList.get(0);
        if(!headerstr.equals("")){
            String[] headerLst = headerstr.split(",");
            int hedLnt = headerLst.length;
            if(hedLnt>0){
            for(int i=0;i<hedLnt;i++){
                    String hdrval = headerLst[i];
                    String mappIdn = jspUtil.nvl((String)mappingMap.get(hdrval+"#ID"));
                    String mapptyp = jspUtil.nvl((String)mappingMap.get(hdrval+"#D"));
                    headerList.add(hdrval);
                if(!mappIdn.equals("")){
                    headerIdnList.add(mappIdn);
                    headerIdnTypList.add(mapptyp);
                }else{
                    headerIdnList.add("");
                    headerIdnTypList.add("");
                }
             }
            headerDtl.put("TYPLIST", headerIdnTypList);     
            headerDtl.put("IDNLIST", headerIdnList);     
            headerDtl.put("VALLIST", headerList);   
            
        }}
        return headerDtl;
       
    }
    
    public HashMap VerifiedFileData(HttpServletRequest req , HttpServletResponse res,String dteFmt,ArrayList dataList){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        JspUtil jspUtil = new JspUtil();
        HashMap dbinfo = info.getDmbsInfoLst();
        HashMap returnMap = new HashMap();
        HashMap headerDtlMap = VerifiedHeaderDtl(dataList,dbinfo);
        ArrayList headerList = (ArrayList)headerDtlMap.get("VALLIST");
        ArrayList headerIdnList = (ArrayList)headerDtlMap.get("IDNLIST");
        ArrayList headerTypList = (ArrayList)headerDtlMap.get("TYPLIST");
        int fileSeq = (Integer)session.getAttribute("file_seq");
       HashMap mappingMap = jspUtil.MappingMap(dbinfo);
        String stt="SUCCESS";
        String msg="";
        HashMap prpMappingNotFd=new HashMap();
        int headerLn=headerList.size();
        int dataSize = dataList.size();
        String headStr="";
        for(int i=1;i< dataSize;i++){
            String dataStr = (String)dataList.get(i);
            if(!dataStr.equals("")){
                String[] dataStrLst = dataStr.split(",");
                int dataLnt = dataStrLst.length;
                String mppingError="";
             if(dataLnt>0){
                 if(headerLn==dataLnt){
                for(int j=0;j<dataLnt;j++){
                    String dtlVal =jspUtil.nvl((String)dataStrLst[j]);
                     dtlVal=dtlVal.trim();
                    String hedTyp = jspUtil.nvl((String)headerTypList.get(j));
                     String hedIdn = jspUtil.nvl((String)headerIdnList.get(j));
                     String hedVal = jspUtil.nvl((String)headerList.get(j));
                    if(!hedTyp.equals("")){
                        if(i==1)
                            headStr=headStr+","+hedVal;
                        if(hedTyp.equals("C")){
                         String mapVal = jspUtil.nvl((String)mappingMap.get(hedIdn+"#"+dtlVal+"#V1"));
                         if(mapVal.equals(""))
                             mapVal = jspUtil.nvl((String)mappingMap.get(hedIdn+"#"+dtlVal+"#V2"));
                         if(mapVal.equals("")){
                             ArrayList dtlList = (ArrayList)prpMappingNotFd.get(hedIdn);
                          if(dtlList==null)
                                 dtlList = new ArrayList();
                          if(!dtlList.contains(dtlVal))
                               dtlList.add(dtlVal);
                                 prpMappingNotFd.put(hedIdn, dtlList);
                             mppingError=mppingError+","+hedVal+"="+dtlVal;
                         }
                           
                        }else if(hedTyp.equals("N")){
                            boolean isValid = jspUtil.isNumValid(dtlVal);
                          
                            if(!isValid){
                                ArrayList dtlList = (ArrayList)prpMappingNotFd.get(hedIdn);
                                if(dtlList==null)
                                    dtlList = new ArrayList();
                                  dtlList.add(hedVal+"="+dtlVal);
                                    prpMappingNotFd.put(hedIdn, dtlList);
                                mppingError=mppingError+","+hedVal+"="+dtlVal;
                            }
                            
                            
                                
                        }else if(hedTyp.equals("D")){
                        boolean isValid=jspUtil.CompairingDateFmt(dtlVal,dteFmt);
                            if(!isValid){
                                ArrayList dtlList = (ArrayList)prpMappingNotFd.get(hedIdn);
                                if(dtlList==null)
                                    dtlList = new ArrayList();
                                  dtlList.add(hedVal+"="+dtlVal);
                                    prpMappingNotFd.put(hedIdn, dtlList);
                                mppingError=mppingError+","+hedVal+"="+dtlVal;
                            }
                                
                        }
                       
                
                 }}   
                
                    }else{
                          stt="FAIL";
                    msg="There are problem with Row no."+i+" Please check and upload again";
                        updateFileLog(req, res, i, fileSeq, msg);
                             break;
                    }
                if(mppingError.length()>1)
                     updateFileLog(req, res, i+1, fileSeq, "NF:-"+mppingError);
                  
                }
                }
                
                
            }
            returnMap.put("STT", stt) ;
            returnMap.put("MSG", msg) ;
            returnMap.put("PRPNOTFD", prpMappingNotFd);
            returnMap.put("HEADDTL", headerDtlMap);
            return returnMap;
        }
        
    public int InsertFileHdr(HttpServletRequest req , HttpServletResponse res,String fileTyp){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        JspUtil jspUtil = new JspUtil();
        HashMap dbinfo = info.getDmbsInfoLst();
        int fileSeq=0;
        int ct =0;
        try {

            ArrayList outLst = db.execSqlLst("loadSQL", "select file_hdr_seq.nextval from dual",
                           new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            if (rs.next())
                fileSeq = rs.getInt(1);
            rs.close(); pst.close();
            
            ArrayList params = new ArrayList();
            params.add(String.valueOf(fileSeq));
            params.add(fileTyp);
            params.add(info.getDbUsr());
            params.add("P");
            
            String mloadInsert =
                "insert into file_hdr(file_idn , file_typ , uploaded_by , file_stt  ) values( ? , ? , ? , ?)";
         ct = db.execUpd("mload", mloadInsert, params);
            
            
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        if(ct<1)
            fileSeq=0;
        return fileSeq;
    }
    
    public void InsertFileData(HttpServletRequest req , HttpServletResponse res,ArrayList dataList, int fileSeq){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        int dataSize=dataList.size();
        for(int i=0;i<dataSize;i++){
            String dtl = (String)dataList.get(i) ;
            int lnNo=i+1;
        ArrayList params = new ArrayList();
        params.add(String.valueOf(fileSeq));
        params.add(String.valueOf(lnNo));
        params.add(dtl);
        
        String mloadInsert =
            "insert into file_log(log_idn , file_idn , ln_no , dtl  ) values( file_log_seq.nextval , ? , ? , ?)";
        int ct = db.execUpd("mload", mloadInsert, params);
        }
        
    }
    
    public void updateFileLog(HttpServletRequest req , HttpServletResponse res,int lineNo, int fileSeq,String rmk){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        String updateFileLog="update file_log set rmk=? where file_idn=?  and  ln_no=? ";
        ArrayList params = new ArrayList();
        params.add(rmk);
        params.add(String.valueOf(fileSeq));
        params.add(String.valueOf(lineNo));
        int ct = db.execUpd("updatelog", updateFileLog, params);
        
    }
    
    public void updateFileHdr(HttpServletRequest req , HttpServletResponse res,String stt,int fileSeq){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        String updateFileLog="update FILE_hdr set stt=? where file_idn=? ";
        ArrayList params = new ArrayList();
        params.add(stt);
        params.add(String.valueOf(fileSeq));
        int ct = db.execUpd("updatelog", updateFileLog, params);
        
    }
    public void updateFileDtlLog(HttpServletRequest req , HttpServletResponse res,int lineNo, int fileSeq,String dtl){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        String updateFileLog="update file_log set dtl=? where file_idn=?  and  ln_no=? ";
        ArrayList params = new ArrayList();
        params.add(dtl);
        params.add(String.valueOf(fileSeq));
        params.add(String.valueOf(lineNo));
        int ct = db.execUpd("updatelog", updateFileLog, params);
        
    }
    
    public ArrayList uploadFile(HttpServletRequest req , HttpServletResponse res,String uploadTyp,int fileSeq){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        JspUtil jspUtil = new JspUtil();
        boolean isSuccess=true;
        JSONObject jsonOutPut=null;
        ArrayList sttList=new ArrayList();
        String status ="FAIL";
        String msg="Some Error in process..";
        try{
        JSONObject jObj = new JSONObject();
        jObj.put("file_idn", String.valueOf(fileSeq));
        jObj.put("cnt", info.getDbTyp());
            
        System.out.print(jObj.toString());
        String serviceUrl="http://apps.faunatechnologies.com/diaflexWebService/REST/fileUpload/load";
        
           JsonDao jsonDao = new JsonDao();
            jsonDao.setServiceUrl(serviceUrl);
            jsonDao.setJsonObject(jObj);
            String jsonInString = jspUtil.getJsonString(jsonDao);
            System.out.println(jsonInString);
            if (!jsonInString.equals("FAIL")) {
                jsonOutPut = new JSONObject(jsonInString);
            } else
                isSuccess = false;
          status = jspUtil.nvl((String)jsonOutPut.get("STATUS"));
         msg = jspUtil.nvl((String)jsonOutPut.get("MASSAGE"));
        if(!status.equals("SUCCESS"))
            isSuccess = false;
        } catch (JSONException jsone) {
            // TODO: Add catch code
            jsone.printStackTrace();
            isSuccess = false;
        }
     sttList.add(status);
     sttList.add(msg);
       return  sttList;
    }

}
