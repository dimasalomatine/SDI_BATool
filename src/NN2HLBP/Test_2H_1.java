/*
 * Test_2H_1.java
 *
 * Created on November 26, 2006, 10:21 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package NN2HLBP;

/**
 *
 * @author Dmitry
 */
public class Test_2H_1 extends NNTestRecal{

    static float[][] in = {
                            {0.1f,0.0f},
                            {0.2f,0.0f},
                            {0.3f,0.0f},
                            {0.4f,0.0f},
                            {0.1f,0.1f},
                            {0.2f,0.1f},
                            {0.3f,0.1f},
                            {0.4f,0.1f},
                            {0.1f,0.2f},
                            {0.2f,0.2f},
                            {0.3f,0.2f},
                            {0.4f,0.2f},
                            {0.1f,0.3f},
                            {0.2f,0.3f},
                            {0.3f,0.3f},
                            {0.4f,0.3f}
    };
    static float[][] out = {
                        {1.0f,0.1f},
                        {0.2f,0.1f},
                        {0.04f,0.1f},
                        {0.008f,0.1f},
                        {0.5f,0.1f},
                        {0.3f,0.1f},
                        {0.2f,0.1f},
                        {0.01f,0.1f},
                        {0.0f,0.01f},
                        {0.55f,0.1f},
                        {0.75f,0.1f},
                        {0.55f,0.2f},
                        {0.85f,0.1f},
                        {0.55f,0.01f},
                        {0.055f,0.1f},
                        {0.055f,0.01f}
    };

    static float[][] testDATA = {
                            {0.1f,0.0f},
                            {0.2f,0.1f},
                            {0.3f,0.2f},
                            {0.4f,0.3f}
    };


    public static void main(String[] args) {
        Neural_2H nn = new Neural_2H(2, 10, 10, 2);
        int i;
        for(i=0;i<in.length&&i<out.length;i++)nn.addTrainingExample(in[i], out[i]);
        double error = 0;
        for (  i = 0; i < 100000; i++) {
            error += nn.train();
            if (i > 0 && (i % 100 == 0)) {
                error /= 100;
                System.out.println("cycle " + i + " error is " + error);
                error = 0;
		            }
        }
        for(i=0;i<testDATA.length;i++)test_recall(nn, testDATA[i]);
        
        nn.save("test.neural");
      /*  
	System.out.println("Reload a previously trained NN from disk and re-test:");
        Neural_2H nn2 = Neural_2H.Factory("test.neural");
        nn2.addTrainingExample(in1, out1);
        nn2.addTrainingExample(in2, out2);
        nn2.addTrainingExample(in3, out3);
        nn2.addTrainingExample(in4, out4);
        nn2.addTrainingExample(in5, out5);
        nn2.addTrainingExample(in6, out6);
        nn2.addTrainingExample(in7, out7);
        nn2.addTrainingExample(in8, out8);
        nn2.addTrainingExample(in9, out9);
        nn2.addTrainingExample(in10, out10);
*/
    }
}
