package ft.com;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.MemoryHandler;
import java.util.logging.SimpleFormatter;

public class LogMgr {
    private String applNm = "Diaflex";
    private String logNm  = "com.log.DiaFlex";
    private Logger        logger = null;
    int errLvl=0;
    public LogMgr() {
        init();
    }

    public void init() {
        boolean     append  = true;
        FileHandler handler = null;

        try {

            /*
             * logger = Logger.getLogger(logNm);
             *    //logger.addHandler(handler);
             * String logFileNm = logNm.replace('.','_')+".log";
             * handler = new FileHandler("/"+ logFileNm, append);
             * logger.addHandler(handler);
             * logger.setLevel(Level.INFO);
             * SimpleFormatter formatter = new SimpleFormatter();
             * handler.setFormatter(formatter);
             * // the following statement is used to log any messages
             * //log(Level.WARNING,"My first log");
             *
             * handler.setFilter(new Filter(){
             *   public boolean isLoggable(LogRecord record) {
             *       // return true if the record should be logged;
             *       // false otherwise.
             *       return true;
             * }});
             * int numRec = 100;
             * MemoryHandler mhandler =
             *   new MemoryHandler(handler, numRec, Level.OFF) {
             *   public synchronized void publish(LogRecord record) {
             *       // Log it before checking the condition
             *       super.publish(record);
             *
             *       boolean condition = false;
             *       if (condition) {
             *           // Condition occurred so dump buffered records
             *           push();
             *       }
             *   }
             * };
             */
        } catch (Exception e) {
            SOP(" Log IO  Error : " + e.getMessage());
        }
    }

    public void log(String s) {

        /*
         * if (logger == null)
         *   init();
         * logger.log(Level.FINE, s);
         */
        SOP(applNm + ":" + getDtTm() + ":" + s);
    }

    public void log(Level l, String s) {

        /*
         * if (logger == null)
         *   init();
         * logger.log(l, s);
         */
        SOP(applNm + ":" + getDtTm() + ":" + l.toString() + ":" + s);
    }
    public void log(int msgLvl, String msg) {
        if(errLvl<msgLvl)
        SOP(applNm + ":" + getDtTm() + ":" + msg);
    }
    public void log(int msgLvl,Level l, String msg) {
        if(errLvl<msgLvl)
        SOP(applNm + ":" + getDtTm() + ":" + l.toString() + ":" + msg);
    }
    public void SOP(String s) {
        System.out.println(s);
    }

    public String getDtTm() {
        String           dtTm      = "";
        Calendar         date      = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");

        dtTm = formatter.format(date.getTime());

        return dtTm;
    }

    public void setApplNm(String applNm) {
        this.applNm = applNm;
    }

    public String getApplNm() {
        return applNm;
    }

    public void setLogNm(String logNm) {
        this.logNm = logNm;
    }

    public String getLogNm() {
        return logNm;
    }

    public void setErrLvl(int errLvl) {
        this.errLvl = errLvl;
    }

    public int getErrLvl() {
        return errLvl;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
