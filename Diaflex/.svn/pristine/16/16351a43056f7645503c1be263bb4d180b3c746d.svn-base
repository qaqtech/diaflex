package ft.com;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.naming.NamingException;

import javax.sql.DataSource;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
public class DBMgr {
   
    Connection con = null;
    Properties prop = null;
    private LogMgr logger = null;
    String applId = null, dbHost = null, dbPort = null, dbSID = null, dbUsr =
        null, dbPwd = null ,dsName = null ,dsNameLeo=null ;
    String dbTyp = null, connectBy = null ;
    String logid="";
    HashMap batchStmt = new HashMap(); 
    ArrayList batchKeys = new ArrayList() ;
    String logApplNm="" ;
    int sleep=20;
  private long openCursorsLimit =1000;
        
    public DBMgr() {
        logger=new LogMgr();
        prop = new Properties();
        try {           
//           prop.load(new FileInputStream("/home/dev1/Documents/Applications/config/accountnm.properties"));
            prop.load(this.getClass().getClassLoader().getResourceAsStream("accountnm.properties"));
            logger.setErrLvl(Integer.parseInt(String.valueOf(prop.get("ERROR_LEVEL"))));
        } catch (IOException e) {
            e.printStackTrace();
            new ExceptionMail("","",e.getMessage(),getLogApplNm(),getLogid()).start();
        }
    }
    public DBMgr(String applId) {
        super();
        this.applId = applId;
    }
    public void close() {
        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
        }
    }
    
    public void setAutoCommit(boolean status)  {
        try {
            con.setAutoCommit(status);
        } catch (SQLException e) {
        }
    }
    
    public void doCommit() {
        try {
            con.commit();
        } catch (SQLException e) {
        }
    }
    
  public void doRollBack() {
      try {
          con.rollback();
      } catch (SQLException e) {
      }
  }
    public Connection giveconn(){
        return con;
    }
    public boolean init(String db ,String typ) {
        if(logger == null)
            logger = new LogMgr();
        boolean valid=false; 
        if(!db.equals("") && !typ.equals("")){
        if(typ.equalsIgnoreCase("DS"))
            valid=getConnectionDS(db) ;
        else
            valid=getConnectionCP(db) ;
        }
        return valid;
    }
    public String getSchema(String dbName){
        prop = new Properties();
        String schema=null;
        
        try {
//            prop.load(new FileInputStream("/home/dev1/Documents/Applications/config/connectiondf.properties"));
            prop.load(this.getClass().getClassLoader().getResourceAsStream("connectiondf.properties"));
            schema=(String)prop.get(dbName+"UserName");
        } catch (FileNotFoundException fnfe) {
            // TODO: Add catch code
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
        }
            
          return   schema;
        
    }
    private Boolean getConnectionDS(String dbName) {
        Connection con = null;
        try {
            if (con == null) {
                Context ctx;
                ctx = new InitialContext();
                if (ctx != null) {
                    DataSource ds =
                        (DataSource)ctx.lookup("java:comp/env/jdbc/" + dbName);
                    con =ds.getConnection();
                    if(con!=null)
                   // setProxy(usr);
                    logger.log(Level.INFO,
                               " Connection established " + dbName);
                  
                }
                //logger.log(Level.FINEST, dbUsr + "/" + dbPwd);
            }
        } catch (SQLException sqle) {
            // TODO: Add catch code
            logger.log(3,Level.SEVERE, "Connection Failed");
            logger.log(3,Level.SEVERE, sqle.toString());
            new ExceptionMail("","","Connection Failed_"+dbName,getLogApplNm(),getLogid()).start();
        } catch (NamingException ne) {
            // TODO: Add catch code
            logger.log(3,Level.SEVERE, dsName + " context not found");
            logger.log(3,Level.SEVERE, ne.toString());
            new ExceptionMail("",""," context not found "+dbName,getLogApplNm(),getLogid()).start();
            
        }
        this.con = con ;
        if(this.con != null){
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }


    private Boolean getConnectionCP(String dbName) {
        Connection con = null;
        prop = new Properties();
        try {           
//           prop.load(new FileInputStream("/home/dev1/Documents/Applications/config/connectiondf.properties"));
            prop.load(this.getClass().getClassLoader().getResourceAsStream("connectiondf.properties"));
            String sid=(String)prop.get(dbName+"SID");
            if(sid.indexOf("/")  > -1)
            sid=sid;
            else
            sid=":"+sid;
            String url =
                "jdbc:oracle:thin:@" + prop.get(dbName+"HostName") + ":" + prop.get(dbName+"Port") +sid;
            System.out.println("MB"+dbName+"MB");
            System.out.println(url);
            String driver = "oracle.jdbc.driver.OracleDriver";
            Class.forName(driver);
            con = DriverManager.getConnection(url, (String)prop.get(dbName+"UserName"),
                            (String)prop.get(dbName+"Password"));

        } catch (IOException e) {
            e.printStackTrace();
            new ExceptionMail("","",e.getMessage(),getLogApplNm(),getLogid()).start();
        } catch (SQLException e) {
            e.printStackTrace();
            new ExceptionMail("","",e.getMessage(),getLogApplNm(),getLogid()).start();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.con = con ;
        if(this.con != null)
            return Boolean.TRUE;
        else
            return Boolean.FALSE;
    } 
    
    public void initWebXml() {
        try {
                              logger = new LogMgr();
                             
                              String url = "jdbc:oracle:thin:@"+dbHost+":"
                                      + dbPort +":"+ dbSID;
                              
                              if(con == null) {
                                  con = DriverManager.getConnection(url, dbUsr, dbPwd);
                                  logger.log(1,Level.INFO, " Connection established "+ url + new Date());
                              }
                             
                              
                          } catch (SQLException e) {
                             
                          }
     }
    public void init() {
        //discarded on 14Aug2014 in the new implementation
        //init(info.getDbTyp(), info.getConnectBy());
    }
    
    public String getStatus() {
        String status = "Alive";
        if(con == null)
            status = "Not Connected";
        return status;
    }
    
  public void chkConn() {
          String query = "select a.value, b.name from v$mystat a, v$statname b where a.statistic# = b.statistic# and b.name = 'opened cursors current'";
          ResultSet rs = execSql(" Cursors ", query, new ArrayList()) ;
          try {
              if(rs.next()) {
                  long openCursors = rs.getLong(1);
                  logger.log(1," Open Cursors "+openCursors);
                  /*
                  if(openCursors > openCursorsLimit) {
                      con.close();
                      con = null;
                      init();
                  } 
*/
              }
              rs.close();
          } catch (SQLException e) {
          }
    }
  public String validateAccountNm(String accountnm){
      String dbtyp="FAIL";
      prop = new Properties();
      try {
//     prop.load(new FileInputStream("/home/dev1/Documents/Applications/config/accountnm.properties"));
      prop.load(this.getClass().getClassLoader().getResourceAsStream("accountnm.properties"));
      if (prop.containsKey(accountnm))
      dbtyp = prop.getProperty(accountnm, "FAIL");
      } catch (IOException e) {
      }
      return dbtyp;
  }
    public void initDesktop(String desktop) {
        prop = new Properties();
        logger = new LogMgr();
        try {
//            prop.load(new FileInputStream("/home/dev1/Documents/Applications/config/connection.properties"));
            prop.load(this.getClass().getClassLoader().getResourceAsStream("connection.properties"));
            //logger.log(Level.INFO, "Prop Initialized");
            if (prop.containsKey("applId"))
                applId = prop.getProperty("applId", "0");
            //logger.log(Level.INFO, " Appl Id " + applId);
            String url =
                "jdbc:oracle:thin:@" + prop.get("HostName") + ":" + prop.get("Port") +
                ":" + prop.get("SID");
            con = DriverManager.getConnection(url, (String)prop.get("UserName"),
                            (String)prop.get("Password"));

            logger.log(1,Level.INFO, " Connection established " + url);
            //logger.log(Level.INFO, (String)prop.get("UserName") + "/" + (String)prop.get("Password"));

        } catch (IOException e) {
            logger.log(3,Level.INFO, "Appl Id : " + applId);
            logger.log(3,"Connection IO Error");
        } catch (SQLException e) {
            logger.log(3,Level.SEVERE, "Connection Error : " + e);
        }
      
    }
//    public void setProxy(String usr) {
//    java.util.Properties prop = new java.util.Properties();
//    prop.put(OracleConnection.PROXY_USER_NAME, usr);
//    //String[] roles = {"JBROS_ROLE"};
//    //prop.put(OracleConnection.PROXY_ROLES, roles);
//    try {
//    con.openProxySession(OracleConnection.PROXYTYPE_USER_NAME, prop);
//    } catch (SQLException e) {
//    logger.log(" error@proxy : "+e);
//    }
//    }
    public CallableStatement execCallNoGt(String info, String sql, ArrayList params, ArrayList out) {
        int ct = -1 ;
        CallableStatement cst = null ;
        ResultSet rs = null;
        Date start, end ;
        Date prcStart, prcEnd ;
        start = new Date();
        try {
            if(con == null){
                init();
                try{
                updAccessLogReconnect();
                }catch (Exception sqle) {
                sqle.printStackTrace();
                }
            }
            cst = con.prepareCall("{ call "+ sql + "}");
            for(int i=0; i < params.size(); i++) {
                String val = (String)params.get(i);
                try {
                    int numVal = Integer.parseInt(val);
                    //logger.log(Level.FINEST, (i+1) + " : " + numVal);
                    cst.setInt(i+1, numVal);
                    
                } catch (NumberFormatException e) {
                    ct = i;
                    //logger.log(Level.FINER, (i+1) + " : " + val);
                    cst.setString(i+1, val);
                    //logger.log(Level.FINER, " Date " + " : " + val);
                }
            }
            for(int i=0; i < out.size(); i++) {
                String val = (String)out.get(i);
                if(val.equals("I"))
                    cst.registerOutParameter((params.size())+i+1, Types.INTEGER);
                if(val.equals("V"))
                    cst.registerOutParameter((params.size())+i+1, Types.VARCHAR);
                if(val.equals("IA"))
                    cst.registerOutParameter((params.size())+i+1, OracleTypes.ARRAY, "DBMS_SQL.NUMBER_TABLE");
                if(val.equals("VA"))
                    cst.registerOutParameter((params.size())+i+1, OracleTypes.ARRAY, "DBMS_SQL.VARCHAR2_TABLE");
                if(val.equals("SD"))
                    cst.registerOutParameter((params.size())+i+1, OracleTypes.ARRAY, "JDBC_STK_DTL_PRP_TYP_TBL");
                
            }
            prcStart = new Date();
            cst.execute();
            prcEnd = new Date();
            timeDiff(" ExecCall", prcStart, prcEnd);
            //return cst;
            //ct =cst.executeUpdate(); //cst.execute();
            //rs = cst.getResultSet();
            //cst.close();
        } catch (SQLException e) {
            logger.log(3,Level.SEVERE, "@init "+e);
        }catch (Exception sqle) {
                sqle.printStackTrace();
        }
        end  = new Date();
        timeDiff(" Overall ExecCall", start, end);
        return cst ;
    }
    public ResultSet execSql(String info, String sql) {
        ResultSet rs = null;
        Statement st = null;

        try {
            if(con == null){
                init();
                try{
                updAccessLogReconnect();
                }catch (Exception sqle) {
                sqle.printStackTrace();
                }
            }
            st = con.createStatement();
            //logger.log(Level.INFO, info);
            //logger.log(Level.FINEST, sql);
            rs = st.executeQuery(sql);
            if (st != null)
                st.close();
        } catch (SQLException e) {
           logger.log(3,Level.SEVERE, " Log Id "+getLogid() +"@"+info + "@" + e.getMessage());
//            logger.log(Level.SEVERE, sql);
           String stkmsg=e.getMessage();
           new ExceptionMail(sql,"",stkmsg,getLogApplNm(),getLogid()).start();
            if(stkmsg.indexOf("ORA-01031")  > -1 || stkmsg.indexOf("ORA-00942")  > -1){
                try {
                Thread.sleep(1000*sleep);
                execSql(info,sql);
                } catch (InterruptedException z) {
                z.printStackTrace();
                }
            }
        }
        return rs;
    }

    public ResultSet execSql(String info, String sql, ArrayList params) {
        ResultSet rs = null;
        PreparedStatement pst = null;
        try {
            if(con == null){
                init();
                try{
                updAccessLogReconnect();
                }catch (Exception sqle) {
                sqle.printStackTrace();
                }
            }
            pst = con.prepareCall(sql);
            //logger.log(Level.INFO, info);
            logger.log(1,Level.FINEST, sql);
            logger.log(2,Level.INFO, " Log Id "+getLogid() +"@"+info + "@" + sql + " | params = "+ params.toString());
             for (int i = 0; i < params.size(); i++) {
                String val = (String)params.get(i);
                try {
                    int numVal = Integer.parseInt(val);
                    //logger.log(Level.FINEST, (i + 1) + " : " + numVal);
                    pst.setInt(i + 1, numVal);
                } catch (NumberFormatException e) {
                    try {
                        double numVal = Double.parseDouble(val);
                        //logger.log(Level.FINEST, (i + 1) + " : " + numVal);
                        pst.setDouble(i + 1, numVal);
                    } catch (Exception ee) {
                        //logger.log(Level.FINER, (i + 1) + " : " + val);
                        pst.setString(i + 1, val);
                    }
                }
            }
            rs = pst.executeQuery();
            /*
            if(pst != null)
                pst.close();
            */
        } catch (SQLException e) {
            logger.log(3,Level.SEVERE, " Log Id "+getLogid() +"@"+info + "@" + e.getMessage());
            logger.log(3,Level.SEVERE, sql);
            logger.log(3,Level.SEVERE, params.toString());
            String stkmsg=e.getMessage();
            new ExceptionMail(sql,params.toString(),stkmsg,getLogApplNm(),getLogid()).start();
             if(stkmsg.indexOf("ORA-01031")  > -1 || stkmsg.indexOf("ORA-00942")  > -1){
                 try {
                 Thread.sleep(1000*sleep);
                 execSql(info, sql, params);
                 } catch (InterruptedException z) {
                 z.printStackTrace();
                 }
             }
        }
        return rs;
    }
    
    public ResultSet execDirSql(String info, String sql, ArrayList params) {
        ResultSet rs = null;
        PreparedStatement pst = null;
        try {
            if(con == null){
                init();
                try{
                updAccessLogReconnect();
                }catch (Exception sqle) {
                sqle.printStackTrace();
                }
            }
            pst = con.prepareCall(sql);
            //logger.log(Level.INFO, info);
            //logger.log(Level.FINEST, sql);
            logger.log(2,Level.INFO, " Log Id "+getLogid() +"@"+info + "@" + sql + " | params = "+ params.toString());
            for(int i=0; i < params.size(); i++) {
                     String val = (String)params.get(i);
                     pst.setString(i+1, val);
                     logger.log(1,Level.FINER, (i + 1) + " : " + val);
             }
            rs = pst.executeQuery();
            /*
            if(pst != null)
                pst.close();
            */
        } catch (SQLException e) {
            logger.log(3,Level.SEVERE, " Log Id "+getLogid() +"@"+info + "@" + e.getMessage());
//            logger.log(Level.SEVERE, sql);
           logger.log(3,Level.SEVERE, params.toString());
            String stkmsg=e.getMessage();
            new ExceptionMail(sql,params.toString(),stkmsg,getLogApplNm(),getLogid()).start();
             if(stkmsg.indexOf("ORA-01031")  > -1 || stkmsg.indexOf("ORA-00942")  > -1){
                 try {
                 Thread.sleep(1000*sleep);
                 execDirSql(info, sql, params);
                 } catch (InterruptedException z) {
                 z.printStackTrace();
                 }
             }
        }
        return rs;
    }
    public ArrayList execSqlLst(String info, String sql, ArrayList params) {
           ArrayList lst = new ArrayList();
           ResultSet rs = null;
           PreparedStatement pst = null;
           try {
               if(con == null){
                   init();
                   try{
                   updAccessLogReconnect();
                   }catch (Exception sqle) {
                   sqle.printStackTrace();
                   }
               }
               pst = con.prepareCall(sql);
               logger.log(1,Level.INFO, info);
               logger.log(1,Level.FINEST, sql);
               logger.log(2,Level.INFO, " Log Id "+getLogid() +"@"+info + "@" + sql + " | params = "+ params.toString());
                for (int i = 0; i < params.size(); i++) {
                   String val = (String)params.get(i);
                   try {
                       int numVal = Integer.parseInt(val);
                       logger.log(1,Level.FINEST, (i + 1) + " : " + numVal);
                       pst.setInt(i + 1, numVal);
                   } catch (NumberFormatException e) {
                       try {
                           double numVal = Double.parseDouble(val);
                           logger.log(1,Level.FINEST, (i + 1) + " : " + numVal);
                           pst.setDouble(i + 1, numVal);
                       } catch (Exception ee) {
                           logger.log(3,Level.FINER, (i + 1) + " : " + val);
                           pst.setString(i + 1, val);
                       }
                   }
               }
               rs = pst.executeQuery();
               /*
               if(pst != null)
                   pst.close();
               */
           } catch (SQLException e) {
               logger.log(3,Level.SEVERE, " Log Id "+getLogid() +"@"+info + "@" + e.getMessage());
             logger.log(3,Level.SEVERE, sql);
               logger.log(3,Level.SEVERE, params.toString());
               String stkmsg=e.getMessage();
               new ExceptionMail(sql,params.toString(),stkmsg,getLogApplNm(),getLogid()).start();
                if(stkmsg.indexOf("ORA-01031")  > -1 || stkmsg.indexOf("ORA-00942")  > -1){
                    try {
                    Thread.sleep(1000*sleep);
                    execSqlLst(info, sql, params);
                    } catch (InterruptedException z) {
                    z.printStackTrace();
                    }
                }
           }
           lst.add(pst);
           lst.add(rs);
           return lst;
       }
    public CallableStatement execCall(String info, String sql, ArrayList params, ArrayList out) {
       
        int ct = -1 ;
        CallableStatement cst = null ;
        ResultSet rs = null;
        try {
            if(con == null){
                init();
                try{
                updAccessLogReconnect();
                }catch (Exception sqle) {
                sqle.printStackTrace();
                }
            }
            cst = con.prepareCall("{ call "+ sql + "}");
            logger.log(1,Level.INFO, info+" Log Id "+getLogid());
            logger.log(1,Level.FINEST, sql);
            logger.log(2,Level.INFO, " Log Id "+getLogid() +"@"+info + "@" + sql + " | params = "+ params.toString());
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
           // logger.log(Level.FINER, " Date " + " : " + ct);
            logger.log(3,Level.SEVERE, info+"@"+e.getMessage());
            String stkmsg=e.getMessage();
            new ExceptionMail(sql,params.toString(),stkmsg,getLogApplNm(),getLogid()).start();
             if(stkmsg.indexOf("ORA-01031")  > -1 || stkmsg.indexOf("ORA-00942")  > -1){
                 try {
                 Thread.sleep(1000*sleep);
                 execCall(info, sql, params, out);
                 } catch (InterruptedException z) {
                 z.printStackTrace();
                 }
             }
        }
        return cst ;
    }
    

    public int execUpd(String info, String sql) {
       
        int ct = 0;
        Statement st = null;
        try {
            if(con == null){
                init();
                try{
                updAccessLogReconnect();
                }catch (Exception sqle) {
                sqle.printStackTrace();
                }
            }
            st = con.createStatement();
            //logger.log(Level.INFO, info);
            //logger.log(Level.FINEST, sql);
            ct = st.executeUpdate(sql);
            st.close();
        } catch (SQLException e) {
            logger.log(3,Level.SEVERE, " Log Id "+getLogid() +"@"+info + "@" + e.getMessage());
            String stkmsg=e.getMessage();
            new ExceptionMail(sql,"",stkmsg,getLogApplNm(),getLogid()).start();
             if(stkmsg.indexOf("ORA-01031")  > -1 || stkmsg.indexOf("ORA-00942")  > -1){
                 try {
                 Thread.sleep(1000*sleep);
                 execUpd(info, sql);
                 } catch (InterruptedException z) {
                 z.printStackTrace();
                 }
             }
        }
        return ct;
    }

    public int execUpd(String info, String sql, ArrayList params) {
       
        int ct = 0;
        PreparedStatement pst = null;
        try {
            if(con == null){
                init();
                try{
                updAccessLogReconnect();
                }catch (Exception sqle) {
                sqle.printStackTrace();
                }
            }
            pst = con.prepareStatement(sql);
           // logger.log(1,Level.INFO, info+" Log Id "+getLogid());
          //  logger.log(2,Level.INFO, " Log Id "+getLogid() +"@"+info + "@" + sql + " | params = "+ params.toString());
            for (int i = 0; i < params.size(); i++) {
                String val = (String)params.get(i);
                try {
                    int numVal = Integer.parseInt(val);
                 //   logger.log(1,Level.FINEST, (i + 1) + " : " + numVal);
                    pst.setInt(i + 1, numVal);
                } catch (NumberFormatException e) {
                    try {
                        double numVal = Double.parseDouble(val);
                    //    logger.log(1,Level.FINEST, (i + 1) + " : " + numVal);
                        pst.setDouble(i + 1, numVal);
                    } catch (Exception ee) {
                     //  logger.log(3,Level.FINER, (i + 1) + " : " + val);
                        pst.setString(i + 1, val);
                    }
                }
            }
            ct = pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
          //  logger.log(3,Level.SEVERE, " Log Id "+getLogid() +"@"+info + "@" + e.getMessage());
           // logger.log(3,Level.SEVERE, params.toString());
         //   logger.log(3,Level.SEVERE, sql);
            String stkmsg=e.getMessage();
            new ExceptionMail(sql,params.toString(),stkmsg,getLogApplNm(),getLogid()).start();
             if(stkmsg.indexOf("ORA-01031")  > -1 || stkmsg.indexOf("ORA-00942")  > -1){
                 try {
                 Thread.sleep(1000*sleep);
                 execUpd(info, sql, params);
                 } catch (InterruptedException z) {
                 z.printStackTrace();
                 }
             }
        }
        return ct;
    }

  public int execDirUpd(String info, String sql, ArrayList params) {
          int ct = 0 ;
          PreparedStatement pst = null;
          try {
              if(con == null){
                  init();
                  try{
                  updAccessLogReconnect();
                  }catch (Exception sqle) {
                  sqle.printStackTrace();
                  }
              }
              pst = con.prepareStatement(sql);
              logger.log(1,Level.INFO, info);
              logger.log(2,Level.INFO, " Log Id "+getLogid() +"@"+info + "@" + sql + " | params = "+ params.toString());
              for(int i=0; i < params.size(); i++) {
                  String val = (String)params.get(i);
                  pst.setString(i+1, val);
               logger.log(1,Level.FINER, (i + 1) + " : " + val);
              }
              
              ct = pst.executeUpdate();
             
              pst.close();
          } catch (SQLException e) {
              //e.printStackTrace();
              logger.log(3,Level.FINEST, " Log Id "+getLogid() +"@"+info+"@"+sql+" = "+ params.toString());
              
                  logger.log(3,Level.SEVERE, info + "@" + e.getMessage());
                  ct = -1;
              String stkmsg=e.getMessage();
              new ExceptionMail(sql,params.toString(),stkmsg,getLogApplNm(),getLogid()).start();
               if(stkmsg.indexOf("ORA-01031")  > -1 || stkmsg.indexOf("ORA-00942")  > -1){
                   try {
                   Thread.sleep(1000*sleep);
                  execDirUpd(info, sql, params);
                   } catch (InterruptedException z) {
                   z.printStackTrace();
                   }
               }
            
          }finally {
            
          }
      
          return ct ;
      }
      
    public int execUpd(String info, ArrayList sql) {
       
        int ct = 0;
        Statement st = null;
        try {
            if(con == null){
                init();
                try{
                updAccessLogReconnect();
                }catch (Exception sqle) {
                sqle.printStackTrace();
                }
            }
            st = con.createStatement();
            //logger.log(Level.INFO, info);
            for (int i = 0; i < sql.size(); i++) {
                st.addBatch((String)sql.get(i));
            }
            st.executeBatch();
            st.close();
            ct = 1;
        } catch (SQLException e) {
            logger.log(3,Level.SEVERE, info + "@" + e.getMessage());
         //   logger.log(Level.SEVERE, sql.toString());
        }
        return ct;
    }

  public void execBatchUpd(String key, String info, String sql, ArrayList params) {
          execBatchUpd(key, info, sql, params, "NRM");
      }
      public void execBatchUpd(String key, String info, String sql, ArrayList params, String typ) {
          
              try {
                  PreparedStatement batchSt = null;
                  batchSt = (PreparedStatement)batchStmt.get(key);
                  if (batchSt == null) {
                      batchSt = con.prepareStatement(sql);
                      batchStmt.put(key, batchSt);
                      batchKeys.add(key);
                  }
                  for(int i=0; i < params.size(); i++) {
                      if(typ.equals("DIR"))
                          try{
                          String val = nvl((String)params.get(i),"");
                          batchSt.setString(i+1, val);
                          }catch (Exception ee) {
                          batchSt.setString(i+1, "");
                          }
                      else {
                          String val = nvl((String)params.get(i),"");
                          try {
                              int numVal = Integer.parseInt(val);
                              //logger.log(Level.FINEST, (i+1) + " : " + numVal);
                              batchSt.setInt(i+1, numVal);
                          } catch (NumberFormatException e) {
                              try {
                                  double numVal = Double.parseDouble(val);
                                  //logger.log(Level.FINEST, (i+1) + " : " + numVal);
                                  batchSt.setDouble(i+1, numVal);
                              } catch (Exception ee) {
                                  //logger.log(Level.FINER, (i+1) + " : " + val);
                                  batchSt.setString(i+1, val);
                              }    
                          }
                      }    
                  }
                  batchSt.addBatch();

                      
              } catch (SQLException e) {
                  logger.log(3,Level.SEVERE, "@execBatch : " + info + " | " + e.toString());
                  String stkmsg=e.getMessage();
                  new ExceptionMail(sql,params.toString(),stkmsg,getLogApplNm(),getLogid()).start();
                   if(stkmsg.indexOf("ORA-01031")  > -1 || stkmsg.indexOf("ORA-00942")  > -1){
                       try {
                       Thread.sleep(1000*sleep);
                       execBatchUpd(key, info, sql, params, typ);
                       } catch (InterruptedException z) {
                       z.printStackTrace();
                       }
                   }
              }

      }
      public void execBatch() {
          int ct[];
          try {
              for(int i=0; i < batchKeys.size(); i++) {
                  String key = (String)batchKeys.get(i);
                  logger.log(1," Batch : " + key);
                  PreparedStatement batchSt = (PreparedStatement)batchStmt.get(key);
                  ct = batchSt.executeBatch();
                  logger.log(1," Batch : " + key + " executed "+ ct.length);
                  con.commit();
                  
              }    
          } catch (SQLException e) {
              logger.log(3,Level.SEVERE, "@execBatch : " + e.toString());
              String stkmsg=e.getMessage();
              new ExceptionMail("execBatch","",stkmsg,getLogApplNm(),getLogid()).start();
               if(stkmsg.indexOf("ORA-01031")  > -1 || stkmsg.indexOf("ORA-00942")  > -1){
                   try {
                   Thread.sleep(1000*sleep);
                       execBatch();
                   } catch (InterruptedException z) {
                   z.printStackTrace();
                   }
               }
          }
          batchStmt = new HashMap();
      }
      
      
    public String nvl(String pVal) {
        return nvl(pVal, "");
    }
    
    public String nvl(String pVal, String rVal) {
    if(pVal == null)
        pVal = rVal;
    else if(pVal.equals(""))
      pVal = rVal;
    return pVal;
    //        String val = pVal ;
    //        if(pVal == null)
    //            val = rVal;
    //        else if(pVal.equals(""))
    //          val = rVal;
    //        return val;

    }
    public int execCall(String info, String sql, ArrayList params) {
       
        int ct = -1;
        CallableStatement cst = null;
        try {
            if(con == null){
                init();
                try{
                updAccessLogReconnect();
                }catch (Exception sqle) {
                sqle.printStackTrace();
                }
            }
            cst = con.prepareCall("{ call " + sql + "}");
            logger.log(1,Level.INFO, info);
            logger.log(2,Level.INFO, " Log Id "+getLogid() +"@"+info + "@" + sql + " | params = "+ params.toString());
            for (int i = 0; i < params.size(); i++) {
                String val = (String)params.get(i);
                try {
                    int numVal = Integer.parseInt(val);
                    logger.log(1,Level.FINEST, (i + 1) + " : " + numVal);
                    cst.setInt(i + 1, numVal);
                } catch (NumberFormatException e) {
                    logger.log(3,Level.FINER, (i + 1) + " : " + val);
                    cst.setString(i + 1, val);
                }
            }
            ct = cst.executeUpdate();
            cst.close();

        } catch (SQLException e) {
            logger.log(3,Level.SEVERE, " Log Id "+getLogid() +"@"+info + "@" + e.getMessage());
           logger.log(3,Level.SEVERE, sql);
            String stkmsg=e.getMessage();
            new ExceptionMail(sql,params.toString(),stkmsg,getLogApplNm(),getLogid()).start();
             if(stkmsg.indexOf("ORA-01031")  > -1 || stkmsg.indexOf("ORA-00942")  > -1){
                 try {
                 Thread.sleep(1000*sleep);
                 execCall(info, sql, params);
                 } catch (InterruptedException z) {
                 z.printStackTrace();
                 }
             }
        }finally {
         cst=null;
         }
        return ct;
    }
   
    public int execCallDir(String info, String sql, ArrayList params) {
       
        int ct = -1;
        CallableStatement cst = null;
        try {
            if(con == null){
                init();
                try{
                updAccessLogReconnect();
                }catch (Exception sqle) {
                sqle.printStackTrace();
                }
            }
            cst = con.prepareCall("{ call " + sql + "}");
            logger.log(Level.INFO, info);
            logger.log(Level.FINEST, sql);
            for (int i = 0; i < params.size(); i++) {
                String val = (String)params.get(i);
                
                    logger.log(Level.FINER, (i + 1) + " : " + val);
                    cst.setString(i + 1, val);
                
            }
            ct = cst.executeUpdate();
          
        } catch (SQLException e) {
         
            logger.log(3,Level.SEVERE, " Log Id "+getLogid() +"@"+info + "@" + e.getMessage());
            String stkmsg=e.getMessage();
            new ExceptionMail(sql,params.toString(),stkmsg,getLogApplNm(),getLogid()).start();
             if(stkmsg.indexOf("ORA-01031")  > -1 || stkmsg.indexOf("ORA-00942")  > -1){
                 try {
                 Thread.sleep(1000*sleep);
                     execCallDir(info, sql, params);
                 } catch (InterruptedException z) {
                 z.printStackTrace();
                 }
             }
        }finally {
         cst=null;
         }
        return ct;
    }
    public void timeDiff(String msg, Date start, Date end) {
        long difference = (end.getTime() - start.getTime())/1000;

    }
    
    public int updAccessLogReconnect()throws Exception {
        /*
           ArrayList ary = new ArrayList();
           ArrayList out = new ArrayList();
           int access_logidn=0;
           ary.add(Integer.toString(info.getLogId()));
           ary.add("Reconnection");
           ary.add("With "+info.getConnectBy()+" by "+info.getDbTyp());
           out.add("I");
            CallableStatement cst = null;
            cst = execCall(
                "NME_SRCH_PKG ",
                "DP_INS_ACCESS_LOG_IDN(pLogIdn => ? , pPg => ? , pPgItm => ? ,lIdn => ?)", ary, out);        
        access_logidn = cst.getInt(ary.size()+1);
        cst.close();
        return access_logidn;
        */
        return 0 ;
    }
    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public String getDbHost() {
        return dbHost;
    }

    public void setDbPort(String dbPort) {
        this.dbPort = dbPort;
    }

    public String getDbPort() {
        return dbPort;
    }

    public void setDbUsr(String dbUsr) {
        this.dbUsr = dbUsr;
    }

    public String getDbUsr() {
        return dbUsr;
    }

    public void setDbPwd(String dbPwd) {
        this.dbPwd = dbPwd;
    }

    public String getDbPwd() {
        return dbPwd;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }
 
    public String getApplId() {
        return applId;
    }

    public void setDbSID(String dbSID) {
        this.dbSID = dbSID;
    }

    public String getDbSID() {
        return dbSID;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

    public String getDsName() {
        return dsName;
    }

    public void setLogid(String logid) {
        this.logid = logid;
    }

    public String getLogid() {
        return logid;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }

    public void setLogger(LogMgr logger) {
        this.logger = logger;
    }

    public LogMgr getLogger() {
        return logger;
    }

    public void setLogApplNm(String logApplNm) {
        this.logApplNm = logApplNm;
        logger.setApplNm(logApplNm);
    }

    public String getLogApplNm() {
        return logApplNm;
    }
}
