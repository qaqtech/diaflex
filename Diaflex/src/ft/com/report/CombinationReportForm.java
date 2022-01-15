package ft.com.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class CombinationReportForm extends ActionForm {
    
    ArrayList sttVec=new ArrayList();
  ArrayList mprpArryRow1=new ArrayList();
  ArrayList mprpArryRow2=new ArrayList();
  ArrayList mprpArryCol1=new ArrayList();
  ArrayList mprpArryCol2=new ArrayList();
  ArrayList sttList = new ArrayList();
  
  public final Map values = new HashMap();
  private String method = "";
  
    public CombinationReportForm() {
        super();
    }
    
    
  public Map getValues() {
      return values;
  }
  public void reset() {
      values.clear();
    
  }
 
    public void setSttVec(ArrayList sttVec) {
        this.sttVec = sttVec;
    }

    public ArrayList getSttVec() {
        return sttVec;
    }

    public void setMprpArryRow1(ArrayList mprpArryRow1) {
        this.mprpArryRow1 = mprpArryRow1;
    }

    public ArrayList getMprpArryRow1() {
        return mprpArryRow1;
    }
  public void setValue(String key, Object value) {
      System.out.println(key + ":" + (String)value);
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

    public void setMprpArryRow2(ArrayList mprpArryRow2) {
        this.mprpArryRow2 = mprpArryRow2;
    }

    public ArrayList getMprpArryRow2() {
        return mprpArryRow2;
    }

    public void setMprpArryCol1(ArrayList mprpArryCol1) {
        this.mprpArryCol1 = mprpArryCol1;
    }

    public ArrayList getMprpArryCol1() {
        return mprpArryCol1;
    }

    public void setMprpArryCol2(ArrayList mprpArryCol2) {
        this.mprpArryCol2 = mprpArryCol2;
    }

    public ArrayList getMprpArryCol2() {
        return mprpArryCol2;
    }

    public void setSttList(ArrayList sttList) {
        this.sttList = sttList;
    }

    public ArrayList getSttList() {
        return sttList;
    }
}
