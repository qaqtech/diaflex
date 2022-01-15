package ft.com.dao;

import java.io.Serializable;

import java.util.ArrayList;

public class TransReportList implements Serializable {
    
    public TransReportList() {
        super();
    }
    public ArrayList<TransactionRDAO> RESULT = null;
    public String STATUS = null;
    public String MASSAGE = null;
   
    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setMASSAGE(String MASSAGE) {
        this.MASSAGE = MASSAGE;
    }

    public String getMASSAGE() {
        return MASSAGE;
    }


    public void setRESULT(ArrayList<TransactionRDAO> RESULT) {
        this.RESULT = RESULT;
    }

    public ArrayList<TransactionRDAO> getRESULT() {
        return RESULT;
    }
}
