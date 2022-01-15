package ft.com.mix;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class SingleToMixTrfForm extends ActionForm {
    String     method = "";
    private final Map values  = new HashMap(); 
    
    public SingleToMixTrfForm() {
        super();
    }
    public void reset() {
        values.clear();
    }
    public void resetAll() {
        values.clear();
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

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}

    

