package ft.com.role;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.FormsUtil;
import ft.com.InfoMgr;
import ft.com.dao.DFMenu;
import ft.com.dao.Role;
import ft.com.dao.UIForms;

import ft.com.generic.GenericImpl;
import ft.com.marketing.SearchQuery;

import java.io.IOException;

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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class MenuRoleAction  extends  DispatchAction {

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
         util.updAccessLog(req,res,"Menu Role Deatail", "load start");
         RoleForm udf = (RoleForm)af;
         ArrayList roleList = new ArrayList();
         String roleDtl = "select df_role_idn , role_dsc from df_mrole where stt='A' and to_dte is null";

             ArrayList outLst = db.execSqlLst("roleDtl", roleDtl,new ArrayList());
             PreparedStatement pst = (PreparedStatement)outLst.get(0);
             ResultSet rs = (ResultSet)outLst.get(1);
         while(rs.next()){
             Role role = new Role();
             role.setRoleIdn(rs.getInt("df_role_idn"));
             role.setRole_dsc(rs.getString("role_dsc"));
             roleList.add(role);
         }
         rs.close(); pst.close();
        String meruRole = "select df_mr_idn , df_menu_idn , df_menu_itm_idn , df_role_idn from  df_menu_role where stt='A' and to_dte is null";

              outLst = db.execSqlLst("meruRole", meruRole, new ArrayList());
              pst = (PreparedStatement)outLst.get(0);
              rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            String mrIdn = rs.getString("df_mr_idn");
            String df_menu =  rs.getString("df_menu_idn");
            String df_menu_itm = nvl2(rs.getString("df_menu_itm_idn"));
            String df_role = rs.getString("df_role_idn");
            String fldNme = df_menu+"_"+df_menu_itm+"_"+df_role;
            udf.setValue(fldNme,fldNme);
        }
         rs.close(); pst.close();
        
        
         session.setAttribute("roleList", roleList);
         
         ArrayList menuHdr = new ArrayList();
         String mainMenu ="select df_menu_idn, img, dsp, lnk from df_menu where dsp_lvl = 1 " +
             " and nvl(grp_df_menu_idn,0) = 0 and stt='A' and to_dte is null order by srt ";


             outLst = db.execSqlLst("mainMenu",  mainMenu, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
         try {
             while (rs.next()) {
                 DFMenu menuDeo = new DFMenu() ;            
                 menuDeo.setDfMenuIdn(rs.getInt("df_menu_idn"));
                 menuDeo.setDsp(util.nvl(rs.getString("dsp")));
                 menuDeo.setLnk(util.nvl(rs.getString("lnk")));
                 menuHdr.add(menuDeo);
             }
             rs.close(); pst.close();
         } catch (SQLException sqle) {
             // TODO: Add catch code
             sqle.printStackTrace();
         }
         
         req.setAttribute("menuHdr", menuHdr);
          ArrayList subMenuLst = new ArrayList();
          HashMap subMenuMap = new HashMap();
         String subMenu = "select grp_df_menu_idn, df_menu_idn, img, dsp, lnk from df_menu where dsp_lvl = 2 and nvl(grp_df_menu_idn,0) > 0 " + 
         "  and to_dte is null and stt='A' order by grp_df_menu_idn, srt ";
         int pGrpId = 0;

             outLst = db.execSqlLst("subMenu", subMenu, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
         try {
            
             while (rs.next()) { 
                int dfMenuId = rs.getInt("grp_df_menu_idn");
                 
                 if(pGrpId==0)
                  pGrpId = dfMenuId;
                 
                 if(pGrpId!=dfMenuId){
                     subMenuMap.put(Integer.toString(pGrpId),subMenuLst);
                     subMenuLst = new ArrayList();
                     pGrpId = dfMenuId;
                 }

                 DFMenu menuDeo = new DFMenu() ;
                 menuDeo.setGrpDfMenuId(rs.getInt("grp_df_menu_idn"));
                 menuDeo.setDfMenuIdn(rs.getInt("df_menu_idn"));
                 menuDeo.setDsp(util.nvl(rs.getString("dsp")));
                 menuDeo.setLnk(util.nvl(rs.getString("lnk")));
                 subMenuLst.add(menuDeo);
             }
             rs.close(); pst.close();
         } catch (SQLException sqle) {
             // TODO: Add catch code
             sqle.printStackTrace();
         }
         if(pGrpId!=0)
         subMenuMap.put(Integer.toString(pGrpId),subMenuLst);
         
        
         req.setAttribute("subMenuMap", subMenuMap);
         HashMap trdLevelMap = new HashMap();
         ArrayList trdLevelLst = new ArrayList();
         String trdLevel = "select df_menu_idn , df_menu_itm_idn , dsp , lnk  from df_menu_itm where stt = 'A' order by df_menu_idn , srt ";
         int pDfMenuId = 0;

             outLst = db.execSqlLst("subMenu", trdLevel, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
         try {
            while (rs.next()) {
                int dfMenuId = rs.getInt("df_menu_idn");
                 
                 if(pDfMenuId==0)
                  pDfMenuId = dfMenuId;
                 
                 if(pDfMenuId!=dfMenuId){
                    trdLevelMap.put(Integer.toString(pDfMenuId),trdLevelLst);
                     trdLevelLst = new ArrayList();
                     pDfMenuId = dfMenuId;
                 }
                
                
                
                DFMenu menuDeo = new DFMenu() ; 
              
                menuDeo.setDfMenuIdn(rs.getInt("df_menu_idn"));
                menuDeo.setDfmenuitmidn(rs.getInt("df_menu_itm_idn"));
                menuDeo.setDsp(util.nvl(rs.getString("dsp")));
                menuDeo.setLnk(util.nvl(rs.getString("lnk")));
                trdLevelLst.add(menuDeo);
            }
             rs.close(); pst.close();
         } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
         }
         if(pDfMenuId!=0)
         trdLevelMap.put(Integer.toString(pDfMenuId),trdLevelLst);
         
         req.setAttribute("trdLevelMap", trdLevelMap);
             util.updAccessLog(req,res,"Menu Role Deatail", "load end");
    
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
        String stt = req.getParameter("stt");
        String manuIdn = req.getParameter("menuIdn");
        String itemIdn = req.getParameter("itemIdn").replaceAll(" ", "");
        String roleIdn = req.getParameter("roleIdn");
        ArrayList ary = new ArrayList();
       
        if(stt.equals("true")){
            String checkRole = "select * from df_menu_role where  df_menu_idn=? and  df_role_idn=?";
            if(itemIdn.equals("NA")){
                 checkRole =  checkRole+" and df_menu_itm_idn is null ";
                ary = new ArrayList();
                ary.add(manuIdn);
                ary.add(roleIdn);
            }else{
                 checkRole =  checkRole+" and df_menu_itm_idn=? ";
                ary = new ArrayList();
                ary.add(manuIdn);
                ary.add(roleIdn);
                ary.add(itemIdn);
            }

            ArrayList outLst = db.execSqlLst("checkRole", checkRole, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            if(rs.next()){
                String updateRole = "update df_menu_role set stt='A' where df_menu_idn=?  and df_role_idn=? ";
                if(itemIdn.equals("NA")){
                    updateRole = updateRole+" and df_menu_itm_idn is null ";
                    ary = new ArrayList();
                    ary.add(manuIdn);
                    ary.add(roleIdn);
                }else{
                    updateRole = updateRole+" and df_menu_itm_idn=? ";
                    ary = new ArrayList();
                    ary.add(manuIdn);
                    ary.add(roleIdn);
                    ary.add(itemIdn);
                }
                int up = db.execUpd("update", updateRole,ary);
            }else{
                String insertRole = "insert into df_menu_role(df_mr_idn,df_menu_idn, df_menu_itm_idn,df_role_idn ,stt) "+
                    "select df_ur_idn_seq.nextval , ? , ?, ? ,? from dual ";
                ary = new ArrayList();
                ary.add(manuIdn);
                ary.add(nvl(itemIdn));
                ary.add(roleIdn);
                ary.add("A");
            int up = db.execUpd("insertRole",insertRole,ary);
                
            }
            rs.close(); pst.close();
        }else{
            String updateRole = "update df_menu_role set stt='IA'  where  df_menu_idn=? and df_role_idn=? "; 
            
            if(itemIdn.equals("NA")){
                updateRole = updateRole +"and df_menu_itm_idn is null";
                ary = new ArrayList();
                ary.add(manuIdn);
                ary.add(roleIdn);
              
                
            }else{
                updateRole = updateRole +"and df_menu_itm_idn =?";
                ary = new ArrayList();
                ary.add(manuIdn);
                ary.add(roleIdn);
                ary.add(itemIdn);
           
            }
            int up = db.execUpd("update", updateRole,ary);
        }
      

        return null;
        }
    }
    public String nvl(String pval){
       pval = pval.replaceAll(" ", "");
        if(pval.equals("NA"))
            pval = null;
        return pval;
        
    }
 
    public String nvl2(String pval){
       
        if(pval==null)
            pval = "NA";
        return pval;
        
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
        util.updAccessLog(req,res,"Menu Role Deatail", "Unauthorized Access");
        else
        util.updAccessLog(req,res,"Menu Role Deatail", "init");
    }
    }
    return rtnPg;
    }
    public MenuRoleAction() {
        super();
    }
}
