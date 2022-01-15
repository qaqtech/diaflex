package ft.com.marketing;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class UpdateFinalExChangeRateForm extends ActionForm {
    
    private final Map values  = new HashMap();
    String           method    = "";

    public UpdateFinalExChangeRateForm() {
        super();
    }
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
    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
