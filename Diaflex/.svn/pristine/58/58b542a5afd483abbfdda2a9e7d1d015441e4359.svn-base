package ft.com.mixakt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class StonesTransferForm extends ActionForm {
  private final Map values  = new HashMap();
  private String method;
  private ArrayList frmSttList = null;
    private ArrayList toSttList = null;
    private FormFile loadFile;
  public StonesTransferForm() {
        super();
    }
    public void reset() {
        values.clear();
      
    }
    public void resetAll() {
        values.clear();
        frmSttList=null;
        toSttList=null;
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


    public void setFrmSttList(ArrayList frmSttList) {
        this.frmSttList = frmSttList;
    }

    public ArrayList getFrmSttList() {
        return frmSttList;
    }

    public void setToSttList(ArrayList toSttList) {
        this.toSttList = toSttList;
    }

    public ArrayList getToSttList() {
        return toSttList;
    }

    public void setLoadFile(FormFile loadFile) {
        this.loadFile = loadFile;
    }

    public FormFile getLoadFile() {
        return loadFile;
    }
}

