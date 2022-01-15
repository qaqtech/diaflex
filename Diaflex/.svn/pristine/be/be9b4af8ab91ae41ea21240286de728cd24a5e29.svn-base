package ft.com.mpur;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.FormsUtil;
import ft.com.InfoMgr;
import ft.com.dao.ByrDao;
import ft.com.dao.GenDAO;
import ft.com.dao.UIForms;

import ft.com.dao.UIFormsFields;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.SearchQuery;
import ft.com.marketing.StockPrpUpdForm;
import ft.com.masters.CountryFrm;

import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.record.Margin;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class PurchaseDtlAction extends DispatchAction {
    private final String formName   = "purDtlForm";

    public PurchaseDtlAction() {
        super();
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
                util.updAccessLog(req,res,"Purchase Form", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Purchase Form", "init");
            }
            }
            return rtnPg;
            }
    
    public String initnormal(HttpServletRequest req , HttpServletResponse res,HttpSession session,DBUtil util) {
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
                rtnPg=util.checkUserPageRights("purchase/purchaseAction.do?","");
                if(rtnPg.equals("unauthorized"))
                util.updAccessLog(req,res,"Purchase Form", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Purchase Form", "init");
            }
            }
            return rtnPg;
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
        rtnPg=initnormal(req,res,session,util);
        }else
        rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
            util.updAccessLog(req,res,"Purchase Form", "load start");
            HashMap  formFields = info.getFormFields(formName);
            HashMap avgDtl = new HashMap();
            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        PurchaseDtlForm udf = (PurchaseDtlForm) af;
        ArrayList params = new ArrayList();
        String cnt = util.nvl((String)info.getDmbsInfoLst().get("CNT"));
        String purIdn = req.getParameter("purIdn");
        GenericInterface genericInt = new GenericImpl();
            
        String mdl="PUR_DTL_VIEW";
        String rughForm = util.nvl(req.getParameter("rughForm"),"N");
        String mixForm = util.nvl(req.getParameter("mixForm"),"N");
        String flg= util.nvl(req.getParameter("flg"),"N");
            if(rughForm.equals("Y")){
                mixForm="Y";
                flg="M";
                mdl="RGH_DTL_VIEW";
            }
        String lotDsc = util.nvl(req.getParameter("lotDsc"));
        if(!mixForm.equals("Y") || !flg.equals("M")){
        if (purIdn == null) {
            purIdn = (String)udf.getValue("purIdn");
        }
        if (purIdn == null) {
            purIdn = (String)info.getValue("purIdn");
        }
        udf.reset();
        params = new ArrayList();
        params = new ArrayList();
        params.add(purIdn);
        info.setValue("purIdn", purIdn);
        String    srchFields = helper.getSrchFields(daos, "pur_dtl_idn");
        String    srchQ      = " and pur_idn = ? ";
        ArrayList list       = helper.getSrchList(uiForms, "", srchFields, srchQ, params, daos, "VIEW");
            ArrayList vwPrpLst =genericInt.genericPrprVw(req, res, mdl, mdl);
        if (list.size() > 0) {
            int lmt    = list.size();
            int frmRec = Integer.parseInt(util.nvl(uiForms.getREC(), "0"));

            if (frmRec == 1) {
                lmt = 1;
            }

            for (int i = 0; i < lmt; i++) {
                GenDAO gen  = (GenDAO) list.get(i);
                String lIdn = gen.getIdn();

                // util.SOP(" i : "+i + " | Idn : "+ lIdn);
                // util.SOP(gen.toString());
                for (int j = 0; j < daos.size(); j++) {
                    UIFormsFields dao     = (UIFormsFields) daos.get(j);
                    String        lFld    = dao.getFORM_FLD();
                    String        lFrmFld = lFld;

                    if (frmRec > 1) {
                        lFrmFld = lFld + "_" + lIdn;
                    }

                    String fldAlias = util.nvl(dao.getALIAS());
                    String aliasFld = fldAlias.substring(fldAlias.indexOf(" ") + 1);

                    udf.setValue(lFrmFld, util.nvl((String) gen.getValue(lFld)));
                    udf.setValue(lFrmFld + "_dsp", util.nvl((String) gen.getValue(aliasFld)));
                }
                
               
                
                
            }
            
            
        }
        req.setAttribute(formName, list);
        if(vwPrpLst!=null && vwPrpLst.size()>0){
            String sql = "select a.pur_dtl_idn,pd.cts , pd.ref_idn, a.mprp , decode(b.dta_typ, 'C', a.val, 'N', to_char(a.num), 'D', to_char(a.dte, 'dd-Mon-rrrr'), a.txt)  val\n" + 
            "from pur_dtl pd,pur_prp a , mprp b , rep_prp c \n" + 
            "where pd.pur_dtl_idn=a.pur_dtl_idn and pd.stt not in ('IA','PI') \n" + 
            "and nvl(pd.flg, 'NA') not in('TRF','PI') and nvl(pd.flg2, 'NA') <> 'DEL' and nvl(pd.flg3, 'NA') <> 'SP'\n" + 
            "and a.pur_idn =? and a.mprp = b.prp  and b.prp = c.prp and c.prp = a.mprp and c.mdl=?\n" + 
            "and c.flg='Y' order by a.pur_dtl_idn,rnk";
            params = new ArrayList();
            params.add(purIdn);
            params.add(mdl);
            ArrayList outLst = db.execSqlLst("sql", sql, params);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                
                String pur_dtl_idn=util.nvl(rs.getString("pur_dtl_idn"));
                String mprp=util.nvl(rs.getString("mprp"));
                String val =util.nvl(rs.getString("val"),"0");
                String        lfldNm = mprp + "_" + pur_dtl_idn;
                udf.setValue(lfldNm, val);
                
            }
            rs.close();
            pst.close();
        }
        params = new ArrayList();
        params.add(purIdn);
        String ttlQ="select Trunc(Sum(a.Cts),2) cts,Trunc(Sum((a.rte*a.Cts)*nvl(a1.exh_rte,get_xrt(a1.cur))),2) inrvlu,Trunc(Sum(a.rte*a.Cts),2) vlu,Trunc(Sum(a.rte*a.Cts)/Sum(a.Cts),2) avg,\n" + 
        "trunc(((sum(trunc(a.Cts,2)*a.rte) / sum(trunc(a.Cts,2)* greatest(a.rap,1) ))*100) - 100, 2) avg_dis,\n" + 
        "trunc(((sum(trunc(a.Cts,2)*nvl(b.num,a.rte)) / sum(trunc(a.Cts,2)* greatest(a.rap,1) ))*100) - 100, 2) netavg_dis,\n" + 
        "Trunc(Sum(nvl(b.num,a.rte)*a.Cts),2) netvlu,\n" +
        "Trunc(Sum((nvl(b.num,a.rte)*a.Cts)*nvl(a1.exh_rte,get_xrt(a1.cur))),2) netinrvlu,"+
        "Trunc(Sum(nvl(b.num,a.rte)*a.Cts)/Sum(a.Cts),2) netavg \n" + 
        "From Mpur A1, Pur_Dtl A,pur_prp b \n" + 
        "Where A.Pur_Idn = A1.Pur_Idn and a.pur_dtl_idn=b.pur_dtl_idn and b.mprp='CP'\n" + 
        "And A1.Pur_Idn  = ? and a.mstk_idn is null and nvl(a.flg,'NA') not in ('TRF','DEL') and nvl(a1.flg2, 'NA') <> 'DEL'";
            if(cnt.equalsIgnoreCase("NJ")){
                ttlQ="select Trunc(Sum(a.Cts),2) cts,Trunc(Sum((a.rte*a.Cts)*nvl(a1.exh_rte,get_xrt(a1.cur))),2) inrvlu,Trunc(Sum(a.rte*a.Cts),2) vlu,\n" + 
                "Trunc(Sum(a.rte*a.Cts)/Sum(a.Cts),2) avg,\n" + 
                "trunc(((sum(trunc(a.Cts,2)*a.rte) / sum(trunc(a.Cts,2)* greatest(a.rap,1) ))*100) - 100, 2) avg_dis,\n" + 
                "trunc((((sum(trunc(a.Cts,2)*nvl(b.num,a.rte))-(Sum(a.rte*a.Cts)*nvl(A1.aadat_comm,0)/100)) / sum(trunc(a.Cts,2)* greatest(a.rap,1) ))*100) - 100, 2) netavg_dis,\n" + 
                "Trunc((Sum(nvl(b.num,a.rte)*a.Cts)-(Sum(a.rte*a.Cts)*nvl(A1.aadat_comm,0)/100)) ,2) netvlu,\n" + 
                "Trunc(Sum((nvl(b.num,a.rte)*a.Cts)*nvl(a1.exh_rte,get_xrt(a1.cur))) - (Sum(a.rte*a.Cts*nvl(a1.exh_rte,get_xrt(a1.cur)))*nvl(A1.aadat_comm,0)/100) ,2) netinrvlu,\n" + 
                "Trunc((Sum(nvl(b.num,a.rte)*a.Cts)-(Sum(a.rte*a.Cts)*nvl(A1.aadat_comm,0)/100))/Sum(a.Cts),2) netavg \n" + 
                "From Mpur A1, Pur_Dtl A,pur_prp b \n" + 
                "Where A.Pur_Idn = A1.Pur_Idn and a.pur_dtl_idn=b.pur_dtl_idn and b.mprp='CP'\n" + 
                "And A1.Pur_Idn  =? and a.mstk_idn is null and nvl(a.flg,'NA') not in('TRF','DEL') and nvl(a1.flg2, 'NA') <> 'DEL' \n" + 
                "group by A1.aadat_comm ";
                    
                    
                    
            }
            if(cnt.equalsIgnoreCase("HJ")){
                ttlQ="select Trunc(Sum(a.Cts),2) cts,Trunc(Sum((a.rte*a.Cts)*nvl(a1.exh_rte,get_xrt(a1.cur))),2) inrvlu,Trunc(Sum(a.rte*a.Cts),2) vlu,\n" + 
                "Trunc(Sum(a.rte*a.Cts)/Sum(a.Cts),2) avg,\n" + 
                "trunc(((sum(trunc(a.Cts,2)*a.rte) / sum(trunc(a.Cts,2)* greatest(a.rap,1) ))*100) - 100, 2) avg_dis,\n" + 
                "trunc((((sum(trunc(a.Cts,2)*nvl(b.num,a.rte))-(Sum(a.rte*a.Cts)*nvl(A1.aadat_comm,0)/100)) / sum(trunc(a.Cts,2)* greatest(a.rap,1) ))*100) - 100, 2) netavg_dis,\n" + 
                "Trunc((Sum(nvl(b.num,a.rte)*a.Cts)-(Sum(a.rte*a.Cts)*nvl(A1.aadat_comm,0)/100)) ,2) netvlu,\n" + 
                "Trunc(Sum((nvl(b.num,a.rte)*a.Cts)*nvl(a1.exh_rte,get_xrt(a1.cur))) - (Sum(a.rte*a.Cts*nvl(a1.exh_rte,get_xrt(a1.cur)))*nvl(A1.aadat_comm,0)/100) ,2) netinrvlu,\n" + 
                "Trunc((Sum(nvl(b.num,a.rte)*a.Cts)-(Sum(a.rte*a.Cts)*nvl(A1.aadat_comm,0)/100))/Sum(a.Cts),2) netavg \n" + 
                "From Mpur A1, Pur_Dtl A,pur_prp b \n" + 
                "Where A.Pur_Idn = A1.Pur_Idn and a.pur_dtl_idn=b.pur_dtl_idn and b.mprp='NET_PUR_RTE'\n" + 
                "And A1.Pur_Idn  =? and a.mstk_idn is null and nvl(a.flg,'NA') not in('TRF','DEL') and nvl(a1.flg2, 'NA') <> 'DEL' \n" + 
                "group by A1.aadat_comm ";
                    
                    
                    
            }
          ArrayList rsList = db.execSqlLst(" Memo Info", ttlQ, params); 
          PreparedStatement pst = (PreparedStatement)rsList.get(0);
          ResultSet mrs = (ResultSet)rsList.get(1);
            if (mrs.next()) {
                avgDtl.put("cts",util.nvl(mrs.getString("cts")));
                avgDtl.put("vlu",util.nvl(mrs.getString("vlu")));
                avgDtl.put("avg_dis",util.nvl(mrs.getString("avg_dis")));
                avgDtl.put("avg",util.nvl(mrs.getString("avg")));
                avgDtl.put("inrvlu",util.nvl(mrs.getString("inrvlu")));
                avgDtl.put("netvlu",util.nvl(mrs.getString("netvlu")));
                avgDtl.put("netinrvlu",util.nvl(mrs.getString("netinrvlu")));
                avgDtl.put("netavg_dis",util.nvl(mrs.getString("netavg_dis")));
                avgDtl.put("netavg",util.nvl(mrs.getString("netavg")));
            }
            mrs.close();
            pst.close();
        udf.setValue("purIdn", purIdn);
        req.setAttribute("avgDtl", avgDtl);
        
        ArrayList pageList=new ArrayList();
        HashMap pageDtlMap=new HashMap();
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("PUR_DTL");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("PUR_DTL");
        allPageDtl.put("PUR_DTL",pageDtl);
        }
        info.setPageDetails(allPageDtl);
            
            pageList=(ArrayList)pageDtl.get("CUSTOM_REF_IDN");
            if(pageList!=null && pageList.size() >0){
                  for(int j=0;j<pageList.size();j++){
                      pageDtlMap=(HashMap)pageList.get(j);
                      String dflt_val=(String)pageDtlMap.get("dflt_val");
                      String val_cond=(String)pageDtlMap.get("val_cond");
                      String  fld_typ=(String)pageDtlMap.get("fld_typ");
                      for(int l=1;l<=5;l++){
                              int seqVal=util.getSeqVal(val_cond);
                              if(fld_typ.equals("HSEQ"))
                              udf.setValue("ref_idn_"+l,dflt_val+String.valueOf(seqVal));
                              if(fld_typ.equals("SEQ"))
                              udf.setValue("ref_idn_"+l,String.valueOf(seqVal));
                              if(fld_typ.equals("HLOOP"))
                              udf.setValue("ref_idn_"+l,dflt_val+l);
                      }
                  }
             }
        
        util.updAccessLog(req,res,"Purchase Form", "load end");
        
         req.setAttribute("vwPrpLst", vwPrpLst);
        return am.findForward("view");
        }else{
            return loadmixForm(am, af, req, res,purIdn,lotDsc);
        }
        }
    }

    public ActionForward loadmixForm(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res,String purIdn,String lotDsc)
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
            util.updAccessLog(req,res,"Pur Update", "loadmixForm Start");
            PurchaseDtlForm udf = (PurchaseDtlForm) af;
            GenericInterface genericInt = new GenericImpl();
            String rughForm = util.nvl(req.getParameter("rughForm"),"N");
            String mdl="PUR_MIX_VW";
          HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        
            HashMap pageDtl=(HashMap)allPageDtl.get("MIX_PUR_DTL");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("MIX_PUR_DTL");
            allPageDtl.put("MIX_PUR_DTL",pageDtl);
            }
            if(rughForm.equals("Y")){
                mdl="RUGH_MIX_VW";
                pageDtl=(HashMap)allPageDtl.get("RGH_PUR_DTL");
                if(pageDtl==null || pageDtl.size()==0){
                pageDtl=new HashMap();
                pageDtl=util.pagedef("RGH_PUR_DTL");
                allPageDtl.put("RGH_PUR_DTL",pageDtl);
                }
            }
            info.setPageDetails(allPageDtl);
           ArrayList vwPrpLst =genericInt.genericPrprVw(req, res, mdl, mdl);

            HashMap avgDtl = new HashMap();  
            ArrayList purdtlidnLst=new ArrayList();
            udf.reset();
            String cts="";
            String rte="";
            String sql = "select a.pur_dtl_idn,pd.cts , pd.ref_idn, a.mprp , decode(b.dta_typ, 'C', a.val, 'N', to_char(a.num), 'D', to_char(a.dte, 'dd-Mon-rrrr'), a.txt)  val\n" + 
            "from pur_dtl pd,pur_prp a , mprp b , rep_prp c \n" + 
            "where pd.pur_dtl_idn=a.pur_dtl_idn and pd.stt not in ('IA','PI') \n" + 
            "and nvl(pd.flg, 'NA') not in('TRF','PI') and nvl(pd.flg2, 'NA') <> 'DEL' and nvl(pd.flg3, 'NA') <> 'SP'\n" + 
            "and a.pur_idn =? and a.mprp = b.prp  and b.prp = c.prp and c.prp = a.mprp and c.mdl=?\n" + 
            "and c.flg='Y' order by a.pur_dtl_idn,rnk";
            ArrayList ary = new ArrayList();
            ary.add(purIdn);
            ary.add(mdl);
          ArrayList rsList = db.execSqlLst("sql", sql, ary); 
          PreparedStatement stmt = (PreparedStatement)rsList.get(0);
          ResultSet rs = (ResultSet)rsList.get(1);
            while(rs.next()){
                String pur_dtl_idn=util.nvl(rs.getString("pur_dtl_idn"));
                String mprp=util.nvl(rs.getString("mprp"));
                String val =util.nvl(rs.getString("val"),"0");
                String vnm = util.nvl(rs.getString("ref_idn"),"0");
                avgDtl.put("VNM_"+pur_dtl_idn, vnm);
                udf.setValue(pur_dtl_idn+"_"+mprp, val);
                if(!purdtlidnLst.contains(pur_dtl_idn))
                purdtlidnLst.add(pur_dtl_idn);
                if(mprp.equals("CRTWT")){
                    cts=val;
                    avgDtl.put("CTS_"+pur_dtl_idn, cts);
                }
                if(mprp.equals("RTE"))
                    rte=val;
                if(!cts.equals("")  && !rte.equals("")){
                    double vlu=Double.parseDouble(cts)*Double.parseDouble(rte);
                    udf.setValue(pur_dtl_idn+"_VLU",String.valueOf(util.roundToDecimals((vlu),2)));
                    cts="";
                    rte="";
                }
            }
            rs.close();  
            stmt.close();
            String lastCnt ="select rgh_pkg.get_lot_vnm('"+lotDsc+"', '_', 'PUR') lotNum from dual";
           rsList = db.execSqlLst("lastCnt", lastCnt, new ArrayList());
           stmt = (PreparedStatement)rsList.get(0);
           rs = (ResultSet)rsList.get(1);
            while(rs.next()){
                 req.setAttribute("lstCnt", rs.getInt("lotNum"));
            }
            rs.close();
            stmt.close();
        
            
            ArrayList params = new ArrayList();
            String ttlQ="         select 'CUR' typ ,Sum(a.qty) qty,to_char(Sum(a.Cts),'99999999.90') cts,to_char(Sum(a.rte*a.Cts),'9999999999999.90') vlu,\n" + 
            "          to_char(Sum(a.rte*a.Cts)/Sum(a.Cts),'9999999999999.90') avgrte\n" + 
            "            From Mpur A1, Pur_Dtl A Where A.Pur_Idn = A1.Pur_Idn And A1.Pur_Idn  = ? \n" + 
            "            and a.mstk_idn is null and nvl(a.flg,'NA') not in ('TRF','DEL') and nvl(a1.flg2, 'NA') <> 'DEL' \n" + 
            "           union\n" + 
            "          select 'PUR' typ, sum(a.ttl_qty) qty ,to_char(Sum(a.ttl_Cts),'99999999.90') ttlcts,to_char(Sum(a.avg_rte*a.ttl_Cts),'9999999999999.90') ttlvlu,\n" + 
            "          to_char(Sum(a.avg_rte*a.ttl_Cts)/Sum(a.ttl_Cts),'9999999999999.90') ttlavgrte\n" + 
            "           from Mpur a where pur_idn=? \n" + 
            "           union\n" + 
            "            select 'TRF' typ, Sum(nvl(a.qty,1)) qty,to_char(Sum(a.Cts),'99999999.90') cts,to_char(Sum(a.rte*a.Cts),'9999999999999.90') vlu,\n" + 
            "            to_char(Sum(a.rte*a.Cts)/Sum(a.Cts),'9999999999999.90') avgrte\n" + 
            "            From Mpur A1, Pur_Dtl A Where A.Pur_Idn = A1.Pur_Idn And A1.Pur_Idn  = ?  \n" + 
            "            and a.mstk_idn is not null and nvl(a.flg,'NA') in ('TRF') and nvl(a1.flg2, 'NA') <> 'DEL' ";
            params.add(purIdn);
            params.add(purIdn);  
            params.add(purIdn);
            ArrayList outLst = db.execSqlLst(" Memo Info", ttlQ, params);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet mrs = (ResultSet)outLst.get(1);       
            while (mrs.next()) {
                String typ = util.nvl(mrs.getString("typ"));
                avgDtl.put(typ+"_cts",util.nvl(mrs.getString("cts")));
                avgDtl.put(typ+"_vlu",util.nvl(mrs.getString("vlu")));
                avgDtl.put(typ+"_avgrte",util.nvl(mrs.getString("avgrte")));
                avgDtl.put(typ+"_qty",util.nvl(mrs.getString("qty")));
                
                
            }
            mrs.close();
            pst.close();
            
            req.setAttribute("avgDtl", avgDtl);
            
            session.setAttribute("purdtlidnLst", purdtlidnLst);
            info.setValue("purIdn", purIdn);
            info.setValue("lotDsc", lotDsc);
            udf.setValue("purIdn", purIdn);
            req.setAttribute("lotDsc", lotDsc);
            util.updAccessLog(req,res,"Pur Update", "loadmixForm end");
        return am.findForward("loadmixForm");
        }
    }
    
    public ActionForward savemixForm(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Pur Update", "loadmixForm Start");
            PurchaseDtlForm udf = (PurchaseDtlForm) af;
            GenericInterface genericInt = new GenericImpl();
            String rughForm = util.nvl(req.getParameter("rughForm"),"N");

            ArrayList vwPrpLst =null;
            if(rughForm.equals("Y"))
             vwPrpLst = genericInt.genericPrprVw(req, res, "RUGH_MIX_VW", "RUGH_MIX_VW");
            else
             vwPrpLst =genericInt.genericPrprVw(req, res, "PUR_MIX_VW", "PUR_MIX_VW");
            
            ArrayList purdtlidnLst=(ArrayList)session.getAttribute("purdtlidnLst");
            String purIdn = util.nvl((String)info.getValue("purIdn"));
            String lotDsc = util.nvl((String)info.getValue("lotDsc"));
            ArrayList ary = new ArrayList();
            ArrayList msgList = new ArrayList();
            String addnew = util.nvl((String)udf.getValue("addnew"));
            String save = util.nvl((String)udf.getValue("save"));
            String trfToStk = util.nvl((String)udf.getValue("trfToStk"));
            if(!addnew.equals("")){
            for(int p=1; p<=5 ; p++){
            String vnm = util.nvl((String)udf.getValue(p+"_VNM"));
            String cts = util.nvl((String)udf.getValue(p+"_CRTWT"));
            String rte = util.nvl((String)udf.getValue(p+"_RTE"));
            if(!vnm.equals("") && !cts.equals("") && !rte.equals("")){
                String purDtlIdn="";
                String seqNme = "select pur_dtl_seq.nextval from dual";
                ResultSet rs = db.execSql("pur_dtl_seq", seqNme, new ArrayList());
                if(rs.next())
                purDtlIdn = rs.getString(1);
                rs.close();
                String insIntoQ = " insert into pur_dtl ( pur_dtl_idn, pur_idn,ref_idn,cts,rap,rte ";
                String insValQ  = "values(?,?,?,?,?,?  ";
                ary = new ArrayList();
                ary.add(purDtlIdn);
                ary.add(purIdn);
                ary.add(vnm);
                ary.add(cts);
                ary.add("1");
                ary.add(rte);
                String sql = insIntoQ + ") " + insValQ + ")";
                int    ct  = db.execUpd(" Ins Menu", sql, ary);
            if(ct>0){
                msgList.add("Packet Created : "+vnm);
            for(int i=0; i < vwPrpLst.size() ; i++){
                String lprp = (String)vwPrpLst.get(i);
                String prpVal = util.nvl((String)udf.getValue(p+"_"+lprp));
                if(!prpVal.equals("")){
                    ary = new ArrayList();
                    ary.add(purIdn);
                    ary.add(purDtlIdn);
                    ary.add(lprp);
                    ary.add(prpVal);
                    db.execCallDir("updatePrp","PUR_PKG.PKT_PRP_UPD(pPurIdn => ?, pPurDtlIdn => ?, pPRP => ?, pVal => ?)", ary);
                }
            }
            }
            }
            }
            }
            if(!save.equals("")){
            for(int p=0; p< purdtlidnLst.size() ; p++){
            String purDtlIdn=util.nvl((String)purdtlidnLst.get(p));
            String chkVal = util.nvl((String)udf.getValue("upd_"+purDtlIdn));
            if(!chkVal.equals("")){
            for(int i=0; i < vwPrpLst.size() ; i++){
                String lprp = (String)vwPrpLst.get(i);
                String prpVal = util.nvl((String)udf.getValue(purDtlIdn+"_"+lprp));
                if(!prpVal.equals("")){
                    ary = new ArrayList();
                    ary.add(purIdn);
                    ary.add(purDtlIdn);
                    ary.add(lprp);
                    ary.add(prpVal);
                    int ct = db.execCall("updatePrp","PUR_PKG.PKT_PRP_UPD(pPurIdn => ?, pPurDtlIdn => ?, pPRP => ?, pVal => ?)", ary);
                }
            }
            }
            }
            }
            
            if(!trfToStk.equals("")){
            String trfStkidn="";
            String trftostkproc="PUR_PKG.TRF_TO_STK(pPurDtlIdn => ?, pStkIdn => ?,  pMsg => ?)";
            for(int p=0; p< purdtlidnLst.size() ; p++){
            String purDtlIdn=util.nvl((String)purdtlidnLst.get(p));
            String chkVal = util.nvl((String)udf.getValue("upd_"+purDtlIdn));
            if(!chkVal.equals("")){
                ary = new ArrayList();
                ary.add(purDtlIdn);
                ArrayList out = new ArrayList();
                out.add("I");
                out.add("V");
                CallableStatement cst = db.execCall("TrfToStk",trftostkproc, ary ,out);
                String newVnm= cst.getString(ary.size()+2); 
                trfStkidn=trfStkidn+","+newVnm;
              cst.close();
              cst=null;
                msgList.add("Packet Transfer WIth New : "+newVnm);
            }
            }
            }
            util.updAccessLog(req,res,"Pur Update", "loadmixForm end");
            req.setAttribute("msgList", msgList);
        return loadmixForm(am, af, req, res,purIdn,lotDsc);
        }
    }
    
    public ActionForward LoadSplitStone(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            String purIdn = util.nvl(req.getParameter("purIdn"));
            String purDtlIdn =  util.nvl(req.getParameter("purDtlIdn"));
            String rughForm = util.nvl(req.getParameter("rughForm"),"N");/*  */
            String lotdsc =  util.nvl(req.getParameter("lotDsc"));
            GenericInterface genericInt = new GenericImpl();
          
            String mdl="PUR_MIX_VW";
            if(rughForm.equals("Y"))
                mdl="RUGH_MIX_VW";
            ArrayList vwPrpLst =genericInt.genericPrprVw(req, res, mdl, mdl);

                 
            ArrayList purdtlidnLst=new ArrayList();
            String cts="";
            String rte="";
            HashMap purPrpDtl = new HashMap();
            String sql = "select a.pur_dtl_idn,pd.ref_idn, a.mprp , decode(b.dta_typ, 'C', a.val, 'N', to_char(a.num), 'D', to_char(a.dte, 'dd-Mon-rrrr'), a.txt)  val\n" + 
            "from pur_dtl pd,pur_prp a , mprp b , rep_prp c \n" + 
            "where pd.pur_dtl_idn=a.pur_dtl_idn and pd.stt not in ('IA','PI') \n" + 
            "and nvl(pd.flg, 'NA') not in('TRF','PI') and nvl(pd.flg2, 'NA') <> 'DEL' and nvl(flg3,'NA')= ? \n" + 
            "and a.pur_idn =? and pd.pur_dtl_rt= ? and a.mprp = b.prp  and b.prp = c.prp and c.prp = a.mprp and c.mdl=?\n" + 
            "and c.flg='Y' order by a.pur_dtl_idn,rnk";
            ArrayList ary = new ArrayList();
            ary.add("SP");
            ary.add(purIdn);
            ary.add(purDtlIdn);
            ary.add(mdl);
          ArrayList  rsList = db.execSqlLst("sql", sql, ary);
         PreparedStatement stmt = (PreparedStatement)rsList.get(0);
        ResultSet  rs = (ResultSet)rsList.get(1);
            while(rs.next()){
                String pur_dtl_idn=util.nvl(rs.getString("pur_dtl_idn"));
                String mprp=util.nvl(rs.getString("mprp"));
                String val =util.nvl(rs.getString("val"),"0");
                String vnm =util.nvl(rs.getString("ref_idn"),"0");
                purPrpDtl.put(pur_dtl_idn+"_VNM" ,vnm );
                purPrpDtl.put(pur_dtl_idn+"_"+mprp ,val );
                if(!purdtlidnLst.contains(pur_dtl_idn))
                purdtlidnLst.add(pur_dtl_idn);
                if(mprp.equals("CRTWT"))
                    cts=val;
                if(mprp.equals("RTE"))
                    rte=val;
                if(!cts.equals("")  && !rte.equals("")){
                    double vlu=Double.parseDouble(cts)*Double.parseDouble(rte);
                    purPrpDtl.put(pur_dtl_idn+"_VLU",String.valueOf(util.roundToDecimals((vlu),2)));
                    cts="";
                    rte="";
                }
            }
            rs.close();   
            stmt.close();
            String lastCnt ="select rgh_pkg.get_lot_vnm('"+lotdsc+"', '.', 'PUR') lotNum from dual";
           rsList = db.execSqlLst("lastCnt", lastCnt, new ArrayList());
           stmt = (PreparedStatement)rsList.get(0);
           rs = (ResultSet)rsList.get(1);
            while(rs.next()){
                 req.setAttribute("lstCnt", rs.getString("lotNum"));
            }
            rs.close();
            stmt.close();
           
            
            ArrayList params = new ArrayList();
            String ttlQ="select to_char(Sum(a.Cts),'99999999.90') cts,to_char(Sum(a.rte*a.Cts),'9999999999999.90') vlu,\n" + 
            "to_char(Sum(a.rte*a.Cts)/Sum(a.Cts),'9999999999999.90') avgrte "+
            "From  Pur_Dtl A Where a.pur_dtl_rt=? \n" + 
            " and a.mstk_idn is null and nvl(a.flg,'NA') not in('TRF','DEL') and nvl(a.flg2, 'NA') <> 'DEL' ";
         
            params.add(purDtlIdn);
            ArrayList outLst = db.execSqlLst(" Memo Info", ttlQ, params);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet mrs = (ResultSet)outLst.get(1);       
            if (mrs.next()) {
                purPrpDtl.put("cts",util.nvl(mrs.getString("cts")));
                purPrpDtl.put("vlu",util.nvl(mrs.getString("vlu")));
                purPrpDtl.put("avgrte",util.nvl(mrs.getString("avgrte")));
               
            }
            mrs.close();
            pst.close();
            
            req.setAttribute("purPrpDtl", purPrpDtl);
            req.setAttribute("purdtlidnLst", purdtlidnLst);
            return am.findForward("loadSplit");
        }
      }
    
    public ActionForward SaveSplitStone(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Pur Update", "loadmixForm Start");
            String purIdn = util.nvl(req.getParameter("purIdn"));
            String purDtlIdn =  util.nvl(req.getParameter("purDtlIdn"));
            String vnm = util.nvl(req.getParameter("vnm"));
            String cts = util.nvl(req.getParameter("cts"));
            String qty = util.nvl(req.getParameter("qty"));
            String msg = "";
             if(!purIdn.equals("") && !purDtlIdn.equals("") && !cts.equals("")){
                db.setAutoCommit(false);
                boolean isCommit=true;
                try {
                    String pur_dtl_seq = "";
                    String seqNme = "select pur_dtl_seq.nextval from dual";
                    ResultSet rs =
                        db.execSql("pur_dtl_seq", seqNme, new ArrayList());
                    if (rs.next())
                        pur_dtl_seq = rs.getString(1);
                    rs.close();

                    ArrayList params = new ArrayList();
                    String insIntoQ =
                        " insert into pur_dtl(pur_dtl_idn,pur_idn,ref_idn,qty,cts,rte,dis,rap,cmp,stt,flg,flg3,rgh_sz,RGH_QUALITY,pur_dtl_rt) \n" +
                        " select ?,?,?,?,?,rte,dis,rap,cmp,stt,flg,'SP',rgh_sz,RGH_QUALITY,? from pur_dtl where pur_dtl_idn=? ";
                    params.add(pur_dtl_seq);
                    params.add(purIdn);
                    params.add(vnm);
                    params.add(qty);
                    params.add(cts);
                    params.add(purDtlIdn);
                    params.add(purDtlIdn);
                    int ct = db.execUpd("insIntoQ", insIntoQ, params);
                    if (ct > 0) {
                        String insertPurPRp =
                            "insert into pur_prp (pur_prp_idn,pur_dtl_idn,pur_idn,mprp,val,num,dte,txt,srt,dta_typ,stt,flg) " +
                            " select pur_prp_seq.nextval,?,?,mprp,val,num,dte,txt,srt,dta_typ,stt,flg " +
                            " from pur_prp where pur_dtl_idn=? and mprp not in ('CRTWT','VNM') ";
                        params = new ArrayList();
                        params.add(pur_dtl_seq);
                        params.add(purIdn);
                        params.add(purDtlIdn);
                        ct = db.execUpd("insertPurPRp", insertPurPRp, params);
                        if (ct >0){
                            
                            ArrayList  ary = new ArrayList();
                            ary.add(purIdn);
                            ary.add(pur_dtl_seq);
                            ary.add("CRTWT");
                            ary.add(cts);
                            ct = db.execCall("updatePrp","PUR_PKG.PKT_PRP_UPD(pPurIdn => ?, pPurDtlIdn => ?, pPRP => ?, pVal => ?)", ary);
                            
                            ary = new ArrayList();
                            ary.add(purIdn);
                            ary.add(pur_dtl_seq);
                            ary.add("VNM");
                            ary.add(vnm);
                            ct = db.execCall("updatePrp","PUR_PKG.PKT_PRP_UPD(pPurIdn => ?, pPurDtlIdn => ?, pPRP => ?, pVal => ?)", ary);
                            
                            
                            ary = new ArrayList();
                            ary.add(purIdn);
                            ary.add(pur_dtl_seq);
                            ary.add("QTY");
                            ary.add(qty);
                            ct = db.execCall("updatePrp","PUR_PKG.PKT_PRP_UPD(pPurIdn => ?, pPurDtlIdn => ?, pPRP => ?, pVal => ?)", ary);
                            
                            
                            
                            
                        }else
                        isCommit = false;

                    } else
                        isCommit = false;
                } catch (SQLException sqle) {
                    // TODO: Add catch code
                    sqle.printStackTrace();
                } finally {
                    db.setAutoCommit(true);
                }
                if(isCommit){
                     db.doCommit();
                     msg="Procee done successfully..";
                }else{
                    
                     db.doRollBack();
                    msg = "Some error in process..";
                }
                
             }else{
                 msg="Data transfer error..";
             }
            
    req.setAttribute("msg", msg);
            return LoadSplitStone(am, af, req, res);
        }
        }
    
    public ActionForward deletemixForm(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        rtnPg=initnormal(req,res,session,util);
        }else{
        rtnPg="connExists";   
        }
        }else
        rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
            util.updAccessLog(req,res,"Purchase Form", "delete start");
        PurchaseDtlForm udf = (PurchaseDtlForm) af;
        ArrayList ary = new ArrayList();
        String purDtlIdn = util.nvl((String)req.getParameter("purDtlIdn"));
        String purIdn = util.nvl((String)info.getValue("purIdn"));
        String lotDsc = util.nvl((String)info.getValue("lotDsc"));
        String deleteCity = "delete pur_prp where pur_dtl_idn= ?";
        ary = new ArrayList();
        ary.add(purDtlIdn);
        int ct = db.execUpd("deleteCity", deleteCity, ary);
        deleteCity = "delete pur_dtl where pur_dtl_idn= ?";
        ary = new ArrayList();
        ary.add(purDtlIdn);
        ct = db.execUpd("deleteCity", deleteCity, ary);
        util.updAccessLog(req,res,"Purchase Form", "delete end");
        return loadmixForm(am, af, req, res,purIdn,lotDsc);
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
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        rtnPg=initnormal(req,res,session,util);
        }else
        rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
            util.updAccessLog(req,res,"Purchase Form", "save start");
            HashMap  formFields = info.getFormFields(formName);
            GenericInterface genericInt = new GenericImpl();
            String mdl="PUR_DTL_VIEW";
            ArrayList vwPrpLst =genericInt.genericPrprVw(req, res, mdl, mdl);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        PurchaseDtlForm udf = (PurchaseDtlForm) af;
        String cnt=util.nvl((String)info.getDmbsInfoLst().get("CNT"));
        ArrayList errorList = new ArrayList();
        int    lmt        = Integer.parseInt(util.nvl(uiForms.getREC(), "5"));
        String purIdn = (String) udf.getValue("purIdn");

        if (udf.getAddnew() != null) {
          

            for (int i = 1; i <= lmt; i++) {
                int    lIdn     = i;
                boolean isValid = false;
                String pur_dtl_seq="";
                String seqNme = "select pur_dtl_seq.nextval from dual";
                ResultSet rs = db.execSql("pur_dtl_seq", seqNme, new ArrayList());
                if(rs.next())
                   pur_dtl_seq = rs.getString(1);
                rs.close();
                
                ArrayList params   = new ArrayList();
                String insIntoQ = " insert into pur_dtl ( pur_dtl_idn, pur_idn ";
                String insValQ  = "values(?,?  ";
                String lrte="";
                String calCts ="";
                String calQty ="";
                params.add(pur_dtl_seq);
                params.add(purIdn);

                for (int j = 0; j < daos.size(); j++) {
                    UIFormsFields dao   = (UIFormsFields) daos.get(j);
                    String        lFld  = dao.getFORM_FLD();
                    String        fldNm = lFld + "_" + lIdn;
                    String        lVal  = util.nvl((String) udf.getValue(fldNm));
                    String        lReqd = dao.getREQ_YN();    // util.nvl((String)formFields.get(lFld+"REQ"));
                    if(lFld.equals("rte"))
                        lrte=lVal;
                    
                    if(lFld.equals("cts"))
                        calCts=lVal;
                    
                    if(lFld.equals("qty"))
                        calQty=lVal;
                    
                    String        lDsc = dao.getDSP_TTL();
                    if ((lReqd.equalsIgnoreCase("Y")) && (lVal.length() == 0) ) {
                        String errorMsg = lIdn + "-" + lDsc + " Required.";

                        errors.add(errorMsg);
                        params.clear();

                    } else {
                        String lTblFld = dao.getTBL_FLD().toLowerCase();    // util.nvl((String)formFields.get(lFld+"TF"));

                        insIntoQ += ", " + lTblFld;
                        if((dao.getFLD_TYP().equals("DT")) && (lVal.length() == 10))
                            insValQ += ", to_date(?, 'dd-mm-rrrr')";
                        else
                            insValQ  += ", ?";
                        params.add(lVal);
                        
                       if(lReqd.equalsIgnoreCase("Y"))
                          isValid = true;
                    }
                    
                    
                }
                
               
                int    ct=0;
                    if (params.size() > 0 && errors.size()==0) {
                        String sql = insIntoQ + ") " + insValQ + ")";
                       ct = db.execUpd(" Ins Menu", sql, params);
                      }
                
                if(ct > 0){
                    
                    if(cnt.equalsIgnoreCase("RN")){
                    boolean numerisStt = false;
                    if(!calCts.equals("") && !calQty.equals("")){
                        double calDiv = Double.parseDouble(calCts)/Double.parseDouble(calQty);
                        HashMap prp = info.getPrp();
                        ArrayList valList = (ArrayList)prp.get("MIX_SIZEV");
                        if(valList!=null && valList.size()>0){
                            for(int l=0;l<valList.size();l++){
                                String mixSizeVal =(String)valList.get(l);
                                    String[] split_mixSizeVal = mixSizeVal.split("-");
                                if(split_mixSizeVal!=null && split_mixSizeVal.length >0){
                                  numerisStt= util.isNumeric(split_mixSizeVal[0]);
                                  
                                if(numerisStt){
                                    double minVal = Double.parseDouble(split_mixSizeVal[0]);
                                    double maxval =minVal; 
                                    if(split_mixSizeVal.length==2){
                                    maxval = Double.parseDouble(split_mixSizeVal[1]);
                                    }else{
                                            minVal=0.0;
                                        }
                                    if( calDiv >= minVal && calDiv <= maxval){
                                        params=new ArrayList();
                                        params.add(purIdn);
                                        params.add(pur_dtl_seq);
                                        params.add("MIX_SIZE");
                                        params.add(mixSizeVal);
                                         ct = db.execCall("PKT PRP update", "PUR_PKG.PKT_PRP_UPD(pPurIdn => ?, pPurDtlIdn => ?, pPRP => ?, pVal => ?)", params);
                                     break;
                                        
                                    }
                                    
                                }
                                }
                            }
                            }
                      
                        }
                }
                    
                if(vwPrpLst!=null && vwPrpLst.size()>0 ){
                for (int k= 0; k < vwPrpLst.size(); k++) {
                    String        lprp  = (String)vwPrpLst.get(k);
                    String        lfldNm = lprp + "_" + lIdn;
                    String        llVal  = util.nvl((String) udf.getValue(lfldNm));
                   
                    params=new ArrayList();
                    params.add(purIdn);
                    params.add(pur_dtl_seq);
                    params.add(lprp);
                    params.add(llVal);
                     ct = db.execCall("cp update", "PUR_PKG.PKT_PRP_UPD(pPurIdn => ?, pPurDtlIdn => ?, pPRP => ?, pVal => ?)", params);

                    
                } 
                }
                
                }
                    
                    if(isValid){
                        errorList.add(errors);
                        errors = new ArrayList();
                    }
                
                
            }
        }

        if (udf.getModify() != null) {
            

            String srchFields = helper.getSrchFields(daos, "pur_dtl_idn", false);
            String srchQ      = " and pur_idn = ? ";
            String calCts ="";
            String calQty ="";
            // ArrayList list = helper.getSrchList(uiForms, "", srchFields, srchQ, params, daos, "VIEW");
            String getMenu    = "select " + srchFields + " from pur_dtl where 1 = 1 " + srchQ + " order by srt";
            ArrayList params     = new ArrayList();

            params.add(purIdn);

          ArrayList rsList = db.execSqlLst("getUIFormFields for update", getMenu, params);
          PreparedStatement pst = (PreparedStatement)rsList.get(0);
          ResultSet rs = (ResultSet)rsList.get(1);
            while (rs.next()) {
                int    lIdn  = rs.getInt(1);
                String updQ  = " update pur_dtl set pur_dtl_idn = pur_dtl_idn ";
                String condQ = " where pur_dtl_idn = ? ";
                boolean isValid = false;
                params = new ArrayList();
                String lrte="";
                String isChecked = util.nvl((String) udf.getValue("upd_" + lIdn));

                if (isChecked.length() > 0) {

                    // params.add(Integer.toString(menuIdn));
                    for (int j = 0; j < daos.size(); j++) {
                        UIFormsFields dao   = (UIFormsFields) daos.get(j);
                        String        lFld  = util.nvl(dao.getFORM_FLD());    // (String)flds.get(j);
                        String        fldNm = lFld + "_" + lIdn;
                        String        lVal  = util.nvl((String) udf.getValue(fldNm));
                        String        lReqd = dao.getREQ_YN();    // util.nvl((String)formFields.get(lFld+"REQ"));
                        if(lFld.equals("rte"))
                            lrte=lVal;
                        
                        if(lFld.equals("cts"))
                            calCts=lVal;
                        
                        if(lFld.equals("qty"))
                            calQty=lVal;
                        String        lDsc = dao.getDSP_TTL();
                        // util.nvl((String)formFields.get(lFld+"REQ"));

                        if ((lReqd.equalsIgnoreCase("Y")) && (lVal.length() == 0) ) {
                            String errorMsg = lIdn + "-" + lDsc + " Required.";

                            errors.add(errorMsg);
                            params.clear();

                        } else {
                            String dfltParam = " ? ";
                            String lTblFld = dao.getTBL_FLD().toLowerCase();    // util.nvl((String)formFields.get(lFld+"TF"));
                            if (dao.getFLD_TYP().equals("DT")) {
                                if(lVal.length() == 10) {
                                    //lVal = 
                                    dfltParam = " to_date(?,'dd-mm-rrrr')";
                                }    
                                else
                                    lVal = "";
                            }    
                            
                             updQ += ", " + lTblFld + " = " + dfltParam;
                            params.add(lVal);
                            
                            if(lReqd.equalsIgnoreCase("Y"))
                            isValid = true;
                        }
                    }
                }
                int ct=0;
                if (params.size() > 0 && errors.size()==0) {
                    params.add(Integer.toString(lIdn));

                    ct = db.execUpd(" upd menu " + lIdn, updQ + condQ, params);
                    
                    if(cnt.equalsIgnoreCase("RN")){
                    boolean numerisStt = false;
                    if(!calCts.equals("") && !calQty.equals("")){
                        double calDiv = Double.parseDouble(calCts)/Double.parseDouble(calQty);
                        HashMap prp = info.getPrp();
                        ArrayList valList = (ArrayList)prp.get("MIX_SIZEV");
                        if(valList!=null && valList.size()>0){
                            for(int l=0;l<valList.size();l++){
                                String mixSizeVal =(String)valList.get(l);
                                    String[] split_mixSizeVal = mixSizeVal.split("-");
                                if(split_mixSizeVal!=null && split_mixSizeVal.length >0){
                                  numerisStt= util.isNumeric(split_mixSizeVal[0]);
                                  
                                if(numerisStt){
                                    double minVal = Double.parseDouble(split_mixSizeVal[0]);
                                    double maxval =minVal; 
                                    if(split_mixSizeVal.length==2){
                                    maxval = Double.parseDouble(split_mixSizeVal[1]);
                                    }else{
                                            minVal=0.0;
                                        }
                                    if( calDiv >= minVal && calDiv <= maxval){
                                        params=new ArrayList();
                                        params.add(purIdn);
                                        params.add(Integer.toString(lIdn));
                                        params.add("MIX_SIZE");
                                        params.add(mixSizeVal);
                                         ct = db.execCall("PKT PRP update", "PUR_PKG.PKT_PRP_UPD(pPurIdn => ?, pPurDtlIdn => ?, pPRP => ?, pVal => ?)", params);
                                     break;
                                        
                                    }
                                    
                                }
                                }
                            }
                            }
                      
                        }
                    }
                  
                }
                if(ct>0){
                    
                if(vwPrpLst!=null && vwPrpLst.size()>0 ){
                for (int k= 0; k < vwPrpLst.size(); k++) {
                    String        lprp  = (String)vwPrpLst.get(k);
                    String        lfldNm = lprp + "_" + lIdn;
                    String        llVal  = util.nvl((String) udf.getValue(lfldNm));
                   
                    params=new ArrayList();
                    params.add(purIdn);
                    params.add(Integer.toString(lIdn));
                    params.add(lprp);
                    params.add(llVal);
                     ct = db.execCall("cp update", "PUR_PKG.PKT_PRP_UPD(pPurIdn => ?, pPurDtlIdn => ?, pPRP => ?, pVal => ?)", params);

                    
                } 
                }}
              
                if(isValid){
                    errorList.add(errors);
                    errors = new ArrayList();
                }
                
            }
            rs.close();
            pst.close();
        }
        
        if(udf.getTfrToMkt()!=null){
            String srchFields = helper.getSrchFields(daos, "pur_dtl_idn", false);
            String srchQ      = " and pur_idn = ? ";

            // ArrayList list = helper.getSrchList(uiForms, "", srchFields, srchQ, params, daos, "VIEW");
            String getMenu    = "select " + srchFields + " from pur_dtl where 1 = 1 " + srchQ + " order by 1";
            ArrayList params     = new ArrayList();

            params.add(purIdn);

          ArrayList rsList = db.execSqlLst("getUIFormFields for update", getMenu, params);
          PreparedStatement pst = (PreparedStatement)rsList.get(0);
          ResultSet rs = (ResultSet)rsList.get(1);
            String trfStkidn ="";
            while (rs.next()) {
                int    lIdn  = rs.getInt(1);
                params = new ArrayList();
                String isChecked = util.nvl((String) udf.getValue("upd_" + lIdn));
                if (isChecked.length() > 0) {
                    params.add(String.valueOf(lIdn));
                    ArrayList out = new ArrayList();
                    out.add("I");
                    CallableStatement cst = db.execCall("TrfToStk","PUR_PKG.TRF_TO_STK(pPurDtlIdn => ?,  pStkIdn => ?)", params ,out);
                    int stkidn = cst.getInt(2); 
                  cst.close();
                  cst=null;
                    trfStkidn=trfStkidn+","+String.valueOf(stkidn);
                 req.setAttribute("msg", "Tansfer To Stock Successfully...");
                }
            }
            rs.close();
            pst.close();
        }
        req.setAttribute("errors", errorList);
            util.updAccessLog(req,res,"Purchase Form", "save end");
        return load(am, af, req, res);
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
        rtnPg=initnormal(req,res,session,util);
        }else{
        rtnPg="connExists";   
        }
        }else
        rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
            util.updAccessLog(req,res,"Purchase Form", "delete start");
        PurchaseDtlForm udf = (PurchaseDtlForm) af;
        ArrayList ary = new ArrayList();
        String purDtlIdn = req.getParameter("purDtlIdn");
        String deleteCity = "delete pur_prp where pur_dtl_idn= ?";
        ary = new ArrayList();
        ary.add(purDtlIdn);
        int ct = db.execUpd("deleteCity", deleteCity, ary);
        deleteCity = "delete pur_dtl where pur_dtl_idn= ?";
        ary = new ArrayList();
        ary.add(purDtlIdn);
        ct = db.execUpd("deleteCity", deleteCity, ary);
        util.updAccessLog(req,res,"Purchase Form", "delete end");
        return load(am, af, req, res);
        }
        
    }
    public ActionForward loadPrp(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        rtnPg=initnormal(req,res,session,util);
        }else{
        rtnPg="connExists";   
        }
        }else
        rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
            util.updAccessLog(req,res,"Purchase Form", "loadprp start");
        PurchaseDtlForm udf = (PurchaseDtlForm) af;
            GenericInterface genericInt = new GenericImpl();
            genericInt.genericPrprVw(req, res, "purPrpLst", "PUR_PRP_UPD");
        String purIdn = util.nvl(req.getParameter("purIdn"));
        String purDtlIdn = util.nvl(req.getParameter("purDtlIdn"));
        if(purIdn.equals("")){
            purIdn = util.nvl((String)udf.getValue("purIdn"));
            purDtlIdn =util.nvl((String)udf.getValue("purDtlIdn"));
        }
        String sql = " select a.mprp , decode(b.dta_typ, 'C', a.val, 'N', to_char(a.num), 'D', to_char(a.dte, 'dd-Mon-rrrr'), a.txt)  val "+ 
                     " from pur_prp a , mprp b , rep_prp c "+
                     " where pur_idn = ? and pur_dtl_idn=?  and a.mprp = b.prp  and b.prp = c.prp and c.prp = a.mprp and c.mdl='PUR_PRP_UPD' "+ 
                     " and c.flg='Y' order by rnk ";

        ArrayList ary = new ArrayList();
        ary.add(purIdn);
        ary.add(purDtlIdn);
          ArrayList rsList = db.execSqlLst("sql", sql, ary);
          PreparedStatement pst = (PreparedStatement)rsList.get(0);
          ResultSet rs = (ResultSet)rsList.get(1);
        while(rs.next()){
            udf.setValue(util.nvl(rs.getString("mprp")), util.nvl(rs.getString("val")));
        }
        rs.close();    
        pst.close();
        udf.setValue("purIdn", purIdn);
        udf.setValue("purDtlIdn", purDtlIdn);
            util.updAccessLog(req,res,"Purchase Form", "loadprp end");
        return am.findForward("loadPrp");
        }
    }
    
    public ActionForward addPrp(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        rtnPg=initnormal(req,res,session,util);
        }else{
        rtnPg="connExists";   
        }
        }else
        rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
            util.updAccessLog(req,res,"Purchase Form", "addprp start");
        PurchaseDtlForm udf = (PurchaseDtlForm) af;
        ArrayList purPrpList = (ArrayList)session.getAttribute("purPrpLst");
        String purIdn = util.nvl((String)udf.getValue("purIdn"));
        String purDtlIdn =util.nvl((String)udf.getValue("purDtlIdn"));
        ArrayList ary = new ArrayList();
            int ct=0;
        for(int i=0; i < purPrpList.size() ; i++){
            String lprp = (String)purPrpList.get(i);
            String prpVal = util.nvl((String)udf.getValue(lprp));
            if(!prpVal.equals("")){
                ary = new ArrayList();
                ary.add(purIdn);
                ary.add(purDtlIdn);
                ary.add(lprp);
                ary.add(prpVal);
               ct = db.execCall("updatePrp","PUR_PKG.PKT_PRP_UPD(pPurIdn => ?, pPurDtlIdn => ?, pPRP => ?, pVal => ?)", ary);
            if(ct>0)
                req.setAttribute("msg", "Propeties Get update successfully...");
            }
        }
            ary = new ArrayList();
            ary.add(purIdn);
            ary.add(purDtlIdn);
            ct = db.execCall("updateRap","PUR_PKG.CALC_RAP(pPurIdn => ?, p_dtl_idn => ?)", ary);
            util.updAccessLog(req,res,"Purchase Form", "addprp end");
        return loadPrp(am, af, req, res);
        }
    }
    
    public ActionForward loadbulkPrp(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Pur Bulk Update", "loadbulkPrp Start");
        PurchaseDtlForm udf = (PurchaseDtlForm) af;
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("PUR_BULK_PRP_UPD");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("PUR_BULK_PRP_UPD");
        allPageDtl.put("PUR_BULK_PRP_UPD",pageDtl);
        }
        info.setPageDetails(allPageDtl);
            util.updAccessLog(req,res,"Pur Bulk Update", "loadbulkPrp end");
        return am.findForward("loadbulkPrp");
        }
    }
    
    public ActionForward updatePrp(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        PurchaseDtlForm udf = (PurchaseDtlForm) af;
        GenericInterface genericInt = new GenericImpl();
        util.updAccessLog(req,res,"Pur Bulk Update", "updatePrp start");
        int ct = db.execCall("delete gt", "delete from Gt_Gia_File_Trf", new ArrayList());
        ct = db.execCall("delete gt_pkt_scan", "delete from gt_pkt_scan", new ArrayList());
        ArrayList ary = new ArrayList();
        String lprp = util.nvl((String)udf.getValue("lprp"));
        String vnm = util.nvl((String)udf.getValue("vnmLst"));
        String val = util.nvl((String)udf.getValue("prpVal"));
        String deletepkt = util.nvl((String)udf.getValue("deletepkt"));
        HashMap dbinfo = info.getDmbsInfoLst();
        int cnt = 0;
        int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
        ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "ascallSru", "CALL_SRU_VW");
        HashMap mprp = info.getMprp();
        if(deletepkt.equals("")){
        String msg = "";
        ArrayList RtnMsg = new ArrayList();
        ArrayList RtnMsgList = new ArrayList();
        String prpTyp = util.nvl((String)mprp.get(lprp+"T"));
        if(!vnm.equals("") && !val.equals("") ){
        vnm = util.getVnm(vnm);
        if(prpTyp.equals("T"))
        val = util.getValNline(val);
        else
        val = util.getVal(val);
    
       ArrayList vnmList = new ArrayList();
       vnm = vnm.substring(1, vnm.length()-1);
       String[] vnmStr = vnm.split("','");
      
       
        ArrayList valList = new ArrayList();
        val = val.substring(1, val.length()-1);
        String[] valStr =null;
        if(prpTyp.equals("T"))
         valStr = val.split("'#'");
        else
         valStr = val.split("','");
        if(vnmStr.length==valStr.length){
        
            for(int i=0;i<vnmStr.length;i++){
                vnm = vnmStr[i];
               vnm = vnm.replaceAll(",", "");
               vnm = vnm.replaceAll("'", "");
              
             vnmList.add(vnm);
            }
        
            for(int i=0;i<valStr.length;i++){
                val = valStr[i];
                if(!prpTyp.equals("T")){
              val=val.replaceAll(",", "");
              val=val.replaceAll("'", "");
                }
              val = val.trim();
               valList.add(val);
            }
        
        for(int k=0 ;k<vnmList.size();k++){
          String insertGt = "Insert Into Gt_Gia_File_Trf(Cert_No,Lab,Mprp,Val)"+
              "Select ?,'GIA',?,? From Dual";
                   ary = new ArrayList();
                   ary.add(vnmList.get(k));
                   ary.add(lprp);
                   ary.add(valList.get(k));
                   ct = db.execDirUpd("insert Gt_Gia_File_Trf", insertGt, ary);
        }
        if(ct>0){
            ary = new ArrayList();
            ary.add("PUR");
            ArrayList out = new ArrayList();
            out.add("I");
            out.add("V");
            CallableStatement cst = null;
            cst = db.execCall(
                "PUR_PKG ",
                "PUR_PKG.BLK_UPD(pTyp => ?, pCnt => ?, pMsg => ?)", ary, out);
            cnt = cst.getInt(ary.size()+1);
            msg = cst.getString(ary.size()+2);
          cst.close();
          cst=null;
            RtnMsg.add(cnt);
            RtnMsg.add(msg);
            RtnMsgList.add(RtnMsg); 
            req.setAttribute("msgList",RtnMsgList);
            udf.reset();
        }else{
            req.setAttribute("msg","Update Process failed..");
        }
        }else{
            req.setAttribute("msg","Please check Packets and there corresponding  Values. ");
        }
        }else{
          req.setAttribute("msg","Please Specify Packets. ");
        }
        }else{
            ary=new ArrayList();
            vnm = util.getVnm(vnm);
            vnm = vnm.replaceAll("'", "");
            if(!vnm.equals("")){
            vnm="'"+vnm+"'";
            String insScanPkt = " insert into gt_pkt_scan select * from TABLE(PARSE_TO_TBL("+vnm+"))";
            ct = db.execDirUpd(" ins scan", insScanPkt,ary);
            System.out.println(insScanPkt);
            }
            ary=new ArrayList();
            ArrayList out = new ArrayList();
            out.add("V");
            CallableStatement cst = db.execCall("purTrf","pur_pkg.DEL_UPL_TRNS_PKT(pMsg => ?)", ary, out);
            String msg = cst.getString(ary.size()+1);
            req.setAttribute("msg", msg);
          cst.close();
          cst=null;
            
        }
            util.updAccessLog(req,res,"Pur Bulk Update", "updatePrp end");
        return am.findForward("loadbulkPrp");
        }
    } 
    public ActionForward verifyPurchaseVnm(ActionMapping mapping, ActionForm form,
                               HttpServletRequest req,
                               HttpServletResponse response) throws Exception {
           HttpSession session = req.getSession(false);
           InfoMgr info = (InfoMgr)session.getAttribute("info");
           DBUtil util = new DBUtil();
           DBMgr db = new DBMgr(); 
           db.setCon(info.getCon());
           util.setDb(db);
           util.setInfo(info);
           db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
           util.setLogApplNm(info.getLoApplNm());
       StringBuffer sb = new StringBuffer();
       String vnm = util.nvl(req.getParameter("vnm")).toUpperCase();
       ArrayList ary = new ArrayList();
       boolean exists=false;
       vnm = util.getVnm(vnm);
       String sql = " select ref_idn from pur_dtl where ref_idn in ("+vnm+")" ;
         ArrayList rsList = db.execSqlLst("Verify VNM", sql, ary);
         PreparedStatement pst = (PreparedStatement)rsList.get(0);
         ResultSet rs = (ResultSet)rsList.get(1);
       if(rs.next()){
               exists=true;
       }
       rs.close();
       pst.close();
       if(!exists){
               sql = " select vnm from mstk where vnm in ("+vnm+")" ; ;
                      rs = db.execSql("Verify VNM", sql, ary);
          rsList = db.execSqlLst("Verify VNM", sql, ary);
          pst = (PreparedStatement)rsList.get(0);
          rs = (ResultSet)rsList.get(1);
                      if(rs.next()){
                              exists=true;
                      }
              rs.close();
              pst.close();
       }
       if(exists){
         response.setContentType("text/xml"); 
         response.setHeader("Cache-Control", "no-cache"); 
         response.getWriter().write("<message>yes</message>");
       }
       else{
         response.setContentType("text/xml"); 
         response.setHeader("Cache-Control", "no-cache"); 
         response.getWriter().write("<message>No</message>");
       }

       rs.close();
       return null;
       }
    
    
    
    public ActionForward mpurDtl(ActionMapping mapping, ActionForm form,
                               HttpServletRequest req,
                               HttpServletResponse response) throws Exception {
           HttpSession session = req.getSession(false);
           InfoMgr info = (InfoMgr)session.getAttribute("info");
           DBUtil util = new DBUtil();
           DBMgr db = new DBMgr(); 
           db.setCon(info.getCon());
           util.setDb(db);
           util.setInfo(info);
           db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
           util.setLogApplNm(info.getLoApplNm());
           StringBuffer sb = new StringBuffer();
           String purIdn = util.nvl(req.getParameter("purIdn"));
           String purRte = util.nvl(req.getParameter("purRte"),"0");
           String purDtlIdn = util.nvl(req.getParameter("purDtlIdn"),"0");
           if(!purRte.equals("")){
              ArrayList ary = new ArrayList();
               ary.add(purIdn);
               ary.add(purDtlIdn);
               ary.add("NET_PUR_RTE");
                ary.add(purRte);
             int ct = db.execCall("updatePrp","PUR_PKG.PKT_PRP_UPD(pPurIdn => ?, pPurDtlIdn => ?, pPRP => ?, pVal => ?)", ary);
                    
               ary = new ArrayList();
               ary.add(purIdn);
               ary.add(purDtlIdn);
               ary.add("PUR_RTE");
               ary.add(purRte);
             ct = db.execCall("updatePrp","PUR_PKG.PKT_PRP_UPD(pPurIdn => ?, pPurDtlIdn => ?, pPRP => ?, pVal => ?)", ary);
             if(ct>0){
                 
                 String sql = " select rte from pur_dtl where pur_dtl_idn = ? " ;
                 ary = new ArrayList();
                 ary.add(purDtlIdn);
                 ArrayList rsLst = db.execSqlLst("sql", sql, ary);
                 PreparedStatement pst = (PreparedStatement)rsLst.get(0);
                 ResultSet rs = (ResultSet)rsLst.get(1);
                 if(rs.next()){
                     purRte= rs.getString("rte") ; 
                 }
                 rs.close();
                 pst.close();
             }
               
           }
          
           response.setContentType("text/xml"); 
           response.setHeader("Cache-Control", "no-cache"); 
           response.getWriter().write("<RTE>"+purRte+"</RTE>");
       return null;
       }
    
    
    
   
    
    public ActionForward mpurforNewDtl(ActionMapping mapping, ActionForm form,
                               HttpServletRequest req,
                               HttpServletResponse response) throws Exception {
           HttpSession session = req.getSession(false);
           InfoMgr info = (InfoMgr)session.getAttribute("info");
           DBUtil util = new DBUtil();
           DBMgr db = new DBMgr(); 
           db.setCon(info.getCon());
           util.setDb(db);
           util.setInfo(info);
           db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
           util.setLogApplNm(info.getLoApplNm());
           StringBuffer sb = new StringBuffer();
           String purIdn = util.nvl(req.getParameter("purIdn"));
           String purRte = util.nvl(req.getParameter("purRte"),"0");
           double calpurRte =0.0;
           if(!purRte.equals("")){
               boolean isExit = false;
               ArrayList ary = new ArrayList();
               String venderQ = "select decode(cur,'INR',p.EXH_RTE,1) exh_rte, nvl(pct,0) lTrms, nvl(addl_dis, 0) lDis,nvl(addl_dis1, 0) lDis1, nvl(aadat_comm, 0) lComm \n" + 
               "from mtrms t, mpur p where t.idn = p.trms_idn and p.pur_idn =?";
               ary.add(purIdn);
              calpurRte = Double.parseDouble(purRte);
               double lTrms = 0.0;
               double lDis = 0.0;
               double lDis1 = 0.0;
               double lComm = 0.0;
               double lxrte=0.0;
               String slMrgn = util.nvl((String)info.getDmbsInfoLst().get("PUR_MRGN"),"3");
               double lMrgn = Double.parseDouble(slMrgn);
             ArrayList rsList = db.execSqlLst("empSql", venderQ, ary);
             PreparedStatement pst = (PreparedStatement)rsList.get(0);
             ResultSet rs = (ResultSet)rsList.get(1);
               try {
                   while (rs.next()) {
                        lTrms = rs.getDouble("lTrms");
                       lDis = rs.getDouble("lDis");
                       lDis1 = rs.getDouble("lDis1");
                       lComm = rs.getDouble("lComm");
                       lxrte = rs.getDouble("exh_rte");
                       isExit=true;
                   }
                   rs.close();
                   pst.close();
                   if(!isExit){
                       venderQ = "select decode(cur,'INR',p.EXH_RTE,1) exh_rte,  nvl(addl_dis, 0) lDis,nvl(addl_dis1, 0) lDis1, nvl(aadat_comm, 0) lComm \n" + 
                       "from  mpur p where p.pur_idn =?";
                       ary = new ArrayList();
                       ary.add(purIdn);
                     rsList = db.execSqlLst("empSql", venderQ, ary);
                      pst = (PreparedStatement)rsList.get(0);
                      rs = (ResultSet)rsList.get(1);
                       while (rs.next()) {
                           
                           lDis = rs.getDouble("lDis");
                           lDis1 = rs.getDouble("lDis1");
                           lComm = rs.getDouble("lComm");
                           lxrte = rs.getDouble("exh_rte");
                       }
                       rs.close();
                       pst.close();
                       
                   }
               } catch (SQLException sqle) {
                   // TODO: Add catch code
                   sqle.printStackTrace();
               }
               calpurRte=calpurRte/lxrte;
               calpurRte=calpurRte*((100 - lDis)/100) ;
               calpurRte=calpurRte*((100 - lDis1)/100) ;
               calpurRte=calpurRte*((100 - lTrms)/100) ;
                calpurRte=calpurRte*((100 - lComm)/100) ;
                 calpurRte=calpurRte/((100 - lMrgn)/100) ;
           
               
           }
           purRte = String.valueOf(util.roundToDecimals((calpurRte),2));
           response.setContentType("text/xml"); 
           response.setHeader("Cache-Control", "no-cache"); 
           response.getWriter().write("<RTE>"+purRte+"</RTE>");
       return null;
       }
    
    public ActionForward calculatemixValue(ActionMapping mapping, ActionForm form,
                               HttpServletRequest req,
                               HttpServletResponse response) throws Exception {
           HttpSession session = req.getSession(false);
           InfoMgr info = (InfoMgr)session.getAttribute("info");
           DBUtil util = new DBUtil();
           DBMgr db = new DBMgr(); 
           db.setCon(info.getCon());
           util.setDb(db);
           util.setInfo(info);
           db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
           util.setLogApplNm(info.getLoApplNm());
           StringBuffer sb = new StringBuffer();
           String purIdn = util.nvl(req.getParameter("purIdn"));
           String purRte = util.nvl(req.getParameter("purRte"),"0");
           double calpurRte =0.0;
           if(!purRte.equals("")){
               ArrayList ary = new ArrayList();
               String venderQ = "select nvl(pct,0) lTrms, nvl(addl_dis, 0) lDis,nvl(addl_dis1, 0) lDis1, nvl(aadat_comm, 0) lComm \n" + 
               "from mtrms t, mpur p where t.idn = p.trms_idn and p.pur_idn =?";
               ary.add(purIdn);
                calpurRte = Double.parseDouble(purRte);
               double lTrms = 0.0;
               double lDis = 0.0;
               double lDis1 = 0.0;
               double lComm = 0.0;
               String slMrgn = util.nvl((String)info.getDmbsInfoLst().get("A DFULT_PCT"),"3");
               double lMrgn = Double.parseDouble(slMrgn);
             ArrayList rsList = db.execSqlLst("empSql", venderQ, ary);
             PreparedStatement pst = (PreparedStatement)rsList.get(0);
             ResultSet rs = (ResultSet)rsList.get(1);
               try {
                   while (rs.next()) {
                        lTrms = rs.getDouble("lTrms");
                       lDis = rs.getDouble("lDis");
                       lDis1 = rs.getDouble("lDis1");
                       lComm = rs.getDouble("lComm");
                   }
                   rs.close();
                   pst.close();
               } catch (SQLException sqle) {
                   // TODO: Add catch code
                   sqle.printStackTrace();
               }
               
               calpurRte=calpurRte*((100 - lDis)/100) ;
               calpurRte=calpurRte*((100 - lDis1)/100) ;
               calpurRte=calpurRte*((100 - lTrms)/100) ;
                calpurRte=calpurRte*((100 - lComm)/100) ;
                  calpurRte=calpurRte/((100 - lMrgn)/100) ;
           
               
           }
           purRte = String.valueOf(util.roundToDecimals((calpurRte),2));
           response.setContentType("text/xml"); 
           response.setHeader("Cache-Control", "no-cache"); 
           response.getWriter().write("<RTE>"+purRte+"</RTE>");
       return null;
       }
    
}
