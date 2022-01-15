package ft.com.contact;

//~--- non-JDK imports --------------------------------------------------------

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NmeDocFrm extends ActionForm {
    public final Map  values  = new HashMap();
    private ArrayList editLst = new ArrayList();
    private String    addnew;
    private String    docType;
    private String    doc_nme;
    private String    modify;
    private String    nmeIdn;

    public NmeDocFrm() {
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

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocType() {
        return docType;
    }

    public void setDoc_nme(String doc_nme) {
        this.doc_nme = doc_nme;
    }

    public String getDoc_nme() {
        return doc_nme;
    }

    public void setNmeIdn(String nmeIdn) {
        this.nmeIdn = nmeIdn;
    }

    public String getNmeIdn() {
        return nmeIdn;
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

    public void setEditLst(ArrayList editLst) {
        this.editLst = editLst;
    }

    public ArrayList getEditLst() {
        return editLst;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
