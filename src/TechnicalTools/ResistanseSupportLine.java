/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package TechnicalTools;

import AnalysisTool.AnalysisTool;
import StatisticBase.STA;
import Utils.LoggerNB;
import Utils.TDoc;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dmitry
 */
public class ResistanseSupportLine  extends AnalysisTool{

    private static double[] getSRP12Vals(double[][] help,  int hlength, double[]pivot,int plength,int start,int days) {
        double[] ret = new double[5];
        try {
            
            ret[0] = STA.avg(pivot, start, days); //med pivot
            ret[1] = STA.findMax(help, 0, start, days); //1 resistance
            ret[2] = STA.findMax(help, 1, start, days); //1 resistance
            ret[3] = STA.findMax(help, 2, start, days); //1 support
            ret[4] = STA.findMax(help, 3, start, days); //2 support
            
        } catch (Exception ex) {
            Logger.getLogger(ResistanseSupportLine.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
 private double  RSL[] =null;
 public double[]getRSL()
 {
     return RSL;
 }
 
public ResistanseSupportLine() {
        super("Resistance&Support Line  by Salomatine Dmitry");
        setParam("PERIOD", new Integer(30));
        setParam("DATA_H_INCOLUMN", new Integer(2));
        setParam("DATA_L_INCOLUMN", new Integer(3));
        setParam("DATA_C_INCOLUMN", new Integer(4));
        setParam("DATA_O_INCOLUMN", new Integer(1));
    }

public ResistanseSupportLine(int period) {
        super("Resistance&Support Line  by Salomatine Dmitry");
        setParam("PERIOD", new Integer(period));
        setParam("DATA_H_INCOLUMN", new Integer(2));
        setParam("DATA_L_INCOLUMN", new Integer(3));
        setParam("DATA_C_INCOLUMN", new Integer(4));
        setParam("DATA_O_INCOLUMN", new Integer(1));
    }

    //~--- methods ------------------------------------------------------------

    public static double[] computeRSL(java.util.List dataTrade, int days,
                                     int start, int dataHincolumn, int dataLincolumn, int dataCincolumn, int dataOincolumn) {
        Float close;
        Float high;
        Float low;
        Float open;

        if ((dataTrade == null) || (dataTrade.size() <= 0)) {
            return null;
        }

        int    size   = dataTrade.size();
        double help[][] = new double[size][4];
        double pivot[] = new double[size];
        double ret[] =new double [5] ;

        try {
            for (int i = 0; i < dataTrade.size(); i++) {
                close  =(Float) (((TDoc) dataTrade.get(i)).o[dataCincolumn]);
                  high   =(Float) (((TDoc) dataTrade.get(i)).o[dataHincolumn]);
                   low   =(Float) (((TDoc) dataTrade.get(i)).o[dataLincolumn]);
                   open   =(Float) (((TDoc) dataTrade.get(i)).o[dataOincolumn]);
                //pivot[i] = (high+low+close)/3.0;//pivot
                pivot[i] = (high+low+close+open)/4.0;//pivot
                help[i] [0]= pivot[i]*2.0-low;//1R
                help[i] [1]= pivot[i]+(high-low);//2R
                help[i] [2]= pivot[i]*2.0-high;//1S
                help[i] [3]= pivot[i]-(high-low);//2s
            }

            if(days>help.length)days=help.length;
            ret = getSRP12Vals(help, help.length,pivot,pivot.length,help.length-days, help.length-1);
        } catch (Exception e) {
            if(LoggerNB.debuging){System.out.println("RSL -" + days + " calculation exception" + e);
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
        runRSL(datasource);
    }

    /*
     * MA - (Moving average)
     */
    public void runRSL(java.util.List dataTrade) {
        tdp.clear();
        Integer days1        = (Integer) getParam("PERIOD");
        Integer dataHincolumn = (Integer) getParam("DATA_H_INCOLUMN");
        Integer dataLincolumn = (Integer) getParam("DATA_L_INCOLUMN");
        Integer dataCincolumn = (Integer) getParam("DATA_C_INCOLUMN");
        Integer dataOincolumn = (Integer) getParam("DATA_O_INCOLUMN");
        RSL    = computeRSL(dataTrade, days1.intValue(), 0,
                                         dataHincolumn.intValue(),dataLincolumn.intValue(),dataCincolumn.intValue(),dataOincolumn.intValue());
        }
}
