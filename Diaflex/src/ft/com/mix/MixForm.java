package ft.com.mix;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class MixForm extends ActionForm {
    private ArrayList empList=new ArrayList();
    private ArrayList prcList = new ArrayList();
    public final Map values     = new HashMap();
    private String method="";
    public MixForm() {
        super();
    }
    
    public void reset() {
        values.clear();
      
    }
    public void resetALL() {
        values.clear();
        empList=new ArrayList();
    }
    public void setValue(String key, Object value) {
        values.put(key, value);
    }

    public Object getValue(String key) {
        return values.get(key);
    }

    public void setEmpList(ArrayList empList) {
        this.empList = empList;
    }

    public ArrayList getEmpList() {
        return empList;
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

    public void setPrcList(ArrayList prcList) {
        this.prcList = prcList;
    }

    public ArrayList getPrcList() {
        return prcList;
    }
}
