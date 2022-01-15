package ft.com.lab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class LabComparisonForm extends ActionForm{
    public final Map values     = new HashMap();
    private String method="";
    private ArrayList reportList = new ArrayList();
    private String reportTyp = "";
    private String view = "";
    private ArrayList empList = new ArrayList();
    
    
    public LabComparisonForm() {
        super();
    }
    public void reset() {
        values.clear();
      
    }
    public void resetAll() {
        values.clear();
        view = "";
        reportTyp = "";
        reportList = new ArrayList();
        empList = new ArrayList();
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

    public void setReportList(ArrayList reportList) {
        this.reportList = reportList;
    }

    public ArrayList getReportList() {
        return reportList;
    }

    public void setReportTyp(String reportTyp) {
        this.reportTyp = reportTyp;
    }

    public String getReportTyp() {
        return reportTyp;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getView() {
        return view;
    }

    public void setEmpList(ArrayList empList) {
        this.empList = empList;
    }

    public ArrayList getEmpList() {
        return empList;
    }
}
