package org.uma.jmetal.problem.singleobjective.trading.model;

import java.sql.Date;
import java.sql.Timestamp;

import static org.uma.jmetal.problem.singleobjective.trading.daos.DAO.NON_PERSISTED_ID;

/**
 * Created by des01c7 on 22-03-19.
 */
public class Problem {
    /** El identificador único de la entidad, inicialmente fijado en <code>NON_PERSISTED_ID</code>. */
    private long id = NON_PERSISTED_ID;

    private String name;
    private int variables;
    private ProblemType type;

    public Problem(long id, String name, int variables, ProblemType type) {
        this.id = id;
        this.name = name;
        this.variables = variables;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVariables() {
        return variables;
    }

    public void setVariables(int variables) {
        this.variables = variables;
    }

    public ProblemType getType() {
        return type;
    }

    public void setType(ProblemType type) {
        this.type = type;
    }
}
