package ft.com.mix;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class MixReturnForm extends ActionForm {
    private String method="";
    private ArrayList mixSizeList = null;
    private ArrayList empList=new ArrayList();
    private ArrayList prcList = new ArrayList();
    public final Map values     = new HashMap();
    public MixReturnForm() {
        super();
    }
    
    public void reset(){
        values.clear();
    }
    public void resetAll(){
        values.clear();
        mixSizeList = new ArrayList();
        empList=new ArrayList();
        prcList = new ArrayList();
    }
    public void setValue(String key, Object value) {
        values.put(key, value);
    }

    public Object getValue(String key) {
        return values.get(key);
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setMixSizeList(ArrayList mixSizeList) {
        this.mixSizeList = mixSizeList;
    }

    public ArrayList getMixSizeList() {
        return mixSizeList;
    }

    public Map getValues() {
        return values;
    }

    public void setEmpList(ArrayList empList) {
        this.empList = empList;
    }

    public ArrayList getEmpList() {
        return empList;
    }

    public void setPrcList(ArrayList prcList) {
        this.prcList = prcList;
    }

    public ArrayList getPrcList() {
        return prcList;
    }
}
