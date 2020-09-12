package com.example.demo.services;

import com.example.demo.indicators.GreenBarsIndicator;
import org.springframework.stereotype.Service;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseStrategy;
import org.ta4j.core.Rule;
import org.ta4j.core.Strategy;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.trading.rules.BooleanIndicatorRule;
import org.ta4j.core.trading.rules.StopGainRule;
import org.ta4j.core.trading.rules.StopLossRule;

@Service
public class StrategyService {
  public Strategy greenBarStrategy(BarSeries barSeries) {
    GreenBarsIndicator greenBarsIndicator = new GreenBarsIndicator(barSeries, 2);
    Rule entryRule = new BooleanIndicatorRule(greenBarsIndicator);
    ClosePriceIndicator closePrice = new ClosePriceIndicator(barSeries);
    // Exit if gained 3% or lost 2%.
    Rule exitRule = new StopGainRule(closePrice, 3.0).or(new StopLossRule(closePrice, 2.0));
    return new BaseStrategy(entryRule, exitRule);
  }
}
