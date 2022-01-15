package ft.com;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class MemoTransThread  implements Runnable {
        DBMgr                db;
        InfoMgr              info;
        HttpSession          session;
        DBUtil               util;
        List            pktList;
      public MemoTransThread(HttpServletRequest req,List pktList) {
          this.session = req.getSession(false);
          this.info = (InfoMgr)session.getAttribute("info");
          this.util = new DBUtil();
          this.db = new DBMgr(); 
          this.pktList = pktList;
          db.setCon(info.getCon());
          util.setDb(db);
          util.setInfo(info);
          db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
          util.setLogApplNm(info.getLoApplNm());
      }
    
    @Override
    public void run() {
        for(int i=0;i<pktList.size();i++){
            HashMap pktMap = (HashMap)pktList.get(i);
            String stkIdn = (String)pktMap.get("stkIdn");
            String memoId = (String)pktMap.get("memoId");
            String newMemoIdn = (String)pktMap.get("newMemoIdn");
            String memoTyp = (String)pktMap.get("memoTyp");
            ArrayList  ary = new ArrayList();
            ary.add(stkIdn);
            ary.add(memoId);
            ary.add(String.valueOf(newMemoIdn));
            ary.add(memoTyp);
           int  ct = db.execCall("MemoTrf","MEMO_TRF_PKT(pStkIdn => ?, pFrmIdn => ?, pToIdn => ?, pTyp => ?)", ary);
                    
        }
        
    }
}
