package org.uma.jmetal.problem.singleobjective.trading.model;

import java.sql.Date;
import java.sql.Timestamp;

import static org.uma.jmetal.problem.singleobjective.trading.daos.DAO.NON_PERSISTED_ID;

/**
 * Created by des01c7 on 22-03-19.
 * Esta clase representa el vector de estrategias que otorga las señales para la serie de precios
 */
public class StrategyVector {

    /** El identificador único de la entidad, inicialmente fijado en <code>NON_PERSISTED_ID</code>. */
    private long id = NON_PERSISTED_ID;

    private Date date;
    private Timestamp timestamp;

    private boolean cciCorrection;
    private boolean globalExtrema;
    private boolean movingMomentum;
    private boolean rsi2;
    private boolean macd;
    private boolean stochastic;
    private boolean parabolidSar;
    private boolean movingAverages;
    private boolean bagovino;
    private boolean fxBootcamp;
    private boolean tunnel;

    public StrategyVector(long id, Date date, Timestamp timestamp, boolean cciCorrection, boolean globalExtrema, boolean movingMomentum, boolean rsi2, boolean macd, boolean stochastic, boolean parabolidSar, boolean movingAverages, boolean bagovino, boolean fxBootcamp, boolean tunnel) {
        this.id = id;
        this.date = date;
        this.timestamp = timestamp;
        this.cciCorrection = cciCorrection;
        this.globalExtrema = globalExtrema;
        this.movingMomentum = movingMomentum;
        this.rsi2 = rsi2;
        this.macd = macd;
        this.stochastic = stochastic;
        this.parabolidSar = parabolidSar;
        this.movingAverages = movingAverages;
        this.bagovino = bagovino;
        this.fxBootcamp = fxBootcamp;
        this.tunnel = tunnel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isCciCorrection() {
        return cciCorrection;
    }

    public void setCciCorrection(boolean cciCorrection) {
        this.cciCorrection = cciCorrection;
    }

    public boolean isGlobalExtrema() {
        return globalExtrema;
    }

    public void setGlobalExtrema(boolean globalExtrema) {
        this.globalExtrema = globalExtrema;
    }

    public boolean isMovingMomentum() {
        return movingMomentum;
    }

    public void setMovingMomentum(boolean movingMomentum) {
        this.movingMomentum = movingMomentum;
    }

    public boolean isRsi2() {
        return rsi2;
    }

    public void setRsi2(boolean rsi2) {
        this.rsi2 = rsi2;
    }

    public boolean isMacd() {
        return macd;
    }

    public void setMacd(boolean macd) {
        this.macd = macd;
    }

    public boolean isStochastic() {
        return stochastic;
    }

    public void setStochastic(boolean stochastic) {
        this.stochastic = stochastic;
    }

    public boolean isParabolidSar() {
        return parabolidSar;
    }

    public void setParabolidSar(boolean parabolidSar) {
        this.parabolidSar = parabolidSar;
    }

    public boolean isMovingAverages() {
        return movingAverages;
    }

    public void setMovingAverages(boolean movingAverages) {
        this.movingAverages = movingAverages;
    }

    public boolean isBagovino() {
        return bagovino;
    }

    public void setBagovino(boolean bagovino) {
        this.bagovino = bagovino;
    }

    public boolean isFxBootcamp() {
        return fxBootcamp;
    }

    public void setFxBootcamp(boolean fxBootcamp) {
        this.fxBootcamp = fxBootcamp;
    }

    public boolean isTunnel() {
        return tunnel;
    }

    public void setTunnel(boolean tunnel) {
        this.tunnel = tunnel;
    }
}
