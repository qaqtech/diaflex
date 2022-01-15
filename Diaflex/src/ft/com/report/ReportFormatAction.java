package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.PdfforReport;
import ft.com.dao.ByrDao;
import ft.com.dao.DFMenu;

import ft.com.dao.DFMenuItm;

import ft.com.dao.MNme;
import ft.com.dao.ObjBean;
import ft.com.fileupload.FileUploadForm;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.SearchQuery;

import ft.com.pri.PriceDtlMatrixForm;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;

import java.net.URLEncoder;

import java.sql.Connection;
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

public class ReportFormatAction extends DispatchAction {

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
             util.updAccessLog(req,res,"Report Details", "load start");
         ReportForm udf = (ReportForm)af;
         GenericInterface genericInt = new GenericImpl();
         udf.resetALL();
//         if(info.getSrchPrp()==null)
//         util.initPrpSrch();
         ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_rptFmtGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_rptFmtGNCSrch");
         info.setGncPrpLst(assortSrchList);
//         genericInt.graphPath(req,res);
                          util.updAccessLog(req,res,"Report Details", "load end");
        return am.findForward("load");
         }
     }
    public ActionForward fetch(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Report Details", "fetch start");
         ReportForm udf = (ReportForm)af;
        String rpttyp = util.nvl((String)udf.getValue("rpttyp"));
        if(rpttyp.equals("shapeclarity")){
              return shapeclarity(am, af, req, res);
        }
        if(rpttyp.equals("sizepurity")){
              return sizepurity(am, af, req, res);
        }
            util.updAccessLog(req,res,"Report Details", "fetch end");
        return am.findForward("load");
        }
    }
    public ActionForward shapeclarity(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Report Details", "shapeclarity start");
    ReportForm udf = (ReportForm)af;
    String conQ=" and a.shape not in('CUSHION MODIFIED','SQUARE EMERALD','ST BUG','ROUND MB','SQ.EMERALD') ";
        conQ=conQ+criteriaDtl(udf,req,res);
        String frmDte = util.nvl((String)udf.getValue("frmDte"));
        String toDte = util.nvl((String)udf.getValue("toDte"));
        HashMap dataDtl = new HashMap();
        ArrayList grpLst=new ArrayList();
        ArrayList shapeLst=new ArrayList();
        ArrayList szLst=new ArrayList();
        String key="";
        if(frmDte.equals("") && !toDte.equals(""))
            frmDte=toDte;
        if(toDte.equals("") && !frmDte.equals(""))
            toDte=frmDte  ;
        if(!frmDte.equals("") && !toDte.equals(""))
        conQ=conQ+" and trunc(b.FRM_DTE) between to_date('"+frmDte+"' , 'dd-mm-yyyy') and to_date('"+toDte+"' , 'dd-mm-yyyy') ";
        else
        conQ=conQ+" and B.Stt='A' ";
        String dataQ="Select A.Shape shape,A.sh_so,A.Sz sz,c.grp grp,trunc(avg(mfg_dis),2) avg_dis,trunc((sum(mfg_rte)/sum(rap_rte)*100) - 100, 2) cum_dis,trunc(avg(mfg_rte)) avg\n" + 
        "From Itm_Bse A,Itm_Bse_Pri B,Prp c  \n" + 
        "where a.idn=b.itm_bse_idn \n" + 
        "and B.Mfg_Dis is not null  and b.mfg_rte > 0 \n" +conQ+ 
        "And C.Mprp='CLR' And A.Clr=C.Val and A.clr_so=C.srt\n" + 
        "Group By A.Shape,A.sh_so,A.Sz,c.grp\n" + 
        "order by 2,3,4";
          ArrayList outLst = db.execSqlLst("dataQ", dataQ, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
        String szVal = util.nvl(rs.getString("sz"));
        String shVal = util.nvl(rs.getString("shape"));
        String grp = util.nvl(rs.getString("grp"));
        key=szVal+"_"+grp+"_"+shVal;
        dataDtl.put(key+"_AVG",util.nvl(rs.getString("avg")));
        dataDtl.put(key+"_DIS",util.nvl(rs.getString("avg_dis")));
        dataDtl.put(key+"_CUMDIS",util.nvl(rs.getString("cum_dis")));
        if(!szLst.contains(szVal))
            szLst.add(szVal);
        if(!shapeLst.contains(shVal))
        shapeLst.add(shVal);
        }
            rs.close();
            pst.close();
        String grpQ="Select distinct c.grp grp\n" + 
        "From Itm_Bse A,Itm_Bse_Pri B,Prp c  \n" + 
        "where a.idn=b.itm_bse_idn \n" + 
        "and B.Mfg_Dis is not null and b.mfg_rte > 0 \n" +conQ+ 
        "And C.Mprp='CLR' And A.Clr=C.Val and A.clr_so=C.srt\n" + 
        "order by c.grp";
           outLst = db.execSqlLst("grpQ", grpQ, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        while(rs.next()){
        grpLst.add(util.nvl(rs.getString("grp")));
        }
            rs.close();
            pst.close();
        String szQ="Select distinct c.val,c.dsc,c.srt\n" + 
        "From Itm_Bse A,Itm_Bse_Pri B,Prp c  \n" + 
        "where a.idn=b.itm_bse_idn\n" + 
        "and B.Mfg_Dis is not null  and b.mfg_rte > 0 \n" +conQ+  
        "And C.Mprp='SIZE' And A.sz=C.Val \n" + 
        "order by c.srt";
          outLst = db.execSqlLst("szQ", szQ, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while(rs.next()){
        String val=util.nvl(rs.getString("val"));
        dataDtl.put(val,(util.nvl(rs.getString("dsc"))));
        }
            rs.close();
            pst.close();
        dataDtl.put("GRP",grpLst);
        dataDtl.put("SHAPE",shapeLst);
        dataDtl.put("SIZE",szLst);
        session.setAttribute("dataDtl", dataDtl);
            util.updAccessLog(req,res,"Report Details", "shapeclarity end");
    return am.findForward("shapeclarity");
        }
    } 
    
    public ActionForward sizepurity(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Report Details", "sizepurity start");
    ReportForm udf = (ReportForm)af;
    String conQ=criteriaDtl(udf,req,res);
        String frmDte = util.nvl((String)udf.getValue("frmDte"));
        String toDte = util.nvl((String)udf.getValue("toDte"));
        HashMap dbinfo = info.getDmbsInfoLst();
        String clrval = (String)dbinfo.get("CLR");
        HashMap dataDtl = new HashMap();
        ArrayList monthLst=new ArrayList();
        ArrayList grpLst=new ArrayList();
        ArrayList szLst=new ArrayList();
        String key="";
//        if(frmDte.equals("") && toDte.equals("")){
//            frmDte="sysdate";
//            toDte="sysdate";
//        }
        if(frmDte.equals("") && !toDte.equals(""))
            frmDte=toDte;
        if(toDte.equals("") && !frmDte.equals(""))
            toDte=frmDte  ;
        if(!frmDte.equals("") && !toDte.equals(""))
        conQ=conQ+" and trunc(b.FRM_DTE) between to_date('"+frmDte+"' , 'dd-mm-yyyy') and to_date('"+toDte+"' , 'dd-mm-yyyy') ";
        else
        conQ=conQ+" and B.Stt='A' ";
        String dataQ="Select trunc(avg(mfg_rte)) avg,c.grp grp,d.dsc sz, \n" + 
        "To_Number(To_Char(Trunc(Nvl(b.frm_dte,Sysdate)), 'rrrrmm')) Srt_Mon, \n" + 
        "To_Char(Trunc(Nvl(b.frm_dte,Sysdate)), 'MON rrrr') Dsp_Mon\n" + 
        "From Itm_Bse A,Itm_Bse_Pri B,Prp c,prp d \n" + 
        "where a.idn=b.itm_bse_idn \n" + 
        "and B.Mfg_Dis is not null and b.mfg_rte > 0 \n" +conQ+  
        " and d.mprp='SIZE' and a.sz=d.val "+
        "And C.Mprp='"+clrval+"' And A.Clr=C.Val and A.clr_so=C.srt \n" + 
        " group by c.grp, d.dsc\n" + 
        ", To_Number(To_Char(Trunc(Nvl(b.frm_dte,Sysdate)), 'rrrrmm'))\n" + 
        ", To_Char(Trunc(Nvl(b.frm_dte,Sysdate)), 'MON rrrr')\n" + 
        "order by 4,3";
         ArrayList outLst = db.execSqlLst("dataQ", dataQ, new ArrayList());
        PreparedStatement  pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            String szVal = util.nvl(rs.getString("sz"));
            String grp = util.nvl(rs.getString("grp"));
            String month = util.nvl(rs.getString("Dsp_Mon"));
            key=szVal+"_"+month+"_"+grp;
            dataDtl.put(key+"_AVG",util.nvl(rs.getString("avg")));
            dataDtl.put(key+"_MONTH",util.nvl(rs.getString("Srt_Mon")));
            if(!monthLst.contains(month))
                monthLst.add(month); 
            if(!szLst.contains(szVal))
                szLst.add(szVal); 
        }
            rs.close();
            pst.close();
        String prpQ="Select Distinct c.Grp grp \n" + 
        "        From Itm_Bse A,Itm_Bse_Pri B,Prp c\n" + 
        "        where a.idn=b.itm_bse_idn and B.Mfg_Dis is not null  and b.mfg_rte > 0\n" +conQ+
        "        And C.Mprp='"+clrval+"' And A.clr=C.Val order by c.Grp";
           outLst = db.execSqlLst("prpQ", prpQ, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            grpLst.add(util.nvl(rs.getString("grp")));
        }
            rs.close();
            pst.close();
        dataDtl.put("GRP",grpLst);
        session.setAttribute("dataDtl", dataDtl);
        session.setAttribute("monthLst", monthLst);
        session.setAttribute("szLst", szLst);
            util.updAccessLog(req,res,"Report Details", "sizepurity end");
    return am.findForward("sizepurity");
        }
    } 
    public String criteriaDtl(ReportForm udf,HttpServletRequest req, HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HashMap prp = info.getPrp();
                 HashMap mprp = info.getMprp();
                 HashMap dbinfo = info.getDmbsInfoLst();
                 String shval = (String)dbinfo.get("SHAPE");
                 String szval = (String)dbinfo.get("SIZE");
                 String colval = (String)dbinfo.get("COL");
                 String clrval = (String)dbinfo.get("CLR");
                ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_rptFmtGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_rptFmtGNCSrch");
                info.setGncPrpLst(genricSrchLst);
                ArrayList shapeLst=new ArrayList();
                ArrayList szLst=new ArrayList();
                ArrayList colorLst=new ArrayList();
                ArrayList clrLst=new ArrayList();
                String sh="",sz="",col="",clr="",conQ="";

                for (int i = 0; i < genricSrchLst.size(); i++) {
                ArrayList srchPrp = (ArrayList)genricSrchLst.get(i);
                String lprp = (String)srchPrp.get(0);
                String flg= (String)srchPrp.get(1);
                String prpSrt = lprp ;  
                String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
                    if(flg.equals("M")) {
                        ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
                        ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
                        for(int j=0; j < lprpS.size(); j++) {
                        String lSrt = (String)lprpS.get(j);
                        String lVal = (String)lprpV.get(j);    
                        String reqVal1 = util.nvl((String)udf.getValue(lprp + "_" + lVal),"");
                            if(lprp.equals(shval) && !reqVal1.equals("")){
                                shapeLst.add(lSrt);    
                            }
                            if(lprp.equals(szval) && !reqVal1.equals("")){
                                szLst.add(lSrt);    
                            }
                            if(lprp.equals(colval) && !reqVal1.equals("")){
                                colorLst.add(lSrt);    
                            }
                            if(lprp.equals(clrval) && !reqVal1.equals("")){
                                clrLst.add(lSrt);    
                            }
                        }
                    }else{
                        String fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
                       String fldVal2 = util.nvl((String)udf.getValue(lprp+"_2"));
                       if(fldVal2.equals(""))
                           fldVal2=fldVal1; 
                    }            
                }
                if(shapeLst.size() > 0) {    
                sh=shapeLst.toString();
                sh=sh.replaceAll("\\[","\\(");
                sh=sh.replaceAll("\\]","\\)").replaceAll(" ", "");
                }
                if(szLst.size() > 0){
                sz=szLst.toString();
                sz=sz.replaceAll("\\[","\\(");
                sz=sz.replaceAll("\\]","\\)").replaceAll(" ", "");
                }
                if(colorLst.size() > 0) {    
                col=colorLst.toString();
                col=col.replaceAll("\\[","\\(");
                col=col.replaceAll("\\]","\\)").replaceAll(" ", "");
                }
                  if(clrLst.size() > 0){
                clr=clrLst.toString();
                clr=clr.replaceAll("\\[","\\(");
                clr=clr.replaceAll("\\]","\\)").replaceAll(" ", "");
                }
                if(!sh.equals("")){
                    conQ=" and a.sh_so in "+sh;
                }
                if(!sz.equals("")){
                    conQ=conQ+" and a.sz_so in "+sz; 
                }
                if(!col.equals("")){
                    conQ=conQ+" and a.col_so in "+col; 
                }
                if(!clr.equals("")){
                    conQ=conQ+" and a.clr_so in "+clr; 
                }
                 
    return conQ;
    }
//    public ArrayList rptGenricSrch(HttpServletRequest req , HttpServletResponse res){
//        init(req,res);
//        ArrayList asViewPrp = (ArrayList)session.getAttribute("rptFmtGNCSrch");
//        try {
//            if (asViewPrp == null) {
//
//                asViewPrp = new ArrayList();
//                ResultSet rs1 =
//                    db.execSql(" Vw Lst ", "Select prp,flg  from rep_prp where mdl = 'RPTFMT_SRCH' and flg in ('Y','S','M') order by rnk ",
//                               new ArrayList());
//                while (rs1.next()) {
//                    ArrayList asViewdtl=new ArrayList();
//                    asViewdtl.add(rs1.getString("prp"));
//                    asViewdtl.add(rs1.getString("flg"));
//                    asViewPrp.add(asViewdtl);
//                }
//                rs1.close();
//                session.setAttribute("rptFmtGNCSrch", asViewPrp);
//            }
//
//        } catch (SQLException sqle) {
//            // TODO: Add catch code
//            sqle.printStackTrace();
//        }
//        
//        return asViewPrp;
//    }
    public ReportFormatAction() {
        super();
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
                util.updAccessLog(req,res,"Report Details", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Report Details", "init");
            }
            }
            return rtnPg;
            }

}

