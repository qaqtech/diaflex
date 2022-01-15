package ft.com.role;

import ft.com.masters.CountryFrm;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class RoleForm extends ActionForm {
   
    public final Map values     = new HashMap();
    private String   addnew;
    private String   modify;

    public RoleForm() {
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

  
}
