package com.chat;

import java.net.InetAddress;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Server class for the chat
 * 
 * @author gaetan
 */
public class ChatServer extends UnicastRemoteObject implements Chattable {

    static public final String SERVER = "SERVER";
    
    public ArrayList<Message> messages;
    public int messageCount;
    public ArrayList<String> participants;
    
    
    public ChatServer() throws java.rmi.RemoteException {
        
        messages = new ArrayList<Message>();
        messageCount = 0;
        participants = new ArrayList<String>();
        participants.add(SERVER);
        
    };
    
    
    public static void main(String args[]) {
        
        int port;
        String URL;
        
        try {
            //Integer I = new Integer(args[0]);
            //port  = I.intValue();
            port = 8080; // force port
        } catch (Exception e) {
            System.out.println("Please enter: server <port>");
            return;
        }
        
        try {
            
            Registry reg = LocateRegistry.createRegistry(port);
            
            Chattable server = new ChatServer();
            
            // TODO factorize binding name ChatServer
            URL = "//" + InetAddress.getLocalHost().getHostName() + ":" + port + "/chat_server";
            
            // bind server object in registry
            Naming.rebind(URL, server);
            System.out.println("ChatServer bound in registry.");
            
            
        } catch(Exception e) {
            // TODO process e
            e.printStackTrace();
        }
    }
    
    
    /**
     * Connects the client to the server with id given as parameter
     * 
     * @param id the client id
     */
    @Override
    public String connect(String id) {
        
        String msg;
        
        if (participants.contains(id)) {
            msg = id + " is already connected.";
        } else {
            participants.add(id);
            this.addMessage(SERVER, id + " is now connected");
            msg = "You are connected as " + id;
        }
        
        return msg;
    }
    

    @Override
    public void send(String msg) {
        // TODO code
    }

    @Override
    public void bye() {
        // TODO code
    }

    @Override
    public void who() {
        // TODO code
    }

    @Override
    public void receive() {
        // TODO code
    }
    
    
    /**
     * Gets current date time
     * @return String
     */
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    
    /**
     * Adds a message to message list
     * 
     * @param author_id The identifier of the author
     * @param content The content of the message
     */
    private void addMessage(String author_id, String content) {
        messageCount++;
        messages.add(new Message(
                messageCount, 
                author_id,
                content,
                getDateTime())
            );
        
    }
    
}
