package ft.com;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class InsertGtThread implements Runnable {
    DBMgr                db;
    InfoMgr              info;
    HttpSession          session;
    DBUtil               util;
    ArrayList vwPrpLst=null;
    List tilepktLst=null;
    String insertSql="";
    int tile=0;

    public InsertGtThread(int tile,HttpServletRequest req,ArrayList vwPrpLst,List tilepktLst,String insertSql) {
        this.session = req.getSession(false);
        this.info = (InfoMgr)session.getAttribute("info");
        this.util = new DBUtil();
        this.db = new DBMgr(); 
        this.vwPrpLst = vwPrpLst; 
        this.tilepktLst = tilepktLst; 
        this.insertSql = insertSql; 
        this.tile = tile;
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    }
    @Override
    public void run() {
        try {
             insertGtdata();
        } catch(Exception e) {
            System.out.println("Conn Init Err : "+ e.toString());
        } finally {

        }
    }
    public void insertGtdata() throws Exception {
        long startm = new Date().getTime();
        int tilepktLstsz=tilepktLst.size();
        ArrayList param=new ArrayList();
        int vwPrpLstsz=vwPrpLst.size();
        if(tilepktLstsz > 0){
        for(int i=0;i< tilepktLstsz;i++){
            param=new ArrayList();
            HashMap pktPrpMap = new HashMap();
            pktPrpMap=(HashMap)tilepktLst.get(i);
            param.add("");
            param.add("");
            param.add(pktPrpMap.get("pkt_ty"));
            param.add(pktPrpMap.get("stk_idn"));
            param.add(pktPrpMap.get("vnm"));
            param.add(pktPrpMap.get("qty"));
            param.add(pktPrpMap.get("quot"));
            param.add(pktPrpMap.get("cts"));
            param.add(pktPrpMap.get("pkt_dte"));
            param.add(pktPrpMap.get("stt"));
            param.add(pktPrpMap.get("dsp_stt"));
            param.add(pktPrpMap.get("cmp"));
            param.add(pktPrpMap.get("rap_rte"));
            param.add(pktPrpMap.get("cert_lab"));
            param.add(pktPrpMap.get("cert_no"));
            param.add(pktPrpMap.get("flg"));
            param.add(pktPrpMap.get("sk1"));
            param.add(pktPrpMap.get("quot"));
            param.add(pktPrpMap.get("rap_dis"));
            param.add(pktPrpMap.get("CERTIMG"));
            param.add(pktPrpMap.get("DIAMONDIMG"));
            param.add(pktPrpMap.get("JEWIMG"));
            param.add(pktPrpMap.get("SRAYIMG"));
            param.add(pktPrpMap.get("AGSIMG"));
            param.add(pktPrpMap.get("MRAYIMG"));
            param.add(pktPrpMap.get("RINGIMG"));
            param.add(pktPrpMap.get("LIGHTIMG"));
            param.add(pktPrpMap.get("REFIMG"));
            param.add(pktPrpMap.get("VIDEOS"));
            param.add(pktPrpMap.get("VIDEO_3D"));
            param.add(pktPrpMap.get("fnl_usd"));
            param.add(pktPrpMap.get("byr"));
            param.add(pktPrpMap.get("byr_cntry"));
            param.add(pktPrpMap.get("emp"));
            param.add(pktPrpMap.get("sl_dte"));
            param.add(pktPrpMap.get("rmk"));
            param.add((pktPrpMap.get("sl_dte_srt")));
//            System.out.println(param);
            for (int j = 0; j <vwPrpLstsz; j++) {
                String lbPrp = util.nvl((String)vwPrpLst.get(j));
                param.add(pktPrpMap.get(lbPrp));
                param.add(pktPrpMap.get(lbPrp+"_SRT"));
                if(lbPrp.equals("KTSVIEW") || lbPrp.equals("COMMENT") || lbPrp.equals("MEM_COMMENT"))
                param.add(pktPrpMap.get(lbPrp));
            }
//            int ct = db.execDirUpd("insert gt",insertSql, param);
//            System.out.println(insertSql);
//            System.out.println(param);
            db.execBatchUpd("TILE_"+tile, "insert gt", insertSql, param,"DIR");
        }
            db.execBatch();
        }
        long endm = new Date().getTime();
        System.out.println("@ "+tile+" Time = "+ ((endm-startm)/1000));
    }
}
