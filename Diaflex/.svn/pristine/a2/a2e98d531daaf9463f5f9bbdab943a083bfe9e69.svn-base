package ft.com.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class DailyPurReportForm  extends ActionForm{
  String     method = "";
  ArrayList  ppPktList = null;
  private final Map values  = new HashMap(); 
  
  public DailyPurReportForm() {
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

  public void setMethod(String method) {
      this.method = method;
  }

  public String getMethod() {
      return method;
  }

  public void setPpPktList(ArrayList ppPktList) {
      this.ppPktList = ppPktList;
  }

  public ArrayList getPpPktList() {
      return ppPktList;
  }
}
