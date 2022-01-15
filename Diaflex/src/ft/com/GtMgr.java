package ft.com;

import java.util.HashMap;
import java.util.Map;

public class GtMgr {
    private String elementNme = null;
    public final Map values  = new HashMap();

    public GtMgr() {
        super();
    }
  public void reset() {
      values.clear();
    
  }
    public void setElementNme(String elementNme) {
        this.elementNme = elementNme;
    }

    public String getElementNme() {
        return elementNme;
    }

    public Map getValues() {
        return values;
    }
  public void setValue(String key, Object value) {
      values.put(key, value);
  }

  public Object getValue(String key) {
      return values.get(key);
  }

}
