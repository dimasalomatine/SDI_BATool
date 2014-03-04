package AdvancedAnalisys;

//~--- non-JDK imports --------------------------------------------------------

import Utils.TDoc;

import AnalysisTool.AnalysisTool;

import StatisticBase.STA;

//~--- classes ----------------------------------------------------------------

public class BAndS extends AnalysisTool {

   public static int PUT=1;
   public static int CALL=2;

    /**
     * Method Black-Scholes
     * S= Stock price
     * X=Strike price
     * T=Years to maturity
     * r= Risk-free rate
     * v=Volatility
     *
     *
     */
    public BAndS(int CallPutFlag,double T,double r,double v,double S,double X) {
        super("Black-Scholes by Salomatine Dmitry");
        setParam("CALLPUTFLAG",new Integer(CallPutFlag));
        setParam("DATAXINCOLUMN", new Integer(4));
        setParam("STOCKPRICEINCOLUMN", new Integer(5));
        setParam("STRIKEPRICEINCOLUMN", new Integer(6));
        setParam("YEARSTOMATURITY", new Double(T));
        setParam("RISKFREERATE", new Double(r));
        setParam("VOLATILITY", new Double(v));
         setParam("STOCKPRICE", new Double(S));
          setParam("STRIKEPRICE", new Double(X));
        setParam("ComputeFROM", new Integer(-1));
    }
    public BAndS(int CallPutFlag,double T,double r,double v) {
        super("Black-Scholes by Salomatine Dmitry");
        setParam("CALLPUTFLAG",new Integer(CallPutFlag));
        setParam("DATAXINCOLUMN", new Integer(4));
        setParam("STOCKPRICEINCOLUMN", new Integer(5));
        setParam("STRIKEPRICEINCOLUMN", new Integer(6));
        setParam("YEARSTOMATURITY", new Double(T));
        setParam("RISKFREERATE", new Double(r));
        setParam("VOLATILITY", new Double(v));
        setParam("ComputeFROM", new Integer(-1));
    }

    public BAndS(int CallPutFlag,double T,double r,double v,java.util.List dataTrade) {
       super("Black-Scholes by Salomatine Dmitry");
        setParam("CALLPUTFLAG",new Integer(CallPutFlag));
        setParam("DATAXINCOLUMN", new Integer(4));
        setParam("STOCKPRICEINCOLUMN", new Integer(5));
        setParam("STRIKEPRICEINCOLUMN", new Integer(6));
         setParam("YEARSTOMATURITY", new Double(T));
        setParam("RISKFREERATE", new Double(r));
        setParam("VOLATILITY", new Double(v));
        setParam("ComputeFROM", new Integer(dataTrade.size()-1));
        
    }

    //~--- methods ------------------------------------------------------------

    public static double[] computeBAndS(java.util.List datasource,  int from, int xcol, int ycol,int ycol2,int CallPutFlag,double T,double r,double v,double __S,double __X)
{
double d1, d2;
double ret[]=new double[5];

double S=0,  X=0;

 ret[0]=-99999;
    ret[1]=-99999;

    if(from<0)
    {
       S=__S;
       X=__X;
    }
    
   if (from>=0)
                {
                   if( datasource == null || datasource.size() <= 0 || from > datasource.size()-1)  return ret;
                   else
                   {
                      S    = (Float) (((TDoc) datasource.get(from)).o[ycol]);
           X    = (Float) (((TDoc) datasource.get(from)).o[ycol2]);
                   }
                }
       

d1=(Math.log(S/X)+(r+v*v/2)*T)/(v*Math.sqrt(T));
d2=d1-v*Math.sqrt(T);
 ret[0]=d1;
    ret[1]=d2;

if (CallPutFlag==BAndS.CALL)
{

    ret[2]=S*CND(d1)-X*Math.exp(-r*T)*CND(d2);
    ret[3]=CND(d1);
    ret[4]=CND(d2);
}
else if (CallPutFlag==BAndS.PUT)
{
    ret[2]=X*Math.exp(-r*T)*CND(-d2)-S*CND(-d1);
    ret[0]*=-1;
     ret[1]*=-1;
     ret[3]=CND(-d1);
    ret[4]=CND(-d2);
}
    else
{
    ret[2]=-99999;
    }
    
    return ret;
}

    // The cumulative normal distribution function
public static  double CND(double X)
{
double L, K, w ;
double a1 = 0.31938153, a2 = -0.356563782, a3 = 1.781477937, a4 = -1.821255978, a5 = 1.330274429;

L = Math.abs(X);
K = 1.0 / (1.0 + 0.2316419 * L);
w = 1.0 - 1.0 / Math.sqrt(2.0 * Math.PI) * Math.exp(-L *L / 2) * (a1 * K + a2 * K *K + a3
* Math.pow(K,3) + a4 * Math.pow(K,4) + a5 * Math.pow(K,5));

if (X < 0.0)
{
w= 1.0 - w;
}
return w;
}

    /**
     * Method Run
     *
     *
     */
    public void run(java.util.List datasource) {
        runBAndS(datasource);
    }

    public void runBAndS(java.util.List dataTrade) {
          Integer CallPutFlag = (Integer) getParam("CALLPUTFLAG");
        Integer dataXincolumn = (Integer) getParam("DATAXINCOLUMN");
        Integer dataYincolumn = (Integer) getParam("STOCKPRICEINCOLUMN");
         Integer dataYincolumn2 = (Integer) getParam("STRIKEPRICEINCOLUMN");
          Double T = (Double) getParam("YEARSTOMATURITY");
           Double v = (Double) getParam("VOLATILITY");
            Double r = (Double) getParam("RISKFREERATE");
             Double S = (Double) getParam("STOCKPRICE");
            Double X = (Double) getParam("STRIKEPRICE");
            Integer from          = (Integer) getParam("ComputeFROM");

        double  ar[]          = computeBAndS(dataTrade,
                                    from.intValue(),
                                    dataXincolumn.intValue(),
                                    dataYincolumn.intValue(),
                                    dataYincolumn2.intValue(),
                                    CallPutFlag.intValue(),
                                    T.doubleValue(),
                                    r.doubleValue(),
                                    v.doubleValue(),
                                    S.doubleValue(),
                                    X.doubleValue());

        tdp.clear();

        for (int i = 0; i < ar.length; i++) {
            tdp.add(new Double(ar[i]));
        }
    }

    public static void main(String []args)
    {
        int CallPutFlag=BAndS.CALL;
        double T=0.5, r=0.05, v=0.20, S=110, X=100;
       BAndS bs=new BAndS( CallPutFlag, T, r, v, S, X);
       bs.runBAndS(null);
       bs.dumpParams();
       bs.dumpResults();
      // expect
               /*{STRIKEPRICE=100.0, YEARSTOMATURITY=0.5, ToolName=Black-Scholes by Salomatine Dmitry, ComputeFROM=-1, STOCKPRICEINCOLUMN=5, STRIKEPRICEINCOLUMN=6, DATAXINCOLUMN=4, VOLATILITY=0.2, RISKFREERATE=0.05, STOCKPRICE=110.0, CALLPUTFLAG=2}
0.921
0.780
14.07*/
       CallPutFlag=BAndS.PUT;
       BAndS bs2=new BAndS( CallPutFlag, T, r, v, S, X);
       bs2.runBAndS(null);
       bs2.dumpParams();
       bs2.dumpResults();
       //expect
       /*
        {STRIKEPRICE=100.0, YEARSTOMATURITY=0.5, ToolName=Black-Scholes by Salomatine Dmitry, ComputeFROM=-1, STOCKPRICEINCOLUMN=5, STRIKEPRICEINCOLUMN=6, DATAXINCOLUMN=4, VOLATILITY=0.2, RISKFREERATE=0.05, STOCKPRICE=110.0, CALLPUTFLAG=1}
-0.921
-0.780
1.60
        */
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
