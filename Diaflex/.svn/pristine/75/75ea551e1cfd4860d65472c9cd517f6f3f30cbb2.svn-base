package ft.com;

import java.sql.Connection;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.Callable;

public class GetPktPriceGroup implements Callable<String> {
    String stkIdn ;
    String grpNme ;
    ArrayList grpSheets ;
    int pct = 0;
    int seq ;
    Connection con = null;
    String sqlDb  ;
    
    public GetPktPriceGroup(String stkIdn, String grpNme, ArrayList grpSheets,String sqlDb) {
        super();
        this.stkIdn = stkIdn;
        this.grpNme = grpNme;
        this.grpSheets = grpSheets;
        this.sqlDb = sqlDb;
    }

    public void setPct(int pct) {
        this.pct = pct;
    }

    public int getPct() {
        return pct;
    }
    
    public String getGrpNme() {
        return grpNme ;    
    }
    
    @Override
    public String call() {
        // TODO Implement this method
        String rtnVal = "NA";
        long start = new Date().getTime();
        try {
            con = getConnectionCPDF(sqlDb);
            //SOP("Checking for : "+ stkIdn + " and "+ grpNme);
            int pct = getGrpPct(stkIdn, grpNme, grpSheets);
            if(pct != 0) {
                rtnVal = grpNme + "=" + pct;
                SOP("@local :"+ rtnVal);
            }
            //setPct(pct);
            
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
//        System.out.println("@"+ grpNme + " Total Time in Seconds = "+ ((end - start)/1000));
        return rtnVal;
    }
    
    public int getGrpPct(String stkIdn, String grpNme, ArrayList grpSheets) {
        int pct = 0 ;
        
        String grpPctQ = "    With pri as (\n" + 
        "      select count(*), m.idn\n" + 
        "      --m.prmnme, nvl(pct, vlu) pct --sum(nvl(pct, vlu)) pct \n" + 
        "        from stk_dtl sd, mpri m, pri_dtl pd\n" + 
        "      where 1 = 1 \n" + 
        "        and sd.grp = 1 and sd.mstk_idn = ? \n" + 
        "        and m.idn = pd.mpri_idn and sd.mprp = pd.mprp\n" + 
        "        and m.stt = 'A' and pd.rec_stt = 1 \n" + 
        "        and m.pri_grp = ? and nvl(m.pct, m.vlu) <> 0\n" + 
        "        and sd.srt between pd.srt_fr and pd.srt_to\n" + 
        "      group by m.idn, m.dcnt\n" + 
        "      having count(*) = m.dcnt\n" + 
        "    ) \n" + 
        "    select sum(nvl(pct, vlu)) pct\n" + 
        "      from mpri m, pri p \n" + 
        "    where m.idn = p.idn";
        
        
        
        ArrayList<String> params = new ArrayList<String>();
        params.add(String.valueOf(stkIdn));
        params.add(grpNme);
        
        //SOP(grpPctQ);
            
        ArrayList outLst = execSqlLst("Price Sheets", grpPctQ, con, params);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);

        try {
            if (rs.next()) {
                pct = rs.getInt("pct");
            }
            //SOP(grpNme + ":" + pct);
            rs.close();
            pst.close();

            //HashMap grps = (HashMap) grpSheets.get(grpNme) == null? new HashMap() : (HashMap) grpSheets.get(grpNme);
            
            if((pct != 0) && (grpSheets.size() > 0)) {
                for(int i=0; i < grpSheets.size(); i++) {
                    grpPctQ = "    With pri as (\n" + 
                    "      select count(*), m.idn\n" + 
                    "      --m.prmnme, nvl(pct, vlu) pct --sum(nvl(pct, vlu)) pct \n" + 
                    "        from stk_dtl sd, mpri m, pri_dtl pd\n" + 
                    "      where 1 = 1 \n" + 
                    "        and sd.grp = 1 and sd.mstk_idn = ? \n" + 
                    "        and m.idn = pd.mpri_idn and sd.mprp = pd.mprp\n" + 
                    "        and m.stt = 'A' and pd.rec_stt = 1 \n" + 
                    "        and m.pri_grp = ? and nvl(m.pct, m.vlu) <> 0\n" + 
                    "        and sd.srt between pd.srt_fr and pd.srt_to\n" + 
                    "      group by m.idn, m.dcnt\n" + 
                    "      having count(*) = m.dcnt\n" + 
                    "    ) \n" + 
                    "    select sum(nvl(pct, vlu)) pct\n" + 
                    "      from mpri m, pri p \n" + 
                    "    where m.idn = p.idn";
                    
                    params = new ArrayList<String>();
                    params.add(String.valueOf(stkIdn));
                    params.add((String)grpSheets.get(i));
                    outLst = execSqlLst("Price Group Sheets", grpPctQ, con, params);
                    pst = (PreparedStatement)outLst.get(0);
                    rs = (ResultSet)outLst.get(1);
                    if(rs.next()) {
                        pct += rs.getInt("pct");    
                    }
                    //SOP("@SubGrp : " + grpNme + ":" + pct);
                    rs.close();
                    pst.close();
                }
            }
        } catch (SQLException e) {
            SOP("@"+grpNme+" : Error "+ e.toString());
        }
        return pct;
    }
    
    public ArrayList execSqlLst(String info, String sql,Connection con, ArrayList params) {
        ArrayList lst = new ArrayList();
        ResultSet rs = null;
        PreparedStatement pst = null;
        try {
            
            //showConnDetails(con);
            pst = con.prepareCall(sql);
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
    //            System.out.println("@"+info + " | Exec : "+ new Date());
            rs = pst.executeQuery();
            /*
            if(pst != null)
                pst.close();
            */
        } catch (SQLException e) {
                System.out.println("msg"+e);
         }
        lst.add(pst);
        lst.add(rs);
        return lst;
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
