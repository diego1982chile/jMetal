package org.uma.jmetal.problem.singleobjective.trading;


import org.ta4j.core.Strategy;
import org.ta4j.core.TimeSeries;
import ta4jexamples.strategies.*;

/**
 * Created by des01c7 on 08-02-19.
 */
public final class Decoder {

    static Strategy decode(TimeSeries series, int locus) {

        switch (locus) {
            case 0:
                return CCICorrectionStrategy.buildStrategy(series);
            case 1:
                return GlobalExtremaStrategy.buildStrategy(series);
            case 2:
                return MovingMomentumStrategy.buildStrategy(series);
            case 3:
                return RSI2Strategy.buildStrategy(series);
            case 4:
                return MACDStrategy.buildStrategy(series);
            case 5:
                return StochasticStrategy.buildStrategy(series);
            case 6:
                return ParabolicSARStrategy.buildStrategy(series);
            case 7:
                return MovingAveragesStrategy.buildStrategy(series);

        }

        throw new IllegalArgumentException("El valor para el parámetro 'locus' no es válido");
    }

    static Strategy decode(TimeSeries series, int locus, boolean operator) {

        switch (locus) {
            case 0:
                return CCICorrectionStrategy.buildStrategy(series);
            case 1:
                return GlobalExtremaStrategy.buildStrategy(series);
            case 2:
                return MovingMomentumStrategy.buildStrategy(series);
            case 3:
                return RSI2Strategy.buildStrategy(series);
            case 4:
                return MACDStrategy.buildStrategy(series);

        }

        throw new IllegalArgumentException("El valor para el parámetro 'locus' no es válido");
    }


}
