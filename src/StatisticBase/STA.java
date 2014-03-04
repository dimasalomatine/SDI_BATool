package StatisticBase;

import Utils.LoggerNB;

public class STA {

    /**
     * Method squareAvg
     *
     *
     * @return
     *
     */
    public static final int CNT_NEGATIVE = 1;
    public static final int CNT_POSITIVE = 2;
    public static final int CNT_BOOTH = 3;

    //~--- constructors -------------------------------------------------------
    /**
     * Method STA
     */
    public STA() {
        // TODO: Add your code here
    }

    //~--- methods ------------------------------------------------------------
    /**
     * Method AVG
     *
     *
     * @return
     *
     */
    public static double avg(double[] data) throws Exception {
        double ret = 0.0f;

        if (data.length == 0) {
            throw new Exception("No data to calculate average");
        }
        int d = 0;
        for (int i = 0; i < data.length; i++) {
            d++;
            if (!Double.isNaN(data[i]) && !Double.isInfinite(data[i])) {
                ret += data[i];
            } else {
                d--;
            }
        }

        return ret / (double) d;
    }

    /**
     * Method AVG
     *
     *
     * @return
     *
     */
    public static double avg(float[] data) throws Exception {
        double ret = 0.0f;

        if (data.length == 0) {
            throw new Exception("No data to calculate average");
        }
        int d = 0;
        for (int i = 0; i < data.length; i++) {
            d++;
            if (!Float.isInfinite(data[i]) && !Float.isNaN(data[i])) {
                ret += data[i];
            } else {
                d--;
            }
        }

        return ret / (double) d;
    }

    public static double avg(double[] data, int cnt) throws Exception {
        double ret = 0.0f;
        int i;

        if (data.length == 0) {
            throw new Exception("No data to calculate average");
        }
        int d = 0;
        for (i = 0; (i < cnt) && (i < data.length); i++) {
            d++;
            if (!Double.isInfinite(data[i]) && !Double.isNaN(data[i])) {
                ret += data[i];
            } else {
                d--;
            }
        }

        return ret / (double) d;
    }

    /**
     * Method AVG
     *
     *
     * @return
     *
     */
    public static double avg(float[] data, int cnt) throws Exception {
        double ret = 0.0f;
        int i;

        if (data.length == 0) {
            throw new Exception("No data to calculate average");
        }
        int d = 0;
        for (i = 0; (i < cnt) && (i < data.length); i++) {
            d++;
            if (!Float.isInfinite(data[i]) && !Float.isNaN(data[i])) {
                ret += data[i];
            } else {
                d--;
            }
        }

        return ret / (double) d;
    }

    public static double avg(double[] data, int from, int to)
            throws Exception {
        double ret = 0.0f;
        int i,
                cnt = 0;
        double dcnt;

        if (data.length == 0) {
            throw new Exception("No data to calculate average");
        }

        if ((from > to) || (from < 0)) {
            throw new Exception("Wrong limits to calculate average");
        }
        int d = 0;
        for (i = from; (i <= to) && (i < data.length); i++) {
            d++;
            if (!Double.isInfinite(data[i]) && !Double.isNaN(data[i])) {
                ret += data[i];
            } else {
                d--;
            }
            cnt++;
        }
        if (d == 0) {
            dcnt = 0.0001;
        } else {
            dcnt = d;
        }
        return ret / dcnt;
    }

    /**
     * Method AVG
     *
     *
     * @return
     *
     */
    public static double avg(float[] data, int from, int to) throws Exception {
        double ret = 0.0f;
        int i,
                cnt = 0;
        double dcnt;
        if (data.length == 0) {
            throw new Exception("No data to calculate average");
        }

        if ((from > to) || (from < 0)) {
            throw new Exception("Wrong limits to calculate average");
        }

        int d = 0;
        for (i = from; (i <= to) && (i < data.length); i++) {
            d++;
            if (!Float.isInfinite(data[i]) && !Float.isNaN(data[i])) {
                ret += data[i];
            }
            cnt++;
        }

        if (d == 0) {
            dcnt = 0.0001;
        } else {
            dcnt = d;
        }
        return ret / dcnt;
    }

    /**
     * Method avgW
     *
     *
     * @return
     *
     */
    public static double avgW(double[] data, double[] weights)
            throws Exception {
        double ret = 0.0f;
        double totalw = 0.0f;

        if (data.length == 0) {
            throw new Exception("No data to calculate average");
        }

        if (weights.length == 0) {
            throw new Exception("No weights to calculate average");
        }

        if (weights.length != data.length) {
            throw new Exception("Data and weights different size");
        }

        int i;

        for (i = 0; i < weights.length; i++) {
            totalw += weights[i];
        }

        if (totalw == 0.0f) {
            throw new Exception("The total weights is zero");
        }

        for (i = 0; i < data.length; i++) {
            ret += data[i] * (weights[i] / totalw);
        }

        return ret / totalw;
    }

    public static double[] avgma(double[] data, int from, int to, int count)
            throws Exception {
        if (data.length == 0) {
            throw new Exception("No data to calculate average");
        }

        if ((from > to) || (from < 0)) {
            throw new Exception("Wrong limits to calculate average");
        }

        // if(to>data.length)throw new Exception("Wrong limits to calculate average");
        if ((count <= 0) || (count >= data.length)) {
            throw new Exception("nothing to count");
        }

        double ret[] = new double[data.length - count + 1];

        for (int i = from, j = 0; i < to; i++) {
            try {
                if (i >= (count - 1)) {
                    ret[j++] = avg(data, i - (count - 1), i + 1);
                }
            } catch (Exception e) {
                if (LoggerNB.debuging) {
                    System.out.println(e);
                }
            }
        }

        return ret;
    }

    public static double[] avgma(float[] data, int from, int to, int count)
            throws Exception {
        if (data.length == 0) {
            throw new Exception("No data to calculate average");
        }

        if ((from > to) || (from < 0)) {
            throw new Exception("Wrong limits to calculate average");
        }

        // if(to>data.length)throw new Exception("Wrong limits to calculate average");
        if ((count <= 0) || (count >= data.length)) {
            throw new Exception("nothing to count");
        }

        double ret[] = new double[data.length - count + 1];

        for (int i = from, j = 0; i < to; i++) {
            try {
                if (i >= (count - 1)) {
                    ret[j++] = avg(data, i - (count - 1), i + 1);
                }
            } catch (Exception e) {
                if (LoggerNB.debuging) {
                    System.out.println(e);
                }
            }
        }

        return ret;
    }

    /**
     * Method REGRESSION
     *
     *
     * @return
     *
     */
    public static double[] regression(double sxy, double sx, double sy,
            double sxp2, int n) {

        // y=ax+b
        double slope, // a
                intercept;    // b

        slope = (n * sxy - sx * sy) / (n * sxp2 - sx * sx);
        intercept = (sy - slope * sx) / n;

        double ar[] = new double[2];

        ar[0] = slope;
        ar[1] = intercept;

        return ar;
    }

    public static double squareAvg(double[] data, int from, int to, int cnt)
            throws Exception {
        double ret = 0.0f;
        int i,
                a = 0;
        ;

        if (data.length == 0) {
            throw new Exception("No data to calculate average");
        }

        if ((from > to) || (from < 0)) {
            throw new Exception("Wrong limits to calculate average");
        }

        for (i = from; (i <= to) && (i < data.length); i++) {
            switch (cnt) {
                case CNT_NEGATIVE:
                    if (data[i] < 0.0f) {
                        ret += data[i];
                        a++;
                    }

                    break;

                case CNT_POSITIVE:
                    if (data[i] >= 0.0f) {
                        ret += data[i];
                        a++;
                    }

                    break;

                case CNT_BOOTH:
                    ret += data[i];
                    a++;

                    break;

                default:
                    break;
            }
            ;
        }

        if (a == 0) {
            if (LoggerNB.debuging) {
                System.out.println("no data points summed");
            }

            return 0.0f;
        }

        return ret / (double) a;
    }

    public static double squareAvg(float[] data, int from, int to, int cnt)
            throws Exception {
        double ret = 0.0f;
        int i,
                a = 0;
        ;

        if (data.length == 0) {
            throw new Exception("No data to calculate average");
        }

        if ((from > to) || (from < 0)) {
            throw new Exception("Wrong limits to calculate average");
        }

        for (i = from; (i <= to) && (i < data.length); i++) {
            switch (cnt) {
                case CNT_NEGATIVE:
                    if (data[i] < 0.0f) {
                        ret += data[i];
                        a++;
                    }

                    break;

                case CNT_POSITIVE:
                    if (data[i] >= 0.0f) {
                        ret += data[i];
                        a++;
                    }

                    break;

                case CNT_BOOTH:
                    ret += data[i];
                    a++;

                    break;

                default:
                    break;
            }
            ;
        }

        if (a == 0) {
            if (LoggerNB.debuging) {
                System.out.println("no data points summed");
            }

            return 0.0f;
        }

        return ret / (double) a;
    }

    public static double squareAvgRSIVer(double[] data, int from, int to,
            int cnt, double zv)
            throws Exception {
        double ret = 0.0f;
        double divby = to - from + 1.0f;
        int i,
                a = 0;
        double zero = zv;    // zero=0.000001f;

        if (data.length == 0) {
            throw new Exception("No data to calculate average");
        }

        if ((from > to) || (from < 0)) {
            throw new Exception("Wrong limits to calculate average");
        }

        for (i = from; (i <= to) && (i < data.length); i++) {
            switch (cnt) {
                case CNT_NEGATIVE:
                    if (data[i] <= -zero) {
                        ret += data[i];
                        a++;
                    }

                    break;

                case CNT_POSITIVE:
                    if (data[i] >= zero) {
                        ret += data[i];
                        a++;
                    }

                    break;

                case CNT_BOOTH:
                    if ((data[i] <= -zero) || (data[i] >= zero)) {
                        ret += data[i];
                        a++;
                    }

                    break;

                default:
                    break;
            }
            ;
        }

        if (divby == 0.0f) {
            if (LoggerNB.debuging) {
                System.out.println("no days have specified");
            }

            return 0.0f;
        }

        return ret / divby;
    }

    //here some std statistic functions
    public static double variance(double[] list, double mean) throws Exception {
        double sum = 0;
        double sum1 = 0;
        double variance = 0;
        if (list.length == 0) {
            throw new Exception("No data to calculate variance");
        }
        for (int i = 0; i < list.length; i++) {
            sum = list[i] - mean;
            sum1 += Math.pow(sum, 2);
        }
        variance = (1 / (double) list.length) * sum1;

        return (variance);
    }

    public static double variance(double[] list, double mean, int from, int to) throws Exception {
        double sum = 0;
        double sum1 = 0;
        double variance = 0;
        if (list.length == 0) {
            throw new Exception("No data to calculate variance");
        }

        if ((from > to) || (from < 0)) {
            throw new Exception("Wrong limits to calculate average");
        }

        for (int i = from; (i < to) && (i < list.length); i++) {
            sum = list[i] - mean;
            sum1 += Math.pow(sum, 2);
        }
        variance = (1 / (double) list.length) * sum1;

        return (variance);
    }

    public static double std_dev_old(double variance) {
        double loop = 0;
        double std_dev = 0;
        double EPSILON = 0.05;
        double z = variance / 2.0;
        while (Math.abs(z * z - variance) > +EPSILON) {
            z = (variance / z + z) / 2.0;
            loop++;
        }
        std_dev = z;
        return (std_dev);
    }

    public static double std_dev(double variance) {
        double std_dev = 0;
        std_dev = Math.sqrt(variance);
        return (std_dev);
    }

    public static double findMin(double[] data, int from, int to)
            throws Exception {
        double ret = 0.0f;
        int i;

        if (data.length == 0) {
            throw new Exception("No data to calculate  minimum");
        }

        if ((from > to) || (from < 0)) {
            throw new Exception("Wrong limits to calculate average");
        }

        for (i = from; (i <= to) && (i < data.length); i++) {
            if (i == from) {
                ret = data[i];
                continue;
            }
            if (data[i] < ret) {
                ret = data[i];
            }
        }
        return ret;
    }

    public static double findMin(double[][] data, int dataincolumn, int from, int to)
            throws Exception {
        double ret = 0.0f;
        int i;

        if (data.length == 0) {
            throw new Exception("No data to calculate  minimum");
        }

        if ((from > to) || (from < 0)) {
            throw new Exception("Wrong limits to calculate average");
        }
        if (dataincolumn < 0 || dataincolumn > data[from].length - 1) {
            throw new Exception("Column not valid to calculate  minimum");
        }
        for (i = from; (i < to) && (i < data.length); i++) {
            if (i == from) {
                ret = data[i][dataincolumn];
                continue;
            }
            if (data[i][dataincolumn] < ret) {
                ret = data[i][dataincolumn];
            }
        }
        return ret;
    }

    public static double findMax(double[] data, int from, int to)
            throws Exception {
        double ret = 0.0f;
        int i;

        if (data.length == 0) {
            throw new Exception("No data to calculate  minimum");
        }

        if ((from > to) || (from < 0)) {
            throw new Exception("Wrong limits to calculate average");
        }

        for (i = from; (i <= to) && (i < data.length); i++) {
            if (i == from) {
                ret = data[i];
                continue;
            }
            if (data[i] > ret) {
                ret = data[i];
            }
        }
        return ret;
    }

    public static double findMax(double[][] data, int dataincolumn, int from, int to)
            throws Exception {
        double ret = 0.0f;
        int i;

        if (data.length == 0) {
            throw new Exception("No data to calculate  minimum");
        }

        if ((from > to) || (from < 0)) {
            throw new Exception("Wrong limits to calculate average");
        }
        if (dataincolumn < 0 || dataincolumn > data[from].length - 1) {
            throw new Exception("Column not valid to calculate  minimum");
        }
        for (i = from; (i < to) && (i < data.length); i++) {
            if (i == from) {
                ret = data[i][dataincolumn];
                continue;
            }
            if (data[i][dataincolumn] > ret) {
                ret = data[i][dataincolumn];
            }
        }
        return ret;
    }

    public static double geomean(double[] data, int from, int to)
            throws Exception {
        double ret = 0.0f;
        int i,
                cnt = 0;
        double dcnt;

        if (data.length == 0) {
            throw new Exception("No data to calculate average");
        }

        if ((from > to) || (from < 0)) {
            throw new Exception("Wrong limits to calculate average");
        }

        for (i = from; (i < to) && (i < data.length); i++) {
            ret += Math.log10(data[i]);
            cnt++;
        }
        if (cnt == 0) {
            dcnt = 0.0001;
        } else {
            dcnt = cnt;
        }
        return Math.pow(10.0, ret / dcnt);
    }
    private static final double chiP[]={0.95, 	0.90, 	0.80, 	0.70, 	0.50, 	0.30, 	0.20 ,	0.10, 	0.05 ,	0.01, 	0.001};
    private static final double chitable[][] = {
        {0.004, 0.02, 0.06, 0.15, 0.46, 1.07, 1.64, 2.71, 3.84, 6.64, 10.83},
        {0.10, 0.21, 0.45, 0.71, 1.39, 2.41, 3.22, 4.60, 5.99, 9.21, 13.82},
        {0.35, 0.58, 1.01, 1.42, 2.37, 3.66, 4.64, 6.25, 7.82, 11.34, 16.27},
        {0.71, 1.06, 1.65, 2.20, 3.36, 4.88, 5.99, 7.78, 9.49, 13.28, 18.47},
        {1.14, 1.61, 2.34, 3.00, 4.35, 6.06, 7.29, 9.24, 11.07, 15.09, 20.52},
        {1.63, 2.20, 3.07, 3.83, 5.35, 7.23, 8.56, 10.64, 12.59, 16.81, 22.46},
        {2.17, 2.83, 3.82, 4.67, 6.35, 8.38, 9.80, 12.02, 14.07, 18.48, 24.32},
        {2.73, 3.49, 4.59, 5.53, 7.34, 9.52, 11.03, 13.36, 15.51, 20.09, 26.12},
        {3.32, 4.17, 5.38, 6.39, 8.34, 10.66, 12.24, 14.68, 16.92, 21.67, 27.88},
        {3.94, 4.86, 6.18, 7.27, 9.34, 11.78, 13.44, 15.99, 18.31, 23.21, 29.59}
    };

    public static double chiP(int df,double chivalue)
    {
        double res=-1;
     if(df>=1&&df<=10)
     {
       double begin=0.0;
       int i=0;
       boolean found=false;
       for(;i<chitable[df-1].length;i++)
       {
         if(chivalue>=begin&&chivalue<=  chitable[df-1][i]){found=true;break;}
         begin=chitable[df-1][i];
       }
       if(found)
       {
          double ratio=(chivalue-begin)/(chitable[df-1][i]-begin);
          res=chiP[i]-(i>0?(chiP[i]-chiP[i-1]):(chiP[i]-1.0))*ratio;
       }
     }
        return res;
    }
    public static double chisquare(double data[][]) {
        double chi=0.0;
        double tot=0.0;
         for(int i=0;i<data.length;i++)
        {
         tot=Math.pow( data[i][0]-data[i][1],2);
         tot=tot/data[i][1];
         chi+=tot;
        }
        return chi;
    }
    public static double chisquare(double data[][],double maxerr) {
        double chi=0.0;
        for(int i=0;i<data.length;i++)
        {
          if(Math.abs(data[i][0]-data[i][1])<=maxerr){data[i][0]=0.5;data[i][1]=0.5;}
          else {data[i][0]=0.0;data[i][1]=0.5;}
        }
        chi=chisquare(data);
        return chi;
    }

    public static void main(String args[])
    {
       double data[][]={{639.0,660.0},{241.0,220.0}};
       double chi=chisquare(data);
       System.out.println("chi="+chi);
        System.out.println("P="+chiP(1,chi));
    }
}


//~ Formatted by Jindent --- http://www.jindent.com

