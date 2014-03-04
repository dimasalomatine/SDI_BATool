
import Utils.TDoc;
import Utils.Utils;
import java.io.Serializable;

public class MinMax implements Serializable, MMMode
{
    double[][] mm = new double[2][];//0 for price 1 for amount
    public void setMinMax (int arr, double min, double max)
    {
        mm[arr][0]=min;
        mm[arr][1]=max;
    }
    public double getMinMax (int arr, int what)
    {
        return mm[arr][what];
    }
    public void initMinMax ()
    {
        for (int i = 0; i<2; i++)
        {
            mm[i] = new double[2];
            mm[i][0]=100000.f;
            mm[i][1]=-100000.f;
        }
    }
    //detects min max bounds of open high low close values
    //also detects bounds of amounts
    public void detectminmaxOHLCA (java.util.List dataTrade,int mode)
    {
        int i, j;
        Float p;
        if(mode==RESET)initMinMax();
        for (i=0; i < dataTrade.size(); i++)
        {
            if (mode==DETECTMMPRICE)
                for (j=1; j<=4; j++)
                {
                    p = (Float) (((TDoc) dataTrade.get(i)).o[j]);
                    mm[0][0] = Utils.min(p.floatValue(),mm[MMIndex.PRICE][0]);
                    mm[0][1] = Utils.max(p.floatValue(),mm[MMIndex.PRICE][1]);
                }
            if (mode==DETECTMMVOLUME)
            {
                p = (Float) (((TDoc) dataTrade.get(i)).o[5]);
                mm[1][0] = Utils.min(p.floatValue(),mm[MMIndex.VOLUME][0]);
                mm[1][1] = Utils.max(p.floatValue(),mm[MMIndex.VOLUME][1]);
            }
        }	
    }
}