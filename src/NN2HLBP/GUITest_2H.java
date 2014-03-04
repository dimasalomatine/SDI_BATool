/*
 * GUITest_2H.java
 *
 * Created on November 26, 2006, 10:46 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package NN2HLBP;

/**
 *
 * @author Dmitry
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUITest_2H extends JFrame {
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

    Neural_2H nn = new Neural_2H(2, 2, 2, 2);
    Plot1DPanel inputPanel = new Plot1DPanel(2,   0.0f, 1.0f, nn.inputs);
    Plot1DPanel hidden1Panel = new Plot1DPanel(2, 0.0f, 1.0f, nn.hidden1);
    Plot1DPanel hidden2Panel = new Plot1DPanel(2, 0.0f, 1.0f, nn.hidden2);
    Plot1DPanel outputPanel = new Plot1DPanel(2,  0.0f, 1.0f, nn.outputs);
    Plot2DPanel w1Panel = new Plot2DPanel(2, 2, -2.0f, 2.0f, nn.W1);
    Plot2DPanel w2Panel = new Plot2DPanel(2, 2, -2.0f, 2.0f, nn.W2);
    Plot2DPanel w3Panel = new Plot2DPanel(2, 2, -2.0f, 2.0f, nn.W3);
    JButton jButton1 = new JButton();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel2b = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JLabel jLabel4b = new JLabel();
    JLabel jLabel5 = new JLabel();

    public GUITest_2H() {
        try
        {
            jbInit();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        this.setSize(450, 450);
        this.setVisible(true);
    }
    public void initiputsoutputs()
    {
        int i;
        for(i=0;i<in.length&&i<out.length;i++)nn.addTrainingExample(in[i], out[i]);
    }
    public static void main(String[] args) {
        GUITest_2H GUITest_1H1 = new GUITest_2H();
        GUITest_1H1.initiputsoutputs();
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(null);
        inputPanel.setBounds(new Rectangle(5, 30, 400, 20));
        hidden1Panel.setBounds(new Rectangle(5, 138, 400, 20));
        hidden2Panel.setBounds(new Rectangle(5, 238, 400, 20));
        outputPanel.setBounds(new Rectangle(5, 340, 400, 20));
        w1Panel.setBounds(new Rectangle(160, 50, 60, 60));
        w2Panel.setBounds(new Rectangle(160, 158, 60, 60));
        w3Panel.setBounds(new Rectangle(160, 258, 60, 60));
        jButton1.setText("Reset and Run");
        jButton1.setBounds(new Rectangle(246, 380, 148, 28));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                do_run_button(e);
            }
        });
        this.setDefaultCloseOperation(3);
        jLabel1.setText("Input neurons:");
        jLabel1.setBounds(new Rectangle(4, 3, 144, 19));
        jLabel2.setText("Hidden 1 neurons:");
        jLabel2.setBounds(new Rectangle(4, 102, 144, 19));
        jLabel2b.setText("Hidden 2 neurons:");
        jLabel2b.setBounds(new Rectangle(4, 202, 144, 19));
        jLabel3.setText("Output neurons:");
        jLabel3.setBounds(new Rectangle(4, 308, 240, 19));
        jLabel4.setText("input to hidden 1 weights");
        jLabel4.setBounds(new Rectangle(230, 80, 170, 19));
        jLabel4b.setText("hidden 1 to hidden 2 weights");
        jLabel4b.setBounds(new Rectangle(230, 180, 170, 19));
        jLabel5.setText("hidden 2 to output weights");
        jLabel5.setBounds(new Rectangle(230, 280, 170, 19));
        this.getContentPane().add(inputPanel, null);
        this.getContentPane().add(hidden1Panel, null);
        this.getContentPane().add(hidden2Panel, null);
        this.getContentPane().add(outputPanel, null);
        this.getContentPane().add(w1Panel, null);
        this.getContentPane().add(w2Panel, null);
        this.getContentPane().add(w3Panel, null);
        this.getContentPane().add(jButton1, null);
        this.getContentPane().add(jLabel1, null);
        this.getContentPane().add(jLabel2, null);
        this.getContentPane().add(jLabel2b, null);
        this.getContentPane().add(jLabel3, null);
        this.getContentPane().add(jLabel4, null);
        this.getContentPane().add(jLabel4b, null);
        this.getContentPane().add(jLabel5, null);
    }

    void do_run_button(MouseEvent e) {
        Graphics g1 = inputPanel.getGraphics();
        Graphics g2 = hidden1Panel.getGraphics();
        Graphics g3 = hidden2Panel.getGraphics();
        Graphics g4 = outputPanel.getGraphics();
        Graphics g5 = w1Panel.getGraphics();
        Graphics g6 = w2Panel.getGraphics();
        Graphics g7 = w3Panel.getGraphics();
        for (int i = 0; i < 5600; i++) {
            float error = nn.train();
            if (i > 0 && i % 200 == 0) System.out.println("cycle " + i + " error is " + error);
            inputPanel.paint(g1);
            hidden1Panel.paint(g2);
            hidden2Panel.paint(g3);
            outputPanel.paint(g4);
            w1Panel.paint(g5);
            w2Panel.paint(g6);
            w3Panel.paint(g7);
        }
    }
}

