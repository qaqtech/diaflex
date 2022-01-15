package ft.com.report;

//~--- non-JDK imports --------------------------------------------------------

import org.apache.struts.action.ActionForm;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WebReportForm extends ActionForm {
    String            method    = "";
    private ArrayList ppListMap = null;
    private final Map values  = new HashMap(); 
    private String view ="";

    public WebReportForm() {
        super();
    }
    public void reset() {
        values.clear();
    }
    public void resetALL() {
        values.clear();
        ppListMap = null;
    }
    public void setPpListMap(ArrayList ppListMap) {
        this.ppListMap = ppListMap;
    }

    public ArrayList getPpListMap() {
        return ppListMap;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
    public void setValue(String key, Object value) {
        System.out.println(" "+ key + " : "+value );
        values.put(key, value);
    }

    public Object getValue(String key) {
        return values.get(key);
    }
    public Map getValues() {
        return values;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getView() {
        return view;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
