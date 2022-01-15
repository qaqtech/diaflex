package ft.com.marketing;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class DeliveryReturnForm extends ActionForm {
    private ArrayList byrLst  = new ArrayList();
    private final Map values  = new HashMap();
    
    public DeliveryReturnForm() {
        super();
    }
    public void reset() {
        values.clear();
    }
    public void resetAll() {
        values.clear();
        byrLst = new ArrayList();
    }
    public void setValue(String key, Object value) {
        values.put(key, value);
    }

    public Object getValue(String key) {
        return values.get(key);
    }
    public void setByrLst(ArrayList byrLst) {
        this.byrLst = byrLst;
    }

    public ArrayList getByrLst() {
        return byrLst;
    }

    public Map getValues() {
        return values;
    }
}
