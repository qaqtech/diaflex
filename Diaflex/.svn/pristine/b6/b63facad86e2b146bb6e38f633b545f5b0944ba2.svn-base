package ft.com.report;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class MixStockSummaryForm extends ActionForm {
    private String reportNme = "";
    private String criteria = "";
    private String memo;
    private final Map values  = new HashMap();
    public MixStockSummaryForm() {
        super();
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
    public void setReportNme(String reportNme) {
        this.reportNme = reportNme;
    }

    public String getReportNme() {
        return reportNme;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMemo() {
        return memo;
    }
}
