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
            case 10:
                return TunnelStrategy.buildStrategy(series);
            case 11:
                return WinslowStrategy.buildStrategy(series);

        }

        throw new IllegalArgumentException("El valor para el parámetro 'locus' no es válido");
    }

    static Strategy decode(TimeSeries series, List<Integer> parameters) {

        switch (parameters.size()) {
            case 1:
                TunnelStrategy.setPERIOD(parameters.get(0));
                return TunnelStrategy.buildStrategy(series);
            case 2:
                return CCICorrectionStrategy.buildStrategy(series);
            case 3:
                BagovinoStrategy.setShortEma(parameters.get(0));
                BagovinoStrategy.setLongEma(parameters.get(1));
                BagovinoStrategy.setRSI(parameters.get(2));
                return BagovinoStrategy.buildStrategy(series);
            case 4:
                MovingAveragesStrategy.setShorterEma(parameters.get(0));
                MovingAveragesStrategy.setShortEma(parameters.get(1));
                MovingAveragesStrategy.setLongEma(parameters.get(2));
                MovingAveragesStrategy.setLongerEma(parameters.get(3));
                return MovingAveragesStrategy.buildStrategy(series);
            case 5:
                ParabolicSARStrategy.setSar1(parameters.get(0));
                ParabolicSARStrategy.setSar2(parameters.get(1));
                ParabolicSARStrategy.setRSI(parameters.get(2));
                ParabolicSARStrategy.setK(parameters.get(3));
                ParabolicSARStrategy.setD(parameters.get(4));
                return ParabolicSARStrategy.buildStrategy(series);
            case 6:
                return MovingMomentumStrategy.buildStrategy(series);
            case 7:
                StochasticStrategy.setSMA(parameters.get(0));
                StochasticStrategy.setEMA(parameters.get(1));
                StochasticStrategy.setRSI(parameters.get(2));
                StochasticStrategy.setK(parameters.get(3));
                StochasticStrategy.setD(parameters.get(4));
                StochasticStrategy.setShortEma(parameters.get(5));
                StochasticStrategy.setLongEma(parameters.get(6));
                return StochasticStrategy.buildStrategy(series);
            case 9:
                if(parameters.get(1) > parameters.get(0)) {
                    parameters.set(1, parameters.get(0) - 1);
                }
                if(parameters.get(2) > parameters.get(1)) {
                    parameters.set(2, parameters.get(1) - 1);
                }
                if(parameters.get(3) > parameters.get(4)) {
                    parameters.set(3, parameters.get(4) - 1);
                }
                /*
                if(parameters.get(6) > parameters.get(5)) {
                    parameters.set(6, parameters.get(5) - 1);
                }
                */
                MACDStrategy.setLongEma(parameters.get(0));
                MACDStrategy.setShortEma(parameters.get(1));
                MACDStrategy.setShorterEma(parameters.get(2));
                MACDStrategy.setMacd1(parameters.get(3));
                MACDStrategy.setMacd2(parameters.get(4));
                MACDStrategy.setSignalEma(parameters.get(5));
                //MACDStrategy.setTpSignalEma(parameters.get(6));
                //MACDStrategy.setATR(parameters.get(7));
                //MACDStrategy.setX(parameters.get(8));
                return MACDStrategy.buildStrategy(series);
            case 10:
                return FXBootCampStrategy.buildStrategy(series);
        }

        throw new IllegalArgumentException("El valor para el parámetro 'locus' no es válido");
    }

    static Strategy decode(TimeSeries series, String strategy, List<Integer> parameters) {

        switch (strategy) {
            case "GlobalExtremaStrategy":
                GlobalExtremaStrategy.setNbBarsPerWeek(parameters.get(0));
                return GlobalExtremaStrategy.buildStrategy(series);
            case "TunnelStrategy":
                if(parameters.get(1) > parameters.get(2)) {
                    parameters.set(1, parameters.get(2) - 1);
                }
                TunnelStrategy.setPERIOD(parameters.get(0));
                TunnelStrategy.setMacd1(parameters.get(1));
                TunnelStrategy.setMacd2(parameters.get(2));
                TunnelStrategy.setSignalEma(parameters.get(3));
                TunnelStrategy.setTpSignalEma(parameters.get(4));
                return TunnelStrategy.buildStrategy(series);
            case "CCICorrectionStrategy":
                CCICorrectionStrategy.setLongCci(parameters.get(0));
                CCICorrectionStrategy.setShortCci(parameters.get(1));
                return CCICorrectionStrategy.buildStrategy(series);
            case "BagovinoStrategy":
                BagovinoStrategy.setShortEma(parameters.get(0));
                BagovinoStrategy.setLongEma(parameters.get(1));
                BagovinoStrategy.setRSI(parameters.get(2));
                return BagovinoStrategy.buildStrategy(series);
            case "MovingAveragesStrategy":
                MovingAveragesStrategy.setShorterEma(parameters.get(0));
                MovingAveragesStrategy.setShortEma(parameters.get(1));
                MovingAveragesStrategy.setLongEma(parameters.get(2));
                MovingAveragesStrategy.setLongerEma(parameters.get(3));
                return MovingAveragesStrategy.buildStrategy(series);
            case "RSI2Strategy":
                RSI2Strategy.setRSI(parameters.get(0));
                RSI2Strategy.setSma1(parameters.get(1));
                RSI2Strategy.setSma2(parameters.get(2));
                RSI2Strategy.setEMA(parameters.get(3));
                return RSI2Strategy.buildStrategy(series);
            case "ParabolicSARStrategy":
                ParabolicSARStrategy.setSar1(parameters.get(0));
                ParabolicSARStrategy.setSar2(parameters.get(1));
                ParabolicSARStrategy.setRSI(parameters.get(2));
                ParabolicSARStrategy.setK(parameters.get(3));
                ParabolicSARStrategy.setD(parameters.get(4));
                return ParabolicSARStrategy.buildStrategy(series);
            case "MovingMomentumStrategy":
                if(parameters.get(3) > parameters.get(4)) {
                    parameters.set(3, parameters.get(4) - 1);
                }
                MovingMomentumStrategy.setShortEma(parameters.get(0));
                MovingMomentumStrategy.setLongEma(parameters.get(1));
                MovingMomentumStrategy.setSTOCHASTIC(parameters.get(2));
                MovingMomentumStrategy.setMacd1(parameters.get(3));
                MovingMomentumStrategy.setMacd2(parameters.get(4));
                MovingMomentumStrategy.setSignalEma(parameters.get(5));
                return MovingMomentumStrategy.buildStrategy(series);
            case "StochasticStrategy":
                StochasticStrategy.setSMA(parameters.get(0));
                StochasticStrategy.setEMA(parameters.get(1));
                StochasticStrategy.setRSI(parameters.get(2));
                StochasticStrategy.setK(parameters.get(3));
                StochasticStrategy.setD(parameters.get(4));
                StochasticStrategy.setShortEma(parameters.get(5));
                StochasticStrategy.setLongEma(parameters.get(6));
                return StochasticStrategy.buildStrategy(series);
            case "MACDStrategy":
                if(parameters.get(1) > parameters.get(0)) {
                    parameters.set(1, parameters.get(0) - 1);
                }
                if(parameters.get(2) > parameters.get(1)) {
                    parameters.set(2, parameters.get(1) - 1);
                }
                if(parameters.get(3) > parameters.get(4)) {
                    parameters.set(3, parameters.get(4) - 1);
                }
                /*
                if(parameters.get(6) > parameters.get(5)) {
                    parameters.set(6, parameters.get(5) - 1);
                }
                */
                MACDStrategy.setLongEma(parameters.get(0));
                MACDStrategy.setShortEma(parameters.get(1));
                MACDStrategy.setShorterEma(parameters.get(2));
                MACDStrategy.setMacd1(parameters.get(3));
                MACDStrategy.setMacd2(parameters.get(4));
                MACDStrategy.setSignalEma(parameters.get(5));
                //MACDStrategy.setTpSignalEma(parameters.get(6));
                //MACDStrategy.setATR(parameters.get(7));
                //MACDStrategy.setX(parameters.get(8));
                return MACDStrategy.buildStrategy(series);
            case "FXBootCampStrategy":
                if(parameters.get(5) > parameters.get(6)) {
                    parameters.set(5, parameters.get(6) - 1);
                }
                FXBootCampStrategy.setEma5(parameters.get(0));
                FXBootCampStrategy.setSma8(parameters.get(1));
                FXBootCampStrategy.setEma21(parameters.get(2));
                FXBootCampStrategy.setEma55(parameters.get(3));
                FXBootCampStrategy.setEma200(parameters.get(4));
                FXBootCampStrategy.setMacd1(parameters.get(5));
                FXBootCampStrategy.setMacd2(parameters.get(6));
                FXBootCampStrategy.setEma8(parameters.get(7));
                FXBootCampStrategy.setStochasticR(parameters.get(8));
                FXBootCampStrategy.setStochasticK(parameters.get(9));
                FXBootCampStrategy.setStochasticD(parameters.get(10));
                return FXBootCampStrategy.buildStrategy(series);
            case "WinslowStrategy":
                if(parameters.get(4) > parameters.get(5)) {
                    parameters.set(4, parameters.get(5) - 1);
                }
                WinslowStrategy.setEma800(parameters.get(0));
                WinslowStrategy.setEma200(parameters.get(1));
                WinslowStrategy.setEma144(parameters.get(2));
                WinslowStrategy.setEma62(parameters.get(3));
                WinslowStrategy.setMacd1(parameters.get(4));
                WinslowStrategy.setMacd2(parameters.get(5));
                WinslowStrategy.setSIGNAL(parameters.get(6));
                WinslowStrategy.setStochasticR(parameters.get(7));
                WinslowStrategy.setStochasticK(parameters.get(8));
                WinslowStrategy.setStochasticD(parameters.get(9));
                return WinslowStrategy.buildStrategy(series);
        }

        throw new IllegalArgumentException("El valor para el parámetro 'locus' no es válido");
    }



}
