package org.uma.jmetal.runner.singleobjective;

import cl.dsoto.trading.cdi.ServiceLocator;
import cl.dsoto.trading.components.StrategyManager;
import cl.dsoto.trading.model.Objective;
import cl.dsoto.trading.model.Optimization;
import cl.dsoto.trading.model.Strategy;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GeneticAlgorithmBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.IntegerSBXCrossover;
import org.uma.jmetal.operator.impl.crossover.SinglePointCrossover;
import org.uma.jmetal.operator.impl.mutation.BitFlipMutation;
import org.uma.jmetal.operator.impl.mutation.IntegerPolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.BinaryProblem;
import org.uma.jmetal.problem.IntegerProblem;
import org.uma.jmetal.problem.singleobjective.trading.StockMarket;
import org.uma.jmetal.problem.singleobjective.trading.StockMarketInteger;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.IntegerSolution;
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
public class GenerationalGeneticAlgorithmStockMarketIntegerRunner {

  String name;
  String file;
  int parameters;

  public GenerationalGeneticAlgorithmStockMarketIntegerRunner(String name, String file, int parameters) {
    this.name = name;
    this.file = file;
    this.parameters = parameters;
  }

  /**
   * Usage: java org.uma.jmetal.runner.singleobjective.BinaryGenerationalGeneticAlgorithmRunner
   */
  public Optimization run() throws Exception {

    IntegerProblem problem;
    Algorithm<IntegerSolution> algorithm;
    CrossoverOperator<IntegerSolution> crossover;
    MutationOperator<IntegerSolution> mutation;
    SelectionOperator<List<IntegerSolution>, IntegerSolution> selection;

    //problem = new TSP("/tspInstances/kroA100.tsp");
    problem = new StockMarketInteger(name, file, parameters);

    crossover = new IntegerSBXCrossover(0.9, 20.0) ;

    double mutationProbability = 1.0 / problem.getNumberOfVariables() ;
    mutation = new IntegerPolynomialMutation(mutationProbability, 20.0) ;

    selection = new BinaryTournamentSelection<IntegerSolution>(new RankingAndCrowdingDistanceComparator<IntegerSolution>());

    algorithm = new GeneticAlgorithmBuilder<>(problem, crossover, mutation)
            .setPopulationSize(500)
            //.setMaxEvaluations(250000)
            .setMaxEvaluations(1000)
            .setSelectionOperator(selection)
            .build() ;

    AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
            .execute() ;

    IntegerSolution solution = algorithm.getResult() ;
    List<IntegerSolution> population = new ArrayList<>(1) ;
    population.add(solution);

    long computingTime = algorithmRunner.getComputingTime() ;

    new SolutionListOutput(population)
            .setSeparator("\t")
            .setVarFileOutputContext(new DefaultFileOutputContext("VAR.tsv"))
            .setFunFileOutputContext(new DefaultFileOutputContext("FUN.tsv"))
            .print();

    JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
    JMetalLogger.logger.info("Objectives values have been written to file FUN.tsv");
    JMetalLogger.logger.info("Variables values have been written to file VAR.tsv");

    //Devolver una instancia de Optimization
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    List<Objective> objectives = new ArrayList<>();
    List<cl.dsoto.trading.model.Solution> solutions = new ArrayList<>();

    StrategyManager strategyManager = (StrategyManager) ServiceLocator.getInstance().getService(StrategyManager.class);
    Strategy strategy = strategyManager.getByName(problem.getName());

    Optimization optimization = new Optimization(null, strategy, timestamp, objectives, solutions);

    for (Solution<?> sol : population) {
      objectives.add(new Objective(optimization, sol.getObjective(0)));
    }

    for (Solution<?> sol : population) {
      solutions.add(new cl.dsoto.trading.model.Solution(optimization, sol.getVariables()));
    }

    optimization.setObjectives(objectives);
    optimization.setSolutions(solutions);

    return optimization;

  }
}
