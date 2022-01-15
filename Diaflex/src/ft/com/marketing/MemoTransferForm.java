package ft.com.marketing;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class MemoTransferForm extends ActionForm {
    private String method="";
    private ArrayList byrList = new ArrayList();
    private ArrayList memoByrList = new ArrayList();
    private ArrayList memoTypList = new ArrayList();
    ArrayList        ExpDayList = new ArrayList();
    private final Map values  = new HashMap();
   private ArrayList trmList = new ArrayList();
    public MemoTransferForm() {
        super();
    }
  public void reset() {
      values.clear();
  }
  
  public void resetAll() {
      values.clear();
      byrList = new ArrayList();
      memoByrList = new ArrayList();
      memoTypList = new ArrayList();
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

    public void setByrList(ArrayList byrList) {
        this.byrList = byrList;
    }

    public ArrayList getByrList() {
        return byrList;
    }

    public void setTrmList(ArrayList trmList) {
        this.trmList = trmList;
    }

    public ArrayList getTrmList() {
        return trmList;
    }

    public void setMemoByrList(ArrayList memoByrList) {
        this.memoByrList = memoByrList;
    }

    public ArrayList getMemoByrList() {
        return memoByrList;
    }

    public void setMemoTypList(ArrayList memoTypList) {
        this.memoTypList = memoTypList;
    }

    public ArrayList getMemoTypList() {
        return memoTypList;
    }

    public void setExpDayList(ArrayList ExpDayList) {
        this.ExpDayList = ExpDayList;
    }

    public ArrayList getExpDayList() {
        return ExpDayList;
    }
}
