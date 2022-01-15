package ft.com.dao;

import java.util.List;

public class PrcPrpBean {
  String prp = "", prpTyp = "", val = "",prt1="" ;
  int srt = 0 ;
  List prpL = null;
  
  public PrcPrpBean() {
      super();
  }

  public void setPrp(String prp) {
      this.prp = prp;
  }

  public String getPrp() {
      return prp;
  }

  public void setPrpTyp(String prpTyp) {
      this.prpTyp = prpTyp;
  }

  public String getPrpTyp() {
      return prpTyp;
  }

  public void setPrpL(List prpL) {
      this.prpL = prpL;
  }

  public List getPrpL() {
      return prpL;
  }

  public void setVal(String val) {
      this.val = val;
  }

  public String getVal() {
      return val;
  }

  public void setSrt(int srt) {
      this.srt = srt;
  }

  public int getSrt() {
      return srt;
  }

  public void setPrt1(String prt1) {
      this.prt1 = prt1;
  }

  public String getPrt1() {
      return prt1;
  }

}
