package ft.com.marketing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;
import ft.com.JspUtil;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ManagePairAction  extends DispatchAction{
    public ManagePairAction() {
        super();
    }
    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));db.setDbUsr(info.getDbUsr());
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
        util.updAccessLog(req,res,"Manage Pair", "Manage Pair Load");
        ManagePairForm udf = (ManagePairForm)af;
        udf.reset();
        util.updAccessLog(req,res,"Manage Pair", "Manage Pair Load done");
        session.setAttribute("itemHdrManagePair", new ArrayList());
        session.setAttribute("PktListManagePair", new ArrayList());
        return am.findForward("load");
      }
    }
    
    public ActionForward generate(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));db.setDbUsr(info.getDbUsr());
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
        util.updAccessLog(req,res,"Manage Pair", "Manage Pair generate");
        ManagePairForm udf = (ManagePairForm)af;
        String loadall = util.nvl((String)udf.getValue("loadall"));
        String resetall = util.nvl((String)udf.getValue("resetall"));
        String remove = util.nvl((String)udf.getValue("remove"));
        String generate = util.nvl((String)udf.getValue("generate"));
        String msg="";
        int cnt=0;
          ArrayList param = new ArrayList();
        if(!resetall.equals("")){
            String updateFlg = "update stk_dtl st  set num=null where mprp='PAIR_ID' and grp=1 and nvl(num,0) > 0 and exists\n" + 
            "(select 1 from mstk ms where st.mstk_idn=ms.idn and ms.pkt_ty <> 'MIX' and ms.stt in('MKAV','BRAV','MKEI','MKIS','MKAP','MKWA','MKWH'))";
            cnt = db.execUpd("update stk_dtl Pair Id", updateFlg, new ArrayList());  
            if(cnt>0)
            msg="Removed All Pair Id Sucessfully";
        }
          
        if(!loadall.equals("")){
            ArrayList pktList = new ArrayList();
            ArrayList itemHdr = new ArrayList();
            GenericInterface genericInt = new GenericImpl();
            ArrayList prpDspBlocked = info.getPageblockList();
            ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "PAIR_VW","PAIR_VW");
            itemHdr.add("PAIR_ID");
            itemHdr.add("VNM");
            itemHdr.add("RTE");
            itemHdr.add("RAP_DIS");
            for (int i = 0; i < vwPrpLst.size(); i++) {
            String lprp=(String)vwPrpLst.get(i);
            String fld = "prp_";
            int j = i + 1;
            if (j < 10)
            fld += "00" + j;
            else if (j < 100)
            fld += "0" + j;
            else if (j > 100)
            fld += j;
            if(prpDspBlocked.contains(lprp)){
            }else{
            itemHdr.add(lprp);
            }}            
            param = new ArrayList();
            String mprpStr = "";
            String mdlPrpsQ = " Select Decode ( Rank() Over ( Order By Srt),1,' ',',')||''''||Prp||''''||' '||Upper(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1')) str From Rep_Prp Rp Where rp.MDL = ? and flg ='Y' order by srt " ;
            param = new ArrayList();
            param.add("PAIR_VW");
          ArrayList  outLst = db.execSqlLst("mprp ", mdlPrpsQ , param);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()) {
            String val = util.nvl((String)rs.getString("str"));
            mprpStr = mprpStr +" "+val;
            }
            rs.close();
            pst.close();
            String rsltQ = " with  STKDTL as  ( select c.num p_id,b.sk1, nvl(nvl(a.txt,a.num),a.val) atr , a.mprp,b.idn, b.vnm ,\n" + 
            "b.cts,b.upr rte1, b.rap_rte,\n" + 
            "to_char(decode(b.rap_rte, 1, '', trunc(((nvl(b.upr, b.cmp)/greatest(b.rap_rte,1))*100)-100,2)),9990.99) dis \n" + 
            "from stk_dtl a, mstk b , stk_dtl c \n" + 
            "Where 1=1  and a.mstk_idn = b.idn and b.idn = c.mstk_idn and c.grp=1 and c.mprp='PAIR_ID' and nvl(c.num,0)>0\n" + 
            "and b.stt in('MKAV','BRAV','MKEI','MKIS','MKAP','MKWA','MKWH')\n" + 
            "and exists ( select 1 from rep_prp rp where rp.MDL = ? and a.mprp = rp.prp)  And a.Grp = 1)  \n" + 
            "Select * from stkDtl PIVOT  \n" + 
            "( max(atr) \n" + 
            " for mprp in ( "+mprpStr+" ) ) order by 1,2 " ;
            
            param = new ArrayList();
            param.add("PAIR_VW");
            outLst = db.execSqlLst("search Result", rsltQ, param);
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
            HashMap pktPrpMap = new HashMap();
            pktPrpMap.put("VNM", util.nvl((String)rs.getString("vnm")));
            pktPrpMap.put("PAIR_ID", util.nvl((String)rs.getString("p_id")));
            pktPrpMap.put("STK_IDN", util.nvl((String)rs.getString("idn")));
            for (int i = 0; i < vwPrpLst.size(); i++) {
            String vwPrp = (String)vwPrpLst.get(i);
            String fldName = util.pivot(vwPrp);
            String fldVal = util.nvl((String)rs.getString(fldName));
            
            pktPrpMap.put(vwPrp, fldVal);
            }
            pktPrpMap.put("RTE", util.nvl((String)rs.getString("rte1")));
            pktPrpMap.put("RAP_DIS", util.nvl((String)rs.getString("dis")));
            pktList.add(pktPrpMap);
            }
            rs.close();
            pst.close();
            session.setAttribute("itemHdrManagePair",itemHdr);
            session.setAttribute("PktListManagePair",pktList);
        }
          if(!remove.equals("")){
              ArrayList pktList = (ArrayList)session.getAttribute("PktListManagePair");
              if(pktList!=null && pktList.size()>0){
                  String updateFlg = "update stk_dtl set num=null where mprp='PAIR_ID' and grp=1 and mstk_idn=?";
                  for(int j=0 ; j <pktList.size() ;j++){
                    HashMap pktDtl = (HashMap)pktList.get(j);
                    String pair_id=util.nvl((String)pktDtl.get("PAIR_ID"));
                    String chkPairid=util.nvl((String)req.getParameter("cb_stk_"+pair_id));
                      if(chkPairid.equals(pair_id)){
                          param = new ArrayList();
                          param.add(util.nvl((String)pktDtl.get("STK_IDN")));
                          cnt = db.execUpd("update stk_dtl Pair Id", updateFlg, param);  
                      }
                  }
                  if(cnt>0)
                  msg="Removed All Pair Id Sucessfully";
              }
          }
        
        if(!generate.equals("")){
            String vnmLst1 = util.nvl((String)udf.getValue("vnmLst1"));
            String vnmLst2 = util.nvl((String)udf.getValue("vnmLst2"));
            vnmLst1 = util.getVnm(vnmLst1);
            vnmLst2 = util.getVnm(vnmLst2);
            
            ArrayList vnmList1 = new ArrayList();
            vnmLst1 = vnmLst1.substring(1, vnmLst1.length()-1);
            String[] vnm1Str = vnmLst1.split("','");
            
            ArrayList vnmList2 = new ArrayList();
            vnmLst2 = vnmLst2.substring(1, vnmLst2.length()-1);
            String[] vnm2Str = vnmLst2.split("','");
            if(vnm1Str.length==vnm2Str.length){
                for(int i=0;i<vnm1Str.length;i++){
                   vnmLst1 = vnm1Str[i];
                   vnmLst1 = vnmLst1.replaceAll(",", "");
                   vnmLst1 = vnmLst1.replaceAll("'", "");
                 vnmList1.add(vnmLst1);
                }
                for(int i=0;i<vnm2Str.length;i++){
                   vnmLst2 = vnm2Str[i];
                   vnmLst2 = vnmLst2.replaceAll(",", "");
                   vnmLst2 = vnmLst2.replaceAll("'", "");
                 vnmList2.add(vnmLst2);
                }
                
                for(int k=0 ;k<vnmList1.size();k++){
                    int seq=util.getSeqVal("PAIR_ID_STK_SEQ");
                    String sruQ="select a.idn from mstk a where a.vnm in('"+((String)vnmList1.get(k)).trim()+"','"+((String)vnmList2.get(k)).trim()+"') and a.stt in('MKAV','BRAV','MKEI','MKIS','MKAP','MKWA','MKWH') and not exists(select 1 from stk_dtl b where a.idn=b.mstk_idn and b.grp=1 and b.mprp='PAIR_ID' and nvl(b.num,0)<>0)";
                 ArrayList  outLst = db.execSqlLst("sruQ", sruQ, new ArrayList());
                 PreparedStatement pst = (PreparedStatement)outLst.get(0);
                 ResultSet  rs = (ResultSet)outLst.get(1);
                    while(rs.next()){
                        param = new ArrayList();
                        param.add(util.nvl(rs.getString("idn")));
                        param.add("PAIR_ID");
                        param.add(String.valueOf(seq));
                        String stockUpd ="stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?  )";
                        cnt = db.execCall("stockUpd",stockUpd, param);
                    }
                    rs.close();
                    pst.close();
                }
                if(cnt>0)
                msg="Pair Id Created Sucessfully";
            }else
            msg="Please check Packets and there corresponding  Packets. ";
        }
        req.setAttribute("rtnMsg", msg);
        udf.reset();
         if(loadall.equals("")){
              session.setAttribute("itemHdrManagePair", new ArrayList());
              session.setAttribute("PktListManagePair", new ArrayList());
         }
        util.updAccessLog(req,res,"Manage Pair", "Manage Pair generate done");
        return am.findForward("load");
      }
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
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));db.setDbUsr(info.getDbUsr());
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
            util.updAccessLog(req,res,"Manage Pair", "createXL start");
        HashMap dbinfo = info.getDmbsInfoLst();
        String zipallowyn = util.nvl((String)dbinfo.get("ZIP_DWD_YN"),"N"); 
        int zipdwnsz = Integer.parseInt(util.nvl((String)dbinfo.get("ZIP_DWD_SZ"),"100")); 
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        String   imgCol= util.nvl(req.getParameter("imgCol"));
        ArrayList pktList = (ArrayList)session.getAttribute("PktListManagePair");
        ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdrManagePair");
         ArrayList imagelistDtl= (info.getImagelistDtl() == null)?new ArrayList():(ArrayList)info.getImagelistDtl();
         HashMap dtls=new HashMap();
        int pktListsz=pktList.size();
        HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
            if(zipallowyn.equals("Y") && pktListsz>zipdwnsz){
            String contentTypezip = "application/zip";
            String fileNmzip = "ResultExcel"+util.getToDteTime();
            OutputStream outstm = res.getOutputStream();
            ZipOutputStream zipOut = new ZipOutputStream(outstm);
            ZipEntry entry = new ZipEntry(fileNmzip+".xls");
            zipOut.putNextEntry(entry);
            res.setHeader("Content-Disposition","attachment; filename="+fileNmzip+".zip");
            res.setContentType(contentTypezip);
              ByteArrayOutputStream bos = new ByteArrayOutputStream();
               hwb.write(bos);
               bos.writeTo(zipOut);      
              zipOut.closeEntry();
               zipOut.flush();
               zipOut.close();
               outstm.flush();
               outstm.close(); 
            }else{
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "ResultExcel"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        hwb.write(out);
        out.flush();
        out.close();
        }
            util.updAccessLog(req,res,"Manage Pair", "createXL end");
        return null;
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
                util.updAccessLog(req,res,"Manage Pair", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Manage Pair", "init");
            }
            }
            return rtnPg;
            }
}
