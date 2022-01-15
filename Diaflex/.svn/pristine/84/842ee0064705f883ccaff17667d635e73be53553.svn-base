package ft.com.masters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class AtrDtlForm extends ActionForm {
    
    public final Map values     = new HashMap();
    private String   addnew;
    private String   modify;
    private ArrayList roleList ;
    
    public AtrDtlForm() {
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


    public void setRoleList(ArrayList roleList) {
        this.roleList = roleList;
    }

    public ArrayList getRoleList() {
        return roleList;
    }
    
}

    

