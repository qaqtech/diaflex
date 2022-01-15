package ft.com.mix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class TransferToMKTMIXFrom  extends ActionForm {
    private ArrayList shapeList= new ArrayList();
    private String method="";
    private final Map values  = new HashMap();
    
    public TransferToMKTMIXFrom() {
        super();
    }
    
    public void resetAll(){
        shapeList= new ArrayList();
        values.clear();
    }
    public void reset(){
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
    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }


    public void setShapeList(ArrayList shapeList) {
        this.shapeList = shapeList;
    }

    public ArrayList getShapeList() {
        return shapeList;
    }
}
