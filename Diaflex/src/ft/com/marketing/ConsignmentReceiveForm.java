package ft.com.marketing;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class ConsignmentReceiveForm extends ActionForm {
  private final Map values  = new HashMap();
  private String method;
  
    public ConsignmentReceiveForm() {
        super();
    }
    public void reset(){
      values.clear();
    }
    public void resetALL(){
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

    

