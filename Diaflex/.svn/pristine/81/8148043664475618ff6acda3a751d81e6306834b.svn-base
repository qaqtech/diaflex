  package ft.com.inward;

import java.util.ArrayList;
import java.util.HashMap;
  import java.util.Map;

  import org.apache.struts.action.ActionForm;

  public class AssortLabReportForm extends ActionForm {
      private String method="";
      private final Map values  = new HashMap();
      ArrayList  issuePrintList = new ArrayList();
      public void setValue(String key, Object value) {
          values.put(key, value);
      }

      public Object getValue(String key) {
          return values.get(key);
      }
      public Map getValues() {
          return values;
      }

      public AssortLabReportForm() {
          super();
      }

      public void setMethod(String method) {
          this.method = method;
      }

      public String getMethod() {
          return method;
      }

    public void setIssuePrintList(ArrayList issuePrintList) {
        this.issuePrintList = issuePrintList;
    }

    public ArrayList getIssuePrintList() {
        return issuePrintList;
    }
}
