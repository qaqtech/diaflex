package ft.com.website;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class AdminHomepgScrollForm extends ActionForm{
    
    private FormFile homepageimg;
    private String homepagetext;
    private String validfrom;
    private String validtill;
    private String ctgIdn;
    private ArrayList ctgList;
    private String idn;
    
    public AdminHomepgScrollForm() {
        super();
    }
    public void reset() {
          homepageimg=null;
          homepagetext="";
          validfrom="";
          validtill="";
          ctgIdn="";
          idn="";
        
   }
    public void setHomepageimg(FormFile homepageimg) {
        this.homepageimg = homepageimg;
    }

    public FormFile getHomepageimg() {
        return homepageimg;
    }

    public void setHomepagetext(String homepagetext) {
        this.homepagetext = homepagetext;
    }

    public String getHomepagetext() {
        return homepagetext;
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

    public void setCtgIdn(String ctgIdn) {
        this.ctgIdn = ctgIdn;
    }

    public String getCtgIdn() {
        return ctgIdn;
    }

    public void setCtgList(ArrayList ctgList) {
        this.ctgList = ctgList;
    }

    public ArrayList getCtgList() {
        return ctgList;
    }

    public void setIdn(String idn) {
        this.idn = idn;
    }

    public String getIdn() {
        return idn;
    }
}
