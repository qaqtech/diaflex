package ft.com.dao;

public class boxDet {
  private String boxVal = "";
  private String boxDesc   = "";
  private String in_stt = "";
    public boxDet() {
        super();
    }

   

    public void setIn_stt(String in_stt) {
        this.in_stt = in_stt;
    }

    public String getIn_stt() {
        return in_stt;
    }

    public void setBoxVal(String boxVal) {
        this.boxVal = boxVal;
    }

    public String getBoxVal() {
        return boxVal;
    }

    public void setBoxDesc(String boxDesc) {
        this.boxDesc = boxDesc;
    }

    public String getBoxDesc() {
        return boxDesc;
    }
}
