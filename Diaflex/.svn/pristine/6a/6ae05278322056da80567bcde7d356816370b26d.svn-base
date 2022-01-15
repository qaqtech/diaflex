package ft.com.lab;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.ExcelUtilObj;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import java.io.PrintWriter;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletContext;

public class LabIssueResSttAction extends DispatchAction{
    
    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
                  throws Exception {
                  HttpSession session = req.getSession(false);
                  InfoMgr info = (InfoMgr)session.getAttribute("info");
                  DBUtil util = new DBUtil();
                  DBMgr db = new DBMgr();
                  String rtnPg="sucess";
                  if(info!=null){
                  Connection conn=info.getCon();
                  if(conn!=null){
                  db.setCon(info.getCon());
                  util.setDb(db);
                  util.setInfo(info);
                  db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
                  util.setLogApplNm(info.getLoApplNm());
                  rtnPg=init(req,res,session,util);
                  }else{
                  rtnPg="connExists";
                  }
                  }else
                  rtnPg="sessionTO";
                  if(!rtnPg.equals("sucess")){
                      return am.findForward(rtnPg);
                  }else{
                   util.updAccessLog(req,res,"Lab Issue Status Report", "fetch start");
                   LabIssueResSttForm frm = (LabIssueResSttForm)af;    
                   HashMap ftLst =  new HashMap();
                   labIssueRpt(req,res ,new HashMap());
                   frm.reset();
                   util.updAccessLog(req,res,"Lab Issue Status Report", "fetch end");       
                  return am.findForward("load");
               }
              }
    public ActionForward fatch(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
                  throws Exception {
                  HttpSession session = req.getSession(false);
                  InfoMgr info = (InfoMgr)session.getAttribute("info");
                  DBUtil util = new DBUtil();
                  DBMgr db = new DBMgr();
                  String rtnPg="sucess";
                  if(info!=null){
                  Connection conn=info.getCon();
                  if(conn!=null){
                  db.setCon(info.getCon());
                  util.setDb(db);
                  util.setInfo(info);
                  db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
                  util.setLogApplNm(info.getLoApplNm());
                  rtnPg=init(req,res,session,util);
                  }else{
                  rtnPg="connExists";
                  }
                  }else
                  rtnPg="sessionTO";
                  if(!rtnPg.equals("sucess")){
                      return am.findForward(rtnPg);
                  }else{
                   util.updAccessLog(req,res,"Lab Issue Status Report", "fetch start");
                   LabIssueResSttForm frm = (LabIssueResSttForm)af;
                   HashMap ftLst = new HashMap();
                   String frmDte = util.nvl((String)frm.getValue("dtefr"));
                   String todte = util.nvl((String)frm.getValue("dteto"));
                   String resstt = util.nvl((String)frm.getValue("resstt"));
                   String IssueId =  util.nvl((String)frm.getValue("issuid"));
                   if(!frmDte.equals("") && !todte.equals("")){
                   ftLst.put("dtefr",frmDte);
                   ftLst.put("dteto",todte);
                   }
                   if(!resstt.equals(""))
                   ftLst.put("resstt",resstt);
                   if(!IssueId.equals("0") && !IssueId.equals(""))
                   ftLst.put("issuid",IssueId);               
                   labIssueRpt(req,res ,ftLst);
                   frm.reset();
                   util.updAccessLog(req,res,"Lab Issue Status Report", "fetch end");       
                  return am.findForward("load");
               }
              }
    
    public ActionForward DownloadXml(ActionMapping am, ActionForm af, HttpServletRequest request, HttpServletResponse res)
                  throws Exception {
                  HttpSession session = request.getSession(false);
                  InfoMgr info = (InfoMgr)session.getAttribute("info");
                  DBUtil util = new DBUtil();
                  DBMgr db = new DBMgr();
                  String rtnPg="sucess";
                  if(info!=null){
                  Connection conn=info.getCon();
                  if(conn!=null){
                  db.setCon(info.getCon());
                  util.setDb(db);
                  util.setInfo(info);
                  db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
                  util.setLogApplNm(info.getLoApplNm());
                  rtnPg=init(request,res,session,util);
                  }else{
                  rtnPg="connExists";
                  }
                  }else
                  rtnPg="sessionTO";
                  if(!rtnPg.equals("sucess")){
                      return am.findForward(rtnPg);
                  }else{
                    
                      String issId= request.getParameter("issID");
                      HashMap dbInfoSys = info.getDmbsInfoLst();
                      String usrNme = (String)dbInfoSys.get("GIA_USER_NAME");
                      String clientId = (String)dbInfoSys.get("GIA_CLIENT_ID");
                    
                    
                      String getStr = "select nvl(rtn_val,iss_val) loc , b.num , a.iss_stk_idn \n" + 
                      "from  iss_rtn_prp a ,iss_rtn_dtl c, prp b \n" + 
                      "where c.iss_id=?  and a.ISS_ID=c.ISS_ID and a.iss_stk_idn=c.iss_stk_idn and c.stt in ('IS','CL') \n" + 
                      "and a.mprp in ('LAB_LOC') and a.mprp=b.mprp and b.val=a.rtn_val   "  ;
                      ArrayList<String>  params = new ArrayList<String>();
                      params.add(issId);
                      ArrayList rsLst = db.execSqlLst("lab_loc", getStr, params);
                      PreparedStatement pst =(PreparedStatement)rsLst.get(0);
                      ResultSet  rs = (ResultSet)rsLst.get(1);
                      String inXml = "";
                      while(rs.next()){
                          String stkIdn = rs.getString("iss_stk_idn");
                          if(inXml.equals("")){
                                  int siteId = rs.getInt("num");
                                  inXml="<?xml version=\"1.0\" encoding=\"UTF8\"?>" + 
                              "<CREATE_JOB_FOR_INTAKE_REQUEST>" + 
                              "<HEADER>" + 
                              "<USER_NAME>"+usrNme+"</USER_NAME>" + 
                              "<CLIENT_ID>"+clientId+"</CLIENT_ID>" + 
                              "</HEADER>" + 
                              "<BODY>" + 
                              "<SITE_ID>"+siteId+"</SITE_ID>" + 
                              "<JOBS>";
                          }
                          String autoFlg="N";
                        String autoRes = "select nvl(rtn_val,iss_val) iss_val from iss_rtn_prp where mprp='AUTO_RES' and iss_stk_idn=?  and iss_id=?  ";
                          params = new ArrayList<String>();
                         
                          params.add(stkIdn);
                          params.add(issId);
                         rsLst = db.execSqlLst("autoRes", autoRes, params);
                         PreparedStatement pst1  =(PreparedStatement)rsLst.get(0);
                         ResultSet rs1 = (ResultSet)rsLst.get(1);
                         if(rs1.next()){
                          autoFlg = db.nvl(rs1.getString("iss_val"));
                          }
                          rs1.close();
                          pst1.close();
                      String jobstr ="select b.cert_no,b.vnm,to_char(b.cts,'999990.99') cts, decode(d.dta_typ,'C',e.rap_val,'N',c.iss_num,c.txt) val ,rp.flg,\n" + 
                      "nvl(b.cmp,b.upr) upr, c.mprp , c.iss_stk_idn,b.tfl3,m.rap_prp xmlVal \n" + 
                      " from iss_rtn_dtl a , mstk b , iss_rtn_prp c , mprp d , GIA_SYNC_PRP_VALS e ,GIA_SYNC_PRP_MAP m  , rep_prp rp\n" + 
                      " where a.iss_stk_idn=b.idn and c.iss_stk_idn = b.idn  and c.iss_id=a.iss_id and a.iss_id=? \n" + 
                      " and c.mprp = rp.prp  and d.prp=c.mprp and e.mprp = c.mprp and c.mprp=m.mprp  and rp.mdl='LAB_XML_PRP' and  rp.flg in ('Y','A')\n" + 
                      " and decode(d.dta_typ,'C',c.rtn_val,'N',c.iss_num,c.txt)=decode(d.dta_typ,'C',e.val,'N',c.iss_num,c.txt)\n" + 
                      " and a.iss_stk_idn=? and e.flg='I'\n";
                      
                      
                      params = new ArrayList<String>();
                      params.add(issId);
                      params.add(stkIdn);
                      
                      rsLst = db.execSqlLst("jobstr", jobstr, params);
                       pst1  =(PreparedStatement)rsLst.get(0);
                       rs1 = (ResultSet)rsLst.get(1);
                      inXml=inXml+"<JOB><CONTROL_NUMBER>CONROL</CONTROL_NUMBER>";
                      String certNo=null;
                      String vnm="";
                      while(rs1.next()){
                        
                          String xmlVal = db.nvl(rs1.getString("xmlVal"));
                          String val = db.nvl(rs1.getString("val"));
                          String tfl3 = db.nvl(rs1.getString("tfl3"));
                          vnm = db.nvl(rs1.getString("vnm"));
                          if(xmlVal.equals("STATED_VALUE")){
                                  String cts = db.nvl(rs1.getString("cts"),"0");
                                  double value = Math.round(Double.parseDouble(val)*Double.parseDouble(cts));
                                  val = String.valueOf(value);
                          }
                          inXml = inXml.replace("CONROL", tfl3);
                          String flg = db.nvl(rs1.getString("flg"));
                       if(flg.equals("A")){
                           if(autoFlg.equals("Y")){
                                 if(val.equals("NA"))
                                 inXml=inXml+"<AR_"+xmlVal+"></AR_"+xmlVal+">";
                                 else
                                 inXml=inXml+"<AR_"+xmlVal+">"+val+"</AR_"+xmlVal+">";
                           }
                       }else{
                            if(val.equals("NA"))
                            inXml=inXml+"<"+xmlVal+"/>";
                            else
                            inXml=inXml+"<"+xmlVal+">"+val+"</"+xmlVal+">";
                       }
                      certNo = db.nvl(rs1.getString("cert_no"));
                         
                                  
                      }
                          rs1.close();
                          pst1.close();
                      inXml= inXml+ "<CLIENT_REF_NO>"+vnm+"</CLIENT_REF_NO>"+
                             "<SUB_CLIENT_NO>"+clientId+"</SUB_CLIENT_NO>";
                          if(certNo.length()>3){
                                  inXml= inXml+ "<PREVIOUS_REPORT_NO>"+certNo+"</PREVIOUS_REPORT_NO>";
                          }else{
                                  inXml= inXml+ "<PREVIOUS_REPORT_NO/>";
                          }
                             
                          inXml= inXml+" <DESCRIPTION/> "+
                             "<SEPERATION/>"+
                             "<QUANTITY/>"+
                             "<COLOR/>"+
                             "<CLARITY/>"+
                             "<POLISH/>"+
                             "<SYMMETRY/>"+
                            
                            
                            " <STATED_MATERIAL/>"+
                             "<ITEM_CATEGORY/>"+
                             "<ITEM_DESCRIPTION/>"+
                             "<ADDITIONAL_GEMPASSES/>"+
                             "<IDENT_NO_OF_STONES/>"+
                             "<IDENT_COLOR/>";
                          if(certNo.length()>3){
                                     inXml= inXml+"<AR_FLAG>N</AR_FLAG>";
                        
                             }
                          
                          if(autoFlg.equals("Y")){
                              inXml= inXml+"<AR_FLAG>Y</AR_FLAG>";
                          }
                             inXml= inXml+"</JOB>";
                           
                      }

                      rs.close(); pst.close();
                      inXml = inXml +"</JOBS></BODY></CREATE_JOB_FOR_INTAKE_REQUEST>";
                                    
                            
                      String fileName = "AutoLabIssueXml_"+issId+".xml";    
                     
                      PrintWriter out = res.getWriter();
                      res.setContentType("application/xml");
                      res.setHeader("Content-disposition","attachment;filename="+fileName);
                      
                      out.println(inXml);  
                      
                      
                      out.flush();
                      out.close();   
                     
                      
                      
                      
                      return am.findForward("load");
                  }
                }
    
    public void  labIssueRpt(HttpServletRequest req,HttpServletResponse res ,HashMap fatchLst){
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            String rsltQ ="";
            if(fatchLst!=null && fatchLst.size()>0){
            String frmDte = util.nvl((String)fatchLst.get("dtefr"));
            String todte = util.nvl((String)fatchLst.get("dteto"));
            String resstt = util.nvl((String)fatchLst.get("resstt"));
            String IssueId = util.nvl((String)fatchLst.get("issuid"));
                if(!resstt.equals("")){
                rsltQ+= " and a.res_stt='"+resstt+"' ";
                }
                if(!IssueId.equals("0") && !IssueId.equals("")){
                rsltQ+= " and a.iss_id ="+IssueId;
                }
                if(!frmDte.equals("") && !todte.equals("")){
                rsltQ+= " and trunc(a.iss_dt ) between to_date('"+frmDte+"' , 'dd-mm-rrrr') and to_date('"+todte+"' , 'dd-mm-rrrr') ";
                }
            }else{
            rsltQ+= "and trunc(a.iss_dt)=to_date(sysdate , 'dd-mm-rrrr') ";
            }   
            rsltQ+= " order by a.iss_id ";
           
            String  selQ =" select a.iss_id ,to_char(a.iss_dt , 'dd-mm-rrrr') iss_dt , res_stt, res_err,res_msg from iss_rtn a , mprc b \n" +
                          " where a.prc_id=b.idn and b.is_stt in ('LB_AU_IS','LB_AURI_IS') "+rsltQ ; 
                     
              ArrayList issueList = new ArrayList();
            
            try{

                ArrayList outLst = db.execSqlLst("Rpt Lab Issue Status ", selQ, new ArrayList());
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
                while (rs.next()) {
                 HashMap dtlLst = new HashMap();
                 dtlLst.put("IssueId",util.nvl((String)rs.getString("iss_id")));
                 dtlLst.put("IssueDate",util.nvl((String)rs.getString("iss_dt")));
                 dtlLst.put("ResStatus",util.nvl((String)rs.getString("res_stt")));
                 dtlLst.put("ResError",util.nvl((String)rs.getString("res_err")));
                 dtlLst.put("ResMsg",util.nvl((String)rs.getString("res_msg")));
                 issueList.add(dtlLst);
             }
                rs.close(); pst.close();
            }catch(SQLException e){
              e.printStackTrace();                        
             }
            req.setAttribute("issueSttLst", issueList);
        
        }
    public String init(HttpServletRequest req , HttpServletResponse res,HttpSession session,DBUtil util) {
            String rtnPg="sucess";
            String invalide="";
            String connExists=util.nvl(util.getConnExists());  
            if(!connExists.equals("N"))
            invalide=util.nvl(util.chkTimeOut(),"N");
            if(session.isNew())
            rtnPg="sessionTO";    
            if(connExists.equals("N"))
            rtnPg="connExists";     
            if(invalide.equals("Y"))
            rtnPg="chktimeout";
            if(rtnPg.equals("sucess")){
            boolean sout=util.getLoginsession(req,res,session.getId());
            if(!sout){
            rtnPg="sessionTO";
            System.out.print("New Session Id :="+session.getId());
            }else{
                rtnPg=util.checkUserPageRights("",util.getFullURL(req));
                if(rtnPg.equals("unauthorized"))
                util.updAccessLog(req,res,"Lab Issue Status Report", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Lab Issue", "init");
            }
            }
            return rtnPg;
            }
    public LabIssueResSttAction() {
        super();
    }
}
