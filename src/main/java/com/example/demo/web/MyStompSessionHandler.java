package com.example.demo.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

public class MyStompSessionHandler extends StompSessionHandlerAdapter{
	
	private Logger logger = LogManager.getLogger(MyStompSessionHandler.class);
	
	@Override
	public void afterConnected(
	  StompSession session, StompHeaders connectedHeaders) {
	    session.subscribe("/ws/btcusdt@trade", this);
	}
	
	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
	    String msg = (String) payload;
	    logger.info("Received : " + msg);
	}
}
