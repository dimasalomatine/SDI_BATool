/*
 * NNTestRecal.java
 *
 * Created on November 26, 2006, 10:38 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package NN2HLBP;

import Utils.LoggerNB;
import StatisticBase.STA;

/**
 *
 * @author Dmitry
 */
public class NNTestRecal {
    
    /** Creates a new instance of NNTestRecal */
    public NNTestRecal() {
    }
    public static void test_recall(Neural_2H nn, float[] inputs) {
        float[] results=null;
        results= nn.recall(inputs);
        if(LoggerNB.debuging)
        {
        System.out.print("Test case: ");
        for (int i = 0; i < inputs.length; i++) System.out.print(pp(inputs[i]) + " ");
        System.out.print(" results: ");
        for (int i = 0; i < results.length; i++) System.out.print(pp(results[i]) + " ");
        System.out.println();
        }
    }
    public static void test_recall(Neural_2HDV nn, double[] inputs) {
        double[] results=null;
        results= nn.recall(inputs);
        if(LoggerNB.debuging)
        {
        System.out.print("Test case: ");
        for (int i = 0; i < inputs.length; i++) System.out.print(pp(inputs[i]) + " ");
        System.out.print(" results: ");
        for (int i = 0; i < results.length; i++) System.out.print(pp(results[i]) + " ");
        System.out.println();
        }
    }
    public static double[] test_recall_withERR(Neural_2HDV nn, double[] inputs,double[] expects) {
        double[] results=null;
        results= nn.recall(inputs);
        if(LoggerNB.debuging)
        {
        System.out.print("Test case: ");
        for (int i = 0; i < inputs.length; i++) System.out.print(pp(inputs[i]) + " ");
        System.out.print(" results: ");
        for (int i = 0; i < results.length; i++) System.out.print(pp(results[i]) + " ");
        }
        double errsum=0.0;
        for (int i = 0; i < results.length; i++) errsum=expects[i]-results[i];
        errsum=errsum/results.length;
        if(LoggerNB.debuging)
        {
        System.out.println(String.format("ABS cumulative err %9.6f",errsum));
        }

        nn.resultbest=errsum;
        return new double[]{errsum,results[0],results[1],results[2],results[3],results[4]};
    }
    public static double[] test_recall_withERRVARIANCE(Neural_2HDV nn, double[] inputs,double[] expects) {
        double[] results=null;
        results= nn.recall(inputs);
        if(LoggerNB.debuging)
        {
        System.out.print("Test case: ");
        for (int i = 0; i < inputs.length; i++) System.out.print(pp(inputs[i]) + " ");
        System.out.print(" results: ");
        for (int i = 0; i < results.length; i++) System.out.print(pp(results[i]) + " ");
        }
        double []errs=new double[expects.length];
        for (int i = 0; i < results.length; i++) errs[i]=expects[i]-results[i];
        double mean = 0;
        double variance = 0;
        try {
            mean = STA.avg(errs);
            variance=STA.variance(errs,mean);
            variance=Math.sqrt (variance);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if(LoggerNB.debuging)
        {
        System.out.println(String.format("ABS mean %9.6f",mean)+String.format(" sigma %9.6f",variance));
        }
        nn.resultbest=variance;
         return new double[]{variance,results[0],results[1],results[2],results[3],results[4]};
      
    }
    public static double[] test_recall_withERRMEAN(Neural_2HDV nn, double[] inputs,double[] expects) {
        double[] results=null;
        results= nn.recall(inputs);
        if(LoggerNB.debuging)
        {
        System.out.print("Test case: ");
        for (int i = 0; i < inputs.length; i++) System.out.print(pp(inputs[i]) + " ");
        System.out.print(" results: ");
        for (int i = 0; i < results.length; i++) System.out.print(pp(results[i]) + " ");
        }
        double []errs=new double[expects.length];
        for (int i = 0; i < results.length; i++) errs[i]=
                Math.abs(
                (expects[i]-results[i])/expects[i]
                )
                ;
        double mean = 0;
        try {
            mean = STA.avg(errs);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        if(LoggerNB.debuging)
        {
        System.out.println(String.format("ABS mean %9.6f",mean));
        }
         nn.resultbest=mean;
        return new double[]{mean,results[0],results[1],results[2],results[3],results[4]};
    }
    public static double[] test_recall_withERRSTD_DEV(Neural_2HDV nn, double[] inputs,double[] expects) {
        double[] results=null;
        results= nn.recall(inputs);
        if(LoggerNB.debuging)
        {
        System.out.print("Test case: ");
        for (int i = 0; i < inputs.length; i++) System.out.print(pp(inputs[i]) + " ");
        System.out.print(" results: ");
        for (int i = 0; i < results.length; i++) System.out.print(pp(results[i]) + " ");
        }
        double []errs=new double[expects.length];
        for (int i = 0; i < results.length; i++) errs[i]=expects[i]-results[i];
        double mean = 0;
        double variance = 0;
        try {
            mean=STA.avg(errs);
            variance = STA.variance(errs, mean);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        double stdev=STA.std_dev(variance);
        if(LoggerNB.debuging)
        {
        System.out.print(String.format("ABS mean %9.6f",mean));
        System.out.print(String.format(" variance %9.6f",variance));
        System.out.print(String.format(" stdev %9.6f",stdev));
        System.out.println();
        }

        nn.resultbest=stdev;
        return new double[]{stdev,results[0],results[1],results[2],results[3],results[4]};
    }
    public static String pp(float x) {
        String s = "" + x + "00";
        int index = s.indexOf(".");
        if (index > -1) s = s.substring(0, index + 3);
        if (s.startsWith("-") == false) s = " " + s;
        return s;
    }
    public static String pp(double x) {
        String s = "" + x + "00";
        int index = s.indexOf(".");
        if (index > -1) s = s.substring(0, index + 3);
        if (s.startsWith("-") == false) s = " " + s;
        return s;
    }
}
