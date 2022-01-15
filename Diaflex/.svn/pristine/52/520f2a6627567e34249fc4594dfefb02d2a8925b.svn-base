package ft.com.mpur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class PurchaseConfrimFrom extends ActionForm {
   
    public final Map  values = new HashMap();
    private String    addnew;
    private ArrayList list;
    private String    modify;
    private String tfrToMkt;
    private String prcStt;
    private String inStt;
    private ArrayList venderList;
    private ArrayList outPrcList;
    
    public PurchaseConfrimFrom() {
        super();
    }
    
    public void reset() {
        values.clear();
    }
    
    public void resetAll() {
        values.clear();
        venderList= new ArrayList();
        outPrcList=new ArrayList();
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

    public void setPrcStt(String prcStt) {
        this.prcStt = prcStt;
    }

    public String getPrcStt() {
        return prcStt;
    }

    public void setVenderList(ArrayList venderList) {
        this.venderList = venderList;
    }

    public ArrayList getVenderList() {
        return venderList;
    }

    public void setOutPrcList(ArrayList outPrcList) {
        this.outPrcList = outPrcList;
    }

    public ArrayList getOutPrcList() {
        return outPrcList;
    }

    public void setInStt(String inStt) {
        this.inStt = inStt;
    }

    public String getInStt() {
        return inStt;
    }
}
