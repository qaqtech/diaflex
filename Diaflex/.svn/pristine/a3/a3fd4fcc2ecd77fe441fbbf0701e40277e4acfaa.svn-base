package ft.com.contact;

//~--- non-JDK imports --------------------------------------------------------

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.DynaActionForm;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddressFrm extends ActionForm {
    public final Map  values = new HashMap();
    private String    addnew;
    private ArrayList list;
    private String    modify;
    private String    nmeIdn, idn;
    private String    search;
    private String    view;
    private String    reset;
    public AddressFrm() {
        super();
    }

    public void SOP(String s) {
        System.out.println("@AddressFrm : " + s);
    }

    public void setValue(String key, Object value) {
        SOP(" Key : " + key + " | Value : " + (String) value);
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
        addnew = null;
        modify = null;
        search = null;
        view   = null;
        idn    = null;
    }

    public void setAddnew(String addnew) {
        SOP(" Addnew : " + addnew);
        this.addnew = addnew;
    }

    public String getAddnew() {
        return addnew;
    }

    public void setModify(String modify) {
        SOP(" Modify : " + modify);
        this.modify = modify;
    }

    public String getModify() {
        return modify;
    }

    public void setNmeIdn(String nmeIdn) {
        this.nmeIdn = nmeIdn;
    }

    public String getNmeIdn() {
        return nmeIdn;
    }

    public void setList(ArrayList list) {
        this.list = list;
    }

    public ArrayList getList() {
        return list;
    }

    public void setSearch(String search) {
        SOP(" Search : " + search);
        this.search = search;
    }

    public String getSearch() {
        return search;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getView() {
        return view;
    }

    public void setIdn(String idn) {
        this.idn = idn;
    }

    public String getIdn() {
        return idn;
    }

    public void setReset(String reset) {
        this.reset = reset;
    }

    public String getReset() {
        return reset;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
