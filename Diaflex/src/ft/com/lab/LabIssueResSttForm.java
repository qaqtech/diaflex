
package ft.com.lab;

import java.util.HashMap;
import java.util.Map;
import org.apache.struts.action.ActionForm;

public class LabIssueResSttForm extends ActionForm{
   public final Map values     = new HashMap();
   private String method="";
   private String view="";
   public void reset() {
       values.clear();
     
   }
   public void setValue(String key, Object value) {
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

   public LabIssueResSttForm() {
       super();
   }
}