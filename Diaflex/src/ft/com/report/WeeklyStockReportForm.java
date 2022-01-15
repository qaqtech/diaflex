package ft.com.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class WeeklyStockReportForm extends ActionForm {
    String            method    = "";
     private final Map values  = new HashMap(); 
     private String dept = null;
   
     public WeeklyStockReportForm() {
      super();
     }
    public void reset() {
        values.clear();
    }
   
    public void resetAll() {
        values.clear();
        dept =null;
       
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


    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDept() {
        return dept;
    }
}
