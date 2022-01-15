package ft.com.mix;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.PdfforReport;
import ft.com.assort.AssortIssueForm;
import ft.com.assort.AssortIssueImpl;
import ft.com.assort.AssortIssueInterface;
import ft.com.dao.MNme;
import ft.com.dao.Mprc;
import ft.com.marketing.PacketPrintForm;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class LotPricingAction extends DispatchAction {
    
    public ActionForward load(ActionMapping am, ActionForm af,
                              HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
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
        util.updAccessLog(req,res,"Lot Princing", "load");
        return am.findForward("load");
      }
    }

    public ActionForward fecth(ActionMapping am, ActionForm form,
                               HttpServletRequest req,
                               HttpServletResponse res) throws Exception {
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
        util.updAccessLog(req,res,"Lot Princing", "fecth");
        LotPricingForm udf = (LotPricingForm)form;
        String memoID = util.nvl((String)udf.getValue("memoLst"));
        String pricing = util.nvl((String)udf.getValue("pricing"));
        String pricingMake = util.nvl((String)udf.getValue("pricing+make"));
        if (!memoID.equals("")) {
            memoID = util.getVnm(memoID);

            if (!pricingMake.equals("")) {
                String updateQ =
                    "update mlot set comp_dte=sysdate where dsc in(" + memoID +
                    ")";
                int ct = db.execUpd("Update", updateQ, new ArrayList());
            }

            ArrayList memoLst = new ArrayList();
            HashMap memoIdnLst = new HashMap();
            String memoQ =
                "select idn,dsc,mfg_av,mkt_av,acc_av from mlot where dsc in(" +
                memoID + ") and comp_dte is not null";
          ArrayList  rsLst = db.execSqlLst("Memo ID", memoQ, new ArrayList());
          PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
          ResultSet  rs =(ResultSet)rsLst.get(1);
            
            try {
                while (rs.next()) {
                    String memo = util.nvl(rs.getString("dsc"));
                    memoLst.add(memo);
                    memoIdnLst.put(memo, util.nvl(rs.getString("idn")));
                    udf.setValue("SURT_" + memo,
                                 util.nvl(rs.getString("mfg_av")));
                    udf.setValue("POL_" + memo,
                                 util.nvl(rs.getString("mkt_av")));
                    udf.setValue("ACC_" + memo,
                                 util.nvl(rs.getString("acc_av")));
                }
                rs.close();
              stmt.close();
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }

            session.setAttribute("memoLst", memoLst);
            session.setAttribute("memoIdnLst", memoIdnLst);
            req.setAttribute("view", "Y");
          util.updAccessLog(req,res,"Lot Princing", "fecth end");
        }
        return am.findForward("load");
      }
    }

    public ActionForward save(ActionMapping am, ActionForm form,
                              HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
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
        util.updAccessLog(req,res,"Lot Princing", "save");
        LotPricingForm  udf = (LotPricingForm)form;
        ArrayList memoLst = (ArrayList)session.getAttribute("memoLst");
        String sqlQ = "";
        ArrayList ary = new ArrayList();
        String SucessID = "";
        for (int i = 0; i < memoLst.size(); i++) {
            String memo = (String)memoLst.get(i);
            String isChecked = util.nvl((String)udf.getValue(memo));
            sqlQ ="update mlot set mfg_av= ? , mkt_av=? ,acc_av=? where dsc='" + memo +
                  "' and comp_dte is not null";
            if (isChecked.equals("yes")) {
                ary = new ArrayList();
                ary.add(util.nvl((String)udf.getValue("SURT_" + memo)));
                ary.add(util.nvl((String)udf.getValue("POL_" + memo)));
                ary.add(util.nvl((String)udf.getValue("ACC_" + memo)));
                int ct = db.execUpd("Update", sqlQ, ary);
                System.out.println(ct);
                SucessID = SucessID + "," + memo;
            }
        }
        req.setAttribute("SucessID", SucessID);
        util.updAccessLog(req,res,"Lot Princing", "save end");
        return am.findForward("load");
      }
    }

    public ActionForward loadDept(ActionMapping am, ActionForm form,
                                  HttpServletRequest req,
                                  HttpServletResponse res) throws Exception {
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
        util.updAccessLog(req,res,"Lot Princing", "loaddept");
        LotPricingForm  udf = (LotPricingForm)form;
        String Idn = util.nvl(req.getParameter("Idn"));
        if (Idn.equals(""))
            Idn = util.nvl((String)udf.getValue("IDN"));
        String deptQ =
            "Select idn,dept,mfg_av,mkt_av,acc_av from mlot_dept where idn=? ";
        ArrayList ary = new ArrayList();
        ary.add(Idn);
        ArrayList  rsLst = db.execSqlLst("Dept Collection", deptQ, ary);
        PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
        ResultSet  rs =(ResultSet)rsLst.get(1);
        try {
            while (rs.next()) {
                String idn = util.nvl(rs.getString("idn"));
                String dept = util.nvl(rs.getString("dept"));
                udf.setValue(dept + "_" + "SURT_" + idn,
                             util.nvl(rs.getString("mfg_av")));
                udf.setValue(dept + "_" + "POL_" + idn,
                             util.nvl(rs.getString("mkt_av")));
                udf.setValue(dept + "_" + "ACC_" + idn,
                             util.nvl(rs.getString("acc_av")));
            }
            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        udf.setValue("IDN", Idn);
        req.setAttribute("idn", Idn);
        util.updAccessLog(req,res,"Lot Princing", "loaddept end");
        return am.findForward("loaddept");
      }
    }

    public ActionForward saveDept(ActionMapping am, ActionForm form,
                                  HttpServletRequest req,
                                  HttpServletResponse res) throws Exception {
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
        util.updAccessLog(req,res,"Lot Princing", "savedept");
        LotPricingForm  udf = (LotPricingForm)form;
        String Idn = util.nvl((String)udf.getValue("IDN"));
        HashMap prp = info.getPrp();
        ArrayList PrpDtl = (ArrayList)prp.get("DEPT" + "V");
        ArrayList ary = new ArrayList();
        String updQ =
            "update mlot_dept set mfg_av= ? , mkt_av=? ,acc_av=?,dte=sysdate where idn=? and dept=?";
        String insQ =
            "INSERT INTO mlot_dept (mfg_av,mkt_av,acc_av,idn,dept) VALUES (?,?,?,?,?)";
        for (int i = 0; i < PrpDtl.size(); i++) {
            String dept = (String)PrpDtl.get(i);
            String mfgav =
                util.nvl((String)udf.getValue(dept + "_SURT_" + Idn));
            String mktav =
                util.nvl((String)udf.getValue(dept + "_POL_" + Idn));
            String accav =
                util.nvl((String)udf.getValue(dept + "_ACC_" + Idn));
            if (!mfgav.equals("") || !mktav.equals("") || !accav.equals("")) {
                ary = new ArrayList();
                ary.add(mfgav);
                ary.add(mktav);
                ary.add(accav);
                ary.add(Idn);
                ary.add(dept);
                int ct = db.execUpd("Update", updQ, ary);
                System.out.println(ct);
                if (ct < 1) {
                    ct = db.execUpd("Insert", insQ, ary);
                    System.out.println(ct);
                }
            }
        }
        udf.setValue("IDN", Idn);
        req.setAttribute("idn", Idn);
        req.setAttribute("msg", "Sucessfully Done...");
        util.updAccessLog(req,res,"Lot Princing", "svdept end");
        return loadDept(am, form, req, res);
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
              util.updAccessLog(req,res,"Lot Princing", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"Lot Princing", "init");
          }
          }
          return rtnPg;
          }

    public LotPricingAction() {
        super();
    }
}
