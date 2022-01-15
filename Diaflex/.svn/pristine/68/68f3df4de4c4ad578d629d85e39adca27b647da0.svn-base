package ft.com.box;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.dao.ByrDao;
import ft.com.dao.boxDet;
import ft.com.dao.boxVnm;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;


public class BoxSelectionImpl implements BoxSelectionInterface {
 
    
  public ArrayList FetchResult(HttpServletRequest req,HttpServletResponse res , BoxSelectionForm form){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      ArrayList ary = new ArrayList();
      String view =util.nvl(form.getView());
      String vnm = util.nvl((String)form.getValue("vnmLst"));
      //String boxval = util.nvl(req.getParameter("boxnme"));
      
      ary=new ArrayList();
      
      String delQ = " Delete from gt_srch_rslt ";
      int ct =db.execUpd(" Del Old Pkts ", delQ, ary);
      ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
      
      String srchRefQ = 
      "    Insert into gt_srch_rslt (stk_idn, vnm, cts, qty, cmp, quot, stt, rmk, flg) " +  
      "    select idn, vnm, cts, qty, upr, upr, stt, tfl3, 'Z' from mstk b where stt in ('MIX_AV','MX_AV') ";
      
      if(view.length() > 0){
          
        if(!vnm.equals("")){
           vnm = util.getVnm(vnm);
            
            srchRefQ = srchRefQ+"  and ( b.vnm in ("+vnm+") or b.tfl3 in ("+vnm+"))" ;
            
          }
        
//          if(!boxval.equals("")){
//             
//            srchRefQ = srchRefQ+" and  idn in ( select mstk_idn from stk_dtl  where mprp = 'BOX_TYP' and grp=1 and  val ='"+boxval+"')" ;
//            
//            }
          
      }
      ary = new ArrayList();
      ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
      
      String pktPrp = "pkgmkt.pktPrp(0,?)";
      ary = new ArrayList();
      ary.add("BOX_VIEW");
      ct = db.execCall(" Srch Prp ", pktPrp, ary);
      
      ArrayList stockList = SearchResult(req,res , form);
      
      return stockList;
      
  }
  
    
    public ArrayList SearchResult(HttpServletRequest req,HttpServletResponse res , BoxSelectionForm form ){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        GenericInterface genericInt=new GenericImpl();
        String vnmLst = util.nvl((String)form.getValue("vnmLst"));
        if(!vnmLst.equals(""))
        vnmLst = util.getVnm(vnmLst);
        ArrayList pktList = new ArrayList();
        ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "BoxViewLst", "BOX_VIEW");
            String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt , qty , cts , quot , rmk ";

        for (int i = 0; i < vwPrpLst.size(); i++) {
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

        
        String rsltQ = srchQ + " from gt_srch_rslt  where flg=? ";
        
        ArrayList ary = new ArrayList();
        ary.add("Z");
        ResultSet rs = db.execSql("search Result", rsltQ, ary);
//        System.out.println(rsltQ);
        try {
            while(rs.next()) {
                String stkIdn = util.nvl(rs.getString("stk_idn"));
                if(!stkIdn.equals("")){
                    form.setValue("stt_"+stkIdn, "LB");
                }
                    
                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
                String vnm = util.nvl(rs.getString("vnm"));
                pktPrpMap.put("vnm",vnm);
                    String tfl3 = util.nvl(rs.getString("rmk"));
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
                pktPrpMap.put("stk_idn",stkIdn);
                pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
                pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
                pktPrpMap.put("quot",util.nvl(rs.getString("quot")));
                
                for(int j=0; j < vwPrpLst.size(); j++){
                     String prp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                      
                      String val = util.nvl(rs.getString(fld)) ;
                      
                        
                        pktPrpMap.put(prp, val);
                         }
                              
                    pktList.add(pktPrpMap);
                }
            rs.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        if(!vnmLst.equals(",") && !vnmLst.equals("")){
        vnmLst = vnmLst.replaceAll(",", "");
        req.setAttribute("vnmNotFnd", vnmLst+" : Packets Not Found");
        }

        return pktList;
    }
    
   
    public BoxSelectionImpl() {
        super();
    }
    
       
    public ArrayList getBox(HttpServletRequest req , BoxSelectionForm form){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList ary = null;
        ArrayList boxList = new ArrayList();
        
                
            ArrayList genricSrchLst = info.getGncPrpLst();
            HashMap param = (HashMap)session.getAttribute("paramsmap");
        
            for(int i=0;i<genricSrchLst.size();i++){
            String lprp = (String)genricSrchLst.get(i);
                  String fldVal1 = util.nvl((String)param.get(lprp+"_1"));
                  String fldVal2 = util.nvl((String)param.get(lprp+"_2"));
            if(!fldVal1.equals("")){
                HashMap labSttMap = new HashMap();
                String labQry=" select a.crt_idn,typ||'-'||dsc exsDes from stk_crt a, stk_crt_dtl b where a.crt_idn = b.crt_idn and " +
                    "b.MPRP = ? and b.SFR = ? and b.STO = ? and stt='A' and vld_dte is null";
                ary = new ArrayList();
                ary.add(lprp);
                ary.add(fldVal1);
                ary.add(fldVal2);
                ResultSet rs = db.execSql("labQry", labQry, ary);
                try {
                while (rs.next()) {
                    boxDet boxDet = new boxDet();
                    String boxVal = rs.getString("crt_idn");
                    boxDet.setBoxVal(boxVal);
                    boxDet.setBoxDesc(rs.getString("exsDes"));
                    boxList.add(boxDet);
                }
                    rs.close();
                } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
            }   
        }
    return boxList;
    }
    
    public ArrayList getBoxList (HttpServletRequest req , BoxSelectionForm form){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ResultSet rs = null;
        ArrayList listBox = new ArrayList();
        String box = "select vnm from mstk where pkt_ty = 'MIX'";
        rs = db.execSql("byr", box, new ArrayList());
            try {
                while(rs.next()){
                    boxVnm boxVnm = new boxVnm();
                    String boxvnm =rs.getString("vnm");
                    boxVnm.setBoxvnm(boxvnm);
                    listBox .add(boxVnm);    
                }
            rs.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
       return listBox;
    }   
}
