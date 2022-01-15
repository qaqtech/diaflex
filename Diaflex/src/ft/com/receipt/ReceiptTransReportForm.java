package ft.com.receipt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class ReceiptTransReportForm extends ActionForm {
    String            method    = "";
    private final Map values  = new HashMap();
    private ArrayList partyList = null;
    private ArrayList invTypLst = null;
    public ReceiptTransReportForm() {
        super();
    }
    public void reset() {
        values.clear();
    }
    public void resetAll() {
        values.clear();
       
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


    public void setPartyList(ArrayList partyList) {
        this.partyList = partyList;
    }

    public ArrayList getPartyList() {
        return partyList;
    }

    public void setInvTypLst(ArrayList invTypLst) {
        this.invTypLst = invTypLst;
    }

    public ArrayList getInvTypLst() {
        return invTypLst;
    }
}
