package ft.com.fileupload;

import ft.com.assort.AssortFinalRtnForm;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface FileUploadInterface {
    
    public int addFileData(HttpServletRequest req ,HttpServletResponse res, HashMap paramsList);
    public ArrayList FileUploadStt(HttpServletRequest req ,HttpServletResponse res, String seqNo );
    public ArrayList FileUploadErr(HttpServletRequest req ,HttpServletResponse res, String seqNo );
    public ArrayList fileUploadTyp(HttpServletRequest req, HttpServletResponse res,FileUploadForm form);
    public String fileTRFTOCSV(HttpServletRequest req ,HttpServletResponse res , HashMap param);
    public String fileReportName(HttpServletRequest req ,HttpServletResponse res, String fileTyp);
//    public ArrayList MftGenricSrch(HttpServletRequest req , HttpServletResponse res);
    public ArrayList SearchResult(HttpServletRequest req ,HttpServletResponse res , String mdl);
    public HashMap GetTotal(HttpServletRequest req ,HttpServletResponse res , String flg);
    public ArrayList FileUploadSttTm(HttpServletRequest req , HttpServletResponse res, HashMap params );
    public ArrayList GetTotalByRCNo(HttpServletRequest req , HttpServletResponse res );
    public ArrayList FileUploadErrorDtl(HttpServletRequest req , HttpServletResponse res, HashMap params );
    public void sendmail(HttpServletRequest req, HttpServletResponse res,String typ,String msg);
    public HashMap VerifiedHeadData(HttpServletRequest req , HttpServletResponse res,ArrayList dataList,HashMap params);
    public HashMap VerifiedFileData(HttpServletRequest req , HttpServletResponse res,String dteFmt, ArrayList dataList);
    public int InsertFileHdr(HttpServletRequest req , HttpServletResponse res,String fileTyp);
    public void InsertFileData(HttpServletRequest req , HttpServletResponse res,ArrayList dataList, int fileSeq);
    public void updateFileLog(HttpServletRequest req , HttpServletResponse res,int lineNo, int fileSeq,String rmk);
    public ArrayList uploadFile(HttpServletRequest req , HttpServletResponse res,String uploadTyp, int fileSeq);
     public void updateFileHdr(HttpServletRequest req , HttpServletResponse res,String stt,int fileSeq);
    public ArrayList fileUploadTypList(HttpServletRequest req , HttpServletResponse res,FileUploadForm form);
}
