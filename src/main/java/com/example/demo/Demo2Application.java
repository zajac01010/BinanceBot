package com.example.demo;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.BinanceApiWebSocketClient;
import com.binance.api.client.domain.market.*;
import com.example.demo.services.CandlestickCheckService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Demo2Application {

    private static final String URL = "wss://stream.binance.com:9443/ws/btcusdt@trade";

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
