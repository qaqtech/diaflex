package ft.com.dao;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.Map;

public class GenDAO {
    private String idn, nmeIdn;
    private Map    values;
    private String dsc;
    private String srt;
    
    public GenDAO() {
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

    public String toString() {
        return values.toString();
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }

    public String getDsc() {
        return dsc;
    }

    public void setSrt(String srt) {
        this.srt = srt;
    }

    public String getSrt() {
        return srt;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
