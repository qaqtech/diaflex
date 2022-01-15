package ft.com.report;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class MemoDetailForm extends ActionForm {
    ArrayList pktDtlList = new ArrayList();
    private String typ;
    private final Map values  = new HashMap();
    private ArrayList memoList= new ArrayList();
    public MemoDetailForm() {
        super();
    }
    public void reset() {
        values.clear();
    }

    public void ResetAll(){
        pktDtlList = new ArrayList();
    }
    public void setPktDtlList(ArrayList pktDtlList) {
        this.pktDtlList = pktDtlList;
    }

    public ArrayList getPktDtlList() {
        return pktDtlList;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getTyp() {
        return typ;
    }

    public void setValue(String key, Object value) {
        values.put(key, value);
    }

    public Object getValue(String key) {
        return values.get(key);
    }

    public void setMemoList(ArrayList memoList) {
        this.memoList = memoList;
    }

    public ArrayList getMemoList() {
        return memoList;
    }
}
