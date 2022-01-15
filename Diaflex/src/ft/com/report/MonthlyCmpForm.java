package ft.com.report;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class MonthlyCmpForm extends ActionForm {
    String     method = "";
    private final Map values  = new HashMap(); 
    private String[] empLst;
    private ArrayList empList = null;
    
    public MonthlyCmpForm() {
        super();
    }
    public void reset() {
        values.clear();
    }
    public void resetAll() {
        empLst=new String[]{""};
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

    public void setEmpLst(String[] empLst) {
        this.empLst = empLst;
    }

    public String[] getEmpLst() {
        return empLst;
    }

    public void setEmpList(ArrayList empList) {
        this.empList = empList;
    }

    public ArrayList getEmpList() {
        return empList;
    }
}
