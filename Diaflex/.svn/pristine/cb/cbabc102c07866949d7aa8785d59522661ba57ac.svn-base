package ft.com;

//~--- non-JDK imports --------------------------------------------------------

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class LoginForm extends ActionForm {
    String dbTyp = "live";
    String dfpwd;
    String dfusr;
    String login;
    String method;
    public final Map values  = new HashMap();

    public LoginForm() {
        super();
    }

    public void setDfusr(String dfusr) {
        this.dfusr = dfusr;
//        System.out.println(dfusr);
    }

    public String getDfusr() {
        return dfusr;
    }

    public void setDfpwd(String dfpwd) {
        this.dfpwd = dfpwd;
    }

    public String getDfpwd() {
        return dfpwd;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setDbTyp(String dbTyp) {
        this.dbTyp = dbTyp;
    }

    public String getDbTyp() {
        return dbTyp;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
    public void setValue(String key, Object value) {
        values.put(key, value);
    }

    public Object getValue(String key) {
        return values.get(key);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
