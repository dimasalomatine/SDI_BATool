/*
 * Plot1DPanel.java
 *
 * Created on November 26, 2006, 10:47 AM
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

public class Plot1DPanel extends java.awt.Canvas {

    public Plot1DPanel(int num, float min, float max, float[] values) {
        super();
        this.num = num;
        this.min = min;
        this.max = max;
        this.values= Utils.copyFloat1DArray(values);
        colors = new Color[100];
        float x;
        for (int i = 0; i < 100; i++) 
        {
            x = 1.0f - ((float) i) * 0.0096f;
            colors[i] = new Color(x, x, x);
        }
    }

    private int num;
    private float min, max;
    private float temp;
    private float[] values = null;
    private Color[] colors;

    //public void plot(float [] values) {
    //}
    public void paint(Graphics g) {
        //System.out.println("Plot1DPanel: values="+values);
        if (values == null) return;
        int delta_width = this.getWidth() / num;
        int delta_height = this.getHeight() / num;
        for (int i = 0; i < num; i++) {
            //System.out.println(this.toString() + ", values[" + i + "]=" + values[i]);
            temp = 100.0f * (values[i] - min) / (max - min);
            int ii = (int) temp;
            if (ii < 0) ii = 0;
            if (ii > 99) ii = 99;
            g.setColor(colors[ii]);
            g.fillRect(i * delta_width, 0, delta_width, delta_height);
        }
    }
}
