package ft.com.assorthk;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;


public class VerificationHkForm extends ActionForm{
    public final Map values     = new HashMap();
    
  private String method="";

    public void setMethod(String method) {
        this.method = method;
    }
    public void reset() {
        values.clear();
      
    }
    public void resetAll() {
        values.clear();
    }
    public String getMethod() {
        return method;
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

}
