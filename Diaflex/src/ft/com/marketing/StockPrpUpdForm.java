package ft.com.marketing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class StockPrpUpdForm extends ActionForm {
    private int       nmeIdn, relIdn,oldMemoIdn=0,
                      memoIdn = 0;
    private String    view    = "NORMAL", byr, qty, cts, vlu, typ, dte,stt,msg;
    private final Map values  = new HashMap();
    private ArrayList trmsLst = new ArrayList();
    private ArrayList pkts;
    private String    submit, fullReturn, fullApprove, terms;

    public StockPrpUpdForm() {
        super();
    }
    public void setValue(String key, Object value) {
       

        values.put(key, value);
    }
    public void reset() {
        values.clear();
    }
    public Object getValue(String key) {
        return values.get(key);
    }

    public Map getValues() {
        return values;
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

    public void setOldMemoIdn(int oldMemoIdn) {
        this.oldMemoIdn = oldMemoIdn;
    }

    public int getOldMemoIdn() {
        return oldMemoIdn;
    }

    public void setMemoIdn(int memoIdn) {
        this.memoIdn = memoIdn;
    }

    public int getMemoIdn() {
        return memoIdn;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getView() {
        return view;
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

   

    public void setTrmsLst(ArrayList trmsLst) {
        this.trmsLst = trmsLst;
    }

    public ArrayList getTrmsLst() {
        return trmsLst;
    }

    public void setPkts(ArrayList pkts) {
        this.pkts = pkts;
    }

    public ArrayList getPkts() {
        return pkts;
    }

    public void setSubmit(String submit) {
        this.submit = submit;
    }

    public String getSubmit() {
        return submit;
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

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getTerms() {
        return terms;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getStt() {
        return stt;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
