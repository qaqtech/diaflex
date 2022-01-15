package ft.com.marketing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.io.IOException;

import java.io.OutputStream;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class NewStockTallyAction  extends DispatchAction {
    
    public NewStockTallyAction() {
        super();
    }
    
    public ActionForward loadTally(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
       StockTallyForm  udf = (StockTallyForm)af;
       udf.reset();
       GenericInterface genericInt = new GenericImpl();
       util.updAccessLog(req,res,"Stock Tally", "Stock Tally load start");
       ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_StkTllySrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_StkTllySrch"); 
       info.setGncPrpLst(assortSrchList);
       HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
       HashMap pageDtl=(HashMap)allPageDtl.get("STOCK_TALLY");
       if(pageDtl==null || pageDtl.size()==0){
       pageDtl=new HashMap();
       pageDtl=util.pagedef("STOCK_TALLY");
       allPageDtl.put("STOCK_TALLY",pageDtl);
       }
       TallyStockstatus(req);
       TallyStock(req,"");
       info.setPageDetails(allPageDtl);
       util.updAccessLog(req,res,"Stock Tally", "Stock Tally load end");
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
         StockTallyForm udf = (StockTallyForm)af;
         HashMap mprp = info.getMprp();
         HashMap prp = info.getPrp();
         util.updAccessLog(req,res,"Stock Tally", "Stock Tally fetch start");
            ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_StkTllySrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_StkTllySrch"); 
        info.setGncPrpLst(genricSrchLst);
        //      ArrayList genricSrchLst = info.getGncPrpLst();
        HashMap  paramsMap = new HashMap();
        String mstkStt = "ALL";
        String[] stt =  udf.getSttValLst();
        if(stt!=null){
        if(stt.length>0)
            mstkStt = stt[0];
            for(int i=0;i<genricSrchLst.size();i++){
                ArrayList prplist =(ArrayList)genricSrchLst.get(i);
                String lprp = (String)prplist.get(0);
                String flg= (String)prplist.get(1);
                String typ = util.nvl((String)mprp.get(lprp+"T"));
                String prpSrt = lprp ;  
                if(flg.equals("M")) {
                
                    ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
                    ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
                    for(int j=0; j < lprpS.size(); j++) {
                    String lSrt = (String)lprpS.get(j);
                    String lVal = (String)lprpV.get(j);    
                    String reqVal1 = util.nvl((String)udf.getValue(lprp + "_" + lVal),"");
                    if(!reqVal1.equals("")){
                    paramsMap.put(lprp + "_" + lVal, reqVal1);
                    }
                       
                    }
                }else{
                String fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
                String fldVal2 = util.nvl((String)udf.getValue(lprp+"_2"));
                if(typ.equals("T")){
                    fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
                    fldVal2 = fldVal1;
                }
                if(fldVal2.equals(""))
                fldVal2=fldVal1;
                if(!fldVal1.equals("") && !fldVal2.equals("")){
                    paramsMap.put(lprp+"_1", fldVal1);
                    paramsMap.put(lprp+"_2", fldVal2);
                }
                }
            }
        
        if(paramsMap.size()>0){
        paramsMap.put("stt", mstkStt);
        paramsMap.put("cprp", "GRP_STT");
        paramsMap.put("cprpValLst", stt);
        paramsMap.put("mdl", "RFID_VW");
        paramsMap.put("PRCD","STK_TLY");
        util.genericSrch(paramsMap);
        req.setAttribute("seqMsg", "Y");
        }else{
            req.setAttribute("msg", "Please Select Stock Criteria.");
        }
         TallyStock(req,"");
        }else{
            req.setAttribute("msg", "Please Select Status.");  
        }
        udf.reset();
            util.updAccessLog(req,res,"Stock Tally", "Stock Tally fetch end");
      return am.findForward("load");
        }
    }
    public ActionForward fetchSeq(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        StockTallyForm udf = (StockTallyForm)af;
        util.updAccessLog(req,res,"Stock Tally", "Stock Tally fetch Seq start");
            String loadSeq = util.nvl((String)udf.getValue("seq"));
            String redirect = util.nvl((String)udf.getValue("redirect"),"load");
            TallyStock(req,loadSeq);
            req.setAttribute("seqNo", loadSeq);
            if(redirect.equals("loadRtn")){
            HashMap flgMap=getstktlynf(req,loadSeq);
            req.setAttribute("sttMap", flgMap);
            }
        util.updAccessLog(req,res,"Stock Tally", "Stock Tally fetch Seq end");
        return am.findForward(redirect);
        }
    }
    public ActionForward loadRtn(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
         StockTallyForm udf = (StockTallyForm)af;
         udf.reset();
         util.updAccessLog(req,res,"Stock Tally", "Stock Tally");
//         ArrayList rfiddevice = ((ArrayList)info.getRfiddevice() == null)?new ArrayList():(ArrayList)info.getRfiddevice();
//         if(rfiddevice.size() == 0) {
//         util.rfidDevice();    
//         }
         HashMap flgMap=getstktlynf(req,"");
         req.setAttribute("sttMap", flgMap);
          HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
          HashMap pageDtl=(HashMap)allPageDtl.get("STOCK_TALLY");
          if(pageDtl==null || pageDtl.size()==0){
          pageDtl=new HashMap();
          pageDtl=util.pagedef("STOCK_TALLY");
          allPageDtl.put("STOCK_TALLY",pageDtl);
          }
          info.setPageDetails(allPageDtl);
             TallyStockstatus(req);
             TallyStock(req,"");
             util.updAccessLog(req,res,"Stock Tally", "Stock Tally end");
          return am.findForward("loadRtn");
         }
     }
    public ActionForward Return(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        StockTallyForm udf = (StockTallyForm)af;
        util.updAccessLog(req,res,"Stock Tally", "Stock Tally Return");
            HashMap dbinfo = info.getDmbsInfoLst();
            String BOX_RFID = (String)dbinfo.get("BOX_RFID");
        String boxrfidval = util.nvl((String)udf.getValue("boxrfidval"));    
        String rtnassign = util.nvl((String)udf.getValue("rtnassign"));  
        int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
        ArrayList ary = null;
        int ct = 0;
        ResultSet rs = null;
        String insertGt="";
        int upCnt=0;
        String updateStk_tly="";
        String vnm = util.nvl((String)udf.getValue("vnm"));
        String rtnseq = util.nvl((String)udf.getValue("rtnseq"));
        db.execUpd("delete", "delete from gt_srch_rslt", new ArrayList());
        db.execUpd("delete", "delete from gt_file_trf", new ArrayList());
        ArrayList dummyLst=new ArrayList();
        if(!vnm.equals("") && !rtnseq.equals("")){
            vnm = util.getVnm(vnm);
            ArrayList vnmList = new ArrayList();
            vnm = vnm.substring(1, vnm.length()-1);
            String[] vnmStr = vnm.split("','");
            for(int i=0;i<vnmStr.length;i++){
                vnm = vnmStr[i];
               vnm = vnm.replaceAll(",", "");
               vnm = vnm.replaceAll("'", "");
               vnm = vnm.trim();
              
             vnmList.add(vnm);
            }
            for(int k=0 ;k<vnmList.size();k++){
                insertGt = "insert into gt_file_trf(val ,flg) select ? , ? from dual  ";
                String fromvnmLst=util.nvl((String)vnmList.get(k));
                if(!dummyLst.contains(fromvnmLst)){
                dummyLst.add(fromvnmLst);
                
                ary = new ArrayList();
                ary.add(fromvnmLst);
                ary.add("RT");
                ct = db.execDirUpd("insert gt_file_trf", insertGt, ary);
                }
            }
            if(boxrfidval.equals("")){
                String sql = " select stk_tly_rtn_seq.NextVal seqVal from dual ";
                rs = db.execSql(" get seq val ", sql, new ArrayList());
                if(rs.next())
                boxrfidval = rs.getString(1);
                rs.close();
            }
            String updGt="update gt_file_trf a set (a.stk_idn,a.mprp)=(select b.idn,decode(a.val,b.vnm,'VNM','RFID')\n" + 
            "from mstk b where 1 = 1 and (a.val=b.vnm or a.val=b.tfl3) and rownum <2)";  
            ct = db.execDirUpd("upd gt_file_trf", updGt, new ArrayList());
            
            updGt="update gt_file_trf a set flg='NF' where stk_idn is null";  
            ct = db.execDirUpd("upd gt_file_trf", updGt, new ArrayList()); 
            
            
            ary = new ArrayList();
            ary.add(rtnseq);
            updGt="update gt_file_trf a set flg='EX'\n" + 
            "where not exists (select 1 from stk_tly b where b.stk_idn = a.stk_idn and stt in ('IS','RT') and b.seq_idn=? and trunc(nvl(b.iss_dte, sysdate)) = trunc(sysdate) )   and a.flg not in('NF') ";  
            ct = db.execDirUpd("upd gt_file_trf", updGt, ary);
            
            ary = new ArrayList();
            ary.add(rtnseq);
            updGt="update gt_file_trf a set flg='AR'\n" + 
            "where exists (select 1 from stk_tly b where b.stk_idn = a.stk_idn and stt in ('RT') and b.seq_idn=? and trunc(nvl(b.iss_dte, sysdate)) = trunc(sysdate) )   and a.flg not in('NF','EX') ";  
            ct = db.execDirUpd("upd gt_file_trf", updGt, ary);
            
//            ary = new ArrayList();
//            ary.add(rtnseq);
//            updateStk_tly="delete stk_tly_nf nf where   nf.seq_idn=? and trunc(nvl(nf.rtn_dte, sysdate)) = trunc(sysdate) \n" + 
//            "and exists (select 1 from gt_file_trf a where nf.ref_idn=a.val)";
//            upCnt = db.execUpd("upda", updateStk_tly, ary);
            
            ary = new ArrayList();
            ary.add(rtnseq);
            ary.add(boxrfidval);
            
            insertGt="insert into stk_tly_nf(seq_idn,box_id , ref_idn ,ref_typ, flg,rtn_usr)\n" + 
            "select ?,?, val ,mprp, flg,pack_var.get_usr from gt_file_trf ";  
            ct = db.execDirUpd("insert gt_file_trf", insertGt, ary);
                       
            String insgtPkt = "Insert into gt_srch_rslt (stk_idn, vnm ,qty,cts,stt, flg,sk1,rmk,cert_lab,pkt_ty ) " + 
                  "select b.idn, b.vnm ,b.qty,b.cts,b.stt, 'RT',b.sk1,b.tfl3,b.cert_lab,b.pkt_ty  " +
                  " from mstk b, gt_file_trf a where 1 = 1 and b.idn=a.stk_idn and a.flg in('RT')" ;
               
            ct = db.execUpd(" ins gt", insgtPkt, new ArrayList());
            
            ary = new ArrayList();
            ary.add(rtnseq);
            updateStk_tly = "update stk_tly a set a.rtn_dte=sysdate  , a.stt='RT',rtn_usr=pack_var.get_usr "+
                               " where exists (select 1 from gt_srch_rslt b where a.stk_idn=b.stk_idn and b.flg='RT') and trunc(nvl(a.iss_dte, sysdate)) = trunc(sysdate) and a.stt='IS' and a.seq_idn=?   ";
             upCnt = db.execUpd("upda", updateStk_tly, ary);
            
//        String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
//        ary = new ArrayList();
//        ary.add("RFID_VW");
//        ct = db.execCall(" Srch Prp ", pktPrp, ary);
      
            HashMap flgMap=getstktlynf(req,"");
                        String sqlQ="select sum(c.cts) cts,box_id from \n" + 
                        "stk_tly a,stk_tly_nf b,mstk c\n" + 
                        "where \n" + 
                        "a.stk_idn=c.idn and decode(b.ref_typ,'VNM',c.vnm,c.tfl3)=b.ref_idn and\n" + 
                        "a.seq_idn=b.seq_idn and b.box_id=? group by box_id";
                        ary = new ArrayList();
                        ary.add(boxrfidval);
                        rs = db.execSql("sqlByr", sqlQ, ary);
                        while (rs.next()) {
                            String box_id = util.nvl(rs.getString("box_id"));
                            flgMap.put(box_id+"_CRTWT", util.nvl(rs.getString("cts")));   
                        }
                        rs.close();
            req.setAttribute("sttMap", flgMap);
            SearchResult(req,res,"RT");
        if(!rtnassign.equals("")){
            ct = db.execCall("delete gt", "delete from gt_file_trf", new ArrayList());
            insertGt = "insert into gt_file_trf(stk_idn , lab , mprp , val ,flg) select stk_idn , cert_lab , ? , ? , ? from gt_srch_rslt where pkt_ty='NR' and flg <> 'EX' ";
            insertGt+=" and not exists(select 1 from gt_file_trf b where gt_srch_rslt.stk_idn=b.stk_idn)";
            ary = new ArrayList();
            ary.add(BOX_RFID);
            ary.add(boxrfidval);
            ary.add("N");
            ct = db.execDirUpd("insert gt_file_trf", insertGt, ary);
            if(ct>0){
            ArrayList out = new ArrayList();
            out.add("I");
            out.add("V");

            CallableStatement ct1 = db.execCall("update pkt", "PRP_PKG.BLK_UPD(pCnt => ? , pMsg=> ?)", new ArrayList(), out);
            int count = ct1.getInt(1);
            String msg = ct1.getString(2);
            req.setAttribute("msg","Number of Packet Allocation Done : "+count);
            req.setAttribute("msg1",msg);
            }
        }
        }
        TallyStock(req,"");
        udf.reset();
        return am.findForward("loadRtn");
        }
    }
    
    public void TallyStock(HttpServletRequest req , String seq){
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
        ArrayList seqLst = new ArrayList();
        String str = "";
        String conQ="";
        ArrayList ary = new ArrayList();
        String stktallylinkWise=info.getStktallylinkWise();
        if(!seq.equals("") && !seq.equals("0")){
            str = " and b.seq_idn = ? ";
            ary.add(seq);
        }
        if(!stktallylinkWise.equals("")){
            str += " and b.flg3 = ? ";
            ary.add(stktallylinkWise);
        }
        
        if(!seq.equals("") && !seq.equals("0")){
            ary.add(seq);
        }
        if(!stktallylinkWise.equals("")){
            ary.add(stktallylinkWise);
        }
       String ctstrunc="trunc(sum(trunc(a.cts,2)),2)";
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("STOCK_TALLY");
        ArrayList pageList=new ArrayList();
        HashMap pageDtlMap=new HashMap();
        pageList= ((ArrayList)pageDtl.get("CTS_TRUNC") == null)?new ArrayList():(ArrayList)pageDtl.get("CTS_TRUNC"); 
         if(pageList!=null && pageList.size() >0){
           for(int j=0;j<pageList.size();j++){
             pageDtlMap=(HashMap)pageList.get(j);
             ctstrunc=(String)pageDtlMap.get("dflt_val");
           }
         }
       String sqlTally = " select sum(a.qty) qty, "+ctstrunc+" cts, b.stt, b.stk_stt "+
                         " from mstk a, stk_tly b where a.idn = b.stk_idn and trunc(iss_dte) = trunc(sysdate) "+str+
                         " group by b.stt, b.stk_stt \n"+
                         "union\n" + 
                         "select sum(a.qty) qty, "+ctstrunc+" cts, b.stt, 'TOTAL'  \n" + 
                         "from mstk a, stk_tly b where a.idn = b.stk_idn and trunc(iss_dte) = trunc(sysdate)  \n"+str+
                         "group by b.stt";
        HashMap pktDtl = new HashMap();
        ArrayList grpList = new ArrayList();
      ArrayList  outLst = db.execSqlLst("sql", sqlTally, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet  rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                String qty = util.nvl(rs.getString("qty"));
                String cts = util.nvl(rs.getString("cts"));
                String grp1 = util.nvl(rs.getString("stk_stt"));
                String tlyStt = util.nvl(rs.getString("stt"));
                pktDtl.put(grp1 + "_" + tlyStt + "_Q", qty);
                pktDtl.put(grp1 + "_" + tlyStt + "_C", cts);
                if (!grpList.contains(grp1) && !grp1.equals("TOTAL"))
                    grpList.add(grp1);
            }
        
            rs.close();
            pst.close();
        if(grpList.size()>0){
            ary = new ArrayList();
            if(!stktallylinkWise.equals("")){
                conQ = " and flg3 = ? ";
                ary.add(stktallylinkWise);
            }
            String tallySeq = " select distinct seq_idn from stk_tly where trunc(iss_dte) = trunc(sysdate) "+conQ+" order by seq_idn desc";
          outLst = db.execSqlLst("tallySeq", tallySeq, ary);
         pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                seqLst.add(rs.getString("seq_idn"));
            }
            
            rs.close();
            pst.close();
        }
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        session.setAttribute("grpList",grpList);
        session.setAttribute("pktDtl", pktDtl);
        session.setAttribute("seqLst", seqLst);
    }
    
    public ActionForward TallyCRT(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        StockTallyForm udf = (StockTallyForm)af;
        util.updAccessLog(req,res,"Stock Tally", "Stock Tally TallyCRT Start");
        HashMap pktDtl = new HashMap();
        ArrayList crtList = new ArrayList();
        String loadSeq = util.nvl(req.getParameter("loadSeq"));
        String stt = util.nvl(req.getParameter("stt"));
        String prp = util.nvl(req.getParameter("prp"));
        String str ="";
        ArrayList ary = new ArrayList();
        ary.add(stt);
        if(!loadSeq.equals("")  && !loadSeq.equals("0")){
            str = " and b.seq_idn = ? ";
            ary.add(loadSeq);
        }
            String ctstrunc="trunc(sum(trunc(a.cts,2)),2)";
             HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
             HashMap pageDtl=(HashMap)allPageDtl.get("STOCK_TALLY");
             ArrayList pageList=new ArrayList();
             HashMap pageDtlMap=new HashMap();
             pageList= ((ArrayList)pageDtl.get("CTS_TRUNC") == null)?new ArrayList():(ArrayList)pageDtl.get("CTS_TRUNC"); 
              if(pageList!=null && pageList.size() >0){
                for(int j=0;j<pageList.size();j++){
                  pageDtlMap=(HashMap)pageList.get(j);
                  ctstrunc=(String)pageDtlMap.get("dflt_val");
                }
              }
        String tallyCrt = " select sum(a.qty) qty, "+ctstrunc+" cts, b.flg1 stt , c.dsc , b.stt tlyStt  from mstk a, stk_tly b , df_stk_stt c "+
                          " where a.idn = b.stk_idn and trunc(iss_dte) = trunc(sysdate) and b.stk_stt=? and b.flg1 = c.stt "+str+
                         " group by b.flg1 , c.dsc , b.stt ";
            if(!prp.equals("")){
                tallyCrt = " select sum(a.qty) qty, "+ctstrunc+" cts, b.stk_stt stt , '' dsc , b.stt tlyStt , d.val prpVal,d.prt1 prpPrt,d.srt  from mstk a, stk_tly b , df_stk_stt c , stk_dtl d "+
                          " where a.idn = b.stk_idn and trunc(iss_dte) = trunc(sysdate) and b.stk_stt=? and b.flg1 = c.stt "+str+
                           " and a.idn = d.mstk_idn and d.mprp='Stock_Tally' and d.grp=1 "+
                          " group by b.stk_stt , b.stt , d.val ,d.prt1,d.srt order by d.srt";
                              
            }
        
          ArrayList  outLst = db.execSqlLst("tallyCrt", tallyCrt, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
        while(rs.next()){
               String cnt = util.nvl(rs.getString("qty"));
               String dsc = util.nvl(rs.getString("dsc"));
               String cts =  util.nvl(rs.getString("cts"));
               String stkStt = util.nvl(rs.getString("Stt"));
               String tlyStt = util.nvl(rs.getString("tlyStt"));
            if(!prp.equals("")){
                 stkStt = util.nvl(rs.getString("prpVal"));
                dsc = util.nvl(rs.getString("prpPrt"));
            }
               pktDtl.put(stkStt+"_"+tlyStt+"_Q" , cnt);
               pktDtl.put(stkStt+"_"+tlyStt+"_C" , cts);
               pktDtl.put(stkStt , dsc);
               pktDtl.put(stkStt+"_STT" , util.nvl(rs.getString("Stt")));
               if(!crtList.contains(stkStt))
                   crtList.add(stkStt);
         }
        rs.close();
            pst.close();
            req.setAttribute("crtList",crtList);
            req.setAttribute("pktDtl", pktDtl);
            req.setAttribute("seqNo", loadSeq);
        
        util.updAccessLog(req,res,"Stock Tally", "Stock Tally TallyCRT end");
        return am.findForward("TallyCRT");
        }
    }
        public ActionForward loadTallyhistory(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
         StockTallyForm udf = (StockTallyForm)af;
         udf.reset();
         util.updAccessLog(req,res,"Stock Tally", "Stock Tally loadTallyhistory start");
         TallyStockstatus(req);
            session.setAttribute("grpList",new ArrayList());
            session.setAttribute("pktDtl", new HashMap());
            session.setAttribute("seqLst", new ArrayList());
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("STOCK_TALLY");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("STOCK_TALLY");
            allPageDtl.put("STOCK_TALLY",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            util.updAccessLog(req,res,"Stock Tally", "Stock Tally loadTallyhistory end");
       return am.findForward("history");
        }
    }
    public ActionForward history(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        StockTallyForm udf = (StockTallyForm)af;
        String mstkStt = "";
            util.updAccessLog(req,res,"Stock Tally", "Stock Tally history start");
         String[] stt =  udf.getSttValLst();
        String issdte = util.nvl((String)udf.getValue("issdte"));
        String rtndte = util.nvl((String)udf.getValue("rtndte"));
        String seq = util.nvl((String)udf.getValue("seq"));
        String conQ="";
        udf.reset();
        
        ArrayList seqLst = new ArrayList();
        String str = "";
        ArrayList ary = new ArrayList();
        if(!seq.equals("") && !seq.equals("0")){
        str = " and b.seq_idn = ? ";
        ary.add(seq);
        req.setAttribute("seqNo", seq);
        }
        if(!issdte.equals("")){
            conQ = conQ+" and trunc(b.iss_dte) between to_date('"+issdte+"' , 'dd-mm-yyyy') and to_date('"+issdte+"' , 'dd-mm-yyyy') ";
        }
        if(!rtndte.equals("")){
            conQ = conQ+" and trunc(b.rtn_dte) between to_date('"+rtndte+"' , 'dd-mm-yyyy') and to_date('"+rtndte+"' , 'dd-mm-yyyy') ";
        }
            if(stt!=null){
                for(int i=0;i<stt.length;i++){
                    mstkStt=mstkStt+","+stt[i];
                }
                mstkStt=mstkStt.replaceFirst(",", "");
                if(!mstkStt.equals("")){
                mstkStt = util.getVnm(mstkStt);
                conQ=conQ+" and stk_stt in("+mstkStt+") ";
                }
            }
               String sqlTally = " select sum(a.qty) qty, trunc(sum(trunc(a.cts,2)),2) cts, b.stt, b.stk_stt "+
                                 " from mstk a, stk_tly b where a.idn = b.stk_idn "+conQ+str+
                                 " group by b.stt, b.stk_stt ";
                HashMap pktDtl = new HashMap();
                ArrayList grpList = new ArrayList();
          ArrayList  outLst = db.execSqlLst("sql", sqlTally, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
                try {
                    while (rs.next()) {
                        String qty = util.nvl(rs.getString("qty"));
                        String cts = util.nvl(rs.getString("cts"));
                        String grp1 = util.nvl(rs.getString("stk_stt"));
                        String tlyStt = util.nvl(rs.getString("stt"));
                        pktDtl.put(grp1 + "_" + tlyStt + "_Q", qty);
                        pktDtl.put(grp1 + "_" + tlyStt + "_C", cts);
                        if (!grpList.contains(grp1))
                            grpList.add(grp1);
                    }
                
                    rs.close();
                    pst.close();
                if(grpList.size()>0){
                    ary = new ArrayList();
                    String tallySeq = " select distinct seq_idn from stk_tly b where 1=1 "+conQ+" order by seq_idn desc";
                  outLst = db.execSqlLst("tallySeq", tallySeq, ary);
                  pst = (PreparedStatement)outLst.get(0);
                  rs = (ResultSet)outLst.get(1);
                    while(rs.next()){
                        seqLst.add(rs.getString("seq_idn"));
                    }
                    
                    rs.close();
                    pst.close();
                }
                } catch (SQLException sqle) {
                    // TODO: Add catch code
                    sqle.printStackTrace();
                }
                session.setAttribute("grpList",grpList);
                session.setAttribute("pktDtl", pktDtl);
                session.setAttribute("seqLst", seqLst);
        req.setAttribute("view", "Y");
        req.setAttribute("issdte",issdte);
        req.setAttribute("rtndte",rtndte);
        udf.setValue("issdte",issdte);
        udf.setValue("rtndte",rtndte);
        udf.setValue("seq",seq);
            util.updAccessLog(req,res,"Stock Tally", "Stock Tally history end");
        return am.findForward("history");
        }
    }
    public ActionForward TallyCRTHistory(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        StockTallyForm udf = (StockTallyForm)af;
            util.updAccessLog(req,res,"Stock Tally", "Stock Tally TallyCRTHistory start");
        HashMap pktDtl = new HashMap();
        ArrayList crtList = new ArrayList();
        String loadSeq = util.nvl(req.getParameter("loadSeq"));
        String stt = util.nvl(req.getParameter("stt"));
        String issdte = util.nvl(req.getParameter("issdte"));
        String rtndte = util.nvl(req.getParameter("rtndte"));
        String conQ="";
        String str ="";
        ArrayList ary = new ArrayList();
        ary.add(stt);
        if(!loadSeq.equals("")  && !loadSeq.equals("0")){
            str = " and b.seq_idn = ? ";
            ary.add(loadSeq);
        }
        if(!issdte.equals("")){
            conQ = conQ+" and trunc(b.iss_dte) between to_date('"+issdte+"' , 'dd-mm-yyyy') and to_date('"+issdte+"' , 'dd-mm-yyyy') ";
        }
        if(!rtndte.equals("")){
            conQ = conQ+" and trunc(b.rtn_dte) between to_date('"+rtndte+"' , 'dd-mm-yyyy') and to_date('"+rtndte+"' , 'dd-mm-yyyy') ";
        }
        String tallyCrt = " select sum(a.qty) qty, trunc(sum(trunc(a.cts,2)),2) cts, b.flg1 stt , c.dsc , b.stt tlyStt  from mstk a, stk_tly b , df_stk_stt c "+
                          " where a.idn = b.stk_idn and b.stk_stt=? and b.flg1 = c.stt "+str+conQ+
                         " group by b.flg1 , c.dsc , b.stt ";
        
          ArrayList  outLst = db.execSqlLst("tallyCrt", tallyCrt, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
        while(rs.next()){
               String cnt = util.nvl(rs.getString("qty"));
               String dsc = util.nvl(rs.getString("dsc"));
               String cts =  util.nvl(rs.getString("cts"));
               String stkStt = util.nvl(rs.getString("Stt"));
               String tlyStt = util.nvl(rs.getString("tlyStt"));
               pktDtl.put(stkStt+"_"+tlyStt+"_Q" , cnt);
               pktDtl.put(stkStt+"_"+tlyStt+"_C" , cts);
               pktDtl.put(stkStt , dsc);
               if(!crtList.contains(stkStt))
                   crtList.add(stkStt);
         }
        rs.close();
        pst.close();
            req.setAttribute("crtList",crtList);
            req.setAttribute("pktDtl", pktDtl);
        req.setAttribute("issdte",issdte);
        req.setAttribute("rtndte",rtndte);
        req.setAttribute("seqNo",loadSeq);
            util.updAccessLog(req,res,"Stock Tally", "Stock Tally TallyCRTHistory end");
        return am.findForward("TallyCRTHistory");
        }
    }
    public ActionForward dayWisehistory(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        StockTallyForm udf = (StockTallyForm)af;
            util.updAccessLog(req,res,"Stock Tally", "Stock Tally dayWisehistory start");
        String reqstt = util.nvl(req.getParameter("reqstt"));
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("STOCK_TALLY");
        HashMap dbinfo = info.getDmbsInfoLst();
        String locval = (String)dbinfo.get("LOC");
        String hisdays=util.nvl((String)((HashMap)((ArrayList)pageDtl.get("HISDAYS")).get(0)).get("dflt_val"),"7");
        String conQ=" and a.idn=b1.mstk_idn and b1.grp=1 and b1.mprp='"+locval+"' and trunc(b.iss_dte) between trunc(sysdate) -"+hisdays+" and trunc(sysdate) ";
        ArrayList ary = new ArrayList();
        HashMap pktDtl = new HashMap();
        ArrayList dteList = new ArrayList();
        ArrayList grpList = new ArrayList();
        ArrayList reqsttList = new ArrayList();
        ArrayList locList = new ArrayList();
        reqsttList.add("IS");reqsttList.add("RT");
        if(reqstt.equals("RT")){
                reqsttList = new ArrayList();
                reqsttList.add("RT");
        }
               String sqlTally = " select sum(a.qty) qty, trunc(sum(trunc(a.cts,2)),2) cts, b.stt, b.stk_stt,to_char(b.iss_dte , 'dd-mm-rrrr') dte,b1.val loc\n" + 
               "from mstk a, stk_tly b,stk_dtl b1 \n" + 
               "where a.idn = b.stk_idn \n" + conQ+
               "group by b.stt, b.stk_stt , to_char(b.iss_dte , 'dd-mm-rrrr'),b1.val order by 5 ";
          ArrayList  outLst = db.execSqlLst("sql", sqlTally, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
                try {
                    while (rs.next()) {
                        String loc = util.nvl(rs.getString("loc"));
                        String qty = util.nvl(rs.getString("qty"));
                        String cts = util.nvl(rs.getString("cts"));
                        String grp = util.nvl(rs.getString("stk_stt"));
                        String tlyStt = util.nvl(rs.getString("stt"));
                        String dte = util.nvl(rs.getString("dte"));
                        pktDtl.put(loc+ "_" +dte+ "_" +grp + "_" + tlyStt + "_Q", qty);
                        pktDtl.put(loc+ "_" +dte+ "_" +grp + "_" + tlyStt + "_C", cts);
                        if (!dteList.contains(dte))
                            dteList.add(dte);
                        if (!locList.contains(loc))
                            locList.add(loc);
                    }
                    rs.close();
                    pst.close();
                    sqlTally = " select sum(a.qty) qty, trunc(sum(trunc(a.cts,2)),2) cts, b.stt,to_char(b.iss_dte , 'dd-mm-rrrr') dte,b1.val loc\n" + 
                    "from mstk a, stk_tly b,stk_dtl b1 \n" + 
                    "where a.idn = b.stk_idn \n" + conQ+
                    "group by b.stt,to_char(b.iss_dte , 'dd-mm-rrrr'),b1.val\n" + 
                    "order by 4 ";
                  outLst = db.execSqlLst("sql", sqlTally, ary);
                  pst = (PreparedStatement)outLst.get(0);
                  rs = (ResultSet)outLst.get(1);
                    while (rs.next()) {
                        String loc = util.nvl(rs.getString("loc"));
                        String qty = util.nvl(rs.getString("qty"));
                        String cts = util.nvl(rs.getString("cts"));
                        String tlyStt = util.nvl(rs.getString("stt"));
                        String dte = util.nvl(rs.getString("dte"));
                        pktDtl.put(loc+ "_" +dte+ "_" + tlyStt + "_Q", qty);
                        pktDtl.put(loc+ "_" +dte+ "_" + tlyStt + "_C", cts);
                    }
                    rs.close();
                    pst.close();
                    sqlTally = " select sum(a.qty) qty, trunc(sum(trunc(a.cts,2)),2) cts, b.stt, b.stk_stt,c.srt_fr,b1.val loc\n" + 
                    "from mstk a, stk_tly b,stk_dtl b1,rule_dtl c,mrule d \n" + 
                    "where a.idn = b.stk_idn \n" + conQ+
                    "and c.rule_idn = d.rule_idn and d.mdl = 'JFLEX' and d.nme_rule = 'GROUP_DTL' and b.stk_stt=dsc\n" + 
                    "group by b.stt, b.stk_stt,c.srt_fr,b1.val\n" + 
                    "union\n" + 
                    "select sum(a.qty) qty, trunc(sum(trunc(a.cts,2)),2) cts, b.stt, 'TTL' stk_stt,10000 srt_fr,b1.val loc\n" + 
                    "from mstk a, stk_tly b,stk_dtl b1 \n" + 
                    "where a.idn = b.stk_idn \n" + conQ+
                    "group by b.stt,b1.val\n" + 
                    "order by 5";
                  outLst = db.execSqlLst("sql", sqlTally, ary);
                  pst = (PreparedStatement)outLst.get(0);
                  rs = (ResultSet)outLst.get(1);
                    while (rs.next()) {
                        String loc = util.nvl(rs.getString("loc"));
                        String qty = util.nvl(rs.getString("qty"));
                        String cts = util.nvl(rs.getString("cts"));
                        String grp = util.nvl(rs.getString("stk_stt"));
                        String tlyStt = util.nvl(rs.getString("stt"));
                        pktDtl.put(loc+ "_" +grp + "_" + tlyStt + "_Q", qty);
                        pktDtl.put(loc+ "_" +grp + "_" + tlyStt + "_C", cts);
                        if (!grpList.contains(grp))
                            grpList.add(grp);
                    }
                    rs.close();
                    pst.close();
                } catch (SQLException sqle) {
                    // TODO: Add catch code
                    sqle.printStackTrace();
                }
                req.setAttribute("dteList",dteList);
                req.setAttribute("pktDtl", pktDtl);
                req.setAttribute("grpList", grpList);
                req.setAttribute("reqsttList", reqsttList);
                req.setAttribute("locList", locList);
            util.updAccessLog(req,res,"Stock Tally", "Stock Tally dayWisehistory end");
        return am.findForward("dayWisehistory");
        }
    }
    
    public ActionForward PktDtlHistory(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Stock Tally", "Stock Tally PktDtlHistory start");
        String dfStt = util.nvl(req.getParameter("dfstt"));
        String mstkStt = util.nvl(req.getParameter("mstkstt"));
        String issTyp = util.nvl(req.getParameter("issTyp"));
        String loadSeq = util.nvl(req.getParameter("loadSeq"));
        String issdte = util.nvl(req.getParameter("issdte"));
        String rtndte = util.nvl(req.getParameter("rtndte"));
        int ct = db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());
        ArrayList ary = new ArrayList();
        String pktDtl =" Insert into gt_srch_rslt (stk_idn, vnm ,cts,stt, flg,sk1,rmk ) " + 
                       " select distinct a.stk_idn , a.vnm ,b.cts,b.stt, 'Z',b.sk1,b.tfl3 from stk_tly a,mstk b  " + 
                       " where 1=1 and a.stk_idn=b.idn ";
        if(!dfStt.equals("")){
            pktDtl = pktDtl+" and a.stk_stt= ?" ;
            ary.add(dfStt);
        }
        if(!mstkStt.equals("")){
            pktDtl = pktDtl+" and a.flg1 = ?" ;
            ary.add(mstkStt);
        }
        if(!issTyp.equals("ALL")){
            pktDtl = pktDtl+" and a.stt = ?" ;
            ary.add(issTyp);
        }
        if(!loadSeq.equals("")  && !loadSeq.equals("0")){
            pktDtl = pktDtl+" and a.seq_idn = ?" ;
            ary.add(loadSeq);
        }
        if(!issdte.equals("")){
            pktDtl = pktDtl+" and trunc(a.iss_dte) between to_date('"+issdte+"' , 'dd-mm-yyyy') and to_date('"+issdte+"' , 'dd-mm-yyyy') ";
        }
        if(!rtndte.equals("")){
            pktDtl = pktDtl+" and trunc(a.rtn_dte) between to_date('"+rtndte+"' , 'dd-mm-yyyy') and to_date('"+rtndte+"' , 'dd-mm-yyyy') ";
        }
         ct = db.execUpd("pktDtl", pktDtl, ary);
        
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        ary = new ArrayList();
        ary.add("RFID_VW");
         ct = db.execCall(" Srch Prp ", pktPrp, ary);
            util.updAccessLog(req,res,"Stock Tally", "Stock Tally PktDtlHistory end");
     return SearchResult(am, af, req, res);
        }
    }
    public void TallyStockstatus(HttpServletRequest req)throws Exception{
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
        ArrayList ary=new ArrayList();
        HashMap grp3List = (session.getAttribute("grp3List") == null)?new HashMap():(HashMap)session.getAttribute("grp3List");
        String stktallylinkWise=util.nvl((req.getParameter("stktallylinkWise")));
        
        if(grp3List.size() == 0 || !stktallylinkWise.equals("")) {
        String stkStt =  " select  distinct a.dsc  , a.chr_fr , a.srt_fr  from rule_dtl a, mrule b , df_stk_stt c where a.rule_idn = b.rule_idn and "+ 
                          " c.grp3 = a.dsc and b.mdl = 'JFLEX' and b.nme_rule = 'GROUP_DTL' and a.dsc <> 'DLV' and a.til_dte is null  ";
        
        ary=new ArrayList();
        if(!stktallylinkWise.equals("")){
            stkStt+=" and c.mdl=?";
            ary.add(stktallylinkWise);
        }else{
            info.setStktallylinkWise("");
        }
            stkStt+=" order by a.srt_fr , a.dsc , a.chr_fr";
          ArrayList  outLst = db.execSqlLst("stkStt", stkStt, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
        ArrayList sttList = new ArrayList();
        while(rs.next()){
            ArrayList sttDtl = new ArrayList();
            sttDtl.add(rs.getString("dsc"));
            sttDtl.add(rs.getString("chr_fr"));
            sttList.add(sttDtl);
            grp3List.put(rs.getString("dsc"), rs.getString("chr_fr"));
        }
        rs.close();
        pst.close();
        session.setAttribute("grp3List", grp3List);
        session.setAttribute("sttLst",sttList);
        info.setStktallylinkWise(stktallylinkWise);
        }
    }
    public ActionForward PktDtl(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Stock Tally", "Stock Tally PktDtl start");
        String dfStt = util.nvl(req.getParameter("dfstt"));
        String mstkStt = util.nvl(req.getParameter("mstkstt"));
        String issTyp = util.nvl(req.getParameter("issTyp"));
        String loadSeq = util.nvl(req.getParameter("loadSeq"));
        String prp=util.nvl(req.getParameter("prp"));
        int ct = db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());
        ArrayList ary = new ArrayList();
        String pktDtl =" Insert into gt_srch_rslt (stk_idn, vnm ,cts,stt,dsp_stt, flg,sk1,rmk ) " + 
                       " select distinct a.stk_idn , a.vnm ,b.cts,b.stt,a.flg1, 'Z',b.sk1,b.tfl3 from stk_tly a,mstk b " + 
                       " where  a.stk_idn=b.idn and trunc(a.iss_dte) = trunc(sysdate) ";
            
      if(!prp.equals("")){
            pktDtl =" Insert into gt_srch_rslt (stk_idn, vnm ,cts,stt,dsp_stt, flg,sk1,rmk ) " + 
                     " select distinct a.stk_idn , a.vnm ,b.cts,b.stt,a.flg1, 'Z',b.sk1,b.tfl3 from stk_tly a,mstk b,stk_dtl c " + 
                     " where  a.stk_idn=b.idn and trunc(a.iss_dte) = trunc(sysdate) and b.idn=c.mstk_idn and c.mprp='Stock_Tally' and c.grp=1 ";
                     dfStt=mstkStt;
                     mstkStt="";
            }
        if(!dfStt.equals("")){
            pktDtl = pktDtl+" and a.stk_stt= ?" ;
            ary.add(dfStt);
        }
        if(!mstkStt.equals("")){
            pktDtl = pktDtl+" and a.flg1 = ?" ;
            ary.add(mstkStt);
        }
        if(!prp.equals("")){
                pktDtl = pktDtl+" and c.val = ?" ;
                ary.add(prp);
        }
        if(!issTyp.equals("ALL")){
            pktDtl = pktDtl+" and a.stt = ?" ;
            ary.add(issTyp);
        }
        if(!loadSeq.equals("")  && !loadSeq.equals("0")){
            pktDtl = pktDtl+" and a.seq_idn = ?" ;
            ary.add(loadSeq);
        }
         ct = db.execUpd("pktDtl", pktDtl, ary);
        
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        ary = new ArrayList();
        ary.add("RFID_VW");
         ct = db.execCall(" Srch Prp ", pktPrp, ary);
            util.updAccessLog(req,res,"Stock Tally", "Stock Tally PktDtl end");
     return SearchResult(am, af, req, res);
        }
    }
    
    public ActionForward SearchResult(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      GenericInterface genericInt = new GenericImpl();
      String stt = util.nvl(req.getParameter("stt"));
      String issTyp = util.nvl(req.getParameter("issTyp"));
      String gtFlg = "'EX','RT'";
    
      if(stt.equals("EX")) 
          gtFlg = "'EX'";
          
       if(stt.equals("RT")) 
           gtFlg = "'RT'";
       if(stt.equals("AR")) 
           gtFlg = "'AR'";
       if(stt.equals("NF")) 
       gtFlg = "'NF'";
       if(!issTyp.equals(""))
           gtFlg="'Z'";
      
      ArrayList pktList = new ArrayList();
      ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "rfidPrpLst", "RFID_VW");
      String  srchQ =  " select stk_idn ,  vnm ,dsp_stt, stt,to_char(cts,'99999999990.99') cts,sk1,rmk tfl3,decode(stt, 'MKAP',' color:Olive', 'MKPP','color:Green', 'MKWH','color:Red', 'MKLB','color:Maroon', 'MKAV', 'color:Blue','MKOS_IS', 'color:Red','MKKS_IS', 'color:Red', 'MKIS', 'color:Red', 'MKEI', 'color:Red', '') class ";

      

      for (int i = 0; i < vwPrpLst.size(); i++) {
          String fld = "prp_";
          int j = i + 1;
          if (j < 10)
              fld += "00" + j;
          else if (j < 100)
              fld += "0" + j;
          else if (j > 100)
              fld += j;

         

          srchQ += ", " + fld;
         
       }

      
      String rsltQ = srchQ + " from gt_srch_rslt where flg in ("+gtFlg+") order by sk1,stk_idn,cts";
     
            ArrayList  outLst = db.execSqlLst("search Result", rsltQ, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet  rs = (ResultSet)outLst.get(1);
      try {
          while(rs.next()) {
            
              String stkIdn = util.nvl(rs.getString("stk_idn"));
            
              HashMap pktPrpMap = new HashMap();
              pktPrpMap.put("currentstt", util.nvl(rs.getString("stt")));
              pktPrpMap.put("iss_stt", util.nvl(rs.getString("dsp_stt")));
              pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
              String vnm = util.nvl(rs.getString("vnm"));
              pktPrpMap.put("vnm",vnm);
              pktPrpMap.put("stk_idn",stkIdn);
              pktPrpMap.put("class",util.nvl(rs.getString("class")));
              for(int j=0; j < vwPrpLst.size(); j++){
                   String prp = (String)vwPrpLst.get(j);
                    
                    String fld="prp_";
                    if(j < 9)
                            fld="prp_00"+(j+1);
                    else    
                            fld="prp_0"+(j+1);
                    
                    String val = util.nvl(rs.getString(fld)) ;
                    if(prp.equals("CRTWT"))
                    val = util.nvl(rs.getString("cts"));
                    if (prp.toUpperCase().equals("RFIDCD"))
                    val = util.nvl(rs.getString("tfl3")); 
                      pktPrpMap.put(prp, val);
                       }
                            
                  pktList.add(pktPrpMap);
              }
          rs.close();
          pst.close();
      } catch (SQLException sqle) {

          // TODO: Add catch code
          sqle.printStackTrace();
      }

      req.setAttribute("stockList", pktList);
          return am.findForward("loadRS");
          }
      }
    
    public void SearchResult(HttpServletRequest req, HttpServletResponse res,String stt)
            throws Exception {
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
      ArrayList pktList = new ArrayList();
        GenericInterface genericInt = new GenericImpl();
        ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, "rfidPrpLst", "RFID_VW");
      ArrayList itemHdr = new ArrayList();
      ArrayList prpDspBlocked = info.getPageblockList();
      itemHdr.add("vnm");
      itemHdr.add("stt");
      itemHdr.add("flg");
      String  srchQ =  " select stk_idn ,  vnm , stt,flg,rmk tfl3,to_char(cts,'99999999990.99') cts,sk1  ";
      
      for (int i = 0; i < vwPrpLst.size(); i++) {
          String fld = "prp_";
          int j = i + 1;
          if (j < 10)
              fld += "00" + j;
          else if (j < 100)
              fld += "0" + j;
          else if (j > 100)
              fld += j;

         

          srchQ += ", " + fld;
         
       }

      
      String rsltQ = srchQ + " from gt_srch_rslt where flg in ('"+stt+"') order by sk1,stk_idn,cts ";
     
      ArrayList  outLst = db.execSqlLst("search Result", rsltQ, new ArrayList());
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet  rs = (ResultSet)outLst.get(1);
      try {
          while(rs.next()) {
            
              String stkIdn = util.nvl(rs.getString("stk_idn"));
            
              HashMap pktPrpMap = new HashMap();
              pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
              String vnm = util.nvl(rs.getString("vnm"));
              pktPrpMap.put("vnm",vnm);
              pktPrpMap.put("stk_idn",stkIdn);
              pktPrpMap.put("flg", util.nvl(rs.getString("flg")));
             
              for(int j=0; j < vwPrpLst.size(); j++){
                   String prp = (String)vwPrpLst.get(j);
                    
                    String fld="prp_";
                    if(j < 9)
                            fld="prp_00"+(j+1);
                    else    
                            fld="prp_0"+(j+1);
                    
                    String val = util.nvl(rs.getString(fld)) ;
                    if(prp.equals("CRTWT"))
                    val = util.nvl(rs.getString("cts"));
                    if (prp.toUpperCase().equals("RFIDCD"))
                    val = util.nvl(rs.getString("tfl3"));
                      pktPrpMap.put(prp, val);
                      if(prpDspBlocked.contains(prp)){
                      }else if(!itemHdr.contains(prp)){
                      itemHdr.add(prp);
                      }
                      }
                            
                  pktList.add(pktPrpMap);
              }
          rs.close();
          pst.close();
      } catch (SQLException sqle) {

          // TODO: Add catch code
          sqle.printStackTrace();
      }
        session.setAttribute("pktList", pktList);
        session.setAttribute("itemHdr",itemHdr);
    }
    public ActionForward createXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Stock Tally", "Stock Tally Create Excel");
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "stocktallyreturn"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        ArrayList pktList = (ArrayList)session.getAttribute("pktList");
        ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
        HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
        hwb.write(out);
        out.flush();
        out.close();
        return null;
        }
    }
    
    public ActionForward loaddelete(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
         StockTallyForm udf = (StockTallyForm)af;
           util.updAccessLog(req,res,"Stock Tally", "Stock Tally loaddelete start");
           util.updAccessLog(req,res,"Stock Tally", "Stock Tally loaddelete end");
       return am.findForward("delete");
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
         StockTallyForm udf = (StockTallyForm)af;
           util.updAccessLog(req,res,"Stock Tally", "Stock Tally Create delete start");
           ArrayList ary=new ArrayList();
           String seq=util.nvl((String)udf.getValue("seq"));
           String conQ="";
           if(!seq.equals("")){
               conQ=" and seq_idn=?"; 
               ary.add(seq);
           }
           String updateStk_tly="delete stk_tly_nf where trunc(rtn_dte)=trunc(sysdate) "+conQ;
           int upCnt = db.execUpd("upda", updateStk_tly, ary);
           
           updateStk_tly="delete stk_tly where trunc(iss_dte)=trunc(sysdate)"+conQ;
           upCnt = db.execUpd("upda", updateStk_tly, ary);
           
           TallyStockstatus(req);
           TallyStock(req,"");
           req.setAttribute("RTNMSG", "Deletion Done Sucessfully");
           util.updAccessLog(req,res,"Stock Tally", "Stock Tally Create delete end");
       return am.findForward("delete");
       }
    }
    public ActionForward createXLstocktally(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
    util.updAccessLog(req,res,"Stock Tally", "Stock Tally createXLstocktally start");
    ExcelUtil xlUtil = new ExcelUtil();
    xlUtil.init(db, util, info);
    OutputStream out = res.getOutputStream();
    String CONTENT_TYPE = "getServletContext()/vnd-excel";
    String fileNm = "PacketDtls"+util.getToDteTime()+".xls";
    res.setContentType(CONTENT_TYPE);
    res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
    ArrayList pktList = (ArrayList)session.getAttribute("pktList");
    ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
    HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
    hwb.write(out);
    out.flush();
    out.close();
    return null;
    }
    }
//    public ArrayList GenricSrch(HttpServletRequest req , HttpServletResponse res){
//        init(req,res);
//        ArrayList asViewPrp = (ArrayList)session.getAttribute("StkTllySrch");
//        try {
//            if (asViewPrp == null) {
//
//                asViewPrp = new ArrayList();
//                ResultSet rs1 =
//                    db.execSql(" Vw Lst ", "Select prp, flg  from rep_prp where mdl = 'STK_TLLY_SRCH' and flg in ('Y','S') order by rnk ",
//                               new ArrayList());
//                while (rs1.next()) {
//                    ArrayList asViewdtl=new ArrayList();
//                    asViewdtl.add(rs1.getString("prp"));
//                    asViewdtl.add(rs1.getString("flg"));
//                    asViewPrp.add(asViewdtl);
//                }
//                rs1.close();
//                session.setAttribute("StkTllySrch", asViewPrp);
//            }
//
//        } catch (SQLException sqle) {
//            // TODO: Add catch code
//            sqle.printStackTrace();
//        }
//        
//        return asViewPrp;
//    }
//    
    public HashMap  getstktlynf(HttpServletRequest req,String seq){
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
        HashMap flgMap = new HashMap();
        ArrayList boxidLst=new ArrayList();
        ArrayList ary=new ArrayList();
        String str="";
        if(!seq.equals("") && !seq.equals("0")){
            str = " and seq_idn = ? ";
        }
        String gtFtch = "select count(distinct ref_idn) cnt, flg,box_id,lower(rtn_usr) rtn_usr \n" + 
        "from stk_tly_nf \n" + 
        "where\n" + 
        "trunc(rtn_dte)=trunc(sysdate)\n" +str +
        "group by flg, box_id,lower(rtn_usr)\n" +
        "union\n" + 
        "select count(distinct ref_idn) cnt, flg,'TOTAL',' '  \n" + 
        "from stk_tly_nf \n" + 
        "where \n" + 
        "flg not in('AR') and trunc(rtn_dte)=trunc(sysdate)\n" +str+ 
        "group by flg " +
        "union\n" + 
        "select count(*) cnt, flg,'TOTAL',' '   \n" + 
        "from stk_tly_nf \n" + 
        "where \n" + 
        "flg in('AR') and trunc(rtn_dte)=trunc(sysdate)\n" +str+ 
        "group by flg " +
        "order by 3 desc";
        if(!seq.equals("") && !seq.equals("0"))
            ary.add(seq); 
        if(!seq.equals("") && !seq.equals("0"))
            ary.add(seq); 
        if(!seq.equals("") && !seq.equals("0"))
            ary.add(seq); 
      ArrayList  outLst = db.execSqlLst("gt_srch", gtFtch, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet  rs = (ResultSet)outLst.get(1);
        try {
        while(rs.next()){
            String cnt = util.nvl(rs.getString("cnt"));
            String flg = util.nvl(rs.getString("flg"));
            String box_id = util.nvl(rs.getString("box_id"));
            flgMap.put(box_id+"_"+flg, cnt);
            flgMap.put(box_id+"_RTN_USR", util.nvl(rs.getString("rtn_usr")));
            if(!boxidLst.contains(box_id) && !box_id.equals("TOTAL"))
                boxidLst.add(box_id);
        } 
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        if(flgMap.size()>0)
        flgMap.put("BOXID", boxidLst);
       return flgMap;
    }
        
    public ActionForward PktDtlExtra(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
                util.updAccessLog(req,res,"Stock Tally", "PktDtlExtra start");
            GenericInterface genericInt = new GenericImpl();
            String stt = util.nvl(req.getParameter("stt"));
            String box = util.nvl(req.getParameter("box"));
            String loadSeq = util.nvl(req.getParameter("loadSeq"));
            HashMap dbinfo = info.getDmbsInfoLst();
            String BOX_RFID = (String)dbinfo.get("BOX_RFID");
            ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "rfidPrpLst", "RFID_VW");
            int ct = db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());
            ArrayList ary = new ArrayList();
            String pktDtl =" Insert into gt_srch_rslt (stk_idn,vnm ,cts,stt, flg,sk1,rmk) \n" + 
            "            select distinct b.idn , b.vnm ,b.cts,b.stt, a.flg,b.sk1,b.tfl3 from stk_tly_nf a,mstk b \n" + 
            "            where  1 = 1 and ( b.vnm=a.ref_idn or b.tfl3 =a.ref_idn) and trunc(a.rtn_dte) = trunc(sysdate) ";
            if(stt.equals("NF")){
                int indexBX = vwPrpLst.indexOf(BOX_RFID)+1;
                if(indexBX==0)
                indexBX=1;
                String lbPrp = util.nvl(util.prpsrtcolumn("prp",indexBX),"prp_001");
                pktDtl =" Insert into gt_srch_rslt (stk_idn,vnm,flg,rmk,"+lbPrp+") \n" + 
                            "            select distinct rownum,a.ref_idn,a.flg,a.ref_idn,box_id from stk_tly_nf a \n" + 
                            "            where  1 = 1 and trunc(a.rtn_dte) = trunc(sysdate) ";
                
            }
            if(stt.equals("RT")){
                pktDtl =" Insert into gt_srch_rslt (stk_idn,vnm ,cts,stt,dsp_stt, flg,sk1,rmk) \n" + 
                "select distinct b.idn , b.vnm ,b.cts,b.stt,c.flg1, a.flg,b.sk1,b.tfl3 \n" + 
                "from stk_tly_nf a,mstk b,stk_tly c\n" + 
                "where  1 = 1 and c.seq_idn=a.seq_idn \n" + 
                "and (c.vnm=a.ref_idn or c.tfl3 =a.ref_idn) and ( b.vnm=a.ref_idn or b.tfl3 =a.ref_idn) and trunc(a.rtn_dte) = trunc(sysdate) ";
                
            }
            if(!stt.equals("")){
                pktDtl = pktDtl+" and a.flg =?" ;
                ary.add(stt);
            }
                if(!loadSeq.equals("") && !loadSeq.equals("0")){
                    pktDtl = pktDtl+" and a.seq_idn =?" ;
                    ary.add(loadSeq);
                }
                if(!box.equals("") && !box.equals("TOTAL")){
                    pktDtl = pktDtl+" and a.box_id =?" ;
                    ary.add(box);
                }
            ct = db.execUpd("pktDtl", pktDtl, ary);
            if(!stt.equals("NF")){
            String pktPrp = "pkgmkt.pktPrp(0,?)";
            ary = new ArrayList();
            ary.add("RFID_VW");
             ct = db.execCall(" Srch Prp ", pktPrp, ary);
            }
                util.updAccessLog(req,res,"Stock Tally", "PktDtlExtra end");
         return SearchResult(am, af, req, res);
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
                util.updAccessLog(req,res,"Stock Tally", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Stock Tally", "init");
            }
            }
            return rtnPg;
            }
}
