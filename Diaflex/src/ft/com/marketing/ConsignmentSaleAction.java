package ft.com.marketing;

//~--- non-JDK imports --------------------------------------------------------

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;
import ft.com.MailAction;
import ft.com.dao.ByrDao;
import ft.com.dao.MAddr;
import ft.com.dao.PktDtl;
import ft.com.dao.TrmsDao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import java.io.LineNumberReader;

import java.io.OutputStream;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

//~--- JDK imports ------------------------------------------------------------

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.upload.FormFile;

public class ConsignmentSaleAction extends DispatchAction {
    public ConsignmentSaleAction() {
        super();
    }

    public ActionForward loadSale(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
          util.updAccessLog(req,res,"Consignment Sale", "Consignment Load");
        ConsignmentSaleForm udf = (ConsignmentSaleForm) af;

        ArrayList byrList = new ArrayList();
        String    getByr  =
            "select  distinct byr , nme_idn from cs_pndg_v order by byr";
          ArrayList  outLst = db.execSqlLst("byr", getByr, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);

        while (rs.next()) {
            ByrDao byr = new ByrDao();
            byr.setByrIdn(rs.getInt("nme_idn"));
            byr.setByrNme(rs.getString("byr"));
            byrList.add(byr);
        }
        rs.close();
            pst.close();
//        udf.setValue("byrList", byrList);
        udf.setByrLstFetch(byrList);
        udf.setByrLst(byrList);
        
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("CONSIGNMENT_FORM");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("CONSIGNMENT_FORM");
        allPageDtl.put("CONSIGNMENT_FORM",pageDtl);
        }
        info.setPageDetails(allPageDtl);
          util.updAccessLog(req,res,"Consignment Sale", "Consignment Load Done");
            finalizeObject(db, util);
        return am.findForward("load");
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
          util.updAccessLog(req,res,"Consignment Sale", "Consignment Load Pkt");
        ConsignmentSaleForm udf = (ConsignmentSaleForm) af;
        SearchQuery srchQuery = new SearchQuery();
        ResultSet rs          = null;
        String    view        = "NORMAL";
        ArrayList trmList     = new ArrayList();
        String    memoIds     = "";
        String    pand        = req.getParameter("pnd");
        ArrayList pkts        = new ArrayList();
        String    memoSrchTyp = util.nvl((String) udf.getValue("memoSrch"));
        String    byrIdn      = util.nvl((String)udf.getValue("byrIdn"));
        String    vnmLst      = "";
        FormFile uploadFile = udf.getLoadfile();
        HashMap fileDataMap = new HashMap();
            String dteFilter="";
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("CONSIGNMENT_FORM");
            if(pageDtl!=null && pageDtl.size()>0){
            ArrayList  pageList= ((ArrayList)pageDtl.get("DTEFLT") == null)?new ArrayList():(ArrayList)pageDtl.get("DTEFLT");
            if(pageList!=null && pageList.size() >0){
             HashMap pageDtlMap=(HashMap)pageList.get(0);
             String dflt_val=(String)pageDtlMap.get("dflt_val");
              dteFilter=dflt_val;
            }}
        if (memoSrchTyp.equals("ByrSrch")) {
            Enumeration reqNme = req.getParameterNames();

            while (reqNme.hasMoreElements()) {
                String paramNm = (String) reqNme.nextElement();

                if (paramNm.indexOf("cb_memo") > -1) {
                    String val = req.getParameter(paramNm);

                    if (memoIds.equals("")) {
                        memoIds = val;
                    } else {
                        memoIds = memoIds + "," + val;
                    }
                }
            }
        } else {
            memoIds =String.valueOf((udf.getMemoIdn()));
            vnmLst=util.nvl((String)udf.getValue("vnmLst"));
        }

        if (pand != null) {
            memoIds = util.nvl(req.getParameter("memoId"));
        }
        String app = (String)req.getAttribute("APP");
        if(app!=null)
            memoIds = (String)req.getAttribute("memoId");

            if(!vnmLst.equals("")){
                boolean isRtn = true;
                vnmLst=util.getVnm(vnmLst);
                int cnt=0;
                String checkSql ="select distinct c.nmerel_idn  from jandtl a, mstk b , mjan c " + 
                "where a.mstk_idn = b.idn and c.idn = a.idn and a.stt in('IS','AV') and a.typ='CS' and c.typ like '%CS' " + 
                " and  ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) and b.stt in ('MKCS','BRAV') and nvl(b.prt2,'NA')=decode(nvl(b.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(b.prt2,'NA'))";
              ArrayList  outLst = db.execSqlLst("check rel", checkSql, new ArrayList());
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs1 = (ResultSet)outLst.get(1);

                while(rs1.next()){
                    cnt++;
                }
                rs1.close();
                pst.close();
                if(cnt==1){  
                    isRtn = false;
                    String saleIdSql = "select distinct c.idn  from jandtl a, mstk b , mjan c " + 
                    "  where a.mstk_idn = b.idn and c.idn = a.idn  and a.stt in('IS','AV') and a.typ='CS' and c.typ like '%CS' " + 
                    "  and  ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) and b.stt in ('MKCS','BRAV') and nvl(b.prt2,'NA')=decode(nvl(b.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(b.prt2,'NA'))";
                  outLst = db.execSqlLst("saleID",saleIdSql, new ArrayList());
                  pst = (PreparedStatement)outLst.get(0);
                   rs1 = (ResultSet)outLst.get(1);
                    while(rs1.next()){
                        String appIdnval = util.nvl(rs1.getString("idn"));
                        if (memoIds.equals("")) {
                            memoIds = appIdnval;
                        } else {
                           memoIds = memoIds + "," + appIdnval;
                        }
                    }
                    rs1.close();
                    pst.close();
                }
                if(isRtn){
                    req.setAttribute("RTMSG", "Please verify packets");
                   return loadSale(am, af, req, res);
                }
            }
            if(uploadFile!=null){
            ArrayList dataLst = new ArrayList();
            String fileName = uploadFile.getFileName();
            fileName = fileName.replaceAll(".csv", util.getToDteTime()+".csv");
            if(!fileName.equals("")){
              String path = getServlet().getServletContext().getRealPath("/") + fileName;
            File readFile = new File(path);
            if(!readFile.exists()){
            FileOutputStream fileOutStream = new FileOutputStream(readFile);
            fileOutStream.write(uploadFile.getFileData());
            fileOutStream.flush();
            fileOutStream.close();
            }
            FileReader fileReader = new FileReader(readFile);
            LineNumberReader lnr = new LineNumberReader(fileReader);
            String line = "";
            while ((line = lnr.readLine()) != null){
                int fileCnt = 0;
                String vnm = "";
                String prc = "";
                String dis = "";
                if((line.length() - (line.replaceAll(",","").length())) == 1)
                    line = line + ", ";
                if(line.substring(line.length()-1).equals(","))
                    line = line + " ";
                    String[] vals = line.split(",");
                dataLst.add(line);
                
               //if(vals.length == 2)
                   //vals[2] = "";
               HashMap fileData = new HashMap();
               if(vals.length > 2) {
                    vnm = vals[0];
                    fileData.put("vnm", vnm);
                    if (vnmLst.equals("")) {
                    vnmLst = vnm;
                    } else {
                    vnmLst = vnmLst + "," + vnm;
                    }
                    prc = vals[1];
                    fileData.put("prc", prc);
                    dis = vals[2];
                    try {
                        dis=String.valueOf(Float.parseFloat(dis)*-1);
                    } catch (NumberFormatException nfe) {
                    }
                    fileData.put("dis", dis);
                }
                fileDataMap.put(vnm,fileData);
            }
            fileReader.close();
                vnmLst = util.getVnm(vnmLst);
                udf.setValue("fnlFields", "Y");
            }
            }
        ArrayList params = null;
        memoIds=memoIds.replaceAll("NULL", "");
        memoIds = util.getVnm(memoIds);
      
        int cout = 0;

         ArrayList outLst = db.execSqlLst("check", "select distinct nme_idn from mjan where idn in (" + memoIds + ")", new ArrayList());
       PreparedStatement   pst = (PreparedStatement)outLst.get(0);
        ResultSet   rs1 = (ResultSet)outLst.get(1);
        while (rs1.next()) {
            cout++;
        }
        rs1.close();
         pst.close();
        if (cout < 2) {
            double exh_rte = 0;
            outLst = db.execSqlLst("check", "select max(exh_rte) exhRte , a.nmerel_idn , b.cur " + 
            " from mjan a , nmerel b where idn in ("+memoIds+") " + 
            " and a.nmerel_idn = b.nmerel_idn  group by a.nmerel_idn, b.cur ", new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            while(rs.next()){
               exh_rte = Double.parseDouble(util.nvl(rs.getString("exhRte"),"1"));
               udf.setValue("exhRte", rs.getString("exhRte")); 
               udf.setValue("cur", util.nvl(rs.getString("cur")).toUpperCase());
            }
            rs.close();
            pst.close();
            String getAvgDis = "select sum(a.qty) qty,sum(a.cts) cts,trunc(sum(trunc(a.cts,2)*nvl(a.fnl_sal, a.quot)), 2) vlu,trunc(((sum(trunc(a.cts,2)*(nvl(a.fnl_sal, a.quot)/"+exh_rte+")) / sum(trunc(a.cts,2)*b.rap_rte))*100) - 100, 2) avg_dis ,\n" + 
            "trunc(((sum(trunc(a.cts,2)*nvl(a.fnl_sal, a.quot)) / sum(trunc(a.cts,2))))) avg_Rte from jandtl a, mstk b " + 
                               " where a.mstk_idn = b.idn and a.stt in('IS','AV') " + 
                                " and a.idn in (" + memoIds + ") and b.stt in ('MKCS','BRAV') and nvl(b.prt2,'NA')=decode(nvl(b.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(b.prt2,'NA')) ";
            if(!vnmLst.equals(""))
            getAvgDis = getAvgDis+" and  ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) ";
            params = new ArrayList();
          
            
          outLst = db.execSqlLst(" Memo Info", getAvgDis , params);
          pst = (PreparedStatement)outLst.get(0);
           rs1 = (ResultSet)outLst.get(1);
            if(rs1.next()){
                udf.setAvgDis(rs1.getString("avg_dis"));
                udf.setAvgPrc(rs1.getString("avg_Rte"));
                udf.setQty(rs1.getString("qty"));
                udf.setCts(rs1.getString("cts"));
                udf.setVlu(rs1.getString("vlu"));
            }
            rs1.close();
            pst.close();
            String getMemoInfo =
                " select a.nme_idn, a.nmerel_idn,byr.get_trms(a.nmerel_idn) trms, byr.get_nm(a.nme_idn) byr,a.typ, a.qty, a.cts, a.vlu, to_char(a.dte, 'dd-Mon-rrrr HH24:mi:ss') dte "
                + " from mjan a  where a.idn in (" + memoIds + ") and a.stt <> 'RT' and a.typ like '%CS' ";

            params = new ArrayList();

          outLst = db.execSqlLst(" Memo Info", getMemoInfo, params);
          pst = (PreparedStatement)outLst.get(0);
           rs1 = (ResultSet)outLst.get(1);

            if (rs1.next()) {
                udf.setNmeIdn(rs1.getInt("nme_idn"));
                udf.setRelIdn(rs1.getInt("nmerel_idn"));
                udf.setByr(rs1.getString("byr"));
                udf.setTyp(rs1.getString("typ"));
                udf.setDte(rs1.getString("dte"));
                udf.setValue("trmsLb", rs1.getString("trms"));
                HashMap buyerDtlMap=util.getBuyerDtl(String.valueOf(rs1.getInt("nme_idn")));
                udf.setValue("email",util.nvl((String)buyerDtlMap.get("EML")));
                udf.setValue("mobile",util.nvl((String)buyerDtlMap.get("MBL")));
                udf.setValue("office",util.nvl((String)buyerDtlMap.get("OFC")));
                if (view.equalsIgnoreCase("Normal")) {
                    String getPktData =
                        " select a.idn ,to_char(a.dte, 'dd-Mon-rrrr') dte, a.qty , mstk_idn, nvl(fnl_sal, quot) memoQuot, quot, fnl_sal , b.vnm,b.stt stkstt "
                        + ", a.cts, nvl(b.upr, b.cmp) rte, nvl(b.sk1,1) sk1, b.rap_rte,to_char(trunc(a.cts,2) * b.rap_rte, '99999999990.00') rapVlu, a.stt , to_number(to_char(a.dte, 'rrrrmmdd')) dtenum "
                        + " ,  to_char(decode(rap_rte, 1, '', trunc(((nvl(b.upr, b.cmp)/greatest(b.rap_rte,1))*100)-100,2)),9990.99) dis,  to_char(decode(rap_rte, 1, '', trunc(((nvl(fnl_sal, quot)/greatest(b.rap_rte,1))*100)-100,2)),9990.99) mDis "
                        + " from jandtl a, mstk b where a.mstk_idn = b.idn " + " and a.idn in (" + memoIds
                        + ")  and a.stt in('IS','AV') and a.typ='CS' and nvl(b.prt2,'NA')=decode(nvl(b.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(b.prt2,'NA'))  " + " ";

                    // " and decode(typ, 'I','IS', 'E', 'IS', 'WH', 'IS', 'AP') = stt ";
                    if(!vnmLst.equals("")){
                    getPktData = getPktData + " and ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) ";
                    }
                    getPktData = getPktData + " order by to_number(to_char(a.dte, 'rrrrmmdd')) , b.sk1";
                    params = new ArrayList();
                    
                  ArrayList outLst1 = db.execSqlLst(" memo pkts", getPktData, params);
                 PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
                  ResultSet mrs = (ResultSet)outLst1.get(1);

                    while (mrs.next()) {
                        PktDtl pkt    = new PktDtl();
                        long   pktIdn = mrs.getLong("mstk_idn");
                        String vnm=mrs.getString("vnm");
                        String quot = util.nvl(mrs.getString("quot"));
                        pkt.setPktIdn(pktIdn);
                        pkt.setValue("dte",util.nvl(mrs.getString("dte")));
                        pkt.setMemoId(util.nvl(mrs.getString("idn")));
                        pkt.setRapRte(util.nvl(mrs.getString("rap_rte")));
                        pkt.setQty(util.nvl(mrs.getString("qty")));
                        pkt.setCts(util.nvl(mrs.getString("cts")));
                        pkt.setValue("CRTWT", util.nvl(mrs.getString("cts")));
                        pkt.setRte(util.nvl(mrs.getString("rte")));
                        pkt.setMemoQuot(util.nvl(mrs.getString("memoQuot")));
                        pkt.setByrRte(quot);
                        pkt.setFnlRte(util.nvl(mrs.getString("fnl_sal")));
                        pkt.setDis(util.nvl(mrs.getString("dis")));
                        pkt.setByrDis(util.nvl(mrs.getString("mDis")));
                        if(dteFilter.equals("Y")){
                        pkt.setDteNum(mrs.getLong("dtenum"));
                        }else{
                            pkt.setDteNum(1);
                        }
                        pkt.setVnm(vnm);
                        String lStt = mrs.getString("stt");
                        pkt.setStt(lStt);
                        udf.setValue("stt_" + pktIdn,"CS");
                        pkt.setValue("rapVlu", (mrs.getString("rapVlu")));
                        pkt.setValue("stkstt", (mrs.getString("stkstt")));
                        HashMap fileDate = (HashMap)fileDataMap.get(vnm);
                        if(fileDate!=null && fileDate.size()>0){
                        double quotdouble = Double.parseDouble(quot);
                        String rapDis = mrs.getString("mDis");
                        int rapRte = mrs.getInt("rap_rte");
                        String price = util.nvl((String)fileDate.get("prc")).trim();
                        String dis = util.nvl((String)fileDate.get("dis")).trim();
                            double fQuot = quotdouble ;
                            String fDis = rapDis ;
                            if(!price.equals("")) {
                                fQuot = Double.parseDouble(price) ; 
                                fQuot=get2Decimal(fQuot*exh_rte);
                                if(rapRte > 1)
                                fDis = String.valueOf(get2Decimal(((fQuot/exh_rte/rapRte*100)- 100)));
                            }
                            if(dis.length() > 0) {
                                fQuot = get2Decimal((rapRte * (100 - Double.parseDouble(dis))/100)*exh_rte);
                                fDis = String.valueOf(get2Decimal((Double.parseDouble(dis))));
                            }
                        fileDate.put("dis",String.valueOf(fDis));
                        fileDate.put("prc",String.valueOf(fQuot));
                        pkt.setValue("fnldis", (String.valueOf(fDis)));
                        pkt.setValue("fnlprc", (String.valueOf(fQuot)));
                        fileDataMap.put(vnm, fileDate);
                        }
                        String getPktPrp =
                            " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
                            + " from stk_dtl a, mprp b, rep_prp c "
                            + " where a.mprp = b.prp and b.prp = c.prp and a.grp=1 and c.mdl = 'MEMO_RTRN' and a.mstk_idn = ? "
                            + " order by c.rnk, c.srt ";

                        params = new ArrayList();
                        params.add(Long.toString(pktIdn));

                        
                      ArrayList outLst2 = db.execSqlLst(" Pkt Prp", getPktPrp, params);
                      PreparedStatement pst2 = (PreparedStatement)outLst2.get(0);
                      ResultSet rs2 = (ResultSet)outLst2.get(1);
                        
                        while (rs2.next()) {
                            String lPrp = rs2.getString("mprp");
                            String lVal = rs2.getString("val");

                            pkt.setValue(lPrp, lVal);
                        }
                         rs2.close();
                        pst2.close();
                        pkts.add(pkt);
                        
                    }
                    mrs.close();
                    pst1.close();
                }

                req.setAttribute("view", "Y");
            }
            rs1.close();
            pst.close();
        } else {
            req.setAttribute("error", "please select memoIds of one buyer");
            req.setAttribute("view", "N");
        }

        loadSale(am, af, req, res);
        ArrayList ary = new ArrayList();
        trmList = srchQuery.getTerm(req,res, udf.getNmeIdn());

//        String getTrm = " select a.idn, a.term from mtrms a, nmerel b "
//                        + " where a.idn = b.trms_idn and b.nme_idn = ? " + " order by a.pct, a.dys  ";
//        ArrayList ary = new ArrayList();
//
//        ary.add(String.valueOf(udf.getNmeIdn()));
//        rs = db.execSql("favSrch", getTrm, ary);
//
//        while (rs.next()) {
//            TrmsDao trms = new TrmsDao();
//
//            trms.setRelId(rs.getInt(1));
//            trms.setTrmDtl(rs.getString(2));
//            trmList.add(trms);
//        }
        
        

        ArrayList byrList = new ArrayList();
        String    getByr  =
            "select nme_idn, a.nme byr from nme_v a " + " where nme_idn = ? "
            + " or exists (select 1 from nme_grp_dtl c, nme_grp b where a.nme_idn = c.nme_idn and b.nme_grp_idn = c.nme_grp_idn "
            + " and b.nme_idn = ? and b.typ = 'BUYER') " + " order by 2 ";

        ary = new ArrayList();
        ary.add(String.valueOf(udf.getNmeIdn()));
        ary.add(String.valueOf(udf.getNmeIdn()));
            
         outLst = db.execSqlLst("byr", getByr, ary);
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);

        while (rs.next()) {
            ByrDao byr = new ByrDao();

            byr.setByrIdn(rs.getInt("nme_idn"));

            String nme = util.nvl(rs.getString("byr"));

            if (nme.indexOf("&") > -1) {
                nme = nme.replaceAll("&", "&amp;");
            }

            byr.setByrNme(nme);
            byrList.add(byr);
        }
        rs.close();
        pst.close();
        String brokerSql =
            "select   byr.get_nm(mbrk1_idn) brk1  , mbrk1_comm , mbrk1_paid , byr.get_nm(mbrk2_idn) brk2  ,mbrk2_comm , mbrk2_paid, byr.get_nm(mbrk3_idn) brk3  ,"
            + "mbrk3_comm , mbrk3_paid, byr.get_nm(mbrk4_idn) brk4  ,mbrk4_comm , mbrk4_paid , byr.get_nm(aadat_idn) aaDat  , aadat_paid , aadat_comm  from nmerel where nmerel_idn = ?";

        ary = new ArrayList();
        ary.add(String.valueOf(udf.getRelIdn()));
          outLst = db.execSqlLst("", brokerSql, ary);
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);

        if (rs.next()) {
            udf.setValue("brk1", rs.getString("brk1"));
            udf.setValue("brk2", rs.getString("brk2"));
            udf.setValue("brk3", rs.getString("brk3"));
            udf.setValue("brk1comm", rs.getString("mbrk1_comm"));
            udf.setValue("brk2comm", rs.getString("mbrk2_comm"));
            udf.setValue("brk3comm", rs.getString("mbrk3_comm"));
            udf.setValue("brk4comm", rs.getString("mbrk4_comm"));
            udf.setValue("brk1paid", rs.getString("mbrk1_paid"));
            udf.setValue("brk2paid", rs.getString("mbrk2_paid"));
            udf.setValue("brk3paid", rs.getString("mbrk3_paid"));
            udf.setValue("brk4paid", rs.getString("mbrk4_paid"));
            udf.setValue("aaDat", rs.getString("aaDat"));
            udf.setValue("aadatpaid", rs.getString("aadat_paid"));
            udf.setValue("aadatcomm", rs.getString("aadat_comm"));
        }
        rs.close();
            pst.close();
       ary = new ArrayList();
        String sql =  "select a.addr_idn , unt_num, bldg ||''|| street ||''|| landmark ||''|| area ||''|| b.city_nm||''|| c.country_nm addr "+
                      " from maddr a, mcity b, mcountry c where a.city_idn = b.city_idn and b.country_idn = c.country_idn and a.nme_idn = ? order by a.dflt_yn desc ";
        
        ary.add(String.valueOf(udf.getNmeIdn()));
        ArrayList maddrList = new ArrayList();
          outLst = db.execSqlLst("memo pkt", sql, ary);
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);

        while(rs.next()){
            MAddr addr = new MAddr();
            addr.setIdn(rs.getString("addr_idn"));
            addr.setAddr(rs.getString("addr"));
            maddrList.add(addr);
        }
        rs.close();
        pst.close();
        
        String bankSql = "select nme_idn, fnme  from mnme  where 1 = 1 and vld_dte is null  and typ = 'GROUP'";
        ArrayList bankList = new ArrayList();
          outLst = db.execSqlLst("bank Sql", bankSql, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            MAddr addr = new MAddr();
            addr.setIdn(rs.getString("nme_idn"));
            addr.setAddr(rs.getString("fnme"));
            bankList.add(addr);
        }
        rs.close();
            pst.close();
            
            
            ArrayList chargesLst=new ArrayList();
            String chargesQ="Select ct.idn,ct.typ,ct.dsc,ct.stg optional,ct.flg,ct.fctr,ct.db_call,ct.rmk\n" + 
            "    From Df_Pg A,Df_Pg_Itm B,Charges_Typ ct\n" + 
            "    Where A.Idn = B.Pg_Idn\n" + 
            "    and b.fld_nme=ct.typ\n" + 
            "    And A.Mdl = 'CONSIGNMENT_FORM'\n" + 
            "    and b.itm_typ=?\n" + 
            "    And A.Stt='A'\n" + 
            "    And B.Stt='A'\n" + 
            "    and ct.stt='A'\n" + 
            "    And A.Vld_Dte Is Null \n" + 
            "    And Not Exists (Select 1 From Df_Pg_Itm_Usr C Where B.Idn=C.Itm_Idn And C.Stt='IA' And C.Usr_Idn=?)\n" + 
            "    order by b.itm_typ,b.srt";
            ary = new ArrayList();
            ary.add("SALE_CHARGES");
            ary.add(String.valueOf(info.getUsrId()));
          outLst = db.execSqlLst("chargesQ", chargesQ,ary);
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                HashMap dtl=new HashMap();
                dtl.put("idn",util.nvl(rs.getString("idn")));
                dtl.put("dsc",util.nvl(rs.getString("dsc")));
                dtl.put("autoopt",util.nvl(rs.getString("optional")));
                dtl.put("flg",util.nvl(rs.getString("flg")));
                dtl.put("typ",util.nvl(rs.getString("typ")));
                dtl.put("fctr",util.nvl(rs.getString("fctr")));
                dtl.put("fun",util.nvl(rs.getString("db_call")));
                dtl.put("rmk",util.nvl(rs.getString("rmk")));
                chargesLst.add(dtl);
            }
            rs.close();
            pst.close();
            session.setAttribute("chargesLst", chargesLst);
            fisalcharges(req);
        udf.setBankList(bankList);
        udf.setAddrList(maddrList);
//        udf.setValue("byrList", byrList);
        udf.setByrLstFetch(byrList);
        udf.setByrLst(byrList);
        udf.setValue("byrIdn", udf.getNmeIdn());
        udf.setValue("byrTrm", udf.getRelIdn());
        udf.setValue("memoIdn", memoIds);
        req.setAttribute("memoIdn", String.valueOf(memoIds).replaceAll("'", ""));
        udf.setByrIdn(String.valueOf(udf.getNmeIdn()));
        udf.setTrmsLst(trmList);
        udf.setPkts(pkts);
        info.setValue("PKTS", pkts);
        udf.setValue("isDLV", "Yes");
          util.updAccessLog(req,res,"Consignment Sale", "Consignment Load Pkt done");
            finalizeObject(db, util);
        return am.findForward("view");
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
          util.updAccessLog(req,res,"Consignment Sale", "Consignment Save");
        ArrayList params = null;
        ConsignmentSaleForm udf = (ConsignmentSaleForm) af;
        ArrayList pkts     = new ArrayList();
        String    pktNmsSl = "";
        String    pktNmsRT = "";
        String buttonPressed = "";
        int       appSlIdn = 0;
        int       appDlvIdn = 0;
        CallableStatement cst = null;
        ArrayList out = new ArrayList();
        ArrayList salIdnLst=new ArrayList();
        ArrayList dlvIdnLst = new ArrayList();
          
        String globalrmk = util.nvl((String)udf.getValue("rmk"));
        String dlvDte = util.nvl((String)udf.getValue("dlvDte"));
        pkts = (ArrayList) info.getValue("PKTS");    // udf.getPkts();
        ArrayList chargesLst=(ArrayList)session.getAttribute("chargesLst");
        long pDteNum = 0;
        for (int i = 0; i < pkts.size(); i++) {
            PktDtl pkt     = (PktDtl) pkts.get(i);
            long   lPktIdn = pkt.getPktIdn();
            long lDteNum = pkt.getDteNum();
            String memoIdn = pkt.getMemoId();
            String lStt    = util.nvl((String) udf.getValue("stt_" + lPktIdn),"CS");
            String vnm = pkt.getVnm();
            if(pDteNum==0)
                pDteNum=lDteNum;
            if(pDteNum!=lDteNum){
                if (appSlIdn != 0) {
                    if(chargesLst!=null && chargesLst.size()>0){
                    for(int x=0;x<chargesLst.size();x++){
                    HashMap dtl=new HashMap();
                    dtl=(HashMap)chargesLst.get(x);
                    String idn=(String)dtl.get("idn");
                    String dsc=(String)dtl.get("dsc");
                    String flg=(String)dtl.get("flg");
                    String typcharge=(String)dtl.get("typ");
                    String fctr=(String)dtl.get("fctr");
                    String fun=(String)dtl.get("fun");
                    String autoopt=util.nvl((String)dtl.get("autoopt"));
                    String calcdis= util.nvl((String)udf.getValue(typcharge+"_save"),"0");  
                    String autooptional= util.nvl((String)udf.getValue(typcharge+"_AUTOOPT"));  
                    String vlu= util.nvl((String)udf.getValue("vluamt"));
                    String exhRte=util.nvl((String)udf.getValue("exhRte"),"1");
                    String extrarmk= util.nvl((String)udf.getValue(typcharge+"_rmksave")); 
                        if(!vlu.equals("") && !vlu.equals("0")  && !calcdis.equals("NaN")  && !vlu.equals("NaN")){
                    if((!calcdis.equals("") && !calcdis.equals("0")) || (autoopt.equals("OPT"))){
                    String insertQ="Insert Into Trns_Charges (trns_idn, ref_typ, ref_idn,charges_idn,amt,amt_usd,charges,CHARGES_PCT,net_usd,rmk,stt,flg)\n" + 
                    "VALUES (TRNS_CHARGES_SEQ.nextval, 'SAL', ?,?,?,?,?,?,?,?,'A',?)";  
                    ArrayList ary=new ArrayList();
                    ary.add(String.valueOf(appSlIdn));
                    ary.add(idn);
                    ary.add(vlu);
                    float amt_usd=Float.parseFloat(vlu)/Float.parseFloat(exhRte);
                    ary.add(String.valueOf(amt_usd));
                    ary.add(calcdis);
                    ary.add(calcdis);
                    float net_usd=amt_usd+Float.parseFloat(calcdis);
                    ary.add(String.valueOf(net_usd));
                    ary.add(extrarmk);
                    ary.add(autooptional);
                    int ct = db.execUpd("Insert", insertQ, ary);
                    }
                    }
                    }
                    }                      
                params = new ArrayList();
                params.add(String.valueOf(appSlIdn));
                      
                int calCt = db.execCall("calQuot", "SL_PKG.Cal_Fnl_Quot(pIdn => ?)", params);
                  
                params = new ArrayList();
                params.add(String.valueOf(appSlIdn));

                int upQuery = db.execCall("calQuot", "SL_PKG.UPD_QTY(pIdn => ? )", params);
                if(!dlvDte.equals("")){
                ArrayList ary=new ArrayList();
                ary.add(String.valueOf(appSlIdn));
                int upmsal = db.execUpd("updatemsal", "update msal set rmk='"+dlvDte+"' where idn= ? ", ary);
                }
                
                
                ArrayList ary = new ArrayList();
                ary.add(Integer.toString(udf.getNmeIdn()));
                ary.add(Integer.toString(udf.getRelIdn()));
                ary.add(udf.getValue("byrTrm"));
                ary.add(udf.getByrIdn());
                ary.add(udf.getValue("addr"));
                ary.add("DLV");
                ary.add("IS");
                ary.add(String.valueOf(appSlIdn));
                ary.add("NN");
                ary.add(nvl((String) udf.getValue("aadatpaid"), "Y"));
                ary.add(nvl((String) udf.getValue("brk1paid"), "Y"));
                ary.add(nvl((String) udf.getValue("brk2paid"), "Y"));
                ary.add(nvl((String) udf.getValue("brk3paid"), "Y"));
                ary.add(nvl((String) udf.getValue("brk4paid"), "Y"));
                ary.add(util.nvl((String) udf.getValue("bankIdn")));
                ary.add(util.nvl((String) udf.getValue("comIdn")));
                
                out = new ArrayList();
                out.add("I");
                cst = db.execCall(
                    "Gen_HDR ",
                    "dlv_pkg.Gen_Hdr(pByrId => ? , pRlnIdn => ? , pTrmsIdn => ? , pInvNmeIdn => ? , pInvAddrIdn => ? , "
                    + "pTyp => ? , pStt => ?,pFrmId => ?, pFlg => ? ,paadat_paid => ?, pMBRK1_PAID => ?, pMBRK2_PAID => ?, pMBRK3_PAID => ? , "
                    + " pMBRK4_PAID => ?, bnk_idn =>? , co_idn => ? , pIdn => ?) ", ary, out);
                appDlvIdn = cst.getInt(17);
                  cst.close();
                  cst=null;
                
                params = new ArrayList();
                params.add("DLV");
                params.add(String.valueOf(appSlIdn));
               int upJan = db.execUpd("updateJAN", "update jansal set stt=? , dte_sal=sysdate where idn= ? ", params);
                params = new ArrayList();
                params.add(String.valueOf(appSlIdn));
                ArrayList outLst = db.execSqlLst("select Sal", "select idn,  mstk_idn, qty, cts,  quot from jansal where idn=? ", params);
                  PreparedStatement pst = (PreparedStatement)outLst.get(0);
                  ResultSet rs = (ResultSet)outLst.get(1);
              
                while(rs.next()){
                    String mstkIdn=rs.getString("mstk_idn");
                    String qty=rs.getString("qty");
                    String cts=rs.getString("cts");
                    String quot=rs.getString("quot");
                    
                    params = new ArrayList();
                    params.add(String.valueOf(appDlvIdn));
                    params.add(String.valueOf(appSlIdn));
                    params.add(mstkIdn);
                    params.add(qty);
                    params.add(cts);
                    params.add(quot);
                    params.add("DLV");
                    int SalPkt = db.execCall(
                            "sale from memo",
                            " DLV_PKG.Dlv_Pkt(pIdn => ? , pSaleIdn => ?, pStkIdn => ?, pQty => ? , pCts => ?, pFnlSal => ?, pStt => ?)",
                            params);
                }
                    rs.close();
                    pst.close();
                params = new ArrayList();
                params.add(String.valueOf(appDlvIdn));

                calCt = db.execCall("calQuot", "DLV_PKG.Cal_Fnl_Quot(pIdn => ?)", params);

                params = new ArrayList();
                params.add(String.valueOf(appDlvIdn));

                 upQuery = db.execCall("calQuot", "DLV_PKG.UPD_QTY(pIdn => ? )", params);
                salIdnLst.add(appSlIdn);
                dlvIdnLst.add(appDlvIdn);
                pDteNum=lDteNum;
                appSlIdn = 0;
                }
            }
            
            String updPkt = "";

            if (lStt.equals("SL")) {
                if (appSlIdn == 0) {
                    ArrayList ary = new ArrayList();

                    ary.add(Integer.toString(udf.getNmeIdn()));
                    ary.add(Integer.toString(udf.getRelIdn()));
                    ary.add(udf.getValue("byrTrm"));
                    ary.add(udf.getByrIdn());
                    ary.add(udf.getValue("addr"));
                    ary.add("SL");
                    ary.add("IS");
                    ary.add(memoIdn);
                    ary.add("NN");
                    ary.add(nvl((String) udf.getValue("aadatpaid"), "Y"));
                    ary.add(nvl((String) udf.getValue("brk1paid"), "Y"));
                    ary.add(nvl((String) udf.getValue("brk2paid"), "Y"));
                    ary.add(nvl((String) udf.getValue("brk3paid"), "Y"));
                    ary.add(nvl((String) udf.getValue("brk4paid"), "Y"));
                    ary.add(util.nvl((String)udf.getValue("exhRte")));
                    out = new ArrayList();
                    out.add("I");
                    cst = db.execCall(
                        "MKE_HDR ",
                        "sl_pkg.gen_hdr(pByrId => ?, pRlnIdn => ?, pTrmsIdn => ?, pInvNmeIdn => ?, pInvAddrIdn => ?, pTyp => ?, pStt => ?, pFrmId => ? , pFlg => ? , paadat_paid => ?, pMBRK1_PAID => ? , pMBRK2_PAID => ?, pMBRK3_PAID => ?, pMBRK4_PAID => ?, pExhRte => ?, pIdn => ? )", ary, out);
                    appSlIdn = cst.getInt(ary.size()+1);
                    cst.close();
                    
                    }

                params = new ArrayList();
                params.add("CS");
                params.add(Long.toString(lPktIdn));
                params.add(memoIdn);

                int upJan = db.execUpd("updateJAN", "update jandtl set stt=? , dte_sal=sysdate where mstk_idn=? and idn= ? ", params);

                
                params = new ArrayList();
                params.add(String.valueOf(appSlIdn));
                params.add(memoIdn);
                params.add(Long.toString(lPktIdn));
                params.add(pkt.getQty());
                params.add(pkt.getCts());
                params.add(util.nvl((String)pkt.getValue("fnlprc"),pkt.getMemoQuot()));
                params.add("SL");

                int SalPkt = db.execCall("sale from memo",
                                         "sl_pkg.Sal_Pkt(" + "pIdn => ?" + ", pMemoIdn =>?" + ", pStkIdn => ?"
                                         + ", pQty => ?" + ", pCts => ?" + ", pFnlSal=> ?" + ", pStt => ?)", params);               
                
            } else {
                if (lStt.equals("RT")) {
                    String rmk = util.nvl((String)udf.getValue("rmk_"+Long.toString(lPktIdn)));
                   
                    params = new ArrayList();
                    params.add(memoIdn);
                    params.add(Long.toString(lPktIdn));
                    params.add(rmk);
                    int ct = db.execCall(" App Pkts", "memo_pkg.rtn_pkt( pMemoId =>?, pStkIdn=> ? , pRem => ?)", params);

                    pktNmsRT = pktNmsRT + "," +vnm;
                    req.setAttribute("RTMSG", "Packets get return: " + pktNmsRT);
                }
            }
            
            String updJanQty = " jan_qty(?) ";
            params = new ArrayList();
            params.add(memoIdn);
            int ct1 = db.execCall("JanQty", updJanQty, params);

        }
        
        
            if(pDteNum!=0){
                if (appSlIdn != 0) {
                    if(chargesLst!=null && chargesLst.size()>0){
                    for(int i=0;i<chargesLst.size();i++){
                    HashMap dtl=new HashMap();
                    dtl=(HashMap)chargesLst.get(i);
                    String idn=(String)dtl.get("idn");
                    String dsc=(String)dtl.get("dsc");
                    String flg=(String)dtl.get("flg");
                    String typcharge=(String)dtl.get("typ");
                    String fctr=(String)dtl.get("fctr");
                    String fun=(String)dtl.get("fun");
                    String autoopt=util.nvl((String)dtl.get("autoopt"));
                    String calcdis= util.nvl((String)udf.getValue(typcharge+"_save"),"0");  
                    String autooptional= util.nvl((String)udf.getValue(typcharge+"_AUTOOPT"));  
                    String vlu= util.nvl((String)udf.getValue("vluamt"));
                    String exhRte=util.nvl((String)udf.getValue("exhRte"),"1");
                    String extrarmk= util.nvl((String)udf.getValue(typcharge+"_rmksave")); 
                        if(!vlu.equals("") && !vlu.equals("0")  && !calcdis.equals("NaN")  && !vlu.equals("NaN")){
                    if((!calcdis.equals("") && !calcdis.equals("0")) || (autoopt.equals("OPT"))){
                    String insertQ="Insert Into Trns_Charges (trns_idn, ref_typ, ref_idn,charges_idn,amt,amt_usd,charges,CHARGES_PCT,net_usd,rmk,stt,flg)\n" + 
                    "VALUES (TRNS_CHARGES_SEQ.nextval, 'SAL', ?,?,?,?,?,?,?,?,'A',?)";  
                    ArrayList ary=new ArrayList();
                    ary.add(String.valueOf(appSlIdn));
                    ary.add(idn);
                    ary.add(vlu);
                    float amt_usd=Float.parseFloat(vlu)/Float.parseFloat(exhRte);
                    ary.add(String.valueOf(amt_usd));
                    ary.add(calcdis);
                    ary.add(calcdis);
                    float net_usd=amt_usd+Float.parseFloat(calcdis);
                    ary.add(String.valueOf(net_usd));
                    ary.add(extrarmk);
                    ary.add(autooptional);
                    int ct = db.execUpd("Insert", insertQ, ary);
                    }
                    }
                    }
                    }                    
                params = new ArrayList();
                params.add(String.valueOf(appSlIdn));
                      
                int calCt = db.execCall("calQuot", "SL_PKG.Cal_Fnl_Quot(pIdn => ?)", params);
                  
                params = new ArrayList();
                params.add(String.valueOf(appSlIdn));

                int upQuery = db.execCall("calQuot", "SL_PKG.UPD_QTY(pIdn => ? )", params);
                if(!dlvDte.equals("")){
                ArrayList ary=new ArrayList();
                ary.add(String.valueOf(appSlIdn));
                int upmsal = db.execUpd("updatemsal", "update msal set rmk='"+dlvDte+"' where idn= ? ", ary);
                }
                
                params = new ArrayList();
                params.add(globalrmk);
                params.add(String.valueOf(appSlIdn));
                String updateFlg = "update msal set rmk=? where idn=?";
                int cntmj = db.execUpd("update msal", updateFlg, params);
                    
                ArrayList ary = new ArrayList();
                ary.add(Integer.toString(udf.getNmeIdn()));
                ary.add(Integer.toString(udf.getRelIdn()));
                ary.add(udf.getValue("byrTrm"));
                ary.add(udf.getByrIdn());
                ary.add(udf.getValue("addr"));
                ary.add("DLV");
                ary.add("IS");
                ary.add(String.valueOf(appSlIdn));
                ary.add("NN");
                ary.add(nvl((String) udf.getValue("aadatpaid"), "Y"));
                ary.add(nvl((String) udf.getValue("brk1paid"), "Y"));
                ary.add(nvl((String) udf.getValue("brk2paid"), "Y"));
                ary.add(nvl((String) udf.getValue("brk3paid"), "Y"));
                ary.add(nvl((String) udf.getValue("brk4paid"), "Y"));
                ary.add(util.nvl((String) udf.getValue("bankIdn")));
                ary.add(util.nvl((String) udf.getValue("comIdn")));
                
                out = new ArrayList();
                out.add("I");
                cst = db.execCall(
                    "Gen_HDR ",
                    "dlv_pkg.Gen_Hdr(pByrId => ? , pRlnIdn => ? , pTrmsIdn => ? , pInvNmeIdn => ? , pInvAddrIdn => ? , "
                    + "pTyp => ? , pStt => ?,pFrmId => ?, pFlg => ? ,paadat_paid => ?, pMBRK1_PAID => ?, pMBRK2_PAID => ?, pMBRK3_PAID => ? , "
                    + " pMBRK4_PAID => ?, bnk_idn =>? , co_idn => ? , pIdn => ?) ", ary, out);
                appDlvIdn = cst.getInt(17);
                cst.close();
                
                params = new ArrayList();
                params.add("DLV");
                params.add(String.valueOf(appSlIdn));
               int upJan = db.execUpd("updateJAN", "update jansal set stt=? , dte_sal=sysdate where idn= ? ", params);
                params = new ArrayList();
                params.add(String.valueOf(appSlIdn));
                ArrayList outLst = db.execSqlLst("select Sal", "select idn,  mstk_idn, qty, cts,  quot from jansal where idn=? ", params);
                    PreparedStatement pst = (PreparedStatement)outLst.get(0);
                    ResultSet rs = (ResultSet)outLst.get(1);
               
                while(rs.next()){
                    String mstkIdn=rs.getString("mstk_idn");
                    String qty=rs.getString("qty");
                    String cts=rs.getString("cts");
                    String quot=rs.getString("quot");
                    
                    params = new ArrayList();
                    params.add(String.valueOf(appDlvIdn));
                    params.add(String.valueOf(appSlIdn));
                    params.add(mstkIdn);
                    params.add(qty);
                    params.add(cts);
                    params.add(quot);
                    params.add("DLV");
                    int SalPkt = db.execCall(
                            "sale from memo",
                            " DLV_PKG.Dlv_Pkt(pIdn => ? , pSaleIdn => ?, pStkIdn => ?, pQty => ? , pCts => ?, pFnlSal => ?, pStt => ?)",
                            params);
                }
                    rs.close();
                    pst.close();
                params = new ArrayList();
                params.add(String.valueOf(appDlvIdn));

               calCt = db.execCall("calQuot", "DLV_PKG.Cal_Fnl_Quot(pIdn => ?)", params);

                params = new ArrayList();
                params.add(String.valueOf(appDlvIdn));

                 upQuery = db.execCall("calQuot", "DLV_PKG.UPD_QTY(pIdn => ? )", params);
                    salIdnLst.add(appSlIdn);
                    dlvIdnLst.add(appDlvIdn);
                }
            }
     
        
         for(int i=0;i<salIdnLst.size();i++){
                appSlIdn=(Integer)salIdnLst.get(i);
              appDlvIdn = (Integer)dlvIdnLst.get(i);
                         
             MailAction mailObj = new MailAction();
             mailObj.sendSalMail(appSlIdn, appDlvIdn , req, res);
              
         req.setAttribute("SLMSG", "Packets get Deliver : " + pktNmsSl + " with delivery Id : " + dlvIdnLst.toString());
            }
            
           HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
         HashMap pageDtl=(HashMap)allPageDtl.get("PERFORMA_INV");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("PERFORMA_INV");
            allPageDtl.put("PERFORMA_INV",pageDtl);
            }
            info.setPageDetails(allPageDtl);
        util.updAccessLog(req,res,"Consignment Sale", "Consignment Save Done ids sale "+appSlIdn+" dlvIdn "+appDlvIdn);
            finalizeObject(db, util);
        return loadSale(am, af, req, res);
        }
    }
    

    public ActionForward createXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        util.updAccessLog(req,res,"Memo sale", "Create Excel");
        String mail=util.nvl(req.getParameter("mail"));
        String nmeIdn=util.nvl(req.getParameter("nmeIdn"));
        String byrDtl=util.nvl(req.getParameter("byrDtl"));
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "MemoSale"+util.getToDteTime()+".xls";
        ArrayList pktList = (ArrayList)session.getAttribute("pktList");
        ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
        if(mail.equals("")){
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
        hwb.write(out);
        out.flush();
        out.close();
            finalizeObject(db, util);
        return null;
        }else{
            SearchQuery query=new SearchQuery();
            query.MailExcelmass(hwb, fileNm, req,res);  
            ArrayList emailids=util.byrAllEmail(nmeIdn);
            req.setAttribute("ByrEmailIds",emailids);
            finalizeObject(db, util);
            return am.findForward("mail");
        }
        }
    }

    public String nvl(String pVal, String rVal) {
        String val = pVal;

        if (pVal == null) {
            val = rVal;
        }

        return val;
    }
    public void finalizeObject(DBMgr db, DBUtil util){
        try {
            db=null;
            util=null;
        } catch (Throwable t) {
            // TODO: Add catch code
            t.printStackTrace();
        }
       
    }
    
    public ActionForward mailSend(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            int appSlIdn=72350;
           int appDlvIdn = 77753;
            MailAction mailObj = new MailAction();
            mailObj.sendSalMail(appSlIdn, appDlvIdn , req, res);
            
    
            return am.findForward("load");   
        }
    
            }
    public double get2Decimal(double val) {
      DecimalFormat df = new  DecimalFormat ("0.##");
      String d = df.format (val);
      System.out.println ("\tformatted: " + d);
      d = d.replaceAll (",", ".");
      Double dbl = new Double (d);
      return dbl.doubleValue ();
    }
    
    public void fisalcharges(HttpServletRequest req){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        Connection conn=info.getCon();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    HashMap fisalcharges = (session.getAttribute("fisalcharges") == null)?new HashMap():(HashMap)session.getAttribute("fisalcharges");

    try {
    if(fisalcharges.size() == 0) {
    String ptyp="";
    ArrayList dataLst=new ArrayList();
    ArrayList data=new ArrayList();
    String gtView = "Select  c.typ,a.chr_fr,a.chr_to  From Rule_Dtl A, Mrule B , Charges_Typ C \n" + 
    "Where A.Rule_Idn = B.Rule_Idn And  \n" + 
    "c.typ = a.dsc and b.mdl = 'JFLEX' \n" + 
    "and b.nme_rule = 'FIX_SALE_CHARGES' and c.stt='A' \n" + 
    "and a.til_dte is null order by a.srt_fr , a.dsc , a.chr_fr";
    
     ArrayList outLst = db.execSqlLst("gtView", gtView, new ArrayList());
    PreparedStatement   pst = (PreparedStatement)outLst.get(0);
     ResultSet rs = (ResultSet)outLst.get(1);
    while (rs.next()) {
    data=new ArrayList();
    String typ=util.nvl(rs.getString("typ"));
        if(ptyp.equals(""))
        ptyp=typ;
        if(!ptyp.equals(typ)){
            fisalcharges.put(ptyp,dataLst);
            dataLst = new ArrayList();
            ptyp=typ;
        }
        data.add(util.nvl(rs.getString("chr_fr")));
        data.add(util.nvl(rs.getString("chr_to")));
        dataLst.add(data);
    }
        rs.close();
        pst.close();
        if(!ptyp.equals("")){
        fisalcharges.put(ptyp,dataLst);
        }
    rs.close();
    session.setAttribute("fisalcharges", fisalcharges);
    }
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }
        finalizeObject(db, util);
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
                util.updAccessLog(req,res,"Consignment Sales", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Consignment Sales", "init");
            }
            }
            return rtnPg;
            }
}


//~ Formatted by Jindent --- http://www.jindent.com
