package ft.com.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class ComparisonReportForm extends ActionForm {
    String            method    = "";
    private final Map values  = new HashMap(); 
    private ArrayList pktDtlList = null;
    private ArrayList SttList = null;
    
    public ComparisonReportForm() {
        super();
    }
    public void reset() {
        values.clear();
    }
    public void resetAll() {
        values.clear();
    }
    
    public void setValue(String key, Object value) {
        System.out.println("@reportParam : "+ key + " : "+value );
        values.put(key, value);
    }

    public Object getValue(String key) {
        return values.get(key);
    }
   
    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public Map getValues() {
        return values;
    }

    public void setPktDtlList(ArrayList pktDtlList) {
        this.pktDtlList = pktDtlList;
    }

    public ArrayList getPktDtlList() {
        return pktDtlList;
    }

    public void setSttList(ArrayList SttList) {
        this.SttList = SttList;
    }

    public ArrayList getSttList() {
        return SttList;
    }
}
