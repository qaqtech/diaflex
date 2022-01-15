package ft.com.receipt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class ReceviceInvoiceForm extends ActionForm {
    String            method    = "";
    private final Map values  = new HashMap(); 
    private ArrayList invTypLst = null;
    private ArrayList slabTypLst = null;
    private ArrayList billTypLst = null;
    public ReceviceInvoiceForm() {
        super();
    }
    
    public void reset() {
        values.clear();
    }
    public void resetAll() {
        values.clear();
        invTypLst = null;
        slabTypLst = null;
        billTypLst = null;
    }
    
    public void setValue(String key, Object value) {
    
        values.put(key, value);
    }

    public Object getValue(String key) {
        return values.get(key);
    }
    
    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public Map getValues() {
        return values;
    }

    public void setInvTypLst(ArrayList invTypLst) {
        this.invTypLst = invTypLst;
    }

    public ArrayList getInvTypLst() {
        return invTypLst;
    }

    public void setSlabTypLst(ArrayList slabTypLst) {
        this.slabTypLst = slabTypLst;
    }

    public ArrayList getSlabTypLst() {
        return slabTypLst;
    }

    public void setBillTypLst(ArrayList billTypLst) {
        this.billTypLst = billTypLst;
    }

    public ArrayList getBillTypLst() {
        return billTypLst;
    }
}
