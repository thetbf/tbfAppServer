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

import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.*;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class TBFPayment {
        public String TBFPaymentFunc(String data) throws SQLException {
        	JsonObject jsonTmp = null;
            String retParm = null;
            String returned_mark = null;
            String returned_mark_error_text = null;
            //con.setAutoCommit(false);
            System.out.println("TBF Payment running now...");
            JsonReader reader = Json.createReader(new StringReader(data));
            JsonObject body = reader.readObject();
            reader.close();
            String uid = body.getString("uid");
            BigDecimal amt = new BigDecimal(body.getString("amt"));
            System.out.println("Amount::: " + amt);
            TBFDataConnect tbf = new TBFDataConnect();
    		Connection con = tbf.getConnection();

            try{
                /////////////////////////////////////////////////
                // prepare the call to the stored procedure
                /////////////////////////////////////////////////
                System.out.println("Preparing call...");
                String procName = "AKADIAN" + ".USER_PAYMENT";
                String sql = "CALL " + procName + "(?,?,?,?)";
                CallableStatement callStmt = con.prepareCall(sql);

                ///////////////////////////////////////
                // register the output parameters
                ///////////////////////////////////////
                System.out.println("Preparing parms...");
//                callStmt.registerOutParameter (7, Types.INTEGER);   // return
                callStmt.registerOutParameter (3, Types.CHAR);   // return
                callStmt.registerOutParameter (4, Types.CHAR);   // return

                ///////////////////////////////////////
                // set input parameters
                ///////////////////////////////////////
                callStmt.setString (1, uid);   // Email Id
                callStmt.setBigDecimal(2, amt);   // Payment Amount

                ///////////////////////////////////////
                // call the stored procedure
                ///////////////////////////////////////
                System.out.println ("\nCall stored procedure named " + procName);
                callStmt.execute();

                ///////////////////////////////////////
                // retrieve output parameters
                ///////////////////////////////////////
               // int returned_cust_no = callStmt.getInt(4);
                 returned_mark = callStmt.getString(3);
                 returned_mark_error_text = callStmt.getString(4);

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
