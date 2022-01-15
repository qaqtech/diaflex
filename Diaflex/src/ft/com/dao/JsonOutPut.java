package ft.com.dao;

import org.json.JSONObject;

public class JsonOutPut {
    private String MESSAGE;
    private String STATUS;
    public JsonOutPut() {
        super();
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getSTATUS() {
        return STATUS;
    }
}
