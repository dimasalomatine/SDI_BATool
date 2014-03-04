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
public class MarkovitchFolderModel extends AnalysisTool {
    private static double minlimit=0.0000001;
 
 private double  MFM[] =null;
 public double[]getMFM()
 {
     return MFM;
 }
 
public MarkovitchFolderModel() {
        super("Markovitch Folder Model   by Salomatine Dmitry");
        setParam("PERIOD", new Integer(30));
        setParam("WORKON", new String("HLCO-AVG"));
        setParam("DATA_H_INCOLUMN", new Integer(2));
        setParam("DATA_L_INCOLUMN", new Integer(3));
        setParam("DATA_C_INCOLUMN", new Integer(4));
        setParam("DATA_O_INCOLUMN", new Integer(0));
    }

public MarkovitchFolderModel(int period) {
        super("Markovitch Folder Model  by Salomatine Dmitry");
        setParam("PERIOD", new Integer(period));
        setParam("WORKON", new String("HLCO-AVG"));
        setParam("DATA_H_INCOLUMN", new Integer(2));
        setParam("DATA_L_INCOLUMN", new Integer(3));
        setParam("DATA_C_INCOLUMN", new Integer(4));
        setParam("DATA_O_INCOLUMN", new Integer(1));
    }
public MarkovitchFolderModel(int period,String valmodel) {
        super("Markovitch Folder Model  by Salomatine Dmitry");
        setParam("PERIOD", new Integer(period));
        setParam("WORKON", new String(valmodel));
        setParam("DATA_H_INCOLUMN", new Integer(2));
        setParam("DATA_L_INCOLUMN", new Integer(3));
        setParam("DATA_C_INCOLUMN", new Integer(4));
        setParam("DATA_O_INCOLUMN", new Integer(1));
    }

    //~--- methods ------------------------------------------------------------

    public static double[] compute(java.util.List dataTrade, int days,
            int dataHincolumn, int dataLincolumn, int dataCincolumn, int dataOincolumn,String On) {
        Float close;
        Float high;
        Float low;
        Float open;

        if ((dataTrade == null) || (dataTrade.size() <= 0)) {
            return null;
        }
        int start=dataTrade.size()-days;
        int    size   = days;
        double val[] = new double[size];//stock values
        double max[] = new double[size];//max on val 0 to i
        double min[] = new double[size];//min on val 0 to i
        double std[] = new double[size];// std on val 0 to i
        double A[] = new double[size];//min+sdt/max-std* 0.01
        double AE[] = new double[size];//expected Rj=A*val
        double WOE[] = new double[size-1];//will on Rj
        double Real[] = new double[size-1];//real earn
        double sumreal=0.0;//sum real earn
        double EVR[] = new double[size-1];//earn value risk =expected /real
        double evrtotal=0.0;//min for lan
        
        double PIJ[] = new double[size-1];
        double EXPIJ[] = new double[size-1];
        double Ei=0.0;
        double sumEi=0.0;
        double dispdata[] = new double[size-1];
        double dispertion=0.0;
        
        double ret[] =new double [5] ;
        ret[0]=0;
        int loci=0;
        
        try {
            for (int i = start; i < dataTrade.size(); i++,loci++) {
                close  =(Float) (((TDoc) dataTrade.get(i)).o[dataCincolumn]);
                  high   =(Float) (((TDoc) dataTrade.get(i)).o[dataHincolumn]);
                   low   =(Float) (((TDoc) dataTrade.get(i)).o[dataLincolumn]);
                   open   =(Float) (((TDoc) dataTrade.get(i)).o[dataOincolumn]);
                   if(On.equalsIgnoreCase("HLCO-AVG"))
                   {  
                val[loci] = (high+low+close+open)/4.0/100.0;//avg
                   }
                   else if(On.equalsIgnoreCase("ONCLOSE"))
                   {
                     val[loci] = close/100.0;//close
                   }
                   else
                   {
                    return ret;
                   }
                   if(loci>0)
                   {
                    max[loci-1]=STA.findMax(val,0,loci);
                    min[loci-1]=STA.findMin(val,0,loci);
                    double avg=STA.avg(val, 0, loci);
                    std[loci-1]=STA.std_dev(STA.variance(val,avg,0,loci));
                    A[loci-1]=((min[loci-1]+std[loci-1])/(max[loci-1]-std[loci-1]))*0.01f;
                    AE[loci-1]=val[loci-1]*(A[loci-1]+1.0);
                   }
            }
            max[loci-1]=max[loci-2];
            min[loci-1]=min[loci-2];
           std[loci-1]=std[loci-2];
           //if ((max[loci-1]-std[loci-1])<minlimit) A[loci-1]=((min[loci-1]+std[loci-1])/minlimit)*0.01f;
           //else
           A[loci-1]=((min[loci-1]+std[loci-1])/(max[loci-1]-std[loci-1]))*0.01f;
           AE[loci-1]=val[loci-1]*(A[loci-1]+1.0);
           int j;
            for (j=1;j<size-1;j++)
            {
                    WOE[j-1]=val[j]-AE[j-1];
                    Real[j-1]=val[j]-val[j-1];
                   // if(Real[j-1]<minlimit)EVR[j-1]=WOE[j-1]/minlimit;
                    //else
                    EVR[j-1]=WOE[j-1]/Real[j-1];
            }
           WOE[j-1]=val[j]-AE[j-1];
            Real[j-1]=val[j]-val[j-1];
            //if(Real[j-1]<minlimit)EVR[j-1]=WOE[j-1]/minlimit;
            //else
            EVR[j-1]=WOE[j-1]/Real[j-1];
            for (j=0;j<size-1;j++)
            {
                evrtotal=evrtotal+EVR[j];
            }
            for( j=0;j<size-1;j++)
            {
            //if(evrtotal<minlimit)PIJ[j]=EVR[j]/minlimit;
            //else
                PIJ[j]=EVR[j]/evrtotal;
                    EXPIJ[j]=A[j]*PIJ[j];
                            Ei=Ei+EXPIJ[j];
                            sumreal=sumreal+Real[j];
            }
            //if(Ei<minlimit)ret[0]=sumreal/minlimit;
            //else
            ret[0]=sumreal/Ei;
            ret[1]=sumreal;
            ret[2]=Ei;
        } catch (Exception e) {
            if(LoggerNB.debuging){System.out.println("MARKOVICH -" + days + " calculation exception" + e);
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
        runone(datasource);
    }

    /*
     * MA - (Moving average)
     */
    public void runone(java.util.List dataTrade) {
        tdp.clear();
        Integer days1        = (Integer) getParam("PERIOD");
        Integer dataHincolumn = (Integer) getParam("DATA_H_INCOLUMN");
        Integer dataLincolumn = (Integer) getParam("DATA_L_INCOLUMN");
        Integer dataCincolumn = (Integer) getParam("DATA_C_INCOLUMN");
        Integer dataOincolumn = (Integer) getParam("DATA_O_INCOLUMN");
        String On = (String) getParam("WORKON");
        MFM    = compute(dataTrade, days1.intValue(), 
                                         dataHincolumn.intValue(),dataLincolumn.intValue(),dataCincolumn.intValue(),dataOincolumn.intValue(),On);
        }
}

