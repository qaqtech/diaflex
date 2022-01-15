package ft.com.report;

import ft.com.dao.TransactionRDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class TransactionReportForm extends ActionForm {
    String            method    = "";
    private final Map values  = new HashMap();
    private ArrayList transRTypeList = null;
    private ArrayList<TransactionRDAO> transDtlList=null;
    private int partyId;
    public TransactionReportForm() {
        super();
    }
    public void reset() {
        values.clear();
    }
    public void resetALL() {
        values.clear();
        transRTypeList = null;
        transDtlList=null;
    }
    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
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

    public void setTransRTypeList(ArrayList transRTypeList) {
        this.transRTypeList = transRTypeList;
    }

    public ArrayList getTransRTypeList() {
        return transRTypeList;
    }

    public void setPartyId(int partyId) {
        this.partyId = partyId;
    }

    public int getPartyId() {
        return partyId;
    }

    public void setTransDtlList(ArrayList<TransactionRDAO> transDtlList) {
        this.transDtlList = transDtlList;
    }

    public ArrayList<TransactionRDAO> getTransDtlList() {
        return transDtlList;
    }
}
