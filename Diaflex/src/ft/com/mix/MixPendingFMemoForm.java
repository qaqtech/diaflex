package ft.com.mix;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class MixPendingFMemoForm  extends ActionForm{
  String     method = "";
  private String terms=null;
  ArrayList  ppPktList = null;
  private ArrayList trmsLst=null;
  private ArrayList typeLst=null;
  private String type = null;
  private final Map values  = new HashMap(); 
    private String pendingNme = "";
  public MixPendingFMemoForm() {
    super();
  }
  
  public void reset() {
      values.clear();
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

  public void setMethod(String method) {
      this.method = method;
  }

  public String getMethod() {
      return method;
  }

  public void setPpPktList(ArrayList ppPktList) {
      this.ppPktList = ppPktList;
  }

  public ArrayList getPpPktList() {
      return ppPktList;
  }

    public void setPendingNme(String pendingNme) {
        this.pendingNme = pendingNme;
    }

    public String getPendingNme() {
        return pendingNme;
    }

    public void setTrmsLst(ArrayList trmsLst) {
        this.trmsLst = trmsLst;
    }

    public ArrayList getTrmsLst() {
        return trmsLst;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getTerms() {
        return terms;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setTypeLst(ArrayList typeLst) {
        this.typeLst = typeLst;
    }

    public ArrayList getTypeLst() {
        return typeLst;
    }
}
