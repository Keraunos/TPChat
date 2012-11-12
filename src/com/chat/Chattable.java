package com.chat;

/**
 * Client interface for chatting
 * 
 * @author gaetan
 */
public interface Chattable extends java.rmi.Remote {
    
    public void connect(String id);
    public void send(String msg);
    public void bye();
    public void who();
    public void receive();
    
}
