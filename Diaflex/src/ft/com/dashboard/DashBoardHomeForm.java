package ft.com.dashboard;

import org.apache.struts.action.ActionForm;

public  class DashBoardHomeForm extends ActionForm {

  private String method="";


public DashBoardHomeForm() {
        super();
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
