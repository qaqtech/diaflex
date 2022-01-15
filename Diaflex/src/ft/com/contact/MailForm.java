package ft.com.contact;


import java.io.File;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class MailForm extends ActionForm {
    public final Map  values = new HashMap();
String subject = "";
String senderNme = "";
String msgpost = "";
FormFile atta1;
FormFile atta2;
FormFile atta3;
String countryIdn = "";
String method = "";    
FormFile theFile;
String FileName = "";
String ContentType = "";
String msg ="";
FormFile fileUpload = null;
    
    public void setValue(String key, Object value) {
      
        values.put(key, value);
    }
    public void reset() {
        values.clear();
    }
    public Object getValue(String key) {
        return values.get(key);
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSenderNme(String senderNme) {
        this.senderNme = senderNme;
    }

    public String getSenderNme() {
        return senderNme;
    }

    public void setMsgpost(String msgpost) {
        this.msgpost = msgpost;
    }

    public String getMsgpost() {
        return msgpost;
    }


    public void setCountryIdn(String countryIdn) {
        this.countryIdn = countryIdn;
    }

    public String getCountryIdn() {
        return countryIdn;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setAtta1(FormFile atta1) {
        this.atta1 = atta1;
    }

    public FormFile getAtta1() {
        return atta1;
    }

    public void setAtta2(FormFile atta2) {
        this.atta2 = atta2;
    }

    public FormFile getAtta2() {
        return atta2;
    }

    public void setAtta3(FormFile atta3) {
        this.atta3 = atta3;
    }

    public FormFile getAtta3() {
        return atta3;
    }

    

    public void setFileName(String FileName) {
        this.FileName = FileName;
    }

    public String getFileName() {
        return FileName;
    }

    public void setContentType(String ContentType) {
        this.ContentType = ContentType;
    }

    public String getContentType() {
        return ContentType;
    }

    public void setFileUpload(FormFile fileUpload) {
        this.fileUpload = fileUpload;
    }

    public FormFile getFileUpload() {
        return fileUpload;
    }

    public void setTheFile(FormFile theFile) {
        this.theFile = theFile;
    }

    public FormFile getTheFile() {
        return theFile;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public Map getValues() {
        return values;
    }
}
