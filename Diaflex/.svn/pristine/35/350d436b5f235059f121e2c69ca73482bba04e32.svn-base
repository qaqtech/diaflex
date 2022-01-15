package ft.com.pri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class PriceHistoryForm extends ActionForm {
    public final Map values     = new HashMap();
    private ArrayList grpList = new ArrayList();
    private String method="";
    public PriceHistoryForm() {
        super();
    }
    public void reset() {
        values.clear();
      
    }
    public void resetALL() {
        values.clear();
        grpList = new ArrayList();
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

    public void setGrpList(ArrayList grpList) {
        this.grpList = grpList;
    }

    public ArrayList getGrpList() {
        return grpList;
    }
}
