package ft.com.marketing;

//~--- non-JDK imports --------------------------------------------------------

import org.apache.struts.action.ActionForm;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MemoSaleFrm extends ActionForm {
    private int       nmeIdn, relIdn;
    private String    memoIdn = "";
    private String    view    = "NORMAL", byr, qty, cts, vlu, typ, dte , avgPrc ,avgDis;
    private final Map values  = new HashMap();
    private ArrayList trmsLst = new ArrayList();
    private ArrayList daytrmsLst = new ArrayList();
    private ArrayList byrLst  = new ArrayList();
    private ArrayList empLst  = new ArrayList();
    private ArrayList byrLstFetch  = new ArrayList();
    private ArrayList addrList = new ArrayList();
    private ArrayList bankList = new ArrayList();
    private ArrayList groupList = new ArrayList();
    private ArrayList bnkAddrList = new ArrayList();
    private ArrayList courierList = new ArrayList();
    private ArrayList thruBankList= new ArrayList();
    private String    byrIdn, byrTrm;
    private ArrayList pkts;
    private String    submit, fullReturn, fullApprove, terms;

    public MemoSaleFrm() {
        super();
        pkts = new ArrayList();
    }

    public void reset() {
        values.clear();
    }

    public void setValue(String key, Object value) {
        values.put(key, value);
    }

    public Object getValue(String key) {
        return values.get(key);
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getView() {
        return view;
    }

    public void setSubmit(String submit) {
        this.submit = submit;
    }

    public String getSubmit() {
        return submit;
    }

    public void setPkts(ArrayList pkts) {
        this.pkts = pkts;
    }

    public ArrayList getPkts() {
        return pkts;
    }

    public void setByr(String byr) {
        this.byr = byr;
    }

    public String getByr() {
        return byr;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getQty() {
        return qty;
    }

    public void setNmeIdn(int nmeIdn) {
        this.nmeIdn = nmeIdn;
    }

    public int getNmeIdn() {
        return nmeIdn;
    }

    public void setRelIdn(int relIdn) {
        this.relIdn = relIdn;
    }

    public int getRelIdn() {
        return relIdn;
    }

    public void setCts(String cts) {
        this.cts = cts;
    }

    public String getCts() {
        return cts;
    }

    public void setVlu(String vlu) {
        this.vlu = vlu;
    }

    public String getVlu() {
        return vlu;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getTyp() {
        return typ;
    }

    public void setDte(String dte) {
        this.dte = dte;
    }

    public String getDte() {
        return dte;
    }

    public void setFullReturn(String fullReturn) {
        this.fullReturn = fullReturn;
    }

    public String getFullReturn() {
        return fullReturn;
    }

    public void setFullApprove(String fullApprove) {
        this.fullApprove = fullApprove;
    }

    public String getFullApprove() {
        return fullApprove;
    }

    public void setTrmsLst(ArrayList trmsLst) {
        this.trmsLst = trmsLst;
    }

    public ArrayList getTrmsLst() {
        return trmsLst;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getTerms() {
        return terms;
    }

    public Map getValues() {
        return values;
    }

    public void setMemoIdn(String memoIdn) {
        this.memoIdn = memoIdn;
    }

    public String getMemoIdn() {
        return memoIdn;
    }

    public void setByrIdn(String byrIdn) {
        this.byrIdn = byrIdn;
    }

    public String getByrIdn() {
        return byrIdn;
    }

    public void setByrTrm(String byrTrm) {
        this.byrTrm = byrTrm;
    }

    public String getByrTrm() {
        return byrTrm;
    }

    public void setByrLst(ArrayList byrLst) {
        this.byrLst = byrLst;
    }

    public ArrayList getByrLst() {
        return byrLst;
    }

    public void setAddrList(ArrayList addrList) {
        this.addrList = addrList;
    }

    public ArrayList getAddrList() {
        return addrList;
    }

    public void setAvgPrc(String avgPrc) {
        this.avgPrc = avgPrc;
    }

    public String getAvgPrc() {
        return avgPrc;
    }

    public void setAvgDis(String avgDis) {
        this.avgDis = avgDis;
    }

    public String getAvgDis() {
        return avgDis;
    }

    public void setBankList(ArrayList bankList) {
        this.bankList = bankList;
    }

    public ArrayList getBankList() {
        return bankList;
    }

    public void setGroupList(ArrayList groupList) {
        this.groupList = groupList;
    }

    public ArrayList getGroupList() {
        return groupList;
    }

    public void setBnkAddrList(ArrayList bnkAddrList) {
        this.bnkAddrList = bnkAddrList;
    }

    public ArrayList getBnkAddrList() {
        return bnkAddrList;
    }

    public void setCourierList(ArrayList courierList) {
        this.courierList = courierList;
    }

    public ArrayList getCourierList() {
        return courierList;
    }

    public void setThruBankList(ArrayList thruBankList) {
        this.thruBankList = thruBankList;
    }

    public ArrayList getThruBankList() {
        return thruBankList;
    }

    public void setByrLstFetch(ArrayList byrLstFetch) {
        this.byrLstFetch = byrLstFetch;
    }

    public ArrayList getByrLstFetch() {
        return byrLstFetch;
    }

    public void setDaytrmsLst(ArrayList daytrmsLst) {
        this.daytrmsLst = daytrmsLst;
    }

    public ArrayList getDaytrmsLst() {
        return daytrmsLst;
    }

    public void setEmpLst(ArrayList empLst) {
        this.empLst = empLst;
    }

    public ArrayList getEmpLst() {
        return empLst;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
