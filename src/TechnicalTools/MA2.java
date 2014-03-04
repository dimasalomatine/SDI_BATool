package TechnicalTools;

//~--- non-JDK imports --------------------------------------------------------

import AnalysisTool.AnalysisTool;
import Utils.LoggerNB;
import Utils.TDoc;

import StatisticBase.STA;

//~--- classes ----------------------------------------------------------------

public class MA2 extends AnalysisTool {
    public final static int TREND_UNKNOWN    = 0;
    public final static int TREND_LITTLEUP   = 1;
    public final static int TREND_UPLONG     = 2;
    public final static int TREND_UP         = 3;
    public final static int TREND_LITTLEDOWN = -1;
    public final static int TREND_DOWNLONG   = -2;
    public final static int TREND_DOWN       = -3;

    //~--- fields -------------------------------------------------------------

    public int trend = 0;

    //~--- constructors -------------------------------------------------------

    /**
     * Method MA2
     *
     *
     */
    public MA2() {
        super("2 moving averages by Salomatine Dmitry");
        setParam("ShortPERIOD", new Integer(21));
        setParam("LongPERIOD", new Integer(210));
        setParam("DATAINCOLUMN", new Integer(4));
    }

    //~--- methods ------------------------------------------------------------

    public static double[] computeMA(java.util.List dataTrade, int days,
                                     int start, int dataincolumn) {
        Float close;

        if ((dataTrade == null) || (dataTrade.size() <= 0)) {
            return null;
        }

        int    size   = dataTrade.size();
        double help[] = new double[size];
        double ret[]  = null;

        try {

            // fill the arrays and in the same time calculate the 26 and 12 averages
            for (int i = 0; i < dataTrade.size(); i++) {
                close   =
                    (Float) (((TDoc) dataTrade.get(i)).o[dataincolumn]);
                help[i] = close.floatValue();
            }

            ret = STA.avgma(help, 0, help.length, days);
        } catch (Exception e) {
            if(LoggerNB.debuging){System.out.println("MA-" + days + " calculation exception" + e);
            e.printStackTrace();}
        }

        return ret;
    }

    /**
     * Method Run
     *
     *
     */
    public void run(java.util.List datasource) {
        runMA(datasource);
    }

    /*
     * MA - (Moving average)
     */
    public void runMA(java.util.List dataTrade) {
        Integer days1        = (Integer) getParam("ShortPERIOD");
        Integer days2        = (Integer) getParam("LongPERIOD");
        Integer dataincolumn = (Integer) getParam("DATAINCOLUMN");
        double  dataMA1[]    = computeMA(dataTrade, days1.intValue(), 0,
                                         dataincolumn.intValue());
        double dataMA2[] = computeMA(dataTrade, days2.intValue(), 0,
                                     dataincolumn.intValue());

        tdp.clear();

        // here make desision about trend
        if ((dataMA1 != null) && (dataMA2 != null)) {
            Float close = (Float) (((TDoc) dataTrade.get(dataTrade.size()
                              - 1)).o[dataincolumn.intValue()]);
            double lastShort      = dataMA1[dataMA1.length - 1],
                   lastShortPrev  = dataMA1[dataMA1.length - 2],
                   lastLong       = dataMA2[dataMA2.length - 1],
                   lastLongPrev   = dataMA2[dataMA2.length - 2],
                   lasttradepoint = close.floatValue();

            System.out.println("Detect MA Buye/Sell 1 pass");

            boolean found = false;

            if ((lastShort > lastShortPrev)
                    && ((lastLong < lastShort)
                        && (lastLong > lastShortPrev))) {

                // buye point
                tdp.add("Buye");
                found = true;
            } else if ((lastShort < lastShortPrev)
                       && ((lastLong > lastShort)
                           && (lastLong < lastShortPrev))) {

                // sell point
                tdp.add("Sell");
                found = true;
            }

            if (found) {
                tdp.add(new Double(lastShort));
                tdp.add(new Double(lastShortPrev));
                tdp.add(new Double(lastLong));
                tdp.add(new Double(lastLongPrev));
            } else {
                lastShort     = dataMA1[dataMA1.length - 2];
                lastShortPrev = dataMA1[dataMA1.length - 3];
                lastLong      = dataMA2[dataMA2.length - 2];
                lastLongPrev  = dataMA2[dataMA2.length - 3];
                System.out.println("Detect MA Buye/Sell 2 pass");

                if ((lastShort > lastShortPrev)
                        && ((lastLong < lastShort)
                            && (lastLong > lastShortPrev))) {

                    // buye point
                    tdp.add("Buye");
                    found = true;
                } else if ((lastShort < lastShortPrev)
                           && ((lastLong > lastShort)
                               && (lastLong < lastShortPrev))) {

                    // sell point
                    tdp.add("Sell");
                    found = true;
                }

                if (found) {
                    tdp.add(new Double(lastShort));
                    tdp.add(new Double(lastShortPrev));
                    tdp.add(new Double(lastLong));
                    tdp.add(new Double(lastLongPrev));
                }
            }

            lastShort = dataMA1[dataMA1.length - 1];
            lastLong  = dataMA2[dataMA2.length - 1];

            if ((lasttradepoint > lastLong) && (lasttradepoint > lastShort)) {
                tdp.add("TREND_UP");
            } else if ((lasttradepoint < lastLong)
                       && (lasttradepoint < lastShort)) {
                tdp.add("TREND_DOWN");
            } else if (lasttradepoint > lastLong) {
                tdp.add("TREND_UPLONG");
            } else if (lasttradepoint > lastShort) {
                tdp.add("TREND_LITTLEUP");
            }

            tdp.add(new Double(lastShort));
            tdp.add(new Double(lastLong));
            tdp.add(new Double(lasttradepoint));
            System.out.println(tdp);
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
