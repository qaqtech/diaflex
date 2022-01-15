package ft.com.masters;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.FormsUtil;
import ft.com.InfoMgr;
import ft.com.dao.GenDAO;
import ft.com.dao.UIForms;
import ft.com.dao.UIFormsFields;

import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class HappyHoursAction extends DispatchAction {
    public HappyHoursAction() {
        super();
    }
    public ActionForward save(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg="sucess";
        if(info!=null){  
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        rtnPg=init(req,res,session,util);
        }else
        rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
            util.updAccessLog(req,res,"Happy Hours form", "save start");
            HappyHoursForm udf=(HappyHoursForm)af ;
            ArrayList ary= new ArrayList();
           ArrayList idnList = (ArrayList)session.getAttribute("idnList");
            int fldsize=3;
            String biddte="",startdte="",starttm="",enddte="",endtm="" ,stt="",bidtm="",issdt="";
            if (udf.getAddnew() != null) {
             for(int i=0;i<fldsize;i++)  {
             biddte =util.nvl((String)udf.getValue("biddte_"+i));
             bidtm =util.nvl((String)udf.getValue("bidtm_"+i));
             startdte =util.nvl((String)udf.getValue("startdte_"+i));
             starttm =util.nvl((String)udf.getValue("starttm_"+i));
             enddte =util.nvl((String)udf.getValue("enddte_"+i));
             endtm =util.nvl((String)udf.getValue("endtm_"+i));
             stt =util.nvl((String)udf.getValue("stt_"+i));
             issdt =util.nvl((String)udf.getValue("issdt_"+i));
                 
           if(!biddte.equals("") && !startdte.equals("") && !enddte.equals("") ) {
             biddte =biddte+" "+bidtm ;
            startdte =startdte+" "+starttm ;
            enddte =enddte+" "+endtm;
             ary= new ArrayList();
                String insertBid="insert into HH_TIMER(idn, start_dte,end_dte,order_dte, sys_dte,stt,mod_dte,df_usr) " +
                    " select HH_TIMER_SEQ.nextval, to_date(?,'DD-MM-YYYY HH24:MI:SS') ,to_date(?,'DD-MM-YYYY HH24:MI:SS'),to_date(?,'DD-MM-YYYY HH24:MI:SS'), to_date(?,'DD-MM-YYYY HH24:MI:SS'),sysdate ,? ,?, ? from dual";
                ary.add(startdte);
                ary.add(enddte);
                ary.add(biddte);
                ary.add(issdt);
                ary.add(stt);
                ary.add("");
                ary.add(info.getDfNme());
             int ct = db.execDirUpd("BID INSERT" ,insertBid, ary);
            if(ct>0)
            req.setAttribute("MSG","Bid Time Added Successfully....");
            }
            }
            }
            if (udf.getModify() != null) {
               fldsize =idnList.size();
             for(int i=0;i<fldsize;i++)  {
             String Idn=(String)idnList.get(i);
             biddte =util.nvl((String)udf.getValue("biddte_"+Idn));
             bidtm =util.nvl((String)udf.getValue("bidtm_"+Idn));
             startdte =util.nvl((String)udf.getValue("startdte_"+Idn));
             starttm =util.nvl((String)udf.getValue("starttm_"+Idn));
             enddte =util.nvl((String)udf.getValue("enddte_"+Idn));
             endtm =util.nvl((String)udf.getValue("endtm_"+Idn));
             stt =util.nvl((String)udf.getValue("stt_"+Idn));
             issdt =util.nvl((String)udf.getValue("issdt_"+Idn));
            
            if(!biddte.equals("") && !startdte.equals("") && !enddte.equals("") ) {
                biddte =biddte+" "+bidtm ;
                startdte =startdte+" "+starttm ;
                enddte =enddte+" "+endtm;
                ary= new ArrayList();
                String isChecked = util.nvl((String) udf.getValue("upd_" + Idn));

                if (isChecked.length() > 0) {
               String updDate="update HH_TIMER set start_dte = to_date(?,'DD-MM-YYYY HH24:MI:SS') , end_dte=to_date(?,'DD-MM-YYYY HH24:MI:SS') ,order_dte =to_date(?,'DD-MM-YYYY HH24:MI:SS') , iss_dt = to_date(?,'DD-MM-YYYY HH24:MI:SS'), sys_dte=sysdate ,stt = ?, mod_dte=sysdate,df_usr =? where idn= ? ";
                 ary.add(startdte);
                 ary.add(enddte);
                 ary.add(biddte);
                 ary.add(issdt);
                 ary.add(stt);
                 ary.add(info.getDfNme());
                 ary.add(Idn);
              int ct = db.execDirUpd(" upd HH_TIMER ",updDate, ary);
                 if(ct>0)
                 req.setAttribute("MSG","Updated Successfully....");
             }
            }
             }
            }
            if (udf.getDelete() != null) {
                    fldsize =idnList.size();
                    String lIdnlst="";
                    for(int i=0;i<fldsize;i++)  {
                    String Idn=(String)idnList.get(i);
                    biddte =util.nvl((String)udf.getValue("biddte_"+Idn));
                     bidtm =util.nvl((String)udf.getValue("bidtm_"+Idn));
                    startdte =util.nvl((String)udf.getValue("startdte_"+Idn));
                    starttm =util.nvl((String)udf.getValue("starttm_"+Idn));
                    enddte =util.nvl((String)udf.getValue("enddte_"+Idn));
                    endtm =util.nvl((String)udf.getValue("endtm_"+Idn));
                    stt =util.nvl((String)udf.getValue("stt_"+Idn));
                    issdt =util.nvl((String)udf.getValue("issdt_"+Idn));
                    
                    if(!biddte.equals("") && !startdte.equals("") && !enddte.equals("") ) {
                     biddte =biddte+" "+bidtm ;
                     startdte =startdte+" "+starttm ;
                     enddte =enddte+" "+endtm;
                     ary= new ArrayList();
                     String isChecked = util.nvl((String) udf.getValue("upd_" + Idn));

                        if (isChecked.length() > 0) {
                            lIdnlst=lIdnlst+","+Idn;
                        }
                        if(!lIdnlst.equals("")){
                            lIdnlst=lIdnlst.replaceFirst("\\,", "");
                    int ct = db.execUpd(" del HH Timer ", "update HH_TIMER set stt='IA' where idn in("+lIdnlst+")", new ArrayList());
                          if(ct>0)
                          req.setAttribute("MSG","Deleted Successfully....");
                      }
                    }
                 }
                }
            util.updAccessLog(req,res,"Happy Hours form", "save end");
        return load(am, af, req, res);
        }
    }



    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg="sucess";
        if(info!=null){  
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        rtnPg=init(req,res,session,util);
        }else
        rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
            util.updAccessLog(req,res,"Happy Hours form", "load start");
            HappyHoursForm form=(HappyHoursForm)af ;
            ArrayList idnList =new ArrayList();
            form.reset();
            String    getDate = " select idn, to_char(start_dte,'DD-MM-YYYY HH24:MI:SS') startdte ,to_char(end_dte,'DD-MM-YYYY HH24:MI:SS') enddte,to_char(order_dte,'DD-MM-YYYY HH24:MI:SS')  biddte , to_char(iss_dt,'DD-MM-YYYY HH24:MI:SS') issdt \n" + 
            " , stt from HH_TIMER  where stt='A' order by idn desc ";
            ResultSet rs      = db.execSql("get Last Bid update", getDate, new ArrayList());
 
            while (rs.next()) {
                String idn= util.nvl((String)rs.getString("idn"));
                String startdate= util.nvl((String)rs.getString("startdte"));
                        startdate=startdate.replace(" ", ",");
                String enddte= util.nvl((String)rs.getString("enddte"));
                       enddte=enddte.replace(" ", ",");
                String biddte=  util.nvl((String)rs.getString("biddte"));
                       biddte=biddte.replace(" ", ",");
                String issdt=  util.nvl((String)rs.getString("issdt"));
                       issdt=issdt.replace(" ", ",");
                       
                String[] biddteLst = biddte.split(",");
                String[] endLst = enddte.split(",");
                String[] dtLst = startdate.split(",");
                String[] issdtLst = issdt.split(",");
                form.setValue("startdte_"+idn, (String)dtLst[0]);
                form.setValue("starttm_"+idn, (String)dtLst[1]);
                form.setValue("enddte_"+idn, (String)endLst[0]);
                form.setValue("endtm_"+idn, (String)endLst[1]);
                form.setValue("biddte_"+idn, biddteLst[0]);
                form.setValue("bidtm_"+idn, biddteLst[1]);
                form.setValue("stt_"+idn,util.nvl((String)rs.getString("stt")));
                form.setValue("issdt_"+idn,issdtLst[0]);
                idnList.add(idn);
            }
            rs.close();
            session.setAttribute("idnList", idnList);
            util.updAccessLog(req,res,"Happy Hours form", "load end");

        return am.findForward("load");
        }
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
                util.updAccessLog(req,res,"Activity form", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Activity form", "init");
            }
            }
            return rtnPg;
         }
}
