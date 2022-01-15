package ft.com.mixakt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class MixInwardReportForm extends ActionForm {
    private final Map values  = new HashMap();
    private String method;
    private ArrayList PKTLIST =null;
    public MixInwardReportForm() {
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

    public void setPKTLIST(ArrayList PKTLIST) {
        this.PKTLIST = PKTLIST;
    }

    public ArrayList getPKTLIST() {
        return PKTLIST;
    }
}


