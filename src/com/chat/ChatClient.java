package com.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.*;

/**
 * Chat client class
 * 
 * @author gaetan
 */
public class ChatClient {
	
	public ChatClient (){
		
	}
    
    private Chattable stub;
    private Boolean isConnected;
    private Boolean hasQuit;
    private String id;
    private int lastDisplayedMsgIndex;
    
    
    /**
     * Main method: starts a client to communicate with the server
     * 
     * @param args 
     */
    public static void main(String args[]) {
        
        Chattable stub;
        
        // get stub on server object
        try {
            stub = (Chattable) Naming.lookup("//127.0.0.1:8080/chat_server");
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Connection failed. The server may not have been started.");
            return;
        }
        
        ChatClient client = new ChatClient(stub);
        
        // launch chat session
        try {
            client.launchChat();
        } catch (Exception e) {
            System.out.println("An error occurred during chat session:");
            e.printStackTrace();
        }
        
    }
    
    
    /**
     * ChatClient constructor
     * @param stub 
     */
    private ChatClient(Chattable stub) {
        this.stub = stub;
        this.isConnected = false;
        this.hasQuit = false;
    }
    
    
    private void launchChat() throws RemoteException {
        
        String userInput = "", command = "", arg = "", splitInput[];
        
        while (!hasQuit) {
            
            // read user input
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                userInput = br.readLine().trim();
                if (userInput.length() > 0) {
                    splitInput = userInput.split("\\s+", 2);
                    command = splitInput[0];
                    if (splitInput.length > 1) {
                        arg = splitInput[1];
                    }
                    processCommand(command, arg);
                }
            } catch (IOException e) {
                // ignore exception gracefully
            }
            
<<<<<<< HEAD
           // System.out.println(stub.connect("client_0"));
            
            ((ChatClient)stub).launchChat();

=======
>>>>>>> Implemented connect and bye commands
            
            
        }
        
    }
    
    
    /**
     * Processes chat client command
     * 
     * @param command The name of the command (e.g. send)
     * @param arg The argument of the command (e.g. Hello)
     */
    private void processCommand(String command, String arg) throws RemoteException {
        
        String msg = "", idTrial;
        
        if (command.equals("connect")) {
            idTrial = arg;
            if (idTrial.equals("")) {
                msg = "Error: you must provide an id to connect.";
            } else if (isConnected) {
                msg = "You are already connected.";
            } else {
                isConnected = stub.connect(idTrial);
                if (isConnected) {
                    id = idTrial;
                    msg = "You are connected as " + id;
                } else {
                    msg = "Sorry, " + id + " is already connected.";
                }
            }
        }
        
<<<<<<< HEAD
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
=======
        else if (command.equals("bye")) {
            
            stub.bye(id);
            isConnected = false;
            hasQuit = true;
            
        }
        
        else {
            msg = "Command not found.";
        }
        
        System.out.println(msg);
    }
>>>>>>> Implemented connect and bye commands
    
}
