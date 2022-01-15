package ft.com.website;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class AdminPopUpDataForm extends ActionForm{
    
    private FormFile  bgimg;
    private String  bgimgName;
    private FormFile headerlogo;
    private String headerlogoName;
    private String eventLabel;
    private FormFile eventimage;
    private String eventimageName;
    private String validfrom;
    private String validtill;
    
    public AdminPopUpDataForm() {
        super();
    }

    public void setHeaderlogo(FormFile headerlogo) {
        this.headerlogo = headerlogo;
    }

    public FormFile getHeaderlogo() {
        return headerlogo;
    }

    public void setEventLabel(String eventLabel) {
        this.eventLabel = eventLabel;
    }

    public String getEventLabel() {
        return eventLabel;
    }

    public void setEventimage(FormFile eventimage) {
        this.eventimage = eventimage;
    }

    public FormFile getEventimage() {
        return eventimage;
    }

    public void setValidfrom(String validfrom) {
        this.validfrom = validfrom;
    }

    public String getValidfrom() {
        return validfrom;
    }

    public void setValidtill(String validtill) {
        this.validtill = validtill;
    }

    public String getValidtill() {
        return validtill;
    }

    public void setBgimg(FormFile bgimg) {
        this.bgimg = bgimg;
    }

    public FormFile getBgimg() {
        return bgimg;
    }
}
