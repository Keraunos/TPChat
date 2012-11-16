package com.chat;

/**
 * Chat Message class
 * 
 * @author gaetan
 */
class Message {
    
    
    public int id;
    public String author_id;
    public String content;
    public String date;
    
    
    /**
     * Message constructor
     * 
     * @param id The id of the message: increasing integer value
     * @param author_id The id (name) of the author (a client of the chat)
     * @param content The body of the message
     * @param date The date the message were received by the server
     */
    public Message(int id, String author_id, String content, String date) {
        
        this.id = id;
        this.author_id = author_id;
        this.content = content;
        this.date = date;
        
    }
    
    
    /**
     * toString() overridden method
     * 
     * @return 
     */
    @Override
    public String toString() {
        return date + " [" + id + "] - " + author_id + " >> " + content;
    }
    
    public void print() {
        System.out.println(this.toString());
    }
    
}
