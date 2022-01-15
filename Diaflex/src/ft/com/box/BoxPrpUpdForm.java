package ft.com.box;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;


public class BoxPrpUpdForm extends ActionForm {
    private int       nmeIdn, relIdn,oldMemoIdn=0,
                      memoIdn = 0;
    private String    view    = "NORMAL", mdl, lab, upd, bname, btyp, ptyp,flgtyp, qty, cts, bprice, vlu, bshp, msg, boxIdn, mstkidn,  bcrt, bclr, byr, typ, dte, stt, fcpr, tfl3;
    private final Map values  = new HashMap();
    private ArrayList trmsLst = new ArrayList();
    private ArrayList pkts, serviceList;
    private String    submit, fullReturn, fullApprove, terms;
    
    public void setValue(String key, Object value) {
        values.put(key, value);
    }

    public Object getValue(String key) {
        return values.get(key);
    }

    public BoxPrpUpdForm() {
        super();
    }
    
    public Map getValues() {
        return values;
    }
    
    public void reset() {
        values.clear();
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

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getBname() {
        return bname;
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

    public void setBtyp(String btyp) {
        this.btyp = btyp;
    }

    public String getBtyp() {
        return btyp;
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


    public void setBprice(String bprice) {
        this.bprice = bprice;
    }

    public String getBprice() {
        return bprice;
    }

    public void setBshp(String bshp) {
        this.bshp = bshp;
    }

    public String getBshp() {
        return bshp;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setBoxIdn(String boxIdn) {
        this.boxIdn = boxIdn;
    }

    public String getBoxIdn() {
        return boxIdn;
    }

    public void setMstkidn(String mstkidn) {
        this.mstkidn = mstkidn;
    }

    public String getMstkidn() {
        return mstkidn;
    }


    
    public void setBcrt(String bcrt) {
        this.bcrt = bcrt;
    }

    public String getBcrt() {
        return bcrt;
    }

    public void setBclr(String bclr) {
        this.bclr = bclr;
    }

    public String getBclr() {
        return bclr;
    }    

    public void setByr(String byr) {
        this.byr = byr;
    }

    public String getByr() {
        return byr;
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

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getStt() {
        return stt;
    }

    public void setServiceList(ArrayList serviceList) {
        this.serviceList = serviceList;
    }

    public ArrayList getServiceList() {
        return serviceList;
    }

    public void setFcpr(String fcpr) {
        this.fcpr = fcpr;
    }

    public String getFcpr() {
        return fcpr;
    }

    public void setTfl3(String tfl3) {
        this.tfl3 = tfl3;
    }

    public String getTfl3() {
        return tfl3;
    }

    public void setPtyp(String ptyp) {
        this.ptyp = ptyp;
    }

    public String getPtyp() {
        return ptyp;
    }

    public void setMdl(String mdl) {
        this.mdl = mdl;
    }

    public String getMdl() {
        return mdl;
    }

    public void setLab(String lab) {
        this.lab = lab;
    }

    public String getLab() {
        return lab;
    }

    public void setUpd(String upd) {
        this.upd = upd;
    }

    public String getUpd() {
        return upd;
    }

    public void setFlgtyp(String flgtyp) {
        this.flgtyp = flgtyp;
    }

    public String getFlgtyp() {
        return flgtyp;
    }
}
