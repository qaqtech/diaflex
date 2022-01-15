package ft.com.mixakt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class MixTransferKapuForm extends ActionForm {
  private final Map values  = new HashMap();
  private String method;
  private ArrayList boxList =null;
    private ArrayList boxIDList =null;
  public MixTransferKapuForm() {
        super();
    }
    public void reset() {
        values.clear();
      
    }
    public void resetAll() {
        values.clear();
        boxList =null;
        boxIDList =null;
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

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }


    public void setBoxList(ArrayList boxList) {
        this.boxList = boxList;
    }

    public ArrayList getBoxList() {
        return boxList;
    }

    public void setBoxIDList(ArrayList boxIDList) {
        this.boxIDList = boxIDList;
    }

    public ArrayList getBoxIDList() {
        return boxIDList;
    }
}


