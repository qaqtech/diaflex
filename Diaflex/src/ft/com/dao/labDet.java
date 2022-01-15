package ft.com.dao;

public class labDet {
  private String labVal = "";
  private String labDesc   = "";
  private String in_stt = "";
  private String grp="";
    public labDet() {
        super();
    }

   

    public void setIn_stt(String in_stt) {
        this.in_stt = in_stt;
    }

    public String getIn_stt() {
        return in_stt;
    }

    public void setLabVal(String labVal) {
        this.labVal = labVal;
    }

    public String getLabVal() {
        return labVal;
    }

    public void setLabDesc(String labDesc) {
        this.labDesc = labDesc;
    }

    public String getLabDesc() {
        return labDesc;
    }

    public void setGrp(String grp) {
        this.grp = grp;
    }

    public String getGrp() {
        return grp;
    }
}
