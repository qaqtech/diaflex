package ft.com.pricing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class ExcelUtilityForm extends ActionForm{
    public final Map values = new HashMap();
    private String   addnew;
    private String   modify;
    private ArrayList seqList= new ArrayList();
    public ExcelUtilityForm() {
        super();
    }
    
    public void setValue(String key, Object value) {
        values.put(key, value);
    }

    public Object getValue(String key) {
        return values.get(key);
    }
    public void resetAll() {
        values.clear();
        seqList = new ArrayList();
    }

    public Map getValues() {
        return values;
    }

    public void reset() {
        values.clear();
    }

    public void setAddnew(String addnew) {
        this.addnew = addnew;
    }

    public String getAddnew() {
        return addnew;
    }

    public void setModify(String modify) {
        this.modify = modify;
    }

    public String getModify() {
        return modify;
    }

    public void setSeqList(ArrayList seqList) {
        this.seqList = seqList;
    }

    public ArrayList getSeqList() {
        return seqList;
    }
}
