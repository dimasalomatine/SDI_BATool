package AdvancedAnalisys;

//~--- non-JDK imports --------------------------------------------------------

import Utils.TDoc;

import AnalysisTool.AnalysisTool;

import StatisticBase.STA;

//~--- classes ----------------------------------------------------------------

public class Regression extends AnalysisTool {

    /**
     * Method Regression
     *
     *
     */
    public Regression() {
        super("Regression test by Salomatine Dmitry");
        setParam("DATAXINCOLUMN", new Integer(4));
        setParam("DATAYINCOLUMN", new Integer(5));
        setParam("ComputeFROM", new Integer(0));
        setParam("To", new Integer(0));
    }

    public Regression(java.util.List dataTrade) {
        super("Regression test by Salomatine Dmitry");
        setParam("DATAXINCOLUMN", new Integer(4));
        setParam("DATAYINCOLUMN", new Integer(5));
        setParam("ComputeFROM", new Integer(0));
        setParam("To", new Integer(dataTrade.size()));
    }

    public Regression(int from, int to) {
        super("Regression test by Salomatine Dmitry");
        setParam("DATAXINCOLUMN", new Integer(4));
        setParam("DATAYINCOLUMN", new Integer(5));
        setParam("ComputeFROM", new Integer(from));
        setParam("To", new Integer(to));
    }

    //~--- methods ------------------------------------------------------------

    public static double[] computeRegression(java.util.List datasource,
            int xcol, int ycol) {
        return computeRegression(datasource, 0, datasource.size(), xcol, ycol);
    }

    public static double[] computeRegression(java.util.List datasource,
            int from, int to, int xcol, int ycol) {
        double ar[] = new double[2];

        ar[0] = 0;
        ar[1] = 0;

        if ((datasource == null) || (datasource.size() <= 0) || (from < 0)
                || (to < 0) || (to <= from) || (to > datasource.size())) {
            return ar;
        }

        double sxy  = 0,
               sx   = 0,
               sy   = 0,
               sxp2 = 0;
        Float  x, y;

        for (int i = from; i < to; i++) {
            x    = (Float) (((TDoc) datasource.get(i)).o[xcol]);
            y    = (Float) (((TDoc) datasource.get(i)).o[ycol]);
            sxy  += x.floatValue() * y.floatValue();
            sx   += x.floatValue();
            sy   += y.floatValue();
            sxp2 += x.floatValue() * x.floatValue();
        }

        return STA.regression(sxy, sx, sy, sxp2, to);
    }

    /**
     * Method Run
     *
     *
     */
    public void run(java.util.List datasource) {
        runRegression(datasource);
    }

    public void runRegression(java.util.List dataTrade) {
        Integer dataXincolumn = (Integer) getParam("DATAXINCOLUMN");
        Integer dataYincolumn = (Integer) getParam("DATAYINCOLUMN");
        Integer from          = (Integer) getParam("ComputeFROM");
        Integer to            = (Integer) getParam("To");
        double  ar[]          = computeRegression(dataTrade, from.intValue(),
                                    to.intValue(), dataXincolumn.intValue(),
                                    dataYincolumn.intValue());

        tdp.clear();

        for (int i = 0; i < ar.length; i++) {
            tdp.add(new Double(ar[i]));
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
