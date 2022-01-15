package ft.com.website;

import java.util.ArrayList;


import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class WebLoginAndSrchDtlForm extends ActionForm{
    private final Map values  = new HashMap(); 
    private ArrayList pktList= new ArrayList();
    private ArrayList srchPktList= new ArrayList();
    private ArrayList actvList= new ArrayList();
 
    private String method ="";
    public WebLoginAndSrchDtlForm() {
        super();
    }

    public void reset() {
        values.clear();
    }
    public void setPktList(ArrayList pktList) {
        this.pktList = pktList;
    }

    public ArrayList getPktList() {
        return pktList;
    }
    public void setActvList(ArrayList actvList) {
        this.actvList = actvList;
    }

    public ArrayList getActvList() {
        return actvList;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
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

    public void setSrchPktList(ArrayList srchPktList) {
        this.srchPktList = srchPktList;
    }

    public ArrayList getSrchPktList() {
        return srchPktList;
    }
}
