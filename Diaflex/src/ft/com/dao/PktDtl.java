package ft.com.dao;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;

public class PktDtl {
    private HashMap values = new HashMap();
    private String  cts, rte, rapRte, dis,prp, byrRte, byrDis, fnlRte, memoQuot, qty , vnm , inc_day , inc_vlu;
    private long    pktIdn, sk1,dteNum;
    private String  stt, memoId ,saleId,typ,Idn,dlvId;

    public PktDtl() {
        super();
    }

    public void setValue(String key, String value) {
        values.put(key, value);
    }

    public String getValue(String key) {
        return (String) values.get(key);
    }

    public void setPktIdn(long pktIdn) {
        this.pktIdn = pktIdn;
    }

    public long getPktIdn() {
        return pktIdn;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getStt() {
        return stt;
    }

    public void setValues(HashMap values) {
        this.values = values;
    }

    public HashMap getValues() {
        return values;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public String getDis() {
        return dis;
    }

    public void setByrRte(String byrRte) {
        this.byrRte = byrRte;
    }

    public String getByrRte() {
        return byrRte;
    }

    public void setByrDis(String byrDis) {
        this.byrDis = byrDis;
    }

    public String getByrDis() {
        return byrDis;
    }

    public void setFnlRte(String fnlRte) {
        this.fnlRte = fnlRte;
    }

    public String getFnlRte() {
        return fnlRte;
    }

    public void setMemoQuot(String memoQuot) {
        this.memoQuot = memoQuot;
    }

    public String getMemoQuot() {
        return memoQuot;
    }

    public void setCts(String cts) {
        this.cts = cts;
    }

    public String getCts() {
        return cts;
    }

    public void setRte(String rte) {
        this.rte = rte;
    }

    public String getRte() {
        return rte;
    }

    public void setRapRte(String rapRte) {
        this.rapRte = rapRte;
    }

    public String getRapRte() {
        return rapRte;
    }

    public void setSk1(long sk1) {
        this.sk1 = sk1;
    }

    public long getSk1() {
        return sk1;
    }

    public void setMemoId(String memoId) {
        this.memoId = memoId;
    }

    public String getMemoId() {
        return memoId;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getQty() {
        return qty;
    }

    public void setVnm(String vnm) {
        this.vnm = vnm;
    }

    public String getVnm() {
        return vnm;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public String getSaleId() {
        return saleId;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getTyp() {
        return typ;
    }

    public void setInc_day(String inc_day) {
        this.inc_day = inc_day;
    }

    public String getInc_day() {
        return inc_day;
    }

    public void setInc_vlu(String inc_vlu) {
        this.inc_vlu = inc_vlu;
    }

    public String getInc_vlu() {
        return inc_vlu;
    }

    public void setPrp(String prp) {
        this.prp = prp;
    }

    public String getPrp() {
        return prp;
    }

    public void setIdn(String Idn) {
        this.Idn = Idn;
    }

    public String getIdn() {
        return Idn;
    }

    public void setDteNum(long dteNum) {
        this.dteNum = dteNum;
    }

    public long getDteNum() {
        return dteNum;
    }

    public void setDlvId(String dlvId) {
        this.dlvId = dlvId;
    }

    public String getDlvId() {
        return dlvId;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
