package com.chat;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

/**
 * Server class for the chat
 * 
 * @author gaetan
 */
public class ChatServer extends UnicastRemoteObject implements Chattable {

    public ChatServer() throws RemoteException {
        // TODO code
    };
    
    @Override
    public void connect(String id) {
        // TODO code
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
