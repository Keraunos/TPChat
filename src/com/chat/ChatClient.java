package com.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.*;

/**
 * Client class for chat
 * 
 * @author gaetan
 */
public class ChatClient {
	
	public ChatClient (){
		
	}
    
    public static void main(String args[]) {
        
        try {
            
            // get stub on server object
            Chattable stub = (Chattable) Naming.lookup("//127.0.0.1:8080/chat_server");
            
           // System.out.println(stub.connect("client_0"));
            
            ((ChatClient)stub).launchChat();

            
            
        } catch (Exception e) {
            e.printStackTrace();
            // TODO process e
        }
        
    }
    
    
    public void launchChat() {
        
        // TODO code
        
    	 BufferedReader keyboard = new BufferedReader (new InputStreamReader (System.in));
         try {
 			String cmd= keyboard.readLine();
 			String cmdparse[]= cmd.split("",2);
 			if(cmdparse[0].equals("connect")){
 	        	System.out.println(((Chattable)this).connect(cmdparse[1]));
 	        }
 	        	
 	        
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}

    	
    	
    	
    };
    
}
