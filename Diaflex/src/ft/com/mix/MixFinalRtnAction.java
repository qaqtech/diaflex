package ft.com.mix;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import java.io.LineNumberReader;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

public class MixFinalRtnAction extends DispatchAction {
   
    public MixFinalRtnAction() {
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
            util.getOpenCursorConnection(db,util,info);
          util.updAccessLog(req,res,"Mix Final Rtn", "load");
      MixFinalRtnInterface  mixInterface = new MixFinalRtnImpl();
      GenericInterface genericInt = new GenericImpl();
      MixFinalRtnForm  udf = (MixFinalRtnForm)af;
        udf.resetALL();
      
        ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIXFNRtNMatrixGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIXFNRtNMatrixGNCSrch");
        info.setGncPrpLst(assortSrchList);
        ArrayList prcList = mixInterface.getPrc(req, res);
        udf.setPrcList(prcList);
        ArrayList dpRng = mixInterface.dpMruleDtl(req, res);
        session.setAttribute("dpRngLst", dpRng);
          util.updAccessLog(req,res,"Mix Final Rtn", "load end");
    return am.findForward("load");
        }
    }
    
    public ActionForward loadGrid(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
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
          util.updAccessLog(req,res,"Mix Final Rtn", "load Grid");
      MixFinalRtnInterface  mixInterface = new MixFinalRtnImpl();
      GenericInterface genericInt = new GenericImpl();
      MixFinalRtnForm  udf = (MixFinalRtnForm)af;
        
        String issueId = nvl((String)udf.getValue("issueId"));
        String mprcIdn = nvl((String)udf.getValue("mprcIdn"));
        String inStt="";
        String otStt ="";
        String isStt="";
        ArrayList ary = new ArrayList();
        ary.add(mprcIdn);
          ArrayList  rsLst = db.execSqlLst("mprcId", "select idn , in_stt , ot_stt , is_stt from mprc where idn=? and stt='A'", ary);

          PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
          ResultSet  rs =(ResultSet)rsLst.get(1);
        if(rs.next()) {
         mprcIdn = nvl(rs.getString("idn"));
         inStt = nvl(rs.getString("in_stt"));
         otStt = nvl(rs.getString("ot_stt"));
         isStt = nvl(rs.getString("is_stt"));
        }
        rs.close();
          stmt.close();
        HashMap prp = info.getPrp();
        HashMap mprp = info.getMprp();
        HashMap paramsMap = new HashMap();
        ArrayList lprpFlds=null;
        String sZLst="";
        ArrayList sizeLst=new ArrayList();
        ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIXFNRtNMatrixGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIXFNRtNMatrixGNCSrch");
        info.setGncPrpLst(genricSrchLst);
        paramsMap.put("stt", isStt);
        if(issueId.length() > 0){
            paramsMap.put("issueId", issueId);
            HashMap prpMap =  mixInterface.FecthResult(req, res, paramsMap);
            if(prpMap.size()==0){
                udf.reset();
                ArrayList prcList = mixInterface.getPrc(req, res);
                udf.setPrcList(prcList);
                return am.findForward("load");
            }else{
            udf.setValue("SHAPE_1", prpMap.get("SHAPE"));
            sZLst = nvl((String)prpMap.get("sizeStr"));
            if(sZLst.length()>0)
            
            sizeLst = (ArrayList)prpMap.get("sizeLst");
            }
        }else{
        for (int i = 0; i < genricSrchLst.size(); i++) {
        ArrayList srchPrp = (ArrayList)genricSrchLst.get(i);
        String lprp = (String)srchPrp.get(0);
        String flg= (String)srchPrp.get(1);
        String prpSrt = lprp ;  
        String lprpTyp = nvl((String)mprp.get(lprp+"T"));
            if(flg.equals("M")) {
                ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
                ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
                for(int j=0; j < lprpS.size(); j++) {
                String lSrt = (String)lprpS.get(j);
                String lVal = (String)lprpV.get(j);    
                String reqVal1 = nvl((String)udf.getValue(lprp + "_" + lVal),"");
                if(!reqVal1.equals("")){
                paramsMap.put(lprp + "_" + lVal, reqVal1);
                }
                    if(lprp.equals("MIX_SIZE") && !reqVal1.equals("")){
                        sZLst = sZLst+"','"+reqVal1;
                        sizeLst.add(reqVal1);    
                    }
                }
            }else{
                String fldVal1 = nvl((String)udf.getValue(lprp+"_1"));
               String fldVal2 = nvl((String)udf.getValue(lprp+"_2"));
               if(fldVal2.equals(""))
                   fldVal2=fldVal1;
                 if(!fldVal1.equals("") && !fldVal2.equals("")){
                                paramsMap.put(lprp+"_1", fldVal1);
                                paramsMap.put(lprp+"_2", fldVal2);
                }   
            }            
        }
     
        paramsMap.put("mdl", "MIX_VIEW");
        paramsMap.put("MIX", "Y");
        int srchIDN=util.genericSrch(paramsMap);
        
        udf.setValue("srchId", String.valueOf(srchIDN));
        }
        ArrayList params = new ArrayList();
        params.add(mprcIdn);
        int ct = db.execCall("", "MIX_ISS_RTN.UPD_GT_ISS_ID(pPrcId => ?)", params);
        HashMap sizeClrMap = new HashMap();
        ArrayList clrLst=new ArrayList();
        if(!sZLst.equals("")){
       
            
           
        String clrQ="select distinct a.mix_clarity , b.srt from mix_size_clarity a , prp b\n" + 
        "where a.mix_clarity = b.val and b.mprp='MIX_CLARITY' and a.mix_size in ('"+sZLst+"') and stt='A' order by b.srt";
          rsLst = db.execSqlLst("Clarity Lst", clrQ, new ArrayList());
            stmt =(PreparedStatement)rsLst.get(0);
          rs =(ResultSet)rsLst.get(1);
        while(rs.next()){
            clrLst.add(nvl(rs.getString("mix_clarity")));
        }
            rs.close();
            stmt.close();
        String gridQ="select mix_size,mix_clarity from mix_size_clarity where mix_size in ('"+sZLst+"') and stt='A'";
          rsLst = db.execSqlLst("gridQ", gridQ, new ArrayList());
          stmt =(PreparedStatement)rsLst.get(0);
          rs =(ResultSet)rsLst.get(1);
        while(rs.next()){
            sizeClrMap.put(nvl(rs.getString("mix_clarity"))+"_"+nvl(rs.getString("mix_size")),"T");
        }
            rs.close();
            stmt.close();
        }
        getSizeCtsDtl(req, res , inStt,db,session,info,udf);
        FormFile uploadFile = udf.getMixFile();
        if(uploadFile!=null){
        setFileDate(req,udf,uploadFile,sizeLst,clrLst,sizeClrMap,session,info);   
        }
        req.setAttribute("sizeClrMap", sizeClrMap);
        session.setAttribute("sizeLst", sizeLst);
        req.setAttribute("view", "Y");
        udf.setValue("INSTT",inStt);
        udf.setValue("OTSTT",otStt);
        udf.setValue("ISSTT", isStt);
        udf.setValue("SHAPE", udf.getValue("SHAPE_1"));
        req.setAttribute("shape", udf.getValue("SHAPE_1"));
        session.setAttribute("clrLst", clrLst);
        ArrayList prcList = mixInterface.getPrc(req, res);
        udf.setPrcList(prcList);
        ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIXFNRtNMatrixGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIXFNRtNMatrixGNCSrch");
        info.setGncPrpLst(assortSrchList);
          util.updAccessLog(req,res,"Mix Final Rtn", "grid end");
        return am.findForward("load");
        }
    }
    
    
    public void getSizeCtsDtl(HttpServletRequest req, HttpServletResponse res , String inStt,DBMgr db,HttpSession session,InfoMgr info, MixFinalRtnForm  udf){
      MixFinalRtnInterface  mixInterface = new MixFinalRtnImpl();
        ArrayList mixPrpLst = mixInterface.MixPrprViw(req, res);
        int inxSize = mixPrpLst.indexOf("MIX_SIZE")+1;
        int inxDp = mixPrpLst.indexOf("DP")+1;
        HashMap prp = info.getPrp();
        ArrayList dprngLst = (ArrayList)session.getAttribute("dpRngLst");
     
        String shape =(String)udf.getValue("SHAPE_1");
        ArrayList shSrtLst = (ArrayList)prp.get("SHAPES");
        ArrayList shValLst = (ArrayList)prp.get("SHAPEV");
      
        ArrayList sizeSrtLst = (ArrayList)prp.get("MIX_SIZES");
        ArrayList clrSrtLst = (ArrayList)prp.get("MIX_CLARITYS");
        ArrayList sizeValLst = (ArrayList)prp.get("MIX_SIZEV");
        ArrayList sizePrtLst = (ArrayList)prp.get("MIX_SIZE"+"P2");
        ArrayList clrValLst = (ArrayList)prp.get("MIX_CLARITYV");
        String sizePrp = "prp_00"+inxSize;
        if(inxSize > 9)
            sizePrp = "prp_0"+inxSize;
        String sizeSrt =sizePrp.replace("prp", "srt");
        int inxClr = mixPrpLst.indexOf("MIX_CLARITY")+1;
        String clrPrp = "prp_00"+inxClr;
        if(inxClr>9)
            clrPrp = "prp_0"+inxClr;
        String clrSrt =clrPrp.replace("prp", "srt");
        String dpPrp = "prp_00"+inxDp;
        if(inxDp > 9)
            dpPrp = "prp_0"+inxDp;
        String dpSrt =dpPrp.replace("prp", "srt");
        String sqlStmt = "select cts , "+sizePrp+" sz , "+clrPrp+" clr , "+dpSrt+" dp ,  "+sizeSrt+" szSrt , "+clrSrt+" clrSrt , "+dpSrt+" dpSrt , stk_idn , dsp_stt  from Gt_srch_asrt";
      ArrayList  rsLst = db.execSqlLst("gt fecth", sqlStmt, new ArrayList());
      PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
      ResultSet  rs =(ResultSet)rsLst.get(1);
       HashMap sizeCrtMap = new HashMap();
        try {
         while(rs.next()){
                String clr = nvl(rs.getString("clr"),"NA").toUpperCase();
                String sz =nvl(rs.getString("sz")).toUpperCase();
                String dp = nvl(rs.getString("dp")).trim();
                String val = nvl(rs.getString("cts"));
                String clrValSrt = nvl(rs.getString("clrSrt"));
                String szValSrt =nvl(rs.getString("szSrt"));
                String dspstt = nvl(rs.getString("dsp_stt"));
                String clrVal = nvl(rs.getString("clr"));
            if(!sz.equals("") && !clr.equals("")){
                 String isDp = nvl((String)sizePrtLst.get(sizeSrtLst.indexOf(szValSrt)));
                 String fldKey = clrValSrt+"_SZCLR_"+szValSrt;
                if(isDp.equals("DP")){
                
                    for(int i=0;i<dprngLst.size();i++){
                         ArrayList dpDscLst = (ArrayList)dprngLst.get(i);
                         String dpDsc =(String)dpDscLst.get(0);
                         String dpVal = (String)dpDscLst.get(2);
                         Float dpdftVal = Float.parseFloat(dpVal);
                          Float dpdbVal = Float.parseFloat(dp);
                         String fldDpKey =fldKey+"_DP_"+dpDsc;
                          if(clrVal.equals("NA"))
                              fldDpKey="IS_"+fldDpKey;
                         if(dpDsc.indexOf("<")!=-1){
                            if( dpdbVal < dpdftVal){
                               
                                udf.setValue(fldDpKey, val);
                              
                             }
                         }else{
                         if(dpdbVal >= dpdftVal){
                          
                             udf.setValue(fldDpKey, val);
                         
                                                 
                        }
                      }}
                }else{
                 
                    fldKey +="_DP_0";
                    if(clrVal.equals("NA"))
                        fldKey="IS_"+fldKey;
                    udf.setValue(fldKey, val);
                   
                   
                }
            
             }
            }
            rs.close();
            stmt.close();
            String mixCrtSql = "select sum(iss_num) isscrt " + 
            ", decode(instr(a1.dept, '18-96'), 1, decode(greatest(Nvl(dp,0), 64.9), 64.9, 0, 65)) dp , a1.mix_size " + 
            "from mix_stk_v a1, Gt_srch_asrt a, iss_rtn_dtl b, iss_rtn_prp c " + 
            "where a1.idn = a.stk_idn " + 
            "and a.srch_id = b.iss_id and a.stk_idn = b.iss_stk_idn " + 
            "and b.flg1 = '"+inStt+"'" + 
            "and b.iss_id = c.iss_id and b.iss_stk_idn = c.iss_stk_idn and c.mprp = 'CRTWT' " + 
            "GROUP BY decode(instr(a1.dept, '18-96'), 1, decode(greatest(Nvl(dp,0), 64.9), 64.9, 0, 65)), a1.mix_size ";
            rsLst = db.execSqlLst("mixCrt", mixCrtSql, new ArrayList());
           stmt =(PreparedStatement)rsLst.get(0);
           rs =(ResultSet)rsLst.get(1);
            while(rs.next()){
                String mixCrt = nvl(rs.getString("isscrt"));
                String dp =  nvl(rs.getString("dp"));
                if(dp.equals(""))
                    dp="0";
                String sz = nvl(rs.getString("mix_size"));
                sizeCrtMap.put(sz+"_DP_"+dp, mixCrt);
            }
            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        req.setAttribute("maxCrtMap", sizeCrtMap);
    }
    
    public void setFileDate(HttpServletRequest req,MixFinalRtnForm  udf,FormFile uploadFile,ArrayList sizeLst,ArrayList clrLst,HashMap sizeClrMap,HttpSession session,InfoMgr info)throws Exception{
        if(uploadFile!=null){
            JspUtil jspUtil = new JspUtil();
        int ln=0;
        String fileName = uploadFile.getFileName();
        HashMap dataRte=new HashMap();
        fileName = fileName.replaceAll(".csv", jspUtil.getToDteTime()+".csv");
        HashMap matDtl = new HashMap();
            HashMap prp = info.getPrp();
            HashMap mprp = info.getMprp();
            ArrayList prpPrtSize=null;
            ArrayList prpPrt2Size=null;
            ArrayList prpValSize=null;
            ArrayList prpSrtSize = null;
            ArrayList prpPrtClr=null;
            ArrayList prpValClr=null;
            ArrayList prpSrtClr = null;
            prpPrtSize = (ArrayList)prp.get("MIX_SIZE"+"P");
            prpSrtSize = (ArrayList)prp.get("MIX_SIZE"+"S");
            prpValSize = (ArrayList)prp.get("MIX_SIZE"+"V");
            prpPrt2Size = (ArrayList)prp.get("MIX_SIZE"+"P2");
            prpPrtClr = (ArrayList)prp.get("MIX_CLARITY"+"P");
            prpSrtClr = (ArrayList)prp.get("MIX_CLARITY"+"S");
            prpValClr = (ArrayList)prp.get("MIX_CLARITY"+"V");
            ArrayList dprngLst = (ArrayList)session.getAttribute("dpRngLst");
        if(!fileName.equals("")){
        String fileTyp = uploadFile.getContentType();
        String path = getServlet().getServletContext().getRealPath("/") + fileName;
            File readFile = new File(path);
            if(!readFile.exists()){

              FileOutputStream fileOutStream = new FileOutputStream(readFile);

              fileOutStream.write(uploadFile.getFileData());

              fileOutStream.flush();

              fileOutStream.close();

              } 

            FileReader fileReader = new FileReader(readFile);
            LineNumberReader lnr        = new LineNumberReader(fileReader);
            String           line       = "";
            
            while ((line = lnr.readLine()) != null) {
                ln     = lnr.getLineNumber();
                StringTokenizer vals   = new StringTokenizer(line, ",");
                ArrayList          aryLst = new ArrayList();

                while (vals.hasMoreTokens()) {
                    aryLst.add(vals.nextToken());
                }
                for (int i = 0; i < aryLst.size(); i++) {
                    matDtl.put(ln+ "_" + i, (String) aryLst.get(i));
                }
               
            }
            String fldValcurrent="";
            String fldValfile="";
            String fldValrtefile="";
            for (int j = 0; j < clrLst.size(); j++) {
               String mix_clarity=(String)clrLst.get(j);
                int sr=0;
               for (int k = 0; k < sizeLst.size(); k++) {
               String mix_Size=(String)sizeLst.get(k);
               String isDp = nvl((String)prpPrt2Size.get(prpValSize.indexOf(mix_Size)));
               String tbox=nvl((String)sizeClrMap.get(mix_clarity+"_"+mix_Size));
               if(tbox.equals("T")){
               String mixSizesrt=(String)prpSrtSize.get(prpValSize.indexOf(mix_Size));
               String mixClrsrt=(String)prpSrtClr.get(prpValClr.indexOf(mix_clarity));
    //               String rte=util.getMixPriRte(shape,mix_Size,mix_clarity);
               String fldName = mixClrsrt+"_SZCLR_"+mixSizesrt+"_DP_0";   
               if(isDp.equals("DP")){
               for(int i=0;i<dprngLst.size();i++){
                   sr++;
               ArrayList dpDscLst = (ArrayList)dprngLst.get(i);
               String dpDsc = (String)dpDscLst.get(0);
               String dpVal = (String)dpDscLst.get(1);
               fldName = mixClrsrt+"_SZCLR_"+mixSizesrt+"_DP_"+dpDsc;
               fldValcurrent = nvl((String)udf.getValue(fldName));
               fldValfile = nvl((String)matDtl.get((j+1)+"_"+sr));
               sr++;
               fldValrtefile = nvl((String)matDtl.get((j+1)+"_"+sr));
            
                   udf.setValue(fldName, fldValfile);
                
               dataRte.put("PRI_"+fldName,fldValrtefile);
               }
               }else{
                   sr++;
                   fldValcurrent = nvl((String)udf.getValue(fldName));   
                   fldValfile = nvl((String)matDtl.get((j+1)+"_"+sr));
                   sr++;
                   fldValrtefile = nvl((String)matDtl.get((j+1)+"_"+sr));
                     
                       udf.setValue(fldName, fldValfile);
                   
                   dataRte.put("PRI_"+fldName,fldValrtefile);
               }}else{
               }
               }
            }
            req.setAttribute("dataRte", dataRte);
            udf.setValue("file", "Y");
            fileReader.close();
        }}  
    }
    public ActionForward save(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
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
       MixFinalRtnForm udf = (MixFinalRtnForm)af;
          MixFinalRtnInterface  mixInterface = new MixFinalRtnImpl();
        String lockNo = nvl(req.getParameter("lockId"));
        String OTSTT = nvl((String)udf.getValue("OTSTT"));
        String INSTT = nvl((String)udf.getValue("INSTT"));
        ArrayList out = new ArrayList();
        out.add("I");
        out.add("V");
         CallableStatement cts = db.execCall("mix_rtn", "MIX_ISS_RTN.ALL_RTN_PKT(pCnt => ?, pMsg => ?)", new ArrayList(),out);
         int cnt = cts.getInt(1);
         String msg = cts.getString(2);
         if(INSTT.equals("MX_FN_AV")){
        
         int ct = db.execCall("unlock", "MERGE_PKG.HALF_DN_TO_HALF_UP", new ArrayList());
         }
         req.setAttribute("msg", "Count :"+cnt+" "+msg+"");
        
       
        String sh = nvl((String)udf.getValue("SHAPE"));
        ArrayList sizeLst = (ArrayList)session.getAttribute("sizeLst");
        String sizeStr = sizeLst.toString();
        sizeStr = sizeStr.replace("[", "('");
        sizeStr = sizeStr.replace("]", "')");
        sizeStr = sizeStr.replaceAll(",", "','");
        sizeStr = sizeStr.replace(" ", "");
        HashMap prp = info.getPrp();
        ArrayList prpSrtSH = (ArrayList)prp.get("SHAPE"+"S");
        ArrayList prpValSH = (ArrayList)prp.get("SHAPE"+"V");
        String shVal=(String)prpValSH.get(prpSrtSH.indexOf(sh));
        
        String mixStkSql= " select f.idn , f.cts cts , '' memo ,b.val shape ,c.val mix_size , e.val mix_clarity , d.num dp , f.upr , f.cmp  " + 
        " from stk_dtl b , stk_dtl c , stk_dtl d ,stk_dtl e , mstk f " + 
        " where b.mprp='SHAPE' and b.grp=1 and b.val=? and b.mstk_idn = c.mstk_idn " + 
        " and c.mprp='MIX_SIZE' and c.grp=1 and c.val in "+sizeStr+" and c.mstk_idn = d.mstk_idn " + 
        " and d.mprp='DP' and d.grp=1 and d.mstk_idn = e.mstk_idn " + 
        " and e.mprp='MIX_CLARITY' and e.grp=1 and e.mstk_idn = f.idn " + 
        " and f.stt in ('"+OTSTT+"') and  f.cts >0 order by f.sk1 ";
              
          ArrayList params = new ArrayList();
        params.add(shVal);
        ArrayList rsLst = db.execSqlLst("mixStkSql", mixStkSql, params);
        PreparedStatement pst = (PreparedStatement)rsLst.get(0);
        ResultSet rs = (ResultSet)rsLst.get(1);
        ArrayList pktDtlList = new ArrayList();
        while(rs.next()){
            HashMap pktDtl = new HashMap();
            pktDtl.put("idn", nvl(rs.getString("idn")));
            pktDtl.put("cts", nvl(rs.getString("cts")));
            pktDtl.put("memo", nvl(rs.getString("memo")));
            pktDtl.put("sh", nvl(rs.getString("shape")));
            pktDtl.put("mixsz", nvl(rs.getString("mix_size")));
            pktDtl.put("mixclr", nvl(rs.getString("mix_clarity")));
            pktDtl.put("dp", nvl(rs.getString("dp")));
            pktDtl.put("upr", nvl(rs.getString("upr")));
            pktDtl.put("cmp", nvl(rs.getString("cmp")));
            pktDtlList.add(pktDtl);
        }
        rs.close();
        pst.close();
        req.setAttribute("pktDtlList", pktDtlList);
        
        udf.reset();
        ArrayList prcList = mixInterface.getPrc(req, res);
        udf.setPrcList(prcList);
        return am.findForward("load");
        }
    }
    public ActionForward saveVal(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
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
        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        String cts = nvl(req.getParameter("cts"));
       if(cts.equals(""))
           cts ="0";
        String mixSizeSrt = nvl(req.getParameter("mixSize"));
        String mixClrSrt = nvl(req.getParameter("mixClr"));
        String sh = nvl(req.getParameter("sh"));
        String cmp = nvl(req.getParameter("cmp"));
        String upr = nvl(req.getParameter("upr"));
        if(upr.equals(""))
            upr="0";
        String lockNo = nvl(req.getParameter("lockId"));
        String dp = nvl(req.getParameter("dp"));
        String isStt = nvl(req.getParameter("isStt"));
        String mprcID = nvl(req.getParameter("prc"));
        String lprp = nvl(req.getParameter("prp"));
        HashMap prp = info.getPrp();
      
      
        ArrayList prpValSize=null;
        ArrayList prpSrtSize = null;
       
        ArrayList prpValClr=null;
        ArrayList prpSrtClr = null;
        ArrayList prpValSH=null;
        ArrayList prpSrtSH = null;
        
        prpSrtSize = (ArrayList)prp.get("MIX_SIZE"+"S");
        prpValSize = (ArrayList)prp.get("MIX_SIZE"+"V");
        String mixSize=(String)prpValSize.get(prpSrtSize.indexOf(mixSizeSrt));
        
       
        prpValClr=new ArrayList();
        prpSrtClr =new ArrayList();
       
        prpSrtClr = (ArrayList)prp.get("MIX_CLARITY"+"S");
        prpValClr = (ArrayList)prp.get("MIX_CLARITY"+"V");
        String mixClr=(String)prpValClr.get(prpSrtClr.indexOf(mixClrSrt));  
        
        prpSrtSH = (ArrayList)prp.get("SHAPE"+"S");
        prpValSH = (ArrayList)prp.get("SHAPE"+"V");
        String shVal=(String)prpValSH.get(prpSrtSH.indexOf(sh));
        
                      
        ArrayList mixPrpLst = (ArrayList)session.getAttribute("MixViewLst");
        
        int inxSz = mixPrpLst.indexOf("MIX_SIZE")+1;
        String szPrp = "srt_00"+inxSz;
        if(inxSz > 9)
            szPrp = "srt_0"+inxSz;
        int inxSh = mixPrpLst.indexOf("SHAPE")+1;
        String shPrp = "srt_00"+inxSh;
        if(inxSh > 9)
            shPrp = "srt_0"+inxSh;
        int inxClr = mixPrpLst.indexOf("MIX_CLARITY")+1;
        String clrPrp = "srt_00"+inxClr;
        if(inxClr > 9)
            clrPrp = "srt_0"+inxClr;
        int inxdp = mixPrpLst.indexOf("DP")+1;
        String dpPrp = "prp_00"+inxdp;
        if(inxdp > 9)
            dpPrp = "prp_0"+inxdp;
        
        int inxdept = mixPrpLst.indexOf("DEPT")+1;
        String deptPrp = "prp_00"+inxdept;
        if(inxdept > 9)
            deptPrp = "prp_0"+inxdept;
        
        String mstkIdn = "";
        String issueId = nvl(req.getParameter("issueId"));
        String dpVal =dp;
        
//        boolean isAddPrp = true;
//        
//        ArrayList params  = new ArrayList();
//        String gtFtch = "select stk_idn , srch_id from gt_srch_rslt where  "+szPrp+" = '"+mixSizeSrt+"' and "+shPrp+"='"+sh+"' and "+clrPrp+"='"+mixClrSrt+"' and " +
//            "   decode(instr("+deptPrp+", '18-96'),1,nvl("+dpPrp+",0), 0) = "+dpVal ;
//         
//        ResultSet rs = db.execSql("gtFtch",gtFtch, new ArrayList());
//        if(rs.next()){
//           mstkIdn = util.nvl(rs.getString("stk_idn"));
//           issueId = util.nvl(rs.getString("srch_id"));
//        }else{
//            if(cts.equals("0") && upr.equals("0")){
//                isAddPrp = false;
//            }else{
//           gtFtch = "select max(srch_id) srch_id from gt_srch_rslt";
//           rs = db.execSql("gtFtch",gtFtch, new ArrayList());
//           if(rs.next())
//               issueId = util.nvl(rs.getString("srch_id"));
//           params  = new ArrayList();
//           params.add(isStt);
//          
//           ArrayList out = new ArrayList();
//           out.add("V");
//           out.add("V");
//           String genPkt = "MIX_PKG.GEN_PKT(pStt => ?  , pIdn => ? , pVnm => ?)";
//           CallableStatement cst = db.execCall("genPkt", genPkt, params, out);
//           mstkIdn = cst.getString(params.size()+1);
//           params  = new ArrayList();
//           params.add(mstkIdn);
//           params.add(shVal);
//           int ct = db.execCall("pkt_prp_upd", "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => 'SHAPE', pVal => ?)", params);
//           params  = new ArrayList();
//           params.add(mstkIdn);
//           params.add(mixSize);
//           ct = db.execCall("pkt_prp_upd", "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => 'MIX_SIZE', pVal => ?)", params);
//           params  = new ArrayList();
//           params.add(mstkIdn);
//           params.add(cts);
//           ct = db.execCall("pkt_prp_upd", "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => 'CRTWT', pVal => ?)", params);
//           params  = new ArrayList();
//           params.add(mstkIdn);
//           params.add(mixClr);
//           ct = db.execCall("pkt_prp_upd", "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp =>'MIX_CLARITY', pVal => ?)", params);
//           params  = new ArrayList();
//           params.add(mstkIdn);
//           params.add(dpVal);
//           ct = db.execCall("pkt_prp_upd", "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp =>'DP', pVal => ?)", params);
//         
//           params  = new ArrayList();
//           params.add(mstkIdn);
//           ct = db.execCall("pkt_prp_upd", "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => 'GRAPH', pVal => '0')", params);
//           params  = new ArrayList();
//           params.add(mstkIdn);
//         ct = db.execCall("pkt_prp_upd", "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => 'DEPT', pVal => stk_crt_pkg.get_crt('"+mstkIdn+"','DEPT_GRP','DEPT'))", params);
//        
//            params  = new ArrayList();
//            params.add(mstkIdn);
//            ct = db.execCall("stk1", "STK_SRT(Pid => ? )", params);
//            }
//        }
//        if(isAddPrp){             
//        params  = new ArrayList();
//        params.add(issueId);
//        params.add(mstkIdn);
//        params.add("CRTWT");
//        params.add(cts);
//        int ct1 = db.execCallDir("RTN_PKT_PRP", "MIX_ISS_RTN.RTN_PKT_PRP(pIssId => ? , pstkIdn => ? , pPrp => ? ,  pVal=> ?)", params);
//        
//        params  = new ArrayList();
//        params.add(issueId);
//        params.add(mstkIdn);
//        params.add("RTE");
//        params.add(upr);
//        ct1 = db.execCallDir("RTN_PKT_PRP", "MIX_ISS_RTN.RTN_PKT_PRP(pIssId => ? , pstkIdn => ? , pPrp => ? ,  pVal=> ?)", params);
//        }

     
            ArrayList ary = new ArrayList();
            ary.add(mprcID);
            ary.add(issueId);
            ary.add(shVal);
            ary.add(mixSize);
            ary.add(mixClr);
            ary.add(dpVal);
            ary.add(cts);
            ary.add(upr);
            String checkRtnPrp = "MIX_ISS_RTN.CHK_RTN_PKT_PRP(pPrcId => ? , pIssId => ? , pShp => ? , pMixSz => ?, pMixClr => ?, pDp => ?,  pCts => ? , pRte => ?) ";
            int ct = db.execCallDir("checkRtnPrp", checkRtnPrp, ary);
        
        ArrayList params  = new ArrayList();
        
        String gtCts = "select MIX_ISS_RTN.GET_RTN_CTS('"+mixSize+"', '"+dpVal+"') cts from dual";
        ResultSet rs = db.execSql("gt fetch",gtCts, params);
        
        if(rs.next()){
           String ttlCts = nvl(rs.getString("cts"));
            res.getWriter().write("<cts>"+ttlCts+"</cts>");
        }else{
            res.getWriter().write("<cts>0</cts>");
        }
        rs.close();
        
        
       return null;
        }
    }
    public ActionForward verify(ActionMapping am, ActionForm af,  HttpServletRequest req, HttpServletResponse res) throws Exception {
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
        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        String sh = nvl(req.getParameter("sh"));
        String isStt = nvl(req.getParameter("isStt"));
        HashMap prp = info.getPrp();
        ArrayList prpSrtSH = (ArrayList)prp.get("SHAPE"+"S");
        ArrayList prpValSH = (ArrayList)prp.get("SHAPE"+"V");
        String shVal=(String)prpValSH.get(prpSrtSH.indexOf(sh));
        ArrayList params  = new ArrayList();
        StringBuffer sb = new StringBuffer();
        ArrayList out = new ArrayList();
        out.add("V");
        out.add("V");
        CallableStatement cst = db.execCall("MIX_Verification","MIX_ISS_RTN.VERIFY_CTS( pRtn => ?, pMsg => ?)", params ,out);
        String flg = cst.getString(params.size()+1);
        String msg = cst.getString(params.size()+2);
        
              sb.append("<msg>");
              sb.append("<flg>"+flg+"</flg>");
              sb.append("<rtnmsg>"+msg+"</rtnmsg>");
              sb.append("</msg>");
          cst.close();
          cst=null;
        res.getWriter().write("<msgs>" +sb.toString()+ "</msgs>");
        return null;
        }
    }
    public ActionForward unlock(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        String srchId = nvl(req.getParameter("srchId"));
    
        ArrayList params = new ArrayList();
       
        params.add(srchId);
        ArrayList out = new ArrayList();
        out.add("I");
        
       CallableStatement cst = db.execCall("MIX_lock","MIX_ISS_RTN.LOCK_FNL_PKT( pSrchID => ?, pLockIdn => ?)", params ,out);
       String lockId = cst.getString(params.size()+1);
          cst.close();
          cst=null;
        
        res.getWriter().write("<lock>"+lockId+"</lock>");
        return null;
        }
    }
    
  public String nvl(String pVal) {
      return nvl(pVal, "");
  }
  
  public String nvl(String pVal, String rVal) {

      String val = pVal ;
      if(pVal == null)
          val = rVal;
      else if(pVal.equals(""))
        val = rVal;
      return val;

  }
  public String init(HttpServletRequest req , HttpServletResponse res,HttpSession session,DBUtil util) {
          String rtnPg="sucess";
          String invalide="";
          String connExists=nvl(util.getConnExists());  
          if(!connExists.equals("N"))
          invalide=nvl(util.chkTimeOut(),"N");
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
              util.updAccessLog(req,res,"Mix Final issue", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"Mix Final issue", "init");
          }
          }
          return rtnPg;
       }
}
