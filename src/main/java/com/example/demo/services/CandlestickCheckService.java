package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.binance.api.client.domain.market.Candlestick;

@Service
public class CandlestickCheckService {
	
	public boolean checkIfLastTwoCandlesticksGreen(List<Candlestick> candlesticks) {
		Candlestick firstCandle = candlesticks.get(candlesticks.size() - 2);
		Candlestick secondCandle = candlesticks.get(candlesticks.size() - 1);
		
		if(checkIfCandleGreen(firstCandle) && checkIfCandleGreen(secondCandle)) return true;
		else return false;
	}
	
	private boolean checkIfCandleGreen(Candlestick candle) {
		return (Integer.parseInt(candle.getHigh()) > Integer.parseInt(candle.getOpen())) ? true : false;
	}
}
