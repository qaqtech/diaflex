package ft.com;

import java.sql.Connection;
import java.io.FileInputStream;
import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.logging.Level;

public class GetPktItempri extends Thread {
    ArrayList stkIdnList ;
    int pct = 0;
    int seq ;
    Connection con = null;
    String sqlDb  ;
    
    public GetPktItempri(String sqlDb,ArrayList stkIdnList) {
        super();
        this.stkIdnList = stkIdnList;
        this.sqlDb = sqlDb;
    }
    
    public void run()
    {
    update();
    }
    
    public void update(){
        long start = new Date().getTime();
        try {
            con = getConnectionCPDF(sqlDb);   
            String insQ = " insert into gt_pkt(mstk_idn) select ? from dual ";
            ArrayList ary = new ArrayList();
            for(int m=0;m< stkIdnList.size();m++){
            String   stkIdn=(String)stkIdnList.get(m);
            ary = new ArrayList();
            ary.add(stkIdn);
            int ctv = execUpd("reprc", insQ, ary);
            }
            
            String  jbCntDb = "2";
            ArrayList out = new ArrayList();
            out.add("I");
            String reprcProc = "reprc(num_job => ?, lstt1 => 'AS_PRC', lstt2 => 'FORM',lSeq=> ?)";
            ArrayList reprcParams = new ArrayList();
            int jobCnt = Integer.parseInt(jbCntDb);
            reprcParams.add(String.valueOf(jobCnt));
            int lseq = 0;
            CallableStatement cnt1 = execCall(" reprc : ",reprcProc, reprcParams,out,con );
            lseq = cnt1.getInt(reprcParams.size()+1);
            System.out.println(lseq);
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
            }
        }
        long end = new Date().getTime();
    }
    
    public int execUpd(String info, String sql, ArrayList params) {
       
        int ct = 0;
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                String val = (String)params.get(i);
                try {
                    int numVal = Integer.parseInt(val);
                    pst.setInt(i + 1, numVal);
                } catch (NumberFormatException e) {
                    try {
                        double numVal = Double.parseDouble(val);
                        pst.setDouble(i + 1, numVal);
                    } catch (Exception ee) {
                        pst.setString(i + 1, val);
                    }
                }
            }
            ct = pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
        }
        return ct;
    }
    
    public CallableStatement execCall(String info, String sql, ArrayList params, ArrayList out,Connection con) {
       
        int ct = -1 ;
        CallableStatement cst = null ;
        ResultSet rs = null;
        try {
            cst = con.prepareCall("{ call "+ sql + "}");
            for (int i = 0; i < params.size(); i++) {
                String val = (String)params.get(i);
                
                    //logger.log(Level.FINER, (i + 1) + " : " + val);
                    cst.setString(i + 1, val);
                
            }
            for(int i=0; i < out.size(); i++) {
                String val = (String)out.get(i);
                if(val.equals("I"))
                    cst.registerOutParameter((params.size())+i+1, Types.INTEGER);
                if(val.equals("V"))
                    cst.registerOutParameter((params.size())+i+1, Types.VARCHAR);
              if(val.equals("N"))
                  cst.registerOutParameter((params.size())+i+1, Types.NUMERIC);
              if(val.equals("D"))
                  cst.registerOutParameter((params.size())+i+1, Types.DOUBLE);
            }
            cst.execute();
            //return cst;
            //ct =cst.executeUpdate(); //cst.execute();
            //rs = cst.getResultSet();
            //cst.close();
        } catch (SQLException e) {
        }
        return cst ;
    }
    
    public Connection getConnectionCPDF(String client) throws Exception {
        String driver = "oracle.jdbc.driver.OracleDriver";
          client=client.trim();
          Properties  prop = new Properties();
          String applId = null
              , dbHost = null
              , dbPort = null
              , dbSID = null
              , dbUsr = null
              , dbPwd = null;
          String url="";
              prop.load(new FileInputStream("c:/shedule/connection.properties"));
              if (prop.containsKey("applId"+client))
                  applId = prop.getProperty("applId"+client, "0");
             
              url = "jdbc:oracle:thin:@"+prop.get("HostName"+client)+":"
                      + prop.get("Port"+client) +":"+ prop.get("SID"+client);
          Class.forName(driver);
        return DriverManager.getConnection(url, (String)prop.get("UserName"+client), (String)prop.get("Password"+client));
    } 

    public void SOP(String s) {
        System.out.println(s);
    }
}
