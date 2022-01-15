package ft.com.masters;
//~--- non-JDK imports --------------------------------------------------------

import ft.com.contact.SearchContactFrm;

import org.apache.struts.action.ActionForm;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.upload.FormFile;

public class ByrSetupForm extends ActionForm {
    String           byrId     = "";
    String           byrRln    = "";
    String           method    = "";
    ArrayList        byrList   = new ArrayList();
    String           party     = "";
    ArrayList        partyList = new ArrayList();
    ArrayList        trmList   = new ArrayList();
    public final Map values    = new HashMap();
    
    
    public ByrSetupForm() {
        super();
        values.clear();
    }

    public void reset() {
        System.out.println(new Date() + " @reset : " + values.size());
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

    public void setByrList(ArrayList byrList) {
        this.byrList = byrList;
    }

    public ArrayList getByrList() {
        return byrList;
    }

    public void setByrRln(String byrRln) {
        this.byrRln = byrRln;
    }

    public String getByrRln() {
        return byrRln;
    }

    public void setByrId(String byrId) {
        this.byrId = byrId;
    }

    public String getByrId() {
        return byrId;
    }

    public void setTrmList(ArrayList trmList) {
        this.trmList = trmList;
    }

    public ArrayList getTrmList() {
        return trmList;
    }
    public void setParty(String party) {
        this.party = party;
    }

    public String getParty() {
        return party;
    }

    public void setPartyList(ArrayList partyList) {
        this.partyList = partyList;
    }

    public ArrayList getPartyList() {
        return partyList;
    }

    public String toString() {
        return values.toString();
    }
}


//~ Formatted by Jindent --- http://www.jindent.com

