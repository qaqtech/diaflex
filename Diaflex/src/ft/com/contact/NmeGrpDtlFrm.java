package ft.com.contact;

//~--- non-JDK imports --------------------------------------------------------

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.DynaActionForm;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.Map;

public class NmeGrpDtlFrm extends ActionForm {
    public final Map values = new HashMap();
    private String   addnew;
    private String   delIdn;
    private String   formName;
    private String   grpIdn;
    private String   idn;
    private String   modify;
    private String   nmeGrpIdn;
    private String   nmeIdn;

    public NmeGrpDtlFrm() {
        super();
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

    public void reset() {
        values.clear();
        addnew   = null;
        modify   = null;
        formName = null;
        idn      = null;
        delIdn   = null;
    }

    public void setAddnew(String addnew) {
        this.addnew = addnew;
    }

    public String getAddnew() {
        return addnew;
    }

    public void setModify(String modify) {
        this.modify = modify;
    }

    public String getModify() {
        return modify;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFormName() {
        return formName;
    }

    public void setDelIdn(String delIdn) {
        this.delIdn = delIdn;
    }

    public String getDelIdn() {
        return delIdn;
    }

    public void setNmeGrpIdn(String nmeGrpIdn) {
        this.nmeGrpIdn = nmeGrpIdn;
    }

    public String getNmeGrpIdn() {
        return nmeGrpIdn;
    }

    public void setIdn(String idn) {
        this.idn = idn;
    }

    public String getIdn() {
        return idn;
    }

    public void setNmeIdn(String nmeIdn) {
        this.nmeIdn = nmeIdn;
    }

    public String getNmeIdn() {
        return nmeIdn;
    }

    public void setGrpIdn(String grpIdn) {
        this.grpIdn = grpIdn;
    }

    public String getGrpIdn() {
        return grpIdn;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
