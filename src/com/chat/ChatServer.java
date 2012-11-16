package com.chat;

import java.net.InetAddress;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


/**
 * Chat Server class
 * 
 * @author gaetan
 */
public class ChatServer extends UnicastRemoteObject implements Chattable {
    
    
    static public final String SERVER = "SERVER";
    
    public HashMap<String, String> participants;
    public HashMap<Integer, Message> messages;
    public int messageCount;
    
    
    /**
     * ChatServer constructor
     * 
     * @throws java.rmi.RemoteException 
     */
    private ChatServer() throws java.rmi.RemoteException {
        
        messages = new HashMap<Integer, Message>();
        messageCount = 0;
        participants = new HashMap<String, String>();
        addParticipant(SERVER);
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
            // TODO if no args[0] then force port (more elegant way)
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
            
            URL = "//" + InetAddress.getLocalHost().getHostName() + ":" + port + "/chat_server";
            
            // bind server object in registry
            Naming.rebind(URL, server);
            //System.out.println("Chat server bound in registry.");
            
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
        
        if (!addParticipant(id)) return false;
        
        addMessage(SERVER, id + " has joined the chat.");
        return true;
    }
    
    
    /**
     * Creates a new message from client which id is given as parameter
     * 
     * @param content The content of the message
     * @param id The id of the client who sent the message
     */
    @Override
    public void send(String content, String id) {
        addMessage(id, content);
    }
    
    
    /**
     * Disconnects the client whose id is given as parameter
     * 
     * @param id The id of the client to be disconnected
     */
    @Override
    public void bye(String id) {
        
        participants.remove(id);
        this.addMessage(SERVER, id + " has left the chat.");   
    }
    
    
    /**
     * Gets the list of the participants to the chat, excluding SERVER
     * 
     * @return String the list of the participants
     */
    @Override
    public String who() {
        
        String res = "Participants:";
        for (String id:participants.values()) {
            res += "\n - " + id;
        }
        return res;
    }
    
    
    /**
     * Gets the last messages, from the one which id follows the id given as parameter
     * 
     * @param lastDisplayed The index of the message which precedes the first to be displayed
     * @return String the last messages
     */
    @Override
    public String receive(int lastDisplayed) {
        
        String res = "";
        for (int i = lastDisplayed+1; i <= messageCount; i++) {
            res += messages.get(i).toString() + "\n";
        }
        return res;
    }
    
    
    /**
     * Gets messageCount, the number of messages sent to the server
     * 
     * @return messageCount
     * @throws RemoteException 
     */
    @Override
    public int getLastMsgIndex() throws RemoteException {
        return this.messageCount;
    }
    
    
    /**
     * Adds a client to the participants
     * 
     * @param id The id of the client
     * @return Boolean true if could add client, else false
     */
    private Boolean addParticipant(String id) {
        
        if (participants.containsKey(id)) return false;
        
        participants.put(id, id);
        return true;
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
        messages.get(messageCount).print();
    }
    
    
    /**
     * Gets current date time
     * 
     * @return String
     */
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    
    /**
     * Displays all the messages
     * 
     */
    private void displayAllMessages() {
        for (Message msg:messages.values()) msg.print();
    }
    
}
