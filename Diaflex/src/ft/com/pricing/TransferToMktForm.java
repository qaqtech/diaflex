package ft.com.pricing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class TransferToMktForm extends ActionForm{
    public final Map values = new HashMap();
    private String method="";
    private String seq="";
    private String view , viewAll;
    private ArrayList tfrSttList = new ArrayList();
    private ArrayList tfrSttPRIList = new ArrayList();
    
    public TransferToMktForm() {
        super();
    }
    public void setValue(String key, Object value) {
        //System.out.println(key + ":" + (String)value);
        values.put(key, value);
    }
    public void reset() {
        values.clear();
      
    }
    public void resetAll() {
        values.clear();
        view = "";
        viewAll = "";
        tfrSttList = new ArrayList();
        seq="";
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

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getSeq() {
        return seq;
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

    public void setTfrSttList(ArrayList tfrSttList) {
        this.tfrSttList = tfrSttList;
    }

    public ArrayList getTfrSttList() {
        return tfrSttList;
    }

    public void setTfrSttPRIList(ArrayList tfrSttPRIList) {
        this.tfrSttPRIList = tfrSttPRIList;
    }

    public ArrayList getTfrSttPRIList() {
        return tfrSttPRIList;
    }
}
