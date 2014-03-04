package TechnicalTools;

//~--- non-JDK imports --------------------------------------------------------

import AnalysisTool.AnalysisTool;
import Utils.LoggerNB;
import Utils.TDoc;

import StatisticBase.STA;

//~--- classes ----------------------------------------------------------------

public class MACD extends AnalysisTool {

    /**
     * Method MACD
     *
     *
     */
    public MACD() {
        super("Convergency/Divergency by Salomatine Dmitry");
        setParam("FirstMA", new Integer(26));
        setParam("SecondMA", new Integer(12));
        setParam("MAOnDiff", new Integer(9));
        setParam("DATAINCOLUMN", new Integer(4));
    }

    //~--- methods ------------------------------------------------------------

    public static double[] computeMACD(java.util.List dataTrade, int daysF,
                                       int daysS, int maod, int start,
                                       int dataincolumn) {
        int   i, j, k, t, size;
        Float close;

        if ((dataTrade == null) || (dataTrade.size() <= 0)) {
            return null;
        }

        size = dataTrade.size();

        double ma1[]   = null;
        double temp2[] = null;
        double help[]  = new double[size];
        double ret[]   = null;

        try {

            // fill the arrays and in the same time calculate the 26 and 12 averages
            for (i = 0; i < dataTrade.size(); i++) {
                close   =
                    (Float) (((TDoc) dataTrade.get(i)).o[dataincolumn]);
                help[i] = close.floatValue();
            }

            ma1   = STA.avgma(help, 0, help.length, daysF);
            temp2 = STA.avgma(help, 0, help.length, daysS);

            // make difference ma12-ma26
            k = ma1.length;
            j = temp2.length;

            int min = k;

            help = new double[min];
            k--;
            j--;

            for (i = min - 1; i >= 0; i--) {
                help[i] = temp2[j];
                help[i] -= ma1[k];
                k--;
                j--;
            }

            temp2 = STA.avgma(help, 0, help.length, maod);
            ret   = new double[2 * temp2.length];
            j     = help.length - 1;
            k     = temp2.length - 1;

            for (i = ret.length - 1; i > 0; i -= 2) {
                ret[i]     = help[j--];
                ret[i - 1] = temp2[k--];
            }
        } catch (Exception e) {
            if(LoggerNB.debuging){System.out.println("MACD calc exception" + e);
            e.printStackTrace();}
            ret = null;
        }

        return ret;
    }

    /**
     * Method Run
     *
     *
     */
    public void run(java.util.List datasource) {
        runMACD(datasource);
    }

    /*
     * MACD - (Moving average convergency/divergency)
     */
    public void runMACD(java.util.List dataTrade) {
        Integer days1        = (Integer) getParam("FirstMA");
        Integer days2        = (Integer) getParam("SecondMA");
        Integer maondif      = (Integer) getParam("MAOnDiff");
        Integer dataincolumn = (Integer) getParam("DATAINCOLUMN");
        double  ar[]         = computeMACD(dataTrade, days1.intValue(),
                                           days2.intValue(),
                                           maondif.intValue(), 0,
                                           dataincolumn.intValue());

        tdp.clear();

        if (ar != null) {
            for (int i = 0; i < ar.length; i++) {
                tdp.add(new Double(ar[i]));
            }
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
