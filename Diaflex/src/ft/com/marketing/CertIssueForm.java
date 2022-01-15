package ft.com.marketing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class CertIssueForm extends ActionForm {
  private final Map values  = new HashMap();
  private ArrayList processLst = new ArrayList();
   private ArrayList  empList = new ArrayList();
  private String method;
    public CertIssueForm() {
        super();
    }
    public void resetAll(){
      processLst= new ArrayList();
      empList = new ArrayList();
      values.clear();
    }
    public void reset(){
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

    public void setProcessLst(ArrayList processLst) {
        this.processLst = processLst;
    }

    public ArrayList getProcessLst() {
        return processLst;
    }

    public void setEmpList(ArrayList empList) {
        this.empList = empList;
    }

    public ArrayList getEmpList() {
        return empList;
    }
}
