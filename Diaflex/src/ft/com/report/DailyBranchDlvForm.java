package ft.com.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class DailyBranchDlvForm extends ActionForm{
  String     method = "";
  ArrayList  branchList = null;
  private final Map values  = new HashMap(); 
  
  public DailyBranchDlvForm() {
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


    public void setBranchList(ArrayList branchList) {
        this.branchList = branchList;
    }

    public ArrayList getBranchList() {
        return branchList;
    }
}
