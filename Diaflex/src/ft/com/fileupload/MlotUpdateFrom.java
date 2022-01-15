package ft.com.fileupload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class MlotUpdateFrom extends ActionForm {
    public final Map values     = new HashMap();
    public ArrayList prpRoLst = new ArrayList();
    public ArrayList prpMinLst = new ArrayList();
    public MlotUpdateFrom() {
        super();
    }
    public void reset() {
        values.clear();
      
    }
    public void resetAll() {
        values.clear();
        prpRoLst = new ArrayList();
        prpMinLst = new ArrayList();
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

    public void setPrpRoLst(ArrayList prpRoLst) {
        this.prpRoLst = prpRoLst;
    }

    public ArrayList getPrpRoLst() {
        return prpRoLst;
    }

    public void setPrpMinLst(ArrayList prpMinLst) {
        this.prpMinLst = prpMinLst;
    }

    public ArrayList getPrpMinLst() {
        return prpMinLst;
    }
}
