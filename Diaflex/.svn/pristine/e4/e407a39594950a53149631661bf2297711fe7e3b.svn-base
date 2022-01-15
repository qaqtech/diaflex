package ft.com;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.logging.Level;

import ft.com.dao.*;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.LocationDlvForm;
import ft.com.marketing.SearchQuery;
import ft.com.pricing.RepriceFrm;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileNotFoundException;

import java.io.IOException;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.math.BigDecimal;

import java.math.MathContext;

import java.math.RoundingMode;

import java.sql.CallableStatement;

import java.sql.PreparedStatement;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.struts.action.ActionForm;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.sql.SQLException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;

import java.net.URLEncoder;

import java.sql.Connection;

import java.text.DateFormat;
import java.text.DecimalFormat;

import java.text.ParseException;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import java.util.concurrent.Future;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import javax.ws.rs.core.MediaType;

import net.spy.memcached.MemcachedClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import org.json.JSONObject;

public class DBUtil {
    DBMgr db ;
    InfoMgr info ;
    LogMgr log ;
    String logApplNm ;
    
    public DBUtil() {
        super();
        log = new LogMgr();
    }

    public void setDb(DBMgr db) {
        this.db = db;
    }

    public DBMgr getDb() {
        return db;
    }

    public void setInfo(InfoMgr info) {
        this.info = info;
    }

    public InfoMgr getInfo() {
        return info;
    }
    
    public void SOP(String s) {
        log.log(s);
    }
    public void SOP(Level l, String s) {
        log.log(l, s);
    }
    
    public void SOPLvl(int msgLvl, String msg) {
        log.log(msgLvl,msg);
    }
    
    public void setLogApplNm(String logApplNm) {
        this.logApplNm = logApplNm;
        log.setApplNm(logApplNm);
    }

    public String getLogApplNm() {
        return logApplNm;
    }

    public Boolean authenticate(String usr, String pwd) {
        Boolean isValid = Boolean.FALSE;
        
        String getQ = " select usr,usr_id, to_number(to_char(sysdate, 'rrrr'))+1 yr, nvl(a.nmerel_idn,0) nmerel_idn  , a.flg , a.nme_idn,nvl(a.auth_mode, 'DFLT') auth_mode,a.spacecodeip from df_users a where  upper(usr) = upper(?) and upper(pwd) = upper(?) and stt = 'A' and\n" + 
        " trunc(nvl(to_dte,sysdate))<=(sysdate) ";
        ArrayList params = new ArrayList();
        params.add(usr);
        params.add(pwd);
        ResultSet rs=null;
        try {
            ArrayList outLst = db.execSqlLst("authenticate", getQ, params);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                info.setUsr(nvl(rs.getString("usr")));            
                int usrId = rs.getInt("usr_id");
                info.setUsrId(usrId);
                info.setCurrentYear(rs.getInt("yr"));
                info.setDf_rln_id(rs.getInt("nmerel_idn"));
                info.setUsrFlg(nvl(rs.getString("flg")));
                info.setAuth_mode(nvl(rs.getString("auth_mode")));
                isValid = Boolean.TRUE;
                initMdls();
                String nmeIdn = nvl(rs.getString("nme_idn"));
                info.setDfNmeIdn(nmeIdn);
                info.setSpaceCodeIp(nvl(rs.getString("spacecodeip")));
                if(!nmeIdn.equals(""))
                    initDfUsr(nmeIdn);
            }
            rs.close();
            pst.close();
            if(info.getDf_rln_id()!=0){
                params = new ArrayList();
                params.add(String.valueOf(info.getDf_rln_id()));
                outLst = db.execSqlLst("nmeIdn", "Select nme_idn ,  get_xrt(cur) xrt ,  byr.get_nm(nme_idn) nme from nmerel where  nmerel_idn=?", params);
                pst = (PreparedStatement)outLst.get(0);
                rs = (ResultSet)outLst.get(1);
                while(rs.next()){
                    info.setDf_nme_id(rs.getInt("nme_idn"));
                    info.setDf_xrt(rs.getString("xrt"));
                    info.setDfNme(rs.getString("nme"));
                }
                rs.close();
                pst.close();
            }
            if(!isValid){
                outLst = db.execSqlLst("dbms_ErrorPath", "select val from dbms_sys_info where prp='ERROR_PAGE'", new ArrayList());
                pst = (PreparedStatement)outLst.get(0);
                rs = (ResultSet)outLst.get(1);
                rs.next();
                info.setErrorPath(rs.getString(1));
                rs.close();
                pst.close();
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Authentication Failed");
            log.log(Level.SEVERE, e.toString());
        }
        initDmbsSysInfo();
        initMenuLink();
        initReport();
        initSrch();
        return isValid;
    }
    public ArrayList getKeyInArrayList(HashMap searchList){
    ArrayList selectstkIdnLst =new ArrayList();
    Set<String> keys = searchList.keySet();
        for(String key: keys){
       selectstkIdnLst.add(key);
        }
    return selectstkIdnLst;
    }
    public ArrayList convertArrayList(HashMap searchList,ArrayList idnLst){
    ArrayList selectstkIdnLst =new ArrayList();
    for(int i=0;i< idnLst.size();i++){
    selectstkIdnLst.add(searchList.get(idnLst.get(i)));
    }
    return selectstkIdnLst;
    }
    public void setDbUsr(HttpServletRequest req,String usrip){
        String      usr     = req.getParameter("dfusr");
        String usrMch = req.getRemoteHost();
       
       
         ArrayList params = new ArrayList();
         params.add(usr);
        int ct = db.execCall("Set_usr", " PACK_VAR.SET_USR(?)", params);
        
        params = new ArrayList();
        params.add(usrip);
        ct = db.execCall("Set_usr", " PACK_VAR.SET_USR_IP(?)", params);
                
        params = new ArrayList();
        params.add(usrMch);
        ct = db.execCall("Set_usr", " PACK_VAR.SET_USR_MCH(?)", params);
                
    }
    public String calculateDisVlu(String rap_rte,String cts,String rte,String typ){
        String rtnval="";
        if(!rap_rte.equals("") && !cts.equals("") && !rte.equals("") && !rap_rte.equals("0") && !cts.equals("0") && !rte.equals("0")){
            double keyVlu = (Double.parseDouble(cts)*Double.parseDouble(rte));
            double keyRapVlu = (Double.parseDouble(cts)*Double.parseDouble(rap_rte));
            if(typ.equals("DIS") && !rap_rte.equals("1") && !rap_rte.equals("1.0") && !rap_rte.equals("0.0") && !rap_rte.equals("0.0")){
            rtnval=String.valueOf(roundToDecimals(((keyVlu/keyRapVlu)*100)-100,2));
            }else if(typ.equals("VLU")){
            rtnval=String.valueOf(roundToDecimals((keyVlu),2));
            }
        }
        return rtnval;
    }
    
    public String calculatePlDiffVlu(String rap_rte,String cts,String quot,String fnlusd,String compareWith,String typ){
        String rtnval="";
        if(!rap_rte.equals("") && !cts.equals("") && !quot.equals("") && !fnlusd.equals("") && !compareWith.equals("") && !rap_rte.equals("0") && !cts.equals("0") && !quot.equals("0") && !fnlusd.equals("0") && !compareWith.equals("0")){
            if(typ.equals("DIFF")){
            rtnval=String.valueOf(roundToDecimals((Double.parseDouble(quot)-Double.parseDouble(compareWith))/Double.parseDouble(rap_rte)*Double.parseDouble("100"),2));
            }else if(typ.equals("PL")){
            rtnval=String.valueOf(roundToDecimals((Double.parseDouble(quot)-Double.parseDouble(compareWith))*Double.parseDouble(cts),2));
            }else if(typ.equals("PLPER")){
            rtnval=String.valueOf(roundToDecimals(((Double.parseDouble(quot)-Double.parseDouble(compareWith))/Double.parseDouble(compareWith))*100,2));
            }else if(typ.equals("PLSALRTE")){
            rtnval=String.valueOf(roundToDecimals((Double.parseDouble(fnlusd)-Double.parseDouble(compareWith))*Double.parseDouble(cts),2));
            }else if(typ.equals("PLSALRTEPER")){
                rtnval=String.valueOf(roundToDecimals(((Double.parseDouble(fnlusd)-Double.parseDouble(compareWith))/Double.parseDouble(compareWith))*100,2));
            }
        }
        return rtnval;
    }
    
    public String getFullURL(HttpServletRequest request) {
//        StringBuffer requestURL = request.getRequestURL();
//        String queryString = request.getQueryString();
//
//        if (queryString == null) {
//            return requestURL.toString();
//        } else {
//            return requestURL.append('?').append(queryString).toString().replaceAll("%27","''''").replaceAll("&", "'||'&'||'");
//        }
        return "";
    }
    public HashMap pagedef(String pg) {
    HashMap pageDtl=new HashMap();
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String itm_typ="",form_nme="",fld_nme="",fld_ttl="",fld_typ="", dflt_val="", lov_qry="", val_cond="", flg1="", prv="";

    String gtView = "Select itm_typ,form_nme,fld_nme,fld_ttl,fld_typ,dflt_val,lov_qry,val_cond,b.flg1,b.flg2,b.srt\n" +
    "From Df_Pg A,Df_Pg_Itm B\n" +
    "Where A.Idn = B.Pg_Idn\n" +
    "And A.Mdl = ?\n" +
    "And A.Stt='A'\n" +
    "And B.Stt='A'\n" +
    "And A.Vld_Dte Is Null \n" +
    "And Not Exists (Select 1 From Df_Pg_Itm_Usr C Where B.Idn=C.Itm_Idn And C.Stt='IA' And C.Usr_Idn=?)\n" +
    "order by b.itm_typ,b.srt ";
    ArrayList ary=new ArrayList();
    ary.add(pg);
    ary.add(String.valueOf(info.getUsrId()));
        ArrayList outLst = db.execSqlLst("gtView", gtView, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
    try {
    while(rs.next()) {
    pageDtlMap=new HashMap();
    itm_typ=nvl(rs.getString("itm_typ"));
    form_nme=nvl(rs.getString("form_nme"));
    fld_nme=nvl(rs.getString("fld_nme"));
    fld_ttl=nvl(rs.getString("fld_ttl"));
    fld_typ=nvl(rs.getString("fld_typ"));
    dflt_val=nvl(rs.getString("dflt_val"));
    lov_qry=nvl(rs.getString("lov_qry"));
    val_cond=nvl(rs.getString("val_cond"));
    flg1=nvl(rs.getString("flg1"));

    pageDtlMap.put("form_nme",form_nme);
    pageDtlMap.put("fld_nme",fld_nme);
    pageDtlMap.put("fld_ttl",fld_ttl);
    pageDtlMap.put("fld_typ",fld_typ);
    pageDtlMap.put("dflt_val",dflt_val);
    pageDtlMap.put("lov_qry",lov_qry);
    pageDtlMap.put("val_cond",val_cond);
    pageDtlMap.put("flg1",flg1);

    if(prv.equals(""))
    prv = itm_typ;

    if(!prv.equals(itm_typ)){
    pageDtl.put(prv,pageList);
    pageList = new ArrayList();
    prv = itm_typ;
    }
    pageList.add(pageDtlMap);



    }
        rs.close();
        pst.close();
    if(!prv.equals(""))
    pageDtl.put(prv,pageList);


    if(pageDtl.size()==0){
        pageDtl=new HashMap();
        pageList=new ArrayList();
        pageDtlMap=new HashMap();
        itm_typ="";form_nme="";fld_nme="";fld_ttl="";fld_typ=""; dflt_val=""; lov_qry=""; val_cond=""; flg1=""; prv="";
        ary=new ArrayList();
        ary.add(pg);
        ary.add(String.valueOf(info.getUsrId()));
        outLst = db.execSqlLst("gtView", gtView, ary);
        pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
        while(rs.next()) {
        pageDtlMap=new HashMap();
        itm_typ=nvl(rs.getString("itm_typ"));
        form_nme=nvl(rs.getString("form_nme"));
        fld_nme=nvl(rs.getString("fld_nme"));
        fld_ttl=nvl(rs.getString("fld_ttl"));
        fld_typ=nvl(rs.getString("fld_typ"));
        dflt_val=nvl(rs.getString("dflt_val"));
        lov_qry=nvl(rs.getString("lov_qry"));
        val_cond=nvl(rs.getString("val_cond"));
        flg1=nvl(rs.getString("flg1"));

        pageDtlMap.put("form_nme",form_nme);
        pageDtlMap.put("fld_nme",fld_nme);
        pageDtlMap.put("fld_ttl",fld_ttl);
        pageDtlMap.put("fld_typ",fld_typ);
        pageDtlMap.put("dflt_val",dflt_val);
        pageDtlMap.put("lov_qry",lov_qry);
        pageDtlMap.put("val_cond",val_cond);
        pageDtlMap.put("flg1",flg1);

        if(prv.equals(""))
        prv = itm_typ;

        if(!prv.equals(itm_typ)){
        pageDtl.put(prv,pageList);
        pageList = new ArrayList();
        prv = itm_typ;
        }
        pageList.add(pageDtlMap);



        }
        rs.close();
        pst.close();
        if(!prv.equals(""))
        pageDtl.put(prv,pageList);
    }
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }
    int pageDtlCnt = 0;
    if(pageDtl != null)
        pageDtlCnt = pageDtl.size();
    SOP("@Util.pageDef : "+ pg + ":" + pageDtlCnt);
    return pageDtl;

    }
    public void alterSession(String schema) {
    String alter="alter session set current_schema="+schema.trim().toUpperCase();
    ArrayList ary = new ArrayList();
    
    db.execUpd("alter", alter, ary);
    }
    
    public ArrayList pagedefblockPrp(String pg,String blocknm) {
    ArrayList pageblockList=new ArrayList();
    String gtView = "Select fld_ttl\n" +
    "From Df_Pg A,Df_Pg_Itm B\n" +
    "Where A.Idn = B.Pg_Idn\n" +
    "And A.Mdl = ?\n" +
    "And B.itm_typ = ?\n" +                
    "And A.Stt='A'\n" +
    "And B.Stt='A'\n" +
    "And A.Vld_Dte Is Null \n" +
    "And Exists (Select 1 From Df_Pg_Itm_Usr C Where B.Idn=C.Itm_Idn And C.Stt='IA' And C.Usr_Idn=?)\n" +
    "order by b.itm_typ,b.srt ";
    ArrayList ary=new ArrayList();
    ary.add(pg);
    ary.add(blocknm);
    ary.add(String.valueOf(info.getUsrId()));
        ArrayList outLst = db.execSqlLst("gtView", gtView, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
    try {
    while(rs.next()) {
    pageblockList.add(nvl(rs.getString("fld_ttl")));
    }
        rs.close();
        pst.close();
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }

    return pageblockList;
    }
    public void pagedefblockPrpLs(String pg,String blocknm) {
    String gtView = "Select fld_ttl\n" +
    "From Df_Pg A,Df_Pg_Itm B\n" +
    "Where A.Idn = B.Pg_Idn\n" +
    "And A.Mdl = ?\n" +
    "And B.itm_typ = ?\n" +                
    "And A.Stt='A'\n" +
    "And B.Stt='A'\n" +
    "And A.Vld_Dte Is Null \n" +
    "And Exists (Select 1 From Df_Pg_Itm_Usr C Where B.Idn=C.Itm_Idn And C.Stt='IA' And C.Usr_Idn=?)\n" +
    "order by b.itm_typ,b.srt ";
    ArrayList ary=new ArrayList();
    ary.add(pg);
    ary.add(blocknm);
    ary.add(String.valueOf(info.getUsrId()));
        ArrayList outLst = db.execSqlLst("gtView", gtView, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
    try {
    while(rs.next()) {
    info.setBlockLs("Y");
    }
        rs.close();
        pst.close();
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }
    }
    public HashMap pagedefXL(String pg,String itm_ty) {
        pg=pg.trim();
        itm_ty = itm_ty.trim();
    HashMap pageDtl=new HashMap();
    String gtView = "select fld_nme,dflt_val from df_pg_itm a, df_pg b where a.pg_idn = b.idn" + 
    " and b.mdl = ? and a.stt=b.stt and a.stt='A' and b.stt='A' and itm_typ=? and a.vld_dte is null order by val_cond";
    ArrayList ary=new ArrayList();
    ary.add(pg);
    ary.add(itm_ty);
        ArrayList outLst = db.execSqlLst("gtView", gtView, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
    try {
    while(rs.next()) {
        pageDtl.put((nvl(rs.getString("fld_nme")).trim()).toUpperCase(), nvl(rs.getString("dflt_val")));
    } 
        rs.close();
        pst.close();
    }catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }
    return pageDtl;
    
    }
    public void initDfUsr(String nmeIdn){
        String usrTyp = "select typ,grp_nme_idn from mnme where vld_dte is null and nme_idn= ? ";
        ArrayList ary = new ArrayList();
        ary.add(nmeIdn);
        ArrayList outLst = db.execSqlLst("usrTyp", usrTyp,ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
         while (rs.next()) {
         info.setDfUsrTyp(rs.getString("typ"));
         info.setDfGrpNmeIdn(nvl(rs.getString("grp_nme_idn")));  
         }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
    }
    
    public String getVendorNm(String nmeIdn){
        String nme="";
        String usrTyp = "select byr.get_nm(?) nm from dual ";
        ArrayList ary = new ArrayList();
        ary.add(nmeIdn);
        ArrayList outLst = db.execSqlLst("usrTyp", usrTyp,ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
         while (rs.next()) {
             nme=nvl(rs.getString("nm"));
         }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        return nme;
    }
    
    public int getIdn(String vnm) {
        int lIdn = 0;
        String getIdn = " select idn from mstk where (vnm = '"+ vnm +"' or tfl3 = '"+ vnm + "') ";
        ArrayList ary = new ArrayList();
        /*
        ary.add(vnm);
        ary.add(vnm);
        */
        ArrayList lst = db.execSqlLst(" Idn ", getIdn, ary);
        PreparedStatement pst = (PreparedStatement)lst.get(0);
        ResultSet rs = (ResultSet)lst.get(1);
        try {
            if(rs != null) {
                if(rs.next()) {
                    lIdn = rs.getInt(1);
                }
                rs.close();
                pst.close();
            }
        } catch (SQLException e) {
        }
        return lIdn ;
    }
    public void initDmbsSysInfo(){
        HashMap dmbsList = new HashMap();
        String sqlDmbsInfo = "select prp , val from dbms_sys_info";
        ArrayList outLst = db.execSqlLst("dmbs", sqlDmbsInfo, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                 dmbsList.put(nvl(rs.getString(1)).trim(),nvl(rs.getString(2)).trim());
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        info.setDmbsInfoLst(dmbsList);
        info.setMem_ip(nvl((String)dmbsList.get("MEM_IP"),"13.229.255.212"));
        info.setMem_port(Integer.parseInt(nvl((String)dmbsList.get("MEM_PORT"),"11211")));
        info.setCnt(nvl((String)dmbsList.get("CNT")));
        
    }
    public void initReport(){
        try {
            ArrayList outLst = db.execSqlLst("reportLink", "select val from dbms_sys_info where prp = 'HOME_DIR'",
                           new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()){
             info.setRportUrl(nvl(rs.getString("val")));
            }
            rs.close();
            pst.close();
            outLst = db.execSqlLst("reportLink", "select val from dbms_sys_info where prp = 'REP_URL'",
                           new ArrayList());
                       pst = (PreparedStatement)outLst.get(0);
                       rs = (ResultSet)outLst.get(1);
                       while(rs.next()){
             info.setWebUrl(nvl(rs.getString("val")));
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
    }
    
    public void initSrch() {

              ResultSet rs = null, rs1 = null;
              ArrayList outLst1 = null;
              PreparedStatement pst1=null;
              ArrayList srchPrpLst = null,soldPrpLst = null, rghScrhLst=null, invPrpLst = null, vwPrpLst = null,dmdPrpLst=null,sortPrpLst=null,

                  refPrpLst=null,srtPrpLst=null , advPrpLst = null , genericPrpLst=null , mixPrpLst=null ,mixScrhLst=null,smxPrpLst=null ,smxScrhLst=null,rghViewLst=null ;

             

              srchPrpLst = new ArrayList();
            
              invPrpLst = new ArrayList();
              ArrayList suggBoxLst = new ArrayList();

              String getSrchPrp = "Select prp , flg from rep_prp where mdl = 'MEMO_SRCH' and flg  in('M','S','CTA','KT') order by rnk ";

        ArrayList outLst = db.execSqlLst(" Srch Prp ", getSrchPrp, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
       try {

         

    

            while (rs.next()) {

                   ArrayList srchMdl = new ArrayList();

                   srchMdl.add(nvl(rs.getString("prp")));

                   srchMdl.add(nvl(rs.getString("flg")));

                   srchPrpLst.add(srchMdl);

           }

           rs.close();
           pst.close();

           if(vwPrpLst == null) {

                 String mdl = info.getUsrId() + "_MEMO_VW";

                   vwPrpLst = new ArrayList();

                   outLst1 = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = '"+mdl+"' and flg='Y' order by rnk ", new ArrayList());
                   pst1 = (PreparedStatement)outLst1.get(0);
                   rs1 = (ResultSet)outLst1.get(1);
                   while(rs1.next()) {

                      

                       vwPrpLst.add(rs1.getString("prp"));

                   }

                   rs1.close();
                   pst1.close();

                   if(vwPrpLst.size()>0) {

                   info.setVwPrpLst(vwPrpLst);

                   info.setVwMdl(mdl);

                   }

               }

              

           if(vwPrpLst.size()==0) {

                

                   vwPrpLst = new ArrayList();
              outLst1 = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'MEMO_VW' and flg='Y' order by rnk ", new ArrayList());
              pst1 = (PreparedStatement)outLst1.get(0);
              rs1 = (ResultSet)outLst1.get(1);
                   while(rs1.next()) {

                      

                       vwPrpLst.add(rs1.getString("prp"));

                   }

              rs1.close();
              pst1.close();

              info.setVwPrpLst(vwPrpLst);

              info.setVwMdl("MEMO_VW");  

          }


            

                  soldPrpLst = new ArrayList();

           outLst1 = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'SOLD_VW' and flg='Y' order by rnk ", new ArrayList());
           pst1 = (PreparedStatement)outLst1.get(0);
           rs1 = (ResultSet)outLst1.get(1);
                   while(rs1.next()) {

                      

                       soldPrpLst.add(rs1.getString("prp"));

                   }

           rs1.close();
           pst1.close();

              info.setSoldPrpLst(soldPrpLst);

              

           

              

         if(refPrpLst == null) {

         

             refPrpLst = new ArrayList();
             outLst1 = db.execSqlLst(" Vw Lst ", " Select prp , flg from rep_prp where mdl = 'REF_VW' and flg='Y' order by rnk ", new ArrayList());
             pst1 = (PreparedStatement)outLst1.get(0);
             rs1 = (ResultSet)outLst1.get(1);
             while(rs1.next()) {

                 refPrpLst.add(rs1.getString("prp"));

              }

             rs1.close();
             pst1.close();

         info.setRefPrpLst(refPrpLst);

         }

           info.setSrchPrpLst(srchPrpLst);

          

           if(srtPrpLst == null) {

            

               srtPrpLst = new ArrayList();

               outLst1 = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'WEB_SRT' and flg='Y' order by rnk ", new ArrayList());
               pst1 = (PreparedStatement)outLst1.get(0);
               rs1 = (ResultSet)outLst1.get(1);
               while(rs1.next()) {

                  

                   srtPrpLst.add(rs1.getString("prp"));

               }

               rs1.close();
               pst1.close();

               info.setSrtPrpLst(srtPrpLst);

           }

           if(advPrpLst == null) {

            

               advPrpLst = new ArrayList();

               outLst1 = db.execSqlLst(" Vw Lst ", "Select prp , flg   from rep_prp where mdl = 'MEMO_ADV' and flg in('M','S') order by rnk ", new ArrayList());
               pst1 = (PreparedStatement)outLst1.get(0);
               rs1 = (ResultSet)outLst1.get(1);
               while(rs1.next()) {

                   ArrayList srchMdl = new ArrayList();

                   srchMdl.add(nvl(rs1.getString("prp")));

                   srchMdl.add(nvl(rs1.getString("flg")));

                   advPrpLst.add(srchMdl);

               }

               rs1.close();
               pst1.close();

               info.setAdvPrpLst(advPrpLst);

           }

          

           if(genericPrpLst == null) {

               genericPrpLst = new ArrayList();
              outLst1 = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'GNC_SRCH' and flg='Y' order by rnk ", new ArrayList());
              pst1 = (PreparedStatement)outLst1.get(0);
              rs1 = (ResultSet)outLst1.get(1);
               while(rs1.next()) {

               genericPrpLst.add(rs1.getString("prp"));

              }

              rs1.close();
              pst1.close();

            info.setGncPrpLst(genericPrpLst);

          }
      
          


           outLst1 = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'SUGGBX_PRP' and flg='Y' order by rnk ", new ArrayList());
           pst1 = (PreparedStatement)outLst1.get(0);
           rs1 = (ResultSet)outLst1.get(1);
               while(rs1.next()) {

               suggBoxLst.add(rs1.getString("prp"));

              }

           rs1.close();
           pst1.close();

            info.setSuggBoxLst(suggBoxLst);

           
          

           if(mixPrpLst == null) {

               mixPrpLst = new ArrayList();


               outLst1 = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'MIX_VW' and flg='Y' order by rnk ", new ArrayList());
               pst1 = (PreparedStatement)outLst1.get(0);
               rs1 = (ResultSet)outLst1.get(1);
               while(rs1.next()) {

               mixPrpLst.add(rs1.getString("prp"));

              }

               rs1.close();
               pst1.close();

            info.setMixPrpLst(mixPrpLst);

           }

           if(smxPrpLst == null) {

               smxPrpLst = new ArrayList();
               outLst1 = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'SMX_VW' and flg='Y' order by rnk ", new ArrayList());
               pst1 = (PreparedStatement)outLst1.get(0);
               rs1 = (ResultSet)outLst1.get(1);
               while(rs1.next()) {

               smxPrpLst.add(rs1.getString("prp"));

              }

               rs1.close();
               pst1.close();

            info.setSmxPrpLst(smxPrpLst);

           }

         if(sortPrpLst == null) {

             sortPrpLst = new ArrayList();

             outLst1 = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'SORT_MDL' and flg='Y' order by rnk ", new ArrayList());
             pst1 = (PreparedStatement)outLst1.get(0);
             rs1 = (ResultSet)outLst1.get(1);
             while(rs1.next()) {

             sortPrpLst.add(rs1.getString("prp"));

            }

             rs1.close();
             pst1.close();

          info.setSortPrpLst(sortPrpLst);

         }

           if(mixScrhLst == null) {

               mixScrhLst = new ArrayList();

               outLst1 = db.execSqlLst(" Vw Lst ", "Select prp , flg from rep_prp where mdl = 'MIX_SRCH' and flg  in('M','S','CTA') order by rnk ", new ArrayList());
               pst1 = (PreparedStatement)outLst1.get(0);
               rs1 = (ResultSet)outLst1.get(1);
               while(rs1.next()) {

                  ArrayList srchMdl = new ArrayList();

                  srchMdl.add(nvl(rs1.getString("prp")));

                  srchMdl.add(nvl(rs1.getString("flg")));

                  mixScrhLst.add(srchMdl);

              }

               rs1.close();
               pst1.close();

            info.setMixScrhLst(mixScrhLst);

           }

          
           if(rghScrhLst == null) {

               rghScrhLst = new ArrayList();

               outLst1 = db.execSqlLst(" Vw Lst ", "Select prp , flg from rep_prp where mdl = 'RGHMKT_SRCH' and flg  in('M','S','CTA','SM') order by rnk ", new ArrayList());
               pst1 = (PreparedStatement)outLst1.get(0);
               rs1 = (ResultSet)outLst1.get(1);
               while(rs1.next()) {

                  ArrayList srchMdl = new ArrayList();

                  srchMdl.add(nvl(rs1.getString("prp")));

                  srchMdl.add(nvl(rs1.getString("flg")));

                  rghScrhLst.add(srchMdl);

              }

               rs1.close();
               pst1.close();

            info.setRghScrhLst(rghScrhLst);

           }

           if(rghViewLst == null) {

               rghViewLst = new ArrayList();

               outLst1 = db.execSqlLst(" Vw Lst ", "Select prp , flg from rep_prp where mdl = 'RGHMKT_VW' and flg  in ('Y') order by rnk ", new ArrayList());
               pst1 = (PreparedStatement)outLst1.get(0);
               rs1 = (ResultSet)outLst1.get(1);
               while(rs1.next()) {

                
                  rghViewLst.add(nvl(rs1.getString("prp")));

              }
               rs1.close();
               pst1.close();

            info.setRghVwList(rghViewLst);

           }

           

           if(smxScrhLst == null) {

               smxScrhLst = new ArrayList();

               outLst1 = db.execSqlLst(" Vw Lst ", "Select prp , flg from rep_prp where mdl = 'SMX_SRCH' and flg  in('M','S','CTA') order by rnk ", new ArrayList());
               pst1 = (PreparedStatement)outLst1.get(0);
               rs1 = (ResultSet)outLst1.get(1);
               while(rs1.next()) {

                  ArrayList srchMdl = new ArrayList();

                  srchMdl.add(nvl(rs1.getString("prp")));

                  srchMdl.add(nvl(rs1.getString("flg")));

                  smxScrhLst.add(srchMdl);

              }

               rs1.close();
               pst1.close();

            info.setSmxScrhLst(smxScrhLst);

           }
           
           ArrayList allowPrp=new ArrayList();

           outLst1 = db.execSqlLst(" Vw Lst ", "Select prp , flg  from rep_prp where mdl = 'ALLOW_PLMN' and  flg <> 'N' order by rnk ",new ArrayList());
           pst1 = (PreparedStatement)outLst1.get(0);
           rs1 = (ResultSet)outLst1.get(1);
                          while (rs1.next()) {
                              
                              allowPrp.add(rs1.getString("prp"));
                          }
           rs1.close();
           pst1.close();
           info.setAllowPlMnLst(allowPrp);

           ArrayList prpDspBlocked = info.getPrpDspBlocked();

        

           info.setPrpDspBlocked(prpDspBlocked);

          

       } catch (SQLException sqle) {

           // TODO: Add catch code

           sqle.printStackTrace();

       }

    }
  
  public ArrayList getMemoXl(String mdl){
     
          mdl = mdl.replaceAll(" ", "");
          ArrayList vwPrpLst = new ArrayList();
          ArrayList ary= new ArrayList();
          
      ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where trim(mdl)= trim('"+mdl+"') and flg='Y' order by rnk ", ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {

                vwPrpLst.add(rs.getString("prp"));
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
      return vwPrpLst;
  }
  
//    public ArrayList imageDtls(){
//    ArrayList ImaageDtls= (info.getImagelistDtl() == null)?new ArrayList():(ArrayList)info.getImagelistDtl();
//    try {
//    if(ImaageDtls.size() == 0) {
//    String gtView = "select chr_fr path, chr_to nmefmt, dsc gtcol,rem nme,lvl allowon,dta_typ typ,nvl(dsc_direct,dsc) hdr,result_map  from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and \n" + 
//    "b.mdl = 'JFLEX' and b.nme_rule = 'IMAGE_EXISTS' and a.til_dte is null order by a.srt_fr";
//    ResultSet rs = db.execSql("gtView", gtView, new ArrayList());
//    while (rs.next()) {
//    HashMap dtls=new HashMap();
//        dtls.put("PATH",nvl(rs.getString("path")));
//        dtls.put("NMEFMT",nvl(rs.getString("nmefmt")));
//        dtls.put("GTCOL",nvl(rs.getString("gtcol")));
//        dtls.put("NME",nvl(rs.getString("nme")));
//        dtls.put("ALLOWON",nvl(rs.getString("allowon")));
//        dtls.put("TYP",nvl(rs.getString("typ")));
//        dtls.put("HDR",nvl(rs.getString("hdr")));
//        dtls.put("DNA",nvl(rs.getString("result_map")));
//        ImaageDtls.add(dtls);    
//    }
//    rs.close();
//    info.setImagelistDtl(ImaageDtls);
//    }
//    } catch (SQLException sqle) {
//    // TODO: Add catch code
//    sqle.printStackTrace();
//    }
//    return ImaageDtls;
//    }
//  public void initPrp() {
//      HashMap dbinfo = info.getDmbsInfoLst();
//      try{
//      String cnt=nvl((String)dbinfo.get("CNT"));
//      String mem_ip=nvl((String)dbinfo.get("MEM_IP"),"13.229.255.212");
//      int mem_port=Integer.parseInt(nvl((String)dbinfo.get("MEM_PORT"),"11211"));
//      MemcachedClient mcc = new MemcachedClient(new InetSocketAddress(mem_ip, mem_port));
//      HashMap mprp=(HashMap)mcc.get(cnt+"_setMprp");
//      HashMap prp=(HashMap)mcc.get(cnt+"_setPrp");
//      if(mprp==null || prp==null){    
//      ResultSet rs = null;
//      ArrayList prpLst = null, prpSrt = null,prpNum=null, prpVal = null, prpPrt = null, prpSrtPM = null, prpValPM = null ,  prpPrt2=null,prpGrp=null,prpDsc=null;
//      String prvPrp = "";
//      
//      mprp = new HashMap();
//      prp = new HashMap();
//      prpLst = new ArrayList();
//      prpSrt = new ArrayList();
//      prpVal = new ArrayList();
//      prpPrt = new ArrayList();
//      prpDsc = new ArrayList();
//      prpGrp = new ArrayList();
//      prpSrtPM = new ArrayList();
//      prpValPM = new ArrayList();
//      prpPrt2 = new ArrayList();
//      ArrayList prpBse = new ArrayList();
//      
//      
//      String getMPrp = 
//          "select prp,initcap(dsc) dsc, srt, initcap(nvl(prt_nme, prp)) prt, dta_typ from mprp where 1 = 1 "+
//          " and trunc(nvl(vld_till, sysdate)) <= trunc(sysdate)  "+
//          " order by srt ";
//      
//      String getPrp = 
//          "select a.mprp prp, a.srt srt, a.val val,a.dsc, a.prt2 , upper(nvl(a.prt1,a.val)) prt,nvl(a.grp,a.val) grp,a.num, b.dta_typ typ"+
//          ", lag(a.mprp, 1) over (order by b.srt, a.srt) p_prp  "+
//          " from prp a, mprp b "+
//          " where a.mprp = b.prp "+
//          " and trunc(nvl(b.vld_till, sysdate)) <= trunc(sysdate)  "+
//          " and trunc(nvl(a.vld_till, sysdate)) <= trunc(sysdate)  "+
//          " and b.dta_typ = 'C' "+
//          
//          " order by b.srt, a.srt";
//
//      rs = db.execSql(" Mst Prp Lst", getMPrp, new ArrayList());
//      try {
//          String lprp=null, lval=null, lprt=null, ltyp=null , lprt2=null,lnum=null,lgrp=null,ldsc=null;
//          int lsrt;
//          while(rs.next()) {
//              lsrt = rs.getInt("srt");  
//              lprt = nvl(rs.getString("prt"));
//              lprp = nvl(rs.getString("prp"));
//           
//              String dtaTyp = nvl(rs.getString("dta_typ"));
//              //logger.log(Level.INFO, lprp+"~"+lprt+"~"+lsrt);
//              mprp.put(lprp+"D", nvl(rs.getString("dsc")));
//              mprp.put(lprp, lprt);
//              mprp.put(lprp+"S", Integer.toString(lsrt));
//              mprp.put(lprp+"T", dtaTyp);
//           
//              prpLst.add(lprp);
//          }
//          rs.close();
//          rs = db.execSql(" Entire Prp Lst", getPrp, new ArrayList());
//          while(rs.next()) {
//              lprp = nvl(rs.getString("prp"));
//              ltyp = nvl(rs.getString("typ"));
//              prvPrp = nvl(rs.getString("p_prp"));
//             
//              
//              if(!(lprp.equals(prvPrp))) {
//                  if(prpSrt.size() > 0) {
//                      prp.put(prvPrp+"S", prpSrt);
//                      prp.put(prvPrp+"V", prpVal);
//                      prp.put(prvPrp+"P", prpPrt);
//                      prp.put(prvPrp+"P2", prpPrt2);
//                      prp.put(prvPrp+"B", prpBse);
//                      prp.put(prvPrp+"N", prpNum);
//                      prp.put(prvPrp+"G", prpGrp);
//                      prp.put(prvPrp+"D", prpDsc);
//                  }
//                  prpSrt = new ArrayList();
//                  prpVal = new ArrayList();
//                  prpPrt = new ArrayList();
//                  prpBse = new ArrayList();
//                  prpPrt2 = new ArrayList();
//                  prpNum = new ArrayList();
//                  prpGrp = new ArrayList();
//                  prpDsc = new ArrayList();
//                  prvPrp = lprp ;
//              }
//              if(ltyp.equals("C")) {
//                  lsrt = rs.getInt("srt");
//                  lval = nvl(rs.getString("val"));
//                  lprt = nvl(rs.getString("prt"));
//                  lprt2 = nvl(rs.getString("prt2"));
//                  lnum = nvl(rs.getString("num"));
//                  lgrp = nvl(rs.getString("grp"));
//                  ldsc = nvl(rs.getString("dsc"));
//                  prpSrt.add(Integer.toString(lsrt));
//                  prpVal.add(lval);
//                  prpPrt.add(lprt);
//                  prpPrt2.add(lprt2);
//                  prpNum.add(lnum);
//                  prpGrp.add(lgrp);
//                  prpDsc.add(ldsc);
//                  if(lval.indexOf("+") > -1) {
//                      
//                  } else if (lval.indexOf("-") > -1) {
//                      
//                  } else
//                      prpBse.add(lval);
//              }
//              
//          }
//          rs.close();
//          if(prpSrt.size() > 0) {
//              prp.put(lprp+"S", prpSrt);
//              prp.put(lprp+"V", prpVal);
//              prp.put(lprp+"P", prpPrt);
//              prp.put(lprp+"B", prpBse);
//              prp.put(lprp+"P2", prpPrt2);
//              prp.put(prvPrp+"N", prpNum);
//              prp.put(prvPrp+"G", prpGrp);
//              prp.put(prvPrp+"D", prpDsc);
//          }
//          prp.put("PRP", prpLst);
//          mprp.put("ALL", prpLst);
//
//          //rs = db.execSql(" View Prp", " select ")
//      } catch (SQLException e) {
//          log.log(Level.SEVERE, " Prp Lst not available");
//      }
//      
//      //initSrch();
//      Future fo = mcc.delete(cnt+"_setMprp");
//      System.out.println("add status:_setMprp" + fo.get());
//      fo = mcc.delete(cnt+"_setPrp");
//      System.out.println("add status:_setPrp" + fo.get());
//      fo = mcc.set(cnt+"_setMprp", 24*60*60, mprp);
//      System.out.println("add status:_setMprp" + fo.get());
//      fo = mcc.set(cnt+"_setPrp", 24*60*60,prp);
//      System.out.println("add status:_setPrp" + fo.get());
//      }
//      mcc.shutdown();
//      initPrcPrp();
//      info.setPrp(prp);
//      info.setMprp(mprp);
//      }catch(Exception ex){
//         System.out.println( ex.getMessage() );
//      }
//      //SOP(mprp.toString());
//      //SOP(prp.toString());
//  }
  
//    public void initPrpSrch() {
//        HashMap dbinfo = info.getDmbsInfoLst();
//        try{
//        String cnt=nvl((String)dbinfo.get("CNT"));
//        String mem_ip=nvl((String)dbinfo.get("MEM_IP"),"13.229.255.212");
//        int mem_port=Integer.parseInt(nvl((String)dbinfo.get("MEM_PORT"),"11211"));
//        MemcachedClient mcc = new MemcachedClient(new InetSocketAddress(mem_ip, mem_port));
//        HashMap prp=(HashMap)mcc.get(cnt+"_setSrchPrp");
//        HashMap mprp=(HashMap)mcc.get(cnt+"_setSrchMprp");
//        if(mprp==null || prp==null){  
//        ResultSet rs = null;
//        ArrayList prpLst = null, prpSrt = null, prpVal = null, prpPrt = null, prpSrtPM = null, prpValPM = null,prpGrp=null;
//        String prvPrp = "";
//        
//        mprp = new HashMap();
//        prp = new HashMap();
//        prpLst = new ArrayList();
//        prpSrt = new ArrayList();
//        prpVal = new ArrayList();
//        prpPrt = new ArrayList();
//        prpGrp = new ArrayList();
//        prpSrtPM = new ArrayList();
//        prpValPM = new ArrayList();
//        
//        ArrayList prpBse = new ArrayList();
//        
//        
//        String getMPrp = 
//            "select prp,initcap(dsc) dsc, srt, initcap(nvl(prt_nme, prp)) prt, dta_typ from mprp where 1 = 1 "+
//            " and trunc(nvl(vld_till, sysdate)) <= trunc(sysdate)  "+
//            " order by srt ";
//        
//        String getPrp = 
//            "select a.mprp prp, a.srt srt, a.val val, upper(nvl(a.prt1,a.val)) prt,nvl(a.grp,a.val) grp, b.dta_typ typ"+
//            ", lag(a.mprp, 1) over (order by b.srt, a.srt) p_prp  "+
//            " from prp a, mprp b "+
//            " where a.mprp = b.prp "+
//            " and trunc(nvl(b.vld_till, sysdate)) <= trunc(sysdate)  "+
//            " and trunc(nvl(a.vld_till, sysdate)) <= trunc(sysdate)  "+
//            " and b.dta_typ = 'C' "+
//            " and a.flg is null "+
//            " order by b.srt, a.srt";
//
//        rs = db.execSql(" Mst Prp Lst", getMPrp, new ArrayList());
//        try {
//            String lprp=null, lval=null, lprt=null, ltyp=null,lgrp=null;
//            int lsrt;
//            while(rs.next()) {
//                lsrt = rs.getInt("srt");  
//                lprt = nvl(rs.getString("prt"));
//                lprp = nvl(rs.getString("prp"));
//                String dtaTyp = nvl(rs.getString("dta_typ"));
//                //logger.log(Level.INFO, lprp+"~"+lprt+"~"+lsrt);
//                mprp.put(lprp+"D", nvl(rs.getString("dsc")));
//                mprp.put(lprp, lprt);
//                mprp.put(lprp+"S", Integer.toString(lsrt));
//                mprp.put(lprp+"T", dtaTyp);
//                prpLst.add(lprp);
//            }
//            rs.close();
//            rs = db.execSql(" Entire Prp Lst", getPrp, new ArrayList());
//            while(rs.next()) {
//                lprp = rs.getString("prp");
//                ltyp = rs.getString("typ");
//                prvPrp = rs.getString("p_prp");
//                if(!(lprp.equals(prvPrp))) {
//                    if(prpSrt.size() > 0) {
//                        prp.put(prvPrp+"S", prpSrt);
//                        prp.put(prvPrp+"V", prpVal);
//                        prp.put(prvPrp+"P", prpPrt);
//                        prp.put(prvPrp+"B", prpBse);
//                        prp.put(prvPrp+"G", prpGrp);
//                    }
//                    prpSrt = new ArrayList();
//                    prpVal = new ArrayList();
//                    prpPrt = new ArrayList();
//                    prpBse = new ArrayList();
//                    prpGrp = new ArrayList();
//                    prvPrp = lprp ;
//                }
//                if(ltyp.equals("C")) {
//                    lsrt = rs.getInt("srt");
//                    lval = rs.getString("val");
//                    lprt = rs.getString("prt");
//                    lgrp = rs.getString("grp");
//                    
//                    prpSrt.add(Integer.toString(lsrt));
//                    prpVal.add(lval);
//                    prpPrt.add(lprt);
//                    prpGrp.add(lgrp);
//                    if(lval.indexOf("+") > -1) {
//                        
//                    } else if (lval.indexOf("-") > -1) {
//                        
//                    } else
//                        prpBse.add(lval);
//                }
//                
//            }
//            rs.close();
//            if(prpSrt.size() > 0) {
//                prp.put(lprp+"S", prpSrt);
//                prp.put(lprp+"V", prpVal);
//                prp.put(lprp+"P", prpPrt);
//                prp.put(lprp+"B", prpBse);
//                prp.put(prvPrp+"G", prpGrp);
//            }
//            prp.put("PRP", prpLst);
//            mprp.put("ALL", prpLst);
//
//            //rs = db.execSql(" View Prp", " select ")
//        } catch (SQLException e) {
//            log.log(Level.SEVERE, " Prp Lst not available");
//        }
//        
//            Future fo = mcc.delete(cnt+"_setSrchPrp");
//            System.out.println("add status:_setSrchPrp" + fo.get());
//            fo = mcc.delete(cnt+"_setSrchMprp");
//            System.out.println("add status:_setSrchMprp" + fo.get());
//            fo = mcc.set(cnt+"_setSrchPrp", 24*60*60, prp);
//            System.out.println("add status:_setSrchPrp" + fo.get());
//            fo = mcc.set(cnt+"_setSrchMprp", 24*60*60,mprp);
//            System.out.println("add status:_setSrchMprp" + fo.get());
//        }
//        mcc.shutdown();
//        info.setSrchPrp(prp);
//        info.setSrchMprp(mprp);
//        //SOP(mprp.toString());
//        //SOP(prp.toString());
//        }catch(Exception ex){
//           System.out.println( ex.getMessage() );
//        }
//    }
    
  public void initPrcPrp() {
      HashMap dbinfo = info.getDmbsInfoLst();
      try{
      String cnt=nvl((String)dbinfo.get("CNT"));
      String mem_ip=nvl((String)dbinfo.get("MEM_IP"),"13.229.255.212");
      int mem_port=Integer.parseInt(nvl((String)dbinfo.get("MEM_PORT"),"11211"));
      MemcachedClient mcc = new MemcachedClient(new InetSocketAddress(mem_ip, mem_port));
      HashMap prc_prp=(HashMap)mcc.get(cnt+"_setPrcPrp");
      if(prc_prp==null){      
      String getPrcPrp = " select a.mdl, a.prp from rep_prp a " +
          "   where exists (select 1 from mrule b, rule_dtl c " +
          "       where b.rule_idn = c.rule_idn and a.mdl = c.chr_fr and b.mdl = 'PRICING' and b.nme_rule = 'PRP_MDL') "+
          "   order by mdl, rnk, srt ";
      
      prc_prp = new HashMap();
      ArrayList vals = new ArrayList();
      String key = "" ;   
          ArrayList outLst = db.execSqlLst(" Prc Prp Lst", getPrcPrp, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
      try {
          while(rs.next()) {
              String mdl = rs.getString("mdl");
              String prp = rs.getString("prp");
              if(rs.isFirst())
                  key = mdl;
              
              if(!(key.equals(mdl))) {
                  prc_prp.put(key, vals);
                  setInfoPrp(key, vals);
                  key = mdl;
                  vals = new ArrayList();
              } 
              vals.add(prp);     
          }
          rs.close();
          pst.close();
          prc_prp.put(key, vals);
          setInfoPrp(key, vals);
      } catch (SQLException e) {
      }
          Future fo = mcc.delete(cnt+"_setPrcPrp");
          System.out.println("add status:_setPrcPrp" + fo.get());
          fo =mcc.set(cnt+"_setPrcPrp", 24*60*60, prc_prp);
          System.out.println("add status:_setPrcPrp" + fo.get());
      }
      mcc.shutdown();
      info.setPrcPrp(prc_prp);
      initGrpDtls(cnt,mem_ip,mem_port);
      }catch(Exception ex){
         System.out.println( ex.getMessage() );
      }

  }
  
  public HashMap getPrcDefnLst() {
      HashMap defnLst = new HashMap();
      ArrayList keys = new ArrayList();
      String qry = " select distinct nme prmnme, idn from dyn_mst_t where flg in ('NR', 'DP') order by srt, idn";
      ArrayList outLst = db.execSqlLst(" Mat Lst", qry, new ArrayList());
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
      try {
          while(rs.next()) {
              String nme = rs.getString("prmnme");
              String id = rs.getString("idn");
              keys.add(id);
              defnLst.put(id, nme);
          }
          rs.close();
          pst.close();
      } catch (SQLException e) {
      }
      defnLst.put("KEYS", keys);
      return defnLst;
  }
  public void setInfoPrp(String key, ArrayList vals) {
      if(key.equals("PRC_BSE_PRP")) 
          info.setPrcBsePrp(vals);
      if(key.equals("PRC_DIS_PRP")) 
          info.setPrcDisPrp(vals);
      if(key.equals("PRC_REF_PRP")) 
          info.setPrcRefPrp(vals);
  }
  
  public ArrayList getGrpPrp(String grpNme) {
      String getDtlPrp = "Select mprp from prc_defn_grp_dtl where upper(grp_nme) = upper(?) and stt = 'A' order by srt";
      ArrayList params = new ArrayList();
      ArrayList grpPrp = new ArrayList();
      
      params.add(grpNme);
      ArrayList outLst = db.execSqlLst(" Grp Prp "+grpNme, getDtlPrp, params);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
      try {
          while(rs.next()) {
              grpPrp.add(rs.getString(1));
          }
          rs.close();
          pst.close();
      } catch (SQLException e) {
      }
      return grpPrp ;
  }
  
  public void initGrpDtls(String cnt,String mem_ip,int mem_port) {
      try{
      MemcachedClient mcc = new MemcachedClient(new InetSocketAddress(mem_ip, mem_port));
      HashMap grpDtls=(HashMap)mcc.get(cnt+"_setGrpDtls");
      if(grpDtls==null){    
      grpDtls = new HashMap();
      String getDtls = "select grp_nme, srt, prmtyp, pct_val, defn_grp_id, defn_grp_srt from prc_defn_grp order by defn_grp_id, defn_grp_srt, srt, grp_nme ";
          ArrayList outLst = db.execSqlLst("GrpDtl", getDtls, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
      try {
          while(rs.next()) {
              String grpNme = rs.getString("grp_nme");
              String grpSrt = nvl2(rs.getString("srt"), "0");
              String prmTyp = nvl2(rs.getString("prmtyp"), "NR");
              String pctVal = nvl2(rs.getString("pct_val"), "PCT");
              String defnGrpId = nvl2(rs.getString("defn_grp_id"),"0");
              String defnGrpSrt = nvl2(rs.getString("defn_grp_srt"),"0");
              
              grpDtls.put(grpNme+"S", grpSrt);
              grpDtls.put(grpNme+"T", prmTyp);
              grpDtls.put(grpNme+"V", pctVal);
              grpDtls.put(grpNme+"GI", defnGrpId);
              grpDtls.put(grpNme+"GS", defnGrpSrt);
          }
          rs.close();
          pst.close();
      } catch (SQLException e) {
      }
          Future fo = mcc.delete(cnt+"_setGrpDtls");
          System.out.println("add status:_setGrpDtls" + fo.get());
          fo =mcc.set(cnt+"_setGrpDtls", 24*60*60, grpDtls);
          System.out.println("add status:_setGrpDtls" + fo.get());
      }
      info.setGrpDtls(grpDtls);
      mcc.shutdown();
      }catch(Exception ex){
         System.out.println( ex.getMessage() );
      }
      //return grpDtls;
  }
  
  public HashMap getPrmTyp() {
      HashMap prmTyp = new HashMap();
      ArrayList keys = new ArrayList();
      ArrayList vals = new ArrayList();
      String getPrmTyp = " select b.dsc, b.chr_fr from mrule a, rule_dtl b " +
          " where a.rule_idn = b.rule_idn and a.mdl = 'PRICING' and a.nme_rule = 'PRMTYP' order by srt_fr";
      ArrayList outLst = db.execSqlLst("PrmTyp", getPrmTyp, new ArrayList());
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
      try {
          while(rs.next()) {
              keys.add(rs.getString("dsc"));
              vals.add(rs.getString("chr_fr"));
          }
          rs.close();
          pst.close();
      } catch (SQLException e) {
      }
      prmTyp.put("K", keys);
      prmTyp.put("V", vals);
      return prmTyp ;
  }
  public HashMap getMatrixDtl(int matIdn , String dbStr) {
      if(dbStr.equals("")){
            ArrayList outLst = db.execSqlLst("pri_prop", "select user from dual", new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        try {
            if (rs.next()){
                dbStr = rs.getString(1);
                dbStr = dbStr+".";
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }}
      String matNme = "";
      String getNme = " select nme from "+dbStr+"dyn_mst_t where idn = ?";
      ArrayList params = new ArrayList();
      params.add(Integer.toString(matIdn));
      ArrayList outLst = db.execSqlLst("get Nme", getNme, params);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
      try {
          if(rs.next()) {
          matNme = rs.getString(1);
      }
          rs.close();
          pst.close();
      } catch (SQLException e) {
      }
      return getMatrixDtl(matIdn, matNme , dbStr);
  }
  
  
  public HashMap getMatrixDtl(String matNme) {
      int matIdn = 0;
      String getNme = " select nme from dyn_mst_t where nme = ?";
      ArrayList params = new ArrayList();
      params.add(matNme);
      ArrayList outLst = db.execSqlLst("get Nme", getNme, params);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
      try {
          if(rs.next()) {
          matIdn = rs.getInt(1);
      }
          rs.close();
          pst.close();
      } catch (SQLException e) {
      }
      return getMatrixDtl(matIdn, matNme,"");
  }
  
  public HashMap getMatrixDtl(int matIdn, String matNme ,String dbStr ) {
      HashMap matDtl = new HashMap();
      ArrayList params = null;
      ResultSet rs = null;
      String getCmn = " select mprp, decode(val_fr, 'NA', to_char(num_fr), val_fr) val_fr "+
          ", decode(val_to, 'NA', to_char(num_to), val_to) val_to"+
          " from "+dbStr+"dyn_cmn_t where idn = ? and val_fr is not null and val_to is not null";
      params = new ArrayList();
      params.add(Integer.toString(matIdn));
      ArrayList outLst = db.execSqlLst(" Cmn Data", getCmn, params);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      rs = (ResultSet)outLst.get(1);
      try {
          while(rs.next()) {
              String lprp = nvl(rs.getString("mprp"));
              String valFr = nvl(rs.getString("val_fr"));
              String valTo = nvl(rs.getString("val_to"));
              matDtl.put(lprp + "_FR", valFr);        
              matDtl.put(lprp + "_TO", valTo);        
          }
          rs.close();
          pst.close();
          String getMat = " select nvl(nvl(pct, vlu),0) pct, CO, PU from "+dbStr+"mprm_dis a, "+dbStr+"PRM_DIS_MAT_V b " +
              " where a.idn = b.mprm_dis_idn and a.prmnme = ?  ";
          params = new ArrayList();
          params.add(matNme);
          outLst = db.execSqlLst(" Cmn Data", getMat, params);
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
          while(rs.next()) {
              String lco = nvl(rs.getString("CO"));
              String lpu = nvl(rs.getString("PU"));
              String lval = nvl(rs.getString("pct"));
              matDtl.put(lco + "_" + lpu, lval);
          }
          rs.close();
          pst.close();
          String getRefMat = " select nvl(nvl(pct, vlu),0) pct, b.mprp, b.val_fr " +
              " from "+dbStr+"mprm_dis a, "+dbStr+"prm_dis_dtl b"+
          " where a.idn = b.mprm_dis_idn and a.ref_nme = ?  and b.val_fr is not null";
          params = new ArrayList();
          params.add(matNme);
          outLst = db.execSqlLst(" Cmn Data", getRefMat, params);
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
          while(rs.next()) {
              String lprp = nvl(rs.getString("mprp"));
              String lval = nvl(rs.getString("val_fr"));
              String lpct = nvl(rs.getString("pct"));
              matDtl.put("ref_" + lprp + "_" + lval, lpct);
          }
          rs.close();
          pst.close();
          
          String getCnsData = "select mprp, decode(val, 'NA', to_char(num_cn), val) val " +
              " from "+dbStr+"cns_prm_dis a, "+dbStr+"mprm_dis b, "+dbStr+"dyn_mst_t c where " +
              " a.mprm_dis_idn = b.idn and b.prmnme = c.nme and c.nme = ? ";
          
        params = new ArrayList();
        params.add(matNme);
         outLst = db.execSqlLst(" Cns Data", getCnsData, params);
         pst = (PreparedStatement)outLst.get(0);
         rs = (ResultSet)outLst.get(1);
        while(rs.next()) {
            String lprp = nvl(rs.getString("mprp"));
            String lval = nvl(rs.getString("val"));
            matDtl.put("cns_" + lprp, lval);
        }
          rs.close();
          pst.close();
          
      } catch (SQLException e) {
      }
      return matDtl;
  }
  
  public String calcRapVal(HashMap matDtl, String lCO, String lPU, double val, double chng) {
      String rapVal = "0" ;
      int rapRte = 1 ;
      String sh = nvl2((String)matDtl.get("SH_FR"), "RD");
      String cts = nvl((String)matDtl.get("CRTWT_FR"));
      if(cts.length() == 0) {
          cts = nvl((String)matDtl.get("SZ_FR"));    
          if(cts.length() == 0)
              cts = nvl((String)matDtl.get("GSZ_FR"));    
          if(cts.length() == 0)
              cts = "1";
      }
      
      String calcRapRte = " select rap_prc(?,?,?,?,1) rapRte from dual ";
      ArrayList params = new ArrayList();
      params.add(sh);
      params.add(cts);
      params.add(lPU);
      params.add(lCO);
      
      ArrayList outLst = db.execSqlLst(" get RapRte", calcRapRte, params);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
      try {
          
          if(rs.next()) {
              //SOP(sh + "," + cts + "," + lPU + "," + lCO + "," + "," + val + "," + rapRte);
              rapRte = rs.getInt(1);
              String rapQ = " select trunc((1 - ((1 - (?/?))+(?/100)))*?,2) nwVal from dual";    
              params = new ArrayList();
              params.add(Double.toString(val));
              params.add(Integer.toString(rapRte));
              params.add(Double.toString(chng));
              params.add(Integer.toString(rapRte));
              ArrayList outLst1 = db.execSqlLst(" get RapDis", rapQ, params);
              PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
              ResultSet rs1 = (ResultSet)outLst1.get(1);
              if(rs1.next()) {
                  rapVal = rs1.getString("nwVal");
              }
              rs1.close();
              pst1.close();
          }
          rs.close();
          pst.close();
          
      } catch (SQLException e) {
          SOP(sh + "," + cts + "," + lPU + "," + lCO + "," + "," + val + "," + rapRte);
      }
      return rapVal;
  }
  public void initSrch(String pricing) {
      ResultSet rs = null, rs1 = null;
      ArrayList srchPrpLst = null, invPrpLst = null, vwPrpLst = null,dmdPrpLst=null;
      
      srchPrpLst = new ArrayList();
      invPrpLst = new ArrayList();
      String getSrchPrp = "Select prp from rep_prp where mdl = 'PRC_DIS_PRP' and flg = 'Y' order by rnk ";
      ArrayList outLst = db.execSqlLst(" Srch Prp ", getSrchPrp, new ArrayList());
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      rs = (ResultSet)outLst.get(1);
      if(rs != null) {
          try {
              while(rs.next()) {
                  srchPrpLst.add(rs.getString("prp"))  ;  
              }
              rs.close();
              pst.close();
          } catch (SQLException e) {
               log.log(Level.WARNING, " Search Property not loaded..."+e);  
          }
          info.setSrchPrpLst(srchPrpLst);
          info.setInvPrpLst(invPrpLst);
      }
  }

    public void initMdls() {
        ArrayList mdls = new ArrayList();
        ArrayList params ;
        mdls.add("MEMO_RTRN");
        mdls.add("MEMO");
        for(int i=0; i < mdls.size(); i++){
            String lMdl = (String)mdls.get(i);
            String getMdlPrp = " select prp from rep_prp where mdl = ? and flg<> 'N' order by srt, rnk";
            params = new ArrayList();
            params.add(lMdl);
            ArrayList outLst = db.execSqlLst(" Mdl Prp", getMdlPrp, params);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            ArrayList prps = new ArrayList();
            try {
                while(rs.next()) {
                    prps.add(rs.getString("prp"));
                }
                rs.close();
                pst.close();
            } catch (SQLException e) {
            }
            info.setValue("MDL_"+lMdl, prps);
        }
    }
    public void setMdl(String mdl) {
        ArrayList params = null;
        String sql = null;
        int ct = 0 ;
        /*
        sql = " pack_var.set_usr_mch(?)";
        params = new ArrayList();
        params.add(util.nvl(request.getRemoteUser(), info.getUsr()) + "@" + request.getRemoteHost());
        ct = db.execCall("set usr mch", sql, params);
        
        sql = " pack_var.set_usr_ip(?)";
        params = new ArrayList();
        params.add(request.getRemoteAddr());
        ct = db.execCall("set usr ip", sql, params);
        */
        sql = " pack_var.set_usr_mdl(?)";
        params = new ArrayList();
        params.add(info.getUsr()+"@jFlex:"+mdl);
        ct = db.execCall("set usr mch", sql, params);

    }
    
    public void initMenuLink(){
        HashMap dbinfo = info.getDmbsInfoLst();
        String diaflexMode = nvl((String)dbinfo.get("DIAFLEX_MODE"));
        ArrayList roleLst = new ArrayList();
        ArrayList rolenmLst = new ArrayList();
        ArrayList orgroleLst = new ArrayList();
        ArrayList orgrolenmLst = new ArrayList();
        String orgrols="";
        ResultSet rs =null;
        ArrayList ary = new ArrayList();
        ArrayList outLst =null;
        PreparedStatement pst =null;
        if(!diaflexMode.equalsIgnoreCase("S")){
        ary.add(String.valueOf(info.getUsrId()));
            outLst = db.execSqlLst("role", "select a.df_role_idn roleidn,b.role_nm rolenm from df_usr_role a,df_mrole b where  a.df_usr_idn=? and a.df_role_idn=b.df_role_idn and a.stt='A' and a.to_dte is null",ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
             roleLst.add(rs.getString("roleidn"));
             rolenmLst.add(rs.getString("rolenm"));
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        if(roleLst.size()==0){
            roleLst.add("103");
            rolenmLst.add("CONTACTS");
        }
        }
        if(diaflexMode.equalsIgnoreCase("S")){
            ary = new ArrayList();
            roleLst = new ArrayList();
            rolenmLst = new ArrayList();
            outLst = db.execSqlLst("role", "select df_role_idn roleidn,role_nm rolenm from df_mrole where role_nm='MODE'",ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
            try {
            while (rs.next()) {
            roleLst.add(rs.getString("roleidn"));
            rolenmLst.add(rs.getString("rolenm"));
            }
                rs.close();
                pst.close();
                
                ary = new ArrayList();
                ary.add(String.valueOf(info.getUsrId()));
                
                outLst = db.execSqlLst("role", "select a.df_role_idn roleidn,b.role_nm rolenm from df_usr_role a,df_mrole b where  a.df_usr_idn=? and a.df_role_idn=b.df_role_idn and a.stt='A' and a.to_dte is null",ary);
                pst = (PreparedStatement)outLst.get(0);
                rs = (ResultSet)outLst.get(1);
                while (rs.next()) {
                 orgroleLst.add(rs.getString("roleidn"));
                 orgrolenmLst.add(rs.getString("rolenm"));
                }
                rs.close();
                pst.close();
                
                if(roleLst.size()==0){
                orgroleLst.add("103");
                orgrolenmLst.add("CONTACTS");
                }
                orgrols = orgroleLst.toString();
                orgrols = orgrols.replace('[','(');
                orgrols = orgrols.replace(']',')');
            } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
            }
            
        }
        if(info.getUsrFlg().equals("SYS")){
           initMenuAdminLink();
        }else{
        String rols = roleLst.toString();
        rols = rols.replace('[','(');
          rols = rols.replace(']',')');
        
        ArrayList menuHdr = new ArrayList();
        String mainMenu = "select  distinct  a.df_menu_idn, a.img, a.dsp, a.lnk ,a.flg ,a.rmk, a.srt from df_menu a , df_menu_role b "+
         " where a.dsp_lvl = 1 and a.df_menu_idn = b.df_menu_idn and b.df_role_idn in"+rols+" and " +
            "nvl(a.grp_df_menu_idn,0) = 0 and a.stt='A' and b.stt='A' and a.to_dte is null order by a.srt";

        outLst = db.execSqlLst("mainMenu",  mainMenu, new ArrayList());
        pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                DFMenu menuDeo = new DFMenu() ;            
                menuDeo.setDfMenuIdn(rs.getInt("df_menu_idn"));
                menuDeo.setDsp(nvl(rs.getString("dsp")));
                menuDeo.setLnk(nvl(rs.getString("lnk")));
                menuDeo.setFlg(nvl(rs.getString("flg")));
                menuDeo.setRmk(nvl(rs.getString("rmk")));
                menuHdr.add(menuDeo);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        info.setMainMenu(menuHdr);
         ArrayList subMenuLst = new ArrayList();
         HashMap subMenuMap = new HashMap();
        String subMenu="";
        int pGrpId = 0;
        if(orgrols.length()>2 || !diaflexMode.equalsIgnoreCase("S")){
        if(diaflexMode.equalsIgnoreCase("S")){
            subMenu = "select distinct a.grp_df_menu_idn, a.df_menu_idn, a.img, a.dsp, a.lnk , a.flg , a.srt,a.rmk from df_menu a , df_menu_role b where a.dsp_lvl = 2 and nvl(a.grp_df_menu_idn,0) > 0 " + 
            "and a.to_dte is null and a.stt='A' and b.stt='A' and b.df_menu_idn = a.df_menu_idn and b.df_role_idn in "+orgrols+" and b.df_menu_itm_idn is null "+
            "and exists (select 1 from df_menu_role r where r.df_role_idn in "+rols+" and r.df_menu_idn = a.df_menu_idn and r.stt='A')"+
            " order by a.grp_df_menu_idn, a.srt " ;
            outLst = db.execSqlLst("subMenu", subMenu, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        }else{    
        subMenu = "select distinct a.grp_df_menu_idn, a.df_menu_idn, a.img, a.dsp, a.lnk , a.flg , a.srt,a.rmk from df_menu a , df_menu_role b where a.dsp_lvl = 2 and nvl(a.grp_df_menu_idn,0) > 0 " + 
        "and a.to_dte is null and a.stt='A' and b.stt='A' and b.df_menu_idn = a.df_menu_idn and b.df_role_idn in "+rols+" and b.df_menu_itm_idn is null order by a.grp_df_menu_idn, a.srt " ;
        }
            outLst = db.execSqlLst("subMenu", subMenu, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        try {
           
            while (rs.next()) { 
               int dfMenuId = rs.getInt("grp_df_menu_idn");
                
                if(pGrpId==0)
                 pGrpId = dfMenuId;
                
                if(pGrpId!=dfMenuId){
                    subMenuMap.put(Integer.toString(pGrpId),subMenuLst);
                    subMenuLst = new ArrayList();
                    pGrpId = dfMenuId;
                }

                DFMenu menuDeo = new DFMenu() ;
                menuDeo.setGrpDfMenuId(rs.getInt("grp_df_menu_idn"));
                menuDeo.setDfMenuIdn(rs.getInt("df_menu_idn"));
                menuDeo.setDsp(nvl(rs.getString("dsp")));
                menuDeo.setLnk(nvl(rs.getString("lnk")));
                menuDeo.setFlg(nvl(rs.getString("flg")));
                menuDeo.setRmk(nvl(rs.getString("rmk")));
                subMenuLst.add(menuDeo);
            }
            rs.close();
            pst.close();
            
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        }
        if(pGrpId!=0)
        subMenuMap.put(Integer.toString(pGrpId),subMenuLst);
        
      info.setSubMenuMap(subMenuMap);
      
      HashMap trdLevelMap = new HashMap();
      ArrayList trdLevelLst = new ArrayList();
      int pDfMenuId = 0;
      if(orgrols.length()>2 || !diaflexMode.equalsIgnoreCase("S")){
      try {
        String trdLevel=""; 
        if(diaflexMode.equalsIgnoreCase("S")){
            trdLevel =  "select distinct a.df_menu_idn ,a.df_menu_itm_idn , a.dsp , a.lnk , a.flg , a.srt,a.rmk from df_menu_itm a , df_menu_role b "+
            " where a.stt = 'A' and b.stt='A' and b.df_menu_itm_idn = a.df_menu_itm_idn and b.df_role_idn in "+orgrols+" and b.stt='A' and b.to_dte is null "+
            " and exists (select 1 from df_menu_role r where r.df_role_idn in "+rols+" and r.df_menu_itm_idn = a.df_menu_itm_idn and r.stt='A') "+
            " order by a.df_menu_idn , a.srt ";
            outLst = db.execSqlLst("subMenu", trdLevel,new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        }else{
        trdLevel =  "select distinct a.df_menu_idn ,a.df_menu_itm_idn , a.dsp , a.lnk , a.flg , a.srt,a.rmk from df_menu_itm a , df_menu_role b "+
        " where a.stt = 'A' and b.stt='A' and b.df_menu_itm_idn = a.df_menu_itm_idn and b.df_role_idn in "+rols+" and b.stt='A' and b.to_dte is null "+
        " order by a.df_menu_idn , a.srt ";
        }
            outLst = db.execSqlLst("subMenu", trdLevel,new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
           while (rs.next()) {
               int dfMenuId = rs.getInt("df_menu_idn");
                
                if(pDfMenuId==0)
                 pDfMenuId = dfMenuId;
                
                if(pDfMenuId!=dfMenuId){
                   trdLevelMap.put(Integer.toString(pDfMenuId),trdLevelLst);
                    trdLevelLst = new ArrayList();
                    pDfMenuId = dfMenuId;
                }
               
               
               
               DFMenu menuDeo = new DFMenu() ; 
             
               menuDeo.setDfMenuIdn(rs.getInt("df_menu_idn"));
               menuDeo.setDfmenuitmidn(rs.getInt("df_menu_itm_idn"));
               menuDeo.setDsp(nvl(rs.getString("dsp")));
               menuDeo.setLnk(nvl(rs.getString("lnk")));
               menuDeo.setFlg(nvl(rs.getString("flg")));
               menuDeo.setRmk(nvl(rs.getString("rmk")));
               trdLevelLst.add(menuDeo);
           }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
           // TODO: Add catch code
           sqle.printStackTrace();
        }
        }
        if(pDfMenuId!=0)
        trdLevelMap.put(Integer.toString(pDfMenuId),trdLevelLst);
        
      info.setTrdMenuMap(trdLevelMap);
      info.setRoleLst(rolenmLst);      
    }
    }
   
    public void initMenuAdminLink(){
        ResultSet rs = null;
        
        ArrayList menuHdr = new ArrayList();
        String mainMenu = "select a.df_menu_idn, a.img, initcap(a.dsp) dsp, a.lnk , a.flg , a.rmk from df_menu a "+
         " where a.dsp_lvl = 1  and " +
            "nvl(a.grp_df_menu_idn,0) = 0 and a.stt='A'  and a.to_dte is null order by a.srt";

        ArrayList outLst = db.execSqlLst("mainMenu",  mainMenu, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                DFMenu menuDeo = new DFMenu() ;            
                menuDeo.setDfMenuIdn(rs.getInt("df_menu_idn"));
                menuDeo.setDsp(nvl(rs.getString("dsp")));
                menuDeo.setLnk(nvl(rs.getString("lnk")));
                menuDeo.setFlg(nvl(rs.getString("flg")));
                menuDeo.setRmk(nvl(rs.getString("rmk")));
                menuHdr.add(menuDeo);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        info.setMainMenu(menuHdr);
         ArrayList subMenuLst = new ArrayList();
         HashMap subMenuMap = new HashMap();
        String subMenu = "select a.grp_df_menu_idn, a.df_menu_idn, a.img, initcap(a.dsp) dsp, a.lnk , a.flg ,a.rmk from df_menu a  where a.dsp_lvl = 2 and nvl(a.grp_df_menu_idn,0) > 0 " + 
        "and a.to_dte is null and a.stt='A'   order by a.grp_df_menu_idn, a.srt " ;
        
        int pGrpId = 0;
        outLst = db.execSqlLst("subMenu", subMenu, new ArrayList());
        pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
        try {
           
            while (rs.next()) { 
               int dfMenuId = rs.getInt("grp_df_menu_idn");
                
                if(pGrpId==0)
                 pGrpId = dfMenuId;
                
                if(pGrpId!=dfMenuId){
                    subMenuMap.put(Integer.toString(pGrpId),subMenuLst);
                    subMenuLst = new ArrayList();
                    pGrpId = dfMenuId;
                }

                DFMenu menuDeo = new DFMenu() ;
                menuDeo.setGrpDfMenuId(rs.getInt("grp_df_menu_idn"));
                menuDeo.setDfMenuIdn(rs.getInt("df_menu_idn"));
                menuDeo.setDsp(nvl(rs.getString("dsp")));
                menuDeo.setLnk(nvl(rs.getString("lnk")));
                menuDeo.setFlg(nvl(rs.getString("flg")));
                menuDeo.setRmk(nvl(rs.getString("rmk")));
                subMenuLst.add(menuDeo);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        if(pGrpId!=0)
        subMenuMap.put(Integer.toString(pGrpId),subMenuLst);
        
      info.setSubMenuMap(subMenuMap);
      
      HashMap trdLevelMap = new HashMap();
      ArrayList trdLevelLst = new ArrayList();
      String trdLevel =  "select a.df_menu_idn ,a.df_menu_itm_idn , initcap(a.dsp) dsp , a.lnk , a.flg,a.rmk from df_menu_itm a  "+
        " where a.stt = 'A'  order by a.df_menu_idn , a.srt ";
       int pDfMenuId = 0;
        outLst = db.execSqlLst("subMenu", trdLevel, new ArrayList());
        pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
        try {
           while (rs.next()) {
               int dfMenuId = rs.getInt("df_menu_idn");
                
                if(pDfMenuId==0)
                 pDfMenuId = dfMenuId;
                
                if(pDfMenuId!=dfMenuId){
                   trdLevelMap.put(Integer.toString(pDfMenuId),trdLevelLst);
                    trdLevelLst = new ArrayList();
                    pDfMenuId = dfMenuId;
                }
               
               
               
               DFMenu menuDeo = new DFMenu() ; 
             
               menuDeo.setDfMenuIdn(rs.getInt("df_menu_idn"));
               menuDeo.setDfmenuitmidn(rs.getInt("df_menu_itm_idn"));
               menuDeo.setDsp(nvl(rs.getString("dsp")));
               menuDeo.setLnk(nvl(rs.getString("lnk")));
               menuDeo.setFlg(nvl(rs.getString("flg")));
               menuDeo.setRmk(nvl(rs.getString("rmk")));
               trdLevelLst.add(menuDeo);
           }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
           // TODO: Add catch code
           sqle.printStackTrace();
        }
        if(pDfMenuId!=0)
        trdLevelMap.put(Integer.toString(pDfMenuId),trdLevelLst);
        
      info.setTrdMenuMap(trdLevelMap);
    }
    
    public void initMenu() {
        ArrayList menuHdr = new ArrayList();
        HashMap menuLst = new HashMap();
        ArrayList dspLevels = new ArrayList();
        String getMenuLvlQ = " select distinct nvl(dsp_lvl,1) lvl from df_menu " + 
            " where to_dte is null and stt = 'A' ";
        
        ResultSet rs = db.execSql(" usr menu ", getMenuLvlQ, new ArrayList());
        try {
            while(rs.next()) {
                dspLevels.add(rs.getString(1));            
            }
            rs.close();
            for(int i=0; i < dspLevels.size(); i++) {
                menuHdr = new ArrayList();
                String lDspLvl = (String)dspLevels.get(i);
                String getMenuQ = " select df_menu_idn, dsp_lvl, nvl(grp_df_menu_idn, 0) grp, dsp, img, lnk, dsc from df_menu " +
                    " where to_dte is null and stt = 'A' and dsp_lvl = ? order by dsp_lvl, srt ";    
                ArrayList params = new ArrayList();
                params.add(lDspLvl);
                while(rs.next()) {
                    DFMenu dao = new DFMenu() ;            
                    dao.setDfMenuIdn(rs.getInt("df_menu_idn"));
                    dao.setDspLvl(rs.getInt("dsp_lvl"));
                    dao.setGrpIdn(rs.getInt("grp"));
                    dao.setDsp(rs.getString("dsp"));
                    dao.setLnk(rs.getString("lnk"));
                    dao.setImg(rs.getString("img"));
                    dao.setDsc(rs.getString("dsc"));
                    menuHdr.add(dao);
                }
                rs.close();
                menuLst.put(lDspLvl, menuHdr);
                
            }    
        } catch (SQLException e) {
        }        
    }
    public HashMap getFormFields(int formsId) {
        String getFormNme = " select form_nme from ui_forms where ui_forms_id = ? ";
        ArrayList params = new ArrayList();
        params.add(Integer.toString(formsId));
        ArrayList outLst = db.execSqlLst(" form nme ", getFormNme, params);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            if(rs.next()) {
                return getFormFields(rs.getString(1));
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
        }
        return new HashMap();
    }
    public String labGroup(){
        String grp="0";
        String sql ="select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " + 
        "b.mdl = 'JFLEX' and b.nme_rule ='LAB_GRP' and a.til_dte is null order by a.srt_fr ";
        ArrayList outLst = db.execSqlLst("sql", sql, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            if(rs.next()) {
             grp = rs.getString("chr_fr");
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        return grp;
    }
    public ArrayList ReporTypList(HttpServletRequest req ,HttpServletResponse res){
        String    memoPrntOptn = "select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and "
                                 + "b.mdl = 'MKTG' and b.nme_rule = 'STATUS' and a.til_dte is null order by a.dsc ";
        ArrayList sttList = new ArrayList();
        ArrayList outLst = db.execSqlLst("memoPrint", memoPrntOptn, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                UIForms memoOpn = new UIForms();
                memoOpn.setFORM_NME(rs.getString("chr_fr"));
                memoOpn.setFORM_TTL(rs.getString("dsc"));
                sttList.add(memoOpn);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        return sttList;
    }
    public String CUTCRLGroup(){
        String grp="0";
        String sql ="select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " + 
        "b.mdl = 'JFLEX' and b.nme_rule ='COM_GRP' and a.til_dte is null order by a.srt_fr ";
        ArrayList outLst = db.execSqlLst("sql", sql, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            if(rs.next()) {
             grp = rs.getString("chr_fr");
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        return grp;
    }
    public HashMap getFormFields(String frmNme) {
    HashMap formFields = new HashMap();
    ArrayList daoFields = new ArrayList();
    ArrayList flds = new ArrayList();
    UIForms dao = new UIForms();

    String getData = " select a.ui_forms_id, a.form_nme, a.form_ttl, a.new_rec, a.srt srt_m, a.stt stt_m" +
    ", a.frm_tbl frm_tbl, a.where_cl where_cl, a.order_by order_by, b.ui_forms_fields_id, b.tbl_nme " +
    ", b.tbl_fld, b.form_fld, b.fld_sz, b.dsp_ttl, b.dta_typ, b.fld_typ, b.req_yn, b.dsp_yn, b.srt, b.lov_qry, b.val_cond, b.flg " +
    " , b.fld_alias alias1, b.dflt_val,b.srch_srt,b.srch_grp "+
    " from ui_forms a, ui_forms_fields b where a.ui_forms_id = b.ui_forms_id and upper(a.form_nme) = upper(?) and b.to_dte is null and b.dsp_yn = 'Y' and b.stt='A' order by b.srt";
    ArrayList params = new ArrayList();
    params.add(frmNme.toUpperCase());
        ArrayList outLst = db.execSqlLst(" form field "+ frmNme, getData, params);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
    int ctr = 0 ;
    try {
    while(rs.next()) {
    ++ctr ;
    //String fld = rs.getString("form_fld");
    String ui_forms_id = nvl(rs.getString("ui_forms_id"));
    String form_nme = frmNme ;
    String form_ttl = nvl(rs.getString("form_ttl"));

    dao.setID(ui_forms_id);
    dao.setFORM_NME(form_nme);
    dao.setFORM_TTL(form_ttl);
    dao.setREC(nvl(rs.getString("new_rec")));
    dao.setSRT(nvl(rs.getString("srt_m")));
    dao.setSTT(nvl(rs.getString("stt_m")));

    dao.setFRM_TBL(nvl(rs.getString("frm_tbl")));
    dao.setWHERE_CL(nvl(rs.getString("where_cl")));
    dao.setORDER_BY(nvl(rs.getString("order_by")));

    UIFormsFields fields = new UIFormsFields();
    fields.setDSP_TTL(nvl(rs.getString("dsp_ttl")));
    fields.setDTA_TYP(nvl(rs.getString("dta_typ")));
    fields.setFLD_SZ(nvl(rs.getString("fld_sz"),"10"));
    fields.setFLD_TYP(nvl(rs.getString("fld_typ")));
    fields.setFORM_FLD(nvl(rs.getString("form_fld")));
    fields.setLOV_QRY(nvl(rs.getString("lov_qry")));
    fields.setREQ_YN(nvl(rs.getString("req_yn")));
    fields.setDSP_YN(nvl(rs.getString("dsp_yn")));
    fields.setSRT(nvl(rs.getString("srt")));
    fields.setTBL_FLD(nvl(rs.getString("tbl_fld")));
    fields.setALIAS(nvl(rs.getString("alias1")));
    fields.setTBL_NME(nvl(rs.getString("tbl_nme")));
    fields.setVAL_COND(nvl(rs.getString("val_cond")));
    fields.setFLG(nvl(rs.getString("flg")));
    fields.setDFLT_VAL(nvl(rs.getString("dflt_val")));
    fields.setSRCH_SRT(nvl(rs.getString("srch_srt")));
    fields.setSRCH_GRP(nvl(rs.getString("srch_grp")));
    daoFields.add(fields);
    /*
    flds.add(fld);
    formFields.put(fld+"TTL", nvl(rs.getString("dsp_ttl")));
    formFields.put(fld+"TF", nvl(rs.getString("tbl_fld")));
    formFields.put(fld+"TBL", nvl(rs.getString("tbl_nme")));
    formFields.put(fld+"DT", nvl(rs.getString("dta_typ")));
    formFields.put(fld+"FT", nvl(rs.getString("fld_typ")));
    formFields.put(fld+"REQ", nvl(rs.getString("req_yn")));
    formFields.put(fld+"SRT", nvl(rs.getString("srt")));
    formFields.put(fld+"LOV", nvl(rs.getString("lov_qry")));
    */

    //SOP(" Util : getFormFields : "+ ctr + " : "+ daoFields.size());
    }
        rs.close();
        pst.close();
    } catch (SQLException e) {
    SOP(" FormFields SqlErr "+e);
    }
    catch (Exception e) {
    SOP(" FormFields Err "+e);
    }
    dao.setFields(daoFields);
    //SOP(frmNme + " : Fields = "+ flds.size());
    //SOP(frmNme + " : daos = "+ daos.size());
    //formFields.put("FLDS", flds);
    formFields.put("DAOS", dao);
    SOP(frmNme + " : DAOS "+ daoFields.size());
    info.setFormFields(frmNme, formFields);
    return formFields;
    }
    
    
    public String getNme(String id) {
      String nme = "None"  ;
      String nmeQ = " select nme from nme_v where nme_idn = ?";
      ArrayList params = new ArrayList();
      params.add(id);
        ArrayList outLst = db.execSqlLst(" get nme", nmeQ, params);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            if(rs.next()){
              nme = rs.getString(1);
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
        }
        return nme ;
    }
    
    public String getNme(int id) {
      return getNme(Integer.toString(id)) ;
    }
    public String getGTPrpLst(int ct) {
        String gtQ = "" ;
        for(int i=0; i < ct; i++) {
                String fld = "prp_" ;
                int j = i + 1 ;
                if(j < 10)
                    fld += "00" + j ;
                else if(j < 100)
                    fld += "0" + j ;
                else if(j > 100)
                    fld += j ;
                gtQ += ", "+ fld ;      
        }       
        return gtQ ;
    }
    public HashMap getContactLinks() {
        ArrayList pages = new ArrayList();
        ArrayList links = new ArrayList();
            
        String reqUrl = info.getReqUrl();
        
        pages.add("View / Edit Contact");
        links.add(reqUrl + "/contact/nme.do?method=load");
        
        pages.add("Address");
        links.add(reqUrl + "/contact/address.do?method=search");
        
        pages.add("Additional Info");
        links.add(reqUrl + "/contact/nmedtl.do?method=load");
        
        pages.add("Terms");
        links.add(reqUrl + "/contact/nmerel.do?method=search");
        
        pages.add("Commission Agents");
        links.add(reqUrl + "/contact/nmecomm.do?method=search");
      
        pages.add("Groups");
        links.add(reqUrl + "/contact/nmegrp.do?method=search");
        
        pages.add("Web Access");
        links.add(reqUrl + "/contact/webaccess.do?method=search");
      
        pages.add("Documents");
        links.add(reqUrl + "/contact/nmedocs.do?method=search");

        pages.add("Complete Profile");
        links.add(reqUrl + "/contacts/NmeReport.jsp?view=Y");
      
        pages.add("Delete");
        links.add(reqUrl + "/contact/nme.do?method=delete");
        
        pages.add("Contacts Search");
        links.add(reqUrl + "/contacts/contactSearch.jsp?view=Y");
        
        
        pages.add("Column Mapping");
        links.add(reqUrl + "/contact/columnMap.do?method=load");
        
        HashMap pageLinks = new HashMap();
        pageLinks.put("PAGES", pages);
        pageLinks.put("LINKS", links);
        return pageLinks;
                                          
    }
    public ArrayList getTrmApprList() {
        ArrayList lists = new ArrayList();
        ArrayList list = new ArrayList();
        ArrayList logList = new ArrayList();
        
        String getLst = " select a.nmerel_idn, a.nme_idn, byr.get_nm(nme_idn) byr, byr.get_nm(aadat_idn) aadat, byr.get_nm(mbrk2_idn) mb2 "+
                " , aadat_comm, mbrk2_comm, cur, symbl, del_typ, del_dys, byr.get_trms(a.nmerel_idn) trms, b.ins_grp_nme, c.brc_grp_nme "+
                " , a.brc_comm, a.ext_vlu,a.ext_pct,a.rap_pct, a.flg, a.dflt_yn,a.mod_usr "+
                " from nmerel a, ins_grp b, brc_grp_aadat c "+
                " where a.ins_grp_idn = b.ins_grp_idn and a.brc_grp_idn = c.brc_grp_idn "+
                " and nvl(a.flg,'NEW') in ('NEW', 'MOD') order by 3 ";
        ArrayList outLst = db.execSqlLst(" trm pending", getLst, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while(rs.next()) {
                NmeRel srchDao = new NmeRel();
                String relIdn = rs.getString(1);
                String nmeIdn = rs.getString(2);
                
                srchDao.setIdn(relIdn);
                srchDao.setNmeIdn(nmeIdn);
                srchDao.setValue("byr", nvl(rs.getString("byr")));
                srchDao.setValue("aadat_idn", nvl(rs.getString("aadat")));
                srchDao.setValue("aadat_comm", nvl(rs.getString("aadat_comm")));
                srchDao.setValue("brk2_idn", nvl(rs.getString("mb2")));
                srchDao.setValue("brk2_comm", nvl(rs.getString("mbrk2_comm")));
                srchDao.setValue("cur", nvl(rs.getString("cur")));
                srchDao.setValue("symbl", nvl(rs.getString("symbl")));
                srchDao.setValue("del_typ", nvl(rs.getString("del_typ")));
                srchDao.setValue("del_dys", nvl(rs.getString("del_dys")));
                srchDao.setValue("trms_idn", nvl(rs.getString("trms")));
                srchDao.setValue("ins_grp_idn", nvl(rs.getString("ins_grp_nme")));
                srchDao.setValue("brc_grp_idn", nvl(rs.getString("brc_grp_nme")));
                srchDao.setValue("brc_comm", nvl(rs.getString("brc_comm")));
                srchDao.setValue("ext_vlu", nvl(rs.getString("ext_vlu")));
                srchDao.setValue("ext_pct", nvl(rs.getString("ext_pct")));
                srchDao.setValue("rap_pct", nvl(rs.getString("rap_pct")));
                srchDao.setValue("dflt_yn", nvl(rs.getString("dflt_yn")));
                srchDao.setValue("flg", nvl(rs.getString("flg")));
                srchDao.setValue("mod_usr", nvl(rs.getString("mod_usr")));
                list.add(srchDao);
            }
            rs.close();
            pst.close();
          String getLogLst = " select a.nmerel_idn, a.nme_idn, byr.get_nm(nme_idn) byr, byr.get_nm(aadat_idn) aadat, byr.get_nm(mbrk2_idn) mb2 "+
                  " , aadat_comm, mbrk2_comm, cur, symbl, del_typ, byr.get_trms(a.nmerel_idn) trms, b.ins_grp_nme, c.brc_grp_nme "+
                  " , a.brc_comm, a.ext_vlu,a.ext_pct,a.rap_pct, a.flg, a.dflt_yn,a.mod_usr,to_char(a.log_dte, 'dd-Mon-rrrr HH24:mi:ss') log_dt "+
                  " from nmerel_log a, ins_grp b, brc_grp_aadat c "+
                  " where a.ins_grp_idn = b.ins_grp_idn and a.brc_grp_idn = c.brc_grp_idn "+
                  " and exists (select 1 from nmerel a1 where a1.nmerel_idn = a.nmerel_idn and nvl(a1.flg,'NEW') in ('NEW', 'MOD'))" +
                  " order by 3 ";  
            outLst = db.execSqlLst(" trm pending", getLogLst, new ArrayList());    
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
          while(rs.next()) {
              NmeRel srchDao = new NmeRel();
              String relIdn = rs.getString(1);
              String nmeIdn = rs.getString(2);
              
              srchDao.setIdn(relIdn);
              srchDao.setNmeIdn(nmeIdn);
              srchDao.setValue("byr", nvl(rs.getString("byr")));
              srchDao.setValue("aadat_idn", nvl(rs.getString("aadat")));
              srchDao.setValue("aadat_comm", nvl(rs.getString("aadat_comm")));
              srchDao.setValue("brk2_idn", nvl(rs.getString("mb2")));
              srchDao.setValue("brk2_comm", nvl(rs.getString("mbrk2_comm")));
              srchDao.setValue("cur", nvl(rs.getString("cur")));
              srchDao.setValue("symbl", nvl(rs.getString("symbl")));
              srchDao.setValue("del_typ", nvl(rs.getString("del_typ")));
              srchDao.setValue("trms_idn", nvl(rs.getString("trms")));
              srchDao.setValue("ins_grp_idn", nvl(rs.getString("ins_grp_nme")));
              srchDao.setValue("brc_grp_idn", nvl(rs.getString("brc_grp_nme")));
              srchDao.setValue("brc_comm", nvl(rs.getString("brc_comm")));
              srchDao.setValue("ext_vlu", nvl(rs.getString("ext_vlu")));
              srchDao.setValue("ext_pct", nvl(rs.getString("ext_pct")));
              srchDao.setValue("rap_pct", nvl(rs.getString("rap_pct")));
              srchDao.setValue("dflt_yn", nvl(rs.getString("dflt_yn")));
              srchDao.setValue("flg", nvl(rs.getString("flg")));
              srchDao.setValue("mod_usr", nvl(rs.getString("mod_usr")));
              srchDao.setValue("log_dt", nvl(rs.getString("log_dt")));
              logList.add(srchDao);
          }
            rs.close();
            pst.close();
        } catch (SQLException e) {
        }
        lists.add(list);
        lists.add(logList);
        return lists ;
    }
    
    public HashMap getLOV(String sql) {
        HashMap lov = new HashMap();
        ArrayList keys = new ArrayList();
        ArrayList vals = new ArrayList();
        keys.add("");
        vals.add("Select");
        ArrayList outLst = db.execSqlLst(" get Lov ", sql, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while(rs.next()) {
                String lkey = nvl(rs.getString(1));
                String lval = nvl(rs.getString(2));
                keys.add(lkey);
                vals.add(lval);
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
        }
        lov.put("K", keys);
        lov.put("V", vals);
        
        return lov ;
    }
    public void AssortViewLyt(){
    ArrayList  dsc=new ArrayList();
    String gtView = "select chr_fr,dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " + 
                    " b.mdl = 'JFLEX' and b.nme_rule = 'Assort_Lab_View' and a.til_dte is null order by a.srt_fr ";
        ArrayList outLst = db.execSqlLst("gtView", gtView, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
    try {
       while(rs.next()) {
            String prp = nvl(rs.getString("dsc"));
            dsc.add(prp);
        }
        rs.close();
        pst.close();
    } catch (SQLException sqle) {
        // TODO: Add catch code
        sqle.printStackTrace();
    }

    info.setAssortViewMap(dsc);
    }
    public HashMap getLOVNOTSEL(String sql) {
        HashMap lov = new HashMap();
        ArrayList keys = new ArrayList();
        ArrayList vals = new ArrayList();
        ArrayList outLst = db.execSqlLst(" get Lov ", sql, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while(rs.next()) {
                String lkey = nvl(rs.getString(1));
                String lval = nvl(rs.getString(2));
                keys.add(lkey);
                vals.add(lval);
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
        }
        lov.put("K", keys);
        lov.put("V", vals);
        
        return lov ;
    }
    public HashMap getLOV(String sql , ArrayList ary) {
        HashMap lov = new HashMap();
        ArrayList keys = new ArrayList();
        ArrayList vals = new ArrayList();
        keys.add("");
        vals.add("Select");
        ArrayList outLst = db.execSqlLst(" get Lov ", sql, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while(rs.next()) {
                String lkey = nvl(rs.getString(1));
                String lval = nvl(rs.getString(2));
                keys.add(lkey);
                vals.add(lval);
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
        }
        lov.put("K", keys);
        lov.put("V", vals);
        
        return lov ;
    }
    public HashMap getLOVList(String lst) {
        HashMap lov = new HashMap();
        ArrayList keys = new ArrayList();
        ArrayList vals = new ArrayList();
        keys.add("");
        vals.add("Select");
        StringTokenizer str = new StringTokenizer(lst, ",");
        int ctr=0;
        while(str.hasMoreTokens()) {
            ++ctr;
            String token = str.nextToken();
            if((ctr%2) == 1)
                keys.add(token);
            else
                vals.add(token);
        }
        
        lov.put("K", keys);
        lov.put("V", vals);
        
        return lov ;
    }
    
  public void initUsr(String rlnId) {
      //info.setSession(session);
      ResultSet rsUsrDtls = null ;
      PreparedStatement pst=null;
      ArrayList ary = null;
      String byrNm = "", trms = "", rln = "" ,byrEml="",memoLmt=null,crLmt=null;
      int byrId, relIdn ;
      double trmCmp, xrt = 1 ;
      String getUsrDtls = 
     " select a.nme_idn byrId, byr.get_nm(a.nme_idn) byrNm,lower(byr.get_eml(a.nme_idn,'N')) eml, "+
     " a.nmerel_idn relIdn, byr.cal_trms(a.nmerel_idn) trm_cmp, byr.get_nm(b.emp_idn) salNm , " + 
     " lower(byr.get_eml(b.emp_idn,'N')) salEml , GET_CREDIT_LMT('Z',b.nme_idn,a.nmerel_idn) memo_lmt,GET_CREDIT_LMT('Z',b.nme_idn,a.nmerel_idn) cr_lmt , nvl(a.rap_pct,'0') rap_pct , "+
      " byr.get_trms(a.nmerel_idn) trms  , nvl(a.cur,'USD') rln, decode(a.cur, 'USD', 1, get_xrt) xrt  "+
      " from nmerel a , mnme b where a.nmerel_idn = ? and a.nme_idn = b.nme_idn  ";
      ary = new ArrayList();
      ary.add(rlnId);
      ArrayList outLst = db.execSqlLst(" User Details", getUsrDtls, ary);
      pst = (PreparedStatement)outLst.get(0);
      rsUsrDtls = (ResultSet)outLst.get(1);
      if(rsUsrDtls != null) {
          try {
              rsUsrDtls.next();
              byrId = rsUsrDtls.getInt("byrId");
              byrNm = nvl(rsUsrDtls.getString("byrNm"));
              relIdn = rsUsrDtls.getInt("relIdn");
              trmCmp = rsUsrDtls.getDouble("trm_cmp");
              trms =  nvl(rsUsrDtls.getString("trms"));
              rln = nvl(rsUsrDtls.getString("rln"));
              xrt = rsUsrDtls.getDouble("xrt");
              byrEml = nvl(rsUsrDtls.getString("eml"));
              memoLmt =  nvl(rsUsrDtls.getString("memo_lmt"),"N");
              crLmt = nvl(rsUsrDtls.getString("cr_lmt"),"N");
              info.setRlnId(relIdn);         
              info.setCmpTrm(trmCmp);
              info.setByrId(byrId);
              info.setByrNm(byrNm);
              info.setRln(rln);
              info.setByrEml(byrEml);
              info.setMemo_lmt(memoLmt);
              info.setCrt_lmt(crLmt);
              info.setRap_pct(rsUsrDtls.getInt("rap_pct"));
              info.setSaleExNme(nvl(rsUsrDtls.getString("salNm")));
              info.setSaleExeEml(nvl(rsUsrDtls.getString("salEml")));
              rsUsrDtls.close();
              pst.close();
          } catch (SQLException e) {
             
          }
      }


      
      String sql="select dtls,GET_TRM_DSC(nmerel_idn) shortTrms  from nme_rel_v where nmerel_idn=?";
      outLst = db.execSqlLst("Terms",sql, ary);
      pst = (PreparedStatement)outLst.get(0);
      rsUsrDtls = (ResultSet)outLst.get(1);
      try {
      if(rsUsrDtls.next()){
      info.setTrms(nvl(rsUsrDtls.getString(1)));
      info.setShorttrms(nvl(rsUsrDtls.getString(2)));
      }
          rsUsrDtls.close();
          pst.close();
          noteperson();
      }catch (SQLException e){
      }

     
         
      //session.setAttribute("info", info);
      
      
  }
  
    public HSSFWorkbook getMemoInXlGT(String getMemoId, String myKey, String typ,String mdl, HttpServletRequest req,HttpServletResponse res) {
            String memoKey = myKey + "_" + getMemoId;
        ExcelUtil excelUtil = new ExcelUtil();
        SearchQuery query = new SearchQuery();
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        Connection conn=info.getCon();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        excelUtil.init(db, util, info);
        String vrfyMemoId =
            " Select nmerel_idn||'_'||idn memoKey ,typ , exh_rte , vw_mdl from mjan where idn = ? ";
        ArrayList ary = new ArrayList();
        ary.add(getMemoId);
        ArrayList outLst = db.execSqlLst(" Vrfy Memo Id", vrfyMemoId, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs1 = (ResultSet)outLst.get(1);
        GenericInterface generic = new GenericImpl();
        try {
            if (rs1.next()) {
                String exh_rte = util.nvl(rs1.getString("exh_rte"));
                req.setAttribute("exhRte", exh_rte);
               int ct = db.execCall("delete gt", "delete from gt_srch_rslt", new ArrayList());
               
                String dbMemoKey = rs1.getString("memoKey");
               
             
                    ary = new ArrayList();
                    ary.add(getMemoId);
                    ary.add(myKey);
                    ary.add(typ);
                    ary.add(mdl);
                  ct=db.execCall("Mail Memo : " + getMemoId, " pkgmkt.popMemo(?,?,?,?)",
                                    ary);

                

            }
            rs1.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        ArrayList vwPrpLst = generic.genericPrprVw(req, res, mdl, mdl);
       HashMap pktDtl = query.SearchResultGT(req, res, "Z", vwPrpLst);
       return excelUtil.getDataAllInXl(typ, req, mdl);
     
      
    }
  
    public HSSFWorkbook getMemoInXl(String getMemoId, String myKey, String typ,String mdl, HttpServletRequest req,HttpServletResponse res,HttpSession session) {
            String memoKey = myKey + "_" + getMemoId;
        ExcelUtil excelUtil = new ExcelUtil();
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        Connection conn=info.getCon();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        excelUtil.init(db, util, info);
        String exh_rte="";
        String vrfyMemoId =
            " Select nmerel_idn||'_'||idn memoKey ,typ , exh_rte , vw_mdl from mjan where idn = ? ";
        ArrayList ary = new ArrayList();
        ary.add(getMemoId);
        ArrayList outLst = db.execSqlLst(" Vrfy Memo Id", vrfyMemoId, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs1 = (ResultSet)outLst.get(1);

        try {
            if (rs1.next()) {
                exh_rte = util.nvl(rs1.getString("exh_rte"),"1");
                req.setAttribute("exhRte", exh_rte);
                info.setXrt(Double.parseDouble(exh_rte));
               int ct = db.execCall("delete gt", "delete from gt_srch_rslt", new ArrayList());
               
                String dbMemoKey = rs1.getString("memoKey");
               
             
                    ary = new ArrayList();
                    ary.add(getMemoId);
                    ary.add(myKey);
                    ary.add(typ);
                    ary.add(mdl);
                  ct=db.execCall("Mail Memo : " + getMemoId, " pkgmkt.popMemo(?,?,?,?)",
                                    ary);

                

            }
            rs1.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        req.setAttribute("Memoid", getMemoId);
//        req.setAttribute("ADDNOTE", "ADDNOTE");
       if(cnt.equals("kj")){
           String lstNme = "MEMOREPORTSRCHRST_"+info.getLogId()+"_"+util.getToDteGiveFrmt("yyyyMMdd");
           SearchQuery query = new SearchQuery();
           GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
           GenericInterface genericInt = new GenericImpl();
           ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, "", mdl);
           HashMap searchList = query.SearchResultGT(req, res , "'M'" , vwPrpLst);
           ArrayList itemHdr = getItemHdrFromvwPrpLst(vwPrpLst);
           ArrayList selectstkIdnLst =new ArrayList();
           Set<String> keys = searchList.keySet();
               for(String key: keys){
              selectstkIdnLst.add(key);
           }
           gtMgr.setValue(lstNme,searchList);
           gtMgr.setValue(lstNme+"_SEL",selectstkIdnLst);
           HashMap excelDtl = new HashMap();
           excelDtl.put("HDR", itemHdr);
           excelDtl.put("lstNme", lstNme);
           excelDtl.put("GRNTTL", "Y");
           excelDtl.put("MDL", mdl);
           excelDtl.put("GRNTTL", "Y");
           excelDtl.put("LOGO", "Y");
           excelDtl.put("EXHRTE", exh_rte);
           if((util.nvl((String)info.getDmbsInfoLst().get("NR_CUST_MDL"),"N").equals("Y"))){
              if(cnt.equalsIgnoreCase("kj"))
              excelDtl.put("GRNTTL", "N");
           }
           ExcelUtilObj excelUtilObj = new ExcelUtilObj();
           excelUtilObj.init(db, util, info,gtMgr);
           HSSFWorkbook hwb = excelUtilObj.getGenXlObj(excelDtl, req);
           return hwb;
       }
       return excelUtil.getDataAllInXl(typ, req, mdl);
     
      
    }
    public ArrayList getItemHdrFromvwPrpLst(ArrayList vwPrpLst){
        ArrayList itemHdr=new ArrayList();
        HashMap hdrDtl = new HashMap();
        int vwPrpLstsz=vwPrpLst.size();
        HashMap mprp = info.getMprp();
        hdrDtl.put("hdr", "vnm");
        hdrDtl.put("typ", "N");
        hdrDtl.put("htyp", "C");
        itemHdr.add(hdrDtl);
          
        hdrDtl = new HashMap();
        hdrDtl.put("hdr", "stt");
        hdrDtl.put("typ", "N");
        hdrDtl.put("htyp", "C");
        itemHdr.add(hdrDtl);
        
        for(int j=0; j < vwPrpLstsz; j++ ){
            String prp = (String)vwPrpLst.get(j);
            String hdr = (String)mprp.get(prp);
            String prpDsc = (String)mprp.get(prp+"P");
            String prpTyp = nvl((String)mprp.get(prp+"T"));
            hdrDtl = new HashMap();
            hdrDtl.put("hdr", prp);
            hdrDtl.put("typ", "P");
            hdrDtl.put("dp", "P");
            hdrDtl.put("htyp",prpTyp);
            itemHdr.add(hdrDtl); 
            if(prp.equals("RTE")){
                hdrDtl = new HashMap();
                hdrDtl.put("hdr", "AMT");
                hdrDtl.put("typ", "N");
                hdrDtl.put("dp", "P");
                hdrDtl.put("htyp","N");
                itemHdr.add(hdrDtl); 
            }
        }
        hdrDtl = new HashMap();
        hdrDtl.put("hdr", "RAPVAL");
        hdrDtl.put("typ", "N");
        hdrDtl.put("dp", "P");
        hdrDtl.put("htyp","N");
        itemHdr.add(hdrDtl); 
        return itemHdr;
    }
    public int userID (int rlnId){
        int usrId =0;
        ArrayList ary  = new ArrayList();
        ary.add(Integer.toString(rlnId));
        ArrayList outLst = db.execSqlLst("usr ID", "select usr_id from web_usrs where rel_idn",ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            if (rs.next()) {
            usrId = rs.getInt(1);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        return usrId;
    }
    
    public String getJsonString(JsonDao jsonDao){
        String url = nvl(jsonDao.getServiceUrl());
        JSONObject jObj = jsonDao.getJsonObject();
        String jsonStr = "";
        try {
            
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(url);
            postRequest.setHeader("Accept", "application/json");
            postRequest.setHeader("Content-type", "application/json");
            postRequest.setHeader("TOKEN", "123456");
            StringEntity insetValue = new StringEntity(jObj.toString());
            insetValue.setContentType(MediaType.APPLICATION_JSON);
            postRequest.setEntity(insetValue);
            HttpResponse responsejson = httpClient.execute(postRequest);

            if (responsejson.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " +
                                           responsejson.getStatusLine().getStatusCode());
            }
            BufferedReader br =
                new BufferedReader(new InputStreamReader((responsejson.getEntity().getContent())));
            String outsetValue = "";
            
            //     System.out.println("OutsetValue from Server .... \n");
            while ((outsetValue = br.readLine()) != null) {
                //    System.out.println(outsetValue);
                jsonStr = jsonStr + outsetValue;
            }
            httpClient.getConnectionManager().shutdown();
        } catch (UnsupportedEncodingException uee) {
            // TODO: Add catch code
            uee.printStackTrace();
            jsonStr="FAIL";
        } catch (IllegalStateException ise) {
            // TODO: Add catch code
            ise.printStackTrace();
            jsonStr="FAIL";
        } catch (ClientProtocolException cpe) {
            // TODO: Add catch code
            cpe.printStackTrace();
            jsonStr="FAIL";
        } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
            jsonStr="FAIL";
        } catch (RuntimeException re) {
            // TODO: Add catch code
            re.printStackTrace();
            jsonStr="FAIL";
        }
        
        return jsonStr;
    }
    
    
    
    
    public HashMap EXLFormat(){
        ResultSet rs = null;
        HashMap exlFormt = new HashMap();
        String sqlExlFormat = "select rule_dtl_idn, dsc, chr_fr, srt_fr, chr_to, srt_to "+
                             " from mrule a , rule_dtl b where mdl = 'JFLEX' and nme_rule = 'EXCEL_FMT' and a.rule_idn = b.rule_idn "+
                              " order by srt_fr, dsc";
        ArrayList ary = new ArrayList();
        ArrayList outLst = db.execSqlLst("exlFormat",sqlExlFormat , ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
            exlFormt.put(nvl(rs.getString("dsc")).toUpperCase(), nvl(rs.getString("chr_fr")));
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        return exlFormt;
    }
    
//    public HSSFWorkbook getDataInXl(String typ,HttpServletRequest req ,String mdl) {
//             
//               int rowCt = 0;
//               short cellCt = -1;
//               String fontNm = "Cambria";
//               HSSFWorkbook hwb = new HSSFWorkbook(); // new HSSFWorkbook();
//               HSSFSheet sheet = hwb.createSheet(typ);
//               //CreationHelper createHelper = hwb.getCreationHelper();
//           
//               HSSFCellStyle hlink_style = hwb.createCellStyle();
//               HSSFFont hlink_font = hwb.createFont();
//               hlink_font.setUnderline(HSSFFont.U_SINGLE);
//               hlink_font.setFontName(fontNm);
//               hlink_font.setColor(HSSFColor.BLUE.index);
//               hlink_style.setFont(hlink_font);
//               //hlink_style.setFillPattern(HSSFCellStyle.SPARSE_DOTS);
//               //hlink_style.setFillForegroundColor(HSSFColor.BLUE.index);
//               
//               HSSFFont fontHdr = hwb.createFont();
//               fontHdr.setFontHeightInPoints((short)10);
//               fontHdr.setFontName(fontNm);
//               fontHdr.setColor(HSSFColor.BLACK.index);
//               fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
//           
//               HSSFCellStyle styleHdr = hwb.createCellStyle();
//               styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//               styleHdr.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
//               styleHdr.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//               styleHdr.setFont(fontHdr);
//               styleHdr.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
//               styleHdr.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
//               styleHdr.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
//               styleHdr.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
//               styleHdr.setTopBorderColor(HSSFColor.BLACK.index);
//               styleHdr.setBottomBorderColor(HSSFColor.BLACK.index);
//               styleHdr.setLeftBorderColor(HSSFColor.BLACK.index);
//               styleHdr.setRightBorderColor(HSSFColor.BLACK.index);
//              
//               //styleHdr.BORDER_DOTTED;
//               //styleHdr.set
//           
//               HSSFFont fontData = hwb.createFont();
//               fontData.setFontHeightInPoints((short)10);
//               fontData.setFontName(fontNm);
//               fontData.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
//               
//               HSSFFont fontStt = hwb.createFont();
//               fontStt.setFontHeightInPoints((short)10);
//               fontStt.setFontName(fontNm);
//               
//               
//               HSSFCellStyle styleDIS = hwb.createCellStyle();
//               styleDIS.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//               styleDIS.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
//               styleDIS.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//               styleDIS.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//               styleDIS.setFont(fontStt);
//               styleDIS.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
//               styleDIS.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
//               styleDIS.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
//               styleDIS.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
//               styleDIS.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
//               styleDIS.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
//               styleDIS.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
//               styleDIS.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
//               HSSFPalette palette1 = hwb.getCustomPalette();
//               palette1.setColorAtIndex(HSSFColor.SKY_BLUE.index, (byte)153,(byte)204, (byte)255);
//               
//               HSSFCellStyle styleData = hwb.createCellStyle();
//               styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//               styleData.setFont(fontData);
//               styleData.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
//               styleData.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
//               styleData.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
//               styleData.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
//               styleData.setTopBorderColor(HSSFColor.BLACK.index);
//               styleData.setBottomBorderColor(HSSFColor.BLACK.index);
//               styleData.setLeftBorderColor(HSSFColor.BLACK.index);
//               styleData.setRightBorderColor(HSSFColor.BLACK.index);
//              
//               //styleData.setDataFormat(HSSFDataFormat);
//           
//               HSSFFont fontDataBig = hwb.createFont();
//               fontDataBig.setFontHeightInPoints((short)10);
//               fontDataBig.setFontName(fontNm);
//               fontDataBig.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//           
//               HSSFCellStyle styleBig = hwb.createCellStyle();
//               styleBig.setAlignment(HSSFCellStyle.ALIGN_LEFT);
//               styleBig.setFont(fontDataBig);
//               styleBig.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
//               styleBig.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
//               styleBig.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
//               styleBig.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
//               styleBig.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
//               styleBig.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
//               styleBig.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
//               styleBig.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
//               styleBig.setFillForegroundColor(HSSFColor.BLUE.index);
//              
//               
//               
//               HSSFFont fontDataBold = hwb.createFont();
//               fontDataBold.setFontHeightInPoints((short)10);
//               fontDataBold.setFontName(fontNm);
//               fontDataBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//           
//               HSSFCellStyle styleBold = hwb.createCellStyle();
//               styleBold.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//               styleBold.setFont(fontDataBold);
//               styleBold.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
//               styleBold.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
//               styleBold.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
//               styleBold.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
//               styleBold.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
//               styleBold.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
//               styleBold.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
//               styleBold.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
//              
//               SortedSet autoCols = null;
//               HashMap exlFormat = EXLFormat();
//               HSSFRow row = null ;
//               HSSFCell cell = null ;
//             
//               int colNum = 0;
//               int rowNm = 0;
//               int rapValInx =0;
//               int totalCell=0;
//               int stCol = ++cellCt, stRow = rowCt;
//               boolean isSrch = false;
//               String logoNme = (String)exlFormat.get("LOGO");
//               if(!logoNme.equals("")){
//                    try {
//                        String servPath = req.getRealPath("/images/clientsLogo");
//    
//                         if(servPath.indexOf("/") > -1)
//                         servPath += "/" ;
//                         else {
//                                               //servPath = servPath.replace('\', '\\');
//                                               servPath += "\\" ;
//                           }
//                        FileInputStream fis=new FileInputStream(servPath+logoNme);
//                        ByteArrayOutputStream img_bytes = new ByteArrayOutputStream();
//                        int b;
//                        while ((b = fis.read()) != -1)
//                            img_bytes.write(b);
//                        fis.close();
//                        int imgWdt = 108, imgHt = 96;
//                       
//                        row = sheet.createRow((short)++rowCt);
//    
//                        HSSFClientAnchor anchor =
//                            new HSSFClientAnchor(1, 1, imgWdt, imgHt, (short)9, rowCt,
//                                                 (short)(stCol+4), (stRow + 5));
//    
//                        rowCt = stRow + 5;
//                        cellCt = (short)(stCol + 5);
//                        int index =
//                            hwb.addPicture(img_bytes.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG);
//                        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
//                        HSSFPicture logo = patriarch.createPicture(anchor, index);
//                        logo.resize();
//                       
//                        anchor.setAnchorType(2);
//    
//                    } catch (FileNotFoundException fnfe) {
//                        // TODO: Add catch code
//                        fnfe.printStackTrace();
//                    } catch (IOException ioe) {
//                        // TODO: Add catch code
//                        ioe.printStackTrace();
//                    }
//    
//               }
//               rowCt++;
//               String add1 = (String)exlFormat.get("ADD1");
//               if(!add1.equals("")){
//                 
//                         row = sheet.createRow((short)2);
//                         cell = row.createCell(11);
//                         cell.setCellValue(add1);
//                         cell.setCellStyle(styleBig);
//                         sheet.addMergedRegion(merge(2, 2, 11, 26));
//                     
//               }
//               String add2 = (String)exlFormat.get("ADD2");
//               if(!add2.equals("")){
//                 
//                         row = sheet.createRow((short)3);
//                         cell = row.createCell(11);
//                         cell.setCellValue(add2);
//                         cell.setCellStyle(styleBig);
//                         sheet.addMergedRegion(merge(3, 3, 11, 26));
//                     
//                }
//               
//               autoCols = new TreeSet();
//               rowCt++;
//               ArrayList colHdr = new ArrayList();
//               ArrayList vwPrpLst = getMemoXl(mdl);
//               if(vwPrpLst==null)
//                   initSrch();
//
//               
//     
//           try {
//             
//           
//           
//              
//             
//             
//               HashMap mprp = info.getMprp();
//    
//
//               String getQuot = "quot rte";
//              
//                 String srchQ =
//                   " select sk1, cts crtwt, "+
//                   //decode(quot, 0, 0, decode(nvl(rap_dis,0), 0, 101, trunc(((quot*100)/greatest(rap_rte,100)) - 100,2))) rr_dis, "+
//                   " decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis " +
//                   " , stk_idn, rap_rte , vnm, kts_vw kts, cert_lab cert, cert_no, rmk, img, to_char(trunc(cts,2),'90.99') cts, "+ getQuot
//                   +", rap_rte, trunc(cts,2)*rap_Rte rap_vlu "
//                   +", rmk , cmnt cmnt ,to_char(trunc(cts,2) * quot, '99999990.00') amt, decode(stt, 'MKAV', 'Available', 'MKIS', 'MEMO', 'MKTL',' ', 'SOLD') stt " ;
//
//            
//               
//               row = sheet.createRow((short)++rowCt);
//               cellCt = -1;
//               cell = row.createCell(++cellCt);
//               cell.setCellValue("Packet Id");
//               colHdr.add("PKT");
//               colNum = cell.getColumnIndex(); 
//               autoCols.add(Integer.toString(colNum));
//               //sheet.autoSizeColumn((short)colNum, true);
//                       
//               cell.setCellStyle(styleHdr);
//           
//           
//              
//               //sheet.autoSizeColumn((short)colNum, true);
//
//           for (int i = 0; i < vwPrpLst.size(); i++) {
//               String fld = "prp_";
//               int j = i + 1;
//               if (j < 10)
//                   fld += "00" + j;
//               else if (j < 100)
//                   fld += "0" + j;
//               else if (j > 100)
//                   fld += j;
//           
//               srchQ += ", " + fld;
//               
//               String prp = (String)vwPrpLst.get(i);
//               String prpDsc = (String)mprp.get(prp);
//
//                
//                       cell = row.createCell(++cellCt);
//                       cell.setCellValue(prpDsc);
//                       colHdr.add(prp);
//                       cell.setCellStyle(styleHdr);
//                       colNum = cell.getColumnIndex();
//                       autoCols.add(Integer.toString(colNum));
//               if (prp.equals("RTE")){
//                   cell = row.createCell(++cellCt);
//                   cell.setCellValue("Amount");
//                   colHdr.add("AMT");
//                   cell.setCellStyle(styleHdr);
//                   colNum = cell.getColumnIndex();
//                   autoCols.add(Integer.toString(colNum));
//               }
//           }                   
//                   //sheet.autoSizeColumn((short)colNum, true);
//        
//                   cell = row.createCell(++cellCt);
//                   cell.setCellValue("Rap Vlu");
//                   colHdr.add("RAPVAL");
//                  
//                   cell.setCellStyle(styleHdr);
//                   colNum = cell.getColumnIndex(); 
//                   autoCols.add(Integer.toString(colNum));
//                   //Freezws are
//               String flg = "M";
//            if(nvl(typ).equals("SRCH"))
//                flg = "Z";
//               sheet.createFreezePane(0, rowCt+1);
//                   String rsltQ =
//                       srchQ + " from gt_srch_rslt  where flg=? order by sk1 ";
//
//                   
//           
//           ArrayList ary = new ArrayList();
//           ary.add(flg);
//           ResultSet rs = db.execSql("stock", rsltQ, ary);
//           String grpCts ="";
//           String grpVlu = "";
//           String grpAvgDis ="";
//           String grpRapVlu = "";
//           String grpRteVlu = "";
//           String grpAvgVlue = "";
//               String grpQty = "";
//                   stRow = rowCt+1;
//               while (rs.next()) {
//
//               isSrch = true;
//
//               String cts = rs.getString("cts");
//               String certNo = rs.getString("cert_no");
//               totalCell = cellCt;
//               row = sheet.createRow((short)++rowCt);
//               cellCt = -1;
//
//               String vnm = rs.getString("vnm");
//
//
//
//
//
//               cell = row.createCell(++cellCt);
//               cell.setCellValue(vnm);
//               cell.setCellStyle(styleData);
//               //cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//               colNum = cell.getColumnIndex();
//               autoCols.add(Integer.toString(colNum));
//
//
//
//
//               for (int i = 0; i < vwPrpLst.size(); i++) {
//               String fld = "prp_";
//               int j = i + 1;
//               if (j < 10)
//               fld += "00" + j;
//               else if (j < 100)
//               fld += "0" + j;
//               else if (j > 100)
//               fld += j;
//               String prp = (String)vwPrpLst.get(i);
//               String prptyp = (String)mprp.get(prp+"T");
//               if (prp.equals("RTE"))
//               fld = "rte";
//               String fldVal = nvl(rs.getString(fld));
//               if (prp.equals("RAP_DIS"))
//               fldVal = nvl(rs.getString("r_dis"));
//               if (prp.equals("RAP_RTE"))
//               fldVal = nvl(rs.getString("rap_rte"));
//
//               fldVal = fldVal.trim();
//               if(fldVal.equals("NA"))
//                   fldVal="";
//               if(prp.equals("LAB") && fldVal.equals("GIA")){
//               cell = row.createCell(++cellCt);
//               cell.setCellValue(fldVal);
//               String link =(String)exlFormat.get("CERTLINK");
//               if(!link.equals("")){
//               link = link.replace("CERTNME",certNo);
//               link = link.replace("WT", cts);
//               HSSFHyperlink link1 =
//               new HSSFHyperlink(HSSFHyperlink.LINK_URL);
//               link1.setAddress(link);
//               cell.setHyperlink(link1);
//               cell.setCellStyle(hlink_style);
//               }else{
//               cell.setCellStyle(styleData);
//               }
//               colNum = cell.getColumnIndex();
//               autoCols.add(Integer.toString(colNum));
//               }else{
//                   
//               cell = row.createCell(++cellCt);
//               if(!fldVal.equals("") && prptyp.equals("N") && fldVal.indexOf("#")==-1 ){
//               cell.setCellValue(Double.parseDouble(fldVal));
//               cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//               }else {
//               cell.setCellValue(fldVal);
//               }
//
//
//               cell.setCellStyle(styleData);
//               colNum = cell.getColumnIndex();
//               autoCols.add(Integer.toString(colNum));
//                   if (prp.equals("RTE")){
//                       cell = row.createCell(++cellCt);
//                       fldVal= nvl(rs.getString("amt"));
//                       if(!fldVal.equals("") && fldVal.indexOf("#")==-1){
//                       cell.setCellValue(Double.parseDouble(fldVal));
//                       cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//                       }else {
//                       cell.setCellValue(fldVal);
//                       }
//
//
//                       cell.setCellStyle(styleData);
//                       colNum = cell.getColumnIndex();
//                       autoCols.add(Integer.toString(colNum));
//                   }
//               }
//               }
//                String rapVal = nvl(rs.getString("rap_vlu"));
//                   if(!rapVal.equals("")){
//                cell = row.createCell(++cellCt);
//                cell.setCellValue(rs.getDouble("rap_vlu"));
//                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//                cell.setCellStyle(styleData);
//                colNum = cell.getColumnIndex(); 
//                autoCols.add(Integer.toString(colNum));  
//                   }
//                   
//            }
//               if(isSrch){
//               stRow++;
//               row = sheet.createRow((short)++rowCt);
//               HashMap ttls = getTtls(req);
//                   int endrow=rowCt;
//             if(ttls.size() > 0){
//                   grpCts = (String)ttls.get(flg+"W");
//                   grpVlu = (String)ttls.get(flg+"V");
//                   grpAvgDis=(String)ttls.get(flg+"D");
//                   grpRapVlu=(String)ttls.get(flg+"R");
//                   grpQty =(String)ttls.get(flg+"Q");
//                   grpAvgVlue=(String)ttls.get(flg+"A");
//                 
//                 
//                   int cwtInx = vwPrpLst.indexOf("CRTWT");
//                   int rap_dis = vwPrpLst.indexOf("RAP_DIS");
//                   int rteInx = vwPrpLst.indexOf("RTE");
//                   int rapRteInx = vwPrpLst.indexOf("RAP_RTE");
//                   cell = row.createCell(0);
//                   cell.setCellValue("Grand Total");
//                   cell.setCellStyle(styleBold);
//                   colNum = cell.getColumnIndex();
//                   autoCols.add(Integer.toString(colNum));
//                     if(cwtInx>0){
//                   cell = row.createCell(cwtInx+1);
//                    int crtwtCol=colHdr.indexOf("CRTWT");
//                    String crtwtcolName=GetAlpha(crtwtCol+1);
//                  
//                   String strFormula = "SUM("+crtwtcolName+stRow+":"+crtwtcolName+endrow+")";
//                   cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
//                   cell.setCellFormula(strFormula);
//
//                   cell.setCellStyle(styleBold);
//                   colNum = cell.getColumnIndex();
//                   autoCols.add(Integer.toString(colNum));
//                    }
//                  if(rap_dis>0){
//                        int amtCol=colHdr.indexOf("AMT");
//                        String amtColName=GetAlpha(amtCol+1);
//                       
//                        int rapVluCol=colHdr.indexOf("RAPVAL");
//                        String rapVluColName=GetAlpha(rapVluCol+1);
//                        
//                      
//                        //    String strFormula = "SUM("+colName+stRow+":"+colName+endrow+")";
//                        String strFormula="(( ("+amtColName+(endrow+1)+")/("+rapVluColName+(endrow+1)+"))*100)-100";
//                   cell = row.createCell(rap_dis+1);
//                  
//                    cell.setCellFormula(strFormula);
//                   cell.setCellStyle(styleBold);
//                   colNum = cell.getColumnIndex();
//                  cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
//                   autoCols.add(Integer.toString(colNum));
//                    }
//                    if(rteInx>0){  
//                   cell = row.createCell(rteInx+1);
//                   int crtwtCol=colHdr.indexOf("CRTWT");
//                   String crtwtColName=GetAlpha(crtwtCol+1);
//                   int rteCol=colHdr.indexOf("RTE");
//                   String rtecolName=GetAlpha(rteCol+1);
//                  
//                           String strFormula="";
//                             for(int i=stRow ;i<=endrow;i++) {
//                                 if((i==endrow) && (stRow==endrow)) {
//                                strFormula+="(("+crtwtColName+i+"*"+rtecolName+i+"))";    
//                                 }else
//                                 {
//                              if(i==stRow) {
//                                strFormula+="(("+crtwtColName+i+"*"+rtecolName+i+")+";
//                              }else if(i==endrow) {
//                               strFormula+="("+crtwtColName+i+"*"+rtecolName+i+"))";   
//                                 }
//                                 else
//                                 {
//                               strFormula+="("+crtwtColName+i+"*"+rtecolName+i+")+";
//                                 }
//                                 }
//                             }
//                           strFormula = strFormula+"/("+crtwtColName+(endrow+1)+")";
//                   cell.setCellStyle(styleBold);
//                   colNum = cell.getColumnIndex();
//                   cell.setCellFormula(strFormula);
//                   cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
//                   autoCols.add(Integer.toString(colNum));
//                       }
////                       if(rapRteInx>1){    
////                   cell = row.createCell(rapRteInx+1);
////                   if(!grpRapVlu.equals(""))
////                   {
////                   cell.setCellValue(Double.parseDouble(grpRapVlu));
////                   cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
////                   }
////                   else {
////                   cell.setCellValue(grpRapVlu);
////                   }
////                   cell.setCellStyle(styleBold);
////                   colNum = cell.getColumnIndex();
////                   autoCols.add(Integer.toString(colNum));
////                    }
//                 if(rteInx>0){  
//                        int amtCol=colHdr.indexOf("AMT");
//                       
//                       cell = row.createCell(rteInx+2);
//                     
//                        String colName=GetAlpha(amtCol+1);
//                        String strFormula = "SUM("+colName+stRow+":"+colName+endrow+")";
//                       cell.setCellFormula(strFormula);
//                       cell.setCellStyle(styleBold);
//                       colNum = cell.getColumnIndex();
//                      cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
//                       autoCols.add(Integer.toString(colNum));
//                    }
//                 
//                       rapValInx=colHdr.indexOf("RAPVAL");
//                       cell = row.createCell(rapValInx);
//                       String rapVlucolName=GetAlpha(rapValInx+1);
//                       String strFormula1 = "SUM("+rapVlucolName+stRow+":"+rapVlucolName+endrow+")";
//                       cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
//                       cell.setCellFormula(strFormula1);
//                       cell.setCellStyle(styleBold);
//                       colNum = cell.getColumnIndex();
//                       autoCols.add(Integer.toString(colNum));
//                 
//                   Iterator it=autoCols.iterator();
//                   while(it.hasNext()) {
//                   String value=(String)it.next();
//                   int colId = Integer.parseInt(value);
//                   sheet.autoSizeColumn((short)colId, true);
//
//                   }
//
//                   String sumSheet = (String)exlFormat.get("SHEET_SMRY");
//                   if(sumSheet.equals("YES")){
//                   rowCt= rowCt+2;
//
//                   row = sheet.createRow((short)++rowCt);
//                   cell = row.createCell(0);
//                   cell.setCellValue("Details Description of Diamonds with given Prices");
//                   cell.setCellStyle(styleBig);
//                   sheet.addMergedRegion(merge(rowCt, rowCt, 0, 6));
//               
//                   row = sheet.createRow((short)++rowCt);
//                   cell = row.createCell(0);
//                   cell.setCellValue("Total Pcs");
//                   cell.setCellStyle(styleBig);
//
//                   sheet.addMergedRegion(merge(rowCt, rowCt, 0, 2));
//                      
//                   cell = row.createCell(3);
//                   
//                   if(!grpQty.equals("") && grpQty.indexOf("#")==-1)
//                   {
//                   cell.setCellValue(Double.parseDouble(grpQty));
//                   cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//                   }else {
//                   cell.setCellValue(grpQty);
//                   }
//                   cell.setCellStyle(styleBig);
//
//                   sheet.addMergedRegion(merge(rowCt, rowCt, 3, 6));
//                  
////                       if(cwtInx>0){
////                   row = sheet.createRow((short)++rowCt);
////                   cell = row.createCell(0);
////                   cell.setCellValue("Total Crts");
////                   cell.setCellStyle(styleBig);
////
////                   sheet.addMergedRegion(merge(rowCt, rowCt, 0, 2));
////                   cell = row.createCell(3);
////                   if(!grpCts.equals(""))
////                   {
////                   cell.setCellValue(Double.parseDouble(grpCts));
////                   cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
////                   }else {
////                   cell.setCellValue(grpCts);
////                   }
////                   cell.setCellStyle(styleBig);
////
////                   sheet.addMergedRegion(merge(rowCt, rowCt, 3, 6));
////                       }
//                       if(rap_dis>0){
//                       row = sheet.createRow((short)++rowCt);
//                       cell = row.createCell(0);
//                       cell.setCellValue("Total Avg.Dis");
//                       cell.setCellStyle(styleBig);
//
//                       sheet.addMergedRegion(merge(rowCt, rowCt, 0, 2));
//                       cell = row.createCell(3);
//                        int amtCol=colHdr.indexOf("AMT");
//                        String amtColName=GetAlpha(amtCol+1);
//                        int rapVluCol=colHdr.indexOf("RAPVAL");
//                        String rapVluColName=GetAlpha(rapVluCol+1);
//                        String strFormula="(( ("+amtColName+(endrow+1)+")/("+rapVluColName+(endrow+1)+"))*100)-100";
//                        cell.setCellFormula(strFormula);
//                        cell.setCellStyle(styleBig);
//                        cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
//                       sheet.addMergedRegion(merge(rowCt, rowCt, 3, 6));
//                       }
////                       if(rapRteInx>1){    
////                   row = sheet.createRow((short)++rowCt);
////                   cell = row.createCell(0);
////                   cell.setCellValue("Total Avg.Rap%");
////                   cell.setCellStyle(styleBig);
////
////                   sheet.addMergedRegion(merge(rowCt, rowCt, 0, 2));
////                   cell = row.createCell(3);
////                   if(!grpRapVlu.equals(""))
////                   {
////                   cell.setCellValue(Double.parseDouble(grpRapVlu));
////                   cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
////                   }else {
////                   cell.setCellValue(grpRapVlu);
////                   }
////                   cell.setCellStyle(styleBig);
////
////                   sheet.addMergedRegion(merge(rowCt, rowCt, 3, 6));
////                       }
//                       if(rteInx>0){    
//                   row = sheet.createRow((short)++rowCt);
//                   cell = row.createCell(0);
//                   cell.setCellValue("Total Avg. Price ($)");
//                   cell.setCellStyle(styleBig);
//
//                   sheet.addMergedRegion(merge(rowCt, rowCt, 0, 2));
//
//                   cell = row.createCell(3);
//                      
//                           int crtwtCol=colHdr.indexOf("CRTWT");
//                           String crtwtColName=GetAlpha(crtwtCol+1);
//                           int rteCol=colHdr.indexOf("RTE");
//                           String rtecolName=GetAlpha(rteCol+1);
//                         
//                                   String strFormula="";
//                                     for(int i=stRow ;i<=endrow;i++) {
//                                         if((i==endrow) && (stRow==endrow)) {
//                                        strFormula+="(("+crtwtColName+i+"*"+rtecolName+i+"))";    
//                                         }else
//                                         {
//                                      if(i==stRow) {
//                                        strFormula+="(("+crtwtColName+i+"*"+rtecolName+i+")+";
//                                      }else if(i==endrow) {
//                                       strFormula+="("+crtwtColName+i+"*"+rtecolName+i+"))";   
//                                         }
//                                         else
//                                         {
//                                       strFormula+="("+crtwtColName+i+"*"+rtecolName+i+")+";
//                                         }
//                                         }
//                                     }
//                           strFormula = strFormula+"/("+crtwtColName+(endrow+1)+")";
//                           cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
//                           cell.setCellStyle(styleBig);
//                           colNum = cell.getColumnIndex();
//                           cell.setCellFormula(strFormula);
//                         
//                   sheet.addMergedRegion(merge(rowCt, rowCt, 3, 6));
//
//                       }
//                       if(rteInx>0){  
//                   row = sheet.createRow((short)++rowCt);
//                   cell = row.createCell(0);
//                   cell.setCellValue("Total Amt. ($)");
//                   cell.setCellStyle(styleBig);
//
//                   sheet.addMergedRegion(merge(rowCt, rowCt, 0, 2));
//                  int amtCol=colHdr.indexOf("AMT");
//                   cell = row.createCell(3);
//                    String colName=GetAlpha(amtCol+1);
//                    String strFormula = "SUM("+colName+stRow+":"+colName+endrow+")";
//                    cell.setCellFormula(strFormula);
//                    cell.setCellStyle(styleBold);
//                    colNum = cell.getColumnIndex();
//                    autoCols.add(Integer.toString(colNum));
//                   cell.setCellStyle(styleBig);
//                    cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
//                   sheet.addMergedRegion(merge(rowCt, rowCt, 3, 6));
//                       }
//                   }
//                   }
//               }
//
//               } catch (SQLException sqle) {
//               // TODO: Add catch code
//               sqle.printStackTrace();
//               }
//
//
//
//               return hwb ;
//               }

    public CellRangeAddress merge(int fRow, int lRow, int fCol, int lCol) {
            CellRangeAddress cra = new CellRangeAddress(fRow, lRow, fCol, lCol);
            return cra;
     }
     
    private String GetAlpha(int num)
       {
            int A = 65;    //ASCII value for capital A
         String sCol = "";
         int iRemain = 0;
         // THIS ALGORITHM ONLY WORKS UP TO ZZ. It fails on AAA
         if (num > 701) 
           {                
               return null;
           }
         if (num <= 26) 
           {
               if (num == 0)
               {
                   sCol = (char)((A + 26) - 1)+sCol; 
               }
               else
               {
                   sCol =(char)((A + num) - 1)+sCol;
               }
           }
         else
           {
               iRemain = ((num / 26))-1;
               if ((num % 26) == 0)
               {
                   sCol = GetAlpha(iRemain) + GetAlpha(num % 26);
               }
               else
               {                    
                   sCol = (char)((A + iRemain)) + GetAlpha(num % 26);
               }
         }
           return sCol;

       }

    
    public HashMap getTtls(HttpServletRequest req) {

        db.execUpd("delete gt", "delete from gt_smry ", new ArrayList());
        int ct = db.execCall("gettotal", "DP_GT_SMRY", new ArrayList());

        HashMap ttls = new HashMap();
        ResultSet rs = null;
        String getTotals =
        " select flg , qty, cts, quot vlu,to_char(rap,99999999999999.99) rap, AVG_QUOT_DIS avg_disc,AVG_QUOT avg , AVG_RAP, avg_rte,cst cval,avg_cst cavg,avg_cst_dis cdis from gt_smry ";
        ArrayList outLst = db.execSqlLst(" page Totals ", getTotals, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
        try {
        while(rs.next()) {
        String lflg = rs.getString("flg");
        int qty = rs.getInt("qty");
        String cts = rs.getString("cts");
        String vlu = nvl(rs.getString("vlu"));
        String dis = nvl(rs.getString("avg_disc"));
        ttls.put(lflg+"Q", Integer.toString(qty));
        ttls.put(lflg+"W", cts);
        ttls.put(lflg+"V", vlu);
        ttls.put(lflg+"A", nvl(rs.getString("avg")));
        ttls.put(lflg+"D", dis);
        ttls.put(lflg+"R", nvl(rs.getString("rap")));
        //    ttls.put(lflg+"R", nvl(rs.getString("AVG_RAP")));
        ttls.put(lflg+"B", nvl(rs.getString("avg_rte")));
        ttls.put(lflg+"CA", nvl(rs.getString("cavg")));
        ttls.put(lflg+"CV", nvl(rs.getString("cval")));
        ttls.put(lflg+"CD", nvl(rs.getString("cdis")));
        }
            rs.close();
            pst.close();
                      
           String cnt = nvl((String)info.getDmbsInfoLst().get("CNT"));
            if(cnt.equalsIgnoreCase("kj")){
                ArrayList ary = new ArrayList();
              String pktCnt = nvl2((String)ttls.get("MQ"), "0");
              String selValue =nvl2((String)ttls.get("MV"), "0"); 
              String selAvg = nvl2((String)ttls.get("MA"), "0");
              String netDsc = "";
              String loyallow="",memallow="";
              if(pktCnt!="0"){
                   String mPct  = String.valueOf(info.getRap_pct());
                   String favSrch = "select GET_MEMBER_DISCOUNT mem_dis,loyalty_pkg.byr_allowed(?) loyallow,GET_MEMBER_DISCOUNT_ALLOWED(?) memallow from dual";
                   ary = new ArrayList();
                   ary.add(String.valueOf(info.getByrId()));
                   ary.add(String.valueOf(info.getByrId()));
                   outLst = db.execSqlLst("favSrch",favSrch, ary);
                   pst = (PreparedStatement)outLst.get(0);
                   rs = (ResultSet)outLst.get(1);
                   while(rs.next()){
                       mPct=nvl(rs.getString("mem_dis"));
                       loyallow=nvl(rs.getString("loyallow"));
                       memallow=nvl(rs.getString("memallow"));
                   }
                   rs.close();
                   pst.close();
                double lPct=0;
                double lVlu=0;
                int ldiff=0;
                String lctg="0";
                if(loyallow.equals("YES")){
                String loyDisc = " LOYALTY_PKG.GET_DTLS(pNmeIdn=>?, pSelVlu =>?, lVlu=>?, lLoyVlu=>?, lCtg=>?, lPct=>? , lDiff=>?) ";
                ary = new ArrayList();
                ary.add(String.valueOf(info.getByrId()));
                ary.add(String.valueOf(selValue));
                ArrayList out1 = new ArrayList();
                out1.add("I");
                out1.add("I");
                out1.add("V");
                out1.add("I");
                out1.add("I");
                CallableStatement cst = db.execCall("Calculate Loyalty",loyDisc , ary , out1) ;
                lVlu= cst.getDouble(ary.size()+2);
                lctg= cst.getString(ary.size()+3);
                lPct= cst.getDouble(ary.size()+4);
                ldiff= cst.getInt(ary.size()+5);
                cst.close();
                cst=null;
                }else{
                    lPct=0;
                    lVlu=0;
                    ldiff=0;
                    lctg="0";
                }
                if(!loyallow.equals("YES"))
                    mPct="0"  ;
                String totalD =String.valueOf(Integer.parseInt(mPct) + lPct) ;
                String netDiscQ =" select decode(rap, 0, null, trunc((((quot*cts)*(100-"+totalD+")/100)/(rap*cts)*100) - 100, 2)) netdsc from gt_smry where flg = 'M' ";
                   outLst = db.execSqlLst("Net Disc", netDiscQ, new ArrayList()); 
                   pst = (PreparedStatement)outLst.get(0);
                   rs = (ResultSet)outLst.get(1);
                if(rs.next()) {  
                netDsc = nvl2((String)rs.getString("netdsc"),"0");
                }
                   rs.close();
                   pst.close();
                
               double mval = (Double.parseDouble(selValue) * Double.parseDouble(mPct)) / 100 ;
               double  nprc = ((Double.parseDouble(selAvg) * (100 - (Double.parseDouble(mPct) + lPct))) / 100)  ;
               double  nval = (Double.parseDouble(selValue) - mval - lVlu )  ;    
                ttls.put("NETPRC",String.valueOf(roundToDecimals(nprc, 2)));
                ttls.put("NETVAL",String.valueOf(roundToDecimals(nval, 2)));
                ttls.put("NETDIS",netDsc);
                ttls.put("DIFF",String.valueOf(ldiff));
                ttls.put("CTG",lctg);
                 ttls.put("LOYVLU",String.valueOf(lVlu));
                 ttls.put("MEMVLU",String.valueOf(roundToDecimals(mval,2)));
                 ttls.put("LOYPCT",String.valueOf(lPct));
               }else{
                ttls.put("NETPRC","0");
                ttls.put("NETVAL","0");
                ttls.put("NETPRC","0");
                ttls.put("DIFF","0");
                ttls.put("CTG","0");
                ttls.put("LOYVLU","0");
                ttls.put("MEMVLU","0");
                ttls.put("LOYPCT","0");
              }
              
            }
                      

        } catch (SQLException e) {

        }
        return ttls;
    }
       
    public String convertNumToAlp(String total){

    String[] unitdo ={"", " One", " Two", " Three", " Four", " Five",
    " Six", " Seven", " Eight", " Nine", " Ten", " Eleven", " Twelve",
    " Thirteen", " Fourteen", " Fifteen", " Sixteen", " Seventeen",
    " Eighteen", " Nineteen"};
    String[] tens = {"", "Ten", " Twenty", " Thirty", " Forty", " Fifty",
    " Sixty", " Seventy", " Eighty"," Ninety"};
    String[] digit = {"", " Hundred", " Thousand", " Lakh", " Crore"};




    //Defining variables q is quotient, r is remainder

    int len, q=0, r=0;
    String ltr = " ";
    String Str = "";

    ArrayList vals = new ArrayList();

        if(total.indexOf(".")>-1){
          int num = Integer.parseInt(total.substring(0, total.indexOf(".")));
          vals.add(Integer.toString(num));
          int val2 = Integer.parseInt(total.substring(total.indexOf(".")+1));
          vals.add(String.valueOf(val2));
          }else {
          vals.add(total);
          }
          for(int i=0; i < vals.size(); i++){
          int num = Integer.parseInt((String)vals.get(i));
          if(i==1)
          Str += " and ";
          while (num > 0) {

          len = numberCount(num);

          switch (len)

          {
          case 8:
          q = num / 10000000;
          r = num % 10000000;
          ltr = twonum(q);
          Str = Str + ltr + digit[4];
          num = r;
          break;

          case 7:
          case 6:
          q = num / 100000;
          r = num % 100000;
          ltr = twonum(q);
          Str = Str + ltr + digit[3];
          num = r;
          break;

          case 5:
          case 4:

          q = num / 1000;
          r = num % 1000;
          ltr = twonum(q);
          Str = Str + ltr + digit[2];
          num = r;
          break;

          case 3:


          if (len == 3)
          r = num;
          ltr = threenum(r);
          Str = Str + ltr;
          num = 0;
          break;

          case 2:

          ltr = twonum(num);
          Str = Str + ltr;
          num = 0;
          break;

          case 1:
          Str = Str + unitdo[num];
          num = 0;
          break;
          default:

          num = 0;
//          System.out.println("Exceeding Crore....No conversion");


          }
          }
          if(i==1)
          Str += " CENTS ONLY ";

          }


          return Str;
          }


    public int numberCount(int num)
    {
    int cnt=0;
    int r;
    while (num>0)
    {
    r = num%10;
    cnt++;
    num = num / 10;
    }

    return cnt;
    }

    //Function for Conversion of two digit

    public String twonum(int numq)
    {
    String[] unitdo ={"", " One", " Two", " Three", " Four", " Five",
    " Six", " Seven", " Eight", " Nine", " Ten", " Eleven", " Twelve",
    " Thirteen", " Fourteen", " Fifteen", " Sixteen", " Seventeen",
    " Eighteen", " Nineteen"};
    String[] tens = {"", "Ten", " Twenty", " Thirty", " Forty", " Fifty",
    " Sixty", " Seventy", " Eighty"," Ninety"};
    String[] digit = {"", " Hundred", " Thousand", " Lakh", " Crore"};


    int numr, nq;
    String ltr="";

    nq = numq / 10;
    numr = numq % 10;

    if (numq>19)
    {
    ltr=ltr+tens[nq]+unitdo[numr];
    }
    else
    {
    ltr = ltr+unitdo[numq];
    }

    return ltr;
    }

    //Function for Conversion of three digit

    String threenum(int numq)
    {
    int numr, nq;
    String ltr = "";
    String[] unitdo ={"", " One", " Two", " Three", " Four", " Five",
    " Six", " Seven", " Eight", " Nine", " Ten", " Eleven", " Twelve",
    " Thirteen", " Fourteen", " Fifteen", " Sixteen", " Seventeen",
    " Eighteen", " Nineteen"};
    String[] tens = {"", "Ten", " Twenty", " Thirty", "Forty", " Fifty",
    " Sixty", " Seventy", " Eighty"," Ninety"};
    String[] digit = {"", " Hundred", " Thousand", " Lakh", " Crore"};
    nq = numq / 100;
    numr = numq % 100;

    if (numr == 0)
    {
    ltr = ltr + unitdo[nq]+digit[1];
    }
    else
    {
    ltr = ltr +unitdo[nq]+digit[1]+" and "+twonum(numr);
    }
    return ltr;

    }


    public String nvl(String pVal) {
        return nvl(pVal, "");
    }
    
    public String nvl(String pVal, String rVal) {
    if(pVal == null)
        pVal = rVal;
        else if(pVal.equals(""))
          pVal = rVal;
    return pVal;
//        String val = pVal ;
//        if(pVal == null)
//            val = rVal;
//        else if(pVal.equals(""))
//          val = rVal;
//        return val;

}
  public String nvl2(String pVal, String nVal) {
      return nvl(pVal, nVal);
  }
  
  public String nvl3(String pVal, String rVal) {
      String val = pVal ;
      if(pVal == null || pVal=="")
          val = rVal;
      return val;
  }
  
  public int getSeqVal(String seqNm) {
          int lSeqVal = 0 ;
          String sql = " select "+ seqNm + ".NextVal seqVal from dual ";
          ResultSet rs = db.execSql(" get seq val "+ seqNm, sql, new ArrayList());
          try {
              if(rs.next()) {
              lSeqVal = rs.getInt(1);
          }
              rs.close();
          } catch (SQLException e) {
          }
          return lSeqVal ;
      }
  
  public void emptyGT() {
    emptyGT("all");
  }
  
  public int getProcess(String stt){
      int prcIdn=0;
      ArrayList ary = new ArrayList();
      ary.add(stt);
      ArrayList outLst = db.execSqlLst("mprcId", "select idn from mprc where is_stt=? and stt='A'", ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        try {
            if (rs.next()) {
                prcIdn = rs.getInt(1);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        return prcIdn;
  }
  
    public int msrch(HttpServletRequest req , ActionForm af , ArrayList prcPrpList , String mdl){
       
        RepriceFrm form = (RepriceFrm)af;
    
        int lSrchId =0;
        String getSrchId = "srch_pkg.get_srch_id(pRlnId => ?, pTyp => ?, pTrmVal => ?, pMdl => ?, pFlg => ?, pSrchId => ?)";
        ArrayList params = new ArrayList();
        params.add("104");
        params.add("Z");  
        params.add("1");
        params.add("Z");
        params.add("");  
          
        ArrayList outParams = new ArrayList();
        outParams.add("I");
          
         CallableStatement cst = db.execCall(" Add Srch Hdr ", getSrchId, params, outParams);
          try {
            lSrchId = cst.getInt(params.size()+1);
            cst.close();
            cst=null;
          } catch (SQLException e) {
          }
        
        
        if(lSrchId > 0) {
        HashMap prp = info.getPrp();
        HashMap mprp = info.getMprp();
        String addSrchDtl = "srch_pkg.add_srch_dtl(pSrchId => ?, pPrp => ?, pSrtFr => ?, pSrtTo => ?)";
        String addSrchDtlVal = "srch_pkg.add_srch_dtl_val(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
        String addSrchDtlSub = "srch_pkg.add_srch_dtl_sub(pSrchId => ?, pPrp => ?, pSrtFr => ?, pSrtTo => ?)";
        String addSrchDtlSubVal = "srch_pkg.add_srch_dtl_sub_val(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
        String addSrchDtlDte = "srch_pkg.add_srch_dtl_dte(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
        for (int i = 0; i < prcPrpList.size(); i++) {
          boolean dtlAddedOnce = false ;
            int cnt =0;
          ArrayList srchPrp = (ArrayList) prcPrpList.get(i);
          String lprp = (String)srchPrp.get(0);
          String flg= (String)srchPrp.get(1);
          String prpSrt = lprp ;  
          String lprpTyp = nvl((String)mprp.get(lprp+"T"));
         
            if(lprp.equals("CRTWT"))
                prpSrt = "SIZE";
            ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
            ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
            
           
            
            if(flg.equals("M")) {
                if(lprp.equalsIgnoreCase("CRTWT")) {
                  
                      for(int j=0; j < lprpS.size(); j++) {
                        String lSrt = (String)lprpS.get(j);
                        String reqVal1 = nvl((String)form.getValue(lprp + "_1_" + lSrt),"");
                        String reqVal2 = nvl((String)form.getValue(lprp + "_2_" + lSrt),"");
                          if((reqVal1.length() == 0) || (reqVal2.length() == 0)) {
                            //ignore no value selected;
                          } else {
                              if(!dtlAddedOnce) {
                                params = new ArrayList();
                                params.add(String.valueOf(lSrchId));
                                params.add(lprp);
                                params.add(reqVal1);
                                params.add(reqVal2);
                                cnt = db.execCall(" SrchDtl ", addSrchDtl, params);
                                dtlAddedOnce = true;
                              }
                              params = new ArrayList();
                              params.add(String.valueOf(lSrchId));
                              params.add(lprp);
                              params.add(reqVal1);
                              params.add(reqVal2);
                              cnt = db.execCall(" SrchDtl ", addSrchDtlSub, params);
                          }
                      }
                  }    
                 else {
                  //All Other Multiple Properties
                    
                  for(int j=0; j < lprpS.size(); j++) {
                    String lSrt = (String)lprpS.get(j);
                    String lVal = (String)lprpV.get(j);
                    String lFld =  lprp + "_" + lVal; 
                    String reqVal = nvl((String)form.getValue(lFld));
                    //util.SOP(lprp + " : " + lFld + " : " + reqVal);  
                      if(reqVal.length() > 0) {  
                        if(!dtlAddedOnce) {
                          params = new ArrayList();
                          params.add(String.valueOf(lSrchId));
                          params.add(lprp);
                          params.add(reqVal);
                          params.add(reqVal);
                          cnt = db.execCall(" SrchDtl ", addSrchDtlVal, params);
                          dtlAddedOnce = true;
                        }
                        params = new ArrayList();
                        params.add(String.valueOf(lSrchId));
                        params.add(lprp);
                        params.add(reqVal);
                        params.add(reqVal);
                        cnt = db.execCall(" SrchDtl ", addSrchDtlSubVal, params);
                    }
                  }
                }     
            } else {
                
                
              String reqVal1 = nvl((String)form.getValue(lprp + "_1"),"");
              String reqVal2 = nvl((String)form.getValue(lprp + "_2"),"");
                if(lprpTyp.equals("T"))
                    reqVal2 = reqVal1;
              //util.SOP(lprp + " : "+ reqVal1 + " : " + reqVal2);  
              
              if(((reqVal1.length() == 0) || (reqVal2.length() == 0))
                || ((reqVal1.equals("0")) && (reqVal2.equals("0")) )) {
                  //ignore no selection;
              } else {
                  if(lprpTyp.equals("T")){
                     ArrayList fmtVal = getStrToArr(reqVal1);
                     if(fmtVal!=null && fmtVal.size()>0){
                     for(int k=0;k<fmtVal.size();k++){
                         String txtVal = (String)fmtVal.get(k);
                         if(!dtlAddedOnce) {
                         params = new ArrayList();
                         params.add(String.valueOf(lSrchId));
                         params.add(lprp);
                         params.add(txtVal);
                         params.add(txtVal);
                         cnt = db.execCallDir(" SrchDtl ", addSrchDtlVal, params);
                         dtlAddedOnce = true;
                         }
                         params = new ArrayList();
                         params.add(String.valueOf(lSrchId));
                         params.add(lprp);
                         params.add(txtVal);
                         params.add(txtVal);
                         cnt = db.execCallDir(" SrchDtl ", addSrchDtlSubVal, params);
                     }
                     }
                     }else{
                params = new ArrayList();
                params.add(String.valueOf(lSrchId));
                params.add(lprp);
                params.add(reqVal1);
                params.add(reqVal2);
              if(lprpTyp.equals("T"))
                cnt = db.execCall(" SrchDtl ", addSrchDtlVal, params);
              else if(lprpTyp.equals("D"))
                cnt = db.execCall(" SrchDtl ", addSrchDtlDte, params);
              else
                  cnt = db.execCall(" SrchDtl ", addSrchDtl, params);  
                
              }}
            }
          }    
        }   
        
            String dbSrch = "pkgmkt.srch(?, ?)";
            params = new ArrayList();
            params.add(String.valueOf(lSrchId));
            params.add(mdl);
            int cnt = db.execCall(" Srch : " + lSrchId, dbSrch, params);
            return cnt;
    }
    
    
    
    public int genericSrch(HashMap paramsMap){
            String addSrchDtl = "srch_pkg.add_srch_dtl(pSrchId => ?, pPrp => ?, pSrtFr => ?, pSrtTo => ?)";
            String addSrchDtlVal = "srch_pkg.add_srch_dtl_val(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
            String addSrchDtlDte = "srch_pkg.add_srch_dtl_dte(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
            String addSrchDtlSub = "srch_pkg.add_srch_dtl_sub(pSrchId => ?, pPrp => ?, pSrtFr => ?, pSrtTo => ?)";
            String addSrchDtlSubVal = "srch_pkg.add_srch_dtl_sub_val(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
            
    String gtDel = nvl((String)paramsMap.get("DELGT"));
    if(gtDel.equals("NO")){

    }else{
    db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());
    }
    int lSrchId =0;
    String stt = nvl((String)paramsMap.get("stt"));
    String mdl = nvl((String)paramsMap.get("mdl"));
    String getSrchId = "srch_pkg.get_srch_id(pRlnId => ?, pTyp => ?, pTrmVal => ?, pMdl => ?, pFlg => ?, pSrchId => ?)";
    ArrayList params = new ArrayList();
    params.add(String.valueOf(0));
    params.add(stt);
    params.add("1");
    params.add("Z");
    params.add("");
    ArrayList outParams = new ArrayList();
    outParams.add("I");
    CallableStatement cst = db.execCall(" Add Srch Hdr ", getSrchId, params, outParams);
    try {
    lSrchId = cst.getInt(params.size()+1);
      cst.close();
      cst=null;
//        System.out.println("Srch ID = >"+lSrchId);
    } catch (SQLException e) {
    }
    info.setSrchId(lSrchId);
           
    if(lSrchId > 0) {
    int cnt=0;
    ArrayList gncPrpLst = info.getGncPrpLst();
    ArrayList prplist=null;
    HashMap prp = info.getPrp();
    HashMap mprp = info.getMprp();
    for (int i = 0; i < gncPrpLst.size(); i++) {
    boolean dtlAddedOnce = false ;
    prplist =(ArrayList)gncPrpLst.get(i);
    String lprp = (String)prplist.get(0);
    String flg= (String)prplist.get(1);
    String lprpTyp = nvl((String)mprp.get(lprp+"T"));
    String prpSrt = lprp ;
    String reqVal1="";
    String reqVal2="";
    if(flg.equals("M") || flg.equals("CTA")) {
    ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
    ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
    for(int j=0; j < lprpS.size(); j++) {
    String lSrt = (String)lprpS.get(j);
    String lVal = (String)lprpV.get(j);
    reqVal1 = nvl((String)paramsMap.get(lprp + "_" + lVal),"");
    reqVal2 = nvl((String)paramsMap.get(lprp + "_" + lVal),"");
    if((reqVal1.length() == 0) || (reqVal2.length() == 0)) {
    //ignore no value selected;
    }else{
    if(!dtlAddedOnce) {
    params = new ArrayList();
    params.add(String.valueOf(lSrchId));
    params.add(lprp);
    params.add(reqVal1);
    params.add(reqVal2);
    cnt = db.execCallDir(" SrchDtl ", addSrchDtlVal, params);
    dtlAddedOnce = true;
    }
    params = new ArrayList();
    params.add(String.valueOf(lSrchId));
    params.add(lprp);
    params.add(reqVal1);
    params.add(reqVal2);
    cnt = db.execCallDir(" SrchDtl ", addSrchDtlSubVal, params);
    }
    }
    }else if(flg.equals("SM")){
      String[]  reqValLst = (String[])paramsMap.get(lprp);
      if(reqValLst !=null && reqValLst.length>0){
          for(int s=0;s<reqValLst.length;s++){
            String  reqVal= reqValLst[s];
          if(!dtlAddedOnce) {
          params = new ArrayList();
          params.add(String.valueOf(lSrchId));
          params.add(lprp);
          params.add(reqVal);
          params.add(reqVal);
          cnt = db.execCallDir(" SrchDtl ", addSrchDtl, params);
          dtlAddedOnce = true;
          }
          params = new ArrayList();
          params.add(String.valueOf(lSrchId));
          params.add(lprp);
          params.add(reqVal);
          params.add(reqVal);
          cnt = db.execCallDir(" SrchDtl ", addSrchDtlSub, params);
          }  
      }
    }else{
    reqVal1 = nvl((String)paramsMap.get(lprp + "_1"));
    reqVal2 = nvl((String)paramsMap.get(lprp + "_2"));
        if(lprpTyp.equals("T"))
       reqVal2 = reqVal1;
    //util.SOP(lprp + " : "+ reqVal1 + " : " + reqVal2);
        
    if(((reqVal1.length() == 0) || (reqVal2.length() == 0))
    || ((reqVal1.equals("0")) && (reqVal2.equals("0")) )) {
    //ignore no selection;
    } else {
     if(lprpTyp.equals("T")){
     ArrayList fmtVal=new ArrayList();
     fmtVal = getStrToArr(reqVal1);
     if(flg.equals("TS")){
     fmtVal=new ArrayList();
     fmtVal.add(getVendorNm(reqVal1));
     }
     if(fmtVal!=null && fmtVal.size()>0){
     for(int k=0;k<fmtVal.size();k++){
         String txtVal = (String)fmtVal.get(k);
         if(!dtlAddedOnce) {
         params = new ArrayList();
         params.add(String.valueOf(lSrchId));
         params.add(lprp);
         params.add(txtVal);
         params.add(txtVal);
         cnt = db.execCallDir(" SrchDtl ", addSrchDtlVal, params);
         dtlAddedOnce = true;
         }
         params = new ArrayList();
         params.add(String.valueOf(lSrchId));
         params.add(lprp);
         params.add(txtVal);
         params.add(txtVal);
         cnt = db.execCallDir(" SrchDtl ", addSrchDtlSubVal, params);
     }
     }
     }else{
    params = new ArrayList();
    params.add(String.valueOf(lSrchId));
    params.add(lprp);
    params.add(reqVal1);
    params.add(reqVal2);
    if(lprpTyp.equals("T"))
    cnt = db.execCallDir(" SrchDtl ", addSrchDtlVal, params);
    else if(lprpTyp.equals("D"))
    cnt = db.execCallDir(" SrchDtl ", addSrchDtlDte, params);
    else
    cnt = db.execCallDir(" SrchDtl ", addSrchDtl, params);
     }
    }
    }
    }}
    String partyNme=  nvl((String)paramsMap.get("MFG_PARTY_1"));
    if(!partyNme.equals("")){
        params = new ArrayList();
        params.add(String.valueOf(lSrchId));
        params.add("MFG_PARTY");
        params.add(partyNme);
        params.add(partyNme);
      int  cnt = db.execCallDir(" SrchDtl ", addSrchDtl, params);
        
    }
    String[] sttLst = (String[])paramsMap.get("cprpValLst");
    String cprp = nvl((String)paramsMap.get("cprp"));
    if(sttLst!=null){
        for(int j=1;j<sttLst.length;j++){
            String cprpVal  = nvl(sttLst[j]);
            String insrtAddon = " insert into srch_addon( srch_id , cprp , cval  ) "+
            "select ? , ? , ?  from dual ";
           ArrayList ary = new ArrayList();
            ary.add(String.valueOf(lSrchId));
            ary.add(cprp);
            ary.add(cprpVal);
           int cnt1 = db.execUpd("insrtAddon", insrtAddon, ary);
        }
    }
    
           ArrayList grpLst = (ArrayList)paramsMap.get("grpLst");
           
     if(grpLst!=null){
        for(int j=0;j<grpLst.size() ;j++){
             String lgrp = (String)grpLst.get(j);
              insertSrchAddon(lSrchId, lgrp, db);
         }
        }
    String mix =nvl((String)paramsMap.get("MIX"));
    String prcd = nvl((String)paramsMap.get("PRCD"));
        String ppop = nvl((String)paramsMap.get("PPOP"));
        String srch_pkg = "srch_pkg.STK_SRCH(? ,?)";
        ArrayList ary = new ArrayList();
        ary.add(String.valueOf(lSrchId));
        ary.add(mdl);
        if(mix.equals("Y"))
           srch_pkg = "DP_MIX_SRCH(pSrchId => ?, pMdl=> ?)";
        if(prcd.equals("STK_TLY"))
           srch_pkg = "Srch_Pkg.POP_STK_TLY(pSrchId => ?, pMdl=> ?)";
      
      if(prcd.equals("MIX_STK_TLY"))
               srch_pkg = "POP_MIX_STK_TLY(pSrchId => ?, pMdl=> ?)";
       if(prcd.equals("ROUGH"))
            srch_pkg = "RGH_PKG.STK_SRCH(pSrchId => ?, pMdl=> ?)";
            
        if(ppop.equals("N")){
           srch_pkg = "srch_pkg.STK_SRCH(pSrchId => ?, pMdl=> ?, pPop=> ?)";
           ary.add(ppop);   
        }
            
        int ct = db.execCall("stk_srch", srch_pkg, ary);
        return lSrchId ;

        }
    
    public void insertSrchAddon(int srchId,String grp,DBMgr db){
        String sttQry = "select stt from df_stk_stt where grp1=? and stt not like 'SUS%'  and flg='A' order by srt";
         ArrayList ary = new ArrayList();
         ary.add(grp);
        try {
            ArrayList outLst = db.execSqlLst("sttQry", sttQry, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                String stt = rs.getString("stt");
                String insrtAddon = " insert into srch_addon( srch_id , cprp , cval) "+
                "select ? , ? , ?  from dual ";
                ary = new ArrayList();
                ary.add(String.valueOf(srchId));
                ary.add("STT");
                ary.add(stt);
                int cnt = db.execUpd("", insrtAddon, ary);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
    }
    
    
    public int genericSrchGRP_SRCH(HashMap paramsMap){
        db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());
        int lSrchId =0;
        String stt = nvl((String)paramsMap.get("stt"));
        String mdl = nvl((String)paramsMap.get("mdl"));
        String getSrchId = "srch_pkg.get_srch_id(pRlnId => ?, pTyp => ?, pTrmVal => ?, pMdl => ?, pFlg => ?, pSrchId => ?)";
        ArrayList params = new ArrayList();
        params.add(String.valueOf(0));
        params.add(stt);  
        params.add("1");
        params.add("Z");
        params.add("");  
        ArrayList outParams = new ArrayList();
        outParams.add("I");
        CallableStatement cst = db.execCall(" Add Srch Hdr ", getSrchId, params, outParams);
        try {
            lSrchId = cst.getInt(params.size()+1);
            cst.close();
            cst=null;
          } catch (SQLException e) {
          }
        info.setSrchId(lSrchId);
        if(lSrchId > 0) {
        int cnt=0;
        ArrayList gncPrpLst = info.getGncPrpLst();
     
        HashMap mprp = info.getMprp();
        String addSrchDtl = "srch_pkg.add_srch_dtl(pSrchId => ?, pPrp => ?, pSrtFr => ?, pSrtTo => ?)";
        String addSrchDtlVal = "srch_pkg.add_srch_dtl_val(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
         String addSrchDtlDte = "srch_pkg.add_srch_dtl_dte(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
         String addSrchDtlSubVal = "srch_pkg.add_srch_dtl_sub_val(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
        for (int i = 0; i < gncPrpLst.size(); i++) {
            boolean dtlAddedOnce = false;
            ArrayList prplist =(ArrayList)gncPrpLst.get(i);
            String lprp = (String)prplist.get(0);
            String lprpTyp = nvl((String)mprp.get(lprp+"T"));
            String reqVal1 = nvl((String)paramsMap.get(lprp + "_1"));
            String reqVal2 = nvl((String)paramsMap.get(lprp + "_2"));
            if(lprpTyp.equals("T"))
                reqVal2 = reqVal1;
            //util.SOP(lprp + " : "+ reqVal1 + " : " + reqVal2);
            if(reqVal2.equals(""))
            reqVal2=reqVal1;

            if(((reqVal1.length() == 0) || (reqVal2.length() == 0))
            || ((reqVal1.equals("0")) && (reqVal2.equals("0")) )) {
              //ignore no selection;
            } else {
                if(lprpTyp.equals("T")){
                   ArrayList fmtVal = getStrToArr(reqVal1);
                   if(fmtVal!=null && fmtVal.size()>0){
                   for(int k=0;k<fmtVal.size();k++){
                       String txtVal = (String)fmtVal.get(k);
                       if(!dtlAddedOnce) {
                       params = new ArrayList();
                       params.add(String.valueOf(lSrchId));
                       params.add(lprp);
                       params.add(txtVal);
                       params.add(txtVal);
                       cnt = db.execCallDir(" SrchDtl ", addSrchDtlVal, params);
                       dtlAddedOnce = true;
                       }
                       params = new ArrayList();
                       params.add(String.valueOf(lSrchId));
                       params.add(lprp);
                       params.add(txtVal);
                       params.add(txtVal);
                       cnt = db.execCallDir(" SrchDtl ", addSrchDtlSubVal, params);
                   }
                   }
                   }else{
            params = new ArrayList();
            params.add(String.valueOf(lSrchId));
            params.add(lprp);
            params.add(reqVal1);
            params.add(reqVal2);
            if(lprpTyp.equals("T"))
            cnt = db.execCall(" SrchDtl ", addSrchDtlVal, params);
            else if(lprpTyp.equals("D"))
            cnt = db.execCall(" SrchDtl ", addSrchDtlDte, params);
            else
              cnt = db.execCall(" SrchDtl ", addSrchDtl, params);  
            
            }}
            
        }}
        
        String srch_pkg = "srch_pkg.GRP_SRCH(? ,? )";
        ArrayList ary = new ArrayList();
        ary.add(String.valueOf(lSrchId));
        ary.add(mdl);
        int ct = db.execCall("stk_srch", srch_pkg, ary);
        return lSrchId ;
        
    }
    
    public int genericSrchGRP3_SRCH(HashMap paramsMap){
        String gtDel = nvl((String)paramsMap.get("DELGT"));
        if(gtDel.equals("NO")){

        }else{
        db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());
        }
        int lSrchId =0;
        String stt = nvl((String)paramsMap.get("stt"));
        String mdl = nvl((String)paramsMap.get("mdl"));
        String getSrchId = "srch_pkg.get_srch_id(pRlnId => ?, pTyp => ?, pTrmVal => ?, pMdl => ?, pFlg => ?, pSrchId => ?)";
        ArrayList params = new ArrayList();
        params.add(String.valueOf(0));
        params.add(stt);  
        params.add("1");
        params.add("Z");
        params.add("");  
        ArrayList outParams = new ArrayList();
        outParams.add("I");
        CallableStatement cst = db.execCall(" Add Srch Hdr ", getSrchId, params, outParams);
        try {
            lSrchId = cst.getInt(params.size()+1);
            cst.close();
            cst=null;
          } catch (SQLException e) {
          }
//        System.out.println("SRCH ID=>"+lSrchId);
        info.setSrchId(lSrchId);
        if(lSrchId > 0) {
        int cnt=0;
        ArrayList gncPrpLst = info.getGncPrpLst();
     
        HashMap mprp = info.getMprp();
        String addSrchDtl = "srch_pkg.add_srch_dtl(pSrchId => ?, pPrp => ?, pSrtFr => ?, pSrtTo => ?)";
        String addSrchDtlVal = "srch_pkg.add_srch_dtl_val(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
         String addSrchDtlDte = "srch_pkg.add_srch_dtl_dte(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
            String addSrchDtlSubVal = "srch_pkg.add_srch_dtl_sub_val(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
        for (int i = 0; i < gncPrpLst.size(); i++) {
           boolean dtlAddedOnce  = false;
            ArrayList prplist =(ArrayList)gncPrpLst.get(i);
            String lprp = (String)prplist.get(0);
            String lprpTyp = nvl((String)mprp.get(lprp+"T"));
            String reqVal1 = nvl((String)paramsMap.get(lprp + "_1"));
            String reqVal2 = nvl((String)paramsMap.get(lprp + "_2"));
            if(lprpTyp.equals("T"))
                reqVal2 = reqVal1;
            //util.SOP(lprp + " : "+ reqVal1 + " : " + reqVal2);
            if(reqVal2.equals(""))
            reqVal2=reqVal1;

            if(((reqVal1.length() == 0) || (reqVal2.length() == 0))
            || ((reqVal1.equals("0")) && (reqVal2.equals("0")) )) {
              //ignore no selection;
            } else {
                if(lprpTyp.equals("T")){
                   ArrayList fmtVal = getStrToArr(reqVal1);
                   if(fmtVal!=null && fmtVal.size()>0){
                   for(int k=0;k<fmtVal.size();k++){
                       String txtVal = (String)fmtVal.get(k);
                       if(!dtlAddedOnce) {
                       params = new ArrayList();
                       params.add(String.valueOf(lSrchId));
                       params.add(lprp);
                       params.add(txtVal);
                       params.add(txtVal);
                       cnt = db.execCallDir(" SrchDtl ", addSrchDtlVal, params);
                       dtlAddedOnce = true;
                       }
                       params = new ArrayList();
                       params.add(String.valueOf(lSrchId));
                       params.add(lprp);
                       params.add(txtVal);
                       params.add(txtVal);
                       cnt = db.execCallDir(" SrchDtl ", addSrchDtlSubVal, params);
                   }
                   }
                   }else{
            params = new ArrayList();
            params.add(String.valueOf(lSrchId));
            params.add(lprp);
            params.add(reqVal1);
            params.add(reqVal2);
            if(lprpTyp.equals("T"))
            cnt = db.execCall(" SrchDtl ", addSrchDtlVal, params);
            else if(lprpTyp.equals("D"))
            cnt = db.execCall(" SrchDtl ", addSrchDtlDte, params);
            else
              cnt = db.execCall(" SrchDtl ", addSrchDtl, params);  
                   }
            }
            
        }}
        
        String srch_pkg = "srch_pkg.GRP3_SRCH(pSrchId => ?, pMdl => ?, pMultiple => ?, pTyp => ?) ";
        ArrayList ary = new ArrayList();
        ary.add(String.valueOf(lSrchId));
        ary.add(mdl);
        ary.add("Y");
        ary.add("3");
        int ct = db.execCall("stk_srch", srch_pkg, ary);
        return lSrchId ;
        
    }
    
    public ArrayList getSrchResult(ArrayList vwPrpLst){
        ArrayList pktList = new ArrayList();
        
      
            String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt , qty , cts , cert_lab , rmk ";
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

            
            String rsltQ = srchQ + " from gt_srch_rslt where flg =? order by sk1";
           ArrayList  ary = new ArrayList();
             ary.add("Z");
        ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
            try {
                while(rs.next()) {
                  
                    String stkIdn = nvl(rs.getString("stk_idn"));
                  
                    HashMap pktPrpMap = new HashMap();
                    pktPrpMap.put("stt", nvl(rs.getString("stt")));
                    String vnm = nvl(rs.getString("vnm"));
                   
                    pktPrpMap.put("vnm",vnm);
                    pktPrpMap.put("stk_idn",stkIdn);
                    pktPrpMap.put("qty",nvl(rs.getString("qty")));
                    pktPrpMap.put("cts",nvl(rs.getString("cts")));
                   
                   
                    for(int j=0; j < vwPrpLst.size(); j++){
                         String prp = (String)vwPrpLst.get(j);
                          
                          String fld="prp_";
                          if(j < 9)
                                  fld="prp_00"+(j+1);
                          else    
                                  fld="prp_0"+(j+1);
                          
                          String val = nvl(rs.getString(fld)) ;
                         
                            
                            pktPrpMap.put(prp, val);
                             }
                                  
                        pktList.add(pktPrpMap);
                    }
                rs.close();
                pst.close();
            } catch (SQLException sqle) {

                // TODO: Add catch code
                sqle.printStackTrace();
            }
           
             
        
        return pktList; 
    }
    
    public void initRefMap(HttpServletRequest req){
        ArrayList vwPrpLst = info.getVwPrpLst();
     
          ArrayList srchPrp = info.getRefPrpLst();
          HashMap mprp = info.getMprp();
          HashMap prp = info.getPrp();
          ResultSet rs = null;
          HashMap refineAllPrpMap = new HashMap();
          HashMap refineZPrpMap = new HashMap();
        for(int i=0 ; i < srchPrp.size() ; i++){   
             String isHidTb = "";
             ArrayList srchV =(ArrayList)srchPrp.get(i);
             String lprp = (String)srchV.get(0);
             String flg = (String)srchV.get(1);
             if(!flg.equals("H")){
             isHidTb = "style=\"display:none;\"";
             String prpDsc = (String)mprp.get(lprp+"D");
             ArrayList prpPrt = (ArrayList)prp.get(lprp+"P");
             ArrayList prpSrt = (ArrayList)prp.get(lprp+"S");
             ArrayList prpVal = (ArrayList)prp.get(lprp+"V"); 
            
             if(vwPrpLst.contains(lprp) && !(lprp.equals("CRTWT"))){
             int inxPrp = vwPrpLst.indexOf(lprp);
             String fld = "";
             String srtfld = "";
              if (inxPrp < 9){
                fld = "prp_00" + (inxPrp + 1);
                srtfld = "srt_00" + (inxPrp + 1);
             }else{
                fld = "prp_0" + (inxPrp + 1);
                 srtfld = "srt_0" + (inxPrp + 1);
             }
             if(lprp.equals("RTE"))
                 srtfld = " quot ";
              try {
             if (prpSrt != null) {
            String srchQry = "select distinct " + fld + " , " + srtfld +" , flg  from gt_srch_rslt where flg in ('Z','R','N') order by " +srtfld;
                 ArrayList outLst = db.execSqlLst("gtSrchRslt", srchQry, new ArrayList());
                 PreparedStatement pst = (PreparedStatement)outLst.get(0);
                 ResultSet refrs = (ResultSet)outLst.get(1);
            ArrayList refineAllPrp = new ArrayList();
             ArrayList refineZPrp = new ArrayList();
             while (refrs.next()) {
                 String fldVal = nvl(refrs.getString(fld));
                  String gtflg = nvl(refrs.getString("flg"));
                  if (!fldVal.equals("")) {
                         if (!refineAllPrp.contains(fldVal))
                                        refineAllPrp.add(fldVal);
                        if (gtflg.equals("Z")) {
                            if (!refineZPrp.contains(fldVal))
                                            refineZPrp.add(fldVal);
                        }
                    }

                }
                 refrs.close();
                 pst.close();
                 refineAllPrpMap.put(lprp, refineAllPrp);
                 refineZPrpMap.put(lprp, refineZPrp);
             } else {
                 String srchQry = "select min ("+srtfld+") minVal ,  max("+srtfld+") maxVal from gt_srch_rslt ";
                    ArrayList outLst = db.execSqlLst("gtSrchRslt", srchQry, new ArrayList());
                    PreparedStatement pst = (PreparedStatement)outLst.get(0);
                    rs = (ResultSet)outLst.get(1);
                 ArrayList refineAllPrp = new ArrayList();
                    if (rs.next()) {
                                refineAllPrp.add(nvl(rs.getString("minVal")));
                                refineAllPrp.add(nvl(rs.getString("maxVal")));
                                refineAllPrpMap.put(lprp, refineAllPrp);
                    }
                    rs.close();
                    pst.close();
                     srchQry = "select min ("+srtfld+") minVal ,  max("+srtfld+") maxVal from gt_srch_rslt where flg = 'Z'";
                    outLst = db.execSqlLst("gtSrchRslt", srchQry, new ArrayList());
                    pst = (PreparedStatement)outLst.get(0);
                    rs = (ResultSet)outLst.get(1);
                    ArrayList refineZPrp = new ArrayList();
                       if (rs.next()) {
                                   refineZPrp.add(nvl(rs.getString("minVal")));
                                   refineZPrp.add(nvl(rs.getString("maxVal")));
                                   refineZPrpMap.put(lprp, refineZPrp);
                       }
                    rs.close();
                    pst.close();
                }
                  
                
             } catch (SQLException sqle) {
                        // TODO: Add catch code
                        sqle.printStackTrace();
            }
              
              }
             
          }}
        
        req.setAttribute("RefineAllPrpMap", refineAllPrpMap);
        req.setAttribute("RefineZPrp", refineZPrpMap);
    }
    
    
//    public HashMap StockViewLyt(){
//        HashMap stockViewMap= (info.getStockViewMap() == null)?new HashMap():(HashMap)info.getStockViewMap();
//        if(stockViewMap.size() == 0) {
//            try {
//            HashMap dbinfo = info.getDmbsInfoLst();
//            String cnt=nvl((String)dbinfo.get("CNT"));
//            String mem_ip=nvl((String)dbinfo.get("MEM_IP"),"13.229.255.212");
//            int mem_port=Integer.parseInt(nvl((String)dbinfo.get("MEM_PORT"),"11211"));
//            MemcachedClient mcc = new MemcachedClient(new InetSocketAddress(mem_ip, mem_port));
//            stockViewMap=(HashMap)mcc.get(cnt+"_stockViewMap");
//            if(stockViewMap==null){
//            stockViewMap=new HashMap();
//        String gtView = "select chr_fr, chr_to , dsc , REM , dta_typ , lvl from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " + 
//                        " b.mdl = 'JFLEX' and b.nme_rule = 'STOCK_VIEW' and a.til_dte is null order by a.srt_fr ";
//        ResultSet rs = db.execSql("gtView", gtView, new ArrayList());
//        try {
//            while (rs.next()) {
//                HashMap vwMap = new HashMap();
//               String prp = nvl(rs.getString("dsc"));
//                vwMap.put("nme", nvl(rs.getString("chr_to")));
//                vwMap.put("url",nvl(rs.getString("chr_fr")));
//                vwMap.put("typ",nvl(rs.getString("dta_typ")));
//                vwMap.put("dir",nvl(rs.getString("rem")));
//                vwMap.put("Hed", nvl(rs.getString("lvl")));
//                stockViewMap.put(prp, vwMap);
//            }
//            rs.close();
//        } catch (SQLException sqle) {
//            // TODO: Add catch code
//            sqle.printStackTrace();
//        }
//                Future fo = mcc.delete(cnt+"_stockViewMap");
//                System.out.println("add status:_stockViewMap" + fo.get());
//                fo = mcc.set(cnt+"_stockViewMap", 24*60*60, stockViewMap);
//                System.out.println("add status:_stockViewMap" + fo.get());
//            }
//        mcc.shutdown();
//        info.setStockViewMap(stockViewMap);
//            }catch(Exception ex){
//             System.out.println( ex.getMessage() );
//            }
//        }
//        return stockViewMap;
//    }
  public void emptyGT(String flg) {      
    String delQ = " delete from gt_srch_rslt where flg in ("+ flg + ")";
    if(flg.equals("all")) {
      delQ = "delete from gt_srch_rslt";
    }
    int ct = db.execUpd("del GT", delQ, new ArrayList());
  }
  
    public ArrayList getByerEmailid()throws Exception {
    ArrayList emailids=new ArrayList();
    String email="";
    ResultSet rs=null;
    ArrayList ary = new ArrayList();
    String byrmailid = "select txt emailid from nme_dtl where nme_idn= ? and mprp like 'EMAIL%'and vld_dte is null";
    ary.add(String.valueOf(info.getByrId()));
        ArrayList outLst = db.execSqlLst("Buyer Mail Ids", byrmailid ,ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    email=nvl(rs.getString("emailid"));
    if(!email.equals("NA")){
    emailids.add(email);
    }
    }
        rs.close();
        pst.close();
    return emailids;
    }
    
  public String getPageKeyNme(String info, String sql, ArrayList params) {
    String keyNme = "";
    
    try {
            ResultSet rs = db.execSql(info, sql, params);
            if (rs.next()) {
                keyNme = rs.getString(1);
            }
            rs.close();

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
    return keyNme ;
  }
  public String pktNotFound(String vnm){
      String CodeNF = "";
      String NFMsg = "Not Found =>";
      String extreMsg="Extra =>";
      vnm = vnm.replaceAll(",,", "");
      if(vnm.length()==1 && vnm.equals(","))
          vnm="";
      if(!vnm.equals("")){         
          vnm = vnm.replaceAll("''", ",");
          vnm = vnm.replaceAll("'", "");
          HashMap dbinfo = info.getDmbsInfoLst();
          int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
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
            int ct = db.execDirUpd(" ins scan", insScanPkt,params);
            System.out.println(insScanPkt);  
          }
          }
      }
      int count=0;
      String getRfidExtra="select distinct b.vnm from gt_pkt_scan a, mstk b where ((a.vnm = b.tfl3) or (a.vnm = b.vnm)) ";
      ArrayList outLst = db.execSqlLst("Find NF", getRfidExtra, new ArrayList());
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rsNf = (ResultSet)outLst.get(1);
      try {
            while (rsNf.next()) {
                String vnm1 = rsNf.getString("vnm");
                if(count==0)
                CodeNF = extreMsg +" "+vnm1;
                else
                CodeNF += ","+vnm1;
                count=1;
            }
            rsNf.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      
      String getRfidNF = " select distinct vnm from gt_pkt_scan a where not exists (select 1 from mstk b where ((a.vnm = b.tfl3) or (a.vnm = b.vnm))) "; 
        count=0;
      outLst = db.execSqlLst("Find NF", getRfidNF, new ArrayList());
      pst = (PreparedStatement)outLst.get(0);
      rsNf = (ResultSet)outLst.get(1);
      try {
            while (rsNf.next()) {
                String vnm1 = rsNf.getString("vnm");
                if(count==0)
                CodeNF += "  "+NFMsg+" "+vnm1;
                else
                CodeNF += ", " + vnm1;
            }
            rsNf.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      
      int ct = db.execUpd("delete gtScan", "delete from gt_pkt_scan", new ArrayList());
      return CodeNF;
  }
  
//    public void rfidDevice(){
//    ArrayList rfiddevice = ((ArrayList)info.getRfiddevice() == null)?new ArrayList():(ArrayList)info.getRfiddevice();
//    try {
//    if(rfiddevice.size() == 0) {
//        HashMap dbinfo = info.getDmbsInfoLst();
//        String cnt=nvl((String)dbinfo.get("CNT"));
//        String mem_ip=nvl((String)dbinfo.get("MEM_IP"),"13.229.255.212");
//        int mem_port=Integer.parseInt(nvl((String)dbinfo.get("MEM_PORT"),"11211"));
//        MemcachedClient mcc = new MemcachedClient(new InetSocketAddress(mem_ip, mem_port));
//        rfiddevice=(ArrayList)mcc.get(cnt+"_rfiddevice");
//        if(rfiddevice==null){
//        rfiddevice=new ArrayList();
//
//    String gtView = "select chr_fr, chr_to , dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " +
//    " b.mdl = 'JFLEX' and b.nme_rule = 'RFID_DEVICE' and a.til_dte is null order by a.srt_fr ";
//    ResultSet rs = db.execSql("gtView", gtView, new ArrayList());
//    while (rs.next()) {
//    ArrayList device=new ArrayList();
//    device.add(nvl(rs.getString("chr_fr")));
//    device.add(nvl(rs.getString("chr_to")));
//    rfiddevice.add(device);
//    }
//        rs.close();
//        Future fo = mcc.delete(cnt+"_rfiddevice");
//        System.out.println("add status:_rfiddevice" + fo.get());
//        fo = mcc.set(cnt+"_rfiddevice", 24*60*60, rfiddevice);
//        System.out.println("add status:_rfiddevice" + fo.get());
//        }
//        mcc.shutdown();
//    info.setRfiddevice(rfiddevice);
//    }
//    } catch (SQLException sqle) {
//    // TODO: Add catch code
//    sqle.printStackTrace();
//        }catch(Exception ex){
//         System.out.println( ex.getMessage() );
//        }
//        
//    }

    public ArrayList saleperson()
    throws Exception {
        ArrayList salepersonList = ((ArrayList)info.getSaleperson() == null)?new ArrayList():(ArrayList)info.getSaleperson();
        try {
        if(salepersonList.size() == 0) {
    ResultSet rs = null;
    ArrayList ary = null;
    ArrayList saleperson=new ArrayList();
    String sql = " select nme_idn, nme from nme_v a where typ = 'EMPLOYEE' " +
    " and exists (select 1 from mnme a1, nmerel b where b.nme_idn = a1.nme_idn and a.nme_idn = a1.emp_idn and b.vld_dte is null and b.flg = 'CNF') " +
    " order by nme";
            ArrayList outLst = db.execSqlLst("Sale Person", sql, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
    while (rs.next()) {
    saleperson=new ArrayList();
    saleperson.add(String.valueOf(rs.getInt("nme_idn")));
    saleperson.add(nvl(rs.getString("nme")));
    salepersonList.add(saleperson);
    }
        info.setSaleperson(salepersonList);
            rs.close();
            pst.close();
        }
        } catch (SQLException sqle) {
        // TODO: Add catch code
        sqle.printStackTrace();
        }
    return salepersonList;
    }
    
    public ArrayList noteperson(){
        ArrayList notepersonList = ((ArrayList)info.getNoteperson() == null)?new ArrayList():(ArrayList)info.getNoteperson();
        try {
        if(notepersonList.size() == 0) {
    ResultSet rs = null;
    ArrayList ary = null;
    ArrayList noteperson=new ArrayList();
    String sql = "  select pn.val,pn.dsc \n" + 
    " from\n" + 
    " mprp_nme mn,prp_nme pn\n" + 
    " where \n" + 
    " mn.mprp=pn.mprp and mn.mprp='NOTE_PERSON' and trunc(nvl(pn.vld_dte,sysdate))<=trunc(sysdate) and trunc(nvl(mn.vld_dte,sysdate))<=trunc(sysdate)";
            ArrayList outLst = db.execSqlLst("Sale Person", sql, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
    while (rs.next()) {
    noteperson=new ArrayList();
    noteperson.add(nvl(rs.getString("val")));
    noteperson.add(nvl(rs.getString("dsc")));
    notepersonList.add(noteperson);
    }
            rs.close();
            pst.close();
            info.setNoteperson(notepersonList);
        }
        } catch (Exception sqle) {
        // TODO: Add catch code
        sqle.printStackTrace();
        }
    return notepersonList;
    }
    public ArrayList allsaleperson()
    throws Exception {

    ResultSet rs = null;
    ArrayList ary = null;
    ArrayList salepersonList=new ArrayList();
    ArrayList saleperson=new ArrayList();
    String sql = " select nme_idn, nme from nme_v a where typ = 'EMPLOYEE' order by nme";
        ArrayList outLst = db.execSqlLst("Sale Person", sql, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
    try {
    while (rs.next()) {
    saleperson=new ArrayList();
    saleperson.add(String.valueOf(rs.getInt("nme_idn")));
    saleperson.add(nvl(rs.getString("nme")));
    salepersonList.add(saleperson);
    }
        rs.close();
        pst.close();
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }

    return salepersonList;
    }
    public ArrayList groupcompany()
    throws Exception {
        ArrayList grpList = ((ArrayList)info.getGroupcompany() == null)?new ArrayList():(ArrayList)info.getGroupcompany();
        try {
        if(grpList.size() == 0) {
    ResultSet rs = null;
    ArrayList ary=new ArrayList();
    String conQ="";
    ArrayList grp=new ArrayList();
    ArrayList rolenmLst=(ArrayList)info.getRoleLst();
    String usrFlg=nvl((String)info.getUsrFlg());
    String dfgrpnmeidn=nvl(info.getDfGrpNmeIdn());
    if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
    }else if(!dfgrpnmeidn.equals("") && !dfgrpnmeidn.equals("0")){
    conQ=" and nme_idn=?"; 
    ary.add(dfgrpnmeidn);
    }
    String sql = "Select nme_idn , nme From Nme_V Where nme is not null and typ='GROUP' "+conQ+" order by nme";
            ArrayList outLst = db.execSqlLst("Sale Person", sql, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
    while (rs.next()) {
    grp=new ArrayList();
    grp.add(String.valueOf(rs.getInt("nme_idn")));
    grp.add(nvl(rs.getString("nme")));
    grpList.add(grp);
    }
        info.setGroupcompany(grpList);
            rs.close();
            pst.close();
        }
        } catch (SQLException sqle) {
        // TODO: Add catch code
        sqle.printStackTrace();
        }
        return grpList;
        }
    
    public int updAccessLog(HttpServletRequest request,HttpServletResponse res,String pg, String pgItm) {
           HttpSession session=session = request.getSession(false);
           ArrayList ary = new ArrayList();
           ArrayList out = new ArrayList();
           int access_logidn=0;
        try {
           ary.add(Integer.toString(info.getLogId()));
           ary.add(pg);
           ary.add(pgItm);
           ary.add(session.getId());
           ary.add(request.getSession().getId());
           out.add("I");
            CallableStatement cst = null;
            cst = db.execCall(
                "NME_SRCH_PKG ",
                "DP_INS_ACCESS_LOG_IDN(pLogIdn => ? , pPg => ? , pPgItm => ? ,pCur_Java_Sid => ?,pJava_Sid => ? ,lIdn => ?)", ary, out);        
        access_logidn = cst.getInt(ary.size()+1);
          cst.close();
          cst=null;
        } catch (SQLException sqle) {
        // TODO: Add catch code
        sqle.printStackTrace();
        }
        return access_logidn;
    }
    
    public String checkUserPageRights(String bseurl,String url) {
                  String rtnPg = "" ;
//                  if(!nvl((String)info.getUsrFlg()).equals("SYS")){
//                  String sql = " select DF_CHECK_USR_PG_RIGHTS(pUsrIdn =>"+String.valueOf(info.getUsrId())+",pBseUrl => '"+bseurl+"',pUrl=> '"+url+"') chk from dual ";
//                  ResultSet rs = db.execSql(" get DF_CHECK_USR_PG_RIGHTS "+ sql, sql, new ArrayList());
//                  try {
//                      if(rs.next()) {
//                      rtnPg = rs.getString(1);
//                  }
//                      rs.close();
//                  } catch (SQLException e) {
//                  }
//                  }
//                  if(rtnPg.equals("N"))
//                  rtnPg = "unauthorized";  
//                  else
                  rtnPg = "sucess";   
                  return rtnPg ;
  }

    public String checkNmeIdngt_nme_srch(String lNmeIdn) {
                  String rtnPg = "" ;
                  if(!nvl((String)info.getUsrFlg()).equals("SYS")){
                  String sql = " select 'Y' existsnme  from gt_nme_srch where nme_idn="+lNmeIdn.trim();
                      ArrayList outLst = db.execSqlLst(" get DF_CHECK_USR_PG_RIGHTS "+ sql, sql, new ArrayList());
                      PreparedStatement pst = (PreparedStatement)outLst.get(0);
                      ResultSet rs = (ResultSet)outLst.get(1);
                  try {
                      if(rs.next()) {
                      rtnPg = rs.getString(1);
                  }
                      rs.close();
                      pst.close();
                  } catch (SQLException e) {
                  }
                  }else{
                      rtnPg = "Y";
                  }
                  if(rtnPg.equals(""))
                  rtnPg = "N";  
                  else
                  rtnPg = "Y";   
                  return rtnPg ;
    }
    
  public boolean getLoginsession(HttpServletRequest req,HttpServletResponse res,String reqSid){
      ArrayList ary = new ArrayList();
      boolean isSame=false;
      String javaSid="";
      String cookieName = "DFLOGIDN";
      Cookie cookies [] = req.getCookies();
      Cookie myCookie = null;
      if (cookies != null){
      for (int i = 0; i < cookies.length; i++) {
      if (cookies [i].getName().equals(cookieName)){
       myCookie = cookies[i];
       break;
      }}}
      String logidn = "";
      if(myCookie != null)
      logidn = myCookie.getValue();
      if(logidn.equals(""))
          logidn="0";
      String sql = "Select java_sid From df_login_log Where log_idn=?";
      ary = new ArrayList();
      ary.add(Integer.toString(info.getLogId()));
      ArrayList outLst = db.execSqlLst("validate", sql, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
      try {
      if (rs.next()) {
          javaSid=rs.getString("java_sid");
      }
          rs.close();
          pst.close();
      } catch (Exception sqle) {
      sqle.printStackTrace();
      }
      if(javaSid.equals(reqSid)){
        isSame=true;
      }else{
        updAccessLog(req,res,"Session Swap", "Cookie LogIdn "+logidn +" and Info LogIdn "+info.getLogId());
      }
      return isSame;
  }
    
    public String chkTimeOut(){
        ArrayList ary = new ArrayList();
        String invalide="N";
        String stt="IN";
        String sql = "Select stt From df_login_log Where log_idn=?";
        ary = new ArrayList();
        ary.add(Integer.toString(info.getLogId()));
        ArrayList outLst = db.execSqlLst("validate", sql, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
        while (rs.next()) {
            stt=nvl((String)rs.getString("stt"));
        }
            rs.close();
            pst.close();
        } catch (Exception sqle) {
        sqle.printStackTrace();
        }
        if(stt.equals("TO"))
        invalide="Y";
        return invalide;
    }

    public String getMstkIdn(String vnm){
        String mstkIdn="";
        String sql = "Select idn From mstk Where vnm='"+vnm.trim()+"'";
        ArrayList outLst = db.execSqlLst("validate", sql, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
        while (rs.next()) {
            mstkIdn=nvl((String)rs.getString("idn"));
        }
            rs.close();
            pst.close();
        } catch (Exception sqle) {
        sqle.printStackTrace();
        }
        return mstkIdn;
    }                              
    public void getheadermsg() {
    String msg="";
    String qry="select msg\n" + 
    " from df_msg_log where stt='A' and flg='DF'\n" + 
    " and current_timestamp\n" + 
    " between to_timestamp(to_char(frm_dt, 'dd-mm-rrrr')||' '||nvl(frm_tm,'10:00'), 'dd-mm-rrrr HH24:mi')\n" + 
    " and to_timestamp(to_char(to_dt, 'dd-mm-rrrr')||' '||nvl(to_tm,'19:00'), 'dd-mm-rrrr HH24:mi')\n" + 
    " order by msg_id";
        ArrayList outLst = db.execSqlLst("gtView", qry, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
    int sr=1;
    try {
    while(rs.next()) {
    msg=msg+" "+sr+"."+nvl(rs.getString("msg"))+"    ";
    sr++;
    }
        rs.close();
        pst.close();
    }catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }
    info.setMsgheader(msg);
    }
    public void updateDiaflexMode() {
        int ct = db.execUpd("gtUpdate","update dbms_sys_info set val = 'A' where prp='DIAFLEX_MODE'", new ArrayList());
    }
    public double getTtlPnt(double curAmt , double ttlAmt){
        double ttlPct = (curAmt/ttlAmt)*100;
        ttlPct = Round(ttlPct,2);
        return ttlPct;
    }
    public String getVnmCsv(String vnm){
        vnm = vnm.replaceAll(" ", ",");
        vnm = vnm.replace('\n',',');
        vnm = vnm.trim();
        vnm = vnm.toUpperCase();
        return vnm;
    }

    public double Round(double Rval, int Rpl) {
      double p = (double)Math.pow(10,Rpl);
      Rval = Rval * p;
      double tmp = Math.round(Rval);
      return (double)tmp/p;
      }
    public double roundToDecimals(double d, int c)  
    {   
       int temp = (int)(d * Math.pow(10 , c));  
       return ((double)temp)/Math.pow(10 , c);  
    }
    public double roundToDecimals(String dstr, int c)  
    {   double d = Double.parseDouble(dstr);
       int temp = (int)(d * Math.pow(10 , c));  
       return ((double)temp)/Math.pow(10 , c);  
    }
    public String roundToDecimals2(String dstr, int c)  
    {   double d = Double.parseDouble(dstr);
       int temp = (int)(d * Math.pow(10 , c));  
        double fnlVal = ((double)temp)/Math.pow(10 , c);
       return   String.valueOf(fnlVal);
    }
    public String getVnm(String vnm){
        if(!vnm.equals("")){
        vnm = getVnmCsv(vnm);
        String[] vnmList = vnm.split(",");
        vnm = "";
        for(int i=0 ; i < vnmList.length; i++) {
            String val = vnmList[i].trim();
            if(val.length() > 0)
                vnm += "'" + val.toUpperCase() + "',";
        }
        vnm = vnm.substring(0, vnm.length() - 1);    
        vnm = vnm.trim();
        vnm = vnm.toUpperCase();
        }
        vnm=vnm.replaceAll("&#8203;", "");
        return vnm;
    }
    public ArrayList getStrToArr(String vnm){
        ArrayList strList = new ArrayList();
        vnm = vnm.replace('\n',',');
        vnm = vnm.replace(' ',',');
        String[] vnmList = vnm.split(",");
        vnm = "";
        for(int i=0 ; i < vnmList.length; i++) {
            String val = vnmList[i].trim();
            if(!val.equals(""))
            strList.add(val);
        }
        return strList;
    }
    public String getVal(String vnm){
    
        vnm = vnm.replace('\n',',');
        String[] vnmList = vnm.split(",");
        vnm = "";
        for(int i=0 ; i < vnmList.length; i++) {
            String val = vnmList[i].trim();
            if(val.length() > 0)
                vnm += "'" + val + "',";
        }
        vnm = vnm.substring(0, vnm.length() - 1);    
        vnm = vnm.trim();
        
        return vnm;
    }
    
  public String getValNline(String vnm){
  
      vnm = vnm.replace('\n','#');
      String[] vnmList = vnm.split("#");
      vnm = "";
      for(int i=0 ; i < vnmList.length; i++) {
          String val = vnmList[i].trim();
          if(val.length() > 0)
              vnm += "'" + val + "'#";
      }
      vnm = vnm.substring(0, vnm.length() - 1);    
      vnm = vnm.trim();
      
      return vnm;
  }
    public StringBuffer appendTo(StringBuffer sb, String txt) {
        return appendTo(sb, txt, true);
    }
    public StringBuffer appendTo(StringBuffer sb, String txt, boolean nwLine) {
        if (nwLine)
            sb.append("\n");

        sb.append(txt);
        return sb;
    }
    
    public String getMixPriRte(String pShp, String pMixSz,String pMixClr) {
    String rte="";
    String getMixPriRte = "select PRC.GET_MIX_RTE(?, ?, ?) rte from dual ";
    ArrayList params = new ArrayList();
    params.add(pShp);
    params.add(pMixSz);
    params.add(pMixClr);
        ArrayList outLst = db.execSqlLst("gt fecth", getMixPriRte, params);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
    try {
    if(rs.next()){
    rte = nvl(rs.getString("rte"),"0");
    }
        rs.close();
        pst.close();
    } catch (SQLException e) {
        SOPLvl(3,rte);
    }
    return rte;
    }
    
    public String getMixPri(String pIdn) {
    String rte="";
    String getMixPri = "select PRC.GET_MIX_PRI(?) rte from dual ";
    ArrayList params = new ArrayList();
    params.add(pIdn);
        ArrayList outLst = db.execSqlLst("gt fecth", getMixPri, params);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
    try {
    if(rs.next()){
    rte = nvl(rs.getString("rte"),"0");
    }
        rs.close();
        pst.close();
    } catch (SQLException e) {
        SOPLvl(3,rte);
    }
    return rte;
    }
   
    
    public int genericSrchEntries(HashMap paramsMap){
    int lSrchId =0;
    String stt = nvl((String)paramsMap.get("stt"));
    String mdl = nvl2((String)paramsMap.get("mdl"),"Z");
    String pflg = nvl2((String)paramsMap.get("flg"),"");
    String rlnId = nvl((String)paramsMap.get("rlnId"));
    String purId = nvl((String)paramsMap.get("purId"));
    String getSrchId = "srch_pkg.get_srch_id(pRlnId => ?, pTyp => ?, pTrmVal => ?, pMdl => ?, pFlg => ?, pSrchId => ?)";
    ArrayList params = new ArrayList();
    if(rlnId.equals(""))
    params.add(String.valueOf(0));
    else
    params.add(rlnId);
    params.add(stt);
    params.add("1");
    params.add(mdl);
    params.add(pflg);
    ArrayList outParams = new ArrayList();
    outParams.add("I");
    CallableStatement cst = db.execCall(" Add Srch Hdr ", getSrchId, params, outParams);
    try {
    lSrchId = cst.getInt(params.size()+1);
//    System.out.println("Srch ID = >"+lSrchId);
      cst.close();
      cst=null;
    } catch (SQLException e) {
    }
    info.setSrchId(lSrchId);
    if(lSrchId > 0) {
    int cnt=0;
    if(!purId.equals("")){
    String insrtAddon = " insert into srch_addon( srch_id , cprp , cidn) "+
    "select ? , ? , ? from dual ";
    ArrayList ary = new ArrayList();
    ary.add(String.valueOf(lSrchId));
    ary.add("PUR_IDN");
    ary.add(purId);
    cnt = db.execUpd("", insrtAddon, ary);
    }
    ArrayList gncPrpLst = info.getGncPrpLst();
    ArrayList prplist=null;
    HashMap prp = info.getPrp();
    HashMap mprp = info.getMprp();
    String addSrchDtl = "srch_pkg.add_srch_dtl(pSrchId => ?, pPrp => ?, pSrtFr => ?, pSrtTo => ?)";
    String addSrchDtlVal = "srch_pkg.add_srch_dtl_val(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
    String addSrchDtlDte = "srch_pkg.add_srch_dtl_dte(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
    String addSrchDtlSub = "srch_pkg.add_srch_dtl_sub(pSrchId => ?, pPrp => ?, pSrtFr => ?, pSrtTo => ?)";
    String addSrchDtlSubVal = "srch_pkg.add_srch_dtl_sub_val(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
    for (int i = 0; i < gncPrpLst.size(); i++) {
    boolean dtlAddedOnce = false ;
    prplist =(ArrayList)gncPrpLst.get(i);
    String lprp = (String)prplist.get(0);
    String flg= (String)prplist.get(1);
    String lprpTyp = nvl((String)mprp.get(lprp+"T"));
    String prpSrt = lprp ;
    String reqVal1="";
    String reqVal2="";
    if(flg.equals("M")) {
    ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
    ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
    for(int j=0; j < lprpS.size(); j++) {
    String lSrt = (String)lprpS.get(j);
    String lVal = (String)lprpV.get(j);
    reqVal1 = nvl((String)paramsMap.get(lprp + "_" + lVal),"");
    reqVal2 = nvl((String)paramsMap.get(lprp + "_" + lVal),"");
    if((reqVal1.length() == 0) || (reqVal2.length() == 0)) {
    //ignore no value selected;
    }else{
    if(!dtlAddedOnce) {
    params = new ArrayList();
    params.add(String.valueOf(lSrchId));
    params.add(lprp);
    params.add(reqVal1);
    params.add(reqVal2);
    cnt = db.execCallDir(" SrchDtl ", addSrchDtlVal, params);
    dtlAddedOnce = true;
    }
    params = new ArrayList();
    params.add(String.valueOf(lSrchId));
    params.add(lprp);
    params.add(reqVal1);
    params.add(reqVal2);
    cnt = db.execCallDir(" SrchDtl ", addSrchDtlSubVal, params);
    }
    }
    }else{
    reqVal1 = nvl((String)paramsMap.get(lprp + "_1"));
    reqVal2 = nvl((String)paramsMap.get(lprp + "_2"));
    if(lprpTyp.equals("T"))
    reqVal2 = reqVal1;
    //util.SOP(lprp + " : "+ reqVal1 + " : " + reqVal2);

    if(((reqVal1.length() == 0) || (reqVal2.length() == 0))
    || ((reqVal1.equals("0")) && (reqVal2.equals("0")) )) {
    //ignore no selection;
    } else {
        if(lprpTyp.equals("T")){
           ArrayList fmtVal = getStrToArr(reqVal1);
           if(fmtVal!=null && fmtVal.size()>0){
           for(int k=0;k<fmtVal.size();k++){
               String txtVal = (String)fmtVal.get(k);
               if(!dtlAddedOnce) {
               params = new ArrayList();
               params.add(String.valueOf(lSrchId));
               params.add(lprp);
               params.add(txtVal);
               params.add(txtVal);
               cnt = db.execCallDir(" SrchDtl ", addSrchDtlVal, params);
               dtlAddedOnce = true;
               }
               params = new ArrayList();
               params.add(String.valueOf(lSrchId));
               params.add(lprp);
               params.add(txtVal);
               params.add(txtVal);
               cnt = db.execCallDir(" SrchDtl ", addSrchDtlSubVal, params);
           }
           }
           }else{
    params = new ArrayList();
    params.add(String.valueOf(lSrchId));
    params.add(lprp);
    params.add(reqVal1);
    params.add(reqVal2);
    if(lprpTyp.equals("T"))
    cnt = db.execCallDir(" SrchDtl ", addSrchDtlVal, params);
    else if(lprpTyp.equals("D"))
    cnt = db.execCallDir(" SrchDtl ", addSrchDtlDte, params);
    else
    cnt = db.execCallDir(" SrchDtl ", addSrchDtl, params);

    }}
    }
    }}

    return lSrchId ;

    }
    public HashMap pagedefdashboard(String pg) {
    HashMap pageDtl=new HashMap();
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String pgitmidn="",itm_typ="",form_nme="",fld_nme="",fld_ttl="",fld_typ="", dflt_val="", lov_qry="", val_cond="", flg1="", prv="";

    String gtView = "Select b.idn pgitmidn,Itm_Typ,Form_Nme,Fld_Nme,Fld_Ttl,Fld_Typ,Dflt_Val,Lov_Qry,Val_Cond,B.Flg1\n" +
    "From Df_Pg A,Df_Pg_Itm B, Df_Pg_Itm_Usr C\n" +
    "Where A.Idn = B.pg_Idn\n" +
    "And B.Idn=C.Itm_Idn And c.usr_idn=? And A.Mdl = ?\n" +
    "and a.stt=b.stt and a.stt='A' and b.stt='A' and c.stt='A' and c.alw_yn='Y' and a.vld_dte is null order by b.itm_typ,b.srt ";
    ArrayList ary=new ArrayList();
    ary.add(String.valueOf(info.getUsrId()));
    ary.add(pg);
        ArrayList outLst = db.execSqlLst("gtView", gtView, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
    try {
    while(rs.next()) {
    pageDtlMap=new HashMap();
    itm_typ=nvl(rs.getString("itm_typ"));
    pgitmidn=nvl(rs.getString("pgitmidn"));
    form_nme=nvl(rs.getString("form_nme"));
    fld_nme=nvl(rs.getString("fld_nme"));
    fld_ttl=nvl(rs.getString("fld_ttl"));
    fld_typ=nvl(rs.getString("fld_typ"));
    dflt_val=nvl(rs.getString("dflt_val"));
    lov_qry=nvl(rs.getString("lov_qry"));
    val_cond=nvl(rs.getString("val_cond"));
    flg1=nvl(rs.getString("flg1"));
    pageDtlMap.put("pgitmidn",pgitmidn);
    pageDtlMap.put("form_nme",form_nme);
    pageDtlMap.put("fld_nme",fld_nme);
    pageDtlMap.put("fld_ttl",fld_ttl);
    pageDtlMap.put("fld_typ",fld_typ);
    pageDtlMap.put("dflt_val",dflt_val);
    pageDtlMap.put("lov_qry",lov_qry);
    pageDtlMap.put("val_cond",val_cond);
    pageDtlMap.put("flg1",flg1);

    if(prv.equals(""))
    prv = itm_typ;

    if(!prv.equals(itm_typ)){
    pageDtl.put(prv,pageList);
    pageList = new ArrayList();
    prv = itm_typ;
    }
    pageList.add(pageDtlMap);



    }
        rs.close();
        pst.close();
    if(!prv.equals(""))
    pageDtl.put(prv,pageList);

    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }

    return pageDtl;

    }

    public ArrayList dashboardvisibility() {
    ArrayList pageList=new ArrayList();
    String qry="Select a.idn pgidn,b.idn pgitmidn,Fld_Nme\n" +
    " From Df_Pg A,Df_Pg_Itm B, Df_Pg_Itm_Usr C\n" +
    " Where A.Idn = B.Pg_Idn\n" +
    " And B.Idn=C.Itm_Idn And A.Mdl = 'DASH_BOARD'\n" +
    " and a.stt=b.stt and a.stt='A' and b.stt='A' and c.stt='A' and c.alw_yn='N' and c.usr_idn=? and a.vld_dte is null order by b.itm_typ,b.srt";
    ArrayList ary=new ArrayList();
    ary.add(String.valueOf(info.getUsrId()));
        ArrayList outLst = db.execSqlLst("gtView", qry, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
    try {
    while(rs.next()) {
    ArrayList List=new ArrayList();
    List.add(nvl(rs.getString("Fld_Nme")));
    List.add(nvl(rs.getString("pgidn")+"-"+nvl(rs.getString("pgitmidn"))));
    pageList.add(List);
    }
        rs.close();
        pst.close();
    }catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }
    return pageList;
    }
    
    public String prpsrtcolumn(String column,int index){
        if(index>9)
        column=column+"_0"+index;
        else
        column=column+"_00"+index; 
        return column.trim();
    }
    public ArrayList getcrtwtPrp(String lprp,HttpServletRequest req,HttpServletResponse res) throws Exception {
        ArrayList crtwtPrp = (info.getCrtwtPrpLst() == null)?new ArrayList():(ArrayList)info.getCrtwtPrpLst();
        if(crtwtPrp.size() == 0) {
        String getSzs = " select a.val, a.srt,to_char(trunc(wt_fr,3),'90.999') wt_fr, to_char(trunc(wt_to,3),'90.999') wt_to from prp a, msz b where a.mprp = ? and a.val = trim(b.dsc) order by a.srt ";
        ArrayList ary =new ArrayList();
        ary.add(lprp);
            ArrayList outLst = db.execSqlLst(" Sz ", getSzs, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()) {
        ArrayList Prp=new ArrayList();
        Prp.add(nvl(rs.getString("srt")));
        Prp.add(nvl(rs.getString("val")));
        Prp.add(nvl(rs.getString("wt_fr")));
        Prp.add(nvl(rs.getString("wt_to")));
        crtwtPrp.add(Prp);
        }
            rs.close();
            pst.close();
        info.setCrtwtPrpLst(crtwtPrp);
        }
        return crtwtPrp;
    }

    public String getSignUrl(String docPathNew,String doc_lnk){
        HashMap dbmsSysInfo = info.getDmbsInfoLst();
        AWSCredentials awsCreds = new BasicAWSCredentials(nvl(nvl((String)dbmsSysInfo.get("DOC_S3KEY")).trim(),nvl((String)dbmsSysInfo.get("DOC_S3KEY")).trim()),nvl(nvl((String)dbmsSysInfo.get("DOC_S3VAL")).trim(),nvl((String)dbmsSysInfo.get("S3KEY")).trim()));
        AmazonS3 s3Client = new AmazonS3Client( awsCreds );
        String fileNamePath=doc_lnk.replaceAll(docPathNew, "");
        if (fileNamePath.indexOf("doc/") <= -1)
            fileNamePath="doc/"+fileNamePath;
        URL url = s3Client.generatePresignedUrl(nvl(nvl((String)dbmsSysInfo.get("DOC_BKTNME")).trim(),nvl((String)dbmsSysInfo.get("BKTNME")).trim()),fileNamePath,new Date(System.currentTimeMillis() + 1000 * 60 * 15));
        return url.toString();
    }
    public HashMap getColorClarityRS (ArrayList issRtnComVAL){
      HashMap dbmsSysInfo = info.getDmbsInfoLst();
          String col = (String)dbmsSysInfo.get("COL");
           String clr = (String)dbmsSysInfo.get("CLR");
        String cut = nvl((String)dbmsSysInfo.get("CUT"));
        String symval =  nvl((String)dbmsSysInfo.get("SYM"));
        String polval =  nvl((String)dbmsSysInfo.get("POL"));
        String flval =  nvl((String)dbmsSysInfo.get("FL"));
    HashMap cloclrMap = new HashMap();
    String othPrpStr = issRtnComVAL.toString();
        othPrpStr = othPrpStr.replace("[", "");
        othPrpStr = othPrpStr.replace("]", "");
        othPrpStr = othPrpStr.replace(",", "','");
        othPrpStr = othPrpStr.replaceAll(" ", "");
        
    
//      int ct = db.execCall("refresh vw", "DBMS_MVIEW.REFRESH('MX_LAB_PRC_ISS_MV')", new ArrayList());  
     
//     String labSql = "select a.stk_idn ,b.iss_val  , b.mprp " + 
//     " from gt_srch_rslt a , iss_rtn_prp b , MX_LAB_PRC_ISS_MV m " + 
//     "   where a.stk_idn = b.iss_stk_idn and b.iss_id = m.iss_id " + 
//     "    and a.stk_idn = m.iss_stk_idn and b.mprp in ('"+col+"','"+clr+"') ";
         String labSql = "select a.stk_idn ,decode(c.dta_typ,'C',b.iss_val,b.iss_num) issVal  , b.mprp " + 
     " from gt_srch_rslt a , iss_rtn_prp b , mprp c " + 
     "   where a.srch_id=b.iss_id and a.stk_idn = b.iss_stk_idn  and c.prp=b.mprp " + 
     "   and b.mprp in ('"+col+"','"+clr+"','"+cut+"','"+symval+"','"+polval+"','"+flval+"','"+othPrpStr+"') ";
     ArrayList ary = new ArrayList();
     
        ArrayList outLst = db.execSqlLst("labList", labSql, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                String stkIdn = nvl(rs.getString("stk_idn"));
               String mprp = nvl(rs.getString("mprp"));
                String issVal = nvl(rs.getString("issVal"));
                cloclrMap.put(stkIdn+"_"+mprp, issVal);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
     return cloclrMap;
    }
    public void getmonthyr() throws Exception {
        ArrayList yrList = (info.getYrList() == null)?new ArrayList():(ArrayList)info.getYrList();
        if(yrList.size() == 0) {
            String gtView =
                "select chr_fr, chr_to , dsc , dta_typ from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " +
                " b.mdl = 'JFLEX' and b.nme_rule = 'YEARS' and a.til_dte is null order by a.srt_fr ";
            ArrayList outLst = db.execSqlLst("gtView", gtView, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                ObjBean prp = new ObjBean();
                prp.setNme(nvl(rs.getString("chr_fr")));
                prp.setDsc(nvl(rs.getString("dsc")));
                yrList.add(prp);
            }
            rs.close();
            pst.close();
        info.setYrList(yrList);
        }
        ArrayList monthList = (info.getMonthList() == null)?new ArrayList():(ArrayList)info.getMonthList();
        if(monthList.size() == 0) {
           String gtView =
                    "select chr_fr, chr_to , dsc , dta_typ from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " + " b.mdl = 'JFLEX' and b.nme_rule = 'MONTHS' and a.til_dte is null order by a.srt_fr ";
            ArrayList outLst = db.execSqlLst("gtView", gtView, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                ObjBean prp = new ObjBean();
                prp.setNme(nvl(rs.getString("chr_fr")));
                prp.setDsc(nvl(rs.getString("dsc")));
                monthList.add(prp);
            }
            rs.close();
            pst.close();
            info.setMonthList(monthList);
        }
        ArrayList quarterList = (info.getQuarterList() == null)?new ArrayList():(ArrayList)info.getQuarterList();
        if(quarterList.size() == 0) {
           String gtView =
                    "select chr_fr, chr_to , dsc , dta_typ from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " + " b.mdl = 'JFLEX' and b.nme_rule = 'QUARTER' and a.til_dte is null order by a.srt_fr ";
            ArrayList outLst = db.execSqlLst("gtView", gtView, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                ObjBean prp = new ObjBean();
                prp.setNme(nvl(rs.getString("chr_fr"))+"_"+nvl(rs.getString("chr_to")));
                prp.setDsc(nvl(rs.getString("dsc")));
                quarterList.add(prp);
            }
            rs.close();
            pst.close();
            info.setQuarterList(quarterList);
        }
    }
    
    public void displayDtlsLoc(LocationDlvForm udf,String loc){

    try {
    String gtView = "select chr_fr,dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " +
            " b.mdl = 'JFLEX' and b.nme_rule = 'LOC"+loc+"' and a.til_dte is null order by a.srt_fr ";
        ArrayList outLst = db.execSqlLst("gtView", gtView, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
    while (rs.next()) {
         udf.setValue(nvl(rs.getString("dsc")), nvl(rs.getString("chr_fr"),"NA"));
     }
        rs.close();
        pst.close();
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }
    } 
    public String getConnExists() {
        String connExists="N";
            if(db!=null){
            String sql = " select 1 from dual ";
                ArrayList outLst = db.execSqlLst("get seq val", sql, new ArrayList());
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
            try {
            if(rs!=null){
                rs.close();
                pst.close();
                connExists="";
            }else{
                System.out.println("getConnExists Method db object is null");
            }
            } catch (SQLException e) {
            }
            }
            return connExists;
    }
    public void getOpenCursorConnection(DBMgr db,DBUtil util,InfoMgr info) {
            try {
                int openCursor=0;
                ArrayList param=new ArrayList();
                String schema=util.nvl(info.getSchema());
                String sid=util.nvl(info.getSid());
                Properties prop = new Properties();
                boolean valid=false;
                String usrIp="";
                String usrMch="";
                prop.load(new FileInputStream("C:\\accountnm.properties"));
                String openCursorMin=String.valueOf(prop.get("OPEN_CURSOR_MIN"));
                String sql="select df_usr,df_ip,df_mch from df_login_log where log_dte < Sysdate-"+openCursorMin+"/1440 and log_idn=?";
                    
                param=new ArrayList();
                param.add(String.valueOf(info.getLogId()));
                ArrayList outLst = db.execSqlLst("df_login_log", sql, param);
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
                while (rs.next()) {
                    valid=true;
                    usrIp = nvl(rs.getString("df_ip"));
                    usrMch =nvl(rs.getString("df_mch"));
                }
                rs.close();
                pst.close();
                
                if(valid){
                sql = "SELECT count(1) count\n" + 
                "FROM v$open_cursor oc, v$session s\n" + 
                "WHERE oc.sid = s.sid and s.sid= ?\n" + 
                "and S.USERNAME=?";
                param=new ArrayList();
                param.add(sid);
                param.add(schema);
                    outLst = db.execSqlLst(" get seq val ", sql, param);
                    pst = (PreparedStatement)outLst.get(0);
                    rs = (ResultSet)outLst.get(1);
                while (rs.next()) {
                     openCursor=rs.getInt("count");
                }
                    rs.close();
                    pst.close();

                
                int dfltopenCursor=Integer.parseInt(String.valueOf(prop.get("OPEN_CURSOR_COUNT")));
                if(openCursor > dfltopenCursor){
                db.close();
                db.init(info.getDbTyp(),info.getConnectBy());
                info.setCon(db.getCon());
                
                param= new ArrayList();
                param.add(String.valueOf(info.getLogId()));
                int ct = db.execCall("Set_log_id", " PACK_VAR.Set_LogIdn(?)", param);
                
                String sessionInfo ="select sid,serial# , username from v$session where sid in ( select sid from v$mystat)";
                    outLst = db.execSqlLst("sessionInfo", sessionInfo, new ArrayList());
                    pst = (PreparedStatement)outLst.get(0);
                    rs = (ResultSet)outLst.get(1);
                String serial = "";
                if(rs.next()){
                sid = rs.getString(1);
                serial = rs.getString(2);
                }
                    rs.close();
                    pst.close();
                info.setSid(sid);
                
                String upGt = "update df_login_log set DB_SID=?, DB_SERIAL=? where log_idn=?";
                param= new ArrayList();
                param.add(sid);
                param.add(serial);
                param.add(String.valueOf(info.getLogId()));
                ct = db.execUpd("updatGt", upGt, param);
                param = new ArrayList();
                ct = db.execCall("Set_log_id", "PACK_VAR.Set_Special(GET_USR_SPECIAL)", param);
                param = new ArrayList();
                param.add(info.getUsr());
                ct = db.execCall("Set_usr", " PACK_VAR.SET_USR(?)", param);
                    
                param = new ArrayList();
                param.add(usrIp);
                ct = db.execCall("Set_usr", " PACK_VAR.SET_USR_IP(?)", param);
                           
                param = new ArrayList();
                param.add(usrMch);
                ct = db.execCall("Set_usr", " PACK_VAR.SET_USR_MCH(?)", param);
                }
                }
            } catch (SQLException e) {
            } catch (IOException e) {
            e.printStackTrace();
            }finally{
                    
            }
    }
    public int daysbetweendate(String dte1,String dte2) {
        int diff=0;
        String getdays = "select to_date('"+dte2+"', 'dd-mm-rrrr')-to_date('"+dte1+"', 'dd-mm-rrrr') from dual ";
        ArrayList outLst = db.execSqlLst("getdays", getdays, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
          if(rs.next()) {
            diff = rs.getInt(1);
          }
            rs.close();
            pst.close();
        } catch (SQLException e) {
        }
        return diff;
    }
    public String srchDscription(String lSrchId) {
        String dsc = "";
        String getSrchDscr = "Select web_pkg.get_srch_dscr(?) dscr from dual ";
        ArrayList ary = new ArrayList();
        ary.add(lSrchId);
        
        ArrayList outLst = db.execSqlLst(" srch dscr", getSrchDscr, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
          if(rs.next()) {
            dsc = rs.getString(1);
          }
            rs.close();
            pst.close();
        } catch (SQLException e) {
        }
        return dsc ;
    }
    
    public String srchDscriptionMongo(String lprpTyp,String lprpStrDsc,String lprpValDsc,String sttStr,String tblNme,String spc) {
        String dsc = "";
        String grp = "";
        String[] prpLst = null;
        String[] prpValLst = null;
        String[] prpTypLst = null;
        String[] grpLst = null;
        lprpValDsc=lprpValDsc.replaceAll("@", ",");
        prpLst = lprpStrDsc.split("#");
        prpValLst = lprpValDsc.split("#");
        prpTypLst = lprpTyp.split("#");
        if (prpLst != null && prpLst.length > 0) {
            for (int k = 0; k < prpLst.length; k++) {
                String prpD = prpLst[k];
                String prpV = prpValLst[k];
                if(dsc.equals(""))
                dsc+=prpD+" : "+prpV+" ; ";
                else
                dsc+=prpD+" : "+prpV+" ; ";
            }
        }
        if(!sttStr.equals("")){
            dsc+=" STT : "+sttStr+" ; ";
        }
        if(!tblNme.equals("") && spc.equals("")){
        grpLst = tblNme.split(",");
        if (grpLst != null && grpLst.length > 0) {
            for (int k = 0; k < grpLst.length; k++) {
                String prpD = grpLst[k];
                prpD=prpD.replaceAll("STK_", "");
                prpD=prpD.replaceAll("ASRT", "PMKTG");
                if(grp.equals(""))
                grp=" GRP : "+prpD;
                else
                grp+="," +prpD;
            }
        }
            dsc=grp+" "+dsc;
        }
        if(!spc.equals("")){
            grp=" GRP : "+spc;
            dsc=grp+" "+dsc;
        }
        return dsc ;
    }
    public HashMap getMailFMT(String typ){
       HashMap appMailMap = new HashMap();
      String memoPrntOptn="select typ,subj,to_eml,cc_eml,stt,mail_body,mail_body2,bcc_eml from mail_fmt where typ=? and stt='A' ";
       
       ArrayList ary = new ArrayList();
       ary.add(typ);
         ArrayList outLst = db.execSqlLst("memoPrint", memoPrntOptn, ary);
         PreparedStatement pst = (PreparedStatement)outLst.get(0);
         ResultSet rs = (ResultSet)outLst.get(1);
       try {
       while (rs.next()) {
       appMailMap.put("CCEML", nvl(rs.getString("cc_eml")));
       appMailMap.put("SUBJECT", nvl(rs.getString("subj")));
       appMailMap.put("TOEML", nvl(rs.getString("to_eml")));
       appMailMap.put("MAILBODY", nvl(rs.getString("mail_body")));
       appMailMap.put("SIGN", nvl(rs.getString("mail_body2")));
       appMailMap.put("BCCEML", nvl(rs.getString("bcc_eml")));
       }
           rs.close();
           pst.close();
       } catch (SQLException sqle) {
       // TODO: Add catch code
       sqle.printStackTrace();
       }
     //      appMailMap.put("CCEML", "mayur.boob@faunatechnologies.com");
     //      appMailMap.put("TOEML","mayur.boob@faunatechnologies.com");
       return appMailMap;
     }
    public HashMap getBuyerDtl(String typ){
      HashMap getBuyerMap = new HashMap();
      String memoPrntOptn="select eml,mbl,ofc,byr.get_AttrData('OTHER REFERENCES',nme_id) otherref,byr.get_AttrData('GSTIN',nme_id) gstin,byr.get_AttrData('LOY_CTG',nme_id) loy_ctg from nme_cntct_v where nme_id=? ";
       ArrayList ary = new ArrayList();
       ary.add(typ);
         ArrayList outLst = db.execSqlLst("getBuyerDtl", memoPrntOptn, ary);
         PreparedStatement pst = (PreparedStatement)outLst.get(0);
         ResultSet rs = (ResultSet)outLst.get(1);
       try {
       while (rs.next()) {
       getBuyerMap.put("EML", nvl(rs.getString("eml")));
       getBuyerMap.put("MBL", nvl(rs.getString("mbl")));
       getBuyerMap.put("OFC", nvl(rs.getString("ofc")));
       getBuyerMap.put("OTHERREF", nvl(rs.getString("otherref")));
       getBuyerMap.put("GSTIN", nvl(rs.getString("gstin")));
       getBuyerMap.put("LOY_CTG", nvl(rs.getString("loy_ctg")));
       }
           rs.close();
           pst.close();
       } catch (SQLException sqle) {
       // TODO: Add catch code
       sqle.printStackTrace();
       }
       return getBuyerMap;
     }
    
    public HashMap getDataForExl(int nme_Idn,int RlnIdn){
      HashMap getBuyerMap = new HashMap();
      String memoPrntOptn="select fnme,lower(byr.get_eml(nme_idn,'N')) email,byr.get_trms(?) trms from mnme where nme_idn=?";
       ArrayList ary = new ArrayList();
       ary.add(String.valueOf(RlnIdn));
       ary.add(String.valueOf(nme_Idn));
         ArrayList outLst = db.execSqlLst("getBuyerDtl", memoPrntOptn, ary);
         PreparedStatement pst = (PreparedStatement)outLst.get(0);
         ResultSet rs = (ResultSet)outLst.get(1);
       try {
       while (rs.next()) {
           getBuyerMap.put("COMPANYNME", nvl(rs.getString("fnme")));
           getBuyerMap.put("EMAILID", nvl(rs.getString("email")));
           getBuyerMap.put("TERMS", nvl(rs.getString("trms")));

       }
           rs.close();
           pst.close();
           ArrayList loyalty=getloyalty(nme_Idn);
           getBuyerMap.put("LOYALTYLVL", nvl((String)loyalty.get(0)));
           getBuyerMap.put("365", nvl((String)loyalty.get(7)));
       } catch (SQLException sqle) {
       // TODO: Add catch code
       sqle.printStackTrace();
       }
       return getBuyerMap;
     }
    
    public ArrayList getCustomHdr(ArrayList itemHdr,ArrayList vwPrpList){
        ArrayList itemHdrCustom=new ArrayList();
        HashMap mprp = info.getMprp();
        for(int i=0;i<itemHdr.size();i++){
        HashMap hdrDtl = (HashMap)itemHdr.get(i);
        String hdr = (String)hdrDtl.get("hdr");
        String typ = (String)hdrDtl.get("typ");
        String dp = nvl((String)hdrDtl.get("dp"));
        boolean dis =true;
        String lprpTyp = nvl((String)mprp.get(hdr+"T"));  
        if(!lprpTyp.equals("") && !vwPrpList.contains(hdr))
            dis =false;
        if(dis)
        itemHdrCustom.add(hdrDtl);
        }
        return itemHdrCustom;
    }
    public String gettradeDis(String relidn){
      String tdis="";
      String memoPrntOptn="select byr.get_td(?) tdis from dual";
       ArrayList ary = new ArrayList();
       ary.add(relidn);
         ArrayList outLst = db.execSqlLst("getBuyerDtl", memoPrntOptn, ary);
         PreparedStatement pst = (PreparedStatement)outLst.get(0);
         ResultSet rs = (ResultSet)outLst.get(1);
       try {
       while (rs.next()) {
       tdis=nvl(rs.getString("tdis"));
       }
           rs.close();
           pst.close();
       } catch (SQLException sqle) {
       // TODO: Add catch code
       sqle.printStackTrace();
       }
       return tdis;
     }
    
    
    public ArrayList getloyalty(int nmeIdn){
       ArrayList loyaltyLst = new ArrayList();
      ArrayList ary = new ArrayList();
      ary.add(String.valueOf(nmeIdn));
      ArrayList out = new ArrayList();
         out.add("I");
         out.add("I");
         out.add("V");
         out.add("D");
         out.add("I");
         CallableStatement cst = null;
         try {
         cst = db.execCall(
             "LOYALTY_PKG ",
             "LOYALTY_PKG.GET_DTLS(pNmeIdn=>?,lVlu=>?, lLoyVlu=>?, lCtg=>?, lPct=>?,lDiff => ?) ", ary, out);
         String lCtg= nvl(cst.getString(ary.size()+3));
         double lPct= cst.getDouble(ary.size()+4);
           double lloyVlu= cst.getDouble(ary.size()+2);

         int lDiff = cst.getInt(ary.size()+5);
         loyaltyLst.add(lCtg);
         loyaltyLst.add(String.valueOf(lPct));
         loyaltyLst.add(String.valueOf(lDiff));
         loyaltyLst.add(String.valueOf(lloyVlu));

           cst.close();
           cst=null;
             String favSrch = "select GET_MEMBER_DISCOUNT mem_dis,loyalty_pkg.byr_allowed(?) loyallow,GET_MEMBER_DISCOUNT_ALLOWED(?) memallow  from dual";
             ary = new ArrayList();
             ary.add(String.valueOf(nmeIdn));
             ary.add(String.valueOf(nmeIdn));
             ArrayList outLst = db.execSqlLst("favSrch",favSrch, ary);
             PreparedStatement pst = (PreparedStatement)outLst.get(0);
             ResultSet rs = (ResultSet)outLst.get(1);
             while(rs.next()){
                 loyaltyLst.add(nvl(rs.getString("mem_dis")));
                 loyaltyLst.add(nvl(rs.getString("loyallow")));
                 loyaltyLst.add(nvl(rs.getString("memallow")));
             }
             rs.close();
             pst.close();
             
             ary = new ArrayList();
             ary.add(String.valueOf(nmeIdn));
             out = new ArrayList();
             out.add("I");
             out.add("I");
             cst = db.execCall(
                 "LOYALTY_PKG ",
                 "LOYALTY_PKG.GET_SALES_VLU(pNmeIdn=> ?, pVlu => ?, pRapVlu=> ?)", ary, out);
             double ttlVlu= cst.getDouble(ary.size()+1);
             loyaltyLst.add(String.valueOf(ttlVlu));
             cst.close();
             
             String osamt = "select GET_OS_AMT(nme_idn,cur) osamt  from nmerel where nmerel_idn = ? and nme_idn=?";
             ary = new ArrayList();
             ary.add(String.valueOf(info.getRlnId()));
             ary.add(String.valueOf(nmeIdn));
             outLst = db.execSqlLst("osamt",osamt, ary);
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
             while(rs.next()){
                 loyaltyLst.add(nvl(rs.getString("osamt")));
             }
             rs.close();
             pst.close();
             loyaltyLst.add(osamt);
         } catch (SQLException sqle) {
         // TODO: Add catch code
         sqle.printStackTrace();
         }
       return loyaltyLst;
     }
    public ArrayList byrAllEmail(String nmeIdn){
         String email="";
         ArrayList emailids=new ArrayList();
         String byrmailid = "select txt emailid from nme_dtl where mprp like 'EMAIL%' and vld_dte is null and nme_idn=?";
         ArrayList ary=new ArrayList();
         ary.add(nmeIdn);
         ArrayList outLst = db.execSqlLst("Buyer Mail Ids", byrmailid ,ary);
         PreparedStatement pst = (PreparedStatement)outLst.get(0);
         ResultSet rs = (ResultSet)outLst.get(1);
         try {
         while(rs.next()){
             email=nvl(rs.getString("emailid"));
             if(!email.equals("NA") && !email.equals("")){
             emailids.add(email);
             }
         }
             rs.close();
             pst.close();
         } catch (SQLException sqle) {
         // TODO: Add catch code
         sqle.printStackTrace();
         }

       return emailids;
     }
    
    public ArrayList sheetDtl(String mstkIdn){
         ArrayList grpList=new ArrayList();
         ArrayList grpDtl=new ArrayList();
         ArrayList ary = new ArrayList();
         String sheetQ = "select grp, pct from ITM_PRM_DIS_MFG_V where mstk_idn = ? and nvl(pct,0) <> 0 order by grp_srt, sub_grp_srt";
         ary.add(mstkIdn);
         ArrayList outLst = db.execSqlLst("Sheet", sheetQ ,ary);
         PreparedStatement pst = (PreparedStatement)outLst.get(0);
         ResultSet rs = (ResultSet)outLst.get(1);
         try {
         while(rs.next()){
             grpDtl=new ArrayList();
             grpDtl.add(nvl(rs.getString("grp")));
             grpDtl.add(nvl(rs.getString("pct")));
             grpList.add(grpDtl);
         }
             rs.close();
             pst.close();
         } catch (SQLException sqle) {
         // TODO: Add catch code
         sqle.printStackTrace();
         }
       return grpList;
     }
    public String GET_MFG_PRI(ArrayList grpSheetList){
         String getmfgpri="0";
         for(int i=0;i<grpSheetList.size();i++){
         ArrayList grpDtl=(ArrayList)grpSheetList.get(i);
         String pct =(String)grpDtl.get(1);
             if(!pct.equals("0")){
                 if(Float.parseFloat(pct)<0){
                 if(Integer.parseInt(pct)<0){
                     getmfgpri=String.valueOf(Integer.parseInt(getmfgpri)+((Integer.parseInt(pct))*-1));
                 }
                 }else{
                     if(!getmfgpri.equals("0"))
                     getmfgpri=String.valueOf(Integer.parseInt(getmfgpri)-((Integer.parseInt(pct))));
                 }
             }
         }
         if(getmfgpri.equals("0"))
             getmfgpri="1";
         else
             getmfgpri=String.valueOf(((Integer.parseInt(getmfgpri))*-1));
       return getmfgpri;
     }
    
    public String pivot(String vwPrp){
         if (vwPrp.indexOf("&") > -1)
         vwPrp=vwPrp.replaceAll("\\&","_");
         
         if (vwPrp.indexOf("-") > -1)
         vwPrp=vwPrp.replaceAll("\\-","_");
         
         if(vwPrp.toUpperCase().equals("COMMENT"))
         vwPrp = "COM1";
         
         if(vwPrp.toUpperCase().equals("REMARKS"))
         vwPrp = "REM1";
         
         if(vwPrp.toUpperCase().equals("SUBSIZE"))
         vwPrp = "SUBSIZE1";
         
         if(vwPrp.toUpperCase().equals("SIZE"))
         vwPrp = "SIZE1";
         
         if(vwPrp.toUpperCase().equals("MIX_SIZE"))
         vwPrp = "MIX_SIZE1";
         
         if(vwPrp.toUpperCase().equals("SIZE_DEPT"))
         vwPrp = "SIZE1_DEPT";
         
         if(vwPrp.toUpperCase().equals("CRTWT"))
         vwPrp = "cts";
         
         if(vwPrp.toUpperCase().equals("RTE"))
         vwPrp = "rte";
         
         if (vwPrp.toUpperCase().equals("RAP_RTE"))
         vwPrp = "rap_rte";
         return vwPrp;
     }
    public HashMap mailEventList(){
        HashMap eventList= ((HashMap)info.getEventList() == null)?new HashMap():(HashMap)info.getEventList();
        if(eventList==null || eventList.size()<=0){
        try { 
            String eventQuery = "select event_idn idn , event_cd dsc from mail_event where stt = 'A' and flg='DF' ";
            ArrayList ary = new ArrayList();
            ArrayList outLst = db.execSqlLst("Event Query", eventQuery, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
            String eventIdn = nvl((String)rs.getString("idn"));
            String eventDsc = nvl((String)rs.getString("dsc"));
            eventList.put(eventDsc,eventIdn);
            }
            rs.close();
            pst.close();
            info.setEventList(eventList);
        } catch (SQLException sqle) {
        // TODO: Add catch code
        sqle.printStackTrace();
        }
        }
        return eventList;
        }
    public int alladdUsers(int addRec,int allowusers){
        int addRecrtn=addRec;
        int available=0;
        try { 
            String sql = "select count(*) available from df_users where decode(flg,'','NA','SU','NA','SYS')='NA' and stt='A'";
            ArrayList ary = new ArrayList();
            ArrayList outLst = db.execSqlLst("Event Query", sql, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
            available= rs.getInt("available");    
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
        // TODO: Add catch code
        sqle.printStackTrace();
        }
        if(available<=allowusers){
        int remainUsr=allowusers-available;
        if(remainUsr<=addRec)
            addRecrtn=remainUsr;      
        }else{
            addRecrtn=0;   
        }
            
        return addRecrtn;
    }
    public ArrayList chargesLst(String rule){
            ArrayList chargesLst=new ArrayList();
            ArrayList ary=new ArrayList();
        try { 
            ary.add(rule);
            String chargesQ="Select  c.idn,c.typ,c.dsc,c.stg optional,c.flg,c.fctr,c.db_call,c.rmk  From Rule_Dtl A, Mrule B , Charges_Typ C Where A.Rule_Idn = B.Rule_Idn And \n" + 
            "       c.typ = a.Chr_Fr and b.mdl = 'JFLEX' and b.nme_rule = ? and c.stt='A' and a.til_dte is null order by a.srt_fr , a.dsc , a.chr_fr";
            ArrayList outLst = db.execSqlLst("chargesQ", chargesQ, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                HashMap dtl=new HashMap();
                dtl.put("idn",nvl(rs.getString("idn")));
                dtl.put("dsc",nvl(rs.getString("dsc")));
                dtl.put("autoopt",nvl(rs.getString("optional")));
                dtl.put("flg",nvl(rs.getString("flg")));
                dtl.put("typ",nvl(rs.getString("typ")));
                dtl.put("fctr",nvl(rs.getString("fctr")));
                dtl.put("fun",nvl(rs.getString("db_call")));
                dtl.put("rmk",nvl(rs.getString("rmk")));
                chargesLst.add(dtl);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
        // TODO: Add catch code
        sqle.printStackTrace();
        }
        return chargesLst;
        }
    public String mailLogDetails(HttpServletRequest request ,HashMap logDetails,String action){        
                ArrayList ary = new ArrayList();
                String mailLogIdn="";
                HashMap eventList=mailEventList();
                if(eventList!=null && eventList.size()>0){
                if(action.equals("I")){
                String sql = " select MAIL_LOG_SEQ.NextVal seqVal from dual ";
                    ArrayList outLst = db.execSqlLst(" get seq val MAIL_LOG_SEQ", sql, new ArrayList());
                    PreparedStatement pst = (PreparedStatement)outLst.get(0);
                    ResultSet rs = (ResultSet)outLst.get(1);
                try{
                if(rs.next()) {
                mailLogIdn = nvl((String)rs.getString(1));
                }
                    rs.close();
                    pst.close();
                } catch (SQLException e) {
                }
                String LogFailQ =
                    " insert into mail_log(mail_log_idn, event_idn, byr_idn , rel_idn, mail_dte, del_yn, stt, flg, unm, mch_ip, log_idn,ref_typ,ref_idn)  " +
                    " select ?, ?, ?, ?, sysdate , ?, ?, ?, ?, ?, ?,?,? from dual ";
                ary.add(mailLogIdn);
                ary.add(nvl((String)eventList.get((String)logDetails.get("TYP"))));
                ary.add(nvl(String.valueOf(logDetails.get("BYRID"))));
                ary.add(nvl(String.valueOf(logDetails.get("RELID"))));
                ary.add("N");
                ary.add("PND");
                ary.add("DF");
                ary.add(nvl((String)info.getUsr()));
                ary.add(request.getRemoteAddr());
                ary.add(Integer.toString(info.getLogId()));
                ary.add(nvl((String)logDetails.get("TYP")));
                ary.add(nvl((String)logDetails.get("IDN")));
                int ct = db.execDirUpd(" Mail Insert Check ", LogFailQ, ary);
                }else{
                HashMap maillog=(HashMap)logDetails.get("MAILDTL");
                ary.add(nvl((String)maillog.get("SETTO")));
                ary.add(nvl((String)maillog.get("SETCC")));
                ary.add(nvl((String)maillog.get("SETBCC")));
                ary.add(nvl((String)maillog.get("MES")));
                ary.add(nvl((String)maillog.get("ERR")));
                ary.add(nvl((String)maillog.get("STT")));
                ary.add(nvl((String)logDetails.get("MSGLOGIDN")));
                int ct = db.execDirUpd(" Mail Update Check ","update mail_log set to_eml=?, cc_eml=?, bcc_eml=?,stt=?,rmk=?,del_yn=? where mail_log_idn=?", ary);   
                }
     }
            return mailLogIdn;   
    }
    public static String getUrl(HttpServletRequest req) {
    String reqUrl = req.getRequestURL().toString();
    String queryString = req.getQueryString(); // d=789
    if (queryString != null) {
    reqUrl += "?"+queryString;
    }
    return reqUrl;
    }
    public boolean isExistS3(String url) {
    boolean imgExist = false;
    try {
    URL u = new URL(url);
    HttpURLConnection huc = (HttpURLConnection)u.openConnection();
    huc.setRequestMethod("HEAD");
    huc.connect();
    int code = huc.getResponseCode();
    if (code == 404)
    imgExist = true;
    } catch (ProtocolException pe) {
    pe.printStackTrace();
    } catch (MalformedURLException murle) {
    murle.printStackTrace();
    } catch (IOException ioe) {
    ioe.printStackTrace();
    }
    return imgExist;
    }   
//        public boolean isExistS3(String url) {
//        try {
//              HttpURLConnection.setFollowRedirects(false);
//              HttpURLConnection con =
//                 (HttpURLConnection) new URL(url).openConnection();
//              con.setRequestMethod("HEAD");
//              if(con.getResponseCode() == HttpURLConnection.HTTP_OK)
//              return false;
//              else 
//              return true;  
//            }
//            catch (Exception e) {
//               e.printStackTrace();
//               return false;
//            }
//    }    
    
      public  Map<String, GtPktDtl> sortByComparator(Map<String, GtPktDtl> unsortMap) {
   
      // Convert Map to List
      List<Map.Entry<String, GtPktDtl>> list = 
        new LinkedList<Map.Entry<String, GtPktDtl>>(unsortMap.entrySet());
   
      // Sort list with comparator, to compare the Map values
      Collections.sort(list, new Comparator<Map.Entry<String, GtPktDtl>>() {
        public int compare(Map.Entry<String, GtPktDtl> o1,
                                             Map.Entry<String, GtPktDtl> o2) {
//            System.out.println((Comparable) ((Map.Entry<String, GtPktDtl>) (o1)).getValue().getSk1());
//          System.out.println(((Map.Entry<String, GtPktDtl>) (o2)).getValue().getSk1());
          return ((Comparable) ((Map.Entry<String, GtPktDtl>) (o1)).getValue().getSk1())
                            .compareTo(((Map.Entry<String, GtPktDtl>) (o2)).getValue().getSk1());
        }
      });
   
      // Convert sorted map back to a Map
      Map<String, GtPktDtl> sortedMap = new LinkedHashMap<String, GtPktDtl>();
          for (Iterator<Map.Entry<String, GtPktDtl>> it = list.iterator(); it.hasNext();) {
            Map.Entry<String, GtPktDtl> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
          }
          return sortedMap;
     
    }
   
    
    public HashMap  getTTL(HashMap dtlMap){

        ArrayList stkIdnLst = (ArrayList)dtlMap.get("selIdnLst");

        HashMap stockList = (HashMap)dtlMap.get("pktDtl");

        HashMap ttlMap = (HashMap)dtlMap.get("TTLMAP");
        
        String digit = nvl((String)dtlMap.get("CRDIGIT"));
        
        String isMix = nvl(info.getIsMix(),"NR");
        
        if(isMix.equals("NR"))
            digit="2";
        else
            digit="3";
        if(digit.equals(""))
            digit="2";
        int crRound = Integer.parseInt(digit);
        if(ttlMap==null)

          ttlMap = new HashMap();

        String flg = (String)dtlMap.get("flg");

        double xrt= info.getXrt();

          BigDecimal hrdBig =  new BigDecimal(100);

            MathContext mc = new MathContext(2);
         int ttlcnt=0;
        if(stkIdnLst!=null && stkIdnLst.size()>0){

          int ttlPcs = 0 ;

          double ttlcts = 0 ;

          double avgrte = 0 ;

         

       

          double ttlcpvlu = 0;

          double rapvlu = 0 ;

          double dis = 0 ;

          double avgCp = 0;

          double cpDis =0;

          double ttlBasVlu=0;

          double avgbas =0;

          double ttlUsd=0;
          
          double avgRap = 0;

         BigDecimal ttlBigUsd = new BigDecimal(ttlUsd);
         BigDecimal ttlBigUsdLoy = new BigDecimal(ttlUsd);

         BigDecimal avgBigRte = new BigDecimal(avgrte);

         BigDecimal avgBigCp = new BigDecimal(avgCp);
         
         BigDecimal avgBigRap = new BigDecimal(avgRap);

         BigDecimal ttlBigcts = new BigDecimal(ttlcts);

         BigDecimal ttlBigCPVlu = new BigDecimal(ttlcpvlu);

         BigDecimal ttlBigBasVlu = new BigDecimal(ttlBasVlu);

         BigDecimal rapBigvlu = new BigDecimal(rapvlu);

         BigDecimal avgBigBas = new BigDecimal(avgbas);

         BigDecimal disBig = new BigDecimal(dis);

         BigDecimal cpDisBig = new BigDecimal(cpDis);

        

        for(int i=0;i < stkIdnLst.size();i++){

          String stkIdn = (String)stkIdnLst.get(i);

          GtPktDtl pktDtl =(GtPktDtl)stockList.get(stkIdn);
            String PKT_TY = nvl(pktDtl.getValue("PKT_TY"),"NR");

           String curcts = nvl(pktDtl.getValue("CRTWT"),"0").trim();   

          if(curcts.equals("0"))

         curcts=nvl(pktDtl.getValue("cts"),"0").trim();   

          String curvlu = nvl(pktDtl.getValue("AMT"),"0").trim();   

          String currapvlu = nvl(pktDtl.getValue("RAPVAL"),"0").trim();   

          String curcpvlu = nvl(pktDtl.getValue("cpTotal"),"0").trim();   

          String curbasvlu = nvl(pktDtl.getValue("basval"),"0").trim();   

          String curQty = nvl(pktDtl.getValue("qty"),"0").trim();   

          String curusdvlu = nvl(pktDtl.getValue("USDVAL"),"0").trim();
          String curusdvluLoy = nvl(pktDtl.getValue("USDVALLOY"),"0").trim();  

            if(curusdvlu.equals("0"))

                curusdvlu = curvlu;

          BigDecimal currBigRapVlu = new BigDecimal(currapvlu);

          BigDecimal currBigCpVlu = new BigDecimal(curcpvlu);

          BigDecimal currBigBasVlu = new BigDecimal(curbasvlu);

          BigDecimal currBigUsdVlu = new BigDecimal(curusdvlu);
            
          BigDecimal currBigUsdVluLoy = new BigDecimal(curusdvluLoy);

          BigDecimal currBigCts = new BigDecimal(curcts);

           

         

          ttlPcs = ttlPcs + (int)Integer.parseInt(curQty);

            if(!curcts.equals("0"))

          ttlBigcts =  ttlBigcts.add(currBigCts);

          ttlBigUsd = ttlBigUsd.add(currBigUsdVlu);
            
          ttlBigUsdLoy = ttlBigUsdLoy.add(currBigUsdVluLoy);

          ttlBigBasVlu = ttlBigBasVlu.add(currBigBasVlu);

          ttlBigCPVlu = ttlBigCPVlu.add(currBigCpVlu);
          

                if(!curcts.equals("0"))

          avgBigRte =ttlBigUsd.divide(ttlBigcts, 4,RoundingMode.HALF_EVEN);

            if(!currapvlu.equals("0"))

          rapBigvlu = rapBigvlu.add(currBigRapVlu);

            if(!currapvlu.equals("0")){

          avgBigCp = ttlBigCPVlu.divide(ttlBigcts,4,RoundingMode.HALF_EVEN);

         avgBigBas =  ttlBigBasVlu.divide(ttlBigcts,4,RoundingMode.HALF_EVEN); 
         
         avgBigRap = rapBigvlu.divide(ttlBigcts,4,RoundingMode.HALF_EVEN);

            }

            if(!currapvlu.equals("0")){

           disBig =(hrdBig.subtract((ttlBigUsd.multiply(hrdBig)).divide(rapBigvlu,4,RoundingMode.HALF_EVEN))).multiply(new BigDecimal(-1));

           cpDisBig = (hrdBig.subtract((ttlBigCPVlu.multiply(hrdBig)).divide(rapBigvlu,4,RoundingMode.HALF_EVEN))).multiply(new BigDecimal(-1));

            }

            }

         
          ttlMap.put(flg+"C", String.valueOf(stkIdnLst.size()));
            
          ttlMap.put(flg+"Q", String.valueOf(ttlPcs));
         
          ttlMap.put(flg+"W", String.valueOf(ttlBigcts.setScale(crRound, RoundingMode.HALF_EVEN)));

          ttlMap.put(flg+"A", String.valueOf(avgBigRte.setScale(2, RoundingMode.HALF_EVEN)));

          ttlMap.put(flg+"V", String.valueOf(ttlBigUsd.setScale(2, RoundingMode.HALF_EVEN)));
          
          ttlMap.put(flg+"V1", String.valueOf(ttlBigUsdLoy.setScale(2, RoundingMode.HALF_EVEN)));

          ttlMap.put(flg+"R", String.valueOf(rapBigvlu.setScale(2, RoundingMode.HALF_EVEN)));

          ttlMap.put(flg+"B", String.valueOf(avgBigBas.setScale(2, RoundingMode.HALF_EVEN)));

          ttlMap.put(flg+"U", String.valueOf(ttlBigUsd.setScale(2, RoundingMode.HALF_EVEN)));

     

          ttlMap.put(flg+"D", String.valueOf(disBig.setScale(2, RoundingMode.HALF_EVEN))); 

          ttlMap.put(flg+"CA", String.valueOf(avgBigCp.setScale(2, RoundingMode.HALF_EVEN)));  

          ttlMap.put(flg+"CD", String.valueOf(cpDisBig.setScale(2, RoundingMode.HALF_EVEN)));

          ttlMap.put(flg+"CV", String.valueOf(ttlBigCPVlu.setScale(2, RoundingMode.HALF_EVEN)));

          ttlMap.put(flg+"RA", String.valueOf(avgBigRap.setScale(2, RoundingMode.HALF_EVEN)));

           

        }else{

              ttlMap.put(flg+"Q", "0");

              ttlMap.put(flg+"W", "0");

              ttlMap.put(flg+"A", "0");

              ttlMap.put(flg+"V", "0");
            
                ttlMap.put(flg+"V1", "0");

                ttlMap.put(flg+"U", "0");

              ttlMap.put(flg+"R", "0");   

              ttlMap.put(flg+"D", "0");   

              ttlMap.put(flg+"CA", "0");

              ttlMap.put(flg+"CD", "0");   

              ttlMap.put(flg+"CV", "0");   

              ttlMap.put(flg+"RA", "0");   

            }

       

                  

        try {

                HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
                HashMap pageDtl=(HashMap)allPageDtl.get("SEARCH_RESULT");
            if(pageDtl!=null){
                    boolean isLoyalty = false;
                    boolean isMemberDis = false;
                    
                    ArrayList  pageList= ((ArrayList)pageDtl.get("LOYALTY") == null)?new ArrayList():(ArrayList)pageDtl.get("LOYALTY");
                    if(pageList!=null && pageList.size() >0)
                        isLoyalty = true;
                    
                    pageList= ((ArrayList)pageDtl.get("MEM_DIS") == null)?new ArrayList():(ArrayList)pageDtl.get("MEM_DIS");
                    if(pageList!=null && pageList.size() >0)
                        isMemberDis = true;
                
                
                   if(isLoyalty || isMemberDis){
                    String memoTyp=info.getMemoTypSel();
                    String webDis =nvl2((String)info.getDmbsInfoLst().get("WEB_DISC"),"0");
                    double web_disc = Double.parseDouble(webDis);
                    String pktCnt = nvl2((String)ttlMap.get("MQ"), "0");
                    double rap =Double.parseDouble(nvl2((String)ttlMap.get("MR"), "0"));
                    if(rap>0){
                    BigDecimal selValue = new  BigDecimal(nvl2((String)ttlMap.get("MV"), "0"));
                    
                    BigDecimal selValueLoy = new  BigDecimal(nvl2((String)ttlMap.get("MV1"), "0"));

                    BigDecimal usdValue = new  BigDecimal(nvl2((String)ttlMap.get("MU"), "0"));

                    BigDecimal selAvg = new  BigDecimal(nvl2((String)ttlMap.get("MA"), "0"));

                    BigDecimal rapVal =new  BigDecimal( nvl2((String)ttlMap.get("MR"), "0"));

                    BigDecimal ctsVal = new  BigDecimal(nvl2((String)ttlMap.get("MW"), "0"));

     

                    if (pktCnt != "0") {

                        String mPct = String.valueOf(info.getRap_pct());

                        String favSrch =

                            "select GET_MEMBER_DISCOUNT mem_dis from dual";

                        ArrayList outLst = db.execSqlLst("favSrch", favSrch, new ArrayList());
                        PreparedStatement pst = (PreparedStatement)outLst.get(0);
                        ResultSet rs = (ResultSet)outLst.get(1);

                        while (rs.next()) {

                            mPct = nvl(rs.getString("mem_dis"));

                        }

                        rs.close();
                        pst.close();

                        double lPct=0.0;
                        String lctg ="NA";
                        double lVlu=0.0;
                        int ldiff=0;
                        if(isLoyalty){
                        String loyDisc =

                            " LOYALTY_PKG.GET_DTLS(pNmeIdn=>?, pSelVlu =>?, lVlu=>?, lLoyVlu=>?, lCtg=>?, lPct=>? , lDiff=>?) ";

                        ArrayList ary = new ArrayList();

                        ary.add(String.valueOf(info.getByrId()));

                        ary.add(String.valueOf(selValueLoy));

                        ArrayList out1 = new ArrayList();

                        out1.add("I");

                        out1.add("I");

                        out1.add("V");

                        out1.add("I");

                        out1.add("I");
                        CallableStatement cst =

                        db.execCall("Calculate Loyalty", loyDisc, ary, out1);

                        lVlu = cst.getDouble(ary.size() + 2);

                        lctg = cst.getString(ary.size() + 3);

                        lPct = cst.getDouble(ary.size() + 4);

                         ldiff = cst.getInt(ary.size() + 5);
                          cst.close();
                          cst=null;

                        }

                        String totalD = String.valueOf(Integer.parseInt(mPct) + lPct);

                        BigDecimal ttlBigD = new BigDecimal(totalD);

                        BigDecimal fld1 = rapVal.multiply(ctsVal);
                        double web_disclVlu=0;
     
                        if(web_disc > 0 && memoTyp.equals("WAP") && lPct > 0.0){
                            ArrayList iememov=info.getIememov();
                            for(int i=0;i < stkIdnLst.size();i++){
                              String stkIdn = (String)stkIdnLst.get(i);
                              if(!iememov.contains(stkIdn)){
                              GtPktDtl pktDtl =(GtPktDtl)stockList.get(stkIdn);
                              String curvlu = nvl(pktDtl.getValue("AMT"),"0").trim();   
                              String curusdvlu = nvl(pktDtl.getValue("USDVAL"),"0").trim();  
                              if(curusdvlu.equals("0"))
                              curusdvlu = curvlu;
                              web_disclVlu=web_disclVlu+((Double.parseDouble(curusdvlu)*web_disc)/100);
                              }
                              }
                            web_disclVlu=roundToDecimals(web_disclVlu,2);
                        }else{
                            web_disc=0;
                        }
                        BigDecimal web_disclVlubig=new BigDecimal(web_disclVlu);
                            
                        BigDecimal netDsc = ((((usdValue.multiply(hrdBig.subtract(ttlBigD))).divide(hrdBig)).subtract(web_disclVlubig).divide(rapVal,4,RoundingMode.HALF_EVEN)).multiply(hrdBig)).subtract(hrdBig);
//                        BigDecimal netDsc = (((((usdValue.multiply(ctsVal)).multiply(hrdBig.subtract(ttlBigD))).divide(hrdBig)).divide(fld1,4,RoundingMode.HALF_EVEN)).multiply(hrdBig)).subtract(hrdBig);

                       netDsc = netDsc.setScale(2, RoundingMode.HALF_EVEN);

                        BigDecimal bigPct = new BigDecimal(mPct);

                        BigDecimal mval = (usdValue.multiply(bigPct)).divide(hrdBig,4,RoundingMode.HALF_EVEN);
                
//                        BigDecimal nprc = (selAvg.multiply(hrdBig.subtract(ttlBigD))).divide(hrdBig,4,RoundingMode.HALF_EVEN);

                        BigDecimal nval = (selValue.subtract(mval)).subtract(new BigDecimal(lVlu)).subtract(new BigDecimal(web_disclVlu));
                        BigDecimal nprc=nval.divide(ctsVal, 4,RoundingMode.HALF_EVEN);

                        ttlMap.put("NETPRC"+flg,

                                   String.valueOf(nprc));

                        ttlMap.put("NETVAL"+flg,

                                   String.valueOf(nval.setScale(2, RoundingMode.HALF_EVEN)));

                        ttlMap.put("NETDIS"+flg, String.valueOf(netDsc));

                        ttlMap.put("DIFF"+flg, String.valueOf(ldiff));

                        ttlMap.put("CTG"+flg, lctg);

                        ttlMap.put("LOYVLU"+flg, String.valueOf(lVlu));

                        ttlMap.put("MEMVLU"+flg,String.valueOf(mval.setScale(2, RoundingMode.HALF_EVEN)));

                        ttlMap.put("LOYPCT"+flg, String.valueOf(lPct));
                        
                        ttlMap.put("WEBPCT"+flg, String.valueOf(web_disc));
                        ttlMap.put("WEBVLU"+flg, String.valueOf(web_disclVlu));

                    } else {

                        ttlMap.put("NETPRC"+flg, "0");

                        ttlMap.put("NETVAL"+flg, "0");

                        ttlMap.put("NETPRC"+flg, "0");

                        ttlMap.put("DIFF"+flg, "0");

                        ttlMap.put("CTG"+flg, "0");

                        ttlMap.put("LOYVLU"+flg, "0");

                        ttlMap.put("MEMVLU"+flg, "0");

                        ttlMap.put("LOYPCT"+flg, "0");
                        ttlMap.put("WEBPCT"+flg, "0");
                        ttlMap.put("WEBVLU"+flg, "0");

                    }

     
                   }
                }
                 if(isMemberDis){
                     String memDis = (String)info.getDmbsInfoLst().get("MEMBER_DISCOUNT");
                     if(!memDis.equals("")){
                     BigDecimal bigPct = new BigDecimal(memDis);
                     BigDecimal usdValue = new  BigDecimal(nvl2((String)ttlMap.get("MU"), "0"));
                     BigDecimal mval = (usdValue.multiply(bigPct)).divide(hrdBig,4,RoundingMode.HALF_EVEN);  
                     ttlMap.put("MEMVLU"+flg,String.valueOf(mval.setScale(2, RoundingMode.HALF_EVEN)));
                     }
                 }
                
                }

            } catch (SQLException sqle) {

                // TODO: Add catch code

                sqle.printStackTrace();

            } catch (NumberFormatException nfe) {

                // TODO: Add catch code

                nfe.printStackTrace();

            }

      return ttlMap;

      }
    
        public String lpad(String srt,int cnt,String str){
       String sk1 = srt;
       while(sk1.length()<=cnt){
           sk1 = str+sk1;
       }
       return sk1;
    }
        
  public static HashMap useDifferentMap(HashMap refineDtl) {
       HashMap dtl = new HashMap();
       Set<String> keys = refineDtl.keySet();
       for(String key: keys){
//       System.out.println(key);
       ArrayList valLst=new ArrayList();
       ArrayList valLstdtl=(ArrayList)refineDtl.get(key);
       valLst.addAll(valLstdtl);
       dtl.put(key,valLst);  
       }
       
  return dtl;
  }
    public String  getRghrap_rte(String tenderpktIdn) {
              String rap = "1" ;
              String sql = " select RGH_PKG.GET_PLAN_PKT_RAP("+tenderpktIdn+") rap from dual ";
              ArrayList outLst = db.execSqlLst(" get seq val "+ sql, sql, new ArrayList());
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs = (ResultSet)outLst.get(1);
              try {
                  if(rs.next()) {
                  rap = rs.getString(1);
              }
                  rs.close();
                  pst.close();
              } catch (SQLException e) {
              }
              return rap ;
          }
    public String  getMIXRTE(String stkIdn,String typ) {
              String rte = "1" ;
              ArrayList params = new ArrayList();
              params.add(stkIdn);
              params.add(typ);
              String sql = " select GET_MIX_PRI(?,?) rte from dual ";
              ArrayList outLst = db.execSqlLst(" get seq val "+ sql, sql, params);
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs = (ResultSet)outLst.get(1);
              try {
                  if(rs.next()) {
                  rte = rs.getString(1);
                }
                  rs.close();
                  pst.close();
              } catch (SQLException e) {
              }
              return rte ;
          }
    public String  getMixPriBOX(String boxTyp,String boxID) {
              String rte = "1" ;
              ArrayList params = new ArrayList();
              params.add(boxID);
              String sql = " select  get_mix_box_pri(pPrp => 'BOX_ID', pVal => ?) rte from dual ";
              ArrayList outLst = db.execSqlLst(" get seq val "+ sql, sql, params);
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs = (ResultSet)outLst.get(1);
              try {
                  if(rs.next()) {
                  rte = rs.getString(1);
                }
                  rs.close();
                  pst.close();
              } catch (SQLException e) {
              }
              return rte ;
          }
    
    
    public static ArrayList useDifferentArrayList(ArrayList vWPrpList) {
         ArrayList vWPrpListcopy = new ArrayList();
         for(int i=0;i<vWPrpList.size();i++){
             vWPrpListcopy.add(vWPrpList.get(i)) ;
         }
         
    return vWPrpListcopy;
    }
    public String toTitleCase(String line) {        
    return line.substring(0,1).toUpperCase() + line.substring(1).toLowerCase();
    }
    
    public String priceFormatter(String inputPrice){
        try {
            // to check if the number is a decimal number
            String newPrice = "",afterDecimal = "";
            if(inputPrice.indexOf('.') != -1){
                newPrice = inputPrice.substring(0,inputPrice.lastIndexOf('.'));
                afterDecimal = inputPrice.substring(inputPrice.lastIndexOf('.'));
            }else{
                newPrice = inputPrice;
            }
            int length = newPrice.length();
            if (length < 4) {
                return inputPrice;
            }
            // to check whether the length of the number is even or odd
            boolean isEven = false;
            if (length % 2 == 0) {
                isEven = true;
            }
            // to calculate the comma index
            char ch[] = new char[length + (length / 2 - 1)];
            if (isEven) {
                for (int i = 0, j = 0; i < length; i++) {
                    ch[j++] = inputPrice.charAt(i);
                    if (i % 2 == 0 && i < (length - 3)) {
                        ch[j++] = ',';
                    }
                }
            } else {
                for (int i = 0, j = 0; i < length; i++) {
                    ch[j++] = inputPrice.charAt(i);
                    if (i % 2 == 1 && i < (length - 3)) {
                        ch[j++] = ',';
                    }
                }
            }
            // conditional return based on decimal number check
            return afterDecimal.length() > 0 ? String.valueOf(ch) + afterDecimal : String.valueOf(ch);

        } catch(NumberFormatException ex){
            ex.printStackTrace();
            return inputPrice;
        }
          catch (Exception e) {
            e.printStackTrace();
            return inputPrice;
        }
    }
    
    public static ArrayList useDifferentArrayListUnique(ArrayList vWPrpList) {
         ArrayList vWPrpListcopy = new ArrayList();
         for(int i=0;i<vWPrpList.size();i++){
             if(!vWPrpListcopy.contains(vWPrpList.get(i)))
             vWPrpListcopy.add(vWPrpList.get(i)) ;
         }
         
    return vWPrpListcopy;
    }
    public HashMap applymktVsNewMkt(HashMap allpktListMapMkt,ArrayList genericPacketLstMkt ,int day){
        int todaysdate=getdoubleDateInyyyyMMdd();
        HashMap allpktListMapMktNew=new HashMap();
        for(int i=0;i<genericPacketLstMkt.size();i++){
            String stk_idn=(String)genericPacketLstMkt.get(i);
            HashMap pktListMap=(HashMap)allpktListMapMkt.get(stk_idn);
            int pktdte = ((Integer)pktListMap.get("pkt_dte_SRT") == null)?0:(Integer)pktListMap.get("pkt_dte_SRT");
            if(pktdte>0){
            int days=diffInDaysBetweendate("yyyyMMdd",pktdte,todaysdate);
            if(days >=0 && days>day){
                pktListMap.put("mktvsnewmkt","OLDMKT");
                pktListMap.put("mktvsnewmkt_GRP", "OLDMKT");
            }else{
                pktListMap.put("mktvsnewmkt","PNEWMKT");
                pktListMap.put("mktvsnewmkt_GRP", "PNEWMKT");
                pktListMap.put("NEWMKT","PNEWMKT");
                pktListMap.put("NEWMKT_GRP", "PNEWMKT");
            }
            allpktListMapMktNew.put(stk_idn,pktListMap);
            }
        }
        
        return allpktListMapMktNew;
    }
    
    public HashMap applyOldVsNewFromMkt(HashMap allpktListMapMkt,ArrayList genericPacketLstMkt ,int day){
        int todaysdate=getdoubleDateInyyyyMMdd();
        HashMap allpktListMapMktNew=new HashMap();
        for(int i=0;i<genericPacketLstMkt.size();i++){
            String stk_idn=(String)genericPacketLstMkt.get(i);
            HashMap pktListMap=(HashMap)allpktListMapMkt.get(stk_idn);
            int pktdte = ((Integer)pktListMap.get("pkt_dte_SRT") == null)?0:(Integer)pktListMap.get("pkt_dte_SRT");
            if(pktdte>0){
            int days=diffInDaysBetweendate("yyyyMMdd",pktdte,todaysdate);
            if(days >=0 && days<day){
                pktListMap.put("newgrp","PNEW");
                pktListMap.put("newgrp_GRP", "PNEW");
                pktListMap.put("NEW","PNEW");
                pktListMap.put("NEW_GRP", "PNEW");
            }
            if(days >=0 && days>=day){
                pktListMap.put("oldgrp","OLD");
                pktListMap.put("oldgrp_GRP", "OLD");
            }
            allpktListMapMktNew.put(stk_idn,pktListMap);
            }
        }
        return allpktListMapMktNew;
    }
    
    public HashMap applyOldVsNewFromSold(HashMap allpktListMapMkt,ArrayList genericPacketLstMkt ,int day){
        int todaysdate=getdoubleDateInyyyyMMdd();
        HashMap allpktListMapMktNew=new HashMap();
        for(int i=0;i<genericPacketLstMkt.size();i++){
            String stk_idn=(String)genericPacketLstMkt.get(i);
            HashMap pktListMap=(HashMap)allpktListMapMkt.get(stk_idn);
            int pktdte = ((Integer)pktListMap.get("pkt_dte_SRT") == null)?0:(Integer)pktListMap.get("pkt_dte_SRT");
            int saldte = ((Integer)pktListMap.get("sl_dte_SRT") == null)?0:(Integer)pktListMap.get("sl_dte_SRT");
            if(pktdte>0 && saldte>0){
            int solddays=diffInDaysBetweendate("yyyyMMdd",saldte,todaysdate);
            if(solddays<=day){
            int days=diffInDaysBetweendate("yyyyMMdd",pktdte,todaysdate);
            if(days >=0 && days<day){
                pktListMap.put("newgrp","PNEW");
                pktListMap.put("newgrp_GRP", "PNEW");
                pktListMap.put("NEW","PNEW");
                pktListMap.put("NEW_GRP", "PNEW");
            }
            if(days >=0 && days>=day){
                pktListMap.put("oldgrp","OLD");
                pktListMap.put("oldgrp_GRP", "OLD");
            }
            allpktListMapMktNew.put(stk_idn,pktListMap);
            }
            }
        }
        return allpktListMapMktNew;
    }
    public String getToDte() {
        Date date = new Date();
        String DATE_FORMAT = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String toDte = sdf.format(date) ;
        return toDte;
    }
    
    public String getToDteDDMMMYYYY() {
        Date date = new Date();
        String DATE_FORMAT = "dd-MMM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String toDte = sdf.format(date) ;
        return toDte;
    }
    
    public int getdoubleDateInyyyyMMdd() {
        Date date = new Date();
        String DATE_FORMAT = "yyyyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String toDte = sdf.format(date) ;
        return Integer.parseInt(toDte);
    }
    
    public int diffInDaysBetweendate(String format,int inputString1,int inputString2) {
        SimpleDateFormat myFormat = new SimpleDateFormat(format);
        String i1 = String.valueOf(inputString1);
        String i2 = String.valueOf(inputString2);
        long diff=0;
        try {
            Date date1 = myFormat.parse(i1);
            Date date2 = myFormat.parse(i2);
            diff = date2.getTime() - date1.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int)(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
    }
    
    public int getBackDatedDate(String format,int days) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, days*-1); 
        return Integer.parseInt(dateFormat.format(cal.getTime()));
    }
    
    public String getBackDate(String format,int days) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, days*-1); 
        return dateFormat.format(cal.getTime());
    }
    public String getDtTm() {
        String dtTm = "";
        Calendar date = Calendar.getInstance();
        SimpleDateFormat formatter =
            new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        dtTm = formatter.format(date.getTime());
        return dtTm;
    }
    public String getToDteMarker() {
        String toDte = ""; 
        Date date = new Date();
        String DATE_FORMAT = "dd-MM-yyyy hh:mm:ss aaa";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        toDte = sdf.format(date) ;
        return toDte;
    }
    public String getToDteTime() {
        Date date = new Date();
        String DATE_FORMAT = "ddMMMyyyy_kk.mm.ss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String toDte = sdf.format(date) ;
        return toDte;
    }
  public String getToDteGiveFrmt(String DATE_FORMAT) {
      Date date = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
      String toDte = sdf.format(date) ;
      return toDte;
  }
    public String getTime() {
        Date date = new Date();
        String DATE_FORMAT = "hh.mm.ss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String toDte = sdf.format(date) ;
        return toDte;
    }
    public String convertMillis(long milliseconds){
    long seconds, minutes, hours;
       seconds = milliseconds / 1000;
       minutes = seconds / 60;
       seconds = seconds % 60;
       hours = minutes / 60;
       minutes = minutes % 60;
      return("Processed in: " + hours + ":" + minutes + ":" + seconds);
    }
   
    public void setLog(LogMgr log) {
        this.log = log;
    }

    public LogMgr getLog() {
        return log;
    }
    
    public String toconvertStringtoDate(String strDate) {
    String ddate = null;
    try {
    //input date format
    SimpleDateFormat dFormat = new SimpleDateFormat("dd-MMM-yyyy");
    //output date format
    SimpleDateFormat dFormatFinal = new SimpleDateFormat("yyyyMMdd");
    Date date = dFormat.parse(strDate);
    ddate = dFormatFinal.format(date);
    } catch (Exception e) {
    }
    return ddate;
    }
    public String convertyyyymmddFmt(String strDate) {
    String arrfrm[] = strDate.split("-");
    return arrfrm[2]+arrfrm[1]+arrfrm[0];
    }
    
    public String formattedDate(String dte, String outputFormat) {
        SimpleDateFormat input = new SimpleDateFormat("yyyyMMdd");
        DateFormat output = new SimpleDateFormat(outputFormat);
        Date dt = new Date();
        try {
            dt = input.parse(dte);
        } catch (ParseException e) {
        }
        return output.format(dt);
    }
    public String compressVlu(String vlu,String divideBy) {
        BigDecimal bigVlu = new BigDecimal(vlu);
        BigDecimal bigdivideBy = new BigDecimal(divideBy);
        BigDecimal rtnVlu = new BigDecimal(1);
        try {
            rtnVlu =bigVlu.divide(bigdivideBy, 2,RoundingMode.HALF_EVEN);
        } catch (Exception e) {   
        }
        return rtnVlu.toString();
    }
    public String convertStringArrayToString (String delimiter,String[] fmtLst){
        StringBuffer sbf = new StringBuffer();
        if(fmtLst.length > 0){
        sbf.append(fmtLst[0]);
        for(int i=1; i < fmtLst.length; i++){
        sbf.append(delimiter).append(fmtLst[i]);
        }
        }
        return sbf.toString();
    }
    @SuppressWarnings("unchecked")
    public HashMap<String, String> getKV1(HttpServletRequest req, HttpServletResponse res,ArrayList<HashMap> pkts, String[] gridParams,String setshowpprp,String itm_typ,String keyDelim,String gridby,String mdl,String dfpg,String crtwtfor,String Complsary) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        HashMap<String, String> kv = new HashMap<String, String>();
        int pktssz=pkts.size();
        HashMap dbinfo = info.getDmbsInfoLst();
        String ageval = (String)dbinfo.get("AGE");
        String hitval = (String)dbinfo.get("HIT");
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get(dfpg);
        ArrayList pageList=new ArrayList();
        HashMap pageDtlMap=new HashMap();
        String fld_nme="",fld_ttl="",val_cond="",lov_qry="",dflt_val="",fld_typ="",form_nme="",flg1="";
        GenericInterface genericInt = new GenericImpl();
        ArrayList ANLSVWList=genericInt.genericPrprVw(req, res, mdl,mdl);
        ArrayList prpDspBlocked = info.getPageblockList();
        ArrayList showprpLst=new ArrayList();
        DBUtil util = new DBUtil();
        int indexAge = ANLSVWList.indexOf(ageval)+1;
        int indexHit = ANLSVWList.indexOf(hitval)+1;
        for (int i=0;i<pktssz;i++) {
            HashMap<String, String> dtls;
            dtls = pkts.get(i);
            double ctsCmp=Double.parseDouble(util.nvl(dtls.get("CRTWT"),"0"));
            boolean validPkt=false;
            if(crtwtfor.equals(""))
            validPkt=true; 
            else if(crtwtfor.equals("DOWN")){
                if(ctsCmp<=0.99999)
                validPkt=true; 
            }else if(crtwtfor.equals("UP")){
                if(ctsCmp>=1.0000)
                validPkt=true; 
            }
            if(!Complsary.equals("")){
                           if(!util.nvl((String)dtls.get(Complsary)).equals(""))
                           validPkt=true; 
                           else
                           validPkt=false;
            }
            
            if(validPkt){
            String key = "";
            for(String params : gridParams) {
                key = key + dtls.get(params+gridby) + keyDelim;                
            }
            if(!key.equals("") && !keyDelim.equals("")){
            key = key.substring(0, key.length() - 1);
            }
            int keyQty = 0 ;
            double dflt=0;
            BigDecimal keyCts = new BigDecimal(dflt);
            BigDecimal keyVlu = new BigDecimal(dflt);
            BigDecimal hrdBig =  new BigDecimal(100);
            BigDecimal keyrapVlu = new BigDecimal(dflt);
            BigDecimal keyfnlusdVlu = new BigDecimal(dflt);
            BigDecimal keyageVlu = new BigDecimal(0.00);
            keyQty = Integer.parseInt(util.nvl((String)kv.get(key+keyDelim+"QTY"),"0"));
            keyCts = new BigDecimal(util.nvl((String)kv.get(key+keyDelim+"CTS"),"0"));
            keyageVlu = new BigDecimal(util.nvl((String)kv.get(key+keyDelim+"AGEVLU"),"0.00"));
            keyVlu = new BigDecimal(util.nvl((String)kv.get(key+keyDelim+"VLUNRD"),"0"));
            keyrapVlu = new BigDecimal(util.nvl((String)kv.get(key+keyDelim+"RAPVLU"),"0"));
            keyfnlusdVlu = new BigDecimal(util.nvl((String)kv.get(key+keyDelim+"FNLUSDVLUNRD"),"0"));

            BigDecimal qty = new BigDecimal(util.nvl(dtls.get("QTY"),"0"));
            BigDecimal cts = new BigDecimal(util.nvl(dtls.get("CRTWT"),"0"));
            BigDecimal age = new BigDecimal(util.nvl(dtls.get("AGE"),"0.00"));
            BigDecimal rte = new BigDecimal(util.nvl(dtls.get("quot"),"0"));
            BigDecimal rap_rte = new BigDecimal(util.nvl(dtls.get("RAP_RTE"),"0"));
            BigDecimal fnl_usd = new BigDecimal(util.nvl(dtls.get("fnl_usd"),"0"));
            
            cts=cts.setScale(2, RoundingMode.HALF_EVEN);
            
            BigDecimal curkeyVlu = new BigDecimal("0");
            curkeyVlu = cts.multiply(rte);
            keyVlu = keyVlu.add(curkeyVlu);

            BigDecimal curkeyageVlu = new BigDecimal("0");
            curkeyageVlu = curkeyVlu.multiply(age);
            keyageVlu = keyageVlu.add(curkeyageVlu);
            
            BigDecimal curkeyrapVlu = new BigDecimal("0");
            curkeyrapVlu = cts.multiply(rap_rte);
            keyrapVlu = keyrapVlu.add(curkeyrapVlu);
            
            BigDecimal curkeyfnlusdVlu = new BigDecimal("0");
            curkeyfnlusdVlu = cts.multiply(fnl_usd);
            keyfnlusdVlu = keyfnlusdVlu.add(curkeyfnlusdVlu);
            
            keyCts =  keyCts.add(cts);
            keyQty += qty.intValue();
            BigDecimal lAvg = new BigDecimal(1);
            BigDecimal lRapAvg = new BigDecimal(1);
            try {
                lAvg =keyVlu.divide(keyCts, 4,RoundingMode.HALF_EVEN);
                lRapAvg =keyrapVlu.divide(keyCts, 4,RoundingMode.HALF_EVEN);
            } catch (Exception e) {   
            }
            
            pageList=(ArrayList)pageDtl.get(itm_typ);
            if(pageList!=null && pageList.size() >0 && (setshowpprp.equals("Y") || setshowpprp.equals("N"))){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            if(lov_qry.equals("PRP")){
            int indexprp = ANLSVWList.indexOf(fld_ttl)+1;   
            if(!prpDspBlocked.contains(fld_ttl)){
            if(indexprp!=0){
            if(dflt_val.equals("AVG")){
                BigDecimal lShowPrpval = new BigDecimal(1);
                BigDecimal keyshowprp = new BigDecimal(dflt);
                keyshowprp = new BigDecimal(util.nvl((String)kv.get(key+keyDelim+fld_ttl),"0"));
                String tempval=util.nvl(dtls.get(fld_ttl),"0");
                if(tempval.equals("0"))
                tempval=util.nvl(dtls.get("quot"),"0");
                BigDecimal prpval = new BigDecimal(tempval);
                BigDecimal keyshowVlu = new BigDecimal("0");
                keyshowVlu = keyCts.multiply(keyshowprp);
                BigDecimal curkeyshowVlu = new BigDecimal("0");
                curkeyshowVlu = cts.multiply(prpval);
                keyshowVlu = keyshowVlu.add(curkeyshowVlu);
                try {
                    lShowPrpval =keyshowVlu.divide(keyCts, 4,RoundingMode.HALF_EVEN);
                } catch (Exception e) {   
                }
                if(!showprpLst.contains(fld_ttl))
                showprpLst.add(fld_ttl);
                kv.put(key+keyDelim+fld_ttl, lShowPrpval.setScale(0, RoundingMode.HALF_EVEN).toString());
            }else{
                BigDecimal keyshowVlu = new BigDecimal(dflt);
                keyshowVlu = new BigDecimal(util.nvl((String)kv.get(key+keyDelim+fld_ttl+"VALUE"),"0"));
                String tempval=util.nvl(dtls.get(fld_typ),"0");
                if(tempval.equals("0"))
                tempval=util.nvl(dtls.get("quot"),"0");
                BigDecimal prpval = new BigDecimal(tempval);
                BigDecimal curkeyshowVlu = new BigDecimal("0");
                curkeyshowVlu = cts.multiply(prpval);
                keyshowVlu = keyshowVlu.add(curkeyshowVlu);
                if(!prpDspBlocked.contains(fld_typ)){
                indexprp = ANLSVWList.indexOf(fld_typ)+1; 
                if(indexprp!=0){
                    if(!showprpLst.contains(fld_ttl))
                    showprpLst.add(fld_ttl);
                    
                    BigDecimal fld_ttlDisBig = new BigDecimal("0");
                    fld_ttlDisBig = (hrdBig.subtract((keyshowVlu.multiply(hrdBig)).divide(keyrapVlu,4,RoundingMode.HALF_EVEN))).multiply(new BigDecimal(-1));
                    kv.put(key+keyDelim+fld_ttl,String.valueOf(fld_ttlDisBig.setScale(2, RoundingMode.HALF_EVEN)));
                    kv.put(key+keyDelim+fld_ttl+"VALUE",String.valueOf(keyshowVlu.setScale(0, RoundingMode.HALF_EVEN)));
                }
                }
            }
            }else if(fld_typ.equals("FNL_USD")){
                if(dflt_val.equals("AVG")){
                    BigDecimal lShowPrpval = new BigDecimal(1);
                    BigDecimal keyshowprp = new BigDecimal(dflt);
                    keyshowprp = new BigDecimal(util.nvl((String)kv.get(key+keyDelim+fld_ttl),"0"));
                    String tempval=util.nvl(dtls.get(fld_typ.toLowerCase()),"0");
                    BigDecimal prpval = new BigDecimal(tempval);
                    BigDecimal keyshowVlu = new BigDecimal("0");
                    keyshowVlu = keyCts.multiply(keyshowprp);
                    BigDecimal curkeyshowVlu = new BigDecimal("0");
                    curkeyshowVlu = cts.multiply(prpval);
                    keyshowVlu = keyshowVlu.add(curkeyshowVlu);
                    try {
                        lShowPrpval =keyshowVlu.divide(keyCts, 4,RoundingMode.HALF_EVEN);
                    } catch (Exception e) {   
                    }
                    if(!showprpLst.contains(fld_ttl))
                    showprpLst.add(fld_ttl);
                    kv.put(key+keyDelim+fld_ttl, lShowPrpval.setScale(0, RoundingMode.HALF_EVEN).toString());
                }else{
                    BigDecimal keyshowVlu = new BigDecimal(dflt);
                    keyshowVlu = new BigDecimal(util.nvl((String)kv.get(key+keyDelim+fld_ttl+"VALUE"),"0"));
                    String tempval=util.nvl(dtls.get(fld_typ.toLowerCase()),"0");
                    BigDecimal prpval = new BigDecimal(tempval);
                    BigDecimal curkeyshowVlu = new BigDecimal("0");
                    curkeyshowVlu = cts.multiply(prpval);
                    keyshowVlu = keyshowVlu.add(curkeyshowVlu);
                    if(!showprpLst.contains(fld_ttl))
                    showprpLst.add(fld_ttl);
                    BigDecimal fld_ttlDisBig = new BigDecimal("0");
                    fld_ttlDisBig = (hrdBig.subtract((keyshowVlu.multiply(hrdBig)).divide(keyrapVlu,4,RoundingMode.HALF_EVEN))).multiply(new BigDecimal(-1));
                    kv.put(key+keyDelim+fld_ttl,String.valueOf(fld_ttlDisBig.setScale(2, RoundingMode.HALF_EVEN)));
                    kv.put(key+keyDelim+fld_ttl+"VALUE",String.valueOf(keyshowVlu.setScale(0, RoundingMode.HALF_EVEN)));
                }
            }
            }
            }else if(lov_qry.equals("VLU")){
                double keyshowprp = 0 ;
                keyshowprp = Double.parseDouble(util.nvl((String)kv.get(key+keyDelim+fld_ttl),"0"));
                String tempval=util.nvl(dtls.get(fld_typ),"0");
                if(tempval.equals("0"))
                tempval=util.nvl(dtls.get("quot"),"0");
                BigDecimal prpval = new BigDecimal(tempval);
                double keyshowVlu = 0;
                if(!prpDspBlocked.contains(fld_typ)){
                int indexprp = ANLSVWList.indexOf(fld_typ)+1; 
                if(indexprp!=0){
                keyshowVlu = (keyshowprp)+(cts.doubleValue()*prpval.doubleValue());
                }else{
                    prpval = new BigDecimal(util.nvl(dtls.get(fld_typ.toLowerCase()),"0"));
                    keyshowVlu = (keyshowprp)+(cts.doubleValue()*prpval.doubleValue());
                }
                }
                if(!showprpLst.contains(fld_ttl))
                showprpLst.add(fld_ttl);
                kv.put(key+keyDelim+fld_ttl, String.valueOf(util.roundToDecimals(keyshowVlu,0)));
            }
            }
            }
            
            if(indexAge!=0){
            }
            if(indexHit!=0){
            int keyHit = 0 ;
            keyHit = Integer.parseInt(util.nvl((String)kv.get(key+keyDelim+"HITTOTAL"),"0"));
            BigDecimal hit = new BigDecimal(util.nvl(dtls.get("HIT"),"0"));
            keyHit += hit.intValue();
            kv.put(key+keyDelim+"HIT",String.valueOf(keyHit/keyQty));
            kv.put(key+keyDelim+"HITTOTAL",String.valueOf(keyHit));
            }
            
            kv.put(key+keyDelim+"QTY", String.valueOf(keyQty));
            kv.put(key+keyDelim+"CTS", String.valueOf(keyCts.setScale(2, RoundingMode.HALF_EVEN)));
            kv.put(key+keyDelim+"AVG", lAvg.setScale(0, RoundingMode.HALF_EVEN).toString());
            kv.put(key+keyDelim+"AGEVLU", keyageVlu.setScale(2, RoundingMode.HALF_EVEN).toString());
            kv.put(key+keyDelim+"AGE", (keyageVlu.divide(keyVlu,4,RoundingMode.HALF_EVEN)).setScale(0, RoundingMode.HALF_EVEN).toString());
            kv.put(key+keyDelim+"AVGNRD", lAvg.setScale(2, RoundingMode.HALF_EVEN).toString());
            kv.put(key+keyDelim+"VLU", String.valueOf(keyVlu.setScale(0, RoundingMode.HALF_EVEN)));
            kv.put(key+keyDelim+"VLUNRD", String.valueOf(keyVlu.setScale(2, RoundingMode.HALF_EVEN)));
            kv.put(key+keyDelim+"RAPVLU",String.valueOf(keyrapVlu.setScale(4, RoundingMode.HALF_EVEN)));
            kv.put(key+keyDelim+"FNLUSDVLU",String.valueOf(keyfnlusdVlu.setScale(0, RoundingMode.HALF_EVEN)));
            kv.put(key+keyDelim+"FNLUSDVLUNRD",String.valueOf(keyfnlusdVlu.setScale(2, RoundingMode.HALF_EVEN)));
            BigDecimal sdDisBig = new BigDecimal("0");
            sdDisBig = (hrdBig.subtract((keyfnlusdVlu.multiply(hrdBig)).divide(keyrapVlu,4,RoundingMode.HALF_EVEN))).multiply(new BigDecimal(-1));
            kv.put(key+keyDelim+"SD",String.valueOf(sdDisBig.setScale(2, RoundingMode.HALF_EVEN)));
            
            BigDecimal rapDisBig = new BigDecimal("0");
            rapDisBig = (hrdBig.subtract((keyVlu.multiply(hrdBig)).divide(keyrapVlu,4,RoundingMode.HALF_EVEN))).multiply(new BigDecimal(-1));
            kv.put(key+keyDelim+"RAP",String.valueOf(rapDisBig.setScale(2, RoundingMode.HALF_EVEN)));
        }
        }
        if(setshowpprp.equals("Y"))
        session.setAttribute("showprpLst", showprpLst);
        return kv;        
    }
    
    @SuppressWarnings("unchecked")
    public HashMap<String, String> getKV(HttpServletRequest req, HttpServletResponse res,ArrayList<HashMap> pkts, String[] gridParams,String setshowpprp,String itm_typ,String keyDelim,String gridby,String mdl,String dfpg,String crtwtfor) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        HashMap<String, String> kv = new HashMap<String, String>();
        int pktssz=pkts.size();
        HashMap dbinfo = info.getDmbsInfoLst();
        String ageval = (String)dbinfo.get("AGE");
        String hitval = (String)dbinfo.get("HIT");
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get(dfpg);
        ArrayList pageList=new ArrayList();
        HashMap pageDtlMap=new HashMap();
        String fld_nme="",fld_ttl="",val_cond="",lov_qry="",dflt_val="",fld_typ="",form_nme="",flg1="";
        GenericInterface genericInt = new GenericImpl();
        ArrayList ANLSVWList=genericInt.genericPrprVw(req, res, mdl,mdl);
        ArrayList prpDspBlocked = info.getPageblockList();
        ArrayList showprpLst=new ArrayList();
        DBUtil util = new DBUtil();
        int indexAge = ANLSVWList.indexOf(ageval)+1;
        int indexHit = ANLSVWList.indexOf(hitval)+1;
        for (int i=0;i<pktssz;i++) {
            HashMap<String, String> dtls;
            dtls = pkts.get(i);
            double ctsCmp=Double.parseDouble(util.nvl(dtls.get("CRTWT"),"0"));
            boolean validPkt=false;
            if(crtwtfor.equals(""))
            validPkt=true; 
            else if(crtwtfor.equals("DOWN")){
                if(ctsCmp<=0.99999)
                validPkt=true; 
            }else if(crtwtfor.equals("UP")){
                if(ctsCmp>=1.0000)
                validPkt=true; 
            }
            
            if(validPkt){
            String key = "";
            for(String params : gridParams) {
                key = key + dtls.get(params+gridby) + keyDelim;                
            }
            if(!key.equals("") && !keyDelim.equals("")){
            key = key.substring(0, key.length() - 1);
            }
            int keyQty = 0 ;
            double dflt=0;
            BigDecimal keyCts = new BigDecimal(dflt);
            BigDecimal keyVlu = new BigDecimal(dflt);
            BigDecimal hrdBig =  new BigDecimal(100);
            BigDecimal keyrapVlu = new BigDecimal(dflt);
            BigDecimal keyfnlusdVlu = new BigDecimal(dflt);
            BigDecimal keyageVlu = new BigDecimal(0.00);
            keyQty = Integer.parseInt(util.nvl((String)kv.get(key+keyDelim+"QTY"),"0"));
            keyCts = new BigDecimal(util.nvl((String)kv.get(key+keyDelim+"CTS"),"0"));
            keyageVlu = new BigDecimal(util.nvl((String)kv.get(key+keyDelim+"AGEVLU"),"0.00"));
            keyVlu = new BigDecimal(util.nvl((String)kv.get(key+keyDelim+"VLUNRD"),"0"));
            keyrapVlu = new BigDecimal(util.nvl((String)kv.get(key+keyDelim+"RAPVLU"),"0"));
            keyfnlusdVlu = new BigDecimal(util.nvl((String)kv.get(key+keyDelim+"FNLUSDVLUNRD"),"0"));

            BigDecimal qty = new BigDecimal(util.nvl(dtls.get("QTY"),"0"));
            BigDecimal cts = new BigDecimal(util.nvl(dtls.get("CRTWT"),"0"));
            BigDecimal age = new BigDecimal(util.nvl(dtls.get("AGE"),"0.00"));
            BigDecimal rte = new BigDecimal(util.nvl(dtls.get("quot"),"0"));
            BigDecimal rap_rte = new BigDecimal(util.nvl(dtls.get("RAP_RTE"),"0"));
            BigDecimal fnl_usd = new BigDecimal(util.nvl(dtls.get("fnl_usd"),"0"));
            
            cts=cts.setScale(2, RoundingMode.HALF_EVEN);
            
            BigDecimal curkeyVlu = new BigDecimal("0");
            curkeyVlu = cts.multiply(rte);
            keyVlu = keyVlu.add(curkeyVlu);

            BigDecimal curkeyageVlu = new BigDecimal("0");
            curkeyageVlu = curkeyVlu.multiply(age);
            keyageVlu = keyageVlu.add(curkeyageVlu);
            
            BigDecimal curkeyrapVlu = new BigDecimal("0");
            curkeyrapVlu = cts.multiply(rap_rte);
            keyrapVlu = keyrapVlu.add(curkeyrapVlu);
            
            BigDecimal curkeyfnlusdVlu = new BigDecimal("0");
            curkeyfnlusdVlu = cts.multiply(fnl_usd);
            keyfnlusdVlu = keyfnlusdVlu.add(curkeyfnlusdVlu);
            
            keyCts =  keyCts.add(cts);
            keyQty += qty.intValue();
            BigDecimal lAvg = new BigDecimal(1);
            BigDecimal lRapAvg = new BigDecimal(1);
            try {
                lAvg =keyVlu.divide(keyCts, 4,RoundingMode.HALF_EVEN);
                lRapAvg =keyrapVlu.divide(keyCts, 4,RoundingMode.HALF_EVEN);
            } catch (Exception e) {   
            }
            
            pageList=(ArrayList)pageDtl.get(itm_typ);
            if(pageList!=null && pageList.size() >0 && (setshowpprp.equals("Y") || setshowpprp.equals("N"))){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            if(lov_qry.equals("PRP")){
            int indexprp = ANLSVWList.indexOf(fld_ttl)+1;   
            if(!prpDspBlocked.contains(fld_ttl)){
            if(indexprp!=0){
            if(dflt_val.equals("AVG")){
                BigDecimal lShowPrpval = new BigDecimal(1);
                BigDecimal keyshowprp = new BigDecimal(dflt);
                keyshowprp = new BigDecimal(util.nvl((String)kv.get(key+keyDelim+fld_ttl),"0"));
                String tempval=util.nvl(dtls.get(fld_ttl),"0");
                if(tempval.equals("0"))
                tempval=util.nvl(dtls.get("quot"),"0");
                BigDecimal prpval = new BigDecimal(tempval);
                BigDecimal keyshowVlu = new BigDecimal("0");
                keyshowVlu = keyCts.multiply(keyshowprp);
                BigDecimal curkeyshowVlu = new BigDecimal("0");
                curkeyshowVlu = cts.multiply(prpval);
                keyshowVlu = keyshowVlu.add(curkeyshowVlu);
                try {
                    lShowPrpval =keyshowVlu.divide(keyCts, 4,RoundingMode.HALF_EVEN);
                } catch (Exception e) {   
                }
                if(!showprpLst.contains(fld_ttl))
                showprpLst.add(fld_ttl);
                kv.put(key+keyDelim+fld_ttl, lShowPrpval.setScale(0, RoundingMode.HALF_EVEN).toString());
            }else{
                BigDecimal keyshowVlu = new BigDecimal(dflt);
                keyshowVlu = new BigDecimal(util.nvl((String)kv.get(key+keyDelim+fld_ttl+"VALUE"),"0"));
                String tempval=util.nvl(dtls.get(fld_typ),"0");
                if(tempval.equals("0"))
                tempval=util.nvl(dtls.get("quot"),"0");
                BigDecimal prpval = new BigDecimal(tempval);
                BigDecimal curkeyshowVlu = new BigDecimal("0");
                curkeyshowVlu = cts.multiply(prpval);
                keyshowVlu = keyshowVlu.add(curkeyshowVlu);
                if(!prpDspBlocked.contains(fld_typ)){
                indexprp = ANLSVWList.indexOf(fld_typ)+1; 
                if(indexprp!=0){
                    if(!showprpLst.contains(fld_ttl))
                    showprpLst.add(fld_ttl);
                    
                    BigDecimal fld_ttlDisBig = new BigDecimal("0");
                    fld_ttlDisBig = (hrdBig.subtract((keyshowVlu.multiply(hrdBig)).divide(keyrapVlu,4,RoundingMode.HALF_EVEN))).multiply(new BigDecimal(-1));
                    kv.put(key+keyDelim+fld_ttl,String.valueOf(fld_ttlDisBig.setScale(2, RoundingMode.HALF_EVEN)));
                    kv.put(key+keyDelim+fld_ttl+"VALUE",String.valueOf(keyshowVlu.setScale(0, RoundingMode.HALF_EVEN)));
                }
                }
            }
            }else if(fld_typ.equals("FNL_USD")){
                if(dflt_val.equals("AVG")){
                    BigDecimal lShowPrpval = new BigDecimal(1);
                    BigDecimal keyshowprp = new BigDecimal(dflt);
                    keyshowprp = new BigDecimal(util.nvl((String)kv.get(key+keyDelim+fld_ttl),"0"));
                    String tempval=util.nvl(dtls.get(fld_typ.toLowerCase()),"0");
                    BigDecimal prpval = new BigDecimal(tempval);
                    BigDecimal keyshowVlu = new BigDecimal("0");
                    keyshowVlu = keyCts.multiply(keyshowprp);
                    BigDecimal curkeyshowVlu = new BigDecimal("0");
                    curkeyshowVlu = cts.multiply(prpval);
                    keyshowVlu = keyshowVlu.add(curkeyshowVlu);
                    try {
                        lShowPrpval =keyshowVlu.divide(keyCts, 4,RoundingMode.HALF_EVEN);
                    } catch (Exception e) {   
                    }
                    if(!showprpLst.contains(fld_ttl))
                    showprpLst.add(fld_ttl);
                    kv.put(key+keyDelim+fld_ttl, lShowPrpval.setScale(0, RoundingMode.HALF_EVEN).toString());
                }else{
                    BigDecimal keyshowVlu = new BigDecimal(dflt);
                    keyshowVlu = new BigDecimal(util.nvl((String)kv.get(key+keyDelim+fld_ttl+"VALUE"),"0"));
                    String tempval=util.nvl(dtls.get(fld_typ.toLowerCase()),"0");
                    BigDecimal prpval = new BigDecimal(tempval);
                    BigDecimal curkeyshowVlu = new BigDecimal("0");
                    curkeyshowVlu = cts.multiply(prpval);
                    keyshowVlu = keyshowVlu.add(curkeyshowVlu);
                    if(!showprpLst.contains(fld_ttl))
                    showprpLst.add(fld_ttl);
                    BigDecimal fld_ttlDisBig = new BigDecimal("0");
                    fld_ttlDisBig = (hrdBig.subtract((keyshowVlu.multiply(hrdBig)).divide(keyrapVlu,4,RoundingMode.HALF_EVEN))).multiply(new BigDecimal(-1));
                    kv.put(key+keyDelim+fld_ttl,String.valueOf(fld_ttlDisBig.setScale(2, RoundingMode.HALF_EVEN)));
                    kv.put(key+keyDelim+fld_ttl+"VALUE",String.valueOf(keyshowVlu.setScale(0, RoundingMode.HALF_EVEN)));
                }
            }
            }
            }else if(lov_qry.equals("VLU")){
                double keyshowprp = 0 ;
                keyshowprp = Double.parseDouble(util.nvl((String)kv.get(key+keyDelim+fld_ttl),"0"));
                String tempval=util.nvl(dtls.get(fld_typ),"0");
                if(tempval.equals("0"))
                tempval=util.nvl(dtls.get("quot"),"0");
                BigDecimal prpval = new BigDecimal(tempval);
                double keyshowVlu = 0;
                if(!prpDspBlocked.contains(fld_typ)){
                int indexprp = ANLSVWList.indexOf(fld_typ)+1; 
                if(indexprp!=0){
                keyshowVlu = (keyshowprp)+(cts.doubleValue()*prpval.doubleValue());
                }else{
                    prpval = new BigDecimal(util.nvl(dtls.get(fld_typ.toLowerCase()),"0"));
                    keyshowVlu = (keyshowprp)+(cts.doubleValue()*prpval.doubleValue());
                }
                }
                if(!showprpLst.contains(fld_ttl))
                showprpLst.add(fld_ttl);
                kv.put(key+keyDelim+fld_ttl, String.valueOf(util.roundToDecimals(keyshowVlu,0)));
            }
            }
            }
            
            if(indexAge!=0){
            }
            if(indexHit!=0){
            int keyHit = 0 ;
            keyHit = Integer.parseInt(util.nvl((String)kv.get(key+keyDelim+"HITTOTAL"),"0"));
            BigDecimal hit = new BigDecimal(util.nvl(dtls.get("HIT"),"0"));
            keyHit += hit.intValue();
            kv.put(key+keyDelim+"HIT",String.valueOf(keyHit/keyQty));
            kv.put(key+keyDelim+"HITTOTAL",String.valueOf(keyHit));
            }
            
            kv.put(key+keyDelim+"QTY", String.valueOf(keyQty));
            kv.put(key+keyDelim+"CTS", String.valueOf(keyCts.setScale(2, RoundingMode.HALF_EVEN)));
            kv.put(key+keyDelim+"AVG", lAvg.setScale(0, RoundingMode.HALF_EVEN).toString());
            kv.put(key+keyDelim+"AGEVLU", keyageVlu.setScale(2, RoundingMode.HALF_EVEN).toString());
            kv.put(key+keyDelim+"AGE", (keyageVlu.divide(keyVlu,4,RoundingMode.HALF_EVEN)).setScale(0, RoundingMode.HALF_EVEN).toString());
            kv.put(key+keyDelim+"AVGNRD", lAvg.setScale(2, RoundingMode.HALF_EVEN).toString());
            kv.put(key+keyDelim+"VLU", String.valueOf(keyVlu.setScale(0, RoundingMode.HALF_EVEN)));
            kv.put(key+keyDelim+"VLUNRD", String.valueOf(keyVlu.setScale(2, RoundingMode.HALF_EVEN)));
            kv.put(key+keyDelim+"RAPVLU",String.valueOf(keyrapVlu.setScale(4, RoundingMode.HALF_EVEN)));
            kv.put(key+keyDelim+"FNLUSDVLU",String.valueOf(keyfnlusdVlu.setScale(0, RoundingMode.HALF_EVEN)));
            kv.put(key+keyDelim+"FNLUSDVLUNRD",String.valueOf(keyfnlusdVlu.setScale(2, RoundingMode.HALF_EVEN)));
            BigDecimal sdDisBig = new BigDecimal("0");
            sdDisBig = (hrdBig.subtract((keyfnlusdVlu.multiply(hrdBig)).divide(keyrapVlu,4,RoundingMode.HALF_EVEN))).multiply(new BigDecimal(-1));
            kv.put(key+keyDelim+"SD",String.valueOf(sdDisBig.setScale(2, RoundingMode.HALF_EVEN)));
            
            BigDecimal rapDisBig = new BigDecimal("0");
            rapDisBig = (hrdBig.subtract((keyVlu.multiply(hrdBig)).divide(keyrapVlu,4,RoundingMode.HALF_EVEN))).multiply(new BigDecimal(-1));
            kv.put(key+keyDelim+"RAP",String.valueOf(rapDisBig.setScale(2, RoundingMode.HALF_EVEN)));
        }
        }
        if(setshowpprp.equals("Y"))
        session.setAttribute("showprpLst", showprpLst);
        return kv;        
    }
    
    public String encode(String str)  
         {  
                   try {  
                        String encodeURL=URLEncoder.encode( str, "UTF-8" );  
                        return encodeURL;  
                   } catch (UnsupportedEncodingException e) {  
                        return "Issue while encoding" +e.getMessage();  
                   }  

         } 
   
    public Boolean isNumeric(String str) {
            try  
              {  
                double d = Double.parseDouble(str);  
              }  
              catch(NumberFormatException nfe)  
              {  
                return false;  
              }  
              return true; 
      
        }
}
