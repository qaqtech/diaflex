package ft.com.mix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class SingleToMixForm extends ActionForm {
    String     method = "";
    private final Map values  = new HashMap(); 
    private ArrayList sttList= new ArrayList();
    private ArrayList sttToList= new ArrayList();
    private String stt = null;
    private ArrayList FrmPrpList = null;
  
   
    public SingleToMixForm() {
        super();
    }
  public void reset() {
      values.clear();
    }
  
  public void resetAll() {
      values.clear();
      sttList= new ArrayList();
      sttToList= new ArrayList();
  }
  
   public void setValue(String key, Object value) {
      System.out.println("@reportParam : "+ key + " : "+value );
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

    public void setSttList(ArrayList sttList) {
        this.sttList = sttList;
    }

    public ArrayList getSttList() {
        return sttList;
    }

    public void setSttToList(ArrayList sttToList) {
        this.sttToList = sttToList;
    }

    public ArrayList getSttToList() {
        return sttToList;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getStt() {
        return stt;
    }

    public void setFrmPrpList(ArrayList FrmPrpList) {
        this.FrmPrpList = FrmPrpList;
    }

    public ArrayList getFrmPrpList() {
        return FrmPrpList;
    }
}
