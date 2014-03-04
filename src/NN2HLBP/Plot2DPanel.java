/*
 * Plot2DPanel.java
 *
 * Created on November 26, 2006, 10:45 AM
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
import Utils.Utils;

public class Plot2DPanel extends java.awt.Canvas { // JPanel {

    public Plot2DPanel(int num1, int num2, float min, float max, float[][] values) {
        super();
        this.num1 = num1;
        this.num2 = num2;
        this.min = min;
        this.max = max;
        this.values = Utils.copyFloat2DArray(values);
        colors = new Color[100];
        for (int i = 0; i < 100; i++) {
            float x = 1.0f - ((float) i) * 0.0096f;
            colors[i] = new Color(x, x, x);
        }
    }

    private int num1;
    private int num2;
    private float min, max;
    private float temp;
    private float[][] values = null;
    private Color[] colors;

    public void paint(Graphics g) {
        if (values == null) return;
        int delta_width = this.getWidth() / num1;
        int delta_height = this.getHeight() / num2;
        for (int i = 0; i < num1; i++) {
            for (int j = 0; j < num2; j++) {
                //System.out.println(this.toString() + ", values[" + i + "]=" + values[i]);
                temp = 100.0f * (values[i][j] - min) / (max - min);
                int ii = (int) temp;
                if (ii < 0) ii = 0;
                if (ii > 99) ii = 99;
                g.setColor(colors[ii]);
                g.fillRect(i * delta_width, j * delta_height, delta_width, delta_height);
            }
        }
    }
}

