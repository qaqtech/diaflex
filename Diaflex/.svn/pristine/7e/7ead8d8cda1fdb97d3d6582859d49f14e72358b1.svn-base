package ft.com.marketing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class BiddingIssueFrom extends ActionForm {
  private final Map values  = new HashMap();
  private ArrayList mprcList = null;
  private ArrayList empList = null;
  private String method;
    public BiddingIssueFrom() {
        super();
    }
    public void resetAll(){
      mprcList= new ArrayList();
      empList = new ArrayList();
      values.clear();
    }
    public void reset(){
     
      values.clear();
    }
    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
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

    public void setMprcList(ArrayList mprcList) {
        this.mprcList = mprcList;
    }

    public ArrayList getMprcList() {
        return mprcList;
    }

    public void setEmpList(ArrayList empList) {
        this.empList = empList;
    }

    public ArrayList getEmpList() {
        return empList;
    }
}
