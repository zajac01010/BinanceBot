package com.example.demo;

import java.util.List;
import java.util.Scanner;

// TODO: clean up unused imports
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

// TODO: rename the class
@SpringBootApplication
public class Demo2Application {
	
	public static void main(String[] args) {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance();

        // Instantiate a simple, blocking client.
        BinanceApiRestClient client = factory.newRestClient();

        // Print some random stuff, just to validate that it works.
        long serverTime = client.getServerTime();
        System.out.println("Server time: " + serverTime);

        List<Candlestick> candlesticks = client.getCandlestickBars("YFIBTC", CandlestickInterval.TWO_HOURLY);
        System.out.println("YFIBTC: " + candlesticks.toString());
	}

}
