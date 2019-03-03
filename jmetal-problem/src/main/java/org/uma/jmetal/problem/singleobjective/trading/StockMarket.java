package org.uma.jmetal.problem.singleobjective.trading;

import org.ta4j.core.*;
import org.ta4j.core.analysis.criteria.AverageProfitableTradesCriterion;
import org.ta4j.core.analysis.criteria.TotalProfitCriterion;
import org.ta4j.core.analysis.criteria.VersusBuyAndHoldCriterion;
import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.impl.DefaultBinarySolution;
import org.uma.jmetal.util.JMetalException;
import ta4jexamples.research.*;

import java.util.*;

/**
 * Class representing problem StockMarket. The problem consist of maximizing the
 * profit in a tick serie
 */
@SuppressWarnings("serial")
public class StockMarket extends AbstractBinaryProblem {
  // Getting a time series (from any provider: CSV, web service, etc.)
  private TimeSeries series;
  private static final int NUMBER_OF_STRATEGIES = 10;
  private static final int TIME_FRAME = 20;
  private static final int STEP = 12;

  /** Constructor */
  /*
  public StockMarket() {
    this(256);
  }
  */

  /** Constructor */
  public StockMarket() {
    // Number of variables is the number of strategies at hand
    setNumberOfVariables(NUMBER_OF_STRATEGIES);
    setNumberOfObjectives(1);
    setName("StockMarket");

    //series = CsvTradesLoader.loadBitstampSeries();
    series = CsvTicksLoader.load("EURUSD_Daily_201701020000_201712290000.csv");
    TimeSeries _series = CsvTicksLoader.load("EURUSD_Daily_201801020000_201812310000.csv");
    int barNumber = 0;
    for (Bar bar : _series.getBarData()) {
      if(barNumber <= TIME_FRAME*STEP) {
        series.addBar(bar);
        barNumber++;
      }
      else {
        break;
      }
    }
  }

  @Override
  protected int getBitsPerVariable(int index) {
    /*
  	if (index != 0) {
  		throw new JMetalException("Problem OneMax has only a variable. Index = " + index) ;
  	}
    */
    //TODO: Ver para que se usa esto
  	return 1;
  }

  @Override
  public BinarySolution createSolution() {
    return new DefaultBinarySolution(this) ;
  }

  /** Evaluate() method */
  @Override
  public void evaluate(BinarySolution solution) {

    // Running our juicy trading strategy...
    TimeSeriesManager seriesManager = new TimeSeriesManager(series);

    List<Strategy> strategies = new ArrayList<>();

    for (int i = 0; i < solution.getVariables().size(); i++) {

      BitSet bitset = solution.getVariableValue(i);

      for (int j = 0; j < bitset.length(); j++) {

          // Si el bit está encendido, agregar la estrategia correspondiente
          if (bitset.get(j)) {
            strategies.add(Decoder.decode(series, i));
          }

      }

    }

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


