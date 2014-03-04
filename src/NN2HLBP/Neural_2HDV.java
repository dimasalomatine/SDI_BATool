/*
 * Neural_2HDV.java
 *
 * Created on November 26, 2006, 12:56 PM
 * Description:  Two hidden layer back propagtion neural network model
 *double version 
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package NN2HLBP;

/**
 *
 * @author Dmitry
 */
import java.util.*;
import java.io.*;

class Neural_2HDV implements Serializable {

    protected int numInputs;
    protected int numHidden1;
    protected int numHidden2;
    protected int numOutputs;

    protected int numTraining;

    public double inputs[];
    protected double hidden1[];
    protected double hidden2[];
    public double outputs[];

    protected double W1[][];
    protected double W2[][];
    protected double W3[][];

    protected double output_errors[];
    protected double hidden1_errors[];
    protected double hidden2_errors[];

    transient protected Vector inputTraining = new Vector();
    transient protected Vector outputTraining = new Vector();

   public double resultbest=10000;
   public int besti=0;
   public int bestj=0;
   public  int besttype=0;

    Neural_2HDV(int num_in, int num_hidden1, int num_hidden2, int num_output) {

        numInputs = num_in;
        numHidden1 = num_hidden1;
        numHidden2 = num_hidden2;
        numOutputs = num_output;
        inputs = new double[numInputs];
        hidden1 = new double[numHidden1];
        hidden2 = new double[numHidden2];
        outputs = new double[numOutputs];
        W1 = new double[numInputs][numHidden1];
        W2 = new double[numHidden1][numHidden2];
        W3 = new double[numHidden2][numOutputs];
        randomizeWeights();

        output_errors = new double[numOutputs];
        hidden1_errors = new double[numHidden1];
        hidden2_errors = new double[numHidden2];
    }

    public int getInputCount(){return numInputs;}
    public int getOutputCount(){return numOutputs;}
    
    public void addTrainingExample(double[] inputs, double[] outputs) {
        if(inputs==null||outputs==null)return;
        if (inputs.length != numInputs || outputs.length != numOutputs) {
            System.out.println("addTrainingExample(): array size is wrong");
            return;
        }
        inputTraining.addElement(inputs);
        outputTraining.addElement(outputs);
    }

    public static Neural_2HDV Factory(String serialized_file_name) {
        Neural_2HDV nn = null;
        try {
            //InputStream ins = ClassLoader.getSystemResourceAsStream(serialized_file_name);
            InputStream ins=new FileInputStream(serialized_file_name);
            if (ins == null) {
                System.out.println("Cached: failed to open "+serialized_file_name);
            } else {
                ObjectInputStream p = new ObjectInputStream(ins);
                nn = (Neural_2HDV) p.readObject();
                nn.inputTraining = new Vector();
                nn.outputTraining = new Vector();
                ins.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return nn;
    }

    public void save(String file_name) {
        try {
            //TODO buffered stream
            FileOutputStream ostream = new FileOutputStream(file_name);
            ObjectOutputStream p = new ObjectOutputStream(ostream);
            p.writeObject(this);
            p.flush();
            ostream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void randomizeWeights() {
        //TODO add another algoritm for random
        // Randomize weights here:
        for (int ii = 0; ii < numInputs; ii++)
            for (int hh = 0; hh < numHidden1; hh++)
                W1[ii][hh] =
                        0.1f *  Math.random() - 0.05f;
        for (int ii = 0; ii < numHidden1; ii++)
            for (int hh = 0; hh < numHidden2; hh++)
                W2[ii][hh] =
                        0.1f *  Math.random() - 0.05f;
        for (int hh = 0; hh < numHidden2; hh++)
            for (int oo = 0; oo < numOutputs; oo++)
                W3[hh][oo] =
                        0.1f *  Math.random() - 0.05f;
    }

    public double[] recall(double[] in) {
        for (int i = 0; i < numInputs&&i<in.length; i++) inputs[i] = in[i];
        forwardPass();
        double[] ret = new double[numOutputs];
        for (int i = 0; i < numOutputs; i++) ret[i] = outputs[i];
        return ret;
    }

    public void forwardPass() {
        int i, h, o;
        for (h = 0; h < numHidden1; h++) {
            hidden1[h] = 0.0f;
        }
        for (h = 0; h < numHidden2; h++) {
            hidden2[h] = 0.0f;
        }
        for (i = 0; i < numInputs; i++) {
            for (h = 0; h < numHidden1; h++) {
                hidden1[h] +=
                        inputs[i] * W1[i][h];
            }
        }
        for (i = 0; i < numHidden1; i++) {
            for (h = 0; h < numHidden2; h++) {
                hidden2[h] +=
                        hidden1[i] * W2[i][h];
            }
        }
        for (o = 0; o < numOutputs; o++)
            outputs[o] = 0.0f;
        for (h = 0; h < numHidden2; h++) {
            for (o = 0; o < numOutputs; o++) {
                outputs[o] +=
                        sigmoid(hidden2[h]) * W3[h][o];
            }
        }
        for (o = 0; o < numOutputs; o++)
            outputs[o] = sigmoid(outputs[o]);
    }

    public double train() 
    {
        return train(inputTraining, outputTraining);
    }

    private int current_example = 0;

    public double train(Vector ins, Vector v_outs) {
        int i, h, o;
        double error = 0.0f;
        int num_cases = ins.size();
        //for (int example=0; example<num_cases; example++) {
        // zero out error arrays:
        for (h = 0; h < numHidden1; h++)
            hidden1_errors[h] = 0.0f;
        for (h = 0; h < numHidden2; h++)
            hidden2_errors[h] = 0.0f;
        for (o = 0; o < numOutputs; o++)
            output_errors[o] = 0.0f;
        // copy the input values:
        for (i = 0; i < numInputs; i++) {
            inputs[i] = ((double[]) ins.elementAt(current_example))[i];
        }
        // copy the output values:
        double[] outs = (double[]) v_outs.elementAt(current_example);

        // perform a forward pass through the network:

        forwardPass();

        for (o = 0; o < numOutputs; o++) {
            output_errors[o] =
                    (outs[o] -
                    outputs[o])
                    * sigmoidP(outputs[o]);
        }
        for (h = 0; h < numHidden2; h++) {
            hidden2_errors[h] = 0.0f;
            for (o = 0; o < numOutputs; o++) {
                hidden2_errors[h] +=
                        output_errors[o] * W3[h][o];
            }
        }
        for (h = 0; h < numHidden1; h++) {
            hidden1_errors[h] = 0.0f;
            for (o = 0; o < numHidden2; o++) {
                hidden1_errors[h] +=
                        hidden2_errors[o] * W2[h][o];
            }
        }
        for (h = 0; h < numHidden2; h++) {
            hidden2_errors[h] =
                    hidden2_errors[h] * sigmoidP(hidden2[h]);
        }
        for (h = 0; h < numHidden1; h++) {
            hidden1_errors[h] =
                    hidden1_errors[h] * sigmoidP(hidden1[h]);
        }
        // update the hidden2 to output weights:
        for (o = 0; o < numOutputs; o++) {
            for (h = 0; h < numHidden2; h++) {
                W3[h][o] +=
                        0.5 * output_errors[o] * hidden2[h];
            }
        }
        // update the hidden1 to hidden2 weights:
        for (o = 0; o < numHidden2; o++) {
            for (h = 0; h < numHidden1; h++) {
                W2[h][o] +=
                        0.5 * hidden2_errors[o] * hidden1[h];
            }
        }
        // update the input to hidden1 weights:
        for (h = 0; h < numHidden1; h++) {
            for (i = 0; i < numInputs; i++) {
                W1[i][h] +=
                        0.5 * hidden1_errors[h] * inputs[i];
            }
        }
        for (o = 0; o < numOutputs; o++) {
            error += Math.abs(outs[o] - outputs[o]);
            //error += Math.abs(output_errors[o]);
        }
        current_example++;
        if (current_example >= num_cases) current_example = 0;
        return error;
    }

    protected double sigmoid(double x) {
        
        double returnv= (double) (1.0f / (1.0f + Math.exp((double) (-x))));
        if(Double.isInfinite(returnv)||Double.isNaN(returnv))
            return 0.0;
        return returnv;
    }

    protected double sigmoidP(double x) {
        double z = sigmoid(x); //  + 0.5f;
        return (double) (z * (1.0f - z));
    }

}
