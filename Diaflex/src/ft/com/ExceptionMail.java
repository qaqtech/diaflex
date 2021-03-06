package ft.com;

import java.util.Date;
import java.util.HashMap;

public class ExceptionMail extends Thread {
    String sql="";
    String params="";
    String e="";
    String logApplNm="";
    String logid="";
    public ExceptionMail(String sql,String params,String e,String logApplNm,String logid) {
        super();
        this.sql = sql;
        this.params = params;
        this.e = e;
        this.logApplNm = logApplNm;
        this.logid = logid;
    }
    public void run()
    {
    fireMail();
    }
    public void fireMail(){
        InfoMgr info = new InfoMgr();
        GenMail mail = new GenMail();
        HashMap dmbsInfoLst=new HashMap();
        dmbsInfoLst.put("SSL", "Y");
//        dmbsInfoLst.put("TTL", "Y");
        info.setDmbsInfoLst(dmbsInfoLst);
        info.setSmtpHost("email-smtp.us-west-2.amazonaws.com");
        info.setSmtpPort(465);
        info.setSmtpUsr("AKIAIE4JQGY6RT6XVKOQ");
        info.setSmtpPwd("Aue44//DUuWzLW2GoZIAuwfrwAGW242ewa9DRlutlOO/");
        mail.setInfo(info);    
        mail.init();
        mail.setSender("no-reply@qaqtech.com", "Fauna Team Alert"); 
        
        if(e.indexOf("ORA-02393")  > -1){
        mail.setSubject(logApplNm+" There Was exceeded call limit on CPU usage(Diaflex) In Log Id "+logid);
        mail.setCC("puravrc@gmail.com");
        }else{
        mail.setSubject(logApplNm+" There Was Exception(Diaflex) In Log Id "+logid);
        mail.setCC("puravrc@gmail.com");
        }
        //mail.setTO("mayur@faunatechnologies.com");  
        StringBuffer msg=new StringBuffer();
        msg.append("<p>Hello <b>Team</b></p>");
        msg.append("<p>  Please Check Below Issue.</p>");
        msg.append("<p>  <b>Sql/Proc</p> : -"+sql+"</p>");
        msg.append("<p>  <b>Params</p> : -"+params+"</p>");
        msg.append("<p>  <b>Exception</p> : -"+e+"</p>");
        msg.append("<p>Regards,<br>Mayur R boob<br> </p><p><img src=\"http://sys.faunatechnologies.com/diaflex/images/flame.png\"></p>");
        String mailMag = msg.toString();
        mail.setMsgText(mailMag);
        long lStartTime = new Date().getTime();
        mail.send("");
        long lEndTime = new Date().getTime();
        long difference = lEndTime - lStartTime;
        System.out.println(convertMillis(difference));
        if(e.indexOf("ORA-01031")  > -1 || e.indexOf("ORA-00942")  > -1){
            new Flush().start();
        }
    }
    public String convertMillis(long milliseconds){
    long seconds, minutes, hours;
       seconds = milliseconds / 1000;
       minutes = seconds / 60;
       seconds = seconds % 60;
       hours = minutes / 60;
       minutes = minutes % 60;
      return("Processed in: " + hours + ":" + minutes + ":" + seconds);
    }
}
