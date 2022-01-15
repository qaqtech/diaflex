package ft.com.mixakt;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
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

public class AutoJangadAction extends DispatchAction {
    
    public AutoJangadAction() {
        super();
    }
    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
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
          AutoJangadForm udf = (AutoJangadForm)af;
          HashMap pktDtlMap = new HashMap();
          String mstkIdn="";
          String pktIdnSql="select idn, stt,qty,qty - nvl(qty_iss,0) qtyohd,qty_iss, cts ,nvl(cts,0)-nvl(cts_iss,0) ctsohd, cts_iss , b.val,m.upr\n" + 
          "             from mstk m, dbms_sys_info a , stk_dtl b\n" + 
          "           where m.idn = a.val and a.prp =?  \n" + 
          "           and m.idn=b.mstk_idn and b.mprp=? and b.grp=1 ";
          ArrayList ary = new ArrayList();
          ary.add("SYS_APDKJT");
          ary.add("BOX_ID");
          ArrayList rsLst = db.execSqlLst("pktIdnSql", pktIdnSql, ary);
          PreparedStatement pst = (PreparedStatement)rsLst.get(0);
          ResultSet rs = (ResultSet)rsLst.get(1);
          while(rs.next()){
              mstkIdn= rs.getString("idn");
              HashMap pktList = new HashMap();
              pktList.put("STKIDN",mstkIdn);
              pktList.put("STT",rs.getString("stt"));
              pktList.put("CTS",rs.getString("cts"));
              pktList.put("CTSHD",rs.getString("ctsohd"));
              pktList.put("CTS_ISS",rs.getString("cts_iss"));
              pktList.put("BOXID",rs.getString("val"));
              pktList.put("UPR",rs.getString("upr"));
              pktList.put("QTY",rs.getString("qty"));
              pktList.put("QTYHD",rs.getString("qtyohd"));
              pktList.put("QTY_ISS",rs.getString("qty_iss"));
              pktDtlMap.put("PKTDTL", pktList);
             
          }
          rs.close();
          pst.close();
          String nmeIdn="";
          String nmeIdnSql=" select min(nme_idn) nmeIdn from nme_dtl where mprp = ? and txt = ? ";
          ary = new ArrayList();
          ary.add("AUTO_JANGAD");
          ary.add("YES");
          rsLst = db.execSqlLst("nmeIdnSql", nmeIdnSql, ary);
          pst = (PreparedStatement)rsLst.get(0);
          rs = (ResultSet)rsLst.get(1);
          if(rs.next()){
              nmeIdn = rs.getString("nmeIdn");
          }
          rs.close();
          pst.close();
          udf.setValue("NME_IDN", nmeIdn);
          String nmerel_idn="";
          String nmeRelIdn = "select nmerel_idn  from nmerel where nme_idn = ? and dflt_yn = 'Y'";
          ary = new ArrayList();
          ary.add(nmeIdn);
          rsLst = db.execSqlLst("nmeRelIdn", nmeRelIdn, ary);
          pst = (PreparedStatement)rsLst.get(0);
          rs = (ResultSet)rsLst.get(1);
          if(rs.next()){
              nmerel_idn = rs.getString("nmerel_idn");
          }
          rs.close();
          pst.close();
          udf.setValue("NMEREL_IDN", nmerel_idn);
          String memoDtl =" select byr.get_nm(a.nme_idn) nme, a.idn,to_char(a.dte, 'dd-Mon-rrrr') dte,b.qty,nvl(b.qty,0)-nvl(b.qty_rtn,0) qtyIss,b.qty_rtn,b.cts,nvl(b.cts,0)-nvl(b.cts_rtn,0) ctsIss, b.cts_rtn,nvl(b.fnl_sal,b.quot) quot from mjan a, jandtl b " + 
          "  where a.idn=b.idn and a.nme_idn=? and a.nmerel_idn=? and b.mstk_idn=? and a.typ=? and b.stt=?";
          ary = new ArrayList();
          ary.add(nmeIdn);
          ary.add(nmerel_idn);
          ary.add(mstkIdn);
          ary.add("E");
          ary.add("IS");
          rsLst = db.execSqlLst("memoDtl", memoDtl, ary);
          pst = (PreparedStatement)rsLst.get(0);
          rs = (ResultSet)rsLst.get(1);
          if(rs.next()){
              HashMap pktList = new HashMap();
              pktList.put("STKIDN",mstkIdn);
              pktList.put("NME",rs.getString("nme"));
              pktList.put("IDN",rs.getString("idn"));
              pktList.put("DTE",rs.getString("dte"));
              pktList.put("CTS",rs.getString("cts"));
              pktList.put("CTSISS",rs.getString("ctsIss"));
              pktList.put("CTSRTN",rs.getString("cts_rtn"));
              pktList.put("QTY",rs.getString("qty"));
              pktList.put("QTYISS",rs.getString("qtyIss"));
              pktList.put("QTYRTN",rs.getString("qty_rtn"));
              pktList.put("QUOT",rs.getString("quot"));
              pktDtlMap.put("MEMODTL", pktList);
          }
          rs.close();
          pst.close();
          ArrayList memoList = new ArrayList();
          String memoNoDtl =" select byr.get_nm(a.nme_idn) nme, a.idn,to_char(a.dte, 'dd-Mon-rrrr') dte,b.qty,nvl(b.qty,0)-nvl(b.qty_rtn,0) qtyIss,b.qty_rtn,b.cts,nvl(b.cts,0)-nvl(b.cts_rtn,0) ctsIss, b.cts_rtn,nvl(b.fnl_sal,b.quot) quot from mjan a, jandtl b " + 
          "  where a.idn=b.idn and a.nme_idn not in (?)  and b.mstk_idn=?  and b.stt=?";
          ary = new ArrayList();
          ary.add(nmeIdn);
          ary.add(mstkIdn);
          ary.add("IS");
          rsLst = db.execSqlLst("memoDtl", memoNoDtl, ary);
          pst = (PreparedStatement)rsLst.get(0);
          rs = (ResultSet)rsLst.get(1);
          while(rs.next()){
              HashMap pktList = new HashMap();
              pktList.put("STKIDN",mstkIdn);
              pktList.put("NME",rs.getString("nme"));
              pktList.put("IDN",rs.getString("idn"));
              pktList.put("DTE",rs.getString("dte"));
              pktList.put("CTS",rs.getString("cts"));
              pktList.put("CTSISS",rs.getString("ctsIss"));
              pktList.put("CTSRTN",rs.getString("cts_rtn"));
              pktList.put("QTY",rs.getString("qty"));
              pktList.put("QTYISS",rs.getString("qtyIss"));
              pktList.put("QTYRTN",rs.getString("qty_rtn"));
              pktList.put("QUOT",rs.getString("quot"));
              memoList.add(pktList);
              
          }
          pktDtlMap.put("OTHMEMODTL", memoList);
          rs.close();
          pst.close();
          
          String summary =" select sum(nvl(b.qty,0)) qty,sum(nvl(b.qty_rtn,0)) qtyRtn, sum(nvl(b.qty,0))-sum(nvl(b.qty_rtn,0)) brq,sum(b.cts) cts,sum(b.cts_rtn) ctsRtn, sum(b.cts)-sum(b.cts_rtn) brc from mjan a, jandtl b " + 
          "  where a.idn=b.idn and a.nme_idn not in (?)  and b.mstk_idn=?  and b.stt=?";
          ary = new ArrayList();
          ary.add(nmeIdn);
          ary.add(mstkIdn);
          ary.add("IS");
          rsLst = db.execSqlLst("memoDtl", summary, ary);
          pst = (PreparedStatement)rsLst.get(0);
          rs = (ResultSet)rsLst.get(1);
          while(rs.next()){
              pktDtlMap.put("ISSCTS", rs.getString("cts"));
              pktDtlMap.put("RTNCTS", rs.getString("ctsRtn"));
              pktDtlMap.put("BRCCTS", rs.getString("brc"));
              pktDtlMap.put("ISSQTY", rs.getString("qty"));
              pktDtlMap.put("RTNQTY", rs.getString("qtyRtn"));
              pktDtlMap.put("BRCQTY", rs.getString("brq"));
          }
          rs.close();
          pst.close();
          req.setAttribute("PKTDTLMAP", pktDtlMap);
        
          
          
          return am.findForward("load");   
      }
    }
    
    public ActionForward Save(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
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
          AutoJangadForm udf = (AutoJangadForm)af;
          String pktqty =  util.nvl((String)udf.getValue("pktqty"),"0");
          String pktcts =  util.nvl((String)udf.getValue("pktcts"),"0");
          String pktqtyiss =  util.nvl((String)udf.getValue("pktqtyiss"),"0");
          String pktctsiss =  util.nvl((String)udf.getValue("pktctsiss"),"0");
          String pktidn =  util.nvl((String)udf.getValue("pktidn"),"0");
          
          
          String updateMstk ="update mstk set qty=?,qty_iss=?,cts=? , cts_iss=? where idn = ?";
          ArrayList ary = new ArrayList();
          ary.add(String.valueOf(pktqty));
          ary.add(String.valueOf(pktqtyiss));
          ary.add(String.valueOf(pktcts));
          ary.add(String.valueOf(pktctsiss));
          ary.add(pktidn);
          int ct = db.execUpd("update mstk", updateMstk, ary);
          
          String memoCtr = util.nvl(req.getParameter("memoCr"));
          if(memoCtr.equals("YES")){
              String nmeIdn =  util.nvl((String)udf.getValue("NME_IDN"),"0");
              String nmeRelIdn =  util.nvl((String)udf.getValue("NMEREL_IDN"),"0");
              String pktctshd =  util.nvl((String)udf.getValue("memoctsiss"),"0");
              String pktqtyhd =  util.nvl((String)udf.getValue("memoqtyiss"),"0");
              String quot =  util.nvl((String)udf.getValue("quot"),"0");
              String genHdr = "memo_pkg.gen_hdr(pByrId => ?, pRlnIdn => ?, pStt => ?  , pTyp => ? ,pflg => ? , pMemoIdn => ?)";
              ary = new ArrayList();
              ary.add(nmeIdn);
              ary.add(nmeRelIdn);
              ary.add("IS");
              ary.add("E");
              ary.add("NN");
              
              ArrayList out = new ArrayList();
              out.add("I");
              CallableStatement cst = null;
              cst =db.execCall("MKE_HDR ", genHdr , ary , out);
              int memoId = cst.getInt(ary.size()+1);
            cst.close();
            cst=null;
              
              ary = new ArrayList();
              ary.add(Integer.toString(memoId));
              ary.add(pktidn);
              ary.add(pktqtyhd);
              ary.add(pktctshd);
              ary.add(quot);
              ary.add("IS");
             int ct1 = db.execCall("pop Memo pkt", "MEMO_PKG.Pop_Memo_Pkt(pMemoIdn => ? , pStkIdn => ?, pQty => ?, pCts => ?  ,  pQuot => ? ,  pStt => ? )", ary);
              
          
          }else{
          String memocts =  util.nvl((String)udf.getValue("memocts"),"0");
          String memoctsrtn =  util.nvl((String)udf.getValue("memoctsrtn"),"0");
          String memoqty =  util.nvl((String)udf.getValue("memoqty"),"0");
          String memoqtyrtn =  util.nvl((String)udf.getValue("memoqtyrtn"),"0");
          String memoctsprc =  util.nvl((String)udf.getValue("memoctsprc"),"0");
          String memoIdn =  util.nvl((String)udf.getValue("memoIdn"),"0");
          String memomstkIdn =  util.nvl((String)udf.getValue("memomstkIdn"),"0");
          String updatejandtl ="update jandtl set qty=?,qty_rtn=?,cts=? , cts_rtn=? , fnl_sal=? , quot=? where idn = ? and mstk_idn=?";
           ary = new ArrayList();
          ary.add(String.valueOf(memoqty));
          ary.add(String.valueOf(memoqtyrtn));
          ary.add(String.valueOf(memocts));
          ary.add(String.valueOf(memoctsrtn));
          ary.add(memoctsprc);
          ary.add(memoctsprc);
          ary.add(memoIdn);
          ary.add(memomstkIdn);
          ct = db.execUpd("update jandtl", updatejandtl, ary);
          }
          
          return load(am, af, req, res);
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
                util.updAccessLog(req,res,"Mix Assort Rtn", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Mix Assort Rtn", "init");
            }
            }
            return rtnPg;
            }
   
}
