package ft.com.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class MixdlvReportForm extends ActionForm {
    private String method=null;
    private final Map values  = new HashMap();
    private ArrayList ByrLst=null;
    public MixdlvReportForm() {
        super();
    }
    public void reset() {
      values.clear();
      ByrLst = null;
    }
    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
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

    public void setByrLst(ArrayList ByrLst) {
        this.ByrLst = ByrLst;
    }

    public ArrayList getByrLst() {
        return ByrLst;
    }
}
