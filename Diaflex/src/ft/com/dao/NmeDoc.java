package ft.com.dao;

//~--- JDK imports ------------------------------------------------------------

import java.util.Map;

public class NmeDoc {
    String      nmn_doc_id, doc_typ, doc_nme, doc_lnk, doc_path, doc_con;
    private Map values;

    public NmeDoc() {
        super();
    }

    public void setValue(String key, Object value) {
        if ((key != null) && (value != null)) {
            values.put(key, value);
        }
    }

    public Object getValue(String key) {
        return values.get(key);
    }

    public Map getValues() {
        return values;
    }

    public void setNmn_doc_id(String nmn_doc_id) {
        this.nmn_doc_id = nmn_doc_id;
    }

    public String getNmn_doc_id() {
        return nmn_doc_id;
    }

    public void setDoc_typ(String doc_typ) {
        this.doc_typ = doc_typ;
    }

    public String getDoc_typ() {
        return doc_typ;
    }

    public void setDoc_nme(String doc_nme) {
        this.doc_nme = doc_nme;
    }

    public String getDoc_nme() {
        return doc_nme;
    }

    public void setDoc_lnk(String doc_lnk) {
        this.doc_lnk = doc_lnk;
    }

    public String getDoc_lnk() {
        return doc_lnk;
    }

    public void setDoc_path(String doc_path) {
        this.doc_path = doc_path;
    }

    public String getDoc_path() {
        return doc_path;
    }

    public void setDoc_con(String doc_con) {
        this.doc_con = doc_con;
    }

    public String getDoc_con() {
        return doc_con;
    }

    public void setValues(Map values) {
        this.values = values;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
