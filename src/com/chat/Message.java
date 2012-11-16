package com.chat;

/**
 * Message
 * 
 * @author gaetan
 */
class Message {
   
    public int id;
    public String author_id;
    public String content;
    public String date;
    
    
    public Message(int id, String author_id, String content, String date) {
        
        this.id = id;
        this.author_id = author_id;
        this.content = content;
        this.date = date;
        
    }
    
    
    public void display() {
        System.out.println(date + " [" + id + "] - " + author_id + " >> " + content);
    }
    
}
