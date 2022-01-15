package ft.com.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class MixSaleReportForm extends ActionForm {
    private String method=null;
    private final Map values  = new HashMap();
    private ArrayList ByrLst=null;
    private ArrayList addatList=null;
    private ArrayList brokerList=null;
    public MixSaleReportForm() {
        super();
    }
  public void reset() {
      values.clear();
  }
    public void resetALL() {
        values.clear();
        ByrLst = null;
    }
    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
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

    public void setByrLst(ArrayList ByrLst) {
        this.ByrLst = ByrLst;
    }

    public ArrayList getByrLst() {
        return ByrLst;
    }

    public void setAddatList(ArrayList addatList) {
        this.addatList = addatList;
    }

    public ArrayList getAddatList() {
        return addatList;
    }

    public void setBrokerList(ArrayList brokerList) {
        this.brokerList = brokerList;
    }

    public ArrayList getBrokerList() {
        return brokerList;
    }
}
