package ft.com.mixakt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.struts.action.ActionForm;

public class MixDeliveryUpdateForm extends ActionForm
{
  private final Map values = new HashMap();
  private String method;
  private int nmeIdn; private int relIdn; private int memoIdn = 0;
  private int invTrmsIdn;
  private int invByrIdn; private int invAddrIdn; private ArrayList trmsLst = new ArrayList();
  private ArrayList byrLst = new ArrayList();
  private String byrIdn;
  private String prtyIdn;
  private ArrayList pkts;
  
  public MixDeliveryUpdateForm() {}
  
  public void reset() {
    values.clear();
  }
  
  public void resetAll() {
    values.clear();
    trmsLst = new ArrayList();
    byrLst = new ArrayList();
    nmeIdn = 0;
    relIdn = 0;
    byrIdn = "";
    prtyIdn = "";
  }
  
  public void setMethod(String method) { this.method = method; }
  
  public String getMethod()
  {
    return method;
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
  
  public void setMemoIdn(int memoIdn) {
    this.memoIdn = memoIdn;
  }
  
  public int getMemoIdn() {
    return memoIdn;
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
  
  public void setTrmsLst(ArrayList trmsLst)
  {
    this.trmsLst = trmsLst;
  }
  
  public ArrayList getTrmsLst() {
    return trmsLst;
  }
  
  public void setByrLst(ArrayList byrLst) {
    this.byrLst = byrLst;
  }
  

  public ArrayList getByrLst() { return byrLst; }
  
  private String submit;
  private String fullReturn;
  private String fullApprove;
  private String terms;
  public void setByrIdn(String byrIdn) { this.byrIdn = byrIdn; }
  

  public String getByrIdn() {
    return byrIdn;
  }
  
  public void setPrtyIdn(String prtyIdn) {
    this.prtyIdn = prtyIdn;
  }
  
  public String getPrtyIdn() {
    return prtyIdn;
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
}
 