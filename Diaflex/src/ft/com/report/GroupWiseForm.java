package ft.com.report;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class GroupWiseForm extends ActionForm {
    String            method    = "";
    private final Map values  = new HashMap(); 
    private String view ="";
    private String    byrIdn;
    private String reportNme = "";
    private String criteria = "";
    
    public GroupWiseForm() {
        super();
    }
    public void reset() {
        values.clear();
    }
    public void resetALL() {
        values.clear();
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

    public void setView(String view) {
        this.view = view;
    }

    public String getView() {
        return view;
    }
    public void setReportNme(String reportNme) {
        this.reportNme = reportNme;
    }

    public String getReportNme() {
        return reportNme;
    }

    public void setByrIdn(String byrIdn) {
        this.byrIdn = byrIdn;
    }

    public String getByrIdn() {
        return byrIdn;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getCriteria() {
        return criteria;
    }
}
