package ft.com.marketing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class MemoSrchForm extends ActionForm {
    private final Map values  = new HashMap();
    private ArrayList byrList = new ArrayList();
    private ArrayList brokerList = new ArrayList();
    private ArrayList memoTypList = new ArrayList();
    private ArrayList pktDtlList = new ArrayList();
    ArrayList        ByrCbList = new ArrayList();
    private String    nmeID;
    
    public MemoSrchForm() {
        super();
    }
    public void reset() {
        values.clear();
    }
    public void resetALL() {
        values.clear();
        brokerList = new ArrayList();
        memoTypList = new ArrayList();
        pktDtlList = new ArrayList();
        nmeID="";
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

    public void setByrList(ArrayList byrList) {
        this.byrList = byrList;
    }

    public ArrayList getByrList() {
        return byrList;
    }

  
    public void setMemoTypList(ArrayList memoTypList) {
        this.memoTypList = memoTypList;
    }

    public ArrayList getMemoTypList() {
        return memoTypList;
    }

    public void setPktDtlList(ArrayList pktDtlList) {
        this.pktDtlList = pktDtlList;
    }

    public ArrayList getPktDtlList() {
        return pktDtlList;
    }

    public void setBrokerList(ArrayList brokerList) {
        this.brokerList = brokerList;
    }

    public ArrayList getBrokerList() {
        return brokerList;
    }

    public void setByrCbList(ArrayList ByrCbList) {
        this.ByrCbList = ByrCbList;
    }

    public ArrayList getByrCbList() {
        return ByrCbList;
    }
    public void setNmeID(String nmeID) {
        this.nmeID = nmeID;
    }

    public String getNmeID() {
        return nmeID;
    }
}
