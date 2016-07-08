package com.tbf.webapp;

//import java.io.StringReader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

//import javax.json.Json;
//import javax.json.JsonArray;
//import javax.json.JsonReader;

//import javax.json.JsonObject;

public class TBFRegister {
    public String TBFRegisterFunc(String data) {
        System.out.println("TBF Register running now...");
        JsonReader reader = Json.createReader(new StringReader(data));
        JsonObject body = reader.readObject();
        reader.close();
        String uid = body.getString("uid");
        String emailId = body.getString("email");
        String mblNbr = body.getString("mbl");
        String fName = body.getString("fname");
        String lName = body.getString("lname");
        String passWd = body.getString("passwd");
        String addrLn1 = body.getString("addr1");
        TBFDataConnect tbf = new TBFDataConnect();
		Connection tbfConn = tbf.getConnection();
        TBFSignUp reg = new TBFSignUp();
        String x = null;
		try {
			x = reg.TBFSignUpFunc(tbfConn, uid, emailId, mblNbr, 
					fName, lName, passWd, addrLn1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return x;
    }
	public TBFRegister() {
		return;
	}

}
