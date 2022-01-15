package ft.com.assort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class OddPathEditIssueForm extends ActionForm {
    public final Map values     = new HashMap();
    private String method="";
    private ArrayList mprcList= new ArrayList();
    private ArrayList empList= new ArrayList();
    
    public OddPathEditIssueForm() {
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

    public void setMprcList(ArrayList mprcList) {
        this.mprcList = mprcList;
    }

    public ArrayList getMprcList() {
        return mprcList;
    }

    public void setEmpList(ArrayList empList) {
        this.empList = empList;
    }

    public ArrayList getEmpList() {
        return empList;
    }
}

   
