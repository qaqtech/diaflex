package ft.com.pricing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts.action.ActionForm;

public class PriceCalcFrm extends ActionForm{
    public PriceCalcFrm() {
        super();
    }
  public final Map values = new HashMap();
  private List PRPL = null;
  private String parameter = " ";
  private String[] srtredio;
  private String submit = "", gen ="";
  
  public String getParameter() {
      return parameter;
  }
  public void setParameter(String parameter){
      this.parameter = parameter;
  }
  public void setValue(String key, Object value) {
      System.out.println(key + ":" + (String)value);
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

  public void setSrtredio(String[] srtredio) {
      this.srtredio = srtredio;
  }

  public String[] getSrtredio() {
      return srtredio;
  }

    public void setSubmit(String submit) {
        this.submit = submit;
    }

    public String getSubmit() {
        return submit;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public String getGen() {
        return gen;
    }
}
