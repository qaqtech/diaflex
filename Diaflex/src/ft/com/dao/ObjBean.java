package ft.com.dao;

import java.util.HashMap;
import java.util.Map;

public class ObjBean {
    private String nme =null;
    private String dsc = null;
    public final Map values    = new HashMap();
    public ObjBean() {
        super();
    }
    public void setNme(String nme) {
        this.nme = nme;
    }

    public String getNme() {
        return nme;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }

    public String getDsc() {
        return dsc;
    }
    public void setValue(String key, Object value) {
       

        values.put(key, value);
    }

    public Object getValue(String key) {
        return values.get(key);
    }
}
