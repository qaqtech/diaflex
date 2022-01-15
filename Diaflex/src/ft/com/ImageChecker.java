
package ft.com;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import ft.com.generic.GenericImpl;
import ft.com.marketing.SearchQuery;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.io.UnsupportedEncodingException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.net.URLConnection;
import java.net.URLEncoder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.ArrayList;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
public class ImageChecker extends Thread {
    DBMgr                db;
    InfoMgr              info;
    HttpSession          session;
    DBUtil               util;
    public ImageChecker(HttpServletRequest req) {
        session = req.getSession(false);
        info = (InfoMgr)session.getAttribute("info");
        util = new DBUtil();
        db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    }
    public void run()
    {
    update();
    }
    public void update(){
    info.setBkPrcOn("Y");
    int cnt = 0;
    int totalupd=0;
    ResultSet rs = null;
    long lStartTime = new Date().getTime();
    HashMap dbmsInfo = info.getDmbsInfoLst();
    String bucketName     = util.nvl((String)dbmsInfo.get("BKTNME")).trim();
    String s3key     = util.nvl((String)dbmsInfo.get("S3KEY")).trim();
    String s3val     = util.nvl((String)dbmsInfo.get("S3VAL")).trim();
    HashMap dtls = new HashMap();
    ArrayList idnList=new ArrayList();
    HashMap dataDtls = new HashMap();
    ArrayList imagelistDtl=new ArrayList();
    String client =util.nvl((String)dbmsInfo.get("CNT"));
    long seconds, minutes, hours;
//    System.out.println("Processing......");
    imagelistDtl =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_ImaageDtls") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_ImaageDtls");
    String sqlQ="";
    ArrayList ary=new ArrayList();
    HashMap dataDtlsSpkt = new HashMap();
    HashMap dataDtlsLpkt = new HashMap();
    if(imagelistDtl!=null && imagelistDtl.size()>0){
    try {
//            if(client.equals("hk")){ 
//            sqlQ = " select m.idn, m.vnm, m.cert_no, pkt_id.txt pkt_id, m.certimg, m.diamondimg, m.jewimg, m.srayimg, m.agsimg, m.mrayimg, m.ringimg, m.lightimg, m.refimg, m.videos  "+
//            " from mstk m, gt_srch_rslt gt, stk_dtl pkt_id where m.pkt_ty = 'NR' and m.idn = gt.stk_idn and " +
//            " m.idn = pkt_id.mstk_idn and pkt_id.grp = 1 and pkt_id.mprp = 'S_PKT_ID'   ";
//            }else{
               sqlQ = "select m.idn, m.vnm, m.cert_no, 'N' certimg, 'N' diamondimg, 'N' jewimg, 'N' srayimg,  'N' agsimg,  'N' mrayimg, 'N' ringimg, 'N' lightimg,  'N' refimg,  'N' videos\n" + 
               "from mstk m, gt_srch_rslt gt\n" + 
               "where m.idn = gt.stk_idn";   
//            }
        ArrayList outLst = db.execSqlLst(" idns ", sqlQ, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    dtls = new HashMap();
    String stkidn=util.nvl(rs.getString("idn"));
    dtls.put("vnm",util.nvl(rs.getString("vnm"),"N"));
    dtls.put("certno",util.nvl(rs.getString("cert_no"),"N"));
    dtls.put("diamondImg",util.nvl(rs.getString("diamondimg"),"N"));
    dtls.put("jewImg",util.nvl(rs.getString("jewimg"),"N"));
    dtls.put("srayImg",util.nvl(rs.getString("srayimg"),"N"));
    dtls.put("agsImg",util.nvl(rs.getString("agsimg"),"N"));
    dtls.put("mrayImg",util.nvl(rs.getString("mrayimg"),"N"));
    dtls.put("ringImg",util.nvl(rs.getString("ringimg"),"N"));
    dtls.put("lightImg",util.nvl(rs.getString("lightimg"),"N"));
    dtls.put("refImg",util.nvl(rs.getString("refimg"),"N"));
    dtls.put("videos",util.nvl(rs.getString("videos"),"N"));
    dtls.put("certImg",util.nvl(rs.getString("certimg"),"N"));    
    dataDtls.put(stkidn,dtls);    
    idnList.add(stkidn);
    }   
        rs.close();
        pst.close();
    
    if(client.equals("hk") || client.equals("alb") || client.equals("kb") || client.equals("ku")){ 
    sqlQ = " select m.idn, m.vnm, m.cert_no, pkt_id.txt pkt_id, 'N' certimg, 'N' diamondimg, 'N' jewimg, 'N' srayimg, 'N' agsimg, 'N' mrayimg, 'N' ringimg, 'N' lightimg, 'N' refimg, 'N' videos  "+
    " from mstk m, gt_srch_rslt gt, stk_dtl pkt_id where m.idn = gt.stk_idn and " +
    " m.idn = pkt_id.mstk_idn and pkt_id.grp = 1 and pkt_id.mprp = 'S_PKT_ID'   ";
        outLst = db.execSqlLst(" idns ", sqlQ, new ArrayList());
        pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
        while(rs.next()){
        String stkidn=util.nvl(rs.getString("idn"));    
        dataDtlsSpkt.put(stkidn,util.nvl(rs.getString("pkt_id"),"N"));    
        }  
        rs.close();
        pst.close();
    }
        if(client.equals("kj")){ 
        sqlQ = " select m.idn, m.vnm, m.cert_no, pkt_id.txt lot_no, 'N' certimg, 'N' diamondimg, 'N' jewimg, 'N' srayimg, 'N' agsimg, 'N' mrayimg, 'N' ringimg, 'N' lightimg, 'N' refimg, 'N' videos  "+
        " from mstk m, gt_srch_rslt gt, stk_dtl pkt_id where m.idn = gt.stk_idn and " +
        " m.idn = pkt_id.mstk_idn and pkt_id.grp = 1 and pkt_id.mprp = 'LOT_NO'   ";
            outLst = db.execSqlLst(" idns ", sqlQ, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
            while(rs.next()){
            String stkidn=util.nvl(rs.getString("idn"));    
            dataDtlsLpkt.put(stkidn,util.nvl(rs.getString("lot_no"),"N"));    
            }  
            rs.close();
            pst.close();
        }
    } catch (Exception e) {
    e.printStackTrace();
    }
    if(idnList.size()>0 && idnList!=null){
    int idnListsz=idnList.size();
//    System.out.println("Total packets : "+idnListsz);
    int imagelistDtlsz=imagelistDtl.size();
    
    for(int l=0;l<imagelistDtlsz;l++){
    HashMap imgdtls= (HashMap)imagelistDtl.get(l);
    String path=util.nvl((String)imgdtls.get("PATH"));
    String nmefmt=util.nvl((String)imgdtls.get("NMEFMT"));
    String gtcol=util.nvl((String)imgdtls.get("GTCOL"));
    String nme=util.nvl((String)imgdtls.get("NME"));
    String[] nmefmtLst = nmefmt.split(",");
    String[] nmeLst = nme.split(",");
    int gtcolupd=0;    
    for(int k=0;k<idnListsz;k++){
    String idn = util.nvl((String)idnList.get(k));    
    dtls = new HashMap();
    dtls = (HashMap)dataDtls.get(idn);
    String val = ""; 
    boolean exist = false;
    for(int i=0;i<nmeLst.length;i++)  {
    String nmetyp= nmeLst[i]; 
    if(!nmetyp.equals("")){
    for(int j=0;j<nmefmtLst.length;j++)  { 
    String nmefmttyp= nmefmtLst[j]; 
        String url="";
        if(nmetyp.equals("VNM")){
        val=util.nvl((String)dtls.get(gtcol),"N");
        String vnm=util.nvl((String)dtls.get("vnm"));
        if(!vnm.equals("") && val.equals("N")){
        if(nmefmttyp.indexOf("~VNM~") > -1)
        nmefmttyp = nmefmttyp.replaceAll("~VNM~", vnm);
        url=path+vnm+nmefmttyp;
        exist=chkImageExists(url,gtcol,idn,val,vnm+nmefmttyp,client,bucketName,s3key,s3val);
        if(exist){
        dtls.put(gtcol,vnm+nmefmttyp); 
        totalupd++;
        gtcolupd++;
        }
        }
        }
        if(nmetyp.equals("VNM")){
        val=util.nvl((String)dtls.get(gtcol),"N");
        String vnm=util.nvl((String)dtls.get("vnm"));
        if(!vnm.equals("") && val.equals("N")){
        if(nmefmttyp.indexOf("~VNM~") > -1)
        nmefmttyp = nmefmttyp.replaceAll("~VNM~", vnm);
        url=path+vnm+nmefmttyp;
        exist=chkImageExists(url,gtcol,idn,val,vnm+nmefmttyp,client,bucketName,s3key,s3val);
        if(exist){
        dtls.put(gtcol,vnm+nmefmttyp); 
        totalupd++;
        gtcolupd++;
        }
        }
        }
        if(nmetyp.equals("AVNM")){
        String vnm=util.nvl((String)dtls.get("vnm"));
        if(vnm.indexOf("A") > -1){
        vnm=vnm.replaceAll("A", "");
        val=util.nvl((String)dtls.get(gtcol),"N");
        if(!vnm.equals("") && val.equals("N")){
        if(nmefmttyp.indexOf("~AVNM~") > -1)
        nmefmttyp = nmefmttyp.replaceAll("~AVNM~", vnm);
        url=path+vnm+nmefmttyp;
        exist=chkImageExists(url,gtcol,idn,val,vnm+nmefmttyp,client,bucketName,s3key,s3val);
        if(exist){
        dtls.put(gtcol,vnm+nmefmttyp); 
        totalupd++;
        gtcolupd++;
        }
        }
        }
        }
        if(nmetyp.equals("CERT")){
        val=util.nvl((String)dtls.get(gtcol),"N");
        String certno=util.nvl((String)dtls.get("certno")); 
        if(!certno.equals("") && val.equals("N")){
        if(nmefmttyp.indexOf("~CERT~") > -1)
        nmefmttyp = nmefmttyp.replaceAll("~CERT~", certno);
        url=path+certno+nmefmttyp;
        exist=chkImageExists(url,gtcol,idn,val,certno+nmefmttyp,client,bucketName,s3key,s3val);
        if(exist){
        dtls.put(gtcol,certno+nmefmttyp); 
        totalupd++;
        gtcolupd++;
        }
        }
        }
        if(nmetyp.equals("S_PKT_ID")){
        val=util.nvl((String)dtls.get(gtcol),"N");
        String pktId=util.nvl((String)dataDtlsSpkt.get(idn));
        if(!pktId.equals("") && val.equals("N")){
        if(nmefmttyp.indexOf("~S_PKT_ID~") > -1)
        nmefmttyp = nmefmttyp.replaceAll("~S_PKT_ID~", pktId);
        url=path+pktId+nmefmttyp;
        exist=chkImageExists(url,gtcol,idn,val,pktId+nmefmttyp,client,bucketName,s3key,s3val);
        if(exist){
        dtls.put(gtcol,pktId+nmefmttyp);
        totalupd++;
        gtcolupd++;
        }
        }
        }
        if(nmetyp.equals("LOT_NO")){
        val=util.nvl((String)dtls.get(gtcol),"N");
        String pktId=util.nvl((String)dataDtlsLpkt.get(idn));
        if(!pktId.equals("") && val.equals("N")){
        if(nmefmttyp.indexOf("~LOT_NO~") > -1)
        nmefmttyp = nmefmttyp.replaceAll("~LOT_NO~", pktId);
        url=path+pktId+nmefmttyp;
        exist=chkImageExists(url,gtcol,idn,val,pktId+nmefmttyp,client,bucketName,s3key,s3val);
        if(exist){
        dtls.put(gtcol,pktId+nmefmttyp);
        totalupd++;
        gtcolupd++;
        }
        }
        }
        if(nmetyp.equals("VNMCERT")){
        val=util.nvl((String)dtls.get(gtcol),"N");
        String certno=util.nvl((String)dtls.get("certno")); 
        String vnm=util.nvl((String)dtls.get("vnm")); 
        if(!certno.equals("") && val.equals("N")){
        url=path+vnm+"/"+certno+nmefmttyp;
        exist=chkImageExists(url,gtcol,idn,val,vnm+"/"+certno+nmefmttyp,client,bucketName,s3key,s3val);
        if(exist){
        dtls.put(gtcol,vnm+"/"+certno+nmefmttyp); 
        totalupd++;
        gtcolupd++;
        }
        }
        }
    }
        }else{
            if(path.indexOf("segoma") >-1){
                val=util.nvl((String)dtls.get(gtcol),"N");
                String vnm=util.nvl((String)dtls.get("vnm"));
                try{
                if(!vnm.equals("") && val.equals("N")){
                String segomaLink =util.nvl((String)dbmsInfo.get("SEGOMA_LINK"));
                if(!segomaLink.equals("")){
                    segomaLink=segomaLink.replaceAll("VNM",vnm);
                    URL yahoo = new URL(segomaLink);
//                    System.out.println(segomaLink);
                    URLConnection yc = yahoo.openConnection();
                    BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                    String inputLine="";
                    while ((inputLine = in.readLine()) != null) 
                    val=inputLine.trim();
                    if(!util.nvl(val,"N").equals("N") && val.indexOf("html") <=-1){
                    imageTblUpd(idn,gtcol,val);
                    dtls.put(gtcol,vnm); 
                    totalupd++;
                    gtcolupd++;
                    } 
                }
                }
                } catch (MalformedURLException mue) {

//                   System.out.println("Ouch - a MalformedURLException happened.");
                   mue.printStackTrace();
                   System.exit(1);

                } catch (IOException ioe) {

                   System.out.println("Oops- an IOException happened.");
                   ioe.printStackTrace();
                   System.exit(1);

                }
            }
            if(path.indexOf("segoma") >-1){
                val=util.nvl((String)dtls.get(gtcol),"N");
                String vnm=util.nvl((String)dtls.get("vnm"));
                if(vnm.indexOf("A") > -1){
                vnm=vnm.replaceAll("A", "");
                try{
                if(!vnm.equals("") && val.equals("N")){
                String segomaLink =util.nvl((String)dbmsInfo.get("SEGOMA_LINK"));
                if(!segomaLink.equals("")){
                    segomaLink=segomaLink.replaceAll("VNM",vnm);
                    URL yahoo = new URL(segomaLink);
            //                    System.out.println(segomaLink);
                    URLConnection yc = yahoo.openConnection();
                    BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                    String inputLine="";
                    while ((inputLine = in.readLine()) != null) 
                    val=inputLine.trim();
                    if(!util.nvl(val,"N").equals("N") && val.indexOf("html") <=-1){
                    imageTblUpd(idn,gtcol,val);
                    dtls.put(gtcol,vnm); 
                    totalupd++;
                    gtcolupd++;
                    } 
                }
                }
                } catch (MalformedURLException mue) {

            //                   System.out.println("Ouch - a MalformedURLException happened.");
                   mue.printStackTrace();
                   System.exit(1);

                } catch (IOException ioe) {

                   System.out.println("Oops- an IOException happened.");
                   ioe.printStackTrace();
                   System.exit(1);

                }
                }
            }
        
            if(path.indexOf("segoma") >-1){
                val=util.nvl((String)dtls.get(gtcol),"N");
                String vnm=util.nvl((String)dtls.get("vnm"));
                try{
                if(!vnm.equals("") && val.equals("N")){
                String segomaLink =util.nvl((String)dbmsInfo.get("SEGOMA_LINK_HK"));
                if(!segomaLink.equals("")){
                    segomaLink=segomaLink.replaceAll("VNM",vnm);
                    URL yahoo = new URL(segomaLink);
            //                    System.out.println(segomaLink);
                    URLConnection yc = yahoo.openConnection();
                    BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                    String inputLine="";
                    while ((inputLine = in.readLine()) != null) 
                    val=inputLine.trim();
                    if(!util.nvl(val,"N").equals("N") && val.indexOf("html") <=-1){
                    imageTblUpd(idn,gtcol,val);
                    dtls.put(gtcol,vnm); 
                    totalupd++;
                    gtcolupd++;
                    } 
                }
                }
                } catch (MalformedURLException mue) {

            //                   System.out.println("Ouch - a MalformedURLException happened.");
                   mue.printStackTrace();
                   System.exit(1);

                } catch (IOException ioe) {

                   System.out.println("Oops- an IOException happened.");
                   ioe.printStackTrace();
                   System.exit(1);

                }
            }
            if(path.indexOf("segoma") >-1){
                val=util.nvl((String)dtls.get(gtcol),"N");
                String vnm=util.nvl((String)dtls.get("vnm"));
                if(vnm.indexOf("A") > -1){
                vnm=vnm.replaceAll("A", "");
                try{
                if(!vnm.equals("") && val.equals("N")){
                String segomaLink =util.nvl((String)dbmsInfo.get("SEGOMA_LINK_HK"));
                if(!segomaLink.equals("")){
                    segomaLink=segomaLink.replaceAll("VNM",vnm);
                    URL yahoo = new URL(segomaLink);
            //                    System.out.println(segomaLink);
                    URLConnection yc = yahoo.openConnection();
                    BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                    String inputLine="";
                    while ((inputLine = in.readLine()) != null) 
                    val=inputLine.trim();
                    if(!util.nvl(val,"N").equals("N") && val.indexOf("html") <=-1){
                    imageTblUpd(idn,gtcol,val);
                    dtls.put(gtcol,vnm); 
                    totalupd++;
                    gtcolupd++;
                    } 
                }
                }
                } catch (MalformedURLException mue) {

            //                   System.out.println("Ouch - a MalformedURLException happened.");
                   mue.printStackTrace();
                   System.exit(1);

                } catch (IOException ioe) {

                   System.out.println("Oops- an IOException happened.");
                   ioe.printStackTrace();
                   System.exit(1);

                }
            }
            }
            
        }
    }
    } 
        System.out.println("Total update statement For Column "+gtcol+" of Mstk : "+gtcolupd);   
    }
    }
        System.out.println("Total update statement on Mstk : "+totalupd);
        long lEndTime = new Date().getTime();       
        long difference = lEndTime - lStartTime;
        seconds = difference / 1000;
        minutes = seconds / 60;
        seconds = seconds % 60;
        hours = minutes / 60;
        minutes = minutes % 60;
        System.out.println("Processed in: " + hours + ":" + minutes + ":" + seconds);
    }
        info.setBkPrcOn("N");
    }
    public boolean chkImageExists(String url,String gtCol,String idn,String val,String upfilenme,String client,String bucketName,String s3key,String s3val){
     boolean exist=false;
     try{
        if(gtCol.equals("videos") && url.indexOf("SPECIAL") > -1){
                boolean snd=true;
                String chkUrl=url.replaceAll("SPECIAL", "");
                chkUrl=chkUrl.replaceAll("imaged", "videos");
                    chkUrl+=".mov";
                    URL one = new URL(chkUrl);
                    HttpURLConnection hucconn = (HttpURLConnection)one.openConnection();
                    hucconn.setRequestMethod("HEAD");
                    hucconn.connect();
                    int codeone = hucconn.getResponseCode();
                    if (codeone != 403 && codeone != 404) 
                    exist = true;
                    if(exist && val.equals("N")){
                    imageTblUpd(idn,gtCol,upfilenme.replaceAll("SPECIAL", ".mov"));
                    snd=false;
                    } 
            if(snd){
            url=url.replaceAll("SPECIAL", "");
            String urlVideos = url+"/3.js";
            URL u = new URL(url);
                        HttpURLConnection huc = (HttpURLConnection)u.openConnection();
                        huc.setRequestMethod("HEAD");
                        huc.connect();
                        int code = huc.getResponseCode();
                        if (code != 403 && code != 404) 
                                exist = true;
                        
                        if(exist && val.equals("N"))
                            imageTblUpd(idn,gtCol,"3.js");

                        if(exist == false){
                        urlVideos = url+"/2.js";
                        u = new URL(urlVideos);
                        huc = (HttpURLConnection)u.openConnection();
                        huc.setRequestMethod("HEAD");
                        huc.connect();
                        code = huc.getResponseCode();
                        if (code != 403 && code != 404) 
                                exist = true;
                
                        if(exist && val.equals("N"))
                            imageTblUpd(idn,gtCol,"2.js");
                        
                        }
                        
                        if(exist == false){
                        urlVideos = url+"/1.js";
                        u = new URL(urlVideos);
                        huc = (HttpURLConnection)u.openConnection();
                        huc.setRequestMethod("HEAD");
                        huc.connect();
                        code = huc.getResponseCode();
                        if (code != 403 && code != 404) 
                                exist = true;
                
                        if(exist && val.equals("N"))
                            imageTblUpd(idn,gtCol,"1.js");
                        
                        }
                        
                        if(exist == false){
                        urlVideos = url+"/0.js";
                        u = new URL(urlVideos);
                        huc = (HttpURLConnection)u.openConnection();
                        huc.setRequestMethod("HEAD");
                        huc.connect();
                        code = huc.getResponseCode();
                        if (code != 403 && code != 404) 
                                exist = true;
                
                        if(exist && val.equals("N"))
                            imageTblUpd(idn,gtCol,"0.js");
                        
                        }
                if(exist == false){
                urlVideos = url+"/0.js";
                u = new URL(urlVideos);
                huc = (HttpURLConnection)u.openConnection();
                huc.setRequestMethod("HEAD");
                huc.connect();
                code = huc.getResponseCode();
                if (code != 403 && code != 404) 
                        exist = true;
                
                if(exist && val.equals("N"))
                    imageTblUpd(idn,gtCol,"0.js");
                
                }
                
                if(exist == false){
                for( int j=11;j>=0;j--){
                if(exist == false){
                urlVideos = url+"/"+j+".json";
                u = new URL(urlVideos);
                huc = (HttpURLConnection)u.openConnection();
                huc.setRequestMethod("HEAD");
                huc.connect();
                code = huc.getResponseCode();
                if (code != 403 && code != 404) 
                        exist = true;
                
                if(exist && val.equals("N"))
                    imageTblUpd(idn,gtCol,upfilenme.replaceAll("SPECIAL", "/")+j+".json");
                
                }else
                break;
                }
                }
            }
            }else {
            
            if(url.indexOf("s3") > -1)
            exist=isExistS3(s3key,s3val,bucketName,url.substring(url.indexOf("com/")+4, url.length()));
            
            if(!exist){
            URL u = new URL(url);
            HttpURLConnection huc = (HttpURLConnection)u.openConnection();
            huc.setRequestMethod("HEAD");
            huc.connect();
            int code = huc.getResponseCode();
            if (code != 403 && code != 404) 
            exist = true;
            }
            

            
            if(exist && val.equals("N")){
            imageTblUpd(idn,gtCol,upfilenme);
            } 
            }
     }catch (IOException e) {
     }
        return exist;
    }

    public void imageTblUpd(String idn,String gtCol,String stt){
    String imageUpd = "update mstk set "+gtCol+" = '"+stt+"' where idn = "+idn ;
    int ct = db.execUpd("image update mstk", imageUpd, new ArrayList());
    }
    public static boolean isExistS3(String accessKey, String secretKey, String bucketName, String file) {
        AWSCredentials myCredentials = new BasicAWSCredentials(accessKey, secretKey); 
        AmazonS3Client s3Client = new AmazonS3Client(myCredentials); 
        ObjectListing objects = s3Client.listObjects(new ListObjectsRequest().withBucketName(bucketName).withPrefix(file));
        for (S3ObjectSummary objectSummary: objects.getObjectSummaries()) {
            if (objectSummary.getKey().equals(file)) {
                return true;
            }
        }
        return false;
    }
}
