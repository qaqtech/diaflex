package ft.com.masters;

//~--- non-JDK imports --------------------------------------------------------

import org.apache.struts.action.ActionForm;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.Map;

public class InsGrpFrm extends ActionForm {
    public final Map values = new HashMap();
    private String   addnew;
    private int      id;
    private String   modify;

    public InsGrpFrm() {
        super();
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

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com