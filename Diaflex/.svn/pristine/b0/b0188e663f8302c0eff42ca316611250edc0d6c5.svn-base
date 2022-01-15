package ft.com.assort;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class AssortSelectionForm extends ActionForm {
    
    public final Map values  = new HashMap();
    private String method="";
    private String view="";
    private String viewAll="";
    
    public AssortSelectionForm() {
        super();
    }
    public void reset() {
        values.clear();
      
    }
    public void resetALL() {
        values.clear();
        view = "";
        viewAll = "";
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

    public void setView(String view) {
        this.view = view;
    }

    public String getView() {
        return view;
    }

    public void setViewAll(String viewAll) {
        this.viewAll = viewAll;
    }

    public String getViewAll() {
        return viewAll;
    }
}
