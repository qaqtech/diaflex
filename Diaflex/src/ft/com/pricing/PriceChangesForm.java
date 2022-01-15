package ft.com.pricing;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class PriceChangesForm extends ActionForm{
    public final Map values = new HashMap();
    private String   addnew;
    private String   modify;
    private String  qty, cts, vlu,rapVlu, avgDis ,avg;
    public PriceChangesForm() {
        super();
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

    public void reset() {
        values.clear();
    }

    public void setAddnew(String addnew) {
        this.addnew = addnew;
    }

    public String getAddnew() {
        return addnew;
    }

    public void setModify(String modify) {
        this.modify = modify;
    }

    public String getModify() {
        return modify;
    }

    public void setCts(String cts) {
        this.cts = cts;
    }

    public String getCts() {
        return cts;
    }

    public void setVlu(String vlu) {
        this.vlu = vlu;
    }

    public String getVlu() {
        return vlu;
    }

    public void setRapVlu(String rapVlu) {
        this.rapVlu = rapVlu;
    }

    public String getRapVlu() {
        return rapVlu;
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
