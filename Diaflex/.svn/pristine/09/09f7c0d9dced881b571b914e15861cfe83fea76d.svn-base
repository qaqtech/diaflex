package ft.com.pricing;

import java.util.HashMap;
import java.util.List;

import java.util.Map;

import org.apache.struts.action.ActionForm;

public class RepriceFrm extends ActionForm {
    public RepriceFrm() {
        super();
    }
  public final Map values = new HashMap();
  private List PRPL = null;
  private String parameter = "" , vnm="";
  private String submit = "", repriceAll ="", memoIdn="" , view="", seq="" , getstatus="";
  private int memoId = 0;
  
  public String getParameter() {
      return parameter;
  }
  public void setParameter(String parameter){
      this.parameter = parameter;
  }
  public void setValue(String key, Object value) {
      //System.out.println(key + ":" + (String)value);
      values.put(key, value);
  }

  public Object getValue(String key) {
      return values.get(key);
  }

  public void reset() {
      values.clear();
  }
  
  public void setPRPL(List PRPL) {
      this.PRPL = PRPL;
  }

  public List getPRPL() {
      return PRPL;
  }

    public void setSubmit(String submit) {
        this.submit = submit;
    }

    public String getSubmit() {
        return submit;
    }

    public void setRepriceAll(String repriceAll) {
        this.repriceAll = repriceAll;
    }

    public String getRepriceAll() {
        return repriceAll;
    }

    public void setMemoId(int memoId) {
        this.memoId = memoId;
    }

    public int getMemoId() {
        return memoId;
    }

    public void setMemoIdn(String memoIdn) {
        this.memoIdn = memoIdn;
    }

    public String getMemoIdn() {
        return memoIdn;
    }

    public Map getValues() {
        return values;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getView() {
        return view;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getSeq() {
        return seq;
    }

    public void setGetstatus(String getstatus) {
        this.getstatus = getstatus;
    }

    public String getGetstatus() {
        return getstatus;
    }

    public void setVnm(String vnm) {
        this.vnm = vnm;
    }

    public String getVnm() {
        return vnm;
    }
}

