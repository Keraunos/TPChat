package com.chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Client interface for chatting
 * 
 * @author gaetan
 */
public interface Chattable extends Remote {
    
    public Boolean connect(String id) throws RemoteException;
    public void send(String content, String id) throws RemoteException;
    public void bye(String id) throws RemoteException;
    public String who() throws RemoteException;
    public String receive(int lastDisplayed) throws RemoteException;
    public int getLastMsgIndex() throws RemoteException;
    
}
