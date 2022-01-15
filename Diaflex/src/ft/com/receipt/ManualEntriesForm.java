package ft.com.receipt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class ManualEntriesForm extends ActionForm {
    String            method    = "";
    private final Map values  = new HashMap(); 
    private ArrayList byrList=null;
    private ArrayList mtrmsList=null;
    public ManualEntriesForm() {
        super();
    }
    
    public void reset() {
        values.clear();
    }
    public void resetAll() {
        values.clear();
    
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

    public Map getValues() {
        return values;
    }


    public void setByrList(ArrayList byrList) {
        this.byrList = byrList;
    }

    public ArrayList getByrList() {
        return byrList;
    }

    public void setMtrmsList(ArrayList mtrmsList) {
        this.mtrmsList = mtrmsList;
    }

    public ArrayList getMtrmsList() {
        return mtrmsList;
    }
}
