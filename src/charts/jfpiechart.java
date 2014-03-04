/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package charts;
import Utils.TDoc;
import javax.swing.GroupLayout.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Dmitry
 */
public class jfpiechart {
    
    String title="";
    String wtitle="Pie Chart";
    boolean c3d=false;
    JFreeChart chart=null;
    ChartFrame frame1=null;
    public jfpiechart  close()
    {
        /*
        if(frame1!=null)
        {
     this.frame1.dispose();
     
     frame1=null;}
         * */
     return null;
    }
    public void setdata(java.util.List atd,int oncolumn)
    {
     DefaultPieDataset pieDataset = new DefaultPieDataset();
     
  for (int i=0;i<atd.size();i++)
  {
   TDoc t=(TDoc)atd.get(i);
   pieDataset.setValue((String)t.o[0], (Double)t.o[oncolumn]);
  }
     if(c3d==false)
     {
   chart = ChartFactory.createPieChart (title, pieDataset, true,true,true);
     }else
     {
   chart = ChartFactory.createPieChart3D (title , pieDataset, true,true,true);
  PiePlot3D p=(PiePlot3D)chart.getPlot();
  p.setForegroundAlpha(0.5f);
     }
     frame1=new ChartFrame(wtitle,chart);
     
 frame1.pack();
frame1.setVisible(true);
    }
public jfpiechart(String wtitle,String title,boolean c3d)
{
   this.title=title;
   this.wtitle=wtitle;
   this.c3d=c3d;

}
 
}
