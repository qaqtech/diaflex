package ft.com.rough;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class RoughMergeFrom extends ActionForm{
    private final Map values  = new HashMap();
    private String method;
    private String[] sttValLst = null;
    public RoughMergeFrom() {
        super();
    }
    
    public void reset() {
        values.clear();
      
    }
    public void resetAll() {
        values.clear();
        sttValLst = null;
      
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


    public void setSttValLst(String[] sttValLst) {
        this.sttValLst = sttValLst;
    }

    public String[] getSttValLst() {
        return sttValLst;
    }
}

