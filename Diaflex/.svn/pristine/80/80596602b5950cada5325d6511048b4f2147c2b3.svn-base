package ft.com;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetPktPrice {
    String reqTyp = "MKT";
    String pktTyp = "NR";
    Connection con = null;
    int seq = 0;
    int totalSheets = 18 ;
    int stkIdn ;
    int pktCnt = 0 ;
    DBMgr                db;
    InfoMgr              info;
    HttpSession          session;
    DBUtil               util;
    public GetPktPrice(HttpServletRequest req) {
        session = req.getSession(false);
        info = (InfoMgr)session.getAttribute("info");
        util = new DBUtil();
        db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    }
    public HashMap calcPrice(ArrayList stkIdnLst,String dis_typ) {
        HashMap<String, HashMap> calcPriceDetails =new HashMap<String, HashMap>();
        HashMap dbinfo = info.getDmbsInfoLst();
        String sqlDb = (String)dbinfo.get("CNT");
        JspUtil jsputil=new JspUtil();
        int stkIdnLstsz=stkIdnLst.size();
        
        HashMap<String, HashMap> priSheets = (HashMap<String, HashMap>)jsputil.getFromMem(info.getMem_ip(),info.getMem_port(),sqlDb+"_getSheets");
        HashMap<String, ArrayList> grpSheets = (HashMap<String, ArrayList>)jsputil.getFromMem(info.getMem_ip(),info.getMem_port(),sqlDb+"_getGrpSheets");
            for(int p=0;p < stkIdnLstsz;p++){
                String stkIdn=util.nvl((String)stkIdnLst.get(p));
                HashMap pktPriceDetails =new HashMap();
                ArrayList sheetAppliedLst=new ArrayList();
                ++pktCnt;
                List<String> sheets = new ArrayList<String>(priSheets.keySet());
                totalSheets = priSheets.size();
                double pct = 0;
                double mfgPct = 0;
                String priDtl = "";
                
                ExecutorService executor = Executors.newFixedThreadPool(totalSheets);
                
                List<Future<String>> list = new ArrayList<Future<String>>();
                
                for (int i = 0; i < totalSheets; i++) {
                    String nme = (String)sheets.get(i);
                    ArrayList grps = grpSheets.get(nme) == null? new ArrayList():grpSheets.get(nme);
                    HashMap grpDtl = priSheets.get(nme) == null? new HashMap() : priSheets.get(nme);
                    
                    Callable<String> callable = new GetPktPriceGroup(stkIdn, nme, grps,sqlDb);
                    Future<String> future = executor.submit(callable);
                    list.add(future);

                    /*
                    if(Integer.parseInt((String)threadPct.get()) != 0) {
                        pct += threadPct ;
                        priDtl += " | " + nme + "=" + threadPct ;
                    }*/
                }
                for(Future<String> fut : list){
                    try {
                        String dtl = fut.get();
                        if(!dtl.equals("NA")) {
                            String nme = dtl.substring(0, dtl.indexOf("="));
                            double disPct = Double.parseDouble(dtl.substring(dtl.indexOf("=")+1));
                            HashMap grpDtl = priSheets.get(nme) == null? new HashMap() : priSheets.get(nme);
                            
                            if(grpDtl.get("disTyp").equals("SL"))
                                pct += disPct ;
                            
                            mfgPct += disPct;
                            
                            priDtl += " | " + dtl;
                            util.SOP(dtl + " :: " + new Date());
                            if(grpDtl.get("disTyp").equals(dis_typ) || dis_typ.equals("ALL")){
                                HashMap priDtlMap=new HashMap();
                                priDtlMap.put("NME", nme);
                                priDtlMap.put("DIS", disPct);
                                sheetAppliedLst.add(priDtlMap);
                            }
                        }
                        
                    } catch (ExecutionException e) {
                    } catch (InterruptedException e) {
                    }
                }
                executor.shutdown();
                
                while (!executor.isTerminated()) {
                    
                }        
                util.SOP("@"+stkIdn+" : NR = "+pct+ " | Mfg = "+ mfgPct + priDtl);
                pktPriceDetails.put("NR_PCT", pct);
                pktPriceDetails.put("MFG_PCT", mfgPct);
                pktPriceDetails.put("PRI_DTL", sheetAppliedLst);
                calcPriceDetails.put(stkIdn, pktPriceDetails);
            }
        return calcPriceDetails;
    }
}
