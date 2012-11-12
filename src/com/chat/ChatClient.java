package com.chat;

import java.rmi.*;

/**
 * Client class for chat
 * 
 * @author gaetan
 */
public class ChatClient {
    
    public static void main(String args[]) {
        
        try {
            
            // get stub on server object
            Chattable stub = (Chattable) Naming.lookup("//127.0.0.1:8080/chat_server");
            
            System.out.println(stub.connect("client_0"));
            
            
        } catch (Exception e) {
            e.printStackTrace();
            // TODO process e
        }
        
    }
    
    
    public void launchChat() {
        
        // TODO code
        
    };
    
}
