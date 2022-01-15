package ft.com.masters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class DeptPrcForm extends ActionForm {
    private String   deptNme = "";
    public final Map values     = new HashMap();
    private String   addnew;
    private String   modify;
    private ArrayList mprcList = new ArrayList();
    
    public DeptPrcForm() {
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


    public void setDeptNme(String deptNme) {
        this.deptNme = deptNme;
    }

    public String getDeptNme() {
        return deptNme;
    }

    public void setMprcList(ArrayList mprcList) {
        this.mprcList = mprcList;
    }

    public ArrayList getMprcList() {
        return mprcList;
    }
}
