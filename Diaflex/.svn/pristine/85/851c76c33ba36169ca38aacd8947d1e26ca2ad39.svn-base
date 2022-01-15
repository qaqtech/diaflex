package ft.com.receipt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class PaymentTransForm extends ActionForm {
    String            method    = "";
    private final Map values  = new HashMap(); 
    private ArrayList byrLst = null;
    private ArrayList pablePartyList=null;
    private ArrayList recevPartyList=null;
    public PaymentTransForm() {
        super();
    }
    
    public void reset() {
        values.clear();
    }
    public void resetAll() {
        values.clear();
        byrLst = null;
        pablePartyList=null;
        recevPartyList=null;
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


    public void setByrLst(ArrayList byrLst) {
        this.byrLst = byrLst;
    }

    public ArrayList getByrLst() {
        return byrLst;
    }

    public void setPablePartyList(ArrayList pablePartyList) {
        this.pablePartyList = pablePartyList;
    }

    public ArrayList getPablePartyList() {
        return pablePartyList;
    }

    public void setRecevPartyList(ArrayList recevPartyList) {
        this.recevPartyList = recevPartyList;
    }

    public ArrayList getRecevPartyList() {
        return recevPartyList;
    }
}
