package ft.com.website;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class PendingRegistrationForm extends ActionForm {

    @SuppressWarnings("compatibility:2220250101271271969")
    private static final long serialVersionUID = 1L;

    String method = "";

    private String nmeID = "";
    private String submit = "";
    ArrayList<UserRegistrationInfoImpl> userRegnInfo = new ArrayList<UserRegistrationInfoImpl>();
    private final Map values  = new HashMap(); 
    ArrayList trmList;
   
    String byrRln = "";

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setUserRegnInfo(ArrayList<UserRegistrationInfoImpl> userRegnInfo) {
        this.userRegnInfo = userRegnInfo;
    }

    public ArrayList<UserRegistrationInfoImpl> getUserRegnInfo() {
        if (userRegnInfo == null) {
            userRegnInfo = new ArrayList<UserRegistrationInfoImpl>();
        }
        return userRegnInfo;
    }

    public void setNmeID(String nmeID) {
        this.nmeID = nmeID;
    }

    public String getNmeID() {
        return nmeID;
    }

    public void setTrmList(ArrayList trmList) {
        this.trmList = trmList;
    }

    public ArrayList getTrmList() {
        if (trmList == null) {
            trmList = new ArrayList();
        }
        return trmList;
    }

    public void setByrRln(String byrRln) {
        this.byrRln = byrRln;
    }

    public String getByrRln() {
        return byrRln;
    }

    public void setSubmit(String submit) {
        this.submit = submit;
    }

    public String getSubmit() {
        return submit;
    }

    public void setValue(String key, Object value) {
        System.out.println("@reportParam : "+ key + " : "+value );
        values.put(key, value);
    }

    public Object getValue(String key) {
        return values.get(key);
    }

    public Map getValues() {
        return values;
    }
    public void reset() {
        values.clear();
    }
}
