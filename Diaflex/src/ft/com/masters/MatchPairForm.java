package ft.com.masters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class MatchPairForm extends ActionForm {
    private final Map values  = new HashMap(); 
    ArrayList mprpList = new ArrayList();
    private String method;
    public MatchPairForm() {
        super();
    }
    public void reset() {
        values.clear();
    }

    public void setValue(String key, Object value) {
        System.out.println("@reportParam : "+ key + " : "+value );
        values.put(key, value);
    }

    public Object getValue(String key) {
        return values.get(key);
    }
    public Map getValues() {
        return values;
    }

    public void setMprpList(ArrayList mprpList) {
        this.mprpList = mprpList;
    }

    public ArrayList getMprpList() {
        return mprpList;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
