package ft.com.Repair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class RepairingIssueForm extends ActionForm {
    public final Map values     = new HashMap();
    private String method="";
    private String view ="";
    private String viewAll="";
    private String repView="";
    private ArrayList empList = null;
    private ArrayList mprcList = null;
    private ArrayList trmsLst = new ArrayList();
    
    public RepairingIssueForm() {
        super();
    }
    public void reset() {
        values.clear();
      
    }
    public void resetALL() {
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

    public void setRepView(String repView) {
        this.repView = repView;
    }

    public String getRepView() {
        return repView;
    }

    public void setTrmsLst(ArrayList trmsLst) {
        this.trmsLst = trmsLst;
    }

    public ArrayList getTrmsLst() {
        return trmsLst;
    }

    public void setMprcList(ArrayList mprcList) {
        this.mprcList = mprcList;
    }

    public ArrayList getMprcList() {
        return mprcList;
    }
}
