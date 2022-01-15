package ft.com.assorthk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class WtCheckForm extends ActionForm {
    public final Map values     = new HashMap();
    private String method="";
    private ArrayList mprcList= new ArrayList();
    private ArrayList empList= new ArrayList();
    private String stt = "";
    
    private ArrayList chkRem = new ArrayList();
    private ArrayList appRem = new ArrayList();
    
    public WtCheckForm() {
        super();
    }
    public void reset() {
        values.clear();
      
    }
    public void setValue(String key, Object value) {
        values.put(key, value);
    }

    public Object getValue(String key) {
        return values.get(key);
    }

    public Map getValues() {
        return values;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setMprcList(ArrayList mprcList) {
        this.mprcList = mprcList;
    }

    public ArrayList getMprcList() {
        return mprcList;
    }

    public void setEmpList(ArrayList empList) {
        this.empList = empList;
    }

    public ArrayList getEmpList() {
        return empList;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getStt() {
        return stt;
    }

    public void setChkRem(ArrayList chkRem) {
        this.chkRem = chkRem;
    }

    public ArrayList getChkRem() {
        return chkRem;
    }

    public void setAppRem(ArrayList appRem) {
        this.appRem = appRem;
    }

    public ArrayList getAppRem() {
        return appRem;
    }
}
