package ft.com.masters;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class EmpDepForm extends ActionForm {
    String            method    = "";
    private final Map values  = new HashMap(); 
    private ArrayList empList = null;
    
    public EmpDepForm() {
        super();
    }
    public void reset() {
        values.clear();
    }
    public void resetALL() {
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

    public void setEmpList(ArrayList empList) {
        this.empList = empList;
    }

    public ArrayList getEmpList() {
        return empList;
    }
}

