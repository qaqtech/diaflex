//package ft.com.assorthk;
//
//import ft.com.DBMgr;
//import ft.com.DBUtil;
//import ft.com.InfoMgr;
//import ft.com.dao.Mprc;
//import ft.com.fileupload.FileUploadForm;
//import ft.com.fileupload.FileUploadInterface;
//
//import ft.com.generic.GenericImpl;
//
//import ft.com.generic.GenericInterface;
//
//import java.sql.CallableStatement;
//
//import java.util.ArrayList;
//
//import java.util.HashMap;
//
//import java.util.ArrayList;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.apache.struts.action.ActionForm;
//import org.apache.struts.action.ActionForward;
//import org.apache.struts.action.ActionMapping;
//import org.apache.struts.actions.DispatchAction;
//
//public class WtCheckAction extends DispatchAction {
//    
//    HttpSession session ;
//    DBMgr db ;
//    InfoMgr info ;
//    DBUtil util ;
//    WtCheckForm assortForm ;
//    GenericInterface genericInt ;
//    public ActionForward load(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
//        init(req);
//        assortForm = (WtCheckForm)form;
//        assortForm.reset();
//        
//        String isstt = util.nvl(req.getParameter("isstt"));
//        System.out.println("load instt:"+isstt);
//        req.setAttribute("isstt", isstt);
//        
//        //assortForm.setStt(isstt);
//        ArrayList prcidList = assortInt.getPrc(req);
//        session.setAttribute("prcidList", prcidList);
//        
//        //the mdl will be passed in the url for the particular form
//        String mdl = (String) req.getParameter("mdl");
//        session.setAttribute("mdl", mdl);
//        
//        /*ArrayList mprcList = assortInt.getPrc(req);
//        assortForm.setMprcList(mprcList);
//        
//        ArrayList empList = assortInt.getEmp(req);
//        assortForm.setEmpList(empList);
//        
//        ArrayList assortSrchList = assortInt.ASGenricSrch(req);
//        info.setGncPrpLst(assortSrchList);*/
//      return am.findForward("load");
//    }
//    
//    public ActionForward fetch(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
//        init(req);
//        assortForm = (WtCheckForm)form;
//        String mprcIdn = util.nvl((String)assortForm.getValue("mprcIdn"));
//        String stoneId = util.nvl((String)assortForm.getValue("vnmLst"));
//        String subbutton = util.nvl((String)assortForm.getValue("subbutton"));
//        //String empId = util.nvl((String)assortForm.getValue("empIdn"));
//       
//        /*HashMap prcBeansList = (HashMap)session.getAttribute("mprcBean");
//        Mprc mprcDto = (Mprc)prcBeansList.get(mprcIdn);
//        String prcStt = mprcDto.getIn_stt();*/
//        
//        //System.out.println("assortForm.getStt():"+assortForm.getStt());
//        String isstt = util.nvl((String)assortForm.getValue("isstt"));
//        System.out.println("load isstt:"+isstt);
//        req.setAttribute("isstt", isstt);
//        
//        HashMap params = new HashMap();
//        //params.put("stt", assortForm.getStt());
//        params.put("stt", isstt);
//        
//        if(subbutton.equalsIgnoreCase("srch")) {
//          params.put("vnm",stoneId);
//        }
//        else {
//          params.put("vnm","");
//        }
//        
//        params.put("issueId", "");
//        params.put("empIdn", "0");
//        //add the chk prp
//        params.put("chk", "WT_CHECKER");
//        //params.put("mprcIdn", mprcIdn);
//        //enter the mdl for the form
//        params.put("mdl", session.getAttribute("mdl").toString());
//        
//        ArrayList stockList = assortInt.FecthResult(req, params);
//        System.out.println("stklist size: " + stockList.size());
//        
//        if(stockList.size()>0){
//        HashMap totals = assortInt.GetTotal(req);
//        req.setAttribute("totalMap", totals);
//        }
//        req.setAttribute("view", "Y");
//        assortForm.setValue("prcId", mprcIdn);
//        assortForm.setValue("empId", "0");
//        session.setAttribute("StockList", stockList);
//        
//        //get the checker and approver remark
//        ArrayList magchkRem = assortInt.getRem(req, "Mg_Rmk");
//        assortForm.setChkRem(magchkRem);
//        
//        ArrayList magappRem = assortInt.getRem(req, "Ap_Rmk");
//        assortForm.setAppRem(magappRem);
//        //String magchkRem = "select val, desc from prp where mprp = 'Mg_Rmk'";
//        //String magappRem = "select val, desc from prp where mprp = 'Ap_Rmk'";
//        
//        return am.findForward("load");
//    }
//    public ActionForward save(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
//        init(req);
//        assortForm = (WtCheckForm)form;
//        String prcId = (String)assortForm.getValue("prcId");
//        //String empId = (String)assortForm.getValue("empId");
//        
//        String isstt = util.nvl((String)assortForm.getValue("isstt"));
//        System.out.println("issue isstt:"+isstt);
//        req.setAttribute("isstt", isstt);
//        
//        ArrayList params = null;
//        int issueIdn = 0;
//        ArrayList msglist = new ArrayList();
//        
//        ArrayList stockList = (ArrayList)session.getAttribute("StockList");
//        for(int i=0;i< stockList.size();i++){
//            int sr = i+1;
//            HashMap stockPkt = (HashMap)stockList.get(i);
//            String stkIdn = (String)stockPkt.get("stk_idn");
//            String isChecked = util.nvl((String)assortForm.getValue(stkIdn));
//            
//            if(isChecked.equals("yes")){
//                //if checker says Ok then approver values are not entered/ required. 
//                //    Status of stone updated to out status as per process id
//                //if checker fail then approver can either OK or fail
//                // App OK -> save status as out stt but store checker rem
//                // App FL -> update status 'Surat_AV' and save both rem
//                
//                //if checker says ok - enter property in stk dtl table
//                //if checker fail then approver can either OK or fail
//                //App OK -> store chk fail, app ok and checker rem
//                //App FL -> update status 'Surat_AV' and save both rem
//                String chkOK = util.nvl((String)assortForm.getValue("CHKOK_"+sr));
//                String chkFL = util.nvl((String)assortForm.getValue("CHKFL_"+sr));
//                String chkRem = util.nvl((String)assortForm.getValue("chkRem_"+sr));
//                
//                String appOK = util.nvl((String)assortForm.getValue("APPOK_"+sr));
//                String appFL = util.nvl((String)assortForm.getValue("APPFL_"+sr));
//                String appRem = util.nvl((String)assortForm.getValue("appRem_"+sr));
//                
//                String newWt = util.nvl((String)assortForm.getValue("newWt_"+sr));
//                String diff = util.nvl((String)assortForm.getValue("diff_"+sr));
//                
//                if(chkOK.equals("yes")) {
//                  //String outStt = "update mstk set stt= (select ot_stt from mprc where idn = "+ prcId+") where idn = " + stkIdn;
//                  String outStt = "insert into stk_dtl(mstk_idn, grp, mprp, val, lab) " + 
//                                           "values("+ stkIdn +", 1, 'WT_CHECKER', 'CHK_OK', 'NONE')";
//                  int ct = db.execUpd("update mstk - Checker OK", outStt, new ArrayList());
//                  msglist.add("chk ok prp inserted for "+stkIdn);
//                }
//                else {
//                  if(chkFL.equals("yes")){
//                    String newwtQry = "insert into stk_dtl(mstk_idn, grp, mprp, num, val, lab) " + 
//                                         "values("+ stkIdn +", 1, 'NEW_WT', '"+newWt+"', 'NA', 'NONE')";
//                    int ct = db.execUpd("New Weight", newwtQry, new ArrayList());
//                    String diffQry = "insert into stk_dtl(mstk_idn, grp, mprp, num, val, lab) " + 
//                                         "values("+ stkIdn +", 1, 'WT_DF', '"+diff+"', 'NA', 'NONE')";
//                    ct = db.execUpd("Weight Diff", diffQry, new ArrayList());
//                    msglist.add("new weight and weight diff: "+ newWt + "&" + diff + " of " + stkIdn +" is stored.");
//                    
//                    String outStt = "insert into stk_dtl(mstk_idn, grp, mprp, val, lab) " + 
//                                             "values("+ stkIdn +", 1, 'WT_CHECKER', 'CHK_FAIL', 'NONE')";
//                    ct = db.execUpd("Checker Fail", outStt, new ArrayList());
//                    msglist.add("chk fail prp inserted for "+stkIdn);
//                    
//                    String chkRemqry = "insert into stk_dtl(mstk_idn, grp, mprp, val, lab) " + 
//                                       "values("+ stkIdn +", 1, 'Mg_Rmk', '"+chkRem+"', 'NONE')";
//                    ct = db.execUpd("Checker fail rem", chkRemqry, new ArrayList());
//                    
//                    String appRemqry = "insert into stk_dtl(mstk_idn, grp, mprp, val, lab) " + 
//                                       "values("+ stkIdn +", 1, 'Ap_Rmk', '"+appRem+"', 'NONE')";
//                    ct = db.execUpd("Approver rem", appRemqry, new ArrayList());
//                    
//                    if(appOK.equals("yes")) {
//                      //String upStt = "update mstk set stt= (select ot_stt from mprc where idn = "+ prcId+") where idn = " + stkIdn;
//                      String upStt = "insert into stk_dtl(mstk_idn, grp, mprp, val, lab) " + 
//                                             "values("+ stkIdn +", 2, 'WT_CHECKER', 'APP_OK', 'NONE')";
//                      ct = db.execUpd("Checker OK", upStt, new ArrayList());
//                      
//                      msglist.add(stkIdn +": Chk Fail, App Ok prp stored and Checker fail, Approver remark has been stored.");
//                      
//                    }
//                    else if(appFL.equals("yes")) {
//                      String upStt = "update mstk set stt='Surat_AV'  where idn = " + stkIdn;
//                      ct = db.execUpd("update mstk - Chkr FL and App FL", upStt, new ArrayList());
//                      
//                      msglist.add(stkIdn +" is updated to Surat_AV. Chk Fail, App Fail prp stored and Checker and Approver fail remarks have been stored.");
//                    }
//                  }
//                }
//            }
//       }
//    if(issueIdn!=0)
//        req.setAttribute( "msg","Requested packets get Issue with Issue Id "+issueIdn);
//     assortForm.reset();
//     return am.findForward("load");
//    }
//    public void init(HttpServletRequest req) {
//        session = req.getSession(false);
//        db = (DBMgr)session.getAttribute("db");
//        info = (InfoMgr)session.getAttribute("info");
//        util = ((DBUtil)session.getAttribute("util") == null)?new DBUtil():(DBUtil)session.getAttribute("util");
////        assortInt = new AssortChekerImpl();
//        genericInt = new GenericImpl();
//    }
//    public WtCheckAction() {
//        super();
//    }
//}
