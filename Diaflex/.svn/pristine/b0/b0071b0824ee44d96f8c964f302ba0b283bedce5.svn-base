package ft.com.box;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class PendingMixForm extends ActionForm {
    
    String method    = "";
    private ArrayList pktDtlList = null;
    private final Map values  = new HashMap(); 
    private String view ="";
    
    public PendingMixForm() {
        super();
    }
    
    public void reset() {
        values.clear();
    }
    
    public void setValue(String key, Object value) {
//        System.out.println("@reportParam : "+ key + " : "+value );
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
  
  
    public void setPktDtlList(ArrayList pktDtlList) {
        this.pktDtlList = pktDtlList;
    }
  
    public ArrayList getPktDtlList() {
        return pktDtlList;
  }
}
