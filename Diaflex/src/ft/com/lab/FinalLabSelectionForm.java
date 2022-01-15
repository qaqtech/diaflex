package ft.com.lab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class FinalLabSelectionForm extends ActionForm{
    public final Map values     = new HashMap();
    private String method="";
    private ArrayList labList= new ArrayList();
    private HashMap gtTotalMap =new HashMap(); 
    private String view="";
    private String viewAll="";
    
    public FinalLabSelectionForm() {
        super();
    }
    
    public void reset() {
        values.clear();
      
    }
    public void resetAll() {
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

    public void setLabList(ArrayList labList) {
        this.labList = labList;
    }

    public ArrayList getLabList() {
        return labList;
    }

    public void setGtTotalMap(HashMap gtTotalMap) {
        this.gtTotalMap = gtTotalMap;
    }

    public HashMap getGtTotalMap() {
        return gtTotalMap;
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

