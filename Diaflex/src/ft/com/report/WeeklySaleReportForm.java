package ft.com.report;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class WeeklySaleReportForm extends ActionForm {
    String            method    = "";
     private final Map values  = new HashMap(); 
    public WeeklySaleReportForm() {
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
