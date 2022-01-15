package ft.com.pricing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class ProposToLiveForm extends ActionForm{
    public final Map values = new HashMap();
    private ArrayList remList = new ArrayList();
    private String rem = "";
    
    public ProposToLiveForm() {
        super();
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

    public void reset() {
        values.clear();
    }

    public void setRemList(ArrayList remList) {
        this.remList = remList;
    }

    public ArrayList getRemList() {
        return remList;
    }

    public void setRem(String rem) {
        this.rem = rem;
    }

    public String getRem() {
        return rem;
    }
}
