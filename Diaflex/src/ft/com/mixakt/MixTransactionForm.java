package ft.com.mixakt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class MixTransactionForm extends ActionForm {
  private final Map values  = new HashMap();
  private String method;
    public MixTransactionForm() {
        super();
    }
    
    public void reset() {
            values.clear();
    }
    public void resetAll() {
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


   
}

    

