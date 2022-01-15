
package ft.com.marketing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class MemoReportForm extends ActionForm {
    private String method="";
    private final Map values  = new HashMap();
    ArrayList memoPrintList = new ArrayList();
    ArrayList memoXlList = new ArrayList();
    public void setValue(String key, Object value) {
        values.put(key, value);
    }
    public void resetALL(){
        values.clear();
    }
    public Object getValue(String key) {
        return values.get(key);
    }
    public Map getValues() {
        return values;
    }

    public MemoReportForm() {
        super();
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setMemoPrintList(ArrayList memoPrintList) {
        this.memoPrintList = memoPrintList;
    }

    public ArrayList getMemoPrintList() {
        return memoPrintList;
    }

    public void setMemoXlList(ArrayList memoXlList) {
        this.memoXlList = memoXlList;
    }

    public ArrayList getMemoXlList() {
        return memoXlList;
    }
}
