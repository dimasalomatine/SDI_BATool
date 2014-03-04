/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package charts;
import Utils.TDoc;
import org.jfree.chart.*;
import org.jfree.data.category.*;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.*;
import org.jfree.data.*;
import org.jfree.chart.renderer.category.*;
import org.jfree.chart.plot.*;
import java.awt.*;
/**
 *
 * @author Dmitry
 */
public class jbarchart {
     String title="";
    String wtitle="Pie Chart";
    String xt="X";
    String yt="Y";
    boolean c3d=false;
    JFreeChart chart=null;
    ChartFrame frame1=null;

public jbarchart(String wtitle,String title,String xt,String yt,boolean c3d)
{
   this.title=title;
   this.wtitle=wtitle;
   this.xt=xt;
   this.yt=yt;
   this.c3d=c3d;

}

public void setdata(java.util.List atd,String labels[],int oncolumn,int tocolumn)
    {
     DefaultCategoryDataset dataset = new DefaultCategoryDataset();
  
     
  for (int i=0;i<atd.size();i++)
  {
   TDoc t=(TDoc)atd.get(i);
   int k=0;
   for(int j=oncolumn;j<=tocolumn;j++)
   {
       dataset.setValue( (Double)t.o[j], labels[k], (String)t.o[0]);
       k++;
   }
   //dataset.setValue( (Double)t.o[oncolumn]+1, "Real",(String)t.o[0]);
   //dataset.setValue( (Double)t.o[oncolumn], "Ei", (String)t.o[0]);
  }
     if(c3d==false)
     {
    chart = ChartFactory.createBarChart3D
    (title,xt, yt, dataset, PlotOrientation.VERTICAL, true,true, false);
  chart.setBackgroundPaint(Color.yellow);
  chart.getTitle().setPaint(Color.blue); 
  CategoryPlot p = chart.getCategoryPlot(); 
  p.setRangeGridlinePaint(Color.red); 
     }else
     {
   
     }
     frame1=new ChartFrame(wtitle,chart);
     
 frame1.pack();
frame1.setVisible(true);
    }

}

  
    