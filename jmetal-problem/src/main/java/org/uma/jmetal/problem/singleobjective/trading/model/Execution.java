package org.uma.jmetal.problem.singleobjective.trading.model;

import java.sql.Timestamp;

import static org.uma.jmetal.problem.singleobjective.trading.daos.DAO.NON_PERSISTED_ID;

/**
 * Created by des01c7 on 22-03-19.
 */
public class Execution {
    /** El identificador único de la entidad, inicialmente fijado en <code>NON_PERSISTED_ID</code>. */
    private long id = NON_PERSISTED_ID;

    private Problem problem;
    private Timestamp timestamp;

    private Solution solution;
    private Objective objective;

    public Execution(long id, Problem problem, Timestamp timestamp, Solution solution, Objective objective) {
        this.id = id;
        this.problem = problem;
        this.timestamp = timestamp;
        this.solution = solution;
        this.objective = objective;
    }

    public Solution getSolution() {
        return solution;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    public Objective getObjective() {
        return objective;
    }

    public void setObjective(Objective objective) {
        this.objective = objective;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
