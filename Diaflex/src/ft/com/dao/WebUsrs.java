package ft.com.dao;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.Map;

public class WebUsrs {
    private String idn, nmeIdn;
    private Map    values;

    public WebUsrs() {
        super();
        values = new HashMap();
    }

    public void setValue(String key, Object value) {
        if ((key != null) && (value != null)) {
            values.put(key, value);
        }
    }

    public Object getValue(String key) {
        return values.get(key);
    }

    public Map getValues() {
        return values;
    }

    public void setIdn(String idn) {
        this.idn = idn;
    }

    public String getIdn() {
        return idn;
    }

    public void setNmeIdn(String nmeIdn) {
        this.nmeIdn = nmeIdn;
    }

    public String getNmeIdn() {
        return nmeIdn;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
