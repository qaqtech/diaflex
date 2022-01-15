package ft.com.dao;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.Map;

public class MprcDao {
    private int idn = 0;
    private Map values;

    public MprcDao() {
        super();
        values = new HashMap();
    }

    public void reset() {
        values.clear();
        idn = 0;
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

    public void setIdn(int idn) {
        this.idn = idn;
    }

    public int getIdn() {
        return idn;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
