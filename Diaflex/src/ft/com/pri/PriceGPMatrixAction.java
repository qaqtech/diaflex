package ft.com.pri;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtilObj;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import ft.com.PriSheetExcel;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.SearchQuery;
import ft.com.report.AssortLabPendingForm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashMap;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import java.util.StringTokenizer;
import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class PriceGPMatrixAction extends DispatchAction {
   
    public PriceGPMatrixAction() {
        super();
    }
    
    public ActionForward loadHome(ActionMapping am, ActionForm af, HttpServletRequest req,
                                  HttpServletResponse res) throws Exception {
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
          util.updAccessLog(req,res,"PriceGPMatrix", "loadHome");
          util.updAccessLog(req,res,"PriceGPMatrix", "loadHome");
       return am.findForward("priceGrpHome");
        }
    }
    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
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
          util.updAccessLog(req,res,"PriceGPMatrix", "load");
        PriceGPMatrixForm  udf = (PriceGPMatrixForm)af;
        String grpNme = util.nvl(req.getParameter("grpNme"));
          ArrayList PRICESHEETIDNLST = (ArrayList)udf.getValue("PRICESHEETIDNLST");
        boolean isManual=false;
        if(grpNme.equals(""))
            grpNme = (String)udf.getValue("grpNme");
        udf.reset();
        udf.setValue("grpNme", grpNme);
            udf.setValue("PRICESHEETIDNLST", PRICESHEETIDNLST);
        req.setAttribute("grpNme", grpNme);
      String matIdn = util.nvl(req.getParameter("matIdn"));
        ArrayList rowList = new ArrayList(); //contains llist of row prp
        ArrayList colList = new ArrayList(); // contains list of Column prp
        ArrayList comList = new ArrayList();
        ArrayList refList = new ArrayList();
        HashMap prpMaps = new HashMap();
        ArrayList ary = new ArrayList();
        ArrayList prpList = new ArrayList();
        ary.add(grpNme);
        String grpdtls = "select nme,mprp,typ,dsp_flg,dsp_all from pri_grp_prp where stt='A' and nme=? order by nme, srt, seq, loc ";
          ArrayList  rsList = db.execSqlLst(" Group dtls", grpdtls, ary);
           PreparedStatement pst=(PreparedStatement)rsList.get(0);
           ResultSet  rs = (ResultSet)rsList.get(1);
        try {
            while (rs.next()) {
                String typ = util.nvl(rs.getString("typ"));
                String nme = util.nvl(rs.getString("nme"));
                String mprp = util.nvl(rs.getString("mprp"));
                String dspFlg = util.nvl(rs.getString("dsp_flg"));
                String dspTyp = util.nvl(rs.getString("dsp_all"));
                if(dspFlg.equals("M"))
                    isManual = true;
                if(typ.equalsIgnoreCase("COL")){
                    colList.add(mprp);
                    if(dspFlg.equals("I"))
                      prpList = prpList(req, mprp, dspTyp);  
                    else
                      prpList = defPrpList(req,mprp, nme);
                        
                  }else if(typ.equalsIgnoreCase("ROW")){
                    rowList.add(mprp);
                    if(dspFlg.equals("I"))
                        prpList = prpList(req,mprp , dspTyp);  
                    else
                       prpList = defPrpList(req,mprp, nme);
                }else if(typ.equalsIgnoreCase("CMN")){
                    comList.add(mprp);
                    prpList = prpList(req,mprp , dspTyp);
                }else{
                    refList.add(mprp);
                    prpList = prpList(req,mprp, dspTyp);
                 }
            prpMaps.put(mprp, prpList);
            }
             rs.close();
            pst.close();
         } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
         }
        int coloumnSizeCnt=0;
        if(!isManual){
        int totalColCount = 1 ;
        for(int row=0;row < rowList.size(); row++) {
            int totalCount = 1;
            if(rowList.size() > 1) {
                for(int i=(row+1); i < rowList.size(); i++) {
                    String rowPrp = (String)rowList.get(i);
                    int rowPrpCnt = ((ArrayList)prpMaps.get(rowPrp)).size();
                    totalCount = totalCount*rowPrpCnt ;
                }
            }
            ArrayList newList = new ArrayList();
            String lprp = (String)rowList.get(row);
            ArrayList lprpList = (ArrayList)prpMaps.get(lprp);

//            totalCount = totalCount*lprpList.size() ;

            if((row == 0) && (rowList.size() > 1))
                totalColCount = totalCount*lprpList.size() ;
                        
            int loopCount = 1 ;
            if (row > 0) 
                loopCount = totalColCount/totalCount/lprpList.size() ;
            
            for(int loop = 1; loop <= loopCount; loop++) {
                if(row < (rowList.size() - 1)) {
                    for(int a = 0; a < lprpList.size(); a++) {
                        String lPrpVal = (String)lprpList.get(a);
                        for(int b=0; b < totalCount; b++) {
                            newList.add(lPrpVal);
                        }
                    }
                } else {
                    for(int a = 0; a < totalCount; a++) {
                        for(int b=0; b < lprpList.size(); b++) {
                            String lPrpVal = (String)lprpList.get(b);
                            newList.add(lPrpVal);
                        }
                    }
                }
            }
               
            prpMaps.put(lprp, newList);  
        }
        session.setAttribute("rowPrpSize"+matIdn, String.valueOf(totalColCount));
        
         totalColCount = 1 ;
        
        for(int row=0;row < colList.size(); row++) {
            int totalCount = 1;
            if(colList.size() > 1) {
                for(int i=(row+1); i < colList.size(); i++) {
                    String rowPrp = (String)colList.get(i);
                    int rowPrpCnt = ((ArrayList)prpMaps.get(rowPrp)).size();
                    totalCount = totalCount*rowPrpCnt ;
                }
            } 
                

            ArrayList newList = new ArrayList();
            String lprp = (String)colList.get(row);
            ArrayList lprpList = (ArrayList)prpMaps.get(lprp);

        //            totalCount = totalCount*lprpList.size() ;

                if((row == 0) && (colList.size() > 1))
                  totalColCount = totalCount*lprpList.size() ;
                        
            int loopCount = 1 ;
            if (row > 0) 
                loopCount = totalColCount/totalCount/lprpList.size() ;
            
            for(int loop = 1; loop <= loopCount; loop++) {
                if(row < (colList.size() - 1)) {
                    for(int a = 0; a < lprpList.size(); a++) {
                        String lPrpVal = (String)lprpList.get(a);
                        for(int b=0; b < totalCount; b++) {
                            newList.add(lPrpVal);
                        }
                    }
                } else {
                    for(int a = 0; a < totalCount; a++) {
                        for(int b=0; b < lprpList.size(); b++) {
                            String lPrpVal = (String)lprpList.get(b);
                            newList.add(lPrpVal);
                        }
                    }
                }
            }
               
            prpMaps.put(lprp, newList); 
            coloumnSizeCnt = newList.size();
            }
            session.setAttribute("IsManual"+matIdn, "NO");
        }else{
        session.setAttribute("IsManual"+matIdn, "Y");
            }
       
       
       if(!matIdn.equals("")){
           if(isManual)
               loadDateMN(Integer.parseInt(matIdn) , req , udf);
          else
            loadDate(req,Integer.parseInt(matIdn) , udf);
           udf.setValue("matIdn", matIdn); 
       }
       
        session.setAttribute("rowList"+matIdn, rowList);
        session.setAttribute("columList"+matIdn, colList);
        session.setAttribute("commonList"+matIdn, comList);
        session.setAttribute("refList"+matIdn, refList);
        session.setAttribute("prpMaps"+matIdn, prpMaps);
        req.setAttribute("matIdn", matIdn);
        session.setAttribute("columnPrpSize"+matIdn, String.valueOf(coloumnSizeCnt));
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("PRC_SHEET");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("PRC_SHEET");
            allPageDtl.put("PRC_SHEET",pageDtl);
            }
            info.setPageDetails(allPageDtl);
          util.updAccessLog(req,res,"PriceGPMatrix", "load end");
        return am.findForward("priceGrpMatrix");
        }
    }
    public ArrayList prpList(HttpServletRequest req, String mprp , String  dspTyp){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
        HashMap dspAllCond = new HashMap();
        dspAllCond.put("SA", " and val not like '%+' and val not like '%-' ");
        dspAllCond.put("S", " and val not like '%+' and val not like '%-' and flg is null ");
        dspAllCond.put("PM", " and (val like '%+' or val like '%-') and flg is null ");
        dspAllCond.put("ALL", "and 1 = 1 ");
            
        ArrayList prpList =  new ArrayList();
        String getPrp = 
            "select a.mprp prp, a.srt srt, a.val val, upper(nvl(a.prt1,a.val)) prt, b.dta_typ typ"+
            ", lag(a.mprp, 1) over (order by b.srt, a.srt) p_prp  "+
            " from prp a, mprp b "+
            " where a.mprp = b.prp "+
            " and trunc(nvl(b.vld_till, sysdate)) <= trunc(sysdate)  "+
            " and trunc(nvl(a.vld_till, sysdate)) <= trunc(sysdate)  "+util.nvl((String)dspAllCond.get(dspTyp))+
            " and b.dta_typ = 'C' "+
            " and a.mprp = ? "+
            " order by b.srt, a.srt";
        ArrayList ary = new ArrayList();
        ary.add(mprp);
        ArrayList outLst = db.execSqlLst("prp List", getPrp, ary);
           PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
             prpList.add(rs.getString("val"));
            }
       
        rs.close();
        pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        return prpList;
    }
    
    public ArrayList defPrpList(HttpServletRequest req, String mprp , String nme){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
        ArrayList prpList =  new ArrayList();
        String getPrp = "select a.vfr,a.vto from pri_grp_prp_dtl a,pri_grp_prp b " +
                        " where b.idn=a.pri_grp_prp_idn and b.nme= ? and a.mprp=? and a.flg='A' " +
                        " order by a.srt";
        ArrayList ary = new ArrayList();
        ary.add(nme);
        ary.add(mprp);
        ArrayList outLst = db.execSqlLst("getPrp", getPrp, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                String vfr = util.nvl(rs.getString("vfr"));
                String vto = util.nvl(rs.getString("vto"));
             prpList.add(vfr+"~"+vto);
            }
        
        rs.close();
        pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        return prpList;
        
    }
   
    public ActionForward delete(ActionMapping am, ActionForm af, HttpServletRequest req,
                                  HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
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
          util.updAccessLog(req,res,"PriceGPMatrix", "delete");
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
             HashMap pageDtl=(HashMap)allPageDtl.get("PRICE_MATRIX");
           ArrayList pageList= ((ArrayList)pageDtl.get("BASESHT") == null)?new ArrayList():(ArrayList)pageDtl.get("BASESHT");
            HashMap basSheetMap=new HashMap();
          if(pageList!=null && pageList.size()>0)
            basSheetMap = (HashMap)pageList.get(0);
          String basShts =util.nvl((String)basSheetMap.get("fld_nme"));
        PriceGPMatrixForm  udf = (PriceGPMatrixForm)af;
          String grpNme = util.nvl((String)udf.getValue("grpNme"));
         Enumeration reqNme = req.getParameterNames();
        ArrayList mpriIdnLst = new ArrayList();
        while(reqNme.hasMoreElements()) {
            String paramNm = (String)reqNme.nextElement();
            if(paramNm.indexOf("cb") > -1) {
                String val = req.getParameter(paramNm);
               
                String pktNm = val ;//paramNm.substring(paramNm.indexOf("rb_")+3);
                if(paramNm.equals("cb_mat_"+val)){
                    mpriIdnLst.add(pktNm);
                   
                }
         }}
          String delete = util.nvl((String)udf.getValue("delete"));
          String download = util.nvl((String)udf.getValue("download"));
          String upload = util.nvl((String)udf.getValue("upload"));
          if(delete.equals("Delete")){
        for(int i=0;i<mpriIdnLst.size();i++){
            String matIdn = (String)mpriIdnLst.get(i);
            String delMatrix = " prc_data_pkg.del_matrix_idn(?) ";
            ArrayList params = new ArrayList();
            params.add(matIdn);
            
            db.execCall(" Del Mat", delMatrix, params);
            }}else if(upload.equals("Upload file")){
               
              if(mpriIdnLst!=null && mpriIdnLst.size()>0){
                  String colRowPrp = "select mprp from pri_grp_prp where stt='A' and nme=? and typ in ('COL','ROW') order by   typ,srt ";
                  ArrayList ary                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               = new ArrayList();
                  ary.add(grpNme);
                  
                ArrayList outLst = db.execSqlLst("colRowPrp", colRowPrp,ary);
                   PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
                  String grid_prp="";
                  while(rs.next()){
                      String mprp = util.nvl(rs.getString("mprp"));
                      if(grid_prp.equals(""))
                          grid_prp = mprp;
                      else
                          grid_prp = grid_prp+"#"+mprp;
                  }
                  rs.close();
                  pst.close();
                ArrayList msgLst = new ArrayList();
                for(int i=0;i<mpriIdnLst.size();i++){
                    String matIdn = (String)mpriIdnLst.get(i);
                    String prmnme = util.nvl(req.getParameter(matIdn+"_SHTNME"));
                    String PRMTYP = util.nvl(req.getParameter("PRMTYP_"+matIdn));
                    String refIdSql = "select seq_pri_file_load.nextval from dual";
                    rs = db.execSql("refIDn", refIdSql, new ArrayList());
                    int priFileIdn=0;
                    while(rs.next()){
                    priFileIdn=rs.getInt(1);
                    }
                    rs.close();
                    String inspri_file_load = "insert into pri_file_load(idn,hdr_idn,prmnme,grid_prp) select ? , ? ,? , ? from dual ";
                    ary = new ArrayList();
                    ary.add(String.valueOf(priFileIdn));
                    ary.add(matIdn);
                    ary.add(prmnme);
                    ary.add(grid_prp);
                    int ct = db.execUpd("inspri_file_load", inspri_file_load, ary);
                    if(ct>0){
                    FormFile uploadFile = udf.getFileVal("cb_upl_"+matIdn);
                     if(uploadFile!=null){
                         int ln=0;
                     String fileName = uploadFile.getFileName();
                         if(!fileName.equals("")){
                             fileName = matIdn+"_"+fileName;
                         String fileTyp = uploadFile.getContentType();
                         String path = getServlet().getServletContext().getRealPath("/") + fileName;
                             File readFile = new File(path);
                             if(!readFile.exists()){
                             FileOutputStream fileOutStream = new FileOutputStream(readFile);
                             fileOutStream.write(uploadFile.getFileData());
                             fileOutStream.flush();
                             fileOutStream.close();
                             } 

                             FileReader       fileReader = new FileReader(readFile);
                             LineNumberReader lnr        = new LineNumberReader(fileReader);
                             String           line       = "";
                             ArrayList headLst = new ArrayList();
                             int colCnt=0;
                             int rowNo=0;
                             int colNo=0;
                             while ((line = lnr.readLine()) != null) {
                                 ln     = lnr.getLineNumber();
                                 String[] vals   = line.split(",");
                                 ArrayList          aryLst = new ArrayList();
                                
                                 String colPrp="";
                                 int hdcnt=0;
                                 for(int j=0;j<vals.length;j++) {
                                     String val =vals[j];
                                     if(ln==1){
                                     headLst.add(val);
                                     if(val.equals(""))
                                         colCnt++;
                                     }else{
                                     if(j<colCnt){
                                        colPrp =colPrp+"#"+val;
                                        }else{
                                         String key = colPrp+"#"+headLst.get(j);
                                         key=key.replaceFirst("#", "");
                                         String inspri_file_loadDtl = "insert into pri_file_load_dtl(idn,pri_file_load_idn,prp,vlu,mpri_idn,row_no,col_no)  select seq_pri_file_load_dtl.nextval , ? ,? ,?,?,?,? from dual ";
                                         if(PRMTYP.equals("NR")){
                                             inspri_file_loadDtl = "insert into pri_file_load_dtl(idn,pri_file_load_idn,prp,pct,mpri_idn,row_no,col_no)  select seq_pri_file_load_dtl.nextval , ? ,? ,?, ?,?,? from dual ";
                                         }
                                         ary = new ArrayList();
                                         ary.add(String.valueOf(priFileIdn));
                                         ary.add(key);
                                         ary.add(val);
                                         ary.add(matIdn);
                                         ary.add(String.valueOf(rowNo));
                                         ary.add(String.valueOf(colNo));
                                         ct = db.execUpd("inspri_file_loadDtl", inspri_file_loadDtl, ary);
                                         hdcnt++;
                                         colNo++;
                                     }
                                     }
                                     }
                                 rowNo++;
                                 }
                             ary = new ArrayList();
                             ary.add(String.valueOf(priFileIdn));
                             ArrayList out = new ArrayList();
                             out.add("V");
                             CallableStatement cts = db.execCall("BULK_FILE_LOAD", "PRC_DATA_PKG.BULK_FILE_LOAD(pIdn => ?, pMsg => ?)", ary, out);       
                             String msg = cts.getString(ary.size()+1);
                             if(msg.equals("SUCCESS")){
                                 msgLst.add(msg);
                             }else{
                             msgLst.add(msg);
                             }
                         }
                     }
                     
                     
                         
                    }else{
                        msgLst.add("Error in uploading :"+prmnme);
                    }
                    
                             }
                req.setAttribute("msgList", msgLst);
              }
          }else if(!download.equals("")){
              HashMap priceSheetMap = new HashMap();
              HashMap dbinfo = info.getDmbsInfoLst();
              String client = (String)dbinfo.get("CNT");
              int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
              ArrayList mpriIdnLstFnl = new ArrayList();
              String mpriIdnOrg =  mpriIdnLst.toString();
              mpriIdnOrg = mpriIdnOrg.replaceAll("\\[","");
              mpriIdnOrg = mpriIdnOrg.replaceAll("\\]","");
              
              String[] vnmLst = mpriIdnOrg.split(",");
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
                     
                     toLoc = Math.min(mpriIdnOrg.lastIndexOf(lookupVnm) + lookupVnm.length(), mpriIdnOrg.length());
                     String mpriIdn = mpriIdnOrg.substring(fromLoc, toLoc);  
              String mpriCommon = "  select a.idn, a.prmnme, b.mprp,a.pri_grp, decode(c.dta_typ, 'C', b.val_fr, to_char(b.num_fr)) val_fr, " + 
              "       decode(c.dta_typ, 'C', b.val_to, to_char(b.num_to)) val_to " + 
              "            from mpri_hdr a, pri_cmn b, mprp c, pri_grp_prp d " + 
              "            where a.idn = b.hdr_idn and b.mprp = c.prp " + 
              "            and b.mprp = d.mprp and a.pri_grp = d.nme and d.typ = 'CMN'" + 
              "            and a.idn in ("+mpriIdn+") order by grp_srt, a.idn, d.srt ";
              
             ArrayList rsLst = db.execSqlLst("mpriCommon", mpriCommon, new ArrayList());
              PreparedStatement pst = (PreparedStatement)rsLst.get(0);
              ResultSet rs = (ResultSet)rsLst.get(1);
              String pmpriIdn="0";
              String CommonPrp = "";
              String prmnme = "";
              String pGrp="";
              mpriIdnLst = new ArrayList();
              while(rs.next()){
                  
                  String lmpriIdn = rs.getString("idn");
                  String lGrp=  rs.getString("pri_grp");
                  if(pGrp.equals(""))
                      pGrp=lGrp;
                  if(pmpriIdn.equals("0"))
                      pmpriIdn=lmpriIdn;
                  if(!pmpriIdn.equals(lmpriIdn)){
                      mpriIdnLst.add(pmpriIdn);
                      HashMap sheetDtl = new HashMap();
                      sheetDtl.put("SHEETNME", prmnme);
                      sheetDtl.put("COMMPRP", CommonPrp);
                    
                      if(basShts.indexOf(pGrp)!=-1){
                          String[] commPrp = CommonPrp.split("@");
                          for(int j=0;j<commPrp.length;j++){
                               String lprpLst = commPrp[j];
                              String[] lprp = lprpLst.split("#");
                              String lprpNme=lprp[0];
                              String lprpVal = lprp[1];
                              if(lprpNme.equals("SHAPE")){
                                 String  shape=lprpVal.substring(0, lprpVal.indexOf("~"));
                                 sheetDtl.put("SHAPE", shape);
                                }
                              if(lprpNme.equals("SIZE")){
                                  String size=lprpVal.substring(0, lprpVal.indexOf("~"));
                                 
                                  ArrayList ary = new ArrayList();
                                  ary.add(size);
                                  String wtFr="";
                                  String wtTo="";
                                  String crtWt="select wt_fr,wt_to from msz where dsc=?";
                                  ArrayList rsLst1 = db.execSqlLst("crtWt", crtWt,ary);
                                   PreparedStatement pst1 = (PreparedStatement)rsLst1.get(0);
                                   ResultSet rs1 = (ResultSet)rsLst1.get(1);
                                   if(rs1.next()){
                                       wtFr=rs1.getString("wt_fr");
                                       wtTo=rs1.getString("wt_to");
                                   }
                                  sheetDtl.put("WT_FR", wtFr);
                                  sheetDtl.put("WT_TO", wtTo);
                                   rs1.close();
                                  pst1.close();
                              }
                        
                      }}
                      priceSheetMap.put(pmpriIdn, sheetDtl);
                      pmpriIdn=lmpriIdn;
                      pGrp=lGrp;
                      CommonPrp="";
                      
                  }
                   prmnme = rs.getString("prmnme");
                  String mprp = rs.getString("mprp");
                  String valFr = rs.getString("val_fr");
                  String valTo = rs.getString("val_to");
                  if(CommonPrp.equals(""))
                      CommonPrp = mprp+"#"+valFr+"~"+valTo;
                  else
                      CommonPrp = CommonPrp+"@"+mprp+"#"+valFr+"~"+valTo;
              }
              rs.close();
              pst.close();
              if(!pmpriIdn.equals("")){
                  mpriIdnLst.add(pmpriIdn);
                  HashMap sheetDtl = new HashMap();
                  sheetDtl.put("SHEETNME", prmnme);
                  sheetDtl.put("COMMPRP", CommonPrp);
                  
                  if(basShts.indexOf(pGrp)!=-1){
                      String[] commPrp = CommonPrp.split("@");
                      for(int j=0;j<commPrp.length;j++){
                           String lprpLst = commPrp[j];
                          String[] lprp = lprpLst.split("#");
                          String lprpNme=lprp[0];
                          String lprpVal = lprp[1];
                          if(lprpNme.equals("SHAPE")){
                             String  shape=lprpVal.substring(0, lprpVal.indexOf("~"));
                             sheetDtl.put("SHAPE", shape);
                            }
                          if(lprpNme.equals("SIZE")){
                              String size=lprpVal.substring(0, lprpVal.indexOf("~"));
                             
                              ArrayList ary = new ArrayList();
                              ary.add(size);
                              String wtFr="";
                              String wtTo="";
                              String crtWt="select wt_fr,wt_to from msz where dsc=?";
                              ArrayList rsLst1 = db.execSqlLst("crtWt", crtWt,ary);
                               PreparedStatement pst1 = (PreparedStatement)rsLst1.get(0);
                               ResultSet rs1 = (ResultSet)rsLst1.get(1);
                               if(rs1.next()){
                                   wtFr=rs1.getString("wt_fr");
                                   wtTo=rs1.getString("wt_to");
                               }
                              sheetDtl.put("WT_FR", wtFr);
                              sheetDtl.put("WT_TO", wtTo);
                               rs1.close();
                              pst1.close();
                          }
                    
                  }}
                  priceSheetMap.put(pmpriIdn, sheetDtl);
              }
              rs.close();
              pst.close();
          
          
          String mpriSheet ="select c.srt, c.idn, d.typ, b.mprp ,  \n" + 
          "   decode(d.dsp_flg, 'I', decode(a.dta_typ, 'C', val_fr, trim(to_char(num_fr, a.fmt))), \n" + 
          "   decode(a.dta_typ, 'C', val_fr, trim(to_char(num_fr,a.fmt))) \n" + 
          "        ||'~'|| \n" + 
          "        decode(a.dta_typ, 'C', val_to, trim(to_char(num_to, a.fmt)))) val \n" + 
          "        , nvl(c.vlu , c.pct) fldval \n" + 
          "        from mprp a, pri_dtl b, mpri c, pri_grp_prp d where a.prp = b.mprp and b.mpri_idn = c.idn \n" + 
          "        and c.pri_grp = d.nme and a.prp = d.mprp and c.srt in ("+mpriIdn+") and typ in ('ROW','COL') order by c.srt  ,c.idn, d.typ asc, d.srt \n";
              rsLst = db.execSqlLst("mpriSheet", mpriSheet, new ArrayList());
               pst = (PreparedStatement)rsLst.get(0);
               rs = (ResultSet)rsLst.get(1);
              String priDtlIdn="";
              String typ="";
              String key="";
              String fldVal ="";
              String mprihdrIdn="";
              HashMap gridDtl = new HashMap();
              ArrayList gridKeyList = new ArrayList();
             
              while(rs.next()){
                  String lpriDtlIdn = rs.getString("idn");
                  if(priDtlIdn.equals(""))
                      priDtlIdn = lpriDtlIdn;
                  if(!priDtlIdn.equals(lpriDtlIdn)){
                     gridDtl.put(key, fldVal);
                      gridKeyList.add(key);
                      typ="";
                      key="";
                      priDtlIdn=lpriDtlIdn;
                  }
                  String lmpriIdn = rs.getString("srt");
                  if(mprihdrIdn.equals(""))
                      mprihdrIdn = lmpriIdn;
                  if(!mprihdrIdn.equals(lmpriIdn)){
                   
                      HashMap sheetDtl = (HashMap)priceSheetMap.get(mprihdrIdn);
                      gridKeyList.add(key);
                      sheetDtl.put("GridDtl", gridDtl);
                      sheetDtl.put("GridKeyList", gridKeyList);
                      priceSheetMap.put(mprihdrIdn, sheetDtl);
                      gridKeyList = new ArrayList();
                      gridDtl = new HashMap();
                      mprihdrIdn =lmpriIdn;
                  }
                 String lTyp  = rs.getString("typ");
                  String val = rs.getString("val");
                 fldVal = rs.getString("fldval");
                  if(typ.equals(""))
                      key=val;
                 else if(typ.equals(lTyp))
                    key=key+","+val; 
                 else
                    key=key+"#"+val;    
                      
                  typ=lTyp;
                
          }
              rs.close();
              if(!mprihdrIdn.equals("")){
                
                  gridDtl.put(key, fldVal);
                  gridKeyList.add(key);
                  HashMap sheetDtl = (HashMap)priceSheetMap.get(mprihdrIdn);
                  sheetDtl.put("GridDtl", gridDtl);
                  sheetDtl.put("GridKeyList", gridKeyList);
                  priceSheetMap.put(mprihdrIdn, sheetDtl);
                 
              }
           
              rs.close();
              pst.close();
              mpriIdnLstFnl.addAll(mpriIdnLst);
              }
                try {
                    PriSheetExcel priShtExcl = new PriSheetExcel();
                    priShtExcl.init(db, util, info, gtMgr);
                    HSSFWorkbook hwb = priShtExcl.getGenXlObj(priceSheetMap, mpriIdnLstFnl, req,db);
                    OutputStream out = res.getOutputStream();
                    String CONTENT_TYPE = "getServletContext()/vnd-excel";
                    String fileNm = "PrcieSheet" + util.getToDteTime() + ".xls";
                    res.setContentType(CONTENT_TYPE);
                    res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
                    hwb.write(out);
                    out.flush();
                    out.close();
                } catch (IOException ioe) {
                    // TODO: Add catch code
                    ioe.printStackTrace();
                }
              
          }else{
              SuratFmtExcel(am, af, req, res);
          }
              
          util.updAccessLog(req,res,"PriceGPMatrix", "delete end");
      return  edit(am, af, req, res);
        }
    }
   
    public ActionForward saveGrid(ActionMapping am, ActionForm af, HttpServletRequest req,
                                  HttpServletResponse res) throws Exception {
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
           util.updAccessLog(req,res,"PriceGPMatrix", "save");
           PriceGPMatrixForm  udf = (PriceGPMatrixForm)af;
        String addRow = util.nvl(req.getParameter("addROW"));
       db.execUpd("delete", "delete from GT_MPRI", new ArrayList());
       db.execUpd("delete", "delete from GT_PRI_DTL ", new ArrayList());
       db.setAutoCommit(false);
        String prpSplit = info.getPrpSplit();
        try {
            
          
          
            HashMap mprp = null, prp = null;
            ArrayList bsePrp = null, disPrp = null, refPrp = null, params =
                null, outParams = null;

            String grid_vlu, sub, flg, grpNme, defnGrpNme, matIdn, gridChg;
            matIdn = util.nvl((String)udf.getValue("matIdn"));
            double gridChgVlu = 0;
            ArrayList commonList =
                (ArrayList)session.getAttribute("commonList" +
                                                matIdn); // contains column names
            String IsManual = util.nvl(req.getParameter("IsManual" + matIdn));
            ArrayList rowList =
                (ArrayList)session.getAttribute("rowList" + matIdn);
            mprp = info.getMprp();
            prp = info.getPrp();
            CallableStatement cst = null;
            int ct = 0;
            bsePrp = info.getPrcBsePrp();
            disPrp = info.getPrcDisPrp();
            refPrp = info.getPrcRefPrp();
            grpNme = util.nvl((String)udf.getValue("grpNme"));

            if (addRow.equals("")) {
                HashMap grpDtls = initGrpDtls(req,grpNme);
                info.setGrpDtls(grpDtls);
                flg = util.nvl((String)grpDtls.get(grpNme + "T"));
                String matNme = "NA";


                String matNmeSql = "select prmnme from mpri_hdr where idn = ?";
                params = new ArrayList();
                params.add(matIdn);
              ArrayList  rsList = db.execSqlLst("matNme", matNmeSql, params);
               PreparedStatement pst=(PreparedStatement)rsList.get(0);
               ResultSet  rs = (ResultSet)rsList.get(1);
                if (rs.next()) {
                    matNme = rs.getString("prmnme");
                }
                rs.close();
                pst.close();


                Boolean modMatNme = Boolean.TRUE;


                String dyn_mst_t =
                    "PRC_DATA_PKG.INS_DYN_MST(pNme => ?, pFlg =>?, pProp => ?, pRem => ?  " +
                    "  , PIdn => ? , lMatNme => ? ,  pSrt=> ? )";
                params = new ArrayList();
                params.add(matNme);
                params.add(flg);
                params.add(flg);
                params.add(grpNme);
                outParams = new ArrayList();
                outParams.add("I");
                outParams.add("V");
                outParams.add("I");
                cst = db.execCall("dyn_mst_t", dyn_mst_t, params, outParams);
                int dyn_mdt_idn = cst.getInt(params.size() + 1);
                matNme = cst.getString(params.size() + 2);
                int mSrt = cst.getInt(params.size() + 3);
              cst.close();
              cst=null;
                if (modMatNme.booleanValue())
                    matNme = "";
                for (int i = 0; i < commonList.size(); i++) {

                    String lprp = util.nvl((String)commonList.get(i));
                    String fld1 = lprp + "_1";
                    String fld2 = lprp + "_2";
                    String val1 = util.nvl((String)udf.getValue(fld1));
                    String val2 = util.nvl((String)udf.getValue(fld2));
                    if (modMatNme.booleanValue())
                        matNme += val1 + "_";
                    if (!val1.equals("") && !val1.equals("0")) {
                        if (val2.equals("") || val2.equals("0"))
                            val2 = val1;
                        String DYN_CMN_T =
                            "PRC_DATA_PKG.INS_DYN_CMN(pIdn => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
                        params = new ArrayList();
                        params.add(String.valueOf(dyn_mdt_idn));
                        params.add(lprp);
                        params.add(val1);
                        params.add(val2);
                        ct = db.execCall("dyn_cmn_t", DYN_CMN_T, params);

                    }
                }

                if (modMatNme.booleanValue()) {
                    matNme += dyn_mdt_idn;
                    String updMatNme =
                        " update mpri_hdr set prmnme = ? where idn = ? ";
                    ArrayList ary = new ArrayList();
                    ary.add(matNme);
                    ary.add(String.valueOf(dyn_mdt_idn));
                    ct = db.execUpd("upd mat nme", updMatNme, ary);
                }

                ArrayList keysList =
                    (ArrayList)session.getAttribute("KeyList" + matIdn);
                HashMap cellKeyMap =
                    (HashMap)session.getAttribute("cellKeyMap" + matIdn);
                String srt = "";
                for (int i = 0; i < keysList.size(); i++) {
                    srt = String.valueOf(i + 1);
                    int rowNo = 0;
                    int colNo = 0;
                    String fldKey = (String)keysList.get(i);
                    String fldVal = util.nvl((String)udf.getValue(fldKey));
                    if (fldVal.equals(""))
                        fldVal = util.nvl(req.getParameter(fldKey));
                    if (fldVal.length() > 0) {
                        if (IsManual.equals("Y")) {
                            rowNo = (Integer)cellKeyMap.get(fldKey + "_ROW");
                            colNo = (Integer)cellKeyMap.get(fldKey + "_COL");
                            String[] flds = fldKey.split("~");
                            fldKey = "";
                            if (flds.length > 0) {
                                for (int j = 0; j < flds.length; j++) {
                                    String fldNme = flds[j];
                                    if (!fldNme.equals("0")) {
                                        String fldNmeVal =
                                            util.nvl(req.getParameter(fldNme));
                                        if (fldKey.equals(""))
                                            fldKey = fldNmeVal;
                                        else
                                            fldKey +=prpSplit+ fldNmeVal;
                                    }
                                }
                            }
                        }
                              GT_MPRIInsert(req,fldKey, fldVal, srt, rowNo, colNo,flg,
                                      dyn_mdt_idn, grpNme, matNme, matIdn);
                    }

                }
                ArrayList ary = new ArrayList();
                ary.add(String.valueOf(dyn_mdt_idn));
                ct = db.execCall("Ins_actual", "PRC_DATA_PKG.INS_ACTUAL(pHdrIdn=>?)", ary);

             
              
             

                if (ct > 0 && dyn_mdt_idn!=0) {
                db.doCommit();
                    req.setAttribute("msg",
                                     "Price save with Name : " + matNme);
                    if (!util.nvl((String)udf.getValue("modify")).equals("")) {
                        req.setAttribute("msg",
                                         "Price Modify with Name : " + matNme);

                    }
                  if (!util.nvl((String)udf.getValue("addwithNew")).equals("")) {
                      req.setAttribute("msg",
                                       "Price Added Modify with Name : " + matNme);

                  }
                  if (util.nvl((String)udf.getValue("addwithNew")).equals("")) {
                  String delMatrix = " prc_data_pkg.del_matrix_idn(?) ";
                  params = new ArrayList();
                  params.add(matIdn);

                  db.execCall(" Del Mat", delMatrix, params);
                  }
                 String srchId = (String)udf.getValue("srchID");
                udf.reset();
                 udf.setValue("srchID",srchId);
                  req.setAttribute("srchID",srchId);
                udf.setValue("grpNme", grpNme);
                udf.setValue("matNme", matNme);
                udf.setValue("matIdn", String.valueOf(dyn_mdt_idn));
                if (IsManual.equals("Y"))
                    loadDateMN(dyn_mdt_idn, req, udf);
                else
                    loadDate(req,dyn_mdt_idn, udf);

                ArrayList refList =
                    (ArrayList)session.getAttribute("refList" + matIdn);
                session.setAttribute("refList" + dyn_mdt_idn, refList);
                session.setAttribute("rowList" + dyn_mdt_idn, rowList);
                ArrayList columList =
                    (ArrayList)session.getAttribute("columList" + matIdn);
                session.setAttribute("columList" + dyn_mdt_idn, columList);
                session.setAttribute("commonList" + dyn_mdt_idn, commonList);
                HashMap prpMaps =
                    (HashMap)session.getAttribute("prpMaps" + matIdn);
                session.setAttribute("prpMaps" + dyn_mdt_idn, prpMaps);
                String columnPrpSize =
                    (String)session.getAttribute("columnPrpSize" + matIdn);
                session.setAttribute("columnPrpSize" + dyn_mdt_idn,
                                     columnPrpSize);
                String rowPrpSize =
                    (String)session.getAttribute("rowPrpSize" + matIdn);
                session.setAttribute("rowPrpSize" + dyn_mdt_idn, rowPrpSize);

                session.setAttribute("IsManual" + dyn_mdt_idn, IsManual);


//                HashMap MNDataMap =
//                    (HashMap)session.getAttribute("MNDataMap" + matIdn);
                  HashMap MNDataMap =
                    (HashMap)session.getAttribute("MNDataMap" + dyn_mdt_idn);
                session.setAttribute("MNDataMap" + dyn_mdt_idn, MNDataMap);
                req.setAttribute("matIdn", String.valueOf(dyn_mdt_idn));
                req.setAttribute("load", "Y");
              } else {
                  req.setAttribute("msg", "Error in Saving...");
                req.setAttribute("matIdn", matIdn);
                    
              }
            } else {
                loadDateMN(Integer.parseInt(matIdn), req, udf);
                udf.setValue("grpNme", grpNme);
                udf.setValue("matIdn", matIdn);
                req.setAttribute("matIdn", matIdn);
            }
          req.setAttribute("grpNme",grpNme);
            
        } catch (SQLException sqle) {
            // TODO: Add catch code
           db.doRollBack();
        } catch (NumberFormatException nfe) {
            // TODO: Add catch code
            nfe.printStackTrace();
            } finally{
              db.setAutoCommit(true);
            }
         
      
           util.updAccessLog(req,res,"PriceGPMatrix", "save end");
      return am.findForward("priceGrpMatrix");
         }
     }
    
  
    public int GT_MPRIInsert(HttpServletRequest req, String fldKey , String fldVal , String srt ,int rowNo , int colNo,String typ, int dyn_mdt_idn , String grpNme , String matNme , String matIdn){
       
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
        String prpSplit = info.getPrpSplit();
        String insertGT_MPRI = " insert into GT_MPRI(hdr_idn , srt ,pri_grp , prmtyp ,prmnme , pct , row_no , col_no) values( ?, ? , ? , ? , ? , ?, ? ,?) ";
        
         if(typ.equals("DP")){
             insertGT_MPRI = " insert into GT_MPRI(hdr_idn , srt ,pri_grp , prmtyp ,prmnme , vlu , row_no , col_no ) values( ?, ? , ? , ? , ? , ?,?,?) ";
             
         }
         ary.add(String.valueOf(dyn_mdt_idn)) ;
         ary.add(srt) ;
         ary.add(grpNme);
         ary.add(typ);
         ary.add(matNme);
         ary.add(fldVal);
         ary.add(String.valueOf(rowNo));
         ary.add(String.valueOf(colNo));
         int ct = db.execDirUpd("insert Gt_MPRI", insertGT_MPRI ,ary);
         
        ArrayList rowList = (ArrayList)session.getAttribute("rowList"+matIdn);
        ArrayList columList = (ArrayList)session.getAttribute("columList"+matIdn);
        String[] flds = fldKey.split(prpSplit);
        int fldLnt =0 ;
        if(flds.length > 0){
           
            
            if(columList!=null && columList.size()>0){
            for(int i=0 ; i < columList.size() ; i++){
                String lprp = (String)columList.get(i);
                String lprpVal = flds[fldLnt];
                String valFr = lprpVal;
                String valTo = lprpVal;
                if((lprpVal.indexOf("~"))!=-1){
                        String[] lprpLst = lprpVal.split("~");
                        valFr = lprpLst[0];
                        valTo = lprpLst[1];
                        
                 }
                String insertGT_PRI_DTL = " insert into GT_PRI_DTL(srt , mprp , vfr ,vto) values( ?, ? , ? , ? ) ";
                ary = new ArrayList();
                ary.add(srt);
                ary.add(lprp);
                ary.add(valFr);
                ary.add(valTo);
                System.out.println("pri_dtl:"+ary.toString());
                ct = db.execDirUpd("insert GT_PRI", insertGT_PRI_DTL, ary);
                fldLnt++;
            }}
            if(rowList!=null && rowList.size()>0){
            for(int i=0 ; i < rowList.size() ; i++){
                String lprp = (String)rowList.get(i);
                String lprpVal = flds[fldLnt];
                String valFr = lprpVal;
                String valTo = lprpVal;
                if((lprpVal.indexOf("~"))!=-1){
                        String[] lprpLst = lprpVal.split("~");
                        valFr = lprpLst[0];
                        valTo = lprpLst[1];
                        
                 }
                String insertGT_PRI_DTL = " insert into GT_PRI_DTL(srt , mprp , vfr ,vto) values( ?, ? , ? , ? ) ";
                ary = new ArrayList();
                ary.add(srt);
                ary.add(lprp);
                ary.add(valFr);
                ary.add(valTo);
                System.out.println("pri_dtl:"+ary.toString());
                
                ct = db.execDirUpd("insert GT_PRI", insertGT_PRI_DTL,ary);
                fldLnt++;
            }}
        }
        return ct; 
        
    }
    
    public void loadDateMN(int hdrIdn ,  HttpServletRequest req ,PriceGPMatrixForm form){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      
        HashMap MNDataMap = new HashMap();
        String rowCnt ="0";
        String maxRow = "select max(row_no) rowCol from mpri where srt=?";
        ArrayList ary = new ArrayList();
        ary.add(String.valueOf(hdrIdn));
        ArrayList rsLst = db.execSqlLst("maxRow", maxRow, ary);
        PreparedStatement stmt = (PreparedStatement)rsLst.get(0);
        ResultSet rs = (ResultSet)rsLst.get(1);
        try {
            if (rs.next()) {
            rowCnt = util.nvl(rs.getString("rowCol")).trim();
               
            req.setAttribute("rowCnt", rowCnt);
            }
            rs.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        String fetchSql = "select c.idn, d.typ, b.mprp, c.row_no , c.col_no , decode(d.dsp_flg, 'I', decode(a.dta_typ, 'C', val_fr, num_fr), decode(a.dta_typ, 'C', val_fr, num_fr)||'~'||decode(a.dta_typ, 'C', val_to, num_to)) val , nvl(c.vlu , c.pct) fldval " + 
        "from mprp a, pri_dtl b, mpri c, pri_grp_prp d  " + 
        "where a.prp = b.mprp and b.mpri_idn = c.idn and c.pri_grp = d.nme " + 
        "and a.prp = d.mprp and c.srt = ? and typ in ('ROW','COL') " + 
        " order by c.idn, d.typ asc, d.srt";
        ary = new ArrayList();
        ary.add(String.valueOf(hdrIdn));
         rsLst = db.execSqlLst("fetchDate", fetchSql, ary);
        stmt = (PreparedStatement)rsLst.get(0);
        rs = (ResultSet)rsLst.get(1);
        String pVal ="";
        int pIdn =0;
        int rowcnt=1;
        int colCnt=0;
        String Val = "";
        String fldKey = "";
        try {
            while (rs.next()) {
               
                int lIdn = rs.getInt("idn");
               String lVal = util.nvl(rs.getString("fldval"));
                if(pIdn==0){
                    pIdn = lIdn;
                    pVal = lVal;
                }
             if(pIdn!=lIdn)  {
                 
                MNDataMap.put(fldKey, pVal);
               
                pVal = lVal;
                 pIdn = lIdn;
                fldKey="";
                }
              String typ = rs.getString("typ");
              String lprp = rs.getString("mprp");
             String lprpVal = rs.getString("val");
            
              
                String rowVal = util.nvl(rs.getString("row_no"));
                String colVal = util.nvl(rs.getString("col_no"));
               
             String lprpKey = lprp+colVal;
                if(typ.equals("COL"))
                    lprpKey = lprp+rowVal;
             if(fldKey.equals("")){
                fldKey=lprpKey;
               
                }else
              fldKey+="~"+lprpKey;
            MNDataMap.put(lprpKey, lprpVal);
             
            }
            rs.close();
            if(!pVal.equals("")){
             MNDataMap.put(fldKey, pVal);
            }
            stmt.close();
            rs.close();
            session.setAttribute("MNDataMap"+hdrIdn, MNDataMap);
            String prmnme = "";
            String commonPrpSql = "select a.idn, a.prmnme, b.mprp, decode(c.dta_typ, 'C', b.val_fr, to_char(b.num_fr)) val_fr, decode(c.dta_typ, 'C', b.val_to, to_char(b.num_to)) val_to " + 
            "from mpri_hdr a, pri_cmn b, mprp c, pri_grp_prp d " + 
            "where a.idn = b.hdr_idn and b.mprp = c.prp " + 
            "and b.mprp = d.mprp and a.pri_grp = d.nme and d.typ = 'CMN' " + 
            "and a.idn = ? " + 
            "order by grp_srt, a.idn, d.srt ";
            ary = new ArrayList();
            ary.add(String.valueOf(hdrIdn));
            rsLst = db.execSqlLst("coomonSql", commonPrpSql, ary);
            stmt = (PreparedStatement)rsLst.get(0);
            rs = (ResultSet)rsLst.get(1);
            while(rs.next()){
                String mprp = rs.getString("mprp");
                String valFr = rs.getString("val_fr");
                String valTo = rs.getString("val_to");
                 prmnme = rs.getString("prmnme");
                form.setValue(mprp+"_1", valFr);
                form.setValue(mprp+"_2", valTo);
               
            }
            rs.close();
            form.setValue("sheetNme", prmnme);
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        
    }
    
    public void loadDate(HttpServletRequest req, int hdrIdn ,PriceGPMatrixForm form){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      String prpSplit = info.getPrpSplit();
        HashMap excelMap = new HashMap();
        String fetchSql ="select c.idn, d.typ, b.mprp ,  " + 
      
        "decode(d.dsp_flg, 'I', decode(a.dta_typ, 'C', val_fr, trim(to_char(num_fr, a.fmt))), " + 
        "decode(a.dta_typ, 'C', val_fr, trim(to_char(num_fr,a.fmt))) " + 
        "||'~'|| " + 
        "decode(a.dta_typ, 'C', val_to, trim(to_char(num_to, a.fmt)))) val " + 
        ", nvl(c.vlu , c.pct) fldval " + 
        "from mprp a, pri_dtl b, mpri c, pri_grp_prp d where a.prp = b.mprp and b.mpri_idn = c.idn " + 
        "and c.pri_grp = d.nme and a.prp = d.mprp and c.srt = ? and typ in ('ROW','COL') order by c.idn, d.typ asc, d.srt ";
        ArrayList ary = new ArrayList();
        ary.add(String.valueOf(hdrIdn));
        ArrayList rsLst = db.execSqlLst("fetchDate", fetchSql, ary);
        PreparedStatement stmt = (PreparedStatement)rsLst.get(0);
        ResultSet rs = (ResultSet)rsLst.get(1);
        int pIdn = 0;
        String Val = "";
        String fldKey = "";
        try {
            while (rs.next()) {
               int lIdn = rs.getInt("idn");
                if(pIdn==0)
                    pIdn = lIdn;
             if(pIdn!=lIdn)  {
                form.setValue(fldKey, Val);
                excelMap.put(fldKey, Val);
                fldKey = "";
                pIdn = lIdn;
                }
             Val = rs.getString("fldval");
             String lprpVal = util.nvl(rs.getString("val"));
             if(fldKey.length() > 0)
                 fldKey = fldKey+prpSplit+lprpVal;
             else
                 fldKey = lprpVal;
            }
            rs.close();
            if(!fldKey.equals("")){
                form.setValue(fldKey, Val);
                excelMap.put(fldKey, Val);
            }
            stmt.close();
            rs.close();
            String prmnme = "";
            String commonPrpSql = "select a.idn, a.prmnme, b.mprp, decode(c.dta_typ, 'C', b.val_fr, to_char(b.num_fr)) val_fr, decode(c.dta_typ, 'C', b.val_to, to_char(b.num_to)) val_to " + 
            "from mpri_hdr a, pri_cmn b, mprp c, pri_grp_prp d " + 
            "where a.idn = b.hdr_idn and b.mprp = c.prp " + 
            "and b.mprp = d.mprp and a.pri_grp = d.nme and d.typ = 'CMN' " + 
            "and a.idn = ? " + 
            "order by grp_srt, a.idn, d.srt ";
            ary = new ArrayList();
            ary.add(String.valueOf(hdrIdn));
            rsLst = db.execSqlLst("coomonSql", commonPrpSql, ary);
            stmt = (PreparedStatement)rsLst.get(0);
            rs = (ResultSet)rsLst.get(1);
            while(rs.next()){
                String mprp = rs.getString("mprp");
                String valFr = rs.getString("val_fr");
                String valTo = rs.getString("val_to");
                 prmnme = rs.getString("prmnme");
                form.setValue(mprp+"_1", valFr);
                form.setValue(mprp+"_2", valTo);
               
            }
            rs.close();
            form.setValue("sheetNme", prmnme);
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        session.setAttribute("EXCELMAP"+hdrIdn, excelMap);
        
    }
    
   
    public ActionForward edit(ActionMapping am, ActionForm af, HttpServletRequest req,
                                  HttpServletResponse res) throws Exception {
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
          util.updAccessLog(req,res,"PriceGPMatrix", "edit");
          PriceGPMatrixForm  udf = (PriceGPMatrixForm)af;
        String grpNme = req.getParameter("grpNme");
        if(grpNme==null)
        grpNme =(String)udf.getValue("grpNme");
         String srchID = util.nvl(req.getParameter("srchID"));
          String idnstrQury ="";
          if(!srchID.equals("") && !srchID.equals("null")){
              ArrayList matIdnList = new ArrayList();
              String srchQ =
                  " select a1.prmnme nme, a1.idn idn, a1.pri_grp, count(*)\n" + 
                  "  from mpri_hdr a1, pri_cmn a, gt_pri_srch b,\n" + 
                  "  ( select * from gt_pri_srch_dtl c\n" + 
                  " where c.srch_id = ? and c.flg = 'R'\n" + 
                  "  and not exists (select 1 from gt_pri_srch_dtl_sub c1 where c.srch_id = c1.srch_id and c.mprp = c1.mprp)\n" + 
                  " union\n" + 
                  " select * from\n" + 
                  " gt_pri_srch_dtl_sub d\n" + 
                  " where d.srch_id = ?\n" + 
                  " ) e " + 
                  "  where a.hdr_idn = a1.idn and e.srch_id = b.srch_id and a.mprp = e.mprp and a1.stt in ('A','MOD')\n" + 
                  "  and (e.sfr between a.srt_fr and a.srt_to\n" + 
                  "  or e.sto between a.srt_fr and a.srt_to)\n" + 
                  "  and b.srch_id= ? and a1.pri_grp= ?\n" + 
                  "  having count(*) = b.dtl_cnt\n" + 
                  "  group by b.dtl_cnt, a1.idn, a1.pri_grp, a1.prmnme, a1.grp_srt\n" + 
                  " Order By A1.Pri_Grp, A1.Grp_Srt, A1.Idn";
             ArrayList params = new ArrayList();
              params.add(srchID);
              params.add(srchID);
              params.add(srchID);
              params.add(grpNme);
              util.SOP(params.toString());
            ArrayList  rsList = db.execSqlLst(" Srch Grps", srchQ, params);
             PreparedStatement pst=(PreparedStatement)rsList.get(0);
             ResultSet  rs = (ResultSet)rsList.get(1);
            
              while(rs.next()) {
                String idn = rs.getString("idn");
                 matIdnList.add(idn);
              }
              rs.close();
              pst.close();
              String idnPcs="";
              if(matIdnList.size() > 0) {
              idnPcs = matIdnList.toString();
              idnPcs = idnPcs.replace('[','(');
              idnPcs = idnPcs.replace(']',')');
                  idnstrQury=" and a.idn in "+idnPcs ;
              }
              
          }
            
        
        udf.reset();
      
        ArrayList grpSrchList =PRIGenricSrch(req,grpNme);
        info.setGncPrpLst(grpSrchList);
        String grpNmeSql = "select a.idn,a.PRMTYP, a.prmnme, to_char( a.dte,'dd-mm-yyyy HH24:mi:ss') dte , b.mprp, decode(c.dta_typ, 'C', b.val_fr, to_char(b.num_fr)) val_fr, " +
        " decode(c.dta_typ, 'C', b.val_to, to_char(b.num_to)) val_to "+
         " from mpri_hdr a, pri_cmn b, mprp c, pri_grp_prp d "+
         " where a.idn = b.hdr_idn and b.mprp = c.prp "+
        " and b.mprp = d.mprp and a.pri_grp = d.nme and d.typ = 'CMN' "+
         " and a.pri_grp = ? and a.stt in ('A','MOD') and a.vld_upto is null "+idnstrQury+
        " order by grp_srt, a.idn, d.srt ";
        ArrayList ary = new ArrayList();
        ary.add(grpNme);
        ArrayList rsLst = db.execSqlLst("grpNme", grpNmeSql, ary);
        PreparedStatement stmt =(PreparedStatement)rsLst.get(0);
        ResultSet rs =(ResultSet)rsLst.get(1);
        ArrayList grpDtlList = new ArrayList();
        while(rs.next()){
            String idn = rs.getString("idn");
            String prmNme = rs.getString("prmnme");
            String mprp = rs.getString("mprp");
            String val_fr = rs.getString("val_fr");
            String val_to = rs.getString("val_to");
            String dte = rs.getString("dte");
            String PRMTYP = rs.getString("PRMTYP");
            HashMap grpDtl = new HashMap();
            grpDtl.put("idn" , idn);
            grpDtl.put("nme",prmNme);
            grpDtl.put("mprp",mprp);
            grpDtl.put("valFr",val_fr);
            grpDtl.put("valTo",val_to);
            grpDtl.put("dte",dte);
            grpDtl.put("PRMTYP",PRMTYP);
            grpDtlList.add(grpDtl);
        }
        rs.close();
        stmt.close();
        req.setAttribute("grpDtlList", grpDtlList);
        udf.setValue("grpNme", grpNme);
        udf.setValue("srchID", srchID);
        req.setAttribute("grpNme", grpNme);
            req.setAttribute("srchID", srchID);
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("PRICE_MATRIX");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("PRICE_MATRIX");
            allPageDtl.put("PRICE_MATRIX",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            
          util.updAccessLog(req,res,"PriceGPMatrix", "edit end");
        return am.findForward("sheets");
        }
    }
    public ActionForward srch(ActionMapping am, ActionForm af, HttpServletRequest req,
                                  HttpServletResponse res) throws Exception {
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
          util.updAccessLog(req,res,"PriceGPMatrix", "srch");
          PriceGPMatrixForm  udf = (PriceGPMatrixForm)af;
        udf = (PriceGPMatrixForm)af;
        String grpNme = req.getParameter("grpNme");
        if(grpNme==null)
        grpNme =(String)udf.getValue("grpNme");
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("PRICE_MATRIX");
        ArrayList pageList=new ArrayList();
         HashMap pageDtlMap=new HashMap();
            String lvl1="SHAPE";
            String lvl2="SIZE";
            String lvl3="CUT";
            String baseGrpnme="BSE_DIS";
         String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
         pageList= ((ArrayList)pageDtl.get("LVLPRP_"+grpNme) == null)?new ArrayList():(ArrayList)pageDtl.get("LVLPRP_"+grpNme);
        if(pageList!=null && pageList.size() >0){
             HashMap lvlPrpMap=(HashMap)pageList.get(0);
              dflt_val = (String)lvlPrpMap.get("dflt_val");
              String[] lvlLst = dflt_val.split(",");
              lvl1=(String)lvlLst[0];
              lvl2=(String)lvlLst[1];
              lvl3=(String)lvlLst[2];
        }
            HashMap   mprp    = null,
                        prp     = null;
            mprp   = info.getMprp();
            prp    = info.getPrp();
          ArrayList lvl1PrpList = (ArrayList)prp.get(lvl1+"V");
          ArrayList lvl3PrpList = (ArrayList)prp.get(lvl3+"V");
            pageList= ((ArrayList)pageDtl.get("BSESHEET") == null)?new ArrayList():(ArrayList)pageDtl.get("BSESHEET");
            if(pageList!=null && pageList.size() >0){
                HashMap lvlPrpMap=(HashMap)pageList.get(0);
                baseGrpnme = (String)lvlPrpMap.get("dflt_val");
            }
          
        String dwnload = util.nvl((String)udf.getValue("downloadConsolied"));
        ArrayList      params  = null;
       
        ResultSet   rs      = null;
//        ArrayList    grpPrp          = util.getGrpPrp(grpNme);
        Random    randomGenerator = new Random();
        String    delDtlQ         = " delete from gt_pri_srch_dtl";
        String    delQ            = " delete from gt_pri_srch";

        db.execUpd("del gt dtl", delDtlQ, new ArrayList());
        db.execUpd("del gt dtl", delQ, new ArrayList());

        int srchId = 0;

        srchId = randomGenerator.nextInt(100);

        // srchId = randomGenerator().
            String insGTSrchQ = " insert into gt_pri_srch(srch_id, mdl) values(?, 'PRC') ";

        params = new ArrayList();
        params.add(Integer.toString(srchId));

        int    ct  = db.execUpd(" GT Srch", insGTSrchQ, params);
        int matIdn    = 0,
            matSrt    = 0,
            prmDisIdn = 0;
            ArrayList matIdnList = new ArrayList();
        try {
            String matNme = "";

                if(srchId > 0) {
                    int cnt=0;
                    ArrayList gncPrpLst = info.getGncPrpLst();
                    ArrayList prplist=null;
                    String addSrchDtl = "insert into gt_pri_srch_dtl(srch_id, mprp, vfr, vto) values(?,?,?,?)";
                    String addSrchDtlVal = "insert into gt_pri_srch_dtl(srch_id, mprp, vfr, vto) values(?,?,?,?)";
                    String addSrchDtlSubVal = "insert into gt_pri_srch_dtl_sub(srch_id, mprp, vfr, vto) values(?,?,?,?)";
                      String insSrchDtlQNum =
                          " insert into gt_prc_srch_dtl(srch_id, mprp, vfr, vto, nfr, nto) values(?,?,?,?,?,?) ";
                      
                    for (int i = 0; i < gncPrpLst.size(); i++) {
                    boolean dtlAddedOnce = false ;
                    prplist =(ArrayList)gncPrpLst.get(i);
                    String lprp = (String)prplist.get(0);
                    String flg= (String)prplist.get(1);
                    String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
                    String prpSrt = lprp ;
                    String reqVal1="";
                    String reqVal2="";
                    if(lprpTyp.equals("C")){
                        ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
                        ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
                        for(int j=0; j < lprpS.size(); j++) {
                        String lSrt = (String)lprpS.get(j);
                        String lVal = (String)lprpV.get(j);
                        reqVal1 = util.nvl((String)udf.getValue(lprp + "_" + lVal),"");
                        reqVal2 = util.nvl((String)udf.getValue(lprp + "_" + lVal),"");
                        if((reqVal1.length() != 0) || (reqVal2.length() != 0)) {
                        //ignore no value selected;
                        
                        if(!dtlAddedOnce) {
                        params = new ArrayList();
                        params.add(String.valueOf(srchId));
                        params.add(lprp);
                        params.add(reqVal1);
                        params.add(reqVal2);
                        ct = db.execDirUpd(" SrchDtl ", addSrchDtlVal, params);    
                        dtlAddedOnce = true;
                        }
                        params = new ArrayList();
                        params.add(String.valueOf(srchId));
                        params.add(lprp);
                        params.add(reqVal1);
                        params.add(reqVal2);
                        ct = db.execDirUpd(" SrchDtl ", addSrchDtlSubVal, params);    
                        }
                        }
                    
                       
                      }else{
                      reqVal1 = util.nvl((String)udf.getValue(lprp + "_1"),"");
                      reqVal2 = util.nvl((String)udf.getValue(lprp + "_2"),"");
                      if((reqVal1.length() != 0) || (reqVal2.length() != 0)) {
                        String insSrchDtlQ =
                            " insert into gt_pri_srch_dtl(srch_id, mprp, vfr, vto, nfr, nto) values(?,?,?,?,?,?) ";
                        params = new ArrayList();
                        params.add(Integer.toString(srchId));
                        params.add(lprp);
                        String numFr = "",
                               numTo = "";
                        if (lprpTyp.equals("N")) {
                            numFr = reqVal1;
                            numTo = reqVal2;
                            reqVal1 = "NA";
                            reqVal2 = "NA";
                        }
                        if (lprpTyp.equals("T")) {
                            reqVal2=reqVal1;
                        }
                        params.add(reqVal1);
                        params.add(reqVal2);
                        params.add(numFr);
                        params.add(numTo);
                        ct = db.execDirUpd(" SrchDtl ", insSrchDtlQ, params);  
                      }
                    }
                    }
                    }

            String srchQ =
               "With \n" + 
               "P1 as (\n" + 
               "select a.prmnme nme, a.idn idn, a.pri_grp, a.grp_srt, count(*)\n" + 
               "  from mpri_hdr a, pri_cmn a1\n" + 
               "  , gt_pri_srch b,\n" + 
               "  ( select * from gt_pri_srch_dtl c\n" + 
               " where c.srch_id = ? and c.flg = 'R'\n" + 
               "  and not exists (select 1 from gt_pri_srch_dtl_sub c1 where c.srch_id = c1.srch_id and c.mprp = c1.mprp)\n" + 
               " union\n" + 
               " select * from\n" + 
               " gt_pri_srch_dtl_sub d\n" + 
               " where d.srch_id = ? \n" + 
               " ) e   where 1 = 1 \n" + 
               "    and a.pri_grp= ? and a.stt = 'A'\n" + 
               "    and b.srch_id= ? and e.srch_id = b.srch_id \n" + 
               "    and a.idn = a1.hdr_idn \n" + 
               "    and a1.mprp = e.mprp \n" + 
               "    and e.sfr between a1.srt_fr and a1.srt_to\n" + 
               "  having count(distinct a1.mprp) = b.dtl_cnt\n" + 
               "  group by b.dtl_cnt, a.idn, a.pri_grp, a.prmnme, a.grp_srt\n" + 
               "), \n" + 
               "P2 as (\n" + 
               "select a.prmnme nme, a.idn idn, a.pri_grp, a.grp_srt, count(*)\n" + 
               "  from mpri_hdr a, pri_cmn a1\n" + 
               "  , gt_pri_srch b,\n" + 
               "  ( select * from gt_pri_srch_dtl c\n" + 
               " where c.srch_id = ? and c.flg = 'R'\n" + 
               "  and not exists (select 1 from gt_pri_srch_dtl_sub c1 where c.srch_id = c1.srch_id and c.mprp = c1.mprp)\n" + 
               " union\n" + 
               " select * from\n" + 
               " gt_pri_srch_dtl_sub d\n" + 
               " where d.srch_id = ?\n" + 
               " ) e   where 1 = 1 \n" + 
               "    and a.pri_grp= ? and a.stt = 'A'\n" + 
               "    and b.srch_id= ? and e.srch_id = b.srch_id \n" + 
               "    and a.idn = a1.hdr_idn \n" + 
               "    and a1.mprp = e.mprp \n" + 
               "    and e.sto between a1.srt_fr and a1.srt_to\n" + 
                 "  group by b.dtl_cnt, a.idn, a.pri_grp, a.prmnme, a.grp_srt\n" + 
               "  having count(distinct a1.mprp) = b.dtl_cnt\n" + 
               ")\n" + 
               "select p1.nme, p1.idn idn, p1.pri_grp, p1.grp_srt\n" + 
               "from p1, p2\n" + 
               "where p1.idn = p2.idn\n" + 
               "Order By p1.Pri_Grp, p1.grp_srt, p1.Idn";
            
            params = new ArrayList();
            params.add(Integer.toString(srchId));
            params.add(Integer.toString(srchId));
            params.add(grpNme);
            params.add(Integer.toString(srchId));
            params.add(Integer.toString(srchId));
            params.add(Integer.toString(srchId));
            params.add(grpNme);
            params.add(Integer.toString(srchId));
            util.SOP(params.toString());
            
          ArrayList  rsList = db.execSqlLst(" Srch Grps", srchQ, params);
           PreparedStatement pst=(PreparedStatement)rsList.get(0);
           rs = (ResultSet)rsList.get(1);
            util.SOP(srchQ);
            matIdn = 0;
            matNme = "";

            String prcGrp = "";
           
           while(rs.next()) {
              String idn = rs.getString("idn");
               matIdnList.add(idn);
          }
            rs.close();
            pst.close();
        if(matIdnList.size() > 0) {
        String pIdn="";
        String idnPcs = matIdnList.toString();
        idnPcs = idnPcs.replace('[','(');
        idnPcs = idnPcs.replace(']',')');    
        String grpNmeSql = "select a.idn,a.PRMTYP, a.prmnme, to_char( a.dte,'dd-mm-yyyy HH24:mi:ss') dte , b.mprp, decode(c.dta_typ, 'C', b.val_fr, to_char(b.num_fr)) val_fr, " +
        " decode(c.dta_typ, 'C', b.val_to, to_char(b.num_to)) val_to "+
         " from mpri_hdr a, pri_cmn b, mprp c, pri_grp_prp d "+
         " where a.idn = b.hdr_idn and b.mprp = c.prp "+
        " and b.mprp = d.mprp and a.pri_grp = d.nme and d.typ = 'CMN' "+
         " and a.pri_grp = ? and a.idn in "+idnPcs+
        " order by grp_srt, a.idn, d.srt ";
        ArrayList ary = new ArrayList();
        ary.add(grpNme);
        ArrayList rsLst = db.execSqlLst("grpNme", grpNmeSql, ary);
        PreparedStatement stmt =(PreparedStatement)rsLst.get(0);
        rs =(ResultSet)rsLst.get(1);
        ArrayList grpDtlList = new ArrayList();
        
        String lvl1Val="";
        String lvl2Val="";
        String lvl3Val="";
        String plval1Val="";
        ArrayList lvl1List = new ArrayList();
        ArrayList allSheetHdrIdn = new ArrayList();
        ArrayList HdrIdn = new ArrayList();
        HashMap lvl2Map = new HashMap();
        HashMap lvl3Map = new HashMap();
        HashMap ShapeMherMap = new HashMap();
        String keyFm="";
        String keyTo="";
        String key3Fm="";
        String key3To="";
        String key2="";
        String key="";
        while(rs.next()){
            String idn = rs.getString("idn");
            if(pIdn.equals(""))
                pIdn=idn;
            String prmNme = rs.getString("prmnme");
            String mprpf = rs.getString("mprp");
            String val_fr = rs.getString("val_fr");
            String val_to = rs.getString("val_to");
            String dte = rs.getString("dte");
            HashMap grpDtl = new HashMap();
            grpDtl.put("idn" , idn);
            grpDtl.put("nme",prmNme);
            grpDtl.put("mprp",mprpf);
            grpDtl.put("valFr",val_fr);
            grpDtl.put("valTo",val_to);
            grpDtl.put("dte",dte);
            grpDtl.put("PRMTYP", rs.getString("PRMTYP"));
            grpDtlList.add(grpDtl);
            if(!dwnload.equals("")){
            if(!idn.equals(pIdn)){
                List lvlList = lvl1PrpList.subList(lvl1PrpList.indexOf(keyFm) , lvl1PrpList.indexOf(keyTo)+1);
                for(int i=0;i<lvlList.size();i++){
                String lprpLvlVal = (String)lvlList.get(i);
                List lvl3List = lvl3PrpList.subList(lvl3PrpList.indexOf(key3Fm) , lvl3PrpList.indexOf(key3To)+1);
                for(int j=0;j<lvl3List.size();j++){
                    String lprpLvl3Val = (String)lvl3List.get(j);
                    key = lprpLvlVal+"_"+key2+"_"+lprpLvl3Val;
                    ShapeMherMap.put(key, HdrIdn);
                    
                }
                }
               HdrIdn = new ArrayList();
                pIdn=idn;
                key="";keyFm="";keyTo="";key3Fm="";key3To="";
            }
            if(!HdrIdn.contains(idn))
               HdrIdn.add(idn);
            
          
                if(!allSheetHdrIdn.contains(idn))
                    allSheetHdrIdn.add(idn);
            if(mprpf.equals(lvl1)){
                keyFm=val_fr;
                keyTo=val_to;
                List lvlList = lvl1PrpList.subList(lvl1PrpList.indexOf(val_fr) , lvl1PrpList.indexOf(val_to)+1);
                for(int i=0;i<lvlList.size();i++){
                String lprpLvlVal = (String)lvlList.get(i);
                if(!lvl1List.contains(lprpLvlVal)){
                    lvl1List.add(lprpLvlVal);
                }}
              
             }
            if(mprpf.equals(lvl2)){
                
                List lvlList = lvl1PrpList.subList(lvl1PrpList.indexOf(keyFm) , lvl1PrpList.indexOf(keyTo)+1);
                for(int i=0;i<lvlList.size();i++){
                String lprpLvlVal = (String)lvlList.get(i);
                    ArrayList lvl2Lst = (ArrayList)lvl2Map.get(lprpLvlVal+"_SIZE");
                    if(lvl2Lst==null)
                        lvl2Lst = new ArrayList();
                    lvl2Val = val_fr+"-"+val_to;
                    if(!lvl2Lst.contains(lvl2Val)){
                    lvl2Lst.add(lvl2Val);
                    lvl2Map.put(lprpLvlVal+"_SIZE", lvl2Lst);
                  }
                }
                key2=lvl2Val;
               

               
            }
            if(mprpf.equals(lvl3)){
                key3Fm=val_fr;
                key3To=val_to;
                List lvlList = lvl1PrpList.subList(lvl1PrpList.indexOf(keyFm) , lvl1PrpList.indexOf(keyTo)+1);
                for(int i=0;i<lvlList.size();i++){
                String lprpLvlVal = (String)lvlList.get(i);
                String lprpKey = lprpLvlVal+"_"+key2+"_PRP";
               List lvl3List = lvl3PrpList.subList(lvl3PrpList.indexOf(key3Fm) , lvl3PrpList.indexOf(key3To)+1);
                for(int j=0;j<lvl3List.size();j++){
                    String lprpLvl3Val = (String)lvl3List.get(j);
                    ArrayList lvlval3Lst = (ArrayList)lvl3Map.get(lprpKey);
                    if(lvlval3Lst==null)
                        lvlval3Lst = new ArrayList(); 
                    if(!lvlval3Lst.contains(lprpLvl3Val)){
                    lvlval3Lst.add(lprpLvl3Val);
                     lvl3Map.put(lprpKey, lvlval3Lst);
                    }
                
                }
              }
                
               
              
               
            }
            }
        }
            if(!dwnload.equals("")){
         if(!pIdn.equals("")){
             
              List lvlList = lvl1PrpList.subList(lvl1PrpList.indexOf(keyFm) , lvl1PrpList.indexOf(keyTo)+1);
              for(int i=0;i<lvlList.size();i++){
              String lprpLvlVal = (String)lvlList.get(i);
              List lvl3List = lvl3PrpList.subList(lvl3PrpList.indexOf(key3Fm) , lvl3PrpList.indexOf(key3To)+1);
              for(int j=0;j<lvl3List.size();j++){
                  String lprpLvl3Val = (String)lvl3List.get(j);
                  key = lprpLvlVal+"_"+key2+"_"+lprpLvl3Val;
                  ShapeMherMap.put(key, HdrIdn);
                  
              }
              }
               
          }}
            rs.close();
            stmt.close(); 
            if(!dwnload.equals("")){
           
            ArrayList baseMatIdns = new ArrayList();
            String deletesrchDtlStmt = "delete from gt_pri_srch_dtl where mprp not in ('"+lvl1+"','"+lvl2+"')";
            String deletesrchDtlSubStmt = "delete from gt_pri_srch_dtl_sub where mprp not in ('"+lvl1+"','"+lvl2+"')";
             ct = db.execUpd("gt_pri_srch", deletesrchDtlStmt, new ArrayList());
             ct = db.execUpd("gt_pri_srch", deletesrchDtlSubStmt, new ArrayList());
              ary = new ArrayList();
             ary.add("2");
             ary.add(Integer.toString(srchId));
            ct = db.execUpd("gt_pri_srch", "update gt_pri_srch set dtl_cnt=? where srch_id=?", ary);
            String grpNmesrchQ =
            "With \n" + 
            "P1 as (\n" + 
            "select a.prmnme nme, a.idn idn, a.pri_grp, a.grp_srt, count(*)\n" + 
            "  from mpri_hdr a, pri_cmn a1\n" + 
            "  , gt_pri_srch b,\n" + 
            "  ( select * from gt_pri_srch_dtl c\n" + 
            " where c.srch_id = ? and c.flg = 'R'\n" + 
            "  and not exists (select 1 from gt_pri_srch_dtl_sub c1 where c.srch_id = c1.srch_id and c.mprp = c1.mprp)\n" + 
            " union\n" + 
            " select * from\n" + 
            " gt_pri_srch_dtl_sub d\n" + 
            " where d.srch_id = ? \n" + 
            " ) e   where 1 = 1 \n" + 
            "    and a.pri_grp= ? and a.stt = 'A'\n" + 
            "    and b.srch_id= ? and e.srch_id = b.srch_id \n" + 
            "    and a.idn = a1.hdr_idn \n" + 
            "    and a1.mprp = e.mprp \n" + 
            "    and e.sfr between a1.srt_fr and a1.srt_to\n" + 
            "  having count(distinct a1.mprp) = b.dtl_cnt\n" + 
            "  group by b.dtl_cnt, a.idn, a.pri_grp, a.prmnme, a.grp_srt\n" + 
            "), \n" + 
            "P2 as (\n" + 
            "select a.prmnme nme, a.idn idn, a.pri_grp, a.grp_srt, count(*)\n" + 
            "  from mpri_hdr a, pri_cmn a1\n" + 
            "  , gt_pri_srch b,\n" + 
            "  ( select * from gt_pri_srch_dtl c\n" + 
            " where c.srch_id = ? and c.flg = 'R'\n" + 
            "  and not exists (select 1 from gt_pri_srch_dtl_sub c1 where c.srch_id = c1.srch_id and c.mprp = c1.mprp)\n" + 
            " union\n" + 
            " select * from\n" + 
            " gt_pri_srch_dtl_sub d\n" + 
            " where d.srch_id = ?\n" + 
            " ) e   where 1 = 1 \n" + 
            "    and a.pri_grp= ? and a.stt = 'A'\n" + 
            "    and b.srch_id= ? and e.srch_id = b.srch_id \n" + 
            "    and a.idn = a1.hdr_idn \n" + 
            "    and a1.mprp = e.mprp \n" + 
            "    and e.sto between a1.srt_fr and a1.srt_to\n" + 
              "  group by b.dtl_cnt, a.idn, a.pri_grp, a.prmnme, a.grp_srt\n" + 
            "  having count(distinct a1.mprp) = b.dtl_cnt\n" + 
            ")\n" + 
            "select p1.nme, p1.idn idn, p1.pri_grp, p1.grp_srt\n" + 
            "from p1, p2\n" + 
            "where p1.idn = p2.idn\n" + 
            "Order By p1.Pri_Grp, p1.grp_srt, p1.Idn";
            params = new ArrayList();
            params.add(Integer.toString(srchId));
            params.add(Integer.toString(srchId));
            params.add(baseGrpnme);
            params.add(Integer.toString(srchId));
            params.add(Integer.toString(srchId));
            params.add(Integer.toString(srchId));
            params.add(baseGrpnme);
            params.add(Integer.toString(srchId));
            util.SOP(params.toString());
              rsList = db.execSqlLst(" Srch Grps", grpNmesrchQ, params);
                pst=(PreparedStatement)rsList.get(0);
                rs = (ResultSet)rsList.get(1);
            util.SOP(srchQ);
            matIdn = 0;
            matNme = "";
            while(rs.next()) {
              String idn = rs.getString("idn");
               baseMatIdns.add(idn);
            }
            rs.close();
            pst.close();
            if(baseMatIdns.size()>0){
                 idnPcs = baseMatIdns.toString();
                idnPcs = idnPcs.replace('[','(');
                idnPcs = idnPcs.replace(']',')');   
            String basegrpNmeSql = "select a.idn,a.PRMTYP, a.prmnme, to_char( a.dte,'dd-mm-yyyy HH24:mi:ss') dte , b.mprp, decode(c.dta_typ, 'C', b.val_fr, to_char(b.num_fr)) val_fr, " +
            " decode(c.dta_typ, 'C', b.val_to, to_char(b.num_to)) val_to "+
             " from mpri_hdr a, pri_cmn b, mprp c, pri_grp_prp d "+
             " where a.idn = b.hdr_idn and b.mprp = c.prp "+
            " and b.mprp = d.mprp and a.pri_grp = d.nme and d.typ = 'CMN' "+
             " and a.pri_grp = ? and a.idn in "+idnPcs+
            " order by grp_srt, a.idn, d.srt ";
             ary = new ArrayList();
            ary.add(baseGrpnme);
                pIdn="";
            rsLst = db.execSqlLst("grpNme", basegrpNmeSql, ary);
            stmt =(PreparedStatement)rsLst.get(0);
            rs =(ResultSet)rsLst.get(1);
                 lvl1List = new ArrayList();
                ArrayList lvl2List = new ArrayList();
                HdrIdn = new ArrayList();
            while(rs.next()){
                String idn = rs.getString("idn");
                if(pIdn.equals(""))
                    pIdn=idn;
                String prmNme = rs.getString("prmnme");
                String mprpf = rs.getString("mprp");
                String val_fr = rs.getString("val_fr");
                String val_to = rs.getString("val_to");
                String dte = rs.getString("dte");
               
                if(!idn.equals(pIdn)){
                    List lvlList = lvl1PrpList.subList(lvl1PrpList.indexOf(keyFm) , lvl1PrpList.indexOf(keyTo)+1);
                    for(int i=0;i<lvlList.size();i++){
                    String lprpLvlVal = (String)lvlList.get(i);
                    key=lprpLvlVal+"_"+lvl2Val;
                    ShapeMherMap.put(key, HdrIdn);
                    }
                    HdrIdn = new ArrayList();
                    pIdn=idn;
                    key="";
                }
                if(!HdrIdn.contains(idn))
                    HdrIdn.add(idn);
              if(mprpf.equals(lvl1)){
                  
                     keyFm=val_fr;
                     keyTo=val_to;
                     List lvlList = lvl1PrpList.subList(lvl1PrpList.indexOf(val_fr) , lvl1PrpList.indexOf(val_to)+1);
                     for(int i=0;i<lvlList.size();i++){
                     String lprpLvlVal = (String)lvlList.get(i);
                     if(!lvl1List.contains(lprpLvlVal)){
                         lvl1List.add(lprpLvlVal);
                     }}
                  
                    
                   
                 }
                if(mprpf.equals(lvl2)){
                    lvl2Val = val_fr+"-"+val_to;
                    if(!lvl2List.contains(lvl2Val)){
                    lvl2List.add(lvl2Val);
                      
                   
                    }
                    
                }
            }
            rs.close();
            stmt.close();
            }
            if(!pIdn.equals("")){        
                List lvlList = lvl1PrpList.subList(lvl1PrpList.indexOf(keyFm) , lvl1PrpList.indexOf(keyTo)+1);
                for(int i=0;i<lvlList.size();i++){
                String lprpLvlVal = (String)lvlList.get(i);
                key=lprpLvlVal+"_"+lvl2Val;
                ShapeMherMap.put(key, HdrIdn);
                }
            }
             
            allSheetHdrIdn.addAll(baseMatIdns);
            HashMap gridDtl = new HashMap();
            HashMap SheetGridDtl = new HashMap();
         String idnhdrPcs = allSheetHdrIdn.toString();
         idnhdrPcs = idnhdrPcs.replace('[','(');
          idnhdrPcs = idnhdrPcs.replace(']',')'); 
            ArrayList clrList = new ArrayList();
            ArrayList colList = new ArrayList();
            String pmpriIdn="";
         String sheetSql="select p.srt, nvl(p.pct,p.vlu) pct\n" + 
         ", col.val_fr col, clr.val_fr clr\n" + 
         "from mpri p, pri_dtl col, pri_dtl clr\n" + 
         "where 1 = 1 \n" + 
         " and p.idn = col.mpri_idn and col.mprp = 'COL'\n" + 
         " and p.idn = clr.mpri_idn and clr.mprp = 'CLR' \n" + 
         "and p.srt in "+idnhdrPcs+ 
         " order by p.srt ";
          rsLst = db.execSqlLst("sheetSql", sheetSql, new ArrayList());
         stmt =(PreparedStatement)rsLst.get(0);
          rs =(ResultSet)rsLst.get(1);
          while(rs.next()){
              String lmpriIdn = rs.getString("srt");
              if(pmpriIdn.equals(""))
                  pmpriIdn=lmpriIdn;
              if(!pmpriIdn.equals(lmpriIdn)){
                  SheetGridDtl.put(pmpriIdn, gridDtl);
                  pmpriIdn=lmpriIdn;
                  gridDtl = new HashMap();
                      
              }
              String col = rs.getString("col");
              String clr =  rs.getString("clr");
              String pct = rs.getString("pct");
              String srt = rs.getString("srt");
              gridDtl.put(col+"_"+clr, pct);
              if(!colList.contains(col))
                  colList.add(col);
              if(!clrList.contains(clr))
                  clrList.add(clr);
              
          }
                rs.close();
                stmt.close();
          if(!pmpriIdn.equals("")){
              SheetGridDtl.put(pmpriIdn, gridDtl);
          }
          
                      HashMap excelDtlMap = new HashMap();
                      excelDtlMap.put("COLLIST", colList);
                      excelDtlMap.put("CLRLIST", clrList);
                      excelDtlMap.put("LVL1LIST", lvl1List);
                      excelDtlMap.put("LVL2MAP", lvl2Map);
                      excelDtlMap.put("LVL3MAP", lvl3Map);
                      excelDtlMap.put("SHEETHDRMAP", ShapeMherMap);
                      excelDtlMap.put("SHEETGRIDDTL", SheetGridDtl);
                      OutputStream out = res.getOutputStream();
                      String CONTENT_TYPE = "getServletContext()/vnd-excel";
                      String fileNm = "Consolited_"+util.getToDteTime()+".xls";
                      ExcelUtilObj excelUtil = new ExcelUtilObj();
                      excelUtil.init(db, util, info,new GtMgr());
                      HSSFWorkbook hwb = excelUtil.ConsolidatedSheetExcel(req,excelDtlMap);
                      res.setContentType(CONTENT_TYPE);
                      res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
                      hwb.write(out);
                      out.flush();
                      out.close();
            }
         
       
        req.setAttribute("grpDtlList", grpDtlList);
     
        }
        
           
            
        
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }  
        
        udf.reset(); 
        udf.setValue("grpNme", grpNme);
        udf.setValue("srchID", String.valueOf(srchId));
        req.setAttribute("grpNme", grpNme);
          req.setAttribute("srchID", String.valueOf(srchId));
          util.updAccessLog(req,res,"PriceGPMatrix", "srch end");
        return am.findForward("sheets");
        }
    }
    
    
    public HashMap initGrpDtls(HttpServletRequest req, String grpNme) {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
        HashMap grpDtls = new HashMap();
        String getDtls = "select nme , srt , prmtyp  from pri_GRP where nme = ?  ";
       ArrayList ary = new ArrayList();
       ary.add(grpNme);
      ArrayList  rsList = db.execSqlLst("GrpDtl", getDtls, ary);
       PreparedStatement pst=(PreparedStatement)rsList.get(0);
       ResultSet  rs = (ResultSet)rsList.get(1);
        try {
            while(rs.next()) {
                grpNme = rs.getString("nme");
                String grpSrt = util.nvl2(rs.getString("srt"), "0");
                String prmTyp = util.nvl2(rs.getString("prmtyp"), "NR");
               
                grpDtls.put(grpNme+"S", grpSrt);
                grpDtls.put(grpNme+"T", prmTyp);
                
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
        }
        
        return grpDtls;
    }
    
    public HashMap priceGrpDtl(HttpServletRequest req, String grpNme) {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
        HashMap grpDtl = new HashMap();
        ArrayList prpLst = new ArrayList();
        ArrayList ary = new ArrayList();
        ary.add(grpNme);
        String grpdtls = "select nme,mprp,typ,dsp_flg,dsp_all from pri_grp_prp where stt='A' and nme=? order by nme, srt, seq, loc ";
      ArrayList  rsList = db.execSqlLst(" Group dtls", grpdtls, ary);
       PreparedStatement pst=(PreparedStatement)rsList.get(0);
       ResultSet  rs = (ResultSet)rsList.get(1);
        try {
            while (rs.next()) {
                HashMap prpDtl = new HashMap();
                prpDtl.put("grpNme", rs.getString("nme"));
                prpDtl.put("mprp", rs.getString("mprp"));
                prpDtl.put("typ", rs.getString("typ"));
                prpDtl.put("dspflg", rs.getString("dsp_flg"));
                prpDtl.put("dsp_all", rs.getString("dsp_all"));
                grpDtl.put(util.nvl(rs.getString("mprp")), prpDtl);
                prpLst.add(util.nvl(rs.getString("mprp")));
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        grpDtl.put("PRP_LST", prpLst);
        return grpDtl;
    }
    public ActionForward loadfile(ActionMapping am, ActionForm af, HttpServletRequest req,
                                  HttpServletResponse res) throws Exception {
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
           util.updAccessLog(req,res,"PriceGPMatrix", "load file");
        PriceGPMatrixForm  udf = (PriceGPMatrixForm)af;
        udf = (PriceGPMatrixForm)af;
        String isManual = (String)session.getAttribute("IsManual");
        String  matIdn = util.nvl((String)udf.getValue("matIdn"));
         int             ln=0;
        HashMap matDtl = new HashMap();
        FormFile uploadFile = udf.getPriFile();
         if(uploadFile!=null){
         ArrayList ary = new ArrayList();
         String fileName = uploadFile.getFileName();
         fileName = fileName.replaceAll(".csv", util.getToDteTime()+".csv");
         if(!fileName.equals("")){
         String fileTyp = uploadFile.getContentType();
         String path = getServlet().getServletContext().getRealPath("/") + fileName;
             File readFile = new File(path);
             if(!readFile.exists()){

               FileOutputStream fileOutStream = new FileOutputStream(readFile);

               fileOutStream.write(uploadFile.getFileData());

               fileOutStream.flush();

               fileOutStream.close();

               } 

             FileReader       fileReader = new FileReader(readFile);
             LineNumberReader lnr        = new LineNumberReader(fileReader);
             String           line       = "";
             
             while ((line = lnr.readLine()) != null) {
                 ln     = lnr.getLineNumber();
                 StringTokenizer vals   = new StringTokenizer(line, ",");
                 ArrayList          aryLst = new ArrayList();
                 while (vals.hasMoreTokens()) {
                     aryLst.add(vals.nextToken());
                 }
                for (int i = 0; i < aryLst.size(); i++) {
                         matDtl.put(ln-1+ "_" + i, (String) aryLst.get(i));
                }
               
                  
             }

             fileReader.close();
         }
             String prmnme = "";
             String commonPrpSql = "select a.idn, a.prmnme, b.mprp, decode(c.dta_typ, 'C', b.val_fr, to_char(b.num_fr)) val_fr, decode(c.dta_typ, 'C', b.val_to, to_char(b.num_to)) val_to " + 
             "from mpri_hdr a, pri_cmn b, mprp c, pri_grp_prp d " + 
             "where a.idn = b.hdr_idn and b.mprp = c.prp " + 
             "and b.mprp = d.mprp and a.pri_grp = d.nme and d.typ = 'CMN' " + 
             "and a.idn = ? " + 
             "order by grp_srt, a.idn, d.srt ";
             ary = new ArrayList();
             ary.add(String.valueOf(matIdn));
             ArrayList rsLst = db.execSqlLst("coomonSql", commonPrpSql, ary);
             PreparedStatement stmt = (PreparedStatement)rsLst.get(0);
             ResultSet rs = (ResultSet)rsLst.get(1);
             while(rs.next()){
                 String mprp = rs.getString("mprp");
                 String valFr = rs.getString("val_fr");
                 String valTo = rs.getString("val_to");
                  prmnme = rs.getString("prmnme");
                 udf.setValue(mprp+"_1", valFr);
                 udf.setValue(mprp+"_2", valTo);
                
             }
             rs.close();
             stmt.close();
             udf.setValue("sheetNme", prmnme);
         }
         req.setAttribute("srchID", udf.getValue("srchID"));
         req.setAttribute("rowCnt", String.valueOf(ln-1));
         req.setAttribute("matDtl", matDtl);
         req.setAttribute("matIdn", matIdn);
           udf.setValue("grpNme", udf.getValue("grpNme"));
             req.setAttribute("grpNme", udf.getValue("grpNme"));
        return am.findForward("priceGrpMatrix");
         }
     }

    public ActionForward Excel(ActionMapping am, ActionForm af, HttpServletRequest req,
                                  HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
        String loadMatIdn = util.nvl(req.getParameter("matIdn"));
       
        String matNme="";
        String matNmeSql = "select pri_grp||'_'||prmnme nme from mpri_hdr where idn=?";
        ArrayList  params = new ArrayList();
         params.add(loadMatIdn);
      ArrayList  rsList = db.execSqlLst("matNme", matNmeSql, params);
       PreparedStatement pst=(PreparedStatement)rsList.get(0);
       ResultSet  rs = (ResultSet)rsList.get(1);
        try {
            if (rs.next()) {
                matNme = util.nvl(rs.getString("nme")).replaceAll(" ", "");
              
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        PrintWriter out = res.getWriter();
        String fileName = matNme+"_"+util.getToDteTime()+".csv";
        res.setContentType("application/ms-excel");
        res.setHeader("Content-disposition",
                              "attachment; filename="+fileName);
        int row = (Integer)session.getAttribute("EXCELROW"+loadMatIdn);
        int col = (Integer)session.getAttribute("EXCELCOL"+loadMatIdn);
        HashMap matDtl = (HashMap)session.getAttribute("cssFileMap"+loadMatIdn);
      
//        if(col>0){
//            String ln =",";
//            for(int n=0; n < col; n++){
//                String fldVal = util.nvl((String)matDtl.get("COL_"+n));
//                    ln += fldVal+ ",";
//            }
//            out.println(ln);  
//        }
        for(int m=0; m < row; m++){
           String ln ="";
//            String ROWVal = util.nvl((String)matDtl.get("ROW_"+m));
//            if(ROWVal.length()>0)
//                ln +=ROWVal+ ",";
       for(int n=0; n < col; n++){
                String fldVal = util.nvl((String)matDtl.get(m+"_"+n));
                    ln += fldVal+ ",";
        }
            out.println(ln);  
        }
                                                
     return null;
    }
    
    public ArrayList PRIGenricSrch(HttpServletRequest req, String group){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
        ArrayList grpPrp = new ArrayList();
        try {
             
          ArrayList  rsList = db.execSqlLst(" Vw Lst ", "  select a.mprp,'M' flg from pri_grp_prp a,mprp b where  a.mprp=b.prp and a.nme='"+group+"' and a.typ='CMN' order by b.srt",
                               new ArrayList());
           PreparedStatement pst=(PreparedStatement)rsList.get(0);
           ResultSet  rs1 = (ResultSet)rsList.get(1);
                while (rs1.next()) {
                    ArrayList asViewdtl=new ArrayList();
                    asViewdtl.add(rs1.getString("mprp"));
                    asViewdtl.add(rs1.getString("flg"));
                    grpPrp.add(asViewdtl);
                }
            rs1.close();
            pst.close();

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return grpPrp;
    }
    
    public ActionForward SuratFmtExcel(ActionMapping am, ActionForm af, HttpServletRequest req,
                                  HttpServletResponse res) throws Exception {
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
        util.updAccessLog(req,res,"PriceGPMatrix", "SuratFmtExcel");
           PriceGPMatrixForm  udf = (PriceGPMatrixForm)af;
             String grpNme = util.nvl((String)udf.getValue("grpNme"));
            Enumeration reqNme = req.getParameterNames();
           ArrayList mpriIdnLst = new ArrayList();
           while(reqNme.hasMoreElements()) {
               String paramNm = (String)reqNme.nextElement();
               if(paramNm.indexOf("cb") > -1) {
                   String val = req.getParameter(paramNm);
                  
                   String pktNm = val ;//paramNm.substring(paramNm.indexOf("rb_")+3);
                   if(paramNm.equals("cb_mat_"+val)){
                       mpriIdnLst.add(pktNm);
                      
                   }
            }}
           HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
           HashMap pageDtl=(HashMap)allPageDtl.get("PRICE_MATRIX");
              ArrayList pageList=new ArrayList();
              HashMap pageDtlMap=new HashMap();
              String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
           pageList= ((ArrayList)pageDtl.get(grpNme) == null)?new ArrayList():(ArrayList)pageDtl.get(grpNme);
            if(pageList!=null && pageList.size() >0){
             for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                dflt_val=util.nvl((String)pageDtlMap.get("dflt_val"));
                }}
           String[] subVal = dflt_val.split(",");
           if(subVal.length ==2){
           String lprp1=subVal[0];
           String lprp2=subVal[1];
           String mpriIdn =  mpriIdnLst.toString();
           mpriIdn = mpriIdn.replaceAll("\\[","");
           mpriIdn = mpriIdn.replaceAll("\\]","");
               HashMap excelDtlMap = new HashMap();
               int minSrt=0;
               int maxSrt=0;
               String sheetNme="";
         String shapeSQL="select distinct a.pri_grp||'_'||b.val_fr||'_'||b.val_to sheetNme ,b.srt_fr ,b.srt_to from mpri_hdr a , pri_cmn b "+
                         " where a.idn=b.hdr_idn and a.pri_grp=? and a.stt=? and mprp=? and a.idn in ("+mpriIdn+")  order by b.srt_fr ";
           ArrayList params = new ArrayList();
           params.add(grpNme);
           params.add("A");
           params.add("SHAPE");
           ArrayList rsLst = db.execSqlLst("shapeFilter", shapeSQL, params);
           PreparedStatement psmt = (PreparedStatement)rsLst.get(0);
           ResultSet rs = (ResultSet)rsLst.get(1);
           while(rs.next()){
             
                sheetNme = util.nvl(rs.getString("sheetNme"));
               int srt_fr = rs.getInt("srt_fr");
               int srt_to = rs.getInt("srt_to");
               if(minSrt<srt_fr)
                   minSrt=srt_fr;
               if(maxSrt>srt_to)
                   maxSrt=srt_to;
               excelDtlMap.put("SHEETNME", sheetNme);
           }
           rs.close();
           psmt.close();
             String sheetColl= "select p.srt, p.pct pct\n" + 
             ", ct.val_fr ct_fr, ct.val_to ct_to\n" + 
             ", ss.val_fr||'_'||ss.val_to subSize\n" + 
             ", col.val_fr col, clr.val_fr clr\n" + 
             "from mpri p, pri_dtl col, pri_dtl clr, pri_cmn ct, pri_cmn ss\n" + 
             "where 1 = 1 \n" + 
             " and p.srt = ct.hdr_idn and ct.mprp = ? \n" + 
             " and p.srt = ss.hdr_idn and ss.mprp = ? \n" + 
             " and p.idn = col.mpri_idn and col.mprp = ? \n" + 
             " and p.idn = clr.mpri_idn and clr.mprp = ? \n" + 
             "and exists (select 1 from mpri_hdr h, pri_cmn c \n" + 
             " where h.idn = p.srt and h.idn = c.hdr_idn and h.pri_grp = ? and h.stt = ? \n" + 
             " and c.mprp = ? and c.srt_fr > ? and c.srt_to < ? and h.idn in ("+mpriIdn+")  )\n" + 
             "order by ct.srt_fr, ss.srt_fr, col.srt_fr, clr.srt_fr, p.srt";
                 
               params = new ArrayList();
               params.add(lprp1);
               params.add(lprp2);
               params.add("COL");
               params.add("CLR");
               params.add(grpNme);
               params.add("A");
               params.add("SHAPE");
               params.add(String.valueOf(minSrt));
               params.add(String.valueOf(maxSrt));
               
               rsLst = db.execSqlLst("sheetColl", sheetColl, params);
               PreparedStatement psmt1 = (PreparedStatement)rsLst.get(0);
               ResultSet rs1 = (ResultSet)rsLst.get(1);
               String pcutVal = "";
               int psrt=0;
               ArrayList colList=new ArrayList();
               ArrayList rowList=new ArrayList();
               ArrayList hdrLst=new ArrayList();
               ArrayList subSheetList=new ArrayList();
               HashMap excelMap = new HashMap();
               HashMap MainShtMap = new HashMap();
               HashMap subShtMap = new HashMap();
               String subSize="";
               while(rs1.next()){
                   String lcutVal = rs1.getString("ct_fr");
                 
                   int srt = rs1.getInt("srt");
                   if(psrt==0)
                       psrt=srt;
                   if(psrt!=srt){
                       hdrLst.add(String.valueOf(psrt));
                       subShtMap.put(psrt+"_COLLST", colList);
                       subShtMap.put(psrt+"_ROWLST", rowList);
                       subShtMap.put(psrt+"_GRID", excelMap);
                       psrt=srt;
                      colList = new ArrayList();
                       rowList = new ArrayList();
                       excelMap = new HashMap();
                     
                   }
                    if(pcutVal.equals(""))
                          pcutVal=lcutVal;
                    if(!pcutVal.equals(lcutVal)){
                          MainShtMap.put("HDRLST_"+pcutVal, hdrLst);
                          MainShtMap.put("GRIDDTL_"+pcutVal,subShtMap);
                          hdrLst = new ArrayList();
                          subShtMap = new HashMap();
                          subSheetList.add(pcutVal);
                          pcutVal=lcutVal;
                      }
                   subSize =  rs1.getString("subSize");
                   subShtMap.put(srt+"_SUBSIZE", subSize);
                   String col = util.nvl(rs1.getString("col"));
                   String clr = util.nvl(rs1.getString("clr"));
                   String pct = util.nvl(rs1.getString("pct"));
                   excelMap.put(col+"#"+clr, pct);
                   if(colList.indexOf(col)==-1)
                      colList.add(col);
                   if(rowList.indexOf(clr)==-1)
                       rowList.add(clr);
                      
                  }
               rs1.close();
             psmt1.close();
               if(psrt!=0){
                   hdrLst.add(String.valueOf(psrt));
                   subShtMap.put(psrt+"_COLLST", colList);
                   subShtMap.put(psrt+"_ROWLST", rowList);
                   subShtMap.put(psrt+"_GRID", excelMap);
                   subShtMap.put(psrt+"_SUBSIZE", subSize);
               }
               if(!pcutVal.equals("")){
                   MainShtMap.put("HDRLST_"+pcutVal, hdrLst);
                   MainShtMap.put("GRIDDTL_"+pcutVal,subShtMap);
                   subSheetList.add(pcutVal);
                   
               }
               
               excelDtlMap.put("SUBSHEETLST",subSheetList);
               excelDtlMap.put("SUBSHEETDTL",MainShtMap);
               
              
           
          
               OutputStream out = res.getOutputStream();
               String CONTENT_TYPE = "getServletContext()/vnd-excel";
               String fileNm = sheetNme+"_"+util.getToDteTime()+".xls";
               ExcelUtilObj excelUtil = new ExcelUtilObj();
               excelUtil.init(db, util, info,new GtMgr());
               HSSFWorkbook hwb = excelUtil.PriceSheetExcel(req,excelDtlMap);
               res.setContentType(CONTENT_TYPE);
               res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
               hwb.write(out);
               out.flush();
               out.close();
           rs.close();
           psmt.close();
           }
           return null;
       }
      
    }
    
    
    public HashMap GridDate(HttpServletRequest req, int hdrIdn , HashMap subShtMap){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      String prpSplit = "#";
        HashMap excelMap = new HashMap();
        String fetchSql ="select c.idn, d.typ, b.mprp ,  " + 
      
        "decode(d.dsp_flg, 'I', decode(a.dta_typ, 'C', val_fr, trim(to_char(num_fr, a.fmt))), " + 
        "decode(a.dta_typ, 'C', val_fr, trim(to_char(num_fr,a.fmt))) " + 
        "||'~'|| " + 
        "decode(a.dta_typ, 'C', val_to, trim(to_char(num_to, a.fmt)))) val " + 
        ", nvl(c.vlu , c.pct) fldval " + 
        "from mprp a, pri_dtl b, mpri c, pri_grp_prp d where a.prp = b.mprp and b.mpri_idn = c.idn " + 
        "and c.pri_grp = d.nme and a.prp = d.mprp and c.srt = ? and typ in ('ROW','COL') order by c.idn, d.typ asc, d.srt ";
        ArrayList ary = new ArrayList();
        ary.add(String.valueOf(hdrIdn));
        ArrayList rsLst = db.execSqlLst("fetchDate", fetchSql, ary);
        PreparedStatement stmt = (PreparedStatement)rsLst.get(0);
        ResultSet rs = (ResultSet)rsLst.get(1);
        int pIdn = 0;
        String Val = "";
        String fldKey = "";
        ArrayList colList = new ArrayList();
        ArrayList rowList = new ArrayList();
        try {
            
            while (rs.next()) {
                int lIdn = rs.getInt("idn");
                if (pIdn == 0)
                    pIdn = lIdn;
                if (pIdn != lIdn) {

                    excelMap.put(fldKey, Val);
                    fldKey = "";
                    pIdn = lIdn;
                }
                Val = rs.getString("fldval");
                String lprpVal = util.nvl(rs.getString("val"));
                if (fldKey.length() > 0)
                    fldKey = fldKey + prpSplit + lprpVal;
                else
                    fldKey = lprpVal;
                String typ = util.nvl(rs.getString("typ"));
                if(typ.equals("COL")){
                if(colList.indexOf(lprpVal)==-1)
                    colList.add(lprpVal);
                }else{
                    if(rowList.indexOf(lprpVal)==-1)
                    rowList.add(lprpVal);
                }
            }
            rs.close();
            stmt.close();
            if (!fldKey.equals("")) {

                excelMap.put(fldKey, Val);
            }
            subShtMap.put(hdrIdn+"_COLLST", colList);
            subShtMap.put(hdrIdn+"_ROWLST", rowList);
            subShtMap.put(hdrIdn+"_GRID", excelMap);
            
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return subShtMap;
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
          util.updAccessLog(req,res,"PriceGPMatrix", "init");
          }
          }
          return rtnPg;
          }
}
