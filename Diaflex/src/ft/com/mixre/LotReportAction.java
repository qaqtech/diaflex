package ft.com.mixre;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.report.ReportForm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class LotReportAction extends DispatchAction {
    public LotReportAction() {
        super();
    }
    
    public ActionForward loadlot(ActionMapping am, ActionForm form, HttpServletRequest req,
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
            LotReportForm udf = (LotReportForm)form;
            udf.reset();
            util.updAccessLog(req,res,"Rpt Action", "loadlot start");
            GenericInterface genericInt = new GenericImpl();
            ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "LOT_RPT_VW","LOT_RPT_VW");
            genericInt.genericPrprVw(req, res, "LOT_RPT_FLT","LOT_RPT_FLT");
            genericInt.genericPrprVw(req, res, "LOT_RPT_FLTSUMM","LOT_RPT_FLTSUMM");
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("LOT_REPORT");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("LOT_REPORT");
            allPageDtl.put("LOT_REPORT",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            String mix = util.nvl((String)req.getParameter("MIX"));
            udf.setValue("MIX", mix);
            util.updAccessLog(req,res,"Rpt Action", "loadlot end");
            return am.findForward("loadlot");
        }
    }
    public ActionForward fetchlot(ActionMapping am, ActionForm form, HttpServletRequest req,
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
            LotReportForm udf = (LotReportForm)form;
            util.updAccessLog(req,res,"Rpt Action", "fetchlot start");
            String lot=util.nvl((String)udf.getValue("lot")).trim();
            String rowlprp=util.nvl((String)udf.getValue("rowlprp")).trim();
            String shapebyrightside=util.nvl((String)udf.getValue("shapebyrightside")).trim();
            String lotlprp=util.nvl((String)udf.getValue("lotlprp")).trim();
            HashMap dbinfo = info.getDmbsInfoLst();
            String sh = (String)dbinfo.get("SHAPE");
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("LOT_REPORT");
            ArrayList pageList=new ArrayList();
            HashMap pageDtlMap=new HashMap();
            String fld_nme="",fld_ttl="",val_cond="",lov_qry="",dflt_val="",fld_typ="",form_nme="",flg1="";
            GenericInterface genericInt = new GenericImpl();
            ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "LOT_RPT_VW","LOT_RPT_VW");
            ArrayList vWPrpSummaryList=genericInt.genericPrprVw(req, res, "LOT_RPT_FLT","LOT_RPT_FLT");
            ArrayList prpDspBlocked = info.getPageblockList();
            String frmDte = util.nvl((String)udf.getValue("frmDte"));
            String toDte = util.nvl((String)udf.getValue("toDte"));
            HashMap mprp = info.getMprp();
            if(!lot.equals("") || (!frmDte.equals("") && !toDte.equals(""))){
                String conQ="";
                ArrayList showprpLst=new ArrayList();
                pageList=(ArrayList)pageDtl.get("SHOWPRP");
                if(pageList!=null && pageList.size() >0){
                for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                fld_ttl=(String)pageDtlMap.get("fld_ttl");
                dflt_val=(String)pageDtlMap.get("dflt_val");
                fld_typ=(String)pageDtlMap.get("fld_typ");
                lov_qry=(String)pageDtlMap.get("lov_qry");
                int indexprp=0;
                String showgtPrp="";
                if(lov_qry.equals("PRP")){
                indexprp = vWPrpList.indexOf(fld_typ)+1;   
                if(!prpDspBlocked.contains(fld_typ)){
                if(indexprp!=0){
                showgtPrp = util.prpsrtcolumn("prp",indexprp);
                if(dflt_val.equals("AVG")){
                if(fld_typ.equals("AGE"))
                conQ+=",round(sum("+showgtPrp+")/sum(qty)) "+fld_ttl;
                else if(fld_typ.equals("HIT"))
                conQ+=",round(sum("+showgtPrp+")/sum(qty)) "+fld_ttl;
                else if(fld_typ.equals("RTE"))
                conQ+=",trunc(sum(nvl("+showgtPrp+",quot)*trunc(cts,2))/sum(trunc(cts, 2)),0) "+fld_ttl;  
                else{
                val_cond=(String)pageDtlMap.get("val_cond");
                if(val_cond.equals("")){
                if(fld_typ.equals("MFG_PRI"))
                conQ+=",trunc(sum(nvl("+showgtPrp+",quot)*trunc(cts,2))/sum(trunc(cts, 2)),0) "+fld_ttl; 
                else
                conQ+=",trunc(sum("+showgtPrp+"*trunc(cts,2))/sum(trunc(cts, 2)),0) "+fld_ttl; 
                }else{
                int indexprpcts = vWPrpList.indexOf(val_cond)+1; 
                String showgtPrprw = util.prpsrtcolumn("prp",indexprpcts);
                conQ+=",round(sum("+showgtPrp+"*trunc("+showgtPrprw+",2))/sum(trunc("+showgtPrprw+", 2))) "+fld_ttl; 
                }
                }
                showprpLst.add(fld_ttl);   
                }else{
                if(!prpDspBlocked.contains(fld_typ)){
                indexprp = vWPrpList.indexOf(fld_typ)+1; 
                if(indexprp!=0){
                showgtPrp = util.prpsrtcolumn("prp",indexprp);
                conQ+=",trunc(((sum(nvl("+showgtPrp+",quot)*trunc(cts,2))/sum(trunc(cts, 2)))/(sum(rap_Rte*trunc(cts,2))/sum(trunc(cts, 2)))*100) - 100, 2)  "+fld_ttl;  
                    showprpLst.add(fld_ttl);   
                }
                }
                }
                }
                }
                }else if(lov_qry.equals("VLU")){
                    if(!prpDspBlocked.contains(fld_typ)){
                    indexprp = vWPrpList.indexOf(fld_typ)+1; 
                    if(indexprp!=0){
                    val_cond=(String)pageDtlMap.get("val_cond");
                    showgtPrp = util.prpsrtcolumn("prp",indexprp);
                    if(val_cond.equals("")){
                    conQ+=",round(sum(trunc(cts,2)*nvl("+showgtPrp+",quot))) "+fld_ttl; 
                    }else{
                        int indexprpcts = vWPrpList.indexOf(val_cond)+1; 
                        String showgtPrprw = util.prpsrtcolumn("prp",indexprpcts);
                        conQ+=",round(sum(trunc("+showgtPrprw+",2)*"+showgtPrp+")) "+fld_ttl;  
                    }
                    showprpLst.add(fld_ttl);      
                    }else{
                        conQ+=",round(sum(trunc(cts,2)*nvl("+fld_typ+",quot))) "+fld_ttl; 
                        showprpLst.add(fld_ttl);   
                    }
                    }
                }else if(lov_qry.equals("SUM")){
                    indexprp = vWPrpList.indexOf(fld_typ)+1; 
                    if(indexprp!=0){
                    showgtPrp = util.prpsrtcolumn("prp",indexprp);
                    conQ+=",to_char(sum(nvl("+showgtPrp+",0)),'99999990.00') "+fld_ttl; 
                    showprpLst.add(fld_ttl); 
                    }
                }else if(lov_qry.equals("AGEVLUAVG")){
                    indexprp = vWPrpList.indexOf(fld_typ)+1; 
                    if(indexprp!=0){
                    showgtPrp = util.prpsrtcolumn("prp",indexprp);
                    conQ+=",round(sum((trunc(cts,2)*quot)*nvl("+showgtPrp+",0))/sum(trunc(cts,2)*quot)) "+fld_ttl; 
                    showprpLst.add(fld_ttl); 
                   }
                 }else if(lov_qry.equals("DIFF")){
                     indexprp = vWPrpList.indexOf(fld_typ)+1; 
                     int indexprprte = vWPrpList.indexOf((String)pageDtlMap.get("val_cond"))+1;
                     if(indexprp!=0 && indexprprte !=0){
                     showgtPrp = util.prpsrtcolumn("prp",indexprp);
                     String showgtPrprte = util.prpsrtcolumn("prp",indexprprte);
                     conQ+=",trunc(100 - (trunc(sum(nvl("+showgtPrprte+",quot)*trunc(cts,2))/sum(trunc(cts, 2)),0)*100/trunc(sum(nvl("+showgtPrp+",quot)*trunc(cts,2))/sum(trunc(cts, 2)),0)), 2) "+fld_ttl; 
                     showprpLst.add(fld_ttl); 
                     }
                 }
                }
                }
                
                ArrayList ary=new ArrayList();
                ary.add(lotlprp);
                ary.add(lot);
                ary.add("LOT_RPT_VW");
                String lotPRCQ="DP_LOT_ANALYSIS(pPrp => ?, pVal => ?, pMdl => ?)";
                if(!frmDte.equals("") && !toDte.equals("")){
                lotPRCQ="DP_LOT_ANALYSIS(pPrp => ?, pVal => ?, pMdl => ?,pDteFrm=> to_date(?, 'dd-mm-rrrr'), pDteTo=> to_date(?, 'dd-mm-rrrr'))";
                ary.add(frmDte);
                ary.add(toDte);
                }
                int ct = db.execCall("memoPRCQ", lotPRCQ,ary);
                
                ArrayList pktTyGrid=new ArrayList();
                pktTyGrid.add("NR");
                pktTyGrid.add("MIX");
                for(int z=0;z<pktTyGrid.size();z++){
                String pkt_ty=util.nvl((String)pktTyGrid.get(z));
                if(pkt_ty.equals("MIX") && (rowlprp.equals("CO") || rowlprp.equals("COL")))
                rowlprp="MIX_COLOR";
                if(pkt_ty.equals("MIX") && (rowlprp.equals("PU") || rowlprp.equals("CLR")))
                rowlprp="MIX_CLARITY";
                if(pkt_ty.equals("MIX") && (rowlprp.equals("SH") || rowlprp.equals("SHAPE")))
                rowlprp="MIX_SHAPE";
                if(pkt_ty.equals("MIX") && (rowlprp.equals("SZ") || rowlprp.equals("SIZE")))
                rowlprp="MIX_SIZE";
                    
                    
                    if(pkt_ty.equals("MIX"))
                        sh="MIX_SHAPE";
                    
                    int indexSH = vWPrpList.indexOf(sh)+1;
                    int indexRowlprp = vWPrpList.indexOf(rowlprp)+1;
                    int indexLOT= vWPrpList.indexOf(lotlprp)+1;
                    String shPrp = util.prpsrtcolumn("prp",indexSH);
                    String rowPrp = util.prpsrtcolumn("prp",indexRowlprp);
                    String lotPrp = util.prpsrtcolumn("prp",indexLOT);
                    String shSrt = util.prpsrtcolumn("srt",indexSH);
                    String rowSrt = util.prpsrtcolumn("srt",indexRowlprp);
                    String lotSrt = util.prpsrtcolumn("srt",indexLOT);
                HashMap dataDtl=new HashMap();
                HashMap datattl=new HashMap();
                HashMap data=new HashMap();
                ArrayList keytable=new ArrayList();
                ArrayList sttLst=new ArrayList();
                ArrayList conatinsdata=new ArrayList();
                int showprpLstsz=showprpLst.size();
                
                String memoQ="select dsp_stt,"+shPrp+" sh,"+shSrt+" sh_so,"+rowPrp+" rowcol,"+rowSrt+" row_so,\n" + 
                "count(*) qty,to_char(sum(cts),'99999990.00') cts " +conQ+ 
                " from gt_srch_rslt gt\n" + 
                "where nvl(cts,0) > 0 and gt.pkt_ty='"+pkt_ty+"'\n" + 
                "group by dsp_stt,"+shPrp+" ,"+shSrt+","+rowPrp+" ,"+rowSrt+"\n" + 
                "Order By dsp_stt desc,Sh_So,row_so";
                  ArrayList  rsLst = db.execSqlLst("memo by Shape", memoQ, new ArrayList());
                  PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
                  ResultSet  rs =(ResultSet)rsLst.get(1);
                while(rs.next()){
                    data=new HashMap();   
                    String dsp_stt=util.nvl((String)rs.getString("dsp_stt"));
                    String shapesrt=util.nvl((String)rs.getString("sh_so"));
                    String key=dsp_stt+"_"+shapesrt;
                    if(!keytable.contains(key)){
                        keytable.add(key);
                        conatinsdata=new ArrayList();       
                    }
                    if(!sttLst.contains(dsp_stt)){
                        sttLst.add(dsp_stt);      
                    }
                    data.put("STT",dsp_stt);
                    data.put("SH",util.nvl((String)rs.getString("sh")));
                    data.put("ROW",util.nvl((String)rs.getString("rowcol")));
                    data.put("QTY",util.nvl((String)rs.getString("qty")));
                    data.put("CTS",util.nvl((String)rs.getString("cts")));
                    for(int s=0;s<showprpLstsz;s++){
                    String showprp=(String)showprpLst.get(s);
                    data.put(showprp, util.nvl(rs.getString(showprp)));        
                    }
                    conatinsdata.add(data);
                    dataDtl.put(key,conatinsdata);       
                }
                    rs.close();
                stmt.close();
                String shapeQ="select dsp_stt,"+shPrp+" sh,"+shSrt+" sh_so,\n" + 
                "count(*) qty,to_char(sum(cts),'99999990.00') cts " +conQ+ 
                " from gt_srch_rslt gt\n" + 
                "where nvl(cts,0) > 0  and gt.pkt_ty='"+pkt_ty+"'\n" +
                "group by dsp_stt,"+shPrp+" ,"+shSrt+"\n" + 
                "Order By dsp_stt,Sh_So";
                   rsLst = db.execSqlLst("Sum by shape", shapeQ, new ArrayList());
                   stmt =(PreparedStatement)rsLst.get(0);
                   rs =(ResultSet)rsLst.get(1);
                while(rs.next()){ 
                    data=new HashMap();   
                    String dsp_stt=util.nvl((String)rs.getString("dsp_stt"));
                    String shapesrt=util.nvl((String)rs.getString("sh_so"));
                    String key=dsp_stt+"_"+shapesrt;
                    data.put("STT",dsp_stt);
                    data.put("SH",util.nvl((String)rs.getString("sh")));
                    data.put("QTY",util.nvl((String)rs.getString("qty")));
                    data.put("CTS",util.nvl((String)rs.getString("cts")));
                    for(int s=0;s<showprpLstsz;s++){
                    String showprp=(String)showprpLst.get(s);
                    data.put(showprp, util.nvl(rs.getString(showprp)));        
                    }
                    datattl.put(key,data);  
                }
                    rs.close();
                    stmt.close();
                String deptQ="select dsp_stt,\n" + 
                "count(*) qty,to_char(sum(cts),'99999990.00') cts "+conQ+
                " from gt_srch_rslt gt\n" + 
                "where nvl(cts,0) > 0  and gt.pkt_ty='"+pkt_ty+"'\n" +
                "group by prp_001,dsp_stt\n" + 
                "Order By dsp_stt";
                  rsLst = db.execSqlLst("Sum by Dept", deptQ, new ArrayList());
                  stmt =(PreparedStatement)rsLst.get(0);
                  rs =(ResultSet)rsLst.get(1);
                while(rs.next()){
                    data=new HashMap();
                    String dsp_stt=util.nvl((String)rs.getString("dsp_stt"));
                    data.put("STT",dsp_stt);
                    data.put("QTY",util.nvl((String)rs.getString("qty")));
                    data.put("CTS",util.nvl((String)rs.getString("cts")));
                    for(int s=0;s<showprpLstsz;s++){
                    String showprp=(String)showprpLst.get(s);
                    data.put(showprp, util.nvl(rs.getString(showprp)));        
                    }
                    dataDtl.put(dsp_stt,data);    
                }
                    rs.close();
                    stmt.close();
                    
                    String shpdeptttQ="select dsp_stt,\n" + 
                    "count(*) qty,to_char(sum(cts),'99999990.00') cts " +conQ+ 
                    " from gt_srch_rslt gt\n" + 
                    "where nvl(cts,0) > 0 and "+shPrp+" not in('ROUND','RD') and gt.pkt_ty='"+pkt_ty+"'\n" +
                    "group by dsp_stt\n" + 
                    "Order By dsp_stt";
                  rsLst = db.execSqlLst("Sum by Shape Dept", shpdeptttQ, new ArrayList());
                  stmt =(PreparedStatement)rsLst.get(0);
                  rs =(ResultSet)rsLst.get(1);
                    while(rs.next()){
                        data=new HashMap();
                        String dsp_stt=util.nvl((String)rs.getString("dsp_stt"));
                        data.put("STT",dsp_stt);
                        data.put("QTY",util.nvl((String)rs.getString("qty")));
                        data.put("CTS",util.nvl((String)rs.getString("cts")));
                            for(int s=0;s<showprpLstsz;s++){
                            String showprp=(String)showprpLst.get(s);
                            data.put(showprp, util.nvl(rs.getString(showprp)));        
                            }
                        dataDtl.put(dsp_stt+"_TTL",data);    
                    }
                        rs.close();
                    stmt.close();
                String grandQ="select count(*) qty,to_char(sum(cts),'99999990.00') cts " +conQ+ 
                " from gt_srch_rslt gt\n" + 
                "where nvl(cts,0) > 0 and gt.pkt_ty='"+pkt_ty+"'\n";
                  rsLst = db.execSqlLst("Grand Totals", grandQ, new ArrayList());
                  stmt =(PreparedStatement)rsLst.get(0);
                  rs =(ResultSet)rsLst.get(1);
                while(rs.next()){ 
                    data=new HashMap();
                    data.put("QTY",util.nvl((String)rs.getString("qty")));
                    data.put("CTS",util.nvl((String)rs.getString("cts")));
                        for(int s=0;s<showprpLstsz;s++){
                        String showprp=(String)showprpLst.get(s);
                        data.put(showprp, util.nvl(rs.getString(showprp)));        
                        }
                    data.put("LOT",lot);   
                    data.put("ROW",rowlprp);
                    dataDtl.put("GRANDTTL",data);  
                }
                    rs.close();
                    stmt.close();
                    
                //new
                for(int h=0;h<vWPrpSummaryList.size();h++){
                ArrayList gtPrpList=new ArrayList();
                String summarylprp=util.nvl((String)vWPrpSummaryList.get(h));
                String dta_typ=util.nvl((String)mprp.get(summarylprp+"T"));
                if(pkt_ty.equals("MIX") && (summarylprp.equals("CO") || summarylprp.equals("COL")))
                summarylprp="MIX_COLOR";
                if(pkt_ty.equals("MIX") && (summarylprp.equals("PU") || summarylprp.equals("CLR")))
                summarylprp="MIX_CLARITY";
                if(pkt_ty.equals("MIX") && (summarylprp.equals("SH") || summarylprp.equals("SHAPE")))
                summarylprp="MIX_SHAPE";
                if(pkt_ty.equals("MIX") && (summarylprp.equals("SZ") || summarylprp.equals("SIZE")))
                summarylprp="MIX_SIZE";
                    
                    int indexSummarylprp = vWPrpList.indexOf(summarylprp)+1;
                    String summaryPrp = util.prpsrtcolumn("prp",indexSummarylprp);
                    String summarySrt = util.prpsrtcolumn("srt",indexSummarylprp);
                    
                shapeQ="select "+summaryPrp+" summary,"+summarySrt+" summary_so,\n" + 
                "count(*) qty,to_char(sum(cts),'99999990.00') cts " +conQ+ 
                " from gt_srch_rslt gt\n" + 
                "where nvl(cts,0) > 0  and gt.pkt_ty='"+pkt_ty+"'\n" +
                "group by "+summaryPrp+" ,"+summarySrt+"\n" + 
                "Order By summary_so";
                if(dta_typ.equals("T")){
                    shapeQ="select "+summaryPrp+" summary,"+summaryPrp+" summary_so,\n" + 
                    "count(*) qty,to_char(sum(cts),'99999990.00') cts " +conQ+ 
                    " from gt_srch_rslt gt\n" + 
                    "where nvl(cts,0) > 0  and gt.pkt_ty='"+pkt_ty+"'\n" +
                    "group by "+summaryPrp+" ,"+summaryPrp+"\n" + 
                    "Order By summary_so";                        
                }
                  rsLst = db.execSqlLst("Sum by shape", shapeQ, new ArrayList());
                  stmt =(PreparedStatement)rsLst.get(0);
                  rs =(ResultSet)rsLst.get(1);
                while(rs.next()){ 
                    data=new HashMap();   
                    String summsrt=util.nvl((String)rs.getString("summary_so"));
                    String key=summsrt;
                    data.put("SUMMARY",util.nvl((String)rs.getString("summary")));
                    data.put("QTY",util.nvl((String)rs.getString("qty")));
                    data.put("CTS",util.nvl((String)rs.getString("cts")));
                        for(int s=0;s<showprpLstsz;s++){
                        String showprp=(String)showprpLst.get(s);
                        data.put(showprp, util.nvl(rs.getString(showprp)));        
                        }
                    datattl.put(summarylprp+"_"+key,data);  
                    gtPrpList.add(summsrt);
                }
                    rs.close();  
                    stmt.close();
                    if(dta_typ.equals("T"))
                    Collections.sort(gtPrpList);
                    datattl.put(summarylprp+"_GTPRP",gtPrpList);
                }
                    
                String pky_typQ="select pkt_ty,\n" + 
                "count(*) qty,to_char(sum(cts),'99999990.00') cts "+ conQ+ 
                " from gt_srch_rslt gt\n" + 
                "where nvl(cts,0) > 0\n" +
                "group by pkt_ty\n" + 
                "union\n" + 
                "select 'TTL' pkt_ty,\n" + 
                "count(*) qty,to_char(sum(cts),'99999990.00') cts "+ conQ+ 
                " from gt_srch_rslt gt\n" + 
                "where nvl(cts,0) > 0 \n" + 
                "Order By 1";
                  rsLst = db.execSqlLst("Sum by pky_typ", pky_typQ, new ArrayList());
                  stmt =(PreparedStatement)rsLst.get(0);
                  rs =(ResultSet)rsLst.get(1);
                while(rs.next()){ 
                    data=new HashMap();   
                    String key=util.nvl((String)rs.getString("pkt_ty"));
                    data.put("QTY",util.nvl((String)rs.getString("qty")));
                    data.put("CTS",util.nvl((String)rs.getString("cts")));
                        for(int s=0;s<showprpLstsz;s++){
                        String showprp=(String)showprpLst.get(s);
                        data.put(showprp, util.nvl(rs.getString(showprp)));        
                        }
                    datattl.put(key,data);  
                }
                rs.close();  
                stmt.close();
                session.setAttribute(pkt_ty+"_dataDtl", dataDtl);
                session.setAttribute(pkt_ty+"_datattl", datattl);
                session.setAttribute(pkt_ty+"_keytable", keytable);
                session.setAttribute(pkt_ty+"_sttLst", sttLst);
                }
                req.setAttribute("view", "Y");
                req.setAttribute("shapebyrightside", shapebyrightside);
                session.setAttribute("pktTyGrid", pktTyGrid);
            }else{
                req.setAttribute("msg", "Please Enter Lot");
            }
            udf.reset();
            util.updAccessLog(req,res,"Rpt Action", "fetchlot end");
            return am.findForward("loadlot");
        }
    }
    
    public ActionForward LOTPKTDTL(ActionMapping am, ActionForm form,
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
            util.updAccessLog(req,res,"Report Action", "LOTPKTDTL start");
            GenericInterface genericInt = new GenericImpl();
        ArrayList pktList = new ArrayList();
        ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "LOT_RPT_VW","LOT_RPT_VW");
        int indexLB = vwPrpLst.indexOf("LOT_NO")+1;      
        String lbPrp = "";
        if(indexLB>0)
        lbPrp="to_number(substr("+util.prpsrtcolumn("prp",indexLB)+", instr("+util.prpsrtcolumn("prp",indexLB)+", '-')+1,length("+util.prpsrtcolumn("prp",indexLB)+"))) lotsrt,";
        else
        lbPrp="sk1 , cts,";
        String dsp_stt=util.nvl((String)req.getParameter("dsp_stt")).trim();
        String summaryLprp=util.nvl((String)req.getParameter("summaryLprp")).trim();
        String summaryLprpval=util.nvl((String)req.getParameter("summaryLprpval")).trim();
        String pkt_tygrid=util.nvl((String)req.getParameter("pkt_tygrid")).trim();
        ArrayList itemHdr = new ArrayList();
        ArrayList prpDspBlocked = info.getPageblockList();
        itemHdr.add("Sr");
        itemHdr.add("VNM");
        
        String conQ="";
        if(!dsp_stt.equals("ALL") && !dsp_stt.equals("")){
            conQ+=" and dsp_stt='"+dsp_stt+"'";   
        }
        if(!summaryLprp.equals("")){
            int indexSummarylprp = vwPrpLst.indexOf(summaryLprp)+1;
            String summaryPrp = util.prpsrtcolumn("prp",indexSummarylprp);
            conQ+=" and "+summaryPrp+"='"+summaryLprpval+"'";  
        }
        if(!pkt_tygrid.equals(""))
            conQ+=" and pkt_ty='"+pkt_tygrid+"'";  
        String  srchQ =  " select "+lbPrp+"sk1,stk_idn ,trunc(cts,2) cts, pkt_ty,  vnm, pkt_dte, stt ,dsp_stt,round(quot) quot, qty , rmk,prte,mrte,round(cts*quot) amt ";
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

            srchQ += ", " + fld;
            if(prpDspBlocked.contains(lprp)){
            }else{
            itemHdr.add(lprp);
                if(lprp.equals("SAL_RTE")){
                    itemHdr.add("P1");
                    itemHdr.add("P2");
                }
            if(lprp.equals("RTE")){
                itemHdr.add("AMT");
            }
        }}
        itemHdr.add("STATUS");
        
        int sr=1;
        String rsltQ = srchQ + " from gt_srch_rslt where cts <> 0 "+conQ+" order by 1,2,3";
        
         ArrayList rsLst = db.execSqlLst("search Result", rsltQ, new ArrayList());
        PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
        ResultSet  rs =(ResultSet)rsLst.get(1);
        try {
            while(rs.next()) {

                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("Sr", String.valueOf(sr++));
                pktPrpMap.put("STATUS", util.nvl(rs.getString("dsp_stt")));
                String vnm = util.nvl(rs.getString("vnm"));
                pktPrpMap.put("VNM",vnm);
                pktPrpMap.put("stk_idn",util.nvl(rs.getString("stk_idn")));
                pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
                pktPrpMap.put("P1",util.nvl(rs.getString("prte")));
                pktPrpMap.put("P2",util.nvl(rs.getString("mrte")));
                pktPrpMap.put("AMT",util.nvl(rs.getString("amt")));
                for(int j=0; j < vwPrpLst.size(); j++){
                     String prp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                      
                      String val = util.nvl(rs.getString(fld)) ;
                             if(prp.equals("RTE")){
                                 val = util.nvl(rs.getString("quot")) ;
                             }
                             if(prp.equals("CRTWT")){
                                 val = util.nvl(rs.getString("cts")) ;
                             }
                        pktPrpMap.put(prp, val);
                         }
                              
                    pktList.add(pktPrpMap);
                }
            rs.close();
            stmt.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
        session.setAttribute("pktList", pktList);
        session.setAttribute("itemHdr",itemHdr);
            util.updAccessLog(req,res,"Report Action", "LOTPKTDTL end");
        return am.findForward("pktDtl");
        }
    }
    
    
    public ActionForward loadProcesslot(ActionMapping am, ActionForm form, HttpServletRequest req,
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
            LotReportForm udf = (LotReportForm)form;
            udf.reset();
            util.updAccessLog(req,res,"Rpt Action", "loadProcesslot start");
            GenericInterface genericInt = new GenericImpl();
            ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "LOT_PRC_VW","LOT_PRC_VW");
            util.updAccessLog(req,res,"Rpt Action", "loadProcesslot end");
            return am.findForward("loadProcesslot");
        }
    }
    
    public ActionForward fetchProcesslot(ActionMapping am, ActionForm form, HttpServletRequest req,
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
            LotReportForm udf = (LotReportForm)form;
            util.updAccessLog(req,res,"Rpt Action", "loadProcesslot start");
            GenericInterface genericInt = new GenericImpl();
            ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "LOT_PRC_VW","LOT_PRC_VW");
            int indexLB = vWPrpList.indexOf("CP")+1;
            String cpPrp = util.prpsrtcolumn("prp",indexLB);
            String lot=util.nvl((String)udf.getValue("lot")).trim();
            String msg="";
            if(!lot.equals("")){
                String delQ = " Delete from GT_SRCH_MULTI ";
                int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
                ArrayList params = new ArrayList();
                String srchRef ="insert into GT_SRCH_MULTI(srch_id,rln_idn,stk_idn,grp, vnm, qty, cts, cmp, quot, stt, flg, rap_rte, rap_dis, sk1,byr,pkt_dte,rmk,emp,sl_dte,sl_idn,pair_id)\n" + 
                "select b.iss_id,b.iss_emp_id,a.idn,b.pkt_rt, a.vnm, nvl(a.qty, 1) qty, nvl(b.iss_cts,0), nvl(a.upr, a.cmp), (nvl(a.upr, a.cmp)), a.stt, 'Z', a.rap_rte, a.upr_dis, a.sk1,\n" + 
                "byr.get_nm(b.iss_emp_id),b.iss_dt,null,d.prc,b.rtn_dt,d.idn,d.srt\n" + 
                "from mstk a, iss_rtn_dtl b ,iss_rtn c, mprc d\n" + 
                "where a.idn = b.iss_stk_idn\n" + 
                "and pkt_ty='MIX'\n" + 
                "and c.prc_id=d.idn\n" + 
                "and b.stt ='RT'\n" + 
                "and a.stt<>'SUS'\n" + 
                "and exists (select 1 from stk_dtl b where a.idn=b.mstk_idn and b.grp=1 and b.mprp='MFG_LOT_NO' and b.txt=?)\n" + 
                "and b.iss_id=c.iss_id";
                params.add(lot);
                ct = db.execUpd("insert gt", srchRef, params);
                if(ct>0){
                    String pktPrp = "srch_pkg.POP_PKT_PRP_TEST(pMdl=>?,pTbl=>'GT_SRCH_MULTI')";
                    params = new ArrayList();
                    params.add("LOT_PRC_VW");
                    ct = db.execCall(" Srch Prp ", pktPrp, params);
                    
                    ct = db.execUpd("insert gt", "update GT_SRCH_MULTI gt set cts = \n" + 
                    "(select ip.iss_num from iss_rtn_prp ip where gt.srch_id = ip.iss_id and gt.stk_idn = ip.iss_stk_idn and ip.mprp = 'CRTWT') where gt.grp is null", new ArrayList());
                    
                    ct = db.execUpd("insert gt", "update GT_SRCH_MULTI gt set cts = \n" + 
                    "(select ip.rtn_num from iss_rtn_prp ip where gt.srch_id = ip.iss_id and gt.stk_idn = ip.iss_stk_idn and ip.mprp = 'CRTWT') where gt.grp is not null", new ArrayList());
                    
                    ArrayList pktList = new ArrayList();
                    ArrayList issueIdDtlLst = new ArrayList();
                    ArrayList processLst = new ArrayList();
                    HashMap dtl=new HashMap();
                    HashMap dataDtl=new HashMap();
                    String pPrc="";
                    
                    String rsltQ="select * from mlot where dsc=?";
                    params = new ArrayList();
                    params.add(lot);
                  ArrayList  rsLst = db.execSqlLst("search", rsltQ, params);
                  PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
                  ResultSet  rs =(ResultSet)rsLst.get(1);
                    while (rs.next()) {
                        dataDtl.put("LOTNO", util.nvl(rs.getString("DSC")));
                        dataDtl.put("LP_CTS", util.nvl(rs.getString("LP_CTS")));
                        dataDtl.put("TTL_STONE", util.nvl(rs.getString("TTL_STONE")));
                        dataDtl.put("REJ_CTS", util.nvl(rs.getString("REJ_CTS")));
                        dataDtl.put("LBPERSTONE", util.nvl(rs.getString("LBPERSTONE")));
                        dataDtl.put("RGH_CTS", util.nvl(rs.getString("RGH_CTS")));
                        dataDtl.put("EXH_RTE", util.nvl(rs.getString("EXH_RTE")));
                        dataDtl.put("TTL_VLU", util.nvl(rs.getString("TTL_VLU")));
                    }
                    rs.close(); 
                    stmt.close();
                    
                    rsltQ="select  sum(cts) cts,trunc(sum(cts*quot),2) vlu,\n" + 
                    "trunc(sum(cts*nvl("+cpPrp+",quot)),2) cpvlu,srch_id iss_id,emp prc ,pair_id srt\n" + 
                    "from\n" + 
                    "GT_SRCH_MULTI where grp is null\n" + 
                    "group by srch_id,emp,pair_id\n" + 
                    "order by pair_id,srch_id";
                   rsLst = db.execSqlLst("search", rsltQ, new ArrayList());
                  stmt =(PreparedStatement)rsLst.get(0);
                    rs =(ResultSet)rsLst.get(1);
                    while (rs.next()) {
                        String prc=util.nvl(rs.getString("prc"));
                        if(!processLst.contains(prc))
                        processLst.add(prc);
                        if(pPrc.equals(""))
                            pPrc=prc;
                        if(!pPrc.equals(prc)){
                            dataDtl.put(pPrc, issueIdDtlLst);
                            issueIdDtlLst = new ArrayList();
                            pPrc=prc;
                        }
                        dtl=new HashMap();
                        dtl.put("ISS_ID", util.nvl(rs.getString("iss_id")));
                        dtl.put("CTS", util.nvl(rs.getString("cts")));
                        dtl.put("VLU", util.nvl(rs.getString("vlu")));
                        dtl.put("CPVLU", util.nvl(rs.getString("cpvlu")));
                        issueIdDtlLst.add(dtl);
                    }
                    rs.close();
                    stmt.close();
                    
                    if(!pPrc.equals(""))
                        dataDtl.put(pPrc, issueIdDtlLst);
                    
                    rsltQ="select  sum(qty) qty,sum(cts) cts,trunc(sum(cts*quot),2) vlu,\n" + 
                    "trunc(sum(cts*nvl("+cpPrp+",quot)),2) cpvlu,srch_id iss_id,emp prc ,pair_id srt\n" + 
                    "from\n" + 
                    "GT_SRCH_MULTI where grp is not null\n" + 
                    "group by srch_id,emp,pair_id\n" + 
                    "order by pair_id,srch_id";
                  rsLst = db.execSqlLst("search", rsltQ, new ArrayList());
                  stmt =(PreparedStatement)rsLst.get(0);
                   rs =(ResultSet)rsLst.get(1);
                    while (rs.next()) {
                        String issue_id=util.nvl(rs.getString("iss_id"));
                        dtl=new HashMap();
                        dtl.put("CTS", util.nvl(rs.getString("cts")));
                        dtl.put("VLU", util.nvl(rs.getString("vlu")));
                        dtl.put("CPVLU", util.nvl(rs.getString("cpvlu")));
                        dtl.put("QTY", util.nvl(rs.getString("qty")));
                        dataDtl.put(issue_id+"_TTL", dtl);
                    }
                    rs.close();   
                    stmt.close();
                    
                    String pIssue_id="";
                    String  srchQ =  " select srch_id issue_id,grp,vnm,qty,quot,cts,trunc(cts*quot,2) vlu,trunc(cts*nvl("+cpPrp+",quot),2) cpvlu,sk1  ";
                    for (int i = 0; i < vWPrpList.size(); i++) {
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

                    
                    rsltQ = srchQ + " from GT_SRCH_MULTI where grp is not null order by srch_id,grp,sk1 ";
                  rsLst = db.execSqlLst("search", rsltQ, new ArrayList());
                  stmt =(PreparedStatement)rsLst.get(0);
                   rs =(ResultSet)rsLst.get(1);
                    while (rs.next()) {
                        String issue_id=util.nvl(rs.getString("issue_id"));
                        if(pIssue_id.equals(""))
                            pIssue_id=issue_id;
                        if(!pIssue_id.equals(issue_id)){
                            dataDtl.put(pIssue_id, pktList);
                            pIssue_id=issue_id;
                            pktList = new ArrayList();
                        }
                        HashMap pktPrpMap = new HashMap();
                        pktPrpMap.put("vnm", util.nvl(rs.getString("vnm")));
                        pktPrpMap.put("qty", util.nvl(rs.getString("qty")));
                        pktPrpMap.put("cts", util.nvl(rs.getString("cts")));
                        pktPrpMap.put("vlu", util.nvl(rs.getString("vlu")));
                        pktPrpMap.put("cpvlu", util.nvl(rs.getString("cpvlu")));
                        pktPrpMap.put("pkt_rt", util.nvl(rs.getString("grp")));
                        pktPrpMap.put("rte", util.nvl(rs.getString("quot")));
                        for(int j=0; j < vWPrpList.size(); j++){
                             String prp = (String)vWPrpList.get(j);
                              
                              String fld="prp_";
                              if(j < 9)
                                      fld="prp_00"+(j+1);
                              else    
                                      fld="prp_0"+(j+1);
                              
                              String val = util.nvl(rs.getString(fld)) ;
                              if(prp.equals("CRTWT"))
                              val = util.nvl(rs.getString("cts"));
                              if(prp.equals("RTE"))
                              val = util.nvl(rs.getString("quot"));
                              if (prp.toUpperCase().equals("RFIDCD"))
                              val = util.nvl(rs.getString("tfl3"));
                            pktPrpMap.put(prp, val);
                        }
                        pktList.add(pktPrpMap);
                    }
                    rs.close();
                    stmt.close();

                    if(!pIssue_id.equals(""))
                        dataDtl.put(pIssue_id, pktList);
                    req.setAttribute("view", "Y");
                    session.setAttribute("lotPrcDataprocessLst", processLst);
                    session.setAttribute("lotPrcDatadataDtl", dataDtl);
                }else{
                    msg="No Data Found";
                }
            }else{
                msg="Please Specify Lot No";
            }
            req.setAttribute("msg", msg);
            util.updAccessLog(req,res,"Rpt Action", "loadProcesslot end");
            return am.findForward("loadProcesslot");
        }
    }
    
    
    
    public ActionForward loadRghProcesslot(ActionMapping am, ActionForm form, HttpServletRequest req,
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
            LotReportForm udf = (LotReportForm)form;
            udf.reset();
            util.updAccessLog(req,res,"Rpt Action", "loadRghProcesslot start");
            GenericInterface genericInt = new GenericImpl();
            ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "LOT_RGHPRC_VW","LOT_RGHPRC_VW");
            util.updAccessLog(req,res,"Rpt Action", "loadRghProcesslot end");
            return am.findForward("loadRghProcesslot");
        }
    }
    
    public ActionForward fetchRghProcesslot(ActionMapping am, ActionForm form, HttpServletRequest req,
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
            LotReportForm udf = (LotReportForm)form;
            util.updAccessLog(req,res,"Rpt Action", "fetchRghProcesslot start");
            GenericInterface genericInt = new GenericImpl();
            ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "LOT_RGHPRC_VW","LOT_RGHPRC_VW");
            ArrayList lotPrpList = genericInt.genericPrprVw(req, res, "LOT_CLUS","LOT_CLUS");
            int indexLB = vWPrpList.indexOf("CP")+1;
            String cpPrp = util.prpsrtcolumn("prp",indexLB);
            String lot=util.nvl((String)udf.getValue("lot")).trim();
            String msg="";
            if(!lot.equals("")){
                String delQ = " Delete from GT_SRCH_MULTI ";
                int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
                ArrayList params = new ArrayList();
                HashMap dtl=new HashMap();
                HashMap dataDtl=new HashMap();
                ArrayList processLst = new ArrayList();

                String srchRef ="insert into GT_SRCH_MULTI(srch_id,rln_idn,stk_idn,grp, vnm, qty, cts, cmp, quot, stt, flg, rap_rte, rap_dis, sk1,byr,pkt_dte,rmk,emp,sl_dte,sl_idn,pair_id)\n" + 
                "select b.iss_id,b.iss_emp_id,a.idn,b.pkt_rt, a.vnm, nvl(a.qty, 1) qty, nvl(b.iss_cts,0), nvl(a.upr, a.cmp), (nvl(a.upr, a.cmp)), a.stt, 'Z', a.rap_rte, a.upr_dis, a.sk1,\n" + 
                "byr.get_nm(b.iss_emp_id),b.iss_dt,null,d.prc,b.rtn_dt,d.idn,d.srt\n" + 
                "from mstk a, iss_rtn_dtl b ,iss_rtn c, mprc d\n" + 
                "where a.idn = b.iss_stk_idn\n" + 
                "and pkt_ty='RGH'\n" + 
                "and c.prc_id=d.idn and d.grp in ('RHGPUR','RGHMRG')\n" + 
                "and b.stt in ('RT','IS') \n" + 
                "and a.stt <>'SUS'\n" + 
                "and exists (select 1 from stk_dtl b where a.idn=b.mstk_idn and b.grp=1 and b.mprp='LOTNO' and b.txt=?)\n" + 
                "and b.iss_id=c.iss_id";
                params.add(lot);
                ct = db.execUpd("insert gt", srchRef, params);
                if(ct>0){
                    String pktPrp = "srch_pkg.POP_PKT_PRP_TEST(pMdl=>?,pTbl=>'GT_SRCH_MULTI')";
                    params = new ArrayList();
                    params.add("LOT_RGHPRC_VW");
                    ct = db.execCall(" Srch Prp ", pktPrp, params);
                    
                    ct = db.execUpd("insert gt", "update GT_SRCH_MULTI gt set cts = \n" + 
                    "(select ip.iss_num from iss_rtn_prp ip where gt.srch_id = ip.iss_id and gt.stk_idn = ip.iss_stk_idn and ip.mprp = 'CRTWT') where gt.grp is null", new ArrayList());
                    
                    ct = db.execUpd("insert gt", "update GT_SRCH_MULTI gt set cts = \n" + 
                    "(select ip.rtn_num from iss_rtn_prp ip where gt.srch_id = ip.iss_id and gt.stk_idn = ip.iss_stk_idn and ip.mprp = 'CRTWT') where gt.grp is not null", new ArrayList());
                    
                    ArrayList pktList = new ArrayList();
                    ArrayList issueIdDtlLst = new ArrayList();
                  
                    String pPrc="";
                    ArrayList pktLst = new ArrayList();
                    
                  
                    
//                    rsltQ="with p as (\n" + 
//                    "   select pur_idn, nvl(aadat_comm, 0) + nvl(mbrk1_comm, 0) comm, nvl(ship_chgs, 0)+nvl(other_exp, 0) chgs \n" + 
//                    "       from mpur \n" + 
//                    "   where lot_dsc = ?\n" + 
//                    ")\n" + 
//                    "select sum(d.cts*d.rte) invVlu, (sum(d.cts*d.rte)*((100 + p.comm)/100)) + p.chgs ttlCost\n" + 
//                    "   from p, pur_dtl d \n" + 
//                    "where p.pur_idn = d.pur_idn \n" + 
//                    "group by p.comm, p.chgs";
//                    params = new ArrayList();
//                    params.add(lot);
//                    rs = db.execSql("search", rsltQ, params);
//                    while (rs.next()) {
//                        dataDtl.put("inv_vlu", util.nvl(rs.getString("invVlu")));
//                        dataDtl.put("fnlttl_vlu", util.nvl(rs.getString("ttlCost")));
//                    }
//                    rs.close();  
                     String rsltQ="select  sum(qty) qty,sum(cts) cts,to_char(sum(cts*quot),'999999999999.99') vlu,\n" + 
                "to_char(sum(cts*nvl("+cpPrp+",quot)),'999999999999.99') cpvlu,srch_id iss_id,emp prc , byr , pair_id srt\n" + 
                "from\n" + 
                "GT_SRCH_MULTI where grp is not null or  stt like 'RGH_MRG%' \n" + 
                "group by srch_id,emp,byr,pair_id\n" + 
                "order by pair_id,srch_id";
                  ArrayList  rsLst = db.execSqlLst("search", rsltQ, new ArrayList());
                  PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
                  ResultSet  rs =(ResultSet)rsLst.get(1);
                while (rs.next()) {
                    String issue_id=util.nvl(rs.getString("iss_id"));
                    dtl=new HashMap();
                    dtl.put("CTS", util.nvl(rs.getString("cts")));
                    dtl.put("VLU", util.nvl(rs.getString("vlu")));
                    dtl.put("CPVLU", util.nvl(rs.getString("cpvlu")));
                    dtl.put("QTY", util.nvl(rs.getString("qty")));
                    dtl.put("PRC", util.nvl(rs.getString("prc")));
                    dtl.put("BYR", util.nvl(rs.getString("byr")));
                    dataDtl.put(issue_id+"_TTL", dtl);
                }
                rs.close(); 
                stmt.close();
                
                  
                    
                    rsltQ="select b.ref_idn,b.cts,b.rte,b.cp, to_char((b.cp*b.cts),'999999999999.990') cpvlu, b.trf_dte,b.qty,b.rgh_sz,b.article,b.vlu\n" + 
                    "from mpur a,pur_dtl b where 1 =1  and a.typ in('LOT','TENDER') \n" + 
                    "and nvl(a.flg2,'NA') not in('DEL') and a.lot_dsc = ? and a.pur_idn=b.pur_idn and nvl(b.flg,'NA') <> 'DEL' order by 1";
                    params = new ArrayList();
                    params.add(lot);
                  rsLst = db.execSqlLst("search", rsltQ, params);
                  stmt =(PreparedStatement)rsLst.get(0);
                  rs =(ResultSet)rsLst.get(1);
                    while (rs.next()) {
                        HashMap data=new HashMap();
                        data.put("ref_idn", util.nvl(rs.getString("ref_idn")));
                        data.put("cts", util.nvl(rs.getString("cts")));
                        data.put("rte", util.nvl(rs.getString("rte")));
                        data.put("trf_dte", util.nvl(rs.getString("trf_dte")));
                        data.put("qty", util.nvl(rs.getString("qty")));
                        data.put("rgh_sz", util.nvl(rs.getString("rgh_sz")));
                        data.put("article", util.nvl(rs.getString("article")));
                        data.put("vlu", util.nvl(rs.getString("vlu")));
                        data.put("cp", util.nvl(rs.getString("cp")));
                        data.put("cpvlu", util.nvl(rs.getString("cpvlu")));
                        pktLst.add(data);
                    }
                    rs.close();
                    stmt.close();
                    
                    rsltQ="select  sum(cts) cts,to_char(sum(cts*quot),'999999999999.99') vlu,\n" + 
                    "to_char(sum(cts*nvl("+cpPrp+",quot)),'999999999999.99') cpvlu,srch_id iss_id,emp prc ,byr,pair_id srt\n" + 
                    "from\n" + 
                    "GT_SRCH_MULTI where grp is null\n" + 
                    "group by srch_id,emp,byr,pair_id\n" + 
                    "order by pair_id,srch_id";
                  rsLst = db.execSqlLst("search", rsltQ, new ArrayList());
                  stmt =(PreparedStatement)rsLst.get(0);
                  rs =(ResultSet)rsLst.get(1);
                    while (rs.next()) {
                        String prc=util.nvl(rs.getString("prc"));
                        if(!processLst.contains(prc))
                        processLst.add(prc);
                        if(pPrc.equals(""))
                            pPrc=prc;
                        if(!pPrc.equals(prc)){
                            dataDtl.put(pPrc, issueIdDtlLst);
                            issueIdDtlLst = new ArrayList();
                            pPrc=prc;
                        }
                        dtl=new HashMap();
                        dtl.put("ISS_ID", util.nvl(rs.getString("iss_id")));
                        dtl.put("CTS", util.nvl(rs.getString("cts")));
                        dtl.put("VLU", util.nvl(rs.getString("vlu")));
                        dtl.put("CPVLU", util.nvl(rs.getString("cpvlu")));
                        dtl.put("BYR", util.nvl(rs.getString("byr")));
                        issueIdDtlLst.add(dtl);
                    }
                    rs.close();
                    stmt.close();
                    
                    if(!pPrc.equals(""))
                        dataDtl.put(pPrc, issueIdDtlLst);
                    
                    
                    String pIssue_id="";
                    String  srchQ =  " select srch_id issue_id,grp,vnm,qty,quot,cts,trunc(cts*quot,2) vlu,trunc(cts*nvl("+cpPrp+",quot),2) cpvlu,sk1  ";
                    for (int i = 0; i < vWPrpList.size(); i++) {
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

                    
                    rsltQ = srchQ + " from GT_SRCH_MULTI where grp is not null or stt like 'RGH_MRG%' order by srch_id,grp,sk1 ";
                  rsLst = db.execSqlLst("search", rsltQ, new ArrayList());
                  stmt =(PreparedStatement)rsLst.get(0);
                  rs =(ResultSet)rsLst.get(1);
                    while (rs.next()) {
                        String issue_id=util.nvl(rs.getString("issue_id"));
                        if(pIssue_id.equals(""))
                            pIssue_id=issue_id;
                        if(!pIssue_id.equals(issue_id)){
                            dataDtl.put(pIssue_id, pktList);
                            pIssue_id=issue_id;
                            pktList = new ArrayList();
                        }
                        HashMap pktPrpMap = new HashMap();
                        pktPrpMap.put("vnm", util.nvl(rs.getString("vnm")));
                        pktPrpMap.put("qty", util.nvl(rs.getString("qty")));
                        pktPrpMap.put("cts", util.nvl(rs.getString("cts")));
                        pktPrpMap.put("vlu", util.nvl(rs.getString("vlu")));
                        pktPrpMap.put("cpvlu", util.nvl(rs.getString("cpvlu")));
                        pktPrpMap.put("pkt_rt", util.nvl(rs.getString("grp")));
                        pktPrpMap.put("rte", util.nvl(rs.getString("quot")));
                        for(int j=0; j < vWPrpList.size(); j++){
                             String prp = (String)vWPrpList.get(j);
                              
                              String fld="prp_";
                              if(j < 9)
                                      fld="prp_00"+(j+1);
                              else    
                                      fld="prp_0"+(j+1);
                              
                              String val = util.nvl(rs.getString(fld)) ;
                              if(prp.equals("CRTWT"))
                              val = util.nvl(rs.getString("cts"));
                              if(prp.equals("RTE"))
                              val = util.nvl(rs.getString("quot"));
                              if (prp.toUpperCase().equals("RFIDCD"))
                              val = util.nvl(rs.getString("tfl3"));
                            pktPrpMap.put(prp, val);
                        }
                        pktList.add(pktPrpMap);
                    }
                    rs.close();
                    stmt.close();

                    if(!pIssue_id.equals(""))
                        dataDtl.put(pIssue_id, pktList);
                    
                    dataDtl.put("PKTLIST", pktLst);
                  
                }
                
              
                
                String xrtRte = "";
                String rsltQ="select pur_idn, vndr_idn, byr.get_nm(vndr_idn) vndr, to_char(pur_dte, 'dd-mm-rrrr') pur_dte, \n" + 
                "typ, trms_idn, byr.get_mtrm(trms_idn) trms, aadat_idn, byr.get_nm(aadat_idn) aadat, \n" + 
                "aadat_comm, mbrk1_idn, byr.get_nm(mbrk1_idn) mbrk1, mbrk1_comm, ttl_qty, ttl_cts, \n" + 
                "avg_rte, ttl_vlu, lot_dsc, miner, mine, rgh_typ, sight, exh_rte, \n" + 
                "ship_chgs, other_exp,((ttl_vlu)*(100 + (nvl(aadat_comm,0)+ nvl(mbrk1_comm,0)))/100)+nvl(ship_chgs,0)+nvl(other_exp,0) fnlttl_vlu  from mpur where 1 =1  and typ in('LOT','TENDER') \n" + 
                "and nvl(flg2,'NA') not in('DEL') and lot_dsc = ?";
                params = new ArrayList();
                params.add(lot);
               ArrayList rsLst = db.execSqlLst("search", rsltQ, params);
              PreparedStatement stmt =(PreparedStatement)rsLst.get(0);
              ResultSet rs =(ResultSet)rsLst.get(1);
                while (rs.next()) {
                    dataDtl.put("vndr", util.nvl(rs.getString("vndr")));
                    dataDtl.put("pur_dte", util.nvl(rs.getString("pur_dte")));
                    dataDtl.put("trms", util.nvl(rs.getString("trms")));
                    dataDtl.put("aadat", util.nvl(rs.getString("aadat")));
                    dataDtl.put("aadat_comm", util.nvl(rs.getString("aadat_comm")));
                    dataDtl.put("mbrk1", util.nvl(rs.getString("mbrk1")));
                    dataDtl.put("mbrk1_comm", util.nvl(rs.getString("mbrk1_comm")));
                    dataDtl.put("ttl_qty", util.nvl(rs.getString("ttl_qty")));
                    dataDtl.put("ttl_cts", util.nvl(rs.getString("ttl_cts")));
                    dataDtl.put("inv_vlu", util.nvl(rs.getString("ttl_vlu")));
                    dataDtl.put("lot_dsc", util.nvl(rs.getString("lot_dsc")));
                    dataDtl.put("miner", util.nvl(rs.getString("miner")));
                    dataDtl.put("mine", util.nvl(rs.getString("mine")));
                    dataDtl.put("rgh_typ", util.nvl(rs.getString("rgh_typ")));
                    dataDtl.put("sight", util.nvl(rs.getString("sight")));
                    dataDtl.put("exh_rte", util.nvl(rs.getString("exh_rte")));
                    dataDtl.put("ship_chgs", util.nvl(rs.getString("ship_chgs")));
                    dataDtl.put("other_exp", util.nvl(rs.getString("other_exp")));
                    dataDtl.put("fnlttl_vlu", util.nvl(rs.getString("fnlttl_vlu")));
                    xrtRte=util.nvl(rs.getString("exh_rte"));
                }
                rs.close();  
                stmt.close();
                double ttlCpVlu=0;
                double ttlCp=0;
                double ttlIssCts=0;
                double ttlRghCts=0;
                double ttlPolCts=0;
                double ttlpcp=0;
                ArrayList polishList = new ArrayList();
                String totalRghCluser="select b.idn , get_xrt('INR') xrt, b.mstk_idn,byr.get_nm(c.nme_idn) byr, b.stt , b.cts , a.vnm , TO_CHAR(c.DTE,'dd-MON-rrrr') issDte \n" + 
                "from mstk a, jandtl b ,mjan c\n" + 
                "where a.idn = b.MSTK_IDN\n" + 
                "and b.idn = c.idn \n" + 
                "and a.pkt_ty= ? \n" + 
                "and b.flg = ?  \n" + 
                "and a.stt <> 'SUS'\n" + 
                "and exists (select 1 from stk_dtl b where a.idn=b.mstk_idn and b.grp=1 and b.mprp=? and b.txt=? )\n" + 
                "order by c.idn desc";
                params = new ArrayList();
                params.add("RGH");
                params.add("SP");/*  */
                params.add("LOTNO");
                params.add(lot);
                ArrayList rsList = db.execSqlLst("totalRghCluser", totalRghCluser, params);
                PreparedStatement pst = (PreparedStatement)rsList.get(0);
                rs = (ResultSet)rsList.get(1);
                while(rs.next()){
                    HashMap polishMap = new HashMap();
                    String idn = rs.getString("idn");
                    String mstk_idn = rs.getString("mstk_idn");
                    String vnm = rs.getString("vnm");
                    String stt = rs.getString("stt");
                    String cts = util.nvl(rs.getString("cts"),"0");
                    String dte = rs.getString("issDte");
                    String byr = rs.getString("byr");
                   
                    double ctsd = Double.parseDouble(cts);
                   
                    polishMap.put("STKIDN", mstk_idn);
                    polishMap.put("STT", stt);
                    polishMap.put("ISSCTS", cts);
                    polishMap.put("ISSIDN", idn);
                    polishMap.put("VNM", vnm);
                    polishMap.put("BYR", byr);
                    polishMap.put("ISSDTE", dte);
                    double labper =0;
                    double cp =0;
                    double cpVlu=0;
                    if(stt.equals("RT")){
                    String stkDtlSql=" select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val \n" + 
                    " from stk_dtl a, mprp b, rep_prp c \n" + 
                    "   where a.mprp = b.prp and b.prp = c.prp and c.mdl = ? and grp=? and a.mstk_idn = ? "+
                    "    order by c.rnk, c.srt ";
                      
                    params = new ArrayList();
                    params.add("LOT_CLUS");
                    params.add("1");
                    params.add(mstk_idn);
                    ArrayList rsList1 = db.execSqlLst("stkDtlSql", stkDtlSql, params);
                    PreparedStatement pst1 = (PreparedStatement)rsList1.get(0);
                    ResultSet rs1 = (ResultSet)rsList1.get(1);
                    while(rs1.next()){
                        String mprp = util.nvl(rs1.getString("mprp"));
                        String val = util.nvl(rs1.getString("val"));
                        if(mprp.equals("CP") && !val.equals("")){
                           cp= Double.parseDouble(val);
                         cpVlu = util.roundToDecimals(cp*ctsd,2);
                            polishMap.put("CPVLU", String.valueOf(cpVlu));
                            ttlCpVlu=ttlCpVlu+cpVlu;
                            ttlCp=ttlCp+cp;
                        }
                        if(mprp.equals("LBPERSTONE") && !val.equals(""))
                             labper = Double.parseDouble(val);
                        if(mprp.equals("EXH_RTE") && !val.equals(""))
                            xrtRte=val;
                        polishMap.put(mprp, val);
                    }
                    rs1.close();
                    pst1.close();
                    }
                    if(xrtRte.equals(""))
                        xrtRte=rs.getString("xrt");
                    ttlIssCts=ttlIssCts+ctsd;
                    double xrtRteD = Double.parseDouble(xrtRte);
                   double  pcp= util.roundToDecimals(((labper/xrtRteD)+cpVlu),2);
                    polishMap.put("PCP", String.valueOf(pcp));
                    ttlpcp=ttlpcp+pcp;
                    String ttlCts = "select sum(a.cts) polCts,sum(b.num) rhgCts from jandtl a ,stk_dtl b  \n" + 
                    "where a.idn=? and a.flg=? \n" + 
                    "and a.mstk_idn=b.mstk_idn and b.mprp=? and b.grp=?";
                    double polCtsd=0;
                    double rghCtsd=0;
                    params = new ArrayList();
                    params.add(idn);
                    params.add("MR");
                    params.add("RGH_CTS");
                    params.add("1");
                    ArrayList  rsList1 = db.execSqlLst("ttlCts", ttlCts, params);
                    PreparedStatement pst1 = (PreparedStatement)rsList1.get(0);
                    ResultSet rs1 = (ResultSet)rsList1.get(1);
                    while(rs1.next()){
                        String polCts = util.nvl(rs1.getString("polCts"),"0");
                        String rhgCts = util.nvl(rs1.getString("rhgCts"),"0");
                        polishMap.put("POLCTS", polCts);
                        polishMap.put("RGHCTS", rhgCts);
                        polCtsd=Double.parseDouble(polCts);
                        rghCtsd=Double.parseDouble(rhgCts);
                    }
                    rs1.close();
                    pst1.close();
                    ttlPolCts=ttlPolCts+polCtsd;
                    ttlRghCts=ttlRghCts+rghCtsd;
                    polishList.add(polishMap);
                    
                    ttlCpVlu=util.roundToDecimals(ttlCpVlu,2);
                    ttlCp = util.roundToDecimals(ttlCp,2);
                    ttlpcp=util.roundToDecimals(ttlpcp,2);
                }
                HashMap ttlPolishMap = new HashMap();
                ttlPolishMap.put("TTLRGHCTS", String.valueOf(ttlRghCts));
                ttlPolishMap.put("TTLPOLCTS", String.valueOf(ttlPolCts));
                ttlPolishMap.put("TTLCPVAL", String.valueOf(ttlCpVlu));
                ttlPolishMap.put("TTLCP", String.valueOf(ttlCp));
                ttlPolishMap.put("TTLISSCTS", String.valueOf(ttlIssCts));
                ttlPolishMap.put("TTLPCP", String.valueOf(ttlpcp));
                rs.close();
                pst.close();
                req.setAttribute("view", "Y");
                req.setAttribute("POLISHDATALIST", polishList);
                req.setAttribute("TTLPOLISHDATALIST", ttlPolishMap);
                req.setAttribute("lotRghPrcDataprocessLst", processLst);
                req.setAttribute("lotRghPrcDatadataDtl", dataDtl);
            }else{
                msg="Please Specify Lot No";
            }
            req.setAttribute("msg", msg);
            util.updAccessLog(req,res,"Rpt Action", "fetchRghProcesslot end");
            return am.findForward("loadRghProcesslot");
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
                util.updAccessLog(req,res,"Rpt Action", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Rpt Action", "init");
            }
            }
            return rtnPg;
            }
}
