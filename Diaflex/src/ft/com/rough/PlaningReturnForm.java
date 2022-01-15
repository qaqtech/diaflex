package ft.com.rough;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class PlaningReturnForm extends ActionForm {
  private final Map values  = new HashMap();
  private ArrayList empList = new ArrayList();
  private ArrayList mprcList = new ArrayList();
  private String method;
    public PlaningReturnForm() {
        super();
    }
    public void reset() {
        values.clear();
      
    }
    public void resetAll() {
        values.clear();
        empList = new ArrayList();
        mprcList = new ArrayList();
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

    public void setEmpList(ArrayList empList) {
        this.empList = empList;
    }

    public ArrayList getEmpList() {
        return empList;
    }

    public void setMprcList(ArrayList mprcList) {
        this.mprcList = mprcList;
    }

    public ArrayList getMprcList() {
        return mprcList;
    }
}

   

