/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package charts;
import Utils.TDoc;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.*;
/**
 *
 * @author Dmitry
 */
public class jxychart {

String title="";
    String wtitle="Pie Chart";
    boolean c3d=false;
    JFreeChart chart=null;
    ChartFrame frame1=null;
     public void setdata(java.util.List atd,String xt,String yt,int oncolumn)
    {
          XYSeries series = new XYSeries(title);
     
  for (int i=0;i<atd.size();i++)
  {
   TDoc t=(TDoc)atd.get(i);
  series.add((Double)t.o[0], (Double)t.o[oncolumn]);
  }
     
    XYDataset xyDataset = new XYSeriesCollection(series);
  chart = ChartFactory.createXYLineChart (title, xt, yt,xyDataset, PlotOrientation.VERTICAL, true, true, false);
  
     frame1=new ChartFrame(wtitle,chart);
     
 frame1.pack();
frame1.setVisible(true);
    }
   public void setdata(java.util.List atd,String xt,String yt)
    {
          XYSeries series = new XYSeries(title);
     
  for (int i=0;i<atd.size();i++)
  {
   TDoc t=(TDoc)atd.get(i);
  series.add((Double)t.o[1], (Double)t.o[3]);
  }
     
    XYDataset xyDataset = new XYSeriesCollection(series);
  chart = ChartFactory.createXYLineChart (title, xt, yt,xyDataset, PlotOrientation.VERTICAL, true, true, false);
 
     frame1=new ChartFrame(wtitle,chart);
     
 frame1.pack();
frame1.setVisible(true);
    }
public jxychart(String wtitle,String title)
{
   this.title=title;
   this.wtitle=wtitle;

}
} 
