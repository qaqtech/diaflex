package ft.com.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class SearchCustViewForm extends ActionForm {
    public final Map values     = new HashMap();
    private ArrayList lefXlPrp = new ArrayList();
    private ArrayList rtXlPrp = new ArrayList();
    private String[] leftPrp = null;
    private String[] rightPrp = null;
    public SearchCustViewForm() {
        super();
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

    public void setLefXlPrp(ArrayList lefXlPrp) {
        this.lefXlPrp = lefXlPrp;
    }

    public ArrayList getLefXlPrp() {
        return lefXlPrp;
    }

    public void setRtXlPrp(ArrayList rtXlPrp) {
        this.rtXlPrp = rtXlPrp;
    }

    public ArrayList getRtXlPrp() {
        return rtXlPrp;
    }

    public void setLeftPrp(String[] leftPrp) {
        this.leftPrp = leftPrp;
    }

    public String[] getLeftPrp() {
        return leftPrp;
    }

    public void setRightPrp(String[] rightPrp) {
        this.rightPrp = rightPrp;
    }

    public String[] getRightPrp() {
        return rightPrp;
    }
}
