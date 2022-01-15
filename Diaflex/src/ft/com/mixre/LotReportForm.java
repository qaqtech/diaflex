package ft.com.mixre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class LotReportForm extends ActionForm {
    public LotReportForm() {
        super();
    }
    String            method    = "";
    ArrayList         memoList = null;
    private ArrayList byrList = new ArrayList();
    private final Map values  = new HashMap(); 
    private ArrayList pktList = null;
    private String view ="";
    private String    byrIdn;
    private String reportNme = "";
    private String[] deptLst;
    private ArrayList deptList = null;
    private String[] empLst;
    private ArrayList empList = null;
    private String[] yrLst;
    private ArrayList yrList = null;
    private ArrayList monthList = null;
    private String[] PInvDocIdLst = null;
    private String[] lotLst = null;
    private ArrayList quarterList = null;

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
    public void reset() {
        values.clear();
        empLst=new String[]{""};
    }
    public void setMemoList(ArrayList memoList) {
        this.memoList = memoList;
    }
    public void setValue(String key, Object value) {
        System.out.println("@reportParam : "+ key + " : "+value );
        values.put(key, value);
    }

    public Object getValue(String key) {
        return values.get(key);
    }
    public ArrayList getMemoList() {
        return memoList;
    }

    public void setByrList(ArrayList byrList) {
        this.byrList = byrList;
    }

    public ArrayList getByrList() {
        return byrList;
    }

    public void setPktList(ArrayList pktList) {
        this.pktList = pktList;
    }

    public ArrayList getPktList() {
        return pktList;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getView() {
        return view;
    }

    public void setByrIdn(String byrIdn) {
        this.byrIdn = byrIdn;
    }

    public String getByrIdn() {
        return byrIdn;
    }

    public void setReportNme(String reportNme) {
        this.reportNme = reportNme;
    }

    public String getReportNme() {
        return reportNme;
    }

    public void setDeptLst(String[] deptLst) {
        this.deptLst = deptLst;
    }

    public String[] getDeptLst() {
        return deptLst;
    }

    public void setDeptList(ArrayList deptList) {
        this.deptList = deptList;
    }

    public ArrayList getDeptList() {
        return deptList;
    }

    public void setEmpLst(String[] empLst) {
        this.empLst = empLst;
    }

    public String[] getEmpLst() {
        return empLst;
    }

    public void setEmpList(ArrayList empList) {
        this.empList = empList;
    }

    public ArrayList getEmpList() {
        return empList;
    }

    public void setYrLst(String[] yrLst) {
        this.yrLst = yrLst;
    }

    public String[] getYrLst() {
        return yrLst;
    }

    public void setYrList(ArrayList yrList) {
        this.yrList = yrList;
    }

    public ArrayList getYrList() {
        return yrList;
    }

    public void setMonthList(ArrayList monthList) {
        this.monthList = monthList;
    }

    public ArrayList getMonthList() {
        return monthList;
    }

    public void setPInvDocIdLst(String[] PInvDocIdLst) {
        this.PInvDocIdLst = PInvDocIdLst;
    }

    public String[] getPInvDocIdLst() {
        return PInvDocIdLst;
    }

    public void setLotLst(String[] lotLst) {
        this.lotLst = lotLst;
    }

    public String[] getLotLst() {
        return lotLst;
    }

    public void setQuarterList(ArrayList quarterList) {
        this.quarterList = quarterList;
    }

    public ArrayList getQuarterList() {
        return quarterList;
    }
}
