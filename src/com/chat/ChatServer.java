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
import java.util.HashMap;

/**
 * Chat Server class
 * 
 * @author gaetan
 */
public class ChatServer extends UnicastRemoteObject implements Chattable {

    static public final String SERVER = "SERVER";
    
    public HashMap<Integer, Message> messages;
    public int messageCount;
    public HashMap<String, String> participants;
    
    
    public ChatServer() throws java.rmi.RemoteException {
        
        messages = new HashMap<Integer, Message>();
        messageCount = 0;
        participants = new HashMap<String, String>();
        participants.put(SERVER, SERVER);
        
    };
    
    
    /**
     * Starts chat server
     * 
     * @param args [<port>] where <port> is a port number
     */
    public static void main(String args[]) {
        
        int port;
        String URL;
        
        // get server port
        try {
            // TODO if no args[0] then force port (more graceful way)
            //Integer I = new Integer(args[0]);
            //port  = I.intValue();
            port = 8080; // force port
        } catch (Exception e) {
            System.out.println("Please enter: server <port>");
            return;
        }
        
        
        // create and bind chat server
        try {
            
            Registry reg = LocateRegistry.createRegistry(port);
            
            Chattable server = new ChatServer();
            
            // TODO factorize binding name ChatServer
            URL = "//" + InetAddress.getLocalHost().getHostName() + ":" + port + "/chat_server";
            
            // bind server object in registry
            Naming.rebind(URL, server);
            System.out.println("Chat server bound in registry.");
            
            
        } catch(Exception e) {
            // TODO process e
            e.printStackTrace();
        }
        
        System.out.println("Chat server started.");
    }
    
    
    /**
     * Connects the client to the server with id given as parameter
     * 
     * @param id the client id
     * @return Boolean true if client is connected, else fale
     */
    @Override
    public Boolean connect(String id) {
        
        String msg;
        
        if (participants.containsKey(id)) {
            return false;
        } else {
            participants.put(id, id);
            this.addMessage(SERVER, id + " has joined the chat.");
            return true;
        }
        
    }
    

    @Override
    public void send(String msg) {
        // TODO code
    }
    
    
    @Override
    public void bye(String id) {
        
        participants.remove(id);
        this.addMessage(SERVER, id + " has left the chat.");
        
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
        messages.put(
                messageCount,
                new Message(messageCount,  author_id, content, getDateTime())
            );
        
    }
    
    
    /**
     * Displays all the messages
     * 
     */
    private void displayAllMessages() {
        for (Message msg:messages.values()) msg.display();
    }
    
}
