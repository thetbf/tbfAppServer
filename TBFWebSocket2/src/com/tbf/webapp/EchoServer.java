package com.tbf.webapp;

import java.io.IOException;
import java.io.StringReader;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonArray;


import javax.json.JsonReader;

import java.sql.SQLException;
import java.util.UUID;
 
/** 
 * @ServerEndpoint gives the relative name for the end point
 * This will be accessed via ws://localhost:32820/EchoChamber/echo
 * Where "localhost" is the address of the host,
 * "EchoChamber" is the name of the package
 * and "echo" is the address to access this class from the server
 */
@ServerEndpoint("/echo") 
public class EchoServer {
    /**
     * @OnOpen allows us to intercept the creation of a new session.
     * The session class allows us to send data to the user.
     * In the method onOpen, we'll let the user know that the handshake was 
     * successful.
     */
    String uuid = UUID.randomUUID().toString();
    String messageRet = "";
    //System.out.println("uuid = " + uuid);
    @OnOpen
    public void onOpen(Session session){
    	//System.out.println("Classpath: "+System.getProperty("java.class.path"));
        System.out.println(session.getId() + " has opened a connection: AMRESH KADIAN"); 
        try {
            session.getBasicRemote().sendText("Connection Established");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
 
    /**
     * When a user sends a message to the server, this method will intercept the message
     * and allow us to react to it. For now the message is read as a String.
     * @param message
     * @param session
     * @throws SQLException 
     */
    @OnMessage
    public void onMessage(String message, Session session) throws SQLException {
        TBFApp tbf = new TBFApp();
        messageRet = tbf.distFunc(message);
        try {
            session.getBasicRemote().sendText(messageRet);
//    		JsonReader jsonReader = Json.createReader(new StringReader(messageRet));
////    		JsonObject object = jsonReader.readObject();
//    		JsonArray JSarray = jsonReader.readArray();
//    		jsonReader.close();
//    		System.out.println("JSON Array: " + JSarray);
        } catch (IOException ex) {
        }
    }
 
    /**
     * The user closes the connection.
     * 
     * Note: you can't send messages to the client from this method
     * @param session
     */
    @OnClose
    public void onClose(Session session){
        System.out.println("Session " +session.getId()+" has ended");
//        return messageRet;
    }
}