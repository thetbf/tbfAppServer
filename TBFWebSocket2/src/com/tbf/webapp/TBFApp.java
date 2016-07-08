package com.tbf.webapp;

import java.io.StringReader;
//import java.sql.Connection;
import java.sql.SQLException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;


public class TBFApp {
	public String distFunc(String message) throws SQLException {
    	System.out.println("Incoming message: " + message);
        JsonReader reader = Json.createReader(new StringReader(message));
        JsonObject body = reader.readObject();
        reader.close();
        String reqId = body.getString("reqId");
    	System.out.println("Incoming REQUEST: " + reqId);
//    	System.out.println("Incoming REQUEST: " + body.getString("data"));
        String data = null;
		String res = null;
        switch (reqId.toLowerCase()) {
        case "signup":
//            TBFDataConnect tbf = new TBFDataConnect();
//    		data = body.getString("fname");
            TBFRegister tbfReg = new TBFRegister();
            res = tbfReg.TBFRegisterFunc(message);
            break;
        case "subscribe":
        	data = body.getString("fname");
        	System.out.println("TBFApp.signup: " + data);
            TBFRpt tbfSubscribe = new TBFRpt();
            res = tbfSubscribe.TBFRptFunc();
            break;
        case "report":
            TBFRpt tbfRpt = new TBFRpt();
            res = tbfRpt.TBFRptFunc();
            break;
        case "payment":
          TBFPayment tbfPmt = new TBFPayment();
          res = tbfPmt.TBFPaymentFunc(message);
          break;
        case "attendence":
        	TBFAttendence tbfAtt = new TBFAttendence();
          res = tbfAtt.MarkAttendence(message);
          break;
        default:
            break;
    }
		return res;
	}
	public TBFApp() {
		return;
	}
}
