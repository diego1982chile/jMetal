package org.uma.jmetal.problem.singleobjective.trading;


import org.ta4j.core.Strategy;
import org.ta4j.core.TimeSeries;
import ta4jexamples.strategies.*;

import java.util.List;

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
            case 8:
                return BagovinoStrategy.buildStrategy(series);
            case 9:
                return FXBootCampStrategy.buildStrategy(series);

        }

        throw new IllegalArgumentException("El valor para el parámetro 'locus' no es válido");
    }

    static Strategy decode(TimeSeries series, List<Integer> parameters) {

        switch (parameters.size()) {
            case 3:
                BagovinoStrategy.setShortEma(parameters.get(0));
                BagovinoStrategy.setLongEma(parameters.get(1));
                BagovinoStrategy.setRSI(parameters.get(2));
                return BagovinoStrategy.buildStrategy(series);
            case 7:
                StochasticStrategy.setSMA(parameters.get(0));
                StochasticStrategy.setEMA(parameters.get(1));
                StochasticStrategy.setRSI(parameters.get(2));
                StochasticStrategy.setK(parameters.get(3));
                StochasticStrategy.setD(parameters.get(4));
                StochasticStrategy.setShortEma(parameters.get(5));
                StochasticStrategy.setLongEma(parameters.get(6));
                return StochasticStrategy.buildStrategy(series);
            case 5:
                ParabolicSARStrategy.setSar1(parameters.get(0));
                ParabolicSARStrategy.setSar2(parameters.get(1));
                ParabolicSARStrategy.setRSI(parameters.get(2));
                ParabolicSARStrategy.setK(parameters.get(3));
                ParabolicSARStrategy.setD(parameters.get(4));
                return ParabolicSARStrategy.buildStrategy(series);

        }

        throw new IllegalArgumentException("El valor para el parámetro 'locus' no es válido");
    }


}
