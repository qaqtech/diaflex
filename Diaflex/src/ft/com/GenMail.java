package ft.com;

import ft.com.InfoMgr;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.mail.Authenticator;

import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import javax.mail.*;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.Address;
import javax.mail.internet.*;
import java.util.Arrays;
import java.util.List;

public class GenMail  {
  String senderId = "ceo@faunatechnologies.com",
         senderNm = "Purav Choksi",
         subject  = "Hi",
         replyTo = null,
         contentType = "text/html",
        msgText = "",
        smtpHost="mail.faunatechnologies.com",
      smtpUsr="faunatechnologies",
      smtpPwd="logos19",
         fnl      = "",rtnMailTo="",rtnMailCC="",rtnMailBCC="";
    int smtpPort=25;
    ArrayList fileName = null;
    ArrayList attachmentType = null;
  Session session = null;
  MimeMessage msg = null;
  Address[] mailTO = null;
  Address[] mailCC = null;
  Address[] mailBCC = null;
 ArrayList attachments = null;
 InfoMgr info = null ; 
 
  public GenMail() {
  }
    
  public void init() {
      try {
        Properties props = System.getProperties();
        props.put("mail.host", smtpHost);
         props.put("mail.smtp.auth", "true");
         String isSsl = (String)info.getDmbsInfoLst().get("SSL");
          String isttl = (String)info.getDmbsInfoLst().get("TTL");
          if(isSsl!=null && isSsl.equals("Y")){
              props.put("mail.smtp.socketFactory.port", "465");
              props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");

          }
          if(isttl!=null && isttl.equals("Y")){
          props.put("mail.smtp.starttls.enable", "true");
          }
          props.put("mail.smtp.port", Integer.toString(smtpPort));  
        session = Session.getInstance(props, new MailAuthenticator());
        setMsg();
      }
      catch (Exception e) {
        
      }
  }
  public MimeMessage getMsg() {
    setMsg();
    return msg;
  }
  
  public void setMsg() {
    msg = new MimeMessage(session);
    System.out.println(" New Msg var generated ");
  }

  public void initRecipients(){
     try {
            mailTO = null;
            mailCC = null;
            mailBCC = null;
            msg.setRecipients(Message.RecipientType.BCC, mailBCC);
            msg.setRecipients(Message.RecipientType.CC, mailCC);
           msg.setRecipients(Message.RecipientType.TO, mailTO);
        } catch (MessagingException me) {
            // TODO: Add catch code
            me.printStackTrace();
        }
  }
 public HashMap send(String text) {
      HashMap maillog = new HashMap(); 
      try {
        if (msg == null)  
          setSender();
        
        msg.setHeader("X-Mailer", "Mail Send");
        msg.setSentDate(new Date());
        //msg.setContent(text, "text/html");
        msg.setSubject(subject);

          Multipart mp = new MimeMultipart();
        
        BodyPart bp = new MimeBodyPart();
        bp.setContent(msgText, contentType);
        mp.addBodyPart(bp);
        
          if(attachments != null) {
              for(int i=0; i < attachments.size(); i++) {
                    try {
                        MimeBodyPart mbp = new MimeBodyPart();
                        FileDataSource fds = new FileDataSource((String)attachments.get(i));
                        mbp.setDataHandler(new DataHandler(fds));
                        mbp.setFileName((String)fileName.get(i));
                        mp.addBodyPart(mbp);
                    } catch (MessagingException me) {
                        // TODO: Add catch code
                        me.printStackTrace();
                    }
                }    
          }
        
          
          msg.setContent(mp);
          
        //String mailTO = (new InternetAddress(msg.getRecipients(Message.RecipientType.TO))).toUnicodeString();
       /* msg.addRecipients(Message.RecipientType.TO, mailTO);
        msg.addRecipients(Message.RecipientType.CC, mailCC);
        msg.addRecipients(Message.RecipientType.BCC, mailBCC);*/
        // send the thing off
        System.out.println(" Sending... "+ new Date());  
        msg.saveChanges();  
          
        Transport transport = session.getTransport("smtp");
          //transport.connect(smtpHost, smtpPort, smtpUsr, smtpPwd);
        transport.connect(info.getSmtpHost(), info.getSmtpPort(), info.getSmtpUsr(), info.getSmtpPwd());
        transport.sendMessage(msg, msg.getAllRecipients());
        System.out.println(" Message Send "+ new Date());
        transport.close();
        maillog.put("STT","Y");
        maillog.put("MES","SEND");
        maillog.put("ERR","");
        String toMail = getRtnMailTo();
          if(toMail.length()>180)
            toMail = "bulkMails";
        maillog.put("SETTO",toMail); 
        String ccMail = getRtnMailCC();
          if(ccMail.length()>180)
            ccMail = "bulkMails";
        maillog.put("SETCC",ccMail);
        String bccMail = getRtnMailBCC();
          if(bccMail.length()>180)
            bccMail = "bulkMails";
        maillog.put("SETBCC",bccMail);
        mailTO = null;
        mailCC = null;
        mailBCC = null;
       
      }
      catch (NoSuchProviderException e) {
        e.printStackTrace();
          maillog.put("STT","N");
          maillog.put("MES","FAIL");
          maillog.put("ERR",String.valueOf(e.getClass()));
          maillog.put("SETTO",getRtnMailTo());  
          maillog.put("SETCC",getRtnMailCC());
          maillog.put("SETBCC",getRtnMailBCC());
      }
      catch (MessagingException e) {
        e.printStackTrace();
          maillog.put("STT","N");
          maillog.put("MES","FAIL");
          maillog.put("ERR",String.valueOf(e.getClass()));
          maillog.put("SETTO",getRtnMailTo());  
          maillog.put("SETCC",getRtnMailCC());
          maillog.put("SETBCC",getRtnMailBCC());
      }
      catch (Exception e) {
        e.printStackTrace();
          maillog.put("STT","N");
          maillog.put("MES","FAIL");
          maillog.put("ERR",String.valueOf(e.getClass()));
          maillog.put("SETTO",getRtnMailTo());  
          maillog.put("SETCC",getRtnMailCC());
          maillog.put("SETBCC",getRtnMailBCC());
      }
      return maillog;
  }
  
  public void setSender() {
    if(msg == null)
      setMsg();
    
    try {
      System.out.println("setting sender...");  
      //msg.setSender(new InternetAddress(senderId, senderNm));
      msg.setFrom(new InternetAddress(senderId, senderNm));
      System.out.println(" senderId :"+senderId + "+" + senderNm);
    }
    catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    catch (MessagingException e) {
      e.printStackTrace();
    }

  }
  
  public void setSender(String senderId, String senderNm) {
    this.senderId = nvl(senderId, this.senderId);
    this.senderNm = nvl(senderNm, this.senderNm);
    setSender();
  }
  
  public void setTO(String to) {
    try {
      //mailTO[mailTO.length] = new InternetAddress(to, false);
        msg.addRecipients(Message.RecipientType.TO, to);
        System.out.println(" Setting to :"+to);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setCC(String cc) {
    try {
      msg.addRecipients(Message.RecipientType.CC, cc);
        System.out.println(" Setting cc :"+cc);
      //mailCC[mailCC.length] = new InternetAddress(cc, false);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setBCC(String bcc) {
    try {
      msg.addRecipients(Message.RecipientType.BCC, bcc);
      //mailBCC[mailBCC.length] = new InternetAddress(bcc, false);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public String nvl(String s) {
    if (s == null)
      return "" ;
    else
      return s ;
  }

  public String nvl(String s, String ns) {
    if (s == null)
      return ns ;
    else
      return s ;
  }
  private static Properties loadParams( String file ) throws IOException {
    // Loads a ResourceBundle and creates Properties from it
    Properties prop = new Properties();
    ResourceBundle bundle = ResourceBundle.getBundle( file );

    Enumeration enum1 = bundle.getKeys();
    String key = null;

    while( enum1.hasMoreElements() ) {
      key = (String)enum1.nextElement();
      prop.put( key, bundle.getObject( key ) );
    }
    return prop;
  }


  public void setSubject(String subject) {
    this.subject = subject;
  }


  public String getSubject() {
    return subject;
  }


  public void setReplyTo(String replyTo) {
      try {
        msg.setReplyTo(new Address[]{new InternetAddress(replyTo)});
      }
      catch (Exception e) {
        e.printStackTrace();
      }
  }


  public String getReplyTo() {
    return replyTo;
  }

    public void setInfo(InfoMgr info) {
        this.info = info;
    }

    public InfoMgr getInfo() {
        return info;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    public String getMsgText() {
        return msgText;
    }

    public void setAttachments(ArrayList attachments) {
        this.attachments = attachments;
    }

    public ArrayList getAttachments() {
        return attachments;
    }
    
    public void SOP(String s) {
        System.out.println(s);
    }

    public void setSmtpPwd(String smtpPwd) {
        this.smtpPwd = smtpPwd;
    }

    public String getSmtpPwd() {
        return smtpPwd;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpUsr(String smtpUsr) {
        this.smtpUsr = smtpUsr;
    }

    public String getSmtpUsr() {
        return smtpUsr;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    public int getSmtpPort() {
        return smtpPort;
    }

    public void setFileName(ArrayList fileName) {
        this.fileName = fileName;
    }

    public ArrayList getFileName() {
        return fileName;
    }

    public void setAttachmentType(ArrayList attachmentType) {
        this.attachmentType = attachmentType;
    }

    public ArrayList getAttachmentType() {
        return attachmentType;
    }

    public String getRtnMailTo(){
        String rtnMail="";
        ArrayList rtnMailTo=new ArrayList();
        try {
            javax.mail.Address[] recipients = msg.getRecipients(Message.RecipientType.TO);
            if(recipients!=null){
            for(int i=0;i<recipients.length;i++){
            rtnMailTo.add(((InternetAddress) recipients[i]).getAddress());
            }
            }
        }
        catch (Exception e) {
          e.printStackTrace();
        }
        rtnMail=rtnMailTo.toString();
        rtnMail = rtnMail.replaceAll("\\[", "");
        rtnMail = rtnMail.replaceAll("\\]", "");
        return rtnMail;
      }

    public String getRtnMailCC() {
        String rtnMail="";
        ArrayList rtnMailCC=new ArrayList();
        try {
            javax.mail.Address[] recipients = msg.getRecipients(Message.RecipientType.CC);
            if(recipients!=null){
            for(int i=0;i<recipients.length;i++){
            rtnMailCC.add(((InternetAddress) recipients[i]).getAddress());
            }
            }
        }
        catch (Exception e) {
          e.printStackTrace();
        }
        rtnMail=rtnMailCC.toString();
        rtnMail = rtnMail.replaceAll("\\[", "");
        rtnMail = rtnMail.replaceAll("\\]", "");
        return rtnMail;
    }

    
    public String getRtnMailBCC() {
        String rtnMail="";
        ArrayList rtnMailBCC=new ArrayList();
        try {
            javax.mail.Address[] recipients = msg.getRecipients(Message.RecipientType.BCC);
            if(recipients!=null){
            for(int i=0;i<recipients.length;i++){
            rtnMailBCC.add(((InternetAddress) recipients[i]).getAddress());
            }
            }
        }
        catch (Exception e) {
          e.printStackTrace();
        }
        rtnMail=rtnMailBCC.toString();
        rtnMail = rtnMail.replaceAll("\\[", "");
        rtnMail = rtnMail.replaceAll("\\]", "");
        return rtnMail;
    }

    class MailAuthenticator extends Authenticator {
    public MailAuthenticator() {
    }        
    public PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication("username", "password");
    }
}
}
