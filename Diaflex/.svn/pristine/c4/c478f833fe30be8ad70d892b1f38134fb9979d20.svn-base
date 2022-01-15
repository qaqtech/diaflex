package ft.com.mix;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class MixRtnMatrixForm extends ActionForm {
    private ArrayList empList=new ArrayList();
    private ArrayList prcList = new ArrayList();
    public final Map values     = new HashMap();
    private String method="";
    private ArrayList mixSizeList = null;
    private String[] sizeLst;
    private FormFile mixFile = null;
    public MixRtnMatrixForm() {
        super();
    }
    
    public void reset() {
        values.clear();
      
    }
    public void resetALL() {
        values.clear();
        empList=new ArrayList();
    }
    public void setValue(String key, Object value) {
        values.put(key, value);
    }

    public Object getValue(String key) {
        return values.get(key);
    }

    public void setEmpList(ArrayList empList) {
        this.empList = empList;
    }

    public ArrayList getEmpList() {
        return empList;
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

    public void setPrcList(ArrayList prcList) {
        this.prcList = prcList;
    }

    public ArrayList getPrcList() {
        return prcList;
    }

    public void setMixSizeList(ArrayList mixSizeList) {
        this.mixSizeList = mixSizeList;
    }

    public ArrayList getMixSizeList() {
        return mixSizeList;
    }

    public void setSizeLst(String[] sizeLst) {
        this.sizeLst = sizeLst;
    }

    public String[] getSizeLst() {
        return sizeLst;
    }

    public void setMixFile(FormFile mixFile) {
        this.mixFile = mixFile;
    }

    public FormFile getMixFile() {
        return mixFile;
    }
}

