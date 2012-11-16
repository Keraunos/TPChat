package com.chat;

/**
 * DOCUMENTATION
 * Chat Communication Protocole
 * 
 * -----------------------------------------------------------------------------
 * 
 * Client CLI:
 * 
 * connect <id> : connects client identified by <id> to the server
 * send <msg> : sends to all participants the message <msg>
 * bye : disconnects client from the server
 * who : displays the list of currently connected clients
 * 
 * <id> : a String
 * <msg> : a String
 * 
 * -----------------------------------------------------------------------------
 * 
 * Message format:
 * 
 * <date> <time> [<msg_if>] - <id> >> <msg>
 * 2012-11-12 16:31 [1] - bastien >> bla bla bla
 * 2012-11-12 16:32 [2] - gaetan >> foo bar baz
 * 
 * -----------------------------------------------------------------------------
 * 
 */


public class Main {
    // NOTHING
}
