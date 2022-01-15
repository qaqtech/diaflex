package ft.com.lab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;


public class LabIssueForm extends ActionForm{
  public final Map values     = new HashMap();
   public final Map valuesStr     = new HashMap();
  private String method="";
  private String view="";
  private String viewAll="";
  private ArrayList labList= new ArrayList();
  private ArrayList serviceList = new ArrayList();
    private ArrayList mprcList= new ArrayList();
  private HashMap gtTotalMap =new HashMap(); 
    public LabIssueForm() {
        super();
    }

    public Map getValues() {
        return values;
    }
    
    public Map ValuesStr () {
        return valuesStr ;
    }
    public void resetAll() {
        values.clear();
       labList= new ArrayList();
      serviceList = new ArrayList();
       gtTotalMap =new HashMap(); 
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
  
    public void setValueStr(String key, String[] value) {
        valuesStr.put(key, value);
    }

    public String[] getValueStr(String key) {
        return (String[])valuesStr.get(key);
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setLabList(ArrayList labList) {
        this.labList = labList;
    }

    public ArrayList getLabList() {
        return labList;
    }

    public void setGtTotalMap(HashMap gtTotalMap) {
        this.gtTotalMap = gtTotalMap;
    }

    public HashMap getGtTotalMap() {
        return gtTotalMap;
    }

    public void setServiceList(ArrayList serviceList) {
        this.serviceList = serviceList;
    }

    public ArrayList getServiceList() {
        return serviceList;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getView() {
        return view;
    }

    public void setViewAll(String viewAll) {
        this.viewAll = viewAll;
    }

    public String getViewAll() {
        return viewAll;
    }

    public void setMprcList(ArrayList mprcList) {
        this.mprcList = mprcList;
    }

    public ArrayList getMprcList() {
        return mprcList;
    }
}
