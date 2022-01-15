package ft.com.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class LightScreenForm  extends ActionForm{
  private final Map values  = new HashMap(); 
  
  public LightScreenForm() {
    super();
  }
  
  public void reset() {
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
}
