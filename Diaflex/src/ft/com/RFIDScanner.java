package ft.com;



import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;



import java.util.ArrayList;

import java.util.HashMap;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

public class RFIDScanner extends DispatchAction {
  
    DBMgr db = null;
    InfoMgr info = null ;
    DBUtil util = null;   
    HttpSession session = null;
    
    public RFIDScanner() {
    super();
    }
    
  public ActionForward start(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
      SOP("Enter in action ");
           init(req, res);
           String rfid_seq ="";
           String dvcId = util.nvl(req.getParameter("dvc"));
           String typ = util.nvl(req.getParameter("typ"));
           long start = System.currentTimeMillis();

           String seqNme = "select RFID_BATCH_SEQ.nextval from dual";
      ArrayList outLst = db.execSqlLst("rfID", seqNme, new ArrayList());
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
           if(rs.next())
              rfid_seq = rs.getString(1);
      rs.close();
      pst.close();
               
            String pmsg="SCAN : "+rfid_seq+" : "+dvcId;
            if(typ.equals("AUTOSCAN")){
              pmsg="AUTO : "+rfid_seq+" : "+dvcId;
              info.setRfid_seq(rfid_seq);
            }
           if(typ.equals("STOPAUTOSCAN")){
              rfid_seq = info.getRfid_seq();
              pmsg="AUTOSTOP : "+rfid_seq+" : "+dvcId;
              info.setRfid_seq("");
           }
           StringBuffer sb = new StringBuffer();
       
           ArrayList out = new ArrayList();
           ArrayList params = new ArrayList();
           params.add(pmsg);
           int ct = db.execCall("dpp_rfid", "dpp_rfid.p_enqueue(msg => ?)", params);
          if(typ.equals("SCAN") || typ.equals("STOPAUTOSCAN")){
           if(ct>0){
           String outMsg="";
           String rfIdN0="";
           boolean  continueLoop = true;
           while(continueLoop){
              
                params = new ArrayList();
               params.add(dvcId);
               params.add(rfid_seq);
               out = new ArrayList();
               out.add("V");
               CallableStatement cts = db.execCall("dpp_Rfid","dpp_Rfid.p_res_dequeue(p_dev => ? , p_seq => ? ,  p_msg => ?  )", params, out);
               String outM = util.nvl(cts.getString(params.size()+1));
               String[] outStr = outM.split(":");
//               System.out.println("Out MSG:"+outM);
               if(outStr.length >1){
                outMsg = outStr[0];
                rfIdN0 = util.nvl(outStr[1]).trim();
//                System.out.println("1 str Out MSG:"+outMsg+" :RFBATCHID:"+rfIdN0);
               }
               cts.close();
              if ((outMsg.indexOf("DONE") > -1 || outMsg.indexOf("AUTO") > -1 ) && rfIdN0.equals(rfid_seq)) {
                   continueLoop = false;
//                  System.out.println("LooP Stop:");
              } else{
//                  System.out.println("Thread sleep:");

                    try {
                        
                        Thread.sleep(5000);


                    } catch (InterruptedException ie) {
                        // TODO: Add catch code
                        ie.printStackTrace();
                    }
                  
              }

               long time = System.currentTimeMillis() - start;
               if(time>=60000){
//               System.out.println("EXIT");
               break;
               }

           }}
          
           String RFID_batch = "select b.vnm from RFID_batch a , mstk b where to_char(a.idn) = b.tfl3 and a.RFID_SEQ = ?";
           params = new ArrayList();
           params.add(rfid_seq);
               outLst = db.execSqlLst("RFID_batch", RFID_batch, params);
               pst = (PreparedStatement)outLst.get(0);
               rs = (ResultSet)outLst.get(1);
           while(rs.next()){
                sb.append("<packets>");
                sb.append("<id>" + util.nvl(rs.getString("vnm")) + "</id>");
                sb.append("</packets>");
              
           }
               rs.close();
               pst.close();
           }
            res.setContentType("text/xml");
            res.setHeader("Cache-Control", "no-cache");
            res.getWriter().write("<list>" + sb.toString() + "</list>");
           
         
       
    return null;
  }    
  
    public void init(HttpServletRequest req , HttpServletResponse res) {
        session = req.getSession(false);
        info = (InfoMgr)session.getAttribute("info");
        util = new DBUtil();
        db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    }
  public void SOP(String s) {
    System.out.println("@rfid " + new Date() + s);
  }
}

   

