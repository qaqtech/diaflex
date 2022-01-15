package ft.com;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ResetArrayThread extends Thread {
    public ResetArrayThread() {
        super();
    }
    DBMgr                db;
    InfoMgr              info;
    HttpSession          session;
    DBUtil               util;
    public ResetArrayThread(HttpServletRequest req) {
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
    public void run(){
        int sleeptime=Integer.parseInt(util.nvl((String)info.getDmbsInfoLst().get("ADV_PRI_THREAD"),"10"));
        while(true){
        try {
        String connExists=util.nvl(util.getConnExists());
        if(!connExists.equals("N")){
        int ct = db.execCall("dpp_pri.resetary", "dpp_pri.resetary", new ArrayList());
        Thread.sleep(1000 * 60 *sleeptime);
        }else
            break;
        } catch (InterruptedException e) {
        e.printStackTrace();
        }
       }
    }
}
