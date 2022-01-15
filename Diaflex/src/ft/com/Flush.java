package ft.com;

import java.io.FileInputStream;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Properties;


public class Flush extends Thread {
    Connection con = null;
    public Flush() {
        super();
    }
    public void run()
    {
    flush();
    }
    public void flush(){
        try {
        con = getConnectionCPDF("SYSTEM");  
            int ctv = execUpd("reprc", "alter system FLUSH SHARED_POOL", new ArrayList());
            } catch (Exception e) {
                // TODO: Add catch code
                e.printStackTrace();
            } finally {
                try {
                    con.close();
                } catch (SQLException e) {
                }
            }
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
//              prop.load(new FileInputStream("/home/dev1/Documents/Applications/config/connection.properties"));
                      prop.load(this.getClass().getClassLoader().getResourceAsStream("connection.properties"));
              if (prop.containsKey("applId"+client))
                  applId = prop.getProperty("applId"+client, "0");
             
              url = "jdbc:oracle:thin:@"+prop.get("HostName"+client)+":"
                      + prop.get("Port"+client) +":"+ prop.get("SID"+client);
          Class.forName(driver);
        return DriverManager.getConnection(url, (String)prop.get("UserName"+client), (String)prop.get("Password"+client));
    } 

}
