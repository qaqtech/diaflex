package ft.com.pricing;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class RapnetForm extends ActionForm{
    public final Map values = new HashMap();
    public RapnetForm() {
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
}
