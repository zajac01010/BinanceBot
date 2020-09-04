package com.example.demo.services;

import com.binance.api.client.domain.market.Candlestick;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandlestickCheckService {

    public boolean checkIfLastTwoCandlesticksGreen(List<Candlestick> candlesticks) {
        Candlestick firstCandle = candlesticks.get(candlesticks.size() - 2);
        Candlestick secondCandle = candlesticks.get(candlesticks.size() - 1);

		return checkIfCandleGreen(firstCandle) && checkIfCandleGreen(secondCandle);
    }

    private boolean checkIfCandleGreen(Candlestick candle) {
        return Integer.parseInt(candle.getHigh()) > Integer.parseInt(candle.getOpen());
    }
}
