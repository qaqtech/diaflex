package ft.com;

import ft.com.dao.TransactionRDAO;
import ft.com.website.UserRegistrationInfoImpl;


import java.util.ArrayList;
import java.util.HashMap;

import org.displaytag.decorator.TableDecorator;

public class Decortor extends TableDecorator { 
   
    public String getAction()
       {
            HashMap data = (HashMap)getCurrentRowObject();
            String isIdn = (String)data.get("issIdn");
            String emp = nvl((String)data.get("emp")).toUpperCase();
            String rtnVal=isIdn;
            String reqUrl = (String)data.get("requrl");
            String homeDir = (String)data.get("homeDir");
            String webDir = (String)data.get("webDir");
            String cnt = (String)data.get("cnt");
            InfoMgr info=(InfoMgr)data.get("info");
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("ASSORT_LAB");
            ArrayList pageList=new ArrayList();
            HashMap pageDtlMap=new HashMap();
            String fld_ttl="";
            String dflt_val="";
            String lov_qry="";
            
            String val_cond="";
            String form_nme="";
            String flg1="";
            if(emp.indexOf("LAB-")!=-1){
               String lab = (emp.toUpperCase()).replaceAll("LAB-", "");
//               rtnVal = "Lab Packing List &nbsp; <a href=\""+reqUrl+"/excel/labxl?issueIdn="+isIdn+"&lab="+lab+"\" target=\"_blank\" > Excel </a> &nbsp;|&nbsp;"+
//                       "<a href=\""+webDir+"/reports/rwservlet?"+cnt+"&report="+homeDir+"\\reports\\AFT_GIA_ISS.jsp&P_MEMO_NO="+isIdn+"&P_LAB="+lab+"\" target=\"_blank\" > PDF</a><br/>"+
//                       "<a href=\""+webDir+"/reports/rwservlet?"+cnt+"&report="+homeDir+"\\reports\\assort_rpt.jsp&p_iss_id="+isIdn+"\" target=\"_blank\" >Lab Assort Report</a> &nbsp;|&nbsp;"+
//                       "<a href=\""+reqUrl+"/lab/labIssue.do?method=pktPrint&issueIdn="+isIdn+"&lab="+lab+"\" target=\"_blank\" >Packet Print</a>&nbsp;|&nbsp;"+
//                "<a href=\""+reqUrl+"/report/assortLabPending.do?method=labPktPrint&issueIdn="+isIdn+"\" target=\"_blank\" >Lab Packet Print</a>&nbsp;|&nbsp; "+
//                   "<a href=\""+reqUrl+"/report/assortLabPending.do?method=loadPkt&issueIdn="+isIdn+"\" >Packets Detail</a>";
               rtnVal="";
//                rtnVal = "<input type=\"checkbox\" name=\""+isIdn+"\" id=\""+isIdn+"\" value=\""+lab+"\"> &nbsp;|&nbsp;";
      pageList= ((ArrayList)pageDtl.get("CHK") == null)?new ArrayList():(ArrayList)pageDtl.get("CHK");                
                if(pageList!=null && pageList.size() >0){
                    for(int j=0;j<pageList.size();j++){
                        pageDtlMap=(HashMap)pageList.get(j);
                        fld_ttl=(String)pageDtlMap.get("fld_ttl");
                        dflt_val=(String)pageDtlMap.get("dflt_val");
                        fld_ttl = fld_ttl.replaceAll("ISSIDN", isIdn);
                        dflt_val = dflt_val.replaceAll("LAB", lab);
                        rtnVal = "<input type=\"checkbox\" name=\""+fld_ttl+"\" id=\""+fld_ttl+"\" value=\""+dflt_val+"\"> &nbsp;|&nbsp;";
                        }
                }
                pageList= ((ArrayList)pageDtl.get("LINK") == null)?new ArrayList():(ArrayList)pageDtl.get("LINK");
                if(pageList!=null && pageList.size() >0){
                    for(int j=0;j<pageList.size();j++){
                        pageDtlMap=(HashMap)pageList.get(j);
                        fld_ttl=(String)pageDtlMap.get("fld_ttl");
                        dflt_val=(String)pageDtlMap.get("dflt_val");
                        lov_qry=(String)pageDtlMap.get("lov_qry");
                        form_nme=(String)pageDtlMap.get("form_nme");
                        flg1=(String)pageDtlMap.get("flg1");
                        val_cond=(String)pageDtlMap.get("val_cond");    
                        if(val_cond.equals(""))
                        dflt_val = reqUrl+dflt_val.replaceAll("ISSIDN", isIdn);
                        else
                        dflt_val = webDir+dflt_val.replaceAll("ISSIDN", isIdn);    
                        dflt_val = dflt_val.replaceAll("LAB", lab);
                        if(form_nme.equals("")){
                        if(pageList.size()-1!=j)
                        rtnVal += "<a href=\""+dflt_val+"\" target=\""+flg1+"\">&nbsp;"+ fld_ttl +"</a>&nbsp;|&nbsp; " ;
                        else
                            rtnVal += "<a href=\""+dflt_val+"\" target=\""+flg1+"\">&nbsp;"+ fld_ttl +"</a>" ; 
                        }else if(form_nme.equals(lab)){
                            rtnVal += "<a href=\""+dflt_val+"\" target=\""+flg1+"\">&nbsp;"+ fld_ttl +"</a>" ;
                        }
                        }
                }
//               if(!assortViewMap.contains("EXCL"))
//                  rtnVal = "Lab Packing List &nbsp; <a href=\""+reqUrl+"/excel/labxl?issueIdn="+isIdn+"&lab="+lab+"\" target=\"_blank\" > Excel </a> &nbsp;|&nbsp;";
//              if(!assortViewMap.contains("EXCL2"))
//                   rtnVal += " <a href=\""+reqUrl+"/excel/labxl?issueIdn="+isIdn+"&lab="+lab+"&xl=2\" target=\"_blank\" > Excel 2 </a> &nbsp;|&nbsp;";
//               if(!assortViewMap.contains("PDF"))            
//                  rtnVal =rtnVal+"<a href=\""+webDir+"/reports/rwservlet?"+cnt+"&report="+homeDir+"\\reports\\AFT_GIA_ISS.jsp&P_MEMO_NO="+isIdn+"&P_LAB="+lab+"\" target=\"_blank\" > PDF</a><br/>";
//              if(!assortViewMap.contains("LAR"))            
//                 rtnVal =rtnVal+"<a href=\""+webDir+"/reports/rwservlet?"+cnt+"&report="+homeDir+"\\reports\\assort_rpt.jsp&p_iss_id="+isIdn+"\" target=\"_blank\" >Lab Assort Report</a> &nbsp;|&nbsp;";
//              if(!assortViewMap.contains("PKP"))            
//                 rtnVal =rtnVal+"<a href=\""+reqUrl+"/lab/labIssue.do?method=pktPrint&issueIdn="+isIdn+"&lab="+lab+"\" target=\"_blank\" >Packet Print</a>&nbsp;|&nbsp;";
//              if(!assortViewMap.contains("LPP"))            
//                 rtnVal =rtnVal+"<a href=\""+reqUrl+"/report/assortLabPending.do?method=labPktPrint&issueIdn="+isIdn+"\" target=\"_blank\" >Lab Packet Print</a>&nbsp;|&nbsp; ";
//              if(!assortViewMap.contains("PKD"))            
//                 rtnVal =rtnVal+"<a href=\""+reqUrl+"/report/assortLabPending.do?method=loadPkt&issueIdn="+isIdn+"\" >Packets Detail</a>";
//                          
            }else{

                rtnVal = "<a href=\""+reqUrl+"/report/assortLabPending.do?method=loadPkt&issueIdn="+isIdn+"\" >Packets Detail</a>";
                pageList=(ArrayList)pageDtl.get("ISSUEFNLASSORT_VIEW");
                if(pageList!=null && pageList.size() >0){
                for(int j=0;j<pageList.size();j++){
                    pageDtlMap=(HashMap)pageList.get(j);
                    fld_ttl=(String)pageDtlMap.get("fld_ttl");
                    rtnVal =rtnVal+" | <a href=\""+reqUrl+"/report/assortLabPending.do?method=loadPktFnlAssortdata&issueIdn="+isIdn+"\" >"+fld_ttl+"</a>";
                }}
            }
            return rtnVal;
        }
    public String getMemo(){
        String url = "";
        HashMap data = (HashMap)getCurrentRowObject();
        String typ = nvl((String)data.get("typ"));
        String reqUrl = (String)data.get("reqUrl");
        String memoId = nvl((String)data.get("memo"));
        if(typ.indexOf("AP")!=-1)
            url = "<a href=\""+reqUrl+"/marketing/memoSale.do?method=load&memoId="+memoId+"&pnd=Y&view=Y\" >"+memoId+"</a>";
        else if(typ.indexOf("CS")!=-1)
            url = "<a href=\""+reqUrl+"/marketing/consignmentSale.do?method=load&memoId="+memoId+"&pnd=Y&view=Y\" >"+memoId+"</a>";
        else
           url = "<a href=\""+reqUrl+"/marketing/memoReturn.do?method=load&memoId="+memoId+"&pnd=Y&view=Y\" >"+memoId+"</a>";

        return url;
    }
    
    public String getPktdtl(){
        String url = "";
        HashMap data = (HashMap)getCurrentRowObject();
        String reqUrl = (String)data.get("reqUrl");
        String memoId = nvl((String)data.get("memo"));
        url = "<a href=\""+reqUrl+"/marketing/memoSrch.do?method=loadPkt&memoId="+memoId+"\" target=\"_blank\" title=\"Click here to get packet Details\">PKTDTL</a>";
        return url;
    }
    public String getByr(){
        String url = "";
        HashMap data = (HashMap)getCurrentRowObject();
        String nmeIdn = (String)data.get("NME_IDN");
        String reqUrl = (String)data.get("reqUrl");
        String byr = (String)data.get("byr");
        url = "<a href=\""+reqUrl+"/contact/nme.do?method=load&nmeIdn="+nmeIdn+"\" target=\"_blank\" title=\"Click here to get View /Edit\">"+byr+"</a>";
        return url;
    }
    public String getAmt(){
        String url = "";
        HashMap data = (HashMap)getCurrentRowObject();
        String color = nvl((String)data.get("color"));
        url = "<b style=\"background-color:"+color+"\">"+nvl((String)data.get("amt"))+"</b>";
        return url;
    }
    
    public String getInvId(){
        String url = "";
        HashMap data = (HashMap)getCurrentRowObject();
        String invId = nvl((String)data.get("INVIDN"));
        String typ = nvl((String)data.get("TYP"));
        String target = "TR_"+invId;
        String href="receviceInvAction.do?method=PktDtl&refIdn="+invId+"&TYP="+typ;
        url = "<a href=\""+href+"\" id=\"LNK_"+invId+" onclick=\"loadASSFNL('"+target+"','LNK_"+invId+"')\"  target=\"SC\" >"+invId+"</a>";
        return url;
    }
    
    public String getXrt(){
        String url = "";
        HashMap data = (HashMap)getCurrentRowObject();
        String invId = nvl((String)data.get("INVIDN"));
        String xrt = nvl((String)data.get("XRT"));
          url = " <input type=\"text\" name=\"cb_xrt_"+invId+"\" id=\"cb_xrt_"+invId+"\" value=\""+xrt+"\" size=\"10\" />";
        return url;
    }
    
//    public String getBrkslab(){
////        HashMap data = (HashMap)getCurrentRowObject();
////        String url = "";
////        
////        String invId = nvl((String)data.get("INVIDN"));
////        String xrt = nvl((String)data.get("XRT"));
////        url="<select name=\""">"
////        InfoMgr info=(InfoMgr)data.get("info");
////        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
////        HashMap pageDtl=(HashMap)allPageDtl.get("PAY_RECEIPT");
////        ArrayList pageList=(ArrayList)pageDtl.get("BROKER_SLAB");
////        if(pageList!=null && pageList.size() >0){
////        for(int j=0;j<pageList.size();j++){
////                  HashMap  pageDtlMap=(HashMap)pageList.get(j);
////                   String fld_nme=(String)pageDtlMap.get("fld_nme");
////                   String  fld_ttl=(String)pageDtlMap.get("fld_ttl");
////            
////        }}
////             url = " <input type=\"text\" name=\"cb_xrt_"+invId+"\" id=\"cb_xrt_"+invId+"\" value=\""+xrt+"\" size=\"10\" />";
////        return url;
//    }
    public String nvl(String pVal) {
        return nvl(pVal, "");
    }
    public String nvl(String pVal, String rVal) {
        String val = pVal ;
        if(pVal == null)
            val = rVal;
        return val;
    }
    public String getReject() {
       
        UserRegistrationInfoImpl userRgnrData = (UserRegistrationInfoImpl)getCurrentRowObject();
        String registrationId = "<a href=\"AcceptRegistration.do?method=rejectRegn&regnIdn=" + userRgnrData.getRegId() + "\">Reject</a>";
        return registrationId;
    }
    public String getEditDetail() {
        InfoMgr info = new InfoMgr();
        UserRegistrationInfoImpl userRegistrationInfoImpl = new UserRegistrationInfoImpl();
  
        UserRegistrationInfoImpl userRgnrData = (UserRegistrationInfoImpl)getCurrentRowObject();
        String registrationId = "<a href=\"AcceptRegistration.do?method=saveRegn&regnIdn=" + userRgnrData.getRegId() + "\" >Accept</a>";
        if(userRgnrData.getUserRegistrationInfo().getVerify().equals("N"))
            registrationId="";
        return registrationId;
    }
    public String getAddDetail() {
        InfoMgr info = new InfoMgr();
        UserRegistrationInfoImpl userRegistrationInfoImpl = new UserRegistrationInfoImpl();        
        UserRegistrationInfoImpl userRgnrData = (UserRegistrationInfoImpl)getCurrentRowObject();
        String userId=String.valueOf(userRgnrData.getRegId());
       String   registrationId = "<a href=\"AcceptRegistration.do?method=addUsrEmp&regnIdn=" +userId+"\" id=\"LNK_"+userId+"\" onclick=\"loadASSFNLPop('LNK_"+userId+"')\" target=\"SC\" >Accept</a>";
        if(userRgnrData.getUserRegistrationInfo().getVerify().equals("N"))
            registrationId="";
        return registrationId;
    }

    public String getLinkExistingUser() {
        InfoMgr info = new InfoMgr();
        UserRegistrationInfoImpl userRegistrationInfoImpl = new UserRegistrationInfoImpl();

        //info.setSubpage("Admin");
        UserRegistrationInfoImpl userRgnrData = (UserRegistrationInfoImpl)getCurrentRowObject();
        
        String registrationId = "<a href=\"AcceptRegistration.do?method=linkUserNavigate&regnIdn=" + userRgnrData.getRegId() + "\">Link</a>";
        if(userRgnrData.getUserRegistrationInfo().getVerify().equals("N"))
            registrationId="";
        return registrationId;
    }
    
    
     public String getOg_qty(){
        String url = "";
        TransactionRDAO data = (TransactionRDAO)getCurrentRowObject();
        int IDN = data.getIDN();
        String qty  =data.getOg_qty();
        String transTyp  =data.getTyp();
        if(!qty.equals("0")){
      url = "<a href=\"transactionReport.do?method=pktDetails&memoId="+IDN+"&transTyp="+transTyp+"&TYP=ORG\" target=\"_blank\">"+qty+"</a>";
        }else{
                url = "";
            }
     
        return url;
    }
     
    public String getRtl_qty(){
       String url = "";
       TransactionRDAO data = (TransactionRDAO)getCurrentRowObject();
       int IDN = data.getIDN();
       String qty  =data.getRtl_qty();
        String transTyp  =data.getTyp();
        if(!qty.equals("0")){
     url = "<a href=\"transactionReport.do?method=pktDetails&memoId="+IDN+"&transTyp="+transTyp+"&TYP=RT\" target=\"_blank\">"+qty+"</a>";
        }else{
                url = "";
            }
       return url;
    }
    
    public String getCm_qty(){
       String url = "";
       TransactionRDAO data = (TransactionRDAO)getCurrentRowObject();
       int IDN = data.getIDN();
       String qty  =data.getCm_qty();
        String transTyp  =data.getTyp();
        if(!qty.equals("0")){
     url = "<a href=\"transactionReport.do?method=pktDetails&memoId="+IDN+"&transTyp="+transTyp+"&TYP=COM\" target=\"_blank\" >"+qty+"</a>";
        }else{
                url = "";
            }
       return url;
    }
    
    public String getAc_qty(){
       String url = "";
       TransactionRDAO data = (TransactionRDAO)getCurrentRowObject();
       int IDN = data.getIDN();
       String qty  =data.getAc_qty();
        String transTyp  =data.getTyp();
        if(!qty.equals("0")){
     url = "<a href=\"transactionReport.do?method=pktDetails&memoId="+IDN+"&transTyp="+transTyp+"&TYP=AC\" target=\"_blank\" >"+qty+"</a>";
        }else{
                url = "";
            }
       return url;
    }
     
    public Decortor() {
        super();
    }
}
