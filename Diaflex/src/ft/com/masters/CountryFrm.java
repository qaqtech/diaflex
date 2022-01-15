package ft.com.masters;

//~--- non-JDK imports --------------------------------------------------------

import org.apache.struts.action.ActionForm;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.Map;

public class CountryFrm extends ActionForm {
    private String   countryNme = "";
    public final Map values     = new HashMap();
    private String   addnew;
    private String   modify;
    private String   delete;
    public CountryFrm() {
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

    public void setCountryNme(String countryNme) {
        this.countryNme = countryNme;
    }

    public String getCountryNme() {
        return countryNme;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

    public String getDelete() {
        return delete;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
