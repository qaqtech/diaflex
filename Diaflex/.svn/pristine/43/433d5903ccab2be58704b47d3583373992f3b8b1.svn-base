package ft.com.mix;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;


public class MixIssueForm extends ActionForm{
  public final Map values     = new HashMap();
  private String method="";
  private String view="";
  private String viewAll="";
  private HashMap gtTotalMap =new HashMap(); 
    private ArrayList mprcList= new ArrayList();
    private ArrayList empList= new ArrayList();
    public MixIssueForm() {
        super();
    }

    public Map getValues() {
        return values;
    }
    public void resetAll() {
        values.clear();
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

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setGtTotalMap(HashMap gtTotalMap) {
        this.gtTotalMap = gtTotalMap;
    }

    public HashMap getGtTotalMap() {
        return gtTotalMap;
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

    public void setEmpList(ArrayList empList) {
        this.empList = empList;
    }

    public ArrayList getEmpList() {
        return empList;
    }

    public void setMprcList(ArrayList mprcList) {
        this.mprcList = mprcList;
    }

    public ArrayList getMprcList() {
        return mprcList;
    }
}
