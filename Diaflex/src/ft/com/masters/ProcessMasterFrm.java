package ft.com.masters;

//~--- non-JDK imports --------------------------------------------------------

import org.apache.struts.action.ActionForm;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.Map;

public class ProcessMasterFrm extends ActionForm {
    private String   prcNme = "";
    public final Map values = new HashMap();
    private String   addnew;
    private String   modify;

    public ProcessMasterFrm() {
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

    public void setPrcNme(String prcNme) {
        this.prcNme = prcNme;
    }

    public String getPrcNme() {
        return prcNme;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
