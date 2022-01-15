package ft.com.marketing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class SmxSaleReturnForm extends ActionForm {
    private final Map values  = new HashMap();
    private ArrayList trmsLst = null;
    private ArrayList byrLst  = null;
    private String method = null;
    
     public SmxSaleReturnForm() {
        super();
    }
    
     public void resetAll(){
         values.clear();
         trmsLst = null;
         byrLst  = null;
     }
    public void reset(){
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
    public void setTrmsLst(ArrayList trmsLst) {
        this.trmsLst = trmsLst;
    }

    public ArrayList getTrmsLst() {
        return trmsLst;
    }

    public void setByrLst(ArrayList byrLst) {
        this.byrLst = byrLst;
    }

    public ArrayList getByrLst() {
        return byrLst;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
