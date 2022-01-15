package ft.com.QuickReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class DiscoverReportForm extends ActionForm{
    public DiscoverReportForm(){
        super();
    }
    public final Map values     = new HashMap();
    private String method="";
    private ArrayList pktList = null;
   String[] disStt = null ;
    public void reset() {
        values.clear();
        disStt = null;
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
    

    public void setDisStt(String[] disStt) {
        this.disStt = disStt;
    }

    public String[] getDisStt() {
        return disStt;
    }

    public void setPktList(ArrayList pktList) {
        this.pktList = pktList;
    }

    public ArrayList getPktList() {
        return pktList;
    }
}

