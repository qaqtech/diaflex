package ft.com.report;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



import org.apache.struts.action.ActionForm;


public class PriceHistoryForm extends ActionForm{
  String           method    = "";
  public final Map values    = new HashMap();

  
  public void reset() {
      System.out.println(new Date() + " @reset : " + values.size());
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

  
    public PriceHistoryForm() {
        super();
    }
}