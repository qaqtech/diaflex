package ft.com.assort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class DeptAllocationForm extends ActionForm {
    
    public final Map values     = new HashMap();
    private ArrayList empList= new ArrayList();
    private ArrayList thruList = new ArrayList();
    private String thruPer = "";
    
    private String method="";
    
    public DeptAllocationForm() {
        super();
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

    public void setEmpList(ArrayList empList) {
        this.empList = empList;
    }

    public ArrayList getEmpList() {
        return empList;
    }

    public void setThruList(ArrayList thruList) {
        this.thruList = thruList;
    }

    public ArrayList getThruList() {
        return thruList;
    }

    public void setThruPer(String thruPer) {
        this.thruPer = thruPer;
    }

    public String getThruPer() {
        return thruPer;
    }
}
