package ft.com.mix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class MixGenericReportForm  extends ActionForm{
  String     method = "";
  
  private final Map values  = new HashMap(); 
  
    public MixGenericReportForm() {
        super();
    }
    public void reset(){
        values.clear();
    }
    public void setValue(String key, Object value) {
      
        values.put(key, value);
    }

    public Object getValue(String key) {
        return values.get(key);
    }
    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public Map getValues() {
        return values;
    }
}
