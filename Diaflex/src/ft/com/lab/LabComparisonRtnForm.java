package ft.com.lab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class LabComparisonRtnForm extends ActionForm {
    public final Map values     = new HashMap();
    private String method="";
    private ArrayList empList = new ArrayList();
    private int prcId = 0;
    
    public LabComparisonRtnForm() {
        super();
    }
    public void reset() {
        values.clear();
      
    }
    public void resetAll() {
        values.clear();
        empList = new ArrayList();
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

    public void setPrcId(int prcId) {
        this.prcId = prcId;
    }

    public int getPrcId() {
        return prcId;
    }
}
