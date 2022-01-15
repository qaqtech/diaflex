package ft.com.fileupload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class FileUploadingForm extends ActionForm {
    public final Map values     = new HashMap();
    private String upload;
    private ArrayList uploadList = new ArrayList();
    FormFile fileUpload = null;
    private  FileUploadInterface fileUploadInt ;
    private ArrayList pktDtlList = new ArrayList();
    
    
    public FileUploadingForm() {
        super();
    }
    public void reset() {
        values.clear();
      
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

    public void setUpload(String upload) {
        this.upload = upload;
    }

    public String getUpload() {
        return upload;
    }


    public void setFileUpload(FormFile fileUpload) {
        this.fileUpload = fileUpload;
    }

    public FormFile getFileUpload() {
        return fileUpload;
    }

    public void setFileUploadInt(FileUploadInterface fileUploadInt) {
        this.fileUploadInt = fileUploadInt;
    }

    public FileUploadInterface getFileUploadInt() {
        return fileUploadInt;
    }

    public void setUploadList(ArrayList uploadList) {
        this.uploadList = uploadList;
    }

    public ArrayList getUploadList() {
        return uploadList;
    }

    public void setPktDtlList(ArrayList pktDtlList) {
        this.pktDtlList = pktDtlList;
    }

    public ArrayList getPktDtlList() {
        return pktDtlList;
    }
}

    

