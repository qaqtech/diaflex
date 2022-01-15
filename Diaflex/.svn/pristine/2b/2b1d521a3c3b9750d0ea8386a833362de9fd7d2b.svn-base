package ft.com.marketing;

//~--- non-JDK imports --------------------------------------------------------

import ft.com.contact.SearchContactFrm;

import org.apache.struts.action.ActionForm;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.upload.FormFile;

public class SearchForm extends ActionForm {
    String           byrId     = "";
    String           byrRln    = "";
    String           isEx      = "1";
    String           method    = "";
    ArrayList        byrList   = new ArrayList();
    String           multi     = "";
    String           party     = "";
    ArrayList        partyList = new ArrayList();
    String           srchTyp   = "";
    ArrayList        trmList   = new ArrayList();
    FormFile         fileUpload = null;
    ArrayList        dmdList   = new ArrayList();
    public final Map values    = new HashMap();
    ArrayList        ExpDayList = new ArrayList();
    ArrayList        memoList = new ArrayList();
    ArrayList ByrCbList = new ArrayList();
    ArrayList srchTypList = new ArrayList();
    
    public SearchForm() {
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

    public void setMulti(String multi) {
        this.multi = multi;
    }

    public String getMulti() {
        return multi;
    }

    public void setTrmList(ArrayList trmList) {
        this.trmList = trmList;
    }

    public ArrayList getTrmList() {
        return trmList;
    }

    public void setSrchTyp(String srchTyp) {
        this.srchTyp = srchTyp;
    }

    public String getSrchTyp() {
        return srchTyp;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getParty() {
        return party;
    }

    public void setIsEx(String isEx) {
        this.isEx = isEx;
    }

    public String getIsEx() {
        return isEx;
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

    public void setFileUpload(FormFile fileUpload) {
        this.fileUpload = fileUpload;
    }

    public FormFile getFileUpload() {
        return fileUpload;
    }

    public void setDmdList(ArrayList dmdList) {
        this.dmdList = dmdList;
    }

    public ArrayList getDmdList() {
        return dmdList;
    }

    public void setExpDayList(ArrayList ExpDayList) {
        this.ExpDayList = ExpDayList;
    }

    public ArrayList getExpDayList() {
        return ExpDayList;
    }

    public void setByrCbList(ArrayList ByrCbList) {
        this.ByrCbList = ByrCbList;
    }

    public ArrayList getByrCbList() {
        return ByrCbList;
    }

    public void setMemoList(ArrayList memoList) {
        this.memoList = memoList;
    }

    public ArrayList getMemoList() {
        return memoList;
    }

    public void setSrchTypList(ArrayList srchTypList) {
        this.srchTypList = srchTypList;
    }

    public ArrayList getSrchTypList() {
        return srchTypList;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
