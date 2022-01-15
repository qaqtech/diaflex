package ft.com.mpur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class PurchasePricingForm extends ActionForm {
    public final Map  values = new HashMap();
    private ArrayList venderList= new ArrayList();
    private ArrayList purIdnList= new ArrayList();
    private ArrayList typList= new ArrayList();
    private String  vlu,avgDis ,avg;
    
    public PurchasePricingForm() {
        super();
    }
    public void reset() {
        values.clear();
    }
    public void resetAll() {
        values.clear();
        venderList = new ArrayList();
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

    public void setVenderList(ArrayList venderList) {
        this.venderList = venderList;
    }

    public ArrayList getVenderList() {
        return venderList;
    }

    public void setPurIdnList(ArrayList purIdnList) {
        this.purIdnList = purIdnList;
    }

    public ArrayList getPurIdnList() {
        return purIdnList;
    }

    public void setTypList(ArrayList typList) {
        this.typList = typList;
    }

    public ArrayList getTypList() {
        return typList;
    }

    public void setVlu(String vlu) {
        this.vlu = vlu;
    }

    public String getVlu() {
        return vlu;
    }

    public void setAvgDis(String avgDis) {
        this.avgDis = avgDis;
    }

    public String getAvgDis() {
        return avgDis;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

    public String getAvg() {
        return avg;
    }
}
