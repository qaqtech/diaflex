package ft.com.inward;

//~--- non-JDK imports --------------------------------------------------------

import ft.com.marketing.SearchForm;

import org.apache.struts.action.ActionForm;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IssueResultForm extends ActionForm {
    private String   method  = "";
    ArrayList        mprcLst = new ArrayList();
    ArrayList        nmeLst  = new ArrayList();
    public final Map values  = new HashMap();

    public IssueResultForm() {
        super();
    }

    public void setValue(String key, Object value) {
        System.out.println(key + ":" + (String) value);
        values.put(key, value);
    }

    public Object getValue(String key) {
        return values.get(key);
    }

    public Map getValues() {
        return values;
    }

    public void setMprcLst(ArrayList mprcLst) {
        this.mprcLst = mprcLst;
    }

    public ArrayList getMprcLst() {
        return mprcLst;
    }

    public void setNmeLst(ArrayList nmeLst) {
        this.nmeLst = nmeLst;
    }

    public ArrayList getNmeLst() {
        return nmeLst;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
