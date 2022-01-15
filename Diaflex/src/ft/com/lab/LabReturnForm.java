package ft.com.lab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class LabReturnForm extends ActionForm {
    public final Map values     = new HashMap();
    private String method="";
    private ArrayList labList= new ArrayList();
    
    public LabReturnForm() {
        super();
    }

    public void reset() {
        values.clear();
      
    }
    public void resetAll() {
        values.clear();
        labList = new ArrayList();
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

    public void setLabList(ArrayList labList) {
        this.labList = labList;
    }

    public ArrayList getLabList() {
        return labList;
    }
}
