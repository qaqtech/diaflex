package ft.com.marketing;
import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.generic.GenericImpl;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

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


public class PendingFMemoAction extends DispatchAction {


    public ActionForward load(ActionMapping am, ActionForm form,
                              HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
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
        System.out.println("load conn Is Null rtn pg is connExists");
        }
        }else{
        rtnPg="sessionTO";
        System.out.println("load Info Is Null rtn pg is sessionTO");
        }
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
        PendingFMemoForm pendingForm = (PendingFMemoForm)form;
          util.updAccessLog(req,res,"Pending Memo", "Pending Memo load");
        ArrayList ary = new ArrayList();
  
        HashMap memoDtl=new HashMap();
        HashMap memoTotalsDtl=new HashMap();
        HashMap byrDtl=new HashMap();
        HashMap byr_idn=new HashMap();
        HashMap bDtl=new HashMap();
        HashMap empDtl=new HashMap();
        ArrayList dtl=null;
        ArrayList empList=new ArrayList();
        ArrayList byrList=new ArrayList();
        ArrayList memotyp=new ArrayList();
        String typ=util.nvl(req.getParameter("typ"));
        if(typ.equals("")){
                typ=util.nvl((String)session.getAttribute("typ"));
        }
       
        String  byrid=util.nvl((String)pendingForm.getValue("byrid"));
        String  empid=util.nvl((String)pendingForm.getValue("empid"));
        String  btn=util.nvl((String)pendingForm.getValue("submit"));
        String group = util.nvl((String)pendingForm.getValue("grpid"));
            String dfNmeIdn=util.nvl((String)info.getDfNmeIdn());
            String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = (String)dbinfo.get("CNT");
            ArrayList rolenmLst=(ArrayList)info.getRoleLst();
            String usrFlg=util.nvl((String)info.getUsrFlg());
            String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("PEND_MEMO");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("PEND_MEMO");
            allPageDtl.put("PEND_MEMO",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            ArrayList pageList=new ArrayList();
        String conQ="";   
          String  pkt_ty=util.nvl((String)pendingForm.getValue("pkt_ty"));
            if(pkt_ty.equals(""))
                pkt_ty = util.nvl(req.getParameter("pkt_ty"));
          String  pktTycon=" and pkt_ty in ('NR','SMX') ";
            if(!pkt_ty.equals("") && !pkt_ty.equals("ALL"))
                pktTycon=" and pkt_ty in ('"+pkt_ty+"') ";
              
           

           if(!btn.equals(""))
              typ=util.nvl((String)pendingForm.getValue("typ"));
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
          String pendingNme="Pending on Memo";
          if(!typ.equals("PNDF_MEMO"))
              pendingNme="Pending For Sale";
        int i=0;
        String types=displayDtls(req,typ);
        types=types.replaceAll(",", "','");

        String loadqry ="select nme_idn , byr , emp ,pkt_ty, cnt , qty ,to_char(trunc(cts,2),'99999999999999990.99') cts,to_char(decode(pkt_ty,'SMX',(val/cts)*trunc(cts,2),val),'9999999999990.99') vlu,typ from memo_pndg_v where typ in('"+types+"') "+conQ+pktTycon+" order by emp,byr,typ";
          ArrayList  outLst = db.execSqlLst("loadqry", loadqry, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
              dtl=new ArrayList();
              
              byrDtl.put(util.nvl(rs.getString("emp"))+"_"+i,util.nvl(rs.getString("byr")));
              i++;
              dtl.add(util.nvl(rs.getString("cnt")));  
              dtl.add(util.nvl(rs.getString("qty")));
              dtl.add(util.nvl(rs.getString("cts")));
              dtl.add(util.nvl(rs.getString("vlu")));
              dtl.add(util.nvl(rs.getString("nme_idn")));
              dtl.add(util.nvl(rs.getString("typ")));
              String key=util.nvl(rs.getString("emp"))+"_"+util.nvl(rs.getString("byr"))+"_"+util.nvl(rs.getString("typ"));
              ArrayList exDtl = (ArrayList)memoDtl.get(key);
            if(exDtl!=null && exDtl.size()>0){
              int Cnt = Integer.parseInt((String)exDtl.get(0))+Integer.parseInt((String)dtl.get(0));
              int qty = Integer.parseInt((String)exDtl.get(1))+Integer.parseInt((String)dtl.get(1));
              float cts = Float.parseFloat((String)exDtl.get(2))+Float.parseFloat((String)dtl.get(2));
              double vlu = util.roundToDecimals(Double.parseDouble(util.nvl((String)exDtl.get(3),"0"))+Double.parseDouble(util.nvl((String)dtl.get(3),"0") ),2);
                dtl=new ArrayList();
                dtl.add(0,String.valueOf(Cnt));
                dtl.add(1, String.valueOf(qty));
                dtl.add(2, String.valueOf(cts));
                dtl.add(3, String.valueOf(vlu));
                dtl.add(util.nvl(rs.getString("nme_idn")));
                dtl.add(util.nvl(rs.getString("typ")));
            }
             memoDtl.put(key,dtl);
            
              byr_idn.put(util.nvl(rs.getString("emp"))+"_"+util.nvl(rs.getString("byr")),util.nvl(rs.getString("nme_idn")));  
              if(!memotyp.contains(util.nvl(rs.getString("typ")))) {
                memotyp.add(util.nvl(rs.getString("typ")));
              }      
      }
       rs.close(); 
      pst.close();
            pageList=(ArrayList)pageDtl.get("BUYER");
            if(pageList!=null && pageList.size() >0){
            String loadbyr="select distinct byr,nme_idn from memo_pndg_v  where typ in('"+types+"') "+pktTycon+" order by byr";
           outLst = db.execSqlLst("loadqry", loadbyr, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                  bDtl=new HashMap();
                        bDtl.put("byr", util.nvl(rs.getString("byr")));
                        bDtl.put("nmeidn",util.nvl(rs.getString("nme_idn")));
                        byrList.add(bDtl);
            }
            rs.close();
            pst.close();
            }
            
            String loademp="select distinct emp,nvl(emp_idn,'0') emp_idn from memo_pndg_v  where typ in('"+types+"') "+pktTycon+" order by emp";
          outLst = db.execSqlLst("loadqry", loademp, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                  empDtl=new HashMap();
                  empDtl.put("emp", util.nvl(rs.getString("emp")));
                  empDtl.put("empid",util.nvl(rs.getString("emp_idn")));
                  empList.add(empDtl);
            }
            rs.close();
            pst.close();
            
            String rowttlQ="select byr , emp, sum(cnt) cnt , sum(qty) qty,to_char(sum(trunc(cts,2)),'99999999999999990.99') cts,\n" + 
            "to_char(sum(decode(pkt_ty,'SMX',(val/cts)*trunc(cts,2),val)),'9999999999990.99')  vlu \n" + 
            "from memo_pndg_v \n" + 
            "where typ in('"+types+"') "+pktTycon+" \n" +conQ +
            " group by byr ,emp";
           outLst = db.execSqlLst("loadqry", rowttlQ, ary);
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
            String emp=util.nvl(rs.getString("emp"));
            String byr=util.nvl(rs.getString("byr"));
            memoTotalsDtl.put(emp+"_"+byr+"_CNT", util.nvl(rs.getString("cnt")));
            memoTotalsDtl.put(emp+"_"+byr+"_QTY", util.nvl(rs.getString("qty")));
            memoTotalsDtl.put(emp+"_"+byr+"_CTS", util.nvl(rs.getString("cts")));
            memoTotalsDtl.put(emp+"_"+byr+"_VLU", util.nvl(rs.getString("vlu")));
            }
            rs.close();
            pst.close();
            
            String colttlQ="select typ, sum(cnt) cnt , sum(qty) qty,to_char(sum(trunc(cts,2)),'99999999999999990.99') cts,\n" + 
            "to_char(sum(decode(pkt_ty,'SMX',(val/cts)*trunc(cts,2),val)),'9999999999990.99')  vlu \n" + 
            "from memo_pndg_v \n" + 
            "where typ in('"+types+"') "+pktTycon+" \n" +conQ +
            " group by typ";
          outLst = db.execSqlLst("loadqry", colttlQ, ary);
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                String typttl=util.nvl(rs.getString("typ"));
                memoTotalsDtl.put(typttl+"_CNT", util.nvl(rs.getString("cnt")));
                memoTotalsDtl.put(typttl+"_QTY", util.nvl(rs.getString("qty")));
                memoTotalsDtl.put(typttl+"_CTS", util.nvl(rs.getString("cts")));
                memoTotalsDtl.put(typttl+"_VLU", util.nvl(rs.getString("vlu")));
            }
            rs.close();
            pst.close();
            
            String ttlQ="select sum(cnt) cnt , sum(qty) qty,to_char(sum(trunc(cts,2)),'99999999999999990.99') cts,\n" + 
            "to_char(sum(trunc(val,2)),'9999999999990.99')  vlu \n" + 
            "from memo_pndg_v \n" + 
            " where typ in('"+types+"') "+pktTycon+" \n"+conQ ;
          outLst = db.execSqlLst("loadqry", ttlQ, ary);
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                memoTotalsDtl.put("CNT", util.nvl(rs.getString("cnt")));
                memoTotalsDtl.put("QTY", util.nvl(rs.getString("qty")));
                memoTotalsDtl.put("CTS", util.nvl(rs.getString("cts")));
                memoTotalsDtl.put("VLU", util.nvl(rs.getString("vlu")));
            }
            rs.close();
            pst.close();
            pendingForm.reset();
      pendingForm.setPendingNme(pendingNme);    
      req.setAttribute("empList", empList); 
      req.setAttribute("byrDtl", byrDtl); 
      req.setAttribute("byr_idn", byr_idn); 
      req.setAttribute("memoDtl", memoDtl); 
      req.setAttribute("memoTotalsDtl", memoTotalsDtl); 
      req.setAttribute("memotyp", memotyp);
      req.setAttribute("page", typ);
      req.setAttribute("byrList", byrList);
      pendingForm.setValue("pkt_ty", pkt_ty);   

     
     ArrayList grpList = (ArrayList)session.getAttribute("grpcompanyList");
     if(grpList==null || grpList.size()==0){
     grpList=util.groupcompany();
     session.setAttribute("grpcompanyList", grpList);
     }
     util.updAccessLog(req,res,"Pending Memo", "Pending Memo load done");
      return am.findForward("load");
        }
    }
    public String displayDtls(HttpServletRequest req,String flg){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        Connection conn=info.getCon();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    String disTyp="";
    HashMap Display_typ=new HashMap(); 
    try {
    String gtView = "select chr_fr from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " +
    " b.mdl = 'JFLEX' and b.nme_rule = '"+flg+"' and dsc='TYPES' and a.chr_fr is not null and a.til_dte is null order by a.srt_fr";
      ArrayList  outLst = db.execSqlLst("gtView", gtView, new ArrayList());
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet  rs = (ResultSet)outLst.get(1);
    if (rs.next()) {
    disTyp=util.nvl(rs.getString("chr_fr"));
    }
        rs.close();
        pst.close();
    gtView = "select chr_to,dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " +
            " b.mdl = 'JFLEX' and b.nme_rule = '"+flg+"' and a.til_dte is null order by a.srt_fr ";
      outLst = db.execSqlLst("gtView", gtView, new ArrayList());
       pst = (PreparedStatement)outLst.get(0);
      rs = (ResultSet)outLst.get(1);
    while (rs.next()) {
         Display_typ.put(util.nvl(rs.getString("chr_to")),util.nvl(rs.getString("dsc")));
     }
        rs.close();
        pst.close();
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }
    req.setAttribute("Display_typ", Display_typ);
    return disTyp;
    } 
    
    public String init(HttpServletRequest req , HttpServletResponse res,HttpSession session,DBUtil util) {
            String rtnPg="sucess";
            String invalide="";
            String connExists=util.nvl(util.getConnExists());  
            if(!connExists.equals("N"))
            invalide=util.nvl(util.chkTimeOut(),"N");
            if(session.isNew()){
            rtnPg="sessionTO";    
            System.out.println("init session.isNew() rtn pg sessionTO");
            }
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
                util.updAccessLog(req,res,"Pending Memo", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Pending Memo", "init");
            }
            }
            return rtnPg;
            }
    
    public ActionForward loadPendingConsignment(ActionMapping am, ActionForm form,
                              HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
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
        PendingFMemoForm pendingForm = (PendingFMemoForm)form;
        util.updAccessLog(req,res,"Pending Consignment", "PendingConsignment load");
        ArrayList penconsigndata=new ArrayList();
        HashMap dtl=new HashMap();
        HashMap penconsigndataTtl=new HashMap();
        String pkttyp=util.nvl(req.getParameter("typ"));
        String conQ=" and a.pkt_ty in('NR','SMX')";
        if(pkttyp.equals("MIX")){
        conQ=" and a.pkt_ty not in('NR','SMX') ";
        }
        String getData = " select a.nme_idn, initcap(byr) byr,cnt, qty, cts, val,"+ 
                                   " byr.get_nm(b.emp_idn) salNme,PKT_TY from cs_pndg_v a , mnme b where a.nme_idn = b.nme_idn "+conQ+" order by byr ";
          ArrayList  outLst = db.execSqlLst(" get memo data", getData, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
                 
        while(rs.next()) {
        dtl=new HashMap();
        dtl.put("nmeIdn",rs.getString("nme_idn"));
        dtl.put("byr",rs.getString("byr"));
        dtl.put("sale",rs.getString("salNme"));
        dtl.put("qty",util.nvl(rs.getString("qty")));
        dtl.put("cts",util.nvl(rs.getString("cts")));
        dtl.put("val",util.nvl(rs.getString("val"))); 
        dtl.put("cnt",util.nvl(rs.getString("cnt"))); 
        dtl.put("pkttyp",util.nvl(rs.getString("PKT_TY"))); 
        penconsigndata.add(dtl);
        }
        rs.close();
        pst.close();
        getData = " Select Sum(Qty) qty, sum(cnt) cnt , To_Char(sum(Trunc(Cts,2)),'9999990.99') cts, To_Char(sum(Trunc(Val,2)),'99999990.99')  vlu "+
                            " From Cs_Pndg_V A , Mnme B Where A.Nme_Idn = B.Nme_Idn "+conQ;
        outLst = db.execSqlLst(" get grand memo data", getData, new ArrayList());
        pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
                 
                 
        while(rs.next()) {
        penconsigndataTtl.put("cnt",util.nvl(rs.getString("cnt")));
        penconsigndataTtl.put("qty",util.nvl(rs.getString("qty")));
        penconsigndataTtl.put("cts",util.nvl(rs.getString("cts")));
        penconsigndataTtl.put("vlu",util.nvl(rs.getString("vlu")));
        }
            rs.close();
            pst.close();
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("PEND_CONSIGN");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("PEND_CONSIGN");
            allPageDtl.put("PEND_CONSIGN",pageDtl);
            }
            info.setPageDetails(allPageDtl);
      req.setAttribute("penconsigndataTtl", penconsigndataTtl);
      req.setAttribute("penconsigndata", penconsigndata);  
      util.updAccessLog(req,res,"Pending Consignment", "Pending Consignment done");
      return am.findForward("loadPendingConsignment");
        }
    }
    
    public ActionForward loadDlv(ActionMapping am, ActionForm form,
                              HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
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
        System.out.println("load conn Is Null rtn pg is connExists");
        }
        }else{
        rtnPg="sessionTO";
        System.out.println("load Info Is Null rtn pg is sessionTO");
        }
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
        PendingFMemoForm pendingForm = (PendingFMemoForm)form;
          util.updAccessLog(req,res,"Pending Memo", "Pending Memo load");
        ArrayList ary = new ArrayList();
    
        HashMap saleDtl=new HashMap();
        HashMap saleTotalsDtl=new HashMap();
        HashMap byrDtl=new HashMap();
        HashMap byr_idn=new HashMap();
        HashMap bDtl=new HashMap();
        HashMap empDtl=new HashMap();
        ArrayList dtl=null;
        ArrayList empList=new ArrayList();
        ArrayList byrList=new ArrayList();
        ArrayList saletyp=new ArrayList();
       
        String  byrid=util.nvl((String)pendingForm.getValue("byrid"));
        String  empid=util.nvl((String)pendingForm.getValue("empid"));
        String  btn=util.nvl((String)pendingForm.getValue("submit"));
        String group = util.nvl((String)pendingForm.getValue("grpid"));
            String dfNmeIdn=util.nvl((String)info.getDfNmeIdn());
            String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = (String)dbinfo.get("CNT");
            ArrayList rolenmLst=(ArrayList)info.getRoleLst();
            String usrFlg=util.nvl((String)info.getUsrFlg());
            String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("PEND_MEMO");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("PEND_MEMO");
            allPageDtl.put("PEND_MEMO",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            ArrayList pageList=new ArrayList();
        String conQ="";   
          String  pkt_ty=util.nvl((String)pendingForm.getValue("pkt_ty"));
            if(pkt_ty.equals(""))
                pkt_ty = util.nvl(req.getParameter("pkt_ty"));
          String  pktTycon=" and pkt_ty in ('NR','SMX') ";
            if(!pkt_ty.equals("") && !pkt_ty.equals("ALL"))
                pktTycon=" and pkt_ty in ('"+pkt_ty+"') ";
              
           
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
          String pendingNme="Pending on Delivery";
        int i=0;

        String loadqry ="select nme_idn , byr , emp ,pkt_ty, cnt , qty ,to_char(trunc(cts,2),'99999999999999990.99') cts,to_char(decode(pkt_ty,'SMX',(val/cts)*trunc(cts,2),val),'9999999999990.99') vlu,typ from sale_pndg_v where typ in('SL') "+conQ+pktTycon+" order by emp,byr,typ";
          ArrayList  outLst = db.execSqlLst("loadqry", loadqry, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
              dtl=new ArrayList();
              
              byrDtl.put(util.nvl(rs.getString("emp"))+"_"+i,util.nvl(rs.getString("byr")));
              i++;
              dtl.add(util.nvl(rs.getString("cnt")));  
              dtl.add(util.nvl(rs.getString("qty")));
              dtl.add(util.nvl(rs.getString("cts")));
              dtl.add(util.nvl(rs.getString("vlu")));
              dtl.add(util.nvl(rs.getString("nme_idn")));
              dtl.add(util.nvl(rs.getString("typ")));
              String key=util.nvl(rs.getString("emp"))+"_"+util.nvl(rs.getString("byr"))+"_"+util.nvl(rs.getString("typ"));
              ArrayList exDtl = (ArrayList)saleDtl.get(key);
            if(exDtl!=null && exDtl.size()>0){
              int Cnt = Integer.parseInt((String)exDtl.get(0))+Integer.parseInt((String)dtl.get(0));
              int qty = Integer.parseInt((String)exDtl.get(1))+Integer.parseInt((String)dtl.get(1));
              float cts = Float.parseFloat((String)exDtl.get(2))+Float.parseFloat((String)dtl.get(2));
              double vlu = util.roundToDecimals(Double.parseDouble((String)exDtl.get(3))+Double.parseDouble((String)dtl.get(3)),2);
                dtl=new ArrayList();
                dtl.add(0,String.valueOf(Cnt));
                dtl.add(1, String.valueOf(qty));
                dtl.add(2, String.valueOf(cts));
                dtl.add(3, String.valueOf(vlu));
                dtl.add(util.nvl(rs.getString("nme_idn")));
                dtl.add(util.nvl(rs.getString("typ")));
            }
             saleDtl.put(key,dtl);
            
              byr_idn.put(util.nvl(rs.getString("emp"))+"_"+util.nvl(rs.getString("byr")),util.nvl(rs.getString("nme_idn")));  
              if(!saletyp.contains(util.nvl(rs.getString("typ")))) {
                saletyp.add(util.nvl(rs.getString("typ")));
              }      
      }
       rs.close(); 
      pst.close();
            pageList=(ArrayList)pageDtl.get("BUYER");
            if(pageList!=null && pageList.size() >0){
            String loadbyr="select distinct byr,nme_idn from sale_pndg_v  where typ in('SL') "+pktTycon+" order by byr";
              outLst = db.execSqlLst("loadqry", loadbyr, new ArrayList());
              pst = (PreparedStatement)outLst.get(0);
              rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                  bDtl=new HashMap();
                        bDtl.put("byr", util.nvl(rs.getString("byr")));
                        bDtl.put("nmeidn",util.nvl(rs.getString("nme_idn")));
                        byrList.add(bDtl);
            }
            rs.close();
            pst.close();
            }
            
            String loademp="select distinct emp,nvl(emp_idn,'0') emp_idn from sale_pndg_v  where typ in('SL') "+pktTycon+" order by emp";
          outLst = db.execSqlLst("loadqry", loademp, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                  empDtl=new HashMap();
                  empDtl.put("emp", util.nvl(rs.getString("emp")));
                  empDtl.put("empid",util.nvl(rs.getString("emp_idn")));
                  empList.add(empDtl);
            }
            rs.close();
            pst.close();
            
            String rowttlQ="select byr , emp, sum(cnt) cnt , sum(qty) qty,to_char(sum(trunc(cts,2)),'99999999999999990.99') cts,\n" + 
            "to_char(sum(decode(pkt_ty,'SMX',(val/cts)*trunc(cts,2),val)),'9999999999990.99')  vlu \n" + 
            "from sale_pndg_v \n" + 
            "where typ in('SL') "+pktTycon+" \n" +conQ +
            " group by byr ,emp";
          outLst = db.execSqlLst("loadqry", rowttlQ, ary);
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
            String emp=util.nvl(rs.getString("emp"));
            String byr=util.nvl(rs.getString("byr"));
            saleTotalsDtl.put(emp+"_"+byr+"_CNT", util.nvl(rs.getString("cnt")));
            saleTotalsDtl.put(emp+"_"+byr+"_QTY", util.nvl(rs.getString("qty")));
            saleTotalsDtl.put(emp+"_"+byr+"_CTS", util.nvl(rs.getString("cts")));
            saleTotalsDtl.put(emp+"_"+byr+"_VLU", util.nvl(rs.getString("vlu")));
            }
            rs.close();
            pst.close();
            
            String colttlQ="select typ, sum(cnt) cnt , sum(qty) qty,to_char(sum(trunc(cts,2)),'99999999999999990.99') cts,\n" + 
            "to_char(sum(decode(pkt_ty,'SMX',(val/cts)*trunc(cts,2),val)),'9999999999990.99')  vlu \n" + 
            "from sale_pndg_v \n" + 
            "where typ in('SL') "+pktTycon+" \n" +conQ +
            " group by typ";
          outLst = db.execSqlLst("loadqry", colttlQ, ary);
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                String typttl=util.nvl(rs.getString("typ"));
                saleTotalsDtl.put(typttl+"_CNT", util.nvl(rs.getString("cnt")));
                saleTotalsDtl.put(typttl+"_QTY", util.nvl(rs.getString("qty")));
                saleTotalsDtl.put(typttl+"_CTS", util.nvl(rs.getString("cts")));
                saleTotalsDtl.put(typttl+"_VLU", util.nvl(rs.getString("vlu")));
            }
            rs.close();
            pst.close();
            
            String ttlQ="select sum(cnt) cnt , sum(qty) qty,to_char(sum(trunc(cts,2)),'99999999999999990.99') cts,\n" + 
            "to_char(sum(trunc(val,2)),'9999999999990.99')  vlu \n" + 
            "from sale_pndg_v \n" + 
            " where typ in('SL') "+pktTycon+" \n"+conQ ;
          outLst = db.execSqlLst("loadqry", ttlQ, ary);
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                saleTotalsDtl.put("CNT", util.nvl(rs.getString("cnt")));
                saleTotalsDtl.put("QTY", util.nvl(rs.getString("qty")));
                saleTotalsDtl.put("CTS", util.nvl(rs.getString("cts")));
                saleTotalsDtl.put("VLU", util.nvl(rs.getString("vlu")));
            }
            rs.close();
            pst.close();
            pendingForm.reset();
      pendingForm.setPendingNme(pendingNme);    
      req.setAttribute("empList", empList); 
      req.setAttribute("byrDtl", byrDtl); 
      req.setAttribute("byr_idn", byr_idn); 
      req.setAttribute("saleDtl", saleDtl); 
      req.setAttribute("saleTotalsDtl", saleTotalsDtl); 
      req.setAttribute("saletyp", saletyp);
      req.setAttribute("byrList", byrList);
      pendingForm.setValue("pkt_ty", pkt_ty);   

     
     ArrayList grpList = (ArrayList)session.getAttribute("grpcompanyList");
     if(grpList==null || grpList.size()==0){
     grpList=util.groupcompany();
     session.setAttribute("grpcompanyList", grpList);
     }
            allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            pageDtl=(HashMap)allPageDtl.get("DELIVERY_CONFIRMATION");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("DELIVERY_CONFIRMATION");
            allPageDtl.put("DELIVERY_CONFIRMATION",pageDtl);
            }
            info.setPageDetails(allPageDtl);
     util.updAccessLog(req,res,"Pending Memo", "Pending Memo load done");
      return am.findForward("loadDlv");
        }
    }
    
    public ActionForward loadbrcDlv(ActionMapping am, ActionForm form,
                              HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
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
        System.out.println("load conn Is Null rtn pg is connExists");
        }
        }else{
        rtnPg="sessionTO";
        System.out.println("load Info Is Null rtn pg is sessionTO");
        }
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
        PendingFMemoForm pendingForm = (PendingFMemoForm)form;
          util.updAccessLog(req,res,"Pending Brc dlv", "Pending Brc dlv load");
        ArrayList ary = new ArrayList();
    
        HashMap saleDtl=new HashMap();
        HashMap saleTotalsDtl=new HashMap();
        HashMap byrDtl=new HashMap();
        HashMap byr_idn=new HashMap();
        HashMap bDtl=new HashMap();
        HashMap empDtl=new HashMap();
        ArrayList dtl=null;
        ArrayList empList=new ArrayList();
        ArrayList byrList=new ArrayList();
        ArrayList saletyp=new ArrayList();
       
        String  byrid=util.nvl((String)pendingForm.getValue("byrid"));
        String  empid=util.nvl((String)pendingForm.getValue("empid"));
        String  btn=util.nvl((String)pendingForm.getValue("submit"));
        String group = util.nvl((String)pendingForm.getValue("grpid"));
            String dfNmeIdn=util.nvl((String)info.getDfNmeIdn());
            String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = (String)dbinfo.get("CNT");
            ArrayList rolenmLst=(ArrayList)info.getRoleLst();
            String usrFlg=util.nvl((String)info.getUsrFlg());
            String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("PEND_MEMO");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("PEND_MEMO");
            allPageDtl.put("PEND_MEMO",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            ArrayList pageList=new ArrayList();
        String conQ="";   
          String  pkt_ty=util.nvl((String)pendingForm.getValue("pkt_ty"));
            if(pkt_ty.equals(""))
                pkt_ty = util.nvl(req.getParameter("pkt_ty"));
          String  pktTycon=" and pkt_ty in ('NR','SMX') ";
            if(!pkt_ty.equals("") && !pkt_ty.equals("ALL"))
                pktTycon=" and pkt_ty in ('"+pkt_ty+"') ";
              
           
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
          String pendingNme="Pending on Branch";
        int i=0;

        String loadqry ="select nme_idn , byr , emp ,pkt_ty, cnt , qty ,to_char(trunc(cts,2),'99999999999999990.99') cts,to_char(decode(pkt_ty,'SMX',(val/cts)*trunc(cts,2),val),'9999999999990.99') vlu,typ from brc_pndg_v where typ in('DLV') "+conQ+pktTycon+" order by emp,byr,typ";
          ArrayList  outLst = db.execSqlLst("loadqry", loadqry, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
              dtl=new ArrayList();
              
              byrDtl.put(util.nvl(rs.getString("emp"))+"_"+i,util.nvl(rs.getString("byr")));
              i++;
              dtl.add(util.nvl(rs.getString("cnt")));  
              dtl.add(util.nvl(rs.getString("qty")));
              dtl.add(util.nvl(rs.getString("cts")));
              dtl.add(util.nvl(rs.getString("vlu")));
              dtl.add(util.nvl(rs.getString("nme_idn")));
              dtl.add(util.nvl(rs.getString("typ")));
              String key=util.nvl(rs.getString("emp"))+"_"+util.nvl(rs.getString("byr"))+"_"+util.nvl(rs.getString("typ"));
              ArrayList exDtl = (ArrayList)saleDtl.get(key);
            if(exDtl!=null && exDtl.size()>0){
              int Cnt = Integer.parseInt((String)exDtl.get(0))+Integer.parseInt((String)dtl.get(0));
              int qty = Integer.parseInt((String)exDtl.get(1))+Integer.parseInt((String)dtl.get(1));
              float cts = Float.parseFloat((String)exDtl.get(2))+Float.parseFloat((String)dtl.get(2));
              double vlu = util.roundToDecimals(Double.parseDouble((String)exDtl.get(3))+Double.parseDouble((String)dtl.get(3)),2);
                dtl=new ArrayList();
                dtl.add(0,String.valueOf(Cnt));
                dtl.add(1, String.valueOf(qty));
                dtl.add(2, String.valueOf(cts));
                dtl.add(3, String.valueOf(vlu));
                dtl.add(util.nvl(rs.getString("nme_idn")));
                dtl.add(util.nvl(rs.getString("typ")));
            }
             saleDtl.put(key,dtl);
            
              byr_idn.put(util.nvl(rs.getString("emp"))+"_"+util.nvl(rs.getString("byr")),util.nvl(rs.getString("nme_idn")));  
              if(!saletyp.contains(util.nvl(rs.getString("typ")))) {
                saletyp.add(util.nvl(rs.getString("typ")));
              }      
      }
       rs.close();
       pst.close();
            pageList=(ArrayList)pageDtl.get("BUYER");
            if(pageList!=null && pageList.size() >0){
            String loadbyr="select distinct byr,nme_idn from brc_pndg_v  where typ in('DLV') "+pktTycon+" order by byr";
         
              outLst = db.execSqlLst("loadqry", loadbyr, new ArrayList());
              pst = (PreparedStatement)outLst.get(0);
               rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                  bDtl=new HashMap();
                        bDtl.put("byr", util.nvl(rs.getString("byr")));
                        bDtl.put("nmeidn",util.nvl(rs.getString("nme_idn")));
                        byrList.add(bDtl);
            }
            rs.close();
            pst.close();
            }
            
            String loademp="select distinct emp,nvl(emp_idn,'0') emp_idn from brc_pndg_v  where typ in('DLV') "+pktTycon+" order by emp";
          outLst = db.execSqlLst("loadqry", loademp, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                  empDtl=new HashMap();
                  empDtl.put("emp", util.nvl(rs.getString("emp")));
                  empDtl.put("empid",util.nvl(rs.getString("emp_idn")));
                  empList.add(empDtl);
            }
            rs.close();
            pst.close();
            
            String rowttlQ="select byr , emp, sum(cnt) cnt , sum(qty) qty,to_char(sum(trunc(cts,2)),'99999999999999990.99') cts,\n" + 
            "to_char(sum(decode(pkt_ty,'SMX',(val/cts)*trunc(cts,2),val)),'9999999999990.99')  vlu \n" + 
            "from brc_pndg_v \n" + 
            "where typ in('DLV') "+pktTycon+" \n" +conQ +
            " group by byr ,emp";
          outLst = db.execSqlLst("loadqry", rowttlQ, ary);
          pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
            String emp=util.nvl(rs.getString("emp"));
            String byr=util.nvl(rs.getString("byr"));
            saleTotalsDtl.put(emp+"_"+byr+"_CNT", util.nvl(rs.getString("cnt")));
            saleTotalsDtl.put(emp+"_"+byr+"_QTY", util.nvl(rs.getString("qty")));
            saleTotalsDtl.put(emp+"_"+byr+"_CTS", util.nvl(rs.getString("cts")));
            saleTotalsDtl.put(emp+"_"+byr+"_VLU", util.nvl(rs.getString("vlu")));
            }
            rs.close();
            pst.close();
            
            String colttlQ="select typ, sum(cnt) cnt , sum(qty) qty,to_char(sum(trunc(cts,2)),'99999999999999990.99') cts,\n" + 
            "to_char(sum(decode(pkt_ty,'SMX',(val/cts)*trunc(cts,2),val)),'9999999999990.99')  vlu \n" + 
            "from brc_pndg_v \n" + 
            "where typ in('DLV') "+pktTycon+" \n" +conQ +
            " group by typ";
          outLst = db.execSqlLst("loadqry", colttlQ, ary);
          pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                String typttl=util.nvl(rs.getString("typ"));
                saleTotalsDtl.put(typttl+"_CNT", util.nvl(rs.getString("cnt")));
                saleTotalsDtl.put(typttl+"_QTY", util.nvl(rs.getString("qty")));
                saleTotalsDtl.put(typttl+"_CTS", util.nvl(rs.getString("cts")));
                saleTotalsDtl.put(typttl+"_VLU", util.nvl(rs.getString("vlu")));
            }
            rs.close();
            pst.close();
            
            String ttlQ="select sum(cnt) cnt , sum(qty) qty,to_char(sum(trunc(cts,2)),'99999999999999990.99') cts,\n" + 
            "to_char(sum(trunc(val,2)),'9999999999990.99')  vlu \n" + 
            "from brc_pndg_v \n" + 
            " where typ in('DLV') "+pktTycon+" \n"+conQ ;
          outLst = db.execSqlLst("loadqry", ttlQ, ary);
          pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                saleTotalsDtl.put("CNT", util.nvl(rs.getString("cnt")));
                saleTotalsDtl.put("QTY", util.nvl(rs.getString("qty")));
                saleTotalsDtl.put("CTS", util.nvl(rs.getString("cts")));
                saleTotalsDtl.put("VLU", util.nvl(rs.getString("vlu")));
            }
            rs.close();
            pst.close();
            pendingForm.reset();
      pendingForm.setPendingNme(pendingNme);    
      req.setAttribute("empList", empList); 
      req.setAttribute("byrDtl", byrDtl); 
      req.setAttribute("byr_idn", byr_idn); 
      req.setAttribute("brcdlvDtl", saleDtl); 
      req.setAttribute("brcdlvTotalsDtl", saleTotalsDtl); 
      req.setAttribute("brcdlvtyp", saletyp);
      req.setAttribute("byrList", byrList);
      pendingForm.setValue("pkt_ty", pkt_ty);   

     
     ArrayList grpList = (ArrayList)session.getAttribute("grpcompanyList");
     if(grpList==null || grpList.size()==0){
     grpList=util.groupcompany();
     session.setAttribute("grpcompanyList", grpList);
     }
            allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            pageDtl=(HashMap)allPageDtl.get("BRNCH_DELIVERY");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("BRNCH_DELIVERY");
            allPageDtl.put("BRNCH_DELIVERY",pageDtl);
            }
            info.setPageDetails(allPageDtl);
     util.updAccessLog(req,res,"Pending Brc Dlv", "Pending Brc dlv load done");
      return am.findForward("loadbrcDlv");
        }
    }
    public PendingFMemoAction() {
        super();
    }
}
