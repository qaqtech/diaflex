package ft.com.boxsje;

import ft.com.box.BoxCriteriaForm;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class BoxToBoxForm extends ActionForm{
  String           method    = "";
  public final Map values    = new HashMap();
  public ArrayList boxTypLst = new ArrayList();
  
  public void reset() {
//      System.out.println(new Date() + " @reset : " + values.size());
      values.clear();
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

  public BoxToBoxForm() {
      super();
  }


    public void setBoxTypLst(ArrayList boxTypLst) {
        this.boxTypLst = boxTypLst;
    }

    public ArrayList getBoxTypLst() {
        return boxTypLst;
    }
}


