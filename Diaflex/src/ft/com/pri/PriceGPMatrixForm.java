package ft.com.pri;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class PriceGPMatrixForm extends ActionForm  {
    public final Map values = new HashMap();
    private String method = "";
    private FormFile priFile = null;
    public final Map fileVal = new HashMap();
    public PriceGPMatrixForm() {
        super();
    }
    public Map getValues() {
        return values;
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
    public void setFileVal(String key, FormFile value) {
        fileVal.put(key, value);
    }

    public FormFile getFileVal(String key) {
        return (FormFile)fileVal.get(key);
    }
    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }


    public void setPriFile(FormFile priFile) {
        this.priFile = priFile;
    }

    public FormFile getPriFile() {
        return priFile;
    }
}
