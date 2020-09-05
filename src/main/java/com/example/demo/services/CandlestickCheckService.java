package com.example.demo.services;

import com.binance.api.client.domain.market.Candlestick;
import org.springframework.stereotype.Service;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;

@Service
public class CandlestickCheckService {

    public boolean checkIfLastTwoBarsGreen(BarSeries barSeries) {
        int barsCount = barSeries.getBarCount();
        if (barsCount < 2) {
            return false;
        }
        Bar x = barSeries.getBar(barsCount - 1);
        Bar y = barSeries.getBar(barsCount - 2);
        return x.isBullish() && y.isBullish();
    }
}
