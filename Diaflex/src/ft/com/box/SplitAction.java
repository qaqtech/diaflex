package ft.com.box;
import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import java.io.IOException;

import java.sql.Connection;
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

public class SplitAction  extends DispatchAction {
    public SplitAction() {
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
        SplitForm udf = (SplitForm)af;
        udf.reset();
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("SPLIT_FORM");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("SPLIT_FORM");
            allPageDtl.put("SPLIT_FORM",pageDtl);
            }
            info.setPageDetails(allPageDtl);
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
        SplitForm udf = (SplitForm)af;
        String boxtyp=util.nvl(req.getParameter("boxtyp"));
        String vnmLst=util.nvl((String)udf.getValue("vnmLst"));
        String status=util.nvl((String)udf.getValue("status"));
        String sqlQ="";
        ArrayList pktList=new ArrayList();
        ArrayList pktListDtl=new ArrayList();
        udf.setValue("stt", status);
        status=util.getVnm(status);
        if(vnmLst.equals("")){
            sqlQ="    Select a.vnm,a.idn,a.sk1\n" + 
            "    From Mstk A,Stk_Dtl B\n" + 
            "    Where\n" + 
            "    a.idn=b.mstk_idn and b.grp=1 and b.mprp='BOX_TYP' and a.pkt_ty not in('NR') and a.stt in("+status+") and b.val in('"+boxtyp+"') order by a.sk1";
        }else{
            vnmLst=util.getVnm(vnmLst);
            sqlQ="    Select a.vnm,a.idn,a.sk1\n" + 
            "    From Mstk A\n" + 
            "    Where\n" + 
            "    a.pkt_ty not in('NR') and a.vnm in("+vnmLst+") and a.stt in("+status+")  order by a.sk1";
        }
            ResultSet rs = db.execSql("vnmLst", sqlQ, new ArrayList());
            while(rs.next()) {
                pktListDtl=new ArrayList();
                pktListDtl.add(rs.getString("idn"));
                pktListDtl.add(rs.getString("vnm"));
                pktList.add(pktListDtl);
            }
            rs.close();
            session.setAttribute("splitpktList", pktList);
            req.setAttribute("view", "Y");
        return am.findForward("load");
        }
    }
    
    
    public ActionForward save(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
    util.updAccessLog(req,res,"Split Form", "save start");
    SplitForm udf = (SplitForm)form;
    String stkIdn = util.nvl((String)udf.getValue("splitvnm"));
    String stt = util.nvl((String)udf.getValue("stt"));
    int noofrows = Integer.parseInt(util.nvl((String)udf.getValue("noofrows")));
    String frmPkt = "";
    String toPkt = "";
    boolean exemodroot=false;
    ArrayList ary = new ArrayList();
    String lQtyNew = util.nvl(req.getParameter("newfromqty"));
    String lCtsNew = util.nvl(req.getParameter("newfromcts"));
    String lRteNew = util.nvl(req.getParameter("newfromrte"));
    for(int i=1; i <= noofrows; i++) {
    String lStkIdn = util.nvl((String)udf.getValue("tovnm_"+i));
    String lQty = util.nvl(req.getParameter("addqty_"+i));
    String lCts = util.nvl(req.getParameter("addcts_"+i));
    String lRte = util.nvl(req.getParameter("addrte_"+i));
    String boxtyp = util.nvl(req.getParameter("boxtyp_"+i));
    if(!lStkIdn.equals("0")) {
    toPkt = lStkIdn ;
    frmPkt = stkIdn ;
    if(!lCts.equals("0") && !lCtsNew.equals("0")){
    ary = new ArrayList();
    ary.add(frmPkt);
    ary.add(toPkt);
    ary.add(lCts);

    String mergeParams = "pFrm => ?, pTo => ?, pCts => ?";
    if(lQty.length() > 0) {
    mergeParams += ", pQty => ?";
    ary.add(lQty);
    }
    if(lRte.length() > 0) {
    mergeParams += ", pAvgRte => ?";
    ary.add(lRte);
    }
    String mergeSplitMixPkt = "MIX_PKG.BOX_MERGE_PKT("+ mergeParams +")";
//    System.out.println("mergeParams:=>"+ary.toString());
    int cnt = db.execCall("Merge : "+ frmPkt + " to " + toPkt, mergeSplitMixPkt, ary);
    }
    }else{
        if(!lCts.equals("0") && !lCtsNew.equals("0")){
        ary = new ArrayList();
        ary.add(boxtyp);
        ary.add(stt);
        ary.add(lCts);
        ary.add(lQty);
        ary.add(lRte);
        ary.add(lStkIdn);
        String mergeSplitMixPkt = "DP_SPLIT_MIX_PKT(pBoxTyp => ?,pStt  => ?,pCts  => ?,pQty => ?,pRte => ?,pIdn  => ?)";
        int cnt = db.execCall("Merge : "+ frmPkt + " to " + toPkt, mergeSplitMixPkt, ary);
        if(cnt>0)
        exemodroot=true;
        }
    }
    }
    if(exemodroot){
        String modRtPkt = "MIX_PKG.MOD_RT_PKT(pIdn => ?, pCts => ?, pRte => ?, pQty => ?)";
        ArrayList params = new ArrayList();
        params.add(stkIdn);
        params.add(lCtsNew);
        params.add(lRteNew);
        params.add(lQtyNew);
        int cnt = db.execCall("ModRt : " + stkIdn, modRtPkt, params);          
    }
    req.setAttribute("msg", "Split Done successfully..");
    util.updAccessLog(req,res,"Split Form", "save end");
    return load(am, form, req, res);
    }
    }
    
    
    public String getDataVal(String usrVal, String sysVal) {
        if(usrVal.length() == 0)
            return sysVal ;
        else
            return usrVal ;
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
                util.updAccessLog(req,res,"Single to Smx", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Single to Smx", "init");
            }
            }
            return rtnPg;
            }
}
