package org.uma.jmetal.runner.singleobjective;

import cl.dsoto.trading.clients.ServiceLocator;
import cl.dsoto.trading.components.StrategyManager;
import cl.dsoto.trading.model.Objective;
import cl.dsoto.trading.model.Optimization;
import cl.dsoto.trading.model.Strategy;
import org.ta4j.core.TimeSeries;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GeneticAlgorithmBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.SinglePointCrossover;
import org.uma.jmetal.operator.impl.mutation.BitFlipMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.BinaryProblem;
import org.uma.jmetal.problem.singleobjective.trading.StockMarket;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to configure and run a generational genetic algorithm. The target problem is StockMarket.
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class GenerationalGeneticAlgorithmStockMarketRunner {

  String name;
  String file;
  TimeSeries series;
  int parameters;

  public GenerationalGeneticAlgorithmStockMarketRunner(String name, String file, int parameters) {
    this.name = name;
    this.file = file;
    this.parameters = parameters;
  }

  public GenerationalGeneticAlgorithmStockMarketRunner(String name, TimeSeries series, int parameters) {
    this.name = name;
    this.series = series;
    this.parameters = parameters;
  }

  /**
   * Usage: java org.uma.jmetal.runner.singleobjective.BinaryGenerationalGeneticAlgorithmRunner
   */
  public Optimization run() throws Exception {

    BinaryProblem problem = null;
    Algorithm<BinarySolution> algorithm;
    CrossoverOperator<BinarySolution> crossover;
    MutationOperator<BinarySolution> mutation;
    SelectionOperator<List<BinarySolution>, BinarySolution> selection;

    //problem = new TSP("/tspInstances/kroA100.tsp");
    if(file != null && series == null) {
      problem = new StockMarket(name, file, parameters);
    }
    if(series != null && file == null) {
      problem = new StockMarket(name, series, parameters);
    }
    if(series == null && file == null) {
      throw new Exception("Se debe setear el nombre del archivo o la serie");
    }

    crossover = new SinglePointCrossover(0.9) ;

    double mutationProbability = 1.0 / problem.getNumberOfVariables() ;
    mutation = new BitFlipMutation(mutationProbability) ;

    selection = new BinaryTournamentSelection<BinarySolution>(new RankingAndCrowdingDistanceComparator<BinarySolution>());

    algorithm = new GeneticAlgorithmBuilder<>(problem, crossover, mutation)
            .setPopulationSize(500)
            //.setMaxEvaluations(250000)
            .setMaxEvaluations(500)
            .setSelectionOperator(selection)
            .build() ;

    AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
            .execute() ;


    BinarySolution solution = algorithm.getResult() ;
    List<BinarySolution> population = new ArrayList<>(1) ;
    population.add(solution) ;

    long computingTime = algorithmRunner.getComputingTime() ;

    new SolutionListOutput(population)
            .setSeparator("\t")
            .setVarFileOutputContext(new DefaultFileOutputContext("VAR.tsv"))
            .setFunFileOutputContext(new DefaultFileOutputContext("FUN.tsv"))
            .print();

    JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
    JMetalLogger.logger.info("Objectives values have been written to file FUN.tsv");
    JMetalLogger.logger.info("Variables values have been written to file VAR.tsv");

    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    List<Objective> objectives = new ArrayList<>();
    List<cl.dsoto.trading.model.Solution> solutions = new ArrayList<>();

    StrategyManager problemManager = (StrategyManager) ServiceLocator.getInstance().getService(StrategyManager.class);
    Strategy prob = problemManager.getByName(problem.getName());

    Optimization optimization = new Optimization(null, prob, timestamp, objectives, solutions);

    for (Solution<?> sol : population) {
      objectives.add(new Objective(optimization, sol.getObjective(0)));
    }

    for (Solution<?> sol : population) {
      solutions.add(new cl.dsoto.trading.model.Solution(optimization, solution.getVariables()));
    }

    optimization.setObjectives(objectives);
    optimization.setSolutions(solutions);

    return optimization;

  }

}
