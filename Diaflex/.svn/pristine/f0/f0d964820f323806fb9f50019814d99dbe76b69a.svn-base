package ft.com;

import ft.com.dao.JsonDao;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.util.HashMap;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;

import net.spy.memcached.MemcachedClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import org.json.JSONObject;

public class JspUtil {
    public JspUtil() {
        super();
    }
    
  public String nvl(String pVal) {
      return nvl(pVal, "");
  }
  
  public Object getFromMem(String mem_ip,int mem_port,String key){
      Object rtn=null;
      try {
          System.setProperty("net.spy.log.LoggerImpl", "net.spy.memcached.compat.log.SunLogger");
          Logger.getLogger("net.spy.memcached").setLevel(Level.OFF);
          MemcachedClient mcc = new MemcachedClient(new InetSocketAddress(mem_ip, mem_port));
          rtn=(Object)mcc.get(key);
          mcc.shutdown();
      }catch(Exception ex){
       System.out.println( ex.getMessage() );
      }
      return rtn;
  }
  public HashMap MappingMap (HashMap dbinfo){
      String cnt=nvl((String)dbinfo.get("CNT"));
       String mem_ip=nvl((String)dbinfo.get("MEM_IP"),"13.229.255.212");
        int mem_port=Integer.parseInt(nvl((String)dbinfo.get("MEM_PORT"),"11211"));
      HashMap allmprp = null;
       try {
           MemcachedClient mcc =
               new MemcachedClient(new InetSocketAddress(mem_ip, mem_port));
           allmprp = (HashMap)mcc.get(cnt + "_ALL_MAPPING");
       } catch (IOException ioe) {
           // TODO: Add catch code
           ioe.printStackTrace();
       }
       return allmprp;
  }
  
    public HashMap MappingPRPMap (HashMap dbinfo){
        String cnt=nvl((String)dbinfo.get("CNT"));
         String mem_ip=nvl((String)dbinfo.get("MEM_IP"),"13.229.255.212");
          int mem_port=Integer.parseInt(nvl((String)dbinfo.get("MEM_PORT"),"11211"));
        HashMap allprp = null;
         try {
             MemcachedClient mcc =
                 new MemcachedClient(new InetSocketAddress(mem_ip, mem_port));
             allprp = (HashMap)mcc.get(cnt + "_ALL_PRPING");
         } catch (IOException ioe) {
             // TODO: Add catch code
             ioe.printStackTrace();
         }
         return allprp;
    }
    public HashMap ALLMRPR (HashMap dbinfo){
        String cnt=nvl((String)dbinfo.get("CNT"));
         String mem_ip=nvl((String)dbinfo.get("MEM_IP"),"13.229.255.212");
          int mem_port=Integer.parseInt(nvl((String)dbinfo.get("MEM_PORT"),"11211"));
        HashMap allmprp = null;
         try {
             MemcachedClient mcc =
                 new MemcachedClient(new InetSocketAddress(mem_ip, mem_port));
             allmprp = (HashMap)mcc.get(cnt + "_setAllMprp");
         } catch (IOException ioe) {
             // TODO: Add catch code
             ioe.printStackTrace();
         }
         return allmprp;
    }
    
    public boolean isNumValid(String num){
      
        num=num.replaceAll(" ", "");
        num=num.replaceAll(",", "");
        boolean  isVaild = true;
        try {
            double numD = Double.parseDouble(num);
        } catch (NumberFormatException nfe) {
            // TODO: Add catch code
            nfe.printStackTrace();
            isVaild = false;
        }
       
        return isVaild;
    }
    public HashMap ALLPRP (HashMap dbinfo){
        String cnt=nvl((String)dbinfo.get("CNT"));
         String mem_ip=nvl((String)dbinfo.get("MEM_IP"),"13.229.255.212");
          int mem_port=Integer.parseInt(nvl((String)dbinfo.get("MEM_PORT"),"11211"));
        HashMap allmprp = null;
         try {
             MemcachedClient mcc =
                 new MemcachedClient(new InetSocketAddress(mem_ip, mem_port));
             allmprp = (HashMap)mcc.get(cnt + "_setAllPrp");
             System.out.println(allmprp);
         } catch (IOException ioe) {
             // TODO: Add catch code
             ioe.printStackTrace();
         }
         return allmprp;
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
    
    
    public String compressVlu(String vlu,String divideBy) {
        BigDecimal rtnVlu = new BigDecimal(1);
        if(!vlu.equals("")){
        BigDecimal bigVlu = new BigDecimal(vlu);
        BigDecimal bigdivideBy = new BigDecimal(divideBy);
        try {
            rtnVlu =bigVlu.divide(bigdivideBy, 3,RoundingMode.HALF_EVEN);
        } catch (Exception e) {   
        }
            return rtnVlu.toString();
        }else
        return "";
    }
  public String nvl(String pVal, String rVal) {

      String val = pVal ;
      if(pVal == null)
          val = rVal;
      else if(pVal.equals(""))
        val = rVal;
      return val;

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
    public double roundTopercentage(double d, double c)  
    {   
        double dc=((d*100)/c)-100; 
        int temp = (int)(dc * Math.pow(10 , 1));  
        return ((double)temp)/Math.pow(10 , 1);  
    }
    public double Round(double Rval, int Rpl) {
      double p = (double)Math.pow(10,Rpl);
      Rval = Rval * p;
      double tmp = Math.round(Rval);
      return (double)tmp/p;
      }
    public double getTtlPnt(double curAmt , double ttlAmt){
        double ttlPct = (curAmt/ttlAmt)*100;
        ttlPct = Round(ttlPct,2);
        return ttlPct;
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
    public String getDtTm() {
        String dtTm = "";
        Calendar date = Calendar.getInstance();
        SimpleDateFormat formatter =
            new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        dtTm = formatter.format(date.getTime());
        return dtTm;
    }
    public String getToDte() {
        Date date = new Date();
        String DATE_FORMAT = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String toDte = sdf.format(date) ;
        return toDte;
    }
    public String getTime() {
        Date date = new Date();
        String DATE_FORMAT = "kk.mm.ss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String toDte = sdf.format(date) ;
        return toDte;
    }
  public String prpsrtcolumn(String column,int index){
      if(index>9)
      column=column+"_0"+index;
      else
      column=column+"_00"+index; 
      return column.trim();
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
        return vnm;
    }
  public String getVnmCsv(String vnm){
      vnm = vnm.replaceAll(" ", ",");
      vnm = vnm.replace('\n',',');
      vnm = vnm.trim();
      vnm = vnm.toUpperCase();
      return vnm;
  }
    public String DecimalFormatVal(String val){
    DecimalFormat myFormatter = new DecimalFormat("###,###.##");
    String formatVal = myFormatter.format(val);
    return formatVal;
    }
    public double roundToDecimals(double d, int c)  
    {   
       int temp = (int)(d * Math.pow(10 , c));  
       return ((double)temp)/Math.pow(10 , c);  
    }
    
    public String roundToDecimals2(String dstr, int c)  
    {   double d = Double.parseDouble(dstr);
       int temp = (int)(d * Math.pow(10 , c));  
        double fnlVal = ((double)temp)/Math.pow(10 , c);
       return   String.valueOf(fnlVal);
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
    public ArrayList useDifferentArrayListUnique(ArrayList vWPrpList) {
         ArrayList vWPrpListcopy = new ArrayList();
         for(int i=0;i<vWPrpList.size();i++){
             if(!vWPrpListcopy.contains(vWPrpList.get(i)))
             vWPrpListcopy.add(vWPrpList.get(i)) ;
         }
         
    return vWPrpListcopy;
    }
    
    public HashMap uniqueMap2Level(ArrayList keyLst) {
    HashMap uniqueMap=new HashMap();
    ArrayList shLst = new ArrayList();
    ArrayList mxszLst = new ArrayList();
         for(int i=0;i<keyLst.size();i++){
            String key=(String)keyLst.get(i);
            String[] keyList = key.split("@");
            String sh=keyList[0].trim();
            String mx=keyList[1].trim();
             if(!shLst.contains(sh))
             shLst.add(sh);
             if(!mxszLst.contains(mx))
             mxszLst.add(mx);
         }
    uniqueMap.put("SH", shLst);
    uniqueMap.put("SZ", mxszLst);
    return uniqueMap;
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
    
    public Boolean CompairingDateFmt(String dte,String dateFmt){
              SimpleDateFormat sdfrmt = new SimpleDateFormat(dateFmt);
                    sdfrmt.setLenient(false);
               
                    try
                    {
                        Date javaDate = sdfrmt.parse(dte); 
                       
                    }
                    /* Date format is invalid */
                    catch (ParseException e)
                    {
                      
                        return false;
                    }
                    /* Return true if date format is valid */
                    return true;
    }
}
