package ft.com.rfid;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.MailAction;
import ft.com.dao.ByrDao;
import ft.com.dao.PktDtl;
import java.sql.Connection;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.BuyBackForm;

import java.io.IOException;

import java.sql.CallableStatement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class PacketRfidBoxAllocAction  extends DispatchAction {
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
        PacketRfidBoxAllocForm udf = (PacketRfidBoxAllocForm)af;
         util.updAccessLog(req,res,"Packet Rfid Box Allocation", "load start");      
         util.updAccessLog(req,res,"Packet Rfid Box Allocation", "load end"); 
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
         util.updAccessLog(req,res,"Packet Rfid Box Allocation", "save start"); 
         PacketRfidBoxAllocForm udf = (PacketRfidBoxAllocForm)af;
         HashMap dbinfo = info.getDmbsInfoLst();
         String BOX_RFID = (String)dbinfo.get("BOX_RFID");
         int ct = db.execCall("delete gt", "delete from gt_file_trf", new ArrayList());
         String boxrfidval = util.nvl((String)udf.getValue("boxrfidval"));
         String val = util.nvl((String)udf.getValue("val"));  
         if(!boxrfidval.equals("") && !val.equals("") ){
           ResultSet rs = null;
           boolean allow=false;
           int boxrfidremove=0;
           ArrayList ary = new ArrayList();
           ArrayList valList = new ArrayList();
           val = util.getVal(val);         
           val = val.substring(1, val.length()-1);
           String[] valStr = val.split("','");
           for(int i=0;i<valStr.length;i++){
                   val = valStr[i];
                 val=val.replaceAll(",", "");
                 val=val.replaceAll("'", "");
                 val = val.trim();
                 valList.add(val);
           }
               for(int k=0 ;k<valList.size();k++){
                   String insertGt = "insert into gt_file_trf(stk_idn , lab , mprp , val ,flg) select idn , cert_lab , ? , ? , ? from mstk where tfl3=? and pkt_ty='NR' ";
                   insertGt+=" and not exists(select 1 from gt_file_trf b where mstk.idn=b.stk_idn)";
                   ary = new ArrayList();
                   ary.add(BOX_RFID);
                   ary.add(boxrfidval);
                   ary.add("N");
                   ary.add(valList.get(k));
                   ct = db.execDirUpd("insert gt_file_trf", insertGt, ary);
                   if(ct<=0)
                   boxrfidremove++; 
                   else if(!allow)
                   allow=true;   
               }
               if(allow){
               ArrayList out = new ArrayList();
               out.add("I");
               out.add("V");

               CallableStatement ct1 = db.execCall("update pkt", "PRP_PKG.BLK_UPD(pCnt => ? , pMsg=> ?)", new ArrayList(), out);
               int count = ct1.getInt(1);
               String msg = ct1.getString(2);
               req.setAttribute("msg","Number of Packet Allocation Done : "+count);
               req.setAttribute("msg1",msg);
               udf.reset();
                   String gtFtch = "select count(*) cnt, flg from gt_file_trf group by flg";

                   ArrayList outLst = db.execSqlLst("gt_srch", gtFtch, new ArrayList());
                   PreparedStatement pst = (PreparedStatement)outLst.get(0);
                   rs = (ResultSet)outLst.get(1);
                   HashMap flgMap = new HashMap();
                   while(rs.next()){
                       String cnt = util.nvl(rs.getString("cnt"));
                       String flg = util.nvl(rs.getString("flg"));
                       flgMap.put(flg, cnt);
                   }
                   rs.close(); pst.close();
                   flgMap.put("T", String.valueOf(valStr.length-boxrfidremove));
                   req.setAttribute("sttMap", flgMap);
               }else{
                   req.setAttribute("msg","Update Process failed..");
               }
         }else{
             req.setAttribute("msg","Please check Box Rfid and Packet corresponding Rfid Values. ");
         }
         udf.reset();
         util.updAccessLog(req,res,"Packet Rfid Box Allocation", "save end"); 
         return am.findForward("load");
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
              util.updAccessLog(req,res,"Packet Rfid Box Allocation", "SearchResult start"); 
              GenericInterface genericInt = new GenericImpl();
              int ct = db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());
              int count = 0;
              String flg = util.nvl(req.getParameter("flg"));
              ArrayList pktList = new ArrayList();
              ArrayList itemHdr = new ArrayList();
              ArrayList ary = new ArrayList();
              ArrayList vwPrpLst =genericInt.genericPrprVw(req,res,"rfidPrpLst","RFID_VW");
              ArrayList prpDspBlocked = info.getPageblockList();
              itemHdr.add("SR");
              itemHdr.add("VNM");
              itemHdr.add("STATUS");
              String conQ="";
              if(!flg.equals(""))
                  conQ =" and a.flg in ('"+flg+"') ";  
              
              String srchRefQ =
              "Insert into gt_srch_rslt (rln_idn, srch_id, pkt_ty, stk_idn, vnm, qty, cts, pkt_dte, stt, cmp, rap_rte, cert_lab, cert_no, flg, sk1, quot, rap_dis,rmk) \n" + 
              "select 1, 1 srchId, pkt_ty, b.idn, b.vnm, b.qty qty, trunc(b.cts,2) cts, b.dte, b.stt \n" + 
              ", round(nvl(upr, cmp)) rte, rap_rte, cert_lab, cert_no, 'Z' flg, sk1, decode(nvl(upr,0), 0, cmp, upr), decode(rap_rte ,'1',null,'0',null, trunc((nvl(upr,cmp)/greatest(rap_rte,1)*100)-100, 2)),b.tfl3 \n" + 
              "from gt_file_trf a,mstk b\n" + 
              "where a.stk_idn=b.idn and pkt_ty in ('NR') "+conQ;
              ct = db.execUpd(" Srch Prp ", srchRefQ, new ArrayList());
              
              String pktPrp = "pkgmkt.pktPrp(0,?)";
              ary = new ArrayList();
              ary.add("RFID_VW");
              ct = db.execCall(" Srch Prp ", pktPrp, ary);
              
              String srchQ = " select stk_idn , pkt_ty, vnm, pkt_dte, stt , qty ,cmnt,cert_no, cts ,kts_vw kts, quot ,rmk tfl3,rap_rte,decode(rap_rte, 1, '', trunc(((nvl(quot,0)/greatest(rap_rte,1))*100)-100,2)) dis,trunc(quot*cts,2) total_val,trunc(rap_rte*cts,2) rap_val, to_char(trunc(cts,2)*prte ,'999990.99') val ";
              for (int i = 0; i < vwPrpLst.size(); i++) {
              String prp=(String)vwPrpLst.get(i);
              String fld = "prp_";
              int j = i + 1;
              if (j < 10)
              fld += "00" + j;
              else if (j < 100)
              fld += "0" + j;
              else if (j > 100)
              fld += j;



              srchQ += ", " + fld;
                  if(prpDspBlocked.contains(prp)){
                  }else{
                  itemHdr.add(prp);
                  }
              }


              String rsltQ = srchQ + " from gt_srch_rslt where flg =? order by sk1,cts";
              ary = new ArrayList();
              ary.add("Z");

              ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs = (ResultSet)outLst.get(1);
              while(rs.next()) {
              count++;
              String stkIdn = util.nvl(rs.getString("stk_idn"));
              String stt = util.nvl(rs.getString("stt"));


              HashMap pktPrpMap = new HashMap();
              pktPrpMap.put("STATUS", stt);
               
              pktPrpMap.put("SR", String.valueOf(count));
              String vnm = util.nvl(rs.getString("vnm"));
              pktPrpMap.put("VNM",vnm);
              pktPrpMap.put("stk_idn", stkIdn);
              pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
              pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
              pktPrpMap.put("quot",util.nvl(rs.getString("quot")));

              pktPrpMap.put("total_val",util.nvl(rs.getString("total_val")));
              pktPrpMap.put("rap_val",util.nvl(rs.getString("rap_val")));
              pktPrpMap.put("CERT NO",util.nvl(rs.getString("cert_no")));
                  for (int j = 0; j < vwPrpLst.size(); j++) {
                      String prp = (String)vwPrpLst.get(j);

                      String fld = "prp_";
                      if (j < 9)
                          fld = "prp_00" + (j + 1);
                      else
                          fld = "prp_0" + (j + 1);

                      String val = util.nvl(rs.getString(fld));
                
                      if (prp.toUpperCase().equals("RAP_DIS"))
                      {
                          val = util.nvl(rs.getString("dis"));
                      }
                      if(prp.equalsIgnoreCase("KTSVIEW"))
                        val = util.nvl(rs.getString("kts"));
                        if(prp.equalsIgnoreCase("COMMENT"))
                          val = util.nvl(rs.getString("cmnt"));
                      if (prp.toUpperCase().equals("RFIDCD"))
                      val = util.nvl(rs.getString("tfl3"));
                      if (prp.toUpperCase().equals("RAP_RTE"))
                          val = util.nvl(rs.getString("rap_rte"));
                      if(prp.toUpperCase().equals("RTE"))
                          val = util.nvl(rs.getString("quot"));
                          pktPrpMap.put(prp, val);
                      }

              pktList.add(pktPrpMap);
              }

              rs.close(); pst.close();
                session.setAttribute("pktList", pktList);
                session.setAttribute("itemHdr",itemHdr);
              util.updAccessLog(req,res,"Packet Rfid Box Allocation", "SearchResult end"); 
          return am.findForward("pktDtl");
          }
      }
    public ActionForward getBoxRfidVal(ActionMapping mapping, ActionForm form,
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
            return mapping.findForward(rtnPg);   
        }else{
            HashMap dbinfo = info.getDmbsInfoLst();
            String BOX_RFID = (String)dbinfo.get("BOX_RFID");
        StringBuffer sb = new StringBuffer();
        String boxrfid = util.nvl(req.getParameter("boxrfid")).trim();
            HashMap prp = info.getPrp();
            HashMap mprp = info.getMprp();
            ArrayList prpPrt2Size=null;
            ArrayList prpValSize=null;
            prpValSize = (ArrayList)prp.get(BOX_RFID+"V");
            prpPrt2Size = (ArrayList)prp.get(BOX_RFID+"P2");
            int getIndex=prpPrt2Size.indexOf(boxrfid);
            sb.append("<detail>");
            if(getIndex > -1){
            String boxrfidval = util.nvl((String)prpValSize.get(getIndex));
            sb.append("<boxrfidval>"+boxrfidval+"</boxrfidval>");
            }else{
            sb.append("<boxrfidval>N</boxrfidval>");    
            }
            sb.append("</detail>");
        
        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        res.getWriter().write("<details>" + sb.toString() + "</details>");
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
                util.updAccessLog(req,res,"Packet Rfid Box Allocation", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Packet Rfid Box Allocation", "init"); 
            }
            }
            return rtnPg;
            }
    public PacketRfidBoxAllocAction() {
        super();
    }
}
