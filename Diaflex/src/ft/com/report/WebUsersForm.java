package ft.com.report;

//~--- non-JDK imports --------------------------------------------------------

import org.apache.struts.action.ActionForm;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.HashMap;

public class WebUsersForm extends ActionForm {
    String            method    = "";
    private ArrayList vwListMap = null;

    public WebUsersForm() {
        super();
    }

  

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setVwListMap(ArrayList vwListMap) {
        this.vwListMap = vwListMap;
    }

    public ArrayList getVwListMap() {
        return vwListMap;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
