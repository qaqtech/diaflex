package ft.com.dao;

public class FileUploadDao {
    
    String fileTyp="";
    String fileDsc="";
  String fileExt="";
    String date_fmt="";
    public FileUploadDao() {
        super();
    }

    public void setFileTyp(String fileTyp) {
        this.fileTyp = fileTyp;
    }

    public String getFileTyp() {
        return fileTyp;
    }

    public void setFileDsc(String fileDsc) {
        this.fileDsc = fileDsc;
    }

    public String getFileDsc() {
        return fileDsc;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setDate_fmt(String date_fmt) {
        this.date_fmt = date_fmt;
    }

    public String getDate_fmt() {
        return date_fmt;
    }
}
