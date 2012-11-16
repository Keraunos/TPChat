package com.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.*;

/**
 * Chat Client class
 * 
 * @author gaetan
 */
public class ChatClient {
    
    
    private Chattable stub;
    private Boolean isConnected;
    private Boolean hasQuit;
    private String id;
    private int lastDisplayedMsgIndex;
    
    
    /**
     * Starts a chat client to communicate with the chat server
     * 
     * @param args 
     */
    public static void main(String args[]) {
        
        // get stub from chat server
        Chattable stub;
        try {
            stub = (Chattable) Naming.lookup("//127.0.0.1:8080/chat_server");
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Connection failed. The server may not have been started.");
            return;
        }
        
        // create chat client and launch chat session
        ChatClient client = new ChatClient(stub);
        try {
            client.launchChat();
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("An error occurred during chat session:");
        }
        
    }
    
    
    /**
     * ChatClient constructor
     * 
     * @param stub The RMI stub
     */
    private ChatClient(Chattable stub) {
        this.stub = stub;
        this.isConnected = false;
        this.hasQuit = false;
    }
    
    
    /**
     * Starts interactive CLI chat. User is expected to type chat commands.
     * 
     * @throws RemoteException 
     */
    private void launchChat() throws RemoteException {
        
        // get the index of the last message sent to the server so that client
        // knows from where they can start printing messages
        this.lastDisplayedMsgIndex = stub.getLastMsgIndex();
        
        String  userInput = "",
                command,
                arg,
                splitInput[];
        
        // while client has not quit, process their commands
        while (!hasQuit) {
            
            command = "";
            arg = "";
            
            // read, format and process user input if it is possible
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                
                userInput = br.readLine().trim();
                
                if (userInput.length() > 0) {
                    
                    splitInput = userInput.split("\\s+", 2);
                    command = splitInput[0];
                    if (splitInput.length > 1)
                        arg = splitInput[1];
                    
                    processCommand(command, arg);
                }
                
            } catch (IOException e) {
                // ignore exception gracefully
            }
        }
        
    }
    
    
    /**
     * Processes chat client command
     * 
     * @param command The name of the command (e.g. send)
     * @param arg The argument of the command (e.g. Hello)
     */
    private void processCommand(String command, String arg) throws RemoteException {
        
        String feedback = "";
        
        
        // command CONNECT
        if (command.equals("connect")) {
            String idTrial = arg;
            if (isConnected) {
                feedback = "You are already connected as " + id;
            } else  if (idTrial.equals("")) {
                feedback = "You must provide an id to connect.";
            } else {
                isConnected = stub.connect(idTrial);
                if (isConnected) {
                    id = idTrial;
                    lastDisplayedMsgIndex = stub.getLastMsgIndex();
                    feedback = "You are connected as " + id;
                } else {
                    feedback = "Sorry, " + idTrial + " is already connected.";
                }
            }
        }
        
        
        // command BYE
    	else if (command.equals("bye")) {
            
            if (isConnected) {
                stub.bye(id);
                isConnected = false;
            }
            hasQuit = true;
            if (id != null) feedback = "Bye bye " + id + "!";
            else            feedback = "Bye bye!";
        }
        
        
        // command SEND
        else if (command.equals("send")) {
            if (!isConnected) {
                feedback = "You must be connected to process command \"send\"";
            } else {
                String content = arg;
                if (content.equals("")) {
                    feedback = "You must provide a message content to send it.";
                } else {
                    stub.send(content, id);
                    feedback = stub.receive(lastDisplayedMsgIndex);
                    lastDisplayedMsgIndex = stub.getLastMsgIndex();
                }
            }
        }
        
        
        // command WHO
        else if (command.equals("who")) {
            if (!isConnected) {
                feedback = "You must be connected to process command \"who\"";
            } else {
                feedback = stub.who();
            }
        }
        
        
        // unknown command
        else {
            feedback = "Command not found.";
        }
        
        
        if (!feedback.equals(""))
            System.out.println(feedback);
    }
    
}
