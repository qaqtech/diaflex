package ft.com.mpur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class PurchaseDmdForm extends ActionForm {
    public final Map  values = new HashMap();
    private String    addnew;
    private ArrayList list;
    private String    modify;
    private String tfrToMkt;
    ArrayList        empList   = new ArrayList();
    ArrayList        dmdList   = new ArrayList();
    String           empId     = "";
    String           dmdId     = "";
    public PurchaseDmdForm() {
        super();
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

    public Map getValues() {
        return values;
    }

    public void setAddnew(String addnew) {
        this.addnew = addnew;
    }

    public String getAddnew() {
        return addnew;
    }

    public void setList(ArrayList list) {
        this.list = list;
    }

    public ArrayList getList() {
        return list;
    }

    public void setModify(String modify) {
        this.modify = modify;
    }

    public String getModify() {
        return modify;
    }

    public void setTfrToMkt(String tfrToMkt) {
        this.tfrToMkt = tfrToMkt;
    }

    public String getTfrToMkt() {
        return tfrToMkt;
    }

    public void setempList(ArrayList empList) {
        this.empList = empList;
    }

    public ArrayList getempList() {
        return empList;
    }

    public void setDmdList(ArrayList dmdList) {
        this.dmdList = dmdList;
    }

    public ArrayList getDmdList() {
        return dmdList;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setDmdId(String dmdId) {
        this.dmdId = dmdId;
    }

    public String getDmdId() {
        return dmdId;
    }
}
