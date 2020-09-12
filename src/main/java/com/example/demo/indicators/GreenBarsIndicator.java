package com.example.demo.indicators;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;

import java.util.stream.IntStream;

/**
 * N-green bars indicator.
 *
 * <p>Indicates when a sequence of n green bars has occurred.
 */
public class GreenBarsIndicator extends CachedIndicator<Boolean> {
  private final int barCount;

  public GreenBarsIndicator(BarSeries series, int barCount) {
    super(series);
    this.barCount = barCount;
  }

  @Override
  protected Boolean calculate(int index) {
    if (index < this.barCount) {
      // Not enough historical bars to check.
      return false;
    }
    return IntStream.range(index - 2, index).allMatch(i -> getBarSeries().getBar(i).isBullish());
  }
}
