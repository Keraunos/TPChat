package com.chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Client interface for chatting
 * 
 * @author gaetan
 */
public interface Chattable extends Remote {
    
    public String connect(String id) throws RemoteException;
    public void send(String msg) throws RemoteException;
    public void bye() throws RemoteException;
    public void who() throws RemoteException;
    public void receive() throws RemoteException;
    
}
