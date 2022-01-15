package ft.com.rfid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class PacketRfidAllocForm extends ActionForm {
  private final Map values  = new HashMap();
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
  public PacketRfidAllocForm() {
      super();
  }
  
}
