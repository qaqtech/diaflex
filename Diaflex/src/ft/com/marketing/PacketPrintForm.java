package ft.com.marketing;

import ft.com.pricing.TransferToMktForm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class PacketPrintForm extends ActionForm{
    public final Map values = new HashMap();
    private String method="";
    private ArrayList sttList = new ArrayList();
    private String view , viewAll;
   
    
    public PacketPrintForm() {
        super();
    }
    public void setValue(String key, Object value) {
        //System.out.println(key + ":" + (String)value);
        values.put(key, value);
    }
    public void reset() {
        values.clear();
      
    }
    public void resetAll() {
        values.clear();
        view = "";
        viewAll = "";
       sttList = new ArrayList();
      
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


    public void setSttList(ArrayList sttList) {
        this.sttList = sttList;
    }

    public ArrayList getSttList() {
        return sttList;
    }
}
