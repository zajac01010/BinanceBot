package com.example.demo.web;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.logging.log4j.message.Message;

@ServerEndpoint(value= "stream.binance.com:9443")
public class Endpoints {
		
	@OnOpen
    public void onOpen(Session session) throws IOException {
        // Get session and WebSocket connection
    }
 
    @OnMessage
    public void onMessage(Session session, Message message) throws IOException {
        // Handle new messages
    }
 
    @OnClose
    public void onClose(Session session) throws IOException {
        // WebSocket connection closes
    }
 
    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }
}
