package ft.com;

import ft.com.dao.JsonDao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.ws.rs.core.MediaType;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {

static InputStream is = null;
static JSONObject jObj = null;
static String json = "";

// constructor
public JSONParser() {

}

public JSONObject getJSONFromUrl(String url) {

    // Making HTTP request
    try {
        // defaultHttpClient
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        is = httpEntity.getContent();

    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    } catch (ClientProtocolException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }

    try {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                is, "iso-8859-1"), 8);
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
            System.out.println(line);
        }
        is.close();
        json = sb.toString();

    } catch (Exception e) {
        System.out.println("Error converting result");
    }

    // try parse the string to a JSON object
    try {
        jObj = new JSONObject(json);
    } catch (JSONException e) {
        System.out.println("error on parse data in jsonparser.java");
    }

    // return JSON String
    return jObj;

}


    public String getJsonString(JsonDao jsonDao){
        String url = jsonDao.getServiceUrl();
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
}