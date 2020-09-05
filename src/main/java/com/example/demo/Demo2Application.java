package com.example.demo;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.BinanceApiWebSocketClient;
import com.binance.api.client.domain.market.*;
import com.example.demo.services.CandlestickCheckService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBar;
import org.ta4j.core.BaseBarSeriesBuilder;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Demo2Application {

  private static final String URL = "wss://stream.binance.com:9443/ws/btcusdt@trade";

  public static void main(String[] args) {
    // SpringApplication.run(Demo2Application.class, args);
    CandlestickCheckService candlestickCheckService = new CandlestickCheckService();
    BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance();
    BinanceApiRestClient client = factory.newRestClient();
    client.ping();
    long serverTime = client.getServerTime();
    // System.out.println(serverTime);
    OrderBook orderBook = client.getOrderBook("YFIBTC", 10);
    List<OrderBookEntry> asks = orderBook.getAsks();
    OrderBookEntry firstAskEntry = asks.get(0);
    System.out.println(firstAskEntry.getPrice() + " / " + firstAskEntry.getQty());
    List<AggTrade> aggTrades = client.getAggTrades("YFIBTC");
    System.out.println(aggTrades.size());
    List<Candlestick> candlesticks =
        client.getCandlestickBars("YFIBTC", CandlestickInterval.TWO_HOURLY);
    System.out.println(candlesticks.size());

    BarSeries barSeries = getBars(client);
    boolean shouldBuy = candlestickCheckService.checkIfLastTwoBarsGreen(barSeries);
    System.out.println(barSeries);
    System.out.println(shouldBuy);

    BinanceApiWebSocketClient client2 = BinanceApiClientFactory.newInstance().newWebSocketClient();
  }

  private static BarSeries getBars(BinanceApiRestClient client) {
    List<Bar> bars =
        client.getCandlestickBars("YFIBTC", CandlestickInterval.TWO_HOURLY).stream()
            .map(Demo2Application::candlestickToBar)
            .collect(Collectors.toList());
    return new BaseBarSeriesBuilder().withBars(bars).withName("YFIBTC").build();
  }

  private static Bar candlestickToBar(Candlestick candlestick) {
    ZonedDateTime closeTime =
        ZonedDateTime.ofInstant(Instant.ofEpochMilli(candlestick.getCloseTime()), ZoneOffset.UTC);
    return new BaseBar(
        Duration.ofHours(2),
        closeTime,
        Double.parseDouble(candlestick.getOpen()),
        Double.parseDouble(candlestick.getHigh()),
        Double.parseDouble(candlestick.getLow()),
        Double.parseDouble(candlestick.getClose()),
        Double.parseDouble(candlestick.getVolume()));
  }
}
