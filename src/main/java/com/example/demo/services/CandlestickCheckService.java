package com.example.demo.services;

import com.example.demo.indicators.GreenBarsIndicator;
import org.springframework.stereotype.Service;
import org.ta4j.core.BarSeries;

@Service
public class CandlestickCheckService {

  public boolean checkIfLastTwoBarsGreen(BarSeries barSeries) {
    return new GreenBarsIndicator(barSeries, 2).getValue(barSeries.getBarCount() - 1);
  }
}
