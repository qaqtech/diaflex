package ft.com.dao;

public class MemoType {
   private String dsc ;
   private String srch_typ;
   private String memo_typ;
   private String idn;
   private String hdr_stt;
   private String dtl_stt;
   
    public MemoType() {
        super();
    }
   
    public void setDsc(String dsc) {
        this.dsc = dsc;
    }

    public String getDsc() {
        return dsc;
    }

    public void setSrch_typ(String srch_typ) {
        this.srch_typ = srch_typ;
    }

    public String getSrch_typ() {
        return srch_typ;
    }

    public void setMemo_typ(String memo_typ) {
        this.memo_typ = memo_typ;
    }

    public String getMemo_typ() {
        return memo_typ;
    }

    public void setIdn(String idn) {
        this.idn = idn;
    }

    public String getIdn() {
        return idn;
    }

    public void setHdr_stt(String hdr_stt) {
        this.hdr_stt = hdr_stt;
    }

    public String getHdr_stt() {
        return hdr_stt;
    }

    public void setDtl_stt(String dtl_stt) {
        this.dtl_stt = dtl_stt;
    }

    public String getDtl_stt() {
        return dtl_stt;
    }
}
