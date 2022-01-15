package ft.com.marketing;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class StockTallyForm extends ActionForm {
    private String method;
    public final Map values    = new HashMap();
    private String tallyDsc;
    private ArrayList sttList=null;
    private String[] sttValLst = null;
    
    public StockTallyForm() {
        super();
    }
    
    public void reset() {
       
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
    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setTallyDsc(String tallyDsc) {
        this.tallyDsc = tallyDsc;
    }

    public String getTallyDsc() {
        return tallyDsc;
    }

    public void setSttList(ArrayList sttList) {
        this.sttList = sttList;
    }

    public ArrayList getSttList() {
        return sttList;
    }

    public void setSttValLst(String[] sttValLst) {
        this.sttValLst = sttValLst;
    }

    public String[] getSttValLst() {
        return sttValLst;
    }
}
