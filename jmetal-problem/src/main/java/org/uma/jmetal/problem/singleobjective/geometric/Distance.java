package org.uma.jmetal.problem.singleobjective.geometric;

import javafx.util.Pair;
import org.ta4j.core.*;
import org.ta4j.core.analysis.criteria.TotalProfitCriterion;
import org.ta4j.core.analysis.criteria.VersusBuyAndHoldCriterion;
import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.problem.singleobjective.trading.Decoder;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.impl.DefaultBinarySolution;
import ta4jexamples.loaders.CsvTicksLoader;
import ta4jexamples.research.MultipleStrategy;

import java.awt.*;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * Created by root on 07-12-20.
 */
public class Distance extends AbstractIntegerProblem {

    Point center;
    List<Evaluation> evaluations = new ArrayList<>();

    /** Constructor */
    public Distance(Rectangle rectangle, List<Integer> lowerLimit, List<Integer> upperLimit) {
        // Number of variables is the number of strategies at hand
        setNumberOfVariables(2);
        setNumberOfObjectives(1);
        setName("Distance");

        this.center = new Point((int)rectangle.getCenterX(),(int)rectangle.getCenterY());

        setLowerLimit(lowerLimit);
        setUpperLimit(upperLimit);
    }

    @Override
    public void evaluate(IntegerSolution solution) {
        Point p = new Point(solution.getVariables().get(0), solution.getVariables().get(1));
        System.out.println("Distance to center: " + center.distance(p));
        evaluations.add(new Evaluation(p,(int)center.distance(p)));
        solution.setObjective(0, center.distance(p));
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }
}
