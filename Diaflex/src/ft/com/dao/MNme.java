package ft.com.dao;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.Map;

public class MNme {
    String      pfx, fnme, mnme, lnme, sfx, alias, grp_nme_idn, grp_nme, gndr, dob, anni_dte, typ, emp_idn, emp_nme,
                idn;
    private Map values;

    public MNme() {
        super();
        values = new HashMap();
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

    public void setPfx(String pfx) {
        this.pfx = pfx;
    }

    public String getPfx() {
        return pfx;
    }

    public void setFnme(String fnme) {
        this.fnme = fnme;
    }

    public String getFnme() {
        return fnme;
    }

    public void setMnme(String mnme) {
        this.mnme = mnme;
    }

    public String getMnme() {
        return mnme;
    }

    public void setLnme(String lnme) {
        this.lnme = lnme;
    }

    public String getLnme() {
        return lnme;
    }

    public void setSfx(String sfx) {
        this.sfx = sfx;
    }

    public String getSfx() {
        return sfx;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public void setGrp_nme_idn(String grp_nme_idn) {
        this.grp_nme_idn = grp_nme_idn;
    }

    public String getGrp_nme_idn() {
        return grp_nme_idn;
    }

    public void setGrp_nme(String grp_nme) {
        this.grp_nme = grp_nme;
    }

    public String getGrp_nme() {
        return grp_nme;
    }

    public void setGndr(String gndr) {
        this.gndr = gndr;
    }

    public String getGndr() {
        return gndr;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDob() {
        return dob;
    }

    public void setAnni_dte(String anni_dte) {
        this.anni_dte = anni_dte;
    }

    public String getAnni_dte() {
        return anni_dte;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getTyp() {
        return typ;
    }

    public void setEmp_idn(String emp_idn) {
        this.emp_idn = emp_idn;
    }

    public String getEmp_idn() {
        return emp_idn;
    }

    public void setEmp_nme(String emp_nme) {
        this.emp_nme = emp_nme;
    }

    public String getEmp_nme() {
        return emp_nme;
    }

    public void setIdn(String idn) {
        this.idn = idn;
    }

    public String getIdn() {
        return idn;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
