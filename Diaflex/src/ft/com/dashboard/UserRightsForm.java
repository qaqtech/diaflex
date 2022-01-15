package ft.com.dashboard;
import ft.com.website.BulkRoleForm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class UserRightsForm extends ActionForm {
    public UserRightsForm() {
        super();
    }
    private String method="";
    private final Map values  = new HashMap();
    private ArrayList pktList= new ArrayList();
    private ArrayList pktIList= new ArrayList();
    private ArrayList pktCSList= new ArrayList();
    private ArrayList pktEList= new ArrayList();
    private ArrayList pktCRList= new ArrayList();
    private ArrayList pktWHList= new ArrayList();
    private ArrayList pktWAPList= new ArrayList();
    private ArrayList pktEAPList= new ArrayList();
    private ArrayList pktIAPList= new ArrayList();
    private ArrayList pktDOBList= new ArrayList();
    private ArrayList pktADTEList= new ArrayList();
    private String reportNme = "";
    private String   addnew;
    private String   modify;
    private String   delete;
    private String[] fmtLst;
    public void setValue(String key, Object value) {
        values.put(key, value);
    }
    public void reset() {
        values.clear();
        fmtLst=new String[]{};
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

    public void setPktList(ArrayList pktList) {
        this.pktList = pktList;
    }

    public ArrayList getPktList() {
        return pktList;
    }

    public void setReportNme(String reportNme) {
        this.reportNme = reportNme;
    }

    public String getReportNme() {
        return reportNme;
    }

    public void setPktIList(ArrayList pktIList) {
        this.pktIList = pktIList;
    }

    public ArrayList getPktIList() {
        return pktIList;
    }

    public void setPktEList(ArrayList pktEList) {
        this.pktEList = pktEList;
    }

    public ArrayList getPktEList() {
        return pktEList;
    }

    public void setPktWHList(ArrayList pktWHList) {
        this.pktWHList = pktWHList;
    }

    public ArrayList getPktWHList() {
        return pktWHList;
    }

    public void setPktWAPList(ArrayList pktWAPList) {
        this.pktWAPList = pktWAPList;
    }

    public ArrayList getPktWAPList() {
        return pktWAPList;
    }

    public void setPktEAPList(ArrayList pktEAPList) {
        this.pktEAPList = pktEAPList;
    }

    public ArrayList getPktEAPList() {
        return pktEAPList;
    }

    public void setPktIAPList(ArrayList pktIAPList) {
        this.pktIAPList = pktIAPList;
    }

    public ArrayList getPktIAPList() {
        return pktIAPList;
    }

    public void setPktCRList(ArrayList pktCRList) {
        this.pktCRList = pktCRList;
    }

    public ArrayList getPktCRList() {
        return pktCRList;
    }

    public void setPktDOBList(ArrayList pktDOBList) {
        this.pktDOBList = pktDOBList;
    }

    public ArrayList getPktDOBList() {
        return pktDOBList;
    }

    public void setPktADTEList(ArrayList pktADTEList) {
        this.pktADTEList = pktADTEList;
    }

    public ArrayList getPktADTEList() {
        return pktADTEList;
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

    public void setPktCSList(ArrayList pktCSList) {
        this.pktCSList = pktCSList;
    }

    public ArrayList getPktCSList() {
        return pktCSList;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

    public String getDelete() {
        return delete;
    }

    public void setFmtLst(String[] fmtLst) {
        this.fmtLst = fmtLst;
    }

    public String[] getFmtLst() {
        return fmtLst;
    }
}
