package TechnicalTools;

//~--- non-JDK imports --------------------------------------------------------

import AnalysisTool.AnalysisTool;
import Utils.LoggerNB;
import Utils.TDoc;

import StatisticBase.STA;

//~--- classes ----------------------------------------------------------------

public class RSI extends AnalysisTool {

    /**
     * Method RSI
     */
    public RSI() {
        super("Relative Strength Indicator by Salomatine Dmitry");
        setParam("PERIOD", new Integer(14));
        setParam("DATAINCOLUMN", new Integer(4));
    }

    //~--- methods ------------------------------------------------------------

    public static double[] computeRSI(java.util.List dataTrade, int days,
                                      int dataincolumn) {
        int    i, j;
        Float  p, c;
        double avgGain, avgLoss;

        if ((dataTrade == null) || (dataTrade.size() <= 0)
                || (dataTrade.size() - days <= 0)) {
            return null;
        }

        double help[]  = new double[dataTrade.size() - 1];
        double RSIar[] = new double[dataTrade.size() - days];
        double RS;
        double diuk = 0.000001f;

        // calculate differences
        j = help.length - 1;

        for (i = dataTrade.size() - 1; i > 0; i--) {
            p         = (Float) (((TDoc) dataTrade.get(i)).o[dataincolumn]);
            c         = (Float) (((TDoc) dataTrade.get(i
                    - 1)).o[dataincolumn]);
            help[j--] = c.floatValue() - p.floatValue();
        }

        j = RSIar.length - 1;

        for (i = help.length - 1; (i >= days - 1) && (i > 0); i--, j--) {
            try {
                avgGain = STA.squareAvgRSIVer(help, i - (days - 1), i,
                                                  STA.CNT_POSITIVE, diuk);
                avgLoss = STA.squareAvgRSIVer(help, i - (days - 1), i,
                                                  STA.CNT_NEGATIVE, diuk);

                if (Math.abs(avgLoss) <= diuk) {
                    RSIar[j] = 100.0f;
                } else {
                    RS       = Math.abs(avgGain / avgLoss);
                    RSIar[j] = /* 100.0f- */ (100.0f / (1.0f + RS));
                }
            } catch (Exception e) {
                if(LoggerNB.debuging){System.out.println(e);}
            }
        }

        return RSIar;
    }

    /**
     * Method Run
     */
    public void run(java.util.List datasource) {
        runRSI(datasource);
    }

    /*
     * RSI - (Relative Strength Index)
     */
    public void runRSI(java.util.List dataTrade) {
        Integer days         = (Integer) getParam("PERIOD");
        Integer dataincolumn = (Integer) getParam("DATAINCOLUMN");
        double  ar[]         = computeRSI(dataTrade, days.intValue(),
                                          dataincolumn.intValue());

        tdp.clear();

        for (int i = 0; i < ar.length; i++) {
            tdp.add(new Double(ar[i]));
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
