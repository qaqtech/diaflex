package ft.com;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.io.StringReader;

import java.net.HttpURLConnection;
import java.net.URL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.xml.parsers.DocumentBuilder;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SaleForce {
    DBMgr   db;
    InfoMgr info;
    DBUtil  util;
    public SaleForce() {
        super();
    }
    public void init(DBMgr db, DBUtil util, InfoMgr info) {
        this.db   = db;
        this.util = util;
        this.info = info;
    }
    public void UpdateContact(HttpServletRequest request,String lnmeIdn){
            String urlParameters = "grant_type=password&client_id=3MVG9Nvmjd9lcjRnAtVMf7Jg0Q.qddvXk_cX7QbnMPEapDo1nO6VkHDokAJl4yGkBaOauf6FEKo6TkS9g2qBu&client_secret=8570787319022769827&username=amol@diamondbyhk.com.hkddp&password=hke@2015";

            String loginresponse = execute(
                         "https://ap1.salesforce.com/services/oauth2/token", "POST",urlParameters, "application/x-www-form-urlencoded", null);
            String sessionId=getSessionId(loginresponse);
//            System.out.println("loginresponse \n" + loginresponse);
            
            JSONObject jObj = new JSONObject();
         
           String cntDtl = " select nme_id,byr,byr.get_nm(emp_idn) empNme,typ,eml,mbl,qbc,emp_idn,grp_nme_idn,SALEFORCE_ID from nme_cntct_v  where nme_id=?";
           ArrayList ary = new ArrayList();
           ary.add(lnmeIdn);
           ArrayList rsLst = db.execSqlLst("cntDtl", cntDtl, ary);
            PreparedStatement pst = (PreparedStatement)rsLst.get(0);
            ResultSet rs = (ResultSet)rsLst.get(1);
            try {
                while (rs.next()) {
                    jObj.put("contactId", nvl(rs.getString("nme_id")));
                    jObj.put("name", nvl(rs.getString("byr")));
                    jObj.put("contactPerson", nvl(rs.getString("empNme")));
                    jObj.put("contactType", nvl(rs.getString("typ")));
                    jObj.put("email", nvl(rs.getString("eml")));
                    jObj.put("mob", nvl(rs.getString("mbl")));
                    jObj.put("tel", nvl(rs.getString("qbc")));
                    jObj.put("emp", nvl(rs.getString("emp_idn")));
                    jObj.put("grpComp", nvl(rs.getString("grp_nme_idn")));
                    jObj.put("saleforceId", nvl(rs.getString("SALEFORCE_ID")));
                }
                rs.close();
                pst.close();
                
                  String addrDtl = "select city_idn, bldg, zip   from maddr where nme_idn = ?" ;
                
                  ary = new ArrayList();
                  ary.add(lnmeIdn);
                  rsLst = db.execSqlLst("addrDtl", addrDtl, ary);
                  pst = (PreparedStatement)rsLst.get(0);
                  rs = (ResultSet)rsLst.get(1);
             
                     while (rs.next()) {
                         jObj.put("city", nvl(rs.getString("city_idn")));
                         jObj.put("bldg", nvl(rs.getString("bldg")));
                         jObj.put("zip", nvl(rs.getString("zip")));
                      
                     }
                rs.close();
                pst.close();
                 
               

            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            } catch (JSONException jsone) {
                // TODO: Add catch code
                jsone.printStackTrace();
            }
            try {
                if (jObj != null && jObj.length() > 0) {
                    String reststr = jObj.toString();
//                    System.out.println("Request : " + reststr);
                    String serviceResponse =
                        execute("https://cs5.salesforce.com/services/apexrest/CreateContact", "POST", reststr,
                                "application/json", sessionId);


                    DocumentBuilder doc = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                    InputSource is = new InputSource();
                    is.setCharacterStream(new StringReader(serviceResponse));
                    Document doct = doc.parse(is);
                    NodeList statusNode = doct.getElementsByTagName("response");
                    Element responseEle = (Element)statusNode.item(0);
                    String responseStr = getCharacterDataFromElement(responseEle);

                    jObj = new JSONObject(responseStr);
                    if (jObj.get("SALEFORCE_ID") != null) {
                        try {
                            String SALEFORCE_ID = (String)jObj.get("SALEFORCE_ID");
                            ary = new ArrayList();
                            ary.add(lnmeIdn);
                            ary.add("SALEFORCE_ID");
                            ary.add(SALEFORCE_ID);
                            int ct =
                                db.execCall("nme_dtlUpd", "NME_PRP_PKG.Nme_prp_upd(pNmeIdn => ?, pPrp => ?, pVal => ?)",
                                            ary);
                        } catch (ClassCastException e) {
                            // TODO: Add catch code

                        }
                    }
                }
            } catch (IOException ioe) {
                // TODO: Add catch code
                ioe.printStackTrace();
            } catch (SAXException saxe) {
                // TODO: Add catch code
                saxe.printStackTrace();
            } catch (ParserConfigurationException pce) {
                // TODO: Add catch code
                pce.printStackTrace();
            } catch (JSONException jsone) {
                // TODO: Add catch code
                jsone.printStackTrace();
            }
        
    
        }

         public static String execute(String targetURL, String HttpMethod,
           String urlParameters, String contentType, String SessionId) {
          URL url;
          HttpURLConnection connection = null;
          InputStream is=null;
          try {
           url = new URL(targetURL);
           if (HttpMethod == "GET") {
            url = new URL(url.toString() + "/" + urlParameters);
           }
           connection = (HttpURLConnection) url.openConnection();
           if (SessionId != null){
            connection.setRequestProperty("Authorization", "OAuth "+ SessionId);
           }
            connection.setRequestProperty("accept", "application/xml");
           
           if (HttpMethod == "POST") {
                   
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length","" + Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Type", contentType);
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(urlParameters);
//            System.out.println("SANDIP ATKARI : " + urlParameters);
            writer.flush();
            writer.close();
            os.close();
           }

           is = connection.getInputStream();
           BufferedReader rd = new BufferedReader(new InputStreamReader(is));
           String line;
           int readCharacter;
           StringBuffer response = new StringBuffer();
           while ((readCharacter = is.read()) != -1) {
                   response.append((char) readCharacter);
           }
           
           rd.close();

//          System.out.println("Response Sandip Atkari : " +response.toString());
           return response.toString();
          } catch (Exception e) {
           e.printStackTrace();
           return (e.getClass().getName());
          }

          finally {
           if (connection != null) {
//                   System.out.println("InputStream : "+is);
//                   System.out.println("connection : "+connection);
            connection.disconnect();
           }
          }

         }
    public String nvl(String pVal) {
        return nvl(pVal, "");
    }
    
    public String nvl(String pVal, String rVal) {

        String val = pVal ;
        if(pVal == null)
            val = rVal;
        else if(pVal.equals(""))
          val = rVal;
        return val;

    }
        public String getSessionId(String loingResponse)
         {
              java.io.InputStream sbis = new java.io.StringBufferInputStream(loingResponse.toString());
                 javax.xml.parsers.DocumentBuilderFactory b = javax.xml.parsers.DocumentBuilderFactory.newInstance();
                 b.setNamespaceAware(false);
                 org.w3c.dom.Document doc = null;
                 javax.xml.parsers.DocumentBuilder db = null;
                 try {
                     db = b.newDocumentBuilder();
                     doc = db.parse(sbis);
                 } catch (Exception e) {
                     e.printStackTrace();
                 }    
                 org.w3c.dom.Element element = doc.getDocumentElement();
                 String access_token="";
                 NodeList nodeList = element.getElementsByTagName("access_token");
                 if(nodeList!=null && nodeList.getLength() > 0){
                  Element myElement = (Element)nodeList.item(0);
                  access_token= myElement.getFirstChild().getNodeValue();
                 }
//                 System.out.println("Result : "+access_token);
                 return access_token;
         }
   
    public  String getCharacterDataFromElement(Element e) {
                Node child = e.getFirstChild();
                if (child instanceof CharacterData) {
                  CharacterData cd = (CharacterData) child;
                  return cd.getData();
                }
                return "";
              }
    
  
   
}
