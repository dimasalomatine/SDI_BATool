/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package charts;

import Utils.TDoc;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import org.jfree.chart.*;
//import org.jfree.chart.;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.*;

/**
 *
 * @author Dmitry
 */
public class xyscaterchart {
    String title="";
    String wtitle="Scater Hhart";
    boolean c3d=false;
    JFreeChart chart=null;
    ChartFrame frame1=null;
     public void setdata(java.util.List atd,String xt,String yt)
    {
          XYDataset series = new SampleXYDataset2(atd.size(),1,atd,3,1);    
       chart = ChartFactory.createScatterPlot (title, xt, yt,series, PlotOrientation.VERTICAL, true, true, false);
 
        NumberAxis domainAxis = (NumberAxis) chart.getXYPlot().getDomainAxis();
        domainAxis.setAutoRangeIncludesZero(false);
        
         // PAINT OVER CHART
        /*
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setColor(Color.red);
        g2.fill(new Rectangle2D.Double(0, 0, 99, 99));
       Rectangle2D  r2= new Rectangle2D.Double(1, 1, 20, 50);
        chart.draw(g2, r2);
        */

      
     frame1=new ChartFrame(wtitle,chart);
     
 frame1.pack();
frame1.setVisible(true);

    }
  
public xyscaterchart(String wtitle,String title)
{
   this.title=title;
   this.wtitle=wtitle;

}
}
