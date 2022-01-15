package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.PacketLookupForm;

import java.io.IOException;

import java.io.OutputStream;

import java.net.InetSocketAddress;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.spy.memcached.MemcachedClient;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class MixStockSummaryAction  extends DispatchAction {

    public MixStockSummaryAction() {
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
            util.getOpenCursorConnection(db,util,info);
        util.updAccessLog(req,res,"Mix Stock Summary", "load start");
        GenericInterface genericInt = new GenericImpl();
        MixStockSummaryForm mixStockSummary = (MixStockSummaryForm)form;
            String typ=util.nvl(req.getParameter("typ"));
            String location=util.nvl(req.getParameter("LOC"));
            String df="MIX_STOCK_SUMMARY";
            if(typ.equals("RGH"))
                df="RGH_STOCK_SUMMARY";
            String mlot=util.nvl(req.getParameter("mlot"));
            String mlotQ="";
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get(df);
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef(df);
            allPageDtl.put(df,pageDtl);
            }
            info.setPageDetails(allPageDtl);
            ArrayList pageList=new ArrayList();
            HashMap pageDtlMap=new HashMap();
            ArrayList vWPrpList=new ArrayList();
            if(typ.equals("RGH")){
                String srch_pkg = "DP_GROUP_REPORT(pPktTyp => ? ,pMdl => ?)"; 
                ArrayList ary = new ArrayList();
                ary.add("RGH");
                ary.add("GROUP_RGH_VIEW");
                int ct = db.execCall("stk_srch", srch_pkg, ary);
                 vWPrpList=genericInt.genericPrprVw(req, res, "GROUP_RGH_VIEW","GROUP_RGH_VIEW");

            }else{
       
            vWPrpList=genericInt.genericPrprVw(req, res, "GROUP_MIX_REPORT","GROUP_MIX_VIEW");

        if(mlot.equals("")){
        String srch_pkg = "srch_pkg.GRP3_MIX(pMdl => ?)"; 
        ArrayList ary = new ArrayList();
        ary.add("GROUP_MIX_VIEW");
        int ct = db.execCall("stk_srch", srch_pkg, ary);
        }else{
        int ct = db.execCall("gtrefresh","PKGMKT.GT_REFRESH", new ArrayList());
        String pktPrp = "DP_GT_PRP_UPD(pMdl => ?)";
        ArrayList ary = new ArrayList();
        ary.add("GROUP_MIX_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary); 
        }}
        HashMap dbinfo = info.getDmbsInfoLst();
        String faprival = "FA_PRI";
        String mfgprival = "MFG_PRI";
        String level1="DEPT";
        pageList=(ArrayList)pageDtl.get("LEVEL1");
        if(pageList!=null && pageList.size() >0){
        for(int j=0;j<pageList.size();j++){
        pageDtlMap=(HashMap)pageList.get(j);
        level1=(String)pageDtlMap.get("dflt_val");
        }}
        String sh = (String)dbinfo.get("SHAPE");
        String locVal=util.nvl((String)dbinfo.get("LOC"));    
        int indexSH = vWPrpList.indexOf(sh)+1;
        int indexClr = vWPrpList.indexOf("MIX_CLARITY")+1;
        int indexSZ = vWPrpList.indexOf("MIX_SIZE")+1;
        int indexDp = vWPrpList.indexOf(level1)+1;
        int indexFapri = vWPrpList.indexOf(faprival)+1;
        int indexMfgpri = vWPrpList.indexOf(mfgprival)+1;
        int indexLoc = vWPrpList.indexOf(locVal)+1;    
        String shPrp = util.prpsrtcolumn("prp",indexSH);
        String clrPrp = util.prpsrtcolumn("prp",indexClr);
        String szPrp = util.prpsrtcolumn("prp",indexSZ);
        String dpPrp = util.prpsrtcolumn("prp",indexDp);
        String fapriPrp = util.prpsrtcolumn("prp",indexFapri);
        String mfgPrp = util.prpsrtcolumn("prp",indexMfgpri);
        String shSrt = util.prpsrtcolumn("srt",indexSH);
        String clrSrt = util.prpsrtcolumn("srt",indexClr);
        String szSrt = util.prpsrtcolumn("srt",indexSZ);
        String dpSrt = util.prpsrtcolumn("srt",indexDp);
        String fpriSrt = util.prpsrtcolumn("srt",indexFapri);
        String faSrt = util.prpsrtcolumn("srt",indexMfgpri);
        String LocPrp = util.prpsrtcolumn("prp",indexLoc);    
        ArrayList deptList = new ArrayList();
        String conQ="";
        String memoQ="";    
        HashMap prp = info.getPrp();
        ResultSet rs=null;
            ArrayList outLst=null;
            PreparedStatement pst=null;
        if(!dpPrp.equals("prp_000")){
        String labPrpQ="select distinct "+dpPrp+" dpPrp , "+dpSrt+" dpsrt from gt_srch_rslt where cts > 0 order by "+dpSrt;

            outLst = db.execSqlLst("dp Property", labPrpQ, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while (rs.next()) { 
            deptList.add(util.nvl(rs.getString("dpPrp")));
        }
        rs.close(); pst.close();
        }
        pageList=(ArrayList)pageDtl.get("LEVEL1_MPRP");
        
        if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            deptList= new ArrayList();
            deptList = (ArrayList)prp.get(level1+"V");
        }}
            
        HashMap dataTbl=new HashMap();
            if(indexFapri!=0){
                conQ=" trunc(((sum(trunc(cts,3)* "+fapriPrp+") / sum(trunc(cts,3))))) fapri, ";
            }
            if(indexMfgpri!=0){
               conQ=conQ+" trunc(((sum(trunc(cts,3)* "+mfgPrp+") / sum(trunc(cts,3))))) mfgpri,";
            
            }
            
            if(!location.equals("")){
                        mixStockSummary.setValue("loc", location);
                memoQ=memoQ+" and "+LocPrp+"='"+location+"'" ;    
                    }    
        if(!mlot.equals(""))
        mlotQ=" where pkt_ty <> 'NR' and cts > 0 ";
        else
        mlotQ ="where cts > 0";
        String dataQ="select "+dpPrp+" dept ,sum(qty) qty,"+conQ+" to_char(trunc(sum(trunc(cts, 3)),3),'99999990.000') cts,trunc(sum(quot*trunc(cts,3))/sum(trunc(cts, 3)),0) avg," + 
        "trunc(((sum(quot*trunc(cts,3))/sum(trunc(cts, 3)))/(sum(rap_Rte*trunc(cts,3))/sum(trunc(cts, 3)))*100) - 100, 3) avg_dis,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu,dsp_stt grp from gt_srch_rslt  " +mlotQ+" " +memoQ+ 
        " group by "+dpPrp+", dsp_stt";

            outLst = db.execSqlLst("Data", dataQ, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while (rs.next()) { 
            HashMap data=new HashMap();
            String dept=util.nvl(rs.getString("dept"));
            String grp=util.nvl(rs.getString("grp"));
            data.put("QTY",util.nvl(rs.getString("qty")));
            data.put("CTS",util.nvl(rs.getString("cts")));
            data.put("AVG", util.nvl(rs.getString("avg")));
            data.put("RAP", util.nvl(rs.getString("avg_dis")));
            data.put("VLU", util.nvl(rs.getString("vlu")));
            if(indexFapri!=0)
            data.put("FAPRI", util.nvl(rs.getString("fapri")));             
            if(indexMfgpri!=0)
            data.put("MFGPRI", util.nvl(rs.getString("mfgpri"))); 
            dataTbl.put(grp+"_"+dept,data);       
        }
        rs.close(); pst.close();
        ArrayList grpList = new ArrayList();
        if(!mlot.equals(""))
        mlotQ=" and pkt_ty <> 'NR' and cts > 0 ";
       else
         mlotQ="  and cts > 0 ";
        String grpTtlQ="select sum(qty) qty,"+conQ+" to_char(trunc(sum(trunc(cts, 3)),3),'99999990.000') cts,trunc(sum(quot*trunc(cts,3))/sum(trunc(cts, 3)),0) avg, " + 
        "trunc(((sum(quot*trunc(cts,3))/sum(trunc(cts, 3)))/(sum(rap_Rte*trunc(cts,3))/sum(trunc(cts, 3)))*100) - 100, 3) avg_dis,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu ,dsp_stt grp from gt_srch_rslt,rule_dtl a,mrule b " +
        "where a.rule_idn = b.rule_idn and b.mdl = 'JFLEX' and b.nme_rule = 'GROUP_DTL' and cts > 0 and dsp_stt=dsc "+mlotQ+" "+memoQ+" group by dsp_stt,srt_fr " + 
        "order by srt_fr";

            outLst = db.execSqlLst("Group Total", grpTtlQ, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while (rs.next()) { 
            HashMap data=new HashMap();
            String grp=util.nvl(rs.getString("grp"));
            if(!grpList.contains(grp) && !grp.equals((""))){
                grpList.add(grp);
            }
            data.put("QTY",util.nvl(rs.getString("qty")));
            data.put("CTS",util.nvl(rs.getString("cts")));
            data.put("AVG", util.nvl(rs.getString("avg")));
            data.put("RAP", util.nvl(rs.getString("avg_dis")));
            data.put("VLU", util.nvl(rs.getString("vlu")));
            if(indexFapri!=0)
            data.put("FAPRI", util.nvl(rs.getString("fapri")));             
            if(indexMfgpri!=0)
            data.put("MFGPRI", util.nvl(rs.getString("mfgpri"))); 
            dataTbl.put(grp+"_TTL",data);       
        }
        rs.close(); pst.close();
        if(!mlot.equals(""))
        mlotQ=" where pkt_ty <> 'NR' and cts > 0 ";
        else
        mlotQ=" where cts > 0 ";
        String labTtlQ="select "+dpPrp+" dept,sum(qty) qty,"+conQ+" to_char(trunc(sum(trunc(cts, 3)),3),'99999990.000') cts,trunc(sum(quot*trunc(cts,3))/sum(trunc(cts, 3)),0) avg," + 
        "trunc(((sum(quot*trunc(cts,3))/sum(trunc(cts, 3)))/(sum(rap_Rte*trunc(cts,3))/sum(trunc(cts, 3)))*100) - 100, 3) avg_dis,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu from gt_srch_rslt \n" + mlotQ+" " +memoQ+
        "group by "+dpPrp;
  
            outLst = db.execSqlLst("Lab Total", labTtlQ, new ArrayList()); 
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while (rs.next()) { 
            HashMap data=new HashMap();
            String dept=util.nvl(rs.getString("dept"));
            data.put("QTY",util.nvl(rs.getString("qty")));
            data.put("CTS",util.nvl(rs.getString("cts")));
            data.put("AVG", util.nvl(rs.getString("avg")));
            data.put("RAP", util.nvl(rs.getString("avg_dis")));
            data.put("VLU", util.nvl(rs.getString("vlu")));
            if(indexFapri!=0)
            data.put("FAPRI", util.nvl(rs.getString("fapri")));             
            if(indexMfgpri!=0)
            data.put("MFGPRI", util.nvl(rs.getString("mfgpri"))); 
            dataTbl.put(dept+"_TTL",data);       
        }
        rs.close(); pst.close();
        String grandTtlQ="select sum(qty) qty,"+conQ+" to_char(trunc(sum(trunc(cts, 3)),3),'99999990.000') cts,trunc(sum(quot*trunc(cts,3))/sum(trunc(cts, 3)),0) avg, " + 
        "trunc(((sum(quot*trunc(cts,3))/sum(trunc(cts, 3)))/(sum(rap_Rte*trunc(cts,3))/sum(trunc(cts, 3)))*100) - 100, 3) avg_dis,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu from gt_srch_rslt "+mlotQ+ " " +memoQ ;
 
            outLst = db.execSqlLst("Grand Total", grandTtlQ, new ArrayList());   
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while (rs.next()) { 
            HashMap data=new HashMap();
            data.put("QTY",util.nvl(rs.getString("qty")));
            data.put("CTS",util.nvl(rs.getString("cts")));
            data.put("AVG", util.nvl(rs.getString("avg")));
            data.put("RAP", util.nvl(rs.getString("avg_dis")));
            data.put("VLU", util.nvl(rs.getString("vlu")));
            if(indexFapri!=0)
            data.put("FAPRI", util.nvl(rs.getString("fapri")));             
            if(indexMfgpri!=0)
            data.put("MFGPRI", util.nvl(rs.getString("mfgpri"))); 
            dataTbl.put("TTL",data);       
        }
        rs.close(); pst.close();
        HashMap reportDtl=new HashMap();
        HashMap groupDsc =((HashMap)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_groupDsc") == null)?new HashMap():(HashMap)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_groupDsc"); 
        dataTbl.putAll(groupDsc);
        dataTbl.put("TITLE","Group/Dept"); 
        reportDtl.put("GRP_HDR", deptList);
        reportDtl.put("GRP_CONTENT", grpList);
        reportDtl.put("GRP_DATA", dataTbl);
        reportDtl.put("STT_HDR", deptList);
        session.setAttribute("reportDtl", reportDtl);
        req.setAttribute("mlot", mlot);
            util.updAccessLog(req,res,"Mix Stock Summary", "load end");
        return am.findForward("load");
        
        }
    }
    
    public ActionForward fetchMemo(ActionMapping am, ActionForm form,
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
            util.updAccessLog(req,res,"Mix Stock Summary", "fetchMemo start");
        MixStockSummaryForm mixStockSummary = (MixStockSummaryForm)form;
            GenericInterface genericInt = new GenericImpl();
        String memoLst = util.nvl(mixStockSummary.getMemo()).trim();
        String loc = util.nvl((String)mixStockSummary.getValue("loc"));
        HashMap dbinfo = info.getDmbsInfoLst();
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("MIX_STOCK_SUMMARY");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("MIX_STOCK_SUMMARY");
            allPageDtl.put("MIX_STOCK_SUMMARY",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            
            String level1="DEPT";
            ArrayList pageList=(ArrayList)pageDtl.get("LEVEL1");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            HashMap pageDtlMap=(HashMap)pageList.get(j);
            level1=(String)pageDtlMap.get("dflt_val");
            }}
          
        ArrayList vWPrpList=genericInt.genericPrprVw(req, res, "GROUP_MIX_REPORT","GROUP_MIX_VIEW");
        String sh = (String)dbinfo.get("SHAPE");
        String memo = util.nvl((String)dbinfo.get("MEMO"));
        String locVal=util.nvl((String)dbinfo.get("LOC"));
        String faprival = "FA_PRI";
        String mfgprival = "MFG_PRI";        
        int indexSH = vWPrpList.indexOf(sh)+1;
        int indexClr = vWPrpList.indexOf("MIX_CLARITY")+1;
        int indexSZ = vWPrpList.indexOf("MIX_SIZE")+1;
        int indexDp = vWPrpList.indexOf(level1)+1;
        int indexMemo = vWPrpList.indexOf(memo)+1;
        int indexLoc = vWPrpList.indexOf(locVal)+1;
        int indexFapri = vWPrpList.indexOf(faprival)+1;
        int indexMfgpri = vWPrpList.indexOf(mfgprival)+1; 
        String shPrp = util.prpsrtcolumn("prp",indexSH);
        String clrPrp = util.prpsrtcolumn("prp",indexClr);
        String szPrp = util.prpsrtcolumn("prp",indexSZ);
        String dpPrp = util.prpsrtcolumn("prp",indexDp);
        String memoPrp = util.prpsrtcolumn("prp",indexMemo);
        String LocPrp = util.prpsrtcolumn("prp",indexLoc);
        String fapriPrp = util.prpsrtcolumn("prp",indexFapri);
        String mfgPrp = util.prpsrtcolumn("prp",indexMfgpri);
        String shSrt = util.prpsrtcolumn("srt",indexSH);
        String clrSrt = util.prpsrtcolumn("srt",indexClr);
        String szSrt = util.prpsrtcolumn("srt",indexSZ);
        String dpSrt = util.prpsrtcolumn("srt",indexDp);
        String memoSrt = util.prpsrtcolumn("srt",indexMemo);
        String fpriSrt = util.prpsrtcolumn("srt",indexFapri);
        String faSrt = util.prpsrtcolumn("srt",indexMfgpri);
        ArrayList deptList = new ArrayList();
        String memoQ="";
        String conQ="";
        if(indexFapri!=0){
             conQ=" trunc(((sum(trunc(cts,3)* "+fapriPrp+") / sum(trunc(cts,3))))) fapri, ";
         }
         if(indexMfgpri!=0){
           conQ=conQ+" trunc(((sum(trunc(cts,3)* "+mfgPrp+") / sum(trunc(cts,3))))) mfgpri,";
            
         }
        if(!memoLst.equals("")){
        mixStockSummary.setMemo(memoLst);
        memoLst = util.getVnm(memoLst);
        memoQ=" and "+memoPrp+" in ("+memoLst+")";
        }
        if(!loc.equals("")){
            memoQ=memoQ+" and "+LocPrp+"='"+loc+"'" ;    
        }
        
            ResultSet rs=null;
                ArrayList outLst=null;
                PreparedStatement pst=null;
        if(!dpPrp.equals("prp_000")){
        String labPrpQ="select distinct "+dpPrp+" dpPrp , "+dpSrt+" dpsrt from gt_srch_rslt order by "+dpSrt;
            outLst = db.execSqlLst("dp Property", labPrpQ, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while (rs.next()) { 
            deptList.add(util.nvl(rs.getString("dpPrp")));
        }
        rs.close(); pst.close();
        }
        HashMap dataTbl=new HashMap();
        String dataQ="select "+dpPrp+" dept ,sum(qty) qty,"+conQ+" to_char(trunc(sum(trunc(cts, 3)),3),'99999990.000') cts,trunc(sum(quot*trunc(cts,3))/sum(trunc(cts, 3)),0) avg," + 
        "trunc(((sum(quot*trunc(cts,3))/sum(trunc(cts, 3)))/(sum(rap_Rte*trunc(cts,3))/sum(trunc(cts, 3)))*100) - 100, 3) avg_dis,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu,dsp_stt grp from gt_srch_rslt  where 1=1 " +memoQ+ 
        " group by "+dpPrp+", dsp_stt";

            outLst = db.execSqlLst("Data", dataQ, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while (rs.next()) { 
            HashMap data=new HashMap();
            String dept=util.nvl(rs.getString("dept"));
            String grp=util.nvl(rs.getString("grp"));
            data.put("QTY",util.nvl(rs.getString("qty")));
            data.put("CTS",util.nvl(rs.getString("cts")));
            data.put("AVG", util.nvl(rs.getString("avg")));
            data.put("RAP", util.nvl(rs.getString("avg_dis")));
            data.put("VLU", util.nvl(rs.getString("vlu")));
            if(indexFapri!=0)
            data.put("FAPRI", util.nvl(rs.getString("fapri")));             
            if(indexMfgpri!=0)
            data.put("MFGPRI", util.nvl(rs.getString("mfgpri"))); 
            dataTbl.put(grp+"_"+dept,data);       
        }
        rs.close(); pst.close();
        ArrayList grpList = new ArrayList();
        String grpTtlQ="select sum(qty) qty,"+conQ+" to_char(trunc(sum(trunc(cts, 3)),3),'99999990.000') cts,trunc(sum(quot*trunc(cts,3))/sum(trunc(cts, 3)),0) avg, " + 
        "trunc(((sum(quot*trunc(cts,3))/sum(trunc(cts, 3)))/(sum(rap_Rte*trunc(cts,3))/sum(trunc(cts, 3)))*100) - 100, 3) avg_dis,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu ,dsp_stt grp from gt_srch_rslt,rule_dtl a,mrule b " +
        "where a.rule_idn = b.rule_idn and b.mdl = 'JFLEX' and b.nme_rule = 'GROUP_DTL' and dsp_stt=dsc" +memoQ+ " group by dsp_stt,srt_fr " + 
        "order by srt_fr";

            outLst = db.execSqlLst("Group Total", grpTtlQ, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while (rs.next()) { 
            HashMap data=new HashMap();
            String grp=util.nvl(rs.getString("grp"));
            if(!grpList.contains(grp) && !grp.equals((""))){
                grpList.add(grp);
            }
            data.put("QTY",util.nvl(rs.getString("qty")));
            data.put("CTS",util.nvl(rs.getString("cts")));
            data.put("AVG", util.nvl(rs.getString("avg")));
            data.put("RAP", util.nvl(rs.getString("avg_dis")));
            data.put("VLU", util.nvl(rs.getString("vlu")));
            if(indexFapri!=0)
            data.put("FAPRI", util.nvl(rs.getString("fapri")));             
            if(indexMfgpri!=0)
            data.put("MFGPRI", util.nvl(rs.getString("mfgpri"))); 
            dataTbl.put(grp+"_TTL",data);       
        }
        rs.close(); pst.close();
        String labTtlQ="select "+dpPrp+" dept,sum(qty) qty,"+conQ+" to_char(trunc(sum(trunc(cts, 3)),3),'99999990.000') cts,trunc(sum(quot*trunc(cts,3))/sum(trunc(cts, 3)),0) avg," + 
        "trunc(((sum(quot*trunc(cts,3))/sum(trunc(cts, 3)))/(sum(rap_Rte*trunc(cts,3))/sum(trunc(cts, 3)))*100) - 100, 3) avg_dis,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu from gt_srch_rslt where 1=1 " +memoQ+ 
        "group by "+dpPrp;

            outLst = db.execSqlLst("Lab Total", labTtlQ, new ArrayList()); 
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while (rs.next()) { 
            HashMap data=new HashMap();
            String dept=util.nvl(rs.getString("dept"));
            data.put("QTY",util.nvl(rs.getString("qty")));
            data.put("CTS",util.nvl(rs.getString("cts")));
            data.put("AVG", util.nvl(rs.getString("avg")));
            data.put("RAP", util.nvl(rs.getString("avg_dis")));
            data.put("VLU", util.nvl(rs.getString("vlu")));
            if(indexFapri!=0)
            data.put("FAPRI", util.nvl(rs.getString("fapri")));             
            if(indexMfgpri!=0)
            data.put("MFGPRI", util.nvl(rs.getString("mfgpri"))); 
            dataTbl.put(dept+"_TTL",data);       
        }
        rs.close(); pst.close();
        String grandTtlQ="select sum(qty) qty,"+conQ+" to_char(trunc(sum(trunc(cts, 3)),3),'99999990.000') cts,trunc(sum(quot*trunc(cts,3))/sum(trunc(cts, 3)),0) avg, " + 
        "trunc(((sum(quot*trunc(cts,3))/sum(trunc(cts, 3)))/(sum(rap_Rte*trunc(cts,3))/sum(trunc(cts, 3)))*100) - 100, 3) avg_dis,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu from gt_srch_rslt where 1=1 " +memoQ ;

            outLst = db.execSqlLst("Grand Total", grandTtlQ, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while (rs.next()) { 
            HashMap data=new HashMap();
            data.put("QTY",util.nvl(rs.getString("qty")));
            data.put("CTS",util.nvl(rs.getString("cts")));
            data.put("AVG", util.nvl(rs.getString("avg")));
            data.put("RAP", util.nvl(rs.getString("avg_dis")));
            data.put("VLU", util.nvl(rs.getString("vlu")));
            if(indexFapri!=0)
            data.put("FAPRI", util.nvl(rs.getString("fapri")));             
            if(indexMfgpri!=0)
            data.put("MFGPRI", util.nvl(rs.getString("mfgpri"))); 
            dataTbl.put("TTL",data);       
        }
        rs.close(); pst.close();
        HashMap reportDtl=new HashMap();
        HashMap groupDsc =((HashMap)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_groupDsc") == null)?new HashMap():(HashMap)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_groupDsc"); 
        dataTbl.putAll(groupDsc);
        dataTbl.put("TITLE","Group/Dept"); 
        reportDtl.put("GRP_HDR", deptList);
        reportDtl.put("GRP_CONTENT", grpList);
        reportDtl.put("GRP_DATA", dataTbl);
        reportDtl.put("STT_HDR", deptList);
        session.setAttribute("reportDtl", reportDtl);
        
        req.setAttribute("memo", memoLst);
            req.setAttribute("loc", loc);
            util.updAccessLog(req,res,"Mix Stock Summary", "fetchMemo end");
        return am.findForward("load");
        
        }
    }
    public ActionForward loadmixSZCLR(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
             util.updAccessLog(req,res,"Mix Stock Summary", "loadmixSZCLR start");
        MixStockSummaryForm mixStockSummary = (MixStockSummaryForm)af;
             HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
             HashMap pageDtl=(HashMap)allPageDtl.get("MIX_STOCK_SUMMARY");
             if(pageDtl==null || pageDtl.size()==0){
             pageDtl=new HashMap();
             pageDtl=util.pagedef("MIX_STOCK_SUMMARY");
             allPageDtl.put("MIX_STOCK_SUMMARY",pageDtl);
             }
             info.setPageDetails(allPageDtl);
             ArrayList pageList=new ArrayList();
             HashMap pageDtlMap=new HashMap();
         GenericInterface genericInt = new GenericImpl();
         String deptS = util.nvl(req.getParameter("lab")).trim(); 
         String groupS = util.nvl(req.getParameter("group")).trim(); 
         String memoLst = util.nvl(req.getParameter("memo")).trim();
         String mlot = util.nvl(req.getParameter("mlot")).trim();   
         String loc = util.nvl(req.getParameter("loc")).trim(); 
         HashMap dbinfo = info.getDmbsInfoLst();
         ArrayList vWPrpList=genericInt.genericPrprVw(req, res, "GROUP_MIX_REPORT","GROUP_MIX_VIEW");
         String sh = (String)dbinfo.get("SHAPE");
         String memo = util.nvl((String)dbinfo.get("MEMO"));
         String faprival = "FA_PRI";
         String mfgprival = "MFG_PRI";
             String level2row="MIX_CLARITY";
             pageList=(ArrayList)pageDtl.get("LEVEL2ROW");
             if(pageList!=null && pageList.size() >0){
             for(int j=0;j<pageList.size();j++){
             pageDtlMap=(HashMap)pageList.get(j);
             level2row=(String)pageDtlMap.get("dflt_val");
             }}
             String level2col="MIX_SIZE";
             pageList=(ArrayList)pageDtl.get("LEVEL2COL");
             if(pageList!=null && pageList.size() >0){
             for(int j=0;j<pageList.size();j++){
             pageDtlMap=(HashMap)pageList.get(j);
             level2col=(String)pageDtlMap.get("dflt_val");
             }}
             String level1="DEPT";
             pageList=(ArrayList)pageDtl.get("LEVEL1");
             if(pageList!=null && pageList.size() >0){
             for(int j=0;j<pageList.size();j++){
             pageDtlMap=(HashMap)pageList.get(j);
             level1=(String)pageDtlMap.get("dflt_val");
             }}
         int indexSH = vWPrpList.indexOf(sh)+1;
         int indexClr = vWPrpList.indexOf(level2row)+1;
         int indexSZ = vWPrpList.indexOf(level2col)+1;
         int indexDp = vWPrpList.indexOf(level1)+1;
         int indexMemo = vWPrpList.indexOf(memo)+1;
         int indexFapri = vWPrpList.indexOf(faprival)+1;
         int indexMfgpri = vWPrpList.indexOf(mfgprival)+1;
         String shPrp = util.prpsrtcolumn("prp",indexSH);
         String clrPrp = util.prpsrtcolumn("prp",indexClr);
         String szPrp = util.prpsrtcolumn("prp",indexSZ);
         String dpPrp = util.prpsrtcolumn("prp",indexDp);
         String memoPrp = util.prpsrtcolumn("prp",indexMemo);
         String fapriPrp = util.prpsrtcolumn("prp",indexFapri);
         String mfgPrp = util.prpsrtcolumn("prp",indexMfgpri);
         String shSrt = util.prpsrtcolumn("srt",indexSH);
         String clrSrt = util.prpsrtcolumn("srt",indexClr);
         String szSrt = util.prpsrtcolumn("srt",indexSZ);
         String dpSrt = util.prpsrtcolumn("srt",indexDp);
         String memoSrt = util.prpsrtcolumn("srt",indexMemo);
         String fpriSrt = util.prpsrtcolumn("srt",indexFapri);
         String faSrt = util.prpsrtcolumn("srt",indexMfgpri);
         ResultSet rs = null;
         HashMap dataTbl=new HashMap();
         ArrayList mixSzLst=new ArrayList();
         ArrayList mixClrList=new ArrayList();
         ArrayList Lst=new ArrayList();
         String memoQ="";
         String mlotQ="";
         String conQprp="";
    
             if(indexFapri!=0){
                 conQprp=" trunc(((sum(trunc(cts,3)* "+fapriPrp+") / sum(trunc(cts,3))))) fapri, ";
             }
             if(indexMfgpri!=0){
                conQprp=conQprp+" trunc(((sum(trunc(cts,3)* "+mfgPrp+") / sum(trunc(cts,3))))) mfgpri,";
             
             }   
         if(!memoLst.equals("")){
         memoQ=" and "+memoPrp+" in ("+memoLst+")";
         }
         if(!mlot.equals(""))
         mlotQ="  and pkt_ty <> 'NR' and cts > 0 ";
         String reportNme="MIX Sixe Clearity Report";
         String criteria="Group-"+groupS+" , Dept-"+deptS;
         String conQ="";
         if(!groupS.equals("ALL") && !deptS.equals("ALL"))
             conQ=" where dsp_stt='"+groupS+"' and "+dpPrp+"='"+deptS+"' "+memoQ +mlotQ;
         if(!groupS.equals("ALL") && deptS.equals("ALL"))
             conQ=" where dsp_stt='"+groupS+"' "+memoQ+mlotQ;
         if(groupS.equals("ALL") && !deptS.equals("ALL"))
             conQ=" where "+dpPrp+"='"+deptS+"' "+memoQ+mlotQ;
         HashMap reportDtl= (HashMap)session.getAttribute("reportDtl");
         
         if(!groupS.equals("ALL") && !deptS.equals("ALL"))
            conQ=" where dsp_stt='"+groupS+"' and "+dpPrp+"='"+deptS+"' "+memoQ+mlotQ;
         if(!groupS.equals("ALL") && deptS.equals("ALL"))
            conQ=" where dsp_stt='"+groupS+"' "+memoQ+mlotQ;
         if(groupS.equals("ALL") && !deptS.equals("ALL"))
            conQ=" where "+dpPrp+"='"+deptS+"' "+memoQ+mlotQ;
         if(!memoLst.equals("") && groupS.equals("ALL") && deptS.equals("ALL"))
             conQ=" where 1=1 "+memoQ+mlotQ; 
         String szPrpQ="select distinct "+szPrp+" szPrp , "+szSrt+" szSrt from gt_srch_rslt "+conQ+" order by "+szSrt;
             ArrayList outLst = db.execSqlLst("Size Property", szPrpQ, new ArrayList());
             PreparedStatement pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
         while (rs.next()) { 
             mixSzLst.add(util.nvl(rs.getString("szPrp")));
         }
             rs.close(); pst.close();
             
      
            
         String dataQ="select "+szPrp+" sz,"+clrPrp+" clr,"+shPrp+" sh, "+conQprp+" sum(qty) qty,to_char(trunc(sum(trunc(cts, 4)),3),'99999990.00') cts,"+
         "trunc(sum(quot*trunc(cts,4))/sum(trunc(cts, 4)),0) avg,"+
         "trunc(((sum(quot*trunc(cts,4))/sum(trunc(cts, 4)))/(sum(rap_Rte*trunc(cts,4))/sum(trunc(cts, 4)))*100) - 100, 3) avg_dis,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu "+
         "from gt_srch_rslt ";
         dataQ=dataQ+conQ+" group by "+szPrp+", "+clrPrp+", "+shPrp;

             outLst = db.execSqlLst("Data", dataQ, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
         while (rs.next()) { 
             HashMap data=new HashMap();
             String sz=util.nvl(rs.getString("sz"));
             String clr=util.nvl(rs.getString("clr"));
             String shape=util.nvl(rs.getString("sh"));
             data.put("QTY",util.nvl(rs.getString("qty")));
             data.put("CTS",util.nvl(rs.getString("cts")));
             data.put("AVG", util.nvl(rs.getString("avg")));
             data.put("RAP", util.nvl(rs.getString("avg_dis")));
             data.put("VLU", util.nvl(rs.getString("vlu")));
             if(indexFapri!=0)
             data.put("FAPRI", util.nvl(rs.getString("fapri")));             
             if(indexMfgpri!=0)
             data.put("MFGPRI", util.nvl(rs.getString("mfgpri"))); 
             dataTbl.put(shape+"_"+sz+"_"+clr,data);       
         }
         rs.close(); pst.close();
              dataQ="select "+szPrp+" sz,"+clrPrp+" clr,'TOTAL' sh, "+conQprp+" sum(qty) qty,to_char(trunc(sum(trunc(cts, 4)),3),'99999990.00') cts,"+
             "trunc(sum(quot*trunc(cts,4))/sum(trunc(cts, 4)),0) avg,"+
             "trunc(((sum(quot*trunc(cts,4))/sum(trunc(cts, 4)))/(sum(rap_Rte*trunc(cts,4))/sum(trunc(cts, 4)))*100) - 100, 3) avg_dis,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu "+
             "from gt_srch_rslt ";
             dataQ=dataQ+conQ+" group by "+szPrp+", "+clrPrp;

             outLst = db.execSqlLst("Data", dataQ, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
             while (rs.next()) { 
                 HashMap data=new HashMap();
                 String sz=util.nvl(rs.getString("sz"));
                 String clr=util.nvl(rs.getString("clr"));
                 String shape=util.nvl(rs.getString("sh"));
                 data.put("QTY",util.nvl(rs.getString("qty")));
                 data.put("CTS",util.nvl(rs.getString("cts")));
                 data.put("AVG", util.nvl(rs.getString("avg")));
                 data.put("RAP", util.nvl(rs.getString("avg_dis")));
                 data.put("VLU", util.nvl(rs.getString("vlu")));
                 if(indexFapri!=0)
                 data.put("FAPRI", util.nvl(rs.getString("fapri")));             
                 if(indexMfgpri!=0)
                 data.put("MFGPRI", util.nvl(rs.getString("mfgpri"))); 
                 dataTbl.put(shape+"_"+sz+"_"+clr,data);       
             }
             rs.close(); pst.close();
        
         String grpTtlQ="   select "+szPrp+" sz,"+shPrp+" sh,"+conQprp+" sum(a.qty) qty,to_char(trunc(sum(trunc(a.cts, 3)),3),'99999990.00') cts,trunc(sum(quot*trunc(cts,4))/sum(trunc(cts, 4)),0) avg,\n" + 
         "         trunc(((sum(quot*trunc(cts,4))/sum(trunc(cts, 4)))/(sum(rap_Rte*trunc(cts,4))/sum(trunc(cts, 4)))*100) - 100, 3) avg_dis,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu \n" + 
         "         from gt_srch_rslt a ";
         grpTtlQ=grpTtlQ+conQ+" group by "+szPrp+", "+shPrp;

             outLst = db.execSqlLst("SZ Total", grpTtlQ, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
         while (rs.next()) { 
             HashMap data=new HashMap();
             String sz=util.nvl(rs.getString("sz"));
             String shape=util.nvl(rs.getString("sh"));
             data.put("QTY",util.nvl(rs.getString("qty")));
             data.put("CTS",util.nvl(rs.getString("cts")));
             data.put("AVG", util.nvl(rs.getString("avg")));
             data.put("RAP", util.nvl(rs.getString("avg_dis")));
             data.put("VLU", util.nvl(rs.getString("vlu")));
             if(indexFapri!=0)
             data.put("FAPRI", util.nvl(rs.getString("fapri")));             
             if(indexMfgpri!=0)
             data.put("MFGPRI", util.nvl(rs.getString("mfgpri"))); 
             dataTbl.put(shape+"_"+sz+"_TTL",data); 
         }
         rs.close(); pst.close();
             grpTtlQ="   select "+szPrp+" sz,'TOTAL' sh,"+conQprp+" sum(a.qty) qty,to_char(trunc(sum(trunc(a.cts, 3)),3),'99999990.00') cts,trunc(sum(quot*trunc(cts,4))/sum(trunc(cts, 4)),0) avg,\n" + 
             "         trunc(((sum(quot*trunc(cts,4))/sum(trunc(cts, 4)))/(sum(rap_Rte*trunc(cts,4))/sum(trunc(cts, 4)))*100) - 100, 3) avg_dis,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu \n" + 
             "         from gt_srch_rslt a ";
             grpTtlQ=grpTtlQ+conQ+" group by "+szPrp;

             outLst = db.execSqlLst("SZ Total", grpTtlQ, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
             while (rs.next()) { 
                 HashMap data=new HashMap();
                 String sz=util.nvl(rs.getString("sz"));
                 String shape=util.nvl(rs.getString("sh"));
                 data.put("QTY",util.nvl(rs.getString("qty")));
                 data.put("CTS",util.nvl(rs.getString("cts")));
                 data.put("AVG", util.nvl(rs.getString("avg")));
                 data.put("RAP", util.nvl(rs.getString("avg_dis")));
                 data.put("VLU", util.nvl(rs.getString("vlu")));
                 if(indexFapri!=0)
                 data.put("FAPRI", util.nvl(rs.getString("fapri")));             
                 if(indexMfgpri!=0)
                 data.put("MFGPRI", util.nvl(rs.getString("mfgpri"))); 
                 dataTbl.put(shape+"_"+sz+"_TTL",data); 
             }
             rs.close(); pst.close();    
             
         String labTtlQ="select "+clrPrp+" clr,"+shPrp+" sh,"+conQprp+" sum(qty) qty,to_char(trunc(sum(trunc(cts, 4)),3),'99999990.00') cts,trunc(sum(quot*trunc(cts,4))/sum(trunc(cts, 4)),0) avg,"+
         "trunc(((sum(quot*trunc(cts,4))/sum(trunc(cts, 4)))/(sum(rap_Rte*trunc(cts,4))/sum(trunc(cts, 4)))*100) - 100, 3) avg_dis,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu,b.dsc dsc,b.srt from gt_srch_rslt a,prp b  ";
         if(groupS.equals("ALL") && deptS.equals("ALL") && !memoLst.equals(""))
         labTtlQ=labTtlQ+conQ+" and "+clrPrp+"=b.val and b.mprp='"+level2row+"' group by "+clrPrp+", "+shPrp+", b.dsc, b.srt order by b.srt";
         else if(groupS.equals("ALL") && deptS.equals("ALL") && memoLst.equals(""))
         labTtlQ=labTtlQ+conQ+" where "+clrPrp+"=b.val and b.mprp='"+level2row+"' group by "+clrPrp+", "+shPrp+", b.dsc, b.srt order by b.srt";
         else
         labTtlQ=labTtlQ+conQ+" and "+clrPrp+"=b.val and b.mprp='"+level2row+"'  group by "+clrPrp+", "+shPrp+", b.dsc, b.srt order by b.srt";

             outLst = db.execSqlLst("CLR Total", labTtlQ, new ArrayList());  
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
         while (rs.next()) { 
             HashMap data=new HashMap();
             String shape=util.nvl(rs.getString("sh"));
             String clr=util.nvl(rs.getString("clr"));
             if(!mixClrList.contains(clr) && !clr.equals((""))){
                 mixClrList.add(clr);
             }
             data.put("QTY",util.nvl(rs.getString("qty")));
             data.put("CTS",util.nvl(rs.getString("cts")));
             data.put("AVG", util.nvl(rs.getString("avg")));
             data.put("RAP", util.nvl(rs.getString("avg_dis")));
             data.put("VLU", util.nvl(rs.getString("vlu")));
             if(indexFapri!=0)
             data.put("FAPRI", util.nvl(rs.getString("fapri")));             
             if(indexMfgpri!=0)
             data.put("MFGPRI", util.nvl(rs.getString("mfgpri"))); 
             dataTbl.put(shape+"_"+clr+"_TTL",data);       
         }
         rs.close(); pst.close();
             labTtlQ="select "+clrPrp+" clr,'TOTAL' sh,"+conQprp+" sum(qty) qty,to_char(trunc(sum(trunc(cts, 4)),3),'99999990.00') cts,trunc(sum(quot*trunc(cts,4))/sum(trunc(cts, 4)),0) avg,"+
                      "trunc(((sum(quot*trunc(cts,4))/sum(trunc(cts, 4)))/(sum(rap_Rte*trunc(cts,4))/sum(trunc(cts, 4)))*100) - 100, 3) avg_dis,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu,b.dsc dsc,b.srt from gt_srch_rslt a,prp b  ";
                      if(groupS.equals("ALL") && deptS.equals("ALL") && !memoLst.equals(""))
                      labTtlQ=labTtlQ+conQ+" and "+clrPrp+"=b.val and b.mprp='"+level2row+"' group by "+clrPrp+", b.dsc, b.srt order by b.srt";
                      else if(groupS.equals("ALL") && deptS.equals("ALL") && memoLst.equals(""))
                      labTtlQ=labTtlQ+conQ+" where "+clrPrp+"=b.val and b.mprp='"+level2row+"' group by "+clrPrp+", b.dsc, b.srt order by b.srt";
                      else
                      labTtlQ=labTtlQ+conQ+" and "+clrPrp+"=b.val and b.mprp='"+level2row+"'  group by "+clrPrp+", b.dsc, b.srt order by b.srt";
   
             outLst = db.execSqlLst("CLR Total", labTtlQ, new ArrayList());    
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
                      while (rs.next()) { 
                          HashMap data=new HashMap();
                          String shape=util.nvl(rs.getString("sh"));
                          String clr=util.nvl(rs.getString("clr"));
                          if(!mixClrList.contains(clr) && !clr.equals((""))){
                              mixClrList.add(clr);
                          }
                          data.put("QTY",util.nvl(rs.getString("qty")));
                          data.put("CTS",util.nvl(rs.getString("cts")));
                          data.put("AVG", util.nvl(rs.getString("avg")));
                          data.put("RAP", util.nvl(rs.getString("avg_dis")));
                          data.put("VLU", util.nvl(rs.getString("vlu")));
                          if(indexFapri!=0)
                          data.put("FAPRI", util.nvl(rs.getString("fapri")));             
                          if(indexMfgpri!=0)
                          data.put("MFGPRI", util.nvl(rs.getString("mfgpri"))); 
                          dataTbl.put(shape+"_"+clr+"_TTL",data);       
                      }
             rs.close(); pst.close();    
             
         String grandTtlQ="select "+shPrp+" sh,sum(qty) qty,"+conQprp+" to_char(trunc(sum(trunc(cts, 4)),3),'99999990.00') cts,trunc(sum(quot*trunc(cts,4))/sum(trunc(cts, 4)),0) avg,"+
         "trunc(((sum(quot*trunc(cts,4))/sum(trunc(cts, 4)))/(sum(rap_Rte*trunc(cts,4))/sum(trunc(cts, 4)))*100) - 100, 3) avg_dis,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu,b.dsc dsc,b.srt "+
         "from gt_srch_rslt a,prp b ";
         if(conQ.equals(""))
         grandTtlQ=grandTtlQ+conQ+" where "+shPrp+"=b.val and b.mprp='"+sh+"' group by "+shPrp+",b.dsc, b.srt order by b.srt";
         else
         grandTtlQ=grandTtlQ+conQ+" and "+shPrp+"=b.val and b.mprp='"+sh+"' group by "+shPrp+",b.dsc, b.srt order by b.srt";

             outLst = db.execSqlLst("Grand Total", grandTtlQ, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
         while (rs.next()) { 
             HashMap data=new HashMap();
             String shape=util.nvl(rs.getString("sh"));
             if(!Lst.contains(shape) && !shape.equals((""))){
                 Lst.add(shape);
             }
             data.put("QTY",util.nvl(rs.getString("qty")));
             data.put("CTS",util.nvl(rs.getString("cts")));
             data.put("AVG", util.nvl(rs.getString("avg")));
             data.put("RAP", util.nvl(rs.getString("avg_dis")));
             data.put("VLU", util.nvl(rs.getString("vlu")));
             if(indexFapri!=0)
             data.put("FAPRI", util.nvl(rs.getString("fapri")));             
             if(indexMfgpri!=0)
             data.put("MFGPRI", util.nvl(rs.getString("mfgpri"))); 
             dataTbl.put(shape+"_TTL",data);       
         }
         rs.close(); pst.close();

             grandTtlQ="select 'TOTAL' sh,sum(qty) qty,"+conQprp+" to_char(trunc(sum(trunc(cts, 4)),3),'99999990.00') cts,trunc(sum(quot*trunc(cts,4))/sum(trunc(cts, 4)),0) avg,"+
             "trunc(((sum(quot*trunc(cts,4))/sum(trunc(cts, 4)))/(sum(rap_Rte*trunc(cts,4))/sum(trunc(cts, 4)))*100) - 100, 3) avg_dis,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu "+
             "from gt_srch_rslt a,prp b ";
             if(conQ.equals(""))
             grandTtlQ=grandTtlQ+conQ+" where "+shPrp+"=b.val and b.mprp='"+sh+"' order by b.srt";
             else
             grandTtlQ=grandTtlQ+conQ+" and "+shPrp+"=b.val and b.mprp='"+sh+"' order by b.srt";

             outLst = db.execSqlLst("Grand Total", grandTtlQ, new ArrayList());   
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
             while (rs.next()) { 
                 HashMap data=new HashMap();
                 String shape=util.nvl(rs.getString("sh"));
                 if(!Lst.contains(shape) && !shape.equals((""))){
                     Lst.add(shape);
                 }
                 data.put("QTY",util.nvl(rs.getString("qty")));
                 data.put("CTS",util.nvl(rs.getString("cts")));
                 data.put("AVG", util.nvl(rs.getString("avg")));
                 data.put("RAP", util.nvl(rs.getString("avg_dis")));
                 data.put("VLU", util.nvl(rs.getString("vlu")));
                 if(indexFapri!=0)
                 data.put("FAPRI", util.nvl(rs.getString("fapri")));             
                 if(indexMfgpri!=0)
                 data.put("MFGPRI", util.nvl(rs.getString("mfgpri"))); 
                 dataTbl.put(shape+"_TTL",data);       
             }
             rs.close(); pst.close();
         dataTbl.put("TITLE","Clearity/Mix Size");
         reportDtl.put("MIX_HDR", mixSzLst);
         reportDtl.put("MIX_CONTENT", mixClrList);
         reportDtl.put("MIX_DATA", dataTbl);
         reportDtl.put("Lst", Lst);
         session.setAttribute("reportDtl", reportDtl);
         mixStockSummary.setReportNme(reportNme);
         mixStockSummary.setCriteria(criteria);
         req.setAttribute("memo", memoLst);
             String reqUrl = req.getQueryString();
             req.setAttribute("urlParam", reqUrl);
             util.updAccessLog(req,res,"Mix Stock Summary", "loadmixSZCLR end");
         return am.findForward("loaSZCLR");
         }
     }
    
    public ActionForward pktDtl(ActionMapping am, ActionForm form,
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
            util.updAccessLog(req,res,"Mix Stock Summary", "pktDtl start");
            GenericInterface genericInt = new GenericImpl();
            String typ=util.nvl(req.getParameter("typ"));
            String df="MIX_STOCK_SUMMARY";
            ArrayList vWPrpList=genericInt.genericPrprVw(req, res, "GROUP_MIX_REPORT","GROUP_MIX_VIEW");

            if(typ.equals("RGH")){
                df="RGH_STOCK_SUMMARY";
                vWPrpList=genericInt.genericPrprVw(req, res, "GROUP_RGH_VIEW","GROUP_RGH_VIEW");

            }
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get(df);
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef(df);
            allPageDtl.put(df,pageDtl);
            }
            info.setPageDetails(allPageDtl);
            ArrayList pageList=new ArrayList();
            HashMap pageDtlMap=new HashMap();
         String sttS = util.nvl(req.getParameter("stt")).trim(); 
         String labS = util.nvl(req.getParameter("lab")).trim(); 
         String level2S = util.nvl(req.getParameter("level2")).trim(); 
         String groupS = util.nvl(req.getParameter("group")).trim(); 
         String shapeS= util.nvl(req.getParameter("sh")).trim(); 
         String sizeS = util.nvl(req.getParameter("sz")).trim(); 
         String colorS= util.nvl(req.getParameter("col")).trim(); 
         String clrS = util.nvl(req.getParameter("clr")).trim(); 
         String memoLst = util.nvl(req.getParameter("memo")).trim(); 
         String mlot = util.nvl(req.getParameter("mlot")).trim(); 
            String locVal = util.nvl(req.getParameter("loc")).trim(); 
          HashMap dbinfo = info.getDmbsInfoLst();
          String sh = (String)dbinfo.get("SHAPE");
          String szval = (String)dbinfo.get("SIZE");
          String colval = (String)dbinfo.get("COL");
          String clrval = (String)dbinfo.get("CLR");
          String labval = (String)dbinfo.get("LAB");
          String memo = util.nvl((String)dbinfo.get("MEMO"));
          String loc = util.nvl((String)dbinfo.get("LOC"));
            String level1="DEPT";
            pageList=(ArrayList)pageDtl.get("LEVEL1");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            level1=(String)pageDtlMap.get("dflt_val");
            }}
            
            String level2="";
            pageList=(ArrayList)pageDtl.get("LEVEL2");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            level2=(String)pageDtlMap.get("dflt_val");
            }}
          int indexLB = vWPrpList.indexOf(level1)+1;
          int indexLv2 = vWPrpList.indexOf(level2)+1;
          int indexSH = vWPrpList.indexOf(sh)+1;
          int indexSZ = vWPrpList.indexOf(szval)+1;
          int indexCol = vWPrpList.indexOf(colval)+1;
          int indexClr = vWPrpList.indexOf(clrval)+1;
          int indexMemo = vWPrpList.indexOf(memo)+1;
          int indexLoc = vWPrpList.indexOf(loc)+1;
          String lbPrp = util.prpsrtcolumn("prp",indexLB);
          String lb2Prp = util.prpsrtcolumn("prp",indexLv2);
          String shPrp = util.prpsrtcolumn("prp",indexSH);
          String szPrp = util.prpsrtcolumn("prp",indexSZ);
          String colPrp = util.prpsrtcolumn("prp",indexCol);
          String clrPrp = util.prpsrtcolumn("prp",indexClr);
          String memoPrp = util.prpsrtcolumn("prp",indexMemo);
          String locPrp = util.prpsrtcolumn("prp",indexLoc);
          String lbSrt = util.prpsrtcolumn("srt",indexLB);
          String shSrt = util.prpsrtcolumn("srt",indexSH);
          String szSrt = util.prpsrtcolumn("srt",indexSZ);
          String colSrt = util.prpsrtcolumn("srt",indexCol);
          String clrSrt = util.prpsrtcolumn("srt",indexClr);
          String memoSrt = util.prpsrtcolumn("srt",indexMemo);
      
        String conQ="";
        String memoQ="";
        String mlotQ="";
       
        if(!memoLst.equals("")){
        memoQ=" and "+memoPrp+" in ("+memoLst+")";
        }
            if(!locVal.equals("")){
            memoQ=" and "+locPrp+" in ('"+locVal+"')";
            }
        if(!mlot.equals(""))
        mlotQ="  and pkt_ty <> 'NR' and cts > 0 ";
    ArrayList pktList = new ArrayList();
    String srchQ =" select stk_idn , pkt_ty, vnm, pkt_dte, stt , qty , to_char(cts,'999990.999') cts , cert_lab,rmk , quot,rchk  ";

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
    String rsltQ = srchQ + " from gt_srch_rslt where 1=1 and cts > 0 "+memoQ +mlotQ;  
    if(!groupS.equals("ALL") && !labS.equals("ALL") && !labS.equals(""))
    conQ=" and dsp_stt='"+groupS+"' and "+lbPrp+"='"+labS+"' ";
    if(!groupS.equals("ALL") && labS.equals("ALL"))
    conQ=" and dsp_stt='"+groupS+"' ";
    if(groupS.equals("ALL") && !labS.equals("ALL") && !labS.equals(""))
    conQ=" and "+lbPrp+"='"+labS+"' ";
    
    if(!level2.equals("")){
      if(!groupS.equals("ALL") && !level2S.equals("ALL") && !level2S.equals(""))
            conQ=" and dsp_stt='"+groupS+"' and "+lb2Prp+"='"+level2S+"' ";
    if(!groupS.equals("ALL") && level2S.equals("ALL"))
            conQ=" and dsp_stt='"+groupS+"' ";
     if(groupS.equals("ALL") && !level2S.equals("ALL")  && !level2S.equals(""))
            conQ=" and "+lbPrp+"='"+labS+"' ";
    }
            
    if(!sttS.equals("ALL") && !labS.equals("ALL") && !sttS.equals("") && !labS.equals(""))
    conQ=conQ+" and stt='"+sttS+"' and "+lbPrp+"='"+labS+"' ";
    if(!sttS.equals("ALL") && labS.equals("ALL") && !sttS.equals(""))
    conQ=conQ+" and   stt='"+sttS+"' ";
    if(sttS.equals("ALL") && !labS.equals("ALL") && !sttS.equals("") && !labS.equals(""))
    conQ=conQ+" and  "+lbPrp+"='"+labS+"' ";
    
    if(!shapeS.equals("ALL") && !sizeS.equals("ALL") && !shapeS.equals(""))
            conQ=conQ+" and "+shPrp+"='"+shapeS+"' and ("+szPrp+"='"+sizeS+"' or "+szPrp+"='+"+sizeS+"') ";
    if(shapeS.equals("ALL") && !sizeS.equals("ALL") && !shapeS.equals(""))
            conQ=conQ+" and ("+szPrp+"='"+sizeS+"' or "+szPrp+"='+"+sizeS+"') ";
    if(!shapeS.equals("ALL") && sizeS.equals("ALL") && !shapeS.equals(""))
            conQ=conQ+" and "+shPrp+"='"+shapeS+"' "; 
    
    if(!colorS.equals("ALL") && !clrS.equals("ALL") && !colorS.equals(""))
                conQ=conQ+" and "+colPrp+"='"+colorS+"' and ("+clrPrp+"='"+clrS+"' or "+clrPrp+"='+"+clrS+"') ";
    if(colorS.equals("ALL") && !clrS.equals("ALL") && !colorS.equals(""))
                conQ=conQ+" and ("+clrPrp+"='"+clrS+"' or "+clrPrp+"='+"+clrS+"') ";
    if(!colorS.equals("ALL") && clrS.equals("ALL") && !colorS.equals(""))
                conQ=conQ+" and "+colPrp+"='"+colorS+"' "; 
            
            
    
    rsltQ=rsltQ+conQ+" order by sk1";

    ArrayList ary = new ArrayList();

            ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
    try {
    while (rs.next()) {
    String cert_lab = util.nvl(rs.getString("cert_lab"));
    String stkIdn = util.nvl(rs.getString("stk_idn"));
    HashMap pktPrpMap = new HashMap();
    pktPrpMap.put("dsc", util.nvl(rs.getString("rmk")));
    pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
    String vnm = util.nvl(rs.getString("vnm"));
    pktPrpMap.put("vnm", vnm);
    pktPrpMap.put("stk_idn", stkIdn);
    pktPrpMap.put("qty", util.nvl(rs.getString("qty")));
    pktPrpMap.put("cts", util.nvl(rs.getString("cts")));
    pktPrpMap.put("BoxDsc", util.nvl(rs.getString("rchk")));

    for (int j = 0; j < vWPrpList.size(); j++) {
    String prp = (String)vWPrpList.get(j);

    String fld = "prp_";
    if (j < 9)
    fld = "prp_00" + (j + 1);
    else
    fld = "prp_0" + (j + 1);
        if(prp.equals("RTE"))
           fld = "quot";
        if (prp.equals("CRTWT"))
           fld = "cts";
    String val = util.nvl(rs.getString(fld));
    pktPrpMap.put(prp, val);
    }
    pktList.add(pktPrpMap);
    }
        rs.close(); pst.close();
    session.setAttribute("pktList", pktList);
    } catch (SQLException sqle) {

    // TODO: Add catch code
    sqle.printStackTrace();
    }
            util.updAccessLog(req,res,"Mix Stock Summary", "pktDtl end");
    return am.findForward("loadPktDtl");
        }
    }
    

//    public void groupDtls(HttpServletRequest req){
//        HttpSession session = req.getSession(false);
//        InfoMgr info = (InfoMgr)session.getAttribute("info");
//        DBUtil util = new DBUtil();
//        DBMgr db = new DBMgr();
//        db.setCon(info.getCon());
//        util.setDb(db);
//        util.setInfo(info);
//        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
//        util.setLogApplNm(info.getLoApplNm());
//    HashMap groupDsc = (session.getAttribute("groupDsc") == null)?new HashMap():(HashMap)session.getAttribute("groupDsc");
//
//    try {
//    if(groupDsc.size() == 0) {
//        HashMap dbinfo = info.getDmbsInfoLst();
//        String cnt=util.nvl((String)dbinfo.get("CNT"));
//        String mem_ip=util.nvl((String)dbinfo.get("MEM_IP"),"13.229.255.212");
//        int mem_port=Integer.parseInt(util.nvl((String)dbinfo.get("MEM_PORT"),"11211"));
//        MemcachedClient mcc = new MemcachedClient(new InetSocketAddress(mem_ip, mem_port));
//        groupDsc=(HashMap)mcc.get(cnt+"_groupDsc");
//        if(groupDsc==null){
//        groupDsc=new HashMap();
//    String gtView = "select chr_fr, chr_to , dsc , dta_typ from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " +
//    " b.mdl = 'JFLEX' and b.nme_rule = 'GROUP_DTL' and a.til_dte is null order by a.srt_fr ";
//    ResultSet rs = db.execSql("gtView", gtView, new ArrayList());
//    while (rs.next()) {
//    groupDsc.put(util.nvl(rs.getString("dsc")), util.nvl(rs.getString("chr_fr")));
//    }
//        rs.close(); pst.close();
//        Future fo = mcc.delete(cnt+"_groupDsc");
//        System.out.println("add status:_groupDsc" + fo.get());
//        fo = mcc.set(cnt+"_groupDsc", 24*60*60, groupDsc);
//        System.out.println("add status:_groupDsc" + fo.get());
//        }
//        mcc.shutdown();
//    session.setAttribute("groupDsc", groupDsc);
//    }
//    } catch (SQLException sqle) {
//    // TODO: Add catch code
//    sqle.printStackTrace();
//        }catch(Exception ex){
//         System.out.println( ex.getMessage() );
//        }
//    } 
    
    public ActionForward createGridXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Mix Stock Summary", "createGridXL start");
          String CONTENT_TYPE = "getServletContext()/vnd-excel";
          String excelNm=util.nvl(req.getParameter("excelNm"));
          String report=util.nvl(req.getParameter("Report"));
          String colSpan=util.nvl(req.getParameter("colSpan"));
          String qty=util.nvl(req.getParameter("qty"));
          String cts=util.nvl(req.getParameter("cts"));
          String avg=util.nvl(req.getParameter("avg"));
          String rap=util.nvl(req.getParameter("rap"));
          String vlu=util.nvl(req.getParameter("vlu"));
          String fapri=util.nvl(req.getParameter("fap"));
          String mfgpri=util.nvl(req.getParameter("mfgp"));
          String fileNm = excelNm+"ReportExcel"+util.getToDteTime()+".xls";
          res.setContentType(CONTENT_TYPE);
          res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
          HSSFWorkbook hwb = null;
          ExcelUtil xlUtil = new ExcelUtil();
          xlUtil.init(db, util, info);
          hwb = xlUtil.getDataGridInXl(req,colSpan,qty,cts,avg,rap,vlu,fapri,mfgpri,"N",report);
          OutputStream out = res.getOutputStream();
          hwb.write(out);
          out.flush();
          out.close();
            util.updAccessLog(req,res,"Mix Stock Summary", "createGridXL end");
          return null;
        }
    }
    
    public ActionForward createMixGridXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Mix Stock Summary", "createMixGridXL start");
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String excelNm=util.nvl(req.getParameter("excelNm"));
        String report=util.nvl(req.getParameter("Report"));
        String colSpan=util.nvl(req.getParameter("colSpan"));
        String qty=util.nvl(req.getParameter("qty"));
        String cts=util.nvl(req.getParameter("cts"));
        String avg=util.nvl(req.getParameter("avg"));
        String rap=util.nvl(req.getParameter("rap"));
        String fapri=util.nvl(req.getParameter("fap"));
        String mfgpri=util.nvl(req.getParameter("mfgp"));
        String fileNm = excelNm+"ReportExcel"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
        HSSFWorkbook hwb = null;
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        hwb = xlUtil.getMixDataGridInXl(req,colSpan,qty,cts,avg,rap,fapri,mfgpri,report);
        OutputStream out = res.getOutputStream();
        hwb.write(out);
        out.flush();
        out.close();
            util.updAccessLog(req,res,"Mix Stock Summary", "createMixGridXL end");
        return null;
        }
    }
   
    public ActionForward createPKTDtlXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Mix Stock Summary", "createPKTDtlXL start");
          ExcelUtil xlUtil = new ExcelUtil();
          xlUtil.init(db, util, info);
          OutputStream out = res.getOutputStream();
          String CONTENT_TYPE = "getServletContext()/vnd-excel";
          String fileNm = "PacketDtlReportExcel"+util.getToDteTime()+".xls";
          res.setContentType(CONTENT_TYPE);
          res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
          ArrayList pktList = (ArrayList)session.getAttribute("pktList");
          ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
          HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
          hwb.write(out);
          out.flush();
          out.close();
            util.updAccessLog(req,res,"Mix Stock Summary", "createPKTDtlXL end");
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
                util.updAccessLog(req,res,"Mix Stock Summary", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Mix Stock Summary", "init");
            }
            }
            return rtnPg;
            }
}
