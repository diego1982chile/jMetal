package org.uma.jmetal.problem.singleobjective.trading.model;

import java.util.List;

import static org.uma.jmetal.problem.singleobjective.trading.daos.DAO.NON_PERSISTED_ID;

/**
 * Created by des01c7 on 22-03-19.
 */
public class Objective {
    /** El identificador único de la entidad, inicialmente fijado en <code>NON_PERSISTED_ID</code>. */
    private long id = NON_PERSISTED_ID;

    float objective;

    public Objective(long id, float objective) {
        this.id = id;
        this.objective = objective;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getObjective() {
        return objective;
    }

    public void setObjective(float objective) {
        this.objective = objective;
    }
}
