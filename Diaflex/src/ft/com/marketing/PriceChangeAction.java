package ft.com.marketing;

//~--- non-JDK imports --------------------------------------------------------

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.GetPktPrice;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.dao.ByrDao;
import ft.com.dao.PktDtl;

import ft.com.generic.GenericImpl;

import ft.com.generic.GenericInterface;
import ft.com.pricing.RepriceFrm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import java.io.OutputStream;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.text.DecimalFormat;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.StringTokenizer;

import org.apache.struts.actions.DispatchAction;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;

import java.util.Enumeration;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class PriceChangeAction extends DispatchAction {
    public PriceChangeAction() {
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
        PriceChangeForm udf = (PriceChangeForm)af;
            SearchQuery query = new SearchQuery();
          util.updAccessLog(req,res,"Price Change", "Price Change load");
        udf.reset();
        String premiumLnk = util.nvl(req.getParameter("premiumLnk")).trim();
        String typ = util.nvl(req.getParameter("typ")).trim();
        udf.setValue("typ", typ);
            ArrayList byrList = new ArrayList();
            String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
            String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
            String usrFlg=util.nvl((String)info.getUsrFlg());
            ArrayList rolenmLst=(ArrayList)info.getRoleLst();
            String dfNmeIdn=util.nvl((String)info.getDfNmeIdn());
            String conQ="";
            ArrayList ary=new ArrayList();
            if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
            }else if(!dfgrpnmeidn.equals("") && !dfgrpnmeidn.equals("0")){
            conQ +=" and nvl(n.grp_nme_idn,0) =? "; 
            ary.add(dfgrpnmeidn);
            } 
            if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
                    if(util.nvl((String)info.getDmbsInfoLst().get("EMP_LOCK")).equals("Y")){
                        conQ += " and (n.emp_idn= ? or n.emp_idn in(select emp_idn from nme_v where typ='EMPLOYEE' and mgr_idn= ?)) ";  
                        ary.add(dfNmeIdn);
                        ary.add(dfNmeIdn);
                    }
            }
            
            String    getByr  =
                "With JAN_IS_PNDG_V as (\n" + 
                "select distinct nme_idn from memo_pndg_v  where pkt_ty in ('NR')\n" + 
                ")\n" + 
                "select n.nme_idn,n.nme byr\n" + 
                "from nme_v n, jan_is_pndg_v j\n" + 
                "where n.nme_idn = j.nme_idn \n" +conQ +
                "order by 2";

          ArrayList  outLst = db.execSqlLst("byr", getByr, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);

            while (rs.next()) {
                ByrDao byr = new ByrDao();

                byr.setByrIdn(rs.getInt("nme_idn"));
                byr.setByrNme(rs.getString("byr"));
                byrList.add(byr);
            }
            rs.close();
            pst.close();
            udf.setByrLstFetch(byrList);
            udf.setByrLst(byrList);
        ArrayList memoList = query.getMemoType(req,res);
        udf.setMemoList(memoList);
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("PRICE_CHANGE");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("PRICE_CHANGE");
        allPageDtl.put("PRICE_CHANGE",pageDtl);
        }
        info.setPageDetails(allPageDtl);
        util.updAccessLog(req,res,"Price Change", "Price Change load done");
        udf.setValue("premiumLnk", premiumLnk);
        return am.findForward("load");
        }
    }
    
    public ActionForward pktList(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        PriceChangeForm udf = (PriceChangeForm)af;
        GenericInterface genericInt = new GenericImpl();
        SearchQuery query = new SearchQuery();
        util.updAccessLog(req,res,"Price Change", "Price Change pkt List");
        ArrayList ary = null;
        HashMap dbInfoSys = info.getDmbsInfoLst();
        String cnt = (String)dbInfoSys.get("CNT");
        int stepCnt = Integer.parseInt((String)dbInfoSys.get("STEPCNT"));
        FormFile uploadFile = udf.getLoadOffer();
        boolean isDisUpload=false;
        HashMap fileDataMap = new HashMap();
        if(uploadFile!=null){
            ArrayList dataLst = new ArrayList();
        String fileName = uploadFile.getFileName();
        fileName = fileName.replaceAll(".csv", util.getToDteTime()+".csv");
        if(!fileName.equals("")){
          String path = getServlet().getServletContext().getRealPath("/") + fileName;
        File readFile = new File(path);
        if(!readFile.exists()){
        FileOutputStream fileOutStream = new FileOutputStream(readFile);
        fileOutStream.write(uploadFile.getFileData());
        fileOutStream.flush();
        fileOutStream.close();
        }

        FileReader fileReader = new FileReader(readFile);
        LineNumberReader lnr = new LineNumberReader(fileReader);
        String line = "";
        
        
        while ((line = lnr.readLine()) != null){
            int fileCnt = 0;
            String vnm = "";
            String prc = "";
            String dis = "";
            if((line.length() - (line.replaceAll(",","").length())) == 1)
                line = line + ", ";
            if(line.substring(line.length()-1).equals(","))
                line = line + " ";
                String[] vals = line.split(",");
            dataLst.add(line);
            
           //if(vals.length == 2)
               //vals[2] = "";
           HashMap fileData = new HashMap();
           if(vals.length > 2) {
                vnm = vals[0];
                fileData.put("vnm", vnm);
                prc = vals[1];
                fileData.put("prc", prc);
                dis = vals[2];
                try {
                    dis=String.valueOf(Float.parseFloat(dis)*-1);
                } catch (NumberFormatException nfe) {
                }
                fileData.put("dis", dis);
            }
            
            fileDataMap.put(vnm,fileData);
        }
        fileReader.close();
        int file_idn = 0;
          ArrayList  outLst = db.execSqlLst("loadSQL", "select load_file_seq.nextval from dual", new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
        if(rs.next())
             file_idn = rs.getInt(1);
            rs.close();
            pst.close();
        String mloadInsert =
                "insert into mload_file(load_file_idn , typ , dte , unm , cl_ip) values( ? , 'OFR' , sysdate,?,?)";
           ary = new ArrayList();
           ary.add(String.valueOf(file_idn));
           ary.add(info.getUsr());
           ary.add(req.getRemoteUser());
           int ct = db.execUpd("mload", mloadInsert, ary);
            if (ct > 0) {
           
                if (dataLst!= null || dataLst.size() > 0) {
                    for (int i = 0; i < dataLst.size(); i++) {
                        String linestr = (String)dataLst.get(i);
                        int lineNo = i+1;
                        String loadDtlInsert =
                            "insert into load_file_dtl(load_file_idn , ln_no , dtl , dte) values( ? , ? , ?, sysdate)";
                        ArrayList params = new ArrayList();
                        params.add(String.valueOf(file_idn));
                        params.add(String.valueOf(lineNo));
                        params.add(linestr);
                        ct = db.execUpd("load dtl", loadDtlInsert, params);
                    }
                }
            }
        }
        
        }
        int memoIdn=0;
        String memoIds="";

        String    memoSrchTyp = util.nvl((String) udf.getValue("memoSrch"));
        String    premiumLnk = util.nvl((String) udf.getValue("premiumLnk"));
        String pktId = util.nvl((String)udf.getValue("vnm"));
            if (memoSrchTyp.equals("ByrSrch")) {
                Enumeration reqNme = req.getParameterNames();

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
            } else {    
            memoIds = udf.getMemoIdn();
        }
       
            
            if(memoIds.equals("0") || memoIds.equals("") || memoIds.length() < 2 )
            memoIds=util.nvl((String)req.getParameter("memoId"),"0");
              
            if(memoIds.equals("") || memoIds.equals("0")  || memoIds.length() < 2  )
            memoIds=util.nvl((String)session.getAttribute("memoId"),"0");
            
            if(memoIds.equals("") || memoIds.equals("0")  || memoIds.length() < 2  ){
                if(!pktId.equals("")){
                    String deleteGt1="delete gt_pkt_scan";
                    int  ct1=db.execUpd("delete", deleteGt1, new ArrayList()); 
                    pktId = util.getVnm(pktId);
                    String[] vnmLst = pktId.split(",");
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
                           
                           toLoc = Math.min(pktId.lastIndexOf(lookupVnm) + lookupVnm.length(), pktId.length());
                           String vnmSub = pktId.substring(fromLoc, toLoc);
                        
                    vnmSub = vnmSub.replaceAll(" ", "");
                    ArrayList params = new ArrayList();
                        vnmSub = vnmSub.replaceAll(",", "");
                        vnmSub = vnmSub.replaceAll("''", ",");
                        vnmSub = vnmSub.replaceAll("'", "");
                        
                        if(!vnmSub.equals("")){
                        vnmSub="'"+vnmSub+"'";
                      String insScanPkt = " insert into gt_pkt_scan select * from TABLE(PARSE_TO_TBL("+vnmSub+"))";
                      int ct = db.execDirUpd(" ins scan", insScanPkt,params);
                      System.out.println(insScanPkt);
                        }
                    }    
                }

                  int cnt1=0;
                
                String sttChange ="DECODE (a.typ,'O','IS','K','IS','BID','IS','CS','IS', 'I', 'IS','H','IS', 'E', 'IS', 'WH', 'IS', 'WA', 'IS', 'AP' ) = a.stt " ;
                if(cnt.equalsIgnoreCase("alb")){
                    sttChange ="DECODE (a.typ,'O','IS','K','IS','BID','IS','CS','IS', 'I', 'IS','H','IS', 'E', 'IS', 'WH', 'IS', 'WA', 'IS' ) = a.stt " ;
                }
                
                String checkSql ="select distinct mj.nmerel_idn from jandtl a,mjan mj, mstk b,gt_pkt_scan c\n" + 
                  "where a.idn=mj.idn and a.mstk_idn = b.idn and b.vnm=c.vnm and " +sttChange;
                System.out.println("checkSql"+checkSql);
              ArrayList  outLst = db.execSqlLst("check rel", checkSql, new ArrayList());
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet  rs = (ResultSet)outLst.get(1);
                while(rs.next()){
                    cnt1++;
                }
                rs.close();
                pst.close();
                if(cnt1==1){ 
                  String findMaxQ="select distinct a.idn\n" + 
                  "from jandtl a,mjan mj, mstk b,gt_pkt_scan c\n" + 
                  "where a.idn=mj.idn and a.mstk_idn = b.idn and b.vnm=c.vnm and " +sttChange;
                  
                    System.out.println("findMaxQ"+findMaxQ);
                  ArrayList  outLst1 = db.execSqlLst(" Memo Info", findMaxQ, new ArrayList());
                  PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
                  ResultSet  rs1 = (ResultSet)outLst1.get(1);
                  while (rs1.next()) {
                      String mIdn=rs1.getString("idn");
                      if(memoIds.equals(""))
                      memoIds=mIdn;
                      else
                      memoIds+=","+mIdn;   
                  }
                  rs1.close();
                  pst1.close();
                }
            }
            
                 String deleteGt="delete gt_srch_rslt";
                 int  ct=db.execUpd("delete", deleteGt, new ArrayList()); 
                
                 deleteGt="delete gt_memo_pri_chng";
                 ct=db.execUpd("delete", deleteGt, new ArrayList()); 
            
          String[] memoIdLst = memoIds.split(",");
          if(memoIdLst!=null && memoIdLst.length>0){
          for(int j=0; j <memoIdLst.length; j++) {    
         String memoId=util.nvl(memoIdLst[j]);
         
            
        ary = new ArrayList();
        ary.add(memoId.trim());
        ary.add("PRICE_CHNG");
         ct = db.execCall("loadMemoData", "memo_pkg.Load_Memo_Data(pIdn => ?, pMdl => ?)", ary);
      }
      }
        if(!pktId.equals("")){
            ct = db.execDirUpd(" update gt", "update gt_srch_rslt a set a.flg='ND' where exists (select 1 from gt_pkt_scan b where a.vnm=b.vnm) ",new ArrayList());
            String deleteSql="delete gt_srch_rslt where flg not in('ND')";
            ct=db.execUpd("delete", deleteSql, new ArrayList()); 
            deleteSql = "delete gt_memo_pri_chng a where not exists(select b.stk_idn from gt_srch_rslt b where b.stk_idn=a.mstk_idn)";
            ct=db.execUpd("delete", deleteSql, new ArrayList()); 
            deleteSql = "update gt_srch_rslt set flg='Z'";
            ct=db.execUpd("delete", deleteSql, new ArrayList()); 
        }
        String  memoIdns = util.getVnm(memoIds);  
        String typ = util.nvl((String)udf.getValue("typ"));
        ArrayList pkts  = new ArrayList();
        ArrayList memoPktList =genericInt.genericPrprVw(req, res, "PRICE_CHNG", "PRICE_CHNG");
        String cpPrp = "prte";
            if(memoPktList.contains("CP"))
            cpPrp =  util.prpsrtcolumn("srt", memoPktList.indexOf("CP")+1);
        String getMemoInfo = "select b.byr , b.trm , a.exh_rte , count(*) qty , b.nme_idn , b.nmerel_idn  , trunc(sum(trunc(a.cts,2)),2) cts ,"+
       " trunc(sum(a.quot_vlu)/sum(trunc(a.cts,2)),2) avg , sum(rap_vlu) rapVlu ,sum(a.quot_vlu) ttlVlu , trunc((sum(quot_vlu/nvl(a.exh_rte,1))/sum(rap_vlu)*100) - 100, 4) avg_dis , "+
         " trunc(sum(a.mod_diff*a.cts)/sum(trunc(a.cts,2)),2) avgOrg , sum(a.mod_diff * a.cts) ttlOrgVlu , trunc((sum((a.mod_diff * a.cts)/nvl(a.exh_rte,1))/sum(rap_vlu)*100) - 100, 4) org_avg_dis "+ 
       " from gt_memo_pri_chng a , jan_v b "+
       " where a.memo_id = b.idn and b.idn in("+memoIdns+") group by b.byr, b.trm , b.nme_idn , b.nmerel_idn , a.exh_rte  ";
         ary = new ArrayList();
     
       // ary.add(String.valueOf(memoIdns));

          ArrayList  outLst = db.execSqlLst(" Memo Info", getMemoInfo, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  mrs = (ResultSet)outLst.get(1);

        if (mrs.next()) {
            udf.setNmeIdn(mrs.getInt("nme_idn"));
            udf.setRelIdn(mrs.getInt("nmerel_idn"));
            udf.setByr(util.nvl(mrs.getString("byr")));
            udf.setQty(util.nvl(mrs.getString("qty")));
            udf.setCts(util.nvl(mrs.getString("cts")));
            udf.setRapVlu(util.nvl(mrs.getString("rapVlu")));
            udf.setAvgDis(util.nvl(mrs.getString("avg_dis")));
            udf.setVlu(util.nvl(mrs.getString("ttlVlu")));
            udf.setAvg(util.nvl(mrs.getString("avg")));
            udf.setValue("avgOrg", util.nvl(mrs.getString("avgOrg")));
            udf.setValue("ttlOrgVlu", util.nvl(mrs.getString("ttlOrgVlu")));
            udf.setValue("org_avg_dis", util.nvl(mrs.getString("org_avg_dis")));
            udf.setValue("exhRte", util.nvl(mrs.getString("exh_rte")));
            req.setAttribute("exhRte", util.nvl(mrs.getString("exh_rte")));
            udf.setMemoIdnData(memoIds);
            udf.setMemoIdn(memoIds);
                String orderbyconQ=" b.sk1";
                if(cnt.equals("alb"))
                    orderbyconQ=" b.vnm";
                String getPktData ="select b.sk1,a.mstk_idn ,a.memo_id, b.vnm ,b.rmk,b.cmp,b.img, a.quot memoQuot , b.quot ,a.exh_rte, trunc(a.quot/nvl(a.exh_rte,1),2) byrQuot , a.cts , " +
                    " a.rte , a.rap_rte , b.stt , a.rap_dis , a.byr_dis , a.rap_vlu , a.quot_vlu,nvl("+cpPrp+",prte) prte,trunc(((greatest(nvl("+cpPrp+",prte),1)*100)/greatest(b.rap_rte,1)) - 100,2) cpdis,rchk,decode(rchk,null,null,'color:blue') color " ;
                for (int i = 0; i < memoPktList.size(); i++) {
                    String fld = "prp_";
                    int j = i + 1;
                    if (j < 10)
                        fld += "00" + j;
                    else if (j < 100)
                        fld += "0" + j;
                    else if (j > 100)
                        fld += j;
                    getPktData+= ", " + fld;
                }
                
                 getPktData+=" from gt_memo_pri_chng a , gt_srch_rslt b  " + 
                " where a.mstk_idn = b.stk_idn  order by "+orderbyconQ;
                
              ArrayList  outLst1 = db.execSqlLst(" memo pkts", getPktData, new ArrayList());
              PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
              ResultSet  rs = (ResultSet)outLst1.get(1);
                while (rs.next()) {
                    PktDtl pkt    = new PktDtl();
                    long   pktIdn = rs.getLong("mstk_idn");
                    String vnm = util.nvl(rs.getString("vnm"));
                    String quot = util.nvl(rs.getString("quot"));
                     String exh_rte = util.nvl(rs.getString("exh_rte"),"1");
                    pkt.setPktIdn(pktIdn);
                    pkt.setRapRte(util.nvl(rs.getString("rap_rte")));
                    pkt.setCts(util.nvl(rs.getString("cts")));
                    pkt.setRte(util.nvl(rs.getString("rte")));
                    pkt.setMemoQuot(util.nvl(rs.getString("memoQuot")));
                    pkt.setByrRte(quot);
                    pkt.setFnlRte(util.nvl(rs.getString("quot")));
                    pkt.setValue("quot", util.nvl(rs.getString("quot")));
                    pkt.setDis(util.nvl((String)rs.getString("rap_dis")));
                    pkt.setByrDis(util.nvl((String)rs.getString("byr_dis")));
                    pkt.setVnm(vnm);
                    pkt.setValue("byrQuot",util.nvl(rs.getString("byrQuot")));
                    pkt.setValue("cpDis", util.nvl(rs.getString("cpdis")));
                    pkt.setValue("rapVal",util.nvl(rs.getString("rap_vlu")));
                    pkt.setValue("val", util.nvl(rs.getString("quot_vlu")));
                    pkt.setValue("memo_id", util.nvl(rs.getString("memo_id")));
                    pkt.setValue("color", util.nvl(rs.getString("color")));
                    pkt.setValue("oldRte", util.nvl(rs.getString("rchk")));
                    pkt.setValue("oldQuot", util.nvl(rs.getString("cmp")));
                    pkt.setValue("updby", util.nvl(rs.getString("img")));
                    String tfl3 =util.nvl(rs.getString("rmk"));
                    if(!pktId.equals("")){
                        if(pktId.indexOf(tfl3)!=-1 && !tfl3.equals("")){
                            if(pktId.indexOf("'")==-1)
                                pktId =  pktId.replaceAll(tfl3,"");
                            else
                                pktId =  pktId.replaceAll("'"+tfl3+"'", "");
                        } else if(pktId.indexOf(vnm)!=-1 && !vnm.equals("")){
                            if(pktId.indexOf("'")==-1)
                                pktId =  pktId.replaceAll(vnm,"");
                            else
                                pktId =  pktId.replaceAll("'"+vnm+"'", "");
                                pktId =  pktId.replaceAll(vnm, "");
                        }  
                    }
                    String lStt = rs.getString("stt");

                    pkt.setStt(lStt);
                    udf.setValue("stt_" + pktIdn, lStt);
                    if(!typ.equals("BID")){
                    udf.setValue("nwprice_"+pktIdn, util.nvl(rs.getString("quot")));
                    udf.setValue("nwdis_"+pktIdn, util.nvl(rs.getString("byr_dis")));
                    udf.setValue("rapVal_"+pktIdn, util.nvl(rs.getString("rap_vlu")));
                    udf.setValue("val_"+pktIdn, util.nvl(rs.getString("quot_vlu")));
                    }
                    for (int i = 0; i < memoPktList.size(); i++) {
                        String prp = (String)memoPktList.get(i);
                        String fld = "prp_";
                        int j = i + 1;
                        if (j < 10)
                            fld += "00" + j;
                        else if (j < 100)
                            fld += "0" + j;
                        else if (j > 100)
                            fld += j;
                        String lVal = util.nvl(rs.getString(fld));
                        if(prp.equals("CP")){
                            lVal = util.nvl(rs.getString("prte"));
                        }
                        if(prp.equals("CP_DIS")){
                            lVal = util.nvl(rs.getString("cpdis"));
                        }
                        pkt.setValue(prp, lVal);
                    }
                    pkts.add(pkt);
                    HashMap fileDate = (HashMap)fileDataMap.get(vnm);
                    if(fileDate!=null && fileDate.size()>0){
                    double quotdouble = Double.parseDouble(quot);
                    String rapDis = rs.getString("rap_dis");
                    int rapRte = rs.getInt("rap_rte");
                    String price = util.nvl((String)fileDate.get("prc")).trim();
                    String dis = util.nvl((String)fileDate.get("dis")).trim();
                        double fQuot = quotdouble ;
                        String fDis = rapDis ;
                        if(!price.equals("")) {
                            fQuot = Double.parseDouble(price) ; 
                            fQuot=get2Decimal(fQuot*Double.parseDouble(exh_rte));
                            if(rapRte > 1)
                            fDis = String.valueOf(((fQuot/Double.parseDouble(exh_rte)/rapRte*100)- 100));
                        }
                        if(dis.length() > 0) {
                            fQuot = get2Decimal((rapRte * (100 - Double.parseDouble(dis))/100)*Double.parseDouble(exh_rte));
                            fDis = String.valueOf(dis);
                        }
                    udf.setValue("typ_"+String.valueOf(pktIdn), "FIX");
                    udf.setValue(String.valueOf(pktIdn), "Yes");
                    String prc = util.nvl((String)fileDate.get("prc"));
                    fileDate.put("dis",String.valueOf(fDis));
                    fileDate.put("prc",String.valueOf(fQuot));
                    fileDataMap.put(vnm, fileDate);
                    }
                }
                rs.close();
                pst1.close();
                if(!pktId.equals("")){
                    pktId = util.pktNotFound(pktId);
                    req.setAttribute("vnmNotFnd", pktId);
                }
            }
        mrs.close();
        pst.close();
            ArrayList byrList = new ArrayList();
            String    getByr  =
                "select nme_idn, nme  byr from nme_v a " + " where nme_idn = ? "
                + " or exists (select 1 from nme_grp_dtl c, nme_grp b where a.nme_idn = c.nme_idn and b.nme_grp_idn = c.nme_grp_idn "
                + " and b.nme_idn = ? and b.typ = 'BUYER' and c.vld_dte is null) " + " order by 2 ";

            ary  = new ArrayList();
            ary.add(String.valueOf(udf.getNmeIdn()));
            ary.add(String.valueOf(udf.getNmeIdn()));
           outLst = db.execSqlLst("byr", getByr, ary);
          pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);

            while (rs.next()) {
                ByrDao byr = new ByrDao();

                byr.setByrIdn(rs.getInt("nme_idn"));

                String nme = util.nvl(rs.getString("byr"));

               

                byr.setByrNme(nme);
                byrList.add(byr);
            }
            rs.close();
            pst.close();
        if(typ.equals("BID")){
            String offRteSql = "select b.mstk_idn , a.ofr_rte , a.ofr_dis , a.chg_typ , to_char( a.to_dt ,'DD-MM-YYYY ') to_dt , a.diff " +
                "  from web_bid_wl a , jandtl b where b.idn in("+memoIdns+") and a.mstk_idn = b.mstk_idn and rel_idn = ? and  trunc(nvl(a.to_dt, sysdate)) >= trunc(sysdate) and a.typ='BID' and a.stt='A'";
             ary = new ArrayList();
           //  ary.add(String.valueOf(memoIdns));
             ary.add(String.valueOf(udf.getRelIdn()));
          outLst = db.execSqlLst("offerRte", offRteSql, ary);
          pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);

            while(rs.next()){
                String stk_idn = rs.getString("mstk_idn");
                udf.setValue("nwprice_"+stk_idn, rs.getString("ofr_rte"));
                udf.setValue("nwdis_"+stk_idn, rs.getString("ofr_dis"));
                udf.setValue("typ_"+stk_idn, rs.getString("chg_typ"));
                udf.setValue("chng_"+stk_idn, rs.getString("diff"));
                udf.setValue("dte_"+stk_idn, rs.getString("to_dt"));
            }
            rs.close();
        }
        if(cnt.equals("ri")){
            String cpQ = "select trunc(sum("+cpPrp+"*cts)/sum(cts),2) avg,\n" + 
            "trunc(((sum(cts*"+cpPrp+") / sum(cts* greatest(rap_rte,1) ))*100) - 100, 2) avg_dis  from gt_srch_rslt";
            rs = db.execSql("cpQ", cpQ, new ArrayList());
          outLst = db.execSqlLst("cpQ", cpQ, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);

            while(rs.next()){
                udf.setValue("cp", rs.getString("avg"));
                udf.setValue("cpdis", rs.getString("avg_dis"));
            }
            rs.close();   
            pst.close();
        }
        req.setAttribute("fileDataLst", fileDataMap);
        udf.setPkts(pkts);
        udf.setView("yes");
        udf.setTyp(typ);
        info.setValue(memoIdn + "_PKTS", pkts);
      
        info.setValue("PKTS", pkts);
            
            ArrayList  trmList = query.getTerm(req,res, udf.getNmeIdn());
            ArrayList memoList = query.getMemoType(req,res);
            udf.setMemoList(memoList);
            udf.setByrLstFetch(byrList);
            udf.setByrLst(byrList);
            udf.setTrmsLst(trmList);
            udf.setValue("byrIdn", udf.getNmeIdn());
            udf.setValue("byrTrm", udf.getRelIdn());
            
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("PRICE_CHANGE");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("PRICE_CHANGE");
            allPageDtl.put("PRICE_CHANGE",pageDtl);
            }
            info.setPageDetails(allPageDtl);
          util.updAccessLog(req,res,"Price Change", "Price Change pkt List size "+pkts.size());
          udf.setValue("premiumLnk", premiumLnk);
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
        PriceChangeForm udf = (PriceChangeForm)af;
           util.updAccessLog(req,res,"Price Change", "Price Change Save");
        ArrayList  pkts = (ArrayList) info.getValue("PKTS");
         String typ = util.nvl((String)udf.getValue("typ"));
         String    premiumLnk = util.nvl((String) udf.getValue("premiumLnk"));
         ArrayList ary = new ArrayList();
         HashMap dbinfo = info.getDmbsInfoLst();
         String cnt = util.nvl((String)dbinfo.get("CNT"));
          ArrayList memoList = new ArrayList();
         if(typ.equals("BID")){
             ArrayList msgList =new ArrayList();
             for (int i = 0; i < pkts.size(); i++) {
                 PktDtl pkt     = (PktDtl) pkts.get(i);
                 long   lPktIdn = pkt.getPktIdn();
                 String pktIdn = String.valueOf(lPktIdn);
                 String memoIdn =String.valueOf(udf.getMemoIdn());
                 String prcVal = (String)udf.getValue("nwprice_"+pktIdn);
                 String prcDis = (String)udf.getValue("nwdis_"+pktIdn);
                 String typVal = (String)udf.getValue("typ_"+pktIdn);
                 String chngVal = (String)udf.getValue("chng_"+pktIdn);
                 String dateVal = (String)udf.getValue("dte_"+pktIdn);
                 int relIdn = udf.getRelIdn();
                 String quot = util.nvl(pkt.getByrRte());
                 String check = util.nvl((String)udf.getValue(pktIdn));
                 if(check.equals("Yes")){
                   
                 ary = new ArrayList();
                 ary.add(pktIdn);
                 ary.add(quot);
                 ary.add(prcVal);
                 ary.add(typVal);
                 ary.add(chngVal);
                 ary.add(typVal+"_"+chngVal);
                 ary.add(dateVal);
                 ary.add(String.valueOf(relIdn));
                 ary.add("DF");
                 ArrayList out = new ArrayList();
                 out.add("I");
                 out.add("V");
              
                String addOffer = "web_pkg.bid_add( pStkIdn =>? , pQuot => ? , pOfrRte => ? ,  pChgTyp => ? , pDiff => ?, pRmk => ? , pToDte=> ? , pRlnId => ? , pFlg => ? , pCnt=> ? , pMsg=>? )";
                CallableStatement ct = db.execCall("addOffer", addOffer, ary, out);
                 msgList.add(ct.getString(ary.size()+2));
                 ct.close();
                 }
                 
             }
             udf.setPkts(new ArrayList());
             req.setAttribute("msgList", msgList);
             udf.setValue("typ", typ);
             return am.findForward("load");
         }else{
         for (int i = 0; i < pkts.size(); i++) {
             PktDtl pkt     = (PktDtl) pkts.get(i);
             long   lPktIdn = pkt.getPktIdn();
             String pktIdn = String.valueOf(lPktIdn);
             
              String priceFld = "nwprice_"+pktIdn;
             String memoIdn =(String)pkt.getValue("memo_id");
             String prcVal = (String)udf.getValue(priceFld);
             
             String typVal = (String)udf.getValue("typ_"+pktIdn);
             String chngVal = (String)udf.getValue("chng_"+pktIdn);

             String check = util.nvl((String)udf.getValue(pktIdn));
             if(check.equals("Yes") && !typVal.equals("") && !chngVal.equals("")){
             ary = new ArrayList();
             ary.add(chngVal);
             ary.add(prcVal);
             ary.add(typVal);
             ary.add(pktIdn);
             ary.add(memoIdn);
            
             String updateJandtl = "update jandtl set  pct=? , fnl_sal=? , rmk=? where mstk_idn=? and idn=? ";
             int ct = db.execUpd("update jandtl",updateJandtl,ary);
             if(cnt.equals("ag") && ct > 0){
                 ary = new ArrayList();
                 ary.add(Long.toString(lPktIdn));
                 ary.add("PRI_MOD_LOG");
                 ary.add(typVal+" : "+chngVal);
                 ary.add("T");
                 ary.add("1");
                 String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? , pPrpTyp => ?, pgrp => ? )";
                 ct = db.execCall("stockUpd",stockUpd, ary);  
             }
             }
             if(!memoList.contains(memoIdn))
                 memoList.add(memoIdn);
            
         }      
             String memoStr="";
             if(memoList!=null && memoList.size()>0){
                String idnPcs = memoList.toString();
                idnPcs = idnPcs.replace('[','(');
                idnPcs = idnPcs.replace(']',')'); 
                 memoStr=idnPcs;
             }
               
                String typval = util.nvl((String)udf.getValue("val"));
                String diff = util.nvl((String)udf.getValue("diff"));
                if(typval.equals("XRT") && !diff.equals("")){
                    String updatemjan = "update mjan set  exh_rte=? where idn in "+memoStr;
                    ary = new ArrayList();
                    ary.add(diff);
                   
                   int ct = db.execUpd("update mjan",updatemjan,ary);   
                }   
          req.setAttribute("msg", "Price Change successfully");
              util.updAccessLog(req,res,"Price Change", "Price Change Save done");
          session.setAttribute("memoId", String.valueOf(udf.getMemoIdn()));
          udf.setValue("premiumLnk", premiumLnk);
          if (udf.getValue("saveRedirect")!=null){
             String memotyp="";
             String sqlQ = "select typ from mjan where idn in "+memoStr;
             ary = new ArrayList();
            
             ResultSet rs = db.execSql(" Memo Info", sqlQ , ary);
             if(rs.next()){
                 memotyp=util.nvl((String)rs.getString("typ"));
             }
             rs.close();
             memoStr =memoStr.replace("(", "");
             memoStr =memoStr.replace(")", "");
             req.setAttribute("APP","Y");
             req.setAttribute("memoId",memoStr);
             
             if(memotyp.indexOf("AP") !=-1)
             return am.findForward("sale");
             else if(memotyp.indexOf("CS") !=-1)
             return am.findForward("consignment");
             else
             return am.findForward("rtrn");
         }else    
         return am.findForward("memo");   
            }
         }
     }
    
    public ActionForward priceDtlLoad(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_pridtlGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_pridtlGNCSrch"); 
            info.setGncPrpLst(assortSrchList);
            
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("PRICE_DETAIL");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("PRICE_DETAIL");
            allPageDtl.put("PRICE_DETAIL",pageDtl);
            }
            info.setPageDetails(allPageDtl);
        
            util.updAccessLog(req,res,"Price Change", "Price Change priceDtlLoad");
            
        return am.findForward("prcLoad");
        }
    }
    public ActionForward priceDtl(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Price Change", "Price Change priceDtl");
            ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_pridtlGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_pridtlGNCSrch"); 
            info.setGncPrpLst(genricSrchLst);
            boolean isGencSrch = false;
        PriceChangeForm udf = (PriceChangeForm)af;
        SearchQuery srchQuery=new SearchQuery();
        HashMap dbinfo = info.getDmbsInfoLst();
        int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
        ArrayList params = new ArrayList();
        String stoneId =(String)udf.getValue("vnmLst");
        String memoNo = (String)udf.getValue("memoIdn");
            HashMap mprp = info.getMprp();
            HashMap prp = info.getPrp();
            HashMap paramsMap = new HashMap();
       
        if(stoneId.equals(""))
          stoneId = "0";
        
        if(memoNo.equals(""))
          memoNo = "0" ;
   
         String stt = util.nvl((String)udf.getValue("stt"));
          
            if(stoneId.equals("0") && memoNo.equals("0")){
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
                paramsMap.put("stt", stt);
                paramsMap.put("mdl", "PRC_PRP");
                isGencSrch = true;
                util.genericSrch(paramsMap);
                db.execUpd(" update Pkts ", "update gt_srch_rslt set RCHK='RP' where flg='Z'", new ArrayList());
            }else{
        
                stoneId = util.getVnm(stoneId);       
                memoNo = util.getVnm(memoNo);
       
        String delQ = " Delete from gt_srch_rslt";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        
        ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
        if(stoneId.length() > 3){
   
        String[] vnmLst = stoneId.split(",");
        int loopCnt = 1 ;
        System.out.println(vnmLst.length);
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
              
              toLoc = Math.min(stoneId.lastIndexOf(lookupVnm) + lookupVnm.length(), stoneId.length());
              String vnmSub =stoneId.substring(fromLoc, toLoc);
        
        String srchRefQ =
        "    Insert into gt_srch_rslt ( srch_id, pkt_ty, stk_idn, vnm, qty, cts, pkt_dte, stt,  rap_rte,rap_dis , cert_lab, cert_no, flg, sk1, fquot, quot , cmp,RCHK  ) " +
        "     select  1 srchId , pkt_ty, b.idn, b.vnm, b.qty qty, b.cts cts, b.dte, stt,  rap_rte ,  decode(rap_rte ,'1',null, trunc((nvl(upr,cmp)/rap_rte*100)-100, 2)) " +
        "     , cert_lab, cert_no, 'Z' flg, sk1, upr, upr , cmp,'RP' " +
        "    from mstk b " +
        "     where  pkt_ty  in('NR','PC') " +
          
        "    and ( vnm in ("+vnmSub+") or tfl3 in ("+vnmSub+") )   ";
            ct = db.execUpd(" Srch Ref ", srchRefQ, new ArrayList());
            }}else if(memoNo.length() > 3) {
          String srchRefQ = 
          "    Insert into gt_srch_rslt (srch_id, pkt_ty, stk_idn, vnm, qty, cts, pkt_dte, stt, cmp, rap_rte, rap_dis , cert_lab, cert_no, flg, sk1, fquot, quot,RCHK ) " + 
          "                select 1 srchId , pkt_ty, b.idn, b.vnm, b.qty qty, b.cts cts, b.dte, stt" +
              " , cmp , rap_rte,  decode(rap_rte ,'1',null, trunc((nvl(upr,cmp)/rap_rte*100)-100, 2)) , cert_lab, cert_no, 'Z' flg, sk1, upr, upr,'RP'  " + 
          "                from mstk b where  " + 
          "                pkt_ty = 'NR'" + 
          " and exists (select 1 from jandtl a where a.mstk_idn = b.idn and a.idn in ("+ memoNo + "))";
                  
           ct = db.execUpd(" Srch Ref ", srchRefQ, new ArrayList());
         }else{
      
        if(!stt.equals("")){
            
           String srchRefQ =
            "    Insert into gt_srch_rslt ( srch_id, pkt_ty, stk_idn, vnm, qty, cts, pkt_dte, stt,  rap_rte, cert_lab, cert_no, flg, sk1, fquot, quot , cmp,RCHK ) " +
            "     select  1 srchId , pkt_ty, b.idn, b.vnm, b.qty qty, b.cts cts, b.dte, stt,  rap_rte" +
            "     , cert_lab, cert_no, 'Z' flg, sk1, upr, upr , cmp,'RP'  " +
            "    from mstk b " +
            "     where  pkt_ty = 'NR' " +
              
            "    and stt = ? ";
            params = new ArrayList();
            params.add(stt);
            ct = db.execUpd(" Srch Ref ", srchRefQ, params);
        }
            }
        
        
        //params.add(stoneId);
      
        
       
        //      String calQuot = "pkgmkt.Cal_Quot(?)";
        //      params = new ArrayList();
        //      params.add(Integer.toString(info.getRlnId()));
        //      ct = db.execCall(" Srch calQ ", calQuot, params);
        
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        params = new ArrayList();
        params.add("PRC_PRP");
        ct = db.execCall(" Srch Prp ", pktPrp, params);
            
            }    
        
        String delQ = " Delete from gt_srch_rslt where pkt_ty='MIX'";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        ArrayList prcPrpLst = srchQuery.getPRCPRPMdl(req,res);
        req.setAttribute("vnmLst", stoneId);
        HashMap pktList = srchQuery.SearchResult(req,res,"'Z'",prcPrpLst);
        
        req.setAttribute("pktList", pktList);
     
        return am.findForward("prcLoad");
        }
    }
    
    public ActionForward reprc(ActionMapping mapping,
                                 ActionForm af,
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
            util.updAccessLog(req,res,"Price Change", "Price Change reprc");
        PriceChangeForm udf = (PriceChangeForm)af;
        String delQ = " Delete from gt_pkt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        String insQ = " insert into gt_pkt(mstk_idn) select stk_idn from gt_srch_rslt where flg='S' and RCHK='RP' ";
        ct = db.execUpd(" reprc memo", insQ, new ArrayList());
        if(ct>0){
        HashMap dbinfo = info.getDmbsInfoLst();
       String  jbCntDb = util.nvl((String)dbinfo.get("RPCNT"));
       if(jbCntDb.equals(""))
           jbCntDb="15";
        ArrayList out = new ArrayList();
        out.add("I");
        String reprc = "reprc(num_job => ?, lstt1 => 'AS_PRC', lstt2 => 'FORM',lSeq=> ?)";
        ArrayList reprcParams = new ArrayList();
        int jobCnt = Integer.parseInt(jbCntDb);
        reprcParams.add(String.valueOf(jobCnt));
        int lseq = 0;
        CallableStatement cnt = db.execCall(" reprc : ",reprc, reprcParams,out );
         lseq = cnt.getInt(reprcParams.size()+1);
         req.setAttribute("seqNo","Current Repricing Sequence Number :  "+String.valueOf(lseq));
        }else{
         req.setAttribute("msg","Please select Packet to Reprice");
        }
        return mapping.findForward("prcLoad");
        }
    }
    
    public ActionForward CrtExcel(ActionMapping am,
                                 ActionForm af,
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
            util.updAccessLog(req,res,"Price Change", "Price Change Create excel");
      HashMap dbinfo = info.getDmbsInfoLst();
      String clnt = (String)dbinfo.get("CNT");
      ExcelUtil xlUtil = new ExcelUtil();
      xlUtil.init(db, util, info);
      String CONTENT_TYPE = "getServletContext()/vnd-excel";
      String fileNm = "PricingDetailExcel"+util.getToDteTime()+".xls";
      res.setContentType(CONTENT_TYPE);
      res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
      HSSFWorkbook hwb = null;
      hwb = xlUtil.getInXl("TMKT", req, "PRC_PRP");
      OutputStream out = res.getOutputStream();
      hwb.write(out);
      out.flush();
      out.close();
       
       return null;
        }
    }
    
    
    public ActionForward CrtExcelSheet(ActionMapping am,
                                 ActionForm af,
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
      util.updAccessLog(req,res,"Price Change", "Price Change Create excel");   
      HashMap dbInfoSys = info.getDmbsInfoLst();
      String cnt = (String)dbInfoSys.get("CNT");
      String adv_pri_dtl = util.nvl((String)dbInfoSys.get("ADV_PRI_DTL_SHEET"),"N");
      ArrayList itemHdr = new ArrayList();
      ArrayList pktList = new ArrayList();
      ArrayList prigrpLst = new ArrayList();
      HashMap pktPrpMapPrc = new HashMap();
      HashMap pktListMapPrc = new HashMap(); 
      HashMap<String, HashMap> calcPriceDetails=new HashMap<String, HashMap>();
      HashMap<String, HashMap> priSheets = (HashMap<String, HashMap>)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),cnt+"_getSheets");
      String prvStkIdn = "";  
      int prigrpLstSz=0;
            
      itemHdr.add("Sr No");
      itemHdr.add("Packet Id");
      itemHdr.add("Status");
      
      if(!adv_pri_dtl.equals("Y")){      
      int priUpdate = db.execCall("priUpdate", "Dp_Pop_Pkt_Pri", new ArrayList());
      
      if(priUpdate>0){
      String prisqlQ="select distinct pri_grp from gt_pkt_pri order by pri_grp";
        ArrayList  outLst = db.execSqlLst("gt_pkt_pri", prisqlQ, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet  rs = (ResultSet)outLst.get(1);
      while (rs.next()) {
          prigrpLst.add(util.nvl((String)rs.getString("pri_grp")));
      }       
      rs.close();
      pst.close();
      }  
      
      prigrpLstSz = prigrpLst.size();
        
      String idnprisqlQ="select stk_idn,pri_grp,pct_vlu from gt_pkt_pri order by stk_idn";
        ArrayList  outLst = db.execSqlLst("pct_vlu", idnprisqlQ, new ArrayList());   
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet  rs = (ResultSet)outLst.get(1);
      while (rs.next()) {
      String stkIdn = util.nvl((String)rs.getString("stk_idn"));
      if(prvStkIdn.equals(""))  
          prvStkIdn=stkIdn;
          if(!prvStkIdn.equals(stkIdn))  {
              pktPrpMapPrc.put(prvStkIdn, pktListMapPrc);
              pktListMapPrc= new HashMap();
              prvStkIdn=stkIdn;
          }
              
      pktListMapPrc.put(util.nvl((String)rs.getString("pri_grp")),util.nvl((String)rs.getString("pct_vlu")));
      }       
      rs.close();
      pst.close();
      
      if(!prvStkIdn.equals("")) 
      pktPrpMapPrc.put(prvStkIdn, pktListMapPrc);
      }else{
          GetPktPrice getpkt=new GetPktPrice(req);
          ArrayList stkIdnLst = new ArrayList();
              String idnprisqlQ="select stk_idn from gt_srch_rslt";
        ArrayList  outLst = db.execSqlLst("pct_vlu", idnprisqlQ, new ArrayList()); 
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
         ResultSet  rs = (ResultSet)outLst.get(1);
              while (rs.next()) {
                  stkIdnLst.add(util.nvl((String)rs.getString("stk_idn")));
              }       
              rs.close();
          pst.close();
          calcPriceDetails=getpkt.calcPrice(stkIdnLst,"SL");
          List<String> sheets = new ArrayList<String>(priSheets.keySet());
          int totalSheets = priSheets.size();
          for (int i = 0; i < totalSheets; i++) {
          String nme = (String)sheets.get(i);
          HashMap grpDtl = priSheets.get(nme) == null? new HashMap() : priSheets.get(nme);
          if(grpDtl.get("disTyp").equals("SL"))
          prigrpLst.add(nme); 
          }
      }
            
      SearchQuery srchQuery=new SearchQuery();
      ArrayList prcPrpLst = srchQuery.getPRCPRPMdl(req,res);      
      int prcPrpLstsz=prcPrpLst.size();
        
            
                
        String srchQ = "";
        String srchQ1 = " select sk1, 0 dsp , a.stk_idn , cert_lab , dsp_stt , ";
        String getQuot = "quot rte";
        srchQ += "  cts crtwt, " +
                    " decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, to_char(rap_dis, '90.00')))  r_dis " +
                    " , stk_idn  , vnm, kts_vw kts, cert_lab cert, cert_no, rmk, img,img2, cmp , trunc(((cmp*100)/greatest(rap_rte,1)) - 100,2) cmp_dis ,  to_char(trunc(cts,2),'9990.99') cts, qty , rap_rte , " +
                    getQuot +
                    ", cmnt,stt stt1,  nvl(fquot,0) fquot ,ofr_rte,ofr_dis,CMNT2 CMNT2,lmt lmtRte , flg , to_char(trunc(cts,2) * quot, '99999990.00') amt , " +
                    " decode(nvl(fquot,0),0, 'NA', decode(fquot, quot, 'NA', decode(greatest(fquot, quot),quot, 'UP','DN'))) cmp_flg, emp ";
        
        for (int i = 0; i < prcPrpLstsz; i++) {
                String prp = (String)prcPrpLst.get(i);
                String fld = "prp_";
                int j = i + 1;
                if (j < 10)
                    fld += "00" + j;
                else if (j < 100)
                    fld += "0" + j;
                else if (j > 100)
                    fld += j;

                
                srchQ += ", " + fld;
                itemHdr.add(prp);    
             }

            
                String rsltQ = srchQ1+" "+srchQ + " from gt_srch_rslt a where flg='S' and RCHK='RP' " ;
                rsltQ = rsltQ+  " order by 1, 2,3,4";
            
            ArrayList ary = new ArrayList();
            
          ArrayList  outLst = db.execSqlLst("search Result", rsltQ, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
           ResultSet  rs = (ResultSet)outLst.get(1);
            HashMap pktPrpMap = new HashMap();
                int sr=0;
                while (rs.next()) {
                 sr++;   
                 pktPrpMap = new HashMap();
                 String stkIdn = util.nvl(rs.getString("stk_idn"));
                 if(!adv_pri_dtl.equals("Y")){
                 pktListMapPrc = new HashMap();
                 pktListMapPrc= ((HashMap)pktPrpMapPrc.get(stkIdn) == null)?new HashMap():(HashMap)pktPrpMapPrc.get(stkIdn);
                 if(pktListMapPrc!=null && pktListMapPrc.size()>0){
                 for(int i=0;i<prigrpLstSz;i++){
                 String prigrp=util.nvl((String)prigrpLst.get(i));
                 pktPrpMap.put(prigrp, util.nvl((String)pktListMapPrc.get(prigrp)));
                 }
                 }
                 }else{
                     if(calcPriceDetails!=null && calcPriceDetails.size()>0){
                     HashMap pktPriceDetails=calcPriceDetails.get(stkIdn);
                     ArrayList sheetAppliedLst = (pktPriceDetails.get("PRI_DTL") == null)?new ArrayList():(ArrayList)pktPriceDetails.get("PRI_DTL");
                         for(int s=0;s<sheetAppliedLst.size();s++){
                             HashMap priDtlMap=(HashMap)sheetAppliedLst.get(s);
                             pktPrpMap.put(priDtlMap.get("NME"), String.valueOf(priDtlMap.get("DIS")));
                         }
                     }
                 }
                 String vnm =util.nvl(rs.getString("vnm"));
                 String cert_No = util.nvl(rs.getString("cert_no"));
                 pktPrpMap.put("Sr No", String.valueOf(sr));   
                 pktPrpMap.put("Packet Id", vnm);   
                 pktPrpMap.put("Status", util.nvl(rs.getString("dsp_stt")));   
                 pktPrpMap.put("flg", util.nvl(rs.getString("flg")));
                 pktPrpMap.put("stt", util.nvl(rs.getString("dsp_stt")));
                 pktPrpMap.put("stk_idn",stkIdn);
                 pktPrpMap.put("vnm",vnm);
                 pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
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
                 pktPrpMap.put("ofr_rte", util.nvl(rs.getString("ofr_rte")));
                 pktPrpMap.put("ofr_dis", util.nvl(rs.getString("ofr_dis")));
                 pktPrpMap.put("offerCol", util.nvl(rs.getString("CMNT2")));
                 pktPrpMap.put("offerlmtRte", util.nvl(rs.getString("lmtRte")));
                 pktPrpMap.put("UNM", util.nvl(rs.getString("emp")));
                 
                 for (int j = 0; j < prcPrpLstsz; j++) {
                     String prp = (String)prcPrpLst.get(j);

                     String fld = "prp_";
                     if (j < 9)
                         fld = "prp_00" + (j + 1);
                     else
                         fld = "prp_0" + (j + 1);

                     String val = util.nvl(rs.getString(fld));
                     if (prp.toUpperCase().equals("CRTWT"))
                         val = util.nvl(rs.getString("cts"));
                     if (prp.toUpperCase().equals("RAP_DIS"))
                         val = util.nvl(rs.getString("r_dis"));
                     if (prp.toUpperCase().equals("CMP_DIS")){
                         if(val.equals(""))
                         val = util.nvl(rs.getString("cmp_dis")); 
                     }
                     if (prp.toUpperCase().equals("RAP_RTE"))
                         val = util.nvl(rs.getString("rap_rte"));
                     if(prp.equalsIgnoreCase("MEM_COMMENT"))
                         val = util.nvl(rs.getString("img2"));
                     if(prp.equals("RTE"))
                         val = util.nvl(rs.getString("rte"));
                     if(prp.equals("CMP_RTE")){
                       if(val.equals(""))
                           val = util.nvl(rs.getString("cmp"));
                     }
                     if(prp.equals("RFIDCD"))
                         val = util.nvl(rs.getString("rmk"));
                   
                     if(prp.equals("KTSVIEW"))
                         val = util.nvl(rs.getString("kts"));
                     if(prp.equals("COMMENT"))
                         val = util.nvl(rs.getString("cmnt"));
                    
                         pktPrpMap.put(prp, val);
                     }

                 pktList.add(pktPrpMap);
                    
             }
            rs.close();
            pst.close();
            
            itemHdr.addAll(prigrpLst); 
            if(cnt.equals("hk"))
                itemHdr.add("UNM"); 

            
      ExcelUtil xlUtil = new ExcelUtil();
      xlUtil.init(db, util, info);      
      String CONTENT_TYPE = "getServletContext()/vnd-excel";
      String fileNm = "PricingDetailExcelSheet"+util.getToDteTime()+".xls";
      res.setContentType(CONTENT_TYPE);
      res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
            OutputStream out = res.getOutputStream();
            HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
            hwb.write(out);
            out.flush();
            out.close();
       return null;
        }
    }
    
    public ActionForward loadnexttrns(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        PriceChangeForm udf = (PriceChangeForm)af;
        udf.reset();
        String stkIdn = util.nvl(req.getParameter("stkIdn")).trim();
        String stt = util.nvl(req.getParameter("stt")).trim();
        ArrayList params = new ArrayList();
        String typ="";
        String rtnpage="";
        String setReqid="";
        int Idn=0;
            if(stt.equals("MKIS") || stt.equals("MKEI") || stt.equals("MKHS") || stt.equals("MKBDIS") || stt.equals("SHIS") || stt.equals("MKWH") || stt.equals("MKCS")){
                typ="IS";
                setReqid="memoId";
                if(stt.equals("MKCS"))
                rtnpage ="consignment";
                else
                rtnpage ="rtrn";
            }else if(stt.equals("MKAP") || stt.equals("MKWA") || stt.equals("MKSA")){
                typ="AP";
                rtnpage ="sale";
                setReqid="memoId";
            }else{
                typ="SL";
                rtnpage ="delivery";
                setReqid="saleId";
            }
            
            params = new ArrayList();
            params.add(stkIdn);
            params.add(typ);
            ArrayList out = new ArrayList();
            out.add("I");
            CallableStatement cst = db.execCall("findMemo","memo_pkg.find_ref_idn(pStkIdn => ?, pTyp =>? , pIdn => ?)", params ,out);
            Idn = cst.getInt(3);
          cst.close();
          cst=null;
            if(Idn!=0){
                req.setAttribute("APP","Y");
                req.setAttribute(setReqid,String.valueOf(Idn));
                return am.findForward(rtnpage);
            }
        return am.findForward("memo");
        }
    }
    public double get2Decimal(double val) {
      DecimalFormat df = new  DecimalFormat ("0.##");
      String d = df.format (val);
      System.out.println ("\tformatted: " + d);
      d = d.replaceAll (",", ".");
      Double dbl = new Double (d);
      return dbl.doubleValue ();
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
                util.updAccessLog(req,res,"Price Change", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Price Change", "init");
            }
            }
            return rtnPg;
            }
    

   
}


//~ Formatted by Jindent --- http://www.jindent.com
