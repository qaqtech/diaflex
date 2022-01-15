package ft.com.generic;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.net.MalformedURLException;

import java.sql.Array;
import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.dao.GtPktDtl;

import java.io.FileOutputStream;
import java.io.IOException;

import java.io.InputStream;

import java.net.ConnectException;
import java.net.InetSocketAddress;

import java.net.URL;
import java.net.URLConnection;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.spy.memcached.MemcachedClient;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;

import oracle.sql.ARRAY;
import oracle.sql.STRUCT;
public class GenericImpl implements GenericInterface {
   
    public ArrayList genricSrch(HttpServletRequest req , HttpServletResponse res,String sname,String mdl){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList genViewPrp = (session.getAttribute(sname) == null)?new ArrayList():(ArrayList)session.getAttribute(sname);
      genViewPrp = new ArrayList();
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
         try {
        if(genViewPrp.size() == 0) {
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt=util.nvl((String)dbinfo.get("CNT"));
            String mem_ip=util.nvl((String)dbinfo.get("MEM_IP"),"13.229.255.212");
            int mem_port=Integer.parseInt(util.nvl((String)dbinfo.get("MEM_PORT"),"11211"));
            MemcachedClient mcc = new MemcachedClient(new InetSocketAddress(mem_ip, mem_port));
            genViewPrp=(ArrayList)mcc.get(cnt+"_"+sname);
            genViewPrp =new ArrayList();
            if(genViewPrp==null)
                genViewPrp = new ArrayList();
            if(genViewPrp.size()==0){
            genViewPrp=new ArrayList();
                ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp , flg  from rep_prp where mdl = '"+mdl.trim()+"' and  flg <> 'N' order by rnk ",
                               new ArrayList());
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
                while (rs.next()) {
                    ArrayList asViewdtl=new ArrayList();
                    asViewdtl.add(rs.getString("prp"));
                    asViewdtl.add(rs.getString("flg"));
                    genViewPrp.add(asViewdtl);
                }
                rs.close(); pst.close();
                Future fo = mcc.delete(cnt+"_"+sname);
                System.out.println("add status:_"+sname + fo.get());
                fo = mcc.set(cnt+"_"+sname, 24*60*60, genViewPrp);
                System.out.println("add status:_"+sname + fo.get());
            }
                session.setAttribute(sname, genViewPrp);
                mcc.shutdown();
        }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }catch(Exception ex){
         System.out.println( ex.getMessage() );
      }
      }
        return genViewPrp;
    }
    public ArrayList genericPrprVw(HttpServletRequest req , HttpServletResponse res,String sname,String mdl){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList genViewPrp = (session.getAttribute(sname) == null)?new ArrayList():(ArrayList)session.getAttribute(sname); 
     genViewPrp = new ArrayList();
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
          try {
            if(genViewPrp.size() == 0) {
                HashMap dbinfo = info.getDmbsInfoLst();
                String cnt=util.nvl((String)dbinfo.get("CNT"));
                String mem_ip=util.nvl((String)dbinfo.get("MEM_IP"),"13.229.255.212");
                int mem_port=Integer.parseInt(util.nvl((String)dbinfo.get("MEM_PORT"),"11211"));
                MemcachedClient mcc = new MemcachedClient(new InetSocketAddress(mem_ip, mem_port));
                genViewPrp=(ArrayList)mcc.get(cnt+"_"+sname);
               genViewPrp =new ArrayList();
                if(genViewPrp==null)
                    genViewPrp =new ArrayList();
                if(genViewPrp.size()==0){
                genViewPrp=new ArrayList();

                    ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = '"+mdl.trim()+"' and flg='Y' order by rnk ",
                               new ArrayList());
                    PreparedStatement pst = (PreparedStatement)outLst.get(0);
                    ResultSet rs = (ResultSet)outLst.get(1);
                while (rs.next()) {

                    genViewPrp.add(rs.getString("prp"));
                }
                rs.close(); pst.close();
                    Future fo = mcc.delete(cnt+"_"+sname);
                    System.out.println("add status:_"+sname + fo.get());
                    fo = mcc.set(cnt+"_"+sname, 24*60*60, genViewPrp);
                    System.out.println("add status:_"+sname + fo.get());
                }
                session.setAttribute(sname, genViewPrp);
                mcc.shutdown();
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }catch(Exception ex){
             System.out.println( ex.getMessage() );
        }
      }
        return genViewPrp;
    }
    public HashMap SearchResult(HttpServletRequest req,HttpServletResponse res,String display,String mdl, String flg , ArrayList vwPrpLst) {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      HashMap pktListMap = new HashMap(),pktPrpMap = new HashMap(),noGtpktListMap= new HashMap();
      
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
            String cnt = util.nvl((String)info.getDmbsInfoLst().get("CNT"));
            String vnmLst = util.nvl((String)req.getAttribute("vnmLst"));
            int ct = db.execCall("ud_dsp_stt", "pkgmkt.upd_dsp_stt", new ArrayList());

            ArrayList pktStkIdnList = new ArrayList();
            ArrayList ary = new ArrayList();
            boolean isSrchRef = true;
            String srchQ = "";
            String srchRef = util.nvl((String)req.getAttribute("srchRef"));
            if(srchRef.equals(""))
            isSrchRef=false;
            noGtpktListMap=NoGtPop(req,res,display,mdl,cnt);
            String cpPrp = "prte";
            if(vwPrpLst.contains("CP"))
            cpPrp =  util.prpsrtcolumn("srt", vwPrpLst.indexOf("CP")+1);
            String srchQ1 = " select sk1, 0 dsp , a.stk_idn , cert_lab , dsp_stt , decode(stt " +
            ", 'MKAP',' color:Olive'" + 
            ", 'MKPP','color:Green'" + 
            ", 'MKWH','color:Red'" + 
            " , 'MKLB','color:Maroon'" + 
            " , 'MKAV', decode(instr(upper(dsp_stt), 'NEW'), 0, ' ', 'color:Blue')" + 
            " , 'MKIS', 'color:Red'" + 
            " , 'MKEI', 'color:Red'" + 
          " , 'MKKS_IS', 'color:Red'" + 
          " , 'MKOS_IS', 'color:Red'" + 
            " , 'SOLD') class ,  ";
            String srchQ2 = " select sk1, 1 dsp , a.stk_idn , cert_lab , '' dsp_stt , '' class , ";
            String dspStk = "stkDspFF";
            String getQuot = "quot rte";
            srchQ += "  cts crtwt, " +
                    " decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis " +
                    " , stk_idn  , vnm, kts_vw kts, cert_lab cert, cert_no, rmk, img,img2, cmp , trunc(((cmp*100)/greatest(rap_rte,1)) - 100,2) cmp_dis ,  to_char(trunc(cts,2),'9990.99') cts, qty , rap_rte , " +
                    getQuot +
                    ", cmnt,stt stt1,  nvl(fquot,0) fquot , "+cpPrp+" prte , nvl("+cpPrp+",0)*nvl(cts,0) cptotal ,trunc(((greatest("+cpPrp+",1)*100)/greatest(rap_rte,1)) - 100,2) cpdis, flg , to_char(trunc(cts,2) * quot, '99999990.00') amt , " +
                    " decode(nvl(fquot,0),0, 'NA', decode(fquot, quot, 'NA', decode(greatest(fquot, quot),quot, 'UP','DN'))) cmp_flg ";
         
            String rsltQ =
              srchQ1+" "+srchQ + " from gt_srch_rslt a where flg in ("+flg+") union " +srchQ2+ " "+srchQ +"   from gt_srch_multi a " ;
              String isFlgOD = util.nvl((String)req.getAttribute("flgORDER"));
              if(isFlgOD.equals("Y"))
                  rsltQ = rsltQ+  " order by flg , 1, 2,3,4";
            else
                rsltQ = rsltQ+  " order by 1, 2,3,4";            

          ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            String  srchRefVal = util.nvl((String)req.getAttribute("srchRefVal"));
            pktListMap = new HashMap();
            try {
                while (rs.next()) {
                    String vnm =util.nvl(rs.getString("vnm"));
                    String tfl3 =util.nvl(rs.getString("rmk"));
                    String cert_No = util.nvl(rs.getString("cert_no"));
                    String prte = util.nvl(rs.getString("prte"));
                    String stkIdn = util.nvl(rs.getString("stk_idn"));
                    if(isSrchRef){
                        
                        if(srchRef.equals("vnm")){
                            if(srchRefVal.indexOf(vnm)!=-1 ){
                              srchRefVal = srchRefVal.replaceAll("'"+vnm+"'","");
                            }else  if(srchRefVal.indexOf(tfl3)!=-1 ){
                                srchRefVal = srchRefVal.replaceAll("'"+tfl3+"'","");
                            }
                         }
                        if(srchRef.equals("cert_no")){
                            if(srchRefVal.indexOf(cert_No)!=-1)
                                srchRefVal = srchRefVal.replaceAll("'"+cert_No+"'","");
                                
                     }
                    }
                    if(vnmLst.indexOf(tfl3)!=-1 && !tfl3.equals("")){
                        if(vnmLst.indexOf("'")==-1)
                            vnmLst =  vnmLst.replaceAll(tfl3,"");
                        else
                            vnmLst =  vnmLst.replaceAll("'"+tfl3+"'", "");
                    } else if(vnmLst.indexOf(vnm)!=-1 && !vnm.equals("")){
                        if(vnmLst.indexOf("'")==-1)
                            vnmLst =  vnmLst.replaceAll(vnm,"");
                        else
                            vnmLst =  vnmLst.replaceAll("'"+vnm+"'", "");
                    }  
                    pktPrpMap = new HashMap();
                    pktPrpMap=(noGtpktListMap.get(stkIdn) == null)?new HashMap():(HashMap)noGtpktListMap.get(stkIdn);
                    pktPrpMap.put("flg", util.nvl(rs.getString("flg")));
                    pktPrpMap.put("stt", util.nvl(rs.getString("dsp_stt")));
                    pktPrpMap.put("stk_idn",stkIdn);
                    pktPrpMap.put("vnm",vnm);
                    pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
                    pktPrpMap.put("class",util.nvl(rs.getString("class")));
                    pktPrpMap.put("cts", util.nvl(rs.getString("cts")));
                    pktPrpMap.put("cert_no",cert_No);
                    pktPrpMap.put("stt1",util.nvl(rs.getString("stt1")));
                    pktPrpMap.put("quot",util.nvl(rs.getString("rte")));
                    pktPrpMap.put("fquot",util.nvl(rs.getString("fquot")));
                    pktPrpMap.put("cmp",util.nvl(rs.getString("cmp")));
                    pktPrpMap.put("cmp_dis",util.nvl(rs.getString("cmp_dis")));
                    pktPrpMap.put("rap_rte",util.nvl(rs.getString("rap_rte")));
                    pktPrpMap.put("r_dis",util.nvl(rs.getString("r_dis")));
                    pktPrpMap.put("cert_lab", util.nvl(rs.getString("cert_lab")));
                    pktPrpMap.put("amt", util.nvl(rs.getString("amt")));
                    pktPrpMap.put("cpTotal", util.nvl(rs.getString("cptotal")));
                    pktPrpMap.put("cpDis", util.nvl(rs.getString("cpdis")));
                    if(vwPrpLst.indexOf("CRTWT")!=-1)
                    pktPrpMap.put("CRTWT", util.nvl(rs.getString("cts"))); 
                    
                    if(vwPrpLst.indexOf("RAP_DIS")!=-1)
                    pktPrpMap.put("RAP_DIS", util.nvl(rs.getString("r_dis")));
                    
                    if(vwPrpLst.indexOf("RAP_RTE")!=-1)
                    pktPrpMap.put("RAP_RTE", util.nvl(rs.getString("rap_rte")));
                    
                    if(vwPrpLst.indexOf("RTE")!=-1)
                    pktPrpMap.put("RTE", util.nvl(rs.getString("rte")));
                    
                    
                    if(vwPrpLst.indexOf("CP")!=-1){
                        pktPrpMap.put("cpRte", util.nvl(rs.getString("prte")));
                        pktPrpMap.put("CP", util.nvl(rs.getString("prte")));
                    }
                    if(vwPrpLst.indexOf("CP_DIS")!=-1){
                        pktPrpMap.put("CP_DIS", util.nvl(rs.getString("cpdis")));
                    }
                    if(vwPrpLst.indexOf("CMP_DIS")!=-1){
                    String val = util.nvl((String)pktPrpMap.get("CMP_DIS"));
                    if(val.equals(""))
                    pktPrpMap.put("CMP_DIS", util.nvl(rs.getString("cmp_dis")));
                    }
                    pktListMap.put(stkIdn , pktPrpMap);
                    pktStkIdnList.add(stkIdn);
                }
                rs.close(); pst.close();
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
            if(isSrchRef){
                srchRefVal = srchRefVal.replaceAll(",", "");       
            if(!srchRefVal.equals("")){
         
                if(srchRef.equals("vnm")){
                    if(!srchRefVal.equals("")){
                       srchRefVal = util.pktNotFound(srchRefVal);
                        req.setAttribute("vnmNotFnd", srchRefVal);
                    }
                }
                if(srchRef.equals("cert_no")){
                 srchRefVal += " :-certificate numbers not found.";
                                    
            req.setAttribute("vnmNotFnd", srchRefVal);
                }
            }
            }
           
           req.setAttribute("pktStkIdnList", pktStkIdnList);
      }
            return pktListMap;
    }
    public HashMap NoGtPop(HttpServletRequest req,HttpServletResponse res,String display, String mdl,String client){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      HashMap noGtpktListMap = new HashMap(),pktPrpDtl = new HashMap();
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
   
    ArrayList params = new ArrayList();
    ARRAY resultArray = null;
    String lstIdn = "";
    String prpQ = "DP_STK_DTL_BC(pMdl => ?, pDsp => ?, pData => ?)";
    params.add(mdl);
    params.add(display);
    ArrayList outParams;
    outParams = new ArrayList();
    outParams.add("SD");
    CallableStatement cst = db.execCallNoGt("prpLstQ", prpQ, params, outParams);
    try {
        resultArray = (ARRAY)cst.getArray(params.size()+1);
      cst.close();
      cst=null;
        Object[] resultStructArray = (Object[])resultArray.getArray();
        int resultStructArraysz = resultStructArray.length ;
        for(int rows=0; rows < resultStructArraysz; rows++) {
            STRUCT resultElement = (STRUCT)resultStructArray[rows];
            Object[] attr = (Object[])resultElement.getAttributes();
            String idn = String.valueOf(resultElement.getAttributes()[0]);
            if(!(idn.equals(lstIdn))) {
                if((pktPrpDtl != null) && (pktPrpDtl.size() > 0)) {
                    noGtpktListMap.put(lstIdn, pktPrpDtl);
                }
                lstIdn = idn ;
                pktPrpDtl = new HashMap(); 
            }
            String mprp = String.valueOf(resultElement.getAttributes()[2]);
            String val = String.valueOf(resultElement.getAttributes()[3]);
            pktPrpDtl.put(mprp, val);
        }
        noGtpktListMap.put(lstIdn, pktPrpDtl);
    } catch (SQLException e) {
            e.printStackTrace();
    }
      }
    return noGtpktListMap;
    }
    
    public ArrayList DataColloction(HttpServletRequest req,HttpServletResponse res,HashMap dtlMap){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        HashMap noGtpktListMap = new HashMap(),pktPrpDtl = new HashMap();
        ArrayList pktDtl = new ArrayList();
        if(info!=null){
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        
        ArrayList vnmLstdata = (ArrayList)dtlMap.get("vnm");
  
      
        if(vnmLstdata!=null){
            int ct =db.execUpd(" Del Old Pkts ", "Delete from gt_pkt_scan", new ArrayList());
            String vnmStr = vnmLstdata.toString();
            vnmStr =  vnmStr.replace("[","");
            vnmStr =  vnmStr.replace("]","");
            vnmStr =  vnmStr.replace(" ","");
            String mdl = (String)dtlMap.get("mdl");
            HashMap viewMap = ViewPrpLst(req,mdl);
            String pvPrpStr = (String)viewMap.get("STR");
            ArrayList pvPrpLst = (ArrayList)viewMap.get("PVLIST");
            ArrayList lprpLst = (ArrayList)viewMap.get("LPRPLIST");
            HashMap dbinfo = info.getDmbsInfoLst();
            int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
            
            String[] vnmLst = vnmStr.split(",");
            int loopCnt = 1 ;
            float loops = ((float)vnmLst.length)/stepCnt;
            loopCnt = Math.round(loops);
               if(vnmLst.length <= stepCnt || new Float(loopCnt)>=loops) {
                
            } else
                loopCnt += 1 ;
            if(loopCnt==0)
                loopCnt += 1 ;
            int fromLoc = 0 ;
            int toLoc = 0 ;
            for(int i=1; i <= loopCnt; i++) {
                
              int aryLoc = Math.min(i*stepCnt, vnmLst.length) ;
              
              String lookupVnm = vnmLst[aryLoc-1];
                   if(i == 1)
                       fromLoc = 0 ;
                   else
                       fromLoc = toLoc+1;
                   
                   toLoc = Math.min(vnmStr.lastIndexOf(lookupVnm) + lookupVnm.length(), vnmStr.length());
                   String vnmSub = vnmStr.substring(fromLoc, toLoc);
                    if(!vnmSub.equals("")){
            vnmSub="'"+vnmSub+"'";
            ArrayList params = new ArrayList();
            String insScanPkt = " insert into gt_pkt_scan select * from TABLE(PARSE_TO_TBL("+vnmSub+"))";
            ct = db.execDirUpd(" ins scan", insScanPkt,params);
            System.out.println(insScanPkt);  
                }
            }
            
           String sql ="with STKDTL as  ( Select b.sk1, b.pkt_ty,b.stt,b.qty,b.idn, b.rap_rte rapRte ,b.vnm, nvl(nvl(nvl(st.txt,st.dte),st.num),st.prt1) atr ,st.mprp   "+
                    " from stk_dtl st, mstk b,gt_pkt_scan gt "+   
                   " where st.mstk_idn=b.idn and b.vnm = gt.vnm and  b.pkt_ty in ('NR')"+
                   " and exists (select 1 from rep_prp rp where rp.MDL = '"+mdl+"' and rp.flg <> 'N' and st.mprp = rp.prp)  And st.Grp = 1 )"+
                   " Select * from stkDtl PIVOT ( max(atr)  for mprp  in ( "+pvPrpStr+" ) ) "+
                   " order by 1 " ;
                try {
                    ArrayList rsLst = db.execSqlLst("stkDtl", sql, new ArrayList());
                    PreparedStatement pStmt = (PreparedStatement)rsLst.get(0);
                    ResultSet rs = (ResultSet)rsLst.get(1);
                    while (rs.next()) {
                        GtPktDtl pkts = new GtPktDtl();
                        pkts.setPktIdn(rs.getLong("idn"));
                        pkts.setValue("VNM", rs.getString("vnm"));
                        pkts.setValue("QTY", rs.getString("qty"));
                        for(int i=0;i<pvPrpLst.size();i++){
                            String lprp = (String)pvPrpLst.get(i);
                            String lprpKey = (String)lprpLst.get(i);
                            pkts.setValue(lprpKey, util.nvl(rs.getString(lprp)));
                        }
                        pkts.setValue("RAP_RTE", rs.getString("rapRte"));
                      pktDtl.add(pkts);
                    }
                    rs.close(); 
                    pStmt.close();
                } catch (SQLException sqle) {
                    // TODO: Add catch code
                    sqle.printStackTrace();
                }
        }

        
        
        }
        return pktDtl;
    }
    
    public   HashMap ViewPrpLst(HttpServletRequest req,String mdl) {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr();
                HashMap viewMap = new HashMap();
            if(info!=null){
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            
           
            ArrayList pvlprpLst = new ArrayList();
            ArrayList lprpLst = new ArrayList();
            String mprpStr = "";
              String mdlPrpsQ = " Select Decode ( Rank() Over ( Order By Srt),1,' ',',')||''''||Prp||''''||' '||\n" + 
                      "Upper(replace(replace(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1'),'CERT NO.','CERT'),' ','_')) \n" + 
                      "str , Upper(replace(replace(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1'),'CERT NO.','CERT'),' ','_')) prp , rp.prp lprp From Rep_Prp Rp Where rp.MDL = ? and flg <> 'N' order by srt " ;
                ArrayList  ary = new ArrayList();
                ary.add(mdl);
                  try {
                ArrayList outLst = db.execSqlLst("mprp ", mdlPrpsQ, ary);
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
                while (rs.next()) {
                    String val = util.nvl((String)rs.getString("str"));
                    mprpStr = mprpStr + " " + val;
                    String prp = util.nvl((String)rs.getString("prp"));
                    String lprp = util.nvl((String)rs.getString("lprp"));
                    pvlprpLst.add(prp);
                    lprpLst.add(lprp);
                }
                rs.close(); pst.close();
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
                      viewMap.put("STR", mprpStr);
                      viewMap.put("PVLIST", pvlprpLst);
                      viewMap.put("LPRPLIST",lprpLst);
          

        }
                return viewMap;
            }
    public HashMap graphPath(HttpServletRequest req,HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      HashMap graphPath = (session.getAttribute("graphPath") == null)?new HashMap():(HashMap)session.getAttribute("graphPath");

      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
   
    try {
    if(graphPath.size() == 0) {
    HashMap graphPathDtl=new HashMap();
    String gtView = "select chr_fr, chr_to , dsc , dta_typ from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " +
    " b.mdl = 'JFLEX' and b.nme_rule = 'GRAPH_PATH' and a.til_dte is null order by a.srt_fr ";

        ArrayList outLst = db.execSqlLst("gtView", gtView, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
    while (rs.next()) {
    graphPathDtl=new HashMap();
    graphPathDtl.put("DIR",util.nvl(rs.getString("chr_fr")));
    graphPathDtl.put("PATH",util.nvl(rs.getString("chr_to")));
    graphPath.put(util.nvl(rs.getString("dsc")), graphPathDtl);
    }
    rs.close(); pst.close();
    session.setAttribute("graphPath", graphPath);
    }
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }
      }
    return graphPath;
    }
    
    
    
    public void CeatePdf(HttpServletRequest req,HttpServletResponse res,String URLLink, String fileNme){
         HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        if(info!=null){
            try {
                System.out.print(URLLink);
                System.out.print(fileNme);
                URL url = new URL(URLLink);
                byte[] ba1 = new byte[1024];
                int baLength;
                FileOutputStream fos1 = new FileOutputStream(fileNme);

                try {
                    // Contacting the URL
                    System.out.print("Connecting to " + url.toString() +
                                     " ... ");
                    URLConnection urlConn = url.openConnection();

                    // Checking whether the URL contains a PDF

                    try {

                        // Read the PDF from the URL and save to a local file
                        InputStream is1 = url.openStream();
                        while ((baLength = is1.read(ba1)) != -1) {
                            fos1.write(ba1, 0, baLength);
                        }
                        fos1.flush();
                        fos1.close();
                        is1.close();

                        // Load the PDF document and display its page count

                    } catch (ConnectException ce) {
                        System.out.println("FAILED.\n[" + ce.getMessage() +
                                           "]\n");
                    }


                } catch (NullPointerException npe) {
                    System.out.println("FAILED.\n[" + npe.getMessage() +
                                       "]\n");
                }
            } catch (MalformedURLException murle) {
                // TODO: Add catch code
                murle.printStackTrace();
            } catch (FileNotFoundException fnfe) {
                // TODO: Add catch code
                fnfe.printStackTrace();
            } catch (IOException ioe) {
                // TODO: Add catch code
                ioe.printStackTrace();
            } finally {

            }
      }}
    
    
    
    
//  public void init(HttpServletRequest req , HttpServletResponse res,HttpSession session,DBUtil util) {
//        String rtnPg="sucess";
//        String invalide="";
//        String connExists=util.nvl(util.getConnExists());
//      try {
//          if (!connExists.equals("N"))
//              invalide = util.nvl(util.chkTimeOut(), "N");
//          if (session.isNew())
//              res.sendRedirect("../error_page.jsp");
//          if (connExists.equals("N"))
//              res.sendRedirect("../error_page.jsp?connExists=N");
//          if (invalide.equals("Y"))
//              res.sendRedirect("../loginInvalid.jsp");
//          if (rtnPg.equals("sucess")) {
//              boolean sout = util.getLoginsession(req, res, session.getId());
//              if (!sout) {
//                res.sendRedirect("../error_page.jsp");
//                  System.out.print("New Session Id :=" + session.getId());
//              } else {
//                  util.updAccessLog(req, res, "Generic Impl","init");
//              }
//          }
//      } catch (IOException ioe) {
//          // TODO: Add catch code
//          ioe.printStackTrace();
//      }
//      
//     }
    public GenericImpl() {
        super();
    }
}
