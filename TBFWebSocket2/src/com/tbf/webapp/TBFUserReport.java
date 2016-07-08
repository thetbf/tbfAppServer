package com.tbf.webapp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
//import javax.json.JsonObjectBuilder;
//import javax.json.JsonBuilderFactory;
//import org.json.JSONObject;
//import java.sql.Types;
import javax.json.JsonArray;

	public class TBFUserReport {
	    public String TBFUserReportFunc(   
	            Connection con) throws SQLException {
	String jsonData = " ";
	JsonArrayBuilder responseArray = Json.createArrayBuilder();
	
	try{
	/////////////////////////////////////////////////
	// prepare the call to the stored procedure
	/////////////////////////////////////////////////
	System.out.println("Preparing call...");
	String procName = "AKADIAN" + ".REPORT03";
	String sql = "CALL " + procName;
	CallableStatement callStmt = con.prepareCall(sql);
	
	///////////////////////////////////////
	// register the output parameters
	///////////////////////////////////////
	System.out.println("Preparing parms...");
	
	///////////////////////////////////////
	// set input parameters
	///////////////////////////////////////
	
	///////////////////////////////////////
	// call the stored procedure
	///////////////////////////////////////
	callStmt.execute();
	
	///////////////////////////////////////
	// retrieve output parameters
	///////////////////////////////////////
	System.out.println ("\nResultset BEFORE: " + procName);
	ResultSet rs1 = callStmt.getResultSet();
	System.out.println ("\nResultset AFTER: " + procName);
	
	////////////////////////////////////////
	// report results and commit or rollback
	////////////////////////////////////////

    int r = 0;
    
	while (rs1.next())
	      {
//		  System.out.println ("\nINTO WHILE: " + procName);
	      String s1 = rs1.getString(1); 
	      String s2 = rs1.getString(2);
	      String s3 = rs1.getString(3);
	      String s4 = rs1.getString(4);
	      int pmtdays = rs1.getInt(5);
	      boolean s5 = true;
	      if (pmtdays > 0) {
	    	  s5 = true;
	      } else {
	    	  s5 = false;
	      }
//	      System.out.println(s1);
//	      s1 = s1.replaceAll("\\s","");
//	      System.out.println(s1);
//	      s2 = s2.replaceAll("\\s","");
//	      s3 = s3.replaceAll("\\s","");
//	      s4 = s4.replaceAll("\\s","");
////	      s5 = s5.replaceAll("\\s","");
	      s1 = s1.replaceAll(" ","");
	      s2 = s2.replaceAll(" ","");
	      s3 = s3.replaceAll(" ","");
	      String name = s1 + " " + s2;
	      JsonObject jsonTmp = Json.createObjectBuilder()
	    		  .add("uid", s3)
	    		  .add("name", name)
	    		  .add("email", "xyz@me.com")
	    		  .add("balance", "100.12")
	    		  .add("attendence", s4)
	    		  .add("selected", s5)
	    		  .build();
	      jsonData = jsonData + jsonTmp.toString() + ",";
	      r++;
	      
	      responseArray.add(jsonTmp);
	      }
		  callStmt.getMoreResults();
	
	///////////////////////////////////////
	// tidy up
	///////////////////////////////////////
//	callStmt.close ();
	
	} // try
	catch (Exception e){
		System.out.println ("catch Exception being executed"  );
//		System.out.println ("returned_mark: " + returned_mark );
		try { 
		    con.close(); 
		} catch (Exception x) {
			
		}
			e.printStackTrace ();
	} // catch
//	con.close ();
//	if (count > 0) {
		jsonData = "[" + jsonData + "]";
		jsonData = jsonData.replace(",]", " ]");
		System.out.println("\n"+jsonData);
//	}
	return responseArray.build().toString();
	} // public String TBFUserReportFunc
} // class TBFUserReport
