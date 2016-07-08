/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tbf.webapp;

/**
 *
 * @author Public
 */

import java.sql.*;

public class TBFDataConnect {
   static
   {
      try
      {
         System.out.println("test Java Stored Procedure via call_Add_customer");
         Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
      }
      catch (Exception e)
      {
         System.out.println("\nError loading DB2 Driver...\n");
         e.printStackTrace();
      }
   } // static
   
 public Connection getConnection()
 {
 Connection con = null;
 String url;
// String server = "ec2-54-213-244-28.us-west-2.compute.amazonaws.com";
 String server = "localhost";
 String database = "SAMPLE";
 String port = "50000";
// String userid = "db2inst1";
// String passwd = "mytbfgym2016";
 String userid = "akadian";
 String passwd = "ask";
 @SuppressWarnings("unused")
String procName = "";
 @SuppressWarnings("unused")
CallableStatement callStmt;
 try
 {
 ///////////////////////////////////////
 // connect
 ///////////////////////////////////////
 System.out.println("Trying to connect...");
 url = "jdbc:db2://"
 		+ "" + server
 		+ ":" + port + "/" 
 		+ "" + database;
 //url = "jdbc:db2os390sqlj:DBZ1";
 con = DriverManager.getConnection(url, userid, passwd);
 con.setAutoCommit(false);

      } // try
      catch (Exception e)
      {
 System.out.println ("catch Exception being executed"  );
         try { con.close(); } catch (Exception x) { }
         e.printStackTrace ();
      } // catch
return con;
   } // main
}
