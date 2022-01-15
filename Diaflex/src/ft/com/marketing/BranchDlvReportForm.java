package ft.com.marketing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class BranchDlvReportForm extends ActionForm {
    
  private final Map values  = new HashMap();
  private String method;
  private ArrayList byrList= new ArrayList();
  private ArrayList branchList = new ArrayList();
    
    public BranchDlvReportForm() {
        super();
    }
    
  public void resetAll(){
    byrList= new ArrayList();
    branchList = new ArrayList();
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


    public void setByrList(ArrayList byrList) {
        this.byrList = byrList;
    }

    public ArrayList getByrList() {
        return byrList;
    }

    public void setBranchList(ArrayList branchList) {
        this.branchList = branchList;
    }

    public ArrayList getBranchList() {
        return branchList;
    }
}
