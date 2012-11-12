package com.chat;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Server class for the chat
 * 
 * @author gaetan
 */
public class ChatServer extends UnicastRemoteObject implements Chattable {

    
    public ArrayList<Message> messages;
    public ArrayList<String> participants;
    
    
    public ChatServer() throws RemoteException {
        // TODO code
    };
    
    
    /**
     * Connects the client to the server with id given as parameter
     * 
     * @param id the client id
     */
    @Override
    public void connect(String id) {
        
        String msg;
        
        if (participants.contains(id)) {
            msg = id + " is already connected.";
        } else {
            participants.add(id);
            msg = "You are connected as " + id;
        }
        
        System.out.println(msg);
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
    
    
    
}
