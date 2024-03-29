package org.uma.jmetal.problem.singleobjective.trading;

import org.ta4j.core.*;
import org.ta4j.core.analysis.CashFlow;
import org.ta4j.core.analysis.criteria.AverageProfitableTradesCriterion;
import org.ta4j.core.analysis.criteria.RewardRiskRatioCriterion;
import org.ta4j.core.analysis.criteria.TotalProfitCriterion;
import org.ta4j.core.analysis.criteria.VersusBuyAndHoldCriterion;
import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;
import ta4jexamples.loaders.CsvTicksLoader;
import ta4jexamples.research.MultipleStrategy;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing problem StockMarket. The problem consist of maximizing the
 * profit in a tick serie
 */
@SuppressWarnings("serial")
public class StockMarketInteger extends AbstractIntegerProblem {
  // Getting a time series (from any provider: CSV, web service, etc.)
  private TimeSeries series;
  private String file;

  /** Constructor */
  public StockMarketInteger(String name, String file, int parameters) {
    // Number of variables is the number of strategies at hand
    setNumberOfVariables(parameters);
    setNumberOfObjectives(1);
    List<Integer> lowerLimits = new ArrayList<>();

    List<Integer> upperLimits = new ArrayList<>();
    for(int i = 0; i < parameters; i++) {
      lowerLimits.add(1);
      upperLimits.add(200);
    }
    setLowerLimit(lowerLimits);
    setUpperLimit(upperLimits);
    setName(name);

    //series = CsvTradesLoader.loadBitstampSeries();
    series = CsvTicksLoader.load(file);
  }

  /** Constructor */
  public StockMarketInteger(String name, TimeSeries series, int parameters) {
    // Number of variables is the number of strategies at hand
    setNumberOfVariables(parameters);
    setNumberOfObjectives(1);
    List<Integer> lowerLimits = new ArrayList<>();

    List<Integer> upperLimits = new ArrayList<>();
    for(int i = 0; i < parameters; i++) {
      lowerLimits.add(1);
      upperLimits.add(200);
    }
    setLowerLimit(lowerLimits);
    setUpperLimit(upperLimits);
    setName(name);

    //series = CsvTradesLoader.loadBitstampSeries();
    this.series = series;
  }


  public String getFile() {
    return file;
  }

  public void setFile(String file) {
    this.file = file;
  }


  /** Evaluate() method */
  @Override
  public void evaluate(IntegerSolution solution) {

    // Running our juicy trading strategy...
    TimeSeriesManager seriesManager = new TimeSeriesManager(series);

    List<Strategy> strategies = new ArrayList<>();

    Strategy strategy = Decoder.decode(series, getName(), solution.getVariables());
    setName(strategy.getName());

    strategies.add(strategy);

    MultipleStrategy multipleStrategy = new MultipleStrategy(strategies);

    double vsBuyAndHold = 0;
    double profitTradesRatio = 0;
    double rewardRiskRatio = 0;
    double cashFlow = 0;

    if(!multipleStrategy.getStrategies().isEmpty()) {
        TradingRecord tradingRecord = seriesManager.run(multipleStrategy.buildStrategy(series));

        // Getting the profitable trades ratio
        //AnalysisCriterion profitTradesRatio = new AverageProfitableTradesCriterion();
        //profit = profitTradesRatio.calculate(series, tradingRecord);

        // Total profit of our strategy
        // vs total profit of a buy-and-hold strategy
        vsBuyAndHold = new VersusBuyAndHoldCriterion(new TotalProfitCriterion()).calculate(series, tradingRecord);

        if(vsBuyAndHold > 1.117) {
            System.out.println("hola");
        }

        profitTradesRatio = new AverageProfitableTradesCriterion().calculate(series, tradingRecord);

        // Getting the reward-risk ratio
        rewardRiskRatio = new RewardRiskRatioCriterion().calculate(series, tradingRecord);

        CashFlow cashFlow_ = new CashFlow(series, tradingRecord);

        cashFlow = cashFlow_.getValue(cashFlow_.getSize()-1).doubleValue();

    }

    System.out.println("Our profit vs buy-and-hold profit: " + vsBuyAndHold);
    System.out.println("Our profitTradesRatio: " + profitTradesRatio);
    //System.out.println("Our rewardRiskRatio: " + rewardRiskRatio);
    System.out.println("Our cashFlow: " + cashFlow);

    // StockMarket is a maximization problem: multiply by -1 to minimize
    solution.setObjective(0, -1.0 * vsBuyAndHold);

    // OneMax is a maximization problem: multiply by -1 to minimize
    //solution.setObjective(0, -1.0 * counterOnes);
  }
}


