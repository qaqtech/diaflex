package ft.com.marketing;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class ManagePairForm extends ActionForm{
    public final Map values = new HashMap();
   
    
    public ManagePairForm() {
        super();
    }
    public void setValue(String key, Object value) {
        //System.out.println(key + ":" + (String)value);
        values.put(key, value);
    }
    public void reset() {
        values.clear();
      
    }
    public Object getValue(String key) {
        return values.get(key);
    }

    public Map getValues() {
        return values;
    }
}

