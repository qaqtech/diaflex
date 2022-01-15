package ft.com.marketing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class TransferToMixForm extends ActionForm {
    private int       nmeIdn, relIdn,
                      memoIdn = 0 , invTrmsIdn , invByrIdn , invAddrIdn;
    private String    view    = "NORMAL", byr, qty, cts, vlu, typ, dte,method;
    private final Map values  = new HashMap();
    private ArrayList trmsLst = new ArrayList();
    private ArrayList mxPktList = new ArrayList();
    private ArrayList byrLst  = new ArrayList();
    private ArrayList invTrmsLst = new ArrayList();
    private ArrayList invByrLst  = new ArrayList();
    private ArrayList invAddLst  = new ArrayList();
    private ArrayList bankList  = new ArrayList();
    private String    byrIdn, prtyIdn;
    private ArrayList pkts;
    private String    submit, fullReturn, fullApprove, terms;
   
    public TransferToMixForm() {
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

    public void setMemoIdn(int memoIdn) {
        this.memoIdn = memoIdn;
    }

    public int getMemoIdn() {
        return memoIdn;
    }

    public void setByrIdn(String byrIdn) {
        this.byrIdn = byrIdn;
    }

    public String getByrIdn() {
        return byrIdn;
    }

   

    public void setByrLst(ArrayList byrLst) {
        this.byrLst = byrLst;
    }

    public ArrayList getByrLst() {
        return byrLst;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setPrtyIdn(String prtyIdn) {
        this.prtyIdn = prtyIdn;
    }

    public String getPrtyIdn() {
        return prtyIdn;
    }

    public void setInvTrmsLst(ArrayList invTrmsLst) {
        this.invTrmsLst = invTrmsLst;
    }

    public ArrayList getInvTrmsLst() {
        return invTrmsLst;
    }

    public void setInvByrLst(ArrayList invByrLst) {
        this.invByrLst = invByrLst;
    }

    public ArrayList getInvByrLst() {
        return invByrLst;
    }

    public void setInvAddLst(ArrayList invAddLst) {
        this.invAddLst = invAddLst;
    }

    public ArrayList getInvAddLst() {
        return invAddLst;
    }

    public void setInvTrmsIdn(int invTrmsIdn) {
        this.invTrmsIdn = invTrmsIdn;
    }

    public int getInvTrmsIdn() {
        return invTrmsIdn;
    }

    public void setInvByrIdn(int invByrIdn) {
        this.invByrIdn = invByrIdn;
    }

    public int getInvByrIdn() {
        return invByrIdn;
    }

    public void setInvAddrIdn(int invAddrIdn) {
        this.invAddrIdn = invAddrIdn;
    }

    public int getInvAddrIdn() {
        return invAddrIdn;
    }

    public void setBankList(ArrayList bankList) {
        this.bankList = bankList;
    }

    public ArrayList getBankList() {
        return bankList;
    }

    public void setMxPktList(ArrayList mxPktList) {
        this.mxPktList = mxPktList;
    }

    public ArrayList getMxPktList() {
        return mxPktList;
    }
}


