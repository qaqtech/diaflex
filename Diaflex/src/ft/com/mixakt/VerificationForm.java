package ft.com.mixakt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class VerificationForm extends ActionForm {
  private final Map values  = new HashMap();
  private String method;
  private ArrayList boxList =null;
  private ArrayList empList=null;
    public VerificationForm() {
        super();
    }
    
    public void resetAll() {
        values.clear();
        boxList =new ArrayList();
        empList=new ArrayList();
      
    }
    public void reset() {
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

    public void setBoxList(ArrayList boxList) {
        this.boxList = boxList;
    }

    public ArrayList getBoxList() {
        return boxList;
    }

    public void setEmpList(ArrayList empList) {
        this.empList = empList;
    }

    public ArrayList getEmpList() {
        return empList;
    }
}
