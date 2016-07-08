package com.tbf.webapp;

//import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;

//import javax.json.Json;
//import javax.json.JsonArray;
//import javax.json.JsonReader;

//import javax.json.JsonObject;

public class TBFRpt {
//    static{
//        TBFDataConnect tbf = new TBFDataConnect();
//		Connection tbfConn = tbf.getConnection();
//        
//    }
    public String TBFRptFunc() {
        System.out.println("TBF Report running now...");
//        Connection tbfConn = null;
        TBFDataConnect tbf = new TBFDataConnect();
		Connection tbfConn = tbf.getConnection();
        TBFUserReport signUp = new TBFUserReport();
        String x = null;
		try {
			x = signUp.TBFUserReportFunc(tbfConn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return x;
    }
	public TBFRpt() {
		return;
	}

}
