package ft.com.contact;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class ColumnMapForm extends ActionForm {
    public final Map  values = new HashMap();
    private String method="";
    private String   addnew;
    private String   modify;
    private String    nmeID;
    
    public void setValue(String key, Object value) {
      
        values.put(key, value);
    }
    public void reset() {
        values.clear();
    }
    public Object getValue(String key) {
        return values.get(key);
    }

    public Map getValues() {
        return values;
    }

    public ColumnMapForm() {
        super();
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setAddnew(String addnew) {
        this.addnew = addnew;
    }

    public String getAddnew() {
        return addnew;
    }

    public void setModify(String modify) {
        this.modify = modify;
    }

    public String getModify() {
        return modify;
    }

    public void setNmeID(String nmeID) {
        this.nmeID = nmeID;
    }

    public String getNmeID() {
        return nmeID;
    }
}
