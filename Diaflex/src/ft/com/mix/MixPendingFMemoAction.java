package ft.com.mix;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.dao.TrmsDao;
import ft.com.generic.GenericImpl;

import ft.com.marketing.MemoReturnFrm;

import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;


public class MixPendingFMemoAction extends DispatchAction {
    
    public ActionForward load(ActionMapping am, ActionForm form,
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
          util.updAccessLog(req,res,"MixPendingFMemo", "load");
        MixPendingFMemoForm mixpendingForm = (MixPendingFMemoForm)form;
        ArrayList ary = new ArrayList();
        mixpendingForm.reset();
        HashMap memoDtl=new HashMap();
        HashMap byrDtl=new HashMap();
        HashMap byr_idn=new HashMap();
        ArrayList dtl=null;
        ArrayList empList=new ArrayList();
        ArrayList memotyp=new ArrayList();
            HashMap memoTotalsDtl=new HashMap();
            String dfNmeIdn=util.nvl((String)info.getDfNmeIdn());
            String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = (String)dbinfo.get("CNT");
            ArrayList rolenmLst=(ArrayList)info.getRoleLst();
            String usrFlg=util.nvl((String)info.getUsrFlg());
            String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
            String  byrid=util.nvl((String)mixpendingForm.getValue("byrid"));
            String  empid=util.nvl((String)mixpendingForm.getValue("empid"));
            String group = util.nvl((String)mixpendingForm.getValue("grpid"));
        String typ=util.nvl(req.getParameter("typ"));
            String conQ="";  
        if(typ.equals(""))
            typ=util.nvl((String)session.getAttribute("memoTyp"));
          
          
            if(!byrid.equals("")){
                 conQ+=" and nme_idn = ? ";
                 ary.add(byrid);
              }
            if(!empid.equals("")){
                 conQ+=" and emp_idn = ? ";
                 ary.add(empid);
              }
            if(!group.equals("")){
              conQ =conQ+ " and grp_nme_idn= ? ";
              ary.add(group);
            }else{
            if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
            }else if(!dfgrpnmeidn.equals("") && !dfgrpnmeidn.equals("0")){
            conQ =conQ+ " and grp_nme_idn=?"; 
            ary.add(dfgrpnmeidn);
            }  
            }

            if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
                    if(util.nvl((String)info.getDmbsInfoLst().get("EMP_LOCK")).equals("Y")){
                        conQ += " and (emp_idn= ? or emp_idn in(select emp_idn from nme_v where typ='EMPLOYEE' and mgr_idn= ?)) ";  
                        ary.add(dfNmeIdn);
                        ary.add(dfNmeIdn);
                    }
            }
          String pendingNme="Mix Pending on Memo";
          if(!typ.equals("PNDF_MEMO"))
              pendingNme="Mix Pending For Sale";
        int i=0;
        String types=displayDtls(req,typ,db);
        types=types.replaceAll(",", "','");
        String loadqry ="select nme_idn , byr , emp , cnt , qty ,to_char(trunc(cts,2),'99999999999990.999') cts,to_char(trunc(val,2),'99999999999990.999')  vlu,typ from memo_pndg_v where typ in('"+types+"') and pkt_ty in ('MIX','MX') "+conQ+" order by emp,byr,typ";
          ArrayList  rsLst = db.execSqlLst("loadqry", loadqry, ary);
          PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
          ResultSet  rs =(ResultSet)rsLst.get(1);
        while (rs.next()) {
              dtl=new ArrayList();
                if(!empList.contains(util.nvl(rs.getString("emp")))) {
                    empList.add(util.nvl(rs.getString("emp")));
                }
              byrDtl.put(util.nvl(rs.getString("emp"))+"_"+i,util.nvl(rs.getString("byr")));
              i++;
              dtl.add(util.nvl(rs.getString("cnt")));  
              dtl.add(util.nvl(rs.getString("qty")));
              dtl.add(util.nvl(rs.getString("cts")));
              dtl.add(util.nvl(rs.getString("vlu")));
              dtl.add(util.nvl(rs.getString("nme_idn")));
              dtl.add(util.nvl(rs.getString("typ")));
              memoDtl.put(util.nvl(rs.getString("emp"))+"_"+util.nvl(rs.getString("byr"))+"_"+util.nvl(rs.getString("typ")),dtl);
              byr_idn.put(util.nvl(rs.getString("emp"))+"_"+util.nvl(rs.getString("byr")),util.nvl(rs.getString("nme_idn")));  
              if(!memotyp.contains(util.nvl(rs.getString("typ")))) {
                memotyp.add(util.nvl(rs.getString("typ")));
              }      
      }
        rs.close();
          stmt.close();
          
            String colttlQ="select typ, sum(cnt) cnt , sum(qty) qty,to_char(sum(trunc(cts,2)),'99999999999999990.99') cts,\n" + 
            "to_char(sum(decode(pkt_ty,'SMX',(val/cts)*trunc(cts,2),val)),'9999999999990.99')  vlu \n" + 
            "from memo_pndg_v \n" + 
            "where typ in('"+types+"') and pkt_ty in ('MIX','MX') "+conQ+
            " group by typ";
          rsLst = db.execSqlLst("loadqry", colttlQ, ary);
          stmt =(PreparedStatement)rsLst.get(0);
           rs =(ResultSet)rsLst.get(1);
            while (rs.next()) {
                String typttl=util.nvl(rs.getString("typ"));
                memoTotalsDtl.put(typttl+"_CNT", util.nvl(rs.getString("cnt")));
                memoTotalsDtl.put(typttl+"_QTY", util.nvl(rs.getString("qty")));
                memoTotalsDtl.put(typttl+"_CTS", util.nvl(rs.getString("cts")));
                memoTotalsDtl.put(typttl+"_VLU", util.nvl(rs.getString("vlu")));
            }
            rs.close();
          stmt.close();
           
      mixpendingForm.setPendingNme(pendingNme);    
      req.setAttribute("memoTotalsDtl", memoTotalsDtl); 
      req.setAttribute("empList", empList); 
      req.setAttribute("byrDtl", byrDtl); 
      req.setAttribute("byr_idn", byr_idn); 
      req.setAttribute("memoDtl", memoDtl); 
      req.setAttribute("memotyp", memotyp);
          util.updAccessLog(req,res,"MixPendingFMemo", "end"); 
      return am.findForward("load");
        }
    }
    
    public String displayDtls(HttpServletRequest req,String flg,DBMgr db){
    JspUtil jspUtil = new JspUtil();
    String disTyp="";
    HashMap Display_typ=new HashMap(); 
    try {
    String gtView = "select chr_fr from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " +
    " b.mdl = 'JFLEX' and b.nme_rule = '"+flg+"' and a.chr_fr is not null and a.til_dte is null order by a.srt_fr";
      ArrayList  rsLst = db.execSqlLst("gtView", gtView, new ArrayList());
      PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
      ResultSet  rs =(ResultSet)rsLst.get(1);
    if (rs.next()) {
    disTyp=jspUtil.nvl(rs.getString("chr_fr"));
    }
        rs.close();
        stmt.close();
    gtView = "select chr_to,dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " +
            " b.mdl = 'JFLEX' and b.nme_rule = '"+flg+"' and a.til_dte is null order by a.srt_fr ";
      rsLst = db.execSqlLst("gtView", gtView, new ArrayList());
     stmt =(PreparedStatement)rsLst.get(0);
     rs =(ResultSet)rsLst.get(1);
    while (rs.next()) {
         Display_typ.put(jspUtil.nvl(rs.getString("chr_to")),jspUtil.nvl(rs.getString("dsc")));
     }
        rs.close();
        stmt.close();
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }
    req.setAttribute("Display_typ", Display_typ);
    return disTyp;
    } 
    public ActionForward memof(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
        
    StringBuffer sb = new StringBuffer();
    String nmeId = req.getParameter("nameIdn");
    String typ = util.nvl(req.getParameter("typ"));
  

    String sql = "select a.idn, a.memo_typ typ, to_char(a.dte, 'dd-Mon-rrrr HH24:mi:ss') dte , b.nme byr, c.term, a.qty,to_char(trunc(a.cts,2),'99999999999990.999') cts, to_char(trunc(a.vlu,2),'9999999999990.999') vlu , form_url_encode(nvl(a.rmk,'NA')) rmk from " +
    "memo_smry_v a, nme_v b, mtrms c " +
    "where " +
    "a.trms_idn = c.idn and a.nme_idn = b.nme_idn and b.nme_idn =? and a.memo_typ =? and a.pkt_ty <> 'NR' ";
    ArrayList ary = new ArrayList();
    ary.add(nmeId);
    ary.add(typ);
          ArrayList  rsLst = db.execSqlLst("memo pkt", sql, ary);
          PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
          ResultSet  rs =(ResultSet)rsLst.get(1);
    while(rs.next()){
    sb.append("<memo>");
    sb.append("<id>"+util.nvl(rs.getString("idn"),"0") +"</id>");
    sb.append("<trm>"+util.nvl(rs.getString("term"),"0") +"</trm>");
    sb.append("<qty>"+util.nvl(rs.getString("qty"),"0") +"</qty>");
    sb.append("<cts>"+util.nvl(rs.getString("cts"),"0") +"</cts>");
    sb.append("<vlu>"+util.nvl(rs.getString("vlu"),"0") +"</vlu>");
    sb.append("<dte>"+util.nvl(rs.getString("dte"),"0") +"</dte>");
    sb.append("<typ>"+util.nvl(rs.getString("typ"),"0") +"</typ>");
    sb.append("<memormk>"+util.nvl(rs.getString("rmk"),"NA") +"</memormk>");

    sb.append("</memo>");
    }
    rs.close();
      stmt.close();
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    res.getWriter().write("<memos>"+sb.toString()+"</memos>");
    return null;
        }
    }
    
    public ActionForward merge(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        MixPendingFMemoForm udf = (MixPendingFMemoForm) af;
          util.updAccessLog(req,res,"Memo Return ", "Memo Return merge load");
        String    sqlMerge = "";
        ArrayList    ary      = null;
        String    nmeIdn   = req.getParameter("nmeIdn");
        String typ = util.nvl(req.getParameter("typ"));
        String  memoTyp = util.nvl(req.getParameter("MemoTyp"));
        
        ArrayList trmList  = new ArrayList();
        sqlMerge = "select distinct a.nmerel_idn, b.trm||' '||b.rln terms from "
                  + "mjan a, cus_rel_v b ,  jan_pkt_dtl_v c where 1 = 1 " + "and a.nmerel_idn = b.rel_idn "
                   + "and a.nme_idn = ? and a.typ = ? and a.stt='IS' and a.idn = c.idn and c.PKT_TY in ('MIX','MX')";
        ary = new ArrayList();
        ary.add(nmeIdn);
        ary.add(typ);
          ArrayList  rsLst = db.execSqlLst("trms", sqlMerge, ary);
          PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
          ResultSet  rs =(ResultSet)rsLst.get(1);
        while (rs.next()) {
            TrmsDao trms = new TrmsDao();
            trms.setRelId(rs.getInt("nmerel_idn"));
            trms.setTrmDtl(rs.getString("terms"));
            trmList.add(trms);
        }
        rs.close();
        stmt.close();
        udf.setTrmsLst(trmList);
        udf.setValue("nmeIdn", nmeIdn);
        udf.setValue("typ", typ);
        udf.setValue("MemoTyp", memoTyp);
        udf.setValue("PKT_TY", "MIX");
        udf.setTerms("0");
          util.updAccessLog(req,res,"Memo Return ", "Memo Return merge load done");
           
        return am.findForward("merge");
        }
    }

    public ActionForward mergeMemo(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        MixPendingFMemoForm udf = (MixPendingFMemoForm) af;
          util.updAccessLog(req,res,"Memo Return ", "Memo Return memo merge load");
        ArrayList      ary     = new ArrayList();
        String      memoIds = "";
        String      nmeIdn  = (String) udf.getValue("nmeIdn");
        
        Enumeration reqNme  = req.getParameterNames();

        while (reqNme.hasMoreElements()) {
            String paramNm = (String) reqNme.nextElement();

            if (paramNm.indexOf("cb_memo") > -1) {
                String val = req.getParameter(paramNm);

                if (memoIds.equals("")) {
                    memoIds = val;
                } else {
                    memoIds = memoIds + "," + val;
                }
            }
        }

        ary.add(nmeIdn);
        ary.add(String.valueOf(memoIds));

        ArrayList out = new ArrayList();

        out.add("I");

            try {
                db.setAutoCommit(false);
                CallableStatement cst = null;

                cst =db.execCall("MKE_HDR ", "mix_memo_pkg.Merge_Memo(pNmeIdn => ?, pIds => ?,  pMemoIdn => ?)", ary, out);

                int MemoIdn = cst.getInt(3);
                if(MemoIdn <=0)
                    db.doRollBack();
                else
                    db.doCommit();  
              cst.close();
              cst=null;
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
                db.doRollBack();
            } finally {
                db.setAutoCommit(true);
            }
        session.setAttribute("memoTyp",udf.getValue("MemoTyp"));
          
        return am.findForward("pndPage");
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
              util.updAccessLog(req,res,"Mix Issue", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"Mix Issue", "init");
          }
          }
          return rtnPg;
          }

    public MixPendingFMemoAction() {
        super();
    }
}
