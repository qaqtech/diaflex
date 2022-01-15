package ft.com.pri;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtilObj;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import ft.com.dao.ObjBean;

import java.io.OutputStream;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.Enumeration;
import java.util.HashMap;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class PriceHistoryAction extends DispatchAction {
    public PriceHistoryAction() {
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
          PriceHistoryForm  priceHistory = (PriceHistoryForm)af;
           priceHistory.resetALL();
          ArrayList grpList = new ArrayList();
          String grpNme="select nme from pri_grp where stt='A' order by srt";
          ArrayList outLst = db.execSqlLst("prp GrpNme", grpNme, new ArrayList());
         PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
          while(rs.next()){
              String nme = rs.getString("nme");
              ObjBean obj = new ObjBean();
              obj.setNme(nme);
              obj.setDsc(nme);
              grpList.add(obj);
          }
          priceHistory.setGrpList(grpList);
          rs.close();
          pst.close();
          return am.findForward("load");
      }
     }
    
    public ActionForward Fetch(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        PriceHistoryForm  udf = (PriceHistoryForm)af;
        Random    randomGenerator = new Random();
        HashMap   mprp    = null,
                    prp     = null;
        mprp   = info.getMprp();
        prp    = info.getPrp();
        String grpNme = util.nvl((String)udf.getValue("grpNme"));
          String    delDtlQ         = " delete from gt_pri_srch_dtl";
          String    delQ            = " delete from gt_pri_srch";

          db.execUpd("del gt dtl", delDtlQ, new ArrayList());
          db.execUpd("del gt dtl", delQ, new ArrayList());

          int srchId = 0;

          srchId = randomGenerator.nextInt(100);

          // srchId = randomGenerator().
              String insGTSrchQ = " insert into gt_pri_srch(srch_id, mdl) values(?, 'PRC') ";

          ArrayList params = new ArrayList();
          params.add(Integer.toString(srchId));

          int    ct  = db.execUpd(" GT Srch", insGTSrchQ, params);
          int matIdn    = 0,
              matSrt    = 0,
              prmDisIdn = 0;
              ArrayList matIdnList = new ArrayList();
          
              String matNme = "";

                  if(srchId > 0) {
                      int cnt=0;
                      ArrayList gncPrpLst = info.getGncPrpLst();
                      ArrayList prplist=null;
                      String addSrchDtl = "insert into gt_pri_srch_dtl(srch_id, mprp, vfr, vto) values(?,?,?,?)";
                      String addSrchDtlVal = "insert into gt_pri_srch_dtl(srch_id, mprp, vfr, vto) values(?,?,?,?)";
                      String addSrchDtlSubVal = "insert into gt_pri_srch_dtl_sub(srch_id, mprp, vfr, vto) values(?,?,?,?)";
                        String insSrchDtlQNum =
                            " insert into gt_prc_srch_dtl(srch_id, mprp, vfr, vto, nfr, nto) values(?,?,?,?,?,?) ";
                        
                      for (int i = 0; i < gncPrpLst.size(); i++) {
                      boolean dtlAddedOnce = false ;
                      prplist =(ArrayList)gncPrpLst.get(i);
                      String lprp = (String)prplist.get(0);
                      String flg= (String)prplist.get(1);
                      String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
                      String prpSrt = lprp ;
                      String reqVal1="";
                      String reqVal2="";
                      if(lprpTyp.equals("C")){
                          ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
                          ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
                          for(int j=0; j < lprpS.size(); j++) {
                          String lSrt = (String)lprpS.get(j);
                          String lVal = (String)lprpV.get(j);
                          reqVal1 = util.nvl((String)udf.getValue(lprp + "_" + lVal),"");
                          reqVal2 = util.nvl((String)udf.getValue(lprp + "_" + lVal),"");
                          if((reqVal1.length() != 0) || (reqVal2.length() != 0)) {
                          //ignore no value selected;
                          
                          if(!dtlAddedOnce) {
                          params = new ArrayList();
                          params.add(String.valueOf(srchId));
                          params.add(lprp);
                          params.add(reqVal1);
                          params.add(reqVal2);
                          ct = db.execDirUpd(" SrchDtl ", addSrchDtlVal, params);    
                          dtlAddedOnce = true;
                          }
                          params = new ArrayList();
                          params.add(String.valueOf(srchId));
                          params.add(lprp);
                          params.add(reqVal1);
                          params.add(reqVal2);
                          ct = db.execDirUpd(" SrchDtl ", addSrchDtlSubVal, params);    
                          }
                          }
                      
                         
                        }else{
                        reqVal1 = util.nvl((String)udf.getValue(lprp + "_1"),"");
                        reqVal2 = util.nvl((String)udf.getValue(lprp + "_2"),"");
                        if((reqVal1.length() != 0) || (reqVal2.length() != 0)) {
                          String insSrchDtlQ =
                              " insert into gt_pri_srch_dtl(srch_id, mprp, vfr, vto, nfr, nto) values(?,?,?,?,?,?) ";
                          params = new ArrayList();
                          params.add(Integer.toString(srchId));
                          params.add(lprp);
                          String numFr = "",
                                 numTo = "";
                          if (lprpTyp.equals("N")) {
                              numFr = reqVal1;
                              numTo = reqVal2;
                              reqVal1 = "NA";
                              reqVal2 = "NA";
                          }
                          if (lprpTyp.equals("T")) {
                              reqVal2=reqVal1;
                          }
                          params.add(reqVal1);
                          params.add(reqVal2);
                          params.add(numFr);
                          params.add(numTo);
                          ct = db.execDirUpd(" SrchDtl ", insSrchDtlQ, params);  
                        }
                      }
                      }
                      }
          String dfr = util.nvl((String)udf.getValue("dteFM"));
          String dto = util.nvl((String)udf.getValue("dteTO"));
          String dtefrom="";
          String dteto="";
          String dteQ="";
          if(!dfr.equals("") && !dto.equals("")){
            dtefrom = "to_date('"+dfr+"' , 'dd-mm-yyyy')";
          
          if(!dto.equals(""))
            dteto = "to_date('"+dto+"' , 'dd-mm-yyyy')";
          
           dteQ=" and trunc(p1.dte)  between "+dtefrom+" and "+dteto;  
          }

              String srchQ =
                 "With \n" + 
                 "P1 as (\n" + 
                 "select a.prmnme nme,a.dte, a.idn idn, a.pri_grp, a.grp_srt, count(*)\n" + 
                 "  from mpri_hdr a, pri_cmn a1\n" + 
                 "  , gt_pri_srch b,\n" + 
                 "  ( select * from gt_pri_srch_dtl c\n" + 
                 " where c.srch_id = ? and c.flg = 'R'\n" + 
                 "  and not exists (select 1 from gt_pri_srch_dtl_sub c1 where c.srch_id = c1.srch_id and c.mprp = c1.mprp)\n" + 
                 " union\n" + 
                 " select * from\n" + 
                 " gt_pri_srch_dtl_sub d\n" + 
                 " where d.srch_id = ? \n" + 
                 " ) e   where 1 = 1 \n" + 
                 "    and a.pri_grp= ? \n" + 
                 "    and b.srch_id= ? and e.srch_id = b.srch_id \n" + 
                 "    and a.idn = a1.hdr_idn \n" + 
                 "    and a1.mprp = e.mprp \n" + 
                 "    and e.sfr between a1.srt_fr and a1.srt_to\n" + 
                 "  having count(distinct a1.mprp) = b.dtl_cnt\n" + 
                 "  group by b.dtl_cnt,a.dte, a.idn, a.pri_grp, a.prmnme, a.grp_srt\n" + 
                 "), \n" + 
                 "P2 as (\n" + 
                 "select a.prmnme nme,a.dte, a.idn idn, a.pri_grp, a.grp_srt, count(*)\n" + 
                 "  from mpri_hdr a, pri_cmn a1\n" + 
                 "  , gt_pri_srch b,\n" + 
                 "  ( select * from gt_pri_srch_dtl c\n" + 
                 " where c.srch_id = ? and c.flg = 'R'\n" + 
                 "  and not exists (select 1 from gt_pri_srch_dtl_sub c1 where c.srch_id = c1.srch_id and c.mprp = c1.mprp)\n" + 
                 " union\n" + 
                 " select * from\n" + 
                 " gt_pri_srch_dtl_sub d\n" + 
                 " where d.srch_id = ?\n" + 
                 " ) e   where 1 = 1 \n" + 
                 "    and a.pri_grp= ? \n" + 
                 "    and b.srch_id= ? and e.srch_id = b.srch_id \n" + 
                 "    and a.idn = a1.hdr_idn \n" + 
                 "    and a1.mprp = e.mprp \n" + 
                 "    and e.sto between a1.srt_fr and a1.srt_to\n" + 
                   "  group by b.dtl_cnt,a.dte, a.idn, a.pri_grp, a.prmnme, a.grp_srt\n" + 
                 "  having count(distinct a1.mprp) = b.dtl_cnt\n" + 
                 ")\n" + 
                 "select p1.nme,p1.dte, p1.idn idn, p1.pri_grp, p1.grp_srt\n" + 
                 "from p1, p2\n" + 
                 "where p1.idn = p2.idn\n " +dteQ+
                 " Order By p1.Pri_Grp, p1.Idn desc,p1.dte , p1.grp_srt ";
              
              params = new ArrayList();
              params.add(Integer.toString(srchId));
              params.add(Integer.toString(srchId));
              params.add(grpNme);
              params.add(Integer.toString(srchId));
              params.add(Integer.toString(srchId));
              params.add(Integer.toString(srchId));
              params.add(grpNme);
              params.add(Integer.toString(srchId));
              util.SOP(params.toString());
            ArrayList rsLst = db.execSqlLst("grpNme", srchQ, params);
            PreparedStatement stmt =(PreparedStatement)rsLst.get(0);
            ResultSet rs =(ResultSet)rsLst.get(1);
              util.SOP(srchQ);
              matIdn = 0;
              matNme = "";
             HashMap hdrDtlMap = new HashMap();
             while(rs.next()) {
                String idn = rs.getString("idn");
                 String nme = rs.getString("nme");
                String dte = rs.getString("dte");
                 hdrDtlMap.put(idn, nme);
                hdrDtlMap.put(idn+"_DTE", dte);
                 matIdnList.add(idn);
            }
               rs.close();
              stmt.close();
          if(matIdnList.size() > 0) {
          String pIdn="";
          String idnPcs = matIdnList.toString();
          idnPcs = idnPcs.replace('[','(');
          idnPcs = idnPcs.replace(']',')');    
          String grpNmeSql = "select a.idn,a.PRMTYP, a.prmnme, to_char( a.dte,'dd-mm-yyyy HH24:mi:ss') dte , b.mprp, decode(c.dta_typ, 'C', b.val_fr, to_char(b.num_fr)) val_fr, " +
          " decode(c.dta_typ, 'C', b.val_to, to_char(b.num_to)) val_to "+
           " from mpri_hdr a, pri_cmn b, mprp c, pri_grp_prp d "+
           " where a.idn = b.hdr_idn and b.mprp = c.prp "+
          " and b.mprp = d.mprp and a.pri_grp = d.nme and d.typ = 'CMN' "+
           " and a.pri_grp = ? and a.idn in "+idnPcs+
          " order by grp_srt, a.idn, d.srt ";
          ArrayList ary = new ArrayList();
          ary.add(grpNme);
         rsLst = db.execSqlLst("grpNme", grpNmeSql, ary);
          stmt =(PreparedStatement)rsLst.get(0);
          rs =(ResultSet)rsLst.get(1);
          ArrayList grpDtlList = new ArrayList();
            while(rs.next()){
              String idn = rs.getString("idn");
              if(pIdn.equals(""))
                  pIdn=idn;
              String prmNme = rs.getString("prmnme");
              String mprpf = rs.getString("mprp");
              String val_fr = rs.getString("val_fr");
              String val_to = rs.getString("val_to");
              String dte = rs.getString("dte");
              HashMap grpDtl = new HashMap();
              grpDtl.put("idn" , idn);
              grpDtl.put("nme",prmNme);
              grpDtl.put("mprp",mprpf);
              grpDtl.put("valFr",val_fr);
              grpDtl.put("valTo",val_to);
              grpDtl.put("dte",dte);
              grpDtl.put("PRMTYP", rs.getString("PRMTYP"));
              grpDtlList.add(grpDtl);
              
          }
          rs.close();
          stmt.close();
          session.setAttribute("HDRDTLMAP", hdrDtlMap);
              req.setAttribute("grpDtlList", grpDtlList);
              req.setAttribute("grpNme", grpNme);
              udf.setValue("grpNme", grpNme);
          }
         
          return am.findForward("load");
      }
    }
    public ActionForward priceHis(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        PriceHistoryForm  udf = (PriceHistoryForm)af;
          Enumeration reqNme = req.getParameterNames();
          ArrayList mpriIdnLst = new ArrayList();
          while(reqNme.hasMoreElements()) {
             String paramNm = (String)reqNme.nextElement();
             if(paramNm.indexOf("cb") > -1) {
                 String val = req.getParameter(paramNm);
                
                 String pktNm = val ;//paramNm.substring(paramNm.indexOf("rb_")+3);
                 if(paramNm.equals("cb_mat_"+val)){
                     mpriIdnLst.add(pktNm);
                    
                 }
          }}
          
          if(mpriIdnLst.size()>0){
              HashMap HDRDTLMAP= (HashMap)session.getAttribute("HDRDTLMAP");
              String mpriIdn =  mpriIdnLst.toString();
              mpriIdn = mpriIdn.replaceAll("\\[","");
              mpriIdn = mpriIdn.replaceAll("\\]","");
              String sql="select c.srt, c.idn, d.typ, b.mprp ,  \n" + 
                            "        decode(d.dsp_flg, 'I', decode(a.dta_typ, 'C', val_fr, trim(to_char(num_fr, a.fmt))), \n" + 
                            "        decode(a.dta_typ, 'C', val_fr, trim(to_char(num_fr,a.fmt)))\n" + 
                            "        ||'~'|| \n" + 
                            "        decode(a.dta_typ, 'C', val_to, trim(to_char(num_to, a.fmt)))) val \n" + 
                            "        , nvl(c.vlu , c.pct) fldval  \n" + 
                            "        from mprp a, pri_dtl b, mpri c, pri_grp_prp d where a.prp = b.mprp and b.mpri_idn = c.idn \n" + 
                            "        and c.pri_grp = d.nme and a.prp = d.mprp and c.srt in ("+mpriIdn+") and typ in ('ROW','COL') \n" + 
                            "        order by c.idn, d.typ asc, d.srt";
                           ArrayList  rsLst = db.execSqlLst("grpNme", sql, new ArrayList());
                         PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
                         ResultSet  rs =(ResultSet)rsLst.get(1);
                           String pIdn="";
                           String pFldVal="";
                           String pHdrIdn="";
                           HashMap gridDtlMap = new HashMap();
                           ArrayList colList = new ArrayList();
                           ArrayList rowList = new ArrayList();
                           HashMap gridDtl = new HashMap();
                           String key="";
                            while(rs.next()){
                                String idn = rs.getString("idn");
                                String fldVal = rs.getString("fldval");
                                String hdrIdn = rs.getString("srt");
                                if(pFldVal.equals(""))
                                    pFldVal=fldVal;
                                if(pIdn.equals(""))
                                    pIdn=idn;
                                if(pHdrIdn.equals(""))
                                    pHdrIdn=hdrIdn;
                                
                                if(!pIdn.equals(idn)){
                                    gridDtl.put(key, pFldVal);
                                    key="";
                                    pFldVal=fldVal;
                                    pIdn=idn;
                                }
                               
                                if(!pHdrIdn.equals(hdrIdn)){
                                    gridDtlMap.put(pHdrIdn, gridDtl);
                                    gridDtl=new HashMap();
                                    pHdrIdn=hdrIdn;
                                }
                               
                                String typ = rs.getString("typ");
                                String lprp = rs.getString("mprp");
                                String val = rs.getString("val");
                                gridDtlMap.put(typ, lprp);
                                if(typ.equals("COL")){
                                    if(!colList.contains(val))
                                        colList.add(val);
                                }
                                if(typ.equals("ROW")){
                                    if(!rowList.contains(val))
                                        rowList.add(val);
                                }
                                if(key.equals(""))
                                    key=val;
                               else
                                   key=key+"_"+val;
                            }
                            
                        if(!pHdrIdn.equals("")){
                                gridDtlMap.put(pHdrIdn, gridDtl);
                               
                               }
                              gridDtlMap.put("COLLIST", colList);
                              gridDtlMap.put("ROWLIST", rowList);
                              gridDtlMap.put("HDRIDNLIST", mpriIdnLst);
                              gridDtlMap.put("HDRDTLMAP", HDRDTLMAP);
                              
                              OutputStream out = res.getOutputStream();
                              String CONTENT_TYPE = "getServletContext()/vnd-excel";
                              String fileNm = "PriceHistory_"+util.getToDteTime()+".xls";
                              ExcelUtilObj excelUtil = new ExcelUtilObj();
                              excelUtil.init(db, util, info,new GtMgr());
                              HSSFWorkbook hwb = excelUtil.PriceHistoryExcel(req,gridDtlMap);
                              res.setContentType(CONTENT_TYPE);
                              res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
                              hwb.write(out);
                              out.flush();
                              out.close();
                            rs.close();
                            stmt.close();
          }
    
          return am.findForward("load");
      }
    
    }
    
    public ActionForward PriGroup(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
          PriceHistoryForm  udf = (PriceHistoryForm)af;
          String grpNme = req.getParameter("grpNme");
          try {
              String dfr = util.nvl((String)udf.getValue("dtefr"));
              String dto = util.nvl((String)udf.getValue("dteto"));
              ArrayList grpPrp = new ArrayList();
              String commonPrpSql="select a.mprp,'M' flg from pri_grp_prp a,mprp b where  a.mprp=b.prp and a.nme=? and a.typ='CMN' order by b.srt";
              ArrayList ary = new ArrayList();
              ary.add(grpNme);
              ArrayList rsLst = db.execSqlLst("coomonSql", commonPrpSql, ary);
              PreparedStatement stmt = (PreparedStatement)rsLst.get(0);
              ResultSet rs = (ResultSet)rsLst.get(1);
              while(rs.next()){
                  ArrayList asViewdtl=new ArrayList();
                  asViewdtl.add(rs.getString("mprp"));
                  asViewdtl.add(rs.getString("flg"));
                  grpPrp.add(asViewdtl);
                  
              }
              udf.setValue("grpNme", grpNme);
              udf.setValue("dteFM", dfr);
              udf.setValue("dteTO", dto);
              rs.close();
              stmt.close();
              info.setGncPrpLst(grpPrp); 
          } catch (SQLException sqle) {
              // TODO: Add catch code
              sqle.printStackTrace();
          }
         
         
          return am.findForward("priGrp");
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
            util.updAccessLog(req,res,"PriceGPPrpDefAction", "init");
            }
            }
            return rtnPg;
         }
}
