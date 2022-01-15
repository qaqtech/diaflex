package ft.com.mixakt;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import java.util.ArrayList;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class MixAktAjaxAction  extends DispatchAction {
    public MixAktAjaxAction() {
        super();
    }
    
    HttpSession session ;
    DBMgr db ;
    InfoMgr info ;
    DBUtil util ;
    public ActionForward BoxIdns(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        String boxTyp = util.nvl(req.getParameter("boxTyp"));
        String initial = util.nvl(req.getParameter("param"));
        HashMap prp = info.getPrp();
        ArrayList boxIsLst = (ArrayList)prp.get("BOX_IDV");
        ArrayList boxPIsLst = (ArrayList)prp.get("BOX_IDP");
        StringBuffer sb = new StringBuffer();
       
        for(int i=0 ; i < boxIsLst.size();i++){
            String boxId = (String)boxIsLst.get(i);
            if(boxId.indexOf(boxTyp+"-"+initial)!=-1||boxId.indexOf(boxTyp+"_"+initial)!=-1){
                sb.append("<nmes>");
                sb.append("<nmeid>"+boxId+"</nmeid>");
                sb.append("<nme>"+boxPIsLst.get(i)+"</nme>");
                sb.append("</nmes>");
            }
        }
        res.setContentType("text/xml"); 
        res.setHeader("Cache-Control", "no-cache"); 
        res.getWriter().write("<mnme>"+sb.toString()+"</mnme>");
        return null;
    }
}
