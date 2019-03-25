package org.uma.jmetal.problem.singleobjective.trading.model;

import java.sql.Timestamp;
import java.util.List;

import static org.uma.jmetal.problem.singleobjective.trading.daos.DAO.NON_PERSISTED_ID;

/**
 * Created by des01c7 on 22-03-19.
 */
public class Solution<T extends Comparable> {
    /** El identificador único de la entidad, inicialmente fijado en <code>NON_PERSISTED_ID</code>. */
    private long id = NON_PERSISTED_ID;

    List<T> solution;

    public Solution(long id, List<T> solution) {
        this.id = id;
        this.solution = solution;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<T> getSolution() {
        return solution;
    }

    public void setSolution(List<T> solution) {
        this.solution = solution;
    }
}
