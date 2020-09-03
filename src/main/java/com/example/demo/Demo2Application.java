package com.example.demo;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.BinanceApiWebSocketClient;
import com.binance.api.client.domain.market.AggTrade;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.binance.api.client.domain.market.OrderBook;
import com.binance.api.client.domain.market.OrderBookEntry;
import com.example.demo.services.CandlestickCheckService;
import com.example.demo.web.MyStompSessionHandler;

@SpringBootApplication
public class Demo2Application {
	
	private static String URL = "wss://stream.binance.com:9443/ws/btcusdt@trade";
	
	public static void main(String[] args) {
		//SpringApplication.run(Demo2Application.class, args);
		CandlestickCheckService candlestickCheckService = new CandlestickCheckService();
		BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance();
		BinanceApiRestClient client = factory.newRestClient();
		client.ping();
		long serverTime = client.getServerTime();
		//System.out.println(serverTime);
		OrderBook orderBook = client.getOrderBook("YFIBTC", 10);
		List<OrderBookEntry> asks = orderBook.getAsks();
		OrderBookEntry firstAskEntry = asks.get(0);
		System.out.println(firstAskEntry.getPrice() + " / " + firstAskEntry.getQty());
		List<AggTrade> aggTrades = client.getAggTrades("YFIBTC");
		System.out.println(aggTrades.size());
		List<Candlestick> candlesticks = client.getCandlestickBars("YFIBTC", CandlestickInterval.TWO_HOURLY);
		System.out.println(candlesticks.size());
		boolean shouldBuy = candlestickCheckService.checkIfLastTwoCandlesticksGreen(candlesticks);
		BinanceApiWebSocketClient client2 = BinanceApiClientFactory.newInstance().newWebSocketClient();
	}

}
