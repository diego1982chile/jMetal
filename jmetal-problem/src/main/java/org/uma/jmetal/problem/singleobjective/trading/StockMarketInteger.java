package org.uma.jmetal.problem.singleobjective.trading;

import org.ta4j.core.*;
import org.ta4j.core.analysis.criteria.TotalProfitCriterion;
import org.ta4j.core.analysis.criteria.VersusBuyAndHoldCriterion;
import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.impl.DefaultBinarySolution;
import ta4jexamples.loaders.CsvTicksLoader;
import ta4jexamples.research.MultipleStrategy;
import ta4jexamples.strategies.BagovinoStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

/**
 * Class representing problem StockMarket. The problem consist of maximizing the
 * profit in a tick serie
 */
@SuppressWarnings("serial")
public class StockMarketInteger extends AbstractIntegerProblem {
  // Getting a time series (from any provider: CSV, web service, etc.)
  private TimeSeries series;
  private static final int NUMBER_OF_PARAMETERS = 6;

  /** Constructor */
  /*
  public StockMarket() {
    this(256);
  }
  */

  /** Constructor */
  public StockMarketInteger() {
    // Number of variables is the number of strategies at hand
    setNumberOfVariables(NUMBER_OF_PARAMETERS);
    setNumberOfObjectives(1);
    List<Integer> lowerLimits = new ArrayList<>();
    List<Integer> upperLimits = new ArrayList<>();
    for(int i = 0; i < NUMBER_OF_PARAMETERS; i++) {
      lowerLimits.add(1);
      upperLimits.add(200);
    }
    setLowerLimit(lowerLimits);
    setUpperLimit(upperLimits);
    setName("StockMarketInteger");

    //series = CsvTradesLoader.loadBitstampSeries();
    series = CsvTicksLoader.load("test.csv");
  }


  /** Evaluate() method */
  @Override
  public void evaluate(IntegerSolution solution) {

    // Running our juicy trading strategy...
    TimeSeriesManager seriesManager = new TimeSeriesManager(series);

    List<Strategy> strategies = new ArrayList<>();

    strategies.add(Decoder.decode(series, solution.getVariables()));

    MultipleStrategy multipleStrategy = new MultipleStrategy(strategies);

    double profit = 0;

    if(!multipleStrategy.getStrategies().isEmpty()) {
        TradingRecord tradingRecord = seriesManager.run(multipleStrategy.buildStrategy(series));

        // Getting the profitable trades ratio
        //AnalysisCriterion profitTradesRatio = new AverageProfitableTradesCriterion();
        //profit = profitTradesRatio.calculate(series, tradingRecord);

        // Total profit of our strategy
        // vs total profit of a buy-and-hold strategy
        AnalysisCriterion vsBuyAndHold = new VersusBuyAndHoldCriterion(new TotalProfitCriterion());
        profit = vsBuyAndHold.calculate(series, tradingRecord);
    }

    System.out.println("Profitable trades ratio: " + profit);

    // StockMarket is a maximization problem: multiply by -1 to minimize
    solution.setObjective(0, -1.0 * profit);

    // OneMax is a maximization problem: multiply by -1 to minimize
    //solution.setObjective(0, -1.0 * counterOnes);
  }
}


