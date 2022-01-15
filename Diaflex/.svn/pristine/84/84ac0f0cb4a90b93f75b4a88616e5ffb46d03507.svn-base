package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.SearchQuery;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

public class MemoDetailAction extends DispatchAction {

    
    public MemoDetailAction() {
        super();
    }
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
        }
        }else
        rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
            util.updAccessLog(req,res,"Memo Deatils", "load start");
        SearchQuery query = new SearchQuery();
        ArrayList idnList = new ArrayList();
        MemoDetailForm udf = (MemoDetailForm)form;
        String conQtyp="";
        String conQstt="";
        String typ=util.nvl(req.getParameter("typ"));
        String stt=util.nvl(req.getParameter("stt"));
        String client = (String)info.getDmbsInfoLst().get("CNT");
        if(typ.equals(""))
               typ=util.nvl(udf.getTyp()); 
        if(!typ.equals(""))
            conQtyp=" where a.typ='"+typ+"' ";
        if(stt.equals(""))
            stt=util.nvl((String)udf.getValue("stt")); 
        if(typ.equals("") && !stt.equals(""))
            conQstt=" where a.stt='"+stt+"' ";
        if(!typ.equals("") && !stt.equals(""))
            conQstt=" and a.stt='"+stt+"' ";
        ArrayList pktList = new ArrayList();
        String memoDtl = "select a.idn, a.dte, a.byr, a.emp, a.typ, a.byr_cabin,a.thru "+
                         " , a.qty iss_qty, a.cts iss_cts, to_char(a.vlu,'99999990.00') iss_vlu,to_char(a.avg,'99999990.00') iss_avg "+
                         " from dly_memo_ttl_v a  "+conQtyp+conQstt +" order by a.idn desc ";
        if(client.equals("ag")){
            memoDtl = "select a.idn, a.dte, a.byr, a.emp, a.typ, a.byr_cabin,a.thru,a.rmk,a.note_person "+
            " , a.qty iss_qty, a.cts iss_cts, to_char(a.vlu,'99999990.00') iss_vlu,to_char(a.avg,'99999990.00') iss_avg "+
            " from dly_memo_ttl_v a  "+conQtyp+conQstt +" order by a.idn desc ";      
        }

            ArrayList outLst = db.execSqlLst("memoDtl", memoDtl, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            HashMap pktDtl = new HashMap();
            pktDtl.put("IDN", util.nvl(rs.getString("idn")));
            pktDtl.put("DTE", util.nvl(rs.getString("dte")));
            pktDtl.put("BYRCB", util.nvl(rs.getString("byr_cabin")));
            pktDtl.put("TYP", util.nvl(rs.getString("typ")));
            pktDtl.put("BUY", util.nvl(rs.getString("byr")));
            pktDtl.put("THRU", util.nvl(rs.getString("thru")));
            pktDtl.put("EMP", util.nvl(rs.getString("emp")));
            pktDtl.put("ISSQTY", util.nvl(rs.getString("iss_qty")));
            pktDtl.put("ISSCTS", util.nvl(rs.getString("iss_cts")));
            pktDtl.put("ISSVLU", util.nvl(rs.getString("iss_vlu")));
            pktDtl.put("ISSAVG", util.nvl(rs.getString("iss_avg")));
            if(client.equals("ag")){
                pktDtl.put("RMK", util.nvl(rs.getString("rmk")));
                pktDtl.put("NOTE_PERSON", util.nvl(rs.getString("note_person")));
            }
            pktList.add(pktDtl);
            idnList.add(util.nvl(rs.getString("idn")));
        }
        rs.close(); pst.close();
        String rtnData = "select a.idn , a.qty , a.cts , to_char(a.vlu,'9999999990.00') vlu , to_char(a.avg,'9999999990.00') avg from dly_memo_ir_v a  "+conQtyp ;

            outLst = db.execSqlLst("memoDtl", rtnData, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
         while(rs.next()){
            String idn = util.nvl(rs.getString("idn"));
            int idnIndex = idnList.indexOf(idn);
             if(idnIndex !=-1){
             HashMap pktDtl = (HashMap)pktList.get(idnIndex);
             pktDtl.put("RTNQTY", util.nvl(rs.getString("qty")));
             pktDtl.put("RTNCTS", util.nvl(rs.getString("cts")));
             pktDtl.put("RTNVLU", util.nvl(rs.getString("vlu")));
             pktDtl.put("RTNAVG", util.nvl(rs.getString("avg")));
             pktList.set(idnIndex, pktDtl);
             }
         }
        rs.close(); pst.close();
        req.setAttribute("pktList", pktList);
        ArrayList memoList = query.getMemoType(req,res);
//        udf.setValue("memoList", memoList);
        udf.setMemoList(memoList);
        udf.setTyp(typ);
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("MEMO_DTL");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("MEMO_DTL");
        allPageDtl.put("MEMO_DTL",pageDtl);
        }
        info.setPageDetails(allPageDtl);
            util.updAccessLog(req,res,"Memo Deatils", "load end");
        return am.findForward("load");
        }
    }
    public ActionForward pktDtlXL(ActionMapping am, ActionForm form, HttpServletRequest req,
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
                      util.updAccessLog(req,res,"Memo Deatils", "pktDtlXL start");
                  MemoDetailForm udf = (MemoDetailForm)form;
                  String memoIdn = util.nvl(req.getParameter("memoIdn"));
                  int ct = db.execUpd("delete gtSrch", "DELETE FROM gt_srch_rslt ", new ArrayList());
                  ArrayList pktDtlList = new ArrayList();
                  ArrayList itemHdr = new ArrayList();
                  HashMap dbinfo = info.getDmbsInfoLst();
                  String cnt = (String)dbinfo.get("CNT");
                  String ordrby="" ;
                  ArrayList prps = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");
                  itemHdr.add("SRNO");
                  itemHdr.add("MEMOIDN");
                  itemHdr.add("VNM");
                  int count=1;
                  ArrayList ary = new ArrayList();
                  if(!memoIdn.equals("")){
                    String gtinsertQ="insert into gt_srch_rslt(sk1,srch_id,stk_idn,vnm,byr,stt,quot,fquot,cts,prte,rap_rte,cmnt,flg,exh_rte)\n" 
    +
                                    "select b.sk1,a.idn , a.mstk_idn,b.vnm,byr.get_nm(mj.nme_idn) byr, b.stt ,quot, nvl(fnl_sal, quot) ,\n" +
                                    "b.cts, nvl(b.upr, b.cmp) rte,b.rap_rte,b.tfl3 ,'Z',mj.exh_rte\n" +
                                    "from mjan mj,jandtl a, mstk b where mj.idn=a.idn and a.mstk_idn = b.idn and a.idn= ? order by sk1 " ;

                     ary.add(memoIdn);
                     ct = db.execUpd("Insert GT", gtinsertQ, ary);

                     if(cnt.equals("hk")){
                          itemHdr.add("STATUS");
                          ordrby=" decode(stt,'MKAV','A','MKIS','B','C')," ;
                          ct = db.execCall("DP_UPD_GT_BYR"," DP_UPD_GT_BYR", new ArrayList());
                          ct = db.execUpd("update gt_srch_rslt BYR", "update gt_srch_rslt set  byr = null where stt='MKAV' " , new ArrayList());

                         }
                     ArrayList prpDspBlocked = info.getPageblockList();
                     for(int j=0; j < prps.size(); j++) {
                                              String lprp=(String)prps.get(j);
                                              
    if(prpDspBlocked.contains(lprp)){
                                              }else{
                                              itemHdr.add(lprp);
                     }}

                    String getPktData ="select "+ordrby+" nvl(sk1,1) sk1,srch_id memoIdn , stk_idn mstk_idn , vnm ,byr , cmnt tfl3 , fquot memoQuot, quot, cts, prte rte, rap_rte,byr_cntry byr_cb ,\n" +
                                      "to_char(trunc(cts,2) * rap_rte,'99999999990.00') rapVlu ,stt,\n" +
                                      "decode(rap_rte, 1, '',trunc(((prte/greatest(rap_rte,1))*100)-100,2)) dis,\n" +
                                      "decode(rap_rte, 1, '', trunc((((nvl(fquot, quot)/exh_rte)/greatest(rap_rte,1))*100)-100,2)) mDis from gt_srch_rslt where flg='Z' order by 1 ";


                     ArrayList outLst = db.execSqlLst("pktDtl", getPktData, new ArrayList());
                     PreparedStatement pst = (PreparedStatement)outLst.get(0);
                     ResultSet rs = (ResultSet)outLst.get(1);
                  while(rs.next()){
                    HashMap pktDtl = new HashMap();
                      pktDtl.put("SRNO", String.valueOf(count++));
                      pktDtl.put("MEMOIDN",
         util.nvl((String)rs.getString("memoIdn")));
                      pktDtl.put("VNM",
    util.nvl((String)rs.getString("vnm")));
                      pktDtl.put("Buyer",
    util.nvl((String)rs.getString("byr")));
                      pktDtl.put("STATUS",
    util.nvl((String)rs.getString("stt")));
                      pktDtl.put("RapRte",
         util.nvl((String)rs.getString("rap_rte")));
                      pktDtl.put("RapVlu",
         util.nvl((String)rs.getString("rapVlu")));
                      pktDtl.put("DIS",
    util.nvl((String)rs.getString("dis")));
                      pktDtl.put("Prc / Crt",
         util.nvl((String)rs.getString("rte")));
                      pktDtl.put("ByrDis",
         util.nvl((String)rs.getString("mDis")));
                      pktDtl.put("Quot",
         util.nvl((String)rs.getString("memoQuot")));
                      pktDtl.put("Buyer CB",
         util.nvl((String)rs.getString("byr_cb")));

                      String pktIdn = rs.getString("mstk_idn");
                      String getPktPrp =
                                  " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
                                  + " from stk_dtl a, mprp b, rep_prp c "
                                  + " where a.mprp = b.prp and b.prp = c.prp and c.mdl = 'MEMO_RTRN' and a.mstk_idn = ? and a.grp=1 "
                                  + " order by c.rnk, c.srt ";

                      ary = new ArrayList();
                      ary.add(pktIdn);

                      ArrayList outLst1 = db.execSqlLst(" Pkt Prp", getPktPrp, ary);
                      PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
                      ResultSet rs1 = (ResultSet)outLst.get(1);
                      while (rs1.next()) {
                          String lPrp = rs1.getString("mprp");
                          String lVal = rs1.getString("val");

                          pktDtl.put(lPrp, lVal);
                      }
                      pktDtlList.add(pktDtl);
                      rs1.close(); pst1.close();
                  }
                      rs.close(); pst.close();
                 }
                 itemHdr.add("RapRte");
                 itemHdr.add("RapVlu");
                 itemHdr.add("DIS");
                 itemHdr.add("Prc / Crt");
                 itemHdr.add("ByrDis");
                 itemHdr.add("Quot");
                 if(cnt.equals("hk")){
                 itemHdr.add("Buyer CB");
                 itemHdr.add("Buyer");
                 }
                 session.setAttribute("pktList", pktDtlList);
                 session.setAttribute("itemHdr",itemHdr);
                 util.updAccessLog(req,res,"Memo Deatils", "pktDtlXL end");
                  return am.findForward("pktDtl");
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
                util.updAccessLog(req,res,"Memo Deatils", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Memo Deatils", "init");
            }
            }
            return rtnPg;
            }
}
