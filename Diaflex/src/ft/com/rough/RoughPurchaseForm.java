package ft.com.rough;

import ft.com.mpur.PurchaseForm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class RoughPurchaseForm extends ActionForm {
    public final Map  values = new HashMap();
    private String    addnew;
    private ArrayList list;
    private String    modify;
    private String    nmeIdn, idn;
    private String    search,reset;
    private String    view;
    private ArrayList roleList;
    
    
    public RoughPurchaseForm() {
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
        addnew = null;
        modify = null;
        search = null;
        view   = null;
        idn    = null;
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

    public void setRoleList(ArrayList roleList) {
        this.roleList = roleList;
    }

    public ArrayList getRoleList() {
        return roleList;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com

   


   

