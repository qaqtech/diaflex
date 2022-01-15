package ft.com.masters;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.FormsUtil;
import ft.com.InfoMgr;
import ft.com.dao.UIForms;
import ft.com.generic.GenericImpl;
import ft.com.marketing.StockTallyForm;

import java.io.IOException;

import java.sql.Connection;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class HolidayAction extends DispatchAction {

    public HolidayAction() {
        super();
    }
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
            util.updAccessLog(req,res,"holiday Page", "load start");
        HolidayForm udf = (HolidayForm)af;
        ResultSet rs = null;
        String    memoPrntOptn = "select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and "
                                 + "  b.mdl = 'JFLEX' and b.nme_rule =  'HLIDY_TYP' and a.til_dte is null order by a.srt_fr ";

        rs = db.execSql("memoPrint", memoPrntOptn, new ArrayList());
       ArrayList holidayLst = new ArrayList();
        while (rs.next()) {
            UIForms holiDay = new UIForms();

            holiDay.setFORM_NME(rs.getString("chr_fr"));
            holiDay.setFORM_TTL(rs.getString("dsc"));
            holidayLst.add(holiDay);
        }
        rs.close();
        srchHL(req , new HashMap());
       udf.setHolidayList(holidayLst);
            util.updAccessLog(req,res,"holiday Page", "load end");
        return am.findForward("load");
        }
    }
    
    public ActionForward save(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
           util.updAccessLog(req,res,"holiday Page", "save start");
       HolidayForm udf = (HolidayForm)af;
        String frmDte = util.nvl((String)udf.getValue("frmDte"));
        String toDte = util.nvl((String)udf.getValue("toDte"));
        String typ = util.nvl((String)udf.getValue("typ"));
        String rmk = util.nvl((String)udf.getValue("rmk"));
       String add = util.nvl((String)udf.getValue("add"));
       String view = util.nvl((String)udf.getValue("view"));
       HashMap param = new HashMap();
       if(!add.equals("")){
           if(!frmDte.equals("") && !typ.equals("0")){
        String addHoliday = "HOLIDAY_PKG.GEN_RNG(pFrmDte => ?, pToDte => ?, pTyp => ? , pRem => ?)";
        ArrayList ary = new ArrayList();
        ary.add(frmDte);
        ary.add(toDte);
        ary.add(typ);
        ary.add(rmk);
        int ct = db.execCall("holiday", addHoliday, ary);
           }
       }else{
           param.put("frmDte",frmDte);
           param.put("toDte",toDte);
           param.put("typ",typ);
           param.put("rmk",rmk);
       }
      
        udf.reset();
        srchHL(req , param);
           util.updAccessLog(req,res,"holiday Page", "save end");
       return am.findForward("load");
       }
   }
    public ActionForward delete(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
             util.updAccessLog(req,res,"holiday Page", "delete start");
        String idn = req.getParameter("idn");
         HashMap param = new HashMap();
        String updateHoli = "update co_holidays set stt='IA' where idn=?";
            ArrayList ary  = new ArrayList();
            ary.add(idn);
            int ct = db.execUpd("dlete", updateHoli, ary);
            srchHL(req , param);
             util.updAccessLog(req,res,"holiday Page", "delete end");
         return am.findForward("load");
         }
     }
    public ArrayList srchHL( HttpServletRequest req , HashMap params)
            throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList ary = new ArrayList();
        String holidayList = "select to_char(holiday_dte,'dd-Mon-rrrr') hldy, a.idn , a.typ, c.dsc, a.rem from co_holidays a ,   mrule b , rule_dtl c " + 

        "where  a.stt='A'and c.rule_idn = b.rule_idn and " + 
        "b.mdl = 'JFLEX' and b.nme_rule = 'HLIDY_TYP' and a.typ = c.chr_fr ";
        if(params.size()>0){
            String frmDte = util.nvl((String)params.get("frmDte"));
            String toDte = util.nvl((String)params.get("toDte"));
            if(!frmDte.equals("") && !toDte.equals(""))
                 holidayList =  holidayList+" and trunc(holiday_dte) between to_date('"+frmDte+"', 'dd-mm-rrrr') and to_date('"+toDte+"', 'dd-mm-rrrr') ";
            String typ = util.nvl((String)params.get("typ"));
            if(!typ.equals("0")){
                ary.add(typ);
                holidayList =  holidayList+" and a.typ = ? " ;
            }
            String rmk = util.nvl((String)params.get("rmk"));
            if(!rmk.equals("")){
                ary.add(rmk);
                holidayList =  holidayList+" and upper(a.rem) =  upper(?) " ;
            }
            
        }
        holidayList = holidayList +" order by holiday_dte desc";
        ResultSet rs = db.execSql("holidayList", holidayList, ary);
        ArrayList holidList  = new ArrayList();
        while(rs.next()){
            ArrayList holid = new ArrayList();
            holid.add(util.nvl(rs.getString("hldy")));
            holid.add(util.nvl(rs.getString("dsc")));
            holid.add(util.nvl(rs.getString("rem")));
            holid.add(util.nvl(rs.getString("idn")));
            holidList.add(holid);
        }
        rs.close();
        req.setAttribute("holidayList", holidList);
      return holidList;
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
                util.updAccessLog(req,res,"holiday Page", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"holiday Page", "init");
            }
            }
            return rtnPg;
         }
}
