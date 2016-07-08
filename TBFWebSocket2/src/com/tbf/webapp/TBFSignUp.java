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

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

public class TBFSignUp {
        public String TBFSignUpFunc(   
                            Connection con,
                            String uid,
                            String emailId, 
                            String mblNbr,
                            String fName,
                            String lName,
                            String passWd,
                            String addrLn1) throws SQLException {
        	JsonObject jsonTmp = null;
            String retParm = null;
            String returned_mark = null;
            String returned_mark_error_text = null;
            //con.setAutoCommit(false);

            try{
                /////////////////////////////////////////////////
                // prepare the call to the stored procedure
                /////////////////////////////////////////////////
                System.out.println("Preparing call...");
                String procName = "AKADIAN" + ".PROCEDURE5";
                String sql = "CALL " + procName + "(?,?,?,?,?,?,?,?,?)";
                CallableStatement callStmt = con.prepareCall(sql);

                ///////////////////////////////////////
                // register the output parameters
                ///////////////////////////////////////
                System.out.println("Preparing parms...");
//                callStmt.registerOutParameter (7, Types.INTEGER);   // return
                callStmt.registerOutParameter (8, Types.CHAR);   // return
                callStmt.registerOutParameter (9, Types.CHAR);   // return

                ///////////////////////////////////////
                // set input parameters
                ///////////////////////////////////////
                callStmt.setString (1, uid);   // Email Id
                callStmt.setString (2, emailId);   // Email Id
                callStmt.setString (3, mblNbr);   // Mobile Number
                callStmt.setString (4, fName);  // First Name
                callStmt.setString (5, lName);  // Last Name
                callStmt.setString (6, passWd);  // Password
                callStmt.setString (7, addrLn1); //Address Line 1

                ///////////////////////////////////////
                // call the stored procedure
                ///////////////////////////////////////
                System.out.println ("\nCall stored procedure named " + procName);
                callStmt.execute();

                ///////////////////////////////////////
                // retrieve output parameters
                ///////////////////////////////////////
               // int returned_cust_no = callStmt.getInt(4);
                 returned_mark = callStmt.getString(8);
                 returned_mark_error_text = callStmt.getString(9);

                ////////////////////////////////////////
                // report results and commit or rollback
                ////////////////////////////////////////
                if (returned_mark_error_text.trim().equals("OK"))
                {
               //  System.out.println ("returned_cust_no: " + returned_cust_no );
                  retParm = "OK";
                  jsonTmp = Json.createObjectBuilder()
        	    		  .add("retCode", 0)
        	    		  .add("retValue", retParm)
        	    		  .build();
                 con.commit();
                }
                else
                {
                 System.out.println ("returned_mark: " + returned_mark );
                 System.out.println ("returned_mark_error_text:"
                  + returned_mark_error_text.trim() );
                 con.rollback();
                }

                ///////////////////////////////////////
                // tidy up
                ///////////////////////////////////////
                callStmt.close ();
                con.close ();
                
            } // try
            catch (Exception e){
                System.out.println ("catch Exception being executed"  );
                System.out.println ("returned_mark: " + returned_mark );
                System.out.println ("returned_mark_error_text: " + returned_mark_error_text );
                try { 
                    con.close(); 
                } catch (Exception x) { }
                e.printStackTrace ();
            } // catch
            return jsonTmp.toString();
        }
}
