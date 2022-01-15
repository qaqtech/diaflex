package ft.com.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class IssueReturnRptForm extends ActionForm {
    String            method    = "";
    private ArrayList pktDtlList = null;
    private final Map values  = new HashMap(); 
    private String view ="";
    private ArrayList empList = new ArrayList();
    private ArrayList mprcList = new ArrayList();
    public IssueReturnRptForm() {
        super();
    }
    public void reset() {
        values.clear();
    }
   
    public void resetAll() {
        values.clear();
        empList = new ArrayList();
        mprcList = new ArrayList();
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


    public void setPktDtlList(ArrayList pktDtlList) {
        this.pktDtlList = pktDtlList;
    }

    public ArrayList getPktDtlList() {
        return pktDtlList;
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
