package ft.com.marketing;

import java.sql.Date;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class PacketLookupForm extends ActionForm  {
    
    private String vnm; //packetid
    
    //display values
    private String strName; //buyer
    private String strStatusDetail; // detailed status
    private int iMemoNo; // memo id
    private String dtDate; //transaction date - iss_date
    private String strType; //transaction type
    private String strState; //state
    //private String strBuyerType;
    private String log_typ;
    private String strMprp; // name of property
    private String strVal; // val
    /*private int iNum; // number value of property
    private Date dtDateValue; //date value of property
    private String strTxt; //text value of property*/
    private String strRapRate;
    private String strUpr;
    private String strCmp;
    private String strUprDis;
    private String strCmpDis;
    private String rtn_dte;
    private String prc;
    private String emp;
    private String issueID;
    public final Map values     = new HashMap();
    
    public void setValue(String key, Object value) {
        values.put(key, value);
    }

    public Object getValue(String key) {
        return values.get(key);
    }

    public Map getValues() {
        return values;
    }

    public void setVnm(String vnm) {
      
      this.vnm = vnm;
      //System.out.println(vnm);
    }
    
    public String getVnm(){
      
      //System.out.println(vnm);
      return this.vnm;
    }
    
    public PacketLookupForm() {
        super();
    }
    public void reset() {
        System.out.println(new java.util.Date() + " @reset : " + values.size());
        values.clear();
    }
    public void setStrType(String strType) {
        this.strType = strType;
        //System.out.println("type set");
    }

    public String getStrType() {
        return strType;
    }

    public void setStrState(String strState) {
        this.strState = strState;
    }

    public String getStrState() {
        return strState;
    }

    public void setIMemoNo(int iMemoNo) {
        this.iMemoNo = iMemoNo;
    }

    public int getIMemoNo() {
        return iMemoNo;
    }

   

   /* public void setDtDateValue(Date dtDateValue) {
        this.dtDateValue = dtDateValue;
    }

    public Date getDtDateValue() {
        return dtDateValue;
    }*/

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public String getStrName() {
        return strName;
    }

    /*public void setStrBuyerType(String strBuyerType) {
        this.strBuyerType = strBuyerType;
    }

    public String getStrBuyerType() {
        return strBuyerType;
    }*/

    public void setStrMprp(String strMprp) {
        this.strMprp = strMprp;
    }

    public String getStrMprp() {
        return strMprp;
    }

    public void setStrVal(String strVal) {
        this.strVal = strVal;
    }

    public String getStrVal() {
        return strVal;
    }

    /*public void setINum(int iNum) {
        this.iNum = iNum;
    }

    public int getINum() {
        return iNum;
    }

    public void setStrTxt(String strTxt) {
        this.strTxt = strTxt;
    }

    public String getStrTxt() {
        return strTxt;
    }*/

    public void setStrStatusDetail(String strStatusDetail) {
        this.strStatusDetail = strStatusDetail;
    }

    public String getStrStatusDetail() {
        return strStatusDetail;
    }

    public String getVnm1() {
        return vnm;
    }

    public void setStrRapRate(String strRapRate) {
        this.strRapRate = strRapRate;
    }

    public String getStrRapRate() {
        return strRapRate;
    }

    public void setStrUpr(String strUpr) {
        this.strUpr = strUpr;
    }

    public String getStrUpr() {
        return strUpr;
    }

    public void setStrCmp(String strCmp) {
        this.strCmp = strCmp;
    }

    public String getStrCmp() {
        return strCmp;
    }

    public void setStrUprDis(String strUprDis) {
        this.strUprDis = strUprDis;
    }

    public String getStrUprDis() {
        return strUprDis;
    }

    public void setStrCmpDis(String strCmpDis) {
        this.strCmpDis = strCmpDis;
    }

    public String getStrCmpDis() {
        return strCmpDis;
    }

    public void setDtDate(String dtDate) {
        this.dtDate = dtDate;
    }

    public String getDtDate() {
        return dtDate;
    }

    public void setLog_typ(String log_typ) {
        this.log_typ = log_typ;
    }

    public String getLog_typ() {
        return log_typ;
    }

    public void setRtn_dte(String rtn_dte) {
        this.rtn_dte = rtn_dte;
    }

    public String getRtn_dte() {
        return rtn_dte;
    }

    public void setPrc(String prc) {
        this.prc = prc;
    }

    public String getPrc() {
        return prc;
    }

    public void setEmp(String emp) {
        this.emp = emp;
    }

    public String getEmp() {
        return emp;
    }

    public void setIssueID(String issueID) {
        this.issueID = issueID;
    }

    public String getIssueID() {
        return issueID;
    }
}
