/*
 * Neural_2H.java
 *
 * Created on November 26, 2006, 10:18 AM
 *
 * Description:  Two hidden layer back propagtion neural network model
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

class Neural_2H implements Serializable {

    protected int numInputs;
    protected int numHidden1;
    protected int numHidden2;
    protected int numOutputs;

    protected int numTraining;

    public float inputs[];
    protected float hidden1[];
    protected float hidden2[];
    public float outputs[];

    protected float W1[][];
    protected float W2[][];
    protected float W3[][];

    protected float output_errors[];
    protected float hidden1_errors[];
    protected float hidden2_errors[];

    transient protected Vector inputTraining = new Vector();
    transient protected Vector outputTraining = new Vector();

    Neural_2H(int num_in, int num_hidden1, int num_hidden2, int num_output) {

        numInputs = num_in;
        numHidden1 = num_hidden1;
        numHidden2 = num_hidden2;
        numOutputs = num_output;
        inputs = new float[numInputs];
        hidden1 = new float[numHidden1];
        hidden2 = new float[numHidden2];
        outputs = new float[numOutputs];
        W1 = new float[numInputs][numHidden1];
        W2 = new float[numHidden1][numHidden2];
        W3 = new float[numHidden2][numOutputs];
        randomizeWeights();

        output_errors = new float[numOutputs];
        hidden1_errors = new float[numHidden1];
        hidden2_errors = new float[numHidden2];
    }

    public void addTrainingExample(float[] inputs, float[] outputs) {
        if(inputs==null||outputs==null)return;
        if (inputs.length != numInputs || outputs.length != numOutputs) {
            System.out.println("addTrainingExample(): array size is wrong");
            return;
        }
        inputTraining.addElement(inputs);
        outputTraining.addElement(outputs);
    }

    public static Neural_2H Factory(String serialized_file_name) {
        Neural_2H nn = null;
        try {
            InputStream ins = ClassLoader.getSystemResourceAsStream(serialized_file_name);
            if (ins == null) {
                System.out.println("CachedExamples(): failed to open 'cache.dat' in JAR file");
                System.exit(1);
            } else {
                ObjectInputStream p = new ObjectInputStream(ins);
                nn = (Neural_2H) p.readObject();
                nn.inputTraining = new Vector();
                nn.outputTraining = new Vector();
                ins.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return nn;
    }

    public void save(String file_name) {
        try {
            FileOutputStream ostream = new FileOutputStream(file_name);
            ObjectOutputStream p = new ObjectOutputStream(ostream);
            p.writeObject(this);
            p.flush();
            ostream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void randomizeWeights() {
        // Randomize weights here:
        for (int ii = 0; ii < numInputs; ii++)
            for (int hh = 0; hh < numHidden1; hh++)
                W1[ii][hh] =
                        0.1f * (float) Math.random() - 0.05f;
        for (int ii = 0; ii < numHidden1; ii++)
            for (int hh = 0; hh < numHidden2; hh++)
                W2[ii][hh] =
                        0.1f * (float) Math.random() - 0.05f;
        for (int hh = 0; hh < numHidden2; hh++)
            for (int oo = 0; oo < numOutputs; oo++)
                W3[hh][oo] =
                        0.1f * (float) Math.random() - 0.05f;
    }

    public float[] recall(float[] in) {
        for (int i = 0; i < numInputs; i++) inputs[i] = in[i];
        forwardPass();
        float[] ret = new float[numOutputs];
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

    public float train() {
        return train(inputTraining, outputTraining);
    }

    private int current_example = 0;

    public float train(Vector ins, Vector v_outs) {
        int i, h, o;
        float error = 0.0f;
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
            inputs[i] = ((float[]) ins.elementAt(current_example))[i];
        }
        // copy the output values:
        float[] outs = (float[]) v_outs.elementAt(current_example);

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

    protected float sigmoid(float x) {
        return
                (float) (1.0f / (1.0f + Math.exp((double) (-x))));
    }

    protected float sigmoidP(float x) {
        double z = sigmoid(x); //  + 0.5f;
        return (float) (z * (1.0f - z));
    }

}

